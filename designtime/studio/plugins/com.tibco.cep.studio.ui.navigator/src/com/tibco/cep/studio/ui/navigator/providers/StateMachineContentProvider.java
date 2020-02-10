package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.studio.ui.StudioNavigatorNode;
import com.tibco.cep.studio.ui.navigator.model.StateEntityNode;

public class StateMachineContentProvider extends EntityContentProvider {

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.navigator.providers.EntityContentProvider#hasChildren(java.lang.Object)
	 */
	public boolean hasChildren(Object element) {
		if (!(element instanceof IFile)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.navigator.providers.EntityContentProvider#getEntityChildren(com.tibco.cep.designtime.core.model.Entity)
	 */
	@Override
	protected Object[] getEntityChildren(Entity entity, boolean isSharedElement) {

		if (!(entity instanceof StateMachine)) {
			return EMPTY_CHILDREN;
		}
		StateMachine stateMachine = (StateMachine) entity;
		
		EList<StateEntity> entities = stateMachine.getStateEntities();
		EList<StateTransition> stateTransitions = stateMachine.getStateTransitions();
		
		StudioNavigatorNode[] states = new StudioNavigatorNode[entities.size()];
		for (int i = 0; i < entities.size(); i++) {
			states[i] = new StateEntityNode(entities.get(i), isSharedElement);
		}
		
		StudioNavigatorNode[] transitions = new StudioNavigatorNode[stateTransitions.size()];
		for (int i = 0; i < stateTransitions.size(); i++) {
			transitions[i] = new StateEntityNode(stateTransitions.get(i), isSharedElement);
		}
		
		Object[] result = new Object[states.length + transitions.length];
		System.arraycopy(states, 0, result, 0, states.length);
		System.arraycopy(transitions, 0, result, states.length, transitions.length);
		return result;
	}
}
