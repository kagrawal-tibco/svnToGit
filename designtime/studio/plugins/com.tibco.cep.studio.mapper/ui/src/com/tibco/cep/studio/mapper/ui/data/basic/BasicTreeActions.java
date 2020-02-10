package com.tibco.cep.studio.mapper.ui.data.basic;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import org.xml.sax.SAXException;

import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;

/**
 * Implements many of the actions for the BasicTree, split off to keep size
 * of BasicTree down.
 */
@SuppressWarnings("rawtypes")
public final class BasicTreeActions {

   public static void collapseAllPathsBelow(BasicTree tree, TreePath path) {
      for (; ;) {
         // For some reason, unless this is called until there's no change, it doesn't properly get all the
         // paths.  Whatever, it works now.
         boolean any = collapseAllPathsBelowInternal(tree, path);
         if (!any) {
            break;
         }
      }
   }

   private static boolean collapseAllPathsBelowInternal(BasicTree tree, TreePath path) {
      Enumeration<TreePath> e = tree.getExpandedDescendants(path);
      if (e == null) { // can return null, how nice.
         return false;
      }
      // copy list before potentially modifying things:
      ArrayList<TreePath> temp = new ArrayList<TreePath>();
      while (e.hasMoreElements()) {
         TreePath tp = e.nextElement();
         if (tp.getPathCount() > 1) {
            // Without above check, if you call this on the root, it will keep loop forever as this can't be collapsed...
            temp.add(tp);
         }
      }
      for (int i = 0; i < temp.size(); i++) {
         TreePath p = temp.get(i);
         tree.collapsePath(p);
      }
      return temp.size() > 0;
   }

   /**
    * Turns all the expanded paths into a List of String[] paths.
    */
   public static ArrayList<int[]> getAllExpandedPosPaths(BasicTree tree) {
      Enumeration<TreePath> e = tree.getExpandedDescendants(new TreePath(tree.getModel().getRoot()));
      ArrayList<int[]> al = new ArrayList<int[]>();
      if (e != null) {
         while (e.hasMoreElements()) {
            TreePath p = e.nextElement();
            int[] pp = toPosPath(p);
            al.add(pp);
         }
      }
      return al;
   }

   /**
    * Turns all the expanded paths below the given node into a List of String[] paths.
    */
   public static ArrayList<int[]> getAllExpandedPosPaths(BasicTree tree, BasicTreeNode node) {
      Enumeration<TreePath> e = tree.getExpandedDescendants(node.getTreePath());
      ArrayList<int[]> al = new ArrayList<int[]>();
      if (e != null) {
         while (e.hasMoreElements()) {
            TreePath p = e.nextElement();
            int[] pp = toPosPath(p, node);
            al.add(pp);
         }
      }
      return al;
   }

   /**
    * Turns all the expanded paths into a List of String[] paths.
    */
   public static ArrayList<int[]> getAllSelectedPosPaths(BasicTree tree) {
      TreePath[] paths = tree.getSelectionPaths();
      ArrayList<int[]> al = new ArrayList<int[]>();
      if (paths != null) {
         for (int i = 0; i < paths.length; i++) {
            TreePath p = paths[i];
            int[] pp = toPosPath(p);
            al.add(pp);
         }
      }
      return al;
   }

   /**
    * Expands all int[] paths possible (by position).
    */
   public static void expandAllPosPaths(BasicTree tree, ArrayList l) {
      int sz = l.size();
      for (int i = 0; i < sz; i++) {
         int[] pp = (int[]) l.get(i);
         TreePath tp = fromPosPath(tree, pp);
         if (tp != null) {
            tree.expandPath(tp);
         }
      }
   }

   /**
    * Expands all int[] paths possible (by position) where baseNode is considered the root.
    */
   public static void expandAllPosPaths(BasicTree tree, ArrayList l, BasicTreeNode baseNode) {
      int sz = l.size();
      for (int i = 0; i < sz; i++) {
         int[] pp = (int[]) l.get(i);
         TreePath tp = fromPosPath(baseNode, pp);
         if (tp != null) {
            tree.expandPath(tp);
         }
      }
   }

   /**
    * Selects all int[] paths possible (by position).
    */
   public static void selectAllPosPaths(BasicTree tree, ArrayList l) {
      int sz = l.size();
      tree.clearSelection();
      for (int i = 0; i < sz; i++) {
         int[] pp = (int[]) l.get(i);
         TreePath tp = fromPosPath(tree, pp);
         if (tp != null) {
            tree.addSelectionPath(tp);
         }
      }
   }

   /**
    * Extracts a node by following index-of-child paths.
    *
    * @param tree      The tree.
    * @param positions The index-of-child indexes describing the node.
    * @return The node, or null if not found because of out-of-bounds or whatever.
    * @see #toPosPath(com.tibco.ui.data.basic.BasicTreeNode)
    */
   private static BasicTreeNode nodeFromPosPath(BasicTree tree, int[] positions) {
      TreePath path = fromPosPath(tree, positions);
      if (path == null) {
         return null;
      }
      return (BasicTreeNode) path.getLastPathComponent();
   }

   public static TreePath fromPosPath(BasicTree tree, int[] positions) {
      BasicTreeNode at = tree.getRootNode();
      return fromPosPath(at, positions);
      /*TreePath tp = new TreePath(at);
      for (int i=0;i<positions.length;i++) {
          int pos = positions[i];
          if (pos<0 || pos>= at.getChildCount()) {
              return null;
          }
          at = at.getChild(pos);
          tp = tp.pathByAddingChild(at);
      }
      return tp;*/
   }

   private static TreePath fromPosPath(BasicTreeNode root, int[] positions) {
      TreePath tp = root.getTreePath();
      BasicTreeNode at = root;
      for (int i = 0; i < positions.length; i++) {
         int pos = positions[i];
         if (pos < 0 || pos >= at.getChildCount()) {
            return null;
         }
         at = at.getChild(pos);
         tp = tp.pathByAddingChild(at);
      }
      return tp;
   }


   /**
    * Gets the path as described by a set of index-of-childs from the root.
    * This is particularly valuable for
    *
    * @param node The node from which to extract the path.
    * @return A non-null array of child-index steps to get there.
    */
   private static int[] toPosPath(BasicTreeNode node) {
      return toPosPath(node.getTreePath());
   }

   public static int[] toPosPath(TreePath path) {
      if (path == null) {
         return new int[0];
      }
      int[] t = new int[path.getPathCount() - 1];
      for (int i = 0; i < t.length; i++) {
         BasicTreeNode node = (BasicTreeNode) path.getPathComponent(i + 1);
         int index = node.getParent().getIndexOfChild(node);
         t[i] = index;
      }
      return t;
   }

   public static int[] toPosPath(TreePath path, BasicTreeNode depth) {
      if (path == null) {
         return new int[0];
      }
      TreePath trimPath = trimPathTo(path, depth);
      return toPosPath(trimPath);
   }

   /**
    * Returns the passed in path shortened to start with the newRootNode as its root.
    *
    * @param path        The original path
    * @param newRootNode The newRootNode that should be the new root.
    * @return The new path which is the same as the original path, but 'stopping' at the new root newRootNode.
    */
   private static TreePath trimPathTo(TreePath path, BasicTreeNode newRootNode) {
      if (newRootNode == null || path == null) {
         return null;
      }
      if (path.getLastPathComponent() == newRootNode) {
         return new TreePath(newRootNode);
      }
      TreePath pp = trimPathTo(path.getParentPath(), newRootNode);
      if (pp != null) {
         return pp.pathByAddingChild(path.getLastPathComponent());
      }
      return new TreePath(path.getLastPathComponent());
   }

