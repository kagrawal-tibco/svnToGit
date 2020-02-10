package com.tibco.be.custom.channel.kafka;

import com.tibco.be.custom.channel.BaseChannel;
import com.tibco.be.custom.channel.BaseDestination;
import com.tibco.be.custom.channel.BaseDriver;

public class KafkaDriver extends BaseDriver {

	@Override
	public BaseChannel getChannel() {
		return new KafkaChannel();
	}

	@Override
	public BaseDestination getDestination() {
		return new KafkaDestination();
	}

}
