package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class PadFrontXFunction extends DefaultLastArgOptionalXFunction
{
    private static final ExpandedName NAME = TibExtFunctions.makeName("pad-front");

    /**
     * For FunctionList
     */
    PadFrontXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.STRING,SMDT.STRING,SMDT.STRING);
    }
}
