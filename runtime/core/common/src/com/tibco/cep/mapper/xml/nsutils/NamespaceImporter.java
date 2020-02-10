package com.tibco.cep.mapper.xml.nsutils;

import com.tibco.xml.data.primitive.NamespaceContext;


/**
 * A specialized namespace context that knows how to register new namespace declarations.
 */
public interface NamespaceImporter extends NamespaceContext
{
    /**
     * First attempts to find an existing prefix for the namespace, or failing that, registers a new declaration.
     * @param namespace The namespace URI to lookup.
     * @return The prefix, never null, either found or newly added for that namespace.
     */
    String getOrAddPrefixForNamespaceURI(String namespace);

    /**
     * First attempts to find an existing prefix for the namespace, or failing that, registers a new declaration.
     * @param namespace The namespace URI to lookup.
     * @param suggestedPrefix In the event that a registration is required, the suggestedPrefix may be used as a
     * starting point for finding a unique prefix.  This argument can be null, if null, it will be ignored.
     * @return The prefix, never null, either found or newly added for that namespace.
     */
    String getOrAddPrefixForNamespaceURI(String namespace, String suggestedPrefix);
}
