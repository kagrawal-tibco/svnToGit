package com.tibco.be.ws.decisiontable.constraint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.studio.util.logger.problems.ConflictingActionsProblemEvent;
import com.tibco.cep.studio.util.logger.problems.DuplicateRuleProblemEvent;
import com.tibco.cep.studio.util.logger.problems.MissingEqualsCriterionProblemEvent;
import com.tibco.cep.studio.util.logger.problems.OverlappingRangeProblemEvent;
import com.tibco.cep.studio.util.logger.problems.ProblemEvent;
import com.tibco.cep.studio.util.logger.problems.RANGE_TYPE_PROBLEM;
import com.tibco.cep.studio.util.logger.problems.RangeProblemEvent;
import com.tibco.cep.studio.util.logger.problems.RuleCombinationProblemEvent;
import com.tibco.cep.studio.util.logger.problems.UncoveredDomainEntryProblemEvent;

/**
 * Table Analyzer Action 
 * @author vdhumal
 */
public class DecisionTableAnalyzeAction extends AbstractTableAnalyzerAction {

	public List<ProblemEvent> fProblemEvents = new ArrayList<ProblemEvent>();
	private String decisionTable;
	private Object[] subsetRuleIds;

	public static final int SEVERITY_ERROR = 2;
	public static final int SEVERITY_WARNING = 1;
	public static final int SEVERITY_INFO = 0;

	/**
	 * @param fCurrentEditor
	 * @param fCurrentOptimizedTable
	 * @param fComponents
	 * @param fRangeColumnValues
	 */
	public DecisionTableAnalyzeAction(Table table, DecisionTable fCurrentOptimizedTable, Object[] subsetRuleIds) {
		super(table, fCurrentOptimizedTable);
		decisionTable = table.getFolder() + table.getName();
		this.subsetRuleIds = subsetRuleIds;
	}

	@Override
	public void run() {
		analyze();
	}

	public void analyze() {
		if (fCurrentOptimizedTable == null) {
			return;
		}
		Map<String, List<Cell>> allActions = new HashMap<String, List<Cell>>();
		Map<String, List<Cell>> allRuleCells = new HashMap<String, List<Cell>>();

		collectAllActions(fCurrentOptimizedTable, allActions, allRuleCells);
		analyzeTableCompleteness(fCurrentOptimizedTable, subsetRuleIds);
		analyzeRulesForConflictingActions(fCurrentOptimizedTable, allActions, allRuleCells, subsetRuleIds);
		analyzeRulesToBeCombined(fCurrentOptimizedTable, allActions, allRuleCells, subsetRuleIds);
	}

	/**
	 * Collect all conditions and actions with maps having keys as rule id
	 * 
	 * @param table
	 * @param monitor
	 * @param allActions
	 *            -> All actions
	 * @param allRuleCells
	 *            -> All conditions
	 */
	private void collectAllActions(DecisionTable table, Map<String, List<Cell>> allActions, Map<String, List<Cell>> allRuleCells) {
		Set<String> columns = table.getColumns();
		for (String column : columns) {
			List<Filter> allFilters = table.getAllFilters(column);
			for (Filter filter : allFilters) {
				collectActions(table, column, filter, allActions, allRuleCells);
			}
		}
	}

	/**
	 * Find out rules which have identical set of conditions but different set
	 * of actions for them.
	 * 
	 * @param table
	 * @param monitor
	 * @param allActions
	 * @param allRuleCells
	 */
	private void analyzeRulesForConflictingActions(DecisionTable table, Map<String, List<Cell>> allActions, 
													Map<String, List<Cell>> allRuleCells, Object[] subsetRuleIds) {
		if (table == null) {
			return;
		}

		// Get its keyset
		Set<String> ruleIDs = allRuleCells.keySet();
		int size = ruleIDs.size();
		String[] ids = ruleIDs.toArray(new String[size]);
		List<Object> subsetRuleIdList = Arrays.asList(subsetRuleIds);
		
		for (int outerLoop = 0; outerLoop < size; outerLoop++) {
			String lhsId = ids[outerLoop];
			if (!subsetRuleIdList.isEmpty() && !subsetRuleIdList.contains(lhsId)) {
				continue;
			}			
			List<Cell> lhs = allRuleCells.get(lhsId);
			for (int innerLoop = outerLoop + 1; innerLoop < size; innerLoop++) {
				String rhsId = ids[innerLoop];
				List<Cell> rhs = allRuleCells.get(rhsId);
				boolean areEqual = areListsEqual(lhs, rhs);
				// Compare and report conflicting actions if conditions are
				// equal
				if (areEqual) {
					boolean equalActions = compareActions(lhsId, rhsId,
							allActions);
					// If action set is different flag it
					if (!equalActions) {
						// String message =
						// Messages.getString("DecisionTable.analyze.conflictingActions.message",
						// lhsId, rhsId);
						reportConflictingActionsProblem(decisionTable, "Conflicting Actions", Integer.parseInt(rhsId),
																		Integer.parseInt(lhsId), SEVERITY_WARNING);
					}
				}
			}
		}
	}

