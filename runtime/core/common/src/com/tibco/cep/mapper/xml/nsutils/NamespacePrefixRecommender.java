package com.tibco.cep.mapper.xml.nsutils;


/**
 * A happy-go-lucky interface for suggesting nice prefix names for namespaces.<br>
 * For example, a simple implementation might be return 'soap' for the SOAP namespace,
 * 'xsd' for the xml schema namespace, etc.
 */
public interface NamespacePrefixRecommender
{
    /**
     * Recommends a good prefix (or null if it can't get one) for the namespace.
     * @param namespaceURI The namespace, cannot be null or {@link NoNamespace#NoNamespace}.
     * It should not be called with the xml namespace, {@link com.tibco.xml.schema.helpers.XMLNamespace#URI), since that
     * namespace has a hard-coded prefix, {@link com.tibco.xml.schema.helpers.XMLNamespace#PREFIX).
     * @return A valid prefix or null if the implementation can't think of a good one.
     */
    String suggestPrefixForNamespaceURI(String namespaceURI);
}
