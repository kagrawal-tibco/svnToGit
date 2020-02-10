package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * Date: Mar 18, 2008
 */
public class GenerateIdXFunction extends DefaultLastArgOptionalXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("generate-id");

    public GenerateIdXFunction()
    {
        super(NAME, SMDT.STRING, SMDT.REPEATING_ANY_ELEMENT);
    }
}
