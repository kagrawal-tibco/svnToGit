package com.tibco.cep.decision.table.constraintpane;

import static com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerConstants.DONT_CARE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Widget;

import com.jidesoft.grid.FilterableTableModel;
import com.jidesoft.grid.TableScrollPane;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.editors.IDecisionTableEditorInput;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;


public class ShowCoverageAction extends Action {

	private static final String CLASS = ShowCoverageAction.class.getName();

	private Map<String, Widget> fComponents;
	
	private DecisionTableEditor fCurrentEditor;
	
	private DecisionTable fCurrentOptimizedTable;
	
	private HashMap<String, String> fRowCacheMap;
	
	private DecisionTableAnalyzerView analyzerView;
	
	/**
	 * @param analyzerView
	 */
	public ShowCoverageAction(DecisionTableAnalyzerView analyzerView) {

		setText(Messages.getString("DecisionTableAnalyzerView.ShowCoverage"));
		setToolTipText(Messages.getString("DecisionTableAnalyzerView.ShowCoverage"));
		setImageDescriptor(DecisionTableUIPlugin.getImageDescriptor("icons/detail.gif"));

		this.analyzerView = analyzerView;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		try {
			this.fComponents = analyzerView.getComponents();
			this.fCurrentEditor = analyzerView.getCurrentEditor();
			this.fCurrentOptimizedTable = analyzerView.getCurrentOptimizedTable();
			
			if(fComponents == null || fCurrentEditor == null || fCurrentOptimizedTable == null) return;
			
			DecisionTableUIPlugin.debug(CLASS, "selecting cells"); 
			highlightCells();
		}
		catch(Exception e) {
			DecisionTableUIPlugin.log(e);
		}
	}

	protected void highlightCells() {
		clearCache();
		HashMap<String, List<String>> cellsToHighlight = new HashMap<String, List<String>>();
		for (String columnName : fComponents.keySet()) {
			Widget comp = fComponents.get(columnName);
			processComponent(columnName, comp, cellsToHighlight);
		}
		int numColumns = fCurrentOptimizedTable.getColumns().size();		
		int numRows = ((IDecisionTableEditorInput) fCurrentEditor.getEditorInput()).getTableEModel().getDecisionTable().getRule().size();
		
		TableScrollPane tableScrollPane = 
			fCurrentEditor.getDecisionTableDesignViewer().getDecisionTablePane().getTableScrollPane();
		TableModel model = tableScrollPane.getMainTable().getModel();
		List<String> rowsToHighlight = new ArrayList<String>();
		for (int i = 1; i <= numRows; i++) {
			List<String> list = cellsToHighlight.get(String.valueOf(i-1));
			if(numColumns == 1){
				for (int j = 0; j < numColumns; j++) {
					Object valueAt = model.getValueAt(i - 1, j + 1);
					if (valueAt instanceof TableRuleVariable) {
						TableRuleVariable trv = (TableRuleVariable)valueAt;
						if (trv.isEnabled() && trv.getExpr().equals(DONT_CARE)) {
							rowsToHighlight.add(String.valueOf(i - 1));
						}
					}
				}
			}
			if (list == null) {
				continue;
			}
			if (list.size() == numColumns) {
				rowsToHighlight.add(String.valueOf(i - 1));
				
				
			} else {
				// iterate over cells *not* included in the list
				// if those cells are blank/empty, then this row
				// should still be highlighted
				boolean shouldHighlight = true;
				for (int j = 0; j < numColumns; j++) {
					// the list contains the column ID, not the column index
					// need to get the TRV, then check whether the column ID
					// exists in the list
					Object valueAt = model.getValueAt(i - 1, j + 1);
					if (valueAt instanceof TableRuleVariable) {
						TableRuleVariable trv = (TableRuleVariable)valueAt;
						if (trv.isEnabled() && list.contains(trv.getColId())) {
							continue;
						}
						if (trv.isEnabled() && trv.getExpr().equals(DONT_CARE)) {
							continue;
						}
						shouldHighlight = false;
						break;
					} 
					//Check for non TRV cell
				}
				
				if (shouldHighlight) {
					rowsToHighlight.add(String.valueOf(i - 1));
				}
			}
			
		}
		DecisionTableUIPlugin.debug(CLASS, "Number of rows to highlight {0} ", rowsToHighlight.size());
		JTable mainTable = tableScrollPane.getMainTable();
		DecisionTableAnalyzerUtils.highlightRows(mainTable, rowsToHighlight.toArray(new String[rowsToHighlight.size()])); 
		
	}

