package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 */
public final class SubstringBeforeLastXFunction extends AbstractSubstringBeforeOrAfterXFunction
{
    private static final ExpandedName NAME = TibExtFunctions.makeName("substring-before-last");

    /**
     * For FunctionList
     */
    SubstringBeforeLastXFunction()
    {
        super(NAME);
    }
}
