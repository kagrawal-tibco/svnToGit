package com.tibco.cep.studio.ui.compare.model;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.service.channel.Channel;

public class ChannelTreeNode extends AbstractTreeNode {

	public ChannelTreeNode(Object input, int featureId) {
		super(input, featureId);
	}

	@Override
	public boolean isEqualTo(Object obj) {
		if (input instanceof Channel && ((AbstractTreeNode)obj).getInput() instanceof Channel) {
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