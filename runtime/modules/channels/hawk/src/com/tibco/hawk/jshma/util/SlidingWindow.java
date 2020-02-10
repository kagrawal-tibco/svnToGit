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

import java.util.TimerTask;

import com.tibco.hawk.jshma.plugin.PluginResources;

/**
 * This class is to provide a sliding window utilities
 */
public class SlidingWindow {

	private static final String[] sCols = PluginResources.getResources().getStringArray(
			PluginResources.SLIDING_WINDOW_COL_NAMES);

	private int mSlots; // configured slot number
	private long mInterval;
	private double mIntervalSec;
	private double mPeriod = 0;

	private java.util.Timer m_timer = null;
	private TimerTask m_timerTask = null;
	private long mTimerStart;

	private int mCurr = 0;
	private int mTail = -1;
	private double[] mWeights = null;
	private int[] mCounts = null;
	private int mNumAdv = 0; // number of advances
	private int mTmp; // available afetr sum() is called

	// for the period
	private int mPrevIncidenceSum = 0;
	private int mLastIncidenceSum = 0;
	private int mMinIncidenceSum = -1;
	private int mMaxIncidenceSum = 0;
	private double mPrevWeightSum = 0.0;
	private double mLastWeightSum = 0.0;
	private double mMinWeightSum = -1.0;
	private double mMaxWeightSum = 0.0;

	// for overall
	private long mGrandIncidenceSum = 0;
	private double mGrandWeightSum = 0.0;
	private double mGrandMinWeight = -1.0;
	private double mGrandMaxWeight = 0.0;
	private double mGrandSquareSum = 0.0;

	private boolean mCalcStdDev = false;

	/**
	 * @param slots
	 *            the number of slots
	 */
	public SlidingWindow(int slots) {
		if (slots <= 0)
			throw new IllegalArgumentException("The argument slots has to be greater than zero");

		mSlots = slots;
		mWeights = new double[slots + 1]; // have an extra for curr update
		mCounts = new int[slots + 1];
	}

	public SlidingWindow(int slots, long interval, boolean calcStdDev) {
		if (slots <= 0 || interval <= 0)
			throw new IllegalArgumentException("The argument slots and interval have to be greater than zero");

		mSlots = slots;
		mInterval = interval;
		mIntervalSec = 1.0 / 1000.0 * interval;
		mPeriod = mIntervalSec * slots;
		mCalcStdDev = calcStdDev;

		mWeights = new double[slots + 1]; // have an extra for curr update
		mCounts = new int[slots + 1];
		startTimer();

	}

	private void startTimer() {
		m_timer = new java.util.Timer();
		m_timerTask = new TimerTask() {
			public void run() {
				advance();
			}
		};
		m_timer.scheduleAtFixedRate(m_timerTask, 0, mSlots * mInterval);
		mTimerStart = System.currentTimeMillis();
	}

	/**
	 * Destroy this timer.
	 */
	private void destroyTimer() {
		if (m_timerTask != null)
			m_timerTask.cancel();
		m_timer = null;
		mTimerStart = 0;
	}

	public void advance() {
		// System.out.println("advance at : " + System.currentTimeMillis());
		if ((mTail < 0 && mCurr == (mSlots - 1)) || mTail >= 0) {
			mTail++;
			if (mTail > mSlots)
				mTail = 0;
		}
		mCurr++;
		if (mCurr > mSlots)
			mCurr = 0;
		mWeights[mCurr] = 0;
		mCounts[mCurr] = 0;
		mNumAdv++;

		if (mNumAdv >= mSlots) {
			mPrevWeightSum = mLastWeightSum;
			mLastWeightSum = sum();
			if (mPrevWeightSum == 0.0)
				mPrevWeightSum = mLastWeightSum;
			mPrevIncidenceSum = mLastIncidenceSum;
			mLastIncidenceSum = mTmp;
			if (mPrevIncidenceSum == 0.0)
				mLastIncidenceSum = mLastIncidenceSum;

			if (mMinWeightSum < 0 || mLastWeightSum < mMinWeightSum)
				mMinWeightSum = mLastWeightSum;
			if (mLastWeightSum > mMaxWeightSum)
				mMaxWeightSum = mLastWeightSum;
			if (mMinIncidenceSum < 0 || mLastIncidenceSum < mMinIncidenceSum)
				mMinIncidenceSum = mLastIncidenceSum;
			if (mLastIncidenceSum > mMaxIncidenceSum)
				mMaxIncidenceSum = mLastIncidenceSum;

		}

	}

