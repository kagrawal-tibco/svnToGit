/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * A tibco extension function to add days to a date.
 */
public final class CompareDateXFunction extends DefaultLastArgRequiredXFunction
{
    public static final ExpandedName NAME = ExpandedName.makeName(TibExtFunctions.NAMESPACE,"compare-date");

    /**
     * For FunctionList
     */
    CompareDateXFunction()
    {
        super(NAME,SMDT.DOUBLE,new SmSequenceType[] {SMDT.STRING,SMDT.STRING});
    }
}
