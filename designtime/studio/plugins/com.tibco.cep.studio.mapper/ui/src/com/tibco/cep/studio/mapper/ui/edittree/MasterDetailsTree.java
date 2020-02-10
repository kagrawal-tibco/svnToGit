package com.tibco.cep.studio.mapper.ui.edittree;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.BevelBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.basic.DetailsViewFactory;
import com.tibco.cep.studio.mapper.ui.data.utils.HorzSizedScrollPane;
import com.tibco.cep.studio.mapper.ui.data.utils.PreferenceUtils;
import com.tibco.cep.studio.mapper.ui.data.utils.SoftDragNDropHandler;

/**
 * A master-details view with the editable tree on the left and the details on the right.<br>
 * Handles the selection 'glue'.
 */
public class MasterDetailsTree extends JSplitPane {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JPanel m_treePanel;
   private DetailsViewFactory m_details;
   private EditableTree m_tree;
   private JPanel m_buttonGrid;
   private JPanel m_leftHandSidePanel;
   private JScrollPane m_scroller;

   private SoftDragNDropHandler m_softDragNDropHandler;

   public MasterDetailsTree() {
      m_treePanel = new JPanel(new BorderLayout());
      m_treePanel.setBorder(BorderFactory.createEmptyBorder(0, 2, 5, 0)); // random border size.
      setBorder(BorderFactory.createEmptyBorder());

      m_scroller = new HorzSizedScrollPane(m_tree);
      m_scroller.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

      m_treePanel.add(m_scroller, BorderLayout.CENTER);

      m_leftHandSidePanel = new JPanel(new BorderLayout());
      m_leftHandSidePanel.add(m_treePanel, BorderLayout.CENTER);

      JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
      m_buttonGrid = new JPanel(new GridLayout(1, 6, 2, 0));
      m_buttonGrid.setBorder(null);
      buttons.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      buttons.add(m_buttonGrid);

      m_leftHandSidePanel.add(buttons, BorderLayout.NORTH);
      m_leftHandSidePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

      setLeftComponent(m_leftHandSidePanel);
   }

   public MasterDetailsTree(EditableTree tree) {
      this();
      setTree(tree);
   }

   public MasterDetailsTree(EditableTree tree, DetailsViewFactory details) {
      this(tree);
      setDetails(details);
   }

   public void setTree(EditableTree tree) {
      m_tree = tree;
      m_tree.addTreeSelectionListener(new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent tse) {
            updateSelection();
         }
      });
      // Check for initial selection:
      TreePath tp = m_tree.getSelectionPath();
      if (tp != null) {
         updateSelection();
      }

      m_scroller.getViewport().setView(m_tree);
      m_leftHandSidePanel.revalidate();
      m_leftHandSidePanel.repaint();

      m_softDragNDropHandler = new SoftDragNDropHandler(this, new JComponent[]{tree});
      m_tree.setOvershowManager(m_softDragNDropHandler);

      m_tree.addPropertyChangeListener(EditableTree.TREE_MODE_PROPERTY, new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent evt) {
            rebuildButtons();
         }
      });
      rebuildButtons();
   }

   private void rebuildButtons() {
      EditableTreeButtonManager btbm = m_tree.getButtonManager();
      m_buttonGrid.removeAll();
      m_buttonGrid.add(btbm.getAddAtButton());
      if (m_tree.getTreeMode()) {
         m_buttonGrid.add(btbm.getAddTopOrChildButton());
      }
      m_buttonGrid.add(btbm.getDeleteButton());
      m_buttonGrid.add(btbm.getMoveUpButton());
      m_buttonGrid.add(btbm.getMoveDownButton());
      if (m_tree.getTreeMode()) {
         m_buttonGrid.add(btbm.getMoveOutButton());
         m_buttonGrid.add(btbm.getMoveInButton());
      }
      // Force initial re-enable:
      m_tree.reenableButtons();
   }

   public void setDetails(DetailsViewFactory details) {
      if (m_details == details) {
         return;
      }
      m_details = details;
      setRightComponent(m_details.getComponentForNode(null));
      refreshDetails();
   }

   /**
    * Forces an update of the details panel.<br>
    * Call this is, for instance, something about the tree changed.
    */
   public void refreshDetails() {
      updateSelection();
   }


   /**
    * Gets the tree part of this master-details.
    *
    * @return The tree, never null.
    */
   public EditableTree getTree() {
      return m_tree;
   }

   /**
    * Gets the details part of this master-details.
    *
    * @return The details, never null.
    */
   public DetailsViewFactory getDetails() {
      return m_details;
   }

   /**
    * Forwards to whichever has focus.
    */
   public void delete() {
      if (m_tree.hasFocus()) {
         m_tree.delete();
      }
   }

   /**
    * Forwards to whichever has focus.
    */
   public void cut() {
      if (m_tree.hasFocus()) {
         m_tree.cut();
      }
   }

   /**
    * Forwards to whichever has focus.
    */
   public void copy() {
      if (m_tree.hasFocus()) {
         m_tree.copy();
      }
   }

   /**
    * Forwards to whichever has focus.
    */
   public void paste() {
      if (m_tree.hasFocus()) {
         m_tree.paste();
      }
   }

   /**
    * Writes out preferences under the given key (as a prefix).
    *
    * @param keyPrefix The prefix for writing out preferences, should not include trailing '.'.
    */
   public void writePreferences(UIAgent uiAgent, String keyPrefix) {
      PreferenceUtils.writeInt(uiAgent, keyPrefix + keyPrefix + ".splitter", getDividerLocation());
   }

   public void readPreferences(UIAgent uiAgent, String keyPrefix) {
      setDividerLocation(PreferenceUtils.readInt(uiAgent, keyPrefix + keyPrefix + ".splitter", 100));
   }

   /**
    * An internal call for when the tree selection changed, properly sets the details pane.
    */
   private void updateSelection() {
      if (m_tree == null || m_details == null) {
         // for startup, etc.
         return;
      }
      Object node = m_tree.getSelectionNode();
      if (node != null && node.equals(m_tree.getEditableModel().getRoot())) {
         if (!m_tree.isRootVisible()) {
            // Can't select the root if we aren't showing it! (Can happen in swing, though)
            node = null;
         }
      }
      if (m_tree.getEditableModel().isRootNull()) {
         // If we're root null, we're null, too.
         node = null;
      }
      JComponent jc = m_details.getComponentForNode(node);
      if (getRightComponent() != jc) {
         setRightComponent(jc);
      }
   }

   /**
    * Implementation override for over-show.
    */
   public void paint(Graphics g) {
      super.paint(g);
      m_softDragNDropHandler.paintDragging(g);
   }
}
