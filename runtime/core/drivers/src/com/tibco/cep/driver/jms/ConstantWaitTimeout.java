package com.tibco.cep.driver.jms;

/**
 * A simple implementation for constant wait timeout.
 * 
 * @author moshaikh
 *
 */
public class ConstantWaitTimeout implements WaitTimeout {

	private int timeout;
	
	public ConstantWaitTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	@Override
	public int getTimeoutDelay(int attempt) {
		return timeout;
	}
}
