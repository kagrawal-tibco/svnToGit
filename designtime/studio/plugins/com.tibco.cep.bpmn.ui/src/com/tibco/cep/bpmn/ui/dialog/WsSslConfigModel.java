package com.tibco.cep.bpmn.ui.dialog;

/*
@author ssailapp
@date Dec 28, 2009 12:13:08 PM
 */

public class WsSslConfigModel {

	public static final String ID_CERT_FOLDER = "cert";
	public static final String ID_IDENTITY = "identity";
	public static final String ID_TRUST_STORE_PASSWD = "trustStorePassword";
	public String cert = "", identity = "", trustStorePasswd = "";

	public String getTrustStorePasswd() {
		return trustStorePasswd;
	}

	public void setTrustStorePasswd(String trustStorePasswd) {
		this.trustStorePasswd = trustStorePasswd;
	}

	public WsSslConfigModel() {
		cert = "";
		identity = "";
		trustStorePasswd = "";
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
