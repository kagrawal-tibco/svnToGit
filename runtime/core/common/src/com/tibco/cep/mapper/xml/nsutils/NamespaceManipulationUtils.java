package com.tibco.cep.mapper.xml.nsutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.xml.sax.SAXException;

import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.MutableNamespaceContext;
import com.tibco.xml.data.primitive.NamespaceContext;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiNamespace;
import com.tibco.xml.schema.helpers.NoNamespace;
import com.tibco.xml.schema.helpers.XMLNamespace;

/**
 * Utility methods for manipulating bindings.
 */
public final class NamespaceManipulationUtils
{
    /**
     * A short-cut method to add a namespace node to a node.
     * @param onNode The node to which to add the namespace node.
     * @param prefix The prefix to define.
     * @param namespace The namespace.
     * @deprecated Use new one on XiNamespace.
     */
    public static void addNamespaceNode(XiNode onNode, String prefix, String namespace)
    {
        onNode.setNamespace(onNode.getNodeFactory().createNamespace(prefix,namespace));
    }

    /**
     * Any undeclared namespaces used internally are added to the top (passed in) node.
     * @param node The node to scan and add.
     */
    public static void addAllNamespacesUsedRecursively(XiNode node)
    {
        addAllNamespacesUsedRecursively(node,new DefaultNamespacePrefixRecommender("ns",true));
    }

    /**
     * Any undeclared namespaces used internally are added to the top (passed in) node.
     * @param node The node to scan and add.
     * @param recommender A prefix recommender.
     */
    public static void addAllNamespacesUsedRecursively(XiNode node, NamespacePrefixRecommender recommender)
    {
        SimpleNamespaceContextRegistry nnm = new SimpleNamespaceContextRegistry();
        findUndeclaredNamespacesRecursive(node,nnm,recommender);
        // Add added namespaces:
        Iterator i = nnm.getNamespaceIterator();
        while (i.hasNext()) {
            String ns = (String) i.next();
            String pfx;
            try
            {
                pfx = nnm.getPrefixForNamespaceURI(ns);
            }
            catch (NamespaceToPrefixResolver.NamespaceNotFoundException nnfe)
            {
                throw new RuntimeException("Shouldn't happen: " + nnfe.getMessage());
            }
            node.setNamespace(node.getNodeFactory().createNamespace(pfx,ns));
        }
    }

    /**
     * Copy all namespace prefix declarations from <code>from</code> to <code>to</code>.  Doesn't
     * look for prefix "clashes", if a prefix exists in both <code>from</code> and <code>to</code>
     * it will be replaced in <code>to</code> with the one from <code>from</code>.
     * @param from
     * @param to
     */
    public static void copyNamespacePrefixDeclarations(NamespaceContext from, XiNode to)
    {
        Iterator prefixes = from.getPrefixes();
        while (prefixes.hasNext()) {
            String prefix = (String) prefixes.next();
            String namespace = null;
            try {
                namespace = from.getNamespaceURIForPrefix(prefix);
            } catch (PrefixToNamespaceResolver.PrefixNotFoundException e) {
                e.printStackTrace();  // should not happen, we got the prefix from the same place
            }
            if (prefix.length() == 0) {
                if (NoNamespace.URI == namespace) {
                    // see javadoc for NamespaceContext.getPrefixes().
                    continue;
                }
            }
            to.setNamespace(prefix, namespace);
        }
    }

    /**
     * Replaces all namespace declarations on the specified node with the new namespace.
     * @param onNode The node
     * @param oldNamespace The old namespace to replace.
     * @param newNamespace The new namespace.
     */
    public static void changeNamespaceShallow(XiNode onNode, String oldNamespace, String newNamespace)
    {
        for (;;)
        {
            XiNode n = getNamespaceNodeShallow(onNode,oldNamespace);
            if (n==null)
            {
                break;
            }
            n.setStringValue(newNamespace);
        }
    }

    /**
     * Gets the first (in declaration order) namespace node.
     * @param onNode The node to search (the search is shallow, applying only to this node, not parents.).
     * @param namespace The namespace to find.
     * @return The namespace node or null, if not found.
     */
    public static XiNode getNamespaceNodeShallow(XiNode onNode, String namespace)
    {
        Iterator i = onNode.getNamespaces();
        while (i.hasNext())
        {
            XiNode n = (XiNode) i.next();
            String sv = n.getStringValue();
            if (sv==namespace || ((sv!=null && sv.equals(namespace))))
            {
                return n;
            }
        }
        return null;
    }

