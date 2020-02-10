package com.tibco.cep.driver.hawk.internal.listeners;

import COM.TIBCO.hawk.talon.MicroAgentData;
import COM.TIBCO.hawk.talon.MicroAgentException;
import COM.TIBCO.hawk.talon.Subscription;
import COM.TIBCO.hawk.talon.SubscriptionHandler;

import com.tibco.cep.driver.hawk.HawkDestination;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleSession;

public class HawkSubscriber extends HawkListener implements SubscriptionHandler{
	
	public HawkSubscriber(HawkDestination dest, RuleSession session,
			Logger logger) {
		super(dest, session, logger);
	}

	public void onData(Subscription s, MicroAgentData mad) {
		Object maData = mad.getData();
		if (maData != null) {
			try {
				onMessage(maData,dest,session,logger);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void onError(Subscription s, MicroAgentException e) {
		logger.log(Level.ERROR, "ERROR occured during subscription: " + e);
	}

	public void onErrorCleared(Subscription s) {
		logger.log(Level.ERROR, "ERROR cleared resuming subscription: " + s);
	}

	public void onTermination(Subscription s, MicroAgentException e) {
		logger.log(Level.ERROR, "Subscription Terminated: " + e);
	}

}
