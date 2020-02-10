package com.tibco.cep.ws.util.wsdlimport.context.impl;


import com.tibco.cep.ws.WsdlNames;
import com.tibco.cep.ws.util.wsdlimport.Transport;
import com.tibco.cep.ws.util.wsdlimport.context.PortContext;
import com.tibco.cep.ws.util.wsdlimport.context.SoapBindingContext;
import com.tibco.cep.ws.util.wsdlimport.context.SoapPortContext;
import com.tibco.xml.ws.wsdl.WsBinding;
import com.tibco.xml.ws.wsdl.WsExtensionElement;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapBinding;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapStyle;


public class SoapBindingContextImpl
		extends BindingContextImpl
		implements SoapBindingContext {

		
	private final WsSoapBinding soapBinding;
	private WsSoapStyle style;
	private Transport transport;
	
	
	public SoapBindingContextImpl(
			PortContext portContext,
			WsBinding binding)
	{
		super(portContext, binding);

		WsExtensionElement element = this.binding.getExtensionElement(WsdlNames.SOAP11_BINDING);
		if (null == element) {
			element = this.binding.getExtensionElement(WsdlNames.SOAP12_BINDING);
		}
		this.soapBinding = (WsSoapBinding) element;
		this.transport = (null == this.soapBinding)
				? null
				: Transport.fromString(this.soapBinding.getTransport());
		this.style = (null == this.soapBinding)
				? null
				: this.soapBinding.getStyle();
	}
	
	@Override
	public SoapPortContext getPortContext()
	{
		return (SoapPortContext) super.getPortContext();
	}
	
	
	@Override
	public WsSoapBinding getSoapBinding()
	{
		return this.soapBinding;
	}
	
	
	@Override
	public WsSoapStyle getSoapStyle()
	{
		return this.style;
	}

	
	@Override
	public Transport getTransport()
	{
		return this.transport;
	}
	
}
