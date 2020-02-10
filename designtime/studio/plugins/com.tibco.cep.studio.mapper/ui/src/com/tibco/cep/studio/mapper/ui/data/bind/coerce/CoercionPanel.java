package com.tibco.cep.studio.mapper.ui.data.bind.coerce;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.xpath.Coercion;
import com.tibco.cep.mapper.xml.xdata.xpath.CoercionSet;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.Utilities;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataTree;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeActions;
import com.tibco.cep.studio.mapper.ui.edittree.MasterDetailsTree;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;

/**
 * The panel used for the coercion editing.
 */
class CoercionPanel extends MasterDetailsTree {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private NamespaceContextRegistry m_namespaceContextRegistry;
   private CoercionPanelDetails m_details;
   private DataTree m_tree;

   public CoercionPanel(UIAgent uiAgent,
                        DataTree dt,
                        CoercionSet initialSet, String xpath,
                        ExprContext exprContext, ImportRegistry ir) {
      super(new CoercionXPathList(uiAgent, initialSet, xpath, exprContext));
      m_tree = dt;
      m_details = new CoercionPanelDetails(uiAgent, getTree(), exprContext.getNamespaceMapper(), ir);
      setDetails(m_details);
      m_namespaceContextRegistry = exprContext.getNamespaceMapper();

      // Clicking on a coercion shows that in the LHS tree:
      getTree().addTreeSelectionListener(new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent e) {
            showCoercionInTree();
         }
      });
      showCoercionInTree();
   }

   private void showCoercionInTree() {
      // expand/select the node for that xpath in the data tree.
      TreePath path = getTree().getSelectionPath();
      if (path != null && path.getParentPath() != null) // If there's no parent, it's the root, which we don't care about.
      {
         CoercionXPathList.Node n = (CoercionXPathList.Node) path.getLastPathComponent();
         String xpath = n.getXPath();
         String[] steps = Utilities.getAsArray(xpath);
         if (steps != null) // can be null if bad xpath.
         {
            TreePath tp = BasicTreeActions.getTreePathFromXPath(m_tree, steps, m_namespaceContextRegistry);
            if (tp != null) {
               m_tree.expandPath(tp);
               m_tree.scrollPathToVisible(tp);
               m_tree.setSelectionPath(tp);
            }
         }
      }

   }

   public void setEditable(boolean editable) {
      super.getTree().setTreeEditable(editable);
      m_details.setEditable(editable);
   }

   public void rebuildSet(CoercionSet set) {
      set.clear();
      CoercionXPathList l = (CoercionXPathList) super.getTree();
      DefaultMutableTreeNode n = (DefaultMutableTreeNode) l.getModel().getRoot();
      for (int i = 0; i < n.getChildCount(); i++) {
         CoercionXPathList.Node c = (CoercionXPathList.Node) n.getChildAt(i);
         Coercion cc = c.createCoercion(m_namespaceContextRegistry);
         set.add(cc);
      }
   }
}

