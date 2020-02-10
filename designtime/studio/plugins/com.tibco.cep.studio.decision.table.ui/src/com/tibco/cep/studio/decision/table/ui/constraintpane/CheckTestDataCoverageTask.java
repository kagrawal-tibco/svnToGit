package com.tibco.cep.studio.decision.table.ui.constraintpane;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.decision.table.constraintpane.Cell;
import com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerConstants;
import com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerView;
import com.tibco.cep.studio.decision.table.constraintpane.EqualsFilter;
import com.tibco.cep.studio.decision.table.constraintpane.Filter;
import com.tibco.cep.studio.decision.table.constraintpane.Operator;
import com.tibco.cep.studio.decision.table.constraintpane.RangeFilter;
import com.tibco.cep.studio.tester.core.model.TestDataModel;
import com.tibco.cep.studio.tester.core.utils.TesterCoreUtils;

/**
 * @author vdhumal
 *
 */
public class CheckTestDataCoverageTask {

	private DecisionTableAnalyzerView analyzerView = null;
	private String projectName = null;
	private String entityPath = null;
	private String testdataFile = null;
	private HashMap<String, Integer> fRowCacheMap = null;
	
	public CheckTestDataCoverageTask(String projectName, String entityPath, String testDataFile, DecisionTableAnalyzerView analyzerView) {
		this.projectName = projectName;
		this.entityPath = entityPath;
		this.testdataFile = TesterCoreUtils.FORWARD_SLASH + testDataFile;
		this.analyzerView = analyzerView;
	}
	
	public void run() throws Exception {
		runTestData();
	}
	
	/**
	 * @throws Exception
	 */
	private void runTestData() throws Exception {
		TestDataModel testDataModel = getTestDataModel(projectName, entityPath, testdataFile);
		Map<Integer, List<String>> testDataCoverageResult = new HashMap<Integer, List<String>>();

		List<List<String>> selectedTestData = DecisionTableTestDataCoverageUtils.getSelectedTestData(testDataModel);
		for (int row = 0; row < selectedTestData.size(); row++) {
			List<String> testDataRow = selectedTestData.get(row);
			
			Map<String, String> conditionsScopeMap = new HashMap<String, String>();
			populateConditionsScope(projectName, entityPath, testDataModel, conditionsScopeMap);

			HashMap<String, List<String>> cellsToHighlight = new HashMap<String, List<String>>();			
			processEntityTestData(testDataModel, testDataRow, conditionsScopeMap, cellsToHighlight);			
			Set<String> ruleIdsSet = cellsToHighlight.keySet();
			
			int testDataScopeConds = conditionsScopeMap.keySet().size();			
			for (String ruleId : ruleIdsSet) {
				List<String> list = cellsToHighlight.get(ruleId);
				if (list == null) {
					continue;
				}
				if (list.size() == testDataScopeConds) {
					List<String> coveredRulesList = testDataCoverageResult.get(row);
					if (coveredRulesList == null) {
						coveredRulesList = new ArrayList<String>();
						testDataCoverageResult.put(row, coveredRulesList);
					}	
					coveredRulesList.add(ruleId);					
				} else {
					// iterate over cells *not* included in the list
					// if those cells are blank/empty, then this row
					// should still be highlighted
					boolean shouldHighlight = true;
					Set<Map.Entry<String, String>> conditionsScopeMapEntries = conditionsScopeMap.entrySet();
					for (Map.Entry<String, String> entry : conditionsScopeMapEntries) {
						String columnName = entry.getValue();
						// the list contains the column ID, not the column index
						// need to get the TRV, then check whether the column ID
						// exists in the list
						int actualRow = getRowNumber(analyzerView.getCurrentEditor().getTable(), ruleId);
						int colIndx = getConditionColumnIndex(analyzerView.getCurrentEditor().getTable(), columnName);						
						Object valueAt = analyzerView.getCurrentEditor().getModelDataByPosition(colIndx, actualRow, analyzerView.getCurrentEditor().getDtDesignViewer().getDecisionTable());
						if (valueAt instanceof TableRuleVariable) {
							TableRuleVariable trv = (TableRuleVariable)valueAt;
							if (!trv.isEnabled() || trv.getExpr().trim().isEmpty() || trv.getExpr().equals(DecisionTableAnalyzerConstants.DONT_CARE)) { //Asterisk/Empty/Disabled cells
								continue;
							}
							if (list.contains(trv.getColId())) {
								continue;
							}
							shouldHighlight = false;
							break;
						} 
						//Check for non TRV cell
					}
					
					if (shouldHighlight) {
						List<String> coveredRulesList = testDataCoverageResult.get(row);
						if (coveredRulesList == null) {
							coveredRulesList = new ArrayList<String>();
							testDataCoverageResult.put(row, coveredRulesList);
						}	
						coveredRulesList.add(ruleId);
					}
				}				
			}
		}
		
		analyzerView.setTestDataModel(testDataModel);
		analyzerView.setTestDataCoverageResult(testDataCoverageResult);
	}

