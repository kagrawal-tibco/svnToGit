package com.tibco.cep.studio.mapper.ui.edittree;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

/**
 * A {@link EditableTreeModel} which works with {@link javax.swing.tree.MutableTreeNode}.
 */
public abstract class DefaultTreeNodeEditableTreeModel extends DefaultTreeModel implements EditableTreeModel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public DefaultTreeNodeEditableTreeModel(MutableTreeNode root) {
      super(root);
   }

   public void addAt(Object parent, int index, Object newChild) {
      MutableTreeNode pnode = (MutableTreeNode) parent;
      pnode.insert((MutableTreeNode) newChild, index);
      super.nodesWereInserted(pnode, new int[]{index});
   }

   public boolean canHaveChildren(Object node) {
      return true;
   }

   public Object createNewParent(Object optionalAroundNode) {
      return null; // n/a for now.
   }

   public Object getDeleteReplacement(Object node) {
      return null;
   }

   public Object getParent(Object child) {
      MutableTreeNode n = (MutableTreeNode) child;
      return n.getParent();
   }

   public void remove(Object parent, int index) {
      MutableTreeNode node = (MutableTreeNode) parent;
      Object child = node.getChildAt(index);
      node.remove(index);
      super.nodesWereRemoved(node, new int[]{index}, new Object[]{child});
   }

   /**
    * Default implementation is no, does not allow null root.
    *
    * @return
    */
   public boolean getAllowsRootNull() {
      return false;
   }

   /**
    * Since {@link #getAllowsRootNull} is false, this is n/a
    */
   public boolean isRootNull() {
      return false;
   }

   /**
    * Since {@link #getAllowsRootNull} is false, this is n/a
    */
   public void setRootNull(boolean nullRoot) {
   }

   public void setRoot(Object node) {
      throw new RuntimeException("Not yet implemented");
   }

   public TreeModelListener[] getTreeModelListeners() {
      return (TreeModelListener[]) listenerList.getListeners(TreeModelListener.class);
   }
}

