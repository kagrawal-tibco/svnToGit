package com.tibco.cep.decision.table.constraintpane;

import java.util.HashMap;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.decision.table.command.memento.ColumnNameStateMemento;
import com.tibco.cep.decision.table.language.DTLanguageUtil.COND_OPERATORS;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.core.utils.ModelUtils.DecisionTableUtils;

public class DecisionTableCreator {

    private HashMap<String, Column> fColMap = new HashMap<String, Column>();
    
    private static final String CLASS = DecisionTableCreator.class.getName();
    
	private Table fTable;

	public DecisionTableCreator(Table table) {
		this.fTable = table;
	}
	
	public DecisionTable createDecisionTable(SubMonitor mainProcesingMonitor) {
		
		mainProcesingMonitor.setTaskName("Creating Table Analyzer Structure");
		
        long t1 = System.nanoTime();

        TableRuleSet decisionTable = fTable.getDecisionTable();
        DecisionTable dt = new DecisionTable();
		Columns columns = decisionTable.getColumns();
		
		if (columns != null) {
			EList<com.tibco.cep.decision.table.model.dtmodel.Column> columnList = columns.getColumn();
			fColMap.clear();
			SubMonitor columnProcessingMonitor = 
				mainProcesingMonitor.newChild(5).setWorkRemaining(columnList.size());
			mainProcesingMonitor.beginTask("Processing Columns in Decision Table", 5);
			for (com.tibco.cep.decision.table.model.dtmodel.Column column : columnList) {
				processColumn(column);
				columnProcessingMonitor.newChild(1);
			}
			
			EList<TableRule> rules = decisionTable.getRule();
			
			SubMonitor ruleProcessingMonitor = 
				mainProcesingMonitor.newChild(70).setWorkRemaining(rules.size());
			ruleProcessingMonitor.setTaskName("Processing Rows in Decision Table");
			int counter = 0;
			for (TableRule tableRule : rules) {
				mainProcesingMonitor.setTaskName("Processing Rule With Id " + tableRule.getId());
				processRule(dt, tableRule, true);
				counter = counter++;
				ruleProcessingMonitor.newChild(1);
			}
	        long t2 = System.nanoTime();
	        DecisionTableUIPlugin.debug(CLASS, "Time required to create from Table = {0} ms", ((t2 - t1)/1000000));
		}
		return dt;
	}

	private void processColumn(com.tibco.cep.decision.table.model.dtmodel.Column column) {
		ColumnType columnType = column.getColumnType();
		//Do not add custom conditions to the constraints editor
		if (columnType == ColumnType.CUSTOM_CONDITION) {
			return;
		}
		
		Column c = new Column();
		c.columnType = Column.ColumnType.get(columnType.getLiteral());
		
		c.name = column.getName();
		c.propertyType = column.getPropertyType();
		DecisionTableUIPlugin.debug(CLASS, "Processing column with name {0}", column.getName());
		c.propertyPath = column.getPropertyPath();
		fColMap.put(column.getId(), c);
	}
	
	/**
	 * Called upon column rename so that the corresponding constraints
	 * table structure and hence UI for the collapsible panes get updated.
	 * @param constraintsTable
	 * @param columnNameStateMemento
	 */
	public void renameColumn(DecisionTable constraintsTable, ColumnNameStateMemento columnNameStateMemento) {
		processAllColumns();
		constraintsTable.renameAlias(columnNameStateMemento);
	}
	
	/**
	 * Toggle enabled status of the constraints table {@link Cell} based
	 * on the enabled status of the concerned {@link TableRuleVariable}
	 * @param constraintsTable
	 * @param tableRuleVariable
	 */
	public void toggleCellEnableStatus(DecisionTable constraintsTable,
			                           TableRuleVariable tableRuleVariable) {
		String columnId = tableRuleVariable.getColId();
		//Get column type
		Columns columnsModel = fTable.getDecisionTable().getColumns();
		com.tibco.cep.decision.table.model.dtmodel.Column concernedColumn = columnsModel.search(columnId);
		String columnAlias = concernedColumn.getName();
		constraintsTable.toggleCell(columnAlias, tableRuleVariable, concernedColumn.getColumnType());
	}
	
	public void processNewRule(DecisionTable dt, TableRule tableRule) {
		fColMap.clear();
		processAllColumns();
		processRule(dt, tableRule, false);
	}

