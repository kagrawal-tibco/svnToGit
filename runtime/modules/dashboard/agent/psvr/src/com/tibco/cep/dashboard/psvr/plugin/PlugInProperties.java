package com.tibco.cep.dashboard.psvr.plugin;

import java.util.Enumeration;

import com.tibco.cep.dashboard.management.ManagementProperties;

class PlugInProperties extends ManagementProperties {

	private static final long serialVersionUID = -6858265536618549078L;
	
	private String completeKeyPrefix;
	
	PlugInProperties(ManagementProperties source,String plugInName){
		super(source);
		completeKeyPrefix = source.getKeyPrefix() + "plugin."+plugInName+".";
		keyPrefix = "plugin."+plugInName+".";
		int length = keyPrefix.length();
		Enumeration<Object> keys = source.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String propValue = source.getProperty(key);
			if (key.startsWith("plugin.") == true){
				if (key.startsWith(keyPrefix) == true){
					key = key.substring(length);
					super.setPropertyActually(key, propValue);
				}
			}
			else {
				super.setPropertyActually(key, propValue);	
			}
			
		}		
	}

	@Override
	public String getKeyPrefix() {
		return completeKeyPrefix;
	}

}