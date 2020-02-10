package com.tibco.be.ws.decisiontable.constraint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;

import static com.tibco.be.ws.decisiontable.constraint.DecisionTableAnalyzerConstants.DONT_CARE;

public class DecisionTableCoverageAction extends AbstractTableAnalyzerAction {

	private List<String> coveredRules = null;
	private ColumnFilter[] columnFilters = null;
	private HashMap<String, List<Cell>> columnToFilters = new HashMap<String, List<Cell>>();
	
	/**
	 * @param fCurrentEditor
	 * @param fCurrentOptimizedTable
	 * @param fComponents
	 * @param fRangeColumnValues
	 */
	public DecisionTableCoverageAction(Table table, DecisionTable fCurrentOptimizedTable, ColumnFilter[] columnFilters) {
		super(table, fCurrentOptimizedTable);
		coveredRules = new ArrayList<String>();
		this.columnFilters = columnFilters;
	}

	@Override
	public void run() {
		highlightCells();
	}
		
	protected void highlightCells() {
		HashMap<String, List<String>> cellsToHighlight = new HashMap<String, List<String>>();
		columnToFilters.clear();
		for (ColumnFilter filter : columnFilters) {
			processComponent(filter, cellsToHighlight);
		}
		int numColumns = fCurrentOptimizedTable.getColumns().size();		
	
		for (TableRule tableRule : table.getDecisionTable().getRule()) {

			List<String> list = cellsToHighlight.get(tableRule.getId());
			if(numColumns == 1) {
				TableRuleVariable trv = null;
				if (tableRule.getCondition().size() == 1) {
					trv = tableRule.getCondition().get(0);
				}
				
				if (trv == null || !trv.isEnabled() || trv.getExpr().trim().isEmpty() || trv.getExpr().equals(DONT_CARE)) { //Asterisk/Empty/Disabled cells
					coveredRules.add(tableRule.getId());
				}
			}
			if (list == null) {
				continue;
			}
			if (list.size() == numColumns) {
				coveredRules.add(tableRule.getId());
				
				
			} else {
				// iterate over cells *not* included in the list
				// if those cells are blank/empty, then this row
				// should still be highlighted
				boolean shouldHighlight = true;
				for (int j = 0; j < numColumns; j++) {
					TableRuleVariable trv = null;
					if (j < tableRule.getCondition().size()) {
						trv = tableRule.getCondition().get(j);
					}		
					if (trv == null || !trv.isEnabled() || trv.getExpr().trim().isEmpty() || trv.getExpr().equals(DONT_CARE)) { //Asterisk/Empty/Disabled cells
						continue;
					}
					if (list.contains(trv.getColId())) {
						continue;
					}
					shouldHighlight = false;
					break;
				}
				
				if (shouldHighlight) {
					coveredRules.add(tableRule.getId());
				}
			}			
		}		
	}
	
	public List<String> getCoveredTableRules() {
		return Collections.unmodifiableList(coveredRules);
	}
	
	/**
	 * @param columnName
	 * @param comp
	 * @param cellsToHighlight
	 */
	private void processComponent(ColumnFilter columnFilter, HashMap<String, List<String>> cellsToHighlight) {
		List<Filter> allFilters = fCurrentOptimizedTable.getAllFilters(columnFilter.getColumnName());
		if (allFilters.size() == 0) {
			return;
		}
		Filter filter = allFilters.get(0);
		if (!columnFilter.isRangeFilter()) {
			Object[] objectArray = columnFilter.getItems();
			if (objectArray != null && objectArray.length > 0) {
				processEquality(columnFilter.getColumnName(), objectArray, (EqualsFilter)filter, cellsToHighlight);
				validateHighlights(columnFilter.getColumnName(), cellsToHighlight);
			} else {
				// no filter was selected, need to add all Cells for this column
				Collection<LinkedHashSet<Cell>> values = ((EqualsFilter)filter).getValues();				
				for (Set<Cell> set : values) {
					for (Cell cell : set) {
						addCellEntry(columnFilter.getColumnName(), cellsToHighlight, cell.getId());
					}
				}
			}
		} else {			
			Object lowValue = columnFilter.getRange()[0];
			Object highValue = columnFilter.getRange()[1];
			processRange(columnFilter.getColumnName(), lowValue, highValue, allFilters, cellsToHighlight); 
		}
	}
	
