package com.tibco.cep.dashboard.psvr.plugin;

import java.util.ArrayList;

public class SimpleResolverImpl extends BaseResolverImpl {

	protected SimpleResolverImpl(String plugInId,ResolverType resolverType,Class<? extends AbstractHandler> handlerClass){
		super(plugInId,resolverType);
		handlerClasses = new ArrayList<Class<? extends AbstractHandler>>(1);
		handlerClasses.add(handlerClass);		
	}	
}