    /**
     * From an ExpandedName, get the qname, using {@link NamespaceContextRegistry#getOrAddPrefixForNamespaceURI} to get or
     * add the prefix.
     * @param name
     * @param ni
     * @return
     */
    public static QName getOrAddQNameFromExpandedName(ExpandedName name, NamespaceContextRegistry ni)
    {
        if (name==null)
        {
            throw new NullPointerException("Null name");
        }
        String pfx = ni.getOrAddPrefixForNamespaceURI(name.getNamespaceURI());
        return new QName(pfx,name.getLocalName());
    }

    /**
     * From an ExpandedName, get the qname, using {@link NamespaceContextRegistry#getOrAddPrefixForNamespaceURI} to get or
     * add the prefix.
     * @param name
     * @param ni
     * @return
     */
    public static QName getOrAddQNameFromExpandedName(ExpandedName name, NamespaceContextRegistry ni, String optionalSuggestedPrefix)
    {
        if (name==null)
        {
            throw new NullPointerException("Null name");
        }
        String pfx = ni.getOrAddPrefixForNamespaceURI(name.getNamespaceURI(),optionalSuggestedPrefix);
        return new QName(pfx,name.getLocalName());
    }

    /**
     * Creates a {@link NamespaceContext} for the node which automatically declares new prefixes
     * for undeclared namespaces.<br>
     * This means that the returned resolver can <b>never</b> throw an exception for
     * {@link NamespaceToPrefixResolver#getPrefixForNamespaceURI}.<br>
     * All newly declared prefixes are added to the root element as if by {@link #getOrAddPrefixForNamespace}.
     * @deprecated Use namespace importer.
     */
    public static NamespaceContext createAutoDeclaringNamespaceContext(XiNode onNode)
    {
        final XiNode fnode = onNode;
        return new NamespaceContext()
        {
            public String getPrefixForNamespaceURI(String namespace) throws NamespaceToPrefixResolver.NamespaceNotFoundException
            {
                return getOrAddPrefixForNamespace(fnode,namespace);
            }

            public Iterator getLocalPrefixes()
            {
                return fnode.getLocalPrefixes();
            }

            public Iterator getPrefixes()
            {
                return fnode.getPrefixes();
            }

            public NamespaceContext snapshot()
            {
                return fnode.snapshot();
            }

            public String getNamespaceURIForPrefix(String pfx) throws PrefixToNamespaceResolver.PrefixNotFoundException
            {
                return fnode.getNamespaceURIForPrefix(pfx);
            }
        };
    }

    /**
     * Creates a {@link NamespaceContext} where first the node is checked, then the chainTo context.
     * for undeclared namespaces.<br>
     */
    public static NamespaceContext createChainingContext(NamespaceContext first, NamespaceContext chainTo)
    {
        final NamespaceContext fnode = first;
        final NamespaceContext fchainTo = chainTo;
        return new NamespaceContext()
        {
            public String getPrefixForNamespaceURI(String namespace) throws NamespaceToPrefixResolver.NamespaceNotFoundException
            {
                try
                {
                    return fnode.getPrefixForNamespaceURI(namespace);
                }
                catch (NamespaceToPrefixResolver.NamespaceNotFoundException nnfe)
                {
                    return fchainTo.getPrefixForNamespaceURI(namespace);
                }
            }

            public Iterator getLocalPrefixes()
            {
                return fnode.getLocalPrefixes();
            }

            public Iterator getPrefixes()
            {
                //WCETODO
                throw new RuntimeException("Not yet implemented; finish me");//return fnode.getPrefixes();
            }

            public NamespaceContext snapshot()
            {
                //WCETODO
                throw new RuntimeException("Not yet implemented; finish me");//return fnode.getPrefixes();
                //return createChainingContext(fnode.snapshot(),fchainTo.snapshot());
            }

            public String getNamespaceURIForPrefix(String pfx) throws PrefixToNamespaceResolver.PrefixNotFoundException
            {
                try
                {
                    return fnode.getNamespaceURIForPrefix(pfx);
                }
                catch (PrefixToNamespaceResolver.PrefixNotFoundException pnfe)
                {
                    return fchainTo.getNamespaceURIForPrefix(pfx);
                }
            }
        };
    }

