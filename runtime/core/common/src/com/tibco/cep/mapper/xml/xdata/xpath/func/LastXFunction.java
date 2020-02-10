package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class LastXFunction extends DefaultLastArgRequiredXFunction
{
    public static final ExpandedName NAME = ExpandedName.makeName("last");

    public LastXFunction()
    {
        super(NAME,SMDT.DOUBLE);
    }

    public boolean isIndependentOfFocus(int numArgs)
    {
        // see docs on XFunction, last() is part of this check.
        return false;
    }
}