   public static boolean canCopy(BasicTree tree) {
      if (tree.isEditing()) {
         return tree.mEditor.mCurrentField.getSelectionStart() >= 0;
      }
      return true;
   }

   public static void copy(BasicTree tree) {
      if (tree.isEditing()) {
         tree.mEditor.mCurrentField.copy();
         return;
      }
      tree.stopEditing();
      tree.waitForReport();
      BasicTreeNode node = tree.getSelectionNodeOrRoot();
      String xml = node.toXML();
      if (xml != null) {
         Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
         StringSelection sel = new StringSelection(xml);
         clip.setContents(sel, sel);
      }
   }

   public static String copyAsString(BasicTree tree) {
      if (tree.isEditing()) {
         tree.mEditor.mCurrentField.copy();
         return null;
      }
      tree.stopEditing();
      tree.waitForReport();
      BasicTreeNode node = tree.getRootNode();
      String xml = node.toXML();
      return xml;
   }

   public static boolean canCut(BasicTree tree) {
      if (!tree.isEditable()) {
         return false;
      }
      if (tree.isEditing()) {
         return tree.mEditor.mCurrentField.getSelectionStart() >= 0;
      }
      return tree.getSelectionNode() != null;
   }

   public static void cut(BasicTree tree) {
      if (!tree.isEditable()) {
         return;
      }
      if (tree.isEditing()) {
         tree.mEditor.mCurrentField.cut();
         return;
      }
      copy(tree);
      delete(tree);
   }

   public static boolean canPaste(BasicTree basicTree) {
      if (!basicTree.isEditable()) {
         return false;
      }
      if (basicTree.isEditing()) {
         return true;
      }
      return basicTree.getSelectionNode() != null;
   }

   public static void paste(final BasicTree tree) {
      /*if (!tree.isEditable()) {
          return;
      }*/
      if (tree.isEditing()) {
         tree.mEditor.mCurrentField.paste();
         return;
      }
      tree.stopEditing();
      tree.waitForReport();
      Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
      Transferable tf = clip.getContents(tree);
      if (!tf.isDataFlavorSupported(new StringSelection("").getTransferDataFlavors()[0])) {
         return;
      }
      BasicTreeNode pasteOn = tree.getSelectionNode();
      if (pasteOn == null) {
         return;
      }
      try {

         Object got = tf.getTransferData(new StringSelection("").getTransferDataFlavors()[0]);
         final String strGot = (String) got;

         BasicTreeNode node = tree.buildFromXML(pasteOn, strGot);
         // On the root, do a replace (can't insert before)
         if (pasteOn.getParent() == null) {
            replace(tree, pasteOn, node);
         }
         else {
            // otherwise, insert-before, much nicer:
            addNodeAt(tree, pasteOn, node, BasicTreeResources.PASTE);
         }
      }
      catch (SAXException se) {
         String reason;
         if (se.getMessage() == null || se.getMessage().length() == 0) {
            reason = BasicTreeResources.CANNOT_PASTE_GENERIC;
         }
         else {
            reason = se.getMessage();
         }
         String msg = DataIcons.printf(BasicTreeResources.CANNOT_PASTE, reason);

         JOptionPane.showMessageDialog(tree, msg);
      }
      catch (Exception e) {
         // Shouldn't happen, so print here:
         e.printStackTrace(System.out);
      }
   }

   private static void replace(BasicTree tree, BasicTreeNode rnode, BasicTreeNode withNode) {
      if (rnode == null) {
         return;
      }
      if (withNode == null) {
         return;
      }
      tree.waitForReport();
      BasicTreeNode pn = rnode.getParent();
      if (pn == null) {
         tree.setRootNode(withNode);
         // rootChanged()....
      }
      else {
         int index = pn.getIndexOfChild(rnode);
         removeFrom(tree, rnode);
         addTo(tree, pn, index, withNode);
      }
      ((BasicTreeModel) tree.getModel()).copyModelTo(withNode);
      tree.waitForReport();
      tree.expandPath(withNode.getTreePath());
      tree.setSelectionPath(withNode.getTreePath());
   }

   public static TreePath getTreePathFromXPath(BasicTree tree, String[] xpathSteps, NamespaceContextRegistry namespaceContextRegistry) {
      BasicTreeNode at = tree.getRootNode();
//        TreePath tp = new TreePath(at);
      for (int i = 0; i < xpathSteps.length; i++) {
         String step = xpathSteps[i];
         BasicTreeNode sub = at.getNodeForXStep(step, -1, i == xpathSteps.length - 1, namespaceContextRegistry);
         if (sub == null) {
            return null;
         }
         at = sub;
      }
      return at.getTreePath();
   }

   public static boolean canDelete(BasicTree tree) {
      if (tree.isRootNull()) {
         return false;
      }
      BasicTreeNode node = tree.getSelectionNode();
      if (node == null) {
         return false;
      }
      return node.canDelete();
   }

   public static void delete(BasicTree tree) {
      delete(tree, false);
   }

