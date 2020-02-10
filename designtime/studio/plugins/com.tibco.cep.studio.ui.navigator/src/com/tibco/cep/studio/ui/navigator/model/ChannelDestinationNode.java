package com.tibco.cep.studio.ui.navigator.model;

import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.ui.StudioNavigatorNode;

public class ChannelDestinationNode extends StudioNavigatorNode {

	public ChannelDestinationNode(Destination destination) {
		super(destination, false);
	}
	
	public ChannelDestinationNode(Destination destination, boolean sharedElement) {
		super(destination, sharedElement);
	}
	
	public String getEventURI(){
		return ((Destination) getEntity()).getEventURI();
	}
	
	public String getSerializerDeserializerClass(){
		return ((Destination) getEntity()).getSerializerDeserializerClass();
	}
}