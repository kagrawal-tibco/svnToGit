package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * A round function where the # of fraction digits are specified.
 */
public final class RoundFractionXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = TibExtFunctions.makeName("round-fraction");

    public RoundFractionXFunction()
    {
        super(NAME,SMDT.DOUBLE,SMDT.DOUBLE,SMDT.DOUBLE);
    }

}
