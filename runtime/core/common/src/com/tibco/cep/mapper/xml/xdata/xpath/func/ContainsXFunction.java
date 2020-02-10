package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class ContainsXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("contains");

    public ContainsXFunction()
    {
        super(NAME,SMDT.BOOLEAN,SMDT.STRING,SMDT.STRING);
    }
}
