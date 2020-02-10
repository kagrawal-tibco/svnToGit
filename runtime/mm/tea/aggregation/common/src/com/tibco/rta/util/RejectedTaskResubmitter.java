package com.tibco.rta.util;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import com.tibco.rta.log.Level;
import com.tibco.rta.log.Logger;

public class RejectedTaskResubmitter implements RejectedExecutionHandler {
	Logger LOGGER;
	public RejectedTaskResubmitter (Logger LOGGER) {
		this.LOGGER = LOGGER;
	}
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
    	try {
            executor.getQueue().put(r);
        }
        catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "Job discarded, thread was interrupted", e);
        }
    }

}
