package com.tibco.cep.driver.as3;

import com.tibco.be.custom.channel.BaseChannel;
import com.tibco.be.custom.channel.BaseDestination;
import com.tibco.be.custom.channel.BaseDriver;
import com.tibco.cep.runtime.channel.ChannelConfig;

public class AS3Driver extends BaseDriver {
	
	@Override
	public BaseChannel getChannel() {
		return new AS3Channel();
	}

	@Override
	public BaseDestination getDestination() {
		return new AS3Destination();
	}

}
