package com.tibco.cep.ws.util.wsdlimport.context;


import com.tibco.xml.ws.wsdl.WsOperationMessageReference;


public interface MessageReferenceContext
		extends ImportContext
{

	OperationReferenceContext getOperationReferenceContext();

	WsOperationMessageReference getOperationMessageReference();

}
