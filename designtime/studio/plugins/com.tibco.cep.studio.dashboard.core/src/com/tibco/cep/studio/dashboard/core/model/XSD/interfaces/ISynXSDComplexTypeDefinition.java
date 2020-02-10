package com.tibco.cep.studio.dashboard.core.model.XSD.interfaces;

import java.util.List;

import com.tibco.cep.studio.dashboard.core.model.XSD.modifiers.SynXSDComplexTypeBlockModifier;
import com.tibco.cep.studio.dashboard.core.model.XSD.modifiers.SynXSDComplexTypeFinalModifier;

/**
 * @
 *
 */
public interface ISynXSDComplexTypeDefinition extends ISynXSDTypeDefinition {

    /**
     * The simple type corresponding to the simple content (if any) otherwise
     * return <code>null</code>;
     *
     * @return
     */
    public ISynXSDSimpleTypeDefinition getSimpleType();

    /**
     * A particle for an element-only content model, otherwise <code>null</code>.
     */
    public ISynXSDParticle getParticle();

    /**
     * Returns a List of SynXSDComplexTypeBlockModifier
     *
     * @return
     */
    public List<SynXSDComplexTypeBlockModifier> getBlockModifiers();

    public void addBlockModifier(SynXSDComplexTypeBlockModifier blockModifier);

    public void removeBlockModifier(SynXSDComplexTypeBlockModifier blockModifier);

    /**
     * Returns a List of SynXSDComplexTypeFinalModifier for this type
     *
     * @return
     */
    public List<SynXSDComplexTypeFinalModifier> getFinalModifiers();

    public void addFinalModifier(SynXSDComplexTypeFinalModifier finalModifier);

    public void removeFinalModifier(SynXSDComplexTypeFinalModifier finalModifier);

    /**
     *
     * @return boolean Whether this complex type is abstract
     */
    public boolean isAbstract();

    public void setAbstract(boolean value);

    /**
     *
     * @return ISynXSDComplexTypeContent The content of this type Can be complex
     *         or simple or (model group and attribute/attribute group)
     * @see com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDComplexTypeContent
     * @see com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDComplexContent
     * @see com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDSimpleContent
     */
    public ISynXSDComplexTypeContent getContent();
}
