package com.tibco.cep.ws.util.wsdlimport.context;

import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapOperation;

public interface SoapOperationReferenceContext
		extends OperationReferenceContext
{
	
	WsSoapOperation getSoapOperation();
	
}