	/**
	 * Compare actions of row ids specified by lhsId and rhsId
	 * 
	 * @param lhsId
	 * @param rhsId
	 * @param allActions
	 * @return
	 */
	private boolean compareActions(String lhsId, String rhsId,
			Map<String, List<Cell>> allActions) {
		// Get actions matching both
		List<Cell> lhsActions = allActions.get(lhsId);
		List<Cell> rhsActions = allActions.get(rhsId);
		return areListsEqual(lhsActions, rhsActions);
	}

	private void analyzeRulesToBeCombined(DecisionTable table, Map<String, List<Cell>> allActions, 
																Map<String, List<Cell>> allRuleCells, Object[] subsetRuleIds) {
		if (table == null) {
			return;
		}

		// allActions contains all actions, keyed by the rule ID
		Set<String> ruleIDs = allActions.keySet();
		int size = ruleIDs.size();
		String[] ids = ruleIDs.toArray(new String[size]);
		List<Object> subsetRuleIdList = Arrays.asList(subsetRuleIds);
		// Set<RuleTupleInfo> ruleTuples = table.ruleTuples;
		for (int i = 0; i < size; i++) {
			String id = ids[i];
			List<Cell> list = allActions.get(id);
			List<String> equalRules = getEqualRules(ids, allActions, list, i + 1);
			// Get duplicate rules
			for (String id2 : equalRules) {
				// String message =
				// Messages.getString("DecisionTable.analyze.equalRules.message",
				// id, id2);
				if (!subsetRuleIdList.isEmpty() && !subsetRuleIdList.contains(id2)) {
					continue;
				}				
				List<Cell> cellList1 = allRuleCells.get(id);
				List<Cell> cellList2 = allRuleCells.get(id2);
				// compare these two lists!
				analyzeLists(cellList1, cellList2);
				reportRuleCombinationProblem(decisionTable, "Rule Combination", id2, id.toString(), SEVERITY_WARNING);
			}
		}
	}

	private void analyzeLists(List<Cell> list, List<Cell> targetList) {
		int size = list.size();
		int targetSize = targetList.size();
		HashMap<Cell, Cell> rangeCells = new HashMap<Cell, Cell>();
		boolean useTargetList = targetSize > size;
		List<Cell> largerList = useTargetList ? targetList : list;
		for (int i = 0; i < largerList.size(); i++) {
			Cell cell = largerList.get(i);
			Cell otherCell = null;
			if (useTargetList) {
				otherCell = getCell(cell.getColumnId(), list);
			} else {
				otherCell = getCell(cell.getColumnId(), targetList);
			}
			if (otherCell == null) {
				// could not find the equivalent cell, must be a blank cell
				continue;
			}
			if (cell.getExpression().getOperatorKind() == Operator.RANGE_OPERATOR
					&& otherCell.getExpression().getOperatorKind()  == Operator.RANGE_OPERATOR) {
				rangeCells.put(cell, otherCell);
			} else if (!cell.getExpression().getExpr().equals(otherCell.getExpression().getExpr())) {
				// if even one (non-range) cell is not equal, we cannot combine
				// this rule
				// return false;
			}
		}
		if (rangeCells.keySet().size() > 0) {
			// found range cells that could potentially be combined
			analyzeRangeCells(rangeCells);
			// return true;
		}
		// return true;
	}

	private void analyzeRangeCells(HashMap<Cell, Cell> rangeCells) {
		Set<Cell> keySet = rangeCells.keySet();
		for (Cell cell : keySet) {
			Cell cell2 = rangeCells.get(cell);
			combineCells(cell, cell2);
		}
	}

	private void combineCells(Cell cell, Cell cell2) {
		Object[] operands = cell.getExpression().getOperands();
		Object[] operands2 = cell2.getExpression().getOperands();
		Object op1min = getOpValue(operands[0]);
		Object op1max = getOpValue(operands[1]);
		Object op2min = getOpValue(operands2[0]);
		Object op2max = getOpValue(operands2[1]);
		if (op1min instanceof Integer && op2max instanceof Integer
				&& (int) op1min == Integer.MIN_VALUE
				&& (int) op2max == Integer.MAX_VALUE) {
			// this cell can be combined with an OR (for instance, < 30 || > 50)
			reportCellCombination(cell, cell2);
		} else if (op1min instanceof Long && op2max instanceof Long
				&& (long) op1min == Long.MIN_VALUE
				&& (long) op2max == Long.MAX_VALUE) {
			// this cell can be combined with an OR (for instance, < 30 || > 50)
			reportCellCombination(cell, cell2);
		}
		if (op1max instanceof Integer && op2min instanceof Integer
				&& (int) op1max == Integer.MIN_VALUE
				&& (int) op2min == Integer.MAX_VALUE) {
			// this cell can be combined with an OR (for instance, < 30 || > 50)
			reportCellCombination(cell, cell2);
		} else if (op1max instanceof Long && op2min instanceof Long
				&& (long) op1max == Long.MIN_VALUE
				&& (long) op2min == Long.MAX_VALUE) {
			// this cell can be combined with an OR (for instance, < 30 || > 50)
			reportCellCombination(cell, cell2);
		}
	}

