package com.tibco.cep.studio.ui.statemachine.diagram.rule;

import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.studio.ui.statemachine.diagram.tool.StateMachineReconnectEdgeTool;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.swing.editing.tool.TSEBuildEdgeTool;

/**
 * 
 * @author ggrigore
 *
 */
public class StartToOthersRuleImpl implements StateMachineDiagramRule {

	private StateMachineDiagramRuleset ruleset;
	TSEBuildEdgeTool tool;
	public StartToOthersRuleImpl(StateMachineDiagramRuleset ruleset,TSEBuildEdgeTool tool) {
		this.ruleset = ruleset;
		this.tool=tool;
	}
	
	public boolean isAllowed() {
		TSENode srcTSNode = this.ruleset.getSourceNode();
		TSENode tgtTSNode = this.ruleset.getTargetNode();
		
		if(srcTSNode != null && tgtTSNode!=null){
			StateEntity srcSmNode = (StateEntity) srcTSNode.getUserObject();
			StateEntity tgtSmNode = (StateEntity) tgtTSNode.getUserObject();
			/*
			 * This part is done because when trying to reconnect the start of an edge
			 *  to start state it was not allowing.
			 *  During reconnection due to TS src becomes the previous node and 
			 *  takes start node as the target node.
			 */
			if(this.tool instanceof StateMachineReconnectEdgeTool)
			{
				return true;
			}else{
			
			if (srcSmNode instanceof StateStart &&
					tgtSmNode instanceof StateStart) {
					return false;
				}
				else {
					return true;
				}
			}
		}
		return false;
	}
	

	public StateMachineDiagramRuleset getRuleset() {
		return this.ruleset;
	}	

}
