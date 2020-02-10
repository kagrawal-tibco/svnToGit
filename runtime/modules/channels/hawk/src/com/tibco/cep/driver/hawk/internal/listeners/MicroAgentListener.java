package com.tibco.cep.driver.hawk.internal.listeners;

import COM.TIBCO.hawk.console.hawkeye.MicroAgentListMonitorEvent;
import COM.TIBCO.hawk.console.hawkeye.MicroAgentListMonitorListener;

import com.tibco.cep.driver.hawk.HawkConstants;
import com.tibco.cep.driver.hawk.HawkDestination;
import com.tibco.cep.driver.hawk.internal.HawkMonitorEvent;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleSession;

public class MicroAgentListener extends HawkListener implements MicroAgentListMonitorListener {

	public MicroAgentListener(HawkDestination dest, RuleSession session, Logger logger) {
		super(dest, session, logger);
	}

	public void onMicroAgentAdded(MicroAgentListMonitorEvent e) {
		try {
			logger.log(Level.INFO, "*** MicroAgent Added ***");
			HawkMonitorEvent hawkEvent = new HawkMonitorEvent(HawkConstants.EVENT_MICROAGENT_ADDED, e);
			onMessage(hawkEvent, dest, session, logger);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public void onMicroAgentRemoved(MicroAgentListMonitorEvent e) {
		try {
			logger.log(Level.INFO, "*** MicroAgent Removed ***");
			HawkMonitorEvent hawkEvent = new HawkMonitorEvent(HawkConstants.EVENT_MICROAGENT_REMOVED, e);
			onMessage(hawkEvent, dest, session, logger);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
