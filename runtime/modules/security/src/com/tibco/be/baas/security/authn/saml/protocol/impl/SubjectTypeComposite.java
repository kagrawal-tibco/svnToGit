package com.tibco.be.baas.security.authn.saml.protocol.impl;

import java.util.Map;

import com.tibco.be.baas.security.authn.saml.common.ISAMLObject;
import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionFactory;
import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType;
import com.tibco.be.baas.security.authn.saml.protocol.AbstractSAMLObjectComposite;
import com.tibco.be.baas.security.authn.saml.protocol.ISAMLObjectComposite;

/**
 * Created by IntelliJ IDEA.
 * User: aditya
 * Date: 19/9/11
 * Time: 12:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubjectTypeComposite extends AbstractSAMLObjectComposite<SubjectType> {

    public SubjectTypeComposite() {
        addSAMLObjectComposite(new SubjectNameComposite());
        addSAMLObjectComposite(new SubjectConfirmationTypeComposite());
    }
    
    
    @Override
    public SubjectType composeSAMLObject(Map<String, Object> objectAttribues) throws Exception {
        SubjectType subjectType = AssertionFactory.eINSTANCE.createSubjectType();
        for (ISAMLObjectComposite childComposite : childComposites) {
            ISAMLObject sAMLObject = childComposite.composeSAMLObject(objectAttribues);
            if (sAMLObject instanceof NameIDType) {
                subjectType.setNameID((NameIDType)sAMLObject);
            } if (sAMLObject instanceof SubjectConfirmationType) {
                subjectType.getSubjectConfirmation().add((SubjectConfirmationType)sAMLObject);
            }
        }
        return subjectType;
    }
}
