package com.tibco.cep.decision.table.constraintpane;

import static com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerConstants.ANALYZE_FIXABLE_MARKER_NAME;
import static com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerConstants.DONT_CARE;
import static com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerUtils.getConfiguredDomains;
import static com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerUtils.getDomainValues;
import static com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerUtils.getPropertyPathForColumn;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

import com.tibco.cep.decision.table.extension.IDecisionTableAnalyzerExtension;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.studio.util.logger.problems.ProblemEvent;
import com.tibco.cep.studio.util.logger.problems.RANGE_TYPE_PROBLEM;

public class AnalyzeTableRunnable {
	
	private static final String CLASS = AnalyzeTableRunnable.class.getName();
	
	private class Range implements Comparable<Range> {

		public int min;
		public int max;
		public String columnName;
		public Cell cell;

		public Range(Integer min, Integer max, String columnName, Cell cell) {
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
			return (range.min == min && range.max == max && range.columnName == columnName && cell.expression.rangeKind == range.cell.expression.rangeKind); // also check cell?
		}

		public int compareTo(Range r) {
			return this.min > r.min ? -1 : 1;
		}
		
		public void extendRange(Range r) {
		//	extendRange(r.min, r.max);
		}

//		public void extendMin(Integer opMin) {
//			if (this.min > opMin) {
//				this.min = opMin;
//			}
//		}
//
//		public void extendMax(Integer opMax) {
//			if (this.max < opMax) {
//				this.max = opMax;
//			}
//		}

