package com.tibco.cep.dashboard.psvr;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.Service;
import com.tibco.cep.dashboard.management.ServicesRegistry;
import com.tibco.cep.kernel.service.logging.Level;

public class PSVRCoreService extends Service {

	public static final String NAME = "psvr";

	private static final String SERVICES_REG_LOCATION = "psvrservices.registry";

	public PSVRCoreService() throws ManagementException {
		super(NAME, "Presentation Server");
	}

	@Override
	protected void doInit() throws ManagementException {
		List<Service> services = loadServices();
		for (Service embeddedService : services) {
			addChildService(embeddedService);
		}		
	}
	
	private List<Service> loadServices() throws ManagementException {
		ServicesRegistry servicesRegistry = new ServicesRegistry(logger, SERVICES_REG_LOCATION);
		List<String> startupSequence = servicesRegistry.getServices(mode.toString());
		List<Service> embeddedServices = new ArrayList<Service>(startupSequence.size());
		for (String embeddedServiceclassName : startupSequence) {
			try {
				Class<? extends Service> controllableServiceClass = Class.forName(embeddedServiceclassName).asSubclass(Service.class);
				Service lifeCycleControllableInstance = (Service) controllableServiceClass.newInstance();
				embeddedServices.add(lifeCycleControllableInstance);
            } catch (ClassNotFoundException ex) {
            	exceptionHandler.handleException("could not find "+embeddedServiceclassName, ex, Level.ERROR, Level.ERROR);
                throw new ManagementException("could not find "+embeddedServiceclassName);
            } catch (InstantiationException e) {
            	exceptionHandler.handleException("could not instantiate "+embeddedServiceclassName, e, Level.ERROR, Level.ERROR);
                throw new ManagementException("could not instantiate "+embeddedServiceclassName);
            } catch (IllegalAccessException e) {
            	exceptionHandler.handleException("could not access "+embeddedServiceclassName, e, Level.ERROR, Level.ERROR);
                throw new ManagementException("could not access "+embeddedServiceclassName);
            }
		}
		return embeddedServices;
	}	

//	@Override
//	protected boolean doPing() {
//		return true;
//	}

}