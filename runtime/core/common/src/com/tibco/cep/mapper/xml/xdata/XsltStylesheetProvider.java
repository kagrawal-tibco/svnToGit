/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata;

import java.util.Iterator;

import com.tibco.cep.mapper.xml.xdata.xpath.XsltStylesheetDataModel;
import com.tibco.cep.mapper.xml.xdata.xpath.XsltTemplateResolver;

/**
 * Implements the cachingloading operations for Xslt.
 */
public interface XsltStylesheetProvider
{
    /**
     * Resets the internal data structures so next time we access it gets rebuilt.
     */
    public void reset();

    /**
     * Gets the XSLTScriptDataModel for a given XSLT "src" attribute name.
     * @param name fully qualified java name
     */
    public XsltStylesheetDataModel getXsltStylesheetDataModel(String location);

    /**
     * Returns an iterator of XsltStylesheetDataModels.
     * @return
     */
    public Iterator getStylesheets();

    /**
     * Gets the template resolver.
     * @return XsltTemplateResolver
     */
    public XsltTemplateResolver getTemplateResolver();
}
