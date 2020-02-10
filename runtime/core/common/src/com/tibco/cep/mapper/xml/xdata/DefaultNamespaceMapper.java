// copyright 2001 TIBCO Software Inc

package com.tibco.cep.mapper.xml.xdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.bind.ReadFromXSLT;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContext;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * A simple namespace mapper that uses a base prefix & appends numbers for the 2nd, 3rd, etc. namespaces
 */
public class DefaultNamespaceMapper implements NamespaceMapper {

    static final private String EMPTY_STRING = "";
    static final private String DEFAULT_NAMESPACE_PREFIX = EMPTY_STRING;
    private int mCount;
    private String mStartingName;
    private HashMap mNamespaceToPrefix = new HashMap();
    private HashMap mPrefixToNamespace = new HashMap();
    private HashSet mNoNamespaceLocations = new HashSet();
    private ArrayList mNamespacesInOrder = new ArrayList();
    private HashMap mLocationMap = new HashMap();

    //private HashSet mUsedCharacters = new HashSet();
    //private ArrayList mUsedCharactersInOrder = new ArrayList();
    private boolean mAutoRegister;
    //private CharacterEntityMap mEntityMap = CharacterEntityMap.XHTML_MAPS;

    public DefaultNamespaceMapper() {
        this("pfx");
    }

    public DefaultNamespaceMapper(boolean autoRegister) {
        this("ns");
        mAutoRegister = autoRegister;
    }

    public DefaultNamespaceMapper(String prefixBase) {
        mStartingName = prefixBase;
        addXMLNamespace();
    }

    public DefaultNamespaceMapper(NamespaceMapper previous) {
        mStartingName = "pfx";
        addXMLNamespace();
        if (previous!=null) {
            Iterator i = previous.getNamespaceIterator();
            while (i.hasNext()) {
                String ns = (String) i.next();
                try {
                    String pfx = previous.getPrefixForNamespaceURI(ns);
                    addNamespaceURI(pfx,ns);
                } catch (NamespaceToPrefixResolver.NamespaceNotFoundException nnfe) {
                    throw new RuntimeException("Prefix not found for namespace: " + nnfe.getNamespaceURI());
                }
            }
        }
    }

    public String getOrAddPrefixForNamespaceURI(String namespace, String suggestedPrefix)
    {
        return registerOrGetPrefixForNamespaceURI(namespace,suggestedPrefix);
    }

    public String getOrAddPrefixForNamespaceURI(String namespace)
    {
        return registerOrGetPrefixForNamespaceURI(namespace);
    }

    public void removePrefix(String prefix)
    {
        String ns = (String) mPrefixToNamespace.get(prefix);
        if (ns==null)
        {
            return;
        }
        mNamespacesInOrder.remove(ns);
        mPrefixToNamespace.remove(prefix);
        mNamespaceToPrefix.remove(ns);
    }

    public void writePrefixes(XmlContentHandler handler) throws SAXException {
        for (int i=0;i<mNamespacesInOrder.size();i++) {
            String ns = (String) mNamespacesInOrder.get(i);
            String pfx = (String) mNamespaceToPrefix.get(ns);
            handler.prefixMapping(pfx,ns);
        }
    }

    /*
    public String formatCharacterEntities(String rootName) {
        if (mUsedCharacters.size()==0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE " + rootName + "\n[\n");
        for (int i=0;i<mUsedCharactersInOrder.size();i++) {
            Character c = (Character) mUsedCharactersInOrder.get(i);
            char cv = c.charValue();
            String v;
            String r;
            switch (cv) {
                case '\r': v = "&#38;#13;"; r = "cr"; break;
                case '\t': v = "&#38;#9;"; r = "tab"; break;
                case '\n': v = "&#38;#10;"; r = "lf"; break;
                case (char) 160: v = "&#160;"; r = "nbsp"; break;
                case 'N': v = "&#38;#13;&#38;#10;"; r = "crlf"; break; // N is just a marker.
                default: v = "??"; r = "unk"; break;
            }
            sb.append("  <!ENTITY ");
            sb.append(r);
            sb.append(" \"");
            sb.append(v);
            sb.append("\">\n");
        }
        sb.append("]>\n");
        return sb.toString();
    }

    public String unescapeCharacters(String str) {
        int len = str.length();
        boolean hasEscape = false;
        for (int i=0;i<len;i++) {
            char c = str.charAt(i);
            if (c=='&') {
                hasEscape = true;
                break;
            }
        }
        if (!hasEscape) {
            return str;
        }
        StringBuffer sb = new StringBuffer(len+16);
        StringBuffer esc = new StringBuffer();
        for (int i=0;i<len;i++) {
            char c = str.charAt(i);
            if (c=='&') { // 1 special case for crlf
                esc.setLength(0);
                for (i++;i<len;i++) {
                    c = str.charAt(i);
                    if (c==';') {
                        break;
                    }
                    esc.append(c);
                }
                String entity = esc.toString();
                sb.append(getEntityCharacter(entity));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }*/

