package com.tibco.cep.studio.ui.advance.event.payload;

//import javax.swing.tree.TreePath;

/**
 * An abstract base class for a tree structure, works with {@link SimpleTreeModel}.
 */
public abstract class SimpleTreeNode {
   private SimpleTreeNode m_parent;
//   SimpleTreeModel m_model; // set by model.
   private SimpleTreeNode[] m_children;

   public SimpleTreeNode() {
   }

/*   public SimpleTreeModel getModel() {
      return m_model;
   }
*/
   /**
    * Gets the {@link TreePath} for this node.
    *
    * @return The treepath, never null.
    */
   /*public final TreePath getTreePath() {
      if (m_parent == null) {
         return new TreePath(this);
      }
      else {
         return m_parent.getTreePath().pathByAddingChild(this);
      }
   }*/

   public final boolean hasBeenExpanded() {
      return m_children != null;
   }

   public abstract SimpleTreeNode[] buildChildren();

   public int getChildCount() {
      // Do not do this optimization: if (isLeaf()) {
      //    return 0;
      //}.. makes it tough for simpl
      if (m_children == null) {
         /* Not true for parameter editor right now, fix...if (mModel==null) {
             throw new NullPointerException("Model not set on " + this);
         }*/
         m_children = buildChildren();
         for (int i = 0; i < m_children.length; i++) {
 //           m_children[i].m_model = m_model;
            m_children[i].m_parent = this;
         }
      }
      return m_children.length;
   }

   public SimpleTreeNode getChild(int index) {
      if (m_children == null) {
         getChildCount(); // builds 'em.
      }
      return m_children[index];
   }

   /**
    * Indicates if the node can legally have children; a leaf does not necessarily mean !{@link #canHaveChildren}, it may
    * indicate that it's a leaf 'right-now' (and for display purposes, it's usually better to say isLeaf when it has no children)
    */
   public boolean canHaveChildren() {
      return !isLeaf();
   }

   /**
    * Indicates if the node is a leaf; a leaf does not necessarily mean !{@link #canHaveChildren}, it may
    * indicate that it's a leaf 'right-now' (and for display purposes, it's usually better to say isLeaf when it has no children)
    */
   public abstract boolean isLeaf();

   public SimpleTreeNode getParent() {
      return m_parent;
   }

   public final int getIndexOfChild(Object child) {
      if (isLeaf()) {
         return 0;
      }
      int ct = getChildCount();
      for (int i = 0; i < ct; i++) {
         if (child == getChild(i)) {
            return i;
         }
      }
      return -1;
   }

   /**
    * Called when this node is to be removed.<br>
    * Called before children are adjusted, return 'true' if remove is ok, 'false' if not.
    */
   public boolean remove(int index) {
      return false;
   }

   /**
    * Called before children are adjusted, return 'true' if add is ok, 'false' if not.
    */
   public boolean addAt(int index, SimpleTreeNode value) {
      return false;
   }

   public boolean replace(int index, SimpleTreeNode value) {
      return false;
   }

   /**
    * Internal to tree.
    */
   final void removed(int index) {
      SimpleTreeNode[] c = new SimpleTreeNode[m_children.length - 1];
      int j = 0;
      for (int i = 0; i < m_children.length; i++) {
         if (i != index) {
            c[j] = m_children[i];
            j++;
         }
      }
      m_children[index].m_parent = null;
 //     m_children[index].m_model = null;
      m_children = c;
   }

   /**
    * Sort of a hack for type editor, remove later..
    */
   protected void fireStructureChanged() {
    /*  if (m_model != null) {
         m_model.fireStructureChanged(this);
      }*/
   }

   /**
    * Subclasses call this when the subclass changes the children.
    */
   protected final void childrenChanged() {
      m_children = null;
      fireStructureChanged();
   }

   /**
    * Internal to tree (can be used to build structure...)
    */
   protected final void addedAt(int index, SimpleTreeNode node) {
      int cc = getChildCount(); // make sure mChildren not null.
      SimpleTreeNode[] c = new SimpleTreeNode[cc + 1];
      int j = 0;
      for (int i = 0; i < c.length; i++) {
         if (i != index) {
            c[i] = m_children[j];
            j++;
         }
         else {
            c[i] = node;
         }
      }
      node.m_parent = this;
 /*     if (m_model != null) {
         m_model.copyModelTo(node);
      }
 */     m_children = c;
   }
}
