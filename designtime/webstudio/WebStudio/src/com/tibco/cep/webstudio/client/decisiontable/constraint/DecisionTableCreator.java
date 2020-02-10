package com.tibco.cep.webstudio.client.decisiontable.constraint;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.getColumn;

import java.util.HashMap;
import java.util.List;

import com.tibco.cep.webstudio.client.decisiontable.constraint.DecisionConstraintUtils.COND_OPERATORS;
import com.tibco.cep.webstudio.client.decisiontable.model.ColumnType;
import com.tibco.cep.webstudio.client.decisiontable.model.Table;
import com.tibco.cep.webstudio.client.decisiontable.model.TableColumn;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRule;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRuleSet;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRuleVariable;
import com.tibco.cep.webstudio.client.logging.WebStudioClientLogger;
public class DecisionTableCreator {

    private HashMap<String, Column> fColMap = new HashMap<String, Column>();
    
	private Table fTable;
	
	private WebStudioClientLogger logger = WebStudioClientLogger.getLogger(DecisionTableCreator.class.getName());

	public DecisionTableCreator(Table table) {
		this.fTable = table;
	}
	
	public DecisionTable createDecisionTable() {
        TableRuleSet decisionTable = fTable.getDecisionTable();
        DecisionTable dt = new DecisionTable();
		List<TableColumn> columnList = decisionTable.getColumns();
		
		if (columnList != null  && columnList.size() > 0) {
			fColMap.clear();
	
			for (TableColumn column : columnList) {
				processColumn(column);
			}
			
			List<TableRule> rules = decisionTable.getTableRules();
			if (rules != null && rules.size() > 0) {
				int counter = 0;
				for (TableRule tableRule : rules) {
					processRule(dt, tableRule, true);
					counter = counter++;
				}
			}
		}
		return dt;
	}

	private void processColumn(TableColumn column) {
		String columnTypestr = column.getColumnType();
		ColumnType columnType = ColumnType.get(columnTypestr);
		//Do not add custom conditions to the constraints editor
		if (columnType == ColumnType.CUSTOM_CONDITION) {
			return;
		}
		
		Column c = new Column();
		c.columnType = Column.ColumnType.get(columnType.getLiteral());
		
		c.name = column.getName();
		
		logger.debug("Processing column with name " + column.getName());
		c.propertyPath = column.getPropertyPath();
		fColMap.put(column.getId(), c);
	}
	
	/**
	 * Called upon column rename so that the corresponding constraints
	 * table structure and hence UI for the collapsible panes get updated.
	 * @param constraintsTable
	 * @param columnNameStateMemento
	 */
//	public void renameColumn(DecisionTable constraintsTable, ColumnNameStateMemento columnNameStateMemento) {
//		processAllColumns();
//		constraintsTable.renameAlias(columnNameStateMemento);
//	}
	