	/**
	 * @param columnName
	 * @param comp
	 * @param cellsToHighlight
	 */
	private void processComponent(String columnName, Widget comp, HashMap<String, List<String>> cellsToHighlight) {
		List<Filter> allFilters = fCurrentOptimizedTable.getAllFilters(columnName);
		if (allFilters.size() == 0) {
			return;
		}
		Filter filter = allFilters.get(0);
		if (comp instanceof MultiSelectComboBox) {
			Object selectedItem = ((MultiSelectComboBox)comp).getSelectedObjects();
			if (selectedItem instanceof Object[]) {
				Object[] arr = (Object[]) selectedItem;
				processEquality(columnName, arr, (EqualsFilter)filter, cellsToHighlight);
			} else if (selectedItem == null) {
				// no filter was selected, need to add all Cells for this column
				Collection<LinkedHashSet<Cell>> values = ((EqualsFilter)filter).getValues();				
				for (Set<Cell> set : values) {
					for (Cell cell : set) {
						addCellEntry(columnName, cellsToHighlight, cell.getId());
					}
				}
			}
		} else if (comp instanceof RangeSlider) {
			int lowValue = ((RangeSlider)comp).getLowerValue();
			int highValue = ((RangeSlider)comp).getUpperValue();
			processRange(columnName, lowValue, highValue, allFilters, cellsToHighlight);
			DecisionTableUIPlugin.debug(CLASS, "Range for {0} [{1}, {2}]", columnName, lowValue, highValue); 
		}
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
			Collection<LinkedHashSet<Cell>> values = ((EqualsFilter)filter).map.values();
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
				DecisionTableUIPlugin.debug(CLASS, "Found Dont Care condition for column {0}", columnName);
				//Return all values in the map as we don't care. It is like no filter case
				Collection<LinkedHashSet<Cell>> allValues = filter.map.values();
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
		
		TableScrollPane tableScrollPane = 
			fCurrentEditor.getDecisionTableDesignViewer().getDecisionTablePane().getTableScrollPane();
		split[0] = getRowNumber((FilterableTableModel) tableScrollPane.getTableModel(), split[0]);
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
	private void processRange(String columnId, int lowValue, int highValue, List<Filter> filters, HashMap<String, List<String>> cellIds) {
		for (Filter filter : filters) {
			if (filter instanceof RangeFilter) {
				processRangeFilter((RangeFilter)filter, columnId, lowValue, highValue, cellIds);
			} else if (filter instanceof EqualsFilter) {
				processEqualsFilter((EqualsFilter)filter, columnId, lowValue, highValue, cellIds);
			}
		}
	}

	private void processEqualsFilter(EqualsFilter filter, String columnId,
			int lowValue, int highValue, HashMap<String, List<String>> cellIds) {
		Collection<LinkedHashSet<Cell>> values = filter.getValues();
		for (Set<Cell> set : values) {
			for (Cell cell : set) {
				try {
					int propertyType = -1;

					String cellBody = cell.getBody();
					EList<Column> columns = fCurrentEditor
							.getDecisionTableModelManager().getTabelEModel()
							.getDecisionTable().getColumns().getColumn();
					for (Column column : columns) {
						if (column.getName().equalsIgnoreCase(columnId)) {
							propertyType = column.getPropertyType();
							break;
						}
					}
					if ( DONT_CARE.equals( cellBody ) ) {
						// Dont care can still be part of range cells.
						addCellEntry(columnId, cellIds, cell.getId());
					} else {
						switch (propertyType) {

						case PROPERTY_TYPES.DOUBLE_VALUE: {
								double value = Double.valueOf(cellBody);
								if (value >= lowValue && value <= highValue) {
									addCellEntry(columnId, cellIds, cell.getId());
								}
								break;
							}
						case PROPERTY_TYPES.INTEGER_VALUE: {
								int value = Integer.valueOf(cellBody);
								if (value >= lowValue && value <= highValue) {
									addCellEntry(columnId, cellIds, cell.getId());
								}
								break;
							}
						case PROPERTY_TYPES.LONG_VALUE: {
								long value = Long.valueOf(cellBody);
								if (value >= lowValue && value <= highValue) {
									addCellEntry(columnId, cellIds, cell.getId());
								}
								break;
							}
						
					/*	case com.tibco.cep.designtime.model.element.PropertyDefinition.PROPERTY_TYPE_BOOLEAN: {
								boolean value = Boolean.valueOf(cellBody);
								addCellEntry(columnId, cellIds, cell.getId());
								break;
							}
						case com.tibco.cep.designtime.model.element.PropertyDefinition.PROPERTY_TYPE_STRING: {
								String value = String.valueOf(cellBody);
								addCellEntry(columnId, cellIds, cell.getId());
								break;
							}*/

						}
					}
				} catch (NumberFormatException e) {
					// TODO : remove this after testing
					DecisionTableUIPlugin.log(e);
				}
			}
		}
	}

	private void processRangeFilter(RangeFilter filter, String columnId,
			int lowValue, int highValue, HashMap<String, List<String>> cellIds) {
		TreeMap<Integer, LinkedHashSet<Cell>> rangeMap = filter.rangeMap;
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
				// check preference to see whether to highlight 'partial' ranges
				boolean highlightPartial = DecisionTableUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.ANALYZER_HIGHLIGHT_PARTIAL_RANGES);
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

	private int getOpValue(Object object) {
    	if (object instanceof Integer) {
    		return (Integer) object;
    	} else if (object instanceof String){
            String op = (String) object;
            if (op.length() > 0 && op.charAt(0) == '"') {
            	op = op.substring(1, op.length() - 1);
            }
    		return Integer.valueOf(op);
    	}
    	return 0;
	}

	private boolean isInRange(int minRange, int maxRange, Cell cell) {
		Object[] operands = cell.expression.operands;
		int opMin = getOpValue(operands[0]);
		int opMax = getOpValue(operands[1]);

		switch (cell.expression.rangeKind) {
            case Operator.RANGE_BOUNDED: {
                if (isInRange(minRange, opMin, opMax, true, true) 
                		&& isInRange(maxRange, opMin, opMax, true, true)) {
                	return true;
                }
                break;
            }
            case Operator.RANGE_MIN_EXCL_BOUNDED:
            {
                if (isInRange(minRange, opMin, opMax, false, true)
                		&& isInRange(maxRange, opMin, opMax, false, true)) {
                	return true;
                }
                break;
            }
            case Operator.RANGE_MAX_EXCL_BOUNDED:
            {
                if (isInRange(minRange, opMin, opMax, true, false)
                		&& isInRange(maxRange, opMin, opMax, true, false)) {
                	return true;
                }
                break;
            }
            case Operator.RANGE_MIN_MAX_EXCL_BOUNDED:
            {
                if (isInRange(minRange, opMin, opMax, false, false)
                		&& isInRange(maxRange, opMin, opMax, false, false)) {
                	return true;
                }
                break;
            }
        }
        return false;
	}

	/**
	 * This handles ranges in which the slider min/max value is not entirely
	 * in the condition cell's range.  For instance, if the condition cell is
	 * > 700 && < 800, and the range slider is from 0 to 750, by default this
	 * will not highlight the cell.  If the preference to highlight partial ranges
	 * is 'true', then we want to highlight these cells, and so this method must
	 * detect those partial ranges.
	 * @param minRange
	 * @param maxRange
	 * @param cell
	 * @return
	 */
	private boolean isPartiallyInRange(int minRange, int maxRange, Cell cell) {
		Object[] operands = cell.expression.operands;
		int opMin = getOpValue(operands[0]);
		int opMax = getOpValue(operands[1]);
		
		switch (cell.expression.rangeKind) {
		case Operator.RANGE_BOUNDED: {
			if (isInRange(opMin, minRange, maxRange, true, true) 
					|| isInRange(opMax, minRange, maxRange, true, true)
					|| isInRange(minRange, opMin, opMax, true, true) 
            		|| isInRange(maxRange, opMin, opMax, true, true)) {
				return true;
			}
			break;
		}
		case Operator.RANGE_MIN_EXCL_BOUNDED:
		{
			if (isInRange(opMin, minRange, maxRange, false, true)
					|| isInRange(opMax, minRange, maxRange, false, true)
					|| isInRange(minRange, opMin, opMax, false, true)
            		|| isInRange(maxRange, opMin, opMax, false, true)) {
				return true;
			}
			break;
		}
		case Operator.RANGE_MAX_EXCL_BOUNDED:
		{
			if (isInRange(opMin, minRange, maxRange, true, false)
					|| isInRange(opMax, minRange, maxRange, true, false)
					|| isInRange(minRange, opMin, opMax, true, false)
            		|| isInRange(maxRange, opMin, opMax, true, false)) {
				return true;
			}
			break;
		}
		case Operator.RANGE_MIN_MAX_EXCL_BOUNDED:
		{
			if (isInRange(opMin, minRange, maxRange, false, false)
					|| isInRange(opMax, minRange, maxRange, false, false)
					|| isInRange(minRange, opMin, opMax, false, false)
            		|| isInRange(maxRange, opMin, opMax, false, false)) {
				return true;
			}
			break;
		}
		}
		return false;
	}
	
	private boolean isInRange(Integer value, int minRange, int maxRange, boolean minInclusive, boolean maxInclusive) {
		if (minInclusive) {
			if (!(value >= minRange)) return false;
		} else {
			if (!(value > minRange)) return false;
		}
		if (maxInclusive) {
			if (!(value <= maxRange)) return false;
		} else {
			if (!(value < maxRange)) return false;
		}
		return true;
	}

	/**
	 * @param model
	 * @param rowId
	 * @return
	 */
	private String getRowNumber(FilterableTableModel model, String rowId) {
		if (fRowCacheMap == null) {
			initRowCache(model);
		}
		String rowValue = fRowCacheMap.get(rowId);
		if (rowValue != null) {
			return rowValue;
		}
		// convert the rowId to the actual row# in the decision table
		int actualRow = -1;
		int rowCount = model.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			Object o = model.getValueAt(i, 0);
			if (rowId.equals(o)) {
				actualRow = i;
				break;
			}
		}
		return String.valueOf(actualRow);
	}

	/**
	 * @param model
	 */
	private void initRowCache(FilterableTableModel model) {
		fRowCacheMap = new HashMap<String, String>();
		// convert the rowId to the actual row# in the decision table
		int rowCount = model.getRowCount();
		for (int actualRow = 0; actualRow < rowCount; actualRow++) {
			Object o = model.getValueAt(actualRow, 0);
			String value = String.valueOf(actualRow);
			//Make the object a string if it isnt
			String string = o.toString();
			fRowCacheMap.put(string, value);
		}

	}
	
	public void clearCache() {
//		fRowCacheMap.clear();
		fRowCacheMap = null;
	}
}
