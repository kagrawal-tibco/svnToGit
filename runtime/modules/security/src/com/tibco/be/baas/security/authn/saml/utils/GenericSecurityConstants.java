/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.utils;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 *
 * @author aditya
 */
public class GenericSecurityConstants {
    
    public final static String MESSAGE_HEADER_NAMESPACE_PROPERTY ="_ns_";
    public final static String MESSAGE_HEADER_NAME_PROPERTY ="_nm_";
    public final static String UTF8_ENCODING = "UTF-8";
    public final static String POLICY_PACKAGE = "com.tibco.be.baas.security.authn.saml.metadata.policy.model";
    public final static String SAML_MD_PACKAGE = "com.tibco.be.baas.security.authn.saml.metadata.model";
    
    public static final String LOAD_TEMPLATE_REQUEST_EVENT_NAME = "BAAS_E_LoadTemplatesRequestEvent";
    public static final String LOAD_TEMPLATE_REQUEST_EVENT_NAMESPACE = "www.tibco.com/be/ontology/Authentication/MDS/Services/PolicyStore/Events/BAAS_E_LoadTemplatesRequestEvent";
    
    public static final String ADD_AUTHN_POLICY_REQUEST_EVENT_NAME = "BAAS_E_AddNewPolicyRequestEvent";
    public static final String ADD_AUTHN_POLICY_REQUEST_EVENT_NAMESPACE = "www.tibco.com/be/ontology/Authentication/MDS/Services/PolicyStore/Events/BAAS_E_AddNewPolicyRequestEvent";
    
    public static final String REGISTER_SPPARTNER_REQUEST_EVENT_NAME = "BAAS_E_RegisterSAMLSPPartnerRequestEvent";
    public static final String REGISTER_SPPARTNER_REQUEST_EVENT_NAMESPACE = "www.tibco.com/be/ontology/Authentication/MDS/Services/PartnerRegistration/Events/BAAS_E_RegisterSAMLSPPartnerRequestEvent";
    
    public static final ExpandedName POLICY_TEMPLATES_ROOT_EXNAME = ExpandedName.makeName("www.tibco.com/be/ontology/Authentication/MDS/Services/PolicyStore/Events/BAAS_E_LoadExistingTemplatesResponseEvent", "PolicyTemplates");
    
    public static class SSOConstants {
        
        public static final ExpandedName SSO_TOKEN_EX_NAME = ExpandedName.makeName("SSOToken");
        public static final ExpandedName SSO_SUBJECT_EX_NAME = ExpandedName.makeName("Subject");
        public static final ExpandedName SSO_TOKEN_ISSUER_EX_NAME = ExpandedName.makeName("Issuer");
        public static final ExpandedName SSO_TOKEN_ISSUEINSTANT_EX_NAME = ExpandedName.makeName("IssueInstant");
        public static final ExpandedName ATTR_SSOTOKEN_ID = ExpandedName.makeName("ID");
    }
}
