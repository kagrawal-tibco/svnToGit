package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * For 1 arg name functions.
 */
public final class IdXFunction extends DefaultLastArgRequiredXFunction
{
    public static final ExpandedName NAME = ExpandedName.makeName("id");

    /**
     * For function list only.
     */
    IdXFunction()
    {
        super(ExpandedName.makeName("id"),SMDT.PREVIOUS_ERROR, SMDT.REPEATING_NODE);
    }
}
