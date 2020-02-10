package com.tibco.cep.dashboard.management;

import java.util.Properties;

import com.tibco.cep.dashboard.management.ManagementConfigurator.MODE;
import com.tibco.cep.kernel.service.logging.Logger;

public abstract class ServiceDependent extends Manageable {

	protected Service parent;

	protected ServiceDependent(String name, String descriptiveName) {
		super(name, descriptiveName);
	}

	//TODO fix the init calling semantics
	@Override
	public void init(Logger parentLogger, MODE mode, Properties properties, ServiceContext serviceContext) throws ManagementException {
		doInit();
		state = STATE.INITIALIZED;
	}

	@Override
	protected void doInit() throws ManagementException {
	}

	protected boolean isRunning(){
		return STATE.RUNNING.equals(state);
	}
}