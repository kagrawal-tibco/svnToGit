package com.tibco.cep.decision.table.constraintpane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.SubMonitor;

import com.tibco.cep.decision.table.command.memento.ColumnNameStateMemento;
import com.tibco.cep.decision.table.constraintpane.event.ConstraintsTableEvent;
import com.tibco.cep.decision.table.constraintpane.event.ConstraintsTableEvent.ConstraintsTableEventType;
import com.tibco.cep.decision.table.constraintpane.event.IConstraintsTableListener;
import com.tibco.cep.decision.table.language.DTLanguageUtil.COND_OPERATORS;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.core.utils.ModelUtils.DecisionTableUtils;
import com.tibco.decision.table.core.DecisionTableCorePlugin;

/**
 * Optimized representation of Decision Table 
 * @author vdhumal
 */
@SuppressWarnings({"rawtypes" , "unchecked"})
public class DecisionTable {
	
	
	private static final String CLASS = DecisionTableCorePlugin.class.getName();
	
	/**
	 * Map of all rule ids to RuleTupleInfo
	 */
    private Map<String, RuleTupleInfo> idToRuleTuples = new HashMap<String, RuleTupleInfo>();
    
    /**
     * Column name to all conditions in that column
     */
    private HashMap<String, LinkedList<Cell>> conditionMatrix = new HashMap<String, LinkedList<Cell>>(); //Every cell [alias is id] it is M cols (key) x N Rows. The matrix is reduced in the optimize phase
    
    private LinkedHashMap<String, List<Filter>> optimizedMatrix = new LinkedHashMap<String, List<Filter>>();
    
    private String projectName;
    
    private IConstraintsTableListener constraintsTableListener;
    
    private HashMap<String, Cell.CellInfo> cellInfoTable = new HashMap<String, Cell.CellInfo>();

    public void addRuleTupleInfo(RuleTupleInfo ri) {
    	idToRuleTuples.put(ri.id, ri);
    }
    
    public void removeRuleTupleInfo(RuleTupleInfo ri) {
    	idToRuleTuples.remove(ri.id);
    }
    
    public RuleTupleInfo getRuleTupleInfo(String id) {
    	return idToRuleTuples.get(id);
    }

    public void addCell(Cell c) {
    	DecisionTableCorePlugin.debug(CLASS, "Adding Cell {0}", c);
        if (c != null) {
            if (c.getCellInfo() != null && c.getCellInfo().getAlias() != null) {
                LinkedList<Cell> cells = conditionMatrix.get(c.getCellInfo().getAlias());
                if (cells == null) {
                    cells = new LinkedList<Cell>();
                    conditionMatrix.put(c.getCellInfo().getAlias(), cells);
                }
                if (!cells.contains(c)) {
                	cells.add(c);
                	fireCellAdded(c);
                }
            }
        }
    }
    
    public void addCellAtDTCreation(Cell c) {
    	if (c != null) {
            if (c.getCellInfo() != null && c.getCellInfo().getAlias() != null) {
                LinkedList<Cell> cells = conditionMatrix.get(c.getCellInfo().getAlias() );
                if (cells == null) {
                    cells = new LinkedList<Cell>();
                    conditionMatrix.put(c.getCellInfo().getAlias() , cells);
                }
                
                cells.add(c);
                fireCellAdded(c);
            }
        }
    }
    
    public Cell.CellInfo getCellInfo(String alias) {
    	return cellInfoTable.get(alias);
    }
    
     /**
     * Set enable flag of {@link Cell} to true/false
     * @param columnAlias column name
     * @param tableRuleVariable the cell
     * @param colType column type
     */
    public void toggleCell(String columnAlias, 
    		               TableRuleVariable tableRuleVariable, 
    		               ColumnType colType) {
    	if (colType == ColumnType.CONDITION) {
    		toggleConditionCell(columnAlias, tableRuleVariable);
    	} else if (colType == ColumnType.ACTION) { // process RuleTupleInfo
    		toggleActionCell(columnAlias, tableRuleVariable);
    	}
    }
    
