package com.tibco.cep.bpmn.ui.graph.rule;


public class DeleteEdgeRule extends AbstractDiagramRule implements DiagramRule {

	public DeleteEdgeRule(DiagramRuleSet ruleSet) {
		super(ruleSet);
	}


	@Override
	public boolean isAllowed(Object[] args) {

		return true;
	}

	

}
