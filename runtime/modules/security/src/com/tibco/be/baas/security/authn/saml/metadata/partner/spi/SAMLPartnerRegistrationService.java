package com.tibco.be.baas.security.authn.saml.metadata.partner.spi;

import com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 22/9/11
 * Time: 10:11 AM
 * This service will be used by the BAAS metadata UI component
 * to register/de-register SAML partners.
 * @see com.tibco.be.baas.security.authn.saml.providers.SAMLPartner
 */
public interface SAMLPartnerRegistrationService {

    /**
     * Register as SAML SP Patner with the metadata service.
     * <p>
     * BAAS metadata configuration UI should call this API.
     * </p>
     * @param spDescriptor
     * @return
     */
    public PartnerRegistrationStatus registerSPPartner(EntityDescriptorType spDescriptor) throws Exception;

    /**
     * Register as SAML IdP Patner with the metadata service.
     * <p>
     * BAAS metadata configuration UI should call this API.
     * </p>
     * @param idpDescriptorType
     * @return
     */
    public PartnerRegistrationStatus registerIdPPartner(EntityDescriptorType idpDescriptor) throws Exception;

    /**
     * De-Register as SAML SP Patner from the metadata service.
     * Use the entity ID to identify the partner to be deregistered.
     * <p>
     * BAAS metadata configuration UI should call this API.
     * </p>
     * @param entityID
     * @return
     */
    public PartnerRegistrationStatus deRegisterSPPartner(String entityID) throws Exception;

    /**
     * De-Register as SAML IdP Patner from the metadata service.
     * Use the entity ID to identify the partner to be deregistered.
     * <p>
     * BAAS metadata configuration UI should call this API.
     * </p>
     * @param entityID
     * @return
     */
    public PartnerRegistrationStatus deRegisterIdPPPartner(String entityID) throws Exception;

}
