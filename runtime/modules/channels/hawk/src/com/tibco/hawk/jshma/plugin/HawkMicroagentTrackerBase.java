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

import java.util.Enumeration;
import java.util.Vector;

import COM.TIBCO.hawk.console.hawkeye.AgentInstance;
import COM.TIBCO.hawk.talon.MicroAgentException;

import com.tibco.hawk.jshma.util.Filter;
import com.tibco.hawk.jshma.util.NamedTabularData;

/**
 * This class is used to track TIBCO Hawk MicroAgents with their names following
 * the specified pattern or Filter Criteria.
 * <p>
 * The application can either inquire the MicroAgent name list or use this
 * "Tracker" to do TIBCO Hawk method subscription. The advantage of using this
 * "Tracker" is that the applioation does not need to know when or where the
 * MicroAgent will be run on.
 * <p>
 * Filtering on MicroAgent names can be used to provide an "application view" on
 * the network since the same type of applications typically use the same type
 * of MicroAgent name pattern. For examples, COM.TIBCO.ADAPTER.adadb... are for
 * TIBCO ActiveDB adapters.
 */

public class HawkMicroagentTrackerBase {
	private static String sClassName = HawkMicroagentTrackerBase.class.getName();
	private static String[] sColNames = PluginResources.getResources().getStringArray(
			PluginResources.MA_TRACKER_FILTER_COL_NAMES);

	private HawkConsoleBase mConsole = null;
	private Filter[] mANDFilters = null;
	private Filter[] mORFilters = null;
	private Vector mMAList = new Vector();
	private Vector mSubscriberList = null;

	HawkMicroagentTrackerBase() {
	};

	/**
	 * Instantiate a "Tracker" with a MicroAgent name pattern.
	 * 
	 * @param console
	 *            The Hawk Console handle object
	 * @param pattern
	 *            The MicroAgent name pattern. All the MicorAgents with names
	 *            "containing" the specified pattern will be tracked.
	 * 
	 */
	public HawkMicroagentTrackerBase(HawkConsoleBase console, String pattern) throws HawkConsoleException {
		HawkMicroagentTrackerBaseInit(console, pattern);
	}

	/**
	 * Instantiate a "Tracker" with Filters. All the MicorAgents satisfying the
	 * "AND" Filters and "OR" Filters will be tracked. The AND Filters are
	 * applied first, then the OR Filters. Both the ANDFilters and ORFilters
	 * parameters are optional (specify null if not applicable). The Fileters
	 * that can be applied include: "AgentName", "MicroAgentName", "Cluster",
	 * "OS Name", "OS Version", and "Architecture". They are all "String"
	 * Filters.
	 * 
	 * @param console
	 *            The Hawk Console handle object
	 * @param ANDFilters
	 *            The "AND" Filters for MicroAgent filtering
	 * @param ORFilters
	 *            The "OR" Filters for MicroAgent filtering
	 * @see Filter
	 */
	public HawkMicroagentTrackerBase(HawkConsoleBase console, Filter[] ANDFilters, Filter[] ORFilters)
			throws HawkConsoleException {
		HawkMicroagentTrackerBaseInit(console, ANDFilters, ORFilters);
	}

	void HawkMicroagentTrackerBaseInit(HawkConsoleBase console, String pattern) throws HawkConsoleException {
		if (console == null) {
			throw new HawkConsoleException("The 'console' parameter can not be null.");
		}
		if (pattern == null) {
			throw new HawkConsoleException("The 'pattern' parameter can not be null.");
		}
		mANDFilters = new Filter[] { new Filter(PluginResources.getResources().getString(PluginResources.MA_NAME),
				"contain", pattern) };
		mConsole = console;
		mConsole.addMATracker(this);
	}

