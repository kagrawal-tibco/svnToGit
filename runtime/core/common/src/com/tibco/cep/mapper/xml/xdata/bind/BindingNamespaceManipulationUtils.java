package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.tibco.cep.mapper.xml.nsutils.DefaultNamespacePrefixRecommender;
import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.nsutils.NamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.DefaultImportRegistry;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibExtFunctions;
import com.tibco.xml.data.primitive.NamespaceContext;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.helpers.NoNamespace;
import com.tibco.xml.schema.helpers.XMLNamespace;

/**
 * A few utility methods.
 */
public final class BindingNamespaceManipulationUtils
{
    /**
     * Logically the same as {@link NamespaceManipulationUtils#createNamespaceImporter}, but this ensures that non-child-marker comment
     * passed in as a node and <b>vastly</b> more efficient.<br>
     * It is also more type safe.
     * @param binding
     * @return
     */
    public static NamespaceContextRegistry createNamespaceImporter(Binding binding)
    {
        while (binding instanceof CommentBinding)
        {
            binding = binding.getParent();
        }
        Binding p = binding.getParent();
        if (p!=null)
        {
            NamespaceContextRegistry pi = createNamespaceImporter(p);
            return createChildNamespaceResolver(pi,binding);
        }
        else
        {
            final Binding fbinding = binding;
            NamespaceContextRegistry newr = new NamespaceContextRegistry()
            {
                public String getPrefixForNamespaceURI(String s) throws NamespaceToPrefixResolver.NamespaceNotFoundException
                {
                    BindingElementInfo info = fbinding.getElementInfo();
                    final BindingElementInfo.NamespaceDeclaration[] nd = info.getNamespaceDeclarations();

                    for (int i=0;i<nd.length;i++)
                    {
                        if (nd[i].getNamespace().equals(s))
                        {
                            return nd[i].getPrefix();
                        }
                    }
                    if (XMLNamespace.URI.equals(s))
                    {
                        return XMLNamespace.PREFIX;
                    }
                    if (s==NoNamespace.URI)
                    {
                        return "";
                    }
                    throw new NamespaceToPrefixResolver.NamespaceNotFoundException(s);
                }

                public String getNamespaceURIForPrefix(String s) throws PrefixToNamespaceResolver.PrefixNotFoundException
                {
                    if (XMLNamespace.PREFIX.equals(s))
                    {
                        return XMLNamespace.URI;
                    }
                    BindingElementInfo info = fbinding.getElementInfo();
                    final BindingElementInfo.NamespaceDeclaration[] nd = info.getNamespaceDeclarations();

                    for (int i=0;i<nd.length;i++)
                    {
                        if (nd[i].getPrefix().equals(s))
                        {
                            return nd[i].getNamespace();
                        }
                    }
                    if (s.length()==0)
                    {
                        return NoNamespace.URI;
                    }
                    throw new PrefixToNamespaceResolver.PrefixNotFoundException(s);
                }

                public String getOrAddPrefixForNamespaceURI(String namespace)
                {
                    return getOrAddPrefixForNamespaceURI(namespace,null);
                }

                public String getOrAddPrefixForNamespaceURI(String namespace, String suggestedPrefix)
                {
                    if (namespace==NoNamespace.URI)
                    {
                        return "";
                    }
                    if (XMLNamespace.URI.equals(namespace))
                    {
                        return XMLNamespace.PREFIX;
                    }
                    BindingElementInfo info = fbinding.getElementInfo();
                    final BindingElementInfo.NamespaceDeclaration[] nd = info.getNamespaceDeclarations();

                    for (int i=0;i<nd.length;i++)
                    {
                        if (nd[i].getNamespace().equals(namespace))
                        {
                            return nd[i].getPrefix();
                        }
                    }
                    if (suggestedPrefix==null)
                    {
                        suggestedPrefix = DefaultNamespacePrefixRecommender.INSTANCE.suggestPrefixForNamespaceURI(namespace);
                        if (suggestedPrefix==null)
                        {
                            suggestedPrefix = "ns";
                        }
                    }
                    for (int si=0;;si++)
                    {
                        String tp = si==0 ? suggestedPrefix : suggestedPrefix+si;
                        if (!isPrefixTaken(tp))
                        {
                            BindingElementInfo.NamespaceDeclaration[] nd2 = new BindingElementInfo.NamespaceDeclaration[nd.length+1];
                            for (int i=0;i<nd.length;i++)
                            {
                                nd2[i] = nd[i];
                            }
                            nd2[nd2.length-1] = new BindingElementInfo.NamespaceDeclaration(tp,namespace);
                            fbinding.setElementInfo(info.createWithNamespaceDeclarations(nd2));
                            return tp;
                        }
                    }
                }

                private boolean isPrefixTaken(String prefix)
                {
                    BindingElementInfo info = fbinding.getElementInfo();
                    final BindingElementInfo.NamespaceDeclaration[] nd = info.getNamespaceDeclarations();
                    for (int i=0;i<nd.length;i++)
                    {
                        if (nd[i].getPrefix().equals(prefix))
                        {
                            return true;
                        }
                    }
                    return false;
                }

                public Iterator getLocalPrefixes()
                {
                    BindingElementInfo info = fbinding.getElementInfo();
                    final BindingElementInfo.NamespaceDeclaration[] nd = info.getNamespaceDeclarations();

                    ArrayList temp = new ArrayList();
                    for (int i=0;i<nd.length;i++)
                    {
                        temp.add(nd[i].getPrefix());
                    }
                    return temp.iterator();
                }

                public Iterator getPrefixes()
                {
                    return getLocalPrefixes();
                }

                public NamespaceContext snapshot()
                {
                    throw new RuntimeException("Not supported");
                }
            };
            return newr;
        }
    }

