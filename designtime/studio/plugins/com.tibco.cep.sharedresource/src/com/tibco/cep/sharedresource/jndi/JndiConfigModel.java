package com.tibco.cep.sharedresource.jndi;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.tibco.cep.sharedresource.model.SharedResModel;

/*
@author ssailapp
@date Dec 29, 2009 8:18:02 PM
 */

public class JndiConfigModel extends SharedResModel {
	static Map<String, String> factoryUrl;
	public ArrayList<LinkedHashMap<String, String>> jndiProps;
	
	public JndiConfigModel(String name) {
		this.name = name;
		initFactoryUrlMap();
		jndiProps = new ArrayList<LinkedHashMap<String, String>>();
	}
	
	private static void initFactoryUrlMap() {
		factoryUrl = new LinkedHashMap<String, String>();
		factoryUrl.put("com.tibco.tibjms.naming.TibjmsInitialContextFactory", "tibjmsnaming://localhost:7222");
		factoryUrl.put("com.ibm.websphere.naming.WsnInitialContextFactory", "iiop://locahost:900");
		factoryUrl.put("org.jnp.interfaces.NamingContextFactory", "jnp://localhost:1099");
		factoryUrl.put("weblogic.jndi.WLInitialContextFactory", "t3://localhost:7001");
	}

	public static Map<String, String> getFactoryUrls() {
		initFactoryUrlMap();
		return factoryUrl;
	}
}
