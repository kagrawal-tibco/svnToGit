package com.tibco.cep.driver.hawk.internal.listeners;

import COM.TIBCO.hawk.console.hawkeye.AlertMonitorEvent;
import COM.TIBCO.hawk.console.hawkeye.AlertMonitorListener;
import COM.TIBCO.hawk.console.hawkeye.ClearAlertEvent;
import COM.TIBCO.hawk.console.hawkeye.PostAlertEvent;

import com.tibco.cep.driver.hawk.HawkConstants;
import com.tibco.cep.driver.hawk.HawkDestination;
import com.tibco.cep.driver.hawk.internal.HawkMonitorEvent;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleSession;

public class AlertListener extends HawkListener implements AlertMonitorListener {

	public AlertListener(HawkDestination dest, RuleSession session, Logger logger) {
		super(dest, session, logger);
	}

	public void onRetransmittedAlert(AlertMonitorEvent e) {
		logger.log(Level.DEBUG, "*** Retransmitted Alert ***");
	}

	public void onAlertMonitorEvent(AlertMonitorEvent e) {
		if (e instanceof PostAlertEvent) {
			logger.log(Level.DEBUG, "*** PostAlertEvent ***");
			
		} else if (e instanceof ClearAlertEvent) {
			logger.log(Level.DEBUG, "*** ClearAlertEvent ***");
		}
		try {
			HawkMonitorEvent hawkEvent = new HawkMonitorEvent(HawkConstants.EVENT_ALERT_MONITOR, e);
			onMessage(hawkEvent, dest, session, logger);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
