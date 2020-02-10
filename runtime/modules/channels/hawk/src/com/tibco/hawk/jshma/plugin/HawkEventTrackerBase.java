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
import java.util.Enumeration;
import java.util.Vector;

import COM.TIBCO.hawk.console.hawkeye.AlertState;
import COM.TIBCO.hawk.publisher.PublisherAlert;
import COM.TIBCO.hawk.utilities.misc.TimeTool;
import COM.TIBCO.hawk.utilities.trace.Trace;

import com.tibco.hawk.jshma.util.ContextControl;
import com.tibco.hawk.jshma.util.Evm;
import com.tibco.hawk.jshma.util.EvmInterface;
import com.tibco.hawk.jshma.util.Filter;
import com.tibco.hawk.jshma.util.NamedArray;
import com.tibco.hawk.jshma.util.NamedTabularData;
import com.tibco.hawk.jshma.util.Queue;

/**
 * This class is used to track TIBCO Hawk events based on TIBCO Hawk MicroAgent
 * names following a specified pattern or more complicated Filter Criteria.
 * <p>
 * The application can inquire the "filtered" active alerts anytime or handle
 * the events itself. The advantage of using this "Tracker" is that the
 * application does not need to know when or where the TIBCO Hawk MicroAgents
 * will be run on.
 */
public class HawkEventTrackerBase implements EvmInterface {
	public static final String ALERT_ADDED = new String("ALERT_ADDED");
	public static final String ALERT_CLEARED = new String("ALERT_CLEARED");
	public static final String AGENT_ALIVE = new String("AGENT_ALIVE");
	public static final String AGENT_DEAD = new String("AGENT_DEAD");
	public static final String MICROAGENT_ADDED = new String("MA_ADDED");
	public static final String MICROAGENT_REMOVED = new String("MA_REMOVED");

	static String sClassName = HawkEventTrackerBase.class.getName();
	static String[] sColNames = PluginResources.getResources().getStringArray(PluginResources.ALERT_HIST_COL_NAMES);
	static String[] sHistColNames = PluginResources.getResources().getStringArray(PluginResources.EVENT_HIST_COL_NAMES);
	static String sDefaultTimeFormat = "hh:mm:ss MMM dd, yyyy";

	/**
	 * TIBCO Hawk Trace handler
	 */
	protected Trace mTrace = null;

	private HawkConsoleBase mConsole = null;
	private Filter[] mANDFilters = null;
	private Filter[] mORFilters = null;
	private Vector mAlertList = new Vector();
	private boolean mKeepActiveAlerts = false;
	private boolean mDeliverEvents = false;
	private Evm mEvm = null;
	private String mEvent = null;
	private Queue mQueue = null;

	HawkEventTrackerBase() {
	};

	/**
	 * Instantiate a "Tracker" for all the events associated with MicroAgents
	 * corresponding to the specified name pattern.
	 * 
	 * @param console
	 *            The Hawk Console handle object
	 * @param pattern
	 *            The MicroAgent name pattern
	 */
	public HawkEventTrackerBase(HawkConsoleBase console, String pattern) throws HawkConsoleException {
		HawkEventTrackerBaseInit(console, pattern);
	}

	/**
	 * Instantiate a "Tracker" with Filters. The alerts for all the MicorAgents
	 * satisfying the "AND" Filters and "OR" Filters will be tracked. The AND
	 * Filters are applied first, then the OR Filters. Both the ANDFilters and
	 * ORFilters parameters are optional (specify null if not applicable). The
	 * Fileters that can be applied include: "Action", "AgentName",
	 * "MicroAgentName", "State", "TimeStamp", "RuleBase", "Source", "Text" They
	 * are all "String" Filters except the "TimeStamp" is a "Date" Filter. The
	 * valid actions include "ALERT_ADDED", "ALERT_CLEARED", "AGENT_ALIVE",
	 * "AGENT_DEAD", "MA_ADDED", and "MA_REMOVED".
	 * 
	 * @param console
	 *            The Hawk Console handle object
	 * @param ANDFilters
	 *            The "AND" Filters for MicroAgent filtering
	 * @param ORFilters
	 *            The "OR" Filters for MicroAgent filtering
	 * @param keepActiveAlerts
	 *            true if the Tracker should keep track of the current active
	 *            alerts. Then the current active alerts can be retrieved
	 *            anytime with the method getActiveAlerts(); otherwise the
	 *            getActiveAlerts() would return null.
	 * @param deliverEvents
	 *            true if the application wants to handle the alert Histories
	 *            itself. That is it would use getData() to retrieve alert data
	 *            according to the order it's received. The alert data would
	 *            include normal alert data as well as Hawk Agent and Microagent
	 *            add or removal. The event and evm parameters are applicable
	 *            only if the deliverEvents parameter is set to true. Warning:
	 *            The data must be retrieved frequently to prevent running out
	 *            of memory.
	 * 
	 * @param event
	 *            Event name (used by <code>Evm</code>), specify null if Event
	 *            Manager is not used.
	 * @param evm
	 *            Event manager, specify null if Event Manager is not used.
	 * @see Filter
	 */

