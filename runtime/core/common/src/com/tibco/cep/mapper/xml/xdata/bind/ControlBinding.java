/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;



/**
 * Represents an XSLT control structure in a binding.<br>
 * Does not actually add any additional functionality, just a classification.
 */
public abstract class ControlBinding extends TemplateContentBinding
{
    public ControlBinding(BindingElementInfo info)
    {
        super(info);
    }

    public ControlBinding(BindingElementInfo info, String formula)
    {
        super(info, formula);
    }
}