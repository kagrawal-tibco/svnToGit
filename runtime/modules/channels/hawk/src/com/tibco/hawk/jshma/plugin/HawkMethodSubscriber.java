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

//import java.lang.IllegalArgumentException;
import java.util.Enumeration;
import java.util.Vector;

import COM.TIBCO.hawk.talon.MicroAgentException;
import COM.TIBCO.hawk.talon.MicroAgentID;
import COM.TIBCO.hawk.utilities.trace.Trace;

import com.tibco.hawk.jshma.util.ContextControl;
import com.tibco.hawk.jshma.util.Evm;
import com.tibco.hawk.jshma.util.EvmInterface;
import com.tibco.hawk.jshma.util.NamedArray;
import com.tibco.hawk.jshma.util.NamedTabularData;
import com.tibco.hawk.jshma.util.Queue;

/**
 * This class handles all TIBCO Hawk method sucriptions. This class should not
 * be used by JavaScript directly; the <code>HawkSyncMethodSubscriber</code> and
 * <code>HawkAsyncMethodSubscriber</code> should be used instead.
 */
public class HawkMethodSubscriber implements EvmInterface {

	public static final String SUBSCRIPTION_CONTEXT_STATUS_ATTR = "ContextStatus";
	public static final String SUBSCRIPTION_ERROR_ATTR = "SubscriptionError";

	public static final String SUBSCRIPTION_TERMINATED = "Terminated";
	public static final String SUBSCRIPTION_ERROR = "Error";

	public static final int SUBSCRIPTION_CONTEXT_HANDLE = 0;
	public static final int SUBSCRIPTION_CONTEXT_COLUMN_NAMES = 1;
	public static final int SUBSCRIPTION_CONTEXT_SUBSCRIBER = 2;
	public static final int SUBSCRIPTION_CONTEXT_ERROR = 3;

	/**
	 * TIBCO Hawk Trace handler
	 */
	protected Trace mTrace = null;

	// private static String[] sColNames = {"Data"};

	private Evm mEvm = null;
	private String mEvent = null;
	private int mHistory = 0;
	private int mInterval = 0;
	private Queue mQueue = null;

	String mAgentName;
	String mMicroAgentName;
	String mMethodName;
	MicroAgentID mMicroAgentID = null;

	NamedArray mParams;
	Object mSubscriptionHandle;

	private HawkMicroagentTrackerBase mTracker;
	private Vector mMATable = null;
	private HawkConsoleBase mConsole = null;

	HawkMethodSubscriber() {
	};

	private HawkMethodSubscriber(HawkConsoleBase console, String agentName, String microAgentName, String methodName,
			NamedArray params, String event, Evm evm, int history, int interval) throws HawkConsoleException,
			MicroAgentException {
		HawkMethodSubscriberInit(console, agentName, microAgentName, methodName, params, event, evm, history, interval);
	}

	public HawkMethodSubscriber(HawkConsoleBase console, String agentName, String microAgentName, String methodName,
			NamedArray params, int history, int interval) throws Exception {
		HawkMethodSubscriberInit(console, agentName, microAgentName, methodName, params, null, null, history, interval);
	}

	public HawkMethodSubscriber(HawkConsoleBase console, HawkMicroagentTrackerBase tracker, String methodName,
			NamedArray params, int history, int interval) throws Exception {
		HawkMethodSubscriberInit(console, tracker, methodName, params, null, null, history, interval);
	}

	/**
	 * Initiate a TIBCO Hawk method subscription inside a TIBCO Hawk agent -
	 * This is for internal use only.
	 * 
	 * @param console
	 *            The Hawk Console handle object
	 * @param microAgentID
	 *            The MicroAgentID instance associated with the TIBCO Hawk
	 *            MicroAgent of interest.
	 * @param methodName
	 *            The name of the TIBCO Hawk MicroAgent method to be subscribed.
	 * @param params
	 *            method input parameters The parameters specified in the
	 *            NamedArray does not have to follow the exact order defined in
	 *            the "method description". Also, optional parameters can be
	 *            omitted.
	 * @param event
	 *            Event name (used by <code>Evm</code>), specify null if Event
	 *            Manager is not used.
	 * @param evm
	 *            Event manager, specify null if Event Manager is not used.
	 * @param history
	 *            The maximum number of subcription data to be kept in cache, 0
	 *            (or less) means there's no limit.
	 * @param interval
	 *            the subscription internal in number of seconds If the interval
	 *            is greater than 0, it's treated as synchronous method
	 *            subscription; otherwise, it's asynchronous method
	 *            subscription.
	 */

