package com.tibco.cep.studio.mapper.ui.edittree;

import java.util.Iterator;

import javax.swing.tree.TreePath;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.data.primitive.XmlNodeTest;
import com.tibco.xml.data.primitive.XmlTreeNode;
import com.tibco.xml.datamodel.XiNode;

/**
 * A 1-level XiNode tree model which filters on a specific name.
 */
@SuppressWarnings("rawtypes")
public class XiNodeEditableTreeModel extends AbstractEditableTreeModel {
   private XiNode m_root;
   private final ExpandedName m_childFilter;

   public XiNodeEditableTreeModel(XiNode root, ExpandedName childFilter) {
      m_root = root;
      m_childFilter = childFilter;
   }

   public void addAt(Object parent, int index, Object newChild) {
      XiNode p = (XiNode) parent;
      XiNode newChildNode = (XiNode) newChild;
      XiNode c = findChildAtIndex(p, index);
      if (c == null) {
         p.appendChild(newChildNode);
      }
      else {
         p.insertBefore(newChildNode, c);
      }
      super.fireAdded(parent, newChildNode);
   }

   public boolean canHaveChildren(Object node) {
      return node == m_root;
   }

   public Object createNewChild(Object parent) {
      return m_root.getNodeFactory().createElement(m_childFilter);
   }

   public Object createNewParent(Object optionalAroundNode) {
      return null; // n/a for now.
   }

   public Object getDeleteReplacement(Object node) {
      return null;
   }

   public Object getParent(Object child) {
      XiNode n = (XiNode) child;
      if (n == m_root) {
         return null;
      }
      return n.getParentNode();
   }

   public void remove(Object parent, int index) {
      XiNode pnode = (XiNode) parent;
      XiNode child = findChildAtIndex(pnode, index);
      if (child == null) {
         return;
      }
      pnode.removeChild(child);
      super.fireRemoved(parent, index, child);
   }

public Object getChild(Object parent, int index) {
      XiNode n = (XiNode) parent;
      if (n != m_root) {
         return null;
      }
      Iterator i = getChildrenIterator(n);
      int ct = index;
      while (i.hasNext()) {
         Object obj = i.next();
         if (ct-- == 0) {
            return obj;
         }
      }
      return null;
   }

   public int getChildCount(Object parent) {
      XiNode n = (XiNode) parent;
      if (n != m_root) {
         return 0;
      }
      Iterator i = getChildrenIterator(n);
      int ct = 0;
      while (i.hasNext()) {
         ct++;
         i.next();
      }
      return ct;
   }

   public int getIndexOfChild(Object parent, Object child) {
      XiNode n = (XiNode) parent;
      if (n != m_root) {
         return -1;
      }
      Iterator i = getChildrenIterator(n);
      int ct = 0;
      while (i.hasNext()) {
         Object no = i.next();
         if (no == child) {
            return ct;
         }
         ct++;
      }
      return -1;
   }

   public Object getRoot() {
      return m_root;
   }

   public boolean isLeaf(Object node) {
      return node != m_root;
   }

   public void valueForPathChanged(TreePath path, Object newValue) {
      // do nothing.
   }

   private XiNode findChildAtIndex(XiNode parent, int index) {
      Iterator i = getChildrenIterator(parent);
      int at = index;
      while (i.hasNext()) {
         Object x = i.next();
         if (at-- == 0) {
            return (XiNode) x;
         }
      }
      return null;
   }

   private Iterator getChildrenIterator(XiNode parent) {
      return parent.getChildren(new XmlNodeTest() {
         public boolean isNoOp() {
            return false;
         }

         public boolean matches(XmlTreeNode node) {
            if (node.getNodeKind() == XmlNodeKind.ELEMENT) {
               if (node.getName().equals(m_childFilter)) {
                  return true;
               }
            }
            return false;
         }
      });
   }

   /**
    * Does not allow null root currently.
    */
   public boolean getAllowsRootNull() {
      return false;
   }

   /**
    * n/a because does not allow null root currently.
    */
   public boolean isRootNull() {
      return false;
   }

   /**
    * n/a because does not allow null root currently.
    */
   public void setRootNull(boolean nullRoot) {
   }

   public void setRoot(Object node) {
      throw new RuntimeException("NYI");/*
       m_root = (XiNode) node;
       super.fireStructureChanged(node);*/
   }
}

