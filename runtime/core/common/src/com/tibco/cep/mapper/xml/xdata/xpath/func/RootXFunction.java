package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * Date: Mar 18, 2008
 */
public class RootXFunction extends DefaultLastArgOptionalXFunction
{
    public static final ExpandedName NAME = TibXPath20Functions.makeName("root");

    public RootXFunction()
    {
        super(NAME, SMDT.NODE, SMDT.NODE);
    }
}
