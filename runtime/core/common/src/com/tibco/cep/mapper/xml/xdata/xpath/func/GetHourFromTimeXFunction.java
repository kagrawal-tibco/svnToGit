/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * XPath 2.0 function which extracts the centuary from a dateTime (XML Schema Part 2 sec. 3.2.7.1) formatted string.
 * Typically of the form: CCYY-MM-DDThh:mm:ss
 */
public final class GetHourFromTimeXFunction extends GetHourXFunction
{
    public static final ExpandedName NAME = ExpandedName.makeName(TibExtFunctions.NAMESPACE,"get-hours-from-time");

    /**
     * For FunctionList
     */
    GetHourFromTimeXFunction()
    {
        super(NAME);
    }
}