		public void extendRange(int opMin, int opMax) {
			if (this.min > opMin) {
				this.min = opMin;
			}
			if (this.max < opMax) {
				this.max = opMax;
			}
		}

		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			char[] rangeCharacters = getRangeCharacters(this);
			if (min == Integer.MIN_VALUE) {
				buffer.append(rangeCharacters[0]);
				buffer.append("< ");
				buffer.append(max);
				buffer.append(rangeCharacters[1]);
				return buffer.toString();
			}
			if (max == Integer.MAX_VALUE) {
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

	
	
	private DecisionTable fOptimizedTable;
	
	private Table fEMFTable;
	
	private AnalysisProblemsCollector problemsCollection = new AnalysisProblemsCollector();
	
	private IFile decisionTableFile;
	
	
	/**
	 * If true domain models associated with properties of the table will be used
	 * for table completeness.
	 */
	private boolean useDomainModel;

	private IDecisionTableAnalyzerExtension formatter;

	public AnalyzeTableRunnable(DecisionTable optimizedTable, 
			                    Table table, 
			                    boolean useDomainModel,
			                    IDecisionTableAnalyzerExtension formatter) {
		this.fOptimizedTable = optimizedTable;
		this.fEMFTable = table;
		this.useDomainModel = useDomainModel;
		this.formatter = formatter;
		this.decisionTableFile = formatter != null ? formatter.getUnderlyingFile() : null;
	}
	
	private void addMarkers(final IResource resource, SubMonitor monitor) throws Exception {
		if (resource == null) {
			return;
		}
		monitor.setTaskName("Deleting previous markers");
		resource.deleteMarkers(ANALYZE_FIXABLE_MARKER_NAME, true, IResource.DEPTH_ZERO);
		
		try {
			monitor.setTaskName("Creating warning markers");
			List<ProblemEvent> problems = problemsCollection.getProblems();
			monitor.setWorkRemaining(problems.size());
			DecisionTableUIPlugin.debug(CLASS, "Started marker generation at {0}", System.currentTimeMillis());
			for (ProblemEvent problemEvent : problems) {
				formatter.processAnalyzerProblemEvent(problemEvent, fEMFTable);
				monitor.newChild(1);
			}
			DecisionTableUIPlugin.debug(CLASS, "Finished marker generation at {0}", System.currentTimeMillis());
		} catch (CoreException e) {
			e.printStackTrace();
		}

	}

	public void analyze(SubMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		//Thread.sleep(200);
		DecisionTable table = fOptimizedTable;
		//monitor.setWorkRemaining(IProgressMonitor.UNKNOWN);
		if (table == null) {
			monitor.done();
			return;
		}
		//fProblemEvents.clear();
		monitor.beginTask("Analyzing table " + getTable().getName(), IProgressMonitor.UNKNOWN);
		Map<String, List<Cell>> allActions = new HashMap<String, List<Cell>>();
		Map<String, List<Cell>> allRuleCells = new HashMap<String, List<Cell>>();
		
		SubMonitor gatheringActionsMonitor = monitor.newChild(40);
		collectAllActions(table, gatheringActionsMonitor, allActions, allRuleCells);
		//monitor.worked(30);
		if (monitor.isCanceled()) {
			return;
		}
		SubMonitor tableCompletenessMonitor = monitor.newChild(10);
		analyzeTableCompleteness(table, tableCompletenessMonitor);
		monitor.setWorkRemaining(50);
		//monitor.worked(50);
		if (monitor.isCanceled()) {
			return;
		}
		SubMonitor conflictingActionsCheckMonitor = monitor.newChild(10);
		analyzeRulesForConflictingActions(table, conflictingActionsCheckMonitor, allActions, allRuleCells);
		monitor.setWorkRemaining(40);
		//monitor.worked(65);
		if (monitor.isCanceled()) {
			return;
		}
		//
		if (monitor.isCanceled()) {
			return;
		}
		SubMonitor ruleCombiningCheckMonitor = monitor.newChild(15);
		//analyzeOverlappingRules(table, overlappingActionsCheckMonitor);
		
		analyzeRulesToBeCombined(table, ruleCombiningCheckMonitor, allActions, allRuleCells);
		monitor.worked(85);
		if (monitor.isCanceled()) {
			return;
		}
		SubMonitor problemsReportingMonitor = monitor.newChild(25);
		monitor.setTaskName("Reporting problems");
		
		try {
			addMarkers(decisionTableFile, problemsReportingMonitor);
		} catch (Exception e) {
			throw new InvocationTargetException(e);
		}
		//monitor.done();
	}

	
	private Table getTable() {
		return fEMFTable;
	}
	
	
	/**
	 * Collect all conditions and actions with maps having keys as rule id
	 * @param table
	 * @param monitor
	 * @param allActions -> All actions
	 * @param allRuleCells -> All conditions
	 */
	private void collectAllActions(DecisionTable table,
			                       SubMonitor monitor,
			                       Map<String, List<Cell>> allActions,
			                       Map<String, List<Cell>> allRuleCells) {
		Set<String> columns = table.getColumns();
		monitor.setTaskName("Gathering Actions ");	
		monitor.setWorkRemaining(columns.size());
		
		for (String column : columns) {
			List<Filter> allFilters = table.getAllFilters(column);
			for (Filter filter : allFilters) {
				collectActions(table, column, filter, monitor, allActions, allRuleCells);
			}
			//Progress by one tick
			monitor.newChild(1);
		}
		
	}
		
	/**
	 * Find out rules which have identical set of conditions but different
	 * set of actions for them. 
	 * @param table
	 * @param monitor
	 * @param allActions
	 * @param allRuleCells
	 */
	private void analyzeRulesForConflictingActions(DecisionTable table, 
			                                       SubMonitor monitor,
			                                       Map<String, List<Cell>> allActions,
			                                       Map<String, List<Cell>> allRuleCells) {
		monitor.setTaskName("Analyzing rules that can have conflicting actions");
		Date start = new Date();
		if (table == null) {
			return;
		}
		
		//Get its keyset
		Set<String> ruleIDs = allRuleCells.keySet();
		int size = ruleIDs.size();
		monitor.setWorkRemaining(size);
		String[] ids = ruleIDs.toArray(new String[size]);
		
		for (int outerLoop = 0; outerLoop < size; outerLoop++) {
			String lhsId = ids[outerLoop];
			List<Cell> lhs = allRuleCells.get(lhsId);
			for (int innerLoop = outerLoop + 1; innerLoop < size; innerLoop++) {
				String rhsId = ids[innerLoop];
				List<Cell> rhs = allRuleCells.get(rhsId);
				boolean areEqual = areListsEqual(lhs, rhs);
				//Compare and report conflicting actions if conditions are equal
				if (areEqual) {
					boolean equalActions = compareActions(lhsId, rhsId, allActions);
					//If action set is different flag it
					if (!equalActions) {
						String message = Messages.getString("DecisionTable.analyze.conflictingActions.message", lhsId, rhsId);
						problemsCollection.reportConflictingActionsProblem(decisionTableFile,
								                                           message, 
								                                           Integer.parseInt(rhsId),
								                                           IMarker.SEVERITY_WARNING);
					}
				}
			}
			monitor.newChild(1);
		}
		Date end = new Date();
		System.out.println("Rule conflict change check took " + (end.getTime() - start.getTime()));
	}
	
	
	
	/**
	 * Compare actions of row ids specified by lhsId and rhsId
	 * @param lhsId
	 * @param rhsId
	 * @param allActions
	 * @return
	 */
	private boolean compareActions(String lhsId, 
			                       String rhsId, 
			                       Map<String, List<Cell>> allActions) {
		//Get actions matching both
		List<Cell> lhsActions = allActions.get(lhsId);
		List<Cell> rhsActions = allActions.get(rhsId);
		return areListsEqual(lhsActions, rhsActions);
	}

	private void analyzeRulesToBeCombined(DecisionTable table, 
			                              SubMonitor monitor,
			                              Map<String, List<Cell>> allActions,
			                              Map<String, List<Cell>> allRuleCells) {
		monitor.subTask("Analyzing rules that can be combined");
		Date start = new Date();
		if (table == null) {
			return;
		}
		
		// allActions contains all actions, keyed by the rule ID
		Set<String> ruleIDs = allActions.keySet();
		int size = ruleIDs.size();
		Object[] ids = ruleIDs.toArray();
		//Set<RuleTupleInfo> ruleTuples = table.ruleTuples;
		for (int i = 0; i < size; i++) {
			Object id = ids[i];
			List<Cell> list = allActions.get(id);
			List<String> equalRules = getEqualRules(ids, allActions, list, i+1);
			//Get duplicate rules
			for (String id2 : equalRules) {
				String message = 
					Messages.getString("DecisionTable.analyze.equalRules.message", id, id2);
				List<Cell> cellList1 = allRuleCells.get(id);
				List<Cell> cellList2 = allRuleCells.get(id2);
//				compare these two lists!
				analyzeLists(cellList1, cellList2);
				problemsCollection.reportRuleCombinationProblem(decisionTableFile, message, id2, IMarker.SEVERITY_WARNING);
			}
		}
		Date end = new Date();
		DecisionTableUIPlugin.debug(CLASS, "Rule combination check took {0}", (end.getTime() - start.getTime()));
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
				otherCell = getCell(cell.columnId, list);
			} else {
				otherCell = getCell(cell.columnId, targetList);
			}
			if (otherCell == null) {
				// could not find the equivalent cell, must be a blank cell
				continue;
			}
			if (cell.expression.operatorKind == Operator.RANGE_OPERATOR
					&& otherCell.expression.operatorKind == Operator.RANGE_OPERATOR) {
				rangeCells.put(cell, otherCell);
			} else if (!cell.expression.expr.equals(otherCell.expression.expr)) {
				// if even one (non-range) cell is not equal, we cannot combine this rule
				//return false;
			}
		}
		if (rangeCells.keySet().size() > 0) {
			// found range cells that could potentially be combined
			analyzeRangeCells(rangeCells);
			//return true;
		}
		//return true;
	}

