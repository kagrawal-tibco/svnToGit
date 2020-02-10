/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.policy.response.impl;

import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.POLICY_TEMPLATES_ROOT_EXNAME;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.xml.sax.InputSource;

import com.tibco.be.baas.security.authn.saml.metadata.policy.response.IPolicyServiceResponseProcessor;
import com.tibco.be.baas.security.authn.saml.model.policy.DocumentRoot;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType;
import com.tibco.be.baas.security.authn.saml.utils.SAMLModelSerializationUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.data.primitive.XmlNodeTest;
import com.tibco.xml.data.primitive.XmlTreeNode;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;

/**
 *
 * @author Aditya Athalye
 * Date : 28 Sep, 2011
 */
public class PolicyTemplatesResponseProcessor implements IPolicyServiceResponseProcessor<PolicyTemplatesResponse> {

    @Override
    public PolicyTemplatesResponse processResponse(byte[] xmlStringBytes, Map<String, String> reponseHeaders) throws Exception {
        InputSource inputSource = new InputSource(new ByteArrayInputStream(xmlStringBytes));
        //Parse it
        XiNode rootNode = XiParserFactory.newInstance().parse(inputSource);
        XiNode rootChild = rootNode.getFirstChild();
        ExpandedName name = rootChild.getName();
        
        Set<PolicyTemplateType> policyTemplates = new HashSet<PolicyTemplateType>();
        if (POLICY_TEMPLATES_ROOT_EXNAME.equals(name)) {
            Iterator<XiNode> childNodes = rootChild.getChildren(new XmlNodeTest() {

                @Override
                public boolean matches(XmlTreeNode xtn) {
                    return xtn.getNodeKind().equals(XmlNodeKind.ELEMENT);
                }

                @Override
                public boolean isNoOp() {
                    return true;
                }
            });
            
            while (childNodes.hasNext()) {
                PolicyTemplateType policyTemplateType = deserializePolicyTemplate(childNodes.next());
                policyTemplates.add(policyTemplateType);
            }
        }
        return new PolicyTemplatesResponse(policyTemplates, reponseHeaders);
    }
    
    /**
     * 
     * @param childNode
     * @return
     * @throws Exception 
     */
    private PolicyTemplateType deserializePolicyTemplate(XiNode childNode) throws Exception {
        //It is wrapped as string content
        String policyTemplateContent = childNode.getStringValue();
        //Unmarshall it
        Map<Object, Object> options = new HashMap<Object, Object>();
        options.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
        EObject eObject = SAMLModelSerializationUtils.unmarshallEObject(policyTemplateContent, options);
        if (eObject instanceof DocumentRoot) {
            DocumentRoot documentRoot = (DocumentRoot)eObject;
            return documentRoot.getAuthnPolicyTemplate();
        }
        return null;
    }
}
