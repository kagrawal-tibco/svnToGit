package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class LocalNameXFunction extends AbstractNameXFunction
{
    public static final ExpandedName NAME = ExpandedName.makeName("local-name");

    public LocalNameXFunction()
    {
        super(NAME,SMDT.NCNAME);
    }
}
