package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class HexLengthXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = TibExtFunctions.makeName("hex-length");

    HexLengthXFunction()
    {
        super(NAME,SMDT.DOUBLE,SMDT.HEX_BINARY);
    }
}
