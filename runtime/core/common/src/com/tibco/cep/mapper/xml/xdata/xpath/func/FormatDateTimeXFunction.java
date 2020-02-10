/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Formats the given XML dateTime string and returns a formatted dateTime.
 */
public class FormatDateTimeXFunction extends FormatDateBaseXFunction
{
    public static final ExpandedName NAME = TibExtFunctions.makeName("format-dateTime");

    public FormatDateTimeXFunction()
    {
        super(NAME);
    }
}
