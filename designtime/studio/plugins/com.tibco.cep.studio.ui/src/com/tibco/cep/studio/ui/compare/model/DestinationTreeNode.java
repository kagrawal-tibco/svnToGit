package com.tibco.cep.studio.ui.compare.model;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.service.channel.Destination;

public class DestinationTreeNode  extends AbstractTreeNode {

	public DestinationTreeNode(EObject input, int featureId) {
		super(input, featureId);
	}

	@Override
	public boolean isEqualTo(Object obj) {
		if (input instanceof Destination && ((AbstractTreeNode)obj).getInput() instanceof Destination) {
			Destination dest1 = (Destination) input;
			Destination dest2 = (Destination) ((AbstractTreeNode)obj).getInput();
			return dest1.getName().equals(dest2.getName());
		}
		return false;
	}

	@Override
	public void handleReplace(AbstractTreeNode dest, AbstractTreeNode src, EObject newObject) {
		super.handleReplace(dest, src, newObject);
	}

	@Override
	public boolean isUnsettable() {
		return true;
	}
}
