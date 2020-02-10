// (c) Copyright 2001,2002 TIBCO Software Inc.  All rights reserved.
// LEGAL NOTICE:  This source code is provided to specific authorized end
// users pursuant to a separate license agreement.  You MAY NOT use this
// source code if you do not have a separate license from TIBCO Software
// Inc.  Except as expressly set forth in such license agreement, this
// source code, or any portion thereof, may not be used, modified,
// reproduced, transmitted, or distributed in any form or by any means,
// electronic or mechanical, without written permission from  TIBCO
// Software Inc.

package com.tibco.hawk.jshma.plugin;

import java.util.Date;

import COM.TIBCO.hawk.console.hawkeye.AlertState;
import COM.TIBCO.hawk.publisher.PublisherAlert;

import com.tibco.hawk.jshma.util.NamedTabularData;

/**
 * This class initiates a subscription of TIBCO Hawk events.
 * <p>
 * The data received in onData call include the following columns: "TimeStamp",
 * "Action", "AgentName", "MicroAgentName", "State", "RuleBase", "Source", "ID",
 * "Text".
 */
public class HawkEventSubscription extends HawkEventTrackerBase {

	private static String sClassName = HawkEventSubscription.class.getName();

	private HawkSubscriptionListener mListener;

	/**
	 * Instantiate a subscription of all TIBCO Hawk events
	 * 
	 * @param console
	 *            The Hawk Console handle object
	 * @param listener
	 *            the instance implements HawkSubscriptionListener to receive
	 *            onData callback.
	 */
	public HawkEventSubscription(HawkConsoleBase console, HawkSubscriptionListener listener)
			throws HawkConsoleException {
		mListener = listener;
		HawkEventTrackerBaseInit(console, null, null, false, false, null, null);
	}

	NamedTabularData toNamedTabularData(String action, String agent, String microAgent, Date timestamp,
			PublisherAlert alert) {
		Object[] r = new Object[sHistColNames.length];
		r[1] = action; // action
		r[2] = agent; // agent
		r[3] = microAgent == null ? "" : microAgent; // ma
		if (timestamp instanceof Date) {
			r[0] = timestamp;
			for (int i = 4; i < sHistColNames.length; i++)
				r[i] = "";
			r[7] = new Long(-1);
		} else {
			PublisherAlert a = (PublisherAlert) alert;
			switch (a.mAlertState) {
			case AlertState.ALERT_HIGH:
				r[4] = "alert-high";
				break;

			case AlertState.ALERT_MEDIUM:
				r[4] = "alert-medium";
				break;

			case AlertState.ALERT_LOW:
				r[4] = "alert-low";
				break;

			case AlertState.NO_ALERT:
				r[4] = "";
				break;
			}
			r[0] = new Date(a.mTimeGenerated);
			r[5] = a.mRuleBaseName == null ? "" : a.mRuleBaseName;
			r[6] = a.mDataSource == null ? "" : a.mDataSource;
			r[7] = new Long(a.mAlertID);
			r[8] = a.mAlertText == null ? "" : a.mAlertText;
		}
		NamedTabularData result = new NamedTabularData(sHistColNames, new Object[][] { r });

		return result;

	}

	void add(String agentName) {
		mListener.onData(agentName, null, toNamedTabularData(AGENT_ALIVE, agentName, null, new Date(), null));

	}

	void add(String agentName, String maName) {
		mListener.onData(agentName, maName, toNamedTabularData(MICROAGENT_ADDED, agentName, maName, new Date(), null));

	}

	void add(String agentName, String maName, PublisherAlert alert) {
		mListener.onData(agentName, maName, toNamedTabularData(ALERT_ADDED, agentName, maName, null, alert));
	}

	void remove(String agentName) {
		mListener.onData(agentName, null, toNamedTabularData(AGENT_DEAD, agentName, null, new Date(), null));
	}

	void remove(String agentName, String maName) {
		mListener
				.onData(agentName, maName, toNamedTabularData(MICROAGENT_REMOVED, agentName, maName, new Date(), null));

	}

	void remove(String agentName, String maName, PublisherAlert alert) {
		mListener.onData(agentName, maName, toNamedTabularData(ALERT_CLEARED, agentName, maName, null, alert));
	}

}
