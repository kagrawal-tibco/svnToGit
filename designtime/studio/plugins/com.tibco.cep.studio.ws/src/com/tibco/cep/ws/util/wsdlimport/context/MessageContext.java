package com.tibco.cep.ws.util.wsdlimport.context;


import com.tibco.xml.ws.wsdl.WsOperationMessage;


public interface MessageContext
		extends ImportContext
{

	MessageReferenceContext getMessageReferenceContext();

	WsOperationMessage getMessage();

}
