package com.tibco.cep.mapper.xml.nsutils;

import java.util.Map;

import com.tibco.xml.datamodel.XiNode;

/**
 * For use in {@link NamespaceManipulationUtils#renamePrefixUsages}.
 * Used where an implementation stores data with prefixes (where it isn't this isn't required)<br>.
 * For example, an XPath might be stored as just a String.
 */
public interface NodePrefixRenamer
{
    /**
     * @param node The node
     * @param oldToNewPrefixMap The old (String) prefix to new (String) prefix map.
     */
    public void renameLocalPrefixUsages(XiNode node, Map oldToNewPrefixMap);
}
