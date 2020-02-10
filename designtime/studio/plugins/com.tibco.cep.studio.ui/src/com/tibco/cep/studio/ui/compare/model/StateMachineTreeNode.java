package com.tibco.cep.studio.ui.compare.model;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.states.StateMachine;

public class StateMachineTreeNode extends AbstractTreeNode {

	public StateMachineTreeNode(Object input, int featureId) {
		super(input, featureId);
	}

	@Override
	public boolean isEqualTo(Object obj) {
		if (input instanceof StateMachine && ((AbstractTreeNode)obj).getInput() instanceof StateMachine) {
			return true; 
		}
		return false;
	}

	@Override
	public void handleReplace(AbstractTreeNode dest,
			AbstractTreeNode src, EObject newObject) {
		super.handleReplace(dest, src, newObject);
	}
}