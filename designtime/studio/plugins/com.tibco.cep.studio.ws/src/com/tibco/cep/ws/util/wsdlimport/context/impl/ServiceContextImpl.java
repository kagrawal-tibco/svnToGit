package com.tibco.cep.ws.util.wsdlimport.context.impl;

import com.tibco.cep.ws.util.wsdlimport.context.ServiceContext;
import com.tibco.cep.ws.util.wsdlimport.context.WsdlContext;
import com.tibco.xml.ws.wsdl.WsService;


public class ServiceContextImpl
		extends AbstractImportContext
		implements ServiceContext {

	
	private final WsService service;
	private final WsdlContext wsdlContext;
	
	
	public ServiceContextImpl(
			WsdlContext wsdlContext,
			WsService service)
	{
		this.wsdlContext = wsdlContext;
		this.service = service;
	}


	@Override
	public WsService getService()
	{
		return this.service;
	}

	
	@Override
	public WsdlContext getWsdlContext()
	{
		return this.wsdlContext;
	}

}
