package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class StartsWithXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("starts-with");

    /**
     * For FunctionList
     */
    StartsWithXFunction()
    {
        super(NAME,SMDT.BOOLEAN,SMDT.STRING,SMDT.STRING);
    }
}
