package com.tibco.cep.studio.dashboard.core.model.XSD.interfaces;

import java.util.List;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.providers.ISynXSDPropertyProvider;

/**
 * @
 *
 */
public interface ISynXSDComplexContent extends ISynXSDPropertyProvider, ISynXSDComplexTypeContent {

    public List<ISynXSDParticle> getParticles();

    public void addParticle(ISynXSDParticle particle);

    /**
     * Add an element declaration to this complex content
     *
     * @param element
     * @param minOccurs
     * @param maxOccurs
     */
    public void addElement(ISynXSDElementDeclaration element, int minOccurs, int maxOccurs);


    public void removeElement(String elementName);


    /**
     * This is actually adding a model group to the top level model group of
     * this complex content
     *
     * @param group
     * @param minOccurs
     * @param maxOccurs
     */
    public void addModelGroup(ISynXSDModelGroup group, int minOccurs, int maxOccurs);

    /**
     *
     * @return ISynXSDModelGroup The top level model group
     *
     * @see com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDModelGroup
     */
    public ISynXSDModelGroup getModelGroup();

    /**
     *
     * @return ISynValidatingType The base class for extension
     */
    public ISynXSDTypeDefinition getBase();
}
