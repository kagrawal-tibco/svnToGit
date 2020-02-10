package com.tibco.cep.studio.ui.statemachine.diagram.rule;

import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author ggrigore
 *
 */
public class EndToOthersRuleImpl implements StateMachineDiagramRule {

	private StateMachineDiagramRuleset ruleset;
	
	public EndToOthersRuleImpl(StateMachineDiagramRuleset ruleset) {
		this.ruleset = ruleset;
	}
	
	public boolean isAllowed() {
		TSENode srcTSNode = this.ruleset.getSourceNode();
		StateEntity srcSmNode = (StateEntity) srcTSNode.getUserObject();
		
		if (srcSmNode instanceof StateEnd) {
			return false;
		}
		else {
			return true;
		}
	}
	

	public StateMachineDiagramRuleset getRuleset() {
		return this.ruleset;
	}	

}
