// copyright 2001 TIBCO Software Inc

package com.tibco.cep.mapper.xml.nsutils;

/**
 * An entry in the import registry.
 */
public final class ImportRegistryEntry
{
    private final String m_location;
    private final String m_namespaceURI;

    /**
     * Constructs a new entry.
     * @param namespaceURI The namespace, cannot be null.
     * @param location The location or null to indicate no location (a zero length string is legal but not valid --- bad URL)
     */
    public ImportRegistryEntry(String namespaceURI, String location)
    {
        if (namespaceURI==null)
        {
            // according to the docs, above, this isn't allowed, so fail fast.
            // NoNamespace.URI doesn't make sense here; we need an actual namespace.  Allow empty string just for completeness,
            // but not null.
            throw new NullPointerException("Null namespace");
        }
        m_namespaceURI = namespaceURI;
        m_location = location;
    }

    public String getNamespaceURI()
    {
        return m_namespaceURI;
    }

    /**
     * Gets the location or null to indicate none.
     * @return The location or null to indicate none.
     */
    public String getLocation()
    {
        return m_location;
    }

    /**
     * Hashcode corresponds to {@link #equals}
     */
    public int hashCode()
    {
        return m_namespaceURI.hashCode() + (m_location!=null ? m_location.hashCode() : 0);
    }

    /**
     * Equals if the namespace & locations are the same.
     */
    public boolean equals(Object object)
    {
        if (!(object instanceof ImportRegistryEntry))
        {
            return false;
        }
        ImportRegistryEntry ie = (ImportRegistryEntry) object;
        if (!ie.m_namespaceURI.equals(m_namespaceURI))
        {
            return false;
        }
        if (ie.m_location==null)
        {
            return ie.m_location==m_location;
        }
        else
        {
            return ie.m_location.equals(m_location);
        }
    }


    public String toString()
    {
        if (m_location==null)
        {
            return "namespace=" + m_namespaceURI + ", no location.";
        }
        return "namespace=" + m_namespaceURI + ", location=" + m_location;
    }
}
