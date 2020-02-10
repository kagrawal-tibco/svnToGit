package com.tibco.be.baas.security.authn.saml.providers;

/**
 * Created by IntelliJ IDEA.
 * User: aditya
 * Date: 19/9/11
 * Time: 10:13 AM
 * To change this template use File | Settings | File Templates.
 */
public interface SAMLPartner {

    String getPartnerDescription();

    String getName();

    String[] getAudienceURIs();

    /**
     * The URI of this partner requesting assertion or providing one.
     * @return
     */
    String getIssuerURI();
}
