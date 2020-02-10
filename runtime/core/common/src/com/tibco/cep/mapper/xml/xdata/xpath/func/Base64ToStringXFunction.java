package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class Base64ToStringXFunction extends DefaultLastArgOptionalXFunction
{
    private static final ExpandedName NAME = TibExtFunctions.makeName("base64-to-string");

    Base64ToStringXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.STRING,SMDT.STRING);
    }
}
