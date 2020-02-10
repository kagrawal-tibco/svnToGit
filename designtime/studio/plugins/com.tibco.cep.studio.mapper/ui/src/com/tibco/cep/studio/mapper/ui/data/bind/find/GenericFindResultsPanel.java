package com.tibco.cep.studio.mapper.ui.data.bind.find;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.xmlui.QNamePanelResources;

/**
 * The generic floating dialog that shows documentation.
 */
class GenericFindResultsPanel extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JList mLocalResults; // for local mode.
   private JTree mGlobalResults; // for global mode.
   private JPanel mShowPanel;
   private FindWindowPlugin mPlugin;
   private GenericFindSelectionHandler m_selectionHandler;
   private boolean m_singleClickSelect;

   private boolean mIsGlobal;

   public GenericFindResultsPanel(FindWindowPlugin plugin, GenericFindSelectionHandler handler, boolean singleClickSelect, boolean isGlobal) {
      super(new BorderLayout());
      m_selectionHandler = handler;
      mPlugin = plugin;
      mIsGlobal = isGlobal;
      m_singleClickSelect = singleClickSelect;
      if (!mIsGlobal) {
         mLocalResults = new JList();
         mLocalResults.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
               selChanged();
            }
         });
         mLocalResults.setCellRenderer(new ListCellRenderer() {
            public Component getListCellRendererComponent(JList list,
                                                          Object value,
                                                          int index,
                                                          boolean isSelected,
                                                          boolean cellHasFocus) {
               FindWindowResult r = (FindWindowResult) value;
               return mPlugin.getResultRendererComponent(r, isSelected);
            }
         });
      }
      else {
         mGlobalResults = new JTree();
         mGlobalResults.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
               if (e.getClickCount() == 2) {
                  doubleClickOn();
               }
            }
         });
         mGlobalResults.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
               selChanged();
            }
         });
         mGlobalResults.setCellRenderer(new TreeCellRenderer() {
            private JLabel mLabel = new JLabel();

            public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                          boolean selected, boolean expanded,
                                                          boolean leaf, int row, boolean hasFocus) {
               DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
               if (node.isRoot()) {
                  mLabel.setText("Root!"); // text not visible; doesn't matter.
                  return mLabel;
               }
               if (!leaf) {
                  String path = (String) node.getUserObject();
                  int ct = node.getChildCount();
                  String oc = DataIcons.getOccurrencesLabel(ct > 1);
                  String msg = "<html>" + path + " <i>(" + ct + " " + oc + ")</i>";
                  mLabel.setText(msg);
                  return mLabel;
               }
               Object result = node.getUserObject();
               return mPlugin.getResultRendererComponent(result, selected);
            }
         });
         mGlobalResults.setRootVisible(false);
         mGlobalResults.setShowsRootHandles(true);
      }
      mShowPanel = new JPanel(new BorderLayout());
      add(mShowPanel);
   }

   public void refresh() {
      if (!doubleClickOn()) {
         m_selectionHandler.show(null, mPlugin, null);
      }
   }

   public void setBlank(String reason) {
      mShowPanel.removeAll();
      JLabel reasonLabel = new JLabel("<html>" + reason);
      mShowPanel.add(new JScrollPane(reasonLabel), BorderLayout.NORTH);
      mShowPanel.setOpaque(false);
      mShowPanel.revalidate();
      mShowPanel.repaint();
   }

   public void setRunning() {
      mShowPanel.removeAll();
      //WCETODO --- to make it less jarring, put in some sort of slight delay so that, if the search is really quick
      // (which it usually is) that it doesn't flash.

      // Set background to same as tree so it isn't quite so 'flashy'
      JLabel running = new JLabel("<html>" + QNamePanelResources.SEARCHING);
      mShowPanel.setOpaque(true);
      Color back = new JTree().getBackground(); // I really love writing inefficient code where it doesn't matter!
      mShowPanel.setBackground(back);
      running.setBackground(back);
      JScrollPane jsp = new JScrollPane(running);
      mShowPanel.add(jsp, BorderLayout.NORTH);
      mShowPanel.revalidate();
      mShowPanel.repaint();
   }

   /**
    * May be invoked in a non-swing thread (it will handle this ok)
    *
    * @param results
    */
   public void setResults(FindWindowResult[] results) {
      if (mIsGlobal) {
         setGlobalResults(results);
      }
      else {
         setLocalResults(results);
      }
   }

   private void setLocalResults(FindWindowResult[] results) {
      // because this may be invoked on another thread...
      final DefaultListModel dlm = new DefaultListModel();
      for (int i = 0; i < results.length; i++) {
         dlm.addElement(results[i]);
      }
      // Local search:
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            synchronized (GenericFindResultsPanel.this) {
               mShowPanel.removeAll();
               mShowPanel.add(new JScrollPane(mLocalResults));
               mLocalResults.setModel(dlm);
               mShowPanel.revalidate();
               mShowPanel.repaint();
            }
         }
      });
   }

   private void setGlobalResults(FindWindowResult[] results) {
      // because this may be invoked on another thread...
      DefaultMutableTreeNode root = new DefaultMutableTreeNode();
      String currentRes = null; // none.
      DefaultMutableTreeNode currentNode = null;
      for (int i = 0; i < results.length; i++) {
         FindWindowResult fwr = results[i];
         if (!fwr.getLocation().equals(currentRes)) {
            currentNode = new DefaultMutableTreeNode();
            currentNode.setUserObject(fwr.getLocation());
            currentRes = fwr.getLocation();
            root.add(currentNode);
         }
         DefaultMutableTreeNode leafNode = new DefaultMutableTreeNode(fwr.getClosure(), false);
         currentNode.add(leafNode);
      }
      final DefaultTreeModel dtm = new DefaultTreeModel(root);

      // Global search:
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            synchronized (GenericFindResultsPanel.this) {
               ArrayList<String> expansion = getGlobalExpansionResults();
               mShowPanel.removeAll();
               mShowPanel.add(new JScrollPane(mGlobalResults));
               mGlobalResults.setModel(dtm);
               mShowPanel.revalidate();
               mShowPanel.repaint();
               setGlobalExpansionResults(expansion);
               // If only a few things matched, auto-expand to be nice:
               autoExpandGlobalResults();
            }
         }
      });
   }

   private ArrayList<String> getGlobalExpansionResults() {
      Enumeration<TreePath> e = mGlobalResults.getExpandedDescendants(new TreePath(mGlobalResults.getModel().getRoot()));
      ArrayList<String> al = new ArrayList<String>();
      if (e != null) {
         while (e.hasMoreElements()) {
            TreePath p = e.nextElement();
            DefaultMutableTreeNode dmt = (DefaultMutableTreeNode) p.getLastPathComponent();
            Object o = dmt.getUserObject();
            if (o instanceof String) {
               String pp = (String) o;
               al.add(pp);
            }
         }
      }
      return al;
   }

   /**
    * Re-expands the list of expanded locations in the tree.
    *
    * @param list A list of String (location names) that should be expanded.
    */
   private void setGlobalExpansionResults(ArrayList<String> list) {
      DefaultMutableTreeNode root = (DefaultMutableTreeNode) mGlobalResults.getModel().getRoot();
      for (int i = 0; i < list.size(); i++) {
         String li = list.get(i);
         for (int j = 0; j < root.getChildCount(); j++) {
            DefaultMutableTreeNode c = (DefaultMutableTreeNode) root.getChildAt(j);
            if (li.equals(c.getUserObject())) {
               TreeNode[] tn = c.getPath();
               TreePath path = new TreePath(tn);
               mGlobalResults.expandPath(path);
               break;
            }
         }
      }
   }

   /**
    * When there are very few results, it is nicer to just auto-expand these (since they will all fit on the screen)
    * and auto-select if there is one.
    */
   private void autoExpandGlobalResults() {
      TreeModel tm = mGlobalResults.getModel();
      Object r = tm.getRoot();
      TreePath root = new TreePath(r);
      boolean selectedFirst = false;
      if (tm.getChildCount(r) <= 1 || getGlobalResultsTotalLeafCount() < 10) {
         // got few enough results, expand all.
         for (int i = 0; i < tm.getChildCount(r); i++) {
            Object child = tm.getChild(r, i);
            TreePath cp = root.pathByAddingChild(child);
            mGlobalResults.expandPath(cp);
            if (!selectedFirst) {
               selectedFirst = true;

               // Auto select first thing, too:
               Object fc = tm.getChild(child, 0);
               TreePath selFirstPath = cp.pathByAddingChild(fc);
               mGlobalResults.setSelectionPath(selFirstPath);
            }
         }
      }
   }

   /**
    * Gets the total leaf count for the global results.
    */
   private int getGlobalResultsTotalLeafCount() {
      TreeModel tm = mGlobalResults.getModel();
      Object r = tm.getRoot();
      int cc = tm.getChildCount(r);
      int ct = 0;
      for (int i = 0; i < cc; i++) {
         Object c = tm.getChild(r, i);
         ct += tm.getChildCount(c);
      }
      return ct;
   }

   private void selChanged() {
      if (mIsGlobal) {
         if (m_singleClickSelect) {
            doubleClickOn();
         }
         //... otherwise, do nothing, must double click to select.
      }
      else {
         int index = mLocalResults.getSelectedIndex();
         if (index < 0) {
            return;
         }
         //mPlugin.
      }
   }

   private boolean doubleClickOn() {
	   if (mGlobalResults == null) {
		   return false;
	   }
      TreePath path = mGlobalResults.getSelectionPath();
      if (path == null) {
         return false;
      }
      DefaultMutableTreeNode tn = (DefaultMutableTreeNode) path.getLastPathComponent();
      if (tn.getParent() == tn.getRoot()) {
         // A file:
         // do not show anything.  m_selectionHandler.show(null,mPlugin,tn.getUserObject());
         return false;
      }
      if (tn == tn.getRoot()) {
         // do nothing.
         return false;
      }
      // it is a specific item:
      Object result = tn.getUserObject();
      String location = (String) ((DefaultMutableTreeNode) tn.getParent()).getUserObject();
      m_selectionHandler.show(location, mPlugin, result);
      return true;
   }
}