    /**
     * Given the parent namespace context (<b>must be</b> from the parent binding), create a child context.
     * @param parentResolver
     * @param onBinding
     * @return
     */
    public static NamespaceContextRegistry createChildNamespaceResolver(final NamespaceContextRegistry parentResolver, Binding onBinding)
    {
        BindingElementInfo info = onBinding.getElementInfo();
        // In the case where there's nothing here, optimize (simple):
        if (info.getNamespaceDeclarations().length==0)
        {
            return parentResolver;
        }
        final BindingElementInfo.NamespaceDeclaration[] nd = info.getNamespaceDeclarations();
        // it did change.

        // WCETODO --- this could be a standard class somewhere...
        NamespaceContextRegistry newr = new NamespaceContextRegistry()
        {
            public String getPrefixForNamespaceURI(String s) throws NamespaceToPrefixResolver.NamespaceNotFoundException
            {
                for (int i=0;i<nd.length;i++)
                {
                    if (nd[i].getNamespace().equals(s))
                    {
                        return nd[i].getPrefix();
                    }
                }
                return parentResolver.getPrefixForNamespaceURI(s);
            }

            public String getNamespaceURIForPrefix(String s) throws PrefixToNamespaceResolver.PrefixNotFoundException
            {
                for (int i=0;i<nd.length;i++)
                {
                    if (nd[i].getPrefix().equals(s))
                    {
                        return nd[i].getNamespace();
                    }
                }
                return parentResolver.getNamespaceURIForPrefix(s);
            }

            public String getOrAddPrefixForNamespaceURI(String namespace)
            {
                for (int i=0;i<nd.length;i++)
                {
                    if (nd[i].getNamespace().equals(namespace))
                    {
                        return nd[i].getPrefix();
                    }
                }
                return parentResolver.getOrAddPrefixForNamespaceURI(namespace);
            }

            public String getOrAddPrefixForNamespaceURI(String namespace, String suggestedPrefix)
            {
                for (int i=0;i<nd.length;i++)
                {
                    if (nd[i].getNamespace().equals(namespace))
                    {
                        return nd[i].getPrefix();
                    }
                }
                return parentResolver.getOrAddPrefixForNamespaceURI(namespace,suggestedPrefix);
            }

            public Iterator getLocalPrefixes()
            {
                ArrayList temp = new ArrayList();
                for (int i=0;i<nd.length;i++)
                {
                    temp.add(nd[i].getPrefix());
                }
                return temp.iterator();
            }

            public Iterator getPrefixes()
            {
                throw new RuntimeException("Not supported");
            }

            public NamespaceContext snapshot()
            {
                throw new RuntimeException("Not supported");
            }
        };
        return newr;
    }

    public static ImportRegistry createImportRegistry(Binding b)
    {
        //Binding root = BindingManipulationUtils.getRoot(b);
        return new DefaultImportRegistry(); //WCETODO make imports here.
    }

    /**
     * Redeclares all namespace prefixes on the importer -- all existing declarations are removed.
     * @param fromBinding
     * @param toContextRegistry
     */
    public static void migrateNamespaceDeclarations(Binding fromBinding, NamespaceContextRegistry toContextRegistry)
    {
        BindingElementInfo.NamespaceDeclaration[] nds = fromBinding.getElementInfo().getNamespaceDeclarations();
        HashMap prefixChanges = new HashMap();
        for (int i=0;i<nds.length;i++)
        {
            BindingElementInfo.NamespaceDeclaration nd = nds[i];
            String ns = nd.getNamespace();
            String suggestedPfx;
            if (ns!=null && ns.equals(TibExtFunctions.NAMESPACE))
            {
                // (Only for nice-prefix-naming --- just to be sure, re-suggest this prefix for the 'tib' namespace):
                suggestedPfx = TibExtFunctions.SUGGESTED_PREFIX;
            }
            else if (ns!=null && ns.equals(XSDL.INSTANCE_NAMESPACE))
            {
                // (Only for nice-prefix-naming --- just to be sure, re-suggest this prefix for the 'xsi' namespace):
                suggestedPfx = "xsi";
            }
            else if (ns!=null && ns.equals(XSDL.NAMESPACE))
            {
                // (Only for nice-prefix-naming --- just to be sure, re-suggest this prefix for the 'xsi' namespace):
                suggestedPfx = "xsd";
            }
            else
            {
                suggestedPfx = nd.getPrefix();
            }
            String newPrefix = toContextRegistry.getOrAddPrefixForNamespaceURI(ns,suggestedPfx);
            if (!newPrefix.equals(nd.getPrefix()))
            {
                prefixChanges.put(nd.getPrefix(),newPrefix);
            }
        }
        if (prefixChanges.size()>0)
        {
            // Call on the root template rather than the stylesheet; rename prefix usages would otherwise do nothing
            // because the stylesheet contains prefix declarations.
            TemplateBinding tb = BindingManipulationUtils.getNthTemplate((StylesheetBinding)fromBinding,0);
            XiNode asNode = tb.asXiNode();
            NamespaceManipulationUtils.renamePrefixUsages(
                    asNode,
                    BindingNodePrefixRenamer.INSTANCE,
                    prefixChanges);
        }
        // Remove prefixes from here (do after above so that renamePrefixUsages can, for xsi:type, still see xsi).
        BindingElementInfo ninfo = new BindingElementInfo(new BindingElementInfo.NamespaceDeclaration[0],fromBinding.getElementInfo().getAdditionalAttributes());
        fromBinding.setElementInfo(ninfo);
    }
}