    /**
     * Set the enable flag of {@link Cell} of type {@link ColumnType#CONDITION} to true
     * @param columnAlias column name
     * @param tableRuleVariable the cell
     * @param enabled -> If true, enable cell else disable
     */
    private void toggleConditionCell(String columnAlias,
    		                         TableRuleVariable tableRuleVariable) {
    	String cellId = tableRuleVariable.getId();
    	List<Cell> cells = Collections.emptyList();
    	cells = conditionMatrix.get(columnAlias);
    	if (cells != null && cells.isEmpty())
    		return;
    	Cell refCell = null;
    	for (Cell cell : cells) {
    		if (cell.getId().equals(cellId)) {
    			cell.setEnabled(tableRuleVariable.isEnabled());
    			refCell = cell;
    		}
    	}
    	
    	ConstraintsTableEvent event = 
    		new ConstraintsTableEvent(refCell, this, ConstraintsTableEventType.CELL_PROPERTY_UPDATE);
    	constraintsTableListener.cellUpdated(event);
    }
    
    /**
     * Set the enable flag of {@link Cell} of type {@link ColumnType#ACTION} to true
     * @param alias column name
     * @param tableRuleVariable the cell
     */
    private void toggleActionCell(String alias,
    		                      TableRuleVariable tableRuleVariable) {
    	String trvId = tableRuleVariable.getId();
		String ruleId = DecisionTableUtils.getContainingRuleId(trvId);
		List<Cell> cells = Collections.emptyList();
		for (List<Cell> aliasCells : conditionMatrix.values()) {
			if (!aliasCells.isEmpty()) {
				for (Cell cell : aliasCells) {
					String condRuleId = DecisionTableUtils.getContainingRuleId(cell.getId());
					if (condRuleId.equals(ruleId)) {
						cells = cell.getRuleTupleInfo().getActions();
					}
				}
			}
		}
		if (false == cells.isEmpty()) {
			for (Cell action : cells) {
				if (action.getId().equals(trvId)) {
					action.setEnabled(tableRuleVariable.isEnabled());
				}
			}
		}
    }
       
