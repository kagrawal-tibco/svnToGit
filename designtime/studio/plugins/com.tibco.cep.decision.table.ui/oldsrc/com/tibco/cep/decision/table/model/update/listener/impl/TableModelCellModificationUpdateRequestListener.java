/**
 * 
 */
package com.tibco.cep.decision.table.model.update.listener.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.model.update.listener.ITableModelChangeRequestEvent;
import com.tibco.cep.decision.table.model.update.listener.ITableModelChangeRequestListener;
import com.tibco.cep.decision.table.model.update.listener.TableModelChangeRequestEvent;
import com.tibco.cep.decision.table.utils.DecisionTableUtil;

/**
 * Handle cell modification requests to update backend model.
 * @author aathalye
 *
 */
public class TableModelCellModificationUpdateRequestListener implements ITableModelChangeRequestListener {

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.listener.ITableModelChangeRequestListener#doChange(com.tibco.cep.decision.table.listener.TableModelChangeRequestEvent)
	 */
	@Override
	public <T extends ITableModelChangeRequestEvent> void doChange(T changeRequestEvent) {
		if (!(changeRequestEvent instanceof TableModelChangeRequestEvent)) {
			return;
		}
		TableModelChangeRequestEvent tableModelChangeRequestEvent = (TableModelChangeRequestEvent)changeRequestEvent;
		//Source is the cell value that needs to be updated in the backend model
		Object source = tableModelChangeRequestEvent.getSource();
		TableTypes tableType = tableModelChangeRequestEvent.getTableType();
		Table tableEModel = tableModelChangeRequestEvent.getTableEModel();
		//Find the right table rule set.
		TableRuleSet tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable() : tableEModel.getExceptionTable();
		if (source instanceof TableRuleVariable) {
			TableRuleVariable tableRuleVariable = (TableRuleVariable)source;
			String trvId = tableRuleVariable.getId();
			String ruleId = DecisionTableUtil.getContainingRuleId(trvId);
			String columnId = tableRuleVariable.getColId();
			//Find a rule with this id assuming rule has been added already.
			TableRule matchingRule = searchRuleWithId(tableRuleSet, ruleId);
			//Get column Type
			ColumnType columnType = getColumnType(tableRuleSet, columnId);
			int indexAtWhichToSet = 
				getRuleVariableIndexToSet(matchingRule, columnType, columnId, trvId);
			//Search position of this column id.
		
			if (columnType != null) {
				//Update inside backend model.
				List<TableRuleVariable> tableRuleVariables = null;
				switch (columnType) {
				
				case CONDITION :
				case CUSTOM_CONDITION :
					tableRuleVariables = matchingRule.getCondition();
					break;
				
				case ACTION :
				case CUSTOM_ACTION :
					tableRuleVariables = matchingRule.getAction();
					break;
				}
				if (indexAtWhichToSet == -1) {
					//New addition
					tableRuleVariables.add(tableRuleVariable);
				} else {
					tableRuleVariables.set(indexAtWhichToSet, tableRuleVariable);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param tableRuleSet
	 * @param ruleId
	 * @return
	 */
	private TableRule searchRuleWithId(TableRuleSet tableRuleSet, String ruleId) {
		//Create dummy table rule for searching
		TableRule newTableRule = DtmodelFactory.eINSTANCE.createTableRule();
		newTableRule.setId(ruleId);
		EList<TableRule> tableRules = tableRuleSet.getRule();
		TableRuleIDComparator comparator = new TableRuleIDComparator();
		ECollections.sort(tableRules, comparator);
		int ruleIndex = 
			Collections.binarySearch(tableRules, newTableRule, comparator);
		if (ruleIndex >= 0) {
			//Rule id found
			return tableRules.get(ruleIndex);
		}
		throw new IllegalArgumentException("The rule id " + ruleId + " is not valid");
	}
	
	/**
	 * 
	 *
	 */
	private class TableRuleIDComparator implements Comparator<TableRule> {

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(TableRule tableRule1, TableRule tableRule2) {
			String tableRule1ID = tableRule1.getId();
			String tableRule2ID = tableRule2.getId();
			
			//Compare hashcodes
			int hashCode1 = tableRule1ID.hashCode();
			int hashCode2 = tableRule2ID.hashCode();
			return (hashCode1 > hashCode2) ? 1 : (hashCode1 < hashCode2) ? -1 : 0;
		}
	}
	
	/**
	 * 
	 * @param tableRule
	 * @param columnType
	 * @param columnId
	 * @param tableRuleVariableID
	 * @return
	 */
	private int getRuleVariableIndexToSet(TableRule tableRule,
			                              ColumnType columnType,
			                              String columnId,
			                              String tableRuleVariableID) {
		//The actual index to return
		int returnIndex = -1;
		List<TableRuleVariable> tableRuleVariables = null;
		switch (columnType) {
		case CONDITION :
		case CUSTOM_CONDITION :
			tableRuleVariables = tableRule.getCondition();
			break;
			
		case ACTION :
		case CUSTOM_ACTION :
			tableRuleVariables = tableRule.getAction();
			break;
		}
		returnIndex = doesRuleVariableExist(tableRuleVariables, columnType, tableRuleVariableID);;
		return returnIndex;
	}
	
	/**
	 * 
	 * @param tableRuleVariables
	 * @param columnType
	 * @param tableRuleVariableID
	 * @return
	 */
	private int doesRuleVariableExist(List<TableRuleVariable> tableRuleVariables,
			                          ColumnType columnType,
			                          String tableRuleVariableID) {
		for (int loop = 0; loop < tableRuleVariables.size(); loop++) {
			TableRuleVariable tableRuleVariable = tableRuleVariables.get(loop);
			if (tableRuleVariable.getId().equals(tableRuleVariableID)) {
				return loop;
			}
		}
		return -1;
	}
	
	/**
	 * 
	 * @param tableRuleSet
	 * @param columnID
	 * @return
	 */
	private ColumnType getColumnType(TableRuleSet tableRuleSet, String columnID) {
		Columns allColumns = tableRuleSet.getColumns();
		//Search
		Column foundColumn = allColumns.search(columnID);
		if (foundColumn == null) {
			return null;
		}
		return foundColumn.getColumnType();
	}
}
