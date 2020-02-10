package com.tibco.cep.studio.ui.statemachine.diagram.rule;

import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * This rule restricts user to create transition from one state to other graph's state
 * @author sasahoo
 *
 */
public class StateToStateRuleImpl implements StateMachineDiagramRule {

	private StateMachineDiagramRuleset ruleset;
	
	public StateToStateRuleImpl(StateMachineDiagramRuleset ruleset) {
		this.ruleset = ruleset;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.statemachine.diagram.rule.StateMachineDiagramRule#isAllowed()
	 */
	public boolean isAllowed() {
		TSENode srcTSNode = this.ruleset.getSourceNode();
		TSENode tgtTSNode = this.ruleset.getTargetNode();
		
		StateComposite ownerSourceState = (StateComposite)srcTSNode.getOwnerGraph().getUserObject();
		State targetState = (State)tgtTSNode.getUserObject();
		
//		if (srcTSNode.getOwnerGraph() == tgtTSNode.getOwnerGraph()) {
//			return true;
//		}
//		else {
//			return false;
//		}
		return isEnterValidOwnerGraph(ownerSourceState, targetState);
	}

	public StateMachineDiagramRuleset getRuleset() {
		return this.ruleset;
	}	
	
	/**
	 * The following Transition validation Rule is no more executed.
	 * To enter a composite or concurrent state from the boundary, its start state must have a transition to the next state.
	 * @param ownerSourceState
	 * @param targetState
	 * @return
	 */
	public boolean isEnterValidOwnerGraph(StateComposite ownerSourceState, State targetState){
		try{
			if(targetState != null && targetState.eContainer() != null){
				State parent = (State)targetState.eContainer();
				if(parent != null && parent != ownerSourceState){
					StateComposite entity = (StateComposite)parent;
					if(entity.isConcurrentState()){
						return false;
					}/*else{
						for(StateEntity e:entity.getStateEntities()){
							if(e instanceof StateStart){
								if(((StateStart)e).getOutgoingTransitions().size()== 0){
									return false;
								}
							}
						}
					}*/
				}else{
					isEnterValidOwnerGraph(ownerSourceState, parent);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
}