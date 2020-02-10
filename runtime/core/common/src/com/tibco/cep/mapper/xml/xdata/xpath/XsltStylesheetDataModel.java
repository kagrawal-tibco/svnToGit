/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath;

import com.tibco.cep.mapper.xml.xdata.bind.StylesheetBinding;

public interface XsltStylesheetDataModel
{
    public String getXslt();

    /**
     * Sets the raw representation.  The parsed representation will be (lazily) rebuilt
     * from this.
     */
    public void setXslt(String xslt);

    /**
     * Indicates if the xslt can be parsed or not.
     */
    public boolean isValidXslt();

    /**
     * @return The parsed xslt or null if the xslt string representation could not be parsed.
     */
    public StylesheetBinding getParsedXslt();

    /**
     * Sets the parsed representation.  The string representation will be (lazily) rebuilt
     * from this.
     */
    public void setParsedXslt(StylesheetBinding sb);
}
