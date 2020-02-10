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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import com.tibco.be.baas.security.authn.saml.metadata.common.IMetadataServiceResponse;
import com.tibco.be.baas.security.authn.saml.metadata.common.IMetadataServiceType;
import com.tibco.be.baas.security.authn.saml.metadata.common.MetadataServiceResponseHandler;

/**
 * TODO Use a dedicated Thread pool for this.
 * TODO refactor methods for code reuse.
 * @author aditya
 */
public class SAMLMetadataClient {
    
    /**
     * 
     * @param <M>
     * @param <S>
     * @param baseUri
     * @param headersMap
     * @param metadataServiceType
     * @return
     * @throws Exception 
     */
    public static <M extends Enum<M> & IMetadataServiceType, S extends IMetadataServiceResponse> S sendGetRequest(String baseUri,
                                         Map<String, String> headersMap,
                                         M metadataServiceType) throws Exception {
        if (baseUri == null) {
            throw new IllegalArgumentException("URI cannot be null");
        }
        URI uri = new URI(baseUri);
        HttpGet httpGet = new HttpGet(uri);
        Set<Map.Entry<String, String>> entrySet = headersMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            String headerName = entry.getKey();
            String headerValue = entry.getValue();
            //Create Basic Header
            Header header = new BasicHeader(headerName, headerValue);
            httpGet.addHeader(header);
        }
        HttpClient httpClient = new DefaultHttpClient();
        try {
            return httpClient.execute(httpGet, new MetadataServiceResponseHandler<S, M>(metadataServiceType));
        } catch (Throwable e) {
            throw new Exception(e);
        } 
    }
    
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
    public static <M extends Enum<M> & IMetadataServiceType, H extends HttpEntity, S extends IMetadataServiceResponse> S sendPostRequest(String baseUri,
                                         Map<String, String> headersMap,
                                         H httpEntity,
                                         M metadataServiceType) throws Exception {
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
            return httpClient.execute(httpPost, new MetadataServiceResponseHandler<S, M>(metadataServiceType));
        } catch (Throwable e) {
            throw new Exception(e);
        } 
    }
}
