package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/*******************************************************************************
 * Copyright 2003 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */
public class TranslateTimeZoneXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName(TibExtFunctions.NAMESPACE,"translate-timezone");

    /**
     * For FunctionList
     */
    TranslateTimeZoneXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.STRING,SMDT.STRING);
    }
}
