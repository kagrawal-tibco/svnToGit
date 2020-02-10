package com.tibco.cep.studio.core.search;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class ChannelSearchParticipant extends EntitySearchParticipant {

	@Override
	protected void searchEntity(EObject resolvedElement, DesignerProject index,
			String nameToFind, IProgressMonitor monitor, SearchResult result) {
		List<Entity> allEntities = IndexUtils.getAllEntities(index.getName(), new ELEMENT_TYPES[] { ELEMENT_TYPES.CHANNEL });
		for (Entity entity : allEntities) {
			Channel channel = (Channel) entity;
			if (isEqual(channel, resolvedElement)) {
				result.addExactMatch(createElementMatch(DEFINITION_FEATURE, channel.eClass(), resolvedElement));
			}
			EList<Destination> destinations = channel.getDriver().getDestinations();
			for (Destination destination : destinations) {
				if (isEqual(destination, resolvedElement)) {
					result.addExactMatch(createElementMatch(ChannelPackage.DRIVER_CONFIG__DESTINATIONS, channel.getDriver().eClass(), destination));
				}
				if (resolvedElement instanceof EntityElement) {
					if (IndexUtils.getFullPath(((EntityElement)resolvedElement).getEntity()).equalsIgnoreCase(destination.getEventURI())) {
						result.addExactMatch(createElementMatch(ChannelPackage.DESTINATION__EVENT_URI, destination.eClass(), destination));
					}
				}
			}
		}
	}

	@Override
	protected boolean isEqual(Object element, Object resolvedElement) {
		if (element instanceof Destination
				&& resolvedElement instanceof Destination) {
			Destination dest1 = (Destination) element;
			Destination dest2 = (Destination) resolvedElement;
			if (dest1.getName().equals(dest2.getName())
					&& dest1.getEventURI() != null
					&& dest2.getEventURI() != null
					&& dest1.getEventURI().equals(dest2.getEventURI())
					&& dest1.getFolder() != null 
					&& dest2.getFolder()!= null 
					&& dest1.getFolder().equals(dest2.getFolder())) {
				return true;
			}
		}
		return super.isEqual(element, resolvedElement);
	}

}
