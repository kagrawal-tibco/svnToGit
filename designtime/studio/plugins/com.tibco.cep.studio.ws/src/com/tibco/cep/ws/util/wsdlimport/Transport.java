package com.tibco.cep.ws.util.wsdlimport;

import java.net.URI;
import java.net.URISyntaxException;


public enum Transport
{

	
	HTTP("http://schemas.xmlsoap.org/soap/http"),
	
	JMS("http://www.w3.org/2010/soapjms/"),
	
	JMS_TIBCO("http://www.tibco.com/namespaces/ws/2004/soap/binding/JMS");
	
	
	private String uriString;
	private URI uri;

	
	Transport(
			String uriString)
	{
		this.uriString = uriString;
		try {
			this.uri = new URI(uriString);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	
	public URI getUri() {
		return this.uri;
	}

	
	public static Transport fromString(
			String uriString)
	{
		if (uriString != null) {
			for (Transport t : Transport.values()) {
				if (t.uriString.equalsIgnoreCase(uriString)) {
					return t;
				}
			}
		}
		return null;
	}
	
	public static Transport fromUri(
			URI uri)
	{
		if (uri != null) {
			for (Transport t : Transport.values()) {
				if (t.uri.equals(uri)) {
					return t;
				}
			}
		}
		return null;
	}
	
}
