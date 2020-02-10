package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.studio.ui.StudioNavigatorNode;
import com.tibco.cep.studio.ui.navigator.model.ChannelDestinationNode;

public class ChannelContentProvider extends EntityContentProvider {

	public boolean hasChildren(Object element) {
		if (!(element instanceof IFile)) {
			return false;
		}
		return true;
	}

	@Override
	protected Object[] getEntityChildren(Entity entity, boolean isSharedElement) {
		if (!(entity instanceof Channel)) {
			return EMPTY_CHILDREN;
		}
		Channel channel = (Channel) entity;
		
		DriverConfig driverConfig = channel.getDriver();
		
		if(driverConfig == null){
			return EMPTY_CHILDREN;
		}

		EList<Destination> destinations = driverConfig.getDestinations();
		StudioNavigatorNode[] destination = new StudioNavigatorNode[destinations.size()];
		for (int i = 0; i < destinations.size(); i++) {
			destination[i] = new ChannelDestinationNode(destinations.get(i), isSharedElement);
		}
		
		return destination;
	}

}