	public HawkEventTrackerBase(HawkConsoleBase console, Filter[] ANDFilters, Filter[] ORFilters,
			boolean keepActiveAlerts, boolean deliverEvents, String event, Evm evm) throws HawkConsoleException {
		HawkEventTrackerBaseInit(console, ANDFilters, ORFilters, keepActiveAlerts, deliverEvents, event, evm);
	}

	void HawkEventTrackerBaseInit(HawkConsoleBase console, String pattern) throws HawkConsoleException {
		if (console == null) {
			throw new HawkConsoleException("The 'console' parameter can not be null.");
		}
		if (pattern == null) {
			throw new HawkConsoleException("The 'pattern' parameter can not be null.");
		}
		mTrace = ContextControl.getTrace();
		mANDFilters = new Filter[] { new Filter(PluginResources.getResources().getString(PluginResources.MA_NAME),
				"contain", pattern) };

		mConsole = console;
		mConsole.addAlertTracker(this);
		mKeepActiveAlerts = true;
		mDeliverEvents = false;
	}

	void HawkEventTrackerBaseInit(HawkConsoleBase console, Filter[] ANDFilters, Filter[] ORFilters,
			boolean keepActiveAlerts, boolean deliverEvents, String event, Evm evm) throws HawkConsoleException {
		if (console == null) {
			throw new HawkConsoleException("The 'console' parameter can not be null.");
		}
		/*
		 * if (ANDFilters == null && ORFilters == null) { throw new
		 * HawkConsoleException(
		 * "The 'ANDFilters' parameter and 'ORFilters' can not both be null.");
		 * }
		 */

		mTrace = ContextControl.getTrace();

		if (ANDFilters != null)
			for (int i = 0; i < ANDFilters.length; i++) {
				boolean found = false;
				for (int j = 0; j < sHistColNames.length; j++) {
					if (ANDFilters[i].getName().equals(sHistColNames[j])) {
						found = true;
						break;
					}
				}
				if (!found) {
					throw new HawkConsoleException("The 'ANDFilters' name '" + ANDFilters[i].getName()
							+ "' is invalid.");
				}
			}
		mANDFilters = ANDFilters;

		if (ORFilters != null)
			for (int i = 0; i < ORFilters.length; i++) {
				boolean found = false;
				for (int j = 0; j < sHistColNames.length; j++) {
					if (ORFilters[i].getName().equals(sHistColNames[j])) {
						found = true;
						break;
					}
				}
				if (!found) {
					throw new HawkConsoleException("The 'ORFilters' name '" + ORFilters[i].getName() + "' is invalid.");
				}
			}
		mORFilters = ORFilters;

		mConsole = console;
		mConsole.addAlertTracker(this);
		mKeepActiveAlerts = keepActiveAlerts;
		mDeliverEvents = deliverEvents;
		if (mDeliverEvents) {
			mEvent = event;
			mEvm = evm;
			if (evm != null && event != null)
				mEvm.addEvent(this, mEvent);
			mQueue = new Queue(10);
		}
	}

