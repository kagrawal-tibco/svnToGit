package com.tibco.be.baas.security.authn.saml.metadata.partner.spi;

import com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 22/9/11
 * Time: 10:22 AM
 * Used by the SP and IdP partner implementations to
 * verify which is the actual SP/IdP sending the SAML
 * request/response.
 * <p>
 * An SP/IdP can use this information to verify the source of
 * the SAML message.
 * </p>
 * @see com.tibco.be.baas.security.authn.saml.providers.SAMLSPPartner
 * @see com.tibco.be.baas.security.authn.saml.providers.SAMLIdpPartner
 */
public interface SAMLPartnerIdentificationService {

    /**
     * Use this API from the IdP to identify the Service Provider
     * SAML partner registered with the BAAS metadata service.
     * <p>
     * Return null if not registered.
     * </p>
     * @param entityIdURI
     * @return
     */
    public SPSSODescriptorType identifySPPartner(String entityIdURI);


    /**
     * Use this API from the SP to identify the Identity Provider
     * SAML partner registered with the BAAS metadata service.
     * <p>
     * Return null if not registered.
     * </p>
     * @param entityIdURI
     * @return
     */
    public IDPSSODescriptorType identifyIdPPartner(String entityIdURI);
}
