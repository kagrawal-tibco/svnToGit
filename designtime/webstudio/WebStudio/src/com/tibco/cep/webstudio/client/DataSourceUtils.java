package com.tibco.cep.webstudio.client;

public class DataSourceUtils {

	public static final String TEMPLATE_INSTANCE_DS_PREFIX = "templateInstance_DS";

	public static String createTemplateDataSource(String projectDir, String projectName, String elementPath) {
		return createTemplateDataSource(projectDir, projectName, elementPath, null);
	}
	
	public static String createTemplateDataSource(String projectDir, String projectName, String elementPath, String symbolID) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(TEMPLATE_INSTANCE_DS_PREFIX);
		buffer.append('$');
		buffer.append(projectDir);
		buffer.append('$');
		buffer.append(projectName);
		buffer.append('$');
		buffer.append(elementPath);
		if (symbolID != null) {
			buffer.append('$');
			buffer.append(symbolID);
		}
		return buffer.toString();
	}
	
}