	private void validateHighlights(String columnName, HashMap<String, List<String>> cellsToHighlight) {	
		if(!fCurrentOptimizedTable.getAndedCells().isEmpty()) {	
			Iterator<String> a = fCurrentOptimizedTable.getAndedCells().keySet().iterator();
			while(a.hasNext()){
				String cellid = a.next();	
				LinkedList<Cell> values1 = fCurrentOptimizedTable.getAndedCells().get(cellid);
				List<Cell> values2 = columnToFilters.get(columnName);
				boolean cellidCheck = checkValidCellId(values2, cellid); 
				boolean p = true;
				for(Cell cell : values1){
					if(values2!= null && cellidCheck && !values2.contains(cell)){
						p = false;
						break;
					}
				}
				if(!p){
					String[] split = cellid.split("_");
					List<String> cols = cellsToHighlight.get(String.valueOf(Integer.valueOf(split[0])));
					if(cols != null)
					cols.remove(split[1]);	
				}	
			}
			
		}
	}
	

	private boolean checkValidCellId(List<Cell> filterValues, String cellid) {
		for(Cell cell : filterValues) {
			if(cell.id == cellid){
				return true;
			}
		}	
		return false;
	}

	/**
	 * @param columnName
	 * @param arr
	 * @param filter
	 * @param cellIds
	 */
	private void processEquality(String columnName, Object[] arr, EqualsFilter filter, HashMap<String, List<String>> cellIds) {
		if (arr.length == 0) {
			// no filter was selected, need to add all Cells for this column
			Collection<LinkedHashSet<Cell>> values = filter.getValues();
			for (Set<Cell> set : values) {
				for (Cell cell : set) {
					addCellEntry(columnName, cellIds, cell.getId());
				}
			}
			return;
		}
		for (Object object : arr) {
			List<Cell> list = null;
			if (DONT_CARE.equals(object)) {
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
//				list = new ArrayList<Cell>(filter.map.get(object));
				list = new ArrayList<Cell>(filter.getFilteredValues(object));
				if(columnToFilters.containsKey(columnName)){
					columnToFilters.get(columnName).addAll(list);
				}else{
					columnToFilters.put(columnName, list);
				}
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
			if (DONT_CARE.equals(object)) {
				// break it here. We are not interested in processing other values.
				break;
			}
		}
	}
	
	/**
	 * @param columnName
	 * @param cellIds
	 * @param id
	 */
	private void addCellEntry(String columnName, HashMap<String, List<String>> cellIds, String id) {
		String[] split = id.split("_"); //$NON-NLS-1$
//		split[0] = getRecordNumber(split[0]);
		addEntry(cellIds, split);
	}
	
	/**
	 * @param cellIds
	 * @param split
	 */
	private void addEntry(HashMap<String, List<String>> cellIds, String[] split) {
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
	 * @param columnId
	 * @param lowValue
	 * @param highValue
	 * @param filters
	 * @param cellIds
	 */
	private void processRange(String columnId, Object lowValue, Object highValue, List<Filter> filters, HashMap<String, List<String>> cellIds) {
		for (Filter filter : filters) {
			if (filter instanceof RangeFilter) {
				processRangeFilter((RangeFilter)filter, columnId, lowValue, highValue, cellIds);
			} else if (filter instanceof EqualsFilter) {
				processEqualsFilter((EqualsFilter)filter, columnId, lowValue, highValue, cellIds);
			}
		}
	}

	private void processEqualsFilter(EqualsFilter filter, String columnId, Object lowValue, Object highValue, 
			HashMap<String, List<String>> cellIds) {
		Collection<LinkedHashSet<Cell>> values = filter.getValues();
		for (Set<Cell> set : values) {
			for (Cell cell : set) {
				try {
					int propertyType = -1;
					String cellBody = cell.getBody();
					EList<Column> columns = table.getDecisionTable()
							.getColumns().getColumn();
					for (Column column : columns) {
						if (column.getName().equalsIgnoreCase(columnId)) {
							propertyType = column.getPropertyType();
							break;
						}
					}
					if (DONT_CARE.equals(cellBody)) {
						// Dont care can still be part of range cells.
						addCellEntry(columnId, cellIds, cell.getId());
					} else {
						switch (propertyType) {
						case PROPERTY_TYPES.DOUBLE_VALUE: {
							double value = Double.valueOf(cellBody);
							lowValue = Double.parseDouble(lowValue.toString());
							highValue = Double.parseDouble(highValue.toString());
							if (lowValue instanceof Double
									&& highValue instanceof Double) {
								if (value >= (double) lowValue
										&& value <= (double) highValue) {
									addCellEntry(columnId, cellIds,
											cell.getId());
								}
							}
							break;
						}
						case PROPERTY_TYPES.INTEGER_VALUE: {
							int value = Integer.valueOf(cellBody);
							
							lowValue = Integer.parseInt(lowValue.toString());
							highValue = Integer.parseInt(highValue.toString());
							
							if (lowValue instanceof Integer
									&& highValue instanceof Integer) {
								if (value >= (int) lowValue
										&& value <= (int) highValue) {
									addCellEntry(columnId, cellIds,
											cell.getId());
								}
							}
							break;
						}
						case PROPERTY_TYPES.LONG_VALUE: {
							if (cellBody.contains("L")) {
								cellBody = cellBody.replaceAll("L", "").trim();
							}
							if (cellBody.contains("l")) {
								cellBody = cellBody.replaceAll("l,", "").trim();
							}
							long value = Long.valueOf(cellBody);
							
							lowValue =  Long.parseLong(lowValue.toString());
							highValue = Long.parseLong(highValue.toString());
							
							if (lowValue instanceof Long
									&& highValue instanceof Long) {
								if (value >= (long) lowValue
										&& value <= (long) highValue) {
									addCellEntry(columnId, cellIds,
											cell.getId());
								}
							}
							break;
						}
						case PROPERTY_TYPES.DATE_TIME_VALUE: {
							DateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd'T'HH:mm:ss");
							Date value = format.parse(cellBody);

							Date minDate = null;
							if (lowValue instanceof String) {
								minDate = format.parse((String) lowValue);
							} else if (lowValue instanceof Date) {
								minDate = (Date) lowValue;
							}
							Date maxDate = null;
							if (highValue instanceof String) {
								maxDate = format.parse((String) highValue);
							} else if (highValue instanceof Date) {
								maxDate = (Date) highValue;
							}

							if (null != minDate && null != maxDate) {
								if (value.compareTo((Date) minDate) >= 0
										&& value.compareTo((Date) maxDate) <= 0) {
									addCellEntry(columnId, cellIds,
											cell.getId());
								}
							}

							break;
						}
						}
					}
				} catch (NumberFormatException e) {
					// TODO : remove this after testing
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					
				}
			}
		}
	}

	private void processRangeFilter(RangeFilter filter, String columnId,
			Object lowValue, Object highValue, HashMap<String, List<String>> cellIds) {
		TreeMap<Object, LinkedHashSet<Cell>> rangeMap = filter.getRangeMap();
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
				// check preference to see whether to highlight 'partial' ranges
				boolean highlightPartial =  false/*DecisionTableUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.ANALYZER_HIGHLIGHT_PARTIAL_RANGES)*/;
				boolean inRange;
				if (highlightPartial) {
					inRange = isPartiallyInRange(lowValue, highValue, cell);
				} else {
					inRange = isInRange(lowValue, highValue, cell);
				}
				if (inRange) {
					addCellEntry(columnId, cellIds, id);
				}
			}
		}
	}

