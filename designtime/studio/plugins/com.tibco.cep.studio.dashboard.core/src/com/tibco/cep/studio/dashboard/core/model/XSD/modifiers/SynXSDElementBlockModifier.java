package com.tibco.cep.studio.dashboard.core.model.XSD.modifiers;

/**
 * An enum for the {block} modifier of an element
 */
public class SynXSDElementBlockModifier extends AbstractSynXSDBlockModifier {

    /**
     * 
     */
    private static final long serialVersionUID = -2809247030226846185L;

	/**
     * Disallow further extension of this element if it is used in a
     * substitution
     */
    public static final SynXSDElementBlockModifier blockExtension = new SynXSDElementBlockModifier("extension");

    /**
     * Disallow further restriction of this element if it is used in a
     * substitution
     */
    public static final SynXSDElementBlockModifier blockRestriction = new SynXSDElementBlockModifier("restriction");

    /**
     * Disallow the use of this element as a substitution
     */
    public static final SynXSDElementBlockModifier blockSubstitution = new SynXSDElementBlockModifier("sunstitution");

    /**
     * Disallow extension, restriction, and substitution
     */
    public static final SynXSDElementBlockModifier blockALL = new SynXSDElementBlockModifier("ALL");

    protected SynXSDElementBlockModifier(String value) {
        super(value);
        setEnums(new SynXSDElementBlockModifier[] { blockExtension, blockRestriction, blockSubstitution, blockALL });
    }

}