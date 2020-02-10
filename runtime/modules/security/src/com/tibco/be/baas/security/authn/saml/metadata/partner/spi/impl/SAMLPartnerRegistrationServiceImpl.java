package com.tibco.be.baas.security.authn.saml.metadata.partner.spi.impl;

import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.MESSAGE_HEADER_NAMESPACE_PROPERTY;
import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.MESSAGE_HEADER_NAME_PROPERTY;
import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.REGISTER_SPPARTNER_REQUEST_EVENT_NAME;
import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.REGISTER_SPPARTNER_REQUEST_EVENT_NAMESPACE;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.StringEntity;

import com.tibco.be.baas.security.authn.saml.metadata.common.MetadataComponent;
import com.tibco.be.baas.security.authn.saml.metadata.partner.request.ISAMLPartnerRegistrationRequestBuilder;
import com.tibco.be.baas.security.authn.saml.metadata.partner.request.impl.SAMLPartnerRegistrationRequestBuilderImpl;
import com.tibco.be.baas.security.authn.saml.metadata.partner.response.ISAMLPartnerRegistrationServiceResponse;
import com.tibco.be.baas.security.authn.saml.metadata.partner.spi.PartnerRegistrationOperationType;
import com.tibco.be.baas.security.authn.saml.metadata.partner.spi.PartnerRegistrationStatus;
import com.tibco.be.baas.security.authn.saml.metadata.partner.spi.SAMLPartnerRegistrationService;
import com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType;
import com.tibco.be.baas.security.authn.saml.transport.SAMLMetadataClient;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 22/9/11
 * Time: 10:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class SAMLPartnerRegistrationServiceImpl implements SAMLPartnerRegistrationService {

    /**
     * Register as SAML SP Patner with the metadata service.
     * <p>
     * BAAS metadata configuration UI should call this API.
     * </p>
     *
     * @param spDescriptorType
     * @return
     */
    @Override
    @MetadataComponent
    public PartnerRegistrationStatus registerSPPartner(EntityDescriptorType spDescriptorType) throws Exception {
        String baseUrl = "http://localhost:6000/Authentication/MDS/Services/PartnerRegistration/Channels/BAAS_CH_SAMLPartnerRegistrationChannel/BAAS_DS_SAMLSPPartnerRegistrationDestination";
        String eventNamespace = REGISTER_SPPARTNER_REQUEST_EVENT_NAMESPACE;
        String eventName = REGISTER_SPPARTNER_REQUEST_EVENT_NAME;
        Map<String, String> headersMap = new HashMap<String, String>();
        headersMap.put(MESSAGE_HEADER_NAMESPACE_PROPERTY, eventNamespace);
        headersMap.put(MESSAGE_HEADER_NAME_PROPERTY, eventName);
        headersMap.put("partnerIDURI", spDescriptorType.getEntityID());
        
        //Build request
        ISAMLPartnerRegistrationRequestBuilder partnerRegistrationRequestBuilder = new SAMLPartnerRegistrationRequestBuilderImpl();
        StringEntity stringEntity = partnerRegistrationRequestBuilder.buildRequest(spDescriptorType, PartnerRegistrationOperationType.REGSITER_SP_PARTNER);
        //Make post
        ISAMLPartnerRegistrationServiceResponse spPartnerRegistrationServiceResponse = 
                SAMLMetadataClient.sendPostRequest(baseUrl, headersMap, stringEntity, PartnerRegistrationOperationType.REGSITER_SP_PARTNER);
        return spPartnerRegistrationServiceResponse.getResponseObject();
    }

    /**
     * Register as SAML IdP Partner with the metadata service.
     * <p>
     * BAAS metadata configuration UI should call this API.
     * </p>
     *
     * @param idpDescriptorType
     * @return
     */
    @Override
    @MetadataComponent
    public PartnerRegistrationStatus registerIdPPartner(EntityDescriptorType idpDescriptorType) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * De-Register as SAML SP Patner from the metadata service.
     * Use the entity ID to identify the partner to be deregistered.
     * <p>
     * BAAS metadata configuration UI should call this API.
     * </p>
     *
     * @param entityID
     * @return
     */
    @Override
    @MetadataComponent
    public PartnerRegistrationStatus deRegisterSPPartner(String entityID) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * De-Register as SAML IdP Patner from the metadata service.
     * Use the entity ID to identify the partner to be deregistered.
     * <p>
     * BAAS metadata configuration UI should call this API.
     * </p>
     *
     * @param entityID
     * @return
     */
    @Override
    @MetadataComponent
    public PartnerRegistrationStatus deRegisterIdPPPartner(String entityID) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
