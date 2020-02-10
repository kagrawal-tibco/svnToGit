package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * This is the namespace for tibco custom xslt functions.
 */
public final class TibExtFunctions
{
    public static final String NAMESPACE = "http://www.tibco.com/bw/xslt/custom-functions";
    public static final String SUGGESTED_PREFIX = "tib";

    /**
     * Makes a name in {@link #NAMESPACE}.
     * @param localName The local name part of the name.
     * @return The expanded name where namespace is {@link #NAMESPACE} and local name is the local name.
     */
    public static final ExpandedName makeName(String localName)
    {
        return ExpandedName.makeName(NAMESPACE,localName);
    }
}
