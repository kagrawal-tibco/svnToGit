package com.tibco.cep.studio.debug.ui.mapper;

import java.util.List;

import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.xml.schema.SmElement;

/**
 * 
 * @author sasahoo
 *
 */
public class RuleInputMapperInvocationContext {

	private List<VariableDefinition> definitions;
	private String xslt;
	private String projectName;
	private String paramName;
	private SmElement inputElement;
	private boolean mapperEditable = true; // true by default

	public RuleInputMapperInvocationContext(String projectName, List<VariableDefinition> definitions, String xslt) {
		this.definitions = definitions;
		this.xslt = xslt;
		this.projectName = projectName;
		this.mapperEditable = true;
	}

	public List<VariableDefinition> getDefinitions() {
		return definitions;
	}

	public String getXslt() {
		if (xslt != null && xslt.length() > 0 && xslt.charAt(0) == '"') {
			return xslt.substring(1, xslt.length()-1);
		}
		return xslt;
	}

	public void setXslt(String xslt) {
		this.xslt = xslt;
	}

	public String getProjectName() {
		return projectName;
	}

	public boolean isMapperEditable() {
		return mapperEditable;
	}

	public void setMapperEditable(boolean mapperEditable) {
		this.mapperEditable = mapperEditable;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public SmElement getInputElement() {
		if (inputElement != null) {
			return inputElement;
		}
		return null;
	}

	public void setInputElement(SmElement inputElement) {
		this.inputElement = inputElement;
	}
	
	
}
