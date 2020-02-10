package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class SubstringXFunction extends DefaultLastArgOptionalXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("substring");

    /**
     * For FunctionList
     */
    SubstringXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.STRING,SMDT.DOUBLE,SMDT.DOUBLE);
    }
}
