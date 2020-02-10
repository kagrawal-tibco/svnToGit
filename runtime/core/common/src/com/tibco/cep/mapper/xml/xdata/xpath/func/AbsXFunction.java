package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class AbsXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("abs");

    public AbsXFunction()
    {
        super(NAME,SMDT.ANY_NUMBER,SMDT.ANY_NUMBER);
    }
}
