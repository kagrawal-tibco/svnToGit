/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.protocol.impl;

import java.util.Map;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionFactory;
import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;

/**
 *
 * @author Aditya Athalye
 * Date : 19 Oct, 2011
 */
public class SubjectNameComposite extends NameIdTypeComposite {

    @Override
    public NameIDType composeSAMLObject(Map<String, Object> objectAttribues) {
        NameIDType subjectNameType = AssertionFactory.eINSTANCE.createNameIDType();
        String subjectName = (String)objectAttribues.get("SUBJECT_NAME");
        subjectNameType.setValue(subjectName);
        return subjectNameType;
    }
}
