/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.protocol.impl;

import java.util.Map;

import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;
import com.tibco.be.baas.security.authn.saml.protocol.AbstractSAMLObjectComposite;

/**
 *
 * @author Aditya Athalye
 * Date : 19 Oct, 2011
 */
public class NameIdTypeComposite extends AbstractSAMLObjectComposite<NameIDType> {

    @Override
    public NameIDType composeSAMLObject(Map<String, Object> objectAttribues) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
  
}