	private void analyzeRangeCells(HashMap<Cell, Cell> rangeCells) {
		Set<Cell> keySet = rangeCells.keySet();
		for (Cell cell : keySet) {
			Cell cell2 = rangeCells.get(cell);
			combineCells(cell, cell2);
		}
	}

	private void combineCells(Cell cell, Cell cell2) {
		Object[] operands = cell.expression.operands;
		Object[] operands2 = cell2.expression.operands;
		int op1min = getOpValue(operands[0]);
		int op1max = getOpValue(operands[1]);
		int op2min = getOpValue(operands2[0]);
		int op2max = getOpValue(operands2[1]);
		if (op1min == Integer.MIN_VALUE && op2max == Integer.MAX_VALUE) {
			// this cell can be combined with an OR (for instance, < 30 || > 50)
			reportCellCombination(cell, cell2);
		}
		if (op1max == Integer.MAX_VALUE && op2min == Integer.MIN_VALUE) {
			// this cell can be combined with an OR (for instance, < 30 || > 50)
			reportCellCombination(cell, cell2);
		}
	}

	private void reportCellCombination(Cell cell, Cell cell2) {
		DecisionTableUIPlugin.debug(CLASS, "conditions can be combined: {0} and {1}", cell.ri.id, cell2.ri.id);
		StringBuffer message = new StringBuffer();
		message.append("Rules ");
		message.append(cell.ri.id);
		message.append(" and ");
		message.append(cell2.ri.id);
		message.append(" have equivalent actions");
		StringBuffer details = new StringBuffer();
		details.append("Consider combining the conditions '");
		details.append(cell.expression.expr);
		details.append("' and '");
		details.append(cell2.expression.expr);
		details.append("' into the single condition '");
		details.append(cell.expression.expr);
		details.append(" || ");
		details.append(cell2.expression.expr);
		details.append("'");
		
		problemsCollection.reportProblem(decisionTableFile, 
				      message.toString(), 
				      Integer.parseInt(cell2.ri.id), -1, -1, IMarker.SEVERITY_WARNING);
	}

