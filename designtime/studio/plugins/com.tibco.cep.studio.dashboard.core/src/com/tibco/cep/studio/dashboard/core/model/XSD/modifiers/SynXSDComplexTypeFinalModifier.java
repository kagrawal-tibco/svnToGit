package com.tibco.cep.studio.dashboard.core.model.XSD.modifiers;

/**
 * An enum for the {final} modifier of a complex type
 */
public class SynXSDComplexTypeFinalModifier extends AbstractSynXSDFinalModifier {

    /**
     * 
     */
    private static final long serialVersionUID = 4881775932101588214L;

	/**
     * Disallow further extension of this type
     */
    public static final SynXSDComplexTypeFinalModifier finalExtension = new SynXSDComplexTypeFinalModifier("extension");

    /**
     * Disallow further restriction of this type
     */
    public static final SynXSDComplexTypeFinalModifier finalRestriction = new SynXSDComplexTypeFinalModifier("restriction");

    protected SynXSDComplexTypeFinalModifier(String value) {
        super(value);
        setEnums(new SynXSDComplexTypeFinalModifier[] { finalExtension, finalRestriction });
    }

}