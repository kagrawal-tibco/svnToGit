package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class TokenizeXFunction extends DefaultLastArgOptionalXFunction
{
    private static final ExpandedName NAME = TibExtFunctions.makeName("tokenize");
    private static final SmSequenceType RETURN_TYPE = SMDT.REPEATING_TEXT_NODE;

    /**
     * For FunctionList
     */
    TokenizeXFunction() 
    {
        super(NAME,RETURN_TYPE,SMDT.STRING,SMDT.STRING);
    }
}
