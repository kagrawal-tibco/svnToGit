package com.tibco.cep.dashboard.management;

import java.util.Properties;

import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.dashboard.management.ManagementConfigurator.MODE;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author apatil
 * 
 */
public abstract class Manageable {

	protected String name;

	protected String descriptiveName;

	protected STATE state;

	protected Logger logger;

	protected MessageGenerator messageGenerator;

	protected ExceptionHandler exceptionHandler;

	protected MODE mode;

	protected Properties properties;

	protected ServiceContext serviceContext;

	protected Manageable(String name, String descriptiveName) {
		this.name = name;
		this.descriptiveName = descriptiveName;
		state = STATE.UNINITIALIZED;
	}

	public void init(Logger parentLogger, MODE mode, Properties properties, ServiceContext serviceContext) throws ManagementException {
		if (mode == null) {
			throw new IllegalArgumentException("mode cannot be null");
		}
		this.mode = mode;
		this.properties = properties;
		this.serviceContext = serviceContext;
		initLogger(parentLogger);
		this.exceptionHandler = new ExceptionHandler(logger);
		this.messageGenerator = new MessageGenerator(name, exceptionHandler);
		doInit();
		state = STATE.INITIALIZED;
	}

	protected final void initLogger(Logger parentLogger) {
		if (logger == null) {
			if (parentLogger == null) {
				parentLogger = LoggingService.getRootLogger();
			}
			this.logger = LoggingService.getChildLogger(parentLogger, name);
		}
	}

	protected abstract void doInit() throws ManagementException;

	public void start() throws ManagementException {
		doStart();
		state = STATE.RUNNING;
	}

	protected void doStart() throws ManagementException {
		// do nothing
	}

	public void pause() throws ManagementException {
		doPause();
		state = STATE.PAUSED;
	}

	protected void doPause() throws ManagementException {
		// by default we do nothing
	}

	public void resume() throws ManagementException {
		doResume();
		state = STATE.RUNNING;
	}

	protected void doResume() throws ManagementException {
		// by default we do nothing
	}

	public boolean stop() {
		boolean stopped = doStop();
		if (stopped == true) {
			state = STATE.STOPPED;
		}
		return stopped;
	}

	protected boolean doStop(){
		//by default we do nothing
		return true;
	}

	public STATE getStatus() {
		return state;
	}

	public MODE getMode() {
		return mode;
	}

	public String getName() {
		return name;
	}

	public String getDescriptiveName() {
		return descriptiveName;
	}
}