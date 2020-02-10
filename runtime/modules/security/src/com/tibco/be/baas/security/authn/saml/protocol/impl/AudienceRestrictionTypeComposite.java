/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.protocol.impl;

import java.util.List;
import java.util.Map;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionFactory;
import com.tibco.be.baas.security.authn.saml.model.assertion.AudienceRestrictionType;
import com.tibco.be.baas.security.authn.saml.protocol.AbstractSAMLObjectComposite;

/**
 *
 * @author Aditya Athalye
 * Date : 19 Oct, 2011
 */
public class AudienceRestrictionTypeComposite extends AbstractSAMLObjectComposite<AudienceRestrictionType> {

    @Override
    public AudienceRestrictionType composeSAMLObject(Map<String, Object> objectAttribues) throws Exception {
        AudienceRestrictionType audienceRestrictionType = AssertionFactory.eINSTANCE.createAudienceRestrictionType();
        List<String> audiences = (List)objectAttribues.get("AUDIENCE");
        audienceRestrictionType.getAudience().addAll(audiences);
        return audienceRestrictionType;
    }
 
}
