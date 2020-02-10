package com.tibco.cep.studio.dashboard.core.model.XSD.interfaces;

/**
 * An association between a name and a Model Group
 *
 */
public interface ISynXSDModelGroupDefinition extends ISynXSDSchemaComponent {

    public ISynXSDModelGroup getModelGroup()throws Exception;

}