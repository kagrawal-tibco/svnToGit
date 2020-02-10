package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class Base64LengthXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = TibExtFunctions.makeName("base64-length");

    Base64LengthXFunction()
    {
        // in 2.0, arg is base64-binary.
        super(NAME,SMDT.DOUBLE,SMDT.STRING);
    }
}
