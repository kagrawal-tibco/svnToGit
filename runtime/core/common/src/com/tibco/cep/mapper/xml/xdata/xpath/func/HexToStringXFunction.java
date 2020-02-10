package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class HexToStringXFunction extends DefaultLastArgOptionalXFunction
{
    private static final ExpandedName NAME = TibExtFunctions.makeName("hex-to-string");

    HexToStringXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.HEX_BINARY,SMDT.STRING);
    }
}
