// copyright 2001 TIBCO Software Inc

package com.tibco.cep.mapper.xml.xdata;

import java.util.Iterator;

import com.tibco.xml.data.primitive.NamespaceContextRegistry;

/**
 * Needs renaming, refactoring.  This should really be a registry.
 * A interface to lookup namespace prefixes from a namespace and location.
 * Has also added special character marking
 * (Added entity referencing).
 */
public interface NamespaceMapper extends NamespaceContextRegistry
{
    /**
     * Returns the prefix to use.  Returns null if no prefix should be used (for whatever reason).
     * If namespaceURI is null, it will always return null.
     */
    public String registerOrGetPrefixForNamespaceURI(String namespaceURI);

    /**
     * Returns the prefix to use.  Returns null if no prefix should be used (for whatever reason).
     * If namespaceURI is null, it will always return null.
     */
    public String registerOrGetPrefixForNamespaceURI(String namespaceURI, String suggestedPrefix);

    public Object clone();

    public String getNamespaceURIForPrefix(String namespacePrefix) throws PrefixNotFoundException;

    public void removePrefix(String prefix);

    /**
     * Lets one associate a schemaLocation with a URI.  If the namespaceURI is null, it is equivalent to registerLocation(locationURI)
     * @deprecated Remove this, use ImportRegistry.
     */
    public void registerLocation(String namespaceURI, String locationURI);

    /**
     * Registers a location without a namespace.
     * @deprecated Remove this.
     */
    public void registerLocation(String locationURI);

    /**
     * Adds a namespaceURI/prefix pair to the namespace, if its not defined.
     * If it is defined, the prefix is not added, and false is returned.
     */
    public boolean addNamespaceURI(String prefix, String namespaceURI);

    /**
     * Returns an iterator of the namespace URI Strings.
     */
    public Iterator getNamespaceIterator();

    /**
     * Merges one namespace into this given namespace, by using addNamespaceURI.
     */
    public void mergeNamespaces(NamespaceMapper namespace);

    /**
     * Returns all of the locations registered without a namespaceURI (or registered with a null URI)
     */
    public Iterator getNoNamespaceLocations();
}
