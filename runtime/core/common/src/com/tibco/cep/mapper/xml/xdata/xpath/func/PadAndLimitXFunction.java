package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class PadAndLimitXFunction extends DefaultLastArgOptionalXFunction
{
    private static final ExpandedName NAME = TibExtFunctions.makeName("pad-and-limit");

    /**
     * For FunctionList
     */
    PadAndLimitXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.STRING,SMDT.STRING,SMDT.STRING);
    }
}
