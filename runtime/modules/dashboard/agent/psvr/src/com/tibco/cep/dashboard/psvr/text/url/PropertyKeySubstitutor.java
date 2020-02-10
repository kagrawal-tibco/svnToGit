package com.tibco.cep.dashboard.psvr.text.url;

import java.util.Properties;
import com.tibco.cep.kernel.service.logging.Level;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.ManagementProperties;

class PropertyKeySubstitutor implements Substitutor {

	private static final long serialVersionUID = -602076618991948136L;

	// Keys are free-formed, can be anything from properties

	private static final String[] KEYS = new String[] {};

	public String substitute(String key, SubstitutionContext context) throws Exception {
		if (StringUtil.isEmptyOrBlank(key) == true) {
			context.getLogger().log(Level.WARN, "Invalid key specified for property resolution");
			throw new Exception("Invalid key specified for property resolution");
		} else {
			Properties properties = context.getProperties();
			if (properties == null){
				context.getLogger().log(Level.WARN, "No properties found for resolving "+key);
				return null;
			}
			if (properties.containsKey(key) == true){
				return properties.getProperty(key);	
			}
			else if (properties instanceof ManagementProperties){
				ManagementProperties dashboardProperties = (ManagementProperties)properties;
				boolean keepLooking = true;
				while (keepLooking == true){
					String keyPrefix = dashboardProperties.getKeyPrefix();
					if (key.startsWith(keyPrefix) == true){
						String smallerKey = key.substring(keyPrefix.length());
						if (dashboardProperties.containsKey(smallerKey) == true){
							key = smallerKey;
							keepLooking = false;
						}
					}
					if (keepLooking == true){
						keepLooking = dashboardProperties.getSource() instanceof ManagementProperties;
						if (keepLooking == true){
							dashboardProperties = (ManagementProperties) dashboardProperties.getSource();
						}
					}
				}
				return properties.getProperty(key);
			}
			context.getLogger().log(Level.WARN, "No value found for resolving "+key);
			return key;
		}

	}

	@Override
	public String[] getSupportedKeys() {
		return KEYS;
		
	}

}
