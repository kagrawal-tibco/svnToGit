/**
 * 
 */
package com.tibco.cep.decision.table.command.memento;

import com.tibco.cep.decision.table.model.dtmodel.TableRule;

/**
 * @author aathalye
 *
 */
public class TableRuleStateMemento extends StateMemento<TableRule> {

	/**
	 * @param monitored
	 * @param value
	 */
	public TableRuleStateMemento(TableRule monitored, Object value) {
		super(monitored, value);
	}
	
	/**
	 * Get ID of the monitored object.
	 * @return
	 */
	public String getId() {
		return monitored.getId();
	}
}
