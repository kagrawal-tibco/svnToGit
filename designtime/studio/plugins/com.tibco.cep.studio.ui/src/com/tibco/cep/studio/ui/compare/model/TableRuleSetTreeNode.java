package com.tibco.cep.studio.ui.compare.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

public class TableRuleSetTreeNode extends AbstractTreeNode {

	public static final int TYPE_DECISION_TABLE 	= 0;
	public static final int TYPE_EXCEPTION_TABLE 	= 1;
	
	private int type = TYPE_DECISION_TABLE;
	
	public TableRuleSetTreeNode(EObject input, int featureId) {
		super(input, featureId);
	}

	public TableRuleSetTreeNode(EObject input, int featureId, int type) {
		this(input, featureId);
		this.type = type;
	}

	public int getType() {
		return type;
	}

	@Override
	public boolean isEqualTo(Object obj) {
		if (!(obj instanceof TableRuleSetTreeNode)) {
			return false;
		}
		return getType() == ((TableRuleSetTreeNode)obj).getType();
//		if (input instanceof TableRuleSet && ((DecisionTableTreeNode)obj).getInput() instanceof TableRuleSet) {
//		}
//		return false;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public void handleReplace(AbstractTreeNode dest,
			AbstractTreeNode src, EObject newObject) {
		// if the child is a TableRule object, mark it as 'modified'
		if (newObject instanceof TableRule) {
			TableRule rule = (TableRule) newObject;
			rule.setModified(true);
			checkColumns(rule, (TableRule) src.getInput());
		}
		super.handleReplace(dest, src, newObject);
	}

	/*
	 * This method is responsible for adding new columns in the target
	 * table if they do not yet exist.  Needed for newly added rules
	 * to a table that does not yet have any columns
	 */
	private void checkColumns(TableRule newRule, TableRule srcRule) {
		TableRuleSet oldTable = (TableRuleSet) srcRule.eContainer();
		if (oldTable == null) {
			return; // nothing can be done
		}
		TableRuleSet newTable = (TableRuleSet) newRule.eContainer();
		Columns newColumns = newTable.getColumns();
		if (newColumns == null) {
			newColumns = DtmodelFactory.eINSTANCE.createColumns();
			newTable.setColumns(newColumns);
		}
		Columns oldColumns = oldTable.getColumns();
		
		// iterate over conditions, see if any columns are missing
		EList<TableRuleVariable> conditions = newRule.getCondition();
		processRuleVariables(newColumns, oldColumns, conditions);
		
		// iterate over actions, see if any columns are missing
		EList<TableRuleVariable> actions = newRule.getAction();
		processRuleVariables(newColumns, oldColumns, actions);
		
	}

	private void processRuleVariables(Columns newColumns, Columns oldColumns,
			EList<TableRuleVariable> condition) {
		for (TableRuleVariable tableRuleVariable : condition) {
			String columnId = tableRuleVariable.getColId();
			Column column = getColumn(newColumns, columnId);
			if (column == null) {
				// get the column from the oldColumns and add it
				// to the new table
				column = getColumn(oldColumns, columnId);
				newColumns.getColumn().add(column);
			}
		}
	}

	private Column getColumn(Columns newColumns, String columnId) {
		for (Column column : newColumns.getColumn()) {
			if (columnId.equals(column.getId())) {
				return column;
			}
		}
		return null;
	}
	
}
