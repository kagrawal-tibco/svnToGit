/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Formats the given XML date string and returns a formatted date.
 */
public class FormatDateXFunction extends FormatDateBaseXFunction
{
    public static final ExpandedName NAME = TibExtFunctions.makeName("format-date");

    public FormatDateXFunction()
    {
        super(NAME);
    }
}
