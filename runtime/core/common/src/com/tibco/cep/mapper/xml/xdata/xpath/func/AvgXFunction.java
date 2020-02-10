package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class AvgXFunction extends DefaultLastArgRequiredXFunction
{
    public static final ExpandedName NAME = ExpandedName.makeName(TibXPath20Functions.NAMESPACE, "avg");

    /**
     * For FunctionList
     */
    AvgXFunction()
    {
        super(NAME,SMDT.DOUBLE,SMDT.REPEATING_DOUBLE);
    }
}
