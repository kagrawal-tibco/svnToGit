/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.services;

import java.util.Map;

/**
 *
 * @author aditya
 * Date : 17 Oct, 2011
 * To be implemented by Service Providers wishing to participate
 * in the SAML SSO federation for implementing HTTP POST binding
 * profile.
 * e.g : Webstudio, Views, MM web clients.
 */
public interface IIdpAccessService {
    
    /**
     * Most of the attribute values as well as issuer and such
     * will be provided by the specific SP impl.
     * <p>
     * Mandatory additions:
     * <li>Assertion Consumer service index.</li>
     * <li>Request Issuer URL.</li>
     * <li>Request Issue Instant.</li>
     * </p>
     * @param <R>
     * @param idpServiceURL
     * @param requestAttributes -> Used to build the aunthn request. See the mandatory conditions above.
     * @return 
     */
    public Object sendAuthnAssertionRequest(String idpServiceURL, Map<String, Object> requestAttributes) throws Exception;
    
    /**
     * 
     * @param idpServiceURL 
     */
    public void sendHTTPPostRequest(String idpServiceURL);
}