	/**
	 * @param projectName
	 * @param entityPath
	 * @param testDataModel
	 * @param conditionsScopeMap
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> populateConditionsScope(String projectName, String entityPath, TestDataModel testDataModel, Map<String, String> conditionsScopeMap) throws Exception {
		Columns columns = analyzerView.getCurrentEditor().getTable().getDecisionTable().getColumns();
		Iterator<String> itr = testDataModel.getTableColumnNames().iterator();		
		while (itr.hasNext()) {		
			String testDataColumnName = itr.next();
			String propertyPath = entityPath + TesterCoreUtils.FORWARD_SLASH + testDataColumnName;
			for(int col = 0; col < columns.getColumn().size(); col++) {
				Column column = columns.getColumn().get(col);
				if (ColumnType.CONDITION.equals(column.getColumnType()) && propertyPath.equals(column.getPropertyPath())) {
					conditionsScopeMap.put(propertyPath, column.getName());
					break;
				}
			}
		}		
		return conditionsScopeMap;
	}


	
	/**
	 * @param columnName
	 * @param comp
	 * @param cellsToHighlight
	 */
	private void processColumn(String columnName, String columnValue, Map<String, List<String>> cellsToHighlight) {
		List<Filter> allFilters = analyzerView.getCurrentOptimizedTable().getAllFilters(columnName);
		if (allFilters.size() == 0) {
			return;
		}
		Filter filter = allFilters.get(0);
		if (columnValue != null) {
			if (filter instanceof EqualsFilter) {
				processEquality(columnName, columnValue, (EqualsFilter)filter, cellsToHighlight);
			} else {

				EqualsFilter equalFilter = null;
				RangeFilter rangeFilter = null;

				for(Filter filtertype : allFilters){
					if(filtertype instanceof EqualsFilter) {
						equalFilter = (EqualsFilter)filtertype;
					}else if(filtertype instanceof RangeFilter){
						rangeFilter = (RangeFilter)filtertype;
					}	
				}
				try {
					int type = getColumnTypeForParsing(columnName,analyzerView.getCurrentEditor().getTable().getDecisionTable().getColumns());
					if(type==PROPERTY_TYPES.DOUBLE_VALUE){
						double  columnValueDouble = Double.parseDouble(columnValue);
						if(rangeFilter != null){
							processRangeFilter(rangeFilter, columnName, columnValueDouble, cellsToHighlight);
						}
						if(equalFilter != null){
							processEquality(columnName, columnValue, equalFilter, cellsToHighlight);	
						}
					}else if(type==PROPERTY_TYPES.DATE_TIME_VALUE){
						DateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd'T'HH:mm:ss");
						try {
							Date columnValueDate = format.parse(columnValue);
							processRangeFilter(rangeFilter, columnName, columnValueDate, cellsToHighlight);

						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}else{
						long columnValueLong = Long.parseLong(columnValue);
						if(rangeFilter != null){
							processRangeFilter(rangeFilter, columnName, columnValueLong, cellsToHighlight);
						}
						if(equalFilter != null){
							processEquality(columnName, columnValue, equalFilter, cellsToHighlight);	
						}
					}

				} catch (NumberFormatException nfe) {
					//Ignore 
				}
			}
		} else {
			Collection<LinkedHashSet<Cell>> values = ((EqualsFilter)filter).getValues();				
			for (Set<Cell> set : values) {
				for (Cell cell : set) {
					addCellEntry(columnName, cellsToHighlight, cell.getId());
				}
			}
		}
	}

	private int getColumnTypeForParsing(String columnName, Columns columns) {

		for(int col = 0; col < columns.getColumn().size(); col++) {
			Column column = columns.getColumn().get(col);
			if (column.getName().equalsIgnoreCase(columnName)) {
				return column.getPropertyType();
			}
		}

		return 1;
	}

	/**
	 * @param testDataModel
	 * @param testDataRow
	 * @param conditionsScopeMap
	 * @param cellsToHighlight
	 * @throws Exception
	 */
	private void processEntityTestData(TestDataModel testDataModel, List<String> testDataRow, Map<String, String> conditionsScopeMap, Map<String, List<String>> cellsToHighlight) throws Exception {
		for (int col = 1; col < testDataModel.getTableColumnNames().size(); col++) {				
			String testDataColumnName = testDataModel.getTableColumnNames().get(col);
			String propertyPath = testDataModel.getEntity().getFullPath() + TesterCoreUtils.FORWARD_SLASH + testDataColumnName;
			String columnName = conditionsScopeMap.get(propertyPath);				
			if (columnName != null) {
				processColumn(columnName, testDataRow.get(col), cellsToHighlight);
			} else {
				PropertyDefinition property = DecisionTableTestDataCoverageUtils.getConceptPropertyDefinition(testDataModel, testDataColumnName);
				if (DecisionTableTestDataCoverageUtils.isContainedOrReferenceConcept(property, testDataColumnName)) {
					String testDataRowLocation = testDataRow.get(col);
					String parts[] = testDataRowLocation.split(TesterCoreUtils.MARKER);
					if(parts.length == 2){
						try {
							int rowNum = Integer.parseInt(parts[0]);
							String testDataFilePath = parts[1];
							testDataFilePath = testDataFilePath + TesterCoreUtils.CONCEPT_FILE_EXT;
							testDataModel = getTestDataModel(projectName, property.getConceptTypePath(), testDataFilePath);
							populateConditionsScope(projectName, property.getConceptTypePath(), testDataModel, conditionsScopeMap);
							testDataRow = testDataModel.getTestData().get(rowNum);
							processEntityTestData(testDataModel, testDataRow, conditionsScopeMap, cellsToHighlight);
						} catch (NumberFormatException nfe) {
							//Log
						}
					}
				}
			}
		}			  			
	}
	
	/**
	 * @param projectName
	 * @param entityPath
	 * @param testDataFile
	 * @return
	 * @throws Exception
	 */
	private TestDataModel getTestDataModel(String projectName, String entityPath, String testDataFile) throws Exception {

		Entity entity = IndexUtils.getEntity(projectName, entityPath);
		EList<PropertyDefinition> list = null;						
		if(entity instanceof Scorecard){
			list = ((Scorecard) entity).getAllProperties();
		}
		else if(entity instanceof Event){
			list = ((Event) entity).getAllUserProperties();
		}
		else if(entity instanceof Concept){
			list = ((Concept) entity).getAllProperties();									
		}

		ArrayList<String> tableColumnNames = new ArrayList<String>();

		String entityType = null;
		if (entity instanceof Scorecard) {
			entityType = "Scorecard";
		} else if (entity instanceof Event) {
			entityType = "Event";
			tableColumnNames.add("Payload");
		} else if (entity instanceof Concept) {
			entityType = "Concept";
		}

		tableColumnNames.add("ExtId");
		Iterator<PropertyDefinition> itr2 = list.iterator();
		while(itr2.hasNext()){
			PropertyDefinition pd = itr2.next();
			tableColumnNames.add(pd.getName());
		}

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		String testDataFileLocation = project.getLocation() + testDataFile;
		TestDataModel testDataModel = TesterCoreUtils.getDataFromXML(testDataFileLocation, entityType, entity, tableColumnNames);
		
		return testDataModel;
	}	
	
	/**
	 * @param columnName
	 * @param arr
	 * @param filter
	 * @param cellIds
	 */
	private void processEquality(String columnName, String columnValue, EqualsFilter filter, Map<String, List<String>> cellIds) {
		List<Cell> list = null;
		if (DecisionTableAnalyzerConstants.DONT_CARE.equals(columnValue)) {
			//DecisionTableUIPlugin.debug(CLASS, "Found Dont Care condition for column {0}", columnName);
			//Return all values in the map as we don't care. It is like no filter case
			Collection<LinkedHashSet<Cell>> allValues = filter.getValues();
			list = new ArrayList<Cell>();
			//Create sublists
			for (LinkedHashSet<Cell> value : allValues) {
				List<Cell> tempList = new ArrayList<Cell>(value);
				//Add it to main list
				list.addAll(tempList);
			}
			
		} else {
//			list = new ArrayList<Cell>(filter.map.get(object));
			list = new ArrayList<Cell>(filter.getFilteredValues(columnValue));
		}
		if (list != null) {
			for (Cell cell : list) {
				// need to (potentially) highlight this cell
				if (true == cell.isEnabled()) {
					String id = cell.getId();
					addCellEntry(columnName, cellIds, id);
				}
			}
		}
		if (DecisionTableAnalyzerConstants.DONT_CARE.equals(columnValue)) {
			// break it here. We are not interested in processing other values.
			//break;
		}
	}	
			
	/**
	 * @param filter
	 * @param columnId
	 * @param columnValue
	 * @param cellIds
	 */
	private void processRangeFilter(RangeFilter filter, String columnId, Object columnValue, Map<String, List<String>> cellIds) {
		TreeMap<Object, LinkedHashSet<Cell>> rangeMap = filter.getRangeMap();
//		SortedMap<Integer, List<Cell>> subMap = rangeMap.subMap(Integer.valueOf(lowValue-1), Integer.valueOf(highValue+1));
		Collection<LinkedHashSet<Cell>> values = rangeMap.values();
		List<Cell> processedCells = new ArrayList<Cell>();
		for (Set<Cell> set : values) {
			for (Cell cell : set) {
				if (processedCells.contains(cell)) {
					continue;
				}
				processedCells.add(cell);
				// need to (potentially) highlight this cell
				String id = cell.getId();
				// check if in range
				boolean inRange = isInRange(columnValue, cell);
				if (inRange) {
					addCellEntry(columnId, cellIds, id);
				}
			}
		}
	}

	/**
	 * @param columnValue
	 * @param cell
	 * @return
	 */
	private boolean isInRange(Object columnValue, Cell cell) {
		Object[] operands = cell.getExpression().getOperands();
		if(cell.getCellInfo().getType() == PROPERTY_TYPES.DOUBLE_VALUE && (operands[0] instanceof Long || operands[1] instanceof Long)){
			if(operands[0].equals(Long.MIN_VALUE)){
				operands[0] = Double.valueOf(Double.MIN_VALUE);
			}else{
				operands[0] = Double.valueOf(operands[0].toString());
			}
			if(operands[1].equals(Long.MAX_VALUE)){
				operands[1] = Double.valueOf(Double.MAX_VALUE);
			}else{
				operands[1] = Double.valueOf(operands[1].toString());
			}
		}
		
		Object opMin = getOpValue(operands[0]);
		Object opMax = getOpValue(operands[1]);

		switch (cell.getExpression().getRangeKind()) {
            case Operator.RANGE_BOUNDED: {
                if (isInRange(columnValue, opMin, opMax, true, true) 
                		&& isInRange(columnValue, opMin, opMax, true, true)) {
                	return true;
                }
                break;
            }
            case Operator.RANGE_MIN_EXCL_BOUNDED:
            {
                if (isInRange(columnValue, opMin, opMax, false, true)
                		&& isInRange(columnValue, opMin, opMax, false, true)) {
                	return true;
                }
                break;
            }
            case Operator.RANGE_MAX_EXCL_BOUNDED:
            {
                if (isInRange(columnValue, opMin, opMax, true, false)
                		&& isInRange(columnValue, opMin, opMax, true, false)) {
                	return true;
                }
                break;
            }
            case Operator.RANGE_MIN_MAX_EXCL_BOUNDED:
            {
                if (isInRange(columnValue, opMin, opMax, false, false)
                		&& isInRange(columnValue, opMin, opMax, false, false)) {
                	return true;
                }
                break;
            }
        }
        return false;
	}
	
	/**
	 * @param value
	 * @param minRange
	 * @param maxRange
	 * @param minInclusive
	 * @param maxInclusive
	 * @return
	 */
	private boolean isInRange(Object value, Object minRange, Object maxRange, boolean minInclusive, boolean maxInclusive) {
		if (value instanceof Double && minRange instanceof Double
				&& maxRange instanceof Double) {
			if (minInclusive) {

				if (!((double) value >= (double) minRange))
					return false;
			} else {
				if (!((double) value > (double) minRange))
					return false;
			}

			if (maxInclusive) {
				if (!((double) value <= (double) maxRange))
					return false;
			} else {
				if (!((double) value < (double) maxRange))
					return false;
			}
		}else if (value instanceof Date && minRange instanceof Date
				&& maxRange instanceof Date) {
			if (minInclusive) {

				if (!(((Date) value).compareTo((Date) minRange) >= 0))
					return false;
			} else {
				if (!(((Date) value).compareTo((Date) minRange) > 0))
					return false;
			}

			if (maxInclusive) {
				if (!(((Date) value).compareTo((Date) maxRange) <= 0))
					return false;
			} else {
				if (!(((Date) value).compareTo((Date) maxRange) < 0))
					return false;
			}
		}else{
			if (minInclusive) {
				if (!((long)value >= (long)minRange)) return false;
			} else {
				if (!((long)value > (long)minRange)) return false;
			}
			if (maxInclusive) {
				if (!((long)value <= (long)maxRange)) return false;
			} else {
				if (!((long)value < (long)maxRange)) return false;
			}

		}
		return true;
	}
	
	/**
	 * @param object
	 * @return
	 */
	private Object getOpValue(Object object) {
    	if (object instanceof Long) {
    		return (Long) object;
    	}else if(object instanceof Double){
    		return (Double)object;
    	}else if(object instanceof Date){
    		return (Date)object;
    	}else if (object instanceof Integer) {
    		return (Integer)object;
    	} else if (object instanceof String){
            String op = (String) object;
            if (op.length() > 0 && op.charAt(0) == '"') {
            	op = op.substring(1, op.length() - 1);
            }
    		return Long.valueOf(op);
    	}
    	return 0;
	}
	
	/**
	 * @param columnName
	 * @param cellIds
	 * @param id
	 */
	private void addCellEntry(String columnName, Map<String, List<String>> cellIds, String id) {
		String[] split = id.split("_");
		addEntry(cellIds, split);
	}
	
	/**
	 * @param cellIds
	 * @param split
	 */
	private void addEntry(Map<String, List<String>> cellIds, String[] split) {
		String key = split[0];
		String value = split[1];

		List<String> list = cellIds.get(key);
		if (list == null) {
			list = new ArrayList<String>();
			list.add(value);
			cellIds.put(key, list);
		} else {
			if (!list.contains(value)) {
				list.add(value);
			}
		}
	}
	
	/**
	 * @param model
	 * @param rowId
	 * @return
	 */
	private int getRowNumber(Table table, String ruleId) {
		if (fRowCacheMap == null) {
			initRowCache(table);
		}
		Integer rowValue = fRowCacheMap.get(ruleId);
		if (rowValue != null) {
			return rowValue;
		}
		int rowIndx = -1;
		EList<TableRule> rules = table.getDecisionTable().getRule();
		for (int i = 0; i < rules.size(); i++) {
			TableRule rule = rules.get(i);
			if (rule.getId().equals(ruleId)) {
				rowIndx = i;
				break;
			}
		}	
		return rowIndx;
	}

	/**
	 * @param table
	 * @param columnName
	 * @return
	 */
	private int getConditionColumnIndex(Table table, String columnName) {
		int colIndx = -1;
		Columns columns = table.getDecisionTable().getColumns();
		for (int i = 0; i < columns.getColumn().size(); i++) {
			Column col = columns.getColumn().get(i);
			if (ColumnType.CONDITION.equals(col.getColumnType()) && col.getName().equals(columnName)) {
				colIndx = i;
				break;
			}
		}	
		return colIndx;
	}

	/**
	 * @param model
	 */
	private void initRowCache(Table table) {
		fRowCacheMap = new HashMap<String, Integer>();
		EList<TableRule> rules = table.getDecisionTable().getRule();
		for (int i = 0; i < rules.size(); i++) {
			TableRule rule = rules.get(i);
			fRowCacheMap.put(rule.getId(), i);
		}	
	}
	
	public void clearCache() {
//		fRowCacheMap.clear();
		fRowCacheMap = null;
	}
	
}
