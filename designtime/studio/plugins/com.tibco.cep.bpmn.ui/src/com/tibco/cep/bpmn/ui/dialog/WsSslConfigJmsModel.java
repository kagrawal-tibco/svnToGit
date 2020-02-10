package com.tibco.cep.bpmn.ui.dialog;

/*
@author ssailapp
@date Mar 2, 2010 3:15:19 PM
 */

public class WsSslConfigJmsModel extends WsSslConfigModel {
	public static final String ID_TRACE = "trace";
	public static final String ID_DEBUG_TRACE = "debugTrace";
	public static final String ID_VERIFY_HOST_NAME = "verifyHostName";
	public static final String ID_CIPHER_SUITE = "strongCipherSuitesOnly";
	public static final String ID_EXPECTED_HOSTNAME = "expectedHostName";
	public String expectedHostName;
	public boolean trace, debugTrace, verifyHostName, cipherSuites;
	
	public WsSslConfigJmsModel() {
		super();
		expectedHostName = "";
		trace = true;
		debugTrace = true;
		verifyHostName = true;
		cipherSuites = true;
	}
}
