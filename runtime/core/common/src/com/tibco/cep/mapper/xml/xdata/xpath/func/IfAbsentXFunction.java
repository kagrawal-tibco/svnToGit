package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class IfAbsentXFunction extends DefaultLastArgRequiredXFunction
{
    public static final ExpandedName NAME = ExpandedName.makeName(TibExtFunctions.NAMESPACE,"if-absent");

    IfAbsentXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.REPEATING_ITEM,SMDT.STRING);
    }
}
