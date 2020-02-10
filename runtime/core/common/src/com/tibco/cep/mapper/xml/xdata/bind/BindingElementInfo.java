package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.ArrayList;
import java.util.Iterator;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;

/**
 * The additional 'standard' information for each binding; contains namespace decls and extra attributes.
 */
public final class BindingElementInfo
{
    private final NamespaceDeclaration[] mNamespaceDeclarations;
    private final Attribute[] mAttributes;
    //private final NamespaceImporter mNamespaceMapper;
    public static final Attribute[] EMPTY_ATTRIBUTES = new Attribute[0];
    public static final NamespaceDeclaration[] EMPTY_NAMESPACE_DECLARATIONS = new NamespaceDeclaration[0];

    public static final BindingElementInfo EMPTY_INFO = new BindingElementInfo(new NamespaceDeclaration[0],new Attribute[0]);

    static public class NamespaceDeclaration {
        private final String mPrefix;
        private final String mNamespace;

        public NamespaceDeclaration(String pfx, String namespace) {
            mPrefix = pfx.intern();
            mNamespace = namespace.intern();
        }

        public String getPrefix() {
            return mPrefix;
        }

        public String getNamespace() {
            return mNamespace;
        }

        /**
         * For diagnostic/debugging purposes only.
         */
        public String toString()
        {
            return "xmlns:" + mPrefix + "=\"" + mNamespace + "\"";
        }
    }

    static public class Attribute {
        private final ExpandedName mName;
        private final String mValue;

        public Attribute(ExpandedName name, String value) {
            mName = name;
            mValue = value;
        }

        public ExpandedName getName() {
            return mName;
        }

        public String getValue() {
            return mValue;
        }
    }

    public BindingElementInfo(NamespaceDeclaration[] namespaces) {
        this(namespaces,EMPTY_ATTRIBUTES);
    }

    /*public BindingElementInfo(NamespaceImporter namespaceMapper) {
        mNamespaceMapper = namespaceMapper;
        mNamespaceDeclarations = EMPTY_NAMESPACE_DECLARATIONS;
        mAttributes = EMPTY_ATTRIBUTES;
    }*/

    /**
     * Copies namespaces from the importer to the info (does not reference).
     * @param namespaceMapper
     */
    public BindingElementInfo(NamespaceContextRegistry namespaceMapper)
    {
        Iterator i = namespaceMapper.getPrefixes();
        ArrayList nsa = new ArrayList();
        while (i.hasNext())
        {
            String p = (String) i.next();
            try
            {
                String ns = namespaceMapper.getNamespaceURIForPrefix(p);
                nsa.add(new NamespaceDeclaration(p,ns));
            }
            catch (PrefixToNamespaceResolver.PrefixNotFoundException e)
            {
                // eat it, can't happen.
                e.printStackTrace(System.err);
            }
        }
        //mNamespaceMapper = null;
        mNamespaceDeclarations = (NamespaceDeclaration[]) nsa.toArray(new NamespaceDeclaration[nsa.size()]);
        mAttributes = EMPTY_ATTRIBUTES;
    }

    public BindingElementInfo(NamespaceDeclaration[] namespaces, Attribute[] extraAttributes) {
        mNamespaceDeclarations = namespaces;
        if (namespaces==null) {
            throw new NullPointerException("null namespaces");
        }
        mAttributes = extraAttributes;
        if (extraAttributes==null) {
            throw new NullPointerException("null extra attributes");
        }
        //mNamespaceMapper = null;
    }

    public NamespaceDeclaration[] getNamespaceDeclarations() {
        return mNamespaceDeclarations;
    }

    public BindingElementInfo createWithNamespaceDeclarations(NamespaceDeclaration[] nd) {
        return new BindingElementInfo(nd,mAttributes);
    }

    public BindingElementInfo createWithAdditionalAttribute(Attribute attr)
    {
        Attribute[] a = new Attribute[mAttributes.length+1];
        for (int i=0;i<mAttributes.length;i++)
        {
            a[i] = mAttributes[i];
        }
        a[a.length-1] = attr;
        return new BindingElementInfo((NamespaceDeclaration[])mNamespaceDeclarations.clone(),a);
    }

    public Attribute getAdditionalAttribute(ExpandedName name)
    {
        for (int i=0;i<mAttributes.length;i++)
        {
            if (mAttributes[i].getName().equals(name))
            {
                return mAttributes[i];
            }
        }
        return null;
    }

    public BindingElementInfo createWithoutAdditionalAttribute(ExpandedName name)
    {
        ArrayList attrs = new ArrayList();
        for (int i=0;i<mAttributes.length;i++)
        {
            if (!mAttributes[i].getName().equals(name))
            {
                attrs.add(mAttributes[i]);
            }
        }
        Attribute[] ats = (Attribute[]) attrs.toArray(EMPTY_ATTRIBUTES);
        return new BindingElementInfo(mNamespaceDeclarations,ats);
    }

    public Attribute[] getAdditionalAttributes()
    {
        return mAttributes;
    }
}
