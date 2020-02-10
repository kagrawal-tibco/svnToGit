/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.common;

import java.lang.reflect.Constructor;

import com.tibco.be.baas.security.authn.saml.metadata.partner.response.SAMLPartnerRegistrationServiceResponseFactory;
import com.tibco.be.baas.security.authn.saml.metadata.partner.spi.PartnerRegistrationOperationType;
import com.tibco.be.baas.security.authn.saml.metadata.policy.response.PolicyServiceOperationType;
import com.tibco.be.baas.security.authn.saml.metadata.policy.response.PolicyServiceResponseFactory;

/**
 *
 * @author Aditya Athalye
 * Date : 1 Oct, 2011
 */
public class MetadataServiceResponseAbstractFactory {
    
    public static MetadataServiceResponseAbstractFactory INSTANCE = new MetadataServiceResponseAbstractFactory();

    private MetadataServiceResponseAbstractFactory() {
    }
    
    /**
     * 
     * @param <M>
     * @param <F>
     * @param metadataServiceType
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    public <M extends Enum<M> & IMetadataServiceType, F extends IMetadataServiceResponseFactory> F getResponseProcessorFactory(M metadataServiceType) throws Exception {
        Class<?> metadataServiceTypeClass = metadataServiceType.getClass();
        if (metadataServiceTypeClass.isAssignableFrom(PolicyServiceOperationType.class)) {
            Constructor<PolicyServiceResponseFactory> constructor = PolicyServiceResponseFactory.class.getConstructor();
            return (F)constructor.newInstance();
        } else if (metadataServiceTypeClass.isAssignableFrom(PartnerRegistrationOperationType.class)) {
            Constructor<SAMLPartnerRegistrationServiceResponseFactory> constructor = SAMLPartnerRegistrationServiceResponseFactory.class.getConstructor();
            return (F)constructor.newInstance();
        }
        return null;
    }
}
