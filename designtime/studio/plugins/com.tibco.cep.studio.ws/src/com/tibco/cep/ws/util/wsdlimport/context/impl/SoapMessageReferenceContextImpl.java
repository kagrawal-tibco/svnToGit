package com.tibco.cep.ws.util.wsdlimport.context.impl;

import com.tibco.cep.ws.WsdlNames;
import com.tibco.cep.ws.util.wsdlimport.context.OperationReferenceContext;
import com.tibco.cep.ws.util.wsdlimport.context.SoapMessageReferenceContext;
import com.tibco.xml.ws.wsdl.WsExtensionElement;
import com.tibco.xml.ws.wsdl.WsOperationMessageReference;


public class SoapMessageReferenceContextImpl
		extends MessageReferenceContextImpl
		implements SoapMessageReferenceContext
{

	private WsExtensionElement soapBody;
	private WsExtensionElement soapHeader;
	private WsExtensionElement soapHeaderFault;

	 
	public SoapMessageReferenceContextImpl(
			OperationReferenceContext operationReferenceContext,
			WsOperationMessageReference messageReference)
	{
		super(operationReferenceContext, messageReference);

		WsExtensionElement element;
		
		element = messageReference.getExtensionElement(WsdlNames.SOAP11_BODY);
		this.soapBody = (element == null)
				? messageReference.getExtensionElement(WsdlNames.SOAP12_BODY)
				: element;

		element = messageReference.getExtensionElement(WsdlNames.SOAP11_HEADER);
		this.soapHeader = (element == null)
				? messageReference.getExtensionElement(WsdlNames.SOAP12_HEADER)
				: element;

		element = messageReference.getExtensionElement(WsdlNames.SOAP11_BODY);
		this.soapHeaderFault = (element == null)
			? messageReference.getExtensionElement(WsdlNames.SOAP12_BODY)
			: element;
}


	@Override
	public WsExtensionElement getSoapBody()
	{
		return this.soapBody;
	}


	@Override
	public WsExtensionElement getSoapHeader()
	{
		return this.soapHeader;
	}


	@Override
	public WsExtensionElement getSoapHeaderFault()
	{
		return this.soapHeaderFault;
	}

}
