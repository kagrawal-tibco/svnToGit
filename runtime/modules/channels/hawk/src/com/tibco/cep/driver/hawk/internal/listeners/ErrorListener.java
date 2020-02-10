package com.tibco.cep.driver.hawk.internal.listeners;

import COM.TIBCO.hawk.console.hawkeye.ErrorExceptionEvent;
import COM.TIBCO.hawk.console.hawkeye.ErrorExceptionListener;

import com.tibco.cep.driver.hawk.HawkConstants;
import com.tibco.cep.driver.hawk.HawkDestination;
import com.tibco.cep.driver.hawk.internal.HawkMonitorEvent;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleSession;

public class ErrorListener extends HawkListener implements ErrorExceptionListener {

	public ErrorListener(HawkDestination dest, RuleSession session, Logger logger) {
		super(dest, session, logger);
	}

	public void onErrorExceptionEvent(ErrorExceptionEvent ee) {
		try {
			logger.log(Level.INFO, "*** Error or Exception occured ***");
			HawkMonitorEvent hawkEvent = new HawkMonitorEvent(HawkConstants.EVENT_ERROR, ee);
			onMessage(hawkEvent, dest, session, logger);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
