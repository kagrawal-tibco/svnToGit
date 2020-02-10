package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 */
public final class SubstringBeforeXFunction extends AbstractSubstringBeforeOrAfterXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("substring-before");

    /**
     * For FunctionList
     */
    SubstringBeforeXFunction()
    {
        super(NAME);
    }
}