	private void processRule(DecisionTable dt, TableRule tableRule, boolean createTable) {
		RuleTupleInfo ri = null;//
		//Search if there is a Rule Tuple with this id
		String ruleID = tableRule.getId();
		ri = dt.getRuleTupleInfo(ruleID);
		if (ri != null) {
			//This rule has been processed
			return;
		}
		ri = new RuleTupleInfo(dt);
		ri.id = tableRule.getId();
		EList<TableRuleVariable> action = tableRule.getAction();
		for (TableRuleVariable tableRuleVariable : action) {
			Cell cell = new Cell(Cell.ACTION_CELL_TYPE);
			cell.ri = ri;
			cell.setBody((tableRuleVariable.getExpr()==null)?"":tableRuleVariable.getExpr());
			cell.setId(tableRuleVariable.getId());
			cell.columnId = tableRuleVariable.getColId();
			cell.enabled = tableRuleVariable.isEnabled();
			Column column = fColMap.get(tableRuleVariable.getColId());
			if (column != null) {
				//Will be null for custom conditions/actions
				cell.setAlias(column.name);
				cell.ci.path = column.propertyPath;
				cell.setType(column.propertyType);
			}
			
			cell.ri.addAction(cell);
		}
		EList<TableRuleVariable> condition = tableRule.getCondition();
		
		for (TableRuleVariable tableRuleVariable : condition) {
			String condString = tableRuleVariable.getExpr();
			
			if (condString != null && condString.length() > 0) {
				StringTokenizer tokens = new StringTokenizer(condString, COND_OPERATORS.OR.opString());
				
				while (tokens.hasMoreTokens()) {
					Cell cell = new Cell(Cell.CONDITION_CELL_TYPE);
					Column column = fColMap.get(tableRuleVariable.getColId());
					cell.ri = ri;
					String condToken = tokens.nextToken().trim();
					
					if (condToken.equals("")) continue;
					
					if (condToken.length() == 2 && condToken.equals("\"\"")) {
						// empty strings can be treated like blank conditions
					} else {
//						if (DONT_CARE == condToken.intern()) {
//							continue;
//						}
						cell.setBody(condToken);
					}
					cell.setId(tableRuleVariable.getId());
					cell.columnId = tableRuleVariable.getColId();
					cell.enabled = tableRuleVariable.isEnabled();
					
					if (column != null) {
						cell.setAlias(column.name);
						cell.ci.path = column.propertyPath;
						cell.setType(column.propertyType);
					}
					// Add any cell only when its not empty
					if (createTable == false) { 
						dt.addCell(cell); 
					} else {
						dt.addCellAtDTCreation(cell);
					}
				}
			}
		}
		
		dt.addRuleTupleInfo(ri);
	}
	
	private void processAllColumns() {
		TableRuleSet decisionTable = fTable.getDecisionTable();
		Columns columns = decisionTable.getColumns();
		
		if (columns != null) {
			EList<com.tibco.cep.decision.table.model.dtmodel.Column> columnList = columns.getColumn();
			for (com.tibco.cep.decision.table.model.dtmodel.Column column : columnList) {
				processColumn(column);
			}
		}
	}
	
	/**
	 * Used for creating new cell entry in an existing rule
	 * @param dt
	 * @param tableRule
	 * @param tableRuleVariable
	 */
	public void processExistingRule(DecisionTable dt, 
			                        TableRule tableRule,
			                        TableRuleVariable tableRuleVariable) {
		
		fColMap.clear();
		processAllColumns();
		String ruleID = tableRule.getId();
		//Search for a rule tuple with this id
		RuleTupleInfo ri = dt.getRuleTupleInfo(ruleID);
		if (ri == null) {
			ri = new RuleTupleInfo(dt);
			ri.id = ruleID;
			dt.addRuleTupleInfo(ri);
		}
		EList<TableRuleVariable> actions = tableRule.getAction();
		for (TableRuleVariable action : actions) {
			Cell cell = new Cell(Cell.ACTION_CELL_TYPE);
			cell.ri = ri;
			cell.setBody(action.getExpr());
			cell.setId(action.getId());
			cell.columnId = action.getColId();
			Column column = fColMap.get(action.getColId());
			cell.setAlias(column.name);
			cell.setEnabled(tableRuleVariable.isEnabled());
			cell.ci.path = column.propertyPath;
			cell.setType(column.propertyType);
			cell.ri.addAction(cell);
		}
		
		//Check if TRV is action type
		if (!isCondition(tableRuleVariable, tableRule)) {
			//Do not process action cell as it would have been processed above
			return;
		}
		
		
		String condString = tableRuleVariable.getExpr();
		
		if (condString != null && condString.length() > 0) {
			
			StringTokenizer tokens = new StringTokenizer(condString, COND_OPERATORS.OR.opString());
			while (tokens.hasMoreTokens()) {
				
				String condToken = tokens.nextToken().trim();
				
				if (condToken.equals(""))
					continue;
				
				Cell cell = new Cell(Cell.CONDITION_CELL_TYPE);
				cell.ri = ri;
			
				if (condToken.length() == 2 && condToken.equals("\"\"")) {
					// empty strings can be treated like blank conditions
				} else {
//					if (DONT_CARE == condToken.intern()) {
//					return;
//					}
					
					cell.setBody(condToken);
				}
			
				cell.setId(tableRuleVariable.getId());
				cell.columnId = tableRuleVariable.getColId();
				cell.setEnabled(tableRuleVariable.isEnabled());
				Column column = fColMap.get(tableRuleVariable.getColId());
				if (column != null) {
					cell.setAlias(column.name);
					cell.ci.path = column.propertyPath;
					cell.setType(column.propertyType);
				}
				// Add any cell only when its not empty
				dt.addCell(cell);
			}
		}
	}
	
