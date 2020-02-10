package com.tibco.cep.driver.hawk.internal.listeners;

import COM.TIBCO.hawk.console.hawkeye.WarningExceptionEvent;
import COM.TIBCO.hawk.console.hawkeye.WarningExceptionListener;

import com.tibco.cep.driver.hawk.HawkConstants;
import com.tibco.cep.driver.hawk.HawkDestination;
import com.tibco.cep.driver.hawk.internal.HawkMonitorEvent;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleSession;

public class WarningListener extends HawkListener implements WarningExceptionListener {

	public WarningListener(HawkDestination dest, RuleSession session, Logger logger) {
		super(dest, session, logger);
	}

	@Override
	public void onWarningExceptionEvent(WarningExceptionEvent we) {
		try {
			logger.log(Level.INFO, "*** Warning  ***");
			HawkMonitorEvent hawkEvent = new HawkMonitorEvent(HawkConstants.EVENT_WARNING, we);
			onMessage(hawkEvent, dest, session, logger);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
