package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class StringLengthXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("string-length");

    /**
     * For FunctionList
     */
    StringLengthXFunction()
    {
        super(NAME,SMDT.DOUBLE,SMDT.STRING);
    }
}
