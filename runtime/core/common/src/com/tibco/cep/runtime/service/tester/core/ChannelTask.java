package com.tibco.cep.runtime.service.tester.core;

import com.tibco.cep.runtime.channel.Channel.Destination;

public class ChannelTask {
	
	private Destination destination;
	
	public ChannelTask(Destination dest) {
		this.destination = dest;
	}
	
	public Destination getDestination() {
		return destination;
	}

}
