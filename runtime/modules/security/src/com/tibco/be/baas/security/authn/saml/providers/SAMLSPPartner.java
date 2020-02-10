package com.tibco.be.baas.security.authn.saml.providers;

import com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType;



/**
 * Created by IntelliJ IDEA.
 * User: aditya
 * Date: 19/9/11
 * Time: 10:17 AM
 * To change this template use File | Settings | File Templates.
 */
public interface SAMLSPPartner extends SAMLPartner {

    /**
     * Make query for appropriate assertion to the {@link SAMLIdpPartner}.
     * <p>
     * The implementation will assume that the {@link SAMLSPPartner} initiates the request.
     * </p>
     * @param idpPartnerURI
     * @param queryType
     * @param <S>
     */
    <S extends RequestAbstractType> void makeAssertionQuery(String idpPartnerURI, S queryType);

    /**
     * Process the assertion response sent by {@link SAMLIdpPartner}
     * @param responseType
     */
    void processAssertionResponse(ResponseType responseType);
}