    public static NamespaceContextRegistry createChainingImporter(NamespaceContext first, NamespaceContextRegistry chainTo)
    {
        final NamespaceContext fnode = first;
        final NamespaceContextRegistry fchainTo = chainTo;
        return new NamespaceContextRegistry()
        {
            public String getPrefixForNamespaceURI(String namespace) throws NamespaceToPrefixResolver.NamespaceNotFoundException
            {
                try
                {
                    return fnode.getPrefixForNamespaceURI(namespace);
                }
                catch (NamespaceToPrefixResolver.NamespaceNotFoundException nnfe)
                {
                    return fchainTo.getPrefixForNamespaceURI(namespace);
                }
            }

            public Iterator getLocalPrefixes()
            {
                return fnode.getLocalPrefixes();
            }

            public Iterator getPrefixes()
            {
                //WCETODO
                throw new RuntimeException("Not yet implemented; finish me");//return fnode.getPrefixes();
            }

            public NamespaceContext snapshot()
            {
                //WCETODO
                throw new RuntimeException("Not yet implemented; finish me");//return fnode.getPrefixes();
                //return createChainingContext(fnode.snapshot(),fchainTo.snapshot());
            }

            public String getNamespaceURIForPrefix(String pfx) throws PrefixToNamespaceResolver.PrefixNotFoundException
            {
                try
                {
                    return fnode.getNamespaceURIForPrefix(pfx);
                }
                catch (PrefixToNamespaceResolver.PrefixNotFoundException pnfe)
                {
                    return fchainTo.getNamespaceURIForPrefix(pfx);
                }
            }

            public String getOrAddPrefixForNamespaceURI(String namespace)
            {
                return getOrAddPrefixForNamespaceURI(namespace,null);
            }

            public String getOrAddPrefixForNamespaceURI(String namespace, String suggestedPrefix)
            {
                try
                {
                    return fnode.getPrefixForNamespaceURI(namespace);
                }
                catch (NamespaceToPrefixResolver.NamespaceNotFoundException nn)
                {
                    return fchainTo.getOrAddPrefixForNamespaceURI(namespace,suggestedPrefix);
                }
            }
        };
    }

    public static NamespaceContextRegistry createNamespaceImporter(NamespaceContext onNode, NamespaceContextRegistry chainTo)
    {
        final NamespaceContext fnode = onNode;
        final NamespaceContextRegistry fchainTo = chainTo;
        return new NamespaceContextRegistry()
        {

            public String getOrAddPrefixForNamespaceURI(String namespace, String suggestedPrefix)
            {
                try
                {
                    return fnode.getPrefixForNamespaceURI(namespace);
                }
                catch (NamespaceToPrefixResolver.NamespaceNotFoundException nnfe)
                {
                    return fchainTo.getOrAddPrefixForNamespaceURI(namespace,suggestedPrefix);
                }
            }

            public String getOrAddPrefixForNamespaceURI(String namespace)
            {
                return this.getOrAddPrefixForNamespaceURI(namespace,null);
            }

            public Iterator getLocalPrefixes()
            {
                return fnode.getLocalPrefixes();
            }

            public Iterator getPrefixes()
            {
                throw new RuntimeException("Not implemented");
            }

            public NamespaceContext snapshot()
            {
                throw new RuntimeException("Not implemented");
            }

            public String getPrefixForNamespaceURI(String s) throws NamespaceToPrefixResolver.NamespaceNotFoundException
            {
                try
                {
                    return fnode.getPrefixForNamespaceURI(s);
                }
                catch (NamespaceToPrefixResolver.NamespaceNotFoundException nnfe)
                {
                    // argh, expensive!
                    return fchainTo.getPrefixForNamespaceURI(s);
                }
            }

            public String getNamespaceURIForPrefix(String s) throws PrefixToNamespaceResolver.PrefixNotFoundException
            {
                try
                {
                    return fnode.getNamespaceURIForPrefix(s);
                }
                catch (PrefixToNamespaceResolver.PrefixNotFoundException nnfe)
                {
                    // argh, expensive!
                    return fchainTo.getNamespaceURIForPrefix(s);
                }
            }
        };
    }

    /**
     * Creates a {@link NamespaceContextRegistry} for the node which automatically declares new prefixes
     * for undeclared namespaces.<br>
     * This means that the returned resolver can <b>never</b> throw an exception for
     * {@link NamespaceToPrefixResolver#getPrefixForNamespaceURI}.<br>
     * All newly declared prefixes are added to the root element as if by {@link #getOrAddPrefixForNamespace}.
     * @return A namespace importer that works on the nodes.
     */
    public static NamespaceContextRegistry createNamespaceImporter(XiNode onNode)
    {
        return createNamespaceImporter(onNode,DefaultNamespacePrefixRecommender.INSTANCE);
    }

