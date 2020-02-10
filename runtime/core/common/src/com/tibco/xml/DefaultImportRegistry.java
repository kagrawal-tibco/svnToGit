// copyright 2001 TIBCO Software Inc

package com.tibco.xml;

/**
 * A default implementation of {@link ImportRegistry} which simply stores the imports in a list.
 */
public class DefaultImportRegistry implements ImportRegistry
{
    private ImportRegistryEntry[] m_imports;

    public DefaultImportRegistry()
    {
        m_imports = new ImportRegistryEntry[0];
    }

    public ImportRegistryEntry[] getImports()
    {
        return (ImportRegistryEntry[]) m_imports.clone();
    }

    public void setImports(ImportRegistryEntry[] imports)
    {
        if (imports==null)
        {
            throw new NullPointerException();
        }
        m_imports = imports;
    }

    /**
     * For diagnostic/debugging purposes only.
     * @return
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<m_imports.length;i++)
        {
            if (i>0) sb.append(", ");
            sb.append(m_imports[i]);
        }
        return sb.toString();
    }
}