    /**
     * Mostly single character strings, except for CRLF
     *
    private String getEntityCharacter(String entity) {
        if (entity.equals("crlf")) {
            return "\r\n";
        }
        if (entity.equals("lf")) {
            return "\n";
        }
        if (entity.equals("cr")) {
            return "\r";
        }
        if (entity.equals("tab")) {
            return "\t";
        }
        if (entity.equals("amp")) {
            return "&";
        }
        if (entity.equals("quot")) {
            return "\"";
        }
        if (entity.equals("lt")) {
            return "<";
        }
        if (entity.equals("gt")) {
            return ">";
        }
        if (entity.equals("nbsp")) {
            return "" + (char)160;
        }
        return "&#38;" + entity + ";"; // don't escape it.. is this ok?
    }

    public String escapeCharacters(String str, boolean isInAttribute) {
        return escapeCharacters(str,isInAttribute,true);
    }*/

    /**
     * Adds XMLSchema-instance namespace under 'xsi'
     */
    public void addXSINamespace() {
        addNamespaceURI("xsi", "http://www.w3.org/2001/XMLSchema-instance");
    }

    /**
     * Adds XMLSchema namespace under 'xsd'
     */
    public void addXSDNamespace() {
        addNamespaceURI("xsd", "http://www.w3.org/2001/XMLSchema");
    }

    /**
     * Added by default.
     */
    private void addXMLNamespace() {
        String pfx = "xml";
        String ns = "http://www.w3.org/XML/1998/namespace";

        // Register here but not in namespaces in order; xml is always there.
        mNamespaceToPrefix.put(ns,pfx);
        mPrefixToNamespace.put(pfx,ns);
    }

    public Iterator getNamespaceIterator() {
        return mNamespacesInOrder.iterator();
    }

    public String getPrefixForNamespaceURI(String namespaceURI) throws NamespaceToPrefixResolver.NamespaceNotFoundException {
        if (namespaceURI==null || namespaceURI.equals(EMPTY_STRING)) {
            return DEFAULT_NAMESPACE_PREFIX;
        }
        String str = (String) mNamespaceToPrefix.get(namespaceURI);
        if (str!=null) {
            return str;
        }
        if (mAutoRegister) {
            return registerOrGetPrefixForNamespaceURI(namespaceURI);
        }
        throw new NamespaceToPrefixResolver.NamespaceNotFoundException(namespaceURI);
    }

    public String registerOrGetPrefixForNamespaceURI(String namespaceURI) {
        return registerOrGetPrefixForNamespaceURI(namespaceURI, null);
    }

    public String registerOrGetPrefixForNamespaceURI(String namespaceURI, String suggestedPrefix)
    {
        if (namespaceURI==null || namespaceURI.length() == 0)
        {
            return DEFAULT_NAMESPACE_PREFIX; // not necessarily right...
        }
        String existing = (String) mNamespaceToPrefix.get(namespaceURI);
        if (existing!=null) {
            return existing;
        }
        String prefix = suggestedPrefix;
        if (prefix==null) {
            if (namespaceURI.equals(XSDL.INSTANCE_NAMESPACE)) {
                prefix = "xsi"; // hardcode that one.
            }
            else if (namespaceURI.equals(XSDL.NAMESPACE)) {
                prefix = "xsd";
            }
            else if (namespaceURI.equals("http://www.w3.org/XML/1998/namespace")) {
                prefix = "xml";
            }
            else if (namespaceURI.equals(ReadFromXSLT.XSLT_URI)) { // generalize this mechanism...
                prefix = "xsl";
            }
        }
       // Allowing default namespace, i.e. prefix of empty string
        if (prefix == null) {
            prefix = generateNextPrefix();
        }
        while(!addNamespaceURI(prefix, namespaceURI)) {
            prefix = generateNextPrefix();
        }
        return prefix;
    }

