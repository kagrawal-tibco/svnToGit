package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class XorXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = TibExtFunctions.makeName("xor");

    XorXFunction()
    {
        super(NAME,SMDT.BOOLEAN,SMDT.BOOLEAN,SMDT.BOOLEAN);
    }
}