   public static void delete(final BasicTree tree, final boolean deleteAll) {
      if (!canDelete(tree)) {
         return;
      }
      tree.stopEditing();
      tree.waitForReport();
      BasicTreeNode node = tree.getSelectionNode();
      BasicTreeNode parent = node.getParent();
      if (parent == null && tree.mAllowDeleteRoot) {
         tree.mRootIsNull = true;
         tree.rootChanged();
         tree.markReportDirty();
         tree.contentChanged();
         return;
      }
      //boolean canShiftUp = node.hasChildContent();
      BasicTreeNode optionalReplacementMarker = node.createReplacementMarker();
      //boolean doShiftUpDelete = false;
      /*WCETODO -- forget dialog.
      if (!deleteAll)
      {
          if (canShiftUp || optionalReplacementMarker!=null)
          {
              Window w = SwingUtilities.getWindowAncestor(tree);
              JFrame jframe;
              if (w instanceof JDialog)
              {
                  jframe = (JFrame)w.getOwner();
              }
              else
              {
                  jframe = (JFrame) w;
              }
              DeleteChoicePanel dcp = new DeleteChoicePanel(jframe,canShiftUp,optionalReplacementMarker!=null);
              dcp.pack();
              dcp.setLocationRelativeTo(SwingUtilities.getWindowAncestor(tree));
              dcp.setVisible(true);
              if (!dcp.isOkHit())
              {
                  return;
              }
              if (!dcp.isClear())
              {
                  optionalReplacementMarker = null; // not that.
              }
              doShiftUpDelete = dcp.isShiftUp();
          }
      }*/
      if (deleteAll) {
         optionalReplacementMarker = null; // if ctrl-delete hit, never clear.
      }
      final BasicTreeNode foptionalReplacementMarker = optionalReplacementMarker;
      /*if (doShiftUpDelete)
      {
          // totally different logic.
          shiftUpDelete(tree);
          return;
      }*/


      final int[] posPath = toPosPath(node);
      final int[] parentPosPath = toPosPath(node.getParent());
      final int indexOfChild = node.getParent().getIndexOfChild(node);
      final BasicTreeNode fnode = node;

      // in this case, (literal), just add next
      UndoableEdit ua = new AbstractUndoableEdit() {
         /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void undo() throws CannotUndoException {
            BasicTreeNode addTo = nodeFromPosPath(tree, parentPosPath);
            if (addTo != null) {
               if (foptionalReplacementMarker != null) {
                  removeFrom(tree, addTo.getChild(indexOfChild));
               }
               addTo(tree, addTo, indexOfChild, fnode);
               TreePath newSelect = addTo.getChild(indexOfChild).getTreePath();
               tree.expandPath(newSelect);
               tree.setSelectionPath(addTo.getTreePath()); // reselect parent so you can just hit the button again.
               tree.requestFocus();
            }
            else {
               throw new CannotUndoException();
            }
         }

         public void redo() throws CannotRedoException {
            BasicTreeNode remove = nodeFromPosPath(tree, posPath);
            BasicTreeNode treeNodeParent = remove.getParent();

            // remove can mean either blank it out or eliminate it...
            // eliminate here:
            int pos = treeNodeParent.getIndexOfChild(remove);
            if (removeFrom(tree, remove) == null) {
               return;
            }
            if (!deleteAll && foptionalReplacementMarker != null) {
               addTo(tree, treeNodeParent, pos, foptionalReplacementMarker);
            }
            if (treeNodeParent.getChildCount() > 0) {
               tree.setSelectionPath(treeNodeParent.getChild(Math.min(pos, treeNodeParent.getChildCount() - 1)).getTreePath());
            }
            else {
               tree.setSelectionPath(treeNodeParent.getTreePath());
            }
            tree.requestFocus();
         }

         public boolean canUndo() {
            return true;
         }

         public boolean canRedo() {
            return true;
         }

         public void die() {
            // do nothing.
         }

         public boolean addEdit(UndoableEdit anEdit) {
            // can't handle
            return false;
         }

         public boolean replaceEdit(UndoableEdit anEdit) {
            // can't handle
            return false;
         }

         public boolean isSignificant() {
            return true;
         }

         public String getPresentationName() {
            return BasicTreeResources.DELETE;
         }
      };
      try {
         ua.redo();
         //tree.getUndoableEditHandler().registerEdit(ua);
      }
      catch (CannotRedoException cre) {
         // eat it, can't do it.
      }
   }

   /*
   private static class DeleteChoicePanel extends BetterJDialog
   {
       private JButton m_ok;
       private JButton m_cancel;
       private JRadioButton m_clear;
       private JRadioButton m_deleteAndShiftUp;
       private JRadioButton m_deleteBranch;
       private boolean m_okHit;

       public DeleteChoicePanel(JFrame w, boolean canShift, boolean canClear)
       {
           super(w);
           ActionListener al = new ActionListener()
           {
               public void actionPerformed(ActionEvent e)
               {
                   buttonHit(e.getSource());
               }
           };
           JPanel panel = new JPanel(new GridBagLayout());
           panel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
           setTitle(BasicTreeResources.DELETE_TREE_NODE_TITLE);
           GridBagConstraints gbc = new GridBagConstraints();
           ButtonGroup bg = new ButtonGroup();
           if (canClear)
           {
               m_clear = new JRadioButton(BasicTreeResources.CLEAR);
               bg.add(m_clear);
           }
           if (canShift)
           {
               m_deleteAndShiftUp = new JRadioButton(BasicTreeResources.DELETE_MOVE_OUT);
               bg.add(m_deleteAndShiftUp);
           }
           m_deleteBranch = new JRadioButton(BasicTreeResources.DELETE_NODE_AND_CONTENTS);
           bg.add(m_deleteBranch);

           gbc.fill = GridBagConstraints.BOTH;
           gbc.gridy = 0;
           gbc.weightx = 0;
           gbc.weighty = 0;
           if (canClear)
           {
               panel.add(m_clear,gbc);
               gbc.gridy++;
           }
           if (canShift)
           {
               panel.add(m_deleteAndShiftUp,gbc);
               gbc.gridy++;
           }
           panel.add(m_deleteBranch,gbc);
           gbc.gridx++;
           gbc.gridy = 0;
           gbc.weightx = 1;
           panel.add(new JLabel(),gbc);
           JPanel buttons = new JPanel(new GridLayout(1,2,4,0)); // !!! Need a TIBCO standard OK/Cancel layout somewhere.
           m_ok = new JButton(DataIcons.getOKLabel());
           m_cancel = new JButton(DataIcons.getCancelLabel());
           buttons.add(m_ok);
           buttons.add(m_cancel);
           buttons.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));

           JPanel p2 = new JPanel(new BorderLayout());
           p2.add(panel,BorderLayout.CENTER);
           JPanel buttonSpacer = new JPanel(new BorderLayout());
           buttonSpacer.add(buttons,BorderLayout.EAST);
           p2.add(buttonSpacer,BorderLayout.SOUTH);
           setContentPane(p2);

           if (m_clear!=null)
           {
               m_clear.setSelected(true);
           }
           else
           {
               m_deleteBranch.setSelected(true);
           }

           m_ok.addActionListener(al);
           m_ok.setDefaultCapable(true);
           m_cancel.addActionListener(al);

           setModal(true);
       }

       public boolean isOkHit()
       {
           return m_okHit;
       }

       public boolean isClear()
       {
           if (m_clear==null)
           {
               return false;
           }
           return m_clear.isSelected();
       }

       public boolean isShiftUp()
       {
           if (m_deleteAndShiftUp==null)
           {
               return false;
           }
           return m_deleteAndShiftUp.isSelected();
       }

       private void buttonHit(Object src)
       {
           if (src==m_ok)
           {
               m_okHit = true;
               dispose();
           }
           if (src==m_cancel)
           {
               m_okHit = false;
               dispose();
           }
       }
   }*/

   public static void shiftUpDelete(BasicTree tree) {
      if (!canDelete(tree)) {
         return;
      }
      tree.stopEditing();
      tree.waitForReport();
      BasicTreeNode node = tree.getSelectionNode();
      BasicTreeNode parent = node.getParent();
      BasicTreeNode[] p = new BasicTreeNode[node.getChildCount()];
      if (p.length == 0) {
         return;
      }
      for (int i = 0; i < p.length; i++) {
         p[i] = node.getChild(i);
      }
      for (int i = p.length - 1; i >= 0; i--) {
         node.remove(i);
      }
      int iop = parent.getIndexOfChild(node);
      for (int i = 0; i < p.length; i++) {
         parent.addAt(iop + i, p[i]);
      }
      tree.setSelectionPath(p[0].getTreePath());
   }

   /**
    * Does an insert (before).<br>
    * For example, if we had
    * <code><pre>
    * A
    * B
    * C
    * </pre></code>
    * and pointed at 'B' and did an Add, adding X, we would get
    * <code><pre>
    * A
    * X
    * B
    * C
    * </pre></code>
    */
   public static void addAt(final BasicTree tree) {
      if (!canAddAt(tree)) {
         return;
      }
      tree.stopEditing();
      BasicTreeNode node = tree.getSelectionNode();
      if (node == null && !tree.isRootVisible()) {
         addChild(tree);
         // select the one we just added:
         return;
      }
      BasicTreeNode addToOriginal = node.getParent();
      final BasicTreeNode newnode = addToOriginal.createArrayElement();
      if (newnode == null) {
         return;
      }
      addNodeAt(tree, node, newnode, BasicTreeResources.INSERT);
   }

