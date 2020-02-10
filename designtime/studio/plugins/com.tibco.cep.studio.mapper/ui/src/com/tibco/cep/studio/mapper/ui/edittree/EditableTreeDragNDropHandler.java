package com.tibco.cep.studio.mapper.ui.edittree;


/**
 * Works with {@link EditableTree} to allow plug-able drag'n'drop behavior.<br>
 * Note: The current drag'n'drop does not use the AWT 'heavy' mechanism, but a lightweight drag'n'drop version,
 * this is why objects can be used here. Future versions may use AWT 'heavy' mechanism for {@link java.awt.datatransfer.Transferable}
 * objects.
 */
public interface EditableTreeDragNDropHandler {
   /**
    * Gets the drag object associated with the tree node.
    *
    * @param node The tree node from the {@link EditableTreeModel}.
    * @return The object representing this node in a drag'n'drop, or null if none.
    */
   Object getDragObjectForNode(Object node);

   /**
    * Creates a new unconnected node from a drag object.
    *
    * @param dragObject The tree node from the {@link EditableTreeModel}.
    * @return The new node, or null for none.
    */
   Object createNodeFromDragObject(Object dragObject);
}

