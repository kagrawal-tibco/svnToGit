/**
 * 
 */
package com.tibco.cep.decisionproject.util;


/**
 * @author rmishra
 *
 */
public class RuleIdGenerator {
	private int dtRuleIdCounter = 0;
	private int etRuleIdCounter = 0;
	
	public void intializeIdCounter(int curentDTRuleId, int currentETRuleId){
		this.dtRuleIdCounter = curentDTRuleId;
		this.etRuleIdCounter = currentETRuleId;
	}
	public void intializeDTIdCounter(int curentDTRuleId){
		this.dtRuleIdCounter = curentDTRuleId;		
	}
	public void intializeETIdCounter(int currentETRuleId){		
		this.etRuleIdCounter = currentETRuleId;
	}
	
	public String getRuleId(int paneType){
		if (DTConstants.DECISION_TABLE == paneType){
			return ""+(++dtRuleIdCounter);
		}
		else {
			return ""+(++etRuleIdCounter);
		}
	}
	public void resetIdCounter(int paneType){
		if (DTConstants.DECISION_TABLE == paneType){
			dtRuleIdCounter = 0;
		}
		else {
			etRuleIdCounter = 0;
		}
	}
}
