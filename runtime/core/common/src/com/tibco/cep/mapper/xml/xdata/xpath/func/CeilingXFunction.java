package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class CeilingXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("ceiling");

    public CeilingXFunction()
    {
        super(NAME,SMDT.DOUBLE,SMDT.DOUBLE);
    }
}
