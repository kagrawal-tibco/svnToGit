package com.tibco.cep.ws.util.wsdlimport.context.impl;

import com.tibco.cep.ws.util.wsdlimport.context.BindingContext;
import com.tibco.cep.ws.util.wsdlimport.context.OperationReferenceContext;
import com.tibco.xml.ws.wsdl.WsOperationReference;


public class OperationReferenceContextImpl
		extends AbstractImportContext
		implements OperationReferenceContext {

		
	private final BindingContext bindingContext;
	private final WsOperationReference operationReference;
	
	
	public OperationReferenceContextImpl(
			BindingContext bindingContext,
			WsOperationReference operationReference)
	{
		this.bindingContext = bindingContext;
		this.operationReference = operationReference;
	}

	
	@Override
	public BindingContext getBindingContext()
	{
		return this.bindingContext;
	}

	
	@Override
	public WsOperationReference getOperationReference()
	{
		return this.operationReference;
	}


}
