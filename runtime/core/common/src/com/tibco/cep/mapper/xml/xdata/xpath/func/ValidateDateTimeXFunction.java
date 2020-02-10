/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * A tibco extension function to add days to a date.
 */
public final class ValidateDateTimeXFunction extends DefaultLastArgRequiredXFunction
{
    public static final ExpandedName NAME = ExpandedName.makeName(TibExtFunctions.NAMESPACE,"validate-dateTime");

    /**
     * For FunctionList
     */
    ValidateDateTimeXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.STRING,SMDT.STRING);
    }
}
