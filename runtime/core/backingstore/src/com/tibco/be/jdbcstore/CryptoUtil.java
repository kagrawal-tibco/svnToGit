package com.tibco.be.jdbcstore;

import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;

public class CryptoUtil {

	public static String decryptIfEncrypted(String text){
		if(text.startsWith("#!")){
			try {
				return new String(ObfuscationEngine.decrypt(text));
			} catch (AXSecurityException e) {
				e.printStackTrace();
				return null;
			}
		}else {
			return text;
		}
	}
	
}
