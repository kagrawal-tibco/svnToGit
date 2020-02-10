package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;

class StateMachineIndex {
	
	static final synchronized StateMachineIndex getInstance(PresentationContext pCtx) {
		StateMachineIndex stateMachineIndex = (StateMachineIndex) pCtx.getAttribute(StateMachineIndex.class.getName());
		if (stateMachineIndex == null) {
			stateMachineIndex = new StateMachineIndex();
			pCtx.addAttribute(StateMachineIndex.class.getName(), stateMachineIndex);
		}
		return stateMachineIndex;
	}
	
	private StateMachine stateMachine;
	
	private Map<String,State> stateIndex;

	private StateMachineIndex() {
		stateIndex = new HashMap<String, State>();
	}
	
	void setStateMachine(StateMachine stateMachine) {
		this.stateMachine = stateMachine;
		stateIndex.clear();
	}
	
	State findState(String guid){
		State state = stateIndex.get(guid);
		if (state == null) {
			state = search(stateMachine.getStateEntities(), guid);
			stateIndex.put(guid, state);
		}
		return state;
	}

	private State search(List<StateEntity> stateEntities, String guid) {
		//search in the incoming list first  
		for (StateEntity stateEntity : stateEntities) {
			if (stateEntity instanceof State && guid.equals(stateEntity.getGUID()) == true) {
				return (State) stateEntity;
			}
		}
		//search in the children of the incoming list 
		for (StateEntity stateEntity : stateEntities) {
			if (stateEntity instanceof StateComposite) {
				State state = search(((StateComposite)stateEntity).getStateEntities(),guid);
				if (state != null) {
					return state;
				}
			}
		}
		return null;		
	}
	
}