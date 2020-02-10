package com.tibco.cep.studio.mapper.ui.data.basic;

import javax.swing.tree.TreePath;


/**
 * More specific tree model events.
 */
public interface ExtendedTreeModelListener {
   /**
    * @param offset The direction and size of the move, i.e. -1 = move up 1, +2 = move down 2.
    */
   public void treeNodesMovedInParent(TreePath parentPath, int[] children, int offset);
}