	private void reportCellCombination(Cell cell, Cell cell2) {
		StringBuffer message = new StringBuffer();
		message.append("Rules ");
		message.append(cell.getRuleTupleInfo().getId());
		message.append(" and ");
		message.append(cell2.getRuleTupleInfo().getId());
		message.append(" have equivalent actions");
		StringBuffer details = new StringBuffer();
		details.append("Consider combining the conditions '");
		details.append(cell.getExpression().getExpr());
		details.append("' and '");
		details.append(cell2.getExpression().getExpr());
		details.append("' into the single condition '");
		details.append(cell.getExpression().getExpr());
		details.append(" || ");
		details.append(cell2.getExpression().getExpr());
		details.append("'");

		reportProblem(decisionTable, message.toString(), Integer.parseInt(cell2.getRuleTupleInfo().getId()), -1, -1, SEVERITY_WARNING);
	}

	private Cell getCell(String columnId, List<Cell> list) {
		for (Cell cell : list) {
			if (cell.getColumnId().equals(columnId)) {
				return cell;
			}
		}
		return null;
	}

	private List<String> getEqualRules(Object[] ids, Map<String, List<Cell>> allActions, List<Cell> list, int i) {
		// need to check this action list (list of Cells) against all others, to
		// find a match
		List<String> equalLists = new ArrayList<String>();
		for (int j = i; j < ids.length; j++) {
			Object id = ids[j];
			List<Cell> targetList = allActions.get(id);
			if (areListsEqual(list, targetList)) {
				equalLists.add((String) id);
			}
		}
		return equalLists;
	}

	private boolean areListsEqual(List<Cell> list, List<Cell> targetList) {
		if (list.size() != targetList.size()) {
			return false; // this needs to be revisited, how should blank
							// actions be handled?
		}
		int size = list.size();
		for (int i = 0; i < size; i++) {
			Cell cell1 = list.get(i);
			Cell cell2 = targetList.get(i);
			if (!cell1.isEnabled() || !cell2.isEnabled()) {
				if (!cell1.isEnabled() && !cell2.isEnabled()) {
					continue;
				} else {
					return false;
				}
			}
			if (!cell1.getExpression().getExpr().equals(cell2.getExpression().getExpr())) {
				return false;
			}
		}
		return true;
	}

	private void collectActions(DecisionTable table, String columnName, Filter filter, Map<String, List<Cell>> allActions,
			Map<String, List<Cell>> allRuleCells) {
		Collection<LinkedHashSet<Cell>> values = new ArrayList<LinkedHashSet<Cell>>();
		if (filter instanceof EqualsFilter) {
			// When the above method is called
			// It will always have any equals
			// filter wrapped in the combination filter.
			values = ((EqualsFilter) filter).getValues();
		} else if (filter instanceof RangeFilter) {
			values = ((RangeFilter) filter).getRangeMap().values();
		}
		for (Set<Cell> set : values) {

			for (Cell cell : set) {
				if (!cell.isEnabled())
					continue;
				if (allRuleCells.containsKey(cell.getRuleTupleInfo().getId())) {
					List<Cell> cells = allRuleCells.get(cell.getRuleTupleInfo()
							.getId());
					if (!cells.contains(cell)) {
						cells.add(cell);
					}
				} else {
					List<Cell> cells = new ArrayList<Cell>();
					cells.add(cell);
					allRuleCells.put(cell.getRuleTupleInfo().getId(), cells);
				}
				List<Cell> actions = cell.getRuleTupleInfo().getActions();
				if (allActions.containsKey(cell.getRuleTupleInfo().getId())) {
					continue;
				}
				allActions.put(cell.getRuleTupleInfo().getId(), actions);
			}
		}}

	private void analyzeTableCompleteness(DecisionTable table, Object[] subsetRuleIds) {
		if (table == null) {
			return;
		}
		Set<String> columns = table.getColumns();
		for (String column : columns) {
			List<Filter> allFilters = table.getAllFilters(column);
			for (Filter filter : allFilters) {
				if (filter instanceof RangeFilter) {
					analyzeRangeCompleteness(column, (RangeFilter) filter, subsetRuleIds);
				}
			}
		}
	}

