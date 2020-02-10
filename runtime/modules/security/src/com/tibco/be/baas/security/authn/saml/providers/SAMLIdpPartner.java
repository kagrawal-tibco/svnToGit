package com.tibco.be.baas.security.authn.saml.providers;

import java.util.Map;

import com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType;


/**
 * Created by IntelliJ IDEA.
 * User: aditya
 * Date: 19/9/11
 * Time: 10:14 AM
 * To change this template use File | Settings | File Templates.
 */
public interface SAMLIdpPartner extends SAMLPartner {

    /**
     * Process query for appropriate assertion from the {@link SAMLSPPartner}.
     * <p>
     * The implementation will assume that the {@link SAMLSPPartner} initiates the request.
     * </p>
     * @param responseAttributes -> The attributes required to build an assertion response.
     */
    ResponseType processAuthnAssertionQuery(Map<String, Object> responseAttributes) throws Exception;

}
