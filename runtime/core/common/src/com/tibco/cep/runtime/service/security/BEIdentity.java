package com.tibco.cep.runtime.service.security;

/**
 * @author ishaan
 * @version May 3, 2006, 6:04:27 PM
 */
public class BEIdentity {
    protected String strObjectType = null;
    protected boolean passwordDecrypted = false;

    public BEIdentity(final String strObjectType) {
        this.strObjectType = strObjectType;
    }

    public String getObjectType() {
        return strObjectType;
    }

    public boolean isPasswordDecrypted() {
        return passwordDecrypted;
    }

    protected void setPasswordDecrypted(boolean passwordDecrypted) {
        this.passwordDecrypted = passwordDecrypted;
    }
}
