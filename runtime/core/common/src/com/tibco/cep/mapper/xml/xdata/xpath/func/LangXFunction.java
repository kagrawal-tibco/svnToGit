package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * The implementation (design and runtime) of the XPath 1.0 function 'lang'.
 */
public final class LangXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("lang");

    /**
     * For FunctionList
     */
    LangXFunction()
    {
        super(NAME,SMDT.BOOLEAN,SMDT.REPEATING_NODE);
    }

    public boolean hasNonSubTreeTraversal()
    {
        return true; // may climb parent tree.
    }

    public boolean isIndependentOfFocus(int numArgs)
    {
        return false; // uses dot
    }
}