	/**
	 * Analyze and report missing equals conditions for range conditions. e.g :
	 * > 10 false < 10 true Report missing = 10.
	 * 
	 * @param columnName
	 * @param monitor
	 */
	private void analyzeEqualsCompleteness(String columnName) {
		// Get all filters for column
		RangeFilter columnRangeFilter = fCurrentOptimizedTable
				.getRangeFilter(columnName);
		EqualsFilter columnEqualsFilter = fCurrentOptimizedTable
				.getEqualsFilter(columnName);
		// Look for every expression inside rangefilter
		TreeMap<Object, LinkedHashSet<Cell>> rangeMap = columnRangeFilter
				.getRangeMap();
		outer: for (Object expressionKey : rangeMap.keySet()) {
			if (expressionKey instanceof Long) {
				if ((long) expressionKey == Long.MIN_VALUE
						|| (long) expressionKey == Long.MAX_VALUE) {
					// ignore
					continue;
				}
			}
			// Check all cells and operator kind
			LinkedHashSet<Cell> values = rangeMap.get(expressionKey);
			for (Cell cell : values) {
				Byte rangeKind = cell.getExpression().getRangeKind();
				Byte rangeBoundedKind = Byte.valueOf(Operator.RANGE_BOUNDED);
				if (rangeKind.equals(rangeBoundedKind)) {
					// >= or <= met. continue
					continue outer;
				} else {
					if (cell.getExpression().getOperands().length > 1) {
						if (!(expressionKey instanceof Date)) {
							if (cell.getExpression().getOperands()[0]
									.equals(expressionKey)
									&& rangeKind
											.equals(Operator.RANGE_MAX_EXCL_BOUNDED)) {
								continue outer;
							} else if (cell.getExpression().getOperands()[1]
									.equals(expressionKey)
									&& rangeKind
											.equals(Operator.RANGE_MIN_EXCL_BOUNDED)) {
								continue outer;
							}
						} else {
							if (((Date) cell.getExpression().getOperands()[0])
									.equals((Date) expressionKey)
									&& rangeKind
											.equals(Operator.RANGE_MAX_EXCL_BOUNDED)) {
								continue outer;
							} else if (((Date) cell.getExpression().getOperands()[1])
									.equals((Date) expressionKey)
									&& rangeKind
											.equals(Operator.RANGE_MIN_EXCL_BOUNDED)) {
								continue outer;
							}
						}

					}

				}

			}
			// Nothing found in range map check equals filter.
			// Check equals filter if it has this key after stringification
			boolean containsExpr = false;

			if (!(expressionKey instanceof Date)) {
				containsExpr = columnEqualsFilter.getMap().containsKey(expressionKey
						.toString());
			} else {
				DateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss");
				containsExpr = columnEqualsFilter.getMap().containsKey(format
						.format(expressionKey));
			}
			if (!containsExpr) {
				reportMissingEqualsCriterion(columnName, expressionKey);
			}
		}
	}

	private void analyzeRangeCompleteness(String columnName, RangeFilter filter, Object[] subsetRuleIds) {
		TreeMap<Object, LinkedHashSet<Cell>> rangeMap = filter.getRangeMap();
		Collection<LinkedHashSet<Cell>> values = rangeMap.values();
		List<Cell> processedCells = new ArrayList<Cell>();
		List<Range> coveredRanges = new ArrayList<Range>();
		List<Range> allRanges = new ArrayList<Range>();
		List<Object> subsetRuleIdList = Arrays.asList(subsetRuleIds);
		for (Set<Cell> list : values) {
			for (Cell cell : list) {
				if (processedCells.contains(cell)) {
					continue;
				}
				processedCells.add(cell);
				collectRange(cell, coveredRanges, allRanges, columnName);
			}
		}
		analyzeCoveredRanges(coveredRanges, subsetRuleIdList);
		analyzeEqualsCompleteness(columnName);
		analyzeOverlappingRanges(allRanges, subsetRuleIdList);
	}

	private void analyzeOverlappingRanges(List<Range> allRanges, List<Object> subsetRuleIdList) {
		int rangeSize = allRanges.size();
		String tablePath = getTablePath();

		for (int i = 0; i < rangeSize; i++) {
			Range range = allRanges.get(i);
			if (subsetRuleIdList.isEmpty() || subsetRuleIdList.contains(range.cell.getRuleTupleInfo().getId())) {
				Range[] overlappingRanges = getOverlappingRanges(range, allRanges,
						i);
				if (overlappingRanges.length > 0) {
					reportOverlappingRangeError(range, overlappingRanges, tablePath);
				}
			}	
		}
	}

	private void reportMissingEqualsCriterion(String columnName,
			Object expressionKey) {
		// String message =
		// Messages.getString("DecisionTable.analyze.missingEqualsCriterion.message",
		// columnName,
		// expression);
		reportUncoveredEqualsProblem(decisionTable, "Missing Equals Criterion", expressionKey, columnName, SEVERITY_WARNING);
	}

