package com.tibco.cep.dashboard.management;

import com.tibco.cep.dashboard.management.ManagementConfigurator.MODE;

public interface ManagementClient extends Client {
	
	public STATE getStatus();
	
	public MODE getMode();
	
	public String getName();

}
