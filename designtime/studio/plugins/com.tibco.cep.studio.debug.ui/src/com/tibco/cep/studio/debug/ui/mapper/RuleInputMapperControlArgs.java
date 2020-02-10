package com.tibco.cep.studio.debug.ui.mapper;

import org.eclipse.xsd.XSDTerm;

import com.tibco.xml.mapperui.emfapi.EMapperControlArgs;
import com.tibco.xml.schema.SmElement;

/**
 * 
 * @author sasahoo
 *
 */
public class RuleInputMapperControlArgs extends EMapperControlArgs {
	
	private Object targetElement;
	private Object sourceElement;
	private SmElement targetSchema;
	private SmElement sourceSchema;
	private String XSLT;
	private String Xpath;
	private SmElement entitySchema;
	private String entityUri;
	private XSDTerm sourceXSDTerm;
	protected RuleInputMapperInvocationContext context;

	
	public RuleInputMapperInvocationContext getInvocationContext() {
		return context;
	}

	public void setInvocationContext(RuleInputMapperInvocationContext context) {
		this.context = context;
	}

	public XSDTerm getSourceXSDTerm() {
		return sourceXSDTerm;
	}

	public void setSourceXSDTerm(XSDTerm sourceXSDTerm) {
		this.sourceXSDTerm = sourceXSDTerm;
	}

	public RuleInputMapperControlArgs() {
		super();
	}

	public void setTargetElement(Object targetEntity) {
		this.targetElement = targetEntity;
	}

	public Object getTargetElement() {
		return targetElement;
	}

	public String getXSLT() {
		if (XSLT != null && XSLT.length() > 0 && XSLT.charAt(0) == '"') {
			return XSLT.substring(1, XSLT.length()-1);
		}
		return XSLT;
	}

	public void setXSLT(String xSLT) {
		XSLT = xSLT;
	}

	public String getXpath() {
		return Xpath;
	}

	public void setXpath(String xpath) {
		Xpath = xpath;
	}

	public SmElement getEntitySchema() {
		return entitySchema;
	}

	public void setEntitySchema(SmElement entitySchema) {
		this.entitySchema = entitySchema;
	}

	public String getEntityUri() {
		return entityUri;
	}

	public void setEntityUri(String entityUri) {
		this.entityUri = entityUri;
	}

	public Object getSourceElement() {
		return sourceElement;
	}

	public void setSourceElement(Object sourceElement) {
		this.sourceElement = sourceElement;
	}

	public SmElement getTargetSchema() {
		return targetSchema;
	}

	public SmElement getSourceSchema() {
		return sourceSchema;
	}

	public void setTargetSchema(SmElement targetSchema) {
		this.targetSchema = targetSchema;
	}

	public void setSourceSchema(SmElement sourceSchema) {
		this.sourceSchema = sourceSchema;
	}
	
}