	private void reportOverlappingRangeError(Range range, Range[] overlappingRanges, String tablePath) {
		StringBuffer details = new StringBuffer();
		details.append("Range ");
		details.append(range.toString());
		details.append(" in column '");
		details.append(range.columnName);
		details.append("' overlaps with the following ");
		details.append(overlappingRanges.length);
		details.append(" ranges: ");
		for (Range overlappingRange : overlappingRanges) {
			details.append('\t');
			details.append(overlappingRange.toString());
			details.append('\t');
		}
		String ruleId = range.cell.getRuleTupleInfo().getId();

		reportOverlappingRangeProblem(decisionTable, details.toString(), Integer.parseInt(ruleId), SEVERITY_WARNING);
	}

	private Range[] getOverlappingRanges(Range range, List<Range> allRanges,
			int index) {
		List<Range> overlappingRanges = new ArrayList<Range>();

		for (int i = index + 1; i < allRanges.size(); i++) {

			Range targetRange = allRanges.get(i);
			if (range.equals(targetRange)) {
				continue;
			}
			if (range.min instanceof Integer && range.max instanceof Integer
					&& targetRange.min instanceof Integer
					&& targetRange.max instanceof Integer) {
				if (((int) range.min >= (int) targetRange.min && (int) range.max <= (int) targetRange.max)
						|| ((int) targetRange.min >= (int) range.min && (int) targetRange.max <= (int) range.max)
						|| ((int) targetRange.min > (int) range.min
								&& (int) targetRange.max > (int) range.max && (int) targetRange.min < (int) range.max)) {
					if (!overlappingRanges.contains(targetRange)) {
						overlappingRanges.add(targetRange);
					}
				}
			}
			if (range.min instanceof Long && range.max instanceof Long
					&& targetRange.min instanceof Long
					&& targetRange.max instanceof Long) {
				if (((long) range.min >= (long) targetRange.min && (long) range.max <= (long) targetRange.max)
						|| ((long) targetRange.min >= (long) range.min && (long) targetRange.max <= (long) range.max)
						|| ((long) targetRange.min > (long) range.min
								&& (long) targetRange.max > (long) range.max && (long) targetRange.min < (long) range.max)) {
					if (!overlappingRanges.contains(targetRange)) {
						overlappingRanges.add(targetRange);
					}
				}
			}

		}

		return overlappingRanges.toArray(new Range[overlappingRanges.size()]);
	}

