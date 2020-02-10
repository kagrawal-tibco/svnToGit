/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.protocol.impl;

import static com.tibco.be.baas.security.authn.saml.protocol.SAMLObjectCompositeUtils.SUCCESS_RESPONSE_STATUS_CODE;

import java.util.Map;

import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolFactory;
import com.tibco.be.baas.security.authn.saml.model.protocol.StatusCodeType;
import com.tibco.be.baas.security.authn.saml.model.protocol.StatusType;
import com.tibco.be.baas.security.authn.saml.protocol.AbstractSAMLObjectComposite;

/**
 *
 * @author Aditya Athalye
 * Date : 20 Oct, 2011
 */
public class StatusTypeComposite extends AbstractSAMLObjectComposite<StatusType> {

    @Override
    public StatusType composeSAMLObject(Map<String, Object> objectAttribues) throws Exception {
        StatusType statusType = ProtocolFactory.eINSTANCE.createStatusType();
        //Add status code
        StatusCodeType statusCodeType = ProtocolFactory.eINSTANCE.createStatusCodeType();
        statusCodeType.setValue(SUCCESS_RESPONSE_STATUS_CODE);
        statusType.setStatusCode(statusCodeType);
        
        return statusType;
    }
}