	protected HawkMethodSubscriber(HawkConsoleBase console, MicroAgentID microAgentID, String methodName,
			NamedArray params, String event, Evm evm, int history, int interval) throws HawkConsoleException,
			MicroAgentException {
		mTrace = ContextControl.getTrace();
		mMicroAgentID = (MicroAgentID) microAgentID;
		mAgentName = mMicroAgentID.getAgent().getName();
		mMicroAgentName = mMicroAgentID.getName();
		mMethodName = methodName;
		mInterval = interval;
		mParams = params;
		mConsole = console;
		mEvm = evm;
		mEvent = event;
		mHistory = history;

		if (mConsole == null) {
			throw new HawkConsoleException("Hawk console is not available.");
		}

		if (mHistory > 0)
			mQueue = new Queue(mHistory < 10 ? mHistory : 10, Queue.FIFO, mHistory);
		if (evm != null && event != null)
			mEvm.addEvent(this, mEvent);

		// System.out.println("Interval=" + interval);
		mSubscriptionHandle = mConsole
				.handleHawkMethodInternal(mMicroAgentID, methodName, params, this, true, interval);

	}

	void HawkMethodSubscriberInit(HawkConsoleBase console, String agentName, String microAgentName, String methodName,
			NamedArray params, String event, Evm evm, int history, int interval) throws HawkConsoleException,
			MicroAgentException {
		mTrace = ContextControl.getTrace();
		mAgentName = agentName;
		mMicroAgentName = microAgentName;
		mMethodName = methodName;
		mInterval = interval;
		mParams = params;
		mConsole = console;
		mEvm = evm;
		mEvent = event;
		mHistory = history;

		if (mConsole == null) {
			throw new HawkConsoleException("Hawk console is not available.");
		}

		if (mHistory > 0)
			mQueue = new Queue(mHistory < 10 ? mHistory : 10, Queue.FIFO, mHistory);

		if (evm != null && event != null)
			mEvm.addEvent(this, mEvent);

		// System.out.println("Interval=" + interval);
		mSubscriptionHandle = mConsole.subscribeHawkMethod(agentName, microAgentName, methodName, params, interval,
				this);

	}

	void HawkMethodSubscriberInit(HawkConsoleBase console, HawkMicroagentTrackerBase tracker, String methodName,
			NamedArray params, String event, Evm evm, int history, int interval) throws HawkConsoleException,
			MicroAgentException {
		mTrace = ContextControl.getTrace();
		mTracker = tracker;
		mMethodName = methodName;
		mParams = params;
		mEvm = evm;
		mEvent = event;
		mConsole = console;
		mHistory = history;
		mInterval = interval;
		if (evm != null && event != null)
			mEvm.addEvent(this, mEvent);
		tracker.addAsyncSubscriber(this);
	}

	void retry() throws HawkConsoleException, MicroAgentException {
		if (mTracker != null) {
			Trace t = ContextControl.getTrace();
			t.log(Trace.DEBUG, "No op on 'retry' for HawkMethodSubscriber with agent " + mAgentName + " microagent "
					+ mMicroAgentName + " method " + mMethodName);
			return;
		}

		if (mSubscriptionHandle != null)
			return;

		mSubscriptionHandle = mConsole.subscribeHawkMethod(mAgentName, mMicroAgentName, mMethodName, mParams,
				mInterval, this);

	}

