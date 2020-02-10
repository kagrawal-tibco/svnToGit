package com.tibco.cep.ws.util.wsdlimport.context.impl;

import com.tibco.cep.ws.WsdlNames;
import com.tibco.cep.ws.util.wsdlimport.context.BindingContext;
import com.tibco.cep.ws.util.wsdlimport.context.SoapOperationReferenceContext;
import com.tibco.xml.ws.wsdl.WsExtensionElement;
import com.tibco.xml.ws.wsdl.WsOperationReference;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapOperation;


public class SoapOperationReferenceContextImpl
		extends OperationReferenceContextImpl
		implements SoapOperationReferenceContext
{

	 private final WsSoapOperation soapOperation;

	 
	 public SoapOperationReferenceContextImpl(
			BindingContext bindingContext,
			WsOperationReference operationReference)
	{
		super(bindingContext, operationReference);

		WsExtensionElement element =
				operationReference.getExtensionElement(WsdlNames.SOAP11_OPERATION);
		if (element == null) {
			element =
				operationReference.getExtensionElement(WsdlNames.SOAP12_OPERATION);
		}		
		this.soapOperation = (WsSoapOperation) element;

	}

	 
	@Override
	public WsSoapOperation getSoapOperation()
	{
		return this.soapOperation;
	}
	

}
