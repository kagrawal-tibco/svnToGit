package com.tibco.cep.runtime.management.impl.adapter.parent;


import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.management.impl.adapter.RemoteProcessManager;

import java.util.TimerTask;

/*
* Author: Ashwin Jayaprakash Date: Mar 11, 2009 Time: 10:05:39 AM
*/
public class RemoteProcessPingerTask extends TimerTask {
    protected RemoteProcessManager remoteProcessManager;

    protected long pingDelayMillis;

    protected Logger logger;

    protected int consecutivePingFailCount;

    public RemoteProcessPingerTask(RemoteProcessManager remoteProcessManager, long pingDelayMillis,
                                   Logger logger) {
        this.remoteProcessManager = remoteProcessManager;
        this.pingDelayMillis = pingDelayMillis;
        this.logger = logger;
    }

    public long getPingDelayMillis() {
        return pingDelayMillis;
    }

    public void run() {
        try {
            remoteProcessManager.ping();

            consecutivePingFailCount = 0;
        }
        catch (Exception e) {
            consecutivePingFailCount++;

            if (consecutivePingFailCount >= 3) {
            	if(consecutivePingFailCount == 3){
	            	this.logger.log(Level.ERROR, e,
	                        "Error occurred while attempting to ping child process."
	                                + " Recent number of consecutive failures [%s]",
	                        this.consecutivePingFailCount);
            	} else if(consecutivePingFailCount % 30 == 0) {
	            	this.logger.log(Level.ERROR,
	                        "Error occurred while attempting to ping child process."
	                                + " Recent number of consecutive failures [%s]. " +
                                    "If the problem persists and the monitored cluster " +
                                    "is active, please RESTART the BEMM server.",
                                    this.consecutivePingFailCount);
            	}
            }
        }
    }
}
