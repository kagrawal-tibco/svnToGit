package com.tibco.cep.studio.dashboard.core.model.XSD.modifiers;

/**
 * An enum for the {block} modifier of an type
 */
public class SynXSDComplexTypeBlockModifier extends AbstractSynXSDBlockModifier {

    /**
     * 
     */
    private static final long serialVersionUID = -5993797858370426161L;

	/**
     * Disallow further extension of this element if it is used in a
     * substitution
     */
    public static final SynXSDComplexTypeBlockModifier blockExtension = new SynXSDComplexTypeBlockModifier("extension");

    /**
     * Disallow further restriction of this element if it is used in a
     * substitution
     */
    public static final SynXSDComplexTypeBlockModifier blockRestriction = new SynXSDComplexTypeBlockModifier("restriction");

    /**
     * Disallow extension, restriction, and substitution
     */
    public static final SynXSDComplexTypeBlockModifier blockALL = new SynXSDComplexTypeBlockModifier("ALL");

    protected SynXSDComplexTypeBlockModifier(String value) {
        super(value);
        setEnums(new SynXSDComplexTypeBlockModifier[] { blockExtension, blockRestriction, blockALL });
    }

}