    /**
     * Update an existing {@link Cell}'s value with the value
     * specified by expression in the {@link TableRuleVariable}
     * <p>
     * This will work only for existing cells
     * </p>
     * @param alias
     * @param tableRuleVariable
     * @param columnType
     */
    public void updateCell(String alias, 
    		               TableRuleVariable tableRuleVariable, 
    		               ColumnType columnType) {
    	String cellId = tableRuleVariable.getId();
    	String expression = tableRuleVariable.getExpr();
    	DecisionTableCorePlugin.debug(CLASS, "Caling Update Cell for Cell Id {0} and expression {1}", cellId, expression);
        if (alias != null && cellId != null) {
        	switch (columnType) {
        	case CONDITION :
        	case CUSTOM_CONDITION :
        		List<Cell> conditionCells = conditionMatrix.get(alias);
                if (conditionCells == null) {
                	//This means that there was no value in the cell before.
                	byte cellType = 
                		(columnType == ColumnType.CONDITION || 
                				columnType == ColumnType.CUSTOM_CONDITION) ? Cell.CONDITION_CELL_TYPE
                				: Cell.ACTION_CELL_TYPE;
                	
            		StringTokenizer tokens = new StringTokenizer(expression, COND_OPERATORS.OR.opString());
                	while (tokens.hasMoreTokens()) {
	                	Cell newCell = new Cell(cellType);
	                	newCell.setBody(tokens.nextToken().trim());
	                	newCell.setEnabled(tableRuleVariable.isEnabled());
	            		addCell(newCell);
                	}
                	
                } else if (!expression.trim().equals("")) {
                	internalUpdate(conditionCells, expression, cellId);
                } else { //empty cell expr, so remove existing cells
                	List<Cell> cellsToRemove = new ArrayList<Cell>();
                	// update filters
                	for (Cell c : conditionCells) {
                		if (c.id.intern() == cellId.intern()) {
                			cellsToRemove.add(c);
                		}
                	}
                	for (Cell c : cellsToRemove) {
                		removeCell(c);
                	}
                	// update condition matrix
                	cellsToRemove.clear();
                }
                break;
        	case ACTION :
        	case CUSTOM_ACTION :
        		//Get the containing rule id which will be id in ruletupleinfo
        		String ruleId = ModelUtils.DecisionTableUtils.getContainingRuleId(cellId);
        		//Search for this in the hashmap
        		if (ruleId != null) {
        			RuleTupleInfo ruleTupleInfo = idToRuleTuples.get(ruleId);
        			if (ruleTupleInfo != null) {
	    				//Actions in the rule with this id
	    				List<Cell> actionCells = ruleTupleInfo.getActions();
	    				
	    				List<Cell> cellsToRemove = new ArrayList<Cell>();
	    				for (Cell c : actionCells) {
	    					if (c.id.intern().equals(cellId.intern())) {
	    						cellsToRemove.add(c);
	    					}
	    				}
	    				
	    				Cell refCell = cellsToRemove.get(0);	//reference cell 
	    				ruleTupleInfo.removeAllActions(cellsToRemove);
	    				
	    				// add new cells for the same id
	    		    	StringTokenizer tokens = new StringTokenizer(expression, COND_OPERATORS.OR.opString());
	    		    	
	    		    	while (tokens.hasMoreTokens()) {
	    		    		String expr = tokens.nextToken().trim();
	    		    		if (expr.equals("")) continue;
	    		    		Cell newCell = new Cell(refCell.getCellType());
	    		    		newCell.setRuleTupleInfo(refCell.getRuleTupleInfo());
	    		    		newCell.setCellInfo(refCell.getCellInfo());
	    		    		newCell.setColumnId(refCell.getColumnId());
	    		    		newCell.setId(refCell.getId());
	    		    		newCell.setBody(expr);
	    		    		newCell.setAlias(refCell.getAlias());
	    		    		newCell.setEnabled(tableRuleVariable.isEnabled());
	    		    		ruleTupleInfo.addAction(newCell);
	    		    	}
        			}
        		}
        	}
        }
    }
    
    /**
     * @param cells
     * @param updatedExpression
     * @param cellIdToUpdate
     */
    private void internalUpdate(List<Cell> cells, 
    		                    String updatedExpression, 
    		                    String cellIdToUpdate) {
    	// get the existing cells for the rule id
    	List<Cell> oldCells = new ArrayList<Cell>();
    	for (Cell cell : cells) {
        	if (cell.getId().intern() == cellIdToUpdate.intern()) {
        		oldCells.add(cell);
        	}
        }
    	
    	//reference cell 
    	Cell refCell = oldCells.get(0);
    	
    	// create new cells for the same id
    	StringTokenizer tokens = new StringTokenizer(updatedExpression, COND_OPERATORS.OR.opString());
    	List<Cell> updatedCells = new ArrayList<Cell>();
    	while (tokens.hasMoreTokens()) {
    		
    		String expr = tokens.nextToken().trim();
    		if (expr.equals("")) {
    			continue;
    		}
    		Cell newCell = new Cell(refCell.getCellType());
    		newCell.setRuleTupleInfo(refCell.getRuleTupleInfo());
    		newCell.setCellInfo(refCell.getCellInfo());
    		newCell.setColumnId(refCell.getColumnId());
    		newCell.setId(refCell.getId());
    		newCell.setBody(expr);
    		newCell.setAlias(refCell.getAlias());
    		newCell.setEnabled(refCell.isEnabled());
    		updatedCells.add(newCell);
    	}
    	fireCellUpdated(oldCells, updatedCells);
    }