   public static void addNodeAt(final BasicTree tree, BasicTreeNode node, final BasicTreeNode newnode, final String undoLabel) {
      BasicTreeNode addToOriginal = node.getParent();
      if (addToOriginal == null) {
         return; // sanity.
      }
      final int addAt = addToOriginal.getIndexOfChild(node);
      final int[] addToIndexPath = toPosPath(addToOriginal);

      // in this case, (literal), just add next
      UndoableEdit ua = new AbstractUndoableEdit() {
         /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void undo() throws CannotUndoException {
            BasicTreeNode addTo = nodeFromPosPath(tree, addToIndexPath);
            if (addTo != null && addTo.getChildCount() > addAt && addAt >= 0) {
               removeFrom(tree, addTo.getChild(addAt));
               TreePath newSelect = addTo.getChild(addAt).getTreePath();
               tree.expandPath(newSelect);
               tree.setSelectionPath(newSelect);
               tree.requestFocus();
            }
            else {
               throw new CannotUndoException();
            }
         }

         public void redo() throws CannotRedoException {
            BasicTreeNode addTo = nodeFromPosPath(tree, addToIndexPath);
            if (addTo != null) {
               addTo(tree, addTo, addAt, newnode);
               TreePath path = newnode.getTreePath();
               tree.expandPath(path);
               tree.setSelectionPath(path);
               tree.requestFocus();
            }
            else {
               throw new CannotRedoException();
            }
         }

         public boolean canUndo() {
            return true;
         }

         public boolean canRedo() {
            return true;
         }

         public void die() {
            // do nothing.
         }

         public boolean addEdit(UndoableEdit anEdit) {
            // can't handle
            return false;
         }

         public boolean replaceEdit(UndoableEdit anEdit) {
            // can't handle
            return false;
         }

         public boolean isSignificant() {
            return true;
         }

         public String getPresentationName() {
            return undoLabel;
         }
      };
      //tree.getUndoableEditHandler().registerEdit(ua);
      ua.redo();
   }

   /**
    * Adds a new last child to the selected node.
    *
    * @param tree
    */
   public static void addChild(final BasicTree tree) {
      if (!canAddChild(tree)) {
         return;
      }
      tree.stopEditing();

      BasicTreeNode node = tree.getSelectionNode();
      if (node == null && !tree.isRootVisible()) {
         node = tree.getRootNode();
      }
      final int addAt = node.getChildCount(); // add last child.
      final BasicTreeNode newnode = node.createArrayElement();
      if (newnode == null) {
         return;
      }
      final int[] addToIndexPath = toPosPath(node);

      // in this case, (literal), just add next
      UndoableEdit ua = new AbstractUndoableEdit() {
         /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void undo() throws CannotUndoException {
            BasicTreeNode addTo = nodeFromPosPath(tree, addToIndexPath);
            if (addTo != null && addTo.getChildCount() > addAt) {
               removeFrom(tree, addTo.getChild(addAt));
               tree.expandPath(addTo.getTreePath());
               tree.setSelectionPath(addTo.getTreePath()); // reselect parent so you can just hit the button again.
               tree.requestFocus();
            }
            else {
               throw new CannotUndoException();
            }
         }

         public void redo() throws CannotRedoException {
            BasicTreeNode addTo = nodeFromPosPath(tree, addToIndexPath);
            if (addTo != null) {
               addTo(tree, addTo, addAt, newnode);
               tree.expandPath(addTo.getTreePath());
               if (tree.isRootVisible()) {
                  tree.setSelectionPath(addTo.getTreePath()); // reselect parent so you can just hit the button again.
               }
               else {
                  tree.setSelectionPath(newnode.getTreePath());
               }
               tree.requestFocus();
            }
            else {
               throw new CannotRedoException();
            }
         }

         public boolean canUndo() {
            return true;
         }

         public boolean canRedo() {
            return true;
         }

         public void die() {
            // do nothing.
         }

         public boolean addEdit(UndoableEdit anEdit) {
            // can't handle
            return false;
         }

         public boolean replaceEdit(UndoableEdit anEdit) {
            // can't handle
            return false;
         }

         public boolean isSignificant() {
            return true;
         }

         public String getPresentationName() {
            return BasicTreeResources.ADD_CHILD;
         }
      };
      //tree.getUndoableEditHandler().registerEdit(ua);
      ua.redo();
   }

   /**
    * Checks if {@link #addAt} should be allowed.
    *
    * @param tree The tree to add to.
    * @return the success condition.
    */
   public static boolean canAddAt(BasicTree tree) {
      BasicTreeNode node = tree.getSelectionNode();
      if (node == null) {
         // If we're in list mode, this works.
         if (!tree.isRootVisible()) {
            return true;
         }
         return false;
      }
      // new style tree here:
      BasicTreeNode parent = node.getParent();
      // simple enough: (if it already has a child, then it must be able to have more)
      return parent != null;
   }

   /**
    * Adds either to the top, or after the currently selected node.
    *
    * @param tree The tree to add to.
    */
   public static void addTopOrChild(BasicTree tree) {
      if (tree.isRootNull()) {
         tree.stopEditing();
         if (tree.mRootIsNull) {
            tree.mRootIsNull = false;
            tree.rootChanged();
            tree.contentChanged();
            tree.markReportDirty();
            tree.setSelectionPath(tree.getRootNode().getTreePath());
         }
         return;
      }
      else {
         addChild(tree);
      }
   }

   /**
    * Really is 'add after'.
    *
    * @param tree The tree to add to.
    * @return the success condition.
    */
   public static boolean canAddChild(BasicTree tree) {
      BasicTreeNode node = tree.getSelectionNode();
      if (node == null) {
         if (!tree.isRootVisible()) {
            return true;
         }
         return false;
      }
      return node.isArray(); // really should be renamed 'canHaveChildren'
   }

   public static boolean canMoveDown(BasicTree tree) {
      if (tree.isRootNull()) {
         return false;
      }
      TreePath[] paths = tree.getSelectionPaths();
      if (paths == null || paths.length == 0) {
         return false;
      }
      BasicTreeNode onlyParent = null;
      for (int i = 0; i < paths.length; i++) {
         BasicTreeNode node = (BasicTreeNode) paths[i].getLastPathComponent();
         BasicTreeNode parent = node.getParent();
         if (parent == null) {
            return false;
         }
         if (i == 0) {
            onlyParent = parent;
         }
         else {
            if (parent != onlyParent) { // must all have same parent...
               return false;
            }
         }
         int pos = parent.getIndexOfChild(node);
         if (pos < 0 || pos + 1 >= parent.getChildCount()) {
            return false;
         }
         if (!node.canMoveUp()) {
            return false;
         }
      }
      return true;
   }

