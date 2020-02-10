package com.tibco.cep.studio.ui.statemachine.diagram.rule;

/**
 * 
 * @author ggrigore
 *
 */
public interface StateMachineDiagramRule {

	boolean isAllowed();
	
	StateMachineDiagramRuleset getRuleset();
}
