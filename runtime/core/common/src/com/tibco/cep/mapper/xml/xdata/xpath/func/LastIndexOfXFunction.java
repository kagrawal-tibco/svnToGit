package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * A java-like index-of string function.
 */
public final class LastIndexOfXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = TibExtFunctions.makeName("last-index-of");

    /**
     * For FunctionList
     */
    LastIndexOfXFunction()
    {
        super(NAME,SMDT.DOUBLE,SMDT.STRING,SMDT.STRING);
    }
}
