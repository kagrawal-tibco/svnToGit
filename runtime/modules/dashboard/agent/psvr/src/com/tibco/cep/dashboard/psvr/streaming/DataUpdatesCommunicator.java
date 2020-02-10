package com.tibco.cep.dashboard.psvr.streaming;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.naming.NamingException;

import com.tibco.cep.dashboard.management.ManagementClient;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.ManagementUtils;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandlerFactory;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;
import com.tibco.cep.kernel.service.logging.Level;

public class DataUpdatesCommunicator extends AbstractHandlerFactory {

	private static DataUpdatesCommunicator instance;

	public static final synchronized DataUpdatesCommunicator getInstance() {
		if (instance == null) {
			throw new IllegalStateException("DataUpdatesCommunicator has not been initialized");
		}
		return instance;
	}

	private ManagementClient managementClient;
	private String serverName;

	private Map<String, PubSubHandler> handlersIndex;

	DataUpdatesCommunicator() {
		super("dataupdatescommunicator", "Data Updates Communicator", ResolverType.PUB_SUB_HANDLER);
		instance = this;
	}
	
	@Override
	protected void doInit() throws ManagementException {
		try {
			managementClient = (ManagementClient) ManagementUtils.getContext().lookup("management");
		} catch (NamingException e) {
			String msg = messageGenerator.getMessage("updatescommunicator.management.lookup.failure");
			throw new ManagementException(msg, e);
		}
		serverName = managementClient.getName();
		handlersIndex = new HashMap<String, PubSubHandler>();
	}

	public void subscribe(String subscriptionName, String contextName, MALSourceElement sourceElement, String condition) throws StreamingException {
		if (logger.isEnabledFor(Level.DEBUG) == true){
			logger.log(Level.DEBUG,"Attempting to subscribe[name=%s,condition=%s]", subscriptionName, condition);
		}
		try {
			PubSubHandler handler = (PubSubHandler) getHandler(sourceElement);
			handler.subscribe(subscriptionName, contextName, sourceElement, condition);
			handlersIndex.put(subscriptionName, handler);
			if (logger.isEnabledFor(Level.DEBUG) == true){
				logger.log(Level.DEBUG,"Subscribed[name=%s,condition=%s] using %s", subscriptionName, condition, handler.toString());
			}
		} catch (PluginException e) {
			String msg = messageGenerator.getMessage("updatescommunicator.pushsubscription.addsubscription.failure", new MessageGeneratorArgs(e, serverName, subscriptionName));
			throw new StreamingException(msg, e);
		}
	}

	public void unsubscribe(String subscriptionName) throws StreamingException {
		PubSubHandler pubSubHandler = handlersIndex.get(subscriptionName);
		if (logger.isEnabledFor(Level.DEBUG) == true){
			logger.log(Level.DEBUG,"Attempting to unsubscribe[name=%s] using %s", subscriptionName, String.valueOf(pubSubHandler));
		}
		if (pubSubHandler != null) {
			pubSubHandler.unsubscribe(subscriptionName);
		}
		else {
			logger.log(Level.WARN,"Could not find subscription processor for [name=%s]", subscriptionName);
		}
	}
	
	@Override
	protected boolean doStop() {
		HashSet<PubSubHandler> handlers = new HashSet<PubSubHandler>(handlersIndex.values());
		for (PubSubHandler handler : handlers) {
			try {
				handler.shutdown();
			} catch (NonFatalException e) {
				String msg = messageGenerator.getMessage("updatescommunicator.pushsubscriptionhandler.shutdown.failure", new MessageGeneratorArgs(e, handler.getClass().getName()));
				exceptionHandler.handleException(msg, e, Level.WARN);
			}
		}
		handlersIndex.clear();
		return true;
	}

}