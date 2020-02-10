package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.helpers.NoNamespace;

/**
 * These are extended XPath functions are in the working draft of the XPath 2.0 specification but are quite useful now.<br>
 * Currently, the namespace here is set to no-namespace & these are effectively merged with the 1.0 functions (which are also in the no namespace)
 */
public final class TibXPath20Functions
{
    /**
     * The namespace used in BW 1,2.
     */
    public static final String OLD_NAMESPACE = "http://www.w3.org/2001/12/xquery-functions";
    public static final String NAMESPACE = NoNamespace.URI;//XQueryFunctions.NAMESPACE;
    //public static final String SUGGESTED_PREFIX = "xf";

    /**
     * Makes a name in {@link #NAMESPACE}.
     * @param localName The local name.
     */
    public static ExpandedName makeName(String localName)
    {
        return ExpandedName.makeName(NAMESPACE,localName);
    }
}
