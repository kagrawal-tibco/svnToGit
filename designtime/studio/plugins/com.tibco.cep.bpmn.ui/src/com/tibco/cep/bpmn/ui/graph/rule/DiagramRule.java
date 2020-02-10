package com.tibco.cep.bpmn.ui.graph.rule;


/**
 * 
 * @author pdhar
 *
 */
public interface DiagramRule {

	DiagramRuleSet getRuleset();
	
	boolean isAllowed(Object[] args);
	
	/**
	 * 
	 * return the reason, if previous isAllowed check is failed
	 * if previous isAllowed check is passed, it will return null
	 * 
	 */
	String getMessage();
}