    /**
     * Call this from {@link DecisionTableCreator}
     * @param cell
     */
    public void removeCell(Cell cell) {
    	 if (cell != null) {
			if (cell.getCellInfo() != null && cell.getCellInfo().getAlias() != null) {
				byte cellType = cell.getCellType();
				switch (cellType) {
				case 0:
					List<Cell> cells = conditionMatrix.get(cell.getCellInfo().getType());
					if (cells != null) {
						cells.remove(cell);
						fireCellRemoved(cell);
					}
					break;
				case 1:
					// Get the containing rule id which will be id in
					// ruletupleinfo
					String ruleId = ModelUtils.DecisionTableUtils.getContainingRuleId(cell.id);
					// Search for this in the hashmap
					if (ruleId != null) {
						RuleTupleInfo ruleTupleInfo = idToRuleTuples.get(ruleId);
						if (ruleTupleInfo != null) {
							ruleTupleInfo.removeAction(cell);
							fireCellRemoved(cell);
						}
					}
					break;
				}

			}
		}
    }
    
    /**
     * Call this from {@link DecisionTableCreator}
     * @param alias
     */
    public void removeAlias(String alias) {
    	if (alias != null && !alias.isEmpty()) {
    		//Update the DT's optimized and condition matrix
    		conditionMatrix.remove(alias);
    		optimizedMatrix.remove(alias);
    		fireAliasRemoved(alias);
    	}
    }
    
    /**
     * Call this from {@link DecisionTableCreator}
     * @param oldAlias
     * @param newAlias
     */
    public void renameAlias(ColumnNameStateMemento columnNameStateMemento) {
    	String oldAlias = (String)columnNameStateMemento.getValue();
    	String newAlias = columnNameStateMemento.getNewColumnName();
    	if (oldAlias != null && !oldAlias.isEmpty()) {
    		//Update the DT's optimized and condition matrix
    		LinkedList<Cell> values = conditionMatrix.get(oldAlias);
    		//There may not be any values at all
    		if (values != null && !values.isEmpty()) {
    			conditionMatrix.put(newAlias, values);
    		}
    		//Remove from optimized matrix
    		List<Filter> filters = optimizedMatrix.get(oldAlias);
    		//Similarly no filters may be present
    		if (filters != null && !filters.isEmpty()) {
    			optimizedMatrix.put(newAlias, filters);
    		}
    		
    		conditionMatrix.remove(oldAlias);
    		optimizedMatrix.remove(oldAlias);
    		
    		fireAliasRenamed(columnNameStateMemento);
    	}
    }
    
    private void fireCellAdded(Cell addedCell) {
    	ConstraintsTableEvent event = 
    		new ConstraintsTableEvent(addedCell, this, ConstraintsTableEventType.CELL_ADD);
    	if (constraintsTableListener != null) {
    		constraintsTableListener.cellAdded(event);
    	}
    }
    
    private void fireCellUpdated(List<Cell> oldCells, List<Cell> updatedCells) {
    	ConstraintsTableEvent event = 
    		new ConstraintsTableEvent(updatedCells, this, ConstraintsTableEventType.CELL_UPDATE);
    	event.setOldSourceObject(oldCells);

    	String oldCellAlias = oldCells.get(0).getAlias();
    	// update the condition matrix
    	if (conditionMatrix.containsKey(oldCellAlias)) {
    		if (conditionMatrix.get(oldCellAlias).containsAll(oldCells)) {
    			conditionMatrix.get(oldCellAlias).removeAll(oldCells);
    			conditionMatrix.get(oldCellAlias).addAll(updatedCells);
	    	}
    	}
    	if (constraintsTableListener != null) {
    		constraintsTableListener.cellUpdated(event);
    	}
    }
    
