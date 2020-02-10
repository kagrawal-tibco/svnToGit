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
public class CreateDateXFunction extends DefaultLastArgRequiredXFunction
{
    public static final ExpandedName NAME = TibExtFunctions.makeName("create-date");

    public CreateDateXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.DOUBLE,SMDT.DOUBLE,SMDT.DOUBLE);
    }
}
