package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class TrimXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = TibExtFunctions.makeName("trim");

    /**
     * For FunctionList
     */
    TrimXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.STRING);
    }
}