    private void fireCellRemoved(Cell removedCell) {
    	ConstraintsTableEvent event = 
    		new ConstraintsTableEvent(removedCell, this, ConstraintsTableEventType.CELL_REMOVE);
    	if (constraintsTableListener != null) {
    		constraintsTableListener.cellRemoved(event);
    	}
    }
    
    private void fireAliasRemoved(String alias) {
    	ConstraintsTableEvent event = 
    		new ConstraintsTableEvent(alias, this, ConstraintsTableEventType.ALIAS_REMOVE);
    	if (constraintsTableListener != null) {
    		constraintsTableListener.aliasRemoved(event);
    	}
    } 
    
    /**
     * Fire rename of column (alias) event.
     * @param columnNameStateMemento
     */
    private void fireAliasRenamed(ColumnNameStateMemento columnNameStateMemento) {
    	ConstraintsTableEvent event = 
    		new ConstraintsTableEvent(columnNameStateMemento, this, ConstraintsTableEventType.ALIAS_REMOVE);
    	if (constraintsTableListener != null) {
    		constraintsTableListener.aliasRenamed(event);
    	}
    } 
    
    public void addListener(IConstraintsTableListener listener) {
    	constraintsTableListener = listener;
    }

    /**
     * Optimize DT
     */
    public void optimize() {
    	Set<Map.Entry<String, LinkedList<Cell>>> entrySet = conditionMatrix.entrySet();
    	for (Map.Entry<String, LinkedList<Cell>> entry : entrySet) {
    		Filter[] filters = new Filter[]{null, new EqualsFilter(), new NotEqualsFilter(), new RangeFilter(), null, null};
    		List<Cell> lc = entry.getValue();

    		for (Cell c : lc) {
    			if (c.getExpression() == null) { // remove nulls
    				continue;
    			}
    			Filter filter = filters[c.getExpression().getOperatorKind()];
    			if (filter != null) { filter.addCell(c); }
    		}
    		optimizedMatrix.put(entry.getKey(), Arrays.asList(filters));
    	}
    }
    /**
     * Get the current constraint pane listener for the constraint table
     * @return
     */
    public IConstraintsTableListener getListener() {
    	return constraintsTableListener;
    }

    public void printStats() {
        int nosOfRules = idToRuleTuples.size();
        int nosOfConditions = 0;
        int [] cellTypes = new int[6];
        Set<Map.Entry<String, LinkedList<Cell>>> entrySet = conditionMatrix.entrySet();
        for (Map.Entry<String, LinkedList<Cell>> entry : entrySet) {
            List<Cell> lc = entry.getValue();
            nosOfConditions += lc.size();
            for (Cell c : lc) {
            	Expression expression = c.getExpression();
            	if (expression != null) {
            		++(cellTypes[expression.getOperatorKind()]);
            	}
            }
        }

        DecisionTableCorePlugin.debug("******** Non Optimized ***** ");
        DecisionTableCorePlugin.debug(CLASS, "#Rules, #Conditions = [{0}, {1}]",  nosOfRules , nosOfConditions );
        DecisionTableCorePlugin.debug(CLASS, "#Total Cell size = {0} MB",  ((nosOfConditions * 120)/1000000));
        for (int i = 0; i < cellTypes.length; i++) {
        	//LOGGER.logInfo("# Conditions[%1$s] = %2$s", Operator.OperatorTypes[i], cellTypes[i]);
        }

        nosOfConditions = 0;
        for (int i =0; i < cellTypes.length; i++) cellTypes[i] = 0;
        Set<Map.Entry<String, List<Filter>>> filterSet = optimizedMatrix.entrySet();
        for (Map.Entry<String, List<Filter>> entry : filterSet) {
            List<Filter> lf = entry.getValue();
            int cnt = 0;
            for (Filter f : lf ) {
                if (f != null) {
                    nosOfConditions += f.size();
                    cellTypes[cnt] += f.size();
                }
                ++cnt;
            }
        }

        DecisionTableCorePlugin.debug("******** Optimized ***** ");
        DecisionTableCorePlugin.debug(CLASS, "#Rules, #Conditions = [{0}, {1}]",  nosOfRules , nosOfConditions );
        for (int i = 0; i < cellTypes.length; i++) {
        	//LOGGER.logInfo("# Conditions[%1$s]=%2$s", Operator.OperatorTypes[i] , cellTypes[i]);
        }

    }

