package com.tibco.cep.mapper.xml.xdata.xpath;

import java.util.Iterator;

import com.tibco.cep.mapper.xml.xdata.xpath.func.XFunction;

/**
 * A set of xpath functions in a given namespace.
 * @author Bill Eidson
 */
public interface FunctionNamespace
{
    /**
     * If this namespace failed to load, for whatever, reason, returns the error associated with the failure.
     * @return The load error message, or null if none.
     */
    String getLoadErrorMessage();

    /**
     * Gets a 'recommended' prefix for this namespace (of course there's no obligation to actually use it).
     * @return The suggested prefix, or null to indicate no recommendation.
     */
    String getSuggestedPrefix();

    /**
     * True for 'native', built-in functions, the exact interpretation of built-in is implementation dependent,
     * however, xpath built-in functions are certainly built-in.
     */
    boolean isBuiltIn();

    /**
     * Gets the namespace URI for this function namespace.
     * @return The namespace URI string.
     */
    String getNamespaceURI();

    /**
     * Gets all of the functions in this namespace.
     * @return An iterator of {@link XFunction}, never null.
     */
    Iterator getFunctions();

    /**
     * Gets the function for this ncName.
     * @param ncName The NCName function name to look up.
     * @return The function, or null if no matching name.
     */
    XFunction get(String ncName);
}

