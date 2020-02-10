package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class ConcatXFunction extends DefaultXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("concat");

    /**
     * For FunctionList
     */
    ConcatXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.STRING,SMDT.STRING);
    }

    public boolean getLastArgRepeats()
    {
        // concat allows arbitrary # of arguments.
        return true;
    }

    public int getMinimumNumArgs()
    {
        return 2;
    }
}
