// copyright 2001 TIBCO Software Inc

package com.tibco.xml;


/**
 * Represents the set of imports, such as those specified in XML Schema and WSDL, in a context.<br>
 * The class {@link ImportRegistrySupport} provides many useful functions for fine grain manipulations;
 * this is kept deliberately simple for easy implementation, and because there are never a huge number of imports
 * (20 would be a lot of imports) -- so performance should never be an issue.
 * @author Bill Eidson
 */
public interface ImportRegistry
{
    /**
     * Gets all of the import statement contents.
     * @return The import entries, never null, but may be zero length.
     */
    ImportRegistryEntry[] getImports();

    /**
     * Sets all of the import (replacing any existing ones)
     * @param imports The imports, may not be null, but may be zero length.
     */
    void setImports(ImportRegistryEntry[] imports);
}
