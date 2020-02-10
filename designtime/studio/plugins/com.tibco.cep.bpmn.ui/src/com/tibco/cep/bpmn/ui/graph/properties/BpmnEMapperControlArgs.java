/**
 * 
 */
package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.xsd.XSDTerm;

import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.xml.mapperui.emfapi.EMapperControlArgs;
import com.tibco.xml.schema.SmElement;
/**
 * @author sshekhar
 *
 */
public class BpmnEMapperControlArgs extends EMapperControlArgs {
	
	private List<VariableDefinition> sourceElements;
	private List<VariableDefinition> globalVars;
	private Object targetElement;
	private Object sourceElement;
	private SmElement targetSchema;
	private SmElement sourceSchema;
	private com.tibco.cep.designtime.model.Entity modelEntity;
	private com.tibco.cep.designtime.core.model.Entity coreModelEntity;
	private String XSLT;
	private String Xpath;
	private SmElement entitySchema;
	private SmElement importedSchema;
	private BpmnXSDResourceImpl xsdResImpl;
	private String entityUri;
	public IProject project;
	public BEProject beProject;
	public boolean isInputMapper = false;
	public UIAgent uiAgent;
	private SmElement ruleFunctionSchema ;
	private XSDTerm sourceXSDTerm;
	
	public XSDTerm getSourceXSDTerm() {
		return sourceXSDTerm;
	}

	public void setSourceXSDTerm(XSDTerm sourceXSDTerm) {
		this.sourceXSDTerm = sourceXSDTerm;
	}

	public BpmnEMapperControlArgs() {
		super();
	}

	public void setSourceElements(List<VariableDefinition> sourceElements) {
		this.sourceElements = sourceElements;
	}

	public void setTargetElement(Object targetEntity) {
		this.targetElement = targetEntity;
	}

	public List<VariableDefinition> getSourceElements() {
		return sourceElements;
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

	public BpmnXSDResourceImpl getXsdResImpl() {
		return xsdResImpl;
	}

	public void setXsdResImpl(BpmnXSDResourceImpl xsdResImpl) {
		this.xsdResImpl = xsdResImpl;
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

	public List<VariableDefinition> getGlobalVars() {
		return globalVars;
	}

	public void setGlobalVars(List<VariableDefinition> globalVars) {
		this.globalVars = globalVars;
	}

	public SmElement getRuleFunctionSchema() {
		return ruleFunctionSchema;
	}

	public void setRuleFunctionSchema(SmElement ruleFunctionSchema) {
		this.ruleFunctionSchema = ruleFunctionSchema;
	}

	public com.tibco.cep.designtime.model.Entity getModelEntity() {
		return modelEntity;
	}

	public com.tibco.cep.designtime.core.model.Entity getCoreModelEntity() {
		return coreModelEntity;
	}

	public void setModelEntity(com.tibco.cep.designtime.model.Entity modelEntity) {
		this.modelEntity = modelEntity;
	}

	public SmElement getImportedSchema() {
		return importedSchema;
	}

	public void setImportedSchema(SmElement importedSchema) {
		this.importedSchema = importedSchema;
	}

	public void setCoreModelEntity(
			com.tibco.cep.designtime.core.model.Entity coreModelEntity) {
		this.coreModelEntity = coreModelEntity;
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
