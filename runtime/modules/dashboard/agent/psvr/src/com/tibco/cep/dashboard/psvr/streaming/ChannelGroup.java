package com.tibco.cep.dashboard.psvr.streaming;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.dashboard.management.ServiceDependent;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.security.SecurityToken;

public class ChannelGroup extends ServiceDependent {

	private static ChannelGroup instance;

	public static final synchronized ChannelGroup getInstance() {
		if (instance == null) {
			instance = new ChannelGroup();
		}
		return instance;
	}

	private Map<SecurityToken, Channel> channelsIndex;

	private ChannelGroup() {
		super("channelgroup", "Channel Group");
		channelsIndex = new ConcurrentHashMap<SecurityToken, Channel>();
	}

	@Override
	protected void doStart() {
		for (Channel channel : channelsIndex.values()) {
			channel.start();
		}
	}

	@Override
	protected void doPause() {
		for (Channel channel : channelsIndex.values()) {
			channel.pause(messageGenerator.getMessage("server.paused"));
		}
	}

	@Override
	protected void doResume() {
		for (Channel channel : channelsIndex.values()) {
			channel.resume(messageGenerator.getMessage("server.resumed"));
		}
	}

	@Override
	protected boolean doStop() {
		destroy(messageGenerator.getMessage("server.shutdown"));
		return true;
	}

	void destroy(String message) {
		List<Channel> channels = new LinkedList<Channel>(channelsIndex.values());
		for (Channel channel : channels) {
			channel.destroy(message);
		}
		channels.clear();
	}

	void destroy(SecurityToken token, String message) {
		Channel channel = channelsIndex.remove(token);
		if (channel != null) {
			channel.destroy(message);
		}
	}

	public /*synchronized*/ Channel getChannel(SecurityToken token) throws MALException, ElementNotFoundException {
		Channel channel = channelsIndex.get(token);
		if (channel != null) {
			if (channel.getPrefferredPrincipal().equals(token.getPreferredPrincipal()) == false) {
				// the preferred principal has changed, notify the channel about that
				channel.principalChanged();
			}
		} else {
			channel = new Channel(this, token);
			channel.logger = logger;
			channel.exceptionHandler = exceptionHandler;
			channel.messageGenerator = messageGenerator;
			channel.properties = properties;
			channel.serviceContext = serviceContext;
			channelsIndex.put(token, channel);
		}
		return channel;
	}

	void remove(SecurityToken token) {
		channelsIndex.remove(token);
	}

	Collection<Channel> channels() {
		return Collections.unmodifiableCollection(channelsIndex.values());
	}
}