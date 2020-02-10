package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * Date: Mar 18, 2008
 */
public class CompareXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("compare");

    public CompareXFunction()
    {
        super(NAME, SMDT.INTEGER, SMDT.STRING, SMDT.STRING);
    }
}
