package com.tibco.cep.studio.dashboard.core.model.XSD.interfaces;

/**
 * @
 *
 */
public interface ISynXSDListSimpleTypeDefinition extends ISynXSDSimpleTypeDefinition {
    /**
     * The ISynXSDSimpleTypeDefinition return may have facets that correspond to
     * the available facets of the base type of the containing simple type definition
     * @return
     */
    public ISynXSDSimpleTypeDefinition getItemType();
}