	private Cell getCell(String columnId, List<Cell> list) {
		for (Cell cell : list) {
			if (cell.columnId.equals(columnId)) {
				return cell;
			}
		}
		return null;
	}

	private List<String> getEqualRules(
			Object[] ids, Map<String, List<Cell>> allActions, List<Cell> list, int i) {
		// need to check this action list (list of Cells) against all others, to find a match
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
			return false; // this needs to be revisited, how should blank actions be handled?
		}
		int size = list.size();
		for (int i=0; i<size; i++) {
			Cell cell1 = list.get(i);
			Cell cell2 = targetList.get(i);
			if (!cell1.isEnabled() || !cell2.isEnabled()) {
				if (!cell1.isEnabled() && !cell2.isEnabled()) {
					continue;
				} else {
					return false;
				}
			}
			if (!cell1.expression.expr.equals(cell2.expression.expr)) {
				return false;
			}
		}
		return true;
	}
	

	private void collectActions(DecisionTable table, String columnName,
			Filter filter, SubMonitor monitor, Map<String, List<Cell>> allActions, Map<String,List<Cell>> allRuleCells) {
		Collection<LinkedHashSet<Cell>> values = new ArrayList<LinkedHashSet<Cell>>();
		if (filter instanceof EqualsFilter) {
			//When the above method is called
			//It will always have any equals
			//filter wrapped in the combination filter.
			values = ((EqualsFilter)filter).getValues();
		} else if (filter instanceof RangeFilter) {
			values = ((RangeFilter)filter).rangeMap.values();
		}
		for (Set<Cell> set : values) {
			if (monitor.isCanceled()) {
				return;
			}
			for (Cell cell : set) {
				if (!cell.isEnabled())
					continue;
				if (allRuleCells.containsKey(cell.ri.getId())) {
					List<Cell> cells = allRuleCells.get(cell.ri.getId());
					if (!cells.contains(cell)) {
						cells.add(cell);
					}
				} else {
					List<Cell> cells = new ArrayList<Cell>();
					cells.add(cell);
					allRuleCells.put(cell.ri.getId(), cells);
				}
				List<Cell> actions = cell.ri.getActions();
				if (allActions.containsKey(cell.ri.getId())) {
					continue;
				}
				allActions.put(cell.ri.getId(), actions);
			}
		}
	}
	
