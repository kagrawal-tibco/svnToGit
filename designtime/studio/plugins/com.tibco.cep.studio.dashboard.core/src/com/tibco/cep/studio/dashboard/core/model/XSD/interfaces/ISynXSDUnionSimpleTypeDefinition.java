package com.tibco.cep.studio.dashboard.core.model.XSD.interfaces;

import java.util.List;

/**
 *
 */
public interface ISynXSDUnionSimpleTypeDefinition extends ISynXSDSimpleTypeDefinition {

    /**
     * @return List A list of ISynXSDSimpleTypeDefinition for the union operation
     */
    public List<ISynXSDSimpleTypeDefinition> getMemberTypes();
}
