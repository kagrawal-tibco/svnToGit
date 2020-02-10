package com.tibco.cep.bpmn.ui.graph.rule;


public abstract class AbstractDiagramRule implements DiagramRule {
	
	private DiagramRuleSet ruleset;
	
	public AbstractDiagramRule(DiagramRuleSet ruleSet) {
		this.ruleset = ruleSet;
	}

	@Override
	public DiagramRuleSet getRuleset() {
		return this.ruleset;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
