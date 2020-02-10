package com.tibco.cep.studio.mapper.ui.edittree.simple;

import com.tibco.cep.studio.mapper.ui.edittree.AbstractEditableTreeModel;

/**
 * An editable tree model base class which works with {@link SimpleTreeNode}.<br>
 * This model features a simple lazy-expansion model.<br>
 * This model does automatically does event notification.<br>
 */
public class SimpleTreeModel extends AbstractEditableTreeModel {
   private SimpleTreeNode m_root;

   private static class EmptyNode extends SimpleTreeNode {
      public EmptyNode() {
      }

//      public String getName() {
//         return "empty";
//      }

      public SimpleTreeNode[] buildChildren() {
         return new SimpleTreeNode[0];
      }

      public boolean isLeaf() {
         return true;
      }
   }

   public SimpleTreeModel() {
      m_root = new EmptyNode();
   }

   public SimpleTreeModel(SimpleTreeNode node) {
      m_root = node;
      if (node != null) {
         node.m_model = this;
      }
   }

   public final Object getRoot() {
      return m_root;
   }

   public final int getIndexOfChild(Object parent, Object child) {
      int ct = getChildCount(parent);
      for (int i = 0; i < ct; i++) {
         if (child == getChild(parent, i)) {
            return i;
         }
      }
      return -1;
   }

   public final int getChildCount(Object object) {
      SimpleTreeNode in = (SimpleTreeNode) object;
      return in.getChildCount();
   }

   public Object getDeleteReplacement(Object node) {
      return null;
   }

   public final Object getChild(Object object, int index) {
      SimpleTreeNode in = (SimpleTreeNode) object;
      return in.getChild(index);
   }

   public final boolean isLeaf(Object object) {
      if (object == null) {
         return true;
      }
      SimpleTreeNode in = (SimpleTreeNode) object;
      return in.isLeaf();
   }

   public final Object getParent(Object child) {
      SimpleTreeNode n = (SimpleTreeNode) child;
      return n.getParent();
   }

   public final void addAt(Object parent, int index, Object newChild) {
      SimpleTreeNode parentn = (SimpleTreeNode) parent;
      SimpleTreeNode c = (SimpleTreeNode) newChild;
      parentn.getChildCount(); // Force the building of the children (if not already build), so that the call to addedAt, later, won't accidentally recompute that.
      if (parentn.addAt(index, c)) {
         parentn.addedAt(index, c);
         fireAdded(parentn, c);
      }
   }

   public final void remove(Object parent, int index) {
      SimpleTreeNode n = (SimpleTreeNode) parent;
      SimpleTreeNode ch = n.getChild(index);
      if (n.remove(index)) {
         n.removed(index);
         super.fireRemoved(n, index, ch);
      }
   }

   /**
    * Implementation detail; sets the model on all new nodes.
    *
    * @param node
    */
   void copyModelTo(SimpleTreeNode node) {
      node.m_model = this;
      if (node.hasBeenExpanded()) {
         for (int i = 0; i < node.getChildCount(); i++) {
            copyModelTo(node.getChild(i));
         }
      }
   }

   public void setRoot(Object node) {
      m_root = (SimpleTreeNode) node;
      copyModelTo(m_root);
      fireStructureChanged(node);
   }

   public boolean canHaveChildren(Object node) {
      return false;
   }

   public Object createNewChild(Object parent) {
      return null;
   }

   public Object createNewParent(Object optionalAroundNode) {
      return null;
   }

   public boolean getAllowsRootNull() {
      return false;
   }

   public boolean isRootNull() {
      return false;
   }

   public void setRootNull(boolean nullRoot) {
   }
}
