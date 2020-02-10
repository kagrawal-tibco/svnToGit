// (c) Copyright 2001,2002 TIBCO Software Inc.  All rights reserved.
// LEGAL NOTICE:  This source code is provided to specific authorized end
// users pursuant to a separate license agreement.  You MAY NOT use this
// source code if you do not have a separate license from TIBCO Software
// Inc.  Except as expressly set forth in such license agreement, this
// source code, or any portion thereof, may not be used, modified,
// reproduced, transmitted, or distributed in any form or by any means,
// electronic or mechanical, without written permission from  TIBCO
// Software Inc.

package com.tibco.hawk.jshma.util;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This class implements a simple event manager. Any application wants to use
 * this event manager has to implement EvmInterface. Events are identified by
 * names. An application can use multiple events. An applications implements
 * EvemInterface has to provide a method <code>isSet(String event)</code> for
 * the event manager to check whether s specific event is set. Normally, the
 * event manager is polling all events at a fixed interval. However, an
 * application can also notify the event manager asynchronously when an event
 * has occurred (is set).
 * 
 * @see EvmInterface
 */
public class Evm {
	private int waitInterval = 2000; // in milli seconds, default = 2000
	private Hashtable events = new Hashtable();
	private long forever = 10000000 * 1000;
	private Thread mWaitThread = null;

	/**
	 * Default Evm constructor The default event checking interval is once every
	 * 2 seconds.
	 */

	public Evm() {
	}

	/**
	 * Evm constructor with a specfied event checking interval
	 * 
	 * @param interval
	 *            event checking interval in milliseconds
	 */

	public Evm(int interval) {
		if (interval > 0)
			this.waitInterval = interval;
	}

	/**
	 * Add an event for the event manager to manage.
	 * 
	 * @param event
	 *            the event name (identifier)
	 * @param src
	 *            the source application that handles the events
	 */
	public void addEvent(EvmInterface src, String event) {
		events.put(event, src);
	}

	/**
	 * Remove an event from the event manager.
	 * 
	 * @param event
	 *            the event name (identifier)
	 */
	public void removeEvent(String event) {
		events.remove(event);
	}

	/**
	 * Notify the event manager that an event is set. This will make the Evm
	 * <code>waitFor</code> method to return immediately.
	 */
	public void recheck() {
		if (mWaitThread != null)
			mWaitThread.interrupt();
	}

	/**
	 * Waiting for events forever until any event is set.
	 * 
	 * @return a list of events that have been set
	 */
	public String[] waitFor() {
		return waitFor(forever);
	}

	/**
	 * Waiting for events for the specified wait time until any event is set or
	 * timeout.
	 * 
	 * @param waitTime
	 *            the maximum time (in milliseconds) to wait if there's no event
	 * @return a list of events that have been set
	 */
	public String[] waitFor(long waitTime) {
		long target = System.currentTimeMillis() + waitTime;
		long toWait = waitTime;
		Vector v = new Vector();
		int i = 0;
		Enumeration e = null;
		while (true) {
			e = events.keys();
			i = 0;
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				EvmInterface src = (EvmInterface) events.get(key);
				if (src.isSet(key)) {
					v.addElement(key);
					i++;
				}
			}
			if (i == 0) {
				try {
					if (toWait <= 0)
						return null;
					mWaitThread = Thread.currentThread();
					Thread.sleep(toWait > waitInterval ? waitInterval : toWait);
					toWait = target - System.currentTimeMillis();
				} catch (Exception err) {
				}
				;
				mWaitThread = null;
			} else {
				String[] r = new String[i];
				for (int j = 0; j < i; j++)
					r[j] = (String) v.elementAt(j);
				return r;
			}

		} // while

	}

}
