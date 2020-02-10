package com.tibco.cep.studio.mapper.ui.data.basic;

import java.util.Vector;

import javax.swing.Icon;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.tibco.cep.studio.mapper.ui.data.DataIcons;

public class BasicTreeModel implements TreeModel {
   private BasicTreeNode mRoot;
   private Vector<TreeModelListener> mListeners = new Vector<TreeModelListener>();
   private final BasicTree mTree;

   private static class EmptyNode extends BasicTreeNode {
      public EmptyNode() {
      }

      public String getName() {
         return "empty";
      }

      public BasicTreeNode[] buildChildren() {
         return new BasicTreeNode[0];
      }

      public boolean isLeaf() {
         return true;
      }

      public Icon getIcon() {
         return DataIcons.getIfIcon();
      }

      public boolean isEditable() {
         return false;
      }

      public Object getIdentityTerm() {
         return null;
      }
   }

   public BasicTreeModel(BasicTree tree) {
      mRoot = new EmptyNode();
      mTree = tree;
   }

   public BasicTreeModel(BasicTree tree, BasicTreeNode node) {
      mRoot = node;
      if (node != null) {
         node.mModel = this;
      }
      mTree = tree;
   }

   public void setRoot(BasicTreeNode root) {
      mRoot = root;
      root.mModel = this;
   }

   public Object getRoot() {
      return mRoot;
   }

   public BasicTree getTree() {
      return mTree;
   }

   public int getIndexOfChild(Object parent, Object child) {
      int ct = getChildCount(parent);
      for (int i = 0; i < ct; i++) {
         if (child == getChild(parent, i)) {
            return i;
         }
      }
      return -1;
   }

   public int getChildCount(Object object) {
      BasicTreeNode in = (BasicTreeNode) object;
      return in.getChildCount();
   }

   public Object getChild(Object object, int index) {
      BasicTreeNode in = (BasicTreeNode) object;
      return in.getChild(index);
   }

   public boolean isLeaf(Object object) {
      if (object == null) {
         return true;
      }
      BasicTreeNode in = (BasicTreeNode) object;
      return in.isLeaf();
   }

   public void valueForPathChanged(TreePath path, Object object) {
   }

   public void addTreeModelListener(TreeModelListener tml) {
      mListeners.add(tml);
   }

   public void removeTreeModelListener(TreeModelListener tml) {
      mListeners.remove(tml);
   }

   void fireRemoved(BasicTreeNode parent, int index, BasicTreeNode child) {
      TreePath path = parent.getTreePath();
      TreeModelEvent tme = new TreeModelEvent(this, path, new int[]{index}, new Object[]{child});
      for (int i = 0; i < mListeners.size(); i++) {
         TreeModelListener tml = mListeners.get(i);
         tml.treeNodesRemoved(tme);
      }
   }

   void fireAdded(BasicTreeNode parent, BasicTreeNode child) {
      int index = parent.getIndexOfChild(child);
      TreePath path = parent.getTreePath();
      TreeModelEvent tme = new TreeModelEvent(this, path, new int[]{index}, new Object[]{child});
      for (int i = 0; i < mListeners.size(); i++) {
         TreeModelListener tml = mListeners.get(i);
         tml.treeNodesInserted(tme);
      }
   }

   public void fireStructureChanged(BasicTreeNode node) {
      TreePath path = node.getTreePath();
      TreeModelEvent tme = new TreeModelEvent(this, path);
      for (int i = 0; i < mListeners.size(); i++) {
         TreeModelListener tml = mListeners.get(i);
         tml.treeStructureChanged(tme);
      }
   }

   public void fireNodeChanged(BasicTreeNode node) {
      TreePath path = node.getTreePath();
      TreeModelEvent tme = new TreeModelEvent(this, path);
      for (int i = 0; i < mListeners.size(); i++) {
         TreeModelListener tml = mListeners.get(i);
         tml.treeNodesChanged(tme);
      }
   }

   void copyModelTo(BasicTreeNode node) {
      node.mModel = this;
      if (node.hasBeenExpanded()) {
         for (int i = 0; i < node.getChildCount(); i++) {
            copyModelTo(node.getChild(i));
         }
      }
   }
}
