/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.protocol.impl;

import java.util.Map;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionFactory;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType;
import com.tibco.be.baas.security.authn.saml.protocol.AbstractSAMLObjectComposite;
import com.tibco.be.baas.security.authn.saml.protocol.SAMLObjectCompositeUtils;

/**
 *
 * @author Aditya Athalye
 * Date : 28 Oct, 2011
 */
public class SubjectConfirmationTypeComposite extends AbstractSAMLObjectComposite<SubjectConfirmationType> {

    @Override
    public SubjectConfirmationType composeSAMLObject(Map<String, Object> objectAttribues) throws Exception {
        SubjectConfirmationType subjectConfirmationType = AssertionFactory.eINSTANCE.createSubjectConfirmationType();
        subjectConfirmationType.setMethod(SAMLObjectCompositeUtils.COMPOSITE_SAML_BEARER_METHOD);
        
        //Get response to
        String responseTo = (String)objectAttribues.get("RESPONSE_TO");
        SubjectConfirmationDataType subjectConfirmationDataType = AssertionFactory.eINSTANCE.createSubjectConfirmationDataType();
        subjectConfirmationDataType.setInResponseTo(responseTo);
        
        subjectConfirmationType.setSubjectConfirmationData(subjectConfirmationDataType);
        return subjectConfirmationType;
    }
}
