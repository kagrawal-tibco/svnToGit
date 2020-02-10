package com.tibco.cep.studio.dashboard.core.model.XSD.modifiers;

/**
 * An enum for the {final} modifier of an element
 */
public class SynXSDElementFinalModifier extends AbstractSynXSDFinalModifier {

    /**
     * 
     */
    private static final long serialVersionUID = -4683160694578513675L;

	/**
     * Disallow further extension of this element
     */
    public static final SynXSDElementFinalModifier finalExtension = new SynXSDElementFinalModifier("extension");

    /**
     * Disallow further restriction of this element
     */
    public static final SynXSDElementFinalModifier finalRestriction = new SynXSDElementFinalModifier("restriction");

    /**
     * Disallow extension, restriction
     */
    public static final SynXSDElementFinalModifier finalALL = new SynXSDElementFinalModifier("ALL");

    protected SynXSDElementFinalModifier(String value) {
        super(value);
        setEnums(new SynXSDElementFinalModifier[] { finalExtension, finalRestriction, finalALL });
    }

}