	// NamedTabularData getData(boolean blocking)
	/**
	 * This method returns newly arrived subscribed data.
	 * 
	 * @return If there's no data, it returns either null or a NamedTabularData
	 *         instance with no table data. If the subscription had been
	 *         terminated, the NamedTabularData instance would have a table
	 *         attribute SUBSCRIPTION_CONTEXT_STATUS_ATTR with value
	 *         HawkMethodSubscriber.SUBSCRIPTION_TERMINATED. If the subscription
	 *         had an error, the NamedTabularData element would have two table
	 *         attributes: SUBSCRIPTION_CONTEXT_STATUS_ATTR with value
	 *         HawkMethodSubscriber.SUBSCRIPTION_ERROR and
	 *         SUBSCRIPTION_ERROR_ATTR with a value of MicroAgentException
	 *         instance.
	 */
	NamedTabularData getData() {
		NamedTabularData nullReturn = null;
		Object[] cx = mConsole.getSubscriptionContext(mSubscriptionHandle);
		if (mSubscriptionHandle != null && cx == null) {
			NamedTabularData nullr = new NamedTabularData();
			nullr.setTableAttr(SUBSCRIPTION_CONTEXT_STATUS_ATTR, SUBSCRIPTION_TERMINATED);
			nullReturn = nullr;
		}

		if (cx != null && cx[SUBSCRIPTION_CONTEXT_ERROR] != null) {
			NamedTabularData nullr = new NamedTabularData();
			nullr.setTableAttr(SUBSCRIPTION_CONTEXT_STATUS_ATTR, SUBSCRIPTION_ERROR);
			nullr.setTableAttr(SUBSCRIPTION_ERROR_ATTR, cx[SUBSCRIPTION_CONTEXT_ERROR]);
			nullReturn = nullr;
		}

		if (mTracker == null) {
			if (mSubscriptionHandle == null)
				return nullReturn;

			while (true) {
				if (hasSubscriptionData())
					break;
				// if (mEvm == null && blocking)
				if (mEvm == null) {
					try {
						Thread.sleep(2000);
					} catch (Exception ee) {
					}
					;
				} else
					return nullReturn;
			}
			if (mQueue == null)
				return nullReturn;
			NamedTabularData d = (NamedTabularData) mQueue.dequeue();
			if (cx == null)
				d.setTableAttr(SUBSCRIPTION_CONTEXT_STATUS_ATTR, SUBSCRIPTION_TERMINATED);
			else {
				if (cx[SUBSCRIPTION_CONTEXT_ERROR] != null) {
					d.setTableAttr(SUBSCRIPTION_CONTEXT_STATUS_ATTR, SUBSCRIPTION_ERROR);
					d.setTableAttr(SUBSCRIPTION_ERROR_ATTR, cx[SUBSCRIPTION_CONTEXT_ERROR]);
				}
			}
			return d;
		} else {
			while (true) {
				if (mMATable != null)
					for (Enumeration e2 = mMATable.elements(); e2.hasMoreElements();) {
						HawkMethodSubscriber subs = (HawkMethodSubscriber) e2.nextElement();
						if (subs.isSet(mEvent)) {
							// return subs.getData(blocking);
							return subs.getData();
						}
					}
				// if (mEvm != null || (! blocking))
				if (mEvm != null)
					return nullReturn;

				try {
					Thread.sleep(2000);
				} catch (Exception ee) {
				}
				;

			}
		}
	}

	/**
	 * This method returns subscribed data history.
	 * 
	 * @return If there's no history, it returns either null or an array of one
	 *         NamedTabularData element with no table data. If the subscription
	 *         had been terminated, every NamedTabularData instance would have a
	 *         table attribute SUBSCRIPTION_CONTEXT_STATUS_ATTR with value
	 *         HawkMethodSubscriber.SUBSCRIPTION_TERMINATED. If the subscription
	 *         had an error, the NamedTabularData element would have two table
	 *         attributes: SUBSCRIPTION_CONTEXT_STATUS_ATTR with value
	 *         HawkMethodSubscriber.SUBSCRIPTION_ERROR and
	 *         SUBSCRIPTION_ERROR_ATTR with a value of MicroAgentException
	 *         instance.
	 */
	public NamedTabularData[] getHistory() {
		NamedTabularData[] nullReturn = null;
		Object[] cx = mConsole.getSubscriptionContext(mSubscriptionHandle);
		if (mSubscriptionHandle != null && cx == null) {
			NamedTabularData nullr = new NamedTabularData();
			nullr.setTableAttr(SUBSCRIPTION_CONTEXT_STATUS_ATTR, SUBSCRIPTION_TERMINATED);
			nullReturn = new NamedTabularData[] { nullr };
		}

		if (cx != null && cx[SUBSCRIPTION_CONTEXT_ERROR] != null) {
			NamedTabularData nullr = new NamedTabularData();
			nullr.setTableAttr(SUBSCRIPTION_CONTEXT_STATUS_ATTR, SUBSCRIPTION_ERROR);
			nullr.setTableAttr(SUBSCRIPTION_ERROR_ATTR, cx[SUBSCRIPTION_CONTEXT_ERROR]);
			nullReturn = new NamedTabularData[] { nullr };
		}

		if (mHistory <= 0)
			return nullReturn;

		if (mTracker == null) {
			if (mQueue == null)
				return nullReturn;

			Object[] d = mQueue.getList();
			// t.log(Trace.DEBUG, "d=" +d + " d.length=" + d.length);

			/*
			 * if (d == null || d.length == 0) return nullReturn;
			 * 
			 * NamedTabularData d0 = (NamedTabularData)d[0]; for (int i = 1; i <
			 * d.length; i++) d0.merge((NamedTabularData)d[i]);
			 * 
			 * return d0;
			 */
			if (d == null || d.length == 0)
				return nullReturn;

			NamedTabularData[] r = new NamedTabularData[d.length];
			for (int i = 0; i < d.length; i++) {
				r[i] = (NamedTabularData) d[i];
				if (cx == null)
					r[i].setTableAttr(SUBSCRIPTION_CONTEXT_STATUS_ATTR, SUBSCRIPTION_TERMINATED);
				else {
					if (cx[SUBSCRIPTION_CONTEXT_ERROR] != null) {
						r[i].setTableAttr(SUBSCRIPTION_CONTEXT_STATUS_ATTR, SUBSCRIPTION_ERROR);
						r[i].setTableAttr(SUBSCRIPTION_ERROR_ATTR, cx[SUBSCRIPTION_CONTEXT_ERROR]);
					}
				}
			}

			return r;
		} else {
			if (mMATable == null)
				return nullReturn;
			Vector v = new Vector();
			NamedTabularData[] result = null;

			for (Enumeration e2 = mMATable.elements(); e2.hasMoreElements();) {
				HawkMethodSubscriber subs = (HawkMethodSubscriber) e2.nextElement();
				NamedTabularData[] d = subs.getHistory();
				if (d != null)
					for (int i = 0; i < d.length; i++)
						v.addElement(d[i]);
			}
			if (v.size() > 0) {
				result = new NamedTabularData[v.size()];
				int i = 0;
				for (Enumeration e3 = v.elements(); e3.hasMoreElements(); i++) {
					result[i] = (NamedTabularData) e3.nextElement();
				}
			}
			return result;
		}
	}

