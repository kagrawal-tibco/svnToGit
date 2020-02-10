package com.tibco.cep.mapper.xml.xdata.xpath;

import java.util.Iterator;

import com.tibco.xml.xquery.FunctionGroup;

/**
 * A set of extension functions. This class will act as a virtual singleton. There should
 * be only one instance of this Object per XmluiAgent.
 * @author Bill Eidson
 */
public interface FunctionResolver
{
    /**
     * Gets all the namespaces declared.
     * @return An iterator of {@link FunctionNamespace}.
     */
    Iterator getNamespaces();

    /**
     * Gets the {@link FunctionNamespace}.
     * @param namespaceURI The namespaceURI
     * @return The namespace or null if none is found.
     */
    FunctionNamespace getNamespace(String namespaceURI);

    /**
     * This is a temporary measure until the these two interfaces are married; allows getting as a FunctionGroup.
     * @return
     */
    FunctionGroup getAsFunctionGroup();
}