   public static boolean canMoveUp(BasicTree tree) {
      if (tree.isRootNull()) {
         return false;
      }
      TreePath[] paths = tree.getSelectionPaths();
      if (paths == null || paths.length == 0) {
         return false;
      }
      BasicTreeNode onlyParent = null;
      for (int i = 0; i < paths.length; i++) {
         BasicTreeNode node = (BasicTreeNode) paths[i].getLastPathComponent();
         BasicTreeNode parent = node.getParent();
         if (parent == null) {
            return false;
         }
         if (i == 0) {
            onlyParent = parent;
         }
         else {
            if (parent != onlyParent) // must all have same parent...
            {
               return false;
            }
         }
         int pos = parent.getIndexOfChild(node);
         if (pos <= 0) {
            return false;
         }
         if (!node.canMoveUp()) {
            return false;
         }
      }
      return true;
   }

   public static void moveUp(BasicTree tree) {
      if (!canMoveUp(tree)) {
         return;
      }
      tree.stopEditing();
      // because canMoveUp checks that they all have the same parent, take advantage of that fact here:
      BasicTreeNode parent = tree.getSelectionNode().getParent();
      int[] parentPath = toPosPath(parent);
      TreePath[] paths = tree.getSelectionPaths();
      int[] children = new int[paths.length];
      for (int i = 0; i < paths.length; i++) {
         children[i] = parent.getIndexOfChild(paths[i].getLastPathComponent());
      }
      int leadPath = parent.getIndexOfChild(tree.getSelectionNode());
      Arrays.sort(children);
      MoveUpDownEdit edit = new MoveUpDownEdit(tree, parentPath, children, leadPath, true);

      //tree.getUndoableEditHandler().registerEdit(edit);
      edit.doit();
   }

   public static void moveDown(BasicTree tree) {
      if (!canMoveDown(tree)) {
         return;
      }
      tree.stopEditing();
      // because canMoveUp checks that they all have the same parent, take advantage of that fact here:
      BasicTreeNode parent = tree.getSelectionNode().getParent();
      int[] parentPath = toPosPath(parent);
      TreePath[] paths = tree.getSelectionPaths();
      int[] children = new int[paths.length];
      for (int i = 0; i < paths.length; i++) {
         children[i] = parent.getIndexOfChild(paths[i].getLastPathComponent());
      }
      int leadPath = parent.getIndexOfChild(tree.getSelectionNode());
      Arrays.sort(children);
      MoveUpDownEdit edit = new MoveUpDownEdit(tree, parentPath, children, leadPath, false);
      //tree.getUndoableEditHandler().registerEdit(edit);
      edit.doit();
   }

   private static class MoveUpDownEdit extends AbstractUndoableEdit {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int[] mParentPath;
      private final int[] mChildren;
      private final int mPrimaryPath;
      private final BasicTree mTree;
      private final boolean mMoveUp;

      public MoveUpDownEdit(BasicTree tree, int[] parentPath, int[] children, int primaryPath, boolean moveUp) {
         mTree = tree;
         mParentPath = parentPath;
         mChildren = children;
         mPrimaryPath = primaryPath;
         mMoveUp = moveUp;
      }

      public String getPresentationName() {
         return mMoveUp ? BasicTreeResources.MOVE_UP : BasicTreeResources.MOVE_DOWN;
      }

      public void undo() throws CannotUndoException {
         super.undo();
         mTree.stopEditing();
         undoit();
      }

      public void redo() throws CannotUndoException {
         super.redo();
         mTree.stopEditing();
         doit();
      }

      public void doit() {
         TreePath ppath = fromPosPath(mTree, mParentPath);
         if (ppath == null) {
            return;
         }
         BasicTreeNode parent = (BasicTreeNode) ppath.getLastPathComponent();
         for (int i = 0; i < mChildren.length; i++) {
            int idx = mChildren[i];
            if (mMoveUp) {
               if (idx < 1 || idx >= parent.getChildCount()) {
                  return;
               }
            }
            else {
               if (idx < 0 || idx + 1 >= parent.getChildCount()) {
                  return;
               }
            }
         }
         int direction = mMoveUp ? -1 : 1;
         if (direction < 0) {
            for (int i = 0; i < mChildren.length; i++) {
               int idx = mChildren[i];
               BasicTreeNode removed = parent.getChild(idx);
               if (removeFrom(mTree, removed) == null) {
                  continue;
               }
               int newindex = idx + direction;
               addTo(mTree, parent, newindex, removed);
            }
         }
         else {
            for (int i = mChildren.length - 1; i >= 0; i--) {
               int idx = mChildren[i];
               BasicTreeNode removed = parent.getChild(idx);
               if (removeFrom(mTree, removed) == null) {
                  continue;
               }
               int newindex = idx + direction;
               addTo(mTree, parent, newindex, removed);
            }
         }
         mTree.clearSelection();
         BasicTreeNode leadnode = parent.getChild(mPrimaryPath + direction);
         mTree.setSelectionPath(leadnode.getTreePath());
         for (int i = 0; i < mChildren.length; i++) {
            int idx = mChildren[i];
            if (idx != mPrimaryPath) {
               BasicTreeNode node = parent.getChild(mChildren[i] + direction);
               mTree.addSelectionPath(node.getTreePath());
            }
         }
         mTree.fireTreeNodesMovedInParent(parent.getTreePath(), mChildren, direction);
         mTree.reenableButtons();
         mTree.requestFocus();
      }

      private void undoit() {
         TreePath ppath = fromPosPath(mTree, mParentPath);
         if (ppath == null) {
            return;
         }
         BasicTreeNode parent = (BasicTreeNode) ppath.getLastPathComponent();
         for (int i = 0; i < mChildren.length; i++) {
            int idx = mChildren[i];
            if (mMoveUp) {
               if (idx < 1 || idx >= parent.getChildCount()) {
                  return;
               }
            }
            else {
               if (idx < 0 || idx + 1 >= parent.getChildCount()) {
                  return;
               }
            }
         }
         int direction = mMoveUp ? -1 : 1;
         for (int i = mChildren.length - 1; i >= 0; i--) { // go backwards:
            int idx = mChildren[i];
            BasicTreeNode removed = parent.getChild(idx + direction);
            if (removeFrom(mTree, removed) == null) {
               continue;
            }
            addTo(mTree, parent, idx, removed);
         }
         mTree.clearSelection();
         BasicTreeNode leadnode = parent.getChild(mPrimaryPath);
         mTree.setSelectionPath(leadnode.getTreePath());
         for (int i = 0; i < mChildren.length; i++) {
            int idx = mChildren[i];
            if (idx != mPrimaryPath) {
               BasicTreeNode node = parent.getChild(mChildren[i]);
               mTree.addSelectionPath(node.getTreePath());
            }
         }
         mTree.reenableButtons();
         mTree.requestFocus();
      }
   }

   public static boolean canMoveIn(BasicTree tree) {
      if (tree.isRootNull()) {
         return false;
      }
      BasicTreeNode node = tree.getSelectionNode();
      if (node == null) {
         return false;
      }
      if (tree.getSelectionCount() > 1) {
         return false;
      }
      return node.canMoveIn();
   }

   /*public static void moveIn(BasicTree tree) {
       if (!canMoveIn(tree)) {
           return;
       }
       tree.stopEditing();
       BasicTreeNode node = tree.getSelectionNode();
       BasicTreeNode group = tree.buildGroupNode(node);
       if (group==null) {
           return;
       }
       BasicTreeNode p = node.getParent();
       if (p==null) {
           tree.setRootNode(group);
       } else {
           replace(tree,node,group);
       }
       addTo(tree,group,0,node);
       tree.setSelectionPath(node.getTreePath());
   }*/

