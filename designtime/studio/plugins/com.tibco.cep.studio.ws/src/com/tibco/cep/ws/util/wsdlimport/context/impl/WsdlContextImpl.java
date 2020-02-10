package com.tibco.cep.ws.util.wsdlimport.context.impl;

import com.tibco.cep.ws.util.wsdlimport.context.WsdlContext;
import com.tibco.xml.ws.wsdl.WsWsdl;


public class WsdlContextImpl
		extends AbstractImportContext
		implements WsdlContext {

	
	private final WsWsdl wsdl;
	
	
	public WsdlContextImpl(
			WsWsdl wsdl)
	{
		this.wsdl = wsdl;
	}
	
	
	@Override
	public WsWsdl getWsdl()
	{
		return this.wsdl;
	}

	
}
