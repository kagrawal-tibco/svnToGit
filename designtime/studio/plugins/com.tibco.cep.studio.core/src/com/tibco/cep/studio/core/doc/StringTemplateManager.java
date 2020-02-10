package com.tibco.cep.studio.core.doc;

import java.io.File;
import java.io.FileReader;
import java.util.Map;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

/**
 * This class handles all antlr StringTemplate related operations.
 * @author moshaikh
 *
 */
public class StringTemplateManager {
	
	private StringTemplateGroup templateGroup;

	/**
	 * Pass the StringTemplate group.
	 * @param groupFilePath
	 * @throws Exception
	 */
	public StringTemplateManager(StringTemplateGroup templateGroup) throws Exception {
		this.templateGroup = templateGroup;
	}

	public String processTemplate(String templateName, Object bean) {
		StringTemplate template = templateGroup.getInstanceOf(templateName);
		template.setAttribute("dataObject", bean);
		return template.toString();
	}

	public String processTemplate(String templateName,
			Map<String, Object> values) {
		StringTemplate template = templateGroup.getInstanceOf(templateName,
				values);
		return template.toString();
	}
}
