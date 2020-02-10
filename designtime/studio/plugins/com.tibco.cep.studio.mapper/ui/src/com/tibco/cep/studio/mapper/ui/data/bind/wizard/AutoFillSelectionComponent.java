package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.EventObject;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.tibco.cep.mapper.xml.xdata.bind.BindingAutofill;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;

/**
 * A swing component that allows the user to quickly select which fields should be auto-filled in an auto-fill scenario.
 */
class AutoFillSelectionComponent extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JTree mTree;
   private JButton mSelectAll;
   private JButton mDeselectAll;
   private JButton mCopyOnAll;
   private JButton mNoCopyOnAll;

   private ActionListener mListener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         buttonPressed((JButton) e.getSource());
      }
   };

   private static final int EXTRA_ROW_HEIGHT = 2;

   public AutoFillSelectionComponent(UIAgent uiAgent, TemplateReport on) {
      super(new BorderLayout());
      mTree = new Tree(uiAgent);
      setupTree(on);
      String msg = BindingWizardResources.AUTOMAP_HEADER;
      JLabel explanation = new JLabel(msg);
      explanation.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4),
                                                               BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(),
                                                                                                  BorderFactory.createEmptyBorder(4, 4, 4, 4))));
      JScrollPane jsp = new JScrollPane(mTree);
      add(explanation, BorderLayout.NORTH);
      add(jsp, BorderLayout.CENTER);
      add(setupButtons(), BorderLayout.SOUTH);

      reenableButtons();
   }

   private JPanel setupButtons() {
      mSelectAll = new JButton(DataIcons.getSelectAllLabel());
      mSelectAll.addActionListener(mListener);
      mDeselectAll = new JButton(DataIcons.getDeselectAllLabel());
      mDeselectAll.addActionListener(mListener);
      JPanel p1 = makeButtonPair(mSelectAll, mDeselectAll);
      mCopyOnAll = new JButton(BindingWizardResources.AUTOMAP_COPY_ALL);
      mCopyOnAll.addActionListener(mListener);
      mNoCopyOnAll = new JButton(BindingWizardResources.AUTOMAP_COPY_NONE);
      mNoCopyOnAll.addActionListener(mListener);
      JPanel p2 = makeButtonPair(mCopyOnAll, mNoCopyOnAll);
      JPanel buttons = new JPanel(new GridLayout(1, 2, 8, 0));
      buttons.add(p1);
      buttons.add(p2);

      JPanel lr = new JPanel(new BorderLayout(16, 0)); // give ok button space.
      lr.add(buttons, BorderLayout.CENTER);
      lr.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

      return lr;
   }

   private JPanel makeButtonPair(JButton b1, JButton b2) {
      JPanel jp = new JPanel(new GridLayout(1, 2, 4, 0));
      jp.add(b1);
      jp.add(b2);
      return jp;
   }

   private void setupTree(TemplateReport on) {
      ExpandedName name = on.getComputedType().prime().getName();
      BindingAutofill root = new BindingAutofill(on, name);
      TreeNode rootnode = new TreeNode(root, true, new HashSet<SmParticleTerm>(), new CountHolder());
      mTree.setModel(new DefaultTreeModel(rootnode));
      mTree.setCellRenderer(new CellRenderer());
      mTree.setCellEditor(new CellEditor());
      mTree.setEditable(true);
      mTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
      mTree.setSelectionRow(0); // select root!

      // Add listener after above so we don't have initial reenable buttons yet...
      mTree.addTreeSelectionListener(new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent e) {
            reenableButtons();
         }
      });
      mTree.setRowHeight(mTree.getRowHeight() + EXTRA_ROW_HEIGHT);
   }

   private class CellRenderer extends JPanel implements TreeCellRenderer {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean mSpaceField;
      private JLabel mFieldLabel = new JLabel() {
         /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Dimension getMinimumSize() {
            return getPreferredSize();
         }

         public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            if (mSpaceField) {
               return new Dimension(Math.max(120, d.width), d.height);
            }
            else {
               return d;
            }
         }
      };
      private JLabel mEqualsLabel = new JLabel();
      private JLabel mValueLabel = new JLabel();
      private JCheckBox mUse = new JCheckBox();
      private JComboBox mCopy = new JComboBox();
      private boolean mRowSelected = false;
      private Color mBackgroundSelectionColor;
      private final Color mTextNonSelectionColor;
      private Color mBorderSelectionColor;
      private Color mTextSelectionColor;
      private boolean mHasFocus;

      public CellRenderer() {
         super(new BorderLayout());
         mCopy.addItem(BindingWizardResources.AUTOMAP_SET);
         mCopy.addItem(BindingWizardResources.AUTOMAP_COPY);
         JPanel spacer = new JPanel(new BorderLayout());
         spacer.setOpaque(false);
         spacer.setBorder(BorderFactory.createEmptyBorder(2, 1, 1, 2));
         JPanel leftArea = new JPanel(new BorderLayout());
         leftArea.setOpaque(false);
         leftArea.add(mUse, BorderLayout.WEST);
         mUse.setOpaque(false);
         leftArea.add(mCopy, BorderLayout.CENTER);
         mCopy.setOpaque(false);
         //mCopy.setBorder(BorderFactory.createEmptyBorder());
         JPanel labels = new JPanel(new BorderLayout());
         labels.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0)); // give room on left.
         labels.setOpaque(false);
         labels.add(mFieldLabel, BorderLayout.WEST);
         labels.add(mEqualsLabel, BorderLayout.CENTER);
         labels.add(mValueLabel, BorderLayout.EAST);
         spacer.add(labels, BorderLayout.CENTER);
         spacer.add(leftArea, BorderLayout.WEST);
         add(spacer);

         DefaultTreeCellRenderer r = new DefaultTreeCellRenderer();
         mBackgroundSelectionColor = r.getBackgroundSelectionColor();
         mBorderSelectionColor = r.getBorderSelectionColor();
         mTextSelectionColor = r.getTextSelectionColor();
         mTextNonSelectionColor = r.getTextNonSelectionColor();
         setOpaque(false);
      }

      public void paintComponent(Graphics g) {
         if (mRowSelected) {
            g.setColor(mBackgroundSelectionColor);
            int textWidth = getWidth();
            int selOffset = 0;
            g.fillRect(selOffset, 0, textWidth - selOffset, getHeight() - 1);
         }
         else {
            g.setColor(Color.white);
            int textWidth = getWidth();
            int selOffset = 0;
            g.fillRect(selOffset, 0, textWidth - selOffset, getHeight() - 1);
         }
         if (mHasFocus) {
            g.setColor(mBorderSelectionColor);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
         }
      }

      public void setup(TreeNode node) {
         BindingAutofill e = node.getEntry();
         String n = e.getName().getLocalName();
         if (e.isAttribute()) {
            n = "@" + n;
         }
         mFieldLabel.setText(n);
         if (node.isRoot()) {
            mEqualsLabel.setText("");
            mValueLabel.setText("");
         }
         else {
            mEqualsLabel.setText(" = ");
            mValueLabel.setText(e.getName().getLocalName());
         }
         reenableItemButtons(node);
         mUse.revalidate();
      }

      public void reenableItemButtons(TreeNode node) {
         mUse.setSelected(node.isSelected());
         mUse.setEnabled(!node.isRoot());
         mCopy.setSelectedIndex((node.isCopy() && node.canBeCopy()) ? 1 : 0);
         mCopy.setVisible(!node.isRoot()); // root is special:
         mEqualsLabel.setVisible(!node.isRoot());
         mValueLabel.setVisible(!node.isRoot());
         mSpaceField = !node.isRoot();
         mCopy.setEnabled(mUse.isSelected() && node.canBeCopy());
      }

      public boolean isSelected() {
         return mUse.isSelected();
      }

      public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                    boolean selected, boolean expanded,
                                                    boolean leaf, int row, boolean hasFocus) {
         setup((TreeNode) value);
         mRowSelected = selected;
         mFieldLabel.setForeground(mRowSelected ? mTextSelectionColor : mTextNonSelectionColor);
         mValueLabel.setForeground(mRowSelected ? mTextSelectionColor : mTextNonSelectionColor);
         mHasFocus = hasFocus;
         return this;
      }
   }

   private class CellEditor implements TreeCellEditor {
      private Vector<CellEditorListener> mListeners = new Vector<CellEditorListener>();
      private CellRenderer mRenderer;
      private TreeNode mCurrentNode;

      public CellEditor() {
         mRenderer = new CellRenderer();
         mRenderer.mUse.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
               useChanged();
            }
         });
         mRenderer.mCopy.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
               copyChanged();
            }
         });
      }

      private void useChanged() {
         if (mCurrentNode != null) {
            boolean changed = mCurrentNode.isSelected() != mRenderer.isSelected();
            if (changed) {
               mCurrentNode.setSelected(mRenderer.isSelected());
               reenableButtons();
               mRenderer.reenableItemButtons(mCurrentNode);
               mRenderer.repaint();
            }
         }
      }

      private void copyChanged() {
         if (mCurrentNode != null) {
            boolean copy = mRenderer.mCopy.getSelectedIndex() == 1;
            boolean changed = mCurrentNode.isCopy() != copy;
            if (changed) {
               mCurrentNode.setCopy(copy);
               reenableButtons();
            }
         }
      }

      public Component getTreeCellEditorComponent(JTree tree, Object value,
                                                  boolean isSelected, boolean expanded,
                                                  boolean leaf, int row) {
         mCurrentNode = (TreeNode) value;
         mRenderer.getTreeCellRendererComponent(tree,
                                                value,
                                                true, // if we're editing, it's selected.
                                                expanded,
                                                leaf,
                                                row,
                                                true);
         return mRenderer;
      }

      public Object getCellEditorValue() {
         return null;
      }

      public boolean isCellEditable(EventObject anEvent) {
         return true;
      }

      public boolean shouldSelectCell(EventObject anEvent) {
         return true;
      }

      public boolean stopCellEditing() {
         for (int i = 0; i < mListeners.size(); i++) {
            CellEditorListener cel = mListeners.get(i);
            cel.editingStopped(new ChangeEvent(mTree));
         }

         return true;
      }

      public void cancelCellEditing() {
      }

      public void addCellEditorListener(CellEditorListener l) {
         mListeners.add(l);
      }

      public void removeCellEditorListener(CellEditorListener l) {
         mListeners.remove(l);
      }
   }

   /**
    * @return true if any changes made, false otherwise.
    */
   boolean enact(NamespaceContextRegistry nm) {
      // traverse tree & enact all the nodes:
      TreeNode root = (TreeNode) mTree.getModel().getRoot();
      return root.enact(nm);
   }

   static class CountHolder {
      public int m_count;
   }

   private class TreeNode extends DefaultMutableTreeNode {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BindingAutofill mEntry; // if null, is root.
      private boolean mIsSelected;
      private boolean mIsRoot;
      private boolean mIsCopy;

      public String toString() {
         SmSequenceType ct = mEntry.getOn().getComputedType();
         if (ct == null) {
            return "<null>"; // shouldn't happen.
         }
         ExpandedName ename = ct.prime().getName();
         if (ename == null) {
            return "<null name>";
         }
         return ename.getLocalName();
      }

      /**
       * A node for the auto-fill tree.
       */
      public TreeNode(BindingAutofill entry, boolean isRoot, Set<SmParticleTerm> alreadySeenInBranch, CountHolder holder) {
         mEntry = entry;
         boolean recursive = buildChildren(entry, alreadySeenInBranch, holder);
         mIsSelected = !recursive;
         mIsCopy = false;
         mIsRoot = isRoot;
      }

      public boolean isCopy() {
         return mIsCopy;
      }

      public void setCopy(boolean copy) {
         if (mIsCopy == copy) {
            return;
         }
         mIsCopy = copy;
         rebuildChildren();
      }

      /**
       * @return true if any changes made, false otherwise.
       */
      public boolean enact(NamespaceContextRegistry nm) {
         boolean any = false;
         if (!mIsRoot) {
            if (mIsSelected) {
               mEntry.enact(nm, mIsCopy);
               any = true;
            }
         }
         // recurse:
         int cc = getChildCount();
         for (int i = 0; i < cc; i++) {
            TreeNode tn = (TreeNode) getChildAt(i);
            boolean c = tn.enact(nm);
            if (c) {
               any = true;
            }
         }
         return any;
      }

      /**
       * @return true if this is a recursive node, false otherwise.
       */
      private boolean buildChildren(BindingAutofill autofill, Set<SmParticleTerm> alreadySeenInBranch, CountHolder countSoFar) {
         SmSequenceType elType = autofill.getOn().getComputedType();
         SmSequenceType elTypep = elType.prime();
         boolean traverseChildren = true;
         SmParticleTerm addedToSeen = null;
         SmParticleTerm elTypeTerm = elTypep.getParticleTerm();
         if (elTypeTerm != null) {
            if (alreadySeenInBranch.contains(elTypeTerm)) {
               traverseChildren = false;
            }
            else {
               addedToSeen = elTypeTerm;
               alreadySeenInBranch.add(elTypeTerm);
            }
         }
         boolean depthTooDeep = getDepth() > 10; // sanity limit of depth (we have recurse checks above, but just in case)
         if (traverseChildren && !depthTooDeep) {
            BindingAutofill[] children = BindingAutofill.autofill(autofill);
            for (int i = 0; i < children.length; i++) {
               BindingAutofill e = children[i];
               if (countSoFar.m_count < 5000) // sanity limit on total size (we have recurse check above, but just in case.
               {
                  countSoFar.m_count++;
                  add(new TreeNode(e, false, alreadySeenInBranch, countSoFar)); // false-> not root.
               }
            }
         }
         if (addedToSeen != null) {
            alreadySeenInBranch.remove(addedToSeen);
         }
         // Return if it is recursive:
         return !traverseChildren;
      }

      public boolean canBeCopy() {
         return mEntry.isCouldBeCopy();
      }

      public BindingAutofill getEntry() {
         return mEntry;
      }

      public boolean isSelected() {
         return mIsSelected;
      }

      public boolean isRoot() {
         return mIsRoot;
      }

      public void setSelected(boolean val) {
         if (mIsSelected == val) {
            return;
         }
         mIsSelected = val;
         rebuildChildren();
      }

      private void rebuildChildren() {
         if (!mIsSelected || (mEntry.isCouldBeCopy() && mIsCopy)) {
            super.removeAllChildren();
            DefaultTreeModel tm = (DefaultTreeModel) mTree.getModel();
            tm.nodeStructureChanged(this);
         }
         else {
            super.removeAllChildren();
            // rebuild children.
            buildChildren(mEntry, new HashSet<SmParticleTerm>(), new CountHolder());
            DefaultTreeModel tm = (DefaultTreeModel) mTree.getModel();
            tm.nodeStructureChanged(this);
         }
      }
   }

   private void buttonPressed(JButton b) {
      if (b == mSelectAll) {
         setSelectOnChildren(true);
      }
      if (b == mDeselectAll) {
         setSelectOnChildren(false);
      }
      if (b == mCopyOnAll) {
         setCopyOnChildren(true);
      }
      if (b == mNoCopyOnAll) {
         setCopyOnChildren(false);
      }
   }

   private void reenableButtons() {
      TreePath tp = mTree.getSelectionPath();
      if (tp == null) {
         mSelectAll.setEnabled(false);
         mDeselectAll.setEnabled(false);
         mCopyOnAll.setEnabled(false);
         mNoCopyOnAll.setEnabled(false);
      }
      else {
         TreeNode node = (TreeNode) tp.getLastPathComponent();
         if (node.getChildCount() == 0) {
            // no children, so all are disabled.
            mSelectAll.setEnabled(false);
            mDeselectAll.setEnabled(false);
            mCopyOnAll.setEnabled(false);
            mNoCopyOnAll.setEnabled(false);
         }
         else {
            // there are children, see which ones make sense given current selection:
            mSelectAll.setEnabled(isSelectOnChildren(false));
            mDeselectAll.setEnabled(isSelectOnChildren(true));
            mCopyOnAll.setEnabled(isCopyOnChildren(false));
            mNoCopyOnAll.setEnabled(isCopyOnChildren(true));
         }
      }
   }

   private void setSelectOnChildren(boolean sel) {
      TreePath path = mTree.getSelectionPath();
      if (path == null) {
         return;
      }
      TreeNode node = (TreeNode) path.getLastPathComponent();
      for (int i = 0; i < node.getChildCount(); i++) {
         TreeNode c = (TreeNode) node.getChildAt(i);
         c.setSelected(sel);
      }
      mTree.repaint();
      reenableButtons();
   }

   private void setCopyOnChildren(boolean sel) {
      TreePath path = mTree.getSelectionPath();
      if (path == null) {
         return;
      }
      TreeNode node = (TreeNode) path.getLastPathComponent();
      for (int i = 0; i < node.getChildCount(); i++) {
         TreeNode c = (TreeNode) node.getChildAt(i);
         if (c.canBeCopy()) {
            c.setCopy(sel);
         }
      }
      mTree.repaint();
      reenableButtons();
   }

   /**
    * Indicates if all of the children of the selected node's 'selected' value is equal to the parameter.
    */
   private boolean isSelectOnChildren(boolean sel) {
      TreePath path = mTree.getSelectionPath();
      if (path == null) {
         return true;
      }
      TreeNode node = (TreeNode) path.getLastPathComponent();
      for (int i = 0; i < node.getChildCount(); i++) {
         TreeNode c = (TreeNode) node.getChildAt(i);
         if (c.isSelected() != sel) {
            return false;
         }
      }
      return true;
   }

   /**
    * Indicates if all of the children of the selected node's 'can-be-copy' value is equal to the parameter.
    */
   private boolean isCopyOnChildren(boolean sel) {
      TreePath path = mTree.getSelectionPath();
      if (path == null) {
         return true;
      }
      TreeNode node = (TreeNode) path.getLastPathComponent();
      boolean hadAnyCopy = false;
      for (int i = 0; i < node.getChildCount(); i++) {
         TreeNode c = (TreeNode) node.getChildAt(i);
         if (c.canBeCopy()) {
            hadAnyCopy = true;
         }
         if (c.canBeCopy() && c.isCopy() != sel) {
            return false;
         }
      }
      return hadAnyCopy;
   }

   private static class Tree extends JTree {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Tree(UIAgent uiAgent) {
         // we'll paint background:
         setOpaque(false);

         // For X-windows, this is REQUIRED, otherwise the row height is somehow 1 pixel.
         // It is not required for windows.
         FontMetrics fm = getFontMetrics(uiAgent.getAppFont());
         FontMetrics fm2 = getFontMetrics(uiAgent.getScriptFont());
         setRowHeight(Math.max(fm.getHeight(), fm2.getHeight()) + 2); // 2-> 2 pixels for border.
      }

      private Color mRowSeparatorColor = new Color(240, 240, 255);

      public void paint(Graphics g) {
         g.setColor(Color.white);
         int rc = getRowCount();
         int rh = getRowHeight();
         int w = getWidth();
         int h = getHeight();
         g.fillRect(0, 0, w, h);
         // paint any rows:
         int base = getInsets().top;
         g.setColor(mRowSeparatorColor);
         for (int i = 0; i < rc; i++) {
            int y = i * rh + base;
            int bt = y + rh - 1;
            g.drawLine(0, bt, w, bt);
         }
         super.paint(g);
      }
   }
}

