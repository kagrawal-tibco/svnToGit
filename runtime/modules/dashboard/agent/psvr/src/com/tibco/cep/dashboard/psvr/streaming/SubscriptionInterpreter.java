package com.tibco.cep.dashboard.psvr.streaming;

import java.util.Map;

import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.security.SecurityToken;

public final class SubscriptionInterpreter {

	private static SubscriptionInterpreter instance;

	public static final synchronized SubscriptionInterpreter getInstance() {
		if (instance == null) {
			instance = new SubscriptionInterpreter();
		}
		return instance;
	}

	private SubscriptionInterpreter() {
		
	}
	
	public SubscriptionHandler interpret(SecurityToken token,Map<String,String> subscription, PresentationContext ctx) throws StreamingException, DataException, PluginException{
		try {
			SubscriptionHandler subscriptionHandler = createSubscriptionHandler(subscription);
			subscriptionHandler.token = token;
			subscriptionHandler.preferredRole = token.getPreferredPrincipal();
			subscriptionHandler.tokenRoleProfile = TokenRoleProfile.getInstance(token);
			subscriptionHandler.process(subscription, ctx);
			return subscriptionHandler;
		} catch (MALException e) {
			throw new RuntimeException(e);
		} catch (ElementNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private SubscriptionHandler createSubscriptionHandler(Map<String, String> subscription) {
		if (subscription.containsKey("componenttype") == true){
			return new ComponentTypeSubscriptionHandler();
		}
		else if (subscription.containsKey("componentname") == true){
			return new ComponentSubscriptionHandler();
		}
		else if (subscription.containsKey("componentid") == true){
			return new ComponentSubscriptionHandler();
		}
		throw new IllegalArgumentException("Unknown subscription request");
	}
}
