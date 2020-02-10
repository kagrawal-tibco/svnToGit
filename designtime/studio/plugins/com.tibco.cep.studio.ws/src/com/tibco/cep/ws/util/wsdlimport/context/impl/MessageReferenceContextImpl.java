package com.tibco.cep.ws.util.wsdlimport.context.impl;

import com.tibco.cep.ws.util.wsdlimport.context.MessageReferenceContext;
import com.tibco.cep.ws.util.wsdlimport.context.OperationReferenceContext;
import com.tibco.xml.ws.wsdl.WsOperationMessageReference;


public class MessageReferenceContextImpl
		extends AbstractImportContext
		implements MessageReferenceContext
{

		
	private final WsOperationMessageReference messageReference;
	private final OperationReferenceContext operationReferenceContext;
	
	
	public MessageReferenceContextImpl(
			OperationReferenceContext operationReferenceContext,
			WsOperationMessageReference messageReference)
	{
		this.operationReferenceContext = operationReferenceContext;
		this.messageReference = messageReference;
	}

	
	@Override
	public OperationReferenceContext getOperationReferenceContext()
	{
		return this.operationReferenceContext;
	}


	@Override
	public WsOperationMessageReference getOperationMessageReference()
	{		
		return this.messageReference;
	}


}
