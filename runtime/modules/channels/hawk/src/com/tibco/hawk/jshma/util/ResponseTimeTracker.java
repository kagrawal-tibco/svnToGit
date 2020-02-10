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

import java.util.Date;

import com.tibco.hawk.jshma.plugin.PluginResources;

public class ResponseTimeTracker {

	// private String name;

	private static final String[] sCols = PluginResources.getResources().getStringArray(
			PluginResources.RESPONSE_TIME_TRACKER_COL_NAMES);

	private long startTime = 0; // very first start
	private long lastStartTime = 0;
	private long lastStopTime = 0;

	private long lastClocks = 0; // the longest recorded time
	private long maxClocks = 0; // the longest recorded time
	private long minClocks = -1; // the shortest recorded time
	private long totalClocks = 0; // the running total of all times

	// the number of times a successful start()/stop() cycle has been called
	private int completeCount = 0;
	// the # of times abort() has been called
	private int abortCount = 0;
	// the # of times an out of sequence operation has been
	// attempted (e.g., calling start(), start() or stop(), stop()).
	private int sequenceErrorCount = 0;

	// this is true if start() has been called and stop()
	// or abort() has not yet been called, otherwise false
	private boolean isRunning = false;

	private boolean mCalcStdDev;

	private double squareSum = 0.0;

	public ResponseTimeTracker(boolean calcStdDev) {
		// this.name = name;
		mCalcStdDev = calcStdDev;
	}

	public long start(Date timestamp) {
		long elapse = 0;
		synchronized (this) {
			if (isRunning)
				++sequenceErrorCount;
			else
				isRunning = true;

			long start = timestamp == null ? System.currentTimeMillis() : timestamp.getTime();

			if (startTime == 0) {
				startTime = start;
				lastStopTime = start;
			}
			lastStartTime = start;
			elapse = lastStartTime - lastStopTime;
			if (elapse < 0)
				elapse = 0;
		}
		return elapse;
	}

	public long stop(Date timestamp) {
		long elapse = 0;
		synchronized (this) {
			boolean badSeq = false;
			if (isRunning)
				isRunning = false;
			else {
				++sequenceErrorCount;
				badSeq = true;
			}

			long stop = timestamp == null ? System.currentTimeMillis() : timestamp.getTime();

			lastStopTime = stop;
			if (lastStartTime == 0) {
				lastStartTime = stop;
			}
			elapse = lastStopTime - lastStartTime;
			if (elapse < 0)
				elapse = 0;
			if (!badSeq) {
				++completeCount;
				totalClocks += elapse;
				lastClocks = elapse;
				if (mCalcStdDev)
					squareSum += Math.pow(elapse, 2);
				if (elapse > maxClocks)
					maxClocks = elapse;
				/*
				 * if (elapse == 0) System.out.println("stop=" + lastStopTime +
				 * " start=" + lastStartTime + " completeCount=" +
				 * completeCount); else System.out.println("******stop=" +
				 * lastStopTime + " start=" + lastStartTime + " completeCount="
				 * + completeCount);
				 */

				if (minClocks < 0 || elapse < minClocks)
					minClocks = elapse;
			}
		}
		return elapse;
	}

	public long abort(Date timestamp) {
		long elapse = 0;
		synchronized (this) {
			boolean badSeq = false;
			if (isRunning)
				isRunning = false;
			else {
				++sequenceErrorCount;
				badSeq = true;
			}

			long stop = timestamp == null ? System.currentTimeMillis() : timestamp.getTime();

			lastStopTime = stop;
			if (lastStartTime == 0)
				lastStartTime = stop;
			elapse = lastStopTime - lastStartTime;
			if (elapse < 0)
				elapse = 0;
			if (!badSeq) {
				++abortCount;
			}
		}
		return elapse;
	}

	public void reset() {
		synchronized (this) {
			startTime = 0;
			lastStartTime = 0;
			lastStopTime = 0;
			lastClocks = 0;
			maxClocks = 0;
			minClocks = -1;
			totalClocks = 0;
			completeCount = 0;
			abortCount = 0;
			sequenceErrorCount = 0;
			squareSum = 0.0;

			isRunning = false;
		}
	}

	public NamedArray getSummary() {
		synchronized (this) {
			// long now = System.currentTimeMillis();
			// double elapseTime = (now - startTime) /1000.0;

			Object[] d = new Object[sCols.length];
			int i = 0;
			d[i++] = completeCount == 0 ? new Integer(0) : new Integer((int) (totalClocks / completeCount));
			d[i++] = new Integer((int) lastClocks);
			d[i++] = new Integer((int) (minClocks < 0 ? 0 : minClocks));
			d[i++] = new Integer((int) maxClocks);
			d[i++] = new Double((!mCalcStdDev) || completeCount == 0 ? 0.0 : Math.sqrt(squareSum / completeCount
					- Math.pow(1.0 * totalClocks / completeCount, 2)));
			d[i++] = new Integer(completeCount);
			d[i++] = new Integer(abortCount);
			d[i++] = new Integer(sequenceErrorCount);
			d[i++] = new Double(totalClocks / 1000.0);
			Date now = new Date();
			d[i++] = new Double((now.getTime() - lastStartTime) / 1000.0);
			d[i++] = new Double((now.getTime() - lastStopTime) / 1000.0);
			return new NamedArray(sCols, d);

		}
	}

}
