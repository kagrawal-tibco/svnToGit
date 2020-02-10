package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class LowerCaseXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName(TibXPath20Functions.NAMESPACE,"lower-case");

    LowerCaseXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.STRING);
    }
}