	/**
	 * Checking whether the specified event is set - This method is typically
	 * used by Evm.
	 * 
	 * @param event
	 *            The event to be checked. Specify null if Event Manager is not
	 *            used.
	 * @return true if the specified event is set. (In this case, it means there
	 *         are subscription data available.) false if no data available or
	 *         the specified event is not associated with this subscription.
	 */

	public boolean isSet(String event) {

		if (((mEvent != null) && ((event == null) || (!event.equals(mEvent)))) || ((mEvent == null) && (event != null)))
			return false;
		// boolean ready = false;

		if (mTracker == null) {
			if (mSubscriptionHandle == null)
				return false;

			if (hasSubscriptionData())
				return true;
		} else {
			if (mMATable == null)
				return false;
			for (Enumeration e2 = mMATable.elements(); e2.hasMoreElements();) {
				HawkMethodSubscriber subs = (HawkMethodSubscriber) e2.nextElement();
				if (subs.isSet(event))
					return true;
			}
		}

		return false;
	}

	synchronized void addMicroAgent(String agentName, String maName) throws HawkConsoleException, MicroAgentException {
		HawkMethodSubscriber s = null;
		Trace t = ContextControl.getTrace();
		t.log(Trace.DEBUG, "HawkMethodSubscriber addMicroAgent " + agentName + " " + maName);
		try {

			if (mInterval <= 0)
				s = new HawkMethodSubscriber(mConsole, agentName, maName, mMethodName, mParams, mEvent, null, mHistory,
						0);
			else
				s = new HawkMethodSubscriber(mConsole, agentName, maName, mMethodName, mParams, mEvent, null, mHistory,
						mInterval);

			if (mMATable == null)
				mMATable = new Vector();
			mMATable.addElement(s);

		} catch (Exception e) {
			// TODO: check error
			t.log(Trace.DEBUG, "HawkMethodSubscriber subscribe " + agentName + " " + maName + "failed because of:" + e,
					e);
		}
	}

	synchronized void removeMicroAgent(String agentName, String maName) {
		Trace t = ContextControl.getTrace();
		t.log(Trace.DEBUG, "HawkMethodSubscriber removeMicroAgent " + agentName + " " + maName);
		if (mMATable == null)
			return;

		for (Enumeration e2 = mMATable.elements(); e2.hasMoreElements();) {
			HawkMethodSubscriber subs = (HawkMethodSubscriber) e2.nextElement();
			if (subs.mAgentName != null && subs.mAgentName.equals(agentName) && subs.mMicroAgentName != null
					&& subs.mMicroAgentName.equals(maName))
				mMATable.removeElement(subs);
		}

	}

