/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.protocol.impl;


import static com.tibco.be.baas.security.authn.saml.protocol.SAMLObjectCompositeUtils.COMPOSITE_ASSN_CONSUMER_SERVICE_IDX;
import static com.tibco.be.baas.security.authn.saml.protocol.SAMLObjectCompositeUtils.COMPOSITE_HTTP_POST_BINDING;
import static com.tibco.be.baas.security.authn.saml.protocol.SAMLObjectCompositeUtils.COMPOSITE_ISSUE_INSTANT;
import static com.tibco.be.baas.security.authn.saml.protocol.SAMLObjectCompositeUtils.COMPOSITE_SAML_VERSION;

import java.util.GregorianCalendar;
import java.util.Map;
import java.util.UUID;

import javax.xml.datatype.DatatypeFactory;

import com.tibco.be.baas.security.authn.saml.common.ISAMLObject;
import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;
import com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolFactory;
import com.tibco.be.baas.security.authn.saml.protocol.AbstractSAMLObjectComposite;
import com.tibco.be.baas.security.authn.saml.protocol.ISAMLObjectComposite;

/**
 *
 * @author Aditya Athalye
 * Date : 21 Oct, 2011
 */
public class AuthnRequestTypeComposite extends AbstractSAMLObjectComposite<AuthnRequestType> {

    public AuthnRequestTypeComposite() {
        addSAMLObjectComposite(new IssuerURLTypeComposite());
    }
    
    
    @Override
    public AuthnRequestType composeSAMLObject(Map<String, Object> objectAttribues) throws Exception {
        AuthnRequestType authnRequestType = ProtocolFactory.eINSTANCE.createAuthnRequestType();
        authnRequestType.setVersion(COMPOSITE_SAML_VERSION);
        authnRequestType.setForceAuthn(false);
        authnRequestType.setProtocolBinding(COMPOSITE_HTTP_POST_BINDING);
        //Generate an ID for this request
        String uuid = UUID.randomUUID().toString();
        authnRequestType.setID(uuid);
        //Current time is issue instant
        GregorianCalendar issueInstant = (GregorianCalendar)objectAttribues.get(COMPOSITE_ISSUE_INSTANT);
        authnRequestType.setIssueInstant(DatatypeFactory.newInstance().newXMLGregorianCalendar(issueInstant));
        
        //Get consumer service index
        int assertionConsumerServiceIndex = (Integer)objectAttribues.get(COMPOSITE_ASSN_CONSUMER_SERVICE_IDX);
        authnRequestType.setAssertionConsumerServiceIndex(assertionConsumerServiceIndex);
        
        //Build children
        for (ISAMLObjectComposite childComposite : childComposites) {
            ISAMLObject sAMLObject = childComposite.composeSAMLObject(objectAttribues);
            if (sAMLObject instanceof NameIDType) {
                authnRequestType.setIssuer((NameIDType)sAMLObject);
            }
        }
        return authnRequestType;
    }
}
