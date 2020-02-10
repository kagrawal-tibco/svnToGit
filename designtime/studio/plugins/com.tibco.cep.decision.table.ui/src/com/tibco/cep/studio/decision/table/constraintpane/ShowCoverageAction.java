package com.tibco.cep.studio.decision.table.constraintpane;

import static com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerConstants.DONT_CARE;
import static com.tibco.cep.studio.decision.table.constraintpane.Operator.AND_OP;
import static com.tibco.cep.studio.decision.table.constraintpane.Operator.OR_OP;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;

public class ShowCoverageAction extends Action {

	private static final String CLASS = ShowCoverageAction.class.getName();

	private Map<String, Widget> fComponents;

	private IDecisionTableEditor fCurrentEditor;

	private DecisionTable fCurrentOptimizedTable;

	private HashMap<String, String> fRowCacheMap;

	private DecisionTableAnalyzerView analyzerView;

	private List<String> rowsToHighlight;
	
	private HashMap<String, List<Cell>> columnToFilters = new HashMap<String, List<Cell>>();

	/**
	 * @param analyzerView
	 */
	public ShowCoverageAction(DecisionTableAnalyzerView analyzerView) {

		setText(Messages.getString("DecisionTableAnalyzerView.ShowCoverage"));
		setToolTipText(Messages
				.getString("DecisionTableAnalyzerView.ShowCoverage"));
		setImageDescriptor(DecisionTableUIPlugin
				.getImageDescriptor("icons/detail.gif"));

		this.analyzerView = analyzerView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		try {
			this.fComponents = analyzerView.getComponents();
			this.fCurrentEditor = analyzerView.getCurrentEditor();
			this.fCurrentOptimizedTable = analyzerView
					.getCurrentOptimizedTable();

			if (fComponents == null || fCurrentEditor == null
					|| fCurrentOptimizedTable == null)
				return;

			DecisionTableUIPlugin.debug(CLASS, "selecting cells");
			highlightCells();
		} catch (Exception e) {
			DecisionTableUIPlugin.log(e);
		}
	}

