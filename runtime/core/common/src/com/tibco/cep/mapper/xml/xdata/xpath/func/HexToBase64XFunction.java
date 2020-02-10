package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class HexToBase64XFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = TibExtFunctions.makeName("hex-to-base64");

    HexToBase64XFunction()
    {
        super(NAME,SMDT.BASE64_BINARY,SMDT.HEX_BINARY);
    }
}
