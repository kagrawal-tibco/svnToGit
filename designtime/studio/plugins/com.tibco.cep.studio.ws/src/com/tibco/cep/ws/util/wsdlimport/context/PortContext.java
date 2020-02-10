package com.tibco.cep.ws.util.wsdlimport.context;


import com.tibco.xml.ws.wsdl.WsPort;


public interface PortContext
		extends ImportContext
{
	
	WsPort getPort();
	
	ServiceContext getServiceContext();
	
}
