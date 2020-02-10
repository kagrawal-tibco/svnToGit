package com.tibco.cep.ws.util.wsdlimport.context.impl;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tibco.cep.ws.WsdlNames;
import com.tibco.cep.ws.util.wsdlimport.context.ServiceContext;
import com.tibco.cep.ws.util.wsdlimport.context.SoapPortContext;
import com.tibco.xml.ws.wsdl.WsExtensionElement;
import com.tibco.xml.ws.wsdl.WsPort;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapAddress;


public class SoapPortContextImpl
		extends PortContextImpl
		implements SoapPortContext
{

	private static final Pattern JMS_DESTINATION_NAME_AND_QUERY_PATTERN = Pattern.compile("jms:jndi:([^#\\?]*)(?:\\?(.*))");
	
	private final URI soapAddress;
	private final Map<String,String> soapAddressParams = new HashMap<String, String>();
	private final String soapAddressPath; 

	
	public SoapPortContextImpl(
			ServiceContext serviceContext,
			WsPort port)
			throws URISyntaxException
	{
		super(serviceContext, port);
		
		WsExtensionElement element = port.getExtensionElement(WsdlNames.SOAP11_ADDRESS);
		if (element == null) {
			element = port.getExtensionElement(WsdlNames.SOAP12_ADDRESS);		
		}
		if (null == element) {
			this.soapAddress = null;
			this.soapAddressPath = null;
		} else {
			final String location = ((WsSoapAddress) element).getLocation();
			if (null == location) {
				this.soapAddress = null;
				this.soapAddressPath = null;
			} else {
				this.soapAddress = (null == location) ? null : new URI(location);
				
				String query;
				final Matcher matcher = JMS_DESTINATION_NAME_AND_QUERY_PATTERN.matcher(location);
				if (matcher.matches()) {
					this.soapAddressPath = matcher.group(1);
					query = matcher.group(2); 
				}
				else {
					this.soapAddressPath = this.soapAddress.getPath();
					query = this.soapAddress.getQuery();
				}
				if (null != query) {
					for (String param: query.split("&")) {
						final String[] nvp = param.split("=");
						this.soapAddressParams.put(this.decode(nvp[0]), this.decode(nvp[1]));
					}
				}
			}
		}
	}

	
	private String decode(
			String s)
	{
		if (null == s) {
			return null;
		} else { 
			try {
				return URLDecoder.decode(s.replace("+", "%2B"), "UTF-8");
			} catch (UnsupportedEncodingException shouldNotHappen) {
				throw new RuntimeException(shouldNotHappen);
			}
		}
	}
	
	@Override
	public URI getSoapAddressUri()
	{
		return this.soapAddress;
	}
	
	@Override
	public String getSoapAddressPath()
	{
		return this.soapAddressPath;
	}
	
	@Override
	public Map<String, String> getSoapAddressQueryParameters()
	{
		return this.soapAddressParams;
	}

	
}
