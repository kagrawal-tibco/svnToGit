package com.tibco.be.baas.security.authn.saml.protocol.impl;

import java.util.GregorianCalendar;
import java.util.Map;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.tibco.be.baas.security.authn.saml.common.ISAMLObject;
import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionFactory;
import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType;
import com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType;
import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType;
import com.tibco.be.baas.security.authn.saml.protocol.AbstractSAMLObjectComposite;
import com.tibco.be.baas.security.authn.saml.protocol.ISAMLObjectComposite;

/**
 * Created by IntelliJ IDEA.
 * User: aditya
 * Date: 19/9/11
 * Time: 12:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class AssertionTypeComposite extends AbstractSAMLObjectComposite<AssertionType> {

    public AssertionTypeComposite() {
        addSAMLObjectComposite(new IssuerURLTypeComposite());
        addSAMLObjectComposite(new SubjectTypeComposite());
        addSAMLObjectComposite(new ConditionsTypeComposite());
        addSAMLObjectComposite(new AuthnStatementTypeComposite());
    }
    
    
    @Override
    public AssertionType composeSAMLObject(Map<String, Object> objectAttribues) throws Exception {
        //Look for Issuer
        AssertionType assertionType = AssertionFactory.eINSTANCE.createAssertionType();
        String assertionIssuerId = (String)objectAttribues.get("ID");
        assertionType.setID(assertionIssuerId);
        GregorianCalendar issueInstant = (GregorianCalendar)objectAttribues.get("ISSUE_INSTANT");
        XMLGregorianCalendar issueInstantCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(issueInstant);
        assertionType.setIssueInstant(issueInstantCalendar);
        //Construct with child composites
        for (ISAMLObjectComposite childComposite : childComposites) {
            ISAMLObject sAMLObject = childComposite.composeSAMLObject(objectAttribues);
            if (sAMLObject instanceof SubjectType) {
                assertionType.setSubject((SubjectType)sAMLObject);
            } else if (sAMLObject instanceof NameIDType) {
                assertionType.setIssuer((NameIDType)sAMLObject);
            } else if (sAMLObject instanceof AuthnStatementType) {
                assertionType.getAuthnStatement().add((AuthnStatementType)sAMLObject);
            } else if (sAMLObject instanceof ConditionsType) {
                assertionType.setConditions((ConditionsType)sAMLObject);
            }
        }
        return assertionType;
    }
}
