package com.tibco.cep.ws.util.wsdlimport.context.impl;

import com.tibco.cep.ws.util.wsdlimport.context.PortContext;
import com.tibco.cep.ws.util.wsdlimport.context.ServiceContext;
import com.tibco.xml.ws.wsdl.WsPort;


public class PortContextImpl
		extends AbstractImportContext
		implements PortContext {

		
	private final WsPort port;
	private final ServiceContext serviceContext;
	

	public PortContextImpl(
			ServiceContext serviceContext,
			WsPort port
			)
	{
		this.port = port;
		this.serviceContext = serviceContext;
	}


	@Override
	public WsPort getPort()
	{
		return this.port;
	}

	
	@Override
	public ServiceContext getServiceContext()
	{
		return this.serviceContext;
	}

}
