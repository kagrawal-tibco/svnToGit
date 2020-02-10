package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * A round function where the # of fraction digits are specified.
 */
public final class StringRoundFractionXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName(TibExtFunctions.NAMESPACE,"string-round-fraction");

    public StringRoundFractionXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.DOUBLE,SMDT.DOUBLE);
    }
}
