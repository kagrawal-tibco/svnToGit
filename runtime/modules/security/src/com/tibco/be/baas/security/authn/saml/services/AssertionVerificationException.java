/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.services;

/**
 * Thrown by Assertion consumer service on SP side when any
 * of the mandatory checks on assertion fails.
 * @author Aditya Athalye
 * Date : 25 Oct, 2011
 */
public class AssertionVerificationException extends Exception {

    public AssertionVerificationException(Throwable cause) {
        super(cause);
    }

    public AssertionVerificationException(String message) {
        super(message);
    }
}
