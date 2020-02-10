package com.tibco.cep.dashboard.management;

import com.tibco.cep.dashboard.management.ManagementConfigurator.MODE;

public class ManagementClientLocalImpl implements ManagementClient {

	private ManagementService mgmtService;

	public ManagementClientLocalImpl() {
		mgmtService = ManagementService.getInstance();
	}

	@Override
	public MODE getMode() {
		return mgmtService.getMode();
	}

	@Override
	public STATE getStatus() {
		return mgmtService.getStatus();
	}
	
	@Override
	public String getName(){
		return mgmtService.getName();
	}		

	@Override
	public void cleanup() throws ManagementException {
		mgmtService = null;
	}
	


}
