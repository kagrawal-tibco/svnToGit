package com.tibco.cep.ws.util.wsdlimport.context;


import com.tibco.xml.ws.wsdl.WsBinding;


public interface BindingContext
		extends ImportContext
{

	WsBinding getBinding();
	
	PortContext getPortContext();
	
}
