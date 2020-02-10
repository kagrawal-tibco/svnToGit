/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.services.response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import com.tibco.be.baas.security.authn.saml.services.SAMLProtocolType;

/**
 * Handles all SAML protocol message exchanges.
 * @author Aditya Athalye
 * Date : 21 Oct, 2011
 */
public class SAMLProtocolResponseHandler<R extends ISAMLProtocolResponse, S extends ISAMLProtocolResponseProcessor<R>> implements ResponseHandler<R> {
    
    private SAMLProtocolType samlProtocolType;

    public SAMLProtocolResponseHandler(SAMLProtocolType samlProtocolType) {
        this.samlProtocolType = samlProtocolType;
    }
    
        
    @Override
    public R handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
        //Take headers and response content as well.
        HttpEntity entity = httpResponse.getEntity();
        byte[] responseByteContent = null;
        if (entity != null) {
            responseByteContent = EntityUtils.toByteArray(entity);
        }
        Header[] allHeaders = httpResponse.getAllHeaders();
        Map<String, String> headersMap = new HashMap<String, String>(allHeaders.length);
        for (Header header : allHeaders) {
            headersMap.put(header.getName(), header.getValue());
        }
        SAMLProtocolResponseFactory samlProtocolResponseFactory = SAMLProtocolResponseFactory.INSTANCE;
        try {
            S responseProcessor = samlProtocolResponseFactory.<R, S>getProtocolResponseProcessor(samlProtocolType);
            R samlProtocolResponse = responseProcessor.processResponse(responseByteContent, headersMap);
            return samlProtocolResponse; 
        } catch (Exception ex) {
            throw new ClientProtocolException(ex);
        }
    }
}