   public static void moveIn(BasicTree tree) {
      if (!canMoveIn(tree)) {
         return;
      }
      tree.stopEditing();

      BasicTreeNode node = tree.getSelectionNode();
      BasicTreeNode parent = node.getParent();
      if (parent == null) {
         // n/a in use remaining (this will be moved to EditableTree)
         //WCETODO make this undoable.
         // root.
         /*BasicTree.BasicNodeBuilder group = tree.buildGroupNode(node);
         // ... and that they share the same parent.
         if (group==null)
         {
             return;
         }
         replace(tree,node,group);
         addTo(tree,group,0,node);*/
         return;
      }
      BasicTree.BasicNodeBuilder group = tree.buildGroupNode(node);
      if (group == null) {
         return;
      }

      int[] pp = toPosPath(parent.getTreePath());
      TreePath[] paths = tree.getSelectionPaths();
      int[] children = new int[paths.length];
      for (int i = 0; i < paths.length; i++) {
         children[i] = parent.getIndexOfChild(paths[i].getLastPathComponent());
      }
      int leadPath = parent.getIndexOfChild(tree.getSelectionNode());
      Arrays.sort(children);

      MoveInEdit moe = new MoveInEdit(tree, pp, children, leadPath, group);
      //tree.getUndoableEditHandler().registerEdit(moe);
      moe.moveIn();
   }

   private static class MoveInEdit extends AbstractUndoableEdit {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final BasicTree mTree;
      private final int[] mParentPath;
      private final int[] mChildIndexes;
      private final int mLeadIndex;
      private final BasicTree.BasicNodeBuilder m_node;

      public MoveInEdit(BasicTree tree, int[] parentPath, int[] childIndexes, int leadIndex, BasicTree.BasicNodeBuilder node) {
         mTree = tree;
         mParentPath = parentPath;
         mChildIndexes = childIndexes;
         mLeadIndex = leadIndex;
         m_node = node;
      }

      public String getPresentationName() {
         return BasicTreeResources.MOVE_IN;
      }

      public void undo() throws CannotUndoException {
         super.undo();
         undoit();
      }

      public void redo() throws CannotRedoException {
         super.redo();
         moveIn();
      }

      private void moveIn() {
         mTree.stopEditing();

         TreePath pp = fromPosPath(mTree, mParentPath);
         if (pp == null) {
            return;
         }

         BasicTreeNode parent = (BasicTreeNode) pp.getLastPathComponent();
         BasicTreeNode lead = parent.getChild(mLeadIndex);
         if (lead == null) {
            return;
         }
         BasicTreeNode group = m_node.buildNode();
         // ... and that they share the same parent.
         if (group == null) {
            return;
         }
         BasicTreeNode[] children = new BasicTreeNode[mChildIndexes.length];
         for (int i = 0; i < mChildIndexes.length; i++) {
            children[i] = parent.getChild(mChildIndexes[i]);
            if (children[i] == null) {
               return;
            }
         }
         for (int i = 0; i < mChildIndexes.length; i++) {
            if (children[i] == lead) {
               replace(mTree, lead, group);
            }
            else {
               removeFrom(mTree, children[i]);
            }
         }
         for (int i = 0; i < mChildIndexes.length; i++) {
            addTo(mTree, group, i, children[i]);
         }
         updateSelection(children);
      }

      private void undoit() {
         mTree.stopEditing();

         TreePath pp = fromPosPath(mTree, mParentPath);
         if (pp == null) {
            return;
         }
         BasicTreeNode parent = (BasicTreeNode) pp.getLastPathComponent();
         /*BasicTreeNode gparent = parent.getParent();
         if (gparent==null)
         {
             return;
         }*/
         //int index = model.getIndexOfChild(gparent,parent);
         BasicTreeNode[] removed = new BasicTreeNode[mChildIndexes.length];
         BasicTreeNode[] readded = new BasicTreeNode[mChildIndexes.length];
         for (int i = 0; i < mChildIndexes.length; i++) {
            removed[i] = parent.getChild(mChildIndexes[i]);
            readded[i] = removed[i].getChild(0);
         }
         for (int i = 0; i < removed.length; i++) {
            removeFrom(mTree, readded[i]);
            removeFrom(mTree, removed[i]);
            addTo(mTree, parent, mChildIndexes[i], readded[i]);
         }
         updateSelection(readded);
      }

      private void updateSelection(BasicTreeNode[] removed) {
         mTree.clearSelection();

         // set the lead selection path:
         for (int i = 0; i < removed.length; i++) {
            if (mChildIndexes[i] == mLeadIndex) {
               TreePath tp = removed[i].getTreePath();
               mTree.setSelectionPath(tp);
            }
         }
         // add the others:
         for (int i = 0; i < removed.length; i++) {
            if (mChildIndexes[i] != mLeadIndex) {
               TreePath tp = removed[i].getTreePath();
               mTree.addSelectionPath(tp);
            }
         }
         mTree.reenableButtons();
         mTree.requestFocus();
      }
   }

   public static boolean canMoveOut(BasicTree tree) {
      if (tree.mRootIsNull) {
         return false;
      }
      TreePath[] paths = tree.getSelectionPaths();
      if (paths == null || paths.length == 0) {
         return false;
      }
      BasicTreeNode onlyParent = null;
      for (int i = 0; i < paths.length; i++) {
         BasicTreeNode node = (BasicTreeNode) paths[i].getLastPathComponent();
         if (node == null) {
            return false;
         }
         BasicTreeNode p = node.getParent();
         if (p == null) {
            return false;
         }
         if (i == 0) {
            onlyParent = p;
         }
         else {
            if (onlyParent != p) {
               return false;
            }
         }
         BasicTreeNode p2 = p.getParent();
         if (p2 == null) {
            return false;
         }
         if (!node.canMoveOut()) {
            return false;
         }
      }
      return true;
   }

   public static void moveOut(BasicTree tree) {
      if (!canMoveOut(tree)) {
         return;
      }
      BasicTreeNode node = tree.getSelectionNode();
      BasicTreeNode parent = node.getParent();
      int[] pp = toPosPath(parent);
      TreePath[] paths = tree.getSelectionPaths();
      int[] children = new int[paths.length];
      for (int i = 0; i < paths.length; i++) {
         children[i] = parent.getIndexOfChild(paths[i].getLastPathComponent());
      }
      int leadPath = parent.getIndexOfChild(tree.getSelectionNode());
      Arrays.sort(children);

      MoveOutEdit moe = new MoveOutEdit(tree, pp, children, leadPath);
      //tree.getUndoableEditHandler().registerEdit(moe);
      moe.moveOut();
   }

   private static class MoveOutEdit extends AbstractUndoableEdit {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final BasicTree mTree;
      private final int[] mParentPath;
      private final int[] mChildIndexes;
      private final int mLeadIndex;

      public MoveOutEdit(BasicTree tree, int[] parentPath, int[] childIndexes, int leadIndex) {
         mTree = tree;
         mParentPath = parentPath;
         mChildIndexes = childIndexes;
         mLeadIndex = leadIndex;
      }

      public String getPresentationName() {
         return BasicTreeResources.MOVE_OUT;
      }

      public void undo() throws CannotUndoException {
         super.undo();
         undoit();
      }

