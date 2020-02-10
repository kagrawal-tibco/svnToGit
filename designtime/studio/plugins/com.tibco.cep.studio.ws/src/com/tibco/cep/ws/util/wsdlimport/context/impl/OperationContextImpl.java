package com.tibco.cep.ws.util.wsdlimport.context.impl;

import java.util.Iterator;

import com.tibco.cep.ws.util.wsdlimport.context.OperationContext;
import com.tibco.cep.ws.util.wsdlimport.context.OperationReferenceContext;
import com.tibco.cep.ws.util.wsdlimport.context.TransmissionType;
import com.tibco.xml.ws.wsdl.WsMessageKind;
import com.tibco.xml.ws.wsdl.WsOperation;
import com.tibco.xml.ws.wsdl.WsOperationMessage;


public class OperationContextImpl
		extends AbstractImportContext
		implements OperationContext {

		
	private final WsOperation operation;
	private final OperationReferenceContext operationReferenceContext;
	private TransmissionType transmissionType;
	
	
	public OperationContextImpl(
			OperationReferenceContext operationReferenceContext,
			WsOperation operation)
	{
		this.operationReferenceContext = operationReferenceContext;
		this.operation = operation;
		
		@SuppressWarnings("unchecked")
		final Iterator<WsOperationMessage> i = operation.getMessages();
		if (WsMessageKind.INPUT.equals(i.next().getMessageKind())) {
			this.transmissionType = i.hasNext()
					? TransmissionType.REQUEST_RESPONSE
					: TransmissionType.ONE_WAY;
		} else {
			this.transmissionType = i.hasNext()
					? TransmissionType.SOLICIT_RESPONSE
					: TransmissionType.NOTIFICATION;			
		}
	}

	
	@Override
	public OperationReferenceContext getOperationReferenceContext()
	{
		return this.operationReferenceContext;
	}

	
	@Override
	public WsOperation getOperation()
	{
		return this.operation;
	}


	@Override
	public TransmissionType getTransmissionType()
	{
		return this.transmissionType;
	}


}
