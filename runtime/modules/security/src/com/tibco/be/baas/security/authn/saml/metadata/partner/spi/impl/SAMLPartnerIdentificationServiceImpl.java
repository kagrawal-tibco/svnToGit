package com.tibco.be.baas.security.authn.saml.metadata.partner.spi.impl;

import com.tibco.be.baas.security.authn.saml.metadata.partner.spi.SAMLPartnerIdentificationService;
import com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 22/9/11
 * Time: 10:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class SAMLPartnerIdentificationServiceImpl implements SAMLPartnerIdentificationService {
    /**
     * Use this API from the IdP to identify the Service Provider
     * SAML partner registered with the BAAS metadata service.
     * <p>
     * Return null if not registered.
     * </p>
     *
     * @param entityIdURI
     * @return
     */
    @Override
    public SPSSODescriptorType identifySPPartner(String entityIdURI) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Use this API from the SP to identify the Identity Provider
     * SAML partner registered with the BAAS metadata service.
     * <p>
     * Return null if not registered.
     * </p>
     *
     * @param entityIdURI
     * @return
     */
    @Override
    public IDPSSODescriptorType identifyIdPPartner(String entityIdURI) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
