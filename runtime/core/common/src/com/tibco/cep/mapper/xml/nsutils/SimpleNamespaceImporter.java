// copyright 2001 TIBCO Software Inc

package com.tibco.cep.mapper.xml.nsutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.tibco.xml.data.primitive.NamespaceContext;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.helpers.NoNamespace;
import com.tibco.xml.schema.helpers.XMLNamespace;

/**
 * A simple namespace importer that uses {@link NamespacePrefixRecommender} to generate prefixes when required.
 */
public class SimpleNamespaceImporter implements NamespaceImporter {
    private HashMap mNamespaceToPrefix = new HashMap();
    private HashMap mPrefixToNamespace = new HashMap();
    private ArrayList mNamespacesInOrder = new ArrayList();
    private NamespacePrefixRecommender m_namespacePrefixRecommender;

    public SimpleNamespaceImporter()
    {
        this("ns");
    }

    public SimpleNamespaceImporter(String prefixBase)
    {
        this(new DefaultNamespacePrefixRecommender(prefixBase,true));
    }

    public SimpleNamespaceImporter(NamespacePrefixRecommender namespacePrefixRecommender)
    {
        if (namespacePrefixRecommender==null)
        {
            throw new NullPointerException("Null recommender, the recommender recommends that you reconsider.");
        }
        m_namespacePrefixRecommender = namespacePrefixRecommender;
        addXMLNamespace();
    }

    public String getOrAddPrefixForNamespaceURI(String namespaceURI, String suggestedPrefix)
    {
        if (NoNamespace.isNoNamespaceURI(namespaceURI))
        {
            return ""; // no prefix.
        }
        if (namespaceURI.equals(XMLNamespace.URI))
        {
            return XMLNamespace.PREFIX;
        }
        String existing = (String) mNamespaceToPrefix.get(namespaceURI);
        if (existing!=null)
        {
            return existing;
        }
        String prefix;
        if (suggestedPrefix==null)
        {
            prefix = m_namespacePrefixRecommender.suggestPrefixForNamespaceURI(namespaceURI);
        }
        else
        {
            prefix = suggestedPrefix;
        }
        if (prefix == null)
        {
            prefix = "ns";
        }
        String tryPrefix = prefix;
        for (int ct=1;;ct++)
        {
            if (!mPrefixToNamespace.containsKey(prefix))
            {
                break;
            }
            prefix = generateNextPrefix(tryPrefix,ct);
        }
        addNamespaceURI(prefix,namespaceURI);
        return prefix;
    }

    public String getOrAddPrefixForNamespaceURI(String namespace)
    {
        return getOrAddPrefixForNamespaceURI(namespace,null);
    }

    public void removePrefix(String prefix)
    {
        String ns = (String) mPrefixToNamespace.get(prefix);
        if (ns==null)
        {
            return;
        }
        mNamespacesInOrder.remove(prefix);
        mPrefixToNamespace.remove(prefix);
        mNamespaceToPrefix.remove(ns);
    }

    /**
     * Adds XMLSchema-instance namespace, attempting to use 'xsi'
     */
    public void addXSINamespace()
    {
        getOrAddPrefixForNamespaceURI(XSDL.INSTANCE_NAMESPACE,"xsi");
    }

    /**
     * Adds XMLSchema namespace, attempting to use 'xsd'
     */
    public void addXSDNamespace()
    {
        getOrAddPrefixForNamespaceURI(XSDL.NAMESPACE, "xsd");
    }

    /**
     * Added by default.
     */
    private void addXMLNamespace()
    {
        String pfx = XMLNamespace.PREFIX;
        String ns = XMLNamespace.URI;

        // Register here but not in namespaces in order; xml is always there.
        mNamespaceToPrefix.put(ns,pfx);
        mPrefixToNamespace.put(pfx,ns);
    }

    public Iterator getNamespaceIterator()
    {
        return mNamespacesInOrder.iterator();
    }

    public String getPrefixForNamespaceURI(String namespaceURI) throws NamespaceToPrefixResolver.NamespaceNotFoundException {
        if (namespaceURI==null || namespaceURI.equals("")) {
            return "";
        }
        String str = (String) mNamespaceToPrefix.get(namespaceURI);
        if (str!=null) {
            return str;
        }
/*        if (mAutoRegister) {
            return registerOrGetPrefixForNamespaceURI(namespaceURI);
        }*/
        throw new NamespaceToPrefixResolver.NamespaceNotFoundException(namespaceURI);
    }

    private final String generateNextPrefix(String starting, int ct)
    {
        String prefix;
        if (ct==0)
        {
            prefix = starting;
        }
        else
        {
            prefix = starting + (ct+1);
        }
        return prefix;
    }

    private void addNamespaceURI(String prefix, String namespaceURI)
    {
        mNamespaceToPrefix.put(namespaceURI,prefix);
        mPrefixToNamespace.put(prefix, namespaceURI);
        mNamespacesInOrder.add(namespaceURI);
    }

    public String getNamespaceURIForPrefix(String namespacePrefix) throws PrefixNotFoundException{
        if (namespacePrefix==null || namespacePrefix.length() == 0)
        {
            return null;
        }
        String existing = (String) mPrefixToNamespace.get(namespacePrefix);
        if (existing==null)
        {
            throw new PrefixNotFoundException(namespacePrefix);
        }
        return existing;
    }

    public String getNamespaceURI(String namespacePrefix)
    {
        if (namespacePrefix==null || namespacePrefix.length() == 0) {
            return null;
        }
        String existing = (String) mPrefixToNamespace.get(namespacePrefix);
        return existing;
    }

    /**
     * Adds all the declarations to this namespace (if there is a prefix conflict, prefix names will be changed
     * according to {@link #getOrAddPrefixForNamespaceURI}.
     * @param context
     */
    public void mergeNamespaces(NamespaceContext context, boolean localOnly)
    {
        Iterator iter;
        if (localOnly)
        {
            iter = context.getLocalPrefixes();
        }
        else
        {
            iter = context.getPrefixes();
        }
        while (iter.hasNext())
        {
            String uri = (String) iter.next();
            try
            {
                String prefix = context.getNamespaceURIForPrefix(uri);
                getOrAddPrefixForNamespaceURI(uri, prefix);
            } catch (PrefixToNamespaceResolver.PrefixNotFoundException nnfe)
            {
                throw new RuntimeException("Internal error, finding: " + nnfe.getPrefix());
            }
        }
    }

    public Iterator getLocalPrefixes()
    {
        return getPrefixes();
    }

    public Iterator getPrefixes()
    {
        ArrayList ret = new ArrayList();
        for (int i=0;i<mNamespacesInOrder.size();i++)
        {
            Object pfx = mNamespaceToPrefix.get(mNamespacesInOrder.get(i));
            ret.add(pfx);
        }
        return ret.iterator();
    }

    public NamespaceContext snapshot()
    {
        SimpleNamespaceImporter sni = new SimpleNamespaceImporter(m_namespacePrefixRecommender);
        sni.mergeNamespaces(this,false);
        return sni;
    }

    /**
     * Formats the namespace part.
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<mNamespacesInOrder.size();i++)
        {
            String ns = (String) mNamespacesInOrder.get(i);
            String pfx = (String) mNamespaceToPrefix.get(ns);
            sb.append(pfx + "=" + ns);
            sb.append('\n');
        }
        return sb.toString();
    }
}

