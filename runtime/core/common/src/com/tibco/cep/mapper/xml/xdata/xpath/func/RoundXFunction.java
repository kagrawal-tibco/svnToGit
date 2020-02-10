package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class RoundXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("round");

    public RoundXFunction()
    {
        super(NAME,SMDT.DOUBLE,SMDT.DOUBLE);
    }
}
