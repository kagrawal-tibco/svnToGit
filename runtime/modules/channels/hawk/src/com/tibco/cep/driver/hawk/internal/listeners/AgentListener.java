package com.tibco.cep.driver.hawk.internal.listeners;

import COM.TIBCO.hawk.console.hawkeye.AgentMonitorEvent;
import COM.TIBCO.hawk.console.hawkeye.AgentMonitorListener;

import com.tibco.cep.driver.hawk.HawkConstants;
import com.tibco.cep.driver.hawk.HawkDestination;
import com.tibco.cep.driver.hawk.internal.HawkMonitorEvent;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleSession;

public class AgentListener extends HawkListener implements AgentMonitorListener {

	public AgentListener(HawkDestination dest, RuleSession session, Logger logger) {
		super(dest, session, logger);
	}

	public void onAgentAlive(AgentMonitorEvent e) {
		try {
			logger.log(Level.INFO, "*** Agent Alive ***");
			HawkMonitorEvent hawkEvent = new HawkMonitorEvent(HawkConstants.EVENT_AGENT_ALIVE, e);
			onMessage(hawkEvent, dest, session, logger);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void onAgentExpired(AgentMonitorEvent e) {
		logger.log(Level.INFO, "*** Agent Expired ***");
		try {
			HawkMonitorEvent hawkEvent = new HawkMonitorEvent(HawkConstants.EVENT_AGENT_EXPIRED, e);
			onMessage(hawkEvent, dest, session, logger);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
