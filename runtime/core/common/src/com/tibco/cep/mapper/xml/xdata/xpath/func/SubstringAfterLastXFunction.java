package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 */
public final class SubstringAfterLastXFunction extends AbstractSubstringBeforeOrAfterXFunction
{
    private static final ExpandedName NAME = TibExtFunctions.makeName("substring-after-last");

    /**
     * For FunctionList
     */
    SubstringAfterLastXFunction()
    {
        super(NAME);
    }
}
