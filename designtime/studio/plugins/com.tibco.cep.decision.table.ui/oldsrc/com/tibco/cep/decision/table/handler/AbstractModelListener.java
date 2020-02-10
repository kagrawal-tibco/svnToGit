/**
 * 
 */
package com.tibco.cep.decision.table.handler;

import java.util.List;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decisionproject.acl.AccessControlCellData;
import com.tibco.cep.decisionproject.acl.event.AccessControlEvent;
import com.tibco.cep.decisionproject.acl.event.AccessControlEventListener;

/**
 * Put common code in this class
 * @author aathalye
 *
 */
public abstract class AbstractModelListener {
	
	protected AccessControlEventListener<AccessControlEvent, Table> eventListener;
	
	/**
	 * Remove an {@linkplain AccessControlEvent} from the acl event queue
	 * @param trv
	 * @param column
	 * @return true if remove was successful
	 */
	protected boolean removeAccessEvent(final TableRuleVariable trv,
			                            final Column column) {
		if (!trv.isModified()) {
			return false;
		}
		//Search for event with this id
		AccessControlEvent event = eventListener.fetchEvent(trv.getId());
		if (event == null) return true;
		return eventListener.dequeueEvent(event);
	}
	
	/**
	 * Create an {@linkplain AccessControlEvent} for the acl event queue
	 * if the concerned cell is modified.
	 * @param trv
	 * @param column
	 */
	protected void sendAccessEvent(final TableRuleVariable trv,
			                       final Column column) {
		if (!trv.isModified()) {
			return;
		}
		AccessControlCellData acd = new AccessControlCellData(column, trv);
		AccessControlEvent event = new AccessControlEvent(acd);
		event.setUid(trv.getId());
		eventListener.enqueueEvent(event);
	}
	
	/**
	 * Remove all conditions/actions using this column id
	 * before the column id is removed
	 * @param tableRuleSet
	 * @param columnName
	 * @param columnType
	 */
	protected void removeTableRuleVariables(TableRuleSet tableRuleSet, 
			                                String columnName,
			                                ColumnType columnType) {
		if (tableRuleSet == null) {
			return;
		}
		//Get all columns
		Columns columns = tableRuleSet.getColumns();
		Column column = columns.searchByName(columnName, columnType);
		if (column == null) {
			return;
		}
		//Get its id
		String colId = column.getId();
		//Get all rules in it
		List<TableRule> allRules = tableRuleSet.getRule();
		
		for (TableRule tableRule : allRules) {
			switch (columnType) {
			case CONDITION : {
				List<TableRuleVariable> conditions = tableRule.getCondition();
				//Search for condition with this col id
				TableRuleVariable toRemove = null;
				for (TableRuleVariable trv : conditions) {
					if (trv.getColId().equals(colId)) {
						toRemove = trv;
						break;
					}
				}
				conditions.remove(toRemove);
				break;
			}
			case ACTION : {
				List<TableRuleVariable> actions = tableRule.getAction();
				//Search for action with this col id
				TableRuleVariable toRemove = null;
				for (TableRuleVariable trv : actions) {
					if (trv.getColId().equals(colId)) {
						toRemove = trv;
						break;
					}
				}
				actions.remove(toRemove);
				break;
			}
			}
		}
	}

}