    /**
     * Creates a {@link NamespaceContextRegistry} for the node which automatically declares new prefixes
     * for undeclared namespaces.<br>
     * This means that the returned resolver can <b>never</b> throw an exception for
     * {@link NamespaceToPrefixResolver#getPrefixForNamespaceURI}.<br>
     * All newly declared prefixes are added to the root element as if by {@link #getOrAddPrefixForNamespace}.
     * @return A namespace importer that works on the nodes.
     */
    public static NamespaceContextRegistry createNamespaceImporter(XiNode onNode, NamespacePrefixRecommender npr)
    {
        if (npr==null)
        {
            throw new NullPointerException("null recommender"); // fail fast.
        }
        final XiNode fnode = onNode;
        final NamespacePrefixRecommender fnpr = npr;
        return new NamespaceContextRegistry()
        {

            public String getOrAddPrefixForNamespaceURI(String namespace, String suggestedPrefix)
            {
                return NamespaceManipulationUtils.getOrAddPrefixForNamespace(fnode,namespace,suggestedPrefix);
            }

            public String getOrAddPrefixForNamespaceURI(String namespace)
            {
                return NamespaceManipulationUtils.getOrAddPrefixForNamespace(fnode,namespace,fnpr.suggestPrefixForNamespaceURI(namespace));
            }

            public Iterator getLocalPrefixes()
            {
                return fnode.getLocalPrefixes();
            }

            public Iterator getPrefixes()
            {
                return fnode.getPrefixes();
            }

            public NamespaceContext snapshot()
            {
                return fnode.snapshot();
            }

            public String getPrefixForNamespaceURI(String s) throws NamespaceToPrefixResolver.NamespaceNotFoundException
            {
                return fnode.getPrefixForNamespaceURI(s);
            }

            public String getNamespaceURIForPrefix(String s) throws PrefixToNamespaceResolver.PrefixNotFoundException
            {
                return fnode.getNamespaceURIForPrefix(s);
            }
        };
    }

    /**
     * Records any undeclared namespaces used internally (in elements, attributes and content).
     * @param node The root node to check.
     * @param namespaceContextRegistry The namespace importer on which {@link NamespaceContextRegistry#getOrAddPrefixForNamespaceURI}
     * will be called for any missing namespaces.
     */
    public static void findUndeclaredNamespacesRecursive(XiNode node, NamespaceContextRegistry namespaceContextRegistry)
    {
        findUndeclaredNamespacesRecursiveInternal(node,namespaceContextRegistry,new HashSet(),new DefaultNamespacePrefixRecommender("ns",true));
    }

    /**
     * Records any undeclared namespaces used internally (in elements, attributes and content).
     * @param node The root node to check.
     * @param namespaceContextRegistry The namespace importer on which {@link NamespaceContextRegistry#getOrAddPrefixForNamespaceURI}
     * will be called for any missing namespaces.
     */
    public static void findUndeclaredNamespacesRecursive(XiNode node, NamespaceContextRegistry namespaceContextRegistry, NamespacePrefixRecommender prefixRecommender)
    {
        findUndeclaredNamespacesRecursiveInternal(node,namespaceContextRegistry,new HashSet(),prefixRecommender);
    }

    private static void findUndeclaredNamespacesRecursiveInternal(XiNode node, NamespaceContextRegistry namespaceContextRegistry, Set alreadyFoundInRoot, NamespacePrefixRecommender prefixRecommender)
    {
        XmlNodeKind nk = node.getNodeKind();
        if (nk==XmlNodeKind.ELEMENT || nk==XmlNodeKind.ATTRIBUTE)
        {
            ExpandedName ename = node.getName();
            if (!NoNamespace.isNoNamespaceURI(ename.getNamespaceURI()))
            {
                checkNamespaceForDeclaration(namespaceContextRegistry,prefixRecommender, alreadyFoundInRoot,node,ename.getNamespaceURI());
            }
        }
        // allow typed values in content such as 'comments'.
        XmlTypedValue tv = node.getTypedValue();
        if (tv!=null) // getTypedValue is allowed to return null for n/a.
        {
            XmlTypedValue vals = tv.getRequiredInScopeNamespaces(null);
            for (int i=0;i<vals.size();i++)
            {
                String ns = vals.getAtom(i).castAsString();
                if (findPrefixForNamespaceURI(node,ns)==null)
                {
                    checkNamespaceForDeclaration(namespaceContextRegistry,prefixRecommender,alreadyFoundInRoot,node,ns);
                }
            }
        }
        Iterator attributes = node.getAttributes();
        while (attributes.hasNext())
        {
            XiNode attr = (XiNode) attributes.next();
            findUndeclaredNamespacesRecursiveInternal(attr,namespaceContextRegistry,alreadyFoundInRoot,prefixRecommender);
        }
        Iterator children = node.getChildren();
        if (children==null)
        {
            throw new NullPointerException("Node: " + node.getClass().getName() + " returned null children");
        }
        while (children.hasNext())
        {
            XiNode child = (XiNode) children.next();
            findUndeclaredNamespacesRecursiveInternal(child,namespaceContextRegistry,alreadyFoundInRoot,prefixRecommender);
        }
    }

