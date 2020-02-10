package com.tibco.cep.driver.http.client.impl.httpcomponents;


import org.apache.http.client.methods.HttpUriRequest;

import com.tibco.cep.driver.http.client.HttpSoapClientRequest;

/**
 * 
 * @author majha
 *
 */
public class HttpSoapClientRequestImpl extends HttpComponentsClientRequest implements HttpSoapClientRequest {

	
	private String soapAction;

	public HttpSoapClientRequestImpl(HttpUriRequest clientRequest , String soapAction) {
		super(clientRequest);
		this.soapAction = soapAction;
	}

	@Override
	public String getSoapAction() {
		// TODO Auto-generated method stub
		return soapAction;
	}

}
