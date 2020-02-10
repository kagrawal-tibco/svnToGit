/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.policy.response.impl;


import java.util.Map;

import com.tibco.be.baas.security.authn.saml.metadata.policy.response.IPolicyServiceResponseProcessor;

/**
 *
 * @author Aditya Athalye
 * Date : 4 Oct, 2011
 */
public class AuthnPoliciesResponseProcessor implements IPolicyServiceResponseProcessor<AuthnPoliciesResponse> {

    @Override
    public AuthnPoliciesResponse processResponse(byte[] xmlStringBytes, Map<String, String> responseHeaders) throws Exception {
//        InputSource inputSource = new InputSource(new ByteArrayInputStream(xmlStringBytes));
//        //Parse it
//        XiNode rootNode = XiParserFactory.newInstance().parse(inputSource);
//        XiNode rootChild = rootNode.getFirstChild();
//        ExpandedName name = rootChild.getName();
//        
//        Set<AuthnPolicy> policyTemplates = new HashSet<AuthnPolicy>();
//        if (POLICY_TEMPLATES_ROOT_EXNAME.equals(name)) {
//            Iterator<XiNode> childNodes = rootChild.getChildren(new XmlNodeTest() {
//
//                @Override
//                public boolean matches(XmlTreeNode xtn) {
//                    return xtn.getNodeKind().equals(XmlNodeKind.ELEMENT);
//                }
//
//                @Override
//                public boolean isNoOp() {
//                    return true;
//                }
//            });
//            
//            while (childNodes.hasNext()) {
//                AuthnPolicyTemplate policyTemplateType = deserializePolicyTemplate(childNodes.next());
//                policyTemplates.add(policyTemplateType);
//            }
//        }
        return null;
    }
}
