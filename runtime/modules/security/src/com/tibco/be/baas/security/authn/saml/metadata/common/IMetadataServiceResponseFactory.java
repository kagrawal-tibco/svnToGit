/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.common;

/**
 *
 * @author aditya
 * Date : 1 Oct, 2011
 */
public interface IMetadataServiceResponseFactory<T extends IMetadataServiceResponseProcessor, M extends Enum<M> & IMetadataServiceType> {
    
    /**
     * 
     * @param metadataServiceType
     * @return 
     */
    public T getServiceResponseProcessor(M metadataServiceType) throws Exception;
}
