/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * Returns the current-dateTime (as in right NOW!).
 */
public class CurrentDateTimeXFunction extends DefaultLastArgRequiredXFunction
{
    public static final ExpandedName NAME = TibXPath20Functions.makeName("current-dateTime");

    public CurrentDateTimeXFunction()
    {
        super(NAME,SMDT.STRING);
    }
}
