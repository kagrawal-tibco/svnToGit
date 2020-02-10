package com.tibco.cep.studio.ui.statemachine.diagram.rule;

import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author sasahoo
 *
 */
public class ExitCompositeStateGraphRule implements StateMachineDiagramRule {

	private StateMachineDiagramRuleset ruleset;
	
	public ExitCompositeStateGraphRule(StateMachineDiagramRuleset ruleset) {
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
		return isExitValidOwnerGraph(ownerSourceState,targetState);
	}

	public StateMachineDiagramRuleset getRuleset() {
		return this.ruleset;
	}	
	
	/**
	 * The following Transition validation Rule is no more executed.
	 * To exit a composite or concurrent state from the boundary, its end state must have a transition coming from the previous state.
	 * @param ownerSourceState
	 * @return
	 */
	public boolean isExitValidOwnerGraph(StateComposite ownerSourceState, State targetState){
		try{
			if(!(ownerSourceState instanceof StateMachine)){
				if(targetState != null && targetState.eContainer()!=null && ownerSourceState == targetState.eContainer()) 
					return true;
				if(ownerSourceState.isConcurrentState()){
					return false;
				}
				else if(ownerSourceState.isRegion()){
					return false;
				}
//				else{
//					boolean isStateEndPresent = false;
//					for(StateEntity e:ownerSourceState.getStateEntities()){
//						if(e instanceof StateEnd){
//							isStateEndPresent = true;
//							if(((StateEnd)e).getIncomingTransitions().size()== 0){
//								return false;
//							}
//						}
//					}
//					return isStateEndPresent;
//				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
}