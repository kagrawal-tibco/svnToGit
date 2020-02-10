package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class FloorXFunction extends DefaultLastArgRequiredXFunction
{
    public static final ExpandedName NAME = ExpandedName.makeName("floor");

    public FloorXFunction()
    {
        super(NAME, SMDT.DOUBLE, SMDT.DOUBLE);
    }
}