    private static void checkNamespaceForDeclaration(NamespaceContextRegistry regTo, NamespacePrefixRecommender prefixRecommender, Set alreadyFoundInRoot, XiNode node, String ns)
    {
        if (!alreadyFoundInRoot.contains(ns))
        {
            String existingPfx = NamespaceManipulationUtils.findPrefixForNamespaceURI(node,ns);
            if (existingPfx==null)
            {
                // not there, make one.
                try
                {
                    regTo.getPrefixForNamespaceURI(ns);
                    // already there.
                    alreadyFoundInRoot.add(ns);
                }
                catch (NamespaceToPrefixResolver.NamespaceNotFoundException nnfe)
                {
                    // missing, so add it.
                    String pfx = findUniquePrefix(node,prefixRecommender.suggestPrefixForNamespaceURI(ns));
                    regTo.getOrAddPrefixForNamespaceURI(ns,pfx);
                }
            }
        }
    }

    /**
     * Finds the prefix for namespace URI, returns null if not found.
     * This is entirely equivalent to {@link NamespaceToPrefixResolver#getPrefixForNamespaceURI} except it doesn't
     * throw exceptions.
     * For applications that are <b>expecting</b> to not find many namespaces,
     * it's best not to be chucking exceptions left & right.
     */
    public static String findPrefixForNamespaceURI(XiNode node, String ns)
    {
        return findPrefixForNamespaceURI(node,ns,false);
    }

    /**
     * Finds the prefix for namespace URI, returns null if not found.
     * This is entirely equivalent to {@link NamespaceToPrefixResolver#getPrefixForNamespaceURI} except it doesn't
     * throw exceptions.
     * For applications that are <b>expecting</b> to not find many namespaces,
     * it's best not to be chucking exceptions left & right.
     */
    public static String findPrefixForNamespaceURI(XiNode node, String ns, boolean isAttr)
    {
        if (isAttr && NoNamespace.isNoNamespaceURI(ns))
        {
            // xml attributes no-namespace NEVER gets a prefix.
            return "";
        }
        //WCETODO fixme in impl; check for undeclares!!!
        if (node==null)
        {
            return null;
        }
        if (XMLNamespace.URI.equals(ns)) // flip order because 'ns' can be null.
        {
            return XMLNamespace.PREFIX;
        }
        Iterator i = node.getNamespaces();
        if (i==null)
        {
            throw new NullPointerException("Null namespaces returned by " + node.getClass().getName());
        }
        while (i.hasNext())
        {
            XiNode n = (XiNode) i.next();
            String pfx = n.getName().getLocalName();
            String nsv = n.getStringValue();
            if (NoNamespace.isNoNamespaceURI(ns))
            {
                if (NoNamespace.isNoNamespaceURI(nsv))
                {
                    return pfx;
                }
            }
            else
            {
                if (ns.equals(nsv))
                {
                    return pfx;
                }
            }
        }
        // previous context.... if (b.getElementInfo().get)
        XiNode p = node.getParentNode();
        if (p==null)
        {
            if (NoNamespace.isNoNamespaceURI(ns))
            {
                // no namespace is ok.
                return "";
            }
            return null;
        }
        return findPrefixForNamespaceURI(p,ns);
    }

    /**
     * Finds a unique, legal prefix in the node's context.<br>
     * Note that uniqueness is determined by if {@link #isPrefixDeclaredInAncestorOrSelf} returns false, see it for
     * details of what unique means.
     * @param node The node from where a context should be found.
     */
    public static String findUniquePrefix(XiNode node)
    {
        return findUniquePrefix(node,null);
    }

    public static String findUniquePrefix(XiNode node, String basePfx)
    {
        if (basePfx==null)
        {
            basePfx = "ns";
        }
        for (int i=1;;i++)
        {
            String pfx;
            if (i==1)
            {
                pfx = basePfx;
            }
            else
            {
                pfx = basePfx + i;
            }
            if (!isPrefixDeclaredInAncestorOrSelf(node, pfx))
            {
                return pfx;
            }
        }
    }

    /**
     * If it already exists, gets the prefix for the namespace, otherwise, adds a declaration and returns it.
     * If adding is required, the declaration is added to the root element node.
     * This method properly handles no namespace. (WCETODO make that totally real; attributes, etc.)
     * @param node The node to check for namespace delcaration.
     * @param namespace The namespace.
     * @return The prefix corresponding to that namespace.
     */
    public static String getOrAddPrefixForNamespace(XiNode node, String namespace)
    {
        return getOrAddPrefixForNamespace(node,namespace,null,true);
    }

    /**
     * If it already exists, gets the prefix for the namespace, otherwise, adds a declaration and returns it.
     * If adding is required, the declaration is added to the root element node using the suggestedPrefix as a starting
     * point.
     * This method properly handles no namespace. (WCETODO make that totally real; attributes, etc.)
     * @param node The node to check for namespace delcaration.
     * @param namespace The namespace.
     * @param suggestedPrefix The suggested starting prefix, or null, if a default starting prefix should be used.
     * @return The prefix corresponding to that namespace.
     */
    public static String getOrAddPrefixForNamespace(XiNode node, String namespace, String suggestedPrefix)
    {
        return getOrAddPrefixForNamespace(node,namespace,suggestedPrefix,true);
    }

