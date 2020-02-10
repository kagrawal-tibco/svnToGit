/**
 * 
 */
package com.tibco.rta.common.service.impl;

import java.util.Properties;

import com.tibco.rta.common.service.ShutdownService;
import com.tibco.rta.engine.RtaEngineExFactory;

/**
 * @author ssinghal
 *
 */
public class ShutdownServiceExImpl extends AbstractStartStopServiceImpl implements ShutdownService{

	@Override
	public void init(Properties configuration) throws Exception {
		super.init(configuration);
		Runtime.getRuntime().addShutdownHook(new ShutdownHandlerEx());
	}

	
	@Override
	public void shutdown() {
		new Thread (new ShutdownHandlerEx(), "shutdown-thread Ex").start();
		
	}
	
	 static class ShutdownHandlerEx extends Thread {
    	@Override
    	public void run() {
    		try {
    			RtaEngineExFactory.getInstance().getEngine().stop();
    		} catch (Throwable t) {
    			t.printStackTrace();
    		}
    	}
	 }
}
