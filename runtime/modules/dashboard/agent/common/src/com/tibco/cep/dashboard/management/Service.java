package com.tibco.cep.dashboard.management;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.tibco.cep.dashboard.management.ManagementConfigurator.MODE;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author apatil
 * 
 */
public abstract class Service extends Manageable {

	public static enum UNAVAILABILITY_STRATEGY {
		BLOCK, EXCEPTION
	};

	protected String name;

	protected String descriptiveName;

	protected UNAVAILABILITY_STRATEGY strategy;

	protected Service parent;

	protected LinkedHashMap<String, Service> childServices;

	protected LinkedList<ServiceDependent> dependents;

	protected MBeanServer beanServer;

	protected ObjectName baseName;

	protected Service(String name, String descriptiveName) {
		super(name, descriptiveName);
		this.childServices = new LinkedHashMap<String, Service>();
		this.dependents = new LinkedList<ServiceDependent>();
	}

	void setBeanServer(MBeanServer beanServer) {
		this.beanServer = beanServer;
		if (childServices.isEmpty() == false){
			for (Service childService : childServices.values()) {
				childService.setBeanServer(beanServer);
			}
		}
	}

	void setBaseName(ObjectName baseName) {
		this.baseName = baseName;
		if (childServices.isEmpty() == false){
			for (Service childService : childServices.values()) {
				childService.setBaseName(baseName);
			}
		}		
	}

	protected void addChildService(Service childService) throws ManagementException {
		if (childService == this) {
			throw new IllegalArgumentException("cannot add itself as a child service");
		}
		if (childServices.containsKey(childService.getName()) == true) {
			throw new ManagementException(descriptiveName + " already contains an service named '" + childService.getName() + "'");
		}
		childServices.put(childService.getName(), childService);
		childService.parent = this;
		childService.setBeanServer(beanServer);
		childService.setBaseName(baseName);
	}

	protected void removeChildService(Service childService) throws ManagementException {
		childServices.remove(childService.getName());
		childService.parent = null;
		childService.setBeanServer(null);
		childService.setBaseName(null);
	}

	protected Iterator<Service> getChildServices() {
		return childServices.values().iterator();
	}

	protected void addDependent(ServiceDependent dependent) {
		if (dependents.contains(dependent) == false) {
			dependent.parent = this;
			dependent.mode = mode;
			dependent.logger = logger;
			dependent.exceptionHandler = exceptionHandler;
			dependent.messageGenerator = messageGenerator;
			dependent.properties = properties;
			dependent.serviceContext = serviceContext;
			dependents.add(dependent);
		}
	}

	protected void removeDependent(ServiceDependent dependent) {
		dependents.remove(dependent);
	}

	protected Iterator<ServiceDependent> getDependents() {
		return dependents.iterator();
	}

	@Override
	public void init(Logger parentLogger, MODE mode, Properties properties, ServiceContext serviceContext) throws ManagementException {
		initLogger(parentLogger);
		logger.log(Level.INFO, "Attempting to initialize " + getDescriptiveName());
		super.init(parentLogger, mode, properties, serviceContext);
		if (childServices.isEmpty() == false) {
			logger.log(Level.DEBUG, childServices.size() + " services(s) will be initialized...");
			for (Service childService : childServices.values()) {
				logger.log(Level.DEBUG, "Attempting to initialize " + getDescriptiveName() + "/" + childService.getDescriptiveName());
				childService.init(logger, mode, properties, serviceContext);
				logger.log(Level.DEBUG, getDescriptiveName() + "/" + childService.getDescriptiveName() + " has been initialized");
			}
			logger.log(Level.DEBUG, childServices.size() + " services(s) have been initialized...");
		}
		if (dependents.isEmpty() == false) {
			logger.log(Level.DEBUG, dependents.size() + " dependent(s) will be initialized...");
			for (ServiceDependent dependent : dependents) {
				logger.log(Level.DEBUG, "Attempting to initialize " + getDescriptiveName() + "/" + dependent.getDescriptiveName());
				dependent.init(logger, mode, properties, serviceContext);
				logger.log(Level.DEBUG, getDescriptiveName() + "/" + dependent.getDescriptiveName() + " has been initialized");
			}
			logger.log(Level.DEBUG, dependents.size() + " dependent(s) have been initialized...");
		}
		logger.log(Level.INFO, getDescriptiveName() + " has been initialized");
	}

