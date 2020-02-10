package com.tibco.cep.driver.jms;

import java.util.Random;

/**
 * Provides randomised linearly growing wait duration.<br>
 * The returned timeout values grows linearly(plus randomised) upto maxWait and then onwards always returns maxWait.<br>
 * 
 * @author moshaikh
 *
 */
public class LinearWaitTimeout implements WaitTimeout {

	/**
	 * The default value for the multiple used to calculate wait for each attempt.
	 */
	public static final int DEFAULT_MULTIPLIER = 2;
	
	/**
	 * The default value to multiply minWait with, to get the value for maxWait. 
	 */
	public static final int DEFAULT_MAX_WAIT_MULTIPLE = 15;
	
	private final int minWait;
	private final int maxWait;
	private final int multiplier;
	
	/**
	 * 
	 * @param minWait
	 * @param maxWait
	 * @param multiplier
	 */
	public LinearWaitTimeout(int minWait, int maxWait, int multiplier) {
		this.minWait = minWait;
		this.maxWait = maxWait;
		this.multiplier = multiplier;
	}
	
	@Override
	public int getTimeoutDelay(int attempt) {
		//For each attempt min and max waits are calculated using below expressions and then a random value between these min and max waits is returned.
		// Minimum wait for an attempt -> minWait * (k-1) * M	; k:attempt number, M:linear multiple
		// Maximum wait for an attempt -> minWait * k * M
		int currentAttemptMinWait;
		int currentAttemptMaxWait;
		if (attempt <= 1) {
			currentAttemptMinWait = minWait;
			currentAttemptMaxWait = minWait * multiplier;
		}
		else {
			currentAttemptMinWait = minWait * (attempt-1) * multiplier;
			currentAttemptMaxWait = minWait * (attempt) * multiplier;
		}
		int returnVal;
		if (currentAttemptMaxWait <= currentAttemptMinWait || currentAttemptMaxWait > maxWait) {//The wait should always be less than or equal to maxWait.
			returnVal = maxWait;//Do not randomise if the value reached maxWait.
		}
		else {
			returnVal = currentAttemptMinWait + new Random().nextInt(currentAttemptMaxWait - currentAttemptMinWait);//Randomise value beyond currentAttemptMinWait (leaving currentAttemptMinWait as fixed)
		}
		return returnVal;
	}
}
