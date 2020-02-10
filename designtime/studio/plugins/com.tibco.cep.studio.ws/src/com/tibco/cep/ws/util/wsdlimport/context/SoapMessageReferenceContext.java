package com.tibco.cep.ws.util.wsdlimport.context;

import com.tibco.xml.ws.wsdl.WsExtensionElement;


public interface SoapMessageReferenceContext
		extends MessageReferenceContext
{
	
	WsExtensionElement getSoapBody();
	
	WsExtensionElement getSoapHeader();
	
	WsExtensionElement getSoapHeaderFault();
	
}
