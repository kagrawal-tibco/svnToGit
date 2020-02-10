package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * Undocumented for now.
 */
public final class TimestampXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = TibExtFunctions.makeName("timestamp");

    /**
     * For FunctionList
     */
    TimestampXFunction()
    {
        super(NAME,SMDT.STRING);
    }
}
