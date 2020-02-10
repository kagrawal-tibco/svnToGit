package com.tibco.cep.ws.util.wsdlimport.context;


import com.tibco.xml.ws.wsdl.WsService;


public interface ServiceContext
		extends ImportContext
{

	WsService getService();

	WsdlContext getWsdlContext();
	
}