	public void increment(double weight) {
		// System.out.println("v=" + weight + " mCurr=" + mCurr +
		// " mTail=" + mTail + " mNumAdv=" + mNumAdv);
		mWeights[mCurr] += weight;
		mCounts[mCurr] += 1;

		mGrandIncidenceSum++;
		mGrandWeightSum += weight;
		if (weight > mGrandMaxWeight)
			mGrandMaxWeight = weight;
		if (mGrandMinWeight < 0 || weight < mGrandMinWeight)
			mGrandMinWeight = weight;
		if (mCalcStdDev)
			mGrandSquareSum += Math.pow(weight, 2);

	}

	public double sum() {
		double sum = 0;
		int stop = mCurr;
		if (mNumAdv >= mSlots)
			stop = mSlots + 1;
		// the useful data are all slots but the mCurr
		mTmp = 0;
		for (int i = 0; i < stop; i++) {
			if (i != mCurr) {
				sum += mWeights[i];
				mTmp += mCounts[i];
				// System.out.println("sum=" +sum);
			}
		}
		return sum;
	}

	public void reset() {
		destroyTimer();

		for (int i = 0; i < mWeights.length; i++) {
			mWeights[i] = 0;
			mCounts[i] = 0;
		}
		mCurr = 0;
		mTail = -1;
		mNumAdv = 0;

		mPrevIncidenceSum = 0;
		mLastIncidenceSum = 0;
		mMinIncidenceSum = -1;
		mMaxIncidenceSum = 0;
		mPrevWeightSum = 0.0;
		mLastWeightSum = 0.0;
		mMinWeightSum = -1.0;
		mMaxWeightSum = 0.0;

		mGrandIncidenceSum = 0;
		mGrandWeightSum = 0.0;
		mGrandMinWeight = -1.0;
		mGrandMaxWeight = 0.0;
		mGrandSquareSum = 0.0;

		startTimer();

	}

	public NamedArray getSummary() {
		Object[] d = new Object[sCols.length];

		long now = System.currentTimeMillis();
		double elapseTime = (now - mTimerStart) / 1000.0;
		boolean cycleInomplete = mNumAdv < mSlots;
		int i = 0;
		d[i++] = cycleInomplete ? null : new Double(mLastIncidenceSum / mPeriod);
		d[i++] = cycleInomplete ? null : new Double(mMinIncidenceSum < 0 ? 0.0 : mMinIncidenceSum / mPeriod);
		d[i++] = cycleInomplete ? null : new Double(mMaxIncidenceSum / mPeriod);
		d[i++] = cycleInomplete ? null : new Double((mLastIncidenceSum - mPrevIncidenceSum) / mPeriod);
		d[i++] = new Integer(mCounts[mCurr]);
		d[i++] = new Long(mGrandIncidenceSum);
		d[i++] = new Double(elapseTime == 0 ? 0.0 : mGrandIncidenceSum / elapseTime);

		d[i++] = cycleInomplete ? null : new Double(mLastWeightSum / mPeriod);
		d[i++] = cycleInomplete ? null : new Double(mMinWeightSum < 0 ? 0.0 : mMinWeightSum / mPeriod);
		d[i++] = cycleInomplete ? null : new Double(mMaxWeightSum / mPeriod);
		d[i++] = cycleInomplete ? null : new Double((mLastWeightSum - mPrevWeightSum) / mPeriod);
		d[i++] = new Double(mWeights[mCurr]);
		d[i++] = new Double(mGrandWeightSum);
		d[i++] = new Double(elapseTime == 0 ? 0.0 : mGrandWeightSum / elapseTime);

		d[i++] = mGrandIncidenceSum == 0 ? new Double(0.0) : new Double(mGrandWeightSum / mGrandIncidenceSum);
		d[i++] = new Double(mGrandMinWeight);
		d[i++] = new Double(mGrandMaxWeight);
		d[i++] = (!mCalcStdDev) || mGrandIncidenceSum == 0 ? new Double(0.0) : new Double(Math.sqrt(mGrandSquareSum
				/ mGrandIncidenceSum - Math.pow((mGrandWeightSum / mGrandIncidenceSum), 2)));

		NamedArray result = new NamedArray(sCols, d);
		// System.out.println("report: " + result);
		return result;

	}

}
