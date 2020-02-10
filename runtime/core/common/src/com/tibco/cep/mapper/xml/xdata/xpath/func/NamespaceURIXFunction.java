package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class NamespaceURIXFunction extends AbstractNameXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("namespace-uri");

    public NamespaceURIXFunction()
    {
        super(NAME,SMDT.STRING);
    }
}