	@Override
	public void start() throws ManagementException {
		logger.log(Level.INFO, "Attempting to start " + getDescriptiveName());
		super.start();
		if (childServices.isEmpty() == false) {
			logger.log(Level.DEBUG,childServices.size()+" services(s) will be started...");	
			for (Service childService : childServices.values()) {
				logger.log(Level.DEBUG, "Attempting to start " + getDescriptiveName() + "/" + childService.getDescriptiveName());
				childService.start();
				logger.log(Level.DEBUG, getDescriptiveName() + "/" + childService.getDescriptiveName() + " has been started");
			}
			logger.log(Level.DEBUG, childServices.size() + " services(s) have been started...");
		}
		if (dependents.isEmpty() == false) {
			logger.log(Level.DEBUG,dependents.size()+" dependent(s) will be started...");	
			for (ServiceDependent dependent : dependents) {
				logger.log(Level.DEBUG, "Attempting to start " + getDescriptiveName() + "/" + dependent.getDescriptiveName());
				dependent.start();
				logger.log(Level.DEBUG, getDescriptiveName() + "/" + dependent.getDescriptiveName() + " has been started");
			}
			logger.log(Level.DEBUG,dependents.size()+" dependent(s) have been started...");
		}
		if (beanServer != null && baseName != null) {
			registerMBeans(beanServer, baseName);
		}
		synchronized (this) {
			this.notifyAll();
		}
		logger.log(Level.INFO, getDescriptiveName() + " has been started");
	}

	@Override
	public void pause() throws ManagementException {
		logger.log(Level.INFO, "Attempting to pause " + getDescriptiveName());
		if (dependents.isEmpty() == false) {
			LinkedList<ServiceDependent> dependentsReversed = new LinkedList<ServiceDependent>(dependents);
			Collections.reverse(dependentsReversed);
			logger.log(Level.DEBUG, dependentsReversed.size() + " dependent(s) will be paused...");
			for (ServiceDependent dependent : dependentsReversed) {
				logger.log(Level.DEBUG, "Attempting to pause " + getDescriptiveName() + "/" + dependent.getDescriptiveName());
				dependent.pause();
				logger.log(Level.DEBUG, getDescriptiveName() + "/" + dependent.getDescriptiveName() + " has been paused");
			}
			logger.log(Level.DEBUG, dependentsReversed.size() + " dependent(s) have been paused...");
		}
		if (childServices.isEmpty() == false) {
			LinkedList<Service> services = new LinkedList<Service>(childServices.values());
			Collections.reverse(services);
			logger.log(Level.DEBUG, services.size() + " services(s) will be paused...");
			for (Service embeddedService : services) {
				logger.log(Level.DEBUG, "Attempting to pause " + getDescriptiveName() + "/" + embeddedService.getDescriptiveName());
				embeddedService.pause();
				logger.log(Level.DEBUG, getDescriptiveName() + "/" + embeddedService.getDescriptiveName() + " has been paused");
			}
			logger.log(Level.DEBUG, services.size() + " service(s) have been paused...");
		}
		super.pause();
		logger.log(Level.INFO, getDescriptiveName() + " has been paused");
	}

	@Override
	public void resume() throws ManagementException {
		logger.log(Level.INFO, "Attempting to resume " + getDescriptiveName());
		super.resume();
		if (childServices.isEmpty() == false) {
			logger.log(Level.DEBUG, childServices.size() + " services(s) will be resumed...");
			for (Service embeddedService : childServices.values()) {
				logger.log(Level.DEBUG, "Attempting to resume " + getDescriptiveName() + "/" + embeddedService.getDescriptiveName());
				embeddedService.resume();
				logger.log(Level.DEBUG, getDescriptiveName() + "/" + embeddedService.getDescriptiveName() + " has been resumed");
			}
			logger.log(Level.DEBUG, childServices.size() + " services(s) will be resumed...");
		}
		if (dependents.isEmpty() == false) {
			logger.log(Level.DEBUG, dependents.size() + " dependent(s) will be resumed...");
			for (ServiceDependent dependent : dependents) {
				logger.log(Level.DEBUG, "Attempting to resume " + getDescriptiveName() + "/" + dependent.getDescriptiveName());
				dependent.resume();
			}
			logger.log(Level.DEBUG, dependents.size() + " dependent(s) have been resumed...");
		}
		synchronized (this) {
			this.notifyAll();
		}
		logger.log(Level.INFO, getDescriptiveName() + " has been resumed");
	}

