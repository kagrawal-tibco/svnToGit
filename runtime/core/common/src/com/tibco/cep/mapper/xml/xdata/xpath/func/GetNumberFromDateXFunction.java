/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * Generic base class for the GetXXXFrom<XML Date/Time> function.
 */
public abstract class GetNumberFromDateXFunction extends DefaultLastArgRequiredXFunction
{
    /**
     * For FunctionList
     */
    protected GetNumberFromDateXFunction(ExpandedName name)
    {
        super(name,SMDT.DOUBLE,SMDT.STRING);
    }
}
