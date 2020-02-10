package com.tibco.cep.driver.jms;

/**
 * A wait timeout strategy. The user of this interface gets calculated wait timeout for each iteration.
 * The implementing classes can use different strategies to calculate wait timeouts like simple constant timeout, linearly increasing timeout etc.
 * 
 * @author moshaikh
 */
public interface WaitTimeout {
	
	/**
	 * Returns the timeout duration for the specified attempt number.
	 * @param attempt The attempt number (1 as first attempt)
	 * @return
	 */
	public int getTimeoutDelay(int attempt);
}
