package com.tibco.cep.studio.ui.statemachine.diagram.rule;

import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tomsawyer.graphicaldrawing.TSENode;

public class EndToEndStateRuleImpl  implements StateMachineDiagramRule {

	private StateMachineDiagramRuleset ruleset;
	
	public EndToEndStateRuleImpl(StateMachineDiagramRuleset ruleset) {
		this.ruleset = ruleset;
	}
	
	public boolean isAllowed() {
		TSENode srcTSNode = this.ruleset.getSourceNode();
		TSENode tgtTSNode = this.ruleset.getTargetNode();
	
		if(srcTSNode != null && tgtTSNode!=null){
			StateEntity srcSmNode = (StateEntity) srcTSNode.getUserObject();
			StateEntity tgtSmNode = (StateEntity) tgtTSNode.getUserObject();
			StateComposite tgtcomposite =(StateComposite) tgtTSNode.getOwnerGraph().getUserObject();
			StateComposite srccomposite =(StateComposite) srcTSNode.getOwnerGraph().getUserObject();
			if (srcSmNode instanceof StateEnd &&
					tgtSmNode instanceof StateEnd) {
					return false;
				}
				else if(!(srccomposite.isRegion()) && tgtcomposite.isRegion()){
					return false;
				}else{
					return true;
				}
		}
		return false;
	}
	

	public StateMachineDiagramRuleset getRuleset() {
		return this.ruleset;
	}	

}