//	private void analyzeOverlappingRules(DecisionTable table) {
//		if (table == null) {
//			return;
//		}
//		
//	}

	private void analyzeTableCompleteness(DecisionTable table, SubMonitor monitor) {
		if (table == null) {
			return;
		}
		String projectName = table.getProjectName();
		Set<String> columns = table.getColumns();
		monitor.setTaskName("Analyzing TableCompleteness");
		monitor.setWorkRemaining(columns.size());
		for (String column : columns) {
			List<LinkedHashSet<Cell>> values =new LinkedList<LinkedHashSet<Cell>>();
			List<Filter> allFilters = table.getAllFilters(column);
			for (Filter filter : allFilters) {
				if (filter instanceof RangeFilter) {
					RangeFilter rangeFilter = (RangeFilter)filter;
					//Check if domains are configured
					if (useDomainModel) {
						//Get property path
						String propertyPath = 
							getPropertyPathForColumn(column, table);
						List<DomainInstance> domainInstances = 
							getConfiguredDomains(propertyPath, projectName);
						if (domainInstances.size() > 0) {
							values.addAll(rangeFilter.rangeMap.values());
					} else {
							//Go the regular route
							analyzeRangeCompleteness(column, (RangeFilter) filter, monitor);
						}
					} else {
						analyzeRangeCompleteness(column, (RangeFilter) filter, monitor);
					}
				}
				if (filter instanceof EqualsFilter) {
					if (useDomainModel) {
						/**
						 * Analyze coverage for equals based on associated domain model.
						 * This is called only if domain model flag is true
						 */
						EqualsFilter equalsFilter = (EqualsFilter)filter;
						values.addAll(equalsFilter.getValues());
						
					}
				}
			}
			if (useDomainModel) {
				String propertyPath = getPropertyPathForColumn(column, table);
				analyzeDomainEqualsCoverage(values, 
	                    propertyPath, 
	                    column,
	                    projectName);
				
			}
			//monitor.newChild(1);
		}
	}
	
	/**
	 * Analyze and report missing equals conditions for range conditions.
	 * e.g : > 10 false
	 *       < 10 true
	 * Report missing = 10.      
	 * @param columnName
	 * @param monitor
	 */
	private void analyzeEqualsCompleteness(String columnName, SubMonitor monitor) {
		//Get all filters for column
		RangeFilter columnRangeFilter = fOptimizedTable.getRangeFilter(columnName);
		EqualsFilter columnEqualsFilter = fOptimizedTable.getEqualsFilter(columnName);
		//Look for every expression inside rangefilter
		TreeMap<Integer, LinkedHashSet<Cell>> rangeMap = columnRangeFilter.rangeMap;
		outer : 
		for (Integer expressionKey : rangeMap.keySet()) {
			if (expressionKey == Integer.MIN_VALUE || expressionKey == Integer.MAX_VALUE) {
				//ignore
				continue;
			}
			//Check all cells and operator kind
			LinkedHashSet<Cell> values = rangeMap.get(expressionKey);
			for (Cell cell : values) {
				Byte rangeKind = cell.expression.rangeKind;
				Byte rangeBoundedKind = Byte.valueOf(Operator.RANGE_BOUNDED);
				if (rangeKind.equals(rangeBoundedKind)) {
					//>= or <= met. continue
					continue outer;
				} else{
					if(cell.expression.operands.length > 1){
						if(cell.expression.operands[0].equals(expressionKey) && rangeKind.equals(Operator.RANGE_MAX_EXCL_BOUNDED)){
							continue outer;
						}else if (cell.expression.operands[1].equals(expressionKey) && rangeKind.equals(Operator.RANGE_MIN_EXCL_BOUNDED)){
							continue outer;
						}
					}
					
				}			
				
			}
			//Nothing found in range map check equals filter.
			//Check equals filter if it has this key after stringification
			boolean containsExpr = columnEqualsFilter.map.containsKey(expressionKey.toString());
			if (!containsExpr) {
				reportMissingEqualsCriterion(columnName, expressionKey);
			}
		}
	}

	
		
	/**
	 * The cell values collection contains all conditions
	 * for that particular column.
	 * @param cellValues
	 * @param propertyPath
	 * @param columnName -> The actual column name required to report the problem
	 * @param project
	 */
	private void analyzeDomainEqualsCoverage(Collection<LinkedHashSet<Cell>> cellValues, 
			                                 String propertyPath,
			                                 String columnName,
			                                 String project) {
		String[] domainValues = 
			getDomainValues(propertyPath, project, "");
		if (domainValues != null && domainValues.length > 0) {
			//Removing the VALUE value from the domain values
			String[] newDomainValues = new String[domainValues.length-1];
			System.arraycopy(domainValues, 1, newDomainValues, 0,domainValues.length -1);

			//No need to analyze equals coverage
			outer:
			for (String domainValue : newDomainValues) {
				if (DONT_CARE == domainValue.intern()) {
				continue;
				}
				for (Set<Cell> values : cellValues) {
					for (Cell value : values) {
						if(value.getBody().intern() == DONT_CARE){
							continue;
						}
						//FIX BE-14268: Don't care was being compared with other Values and was unequal.So Ignore Don't Care.
						if ( domainValue.equals(value.getBody())) {
							continue outer;
						}
					}
				}
				//If we reach here no match was found for this domain value
				String message = 
					Messages.getString("DecisionTable.analyze.uncoveredDomainEntry.message", domainValue, propertyPath);
				problemsCollection.
					reportUncoveredDomainEntriesProblem(decisionTableFile, message, domainValue, columnName, IMarker.SEVERITY_WARNING);
			}
		}
	}
	
	

	private void analyzeRangeCompleteness(String columnName, RangeFilter filter, SubMonitor monitor) {
		TreeMap<Integer, LinkedHashSet<Cell>> rangeMap = filter.rangeMap;
		Collection<LinkedHashSet<Cell>> values = rangeMap.values();
		List<Cell> processedCells = new ArrayList<Cell>();
		List<Range> coveredRanges = new ArrayList<Range>();
		List<Range> allRanges = new ArrayList<Range>();
		for (Set<Cell> list : values) {
			if (monitor.isCanceled()) {
				return;
			}
			for (Cell cell : list) {
				if (processedCells.contains(cell)) {
					continue;
				}
				processedCells.add(cell);
				collectRange(cell, coveredRanges, allRanges, columnName);
			}
		}
		Date start = new Date();
		monitor.subTask("Performing completeness check");
		analyzeCoveredRanges(coveredRanges, monitor);
		Date end = new Date();
		DecisionTableUIPlugin.debug(CLASS, "Covered range check took {0}", (end.getTime() - start.getTime()));
		start = new Date();
		monitor.subTask("Performing missing equals check");
		analyzeEqualsCompleteness(columnName, monitor);
		end = new Date();
		start = new Date();
		monitor.subTask("Performing overlapping range check");
		analyzeOverlappingRanges(allRanges, monitor);
		end = new Date();
		DecisionTableUIPlugin.debug(CLASS, "Overlapping range check took {0}", (end.getTime() - start.getTime()));
	}

	private void analyzeOverlappingRanges(List<Range> allRanges, SubMonitor monitor) {
		int rangeSize = allRanges.size();
		String tablePath = getTablePath();

		for (int i=0; i<rangeSize; i++) {
			if (monitor.isCanceled()) {
				return;
			}
			Range range = allRanges.get(i);
			Range[] overlappingRanges = getOverlappingRanges(range, allRanges,i);
			if (overlappingRanges.length > 0) {
				reportOverlappingRangeError(range, overlappingRanges, tablePath);
			}
		}
	}
	
	private void reportMissingEqualsCriterion(String columnName, Integer expression) {
		String message = 
			Messages.getString("DecisionTable.analyze.missingEqualsCriterion.message",
					           columnName,
					           expression);
		problemsCollection.reportUncoveredEqualsProblem(decisionTableFile,
				                                        message, 
				                                        expression,
				                                        columnName,
				                                        IMarker.SEVERITY_WARNING);
	}

	private void reportOverlappingRangeError(Range range,
			Range[] overlappingRanges, String tablePath) {
//		String message =
//			Messages.getString("DecisionTable.analyze.overlappingRange.message",
//					           range.columnName,
//					           range.toString());

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
		String ruleId = range.cell.ri.getId();
		//String columnId = range.cell.columnId;
		
		problemsCollection.reportOverlappingRangeProblem(decisionTableFile, details.toString(), Integer.parseInt(ruleId), IMarker.SEVERITY_WARNING);
		}

	
	private Range[] getOverlappingRanges(Range range, List<Range> allRanges, int index) {
		List<Range> overlappingRanges = new ArrayList<Range>();

		for (int i=index + 1; i<allRanges.size(); i++) {
			
			Range targetRange = allRanges.get(i);
			if (range == targetRange) {
				continue;
			}
			if (( range.min >= targetRange.min  &&  range.max <= targetRange.max )
					|| (targetRange.min >= range.min && targetRange.max <= range.max) 
					|| (targetRange.min > range.min && targetRange.max > range.max && targetRange.min < range.max ) ) {
				if (!overlappingRanges.contains(targetRange)) {
					overlappingRanges.add(targetRange);
				}
			}
		}
		
		return overlappingRanges.toArray(new Range[overlappingRanges.size()]);
	}

	private void analyzeCoveredRanges(List<Range> coveredRanges, SubMonitor monitor) {
		int min = Integer.MIN_VALUE;
		int max = Integer.MAX_VALUE;
		int rangeSize = coveredRanges.size();
		String tablePath = getTablePath();

		for (int i = 0; i < rangeSize; i++) {
			if (monitor.isCanceled()) {
				return;
			}
			Range range = coveredRanges.get(i);
			String ruleId = range.cell.ri.getId();
			String columnId = range.cell.columnId;
			//char[] rangeChars = getRangeCharacters(range);//range.cell.expression.operandRangeKind[0]);
			char[] rangeChars = null;
			if(range.cell.expression.operandRangeKinds.equals(Operator.RANGE_BOUNDED) || range.cell.expression.operandRangeKinds.equals(Operator.RANGE_MIN_EXCL_BOUNDED)){
				rangeChars =new char[]{'[', ']'};
			} else{
				rangeChars =new char[]{'[', ')'};
			}
			if (min < range.min){// && (range.cell.expression.rangeKind != Operator.RANGE_BOUNDED && range.cell.expression.rangeKind != Operator.RANGE_MAX_EXCL_BOUNDED)) {
				if (min == Integer.MIN_VALUE) {
					reportUncoveredUnboundedRange(range.columnName, rangeChars, range.min, tablePath, ruleId, columnId, false);
				} else {
					reportUncoveredBoundedRange(range.columnName, rangeChars, min, range.min, tablePath, ruleId, columnId);
				}
			}
			min = Math.max(min, range.max);
			if (i == rangeSize - 1) {
				// the last covered range, check against 'max'
				if (max > range.max) {
					if (max == Integer.MAX_VALUE) {
						reportUncoveredUnboundedRange(range.columnName, rangeChars, range.max, tablePath, ruleId, columnId, true);
					} else {
						reportUncoveredBoundedRange(range.columnName, rangeChars, range.max, max, tablePath, ruleId, columnId);
					}
				}
			}
		}
	}

	private void reportUncoveredBoundedRange(String columnName,
			char[] rangeChars, int min, int max, String tablePath, String ruleId, String columnId) {
		//String message="";
		
		//if ()
		String message = 
				Messages.getString("DecisionTable.analyze.uncoveredBoundedRange.message",
						           columnName,
						           rangeChars[0],
						           min,
						           max,
						           rangeChars[1]);

		StringBuffer details = new StringBuffer();
		details.append("Values between ");
		details.append(min);
		details.append(" and ");
		details.append(max);
		details.append(" will not fire any rule");
		
		//reportProblem(decisionTableFile, message.toString(), Integer.parseInt(ruleId), -1, -1, IMarker.SEVERITY_WARNING);
		problemsCollection.reportRangeProblem(decisionTableFile, 
				           message.toString(), 
				           columnName, 
				           min, 
				           max,
				           RANGE_TYPE_PROBLEM.RANGE_BETWEEN_PROBLEM, 
				           Integer.parseInt(ruleId), 
				           IMarker.SEVERITY_WARNING);
	}

	private void reportUncoveredUnboundedRange(String columnName,
			char[] rangeChars, int min, String tablePath, String ruleId, String columnId, boolean maxRange) {
		String str = (maxRange) ? "DecisionTable.analyze.uncoveredUnboundedRange.message" 
									: "DecisionTable.analyze.uncoveredUnboundedRangeMax.message";

		String message = Messages.getString(str,
		           columnName,
		           rangeChars[0],
		           min,
		           rangeChars[1]);

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
		
		//reportProblem(decisionTableFile, message.toString(), Integer.parseInt(ruleId), -1, -1, IMarker.SEVERITY_WARNING);
		problemsCollection.reportRangeProblem(decisionTableFile, 
		                   message, 
		                   columnName, 
		                   min, 
		                   min,
		                   rangeTypeProblem, 
		                   Integer.parseInt(ruleId), 
		                   IMarker.SEVERITY_WARNING);
	}

	private char[] getRangeCharacters(Range range) {
		//Return the complement of set based on rangekind
		//For problem analysis we need complement range characters
		Expression expression = range.cell.expression;
		//Get the first entry inside the operationRangeKind Map
//		Set<Entry<String, Byte>> entrySet = expression.operandRangeKinds.entrySet();
//		Entry<String, Byte>[] rangeKind = entrySet.toArray(new Entry[entrySet.size()]);
		int rangeKind = expression.rangeKind;
		switch (rangeKind) {
		case Operator.RANGE_BOUNDED: {
			return new char[] { '[', ']' };
		}
		case Operator.RANGE_MIN_EXCL_BOUNDED:
		{
			return new char[] { '(', ']' };
		}
		case Operator.RANGE_MAX_EXCL_BOUNDED:
		{
			return new char[] { '[', ')' };
		}
		case Operator.RANGE_MIN_MAX_EXCL_BOUNDED:
		{
			return new char[] { '(', ')' };
		}
		}
		return new char[] { '(', ')' };
	}
	
	public List<ProblemEvent> getProblems() {
		return problemsCollection.getProblems();
	}

	private String getTablePath() {
		String tablePath = null;
		Table table = getTable();
		if (table != null) {
			tablePath = table.getFolder();
			if (tablePath != null && !tablePath.endsWith("/")) {
				tablePath += "/";
			}
			tablePath += table.getName();
		}
		return tablePath;
	}

	private void collectRange(Cell cell, List<Range> coveredRanges, List<Range> allRanges, String columnName) {
		Object[] operands = cell.expression.operands;
		int opMin = getOpValue(operands[0]);
		int opMax = getOpValue(operands[1]);
		Range newRange = new Range(opMin, opMax, columnName, cell);
		allRanges.add(newRange);
		if (coveredRanges.size() == 0) {
			coveredRanges.add(newRange);
			return;
		}
		for (Range range : coveredRanges) {
			if ((opMin < range.min && opMax > range.min)
					|| (opMin < range.max && opMax > range.max)) {
			/*range.extendRange(newRange);
				return;*/
			} else if (opMin > range.min && opMax < range.max) {
				// range is already completely covered, don't add it
				return;
			}
		}
		coveredRanges.add(newRange);
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
}
