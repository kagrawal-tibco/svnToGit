package com.tibco.cep.studio.ui.compare.model;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.event.Event;

public class EventTreeNode extends AbstractTreeNode {

	public EventTreeNode(Object input, int featureId) {
		super(input, featureId);
	}

	@Override
	public boolean isEqualTo(Object obj) {
		if (input instanceof Event && ((AbstractTreeNode)obj).getInput() instanceof Event) {
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