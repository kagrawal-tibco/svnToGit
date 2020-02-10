package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * Date: Mar 19, 2008
 */
public class KeyXFunction extends DefaultLastArgRequiredXFunction
{
    public static final ExpandedName NAME = TibXPath20Functions.makeName("key");

    public KeyXFunction()
    {
        super(NAME, SMDT.REPEATING_ANY_ELEMENT, SMDT.STRING, SMDT.NODE);
    }

}
