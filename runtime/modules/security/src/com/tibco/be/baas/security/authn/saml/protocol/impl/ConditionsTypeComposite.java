/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.protocol.impl;

import java.util.GregorianCalendar;
import java.util.Map;

import javax.xml.datatype.DatatypeFactory;

import com.tibco.be.baas.security.authn.saml.common.ISAMLObject;
import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionFactory;
import com.tibco.be.baas.security.authn.saml.model.assertion.AudienceRestrictionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType;
import com.tibco.be.baas.security.authn.saml.protocol.AbstractSAMLObjectComposite;
import com.tibco.be.baas.security.authn.saml.protocol.ISAMLObjectComposite;

/**
 *
 * @author Aditya Athalye
 * Date : 19 Oct, 2011
 */
public class ConditionsTypeComposite extends AbstractSAMLObjectComposite<ConditionsType> {

    public ConditionsTypeComposite() {
        addSAMLObjectComposite(new AudienceRestrictionTypeComposite());
    }
    
    
    @Override
    public ConditionsType composeSAMLObject(Map<String, Object> objectAttribues) throws Exception {
        ConditionsType conditionsType = AssertionFactory.eINSTANCE.createConditionsType();
        
        GregorianCalendar notBefore = (GregorianCalendar)objectAttribues.get("NOT_BEFORE");
        conditionsType.setNotBefore(DatatypeFactory.newInstance().newXMLGregorianCalendar(notBefore));
        
        GregorianCalendar notAfter = (GregorianCalendar)objectAttribues.get("NOT_ON_AFTER");
        conditionsType.setNotOnOrAfter(DatatypeFactory.newInstance().newXMLGregorianCalendar(notAfter));
        
        for (ISAMLObjectComposite childComposite : childComposites) {
            ISAMLObject sAMLObject = childComposite.composeSAMLObject(objectAttribues);
            if (sAMLObject instanceof AudienceRestrictionType) {
                conditionsType.getAudienceRestriction().add((AudienceRestrictionType)sAMLObject);
            }
        }
        return conditionsType;
    }
}
