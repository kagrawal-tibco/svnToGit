package com.tibco.cep.sharedresource.ascon;

import com.tibco.cep.sharedresource.model.SharedResModel;

/*
@author George Wang (nwang@tibco-support.com)
@date Jan 21, 2014
*/

public class ASConnectionModel extends SharedResModel {

	static final String AS_CONFIG_DESCRIPTION = "Description";
	static final String AS_CONFIG_METASPACE_NAME = "MetaspaceName";
	static final String AS_CONFIG_MEMBER_NAME = "MemberName";
	static final String AS_CONFIG_DISCOVERY_URL = "DiscoveryUrl";
	static final String AS_CONFIG_LISTEN_URL = "ListenUrl";
	static final String AS_CONFIG_REMOTE_LISTEN_URL = "RemoteListenUrl";
	
	//For AS security
	static final String AS_CONFIG_ENABLE_SECURITY = "EnableSecurity";
	static final String AS_CONFIG_SECURITY_ROLE = "SecurityRole";
	static final String AS_CONFIG_ID_PASSWORD = "IdentityPassword";
	static final String AS_CONFIG_POLICY_FILE = "PolicyFile";
	static final String AS_CONFIG_TOKEN_FILE = "TokenFile";

	//Authentication
	static final String AS_CONFIG_AUTH_CREDENTIAL = "Credential";
	    // user-pwd
	    static final String AS_CONFIG_AUTH_DOMAIN = "Domain";
	    static final String AS_CONFIG_AUTH_USERNAME = "UserName";
	    static final String AS_CONFIG_AUTH_PASSWORD = "Password";

	    // x509v3
        static final String AS_CONFIG_AUTH_KEY_FILE = "KeyFile";
        static final String AS_CONFIG_AUTH_PRIVATE_KEY = "PrivateKey";
}