    /**
     * If it already exists, gets the prefix for the namespace, otherwise, adds a declaration and returns it.
     * If adding is required, the declaration is added using the suggestedPrefix as a starting
     * point.<br>
     * This method properly handles no namespace. (WCETODO make that totally real; attributes, etc.)
     * @param node The node to check for namespace delcaration.
     * @param namespace The namespace.
     * @param suggestedPrefix The suggested starting prefix, or null, if a default starting prefix should be used.
     * @param addToRoot Only matters if the prefix must be added; if true will add declaration to the root, otherwise,
     * adds to the node itself.
     * @return The prefix corresponding to that namespace.
     */
    public static String getOrAddPrefixForNamespace(XiNode node, String namespace, String suggestedPrefix, boolean addToRoot)
    {
        String existing = findPrefixForNamespaceURI(node,namespace);
        if (existing!=null)
        {
            return existing;
        }
        String prefixToUse = findUniquePrefix(node,suggestedPrefix);
        if (addToRoot)
        {
            XiNode rootEl = getRootElement(node);
            XiNamespace.setNamespace(rootEl,prefixToUse,namespace);
        }
        else
        {
            XiNamespace.setNamespace(node,prefixToUse,namespace);
        }
        return prefixToUse;
    }

    // probably a candidate for regular XiNode helpers.
    static XiNode getRootElement(XiNode n)
    {
        XiNode pn = n.getParentNode();
        // Keep climbing until we either hit the top or hit a document or fragment.
        if (pn==null || pn.getNodeKind()==XmlNodeKind.DOCUMENT || pn.getNodeKind()==XmlNodeKind.FRAGMENT)
        {
            return n;
        }
        return getRootElement(pn);
    }

    /**
     * Checks for the use of a prefix ANYWHERE in the namespace context stack.<br>
     * Note that this is subtley different than checking
     * {@link com.tibco.xml.data.primitive.PrefixToNamespaceResolver#getNamespaceURIForPrefix}
     * because of the possibility of prefixes being undeclared.
     */
    public static boolean isPrefixDeclaredInAncestorOrSelf(XiNode node, String prefix)
    {
        if (isPrefixDeclaredLocally(node,prefix))
        {
            return true;
        }
        XiNode p = node.getParentNode();
        if (p==null)
        {
            return false;
        }
        return isPrefixDeclaredInAncestorOrSelf(p,prefix);
    }

    /**
     * Checks for the use of a prefix ANYWHERE in the namespace context stack.<br>
     * Note that this is subtley different than checking
     * {@link com.tibco.xml.data.primitive.PrefixToNamespaceResolver#getNamespaceURIForPrefix}
     * because of the possibility of prefixes being undeclared.
     */
    public static boolean isPrefixDeclaredInAncestorOrSelf(MutableNamespaceContext node, String prefix)
    {
        if (isPrefixDeclaredLocally(node,prefix))
        {
            return true;
        }
        MutableNamespaceContext p = node.popNamespaceContext();
        if (p==null)
        {
            return false;
        }
        return isPrefixDeclaredInAncestorOrSelf(p,prefix);
    }

    /**
     * Checks to see if a prefix is declared locally on a node.<br>
     * (This is simply just iterating & checking the local prefixes).
     * @param node The node to check.
     * @param prefix The prefix to search for.
     * @return True if it's there, false otherwise.
     */
    public static boolean isPrefixDeclaredLocally(NamespaceContext node, String prefix)
    {
        Iterator i = node.getLocalPrefixes();
        if (i==null)
        {
            throw new RuntimeException("Node: " + node.getClass().getName() + " returned null for local prefixes");
        }
        while (i.hasNext())
        {
            String pfx = (String) i.next();
            if (pfx.equals(prefix))
            {
                return true;
            }
        }
        return false;
    }

    public static void issueNamespaceDeclarations(NamespaceContext context, XmlContentHandler handler) throws SAXException
    {
        Iterator p = context.getPrefixes();
        while (p.hasNext())
        {
            String pfx = (String) p.next();
            try
            {
                String ns = context.getNamespaceURIForPrefix(pfx);
                handler.prefixMapping(pfx,ns);
            }
            catch (PrefixToNamespaceResolver.PrefixNotFoundException pnfe)
            {
                throw new RuntimeException("Shouldn't happen: " + pnfe.getMessage());
            }
        }
    }