      public void redo() throws CannotRedoException {
         super.redo();
         moveOut();
      }

      private void moveOut() {
         mTree.stopEditing();

         TreePath pp = fromPosPath(mTree, mParentPath);
         if (pp == null) {
            return;
         }
         BasicTreeNode parent = (BasicTreeNode) pp.getLastPathComponent();
         BasicTreeNode gparent = parent.getParent();
         if (gparent == null) {
            return;
         }
         int index = gparent.getIndexOfChild(parent);
         BasicTreeNode[] removed = new BasicTreeNode[mChildIndexes.length];
         for (int i = 0; i < mChildIndexes.length; i++) {
            removed[i] = parent.getChild(mChildIndexes[i]);
         }
         for (int i = 0; i < removed.length; i++) {
            removeFrom(mTree, removed[i]);
            addTo(mTree, gparent, index + 1 + i, removed[i]);
         }
         updateSelection(removed);
      }

      private void undoit() {
         mTree.stopEditing();

         TreePath pp = fromPosPath(mTree, mParentPath);
         if (pp == null) {
            return;
         }
         BasicTreeNode parent = (BasicTreeNode) pp.getLastPathComponent();
         BasicTreeNode gparent = parent.getParent();
         if (gparent == null) {
            return;
         }
         int index = gparent.getIndexOfChild(parent);
         BasicTreeNode[] removed = new BasicTreeNode[mChildIndexes.length];
         for (int i = 0; i < mChildIndexes.length; i++) {
            removed[i] = gparent.getChild(index + i + 1);
         }
         for (int i = 0; i < removed.length; i++) {
            removeFrom(mTree, removed[i]);
            addTo(mTree, parent, mChildIndexes[i], removed[i]);
         }
         updateSelection(removed);
      }

      private void updateSelection(BasicTreeNode[] removed) {
         mTree.clearSelection();
         // set the lead selection path:
         for (int i = 0; i < removed.length; i++) {
            if (mChildIndexes[i] == mLeadIndex) {
               mTree.setSelectionPath(removed[i].getTreePath());
            }
         }
         // add the others:
         for (int i = 0; i < removed.length; i++) {
            if (mChildIndexes[i] != mLeadIndex) {
               mTree.addSelectionPath(removed[i].getTreePath());
            }
         }
         mTree.reenableButtons();
         mTree.requestFocus();
      }
   }

   /**
    * Utility add method, does not undo/redo.
    */
   private static void addTo(BasicTree tree, BasicTreeNode parent, int at, BasicTreeNode child) {
      parent.getChildCount(); // force building of children.

      // hope we can add:
      if (!parent.addAt(at, child)) {
         return; // it's gone...
      }
      parent.addedAt(at, child);
      tree.getBasicModel().fireAdded(parent, child);

      tree.markReportDirty();
      tree.contentChanged();
   }

   /**
    * Utility remove method, does not undo/redo.
    */
   private static BasicTreeNode removeFrom(BasicTree tree, BasicTreeNode node) {
      BasicTreeNode parent = node.getParent();
      int pos = parent.getIndexOfChild(node);
      boolean removed = parent.remove(pos);
      if (!removed) {
         return null;
      }
      parent.removed(pos);
      tree.getBasicModel().fireRemoved(parent, pos, node);

      tree.markReportDirty();
      tree.contentChanged();

      return node;
   }

   public static void move(BasicTree tree, BasicTreeNode node, BasicTreeNode to, int toIndex) {
      MoveEdit me = new MoveEdit(tree, toPosPath(node), toPosPath(to), toIndex);
      //tree.getUndoableEditHandler().registerEdit(me);
      me.doit();
   }

   private static class MoveEdit extends AbstractUndoableEdit {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final BasicTree mTree;
      private final int[] mFromPath;
      private final int[] mToPath;
      private final int mToIndex;
      private int[] mUndoFromPath;
      private int[] mUndoToPath;
      private int mUndoToIndex;

      public MoveEdit(BasicTree tree, int[] fromPath, int[] toPath, int toIndex) {
         mTree = tree;
         mFromPath = fromPath;
         mToPath = toPath;
         mToIndex = toIndex;
      }

      public void undo() throws CannotUndoException {
         super.undo();
         undoit();
      }

      public String getPresentationName() {
         return BasicTreeResources.MOVE;
      }

      public void doit() {
         TreePath f = fromPosPath(mTree, mFromPath);
         TreePath t = fromPosPath(mTree, mToPath);
         if (f == null || t == null) {
            return;
         }
         BasicTreeNode fn = (BasicTreeNode) f.getLastPathComponent();
         BasicTreeNode tn = (BasicTreeNode) t.getLastPathComponent();
         move(fn, tn, mToIndex);
         mTree.requestFocus();
      }

      private void undoit() {
         if (mUndoFromPath == null) {
            return;
         }
         TreePath from = fromPosPath(mTree, mUndoFromPath);
         if (from == null) {
            return;
         }
         TreePath to = fromPosPath(mTree, mUndoToPath);
         if (to == null) {
            return;
         }
         BasicTreeNode node = (BasicTreeNode) from.getLastPathComponent();
         BasicTreeNode rnode = removeFrom(mTree, node);
         BasicTreeNode tonode = (BasicTreeNode) to.getLastPathComponent();
         if (rnode != null) {
            addTo(mTree, tonode, mUndoToIndex, rnode);
            mTree.setSelectionPath(rnode.getTreePath());
         }
         mTree.requestFocus();
      }

      private void move(BasicTreeNode node, BasicTreeNode to, int toIndex) {
         if (node.getParent() == to) {
            // in same directory.
            int p1 = node.getParent().getIndexOfChild(node);
            int actualIndex;
            if (p1 > toIndex) {
               actualIndex = toIndex;
            }
            else {
               actualIndex = toIndex - 1;
            }
            BasicTreeNode p = node.getParent();
            int pos = p.getIndexOfChild(node);
            BasicTreeNode removed = removeFrom(mTree, node);
            if (removed == null) {
               return;
            }
            addTo(mTree, to, actualIndex, removed);
            mUndoFromPath = toPosPath(removed);
            mUndoToPath = toPosPath(p);
            mUndoToIndex = pos;

            mTree.setSelectionPath(removed.getTreePath());
         }
         else {
            // pretty simple:
            BasicTreeNode p = node.getParent();
            int pos = p.getIndexOfChild(node);
            BasicTreeNode rnode = removeFrom(mTree, node);
            if (rnode != null) {
               addTo(mTree, to, toIndex, rnode);
               mTree.setSelectionPath(rnode.getTreePath());
               mUndoFromPath = toPosPath(rnode);
               mUndoToPath = toPosPath(p);
               mUndoToIndex = pos;
            }
         }
      }
   }

   public static void moveEditingUp(BasicTree tree, boolean collapse) {
      // edit the next row above.
      TreePath path = tree.getEditingPath();
      if (collapse) {
         expandCurrentRow(tree, false);
      }
      if (path == null) {
         return;
      }
      int newRow = tree.getRowForPath(path) - 1;
      if (newRow < 0) {
         newRow = tree.getRowCount() - 1;
      }
      editRow(tree, newRow);
   }

   public static void expandCurrentRow(BasicTree tree, boolean expand) {
      TreePath path = tree.getSelectionPath();
      if (path == null) {
         return;
      }
      if (expand) {
         tree.expandPath(path);
      }
      else {
         tree.collapsePath(path);
      }
   }

