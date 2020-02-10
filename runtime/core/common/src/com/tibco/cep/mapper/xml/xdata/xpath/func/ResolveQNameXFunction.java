package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * Date: Mar 18, 2008
 */
public class ResolveQNameXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("resolve-QName");

    public ResolveQNameXFunction()
    {
        super(NAME, SMDT.QNAME, SMDT.STRING, SMDT.ELEMENT_NODE);
    }
}