	public void destroy() {
		Trace t = ContextControl.getTrace();
		if (mTracker == null) {
			if (mConsole != null && mSubscriptionHandle != null) {
				t.log(Trace.DEBUG, "Destroying subscription: " + mAgentName + ":" + mMicroAgentName + ":" + mMethodName);
				mConsole.cancelSubscription(mSubscriptionHandle);
			}
		} else {
			if (mMATable != null) {

				t.log(Trace.DEBUG, "Destroying tracker subscription.");
				for (Enumeration e2 = mMATable.elements(); e2.hasMoreElements();) {
					HawkMethodSubscriber subs = (HawkMethodSubscriber) e2.nextElement();
					subs.destroy();
				}
			}

		}

		if (mEvm != null && mEvent != null)
			mEvm.removeEvent(mEvent);

		// help garbage collection
		mQueue = null;
		mHistory = 0;
		mMATable = null;
	}

	/**
	 * This method allows the user to set the maximum number of histories to
	 * keep for each subscription.
	 * 
	 * @param numHistory
	 *            The the maximum number of histories to keep for each
	 *            subscription
	 */
	public void setMaxHistory(int numHistory) {
		if (numHistory < 0) {
			throw new IllegalArgumentException("The number of history to keep can not be smaller than 0.");
		}
		mHistory = numHistory;
		if (mTracker == null) {
			if (mQueue != null) {
				mQueue.setMaxKeep(numHistory);
			}

		} else {
			if (mMATable == null)
				return;

			for (Enumeration e2 = mMATable.elements(); e2.hasMoreElements();) {
				HawkMethodSubscriber subs = (HawkMethodSubscriber) e2.nextElement();
				subs.setMaxHistory(numHistory);
			}

		}

	}

	/**
	 * This method is called when data for this method subscription is
	 * available. If a subclass overrides this method, the subclass has to be
	 * responsible for handling the received subscription data. The
	 * <code>getData()</code> method will blocked (if Evm is not used) or always
	 * return null (if Evm is used) in this case.
	 * 
	 * @param d
	 *            The method subscription data.
	 */
	protected void addResult(NamedTabularData d) {
		if (d != null) {
			d.setTableAttr("AgentName", mAgentName);
			d.setTableAttr("MicroAgentName", mMicroAgentName);
			if (mQueue == null)
				mQueue = new Queue(10);
			mQueue.enqueue(d);
			if (mEvm != null)
				mEvm.recheck();
			// System.out.println("addResult mQueue length=" + mQueue.length()
			// );
		} else {
			// TODO
		}
	}

	/**
	 * Checking whether any subscription data available.
	 * 
	 * @return true if there are subscription data available, false if no data
	 *         available or
	 */

	public boolean hasSubscriptionData() {
		if (mTracker != null)
			return isSet(mEvent);
		else if (mQueue == null || mQueue.length() <= 0)
			return false;
		else {
			return true;
		}
	}

	/**
	 * Checking whether subscription has been terminated.
	 * 
	 * @return true if subscription has been terminated,
	 */

	public boolean subscriptionTerminated() {
		Object[] cx = mConsole.getSubscriptionContext(mSubscriptionHandle);
		if (mSubscriptionHandle != null && cx == null)
			return true;
		else
			return false;
	}

	/**
	 * Checking whether any subscription error happened.
	 * 
	 * @return true if there any subscription error happened,
	 */

	public boolean hasSubscriptionError() {
		Object[] cx = mConsole.getSubscriptionContext(mSubscriptionHandle);
		if (mSubscriptionHandle != null && cx != null) {
			if (cx[SUBSCRIPTION_CONTEXT_ERROR] != null)
				return true;
		}
		return false;
	}

	public MicroAgentException getSubscriptionError() {
		Object[] cx;
		if (mSubscriptionHandle != null && (cx = mConsole.getSubscriptionContext(mSubscriptionHandle)) != null) {
			if (cx[SUBSCRIPTION_CONTEXT_ERROR] != null)
				return (MicroAgentException) cx[SUBSCRIPTION_CONTEXT_ERROR];
		}
		return null;
	}

	/**
	 * Internal cleanup (remove event from Evm, ...)
	 */
	protected void finalize() {
		if (mEvm != null && mEvent != null)
			mEvm.removeEvent(mEvent);
	}

}
