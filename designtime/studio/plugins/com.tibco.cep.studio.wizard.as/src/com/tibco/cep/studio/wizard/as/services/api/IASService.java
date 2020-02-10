package com.tibco.cep.studio.wizard.as.services.api;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.as.space.SpaceDef;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.wizard.as.services.spi.IStageParticipant;

public interface IASService {

	List<Channel> getASChannels(IProject project);

	SimpleEvent createSimpleEvent(IProject project, Channel channel, SpaceDef spaceDef);

	Destination createDestination(IProject project, Channel channel, SpaceDef spaceDef);

	List<SpaceDef> getUserSpaceDefs(IProject ownerProject, Channel channel) throws Exception;

	void persist(Channel channel, SimpleEvent simpleEvent, IProject project, IStageParticipant stageParticipant, IProgressMonitor monitor) throws Exception;

}
