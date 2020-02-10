package com.tibco.cep.webstudio.client.decisiontable;

import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRule;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRuleVariable;

/**
 * 
 * @author sasahoo
 *
 */
public class TableRuleVariableRecord extends ListGridRecord {

	private TableRule rule;
	
	public TableRuleVariableRecord (TableRule rule ) {
		this.rule = rule;
		setRule(rule);		
	}

	public TableRule getRule() {
		boolean isRuleEnabled = false;
		for(TableRuleVariable rulVar : rule.getConditions()){
			if(rulVar != null && rulVar.isEnabled()){
				isRuleEnabled = true;
				break;
			}
		}
		for(TableRuleVariable rulVar : rule.getActions()){
			if(rulVar != null && rulVar.isEnabled()){
				isRuleEnabled = true;
				break;
			}
		}
		rule.setEnabled(isRuleEnabled, false);
		return rule;
	}

	public void setRule(TableRule rule) {
		this.setAttribute("rule", rule);		
		this.rule = rule;
	}
	
}
