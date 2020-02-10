package com.tibco.cep.dashboard.psvr.variables;

import java.util.Properties;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.ManagementProperties;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.studio.dashboard.core.variables.Variable;

public class PROPVariableValueProvider implements VariableValueProvider {

	@Override
	public String getIdentifier() {
		return "PROP";
	}

	@Override
	public String getValue(VariableContext context, Variable variable) {
		String key = variable.getArgument();
		if (StringUtil.isEmptyOrBlank(key) == true) {
			context.getLogger().log(Level.WARN, "Invalid argument in "+variable);
			return "";
		} else {
			Properties properties = context.getProperties();
			if (properties == null) {
				context.getLogger().log(Level.WARN, "No properties found for resolving " + variable);
				return "";
			}
			if (properties.containsKey(key) == true) {
				return properties.getProperty(key);
			} else if (properties instanceof ManagementProperties) {
				ManagementProperties dashboardProperties = (ManagementProperties) properties;
				boolean keepLooking = true;
				while (keepLooking == true) {
					String keyPrefix = dashboardProperties.getKeyPrefix();
					if (key.startsWith(keyPrefix) == true) {
						String smallerKey = key.substring(keyPrefix.length());
						if (dashboardProperties.containsKey(smallerKey) == true) {
							key = smallerKey;
							keepLooking = false;
						}
					}
					if (keepLooking == true) {
						keepLooking = dashboardProperties.getSource() instanceof ManagementProperties;
						if (keepLooking == true) {
							dashboardProperties = (ManagementProperties) dashboardProperties.getSource();
						}
					}
				}
				return properties.getProperty(key);
			}
			context.getLogger().log(Level.WARN, "No value found for " + key+" in "+variable);
			return "";
		}

	}

}
