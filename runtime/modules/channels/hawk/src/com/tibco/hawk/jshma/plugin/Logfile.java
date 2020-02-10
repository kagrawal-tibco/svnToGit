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

import java.io.RandomAccessFile;

import com.tibco.hawk.jshma.util.Evm;
import com.tibco.hawk.jshma.util.EvmInterface;
import com.tibco.hawk.jshma.util.LastError;

/**
 * This class is used to monitor log files.
 * 
 * <p>
 * For all methods in this class, use <code>LastError</code> (or JavaScript
 * functions <code>lastError(), lastStackTrace()</code>, etc.) to check whether
 * the execution of a method was successful or not.
 */
public class Logfile implements EvmInterface {

	private static String sClassName = Logfile.class.getName();

	private String logfile = null;
	private RandomAccessFile raf = null;
	private long last_size = 0;
	private long curr_pos = 0;
	private long new_size = 0;
	private Evm evm = null;
	private String event = null;

	/**
	 * Create a log file monitor handle. If the log file exists, by default all
	 * the existing entries will be ignored.
	 * 
	 * @param logfile
	 *            The log file name
	 * 
	 */
	public Logfile(String logfile) {
		this.logfile = logfile;
		checkFile(true);

	}

	/**
	 * Create a log file monitor handle.
	 * 
	 * @param logfile
	 *            The log file name
	 * @param readFromEOF
	 *            If the log file exists and readFromEOF is set to true, then
	 *            all the existing entries will be ignored.
	 * 
	 */
	public Logfile(String logfile, boolean readFromEOF) {
		this.logfile = logfile;
		checkFile(readFromEOF);

	}

	/**
	 * Create a log file monitor handle with optional Event notification
	 * handling. If the log file exists, by default all the existing entries
	 * will be ignored.
	 * 
	 * @param logfile
	 *            The log file name
	 * @param event
	 *            Event name (used by <code>Evm</code>), specify null if Event
	 *            Manager is not used.
	 * @param evm
	 *            Event manager, specify null if Event Manager is not used.
	 */

	public Logfile(String logfile, String event, Evm evm) {
		this.logfile = logfile;
		this.evm = evm;
		this.event = event;
		if (evm != null && event != null)
			evm.addEvent(this, event);
		checkFile(true);

	}

	/**
	 * Create a log file monitor handle with optional Event notification
	 * handling.
	 * 
	 * @param logfile
	 *            The log file name
	 * @param readFromEOF
	 *            If the log file exists and readFromEOF is set to true, then
	 *            all the existing entries will be ignored.
	 * @param event
	 *            Event name (used by <code>Evm</code>), specify null if Event
	 *            Manager is not used.
	 * @param evm
	 *            Event manager, specify null if Event Manager is not used.
	 */

	public Logfile(String logfile, boolean readFromEOF, String event, Evm evm) {
		this.logfile = logfile;
		this.evm = evm;
		this.event = event;
		if (evm != null && event != null)
			evm.addEvent(this, event);
		checkFile(readFromEOF);

	}

	/**
	 * Checking whether the specified event is set - This method is typically
	 * used by Evm.
	 * 
	 * @param event
	 *            The event to be checked. Specify null if Event Manager is not
	 *            used.
	 * @return true if the specified event is set. (In this case, it means there
	 *         are log entries available.) false if no log entries available or
	 *         the specified event is not associated with this log monitoringn.
	 */
	public boolean isSet(String event) {
		if ((event == null) || (!event.equals(this.event)))
			return false;
		if (raf == null)
			checkFile(false);
		if (raf == null)
			return false;
		try {
			new_size = raf.length();
		} catch (Exception e) {
			return false;
		}

		if ((new_size < curr_pos) || (new_size > last_size))
			return true;
		else
			return false;
	}

	private void checkFile(boolean firstTime) {
		try {
			raf = new RandomAccessFile(logfile, "r");
			// System.out.println("raf: " + raf);
			new_size = raf.length();
			// System.out.println("len: " + new_size);
			if (firstTime)
				last_size = new_size;
			else
				last_size = 0;
			curr_pos = last_size;

		} catch (Throwable th) {
			// System.out.println("new RandomAccessFile failed: " + e);
			raf = null;
			last_size = 0;
			new_size = 0;
			curr_pos = 0;
		}

	}

	/**
	 * Get a line of log file entry.
	 * 
	 * @return a line of log file entry. If there are no data available, this
	 *         method will be blocked if Evm is not used or return null if Evm
	 *         is used.
	 */
	public String getLine() {
		LastError.clear();
		try {

			String line;
			while (true) {
				if (curr_pos < new_size) {
					if (curr_pos == last_size)
						raf.seek(last_size);
					try {
						line = raf.readLine();
						curr_pos = raf.getFilePointer();
					} catch (Exception e) {
						// System.out.println("raf.readLine failed: " + e);
						try {
							raf.close();
						} catch (Exception err) {
						}
						;
						checkFile(false);
						continue;
					}
					// System.out.println("curr_pos=" + curr_pos + " lin:" +
					// line);
					return line;
				} else if (curr_pos == new_size) {
					last_size = new_size;
					if (raf == null)
						checkFile(false);
					else {
						new_size = raf.length();
						if (new_size < curr_pos)
							continue;
					}

					while (new_size <= last_size) {
						if (evm == null)
							Thread.sleep(1000);
						else
							return null;

						if (raf == null)
							checkFile(false);
						else {
							new_size = raf.length();
							if (new_size < curr_pos)
								break;
						}
						// System.out.println("new_size=" + new_size);
					}

				} else { // curr_pos > new_size
							// file must be rewritten
					try {
						raf.close();
					} catch (Exception err) {
					}
					;
					checkFile(false);
				}
			} // while

		} catch (java.lang.Throwable th) {
			LastError.set("Failed to getLine because of " + th, sClassName + ".getLine()", th);
			return null;
		}
	}

}