	private void analyzeCoveredRanges(List<Range> coveredRanges,
			List<Object> subsetRuleIdList) {
		Object min = Long.MIN_VALUE;
		Object max = Long.MAX_VALUE;
		int rangeSize = coveredRanges.size();
		String tablePath = getTablePath();

		for (int i = 0; i < rangeSize; i++) {
			Range range = coveredRanges.get(i);
			String ruleId = range.cell.getRuleTupleInfo().getId();
			String columnId = range.cell.getColumnId();
			// char[] rangeChars =
			// getRangeCharacters(range);//range.cell.expression.operandRangeKind[0]);

			char[] rangeChars = null;
			if (i == 0) {
				rangeChars = getRangeCharactersForUncovered(range, null);
			} else {
				rangeChars = getRangeCharactersForUncovered(range,
						coveredRanges.get(i - 1));
			}

			if (subsetRuleIdList.isEmpty() || subsetRuleIdList.contains(ruleId)) {
				if (range.min instanceof Integer
						&& range.max instanceof Integer) {
					if (min instanceof Long) {
						min = Integer.MIN_VALUE;
					}
					if (max instanceof Long) {
						max = Integer.MAX_VALUE;
					}
					if ((int) min < (int) range.min) {
						if ((int) min == Integer.MIN_VALUE) {
							reportUncoveredUnboundedRange(range.columnName,
									rangeChars, range.min, tablePath, ruleId,
									columnId, false);
						} else {
							reportUncoveredBoundedRange(range.columnName,
									rangeChars, min, range.min, tablePath,
									ruleId, columnId);
						}
					}

					min = Math.max((int) min, (int) range.max);
					if (i == rangeSize - 1) {
						// the last covered range, check against 'max'
						if ((int) max > (int) range.max) {
							if ((int) max == Integer.MAX_VALUE) {
								reportUncoveredUnboundedRange(range.columnName,
										rangeChars, range.max, tablePath,
										ruleId, columnId, true);
							} else {
								reportUncoveredBoundedRange(range.columnName,
										rangeChars, range.max, max, tablePath,
										ruleId, columnId);
							}
						}
					}
				}
				
				if (range.min instanceof Long && range.max instanceof Long) {

					if ((long) min < (long) range.min) {// &&
														// (range.cell.expression.rangeKind
														// !=
						// Operator.RANGE_BOUNDED &&
						// range.cell.expression.rangeKind !=
						// Operator.RANGE_MAX_EXCL_BOUNDED)) {
						if ((long) min == Long.MIN_VALUE) {
							reportUncoveredUnboundedRange(range.columnName,
									rangeChars, range.min, tablePath, ruleId,
									columnId, false);
						} else {
							reportUncoveredBoundedRange(range.columnName,
									rangeChars, min, range.min, tablePath, ruleId,
									columnId);
						}
					}
					min = Math.max((long) min, (long) range.max);
					if (i == rangeSize - 1) {
						// the last covered range, check against 'max'
						if ((long) max > (long) range.max) {
							if ((long) max == Long.MAX_VALUE) {
								reportUncoveredUnboundedRange(range.columnName,
										rangeChars, range.max, tablePath, ruleId,
										columnId, true);
							} else {
								reportUncoveredBoundedRange(range.columnName,
										rangeChars, range.max, max, tablePath,
										ruleId, columnId);
							}
						}
					}
				}
				if (range.min instanceof Date && range.max instanceof Date) {
					try {
						DateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd'T'HH:mm:ss");
						if (min instanceof Long) {
							min = format.parse("1601-01-01T00:00:00");
						}
						if (max instanceof Long) {
							max = format.parse("9999-12-31T00:00:00");
						}

						if (((Date) min).before((Date) range.min)) {// &&
							// (range.cell.expression.rangeKind
							// !=
							// Operator.RANGE_BOUNDED &&
							// range.cell.expression.rangeKind !=
							// Operator.RANGE_MAX_EXCL_BOUNDED)) {
							if (((Date) min).equals(format
									.parse("1601-01-01T00:00:00"))) {
								reportUncoveredUnboundedRange(range.columnName,
										rangeChars, range.min, tablePath,
										ruleId, columnId, false);
							} else {
								reportUncoveredBoundedRange(range.columnName,
										rangeChars, min, range.min, tablePath,
										ruleId, columnId);
							}
						}
						if (((Date) min).after((Date) range.max)) {

						} else {
							min = (Date) range.max;
						}
						if (i == rangeSize - 1) {
							// the last covered range, check against 'max'
							if (((Date) max).after((Date) range.max)) {
								if (((Date) max).equals(format
										.parse("9999-12-31T00:00:00"))) {
									reportUncoveredUnboundedRange(
											range.columnName, rangeChars,
											range.max, tablePath, ruleId,
											columnId, true);
								} else {
									reportUncoveredBoundedRange(
											range.columnName, rangeChars,
											range.max, max, tablePath, ruleId,
											columnId);
								}
							}
						}
					} catch (ParseException e) {

					}
				}
			}			
		}
	}

	private char[] getRangeCharactersForUncovered(Range range, Range oldRange) {
		char[] rangeChars = new char[2];
		if (oldRange == null) {
			rangeChars[0] = '(';
		} else {
			if (Operator.RANGE_BOUNDED == oldRange.cell.getExpression().getRangeKind() 
					|| oldRange.cell.getExpression().getRangeKind()  == Operator.RANGE_MIN_EXCL_BOUNDED) {
				rangeChars[0] = '[';
			} else {
				rangeChars[0] = '(';
			}
		}

		if (Operator.RANGE_BOUNDED == range.cell.getExpression().getRangeKind() 
				|| range.cell.getExpression().getRangeKind() == Operator.RANGE_MAX_EXCL_BOUNDED) {
			rangeChars[1] = ']';
		} else {
			rangeChars[1] = ')';
		}

		return rangeChars;
	}

	private void reportUncoveredBoundedRange(String columnName, char[] rangeChars, Object min, Object min2, String tablePath,
												String ruleId, String columnId) {
		// String message =
		// Messages.getString("DecisionTable.analyze.uncoveredBoundedRange.message",
		// columnName,
		// rangeChars[0],
		// min,
		// max,
		// rangeChars[1]);

		StringBuffer details = new StringBuffer();
		details.append("Values between ");
		details.append(min);
		details.append(" and ");
		details.append(min2);
		details.append(" will not fire any rule");

		reportRangeProblem(decisionTable, "Uncovered Bounded Range", columnName, min, min2, rangeChars, 
				RANGE_TYPE_PROBLEM.RANGE_BETWEEN_PROBLEM, Integer.parseInt(ruleId), SEVERITY_WARNING);
	}