    public void optimize(SubMonitor subMonitor) {
    	long startTime = System.nanoTime();
    	Set<Map.Entry<String, LinkedList<Cell>>> entrySet = conditionMatrix.entrySet();
    	SubMonitor optimizationMonitor = 
    		subMonitor.newChild(25).setWorkRemaining(entrySet.size());
    	optimizationMonitor.setTaskName("Beginning Optimization");
    	for (Map.Entry<String, LinkedList<Cell>> entry : entrySet) {
    		Filter[] filters = new Filter[]{null, new EqualsFilter(), new NotEqualsFilter(), new RangeFilter(), null, null};
    		List<Cell> lc = entry.getValue();

    		for (Cell c : lc) {
    			if (c.getExpression() == null) { // remove nulls
    				continue;
    			}
    			Filter filter = filters[c.getExpression().getOperatorKind()];
    			if (filter != null) { filter.addCell(c); }
    		}
    		optimizedMatrix.put(entry.getKey(), Arrays.asList(filters));
    		optimizationMonitor.newChild(1);
    	}
    	long endTime = System.nanoTime();
    	DecisionTableCorePlugin.debug(CLASS, "Total time for optimization :{0}(ms)", (endTime - startTime)/1000000);

    }


    public void executeOptimized(HashMap input) throws Exception {

        HashSet resultantRules = evaluateConditionsOptimized(input);
        DecisionTableCorePlugin.debug(getClass().getName(), "#Nos of rules to be fired - {0}", resultantRules.size());
        DecisionTableCorePlugin.debug(getClass().getName(), "The following rules have to be fired : {0}" , resultantRules.toString() );
    }
 
    public Set<String> getColumns() {
        return optimizedMatrix.keySet();
    }
    
    public List<Filter> getAllFilters(String columnName) {
    	List<Filter> columnFilters = null;
    	Queue<Filter> columnOrderedQueue = new PriorityQueue<Filter>(1, new FilterComparator());
    	
        List<Filter> filters = optimizedMatrix.get(columnName);
        if (filters == null) {
        	columnFilters = new ArrayList<Filter>();
        	return columnFilters;
        }
        EqualityCombinationFilter combinationFilter = new EqualityCombinationFilter();
        boolean hasEquals = false;
        for (Filter filter : filters) {
            if (null == filter) {
            	continue;
            }
            if (filter.size() > 0) {
            	if (filter instanceof EqualsFilter) {
            		hasEquals = true;
            		combinationFilter.addFilter((EqualsFilter)filter);
            	} else {
            		columnOrderedQueue.offer(filter);
            	}
            }
        }
        if (hasEquals) {
        	columnOrderedQueue.offer(combinationFilter);
        }
        //List backed by a priority queue
        return columnFilters = new ArrayList<Filter>(columnOrderedQueue);
    }
    