	private Object getOpValue(Object object) {
    	if (object instanceof Integer) {
    		return (Integer) object;
    	}if (object instanceof Long) {
    		return (Long) object;
    	}if (object instanceof Double) {
    		return (Double) object;
    	}else if (object instanceof Date) {
			return (Date) object;
		}else if (object instanceof String){
            String op = (String) object;
            if (op.length() > 0 && op.charAt(0) == '"') {
            	op = op.substring(1, op.length() - 1);
            }
    		return Integer.valueOf(op);
    	}
    	return 0;
	}

	private boolean isInRange(Object lowValue, Object highValue, Cell cell) {
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
		
		if(cell.getCellInfo().getType() == PROPERTY_TYPES.DOUBLE_VALUE){
			
			lowValue = String.valueOf(Double.parseDouble(lowValue.toString()));
			highValue = String.valueOf(Double.parseDouble(highValue.toString()));
		}

		switch (cell.getExpression().getRangeKind()) {
		case Operator.RANGE_BOUNDED: {
			if (isInRange(lowValue, opMin, opMax, true, true)
					&& isInRange(highValue, opMin, opMax, true, true)) {
				return true;
			}
			break;
		}
		case Operator.RANGE_MIN_EXCL_BOUNDED: {
			if (isInRange(lowValue, opMin, opMax, false, true)
					&& isInRange(highValue, opMin, opMax, false, true)) {
				return true;
			}
			break;
		}
		case Operator.RANGE_MAX_EXCL_BOUNDED: {
			if (isInRange(lowValue, opMin, opMax, true, false)
					&& isInRange(highValue, opMin, opMax, true, false)) {
				return true;
			}
			break;
		}
		case Operator.RANGE_MIN_MAX_EXCL_BOUNDED: {
			if (isInRange(lowValue, opMin, opMax, false, false)
					&& isInRange(highValue, opMin, opMax, false, false)) {
				return true;
			}
			break;
		}
		}
		return false;
	}