	/**
	 * Toggle enabled status of the constraints table {@link Cell} based
	 * on the enabled status of the concerned {@link TableRuleVariable}
	 * @param constraintsTable
	 * @param tableRuleVariable
	 */
	public void toggleCellEnableStatus(DecisionTable constraintsTable,
			                           TableRuleVariable tableRuleVariable) {
		String columnId = tableRuleVariable.getColumnId();
		//Get column type
		List<TableColumn> columnsModel = fTable.getDecisionTable().getColumns();
		TableColumn concernedColumn = getColumn(columnsModel, columnId);
		String columnAlias = concernedColumn.getName();
		ColumnType columnType = ColumnType.get(concernedColumn.getColumnType());
		constraintsTable.toggleCell(columnAlias, tableRuleVariable, columnType);
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
		ri.setId(tableRule.getId());
		List<TableRuleVariable> action = tableRule.getActions();
		for (TableRuleVariable tableRuleVariable : action) {
			Cell cell = new Cell(Cell.ACTION_CELL_TYPE);
			cell.setRuleTupleInfo(ri);
			cell.setBody((tableRuleVariable.getExpression()==null)?"":tableRuleVariable.getExpression());
			cell.setId(tableRuleVariable.getId());
			cell.setColumnId(tableRuleVariable.getColumnId());
			cell.setEnabled(tableRuleVariable.isEnabled());
			Column column = fColMap.get(tableRuleVariable.getColumnId());
			if (column != null) {
				//Will be null for custom conditions/actions
				cell.setAlias(column.name);
				cell.getCellInfo().setPath(column.propertyPath);
			}
			
			cell.getRuleTupleInfo().addAction(cell);
		}
		List<TableRuleVariable> condition = tableRule.getConditions();
		
		for (TableRuleVariable tableRuleVariable : condition) {
			String condString = tableRuleVariable.getExpression();
			
			if (condString != null && condString.length() > 0) {
				StringTokenizer tokens = new StringTokenizer(condString, COND_OPERATORS.OR.opString());
				while (tokens.hasMoreTokens()) {
					Cell cell = new Cell(Cell.CONDITION_CELL_TYPE);
					Column column = fColMap.get(tableRuleVariable.getColumnId());
					cell.setRuleTupleInfo(ri);
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
					cell.setColumnId(tableRuleVariable.getColumnId());
					cell.setEnabled(tableRuleVariable.isEnabled());
					if (column != null) {
						cell.setAlias(column.name);
						cell.getCellInfo().setPath(column.propertyPath);
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
		List<TableColumn> columns = decisionTable.getColumns();
		
		if (columns != null) {
			for (TableColumn column : columns) {
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
			ri.setId(ruleID);
			dt.addRuleTupleInfo(ri);
		}
		List<TableRuleVariable> actions = tableRule.getActions();
		for (TableRuleVariable action : actions) {
			Cell cell = new Cell(Cell.ACTION_CELL_TYPE);
			cell.setRuleTupleInfo(ri);
			cell.setBody(action.getExpression());
			cell.setId(action.getId());
			cell.setColumnId(tableRuleVariable.getColumnId());
			Column column = fColMap.get(action.getColumnId());
			cell.setAlias(column.name);
			cell.setEnabled(tableRuleVariable.isEnabled());
			cell.getCellInfo().setPath(column.propertyPath);
			cell.getRuleTupleInfo().addAction(cell);
		}
		
		//Check if TRV is action type
		if (!isCondition(tableRuleVariable, tableRule)) {
			//Do not process action cell as it would have been processed above
			return;
		}
		
		
		String condString = tableRuleVariable.getExpression();
		
		if (condString != null && condString.length() > 0) {
			
			StringTokenizer tokens = new StringTokenizer(condString, COND_OPERATORS.OR.opString());
			while (tokens.hasMoreTokens()) {
				String condToken = tokens.nextToken().trim();
				if (condToken.equals(""))
					continue;
				
				Cell cell = new Cell(Cell.CONDITION_CELL_TYPE);
				cell.setRuleTupleInfo(ri);
			
				if (condToken.length() == 2 && condToken.equals("\"\"")) {
					// empty strings can be treated like blank conditions
				} else {
//					if (DONT_CARE == condToken.intern()) {
//					return;
//					}
					
					cell.setBody(condToken);
				}
			
				cell.setId(tableRuleVariable.getId());
				cell.setColumnId(tableRuleVariable.getColumnId());
				cell.setEnabled(tableRuleVariable.isEnabled());
				Column column = fColMap.get(tableRuleVariable.getColumnId());
				if (column != null) {
					cell.setAlias(column.name);
					cell.getCellInfo().setPath(column.propertyPath);
				}
				// Add any cell only when its not empty
				dt.addCell(cell);
			}
		}
	}
	
	boolean isCondition(TableRuleVariable trv, TableRule tableRule) {
		for (TableRuleVariable condition : tableRule.getConditions()) {
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
			for (TableRuleVariable condition : tableRule.getConditions()) {
				
				// skip empty cells
				if (condition.getExpression().trim().isEmpty())
					continue;
				Column column = fColMap.get(condition.getColumnId());
				
				StringTokenizer tokens = new StringTokenizer(condition.getExpression(), COND_OPERATORS.OR.opString());
				while (tokens.hasMoreTokens()) {
					String expr = tokens.nextToken().trim();
					
					if (expr.equals(""))
						continue;
					
					Cell cell = new Cell(Cell.CONDITION_CELL_TYPE);
					cell.setId(condition.getId());
					cell.setColumnId(condition.getColumnId());
					logger.debug("condition expression--->>" + expr);
					cell.setBody(expr);
					cell.setRuleTupleInfo(ri);
					if (column != null) {
						cell.setAlias(column.name);
						dt.removeCell(cell);
					}
				}
			}
			//Remove action cells
			for (TableRuleVariable action : tableRule.getActions()) {
				
				// skip empty cells
				if (action.getExpression().trim().isEmpty())
					continue;
				Cell cell = new Cell(Cell.ACTION_CELL_TYPE);
				Column column = fColMap.get(action.getColumnId());
				cell.setId(action.getId());
				cell.setColumnId(action.getColumnId());				
				cell.setBody(action.getExpression() );
				cell.setRuleTupleInfo(ri);
				if (column != null) {
					cell.setAlias(column.name);
					dt.removeCell(cell);
				}
			}
			dt.removeRuleTupleInfo(ri);
		}
	}
	
	public void removeExistingColumn(DecisionTable dt, TableColumn column)	{
		
		// Only for CONDITION columns
		if (ColumnType.get(column.getColumnType()) == ColumnType.CONDITION) {
			//Add all the columns to the fColMap the constraint editor  
			processAllColumns();
			dt.removeAlias(column.getName());
		}
	}
	
	public void removeExistingRuleVariable(DecisionTable dt, 
			TableRuleVariable trv)	{
		//Add all the columns to the fColMap the constraint editor  
		processAllColumns();
		
		String ruleID = DecisionConstraintUtils.getContainingRuleId(trv.getId());
		
		if (ruleID == null) {
			return;
		}
		//Search for a rule tuple with this id
		RuleTupleInfo ri = dt.getRuleTupleInfo(ruleID);

		Column column = fColMap.get(trv.getColumnId());
		
		StringTokenizer tokens = new StringTokenizer(trv.getExpression(), COND_OPERATORS.OR.opString());
		while (tokens.hasMoreTokens()) {
			String expr = tokens.nextToken().trim();
			
			if (expr.equals(""))
				continue;
			
			Cell cell = new Cell(Cell.CONDITION_CELL_TYPE);
			cell.setId(trv.getId());
			cell.setColumnId(trv.getColumnId());
			cell.setBody(expr);
			cell.setRuleTupleInfo(ri);
			if (column != null) {
				cell.setAlias(column.name);
				dt.removeCell(cell);
			}
		}
	}
}
