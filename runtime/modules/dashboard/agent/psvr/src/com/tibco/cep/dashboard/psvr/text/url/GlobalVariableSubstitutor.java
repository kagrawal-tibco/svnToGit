package com.tibco.cep.dashboard.psvr.text.url;

import java.util.Properties;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.ManagementProperties;
import com.tibco.cep.kernel.service.logging.Level;

/**
 * 
 * @author ntamhank
 * Used for substituting global variables in URL links
 */
public class GlobalVariableSubstitutor implements Substitutor {

	private static final long serialVersionUID = 1L;
	
	// Keys are free-formed, can be anything from properties
	private static final String[] KEYS = new String[] {};
	@Override
	public String[] getSupportedKeys() {
		return GlobalVariableSubstitutor.KEYS;
	}

	@Override
	public String substitute(String key, SubstitutionContext context) throws Exception {
		if (StringUtil.isEmptyOrBlank(key) == true) {
			context.getLogger().log(Level.WARN, "Invalid key specified for global variable resolution");
			throw new Exception("Invalid key specified for global variable resolution");
		}
		else {
			String gvKey = "global.variable."+key;
			String gvValue = null;
			Properties properties = context.getProperties();
			if (properties == null){
				context.getLogger().log(Level.WARN, "No properties found for resolving "+key);
				return null;
			}
			if (properties.containsKey(gvKey) == true){
				return properties.getProperty(gvKey);	
			}
			else if (properties instanceof ManagementProperties){
				ManagementProperties dashboardProperties = (ManagementProperties)properties;
				gvValue = (String)dashboardProperties.getSource().get(gvKey);
				if(gvValue != null) {
					return gvValue;
				}
				if(dashboardProperties.getSource() instanceof ManagementProperties) {
					if(((ManagementProperties)dashboardProperties.getSource()).getSource().containsKey(gvKey)) {
						return ((ManagementProperties)dashboardProperties.getSource()).getSource().getProperty(gvKey);
					}
				}
				context.getLogger().log(Level.WARN, "GlobalVariable not found in management properties");
				throw new Exception("GlobalVariable not found in management properties");
			}			
		}
		return key;
	}
}