    /**
     * Issues all of the contextual (ancestor) prefix mappings for the binding.
     * @param b
     * @param handler
     * @throws org.xml.sax.SAXException
     */
    public static void issueAncestorNamespaceDeclarations(XiNode b, XmlContentHandler handler) throws SAXException
    {
        XiNode p = b.getParentNode();
        ArrayList list = new ArrayList();
        gatherNamespaceDeclarations(p,list);
        for (int i=0;i<list.size();i++)
        {
            String pfx = (String) list.get(i);
            String ns;
            if (!isPrefixDeclaredLocally(b,pfx))
            {
                try
                {
                    ns = b.getNamespaceURIForPrefix(pfx);
                }
                catch (PrefixToNamespaceResolver.PrefixNotFoundException nnfe)
                {
                    // this can't happen unless there's a bug.
                    throw new RuntimeException("Internal Error: Prefix not here: " + pfx);
                }
                handler.prefixMapping(pfx,ns);
            }
        }
    }

    public static void bubbleOutNamespaceDeclarationsToRoot(XiNode node, NodePrefixRenamer renamer)
    {
        // Create an importer for the parent:
        XiNode parent = node.getParentNode();
        if (parent==null || parent.getNodeKind()!=XmlNodeKind.ELEMENT)
        {
            return;
        }
        bubbleOutNamespaceDeclarations(node,renamer,createNamespaceImporter(parent));
    }

    /**
     * Takes all of the namespace declarations on this node <b>and</b> its children, and
     * moves them to the specified {@link NamespaceContextRegistry}.<br>
     * @param node The node to bubble out namespaces from.
     * @param renamer Required because the prefix may need change if there are duplicates.
     * @param bubbleTo All prefix declarations are moved there.<br>Could be an importer built off the root node.
     */
    public static void bubbleOutNamespaceDeclarations(XiNode node, NodePrefixRenamer renamer, NamespaceContextRegistry bubbleTo)
    {
        for (XiNode nns = node.getFirstNamespace();nns!=null;nns=nns.getNextSibling())
        {
            String pfx = nns.getName().getLocalName();
            String ns = nns.getStringValue();
            if (ns!=null && ns.length()==0)
            {
                ns = NoNamespace.URI;
            }
            String gotPfx = bubbleTo.getOrAddPrefixForNamespaceURI(ns,pfx);
            if (!gotPfx.equals(pfx))
            {
                renamePrefixUsages(node,renamer,pfx,gotPfx);
            }
        }
        removeAllLocalNamespaceDeclarations(node);
    }

    /**
     * Takes all of the namespace declarations on this node <b>and</b> its children, and
     * moves them to the specified {@link NamespaceContextRegistry}.<br>
     * @param node The node to bubble out namespaces from.
     * @param renamer Required because the prefix may need change if there are duplicates.
     * @param bubbleTo All prefix declarations are moved there.<br>Could be an importer built off the root node.
     */
    public static void bubbleOutNamespaceDeclarationsRecursive(XiNode node, NodePrefixRenamer renamer, NamespaceContextRegistry bubbleTo)
    {
        for (XiNode nns = node.getFirstNamespace();nns!=null;nns=nns.getNextSibling())
        {
            String pfx = nns.getName().getLocalName();
            String ns = nns.getStringValue();
            String gotPfx = bubbleTo.getOrAddPrefixForNamespaceURI(ns,pfx);
            if (!gotPfx.equals(pfx))
            {
                renamePrefixUsages(node,renamer,pfx,gotPfx);
            }
        }
       for (XiNode ch = XiChild.getFirstChild(node);ch!=null;ch=ch.getNextSibling())  // 1-4BIFD5 fixed        {
       {
            bubbleOutNamespaceDeclarationsRecursive(ch,renamer,bubbleTo);
       }
    }

    /*public static void renamePrefix(XiNode node, String oldPrefix, String newPrefix)
    {
        throw new RuntimeException();
        renamePrefixUsages(binding,oldPrefix,newPrefix);
        renamePrefixDeclaration(binding,oldPrefix,newPrefix);
    }*/

    /*
    public static void renamePrefixDeclaration(XiNode node, String oldPrefix, String newPrefix)
    {
        if (node.getParentNode()!=null)
        {
            NamespaceMapper nm = binding.getElementInfo().getPreviousContext();
            try
            {
                String ns = nm.getNamespaceURIForPrefix(oldPrefix);
                nm.removePrefix(oldPrefix);
                nm.addNamespaceURI(newPrefix,ns);
            }
            catch (PrefixToNamespaceResolver.PrefixNotFoundException pnfe)
            {
                // eat it.
            }
            return;
        }
        // otherwise work on local decls:
        BindingElementInfo.NamespaceDeclaration[] namespaceDecl = binding.getElementInfo().getNamespaceDeclarations();
        BindingElementInfo.NamespaceDeclaration[] nnamespaceDecl = new BindingElementInfo.NamespaceDeclaration[namespaceDecl.length];
        for (int i=0;i<namespaceDecl.length;i++)
        {
            if (namespaceDecl[i].getPrefix().equals(oldPrefix))
            {
                nnamespaceDecl[i] = new BindingElementInfo.NamespaceDeclaration(newPrefix,namespaceDecl[i].getNamespace());
            }
            else
            {
                nnamespaceDecl[i] = namespaceDecl[i];
            }
        }
        binding.setElementInfo(binding.getElementInfo().createWithNamespaceDeclarations(nnamespaceDecl));
    }*/

