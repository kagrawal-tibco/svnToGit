package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * An XPath string function that takes n right-most characters from a string.
 */
public final class RightXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName(TibExtFunctions.NAMESPACE,"right");

    /**
     * For FunctionList
     */
    RightXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.STRING,SMDT.STRING);
    }
}
