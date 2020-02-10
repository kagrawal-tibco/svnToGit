package com.tibco.cep.dashboard.common.utils;

import java.security.SecureRandom;

// Should create an instance of this and then use it throughout per JVM or per thread.
public class UUIDGen {
	
	// Constant
	public static final int UUID_LENGTH = 16;
	public static final int MAC_ADDRESS_LENGTH = 6;
	public static final int CLOCK_SEQ_LENGTH = 2;

	// Multiplier to get to 100 nsec resolution.
	private final static long CYCLE_MULTIPLIER = 10000L;
	private final static int CYCLE_MAX_LIMIT = 10000;

	// Offset to move time from 1/1/1970 to 10/10/1582.
	private final static long CALENDAR_OFFSET = 0x01b21dd213814000L;

	// Offset into the UUID array.
	public final static int INDEX_TIME_LOW = 0;
	public final static int INDEX_TIME_MID = 4;
	public final static int INDEX_TIME_HI = 6;
	public final static int INDEX_CLOCK_SEQ = 8;
	public final static int INDEX_MAC_ADDRESS = 10;

	// Class globals.
	private static SecureRandom sRandom = new SecureRandom();

	// Instance variables.
	private final byte[] mMacAddress = new byte[MAC_ADDRESS_LENGTH];
	private final byte[] mClockSequence = new byte[CLOCK_SEQ_LENGTH];
	private int mCycleCounter = 0;
	private long mPreviousTimeMS = 0;
	private final byte[] mNextUuid = new byte[UUID_LENGTH];

	public UUIDGen() {
		// Generate a random MAC address.
		sRandom.nextBytes(mMacAddress);
		mMacAddress[0] |= (byte) 0x01; // Set the broadcast flag to not collide
										// with a real address.

		// Use a random sequence to handle the possibility that the computer
		// clock is set backward.
		sRandom.nextBytes(mClockSequence);

		// Pre-copy unchanged data to the working UUID array.
		System.arraycopy(mMacAddress, 0, mNextUuid, INDEX_MAC_ADDRESS, MAC_ADDRESS_LENGTH);
		System.arraycopy(mClockSequence, 0, mNextUuid, INDEX_CLOCK_SEQ, CLOCK_SEQ_LENGTH);
	}

	public synchronized void createId(StringBuffer uuidResult) {
		nextUuid();
		HexUtil.bytesToHex(mNextUuid, 0, UUID_LENGTH, uuidResult);
	}

	public synchronized String createId() {
		StringBuffer uuidResult = new StringBuffer();
		createId(uuidResult);
		return uuidResult.toString();
	}

	private synchronized void nextUuid() {
		long currTimeMS = System.currentTimeMillis();

		if (currTimeMS < mPreviousTimeMS) {
			// Re-generate clock sequence in case the clock has been rewound.
			sRandom.nextBytes(mClockSequence);
			System.arraycopy(mClockSequence, 0, mNextUuid, INDEX_CLOCK_SEQ, CLOCK_SEQ_LENGTH);
		} else if (currTimeMS > mPreviousTimeMS) {
			// Clock moves beyond the current cycle. Start a new cycle. Reset
			// cycle counter.
			mCycleCounter = 0;
			mPreviousTimeMS = currTimeMS;
		} else {
			// Still within the same cycle.
			if (mCycleCounter >= CYCLE_MAX_LIMIT) {
				// Run out of room in the cycle. Need to wait for the next
				// cycle.
				while ((currTimeMS = System.currentTimeMillis()) == mPreviousTimeMS) {
					try {
						Thread.sleep(1L);
					} catch (Exception ignored) {
					}

				}
				mCycleCounter = 0;
				mPreviousTimeMS = currTimeMS;
			}
		}

		// Convert time in ms to 100ns and from 1/1/1970 to 10/10/1582, plus
		// cycle counter. Inc counter.
		long uuidTimeNS = currTimeMS * CYCLE_MULTIPLIER + CALENDAR_OFFSET + (mCycleCounter++);

		// Lay out the uuid time bits.
		int timeLow = (int) uuidTimeNS;
		int timeMid = (int) (uuidTimeNS >>> 32) & 0xFFFF;
		int timeHi = (int) ((uuidTimeNS >>> 32) >>> 16) & 0xFFFF;
		mNextUuid[INDEX_TIME_LOW] = (byte) (timeLow >>> 24);
		mNextUuid[INDEX_TIME_LOW + 1] = (byte) (timeLow >>> 16);
		mNextUuid[INDEX_TIME_LOW + 2] = (byte) (timeLow >>> 8);
		mNextUuid[INDEX_TIME_LOW + 3] = (byte) (timeLow);
		mNextUuid[INDEX_TIME_MID] = (byte) (timeMid >>> 8);
		mNextUuid[INDEX_TIME_MID + 1] = (byte) (timeMid);
		mNextUuid[INDEX_TIME_HI] = (byte) (timeHi >>> 8);
		mNextUuid[INDEX_TIME_HI + 1] = (byte) (timeHi);
	}

}
