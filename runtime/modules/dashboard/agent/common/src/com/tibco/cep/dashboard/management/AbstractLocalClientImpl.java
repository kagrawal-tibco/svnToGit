package com.tibco.cep.dashboard.management;

import java.util.Iterator;


public abstract class AbstractLocalClientImpl implements Client {

	protected Service service;
	
	protected AbstractLocalClientImpl(String serviceName){
		Iterator<Service> childServices = ManagementService.getInstance().getChildServices();
		while (childServices.hasNext()) {
			Service tempService = childServices.next();
			if (serviceName.equals(tempService.getName()) == true){
				service = tempService;
				break;
			}
		}
		if (service == null){
			throw new IllegalArgumentException("could not find a service with name["+serviceName+"]");
		}
	}

	@Override
	public void cleanup() throws ManagementException {
		
	}

	@Override
	public STATE getStatus() {
		return service.getStatus();
	}

}