	private boolean applyANDFilter(String agentName, String maName, PublisherAlert alert, String action) {
		if (mANDFilters == null)
			return true;
		for (int i = 0; i < mANDFilters.length; i++) {
			Filter f = mANDFilters[i];
			if (f.getName().equals("Action") && action != null) {
				if (!f.validate(action))
					return false;
			} else if (f.getName().equals("AgentName")) {
				if (!f.validate(agentName))
					return false;
			} else if (f.getName().equals("MicroAgentName") && maName != null) {
				if (!f.validate(maName))
					return false;
			} else if (f.getName().equals("State") && alert != null) {
				String state = null;
				switch (alert.mAlertState) {
				case AlertState.ALERT_HIGH:
					state = "alert-high";
					break;

				case AlertState.ALERT_MEDIUM:
					state = "alert-medium";
					break;

				case AlertState.ALERT_LOW:
					state = "alert-low";
					break;

				case AlertState.NO_ALERT:
					state = "";
					break;
				}
				if (!f.validate(state))
					return false;
			} else if (f.getName().equals("TimeStamp")) {
				if (alert == null) {
					if (!f.validate(new Date()))
						return false;
				} else {
					if (!f.validate(new Date(alert.mTimeGenerated)))
						return false;
				}
			} else if (f.getName().equals("RuleBase") && alert != null) {
				if (!f.validate(alert.mRuleBaseName))
					return false;
			} else if (f.getName().equals("Source") && alert != null) {
				if (!f.validate(alert.mDataSource))
					return false;
			} else if (f.getName().equals("Text") && alert != null) {
				if (!f.validate(alert.mAlertText))
					return false;
			}
		}
		return true;
	}

	private boolean applyORFilter(String agentName, String maName, PublisherAlert alert, String action) {
		if (mORFilters == null)
			return true;
		for (int i = 0; i < mORFilters.length; i++) {
			Filter f = mORFilters[i];
			if (f.getName().equals("Action") && action != null) {
				if (f.validate(action))
					return true;
			} else if (f.getName().equals("AgentName")) {
				if (f.validate(agentName))
					return true;
			} else if (f.getName().equals("MicroAgentName") && maName != null) {
				if (f.validate(maName))
					return true;
			} else if (f.getName().equals("State") && alert != null) {
				String state = null;
				switch (alert.mAlertState) {
				case AlertState.ALERT_HIGH:
					state = "alert-high";
					break;

				case AlertState.ALERT_MEDIUM:
					state = "alert-medium";
					break;

				case AlertState.ALERT_LOW:
					state = "alert-low";
					break;

				case AlertState.NO_ALERT:
					state = "";
					break;
				}
				if (f.validate(state))
					return true;
			} else if (f.getName().equals("TimeStamp")) {
				if (alert == null) {
					if (f.validate(new Date()))
						return true;
				} else {
					if (f.validate(new Date(alert.mTimeGenerated)))
						return true;
				}
			} else if (f.getName().equals("RuleBase") && alert != null) {
				if (f.validate(alert.mRuleBaseName))
					return true;
			} else if (f.getName().equals("Source") && alert != null) {
				if (f.validate(alert.mDataSource))
					return true;
			} else if (f.getName().equals("Text") && alert != null) {
				if (f.validate(alert.mAlertText))
					return true;
			}
		}
		return false;
	}

	void add(String agentName) {
		if (mDeliverEvents && applyANDFilter(agentName, null, null, AGENT_ALIVE)
				&& applyORFilter(agentName, null, null, AGENT_ALIVE)) {
			Object[] data = new Object[] { agentName, null, new Date(), AGENT_ALIVE };
			mQueue.enqueue(data);
			if (mEvm != null)
				mEvm.recheck();
		}

	}

	void add(String agentName, String maName) {

		if (mDeliverEvents && applyANDFilter(agentName, maName, null, MICROAGENT_ADDED)
				&& applyORFilter(agentName, maName, null, MICROAGENT_ADDED)) {
			Object[] data = new Object[] { agentName, maName, new Date(), MICROAGENT_ADDED };
			mQueue.enqueue(data);
			if (mEvm != null)
				mEvm.recheck();
		}

	}

	void add(String agentName, String maName, PublisherAlert alert) {
		if (mKeepActiveAlerts && applyANDFilter(agentName, maName, alert, null)
				&& applyORFilter(agentName, maName, alert, null)) {

			Object[] elem = null;
			boolean done = false;
			Enumeration e = mAlertList.elements();
			while (e.hasMoreElements()) {
				elem = (Object[]) e.nextElement();
				if (agentName.equals((String) elem[0]) && maName.equals((String) elem[1])
						&& alert.mAlertID == ((PublisherAlert) elem[2]).mAlertID) {
					elem[2] = alert;
					done = true;
					break;
				}
			}
			if (!done) {
				elem = new Object[] { agentName, maName, alert };
				mAlertList.addElement(elem);
			}
		}

		if (mDeliverEvents && applyANDFilter(agentName, maName, alert, ALERT_ADDED)
				&& applyORFilter(agentName, maName, alert, ALERT_ADDED)) {
			Object[] data = new Object[] { agentName, maName, alert, ALERT_ADDED };
			mQueue.enqueue(data);
			if (mEvm != null)
				mEvm.recheck();
		}

	}

