package com.tibco.rta.common.service.impl;

import java.util.Properties;

import com.tibco.rta.common.service.ShutdownService;

import com.tibco.rta.engine.RtaEngineFactory;

public class ShutdownServiceImpl extends AbstractStartStopServiceImpl implements ShutdownService  {

	@Override
	public void init(Properties configuration) throws Exception {
		super.init(configuration);
		Runtime.getRuntime().addShutdownHook(new ShutdownHandler());
	}

	@Override
	public void shutdown() {
		new Thread (new ShutdownHandler(), "shutdown-thread").start();
	}
	
    static class ShutdownHandler extends Thread {
    	@Override
    	public void run() {
    		try {
    			RtaEngineFactory.getInstance().getEngine().stop();
    		} catch (Throwable t) {
    			t.printStackTrace();
    		}
    	}
    }

}
