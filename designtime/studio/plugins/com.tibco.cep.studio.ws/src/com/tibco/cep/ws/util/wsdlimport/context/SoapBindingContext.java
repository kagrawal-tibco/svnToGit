package com.tibco.cep.ws.util.wsdlimport.context;

import com.tibco.cep.ws.util.wsdlimport.Transport;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapBinding;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapStyle;


public interface SoapBindingContext
		extends BindingContext
{

	@Override
	SoapPortContext getPortContext();

	WsSoapBinding getSoapBinding();

	WsSoapStyle getSoapStyle();

	Transport getTransport();

}