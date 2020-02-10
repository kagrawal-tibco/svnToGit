package com.tibco.cep.mapper.xml.nsutils;

import java.util.HashMap;

import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.helpers.XMLNamespace;

/**
 * A happy-go-lucky interface for suggesting nice prefix names for namespaces.<br>
 * For example, a simple implementation might be return 'soap' for the SOAP namespace,
 * 'xsd' for the xml schema namespace, etc.
 */
public class DefaultNamespacePrefixRecommender implements NamespacePrefixRecommender
{
    private HashMap m_loaded = new HashMap();
    private String m_default;

    public static final NamespacePrefixRecommender INSTANCE = new NamespacePrefixRecommender()
    {
        public String suggestPrefixForNamespaceURI(String namespaceURI)
        {
            // hardcode for a few good ones:
            if (namespaceURI==null)
            {
                return null;
            }
            if (namespaceURI.equals(XSDL.NAMESPACE))
            {
                return "xsd";
            }
            if (namespaceURI.equals(XSDL.INSTANCE_NAMESPACE))
            {
                return "xsi";
            }
            if (namespaceURI.equals("http://www.w3.org/1999/XSL/Transform"))
            {
                return "xsl";
            }
            return null;
        }
    };

    public DefaultNamespacePrefixRecommender()
    {
    }

    public DefaultNamespacePrefixRecommender(String defaultPfx)
    {
        m_default = defaultPfx;
    }

    public DefaultNamespacePrefixRecommender(String defaultPfx, boolean addStandards)
    {
        m_default = defaultPfx;
        if (addStandards)
        {
            addStandardPrefixesRecommendations();
        }
    }

    /**
     * Adds the 'standard' prefix recommendations for 'xsd', 'xsi', 'xsl'.
     */
    public void addStandardPrefixesRecommendations()
    {
        // maybe add more like SOAP, WSDL, etc. here.
        addRecommendation(XSDL.INSTANCE_NAMESPACE,"xsi");
        addRecommendation(XSDL.NAMESPACE,"xsd");
        addRecommendation("http://www.w3.org/1999/XSL/Transform","xsl");
    }

    public String suggestPrefixForNamespaceURI(String namespaceURI)
    {
        if (XMLNamespace.URI.equals(namespaceURI))
        {
            throw new IllegalArgumentException("can't pass in xml here");
        }
        String specific = (String) m_loaded.get(namespaceURI);
        if (specific==null)
        {
            return m_default;
        }
        return specific;
    }

    /**
     * Adds a new recommended prefix for a namespace.<br>
     * If there is already a recommendation for that namespace, it will be overwritten by this call.
     * @param namespace The namespace to use.
     * @param nicePrefix The suggested prefix for the namespace.
     */
    public void addRecommendation(String namespace, String nicePrefix)
    {
        m_loaded.put(namespace,nicePrefix);
    }

    public void removeRecommendation(String namespace)
    {
        m_loaded.remove(namespace);
    }

    /**
     * Sets the 'default' prefix to recommend when there is no specific match.
     * @param prefix The default prefix.
     */
    public void setDefaultPrefix(String prefix)
    {
        m_default = prefix;
    }
}
