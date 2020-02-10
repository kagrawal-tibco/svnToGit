package com.tibco.cep.bpmn.ui.graph.rule;


import com.tomsawyer.graphicaldrawing.TSEObject;

/**
 * 
 * @author majha
 *
 */
public class ChangeNodeTypeRule extends AbstractDiagramRule implements DiagramRule {

	private String message;

	public ChangeNodeTypeRule(DiagramRuleSet ruleSet) {
		super(ruleSet);
	}


	@Override
	public boolean isAllowed(Object[] args) {
		if(args.length != 1 ) 
			return false;
		TSEObject node = (TSEObject) args[0];
		message = null;

		DeleteNodeRule deleteNodeRule = new DeleteNodeRule(getRuleset());
		if(!deleteNodeRule.isAllowed(new Object[]{node})){
			message = deleteNodeRule.getMessage();
			return false;
		}
		

		return true;
	}
	

	@Override
	public String getMessage() {
		return message;
	}
	

}
