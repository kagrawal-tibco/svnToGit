package com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor;

import com.tibco.cep.studio.ui.advance.event.payload.EditableTreeModel;


/**
 * Provides useful functionality for implementing {@link EditableTreeModel}
 */
public abstract class AbstractEditableTreeModel implements EditableTreeModel {
   /**
    * Use a Vector not an ArrayList for old times sake.
    */
/*   private Vector<TreeModelListener> m_listeners = new Vector<TreeModelListener>();

   *//**
    * Provides a default implelementation (may want to override for more efficient one)
    *//*
   public int getIndexOfChild(Object parent, Object child) {
      int ct = getChildCount(parent);
      for (int i = 0; i < ct; i++) {
         if (child == getChild(parent, i)) {
            return i;
         }
      }
      return -1;
   }

   *//**
    * Provides default do-nothing implementation which is usually all that is required.
    *//*
   public void valueForPathChanged(TreePath path, Object object) {
   }

   public void addTreeModelListener(TreeModelListener tml) {
      m_listeners.add(tml);
   }

   public void removeTreeModelListener(TreeModelListener tml) {
      m_listeners.remove(tml);
   }

   public void fireRemoved(Object parent, int index, Object child) {
      TreePath path = EditableTreePathUtils.getTreePath(this, parent);
      TreeModelEvent tme = new TreeModelEvent(this, path, new int[]{index}, new Object[]{child});
      for (int i = 0; i < m_listeners.size(); i++) {
         TreeModelListener tml = m_listeners.get(i);
         tml.treeNodesRemoved(tme);
      }
   }

   public void fireAdded(Object parent, Object child) {
      int index = getIndexOfChild(parent, child);
      TreePath path = EditableTreePathUtils.getTreePath(this, parent);
      TreeModelEvent tme = new TreeModelEvent(this, path, new int[]{index}, new Object[]{child});
      for (int i = 0; i < m_listeners.size(); i++) {
         TreeModelListener tml = m_listeners.get(i);
         tml.treeNodesInserted(tme);
      }
   }

   public void fireStructureChanged(Object node) {
      TreePath path = EditableTreePathUtils.getTreePath(this, node);
      TreeModelEvent tme = new TreeModelEvent(this, path);
      for (int i = 0; i < m_listeners.size(); i++) {
         TreeModelListener tml = m_listeners.get(i);
         tml.treeStructureChanged(tme);
      }
   }

   public void fireNodeChanged(Object node) {
      TreePath path = EditableTreePathUtils.getTreePath(this, node);
      TreeModelEvent tme = new TreeModelEvent(this, path);
      for (int i = 0; i < m_listeners.size(); i++) {
         TreeModelListener tml = m_listeners.get(i);
         tml.treeNodesChanged(tme);
      }
   }
*/}