    private class FilterComparator implements Comparator<Filter> {

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Filter filter1, Filter filter2) {
			if (filter1.getFilterPriority() > filter2.getFilterPriority()) {
				return 1;
			}
			if (filter1.getFilterPriority() == filter2.getFilterPriority()) {
				return 0;
			}
			if (filter1.getFilterPriority() < filter2.getFilterPriority()) {
				return -1;
			}
			return Integer.MIN_VALUE;
		}
    }
    
    public List<Filter> getAllFilters(Cell cell) {
    	String columnName = cell.getAlias();
    	List<Filter> filters = optimizedMatrix.get(columnName);
//    	List<Filter> filters = getAllFilters(columnName);
    	
    	if (filters == null) {
        	Filter[] filterArray = 
        		new Filter[]{null, new EqualsFilter(), new NotEqualsFilter(), new RangeFilter(), null, null};
        	Filter filter = filterArray[cell.getExpression().getOperatorKind()];
            if (filter != null) { 
            	filter.addCell(cell); 
            }
        	optimizedMatrix.put(columnName, Arrays.asList(filterArray));
        	filters = optimizedMatrix.get(columnName);
        }
    	
    	Queue<Filter> columnOrderedQueue = new PriorityQueue<Filter>(1, new FilterComparator());
        EqualityCombinationFilter combinationFilter = new EqualityCombinationFilter();
        boolean hasEquals = false;
        for (Filter filter : filters) {
            if (null == filter) {
            	continue;
            }
            
            filter.addCell(cell);
            if (filter.size() > 0) {
            	if (filter instanceof EqualsFilter) {
            		hasEquals = true;
            		combinationFilter.addFilter((EqualsFilter)filter);
            	} else {
            		columnOrderedQueue.offer(filter);
            	}
            }
        }
        if (hasEquals) {
        	columnOrderedQueue.offer(combinationFilter);
        } 	
    	
    	return new ArrayList<Filter>(columnOrderedQueue);
    }

    public Filter getFilter(String columnName) {
        List<Filter> filters = optimizedMatrix.get(columnName);
        for (Filter f : filters) {
            if (null == f) continue;
            if (f.size() > 0) return f;
        }
        return null;
    }

    public HashSet evaluateConditionsOptimized(HashMap input) {
        HashSet resultantRules = new HashSet();
        Set<Map.Entry<String, List<Filter>>> filterSet = optimizedMatrix.entrySet();
        int cnt = 0;
        long[] times = new long[filterSet.size()];
        String[] columns = new String[filterSet.size()];
        int[] sizes = new int[filterSet.size()];
        for (Map.Entry<String, List<Filter>> entry : filterSet) {
            List<Filter> lf = entry.getValue();

            Object value = input.get(entry.getKey());
            long stTime = System.nanoTime();
            HashSet columnSet = new HashSet();
            for (Filter f : lf ) {
                if (f != null) {
                    f.eval(value, columnSet);
                }
            }

            sizes[cnt] = columnSet.size();
            if (cnt == 0) {

                resultantRules.addAll(columnSet);

            }
            else {
                resultantRules.retainAll(columnSet);
            }

            times[cnt] = (System.nanoTime() - stTime)/1000000;
            columns[cnt]  = entry.getKey();
            ++cnt;

        }

        long totalTime = 0;
        for (int i=0; i< times.length; i++) {
            totalTime += times[i];
            System.out.printf("Evaluating Column[%s]= %d, Matched[#rules] = %d", columns[i], times[i], sizes[i]).println();
        }
        System.out.printf("Total Evaluation time takes %d", totalTime).println();

        return resultantRules;
    }

	/**
	 * @return the projectName
	 */
	public final String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public final void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	/** 
	 * Get the list cells added to the EqualsFilter for the given column and expression, in the decision table. 
	 * If no cells are found, an empty list is returned.
	 * @param columnName The column for which the cells are fetched
	 * @param expr The expression whose matching cells are fetched
	 * @return list of cells
	 * @see EqualsFilter
	 */
	public List<Cell> getEqualsFilterCells(String columnName, String expr) {
				
		List<Cell> cells = null;
		List<Filter> filters = optimizedMatrix.get(columnName);
		if (filters != null) {
			for (Filter f : filters) {
				if (f instanceof EqualsFilter) {
					EqualsFilter filter = (EqualsFilter)f;
					if (filter.getKeySet().contains(expr)) {
						cells = new ArrayList<Cell>(filter.getMap().get(expr));
					}
				}
			}
		}
		
		if (cells == null) {
			cells = new ArrayList<Cell>();
		}
		return cells;
	}
	
	/**
	 * Get the list of cells added to the RangeFilter for the given column and expression, in the decision table.
	 * If no cells are found, an empty list is returned
	 * @param columnName The column for which the cells are fetched
	 * @param expr The expression whose matching cells are fetched
	 * @return list of cells
	 * @see RangeFilter
	 */
	public List<Cell> getRangeFilterCells(String columnName, String expr) {
				
		List<Cell> cells = null;
		List<Filter> filters = optimizedMatrix.get(columnName);
		if (filters != null) {
			for (Filter f : filters) {
				if (f instanceof RangeFilter) {
					RangeFilter filter = (RangeFilter)f;
					if (filter.getRangeMap().containsKey(expr)) {
						cells = new ArrayList<Cell>(filter.getRangeMap().get(expr));
					}
				}
			}
		}
		
		if (cells == null) {
			cells = new ArrayList<Cell>();
		}
		return cells;
	}
	
	/**
	 * Get the Equals filter for a given column of the decision table
	 * @param columnName The column for which the equals filter is fetched
	 * @return An equals filter object, if one exists, null otherwise
	 * @see EqualsFilter
	 */
	public EqualsFilter getEqualsFilter(String columnName) {
		
		EqualsFilter filter = null;
		List<Filter> filters = optimizedMatrix.get(columnName);
		
		if (filters != null) {
			for (Filter f : filters) {
				if (f != null && f.getClass() == EqualsFilter.class) {
					filter = (EqualsFilter)f;
					break;
				}
			}
		}
		
		return filter;
	}
	
	/**
	 * Get the Range filter for a given column of the decision table
	 * @param columnName The column for which the range filter is fetched
	 * @return A range filter object, if one exists, null otherwise
	 * @see RangeFilter
	 */
	public RangeFilter getRangeFilter(String columnName) {
		
		RangeFilter filter = null;
		List<Filter> filters = optimizedMatrix.get(columnName);
		
		if (filters != null) {
			for (Filter f : filters) {
				if (f instanceof RangeFilter) {
					filter = (RangeFilter)f;
					break;
				}
			}
		}
		
		return filter;
	}
	
	/**
	 * Clear the decision table structures
	 */
	public void clear() {
		cellInfoTable.clear();
	
		Set<String> conditionKeys = conditionMatrix.keySet();
		for (String alias : conditionKeys) {
			List<Cell> cells = conditionMatrix.get(alias);
			if (cells != null) { cells.clear(); }
		}
		conditionMatrix.clear();
		
		Set<String> optimizedKeys = optimizedMatrix.keySet();
		for (String alias : optimizedKeys) {
			List<Filter> filters = optimizedMatrix.get(alias);
			//There may not be any filters
			if (filters != null) {
				for (Filter filter : filters) {
					if (filter != null) {
						filter.clearMap();
					}
				}
			}
		}
		optimizedMatrix.clear();
		
		idToRuleTuples.clear();
		projectName = null;
		constraintsTableListener = null;
	}

	/**
	 * This method is used to get all cells for given column name.
	 * 
	 * @param columnName
	 *            - Name of the column for
	 * @return List of LinkedHashSet of Cell for given column name.
	 */
	public List<LinkedHashSet<Cell>> getCells(String columnName) {
		List<Cell> cells = conditionMatrix.get(columnName);
		List<LinkedHashSet<Cell>> cellList = new ArrayList<LinkedHashSet<Cell>>();
		for (Cell cell : cells) {
			LinkedHashSet<Cell> hashSet = new LinkedHashSet<Cell>();
			hashSet.add(cell);
			cellList.add(hashSet);
		}
		return cellList;
	}
	public Cell.CellInfo putCellInfo(String alias,Cell.CellInfo cellInfo) {
    	return cellInfoTable.put(alias,cellInfo);
    }
}
