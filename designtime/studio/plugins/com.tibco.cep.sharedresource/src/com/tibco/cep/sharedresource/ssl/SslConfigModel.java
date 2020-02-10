package com.tibco.cep.sharedresource.ssl;

/*
@author ssailapp
@date Dec 28, 2009 12:13:08 PM
 */

public class SslConfigModel {

	public static final String ID_CERT_FOLDER = "cert";
	public static final String ID_IDENTITY = "identity";
	public static final String ID_TRUST_STORE_PASSWD = "trustStorePassword";
	private String cert = "", identity = "", trustStorePasswd = "";
	private boolean isTrustStorePasswdGv = false;

	public String getTrustStorePasswd() {
		return trustStorePasswd;
	}

	public void setTrustStorePasswd(String trustStorePasswd , boolean isTrustStorePasswdGv ) {
		this.trustStorePasswd = trustStorePasswd;
		this.isTrustStorePasswdGv = isTrustStorePasswdGv;
	}
	
	public boolean isTurstStorePasswordGv(){
		return isTrustStorePasswdGv;
	}

	public SslConfigModel() {
		cert = "";
		identity = "";
		trustStorePasswd = "";
		isTrustStorePasswdGv = false;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}
	
	public String getCert() {
		return cert;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
}
