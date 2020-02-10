package com.tibco.cep.dashboard.psvr.plugin;

import java.net.URL;
import java.util.Properties;

import javax.management.MBeanServer;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.ManagementConfigurator.MODE;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.ServiceContext;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author anpatil
 *
 */
public abstract class PlugIn {

	protected Logger logger;

	protected Properties properties;

	protected ExceptionHandler exceptionHandler;

	protected MessageGenerator messageGenerator;

	protected ServiceContext serviceContext;

	protected MODE mode;

	public abstract String getId();

	public abstract String getDescriptiveName();

	public abstract String getName();

	protected abstract void init() throws ManagementException;

	protected abstract void start() throws ManagementException;

	protected abstract void pause() throws ManagementException;

	protected abstract void unpause() throws ManagementException;

	protected abstract boolean stop();

	protected abstract boolean ping();

	public abstract int getStartOrder();

	public abstract boolean registerMBeans(MBeanServer server,String namePrefix);

	public abstract boolean unregisterMBeans(MBeanServer server);

	public abstract IResolver getResolver(ResolverType type);

	public abstract Builder getBuilder();

	public abstract URL getActionConfigURL();

	public abstract StartUp getStartUp();

	public abstract ShutDown getShutdown();

	public final Properties getProperties(){
		return properties;
	}

	public final ServiceContext getServiceContext(){
		return serviceContext;
	}

	public final Logger getLogger() {
		return logger;
	}

	public final ExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}

	public final MessageGenerator getMessageGenerator() {
		return messageGenerator;
	}

	public final MODE getMode() {
		return mode;
	}

	public String toString(){
		return "["+getDescriptiveName()+"]";
	}
}