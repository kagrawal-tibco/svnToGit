/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;



/**
 * Represents a XSLT statement that can appear in a template body.
 */
public abstract class TemplateContentBinding extends AbstractBinding
{
    public TemplateContentBinding(BindingElementInfo info)
    {
        super(info);
    }

    public TemplateContentBinding(BindingElementInfo info, String formula)
    {
        super(info, formula);
    }
}