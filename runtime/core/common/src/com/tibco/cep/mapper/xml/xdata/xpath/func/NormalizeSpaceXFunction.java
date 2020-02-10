package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class NormalizeSpaceXFunction extends DefaultLastArgOptionalXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("normalize-space");

    NormalizeSpaceXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.STRING);
    }

    public boolean isIndependentOfFocus(int numArgs)
    {
        if (numArgs==0)
        {
            return false;
        }
        return true;
    }
}
