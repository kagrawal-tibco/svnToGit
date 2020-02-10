package com.tibco.cep.ws.util.wsdlimport.context;


import com.tibco.xml.ws.wsdl.WsOperation;


public interface OperationContext
		extends ImportContext
{

	OperationReferenceContext getOperationReferenceContext();

	WsOperation getOperation();

	TransmissionType getTransmissionType();
}
