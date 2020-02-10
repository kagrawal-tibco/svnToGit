package com.tibco.cep.studio.core.dependency;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class ChannelDependencyCalculator extends EntityDependencyCalculator {

	@Override
	protected void processEntityElement(File projectDir, String projectName, EntityElement element,
			List<Object> dependencies) {
		if (!(element.getEntity() instanceof Channel)) {
			return;
		}
		Channel channel = (Channel) element.getEntity();
		EList<Destination> destinations = channel.getDriver().getDestinations();
		for (int i = 0; i < destinations.size(); i++) {
			Destination destination = destinations.get(i);
			String eventURI = destination.getEventURI();
			if (eventURI != null) {
				Event simpleEvent = IndexUtils.getSimpleEvent(projectName, eventURI);
				processAndAddDependency(dependencies, projectName, simpleEvent);
			}
		}
		
		if (channel.getDriver().getConfigMethod() == CONFIG_METHOD.REFERENCE) {
			String resReference = channel.getDriver().getReference();
			if (resReference != null && resReference.trim().length() > 0) {
				processAndAddDependency(projectDir, dependencies, projectName, new Path(resReference));
			}
		}
	}

}
