package com.tibco.cep.studio.ui.statemachine.diagram.rule;

import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * To check Source Node and Target Node (with source node's parent node)
 * @author sasahoo
 *
 */
public class StateToParentStateRuleImpl implements StateMachineDiagramRule {

	private StateMachineDiagramRuleset ruleset;

	/**
	 * @param ruleset
	 */
	public StateToParentStateRuleImpl(StateMachineDiagramRuleset ruleset) {
		this.ruleset = ruleset;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.statemachine.diagram.rule.StateMachineDiagramRule#isAllowed()
	 */
	public boolean isAllowed() {
		TSENode srcTSNode = this.ruleset.getSourceNode();
		TSENode tgtTSNode = this.ruleset.getTargetNode();

		if (srcTSNode.getOwnerGraph() != null) {
			StateComposite ownerSourceState = (StateComposite)srcTSNode.getOwnerGraph().getUserObject();
			State targetState = (State)tgtTSNode.getUserObject();
			if (ownerSourceState == targetState) {
				return false;
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.statemachine.diagram.rule.StateMachineDiagramRule#getRuleset()
	 */
	public StateMachineDiagramRuleset getRuleset() {
		return this.ruleset;
	}	
}