	boolean isCondition(TableRuleVariable trv, TableRule tableRule) {
		for (TableRuleVariable condition : tableRule.getCondition()) {
			if (trv.getId().equals(condition.getId())) {
				return true;
			}
		}
		return false;
	}
	
	public void removeExistingRule(DecisionTable dt, 
			                       TableRule tableRule) {
		processAllColumns();
		String ruleID = tableRule.getId();
		if (ruleID == null) {
			return;
		}
		//Search for a rule tuple with this id
		RuleTupleInfo ri = dt.getRuleTupleInfo(ruleID);

		if (null != ri) {
			//Remove condition cells
			for (TableRuleVariable condition : tableRule.getCondition()) {
				
				// skip empty cells
				if (condition.getExpr().trim().isEmpty())
					continue;
				Column column = fColMap.get(condition.getColId());
				
				StringTokenizer tokens = new StringTokenizer(condition.getExpr(), COND_OPERATORS.OR.opString());
				
				while (tokens.hasMoreTokens()) {
					String expr = tokens.nextToken().trim();
					
					if (expr.equals(""))
						continue;
					
					Cell cell = new Cell(Cell.CONDITION_CELL_TYPE);
					cell.id = condition.getId();
					cell.columnId = condition.getColId();
					cell.setBody(expr);
					cell.ri = ri;
					if (column != null) {
						cell.setAlias(column.name);
						dt.removeCell(cell);
					}
				}
			}
			//Remove action cells
			for (TableRuleVariable action : tableRule.getAction()) {
				
				// skip empty cells
				if (action.getExpr().trim().isEmpty())
					continue;
				Cell cell = new Cell(Cell.ACTION_CELL_TYPE);
				Column column = fColMap.get(action.getColId());
				cell.id = action.getId();
				cell.columnId = action.getColId();
				cell.setBody(action.getExpr());
				cell.ri = ri;
				if (column != null) {
					cell.setAlias(column.name);
					dt.removeCell(cell);
				}
			}
			dt.removeRuleTupleInfo(ri);
		}
	}
	
	public void removeExistingColumn(DecisionTable dt, com.tibco.cep.decision.table.model.dtmodel.Column column)	{
		
		// Only for CONDITION columns
		if (column.getColumnType() == ColumnType.CONDITION) {
			//Add all the columns to the fColMap the constraint editor  
			processAllColumns();
			dt.removeAlias(column.getName());
		}
	}
	
	public void removeExistingRuleVariable(DecisionTable dt, 
			TableRuleVariable trv)	{
		//Add all the columns to the fColMap the constraint editor  
		processAllColumns();
		
		String ruleID = DecisionTableUtils.getContainingRuleId(trv.getId());
		
		if (ruleID == null) {
			return;
		}
		//Search for a rule tuple with this id
		RuleTupleInfo ri = dt.getRuleTupleInfo(ruleID);

		Column column = fColMap.get(trv.getColId());
		
		StringTokenizer tokens = new StringTokenizer(trv.getExpr(), COND_OPERATORS.OR.opString());
		
		while (tokens.hasMoreTokens()) {
			String expr = tokens.nextToken().trim();
			
			if (expr.equals(""))
				continue;
			
			Cell cell = new Cell(Cell.CONDITION_CELL_TYPE);
			cell.id = trv.getId();
			cell.columnId = trv.getColId();
			cell.setBody(expr);
			cell.ri = ri;
			if (column != null) {
				cell.setAlias(column.name);
				dt.removeCell(cell);
			}
		}
	}
}
