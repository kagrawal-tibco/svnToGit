/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Formats the given XML time string and returns a formatted time.
 */
public class FormatTimeXFunction extends FormatDateBaseXFunction
{
    public static final ExpandedName NAME = TibExtFunctions.makeName("format-time");

    public FormatTimeXFunction()
    {
        super(NAME);
    }
}
