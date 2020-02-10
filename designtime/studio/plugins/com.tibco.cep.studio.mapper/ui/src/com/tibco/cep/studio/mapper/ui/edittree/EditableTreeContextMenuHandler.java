package com.tibco.cep.studio.mapper.ui.edittree;

import javax.swing.JPopupMenu;


/**
 * Works with {@link EditableTree} to allow plug-able right-click menu behavior on a per-node basis.<br>
 */
public interface EditableTreeContextMenuHandler {
   /**
    * Adds any additional context menu items associated with the tree node.<br>
    * (Many default operations are provided based on the tree's settings).
    *
    * @param node The tree node from the {@link EditableTreeModel}.
    */
   void addContextMenuItems(Object node, JPopupMenu menu);
}

