/**
 * 
 */
package com.tibco.cep.webstudio.server.command;

import java.io.Serializable;
import java.util.List;

import com.tibco.cep.webstudio.client.process.properties.ProcessVariable;
import com.tibco.cep.webstudio.client.process.properties.ProcessVariableProperty;
import com.tibco.cep.webstudio.server.model.process.Process;
import com.tibco.cep.webstudio.server.model.process.Properties;
import com.tibco.cep.webstudio.server.model.process.Property;

/**
 * This class is used to populate the process variable properties and add it in
 * command data.
 * 
 * @author dijadhav
 * 
 */
public class ProcessVariablePropertyUtil {
	/**
	 * This method is used to populate the process variables
	 * 
	 * @param commandData
	 * @param drawingView
	 */
	public static void populateProcessVariables(List<Serializable> commandData,
			Process process) {

		ProcessVariableProperty processVariableProperty = new ProcessVariableProperty();
		Properties properties = process.getProperties();
		if (null != properties) {
			List<Property> propertyList = properties.getProperty();
			if (null != propertyList && !propertyList.isEmpty()) {
				for (Property property : propertyList) {
					if (null != property) {
						ProcessVariable processVariable = new ProcessVariable();
						processVariable.setId(property.getId().longValue());
						processVariable.setMultiple(property.isIsMultiple());
						processVariable.setName(property.getName());
						processVariable.setPath(property.getPath());
						processVariable.setType(property.getType());
						processVariableProperty
								.addProcessVariable(processVariable);
					}
				}
			}
		}
		commandData.add(processVariableProperty);
	}
}
