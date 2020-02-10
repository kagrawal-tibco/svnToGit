package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class TranslateXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("translate");

    TranslateXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.STRING,SMDT.STRING,SMDT.STRING);
    }
}