   public static void moveEditingDown(BasicTree tree, boolean expand) {
      TreePath path = tree.getEditingPath();
      if (expand) {
         expandCurrentRow(tree, true);
      }
      // edit the next row above.
      if (path == null) {
         return;
      }
      int newRow = tree.getRowForPath(path) + 1;
      if (newRow >= tree.getRowCount()) {
         newRow = 0;
      }
      editRow(tree, newRow);
   }

   public static void expandAndEdit(BasicTree tree, boolean expand) {
      TreePath path = tree.getEditingPath();
      expandCurrentRow(tree, expand);
      if (path == null) {
         return;
      }
      editRow(tree, tree.getRowForPath(path));
   }

   public static void editRow(BasicTree tree, int row) {
      if (row < 0 || row >= tree.getRowCount()) {
         // sanity
         return;
      }
      final TreePath path = tree.getPathForRow(row);
      tree.stopEditing();
      tree.waitForReport(); // gives time.
      tree.clearSelection();
      tree.setSelectionPath(path);
      // Sometimes above doesn't work:
      final BasicTree ftree = tree;
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            ftree.startEditingAtPath(path);
            ftree.mEditor.mCurrentField.requestFocus();
         }
      });
   }

   public static boolean canEdit(BasicTree tree) {
      BasicTreeNode node = tree.getSelectionNode();
      if (node == null) {
         return false;
      }
      return node.isDialogEditable();
   }

   public static void showEditor(BasicTree tree) {
      BasicTreeNode node = tree.getSelectionNode();
      /* WCETODO -- hacky, binding tree is only thing used now, it allows edit of nothing, so allow here.if (node==null)
      {
          return;
      }*/
      //tree.stopEditing();
      //tree.waitForReport();
      tree.edit(node);
   }

   public static void hideEditor(BasicTree tree) {
      tree.hideEditor();
   }

   public static void expandAll(BasicTree tree) {
      BasicTreeNode node = tree.getSelectionNode();
      if (node == null) {
         node = tree.getRootNode();
      }
      expandAll(tree, node);
   }

   public static void expandAllContent(BasicTree tree) {
      tree.waitForReport();
      BasicTreeNode node = tree.getSelectionNode();
      if (node == null) {
         node = tree.getRootNode();
      }
      expandAllContent(tree, node);
   }

   public static void expandAllErrors(BasicTree tree) {
      tree.waitForReport();
      BasicTreeNode node = tree.getSelectionNode();
      if (node == null) {
         node = tree.getRootNode();
      }
      expandAllErrors(tree, node);
   }

   /**
    * Expands the tree to fill in (roughly) the amount of space available.
    */
   public static void expandToFit(BasicTree tree) {
      int rc = tree.getVisibleRowCount();
      tree.stopEditing();
      tree.waitForReport();
      expandToFit(tree, tree.getRootNode(), rc);
   }

   private static void expandToFit(BasicTree tree, BasicTreeNode node, int available) {
      int cc = node.getChildCount();
      if (cc > available) {
         return;
      }
      tree.expandPath(node.getTreePath());
      int newAvailable = available - cc;
      for (int i = 0; i < cc; i++) {
         expandToFit(tree, node.getChild(i), newAvailable);
      }
   }

   private static void expandAll(BasicTree tree, BasicTreeNode node) {
      tree.stopEditing();
      tree.waitForReport();
      expandAllNode(tree, node, new HashSet<Object>(), new HashSet<Object>(), 0);
   }

   private static void expandAllNode(BasicTree tree, BasicTreeNode node, Set<Object> recursiveSet, Set<Object> total, int depth) {
      // Stop if there are just too many... some recursive schemas will cause this.
      if (total.size() > 1000) {
         return;
      }
      if (depth > 12) {
         return; // don't go too deep (another recursion buster)
      }
      // Also stop plain ol' recurse:
      Object obj = node.getIdentityTerm();
      if (obj != null) {
         if (recursiveSet.contains(obj)) {
            // recursion buster.
            return;
         }
         recursiveSet.add(obj);
         total.add(obj);
      }
      tree.expandPath(node.getTreePath());
      int ct = node.getChildCount();
      for (int i = 0; i < ct; i++) {
         BasicTreeNode cnode = node.getChild(i);
         expandAllNode(tree, cnode, recursiveSet, total, depth + 1);
      }
      if (obj != null) {
         recursiveSet.remove(obj);
      }
   }

   private static void expandAllContent(BasicTree tree, BasicTreeNode node) {
      tree.stopEditing();
      tree.waitForReport();
      expandAllContentNode(tree, node, new HashSet<Object>(), new HashSet<Object>());
      tree.markReportDirty();
   }

   private static void expandAllErrors(BasicTree tree, BasicTreeNode node) {
      tree.stopEditing();
      tree.waitForReport();
      ArrayList<TreePath> expandPaths = new ArrayList<TreePath>();

      // find all things to expand:
      expandAllErrorsNode(tree, node, new HashSet<Object>(), new HashSet<Object>(), expandPaths);

      // close everything:
      collapseAllPathsBelow(tree, node.getTreePath());

      // open those we found:
      for (int i = 0; i < expandPaths.size(); i++) {
         TreePath path = expandPaths.get(i);
         tree.expandPath(path);
      }

      tree.expandPath(new TreePath(tree.getRootNode())); // make sure root stays expanded...

      tree.markReportDirty();
   }

   private static void expandAllContentNode(BasicTree tree, BasicTreeNode node, Set<Object> set, Set<Object> total) {
      // Stop if there are just too many... some recursive schemas will cause this.
      if (total.size() > 1000) {
         return;
      }
      // Also stop plain ol' recurse:
      Object obj = node.getIdentityTerm();
      if (obj != null) {
         if (set.contains(obj)) {
            return;
         }
         set.add(obj);
         total.add(obj);
      }
      if (node.hasChildContent()) {
         tree.expandPath(node.getTreePath());
         int ct = node.getChildCount();
         for (int i = 0; i < ct; i++) {
            BasicTreeNode cnode = node.getChild(i);
            expandAllContentNode(tree, cnode, set, total);
         }
      }
      else {
         tree.collapsePath(node.getTreePath());
      }
      if (obj != null) {
         set.remove(obj);
      }
   }

   private static boolean expandAllErrorsNode(BasicTree tree, BasicTreeNode node, Set<Object> set, Set<Object> total, ArrayList<TreePath> paths) {
      // Stop if there are just too many... some recursive schemas will cause this.
      if (total.size() > 1000) {
         return false;
      }
      // Also stop plain ol' recurse:
      Object obj = node.getIdentityTerm();
      if (obj != null) {
         if (set.contains(obj)) {
            return false;
         }
         set.add(obj);
         total.add(obj);
      }
      boolean anyErrors = false;
      int ct = node.getChildCount();
      for (int i = 0; i < ct; i++) {
         BasicTreeNode cnode = node.getChild(i);
         if (expandAllErrorsNode(tree, cnode, set, total, paths)) {
            anyErrors = true;
         }
      }
      if (anyErrors) {
         paths.add(node.getTreePath());
      }
      if (obj != null) {
         set.remove(obj);
      }
      return node.getLineError() != null;
   }
}

