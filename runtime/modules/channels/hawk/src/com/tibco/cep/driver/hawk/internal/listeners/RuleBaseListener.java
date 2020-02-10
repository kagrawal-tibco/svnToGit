package com.tibco.cep.driver.hawk.internal.listeners;

import COM.TIBCO.hawk.console.hawkeye.RuleBaseListMonitorEvent;
import COM.TIBCO.hawk.console.hawkeye.RuleBaseListMonitorListener;

import com.tibco.cep.driver.hawk.HawkConstants;
import com.tibco.cep.driver.hawk.HawkDestination;
import com.tibco.cep.driver.hawk.internal.HawkMonitorEvent;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleSession;

public class RuleBaseListener extends HawkListener implements RuleBaseListMonitorListener {

	public RuleBaseListener(HawkDestination dest, RuleSession session, Logger logger) {
		super(dest, session, logger);
	}

	@Override
	public void onRuleBaseAdded(RuleBaseListMonitorEvent ruleBaseEvent) {
		try {
			logger.log(Level.INFO, "*** RuleBase Added ***");
			HawkMonitorEvent hawkEvent = new HawkMonitorEvent(HawkConstants.EVENT_RULEBASE_ADDED, ruleBaseEvent);
			onMessage(hawkEvent, dest, session, logger);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onRuleBaseRemoved(RuleBaseListMonitorEvent ruleBaseEvent) {
		try {
			logger.log(Level.INFO, "*** RuleBase Removed ***");
			HawkMonitorEvent hawkEvent = new HawkMonitorEvent(HawkConstants.EVENT_RULEBASE_REMOVEED, ruleBaseEvent);
			onMessage(hawkEvent, dest, session, logger);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
