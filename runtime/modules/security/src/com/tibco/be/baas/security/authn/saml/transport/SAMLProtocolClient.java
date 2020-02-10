/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.transport;

import java.net.URI;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import com.tibco.be.baas.security.authn.saml.services.SAMLProtocolType;
import com.tibco.be.baas.security.authn.saml.services.response.ISAMLProtocolResponse;
import com.tibco.be.baas.security.authn.saml.services.response.ISAMLProtocolResponseProcessor;
import com.tibco.be.baas.security.authn.saml.services.response.SAMLProtocolResponseHandler;

/**
 *
 * @author Aditya Athalye
 * Date : 17 Oct, 2011
 */
public class SAMLProtocolClient {
    
    /**
     * 
     * @param <M>
     * @param <H>
     * @param <S>
     * @param baseUri
     * @param headersMap
     * @param httpEntity
     * @param metadataServiceType
     * @return
     * @throws Exception 
     */
    public static <H extends HttpEntity, R extends ISAMLProtocolResponse, S extends ISAMLProtocolResponseProcessor<R>> R sendPostRequest(String baseUri,
                                         Map<String, String> headersMap,
                                         H httpEntity,
                                         SAMLProtocolType samlProtolType) throws Exception {
        if (baseUri == null) {
            throw new IllegalArgumentException("URI cannot be null");
        }
        URI uri = new URI(baseUri);
        HttpPost httpPost = new HttpPost(uri);
        Set<Map.Entry<String, String>> entrySet = headersMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            String headerName = entry.getKey();
            String headerValue = entry.getValue();
            //Create Basic Header
            Header header = new BasicHeader(headerName, headerValue);
            httpPost.addHeader(header);
        }
        httpPost.setEntity(httpEntity);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            return httpClient.execute(httpPost, new SAMLProtocolResponseHandler<R, S>(samlProtolType));
        } catch (Throwable e) {
            throw new Exception(e);
        } 
    }
}
