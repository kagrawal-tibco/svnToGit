package com.tibco.cep.dashboard.psvr.streaming;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.STATE;
import com.tibco.cep.dashboard.management.ServiceContext;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.util.ChangeableInteger;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class Channel {

	Properties properties;

	Logger logger;
	ExceptionHandler exceptionHandler;
	MessageGenerator messageGenerator;
	ServiceContext serviceContext;

	private SecurityToken token;
//	private Principal prefferredPrincipal;
	private ChannelGroup channelGroup;

	private String name;
	private STATE state;

//	private TokenRoleProfile roleProfile;
	private ElementChangeMediator elementChangeMediator;

	private SubscriptionHandlerIndex subscriptionHandlerIndex;

	private Map<String,ChangeableInteger> subscriptionNameUsageTracker;

	private StreamPublisher streamPublisher;

	Channel(ChannelGroup channelGroup, SecurityToken token) throws MALException, ElementNotFoundException {
		this.channelGroup = channelGroup;
		this.token = token;
//		prefferredPrincipal = this.token.getPreferredPrincipal();
		state = STATE.STOPPED;
//		this.roleProfile = TokenRoleProfile.getInstance(token);
		elementChangeMediator = new ElementChangeMediator(token, this);
//		this.roleProfile.getViewsConfigHelper().addElementChangeListener(elementChangeMediator);
		subscriptionHandlerIndex = new SubscriptionHandlerIndex();
		subscriptionNameUsageTracker = new HashMap<String, ChangeableInteger>();
	}

	SecurityToken getToken(){
		return token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Principal getPrefferredPrincipal() {
		return elementChangeMediator.getPrefferredPrincipal();
	}

	void principalChanged() throws MALException, ElementNotFoundException {
		elementChangeMediator.principalChanged();
	}

	protected void validateState() {
		if (name == null) {
			throw new IllegalStateException("No name set on the channel");
		}
		if (state == null) {
			throw new IllegalStateException(name + " has been destroyed");
		}
	}

	public synchronized void start() {
		validateState();
		if (state != STATE.RUNNING) {
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Starting " + this + " for " + token);
			}
			state = STATE.RUNNING;
		}
	}

	public synchronized void stop() {
		validateState();
		if (state == STATE.RUNNING) {
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Stopping " + this);
			}
			if (streamPublisher != null) {
				streamPublisher.stop("");
			}
			state = STATE.STOPPED;
		}
	}

	public synchronized void pause(String message) {
		validateState();
		if (state == STATE.RUNNING) {
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Pausing " + this);
			}
			if (streamPublisher != null) {
				streamPublisher.pause(message);
			}
			state = STATE.PAUSED;
		}
	}

	public synchronized void resume(String message) {
		validateState();
		if (state == STATE.PAUSED) {
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Resuming " + this);
			}
			if (streamPublisher != null) {
				streamPublisher.resume(message);
			}
			state = STATE.RUNNING;
		}
	}

	public synchronized void destroy(String message) {
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Destroying " + this);
		}
		if (streamPublisher != null) {
			streamPublisher.stop(message);
		}
		unsubscribeAll();
		channelGroup.remove(token);
		state = null;
	}

	public synchronized void subscribe(Map<String, String> subscriptionRequest, PresentationContext ctx) throws StreamingException {
		validateState();
		try {
			SubscriptionHandler handler = SubscriptionInterpreter.getInstance().interpret(token, subscriptionRequest, ctx);
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Subscribing " + handler + " on " + this);
			}
			subscriptionHandlerIndex.addHandler(handler);
			DataSourceUpdateHandler[] updateHandlers = handler.getUpdateHandlers();
			for (DataSourceUpdateHandler dataSourceUpdateHandler : updateHandlers) {
				ChangeableInteger usageCounter = subscriptionNameUsageTracker.get(dataSourceUpdateHandler.getSubscriptionName());
				if (usageCounter == null){
					usageCounter = new ChangeableInteger(0);
					subscriptionNameUsageTracker.put(dataSourceUpdateHandler.getSubscriptionName(),usageCounter);
					dataSourceUpdateHandler.subscribe();
					dataSourceUpdateHandler.startNotifying(this);
				}
				usageCounter.increment();
			}
		} catch (DataException e) {
			String msg = messageGenerator.getMessage("subscription.interpret.data.failure", ctx.getMessageGeneratorArgs(e, name, subscriptionRequest.toString()));
			throw new StreamingException(msg, e);
		} catch (PluginException e) {
			String msg = messageGenerator.getMessage("subscription.interpret.plugin.failure", ctx.getMessageGeneratorArgs(e, name, subscriptionRequest.toString()));
			throw new StreamingException(msg, e);
		}
	}

	public synchronized void unsubscribe(Map<String, String> unsubscriptionRequest) {
		validateState();
		SubscriptionHandler subscriptionHandler = null;
		Iterator<SubscriptionHandler> handlers = subscriptionHandlerIndex.getHandlers().iterator();
		while (handlers.hasNext()) {
			subscriptionHandler = handlers.next();
			if (subscriptionHandler.isEquivalentTo(unsubscriptionRequest) == true) {
				break;
			} else {
				subscriptionHandler = null;
			}
		}
		if (subscriptionHandler != null) {
			unsubscribe(subscriptionHandler);
		}
	}

	private void unsubscribe(SubscriptionHandler subscriptionHandler) {
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Unsubscribing " + subscriptionHandler + " from " + this);
		}
		DataSourceUpdateHandler[] updateHandlers = subscriptionHandler.getUpdateHandlers();
		for (DataSourceUpdateHandler dataSourceUpdateHandler : updateHandlers) {
			ChangeableInteger usageCounter = subscriptionNameUsageTracker.get(dataSourceUpdateHandler.getSubscriptionName());
			usageCounter.decrement();
			if (usageCounter.intValue() == 0){
				dataSourceUpdateHandler.stopNotifying(this);
				subscriptionNameUsageTracker.remove(dataSourceUpdateHandler.getSubscriptionName());
			}
			if (dataSourceUpdateHandler instanceof UpdateDataProvider) {
				// we are dealing with a update handler which is specific to
				// this user
				// make sure no other chanel is dependent on this update handler
				if (dataSourceUpdateHandler.interestedChannels.isEmpty() == true) {
//					boolean removedHandler = DataSourceUpdateHandlerFactory.getInstance().removeHandler(dataSourceUpdateHandler);
//					if (removedHandler == true) {
						dataSourceUpdateHandler.shutdown();
//					}
				}
			}
		}
		subscriptionHandlerIndex.removeHandler(subscriptionHandler);
		subscriptionHandler.shutdown();
	}

	public synchronized void unsubscribeAll(){
		Collection<SubscriptionHandler> existingHandlers = new LinkedList<SubscriptionHandler>(subscriptionHandlerIndex.getHandlers());
		for (SubscriptionHandler subscriptionHandler : existingHandlers) {
			unsubscribe(subscriptionHandler);
		}
	}

	public synchronized void startStreaming(Streamer streamer, Map<String, String> streamingRequest) {
		validateState();
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Starting to stream " + streamingRequest + " using " + streamer + " on " + this);
		}
		if (streamPublisher != null){
			streamPublisher.stop(null);
		}
		streamPublisher = createStreamPublisher(streamer, streamingRequest);
		streamPublisher.start();
	}

	private StreamPublisher createStreamPublisher(Streamer streamer, Map<String, String> streamingRequest) {
		MapBasedProperties publisherProps = new MapBasedProperties(properties, streamingRequest);
		StreamPublisher publisher = StreamPublisherFactory.getPublisher(publisherProps, logger, exceptionHandler, messageGenerator);
		publisher.name = name;
		publisher.token = token;
//		publisher.preferredPrincipal = getPrefferredPrincipal();
		publisher.streamer = streamer;
		publisher.subscriptionHandlerIndex = subscriptionHandlerIndex;
		publisher.init(publisherProps);
		return publisher;
	}

	public synchronized void stopStreaming() {
		validateState();
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Stopping streaming on " + this);
		}
		if (streamPublisher != null) {
			// stop streaming
			streamPublisher.stop("");
		}
	}

	public void lineUpForProcessing(String subscriptionName) {
		// a streamPublisher is created only when someone calls startStreaming
		// so till that happens we will ignore all streaming request coming from
		// datasourceupdatehandlers. Same case when streaming has been stopped
		if (streamPublisher == null || streamPublisher.isRunning() == false) {
			return;
		}
		streamPublisher.lineUpForProcessing(subscriptionName);
	}

	StreamPublisher getPublisher(){
		return streamPublisher;
	}

	STATE getState(){
		return state;
	}

	public synchronized Collection<SubscriptionHandler> getSubscriptions(){
		return new LinkedList<SubscriptionHandler>(subscriptionHandlerIndex.getHandlers());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Channel[");
		sb.append("name=" + name);
		sb.append(",token=" + token);
		sb.append(",userid=" + token.getUserID());
		sb.append(",preferredrole=" + token.getPreferredPrincipal());
		sb.append("]");
		return sb.toString();
	}


}
