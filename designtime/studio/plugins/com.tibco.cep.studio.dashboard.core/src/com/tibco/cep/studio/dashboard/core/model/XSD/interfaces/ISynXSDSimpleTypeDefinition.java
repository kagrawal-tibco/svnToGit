package com.tibco.cep.studio.dashboard.core.model.XSD.interfaces;

import java.util.List;

import com.tibco.cep.studio.dashboard.core.model.ISynValidationProvider;
import com.tibco.cep.studio.dashboard.core.model.XSD.modifiers.SynXSDSimpleTypeFinalModifier;

/**
 * @
 *
 */
public interface ISynXSDSimpleTypeDefinition extends ISynXSDTypeDefinition, ISynValidationProvider, ISynXSDElementContent {

    /**
     * Returns A List of SynXSDSimpleTypeFinalModifier
     *
     * @return
     */
    public List<SynXSDSimpleTypeFinalModifier> getFinalModifiers();

    public void addFinalModifier(SynXSDSimpleTypeFinalModifier finalModifier);

    public void removeFinalModifier(SynXSDSimpleTypeFinalModifier finalModifier);

    /**
     *
     * @return ISynXSDSimpleType An instance of ISynXSDSimpleType that is the
     *         base for this type. Can be null
     *
     */
    public ISynXSDSimpleTypeDefinition getBase();

    public void setBase(ISynXSDSimpleTypeDefinition base);

    /**
     *
     * @return List A List of instances of ISynXSDSimpleTypeContent that server
     *         as the content of the simple type. Can be null
     */
    public List<ISynXSDSimpleTypeContent> getContents();

    public void addContent(ISynXSDSimpleTypeContent content);

    public void removeContent(ISynXSDSimpleTypeContent content);

    /**
     * Returns the ultimate ISynXSDAtomicSimpleTypeDefinition that is at the
     * root of every simple type
     *
     * @return
     */
    public ISynXSDAtomicSimpleTypeDefinition getAtomicTypeDefinition();

    public boolean isNullable();

    public void setNullable(boolean value);

}
