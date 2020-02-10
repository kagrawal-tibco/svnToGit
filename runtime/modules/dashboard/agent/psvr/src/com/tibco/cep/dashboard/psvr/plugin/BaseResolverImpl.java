package com.tibco.cep.dashboard.psvr.plugin;

import java.util.Collections;
import java.util.List;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.kernel.service.logging.Logger;

public abstract class BaseResolverImpl implements IResolver {
	
	protected String plugInID;
	protected ResolverType resolverType;
	
	protected Logger logger;
	protected ExceptionHandler exceptionHandler;
	protected MessageGenerator messageGenerator;
	
	protected List<Class<? extends AbstractHandler>> handlerClasses;
	
	protected BaseResolverImpl(String plugInID,ResolverType resolverType){
		this.plugInID = plugInID;
		this.resolverType = resolverType;
		handlerClasses = Collections.emptyList();
	}
	
	@Override
	public String getPlugInID() {
		return plugInID;
	}	
	
	@Override
	public ResolverType getType() {
		return resolverType;
	}

	@Override
	public void init(Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) throws PluginException {
    	this.logger = logger;
    	this.exceptionHandler = exceptionHandler;
    	this.messageGenerator = messageGenerator;
	}

	@Override
	public boolean isAcceptable(MALElement element) {
		return true;
	}

	@Override
	public List<Class<? extends AbstractHandler>> resolve(MALElement element) throws PluginException {
		return handlerClasses;
	}

}