package com.tibco.cep.ws.util.wsdlimport.context;

import java.net.URI;
import java.util.Map;


public interface SoapPortContext
		extends PortContext
{

	URI getSoapAddressUri();

	Map<String, String> getSoapAddressQueryParameters();

	String getSoapAddressPath();
	
}
