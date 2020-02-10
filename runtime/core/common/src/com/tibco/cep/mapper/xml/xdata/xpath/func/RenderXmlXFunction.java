package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * A function that renders an xml fragment.
 */
public final class RenderXmlXFunction extends DefaultXFunction
{
    private static final ExpandedName NAME = TibExtFunctions.makeName("render-xml");

    public RenderXmlXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.DOCUMENT_OR_ELEMENT_NODE,SMDT.BOOLEAN,SMDT.BOOLEAN);
    }

    public boolean getLastArgRepeats()
    {
        return false;
    }

    public int getMinimumNumArgs()
    {
        return 1;
    }
}
