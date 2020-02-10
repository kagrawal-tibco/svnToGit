package com.tibco.cep.dashboard.psvr.plugin;

import java.util.List;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.kernel.service.logging.Logger;


public interface IResolver {
	
	public void init(Logger logger,ExceptionHandler exceptionHandler,MessageGenerator messageGenerator) throws PluginException;
	
	public ResolverType getType();
	
	public String getPlugInID();

	public boolean isAcceptable(MALElement element);
	
	public List<Class<? extends AbstractHandler>> resolve(MALElement element) throws PluginException;
}
