package com.tibco.be.baas.security.authn.saml.providers;

/**
 * Created by IntelliJ IDEA.
 * User: aditya
 * Date: 19/9/11
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SAMLMetadataPartner extends SAMLPartner {

    String getEntityID();

    String getOrganizationURL();

    String getOrganizationName();
}