	protected void highlightCells() {
		clearCache();
		HashMap<String, List<String>> cellsToHighlight = new HashMap<String, List<String>>();
		columnToFilters.clear();
		for (String columnName : fComponents.keySet()) {
			Widget comp = fComponents.get(columnName);
			processComponent(columnName, comp, cellsToHighlight);
		}
		int numColumns = fCurrentOptimizedTable.getColumns().size();
		int numRows = fCurrentEditor.getTable().getDecisionTable()
				.getRule().size();

		rowsToHighlight = new ArrayList<String>();
		for (int i = 1; i <= numRows; i++) {
			List<String> list = cellsToHighlight.get(String.valueOf(i - 1));
			if (numColumns == 1) {
				for (int j = 0; j < numColumns; j++) {
					// Object valueAt =
					// fCurrentEditor.getDtDesignViewer().getDecisionTable().getDataValueByPosition(j
					// + 1, i - 1);
					Object valueAt = fCurrentEditor.getModelDataByPosition(j,
							i - 1, fCurrentEditor.getDtDesignViewer()
									.getDecisionTable());
					if (valueAt instanceof TableRuleVariable) {
						TableRuleVariable trv = (TableRuleVariable) valueAt;
						if (!trv.isEnabled() || trv.getExpr().trim().isEmpty()
								|| trv.getExpr().equals(DONT_CARE)) { // Asterisk/Empty/Disabled
																		// cells
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
					// Object valueAt =
					// fCurrentEditor.getDtDesignViewer().getDecisionTable().getDataValueByPosition(j
					// + 1, i - 1);
					Object valueAt = fCurrentEditor.getModelDataByPosition(j,
							i - 1, fCurrentEditor.getDtDesignViewer()
									.getDecisionTable());
					if (valueAt instanceof TableRuleVariable) {
						TableRuleVariable trv = (TableRuleVariable) valueAt;
						if (!trv.isEnabled() || trv.getExpr() == null || trv.getExpr().trim().isEmpty()
								|| trv.getExpr().equals(DONT_CARE)) { // Asterisk/Empty/Disabled
																		// cells
							continue;
						}
						if (list.contains(trv.getColId())) {
							continue;
						}
						shouldHighlight = false;
						break;
					}
					// Check for non TRV cell
				}

				if (shouldHighlight) {
					rowsToHighlight.add(String.valueOf(i - 1));
				}
			}

		}
		DecisionTableUIPlugin.debug(CLASS, "Number of rows to highlight {0} ",
				rowsToHighlight.size());
		analyzerView.getCurrentEditor().setRowsToHighlight(rowsToHighlight);
		analyzerView.getCurrentEditor().getDtDesignViewer().getDecisionTable()
				.refresh();
		// analyzerView.getCurrentEditor().getDtDesignViewer().getExceptionTable().refresh();
	}

	/**
	 * @param columnName
	 * @param comp
	 * @param cellsToHighlight
	 */
	private void processComponent(String columnName, Widget comp,
			HashMap<String, List<String>> cellsToHighlight) {
		List<Filter> allFilters = fCurrentOptimizedTable
				.getAllFilters(columnName);
		if (allFilters.size() == 0) {
			return;
		}
		Filter filter = allFilters.get(0);
		if (comp instanceof MultiSelectComboBox) {
			if(((MultiSelectComboBox) comp).gettextLableText().isEmpty() || ((MultiSelectComboBox) comp).gettextLableText() == " "){
				Object[] objects = new Object[0];
				((MultiSelectComboBox) comp).setSelectedObjects(objects);
			}
			Object selectedItem = ((MultiSelectComboBox) comp)
					.getSelectedObjects();
			if (selectedItem instanceof Object[]) {
				Object[] arr = (Object[]) selectedItem;
				processEquality(columnName, arr, (EqualsFilter) filter,
						cellsToHighlight);
				validateHighlights(columnName, cellsToHighlight);
			} else if (selectedItem == null) {
				// no filter was selected, need to add all Cells for this column
				Collection<LinkedHashSet<Cell>> values = ((EqualsFilter) filter)
						.getValues();
				for (Set<Cell> set : values) {
					for (Cell cell : set) {
						addCellEntry(columnName, cellsToHighlight, cell.getId());
					}
				}
			}
		} else if (comp instanceof RangeSlider) {
			Object lowValue = ((RangeSlider) comp).getLowerValue();
			Object highValue = ((RangeSlider) comp).getUpperValue();
			processRange(columnName, lowValue, highValue, allFilters,
					cellsToHighlight);
			DecisionTableUIPlugin.debug(CLASS, "Range for {0} [{1}, {2}]",
					columnName, lowValue, highValue);
		} else if (comp instanceof Text) {
			String text = ((Text) comp).getText();
			Expression expression = new Expression(text);
			try {
				  String[] rangeElements = new String[] {text};
			        boolean isBounded = false;
			        if (text.lastIndexOf(AND_OP) != -1) {
			            rangeElements = text.split(AND_OP);
			        } else if (text.lastIndexOf(OR_OP) != -1) {
			        	String orOp = "\\|\\|";
			            rangeElements = text.split(orOp);
			        }
				expression.parse();
				processRange(columnName, expression.getOperands()[0],
						expression.getOperands()[1], allFilters, cellsToHighlight);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	
	private void validateHighlights(String columnName, HashMap<String, List<String>> cellsToHighlight) {	
		if(!fCurrentOptimizedTable.getAndedCells().isEmpty()) {	
			Iterator<String> a = fCurrentOptimizedTable.getAndedCells().keySet().iterator();
			while(a.hasNext()){
				String cellid = a.next();	
				LinkedList<Cell> values1 = fCurrentOptimizedTable.getAndedCells().get(cellid);
				List<Cell> values2 = columnToFilters.get(columnName);
				boolean p = true;
				for(Cell cell : values1){
					if(values2!= null && !values2.contains(cell)){
						p = false;
						break;
					}
				}
				if(!p){
					String[] split = cellid.split("_");
					List<String> cols = cellsToHighlight.get(String.valueOf(Integer.valueOf(split[0])-1));
					if(cols != null)
					cols.remove(split[1]);	
				}	
			}
			
		}
	}


	/**
	 * @param columnName
	 * @param arr
	 * @param filter
	 * @param cellIds
	 */
	private void processEquality(String columnName, Object[] arr,
			EqualsFilter filter, HashMap<String, List<String>> cellIds) {
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
				DecisionTableUIPlugin.debug(CLASS,
						"Found Dont Care condition for column {0}", columnName);
				// Return all values in the map as we don't care. It is like no
				// filter case
				Collection<LinkedHashSet<Cell>> allValues = filter.getValues();
				list = new ArrayList<Cell>();
				// Create sublists
				for (LinkedHashSet<Cell> value : allValues) {
					List<Cell> tempList = new ArrayList<Cell>(value);
					// Add it to main list
					list.addAll(tempList);
				}

			} else {
				// list = new ArrayList<Cell>(filter.map.get(object));
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
				// break it here. We are not interested in processing other
				// values.
				break;
			}
		}
	}

	/**
	 * @param columnName
	 * @param cellIds
	 * @param id
	 */
	private void addCellEntry(String columnName,
			HashMap<String, List<String>> cellIds, String id) {
		String[] split = id.split("_"); //$NON-NLS-1$
		Table dtTable = fCurrentEditor.getTable();
		split[0] = getRowNumber(dtTable, split[0]);
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
	private void processRange(String columnId, Object lowValue,
			Object highValue, List<Filter> filters,
			HashMap<String, List<String>> cellIds) {
		for (Filter filter : filters) {
			if (filter instanceof RangeFilter) {
				processRangeFilter((RangeFilter) filter, columnId, lowValue,
						highValue, cellIds);
			} else if (filter instanceof EqualsFilter) {
				processEqualsFilter((EqualsFilter) filter, columnId, lowValue,
						highValue, cellIds);
			}
		}
	}

	private void processEqualsFilter(EqualsFilter filter, String columnId,
			Object lowValue, Object highValue,
			HashMap<String, List<String>> cellIds) {
		Collection<LinkedHashSet<Cell>> values = filter.getValues();
		for (Set<Cell> set : values) {
			for (Cell cell : set) {
				try {
					int propertyType = -1;

					String cellBody = cell.getBody();
					EList<Column> columns = fCurrentEditor.getTable()
							.getDecisionTable().getColumns().getColumn();
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
						if (cell.getExpression().getOperatorKind() != Operator.EQUALS_OPERATOR && cell.getExpression().getOperands().length > 0) {
							// the cellBody contains the operator -- we wan't the actual operand here to parse the value
							cellBody = cell.getExpression().getOperands()[0].toString();
						}
						switch (propertyType) {

						case PROPERTY_TYPES.DOUBLE_VALUE: {
							double value = Double.valueOf(cellBody);
							if (lowValue instanceof Double
									&& highValue instanceof Double) {
								if (value >= (double) lowValue
										&& value <= (double) highValue) {
									addCellEntry(columnId, cellIds,
											cell.getId());
								}
							}else{
								if (value >= Double.valueOf(lowValue.toString())
										&& value <= Double.valueOf(highValue.toString())) {
									addCellEntry(columnId, cellIds,
											cell.getId());
								}
							}
							break;
						}
						case PROPERTY_TYPES.INTEGER_VALUE: {
							Long value = Long.valueOf(cellBody);
							if (lowValue instanceof Long
									&& highValue instanceof Long) {
								if (value >= (long)lowValue
										&& value <= (long)highValue) {
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
							DateFormat format = fCurrentEditor.getDecisionTableDesignViewer().getDateFormat();
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

							if (null == minDate && null == maxDate) {
								// no date specified in Analyzer, treat as "don't care"
								addCellEntry(columnId, cellIds,
										cell.getId());
							}
							if (maxDate == null) {
								if (cell.getExpression().getOperatorKind() == Operator.EQUALS_OPERATOR) {
									if (value.compareTo((Date) minDate) == 0) {
										addCellEntry(columnId, cellIds,
												cell.getId());
									}
								} else if (value.compareTo((Date) minDate) != 0) {
									addCellEntry(columnId, cellIds,
											cell.getId());
								}
							} else if (null != minDate && null != maxDate) {
								if (value.compareTo((Date) minDate) >= 0
										&& value.compareTo((Date) maxDate) <= 0) {
									addCellEntry(columnId, cellIds,
											cell.getId());
								}
							}

							break;
						}

						/*
						 * case
						 * com.tibco.cep.designtime.model.element.PropertyDefinition
						 * .PROPERTY_TYPE_BOOLEAN: { boolean value =
						 * Boolean.valueOf(cellBody); addCellEntry(columnId,
						 * cellIds, cell.getId()); break; } case
						 * com.tibco.cep.designtime
						 * .model.element.PropertyDefinition
						 * .PROPERTY_TYPE_STRING: { String value =
						 * String.valueOf(cellBody); addCellEntry(columnId,
						 * cellIds, cell.getId()); break; }
						 */

						}
					}
				} catch (NumberFormatException e) {
					// TODO : remove this after testing
					DecisionTableUIPlugin.log(e);
				} catch (ParseException e) {
					DecisionTableUIPlugin.log(e);
				}
			}
		}
	}

	private void processRangeFilter(RangeFilter filter, String columnId,
			Object lowValue, Object highValue,
			HashMap<String, List<String>> cellIds) {
		TreeMap<Object, LinkedHashSet<Cell>> rangeMap = filter.getRangeMap();
		// SortedMap<Integer, List<Cell>> subMap =
		// rangeMap.subMap(Integer.valueOf(lowValue-1),
		// Integer.valueOf(highValue+1));
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
				boolean highlightPartial = DecisionTableUIPlugin
						.getDefault()
						.getPreferenceStore()
						.getBoolean(
								PreferenceConstants.ANALYZER_HIGHLIGHT_PARTIAL_RANGES);
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
		} else if (object instanceof String) {
			String op = (String) object;
			if (op.length() > 0 && op.charAt(0) == '"') {
				op = op.substring(1, op.length() - 1);
			}
			if (op.contains("L")) {
				op = op.replaceAll("L", "").trim();
			}
			if (op.contains("l")) {
				op = op.replaceAll("l,", "").trim();
			}
			return Long.valueOf(op);
		} else if (object instanceof Date) {
			return (Date) object;
		}
		if (object instanceof Long) {
			return (Long) object;
		}
		if(object instanceof Double){
			return (Double) object;
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
		boolean minInclusive = true;
		boolean maxInclusive = true;
		
		switch (cell.getExpression().getRangeKind()) {
		case Operator.RANGE_BOUNDED: {
			minInclusive = true;
			maxInclusive = true;
			break;
		}
		case Operator.RANGE_MIN_EXCL_BOUNDED: {
			minInclusive = false;
			maxInclusive = true;
			break;
		}
		case Operator.RANGE_MAX_EXCL_BOUNDED: {
			minInclusive = true;
			maxInclusive = false;
			break;
		}
		case Operator.RANGE_MIN_MAX_EXCL_BOUNDED: {
			minInclusive = false;
			maxInclusive = false;
			break;
		}
		}
		if (lowValue == null && highValue == null) {
			// treat as 'don't care'
			return true;
		}
		boolean inLowRange = isInRange(lowValue, opMin, opMax, minInclusive, maxInclusive);
		boolean inHighRange = isInRange(highValue, opMin, opMax, minInclusive, maxInclusive);
		if (lowValue == null) {
			return inHighRange;
		}
		if (highValue == null) {
			return inLowRange;
		}
		return inLowRange && inHighRange;
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

		if (lowValue != null && lowValue.equals(opMin)
				&& highValue != null && highValue.equals(opMax)) {
			return true;
		}
		
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
		if (lowValue2 instanceof String && lowValue instanceof Date
				&& highValue instanceof Date) {
			Date dateVal = null;
			try {
				dateVal = fCurrentEditor.getDecisionTableDesignViewer().getDateFormat().parse((String) lowValue2);
			} catch (ParseException e) {
				return false; // return false if the parse fails
			}
			if (minInclusive) {

				if (!(dateVal.compareTo((Date) lowValue) >= 0))
					return false;
			} else {
				if (!(dateVal.compareTo((Date) lowValue) > 0))
					return false;
			}

			if (maxInclusive) {
				if (!(dateVal.compareTo((Date) highValue) <= 0))
					return false;
			} else {
				if (!(dateVal.compareTo((Date) highValue) < 0))
					return false;
			}
		}
		
		if (lowValue2 instanceof Integer && lowValue instanceof Integer
				&& highValue instanceof Integer) {
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

		if (lowValue2 instanceof Long && lowValue instanceof Long
				&& highValue instanceof Long) {
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

		if (lowValue2 instanceof Date && lowValue instanceof Date
				&& highValue instanceof Date) {
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
		
		if (lowValue2 instanceof Double && lowValue instanceof Double
				&& highValue instanceof Double) {
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

		return true;

	}

	/**
	 * @param model
	 * @param rowId
	 * @return
	 */
	private String getRowNumber(Table table, String rowId) {
		if (fRowCacheMap == null) {
			initRowCache(table);
		}
		String rowValue = fRowCacheMap.get(rowId);
		if (rowValue != null) {
			return rowValue;
		}
		int rowIndx = -1;
		EList<TableRule> rules = table.getDecisionTable().getRule();
		for (int i = 0; i < rules.size(); i++) {
			TableRule rule = rules.get(i);
			if (rule.getId().equals(rowId)) {
				rowIndx = i;
				break;
			}
		}	
		return String.valueOf(rowIndx);
	}

	/**
	 * @param model
	 */
	private void initRowCache(Table table) {
		fRowCacheMap = new HashMap<String, String>();
		EList<TableRule> rules = table.getDecisionTable().getRule();
		for (int i = 0; i < rules.size(); i++) {
			TableRule rule = rules.get(i);
			fRowCacheMap.put(rule.getId(), String.valueOf(i));
		}
	}

	public void clearCache() {
		// fRowCacheMap.clear();
		fRowCacheMap = null;
	}

	public List<String> getRowsToHighLight() {
		return rowsToHighlight;
	}
}
