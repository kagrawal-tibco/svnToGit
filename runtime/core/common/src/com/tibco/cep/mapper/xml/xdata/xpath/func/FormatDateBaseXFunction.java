/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * Parses the given formatted date-time string and returns an XML dateTime.
 */
public abstract class FormatDateBaseXFunction extends DefaultLastArgRequiredXFunction
{
    public FormatDateBaseXFunction(ExpandedName name)
    {
        super(name,SMDT.STRING,SMDT.STRING,SMDT.STRING);
    }
}
