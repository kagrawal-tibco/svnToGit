/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Parses the given formatted date-time string and returns an XML dateTime.
 */
public class ParseDateXFunction extends ParseTimeBaseXFunction
{
    private static final ExpandedName NAME = TibExtFunctions.makeName("parse-date");

    public ParseDateXFunction()
    {
        super(NAME);
    }
}
