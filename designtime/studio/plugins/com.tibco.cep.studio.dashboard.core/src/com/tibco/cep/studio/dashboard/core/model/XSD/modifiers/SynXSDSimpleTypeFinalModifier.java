package com.tibco.cep.studio.dashboard.core.model.XSD.modifiers;

/**
 * An enum for the {final} modifier of a simple type
 */
public class SynXSDSimpleTypeFinalModifier extends AbstractSynXSDFinalModifier {

    /**
     * 
     */
    private static final long serialVersionUID = -6235574629459867119L;

	/**
     * Disallow further extension of this type
     */
    public static final SynXSDSimpleTypeFinalModifier finalExtension = new SynXSDSimpleTypeFinalModifier("extension");

    /**
     * Disallow further restriction of this type
     */
    public static final SynXSDSimpleTypeFinalModifier finalRestriction = new SynXSDSimpleTypeFinalModifier("restriction");

    /**
     * Disallow the use of this type as an itemType in a (list)SimpleType
     */
    public static final SynXSDSimpleTypeFinalModifier finalList = new SynXSDSimpleTypeFinalModifier("list");

    /**
     * Disallow the use of this type as a memberType in a (union)SimpleType
     */
    public static final SynXSDSimpleTypeFinalModifier finalUnion = new SynXSDSimpleTypeFinalModifier("union");

    protected SynXSDSimpleTypeFinalModifier(String value) {
        super(value);
        setEnums(new SynXSDSimpleTypeFinalModifier[] { finalExtension, finalRestriction, finalList, finalUnion });
    }

}