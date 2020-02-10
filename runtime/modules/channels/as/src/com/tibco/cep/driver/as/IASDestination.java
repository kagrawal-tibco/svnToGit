package com.tibco.cep.driver.as;

import com.tibco.as.space.Space;
import com.tibco.as.space.SpaceDef;
import com.tibco.cep.runtime.channel.Channel.Destination;
import com.tibco.cep.runtime.channel.impl.ChannelDestinationStats;

public interface IASDestination extends Destination {

	Space getSpace();

	SpaceDef getSpaceDef();

	IASChannel getChannel();

	ChannelDestinationStats getStats();

	void reconnect();

}
