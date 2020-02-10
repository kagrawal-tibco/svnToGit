package com.tibco.be.baas.security.authn.saml.metadata.policy.spi;

import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.ADD_AUTHN_POLICY_REQUEST_EVENT_NAME;
import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.ADD_AUTHN_POLICY_REQUEST_EVENT_NAMESPACE;
import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.LOAD_TEMPLATE_REQUEST_EVENT_NAME;
import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.LOAD_TEMPLATE_REQUEST_EVENT_NAMESPACE;
import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.MESSAGE_HEADER_NAMESPACE_PROPERTY;
import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.MESSAGE_HEADER_NAME_PROPERTY;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.http.entity.StringEntity;

import com.tibco.be.baas.security.authn.saml.metadata.policy.request.IPolicyServiceRequestBuilder;
import com.tibco.be.baas.security.authn.saml.metadata.policy.request.impl.PolicyServiceRequestBuilderImpl;
import com.tibco.be.baas.security.authn.saml.metadata.policy.response.IPolicyServiceResponse;
import com.tibco.be.baas.security.authn.saml.metadata.policy.response.PolicyServiceOperationType;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType;
import com.tibco.be.baas.security.authn.saml.transport.SAMLMetadataClient;

/**
 * Created by IntelliJ IDEA
 * User: aathalye
 * Date: 22/9/11
 * Time: 2:59 PM
 * A policy store is one which as part of the metadata store
 * maintains policy templates and policies which can be associated
 * with service provider metadata.
 * <p>
 *     This class will act as an interface to be used by callers
 *     for interacting with the BAAS policy repository.
 * </p>
 */
public class PolicyStoreService {

    /**
     * Return all existing templates with this policy store.
     * @return
     */
    @SuppressWarnings("unchecked")
    public Set<PolicyTemplateType> getLoadedTemplates() throws Exception {
        //Build all required stuff here
        //TODO read endpoint address from WSDL
        String baseUrl = "http://localhost:5000/Authentication/MDS/Services/PolicyStore/Channels/BAAS_CH_PolicyStoreChannel/BAAS_DS_TemplatesDestination";
        String eventNamespace = LOAD_TEMPLATE_REQUEST_EVENT_NAMESPACE;
        String eventName = LOAD_TEMPLATE_REQUEST_EVENT_NAME;
        Map<String, String> headersMap = new HashMap<String, String>();
        headersMap.put(MESSAGE_HEADER_NAMESPACE_PROPERTY, eventNamespace);
        headersMap.put(MESSAGE_HEADER_NAME_PROPERTY, eventName);
        
        IPolicyServiceResponse policyServiceResponse = SAMLMetadataClient.sendGetRequest(baseUrl, headersMap, PolicyServiceOperationType.LOAD_TEMPLATES);
        return policyServiceResponse.getResponseObject();
    }

    /**
     * Add a new policy template to this policy store.
     * The policy template
     * @param policyTemplate
     * @return
     */
    public boolean addPolicyTemplate(PolicyTemplateType policyTemplate) {
        return true;
    }

    /**
     * Remove a policy template from this policy store.
     * The policy template
     * @param templateId
     * @return
     */
    public boolean removePolicyTemplate(String templateId) {
        return true;
    }

    /**
     * Get details of a policy template from this policy store.
     * The policy template
     * @param templateId
     * @return
     */
    public PolicyTemplateType getPolicyTemplateDetails(String templateId) {
        return null;
    }
    
    /**
     * Add a new policy instance to this policy store.
     * The policy template which will contain values of 
     * policy config properties as well.
     * @param policyTemplate
     * @return
     */
    public Boolean addPolicyInstance(PolicyTemplateType authnPolicy) throws Exception {
        //Build all required stuff here
        //TODO read endpoint address from WSDL
        String baseUrl = "http://localhost:5000/Authentication/MDS/Services/PolicyStore/Channels/BAAS_CH_PolicyStoreChannel/BAAS_DS_AddNewPolicyDestination";
        String eventNamespace = ADD_AUTHN_POLICY_REQUEST_EVENT_NAMESPACE;
        String eventName = ADD_AUTHN_POLICY_REQUEST_EVENT_NAME;
        Map<String, String> headersMap = new HashMap<String, String>();
        headersMap.put(MESSAGE_HEADER_NAMESPACE_PROPERTY, eventNamespace);
        headersMap.put(MESSAGE_HEADER_NAME_PROPERTY, eventName);
        headersMap.put("policyId", authnPolicy.getID());
        
        //Build request
        IPolicyServiceRequestBuilder policyServiceRequestBuilder = new PolicyServiceRequestBuilderImpl();
        StringEntity stringEntity = policyServiceRequestBuilder.buildRequest(authnPolicy, PolicyServiceOperationType.ADD_NEW_AUTHN_POLICY);
        //Make post
        IPolicyServiceResponse policyServiceResponse = 
                SAMLMetadataClient.sendPostRequest(baseUrl, headersMap, stringEntity, PolicyServiceOperationType.ADD_NEW_AUTHN_POLICY);
        return policyServiceResponse.getResponseObject();
    }
    
    /**
     * Return all existing authn policies with this policy store.
     * @return
     */
    public Set<PolicyTemplateType> getExistingAuthnPolicies() {
        return new HashSet<PolicyTemplateType>();
    }
    
    /**
     * Remove a policy instance from this policy store.
     * 
     * @param templateId
     * @return
     */
    public boolean removePolicyInstance(String policyId) {
        return true;
    }
}
