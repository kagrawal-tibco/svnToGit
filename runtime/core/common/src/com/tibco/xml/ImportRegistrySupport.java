// copyright 2001 TIBCO Software Inc

package com.tibco.xml;

import java.util.ArrayList;
import java.util.List;


/**
 * Utility methods for fine grain manipulations with a {@link ImportRegistry}.
 * @author Bill Eidson
 */
public class ImportRegistrySupport
{

    /**
     * Gets all the locations that a particular namespace is imported from.<br>
     * Returning more than 1 entry <b>is</b> a normal condition.
     * @param namespaceURI The namespace to check.
     * @return The set of locations, never null (instead returns a zero length array).
     */
    public static String[] getLocationsForNamespaceURI(ImportRegistry importRegistry, String namespaceURI)
    {
        ArrayList ret = new ArrayList();
        ImportRegistryEntry[] ie = importRegistry.getImports();
        for (int i=0;i<ie.length;i++)
        {
            ImportRegistryEntry im = ie[i];
            if (im.getNamespaceURI().equals(namespaceURI) && im.getLocation() != null)
            {
                ret.add(im.getLocation());
            }
        }
        return (String[]) ret.toArray(new String[ret.size()]);
    }

    /**
     * Gets all the namespaces that a particular location is imported from.<br>
     * Returning more than 1 entry <b>is</b> a normal condition.
     * @param location The location to check.
     * @return The set of namespace URIs, never null (instead returns a zero length array).
     */
    public static String[] getNamespaceURIsForLocation(ImportRegistry importRegistry, String location)
    {
        ArrayList ret = new ArrayList();
        ImportRegistryEntry[] ie = importRegistry.getImports();
        for (int i=0;i<ie.length;i++)
        {
            ImportRegistryEntry im = ie[i];
            if (location.equals(im.getLocation()))
            {
                ret.add(im.getNamespaceURI());
            }
        }
        return (String[]) ret.toArray(new String[ret.size()]);
    }

    /**
     * Checks if the registry already contains a matching entry.
     * @param entry The import.
     */
    public static boolean containsImport(ImportRegistry importRegistry, ImportRegistryEntry entry)
    {
        if (entry==null)
        {
            throw new NullPointerException();
        }
        ImportRegistryEntry[] ie = importRegistry.getImports();
        for (int i=0;i<ie.length;i++)
        {
            ImportRegistryEntry e = ie[i];
            if (e.equals(entry))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an additional import statement at the end of the list.<br>
     * Does no duplicate checking.
     * @param entry The import.
     */
    public static void addImport(ImportRegistry importRegistry, ImportRegistryEntry entry)
    {
        if (entry==null)
        {
            throw new NullPointerException();
        }
        ArrayList imports = getAsArray(importRegistry);
        imports.add(entry);
        setFromList(importRegistry,imports);
    }

    /**
     * Adds an additional import statement at the end of the list if the import is not already present.<br>
     * Essentially a {@link #containsImport} optionally followed by a {@link #addImport}.
     * @param entry The import.
     */
    public static void addImportIfNotExists(ImportRegistry importRegistry, ImportRegistryEntry entry)
    {
        if (entry==null)
        {
            throw new NullPointerException();
        }
        if (!containsImport(importRegistry,entry))
        {
            addImport(importRegistry,entry);
        }
    }

    /**
     * Removes an import statement.
     * @param entry The entry to remove (comparison done by equals, not ==)
     */
    public static void removeImport(ImportRegistry importRegistry, ImportRegistryEntry entry)
    {
        ImportRegistryEntry[] ie = importRegistry.getImports();
        ArrayList al = new ArrayList();
        for (int i=0;i<ie.length;i++)
        {
            ImportRegistryEntry e = ie[i];
            if (e.equals(entry))
            {
                // remove it.
            }
            else
            {
                // keep it.
                al.add(e);
            }
        }
        if (al.size()==ie.length)
        {
            return; // did nothing.
        }
        setFromList(importRegistry,al);
        importRegistry.setImports((ImportRegistryEntry[])al.toArray(new ImportRegistryEntry[al.size()]));
    }

    /**
     * Internal utility, sets the imports from a list containing {@link ImportRegistryEntry}.
     */
    public static void setFromList(ImportRegistry importRegistry, List list)
    {
        importRegistry.setImports((ImportRegistryEntry[])list.toArray(new ImportRegistryEntry[list.size()]));
    }

    /**
     * Internal utility, gets the imports into a list containing {@link ImportRegistryEntry}.
     */
    private static ArrayList getAsArray(ImportRegistry importRegistry)
    {
        ArrayList al = new ArrayList();
        ImportRegistryEntry[] ie = importRegistry.getImports();
        for (int i=0;i<ie.length;i++)
        {
            al.add(ie[i]);
        }
        return al;
    }
}
