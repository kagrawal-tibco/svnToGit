package com.tibco.cep.ws.util.wsdlimport.context;

import com.tibco.xml.ws.wsdl.WsWsdl;


public interface WsdlContext
		extends ImportContext
{
	
	WsWsdl getWsdl();
	
}
