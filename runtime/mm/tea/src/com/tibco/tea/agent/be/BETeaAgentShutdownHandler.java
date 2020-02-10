package com.tibco.tea.agent.be;

import java.util.Properties;

import com.tibco.rta.common.service.ShutdownService;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;

public class BETeaAgentShutdownHandler extends AbstractStartStopServiceImpl implements ShutdownService {

	@Override
	public void init(Properties configuration) throws Exception {
		super.init(configuration);
		Runtime.getRuntime().addShutdownHook(new ShutdownHandler());
	}

	
	@Override
	public void shutdown() {
		new Thread (new ShutdownHandler(), "shutdown-thread Ex").start();
		
	}
	
	 static class ShutdownHandler extends Thread {
    	@Override
    	public void run() {
    		try {
    			BETeaAgentManager.INSTANCE.stop();
    		} catch (Exception e) {
    			
    		}
    	}
	 }
}