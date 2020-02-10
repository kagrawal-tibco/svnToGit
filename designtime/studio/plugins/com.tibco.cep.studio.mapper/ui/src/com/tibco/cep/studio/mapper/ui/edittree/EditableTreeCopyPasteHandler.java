package com.tibco.cep.studio.mapper.ui.edittree;

import java.awt.datatransfer.Transferable;

import org.xml.sax.SAXException;


/**
 * Works with {@link EditableTree} to allow plug-able cut/copy/paste behavior on a per-node basis.
 */
public interface EditableTreeCopyPasteHandler {
   /**
    * Gets the drag object associated with the tree node.
    *
    * @param node The tree node from the {@link EditableTreeModel}.
    * @return The object representing this node in a drag'n'drop, or null if none.
    */
   Transferable getTransferableForNode(Object node);

   /**
    * Creates a new unconnected node from a drag object.
    *
    * @param dragObject The tree node from the {@link EditableTreeModel}.
    * @return The new node, or null for none.
    * @throws SAXException Thrown if the content can't be parsed in (the choice of SAXException was arbitrary),
    *                      the message should contains either empty string (for a generic cannot use) or a message indicating why the content
    *                      is bad.
    */
   Object createNodeFromTransferable(Transferable dragObject) throws SAXException;
}

