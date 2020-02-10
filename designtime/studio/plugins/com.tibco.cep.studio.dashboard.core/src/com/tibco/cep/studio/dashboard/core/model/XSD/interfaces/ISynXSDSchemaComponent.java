package com.tibco.cep.studio.dashboard.core.model.XSD.interfaces;

/**
 * The base for all Schema elements
 *
 */
public interface ISynXSDSchemaComponent {

	public String getName();

	public void setName(String name);

	public String getTargetNameSpace();

	public void setTargetNameSpace(String targetNameSpace);

	public ISynXSDSchemaComponent getContainer();

	public void setContainer(ISynXSDSchemaComponent container);

	public ISynXSDAnnotation getAnnotation();

	public void setAnnotation(ISynXSDAnnotation annotation);

	public Object cloneThis() throws Exception;

}