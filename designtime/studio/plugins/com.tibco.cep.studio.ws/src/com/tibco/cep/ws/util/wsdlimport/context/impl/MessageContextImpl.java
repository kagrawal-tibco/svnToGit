package com.tibco.cep.ws.util.wsdlimport.context.impl;

import com.tibco.cep.ws.util.wsdlimport.context.MessageContext;
import com.tibco.cep.ws.util.wsdlimport.context.MessageReferenceContext;
import com.tibco.xml.ws.wsdl.WsOperationMessage;


public class MessageContextImpl
		extends AbstractImportContext
		implements MessageContext
{

		
	private final WsOperationMessage message;
	private final MessageReferenceContext messageReferenceContext;
	
	
	public MessageContextImpl(
			MessageReferenceContext messageReferenceContext,
			WsOperationMessage message)
	{
		this.messageReferenceContext = messageReferenceContext;
		this.message = message;
	}

	
	@Override
	public MessageReferenceContext getMessageReferenceContext()
	{
		return this.messageReferenceContext;
	}


	@Override
	public WsOperationMessage getMessage()
	{
		return this.message;
	}


}
