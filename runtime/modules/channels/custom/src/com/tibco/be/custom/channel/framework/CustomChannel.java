package com.tibco.be.custom.channel.framework;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.be.custom.channel.BaseChannel;
import com.tibco.be.custom.channel.BaseDestination;
import com.tibco.be.custom.channel.BaseDriver;
import com.tibco.be.custom.channel.BaseEventSerializer;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.impl.AbstractChannel;
import com.tibco.cep.runtime.model.event.SimpleEvent;

/**
 * @author vasharma
 */

public class CustomChannel extends AbstractChannel<CustomDestination> {

	BaseChannel userChannel = null;
	BaseDriver baseDriver;

	public CustomChannel(ChannelManager manager, String uri, CustomChannelConfig channelConfig, BaseChannel channel,
			BaseDriver baseDriver) throws Exception {
		super(manager, uri, channelConfig);

		HashMap<String, BaseDestination> userDestinations = new HashMap<String, BaseDestination>();
		CustomChannelUtils.populateGVConfigForChannel(channelConfig);

		this.userChannel = channel;
		this.baseDriver = baseDriver;

		if (userChannel instanceof BaseChannel) {
			((BaseChannel) this.userChannel).setConfig(channelConfig);
			((BaseChannel) this.userChannel).setRuleServiceProvider(manager.getRuleServiceProvider());
		}
		// Initializing destinations
		userDestinations = initializeDestinations(channel, channelConfig.getDestinations());
		// ---------------------------------------

		// Setting channel config attributes
		if (userChannel instanceof BaseChannel) {
			((BaseChannel) this.userChannel).setDestinations(userDestinations);
		}
		// ---------------------------------------
	}

	private HashMap<String, BaseDestination> initializeDestinations(BaseChannel channel, Collection destinations)
			throws Exception {

		HashMap<String, BaseDestination> userDestinations = new HashMap<String, BaseDestination>();
		Iterator<?> i = destinations.iterator();
		while (i.hasNext()) {
			DestinationConfig destConfig = (DestinationConfig) i.next();
			CustomChannelUtils.populateGVConfigForDestination(destConfig, getGlobalVariables());
			BaseDestination userDestination = null;

			try {
				userDestination = baseDriver.getDestination();
			} catch (Exception e) {
				getLogger().log(Level.ERROR, "Channel : Error while getting Destination instance : [%s]",
						userDestination != null ? userDestination.getClass().toString() : "NA");
			}

			if (userDestination != null) {
				if (!(destConfig.getEventSerializer() instanceof BaseEventSerializer)) {
					getLogger().log(Level.ERROR, "Channel :Serializer not of type BaseEventSerializer : [%s]",
							destConfig.getEventSerializer().getClass().toString());
				}
				// Initializing UserDestination
				userDestination.initInputDestination(channel, destConfig.getName(), destConfig.getURI(),
						destConfig.getDefaultEventURI() == null ? "" : destConfig.getDefaultEventURI().toString(),
						destConfig.getProperties(), (BaseEventSerializer) destConfig.getEventSerializer());
				CustomDestination dest = new CustomDestination(this, destConfig, userDestination);
				userDestinations.put(dest.getURI(), userDestination);
				this.destinations.put(dest.getURI(), dest);
			}
		}
		return userDestinations;
	}

	public void init() throws Exception {
		userChannel.init();
		getLogger().log(Level.INFO, "Channel:Initialized[%s]", getURI());
		super.init();
	}

	@Override
	public void connect() throws Exception {
		userChannel.connect();
		getLogger().log(Level.INFO, "Channel:Connected[%s]", getURI());
	}

	@Override
	public void start(int mode) throws Exception {
		userChannel.start();
		super.start(mode);
	}
	
	@Override
	public void stop() {
		userChannel.stop();
		super.stop();
	}
	
	@Override
	public void close() throws Exception {
		userChannel.close();
	}

	@Override
	public CustomDestination createDestination(DestinationConfig config) {
		return null;
	}

	@Override
	public void send(SimpleEvent event, String destinationURI, Map overrideData) throws Exception {
		// TODO Do Nothing : Not Sure why this is used
	}

	@Override
	public boolean isAsync() {
		return false;
	}
	
    public State getState() {
    	return this.userChannel.getState();
    }
}
