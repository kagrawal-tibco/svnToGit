package com.tibco.cep.dashboard.security;

import com.tibco.cep.dashboard.config.GlobalConfiguration;
import com.tibco.cep.dashboard.config.PropertyKey;
import com.tibco.cep.dashboard.config.PropertyKeys;


public interface SecurityProperties extends PropertyKeys {
	
	static final PropertyKey TOKEN_TIME_OUT_KEY = new PropertyKey("token.timeout", "Defines the amount of inactive period before a token is marked as timed out", PropertyKey.DATA_TYPE.Long, -1L);
	
	//static final PropertyKey JAAS_AUTH_KEY = new PropertyKey("java.security.auth.login.config", "Defines the login configuration to be used by JAAS", PropertyKey.DATA_TYPE.String,System.getProperty("BE_HOME")+"/views/config/jaas-config.config");
	//ntamhank Oct 04 2010: Fix for BE-9435: System.getProperty("BE_HOME") returns null and it subsequently fails. Added option of getting it using GlobalConfiguration.getInstance().getInstallationHome()
	static final PropertyKey JAAS_AUTH_KEY = new PropertyKey("java.security.auth.login.config", "Defines the login configuration to be used by JAAS", PropertyKey.DATA_TYPE.String,GlobalConfiguration.getInstance().getInstallationHome()+"/views/config/jaas-config.config");	

}