	void remove(String agentName) {
		if (mKeepActiveAlerts) {
			Object[] elem = null;
			Enumeration e = mAlertList.elements();
			while (e.hasMoreElements()) {
				elem = (Object[]) e.nextElement();
				if (agentName.equals((String) elem[0])) {
					mAlertList.removeElement(elem);
				}
			}
		}

		if (mDeliverEvents && applyANDFilter(agentName, null, null, AGENT_DEAD)
				&& applyORFilter(agentName, null, null, AGENT_DEAD)) {
			Object[] data = new Object[] { agentName, null, new Date(), AGENT_DEAD };
			mQueue.enqueue(data);
			if (mEvm != null)
				mEvm.recheck();
		}

	}

	void remove(String agentName, String maName) {
		if (mKeepActiveAlerts) {
			Object[] elem = null;
			Enumeration e = mAlertList.elements();
			while (e.hasMoreElements()) {
				elem = (Object[]) e.nextElement();
				if (agentName.equals((String) elem[0]) && maName.equals((String) elem[1])) {
					mAlertList.removeElement(elem);
				}
			}
		}

		if (mDeliverEvents && applyANDFilter(agentName, maName, null, MICROAGENT_REMOVED)
				&& applyORFilter(agentName, maName, null, MICROAGENT_REMOVED)) {
			Object[] data = new Object[] { agentName, maName, new Date(), MICROAGENT_REMOVED };
			mQueue.enqueue(data);
			if (mEvm != null)
				mEvm.recheck();
		}

	}

	void remove(String agentName, String maName, PublisherAlert alert) {
		if (mKeepActiveAlerts) {
			Object[] elem = null;
			Enumeration e = mAlertList.elements();
			while (e.hasMoreElements()) {
				elem = (Object[]) e.nextElement();
				if (agentName.equals((String) elem[0]) && maName.equals((String) elem[1])
						&& alert.mAlertID == ((PublisherAlert) elem[2]).mAlertID) {
					mAlertList.removeElement(elem);
					break;
				}
			}
		}

		if (mDeliverEvents && applyANDFilter(agentName, maName, alert, ALERT_CLEARED)
				&& applyORFilter(agentName, maName, alert, ALERT_CLEARED)) {
			Object[] data = new Object[] { agentName, maName, alert, ALERT_CLEARED };
			mQueue.enqueue(data);
			if (mEvm != null)
				mEvm.recheck();
		}

	}

	/**
	 * Get the TIBCO Hawk active alerts that have been tracked.
	 * 
	 * @return NamedTabularData with the following columns: "AgentName",
	 *         "MicroAgentName", "State", "TimeStamp", "RuleBase", "Source",
	 *         "Text", "Properties".
	 *         <p>
	 *         The TimeStamp is presented with default format
	 *         "hh:mm:ss MMM dd, yyyy".
	 *         <p>
	 *         The properties are in Hashtable data type.
	 */

	public NamedTabularData getActiveAlerts() {
		return getActiveAlerts(sDefaultTimeFormat, null, null, true);
	}

	/**
	 * Get the TIBCO Hawk active alerts that have been tracked.
	 * 
	 * @param timeStampFormat
	 *            the format to present TimeStamp
	 * @param propertyFormat
	 *            If null, the properties are returned as Hashtables. Otherwise,
	 *            they are formatted according to the specified format. (The
	 *            property name is {0} and the value is {1} )
	 * @param propertyLineSeparator
	 *            This is used only when the propertyFormat is specified. The
	 *            property lines are separated with this separator. If not
	 *            specified, line feed character is used.
	 * @param userPropertiesOnly
	 *            If true and propertyFormat is not null, only the properties
	 *            starts with "Action." will be returned in the "Properties"
	 *            field and the "Action." prefix will be removed from property
	 *            name.
	 * @return NamedTabularData with the following columns: "AgentName",
	 *         "MicroAgentName", "State", "TimeStamp", "RuleBase", "Source",
	 *         "Text", "Properties", "ID".
	 *         <p>
	 *         The TimeStamp is presented with the specified format.
	 */

