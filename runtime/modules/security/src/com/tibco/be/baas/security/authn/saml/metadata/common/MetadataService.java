package com.tibco.be.baas.security.authn.saml.metadata.common;

import com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType;



/**
 * Created by IntelliJ IDEA.
 * User: aditya
 *
 * Date: 20/9/11
 * Time: 12:13 PM
 * <p>
 * This class will be used by BAAS UI metadata configuration component to
 * load/create/edit SP/IdP metadata.
 * </p>
 * <p>
 * All SPs and the BAAS IdP will use the metadata to look up each other
 * to verify source target of SAML requests/responses.
 * </p>
 */
public class MetadataService {

    /**
     * Save Service provider metadata configuration to BAAS.
     * @param spsoDescriptorType
     */
    @MetadataComponent
    public static void saveSPPartnerMetadata(SPSSODescriptorType spsoDescriptorType) {
        //TODO save SP metadata configuration to BAAS Metadata component.
    }

    /**
     * Save Identity provider metadata configuration to BAAS.
     * @param idpssoDescriptorType
     */
    @MetadataComponent
    public static void saveIdPPartnerMetadata(IDPSSODescriptorType idpssoDescriptorType) {
        //TODO save IdP metadata configuration to BAAS Metadata component.
    }

    /**
     *
     * @param entityIDURI
     * @return
     */
    @MetadataComponent
    public static SPSSODescriptorType loadSPPartnerMetadata(String entityIDURI) {
        //TODO load SP metadata configuration from BAAS Metadata component.
        return null;
    }

    /**
     *
     * @param entityIDURI
     * @return
     */
    @MetadataComponent
    public static IDPSSODescriptorType loadIdpPartnerMetadata(String entityIDURI) {
        //TODO load IdP metadata configuration from BAAS Metadata component.
        return null;
    }

}