	private void reportUncoveredUnboundedRange(String columnName, char[] rangeChars, Object min, String tablePath, String ruleId,
												String columnId, boolean maxRange) {
		// String str = (maxRange) ?
		// "DecisionTable.analyze.uncoveredUnboundedRange.message"
		// : "DecisionTable.analyze.uncoveredUnboundedRangeMax.message";

		// String message = Messages.getString(str, columnName, rangeChars[0],
		// min, rangeChars[1]);

		RANGE_TYPE_PROBLEM rangeTypeProblem = null;
		StringBuffer details = new StringBuffer();
		if (maxRange) {
			details.append("Values greater than ");
			rangeTypeProblem = RANGE_TYPE_PROBLEM.RANGE_GREATER_THAN_PROBLEM;
		} else {
			details.append("Values less than ");
			rangeTypeProblem = RANGE_TYPE_PROBLEM.RANGE_LESS_THAN_PROBLEM;
		}
		if (rangeChars[1] == ')') {
			details.append("or equal to ");
		}
		details.append(min);
		details.append(" will not fire any rule");

		reportRangeProblem(decisionTable, "Uncovered Unbounded Range", columnName, min, min, rangeChars, rangeTypeProblem,
									Integer.parseInt(ruleId), SEVERITY_WARNING);
	}

	private char[] getRangeCharacters(Range range) {
		// Return the complement of set based on rangekind
		// For problem analysis we need complement range characters
		Expression expression = range.cell.getExpression();
		// Get the first entry inside the operationRangeKind Map
		// Set<Entry<String, Byte>> entrySet =
		// expression.operandRangeKinds.entrySet();
		// Entry<String, Byte>[] rangeKind = entrySet.toArray(new
		// Entry[entrySet.size()]);
		int rangeKind = expression.getRangeKind();
		switch (rangeKind) {
		case Operator.RANGE_BOUNDED: {
			return new char[] { '[', ']' };
		}
		case Operator.RANGE_MIN_EXCL_BOUNDED: {
			return new char[] { '(', ']' };
		}
		case Operator.RANGE_MAX_EXCL_BOUNDED: {
			return new char[] { '[', ')' };
		}
		case Operator.RANGE_MIN_MAX_EXCL_BOUNDED: {
			return new char[] { '(', ')' };
		}
		}
		return new char[] { '(', ')' };
	}

	private String getTablePath() {
		String tablePath = null;
		if (table != null) {
			tablePath = table.getFolder();
			if (tablePath != null && !tablePath.endsWith("/")) {
				tablePath += "/";
			}
			tablePath += table.getName();
		}
		return tablePath;
	}

	private void collectRange(Cell cell, List<Range> coveredRanges,
			List<Range> allRanges, String columnName) {
		Object[] operands = cell.getExpression().getOperands();
		Object opMin = getOpValue(operands[0]);
		Object opMax = getOpValue(operands[1]);
		Range newRange = new Range(opMin, opMax, columnName, cell);
		allRanges.add(newRange);
		if (coveredRanges.size() == 0) {
			coveredRanges.add(newRange);
			return;
		}

		for (Range range : coveredRanges) {
			if (opMin instanceof Integer && range.min instanceof Integer
					&& opMax instanceof Integer && range.max instanceof Integer) {
				if (((int) opMin < (int) range.min && (int) opMax > (int) range.min)
						|| ((int) opMin < (int) range.max && (int) opMax > (int) range.max)) {
					/*
					 * range.extendRange(newRange); return;
					 */
				} else if ((int) opMin > (int) range.min
						&& (int) opMax < (int) range.max) {
					// range is already completely covered, don't add it
					return;
				}
			}

			if (opMin instanceof Long && range.min instanceof Long
					&& opMax instanceof Long && range.max instanceof Long) {
				if (((long) opMin < (long) range.min && (long) opMax > (long) range.min)
						|| ((long) opMin < (long) range.max && (long) opMax > (long) range.max)) {
					/*
					 * range.extendRange(newRange); return;
					 */
				} else if ((long) opMin > (long) range.min
						&& (long) opMax < (long) range.max) {
					// range is already completely covered, don't add it
					return;
				}
			}
			if (opMin instanceof Date && range.min instanceof Date
					&& opMax instanceof Date && range.max instanceof Date) {
				if ((((Date) opMin).before((Date) range.min) && ((Date) opMax)
						.after((Date) range.min))
						|| (((Date) opMin).before((Date) range.max) && ((Date) opMax)
								.after((Date) range.max))) {
					/*
					 * range.extendRange(newRange); return;
					 */
				} else if ((((Date) opMin).after((Date) range.min))
						&& (((Date) opMax).before((Date) range.max))) {
					// range is already completely covered, don't add it
					return;
				}
			}
		}
		coveredRanges.add(newRange);
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
		return 0;
	}
	private class Range implements Comparable<Range> {

		public Object min;
		public Object max;
		public String columnName;
		public Cell cell;

