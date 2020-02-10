/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.protocol.impl;

import java.util.GregorianCalendar;
import java.util.Map;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.tibco.be.baas.security.authn.saml.common.ISAMLObject;
import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolFactory;
import com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType;
import com.tibco.be.baas.security.authn.saml.model.protocol.StatusType;
import com.tibco.be.baas.security.authn.saml.protocol.AbstractSAMLObjectComposite;
import com.tibco.be.baas.security.authn.saml.protocol.ISAMLObjectComposite;

/**
 *
 * @author Aditya Athalye
 * Date : 20 Oct, 2011
 */
public class SAMLResponseTypeComposite extends AbstractSAMLObjectComposite<ResponseType> {

    public SAMLResponseTypeComposite() {
        addSAMLObjectComposite(new StatusTypeComposite());
        addSAMLObjectComposite(new AssertionTypeComposite());
    }
    
    
    @Override
    public ResponseType composeSAMLObject(Map<String, Object> objectAttribues) throws Exception {
        ResponseType samlResponseType = ProtocolFactory.eINSTANCE.createResponseType();
        
        GregorianCalendar issueInstant = (GregorianCalendar)objectAttribues.get("ISSUE_INSTANT");
        XMLGregorianCalendar issueInstantCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(issueInstant);
        samlResponseType.setIssueInstant(issueInstantCalendar);
        
        for (ISAMLObjectComposite childComposite : childComposites) {
            ISAMLObject sAMLObject = childComposite.composeSAMLObject(objectAttribues);
            if (sAMLObject instanceof StatusType) {
                samlResponseType.setStatus((StatusType)sAMLObject);
            } else if (sAMLObject instanceof AssertionType) {
                samlResponseType.getAssertion().add((AssertionType)sAMLObject);
            }
        }
        return samlResponseType;
    }
}
