package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * Date: Mar 18, 2008
 */
public class NamespaceURIFromQNameXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("namespace-uri-from-QName");

    public NamespaceURIFromQNameXFunction()
    {
        super(NAME, SMDT.STRING, SMDT.QNAME);
    }

}
