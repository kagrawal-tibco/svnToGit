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

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.WeakHashMap;

import COM.TIBCO.hawk.utilities.trace.Trace;

/**
 * This class is used for maintaining application context specific data. It is
 * typically needed under a multiple context environment such running under a
 * servlet container.
 */
public class ContextControl {
	static {
		try {
			Method tm = Class.forName("COM.TIBCO.hawk.utilities.trace.Trace").getMethod("useStdRollover", null);
			tm.invoke(null, null);
			// Trace.useStdRollover();
		} catch (Throwable ignore) {
		}
		;
	}

	private static WeakHashMap sContextTable = new WeakHashMap();
	private static WeakHashMap sTraceTable = new WeakHashMap();
	private static String NOCX = new String("_nocx_");
	private static boolean sThreadContextUsed = false;

	/*
	 * Set the context for the current thread so that it can be retrieved later
	 */

	static void setThreadContext(Object context) {
		if (context == null)
			return;
		sContextTable.put(Thread.currentThread(), context);
		sThreadContextUsed = true;
	}

	static Object getThreadContext() {
		return sContextTable.get(Thread.currentThread());
	}

	/**
	 * Set Trace object for the default context
	 * 
	 * @param trace
	 *            the Trace object. It must not be null.
	 */
	public static void setTrace(Trace trace) {
		if (trace != null)
			sTraceTable.put(NOCX, trace);
		return;
	}

	/**
	 * Set Trace object for the specified context
	 * 
	 * @param trace
	 *            the Trace object. It must not be null.
	 * @param context
	 *            the application context
	 */
	public static void setTrace(Trace trace, Object context) {
		if (trace != null)
			sTraceTable.put(context, trace);
		return;
	}

	/*
	 * public static Trace setTrace (String fileName, int level, long fileSize,
	 * long maxFile) { setTrace(fileName level, fileSize, maxFile, null, null);
	 * return t; }
	 */

	/**
	 * Set the trace object based on the specified file name.
	 * 
	 * @param fileName
	 *            the trace file name
	 * @param level
	 *            the default trace level
	 * @param fileSize
	 *            the maximum trace file size (The trace file will roll over if
	 *            this limit is exceeded.)
	 * @param maxFile
	 *            the maximum number of the trace files that will be kept. (The
	 *            second oldest trace file will be deleted when the number of
	 *            the trace files generated exceeding this limit.
	 * @param appID
	 *            the application ID used in logging
	 * @param productID
	 *            the product ID used in logging
	 * @param context
	 *            the application context. If null is specified, the default
	 *            context will be used.
	 * 
	 */

	public static Trace setTrace(String fileName, int level, long fileSize, long maxFile, String appID,
			String productID, Object context) {
		Trace t = null;
		try {
			t = new Trace(level, fileName, fileSize, maxFile);
			t.setTraceStandardDefaults(appID, "", productID);
		} catch (Exception e) {
			// TODO
			LastError.set("Failed to set trace because of " + e, "ContextControl.set()");
			t = new Trace();
		}
		if (context == null)
			setTrace(t);
		else
			setTrace(t, context);

		return t;
	}

	/**
	 * It's equivalent to call:
	 * <code>setTrace(fileName, level, fileSize, maxFile, appID, productID, null);</code>
	 */

	public static Trace setTrace(String fileName, int level, long fileSize, long maxFile, String appID, String productID) {
		return setTrace(fileName, level, fileSize, maxFile, appID, productID, null);
	}

	/**
	 * Get the trace object for the default context
	 */
	public static Trace getTrace() {
		Trace t = null;
		Object cx = null;
		if (sThreadContextUsed)
			t = (Trace) sTraceTable.get(((cx = getThreadContext()) == null) ? NOCX : cx);
		else
			t = (Trace) sTraceTable.get(NOCX);
		if (t == null) {
			t = new Trace();
			setTrace(t);
			String tl = System.getProperty("hawk.traceLevel");
			try {
				if (tl != null) {
					// System.out.println("hawk.traceLevel=" + tl);
					int level = Integer.parseInt(tl);
					if (level > 0) {
						t.setLevel(level);
						t.log(Trace.INFO, "Setting trace level to " + level);
					}
				}
			} catch (Exception e) {
			}
		}
		return t;
	}

	/**
	 * Check whether the trace for the current the specified context has been
	 * established.
	 * 
	 * @param context
	 *            the application context
	 * @return true if the conext is not null and trace has been set.
	 */
	public static boolean hasTrace(Object context) {
		if (context == null)
			return false;
		Trace t = (Trace) sTraceTable.get(context);
		return (t != null);
	}

	/**
	 * Get the Trace object for the specified context
	 * 
	 * @param context
	 *            the application context
	 */
	public static Trace getTrace(Object context) {
		Trace t = (Trace) sTraceTable.get(context == null ? NOCX : context);
		if (t == null) {
			t = new Trace();
			setTrace(t);
			String tl = System.getProperty("hawk.traceLevel");
			try {
				if (tl != null) {
					// System.out.println("hawk.traceLevel=" + tl);
					int level = Integer.parseInt(tl);
					if (level > 0) {
						t.setLevel(level);
						t.log(Trace.INFO, "Setting trace level to " + level);
					}
				}
			} catch (Exception e) {
			}
		}
		return t;
	}

	/**
	 * Get the current trace level for the default context
	 */
	public static int getTraceLevel() {
		Trace t = getTrace();
		return t == null ? 0 : t.getLevel();
	}

	/**
	 * Get the current trace level for the specified context
	 * 
	 * @param context
	 *            the application context
	 */
	public static int getTraceLevel(Object context) {
		Trace t = getTrace(context);
		return t == null ? 0 : t.getLevel();
	}

	/**
	 * Set the current trace level for the default context
	 */
	public static void setTraceLevel(int traceLevel) {
		Trace t = getTrace();
		if (t == null)
			return;
		try {
			t.setLevel(traceLevel);
		} catch (Exception e) {
		}
	}

	/**
	 * Set the current trace level for the specified context
	 * 
	 * @param context
	 *            the application context
	 */
	public static void setTraceLevel(int traceLevel, Object context) {
		Trace t = getTrace(context);
		if (t == null)
			return;
		try {
			t.setLevel(traceLevel);
		} catch (Exception e) {
		}
	}

	/**
	 * A utility method to get the "credential" (an encrypted password) for a
	 * user entered password. It waits for the user to enter a password and then
	 * encrypt the password to generate a "credential" to be used in other
	 * methods provided by this tool. Warning: The password entered will be
	 * echoed back in clear text.
	 */
	public static void getCredential() {
		LastError.clear();
		try {
			Method m = Class.forName("com.tibco.hawk.jshma.JSHmaImpl").getMethod("getImpl", new Class[] {});
			m.invoke(null, new Object[] {});
		} catch (Exception e) {
			// TODO
			LastError.set("Failed to get Credential because of " + e, "ContextControl.getCredential()");
		}
	}

	/**
	 * Get local host name
	 * 
	 * @return local host name or local host ip address if hostname not
	 *         available
	 */
	public static String hostname() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (Exception ignore) {
		}
		;
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception ignore) {
		}
		;
		return "";
	}

}