	void HawkMicroagentTrackerBaseInit(HawkConsoleBase console, Filter[] ANDFilters, Filter[] ORFilters)
			throws HawkConsoleException {
		if (console == null) {
			throw new HawkConsoleException("The 'console' parameter can not be null.");
		}
		if (ANDFilters == null && ORFilters == null) {
			throw new HawkConsoleException("The 'ANDFilters' parameter and 'ORFilters' can not both be null.");
		}

		if (ANDFilters != null)
			for (int i = 0; i < ANDFilters.length; i++) {
				boolean found = false;
				for (int j = 0; j < sColNames.length; j++) {
					if (ANDFilters[i].getName().equals(sColNames[j])) {
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
				for (int j = 0; j < sColNames.length; j++) {
					if (ORFilters[i].getName().equals(sColNames[j])) {
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
		mConsole.addMATracker(this);
	}

	private boolean applyANDFilter(String agentName, String maName, AgentInstance agentInstance) {
		if (mANDFilters == null)
			return true;
		for (int i = 0; i < mANDFilters.length; i++) {
			Filter f = mANDFilters[i];
			if (f.getName().equals("AgentName")) {
				if (!f.validate(agentName))
					return false;
			} else if (f.getName().equals("MicroAgentName")) {
				if (!f.validate(maName))
					return false;
			} else if (f.getName().equals("Cluster")) {
				if (!f.validate(agentInstance.getCluster()))
					return false;
			} else if (f.getName().equals("OS Name")) {
				if (!f.validate(agentInstance.getAgentPlatform().getOsName()))
					return false;
			} else if (f.getName().equals("OS Version")) {
				if (!f.validate(agentInstance.getAgentPlatform().getOsVersion()))
					return false;
			} else if (f.getName().equals("Architecture")) {
				if (!f.validate(agentInstance.getAgentPlatform().getOsArch()))
					return false;
			}
		}
		return true;
	}

	private boolean applyORFilter(String agentName, String maName, AgentInstance agentInstance) {
		if (mORFilters == null)
			return true;
		for (int i = 0; i < mORFilters.length; i++) {
			Filter f = mORFilters[i];
			if (f.getName().equals("AgentName")) {
				if (f.validate(agentName))
					return true;
			} else if (f.getName().equals("MicroAgentName")) {
				if (f.validate(maName))
					return true;
			} else if (f.getName().equals("Cluster")) {
				if (f.validate(agentInstance.getCluster()))
					return true;
			} else if (f.getName().equals("OS Name")) {
				if (f.validate(agentInstance.getAgentPlatform().getOsName()))
					return true;
			} else if (f.getName().equals("OS Version")) {
				if (f.validate(agentInstance.getAgentPlatform().getOsVersion()))
					return true;
			} else if (f.getName().equals("Architecture")) {
				if (f.validate(agentInstance.getAgentPlatform().getOsArch()))
					return true;
			}
		}
		return false;
	}

	void add(AgentInstance agentInstance, String maName) throws HawkConsoleException, MicroAgentException {
		String agentName = agentInstance.getAgentID().getName();
		if (!applyANDFilter(agentName, maName, agentInstance))
			return;
		if (!applyORFilter(agentName, maName, agentInstance))
			return;

		synchronized (this) {
			Object[] elem = null;
			Enumeration e = mMAList.elements();
			while (e.hasMoreElements()) {
				elem = (Object[]) e.nextElement();

				if (agentName.equals((String) elem[0]) && maName.equals((String) elem[1]))
					return;
			}
			elem = new Object[] { agentName, maName, agentInstance };
			mMAList.addElement(elem);
			if (mSubscriberList != null)
				for (Enumeration e2 = mSubscriberList.elements(); e2.hasMoreElements();) {
					Object subs = e2.nextElement();
					if (subs instanceof HawkMethodSubscriber)
						((HawkMethodSubscriber) subs).addMicroAgent(agentName, maName);
				}

		}
	}

	void remove(AgentInstance agentInstance) {

		String agentName = agentInstance.getAgentID().getName();
		synchronized (this) {
			Object[] elem = null;
			Enumeration e = mMAList.elements();
			while (e.hasMoreElements()) {
				elem = (Object[]) e.nextElement();
				if (agentName.equals((String) elem[0])) {
					mMAList.removeElement(elem);
					if (mSubscriberList != null)
						for (Enumeration e2 = mSubscriberList.elements(); e2.hasMoreElements();) {
							Object subs = e2.nextElement();
							if (subs instanceof HawkMethodSubscriber)
								((HawkMethodSubscriber) subs).removeMicroAgent((String) elem[0], (String) elem[1]);
						}
				}
			}
		}
	}

	void remove(AgentInstance agentInstance, String maName) {
		String agentName = agentInstance.getAgentID().getName();
		synchronized (this) {
			Object[] elem = null;
			Enumeration e = mMAList.elements();
			while (e.hasMoreElements()) {
				elem = (Object[]) e.nextElement();
				if (agentName.equals((String) elem[0]) && maName.equals((String) elem[1])) {
					mMAList.removeElement(elem);
					if (mSubscriberList != null)
						for (Enumeration e2 = mSubscriberList.elements(); e2.hasMoreElements();) {
							Object subs = e2.nextElement();
							if (subs instanceof HawkMethodSubscriber)
								((HawkMethodSubscriber) subs).removeMicroAgent((String) elem[0], (String) elem[1]);
						}
					return;
				}
			}
		}
	}

	/**
	 * Get the names and the associated TIB Hawk agent information of the
	 * MicroAgents that have nbeen tracked.
	 * 
	 * @return NamedTabularData with the following columns: "AgentName",
	 *         "MicroAgentName", "Cluster", "OS Name", "OS Version",
	 *         "Architecture"
	 */

	public NamedTabularData getList() {
		synchronized (this) {
			String[][] r = new String[mMAList.size()][6];
			Object[] elem = null;
			int i = 0;
			Enumeration e = mMAList.elements();
			while (e.hasMoreElements()) {
				elem = (Object[]) e.nextElement();
				r[i][0] = (String) elem[0];
				r[i][1] = (String) elem[1];
				AgentInstance agentInstance = (AgentInstance) elem[2];
				r[i][2] = agentInstance.getCluster();
				r[i][3] = agentInstance.getAgentPlatform().getOsName();
				r[i][4] = agentInstance.getAgentPlatform().getOsVersion();
				r[i][5] = agentInstance.getAgentPlatform().getOsArch();
				i++;
			}
			return new NamedTabularData(sColNames, r);
		}
	}

	/**
	 * Inactivate and remove this "Tracker".
	 */
	public void destroy() {
		mConsole.destroyMATracker(this);
	}

	void addAsyncSubscriber(HawkMethodSubscriber subscriber) throws HawkConsoleException, MicroAgentException {
		synchronized (this) {
			if (mSubscriberList == null)
				mSubscriberList = new Vector();
			mSubscriberList.addElement(subscriber);
			Object[] elem = null;
			Enumeration e = mMAList.elements();
			while (e.hasMoreElements()) {
				elem = (Object[]) e.nextElement();
				subscriber.addMicroAgent((String) elem[0], (String) elem[1]);

			}
		}
	}

	protected void finalize() {
		mConsole.destroyMATracker(this);
	}
}
