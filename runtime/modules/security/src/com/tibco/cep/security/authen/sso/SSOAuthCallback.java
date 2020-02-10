/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.cep.security.authen.sso;

import java.net.URL;

import javax.security.auth.callback.Callback;

/**
 *
 * @author Aditya Athalye
 * Date : 17 Oct, 2011
 */
public class SSOAuthCallback implements Callback {
    
    private String username;
    
    private String password;
    
    private URL issuerUrl;
    
    
    public SSOAuthCallback() {
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public URL getIssuerUrl() {
        return issuerUrl;
    }

    public void setIssuerUrl(URL issuerUrl) {
        this.issuerUrl = issuerUrl;
    }
}
