package com.tibco.cep.ws.util.wsdlimport.context;


import com.tibco.xml.ws.wsdl.WsOperationReference;


public interface OperationReferenceContext
		extends ImportContext
{

	BindingContext getBindingContext();

	WsOperationReference getOperationReference();


}
