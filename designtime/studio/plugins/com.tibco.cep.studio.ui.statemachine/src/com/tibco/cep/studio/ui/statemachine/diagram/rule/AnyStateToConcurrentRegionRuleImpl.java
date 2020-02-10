package com.tibco.cep.studio.ui.statemachine.diagram.rule;

import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author sasahoo
 *
 */
public class AnyStateToConcurrentRegionRuleImpl implements StateMachineDiagramRule {

	private StateMachineDiagramRuleset ruleset;
	
	/**
	 * @param ruleset
	 */
	public AnyStateToConcurrentRegionRuleImpl(StateMachineDiagramRuleset ruleset) {
		this.ruleset = ruleset;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.statemachine.diagram.rule.StateMachineDiagramRule#isAllowed()
	 */
	public boolean isAllowed() {
		TSENode tgtTSNode = this.ruleset.getTargetNode();
		if(tgtTSNode.getUserObject() instanceof StateComposite){
			StateComposite composite =(StateComposite) tgtTSNode.getOwnerGraph().getUserObject();
			if(composite.isConcurrentState()){
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}
	}
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.statemachine.diagram.rule.StateMachineDiagramRule#getRuleset()
	 */
	public StateMachineDiagramRuleset getRuleset() {
		return this.ruleset;
	}	
}