	@Override
	public boolean stop() {
		logger.log(Level.INFO, "Attempting to stop " + getDescriptiveName());
		if (dependents.isEmpty() == false) {
			LinkedList<ServiceDependent> dependentsReversed = new LinkedList<ServiceDependent>(dependents);
			Collections.reverse(dependentsReversed);			
			logger.log(Level.DEBUG, dependentsReversed.size() + " dependent(s) will be stopped...");
			for (ServiceDependent dependent : dependentsReversed) {
				logger.log(Level.DEBUG, "Attempting to stop " + getDescriptiveName() + "/" + dependent.getDescriptiveName());
				dependent.stop();
				logger.log(Level.DEBUG, getDescriptiveName() + "/" + dependent.getDescriptiveName() + " has been paused");
			}
			logger.log(Level.DEBUG, dependentsReversed.size() + " dependent(s) have been stopped...");
		}
		List<String> failedToStopServices = new LinkedList<String>();
		if (childServices.isEmpty() == false){
			logger.log(Level.DEBUG, childServices.size() + " services(s) will be stopped...");
			stopChildServices(failedToStopServices);
			logger.log(Level.DEBUG, childServices.size()-failedToStopServices.size() + " services(s) have been stopped...");
		}
		boolean stopped = super.stop();
		if (stopped == false) {
			failedToStopServices.add(descriptiveName);
		}
		stopped = failedToStopServices.isEmpty();
		if (stopped == false) {
			logger.log(Level.WARN, getDescriptiveName() + " has not been fully stopped");
		}
		else {
			logger.log(Level.INFO, getDescriptiveName() + " has been stopped");
		}
		return stopped;
	}

	private void stopChildServices(List<String> failedServices) {
		LinkedList<Service> services = new LinkedList<Service>(childServices.values());
		Collections.reverse(services);
		for (Service embeddedService : services) {
			logger.log(Level.DEBUG, "Attempting to stop " + getDescriptiveName() + "/" + embeddedService.getDescriptiveName());
			if (embeddedService.stop() == false) {
				failedServices.add(descriptiveName + "." + embeddedService.getDescriptiveName());
				logger.log(Level.WARN, getDescriptiveName() + "/" + embeddedService.getDescriptiveName() + " could not be stopped successfully");
			} else {
				logger.log(Level.DEBUG, getDescriptiveName() + "/" + embeddedService.getDescriptiveName() + " has been stopped");
			}
		}
	}

	boolean ping() {
		if (doPing()) {
			for (Service embeddedService : childServices.values()) {
				if (embeddedService.ping() == false) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	protected boolean doPing(){
		return true;
	}

	public void checkAvailability() throws ServiceUnavailableException {
		if (state != STATE.RUNNING) {
			if (strategy == UNAVAILABILITY_STRATEGY.EXCEPTION) {
				throw new ServiceUnavailableException(descriptiveName, state);
			} else {
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						logger.log(Level.WARN, "could not block " + Thread.currentThread().getName() + "...");
						throw new ServiceUnavailableException(descriptiveName, state);
					}
				}
			}
		}
	}

	public void setUnavailabilityStrategy(UNAVAILABILITY_STRATEGY strategy) {
		this.strategy = strategy;
	}

	public UNAVAILABILITY_STRATEGY getUnavailabilityStrategy() {
		return this.strategy;
	}

	// public String getMessage(Throwable t) {
	// return exceptionHandler.getMessage(t);
	// }

	protected void registerMBeans(MBeanServer server, ObjectName baseName) {

	}

}