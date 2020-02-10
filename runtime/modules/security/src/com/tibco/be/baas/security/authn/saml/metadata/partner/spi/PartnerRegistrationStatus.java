package com.tibco.be.baas.security.authn.saml.metadata.partner.spi;

/**
 * Created by IntelliJ IDEA.
 * User: aditya
 * Date: 22/9/11
 * Time: 10:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class PartnerRegistrationStatus {

    private int statusCode;

    public PartnerRegistrationStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}


