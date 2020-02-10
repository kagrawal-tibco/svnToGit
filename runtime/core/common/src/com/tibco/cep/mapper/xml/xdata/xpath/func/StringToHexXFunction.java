package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class StringToHexXFunction extends DefaultLastArgOptionalXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName(TibExtFunctions.NAMESPACE,"string-to-hex");

    StringToHexXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.STRING,SMDT.STRING);
    }
}