	/**
	 * This handles ranges in which the slider min/max value is not entirely in
	 * the condition cell's range. For instance, if the condition cell is > 700
	 * && < 800, and the range slider is from 0 to 750, by default this will not
	 * highlight the cell. If the preference to highlight partial ranges is
	 * 'true', then we want to highlight these cells, and so this method must
	 * detect those partial ranges.
	 * 
	 * @param lowValue
	 * @param highValue
	 * @param cell
	 * @return
	 */
	private boolean isPartiallyInRange(Object lowValue, Object highValue,
			Cell cell) {
		Object[] operands = cell.getExpression().getOperands();
		Object opMin = getOpValue(operands[0]);
		Object opMax = getOpValue(operands[1]);

		switch (cell.getExpression().getRangeKind()) {
		case Operator.RANGE_BOUNDED: {
			if (isInRange(opMin, lowValue, highValue, true, true)
					|| isInRange(opMax, lowValue, highValue, true, true)
					|| isInRange(lowValue, opMin, opMax, true, true)
					|| isInRange(highValue, opMin, opMax, true, true)) {
				return true;
			}
			break;
		}
		case Operator.RANGE_MIN_EXCL_BOUNDED: {
			if (isInRange(opMin, lowValue, highValue, false, true)
					|| isInRange(opMax, lowValue, highValue, false, true)
					|| isInRange(lowValue, opMin, opMax, false, true)
					|| isInRange(highValue, opMin, opMax, false, true)) {
				return true;
			}
			break;
		}
		case Operator.RANGE_MAX_EXCL_BOUNDED: {
			if (isInRange(opMin, lowValue, highValue, true, false)
					|| isInRange(opMax, lowValue, highValue, true, false)
					|| isInRange(lowValue, opMin, opMax, true, false)
					|| isInRange(highValue, opMin, opMax, true, false)) {
				return true;
			}
			break;
		}
		case Operator.RANGE_MIN_MAX_EXCL_BOUNDED: {
			if (isInRange(opMin, lowValue, highValue, false, false)
					|| isInRange(opMax, lowValue, highValue, false, false)
					|| isInRange(lowValue, opMin, opMax, false, false)
					|| isInRange(highValue, opMin, opMax, false, false)) {
				return true;
			}
			break;
		}
		}
		return false;
	}

	private boolean isInRange(Object lowValue2, Object lowValue,
			Object highValue, boolean minInclusive, boolean maxInclusive) {
		if (lowValue instanceof Integer
				&& highValue instanceof Integer) {
			lowValue2 = Integer.parseInt(lowValue2.toString());
			if (minInclusive) {

				if (!((int) lowValue2 >= (int) lowValue))
					return false;
			} else {
				if (!((int) lowValue2 > (int) lowValue))
					return false;
			}

			if (maxInclusive) {
				if (!((int) lowValue2 <= (int) highValue))
					return false;
			} else {
				if (!((int) lowValue2 < (int) highValue))
					return false;
			}
		}

		if (lowValue instanceof Long
				&& highValue instanceof Long) {
			lowValue2 = Long.parseLong(lowValue2.toString());
			if (minInclusive) {

				if (!((long) lowValue2 >= (long) lowValue))
					return false;
			} else {
				if (!((long) lowValue2 > (long) lowValue))
					return false;
			}

			if (maxInclusive) {
				if (!((long) lowValue2 <= (long) highValue))
					return false;
			} else {
				if (!((long) lowValue2 < (long) highValue))
					return false;
			}
		}
		
		if (lowValue instanceof Double
				&& highValue instanceof Double) {
			lowValue2 = Double.parseDouble(lowValue2.toString());
			if (minInclusive) {

				if (!((double) lowValue2 >= (double) lowValue))
					return false;
			} else {
				if (!((double) lowValue2 > (double) lowValue))
					return false;
			}

			if (maxInclusive) {
				if (!((double) lowValue2 <= (double) highValue))
					return false;
			} else {
				if (!((double) lowValue2 < (double) highValue))
					return false;
			}
		}

		if (lowValue instanceof Date
				&& highValue instanceof Date) {
			
			String lowString = lowValue2.toString();
			String[] dateArray =  lowString.split(" ");
			String finalString = dateArray[0]+ " " + dateArray[1] + " "+ dateArray[2]+ " "+ dateArray[3]+" "+ dateArray[5];
			lowValue2 = finalString;
			DateFormat format = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy");
			 
			try {
				
				lowValue2 = format.parse(lowValue2.toString().trim());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (minInclusive) {

				if (!(((Date) lowValue2).compareTo((Date) lowValue) >= 0))
					return false;
			} else {
				if (!(((Date) lowValue2).compareTo((Date) lowValue) > 0))
					return false;
			}

			if (maxInclusive) {
				if (!(((Date) lowValue2).compareTo((Date) highValue) <= 0))
					return false;
			} else {
				if (!(((Date) lowValue2).compareTo((Date) highValue) < 0))
					return false;
			}
		}

		return true;

	}
	

}