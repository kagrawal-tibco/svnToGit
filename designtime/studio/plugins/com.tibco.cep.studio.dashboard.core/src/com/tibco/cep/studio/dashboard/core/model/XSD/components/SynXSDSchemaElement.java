package com.tibco.cep.studio.dashboard.core.model.XSD.components;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAnnotation;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDSchemaComponent;

/**
 * @ *
 */
public class SynXSDSchemaElement implements ISynXSDSchemaComponent {

	private String name;

	private String targetNameSpace;

	private ISynXSDAnnotation annotation;

	private ISynXSDSchemaComponent container;

	public SynXSDSchemaElement() {
	}

	public SynXSDSchemaElement(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTargetNameSpace() {
		return targetNameSpace;
	}

	public void setTargetNameSpace(String targetNameSpace) {
		this.targetNameSpace = targetNameSpace;
	}

	public ISynXSDSchemaComponent getContainer() {
		return container;
	}

	public void setContainer(ISynXSDSchemaComponent container) {
		this.container = container;
	}

	public ISynXSDAnnotation getAnnotation() {
		return annotation;
	}

	public void setAnnotation(ISynXSDAnnotation annotation) {
		this.annotation = annotation;
	}

	public Object cloneThis() throws Exception {
		SynXSDSchemaElement clone = new SynXSDSchemaElement();
		this.cloneThis(clone);
		return clone;
	}

	protected void cloneThis(SynXSDSchemaElement clone) throws Exception {
		clone.name = this.name;
		clone.targetNameSpace = this.targetNameSpace;
		if (this.annotation == null) {
			clone.annotation = null;
		} else {
			clone.annotation = (ISynXSDAnnotation) this.annotation.cloneThis();
		}
		if (this.container == null) {
			clone.container = null;
		} else {
			clone.container = (SynXSDSchemaElement) this.container.cloneThis();
		}
	}

}