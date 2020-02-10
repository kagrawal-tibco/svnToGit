package com.tibco.cep.ws.util.wsdlimport.context.impl;


import com.tibco.cep.ws.util.wsdlimport.context.BindingContext;
import com.tibco.cep.ws.util.wsdlimport.context.PortContext;
import com.tibco.xml.ws.wsdl.WsBinding;


public class BindingContextImpl
		extends AbstractImportContext
		implements BindingContext {

		
	protected final WsBinding binding;
	protected final PortContext portContext;
	
	
	public BindingContextImpl(
			PortContext portContext,
			WsBinding binding)
	{
		this.binding = binding;
		this.portContext = portContext;
	}

	
	@Override
	public WsBinding getBinding()
	{
		return this.binding;
	}

	
	@Override
	public PortContext getPortContext()
	{
		return this.portContext;
	}


}
