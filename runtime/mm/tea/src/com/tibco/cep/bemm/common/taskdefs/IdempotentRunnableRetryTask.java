package com.tibco.cep.bemm.common.taskdefs;

import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;

/**
 * @author vdhumal
 * Implements a Runnable for a retry Task
 */
public class IdempotentRunnableRetryTask extends IdempotentRetryTask implements Runnable {
	private Logger LOGGER = LogManagerFactory.getLogManager().getLogger(IdempotentRunnableRetryTask.class);
	
	public IdempotentRunnableRetryTask(Task wrappedTask, int retryCount, long waitTime) {
		super(wrappedTask, retryCount, waitTime);
	}

	@Override
	public void run() {
		try {
			perform();
		} catch (Throwable t) {
			LOGGER.log(Level.ERROR, t, t.getMessage());
		}
	}

}