		public Range(Object min, Object max, String columnName, Cell cell) {
			this.min = min;
			this.max = max;
			this.columnName = columnName;
			this.cell = cell;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Range)) {
				return false;
			}
			Range range = (Range) obj;
			return (range.min.equals(min) && range.max.equals(max)
					&& range.columnName.equals(columnName) && cell.getExpression().getRangeKind() == range.cell.getExpression().getRangeKind()); // also
																																// check
																																// cell?
		}

		public int compareTo(Range r) {
			if (this.min instanceof Long && r.min instanceof Long) {
				return (long) this.min > (long) r.min ? -1 : 1;
			} else if (this.min instanceof Integer && r.min instanceof Integer) {
				return (int) this.min > (int) r.min ? -1 : 1;
			} else if (this.min instanceof Date && r.min instanceof Date) {
				return ((Date) this.min).compareTo((Date) r.min);
			}
			return 0;
		}

		/*
		 * public void extendRange(Range r) { // extendRange(r.min, r.max); }
		 */

		// public void extendMin(Integer opMin) {
		// if (this.min > opMin) {
		// this.min = opMin;
		// }
		// }
		//
		// public void extendMax(Integer opMax) {
		// if (this.max < opMax) {
		// this.max = opMax;
		// }
		// }

		/*
		 * public void extendRange(int opMin, int opMax) { if (this.min > opMin)
		 * { this.min = opMin; } if (this.max < opMax) { this.max = opMax; } }
		 */
		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			char[] rangeCharacters = getRangeCharacters(this);
			if ((min instanceof Integer && (int) min == Integer.MIN_VALUE)
					|| (min instanceof Long && (long) min == Long.MIN_VALUE)) {
				buffer.append(rangeCharacters[0]);
				buffer.append("< ");
				buffer.append(max);
				buffer.append(rangeCharacters[1]);
				return buffer.toString();
			}
			if ((max instanceof Integer && (int) max == Integer.MAX_VALUE)
					|| (max instanceof Long && (long) max == Long.MIN_VALUE)) {
				buffer.append(rangeCharacters[0]);
				buffer.append("> ");
				buffer.append(min);
				buffer.append(rangeCharacters[1]);
				return buffer.toString();
			}
			buffer.append(rangeCharacters[0]);
			buffer.append(min);
			buffer.append(", ");
			buffer.append(max);
			buffer.append(rangeCharacters[1]);
			return buffer.toString();
		}

	}

	public void reportProblem(String resource, String message, int lineNumber, int start, int length, int severity) {

		// ProblemEvent problemEvent = new ProblemEvent(null, message, resource,
		// lineNumber, severity);
		// Do not add this to the collection for the time-being
		// fProblemEvents.add(problemEvent);
	}

	public void reportRangeProblem(String resource, String message, String columnName, Object min, Object min2, char[] rangeChars,
			RANGE_TYPE_PROBLEM rangeTypeProblem, int lineNumber, int severity) {

		ProblemEvent problemEvent = new RangeProblemEvent(null, null, message,
				null, resource, min, min2, columnName,
				rangeTypeProblem, lineNumber, severity);
		problemEvent.setData("rangeChar1", rangeChars[0]);
		problemEvent.setData("rangeChar2", rangeChars[1]);
		fProblemEvents.add(problemEvent);
	}

	public void reportOverlappingRangeProblem(final String resource, final String message, int ruleId, final int severity) {

		ProblemEvent problemEvent = new OverlappingRangeProblemEvent(null,
				message, resource, ruleId, severity);
		fProblemEvents.add(problemEvent);
	}

	public void reportRuleCombinationProblem(final String resource, final String message, String location, String otherRuleId,
												final int severity) {

		ProblemEvent problemEvent = new RuleCombinationProblemEvent(null,
				message, resource, Integer.parseInt(location), severity);
		problemEvent.setData("otherRuleId", otherRuleId);
		fProblemEvents.add(problemEvent);
	}

	public void reportDuplicatesProblem(final String resource, final String message, final int duplicateRuleID, final int severity) {

		ProblemEvent problemEvent = new DuplicateRuleProblemEvent(null,
				message, resource, duplicateRuleID, -1, severity);
		fProblemEvents.add(problemEvent);
	}

	public void reportConflictingActionsProblem(final String resource, final String message, final int problemRuleID,
													final int otherRuleId, final int severity) {
		ProblemEvent problemEvent = new ConflictingActionsProblemEvent(null,
				message, resource, problemRuleID, severity);
		problemEvent.setData("otherRuleId", otherRuleId);
		fProblemEvents.add(problemEvent);
	}

	public void reportUncoveredDomainEntriesProblem(final String resource, final String message, final String domainValue,
														final String columnName, final int severity) {

		ProblemEvent problemEvent = new UncoveredDomainEntryProblemEvent(null,
				message, resource, domainValue, columnName, severity);
		fProblemEvents.add(problemEvent);
	}

	public List<ProblemEvent> getProblems() {
		return Collections.unmodifiableList(fProblemEvents);
	}

	public void reportUncoveredEqualsProblem(final String resource, final String message, final Object expressionKey,
												final String columnName, final int severity) {
		ProblemEvent problemEvent = new MissingEqualsCriterionProblemEvent(
				null, message, resource, expressionKey, columnName, severity);
		problemEvent.setData("columnName", columnName);
		fProblemEvents.add(problemEvent);
	}
}