package com.tibco.cep.sharedresource.ssl;

/*
@author ssailapp
@date Mar 2, 2010 3:14:31 PM
 */

public class SslConfigHttpModel extends SslConfigModel {
	public static final String ID_CLIENT_AUTH = "requiresClientAuthentication";
	public static final String ID_CIPHER_SUITE = "strongCipherSuitesOnly";
	
	public String cipherSuites, clientAuth;
	
	public SslConfigHttpModel() {
		super();
		cipherSuites = "true";
		clientAuth = "false";
	}
}
