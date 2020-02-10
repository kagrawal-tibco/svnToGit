package com.tibco.rta.common.service.impl;

import java.util.Properties;

import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.rta.common.service.StartStopService;

/**
 * 
 * @author bgokhale
 * 
 *         Base implemmentation of a start/stop service
 */

abstract public class AbstractStartStopServiceImpl implements StartStopService {

	protected Properties configuration;

	protected boolean started;

	protected String serviceName;
	
	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(StartStopService.class);

	protected void setStarted(boolean started) {
		this.started = started;
	}

	@Override
	public void init(Properties configuration) throws Exception {
		this.configuration = configuration;

	}

	@Override
	synchronized public void start() throws Exception {
		started = true;
	}

	@Override
	synchronized public void stop() throws Exception {
		started = false;

	}

	@Override
	public void suspend() {

	}

	@Override
	public void resume() {

	}

	@Override
	public boolean isStarted() {
		return started;
	}
}