    private final String generateNextPrefix()
    {
        String prefix;
        if (mCount==0) {
            prefix = mStartingName;
        } else {
            prefix = mStartingName + (1+mCount);
        }
        mCount++;
        return prefix;
    }
   /**
    * @param prefix use empty string to designate default namespace
    * @param namespaceURI cannot be null
    * @return
    */
    public boolean addNamespaceURI(String prefix, String namespaceURI)
    {
        if (namespaceURI == null        ||
            namespaceURI.length() == 0)
        {
            return false;
        }

        // allow no prefix.
        if (mPrefixToNamespace.get(prefix) != null) {
            return false;
        }
        String op = (String) mNamespaceToPrefix.get(namespaceURI);
        if (op==null || op.length()==0)
        {
            mNamespaceToPrefix.put(namespaceURI,prefix);
        }
        mPrefixToNamespace.put(prefix, namespaceURI);
        mNamespacesInOrder.add(namespaceURI);
        return true;
    }
   /**
    *
    * @param namespacePrefix use empty string to retrieve default namespace
    * @return
    * @throws PrefixNotFoundException
    */
    public String getNamespaceURIForPrefix(String namespacePrefix) throws PrefixNotFoundException{
        String existing = (String) mPrefixToNamespace.get(namespacePrefix);
        if (existing==null) {
            if (namespacePrefix != null)
            {
                return null; // bad, but for backward compat.
            }
            throw new PrefixNotFoundException(namespacePrefix);
        }
        return existing;
    }

   /**
    *
    * @param namespacePrefix use empty string to retrieve default namespace
    * @return
    */
    public String getNamespaceURI(String namespacePrefix)
    {
        if (namespacePrefix==null) {
            return null;
        }
        String existing = (String) mPrefixToNamespace.get(namespacePrefix);
        return existing;
    }

    public void registerLocation(String namespaceURI, String locationURI) {
        if (namespaceURI==null || namespaceURI.length()==0) {
            registerLocation(locationURI);
        } else {
            mLocationMap.put(namespaceURI,locationURI);
        }
    }

    public void registerLocation(String locationURI) {
        mNoNamespaceLocations.add(locationURI);
    }

    public QName registerOrGetQNameForExpandedName(ExpandedName expandedName) {
        String pfx = registerOrGetPrefixForNamespaceURI(expandedName.getNamespaceURI());
        if (pfx==null) pfx=DEFAULT_NAMESPACE_PREFIX;
        return new QName(pfx,expandedName.getLocalName());
    }

    public QName registerOrGetQNameForExpandedName(ExpandedName expandedName, String suggestedPrefix) {
        String pfx = registerOrGetPrefixForNamespaceURI(expandedName.getNamespaceURI(),suggestedPrefix);
        if (pfx==null) pfx=DEFAULT_NAMESPACE_PREFIX;
        return new QName(pfx,expandedName.getLocalName());
    }

    public Object clone() {
        DefaultNamespaceMapper dnm = new DefaultNamespaceMapper(mStartingName);
        dnm.mergeNamespaces(this);
        return dnm;
    }

    /**
     * Returns the registered location URI for the namespace, or null if none.
     */
    public String getLocationURI(String namespaceURI) {
        return (String) mLocationMap.get(namespaceURI);
    }

    /**
     * Returns all of the locations registered without a namespaceURI (or registered with a null URI)
     */
    public Iterator getNoNamespaceLocations() {
        return mNoNamespaceLocations.iterator();
    }

    public void mergeNamespaces(NamespaceMapper namespace)
    {
        if (namespace == null || this == namespace) {
            return;
        }
        Iterator iter = namespace.getNamespaceIterator();
        while (iter.hasNext())
        {
            String uri = (String) iter.next();
            try {
                String prefix = namespace.getPrefixForNamespaceURI(uri);
                this.addNamespaceURI(prefix, uri);
            } catch (NamespaceToPrefixResolver.NamespaceNotFoundException nnfe) {
                throw new RuntimeException("Internal error, finding: " + nnfe.getNamespaceURI());
            }
        }
    }

    public Iterator getLocalPrefixes()
    {
        return getPrefixes();
    }

    /**
     * Really gets prefixes with unique namespace...
     * @return
     */
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
        return null;
    }

    /**
     * Formats the namespace part.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<mNamespacesInOrder.size();i++) {
            String ns = (String) mNamespacesInOrder.get(i);
            String pfx = (String) mNamespaceToPrefix.get(ns);
            sb.append(pfx + "=" + ns);
            sb.append('\n');
        }
        return sb.toString();
    }
}

