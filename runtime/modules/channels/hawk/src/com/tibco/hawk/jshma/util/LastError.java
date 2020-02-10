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

import java.util.Hashtable;

import COM.TIBCO.hawk.utilities.trace.Trace;

/**
 * Because there's no way to catch Java Exceptions inside a JavaScript, this
 * class is used to help tracking the last error condition. The tracked error is
 * stored based on the current running thread. So, its value can be preserved
 * only during the life of a thread. Also, it only saves the last error. A
 * subsequent "set" or "clear" will override the previous value. Hence, after
 * each Plugin or Util method call, the application should check the "LastError"
 * immediately.
 * 
 */
public class LastError {

	private static Hashtable sErrTable = new Hashtable();
	private static Object[] NO_ERROR = new Object[] { "", "", null };
	private static String NO_ERROR_STR = "";
	private static int sLastErrorTraceLevel = -1;

	/**
	 * Set the "why" for the last error
	 * 
	 * @param why
	 *            the last error message
	 */
	public static void set(String why) {
		set(why, "", null, sLastErrorTraceLevel);
	}

	/**
	 * Set the "why" and "where" for the last error
	 * 
	 * @param why
	 *            the last error message
	 * @param where
	 *            the location where the last error happened
	 */
	public static void set(String why, String where) {
		set(why, where, null, sLastErrorTraceLevel);
	}

	/**
	 * Set the "why", "where" and the "Throwable" for the last error
	 * 
	 * @param why
	 *            the last error message
	 * @param where
	 *            the location where the last error happened
	 * @param throwable
	 *            the Throwable object caught in the last error
	 */
	public static void set(String why, String where, Throwable throwable) {
		set(why, where, throwable, sLastErrorTraceLevel);
	}

	/**
	 * Set the "why", "where", the "Throwable" for the last error and specify
	 * which trace level this error should be logged to the trace file. By
	 * default, it's 8 (Trace.DEBUG).
	 * 
	 * @param why
	 *            the last error message
	 * @param where
	 *            the location where the last error happened
	 * @param throwable
	 *            the Throwable object caught in the last error
	 * @param traceLevel
	 *            the trace level this error to be logged to the trace file.
	 *            (1=INFO, 2=WARNING, 4=ERROR 8=DEBUG=8).
	 */
	public static void set(String why, String where, Throwable throwable, int traceLevel) {
		int tLevel = traceLevel;
		if (sLastErrorTraceLevel < 0) {
			String tl = System.getProperty("hawk.lastErrorTraceLevel");
			if (tl != null)
				try {
					sLastErrorTraceLevel = Integer.parseInt(tl);
				} catch (Exception e) {
				}
			else
				sLastErrorTraceLevel = Trace.DEBUG;
			if (traceLevel < 0)
				tLevel = sLastErrorTraceLevel;
		}
		if (tLevel != 0) {
			Trace trace = ContextControl.getTrace();
			trace.log(tLevel, "Last Error: " + why + " At: " + where);
		}
		Thread t = Thread.currentThread();
		Object[] err = new Object[] { why, where, throwable };
		sErrTable.put(t, err);
	}

	/**
	 * Clear the last error. If an application method may set the LastError, it
	 * should clear the previous LastError first (using this method) to prevent
	 * returning a left over LastError.
	 */
	public static void clear() {
		sErrTable.put(Thread.currentThread(), NO_ERROR);
	}

	/**
	 * A method to check where there's an error happened.
	 * 
	 * @return true if there was no error, false if the LastError has been set.
	 */
	public static boolean OK() {
		if (getWhy().equals(NO_ERROR_STR))
			return true;
		else
			return false;
	}

	/**
	 * Get the "why" (error message) of the last error.
	 */
	public static String getWhy() {
		Thread t = Thread.currentThread();
		Object[] err = (Object[]) sErrTable.get(t);

		if (err == null)
			return NO_ERROR_STR;

		return (String) err[0];
	}

	/**
	 * Get the "where" (location) of the last error.
	 */
	public static String getWhere() {
		Thread t = Thread.currentThread();
		Object[] err = (Object[]) sErrTable.get(t);

		if (err == null)
			return NO_ERROR_STR;

		return (String) err[1];
	}

	/**
	 * Get the stack trace of the last error.
	 */
	public static String getStackTrace() {
		Thread t = Thread.currentThread();
		Object[] err = (Object[]) sErrTable.get(t);

		if (err == null)
			return NO_ERROR_STR;

		String result = null;

		try {
			result = Trace.getStackTrace((Throwable) err[2]);
		} catch (Throwable th) {
		}
		;

		return result;
	}

	/**
	 * Throw the Throwable object caught in the last error. It's used in a Java
	 * program that after the application checked the LastError condition and
	 * decided to throw the hrowable object caught in the last error.
	 */
	public static void throwError() throws Throwable {
		Thread t = Thread.currentThread();
		Object[] err = (Object[]) sErrTable.get(t);

		if (err == null)
			return;
		throw ((Throwable) err[2]);
	}

	/**
	 * Get the Exception causing the last error.
	 * 
	 * @return the Exception object causing the last error. If the last Error
	 *         was not caused by an exception, null is returned.
	 */
	public static Exception getLastException() {
		Thread t = Thread.currentThread();
		Object[] err = (Object[]) sErrTable.get(t);

		if (err == null)
			return null;

		if (err[2] instanceof Exception)
			return (Exception) err[2];
		else
			return null;
	}

}
