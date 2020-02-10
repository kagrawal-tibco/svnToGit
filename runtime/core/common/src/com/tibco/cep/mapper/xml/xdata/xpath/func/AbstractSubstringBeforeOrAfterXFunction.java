package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public abstract class AbstractSubstringBeforeOrAfterXFunction extends DefaultLastArgRequiredXFunction
{
    /**
     * For FunctionList
     */
    AbstractSubstringBeforeOrAfterXFunction(ExpandedName name)
    {
        super(name,SMDT.STRING,SMDT.STRING,SMDT.STRING);
    }
}
