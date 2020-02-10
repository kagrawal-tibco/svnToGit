package com.tibco.cep.studio.core.util.mapper;

import java.util.List;

import org.eclipse.jface.text.IDocument;

import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.functions.impl.JavaStaticFunctionWithXSLT;
import com.tibco.be.util.TraxSupport;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.xml.schema.SmElement;

public class MapperInvocationContext {

	private static final String UPDATE_INSTANCE_NAME = "updateInstance";
	private List<VariableDefinition> definitions;
	private String xslt;
	private String projectName;
	private String paramName;
	private Predicate function;
	private SmElement inputElement;
	private IDocument document;
	private RulesASTNode node;
	private boolean mapperEditable = true; // true by default
	private boolean xpath2;

	public MapperInvocationContext(String projectName, List<VariableDefinition> definitions, String xslt, Predicate function, IDocument document, RulesASTNode node) {
		this.definitions = definitions;
		this.xslt = xslt;
		this.projectName = projectName;
		this.function = function;
		this.document = document;
		this.node = node;
		this.mapperEditable = true;
		this.xpath2 = TraxSupport.isXPath2DesignTime(projectName);
	}

	public RulesASTNode getNode() {
		return node;
	}

	public IDocument getDocument() {
		return document;
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
//		boolean hasCoercions = xslt.indexOf("<!--START COERCIONS-->") != -1;
//		if (hasCoercions) { // BE-23420 : this can occur for substitution elements as well, not just coercions.  So need to always search for schema-location
			// terrible hack to get around a BW6 limitation with coercions in project libraries (BE-22982)
			int idx = xslt.indexOf("schema-location=");
			int idx2 = xslt.indexOf(".projlib");
			if (idx2 > idx && idx > 0) {
				// need to strip this schema-location, since BW6 can't handle a linked file inside an archive
				String regex = "schema-location=\\\\\"[^\\\"]+\\\"";
				String newxslt = xslt.replaceAll(regex, "");
				xslt = newxslt;
				// we could check to ensure that the schema-location indeed has a .projlib, but the above check is sufficient for nearly every case
//				Pattern compile = Pattern.compile(regex);
//				Matcher matcher = compile.matcher(xslt);
//				while (matcher.find()) {
//					String group = matcher.group();
//					if (group.contains(".projlib")) {
//						String newxslt = xslt.replaceAll(regex, "");
//						xslt = newxslt;
//					}
//				}
			}
			
			if (xpath2) {
				String regex = "\" version=\\\"1.0\\\"";
				int versionIdx = xslt.indexOf(regex);
				if (versionIdx != -1) {
					// set the version of the xslt to 2.0
					char[] charArray = xslt.toCharArray();
					if (charArray[versionIdx+regex.length()-5] == '1') {
						charArray[versionIdx+regex.length()-5] = '2';
						xslt = String.valueOf(charArray);
					}
				}
			}
//		}
		this.xslt = xslt;
	}

	public String getProjectName() {
		return projectName;
	}

	public Predicate getFunction() {
		return function;
	}

	public boolean isMapperEditable() {
		return mapperEditable;
	}

	public void setMapperEditable(boolean mapperEditable) {
		this.mapperEditable = mapperEditable;
	}

	public boolean isUpdate() {
		return UPDATE_INSTANCE_NAME.equals(getFunction().getName().localName);
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
		if (function instanceof JavaStaticFunctionWithXSLT) {
			return ((JavaStaticFunctionWithXSLT)function).getInputType();
		}
		return null;
	}

	public void setInputElement(SmElement inputElement) {
		this.inputElement = inputElement;
	}

}
