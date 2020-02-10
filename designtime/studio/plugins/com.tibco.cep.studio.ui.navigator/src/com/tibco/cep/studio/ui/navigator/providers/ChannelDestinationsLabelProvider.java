package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.swt.graphics.Image;

import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.navigator.model.ChannelDestinationNode;

public class ChannelDestinationsLabelProvider extends EntityLabelProvider {

	public Image getImage(Object element) {
		if (element instanceof ChannelDestinationNode) {
			return StudioUIPlugin.getDefault().getImage(
					"icons/destination.png");
		}
		return super.getImage(element);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.navigator.providers.EntityLabelProvider#getDescription(java.lang.Object)
	 */
	@Override
	public String getDescription(Object anElement) {
		if (anElement instanceof ChannelDestinationNode) {
			ChannelDestinationNode destNode = (ChannelDestinationNode) anElement;
			Destination propertyDefinition = (Destination)destNode.getEntity();
			Channel channel = (Channel)propertyDefinition.getDriverConfig().eContainer();
			String name = channel.getFullPath()+"." +IndexUtils.getFileExtension(channel)+"/"+destNode.getName();
			return name;
		}
		return null;
	}
}