	public NamedTabularData getActiveAlerts(String timeStampFormat, String propertyFormat,
			String propertyLineSeparator, boolean userPropertiesOnly) {
		if (!mKeepActiveAlerts)
			return null;

		String[][] r = new String[mAlertList.size()][sColNames.length];
		Object[] elem = null;
		int i = 0;
		Enumeration e = mAlertList.elements();
		while (e.hasMoreElements()) {
			elem = (Object[]) e.nextElement();
			Object[] data = r[i];
			data[0] = elem[0];
			data[1] = elem[1];
			PublisherAlert a = (PublisherAlert) elem[2];
			switch (a.mAlertState) {
			case AlertState.ALERT_HIGH:
				data[2] = "alert-high";
				break;

			case AlertState.ALERT_MEDIUM:
				data[2] = "alert-medium";
				break;

			case AlertState.ALERT_LOW:
				data[2] = "alert-low";
				break;

			case AlertState.NO_ALERT:
				data[2] = "";
				break;
			}
			data[3] = TimeTool.localtime(a.mTimeGenerated, timeStampFormat);
			data[4] = a.mRuleBaseName;
			data[5] = a.mDataSource;
			data[6] = a.mAlertText;
			data[7] = propertyFormat == null ? (Object) a.mPostAlertEventProperties : (Object) HawkConsoleBase
					.propertyToString(a.mPostAlertEventProperties, propertyFormat, propertyLineSeparator,
							userPropertiesOnly);
			data[8] = new Long(a.mAlertID);
			i++;
		}
		return new NamedTabularData(sColNames, r);
	}

	/**
	 * Checking whether the specified event is set - This method is typically
	 * used by Evm.
	 * 
	 * @param event
	 *            The event to be checked. Specify null if Event Manager is not
	 *            used.
	 * @return true if the specified event is set. (In this case, it means there
	 *         are alert data available.) false if no data available or the
	 *         specified event is not associated with this subscription.
	 */

	public boolean isSet(String event) {

		if (((mEvent != null) && ((event == null) || (!event.equals(mEvent)))) || ((mEvent == null) && (event != null)))
			return false;
		if (mQueue == null || mQueue.length() <= 0)
			return false;
		else
			return true;
	}

	/**
	 * Get the first unretrieved TIBCO Hawk event data.
	 * 
	 * @return NamedArray with the following columns: "TimeStamp", "Action",
	 *         "AgentName", "MicroAgentName", "State", "RuleBase", "Source",
	 *         "ID", "Text", "Properties". Except the "TimeStamp" is using
	 *         "Date" type, and "Properties" in Hashtable data type, all the
	 *         others are
	 *         <p>
	 *         If there is no data available, this method will be blocked if Evm
	 *         is not used or return null if Evm is used.
	 */

	public NamedArray getData() {
		if (!mDeliverEvents)
			return null;
		if (mEvm != null && (!isSet(mEvent)))
			return null;
		while (!isSet(mEvent)) {
			try {
				Thread.sleep(2000);
			} catch (Exception ee) {
			}
			;
		}

		Object[] r = new Object[sHistColNames.length];
		Object[] data = (Object[]) mQueue.dequeue();
		if (data == null)
			return null;
		r[1] = data[3]; // action
		r[2] = data[0]; // agent
		r[3] = data[1] == null ? "" : data[1]; // ma
		if (data[2] instanceof Date) {
			r[0] = (Date) data[2];
			for (int i = 4; i < sHistColNames.length; i++)
				r[i] = "";
			r[7] = new Long(-1);
		} else {
			PublisherAlert a = (PublisherAlert) data[2];
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
			r[9] = a.mPostAlertEventProperties;
		}
		return new NamedArray(sHistColNames, r);
	}

	/**
	 * Inactivate and remove this "Tracker".
	 */
	public void destroy() {
		mConsole.destroyAlertTracker(this);
	}

	/**
	 * Internal cleanup (remove event from Evm, ...)
	 */
	protected void finalize() {
		mConsole.destroyAlertTracker(this);
		if (mEvm != null && mEvent != null)
			mEvm.removeEvent(mEvent);
	}
}