    /**
     * Updates the node model to account for a prefix being renamed from the old to new value.<br>
     * The implementation is smart enough not to traverse further if the 'newPrefix' is redeclared/undeclared.
     * @param node The node to update (recursively)
     * @param prefixRenamer A callback that knows how to update local prefix uses.
     * @param oldPrefix The old prefix.
     * @param newPrefix The new prefix.
     */
    public static void renamePrefixUsages(XiNode node, NodePrefixRenamer prefixRenamer, String oldPrefix, String newPrefix)
    {
        if (oldPrefix.equals(newPrefix))
        {
            // duh.
            return;
        }
        HashMap map = new HashMap();
        map.put(oldPrefix,newPrefix);
        renamePrefixUsages(node,prefixRenamer,map);
    }

    /**
     * Updates the node model to account for a prefix being renamed from the old to new value.<br>
     * The implementation is smart enough not to traverse further if the 'newPrefix' is redeclared/undeclared.
     * @param node The node to update (recursively)
     * @param prefixRenamer A callback that knows how to update local prefix uses.
     * @param oldPrefixToNewPrefix A map of old prefix (String) to new prefix (String).
     */
    public static void renamePrefixUsages(XiNode node, NodePrefixRenamer prefixRenamer, Map oldPrefixToNewPrefix)
    {
        // Check to see if the 'new prefix' is overwritten on this node.
        Iterator ni = node.getNamespaces();
        boolean hasOverlap = false;
        while (ni.hasNext())
        {
            XiNode n = (XiNode) ni.next();
            if (oldPrefixToNewPrefix.containsKey(n.getName().getLocalName()))
            {
                // don't traverse anymore.
                hasOverlap = true;
                return;
            }
        }
        if (hasOverlap)
        {
            // make a copy of the map w/o the duplicate namespaces:
            Map newMap = new HashMap(oldPrefixToNewPrefix);
            ni = node.getNamespaces();
            XiNode n = (XiNode) ni.next();
            if (oldPrefixToNewPrefix.containsKey(n.getName().getLocalName()))
            {
                newMap.remove(n.getName().getLocalName());
            }
            // Use this for the remainder:
            oldPrefixToNewPrefix = newMap;
        }
        prefixRenamer.renameLocalPrefixUsages(node,oldPrefixToNewPrefix);
        Iterator c = node.getChildren();
        while (c.hasNext())
        {
            XiNode cn = (XiNode) c.next();
            renamePrefixUsages(cn,prefixRenamer,oldPrefixToNewPrefix);
        }
    }

    /**
     * Adds all the namespace declarations from one context to another.
     * @param addTo Add all declarations, using {@link #getOrAddPrefixForNamespace}.
     * @param from The original context.
     */
    public static void addAllNamespaceDeclarations(NamespaceContextRegistry addTo, NamespaceContext from)
    {
        Iterator i = from.getPrefixes();
        while (i.hasNext())
        {
            String pfx = (String) i.next();
            try
            {
                String ns = from.getNamespaceURIForPrefix(pfx);
                addTo.getOrAddPrefixForNamespaceURI(ns,pfx);
            }
            catch (PrefixToNamespaceResolver.PrefixNotFoundException pnfe)
            {
                throw new RuntimeException("Shouldn't happen: " + pnfe);
            }
        }
    }

    /**
     * Removes all namespace declarations.
     */
    public static void removeAllLocalNamespaceDeclarations(XiNode from)
    {
        Iterator i = from.getLocalPrefixes();
        ArrayList temp = new ArrayList();
        while (i.hasNext())
        {
            temp.add(i.next());
        }
        i = temp.iterator();
        while (i.hasNext())
        {
            String pfx = (String) i.next();
            from.removeNamespace(pfx);
        }
    }

    private static void gatherNamespaceDeclarations(XiNode b, ArrayList list)
    {
        //WCETODO make it exclude duplicates!
        if (b==null)
        {
            return;
        }
        Iterator i = b.getLocalPrefixes();
        if (i==null)
        {
            throw new NullPointerException("Null prefixes returned by: " + b.getClass().getName());
        }
        while (i.hasNext())
        {
            String pfx = (String) i.next();
            if (!list.contains(pfx))
            {
                list.add(pfx);
            }
        }
        gatherNamespaceDeclarations(b.getParentNode(),list);
    }
}
