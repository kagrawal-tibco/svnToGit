package com.tibco.cep.studio.core.util;

import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;

/*
@author ssailapp
@date Dec 29, 2009 4:26:15 PM
 */

public class PasswordUtil {

	public static String getEncodedString(String password) {
		if (GvUtil.isGlobalVar(password))
			return password;
		if (password == null)
			return ("");
		if (password.startsWith("#!"))
			return password;
		String encoded = password;
		try {
			encoded = ObfuscationEngine.encrypt(password.toCharArray());
		} catch (AXSecurityException e) {
			e.printStackTrace();
		}
		return encoded;
	}

	public static String getDecodedString(String encoded) {
		if (encoded == null || encoded.trim().equals(""))
			return ("");
		if (GvUtil.isGlobalVar(encoded))
			return encoded;
		char password[] = new char[0];
		try {
			password = ObfuscationEngine.decrypt(encoded);
		} catch (AXSecurityException e) {
			e.printStackTrace();
		}
		return new String(password);
	}
	
	public static boolean isEncodedString(String password) {
		if (password == null || !password.startsWith("#!"))
			return false;
		return true;
	}
}
