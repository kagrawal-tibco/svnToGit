/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

public class CurrentDateTimeWithTimeZoneXFunction extends DefaultLastArgRequiredXFunction
{
    public static final ExpandedName NAME = TibExtFunctions.makeName("current-dateTime-timezone");

    public CurrentDateTimeWithTimeZoneXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.DOUBLE,SMDT.DOUBLE);
    }
}