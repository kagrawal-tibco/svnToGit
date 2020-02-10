package com.tibco.be.util.packaging.descriptors.impl;
/*
 * User: nprade
 * Date: Feb 4, 2010
 * Time: 3:51:53 PM
 */


import com.tibco.be.util.packaging.Constants;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;
import com.tibco.xml.data.primitive.ExpandedName;


public class NameValuePairPassword
        extends NameValuePairImpl {
	
	private static boolean isExplicitlyEncrypted = false;


    public NameValuePairPassword() {
        super();
    }


    public NameValuePairPassword(String name) {
        super(name);
    }


    public NameValuePairPassword(String name, char[] value) {
        super(name, charArrayToEncryptedString(value));
    }


    public NameValuePairPassword(String name, char[] value, String description, boolean requiresConfiguration) {
        super(name, charArrayToEncryptedString(value), description, requiresConfiguration);
    }


    private static String charArrayToEncryptedString(char ac[]) {
        if (null == ac) {
            return null;
        }

        // this is basically the check the ObfuscationEngine uses to determine if a char array is already encrypted 
        if (ac.length > 2 && (ac.length - 2) % 4 == 0) {
        	// might already be an encrypted string
        	if (ac[0] == '#' && ac[1] == '!') {
        		// already encrypted, return string
        		String obfuscated = String.valueOf(ac);
        		return obfuscated;
        	}
        }
        try {
            String obfuscated = ObfuscationEngine.encrypt(ac);
            if (!obfuscated.startsWith("#!")) {
                obfuscated = "#!" + obfuscated;
            }
            isExplicitlyEncrypted =true;
            return obfuscated;
        }
        catch (AXSecurityException e) {
            throw new RuntimeException("Should not happen.", e);
        }
    }


    private static char[] encryptedStringToCharArray(String value) {
        if (null == value) {
            return null;
        }
        try {
        	if(isExplicitlyEncrypted){
        		return ObfuscationEngine.decrypt(value);
        	}else{
        		return value.toCharArray();
        	}
        } catch (AXSecurityException e) {
            throw new RuntimeException("Should not happen.", e);
        }
    }


    public void setValue(char ac[]) {
        super.setValue(charArrayToEncryptedString(ac));
    }


    public String getValue() {
        return new String(encryptedStringToCharArray(super.getValue()));
    }


    @Override
    public ExpandedName getTypeXName() {
        return Constants.DD.XNames.NAME_VALUE_PAIR_PASSWORD;
    }


}