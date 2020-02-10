package com.tibco.cep.dashboard.psvr.variables;

import java.util.Properties;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.ManagementProperties;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.studio.dashboard.core.variables.Variable;

/**
 * 
 * @author ntamhank Used for substituting global variables in URL links
 */
public class GVARVariableValueProvider implements VariableValueProvider {

	@Override
	public String getIdentifier() {
		return "GVAR";
	}

	@Override
	public String getValue(VariableContext context, Variable variable) {
		String key = variable.getArgument();
		if (StringUtil.isEmptyOrBlank(key) == true) {
			context.getLogger().log(Level.WARN, "Invalid argument in " + variable);
			return "";
		} else {
			String gvKey = "global.variable." + key;
			String gvValue = null;
			Properties properties = context.getProperties();
			if (properties == null) {
				context.getLogger().log(Level.WARN, "No properties found for resolving " + variable);
				return "";
			}
			if (properties.containsKey(gvKey) == true) {
				return properties.getProperty(gvKey);
			} else if (properties instanceof ManagementProperties) {
				ManagementProperties dashboardProperties = (ManagementProperties) properties;
				gvValue = (String) dashboardProperties.getSource().get(gvKey);
				if (gvValue != null) {
					return gvValue;
				}
				if (dashboardProperties.getSource() instanceof ManagementProperties) {
					if (((ManagementProperties) dashboardProperties.getSource()).getSource().containsKey(gvKey)) {
						return ((ManagementProperties) dashboardProperties.getSource()).getSource().getProperty(gvKey);
					}
				}
			}
		}
		context.getLogger().log(Level.WARN, "No value found for " + key + " in " + variable);
		return "";
	}
	
}