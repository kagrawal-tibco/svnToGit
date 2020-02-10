/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * Parses the given formatted date-time string and returns an XML dateTime.
 */
public class CreateTimeXFunction extends DefaultLastArgOptionalXFunction
{
    public static final ExpandedName NAME = TibExtFunctions.makeName("create-time");

    public CreateTimeXFunction()
    {
        super(NAME,SMDT.STRING,new SmSequenceType[] {SMDT.DOUBLE,SMDT.DOUBLE,SMDT.DOUBLE,SMDT.DOUBLE});
    }
}
