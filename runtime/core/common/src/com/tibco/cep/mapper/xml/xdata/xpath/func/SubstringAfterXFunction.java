package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 */
public final class SubstringAfterXFunction extends AbstractSubstringBeforeOrAfterXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("substring-after");

    /**
     * For FunctionList
     */
    SubstringAfterXFunction()
    {
        super(NAME);
    }
}
