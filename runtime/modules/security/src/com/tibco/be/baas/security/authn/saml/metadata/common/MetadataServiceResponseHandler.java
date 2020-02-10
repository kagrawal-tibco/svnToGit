/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Aditya Athalye
 * Date : 2 Oct, 2011
 */
public class MetadataServiceResponseHandler<S extends IMetadataServiceResponse, M extends Enum<M> & IMetadataServiceType> implements ResponseHandler<S> {
    
    private M metadataServiceType;
    
    /**
     * 
     * @param metadataServiceType 
     */
    public MetadataServiceResponseHandler(M metadataServiceType) {
        this.metadataServiceType = metadataServiceType;
    }
    
    
    @Override
    @SuppressWarnings("unchecked")
    public S handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
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
        MetadataServiceResponseAbstractFactory metadataServiceResponseAbstractFactory = MetadataServiceResponseAbstractFactory.INSTANCE;
        try {
            IMetadataServiceResponseFactory metadataServiceResponseFactory = metadataServiceResponseAbstractFactory.getResponseProcessorFactory(metadataServiceType);
            S metadataServiceResponse = 
                    (S)metadataServiceResponseFactory.getServiceResponseProcessor(metadataServiceType).processResponse(responseByteContent, headersMap);
            return metadataServiceResponse; 
        } catch (Exception ex) {
            throw new ClientProtocolException(ex);
        }
    }
}
