package com.tibco.cep.studio.dashboard.ui.viewers.attribute;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

/**
 * Verify listener that only allows 0-9 as a valid character
 *  
 */
public class MultiStateVerifier implements VerifyListener {

    public static final String VERIFY_ALPHA_CHARACTER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String VERIFY_INTEGER = "1234567890";

    public static final String VERIFY_DECIMAL = ".";

    public static final String VERIFY_FLOAT = VERIFY_INTEGER + VERIFY_DECIMAL;

    public static final String VERIFY_DOUBLE = VERIFY_INTEGER + VERIFY_DECIMAL;

    public static final String VERIFY_NAME_PREFIX = VERIFY_ALPHA_CHARACTER + "_";

    public static final String VERIFY_NAME_BODY = VERIFY_NAME_PREFIX + VERIFY_INTEGER + VERIFY_DECIMAL;

    public static final String VERIFY_NOT_ALLOWED = VERIFY_DECIMAL + VERIFY_DECIMAL;

    public static final String VERIFY_ALLOW_ALL = null;

    private String allowedCharacters = null;

    public MultiStateVerifier() {
    }

    public MultiStateVerifier(String allowedCharacters) {
        this.allowedCharacters = allowedCharacters;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.events.VerifyListener#verifyText(org.eclipse.swt.events.VerifyEvent)
     */
    public void verifyText(VerifyEvent e) {

        /*
         * If this is a multi-charater edit the allow it through; the
         * implementing user of this class should take care to validate the
         * input.
         */
        if (e.text.length() > 1) {
            e.doit = true;
            return;
        }

        /*
         * If allowedCharacter is null then allow everything
         */
        if (getAllowedCharacters() == null) {
            e.doit = true;
            return;
        }

        /*
         * Allows only the characters found in getAllowedCharacters()
         *  
         */
        if (getAllowedCharacters().indexOf(e.text) != -1) {
            e.doit = true;
            return;
        }

        e.doit = false;

    }

    public String getAllowedCharacters() {

        return allowedCharacters;
    }

    public void setAllowedCharacters(String allowedCharacters) {

        this.allowedCharacters = allowedCharacters;
    }
}