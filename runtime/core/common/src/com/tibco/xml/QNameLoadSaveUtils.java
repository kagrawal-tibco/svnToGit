package com.tibco.xml;

import java.util.ArrayList;
import java.util.Iterator;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContext;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiAttribute;

/**
 * A set of utility methods for loading and saving {@link ImportRegistry} and {@link NamespaceContext}.
 */
public class QNameLoadSaveUtils
{

    /**
     * An attribute for the {@link #IMPORT} element.
     */
    public static final String IMPORT_NAMESPACE = "namespace";

    /**
     * An attribute for the {@link #IMPORT} element (for WSDL imports).
     */
    public static final String IMPORT_LOCATION = "location";

    /**
     * An attribute for the {@link #IMPORT} element (for SCHEMA imports).
     */
    public static final String IMPORT_SCHEMA_LOCATION = "schemaLocation";

    /**
     * Reads all of the namespace declarations off of the node & puts them inside the importer.
     * @param node The node
     * @return The namespace importer.
     */
    public static NamespaceImporter readNamespaces(XiNode node)
    {
        Iterator nsi = node.getNamespaces();
        NamespaceImporter nm = new DefaultNamespaceMapper();
        while (nsi.hasNext())
        {
            XiNode nsn = (XiNode) nsi.next();
            String ns = nsn.getStringValue();
            String pfx  = nsn.getName().getLocalName();
            nm.getOrAddPrefixForNamespaceURI(ns,pfx);
        }
        return nm;
    }

    /**
     * Reads all of the import children off this node into an {@link ImportRegistry}.
     * @param node The node from whose children imports are read.
     * @param importName The name of the import tag in this context.
     * @return The import registry, never null even if empty.
     */
    public static ImportRegistry readImports(XiNode node, ExpandedName importName)
    {
        XiNode c = node.getFirstChild();
        DefaultImportRegistry reg = new DefaultImportRegistry();
        ArrayList imports = new ArrayList();
        while (c!=null)
        {
            if (c.getName().equals(importName))
            {
                String ns = XiAttribute.getStringValue(c,IMPORT_NAMESPACE);
                String loc = XiAttribute.getStringValue(c,IMPORT_LOCATION);
                if (loc==null)
                {
                    loc = XiAttribute.getStringValue(c,IMPORT_SCHEMA_LOCATION);
                }
                if (ns!=null)
                {
                    imports.add(new ImportRegistryEntry(ns,loc));
                }
            }
            c = c.getNextSibling();
        }
        ImportRegistrySupport.setFromList(reg,imports);
        return reg;
    }

    public static void writeNamespaces(XiNode node, NamespaceContext nm)
    {
        Iterator i = nm.getPrefixes();
        while (i.hasNext())
        {
            String pfx = (String) i.next();
            try
            {
                String ns = nm.getNamespaceURIForPrefix(pfx);
                node.setNamespace(node.getNodeFactory().createNamespace(pfx,ns));
            }
            catch (PrefixToNamespaceResolver.PrefixNotFoundException e)
            {
                // can't happen.
                e.printStackTrace(System.err);
            }
        }
    }

    /**
     * Same as version below but hardcodes schemaLocation; deprecate later.
     */
    public static void writeImports(XiNode node, ImportRegistry importRegistry, ExpandedName importName)
    {
        writeImports(node,importRegistry,importName,"schemaLocation");
    }

    /**
     * Writes the import statements by appending new children on the specified node.
     * @param node The node to which to add import children.
     * @param importRegistry The imports to be written, may not be null.
     * @param importName The name for the import tag.
     * @param locationAttrName The name of the attribute use for the 'location'.
     */
    public static void writeImports(XiNode node, ImportRegistry importRegistry, ExpandedName importName, String locationAttrName)
    {
        ImportRegistryEntry[] entries = importRegistry.getImports();
        for (int i=0;i<entries.length;i++)
        {
            ImportRegistryEntry entry = entries[i];
            XiNode importEl = node.getNodeFactory().createElement(importName);
            XiAttribute.setStringValue(importEl,IMPORT_NAMESPACE,entry.getNamespaceURI());
            XiAttribute.setStringValue(importEl,locationAttrName,entry.getLocation());
            node.appendChild(importEl);
        }
    }
}
