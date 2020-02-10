package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class StringXFunction extends DefaultLastArgOptionalXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("string");

    public StringXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.ITEM);
    }

    public boolean isIndependentOfFocus(int numArgs)
    {
        if (numArgs==0)
        {
            // implicit.
            return false;
        }
        return true;
    }
}
