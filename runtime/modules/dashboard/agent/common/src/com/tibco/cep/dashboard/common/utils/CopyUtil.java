package com.tibco.cep.dashboard.common.utils;

import java.util.Collection;

import com.tibco.cep.dashboard.management.ManagementProperties;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.session.RuleServiceProvider;

public class CopyUtil {
	public static void copyGlobalVariablesToProperties(RuleServiceProvider rsp, ManagementProperties properties) {
		// get the latest global variables from RSP and update them in management properties
		GlobalVariables globalVariables = rsp.getGlobalVariables();
		Collection <GlobalVariableDescriptor> allVariables = globalVariables.getVariables();
		for(GlobalVariableDescriptor gvDescriptor: allVariables) {
			String gvFullName = gvDescriptor.getFullName();
			String gvValue = gvDescriptor.getValueAsString();
			properties.getSource().setProperty("global.variable."+gvFullName, gvValue);						
		}
	}
}
