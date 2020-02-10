package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class EndsWithXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName(TibXPath20Functions.NAMESPACE,"ends-with");

    /**
     * For FunctionList
     */
    EndsWithXFunction()
    {
        super(NAME,SMDT.BOOLEAN,SMDT.STRING,SMDT.STRING);
    }
}
