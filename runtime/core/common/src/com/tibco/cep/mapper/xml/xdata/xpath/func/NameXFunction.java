package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class NameXFunction extends AbstractNameXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("name");

    public NameXFunction()
    {
        super(NAME,SMDT.STRING);
    }
}
