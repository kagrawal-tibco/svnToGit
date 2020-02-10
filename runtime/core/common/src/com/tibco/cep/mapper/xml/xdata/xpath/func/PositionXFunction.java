package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class PositionXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("position");

    public PositionXFunction()
    {
        super(NAME,SMDT.DOUBLE);
    }

    public boolean isIndependentOfFocus(int numArgs)
    {
        // this IS the position function, so no, it isn't.
        return false;
    }
}
