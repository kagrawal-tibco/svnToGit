package com.tibco.cep.studio.dashboard.core.model.XSD.components;

import com.tibco.cep.studio.dashboard.core.enums.AbstractSynEnum;


/**
 * An enum for the model group of a complex content
 */
public class SynXSDCompositor extends AbstractSynEnum {

    /**
     * 
     */
    private static final long serialVersionUID = 7360417981664095064L;

	/**
     * This is a sequence where all elements must be declared in the correct order
     */
    public static final SynXSDCompositor sequence = new SynXSDCompositor("sequence");

    /**
     * This is a choice group where only one of the elements may be declared
     */
    public static final SynXSDCompositor choice = new SynXSDCompositor("choice");

    /**
     * This is group where all elements must be declared but can be in any order
     */
    public static final SynXSDCompositor ALL = new SynXSDCompositor("ALL");

    protected SynXSDCompositor(String value) {
        super(value);
        setEnums(new SynXSDCompositor[] { sequence, choice, ALL });
    }

}