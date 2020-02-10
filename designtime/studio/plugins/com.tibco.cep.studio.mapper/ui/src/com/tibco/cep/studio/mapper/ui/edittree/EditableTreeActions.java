package com.tibco.cep.studio.mapper.ui.edittree;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import org.xml.sax.SAXException;

import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeResources;

/**
 * Implements many of the actions for {@link EditableTree}; grouped here for better organization.
 */
public final class EditableTreeActions {
   public static boolean canCopy(EditableTree tree) {
      return tree.getCopyPasteHandler() != null;
   }

   public static void copy(EditableTree tree) {
      if (!canCopy(tree)) {
         return;
      }
      tree.stopEditing();
      Object node = tree.getSelectionNode();
      if (node == null) {
         node = tree.getRootNode();
      }
      Transferable trans = tree.getCopyPasteHandler().getTransferableForNode(node);
      if (trans == null) {
         return;
      }
      Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
      ClipboardOwner co;
      if (trans instanceof ClipboardOwner) {
         co = (ClipboardOwner) trans;
      }
      else {
         co = new StringSelection(""); // n/a, just pass in a dummy.
      }
      clip.setContents(trans, co);
   }

   public static boolean canCut(EditableTree tree) {
      if (!canCopy(tree)) {
         return false;
      }
      if (!tree.isTreeEditable() || !tree.isEnabled()) {
         return false;
      }
      return tree.getSelectionNode() != null;
   }

   public static void cut(EditableTree tree) {
      if (!canCut(tree)) {
         return;
      }
      copy(tree);
      delete(tree);
   }

   public static boolean canPaste(EditableTree editableTree) {
      if (!editableTree.isEditable() || !editableTree.isTreeEditable() || !editableTree.isEnabled()) {
         return false;
      }
      if (editableTree.isEditing()) {
         return false;
      }
      if (editableTree.getCopyPasteHandler() == null) {
         return false;
      }
      return editableTree.getSelectionNode() != null;
   }

   public static void paste(final EditableTree tree) {
      if (!canPaste(tree)) {
         return;
      }
      tree.stopEditing();
      Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
      Transferable tf = clip.getContents(tree);
      Object pasteOn = tree.getSelectionNode();
      if (pasteOn == null) {
         return;
      }
      Object node;
      try {
         node = tree.getCopyPasteHandler().createNodeFromTransferable(tf);
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
         return;
      }
      catch (Exception e) {
         // Shouldn't happen.
         e.printStackTrace(System.err);
         return;
      }
      if (node == null) {
         return;
      }
      if (tree.getEditableModel().getParent(pasteOn) == null) {
         // root, do a replace: (can't do insert-before)
         replace(tree, pasteOn, node);
         tree.markReportDirty();
      }
      else {
         // otherwise, insert before (much nicer)
         addNodeAt(tree, pasteOn, node, BasicTreeResources.PASTE);
         tree.markReportDirty();
      }
       tree.contentChanged();
   }

   /**
    * Does a node replace; not an undoable action.
    */
   private static void replace(EditableTree tree, Object rnode, Object withNode) {
      if (rnode == null) {
         return;
      }
      if (withNode == null) {
         return;
      }
      EditableTreeModel model = tree.getEditableModel();
      Object pn = model.getParent(rnode);
      if (pn == null) {
         model.setRoot(withNode);
      }
      else {
         int index = model.getIndexOfChild(pn, rnode);
         removeFrom(tree, rnode);
         addTo(tree, pn, index, withNode);
      }
      TreePath wpath = EditableTreePathUtils.getTreePath(model, withNode);
      tree.expandPath(wpath);
      tree.markReportDirty(); // Must mark this dirty here, otherwise setSelectinoPath below <may> fill in dirty (bad) reports.
      // (It's ok to mark it dirty a bunch -- it's just a dirty flag, just so long as we don't wait for report too frequently)
      tree.setSelectionPath(wpath);
   }

   public static boolean canDelete(EditableTree tree) {
      EditableTreeModel model = tree.getEditableModel();
      if (!tree.isTreeEditable() || !tree.isEnabled()) {
         return false;
      }
      if (model.getAllowsRootNull() && model.isRootNull()) {
         return false;
      }
      TreePath[] paths = tree.getSelectionPaths();
      if (paths == null || paths.length == 0) {
         return false;
      }
      Object parent = getFirstParent(tree, paths);
      if (parent == null) {
         // It's the root.
         return model.getAllowsRootNull();
      }
      // Not the root:
      if (!hasSingleParent(tree, paths)) {
         return false;
      }
      return true;
   }

   public static void delete(final EditableTree tree) {
      if (!canDelete(tree)) {
         return;
      }
      tree.stopEditing();
      tree.waitForReport();
      TreePath[] paths = tree.getSelectionPaths();
      final EditableTreeModel model = tree.getEditableModel();
      Object parent = null;
      for (int i = 0; i < paths.length; i++) {
         Object n = paths[i].getLastPathComponent();
         Object pp = model.getParent(n);
         if (parent != null && pp != parent) {
            // can't have more than 1 parent.
            return;
         }
         parent = pp;
      }
      if (parent == null && model.getAllowsRootNull() && !model.isRootNull()) {
         //WCETODO make this undoable:
         model.setRootNull(true);
         tree.repaint();
         tree.contentChanged();
         tree.clearSelection();
         return;
      }
      final int[] childrenIndexes = new int[paths.length];
      final Object[] children = new Object[paths.length];
      final Object[] replacement = new Object[paths.length];
      for (int i = 0; i < childrenIndexes.length; i++) {
         Object ch = paths[i].getLastPathComponent();
         childrenIndexes[i] = model.getIndexOfChild(parent, ch);
         replacement[i] = model.getDeleteReplacement(ch);
      }
      Arrays.sort(childrenIndexes);
      for (int i = 0; i < childrenIndexes.length; i++) {
         children[i] = model.getChild(parent, childrenIndexes[i]);
      }

      final int[] parentPosPath = EditableTreePathUtils.toPosPath(model, parent);

      // in this case, (literal), just add next
      UndoableEdit ua = new AbstractUndoableEdit() {
         /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void undo() throws CannotUndoException {
            Object addTo = EditableTreePathUtils.nodeFromPosPath(tree, parentPosPath);
            if (addTo != null) {
               //if (foptionalReplacementMarker!=null)
               //{
               /*for (int i=0;i<childrenIndexes.length;i++)
               {
                   removeFrom(tree,model.getChild(addTo,indexOfChild));
               }*/
               //}
               for (int i = 0; i < childrenIndexes.length; i++) {
                  if (replacement[i] != null) {
                     Object node = model.getChild(addTo, childrenIndexes[i]);
                     replace(tree, node, children[i]);
                  }
                  else {
                     addTo(tree, addTo, childrenIndexes[i], children[i]);
                  }
               }
               Object newSel = model.getChild(addTo, childrenIndexes[0]);
               TreePath newSelect = EditableTreePathUtils.getTreePath(model, newSel);
               tree.expandPath(newSelect);
               TreePath addToPath = EditableTreePathUtils.getTreePath(model, newSel);
               tree.setSelectionPath(addToPath); // reselect parent so you can just hit the button again.
               tree.requestFocus();
               tree.markReportDirty();
            }
            else {
               throw new CannotUndoException();
            }
         }

         public void redo() throws CannotRedoException {
            Object objParent = EditableTreePathUtils.nodeFromPosPath(tree, parentPosPath);

            // remove can mean either blank it out or eliminate it...
            // eliminate here:
            int pos = 0;
            for (int i = childrenIndexes.length - 1; i >= 0; i--) // go backwards
            {
               Object n = model.getChild(objParent, childrenIndexes[i]);
               Object r = replacement[i];
               if (r != null) {
                  replace(tree, n, r);
               }
               else {
                  if (removeFrom(tree, n) == null) {
                     tree.markReportDirty();
                     return;
                  }
               }
               pos = childrenIndexes[i];
            }
            if (model.getChildCount(objParent) > 0) {
               int cc = model.getChildCount(objParent);
               Object ch = model.getChild(objParent, Math.min(pos, cc - 1));
               TreePath path = EditableTreePathUtils.getTreePath(model, ch);
               tree.setSelectionPath(path);
            }
            else {
               TreePath ppath = EditableTreePathUtils.getTreePath(model, objParent);
               tree.setSelectionPath(ppath);
            }
            tree.markReportDirty();
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
         tree.getUndoManager().addEdit(ua);
      }
      catch (CannotRedoException cre) {
         // eat it, can't do it.
      }
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
   public static void addAt(final EditableTree tree) {

      if (!canAddAt(tree)) {
         return;
      }
      tree.stopEditing();
      Object node = tree.getSelectionNode();
      final EditableTreeModel model = tree.getEditableModel();
      Object parent = node == null ? null : model.getParent(node);

      if ((node == null || parent == null) && !tree.getTreeMode()) {
         addChild(tree);
         // select the one we just added:
         return;
      }
      Object addToOriginal = model.getParent(node);
      final Object newnode = model.createNewChild(addToOriginal);

      if (newnode == null) {
         return;
      }
      addNodeAt(tree, node, newnode, BasicTreeResources.INSERT);
   }

   public static void addNodeAt(final EditableTree tree, Object node, final Object newnode, final String undoLabel) {
      final EditableTreeModel model = tree.getEditableModel();
      Object addToOriginal = model.getParent(node);
      if (addToOriginal == null) {
         return; // sanity.
      }

      final int addAt = model.getIndexOfChild(addToOriginal, node);
      final int[] addToIndexPath = EditableTreePathUtils.toPosPath(model, addToOriginal);

      AddEdit ua = new AddEdit(tree, addToIndexPath, addAt, newnode, undoLabel);
      tree.getUndoManager().addEdit(ua);
      ua.doit();
   }

   private static class AddEdit extends AbstractUndoableEdit {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final EditableTree m_tree;
      private final int[] m_addToPath;
      private final int m_addAt;
      private final Object m_newnode;
      private final String m_undoLabel;

      public AddEdit(EditableTree tree, int[] addToPath, int addAt, Object newnode, String undoLabel) {
         m_tree = tree;
         m_addToPath = addToPath;
         m_addAt = addAt;
         m_newnode = newnode;
         m_undoLabel = undoLabel;
      }

      public String getPresentationName() {
         return m_undoLabel;
      }

      public void undo() throws CannotUndoException {
         super.undo();
         EditableTreeModel model = m_tree.getEditableModel();
         Object addTo = EditableTreePathUtils.nodeFromPosPath(m_tree, m_addToPath);
         if (addTo != null && model.getChildCount(addTo) > m_addAt && m_addAt >= 0) {
            Object newSelObj = model.getChild(addTo, m_addAt);
            removeFrom(m_tree, newSelObj);
            TreePath newSelect = EditableTreePathUtils.getTreePath(model, newSelObj);
            m_tree.expandPath(newSelect);
            m_tree.setSelectionPath(newSelect);
            m_tree.requestFocus();
            m_tree.markReportDirty();
         }
         else {
            throw new CannotUndoException();
         }
      }

      public void redo() throws CannotRedoException {
         super.redo();
         doit();
      }

      public void doit() {
         EditableTreeModel model = m_tree.getEditableModel();
         Object addTo = EditableTreePathUtils.nodeFromPosPath(m_tree, m_addToPath);
         if (addTo != null) {
            addTo(m_tree, addTo, m_addAt, m_newnode);
            TreePath path = EditableTreePathUtils.getTreePath(model, m_newnode);
            m_tree.expandPath(path);
            m_tree.setSelectionPath(path);
            m_tree.requestFocus();
            m_tree.markReportDirty();
         }
         else {
            throw new CannotRedoException();
         }
      }
   }

   /**
    * Adds a new last child to the selected node.
    *
    * @param tree
    */
   public static void addChild(final EditableTree tree) {

      if (!canAddChild(tree)) {
         return;
      }
      tree.stopEditing();

      Object node = tree.getSelectionNode();
      if (node == null && !tree.getTreeMode()) {
         node = tree.getRootNode();
      }
      final EditableTreeModel model = tree.getEditableModel();
      final int addAt = model.getChildCount(node); // add last child.
      final Object newnode = model.createNewChild(node);

      if (newnode == null) {
         return;
      }
      final int[] addToIndexPath = EditableTreePathUtils.toPosPath(model, node);

      // in this case, (literal), just add next
      UndoableEdit ua = new AbstractUndoableEdit() {
         /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void undo() throws CannotUndoException {
            Object addTo = EditableTreePathUtils.nodeFromPosPath(tree, addToIndexPath);
            if (addTo != null && model.getChildCount(addTo) > addAt) {
               removeFrom(tree, model.getChild(addTo, addAt));
               TreePath path = EditableTreePathUtils.getTreePath(model, addTo);
               tree.expandPath(path);

               // Mark report dirty before selection because selection may cause an edit:
               tree.markReportDirty();

               tree.setSelectionPath(path); // reselect parent so you can just hit the button again.
               tree.requestFocus();
            }
            else {
               throw new CannotUndoException();
            }
         }

         public void redo() throws CannotRedoException {
            Object addTo = EditableTreePathUtils.nodeFromPosPath(tree, addToIndexPath);
            if (addTo != null) {
               addTo(tree, addTo, addAt, newnode);
               TreePath path = EditableTreePathUtils.getTreePath(model, addTo);
               tree.expandPath(path);

               // Mark the report dirty before selection, because selection may edit:
               tree.markReportDirty();

               if (tree.getTreeMode()) {
                  tree.setSelectionPath(path); // reselect parent so you can just hit the button again.
               }
               else {
                  TreePath newpath = EditableTreePathUtils.getTreePath(model, newnode);
                  tree.setSelectionPath(newpath);
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
      tree.getUndoManager().addEdit(ua);
      ua.redo();
   }

   /**
    * Checks if {@link #addAt} should be allowed.
    *
    * @param tree The tree to add to.
    * @return the success condition.
    */
   public static boolean canAddAt(EditableTree tree) {
      EditableTreeModel etm = tree.getEditableModel();


      if (etm.getAllowsRootNull() && etm.isRootNull()) {
         return false;
      }
      Object node = tree.getSelectionNode();
      if (node == null) {
         // If we're in list mode, this works.

         if (!tree.getTreeMode()) {
            return true;
         }
         return false;
      }
      // new style tree here:
      Object parent = tree.getEditableModel().getParent(node);
      // simple enough: (if it already has a child, then it must be able to have more)
      if (!tree.getTreeMode()) {
         // In list mode, allow this.
         return true;
      }

      return parent != null;
   }

   /**
    * Adds either to the top, or after the currently selected node.
    *
    * @param tree The tree to add to.
    */
   public static void addTopOrChild(EditableTree tree) {
      EditableTreeModel model = tree.getEditableModel();
      if (model.getAllowsRootNull() && model.isRootNull()) {
         tree.stopEditing();
         model.setRootNull(false);
         tree.repaint();
         tree.contentChanged();
         tree.markReportDirty();

         TreePath path = EditableTreePathUtils.getTreePath(tree.getEditableModel(), tree.getRootNode());
         // Force reselection.
         tree.clearSelection();
         tree.setSelectionPath(path);
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
   public static boolean canAddChild(EditableTree tree) {
      Object node = tree.getSelectionNode();
      if (node == null) {
         if (!tree.getTreeMode()) {
            return true;
         }
         return false;
      }
      return tree.getEditableModel().canHaveChildren(node);
   }

   public static boolean canMoveDown(EditableTree tree) {
      EditableTreeModel model = tree.getEditableModel();
      if (model.isRootNull()) {
         return false;
      }
      TreePath[] paths = tree.getSelectionPaths();
      if (paths == null || paths.length == 0) {
         return false;
      }
      Object onlyParent = null;
      for (int i = 0; i < paths.length; i++) {
         Object node = paths[i].getLastPathComponent();
         Object parent = model.getParent(node);
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
         int pos = model.getIndexOfChild(parent, node);
         int cc = model.getChildCount(parent);
         if (pos < 0 || pos + 1 >= cc) {
            return false;
         }
      }
      return true;
   }

   public static boolean canMoveUp(EditableTree tree) {
      EditableTreeModel model = tree.getEditableModel();
      if (model.isRootNull()) {
         return false;
      }
      TreePath[] paths = tree.getSelectionPaths();
      if (paths == null || paths.length == 0) {
         return false;
      }
      Object onlyParent = null;
      for (int i = 0; i < paths.length; i++) {
         Object node = paths[i].getLastPathComponent();
         Object parent = model.getParent(node);
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
         int pos = model.getIndexOfChild(parent, node);
         if (pos <= 0) {
            return false;
         }
      }
      return true;
   }

   /**
    * Moves the selected nodes up (if applicable)
    *
    * @param tree The tree.
    */
   public static void moveUp(EditableTree tree) {
      if (!canMoveUp(tree)) {
         return;
      }
      tree.stopEditing();
      // because canMoveUp checks that they all have the same parent, take advantage of that fact here:
      EditableTreeModel model = tree.getEditableModel();
      Object parent = model.getParent(tree.getSelectionNode());
      int[] parentPath = EditableTreePathUtils.toPosPath(model, parent);
      TreePath[] paths = tree.getSelectionPaths();
      int[] children = new int[paths.length];
      for (int i = 0; i < paths.length; i++) {
         children[i] = model.getIndexOfChild(parent, paths[i].getLastPathComponent());
      }
      int leadPath = model.getIndexOfChild(parent, tree.getSelectionNode());
      Arrays.sort(children);
      MoveUpDownEdit edit = new MoveUpDownEdit(tree, parentPath, children, leadPath, true);

      tree.getUndoManager().addEdit(edit);
      edit.doit();
   }

   public static void moveDown(EditableTree tree) {
      if (!canMoveDown(tree)) {
         return;
      }
      tree.stopEditing();
      // because canMoveUp checks that they all have the same parent, take advantage of that fact here:
      EditableTreeModel model = tree.getEditableModel();
      Object parent = model.getParent(tree.getSelectionNode());
      int[] parentPath = EditableTreePathUtils.toPosPath(model, parent);
      TreePath[] paths = tree.getSelectionPaths();
      int[] children = new int[paths.length];
      for (int i = 0; i < paths.length; i++) {
         children[i] = model.getIndexOfChild(parent, paths[i].getLastPathComponent());
      }
      int leadPath = model.getIndexOfChild(parent, tree.getSelectionNode());
      Arrays.sort(children);
      MoveUpDownEdit edit = new MoveUpDownEdit(tree, parentPath, children, leadPath, false);
      tree.getUndoManager().addEdit(edit);
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
      private final EditableTree mTree;
      private final boolean mMoveUp;

      public MoveUpDownEdit(EditableTree tree, int[] parentPath, int[] children, int primaryPath, boolean moveUp) {
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
         EditableTreeModel model = mTree.getEditableModel();
         TreePath ppath = EditableTreePathUtils.fromPosPath(mTree.getEditableModel(), mParentPath);
         if (ppath == null) {
            return;
         }
         Object parent = ppath.getLastPathComponent();
         int parentChildCount = model.getChildCount(parent);
         for (int i = 0; i < mChildren.length; i++) {
            int idx = mChildren[i];
            if (mMoveUp) {
               if (idx < 1 || idx >= parentChildCount) {
                  return;
               }
            }
            else {
               if (idx < 0 || idx + 1 >= parentChildCount) {
                  return;
               }
            }
         }
         int direction = mMoveUp ? -1 : 1;
         if (direction < 0) {
            for (int i = 0; i < mChildren.length; i++) {
               int idx = mChildren[i];
               Object removed = model.getChild(parent, idx);
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
               Object removed = model.getChild(parent, idx);
               if (removeFrom(mTree, removed) == null) {
                  continue;
               }
               int newindex = idx + direction;
               addTo(mTree, parent, newindex, removed);
            }
         }
         mTree.clearSelection();

         // Mark report dirty before selection, may cause re-edit.
         mTree.markReportDirty();

         Object leadnode = model.getChild(parent, mPrimaryPath + direction);
         mTree.setSelectionPath(EditableTreePathUtils.getTreePath(model, leadnode));
         for (int i = 0; i < mChildren.length; i++) {
            int idx = mChildren[i];
            if (idx != mPrimaryPath) {
               Object node = model.getChild(parent, mChildren[i] + direction);
               mTree.addSelectionPath(EditableTreePathUtils.getTreePath(model, node));
            }
         }
         TreePath parentPath = EditableTreePathUtils.getTreePath(model, parent);
         mTree.fireTreeNodesMovedInParent(parentPath, mChildren, direction);
         mTree.reenableButtons();
         mTree.requestFocus();
      }

      private void undoit() {
         EditableTreeModel model = mTree.getEditableModel();
         TreePath ppath = EditableTreePathUtils.fromPosPath(model, mParentPath);
         if (ppath == null) {
            return;
         }
         Object parent = ppath.getLastPathComponent();
         int parentChildCount = model.getChildCount(parent);
         for (int i = 0; i < mChildren.length; i++) {
            int idx = mChildren[i];
            if (mMoveUp) {
               if (idx < 1 || idx >= parentChildCount) {
                  return;
               }
            }
            else {
               if (idx < 0 || idx + 1 >= parentChildCount) {
                  return;
               }
            }
         }
         int direction = mMoveUp ? -1 : 1;
         for (int i = mChildren.length - 1; i >= 0; i--) { // go backwards:
            int idx = mChildren[i];
            Object removed = model.getChild(parent, idx + direction);
            if (removeFrom(mTree, removed) == null) {
               continue;
            }
            addTo(mTree, parent, idx, removed);
         }
         mTree.clearSelection();

         // Mark report dirty before selection, may cause re-edit.
         mTree.markReportDirty();

         Object leadnode = model.getChild(parent, mPrimaryPath);
         mTree.setSelectionPath(EditableTreePathUtils.getTreePath(model, leadnode));
         for (int i = 0; i < mChildren.length; i++) {
            int idx = mChildren[i];
            if (idx != mPrimaryPath) {
               Object node = model.getChild(parent, mChildren[i]);
               mTree.addSelectionPath(EditableTreePathUtils.getTreePath(model, node));
            }
         }
         mTree.reenableButtons();
         mTree.requestFocus();
      }
   }

   public static boolean canMoveIn(EditableTree tree) {
      EditableTreeModel model = tree.getEditableModel();
      if (model.getAllowsRootNull() && model.isRootNull()) {
         return false;
      }
      TreePath[] paths = tree.getSelectionPaths();
      if (paths == null || paths.length == 0) {
         return false;
      }
      return hasSingleParent(tree, paths) || isSingleRoot(paths);
   }

   public static void moveIn(EditableTree tree) {
      if (!canMoveIn(tree)) {
         return;
      }
      EditableTreeModel model = tree.getEditableModel();
      tree.stopEditing();

      Object node = tree.getSelectionNode();
      Object parent = model.getParent(node);
      if (parent == null) {
         // At the root level:

         //WCETODO make this undoable.
         // root.
         Object group = model.createNewParent(node);
         // ... and that they share the same parent.
         if (group == null) {
            return;
         }
         replace(tree, node, group);
         addTo(tree, group, 0, node);
         return;
      }
      int[] pp = EditableTreePathUtils.toPosPath(model, parent);
      TreePath[] paths = tree.getSelectionPaths();
      int[] children = new int[paths.length];
      for (int i = 0; i < paths.length; i++) {
         children[i] = model.getIndexOfChild(parent, paths[i].getLastPathComponent());
      }
      Object lead = tree.getSelectionNode();
      int leadPath = model.getIndexOfChild(parent, lead);
      Arrays.sort(children);

      Object group = model.createNewParent(lead);
      if (group == null) {
         return; // cancel.
      }
      MoveInEdit moe = new MoveInEdit(tree, group, pp, children, leadPath);
      // Don't really want to be dependant on entire DesignerDocument API, when all we want is just the UndoManager part, but we have no choice....
      tree.getUndoManager().addEdit(moe);
      moe.moveIn();
   }

   public static boolean canMoveOut(EditableTree tree) {
      EditableTreeModel model = tree.getEditableModel();
      if (model.getAllowsRootNull() && model.isRootNull()) {
         return false;
      }
      TreePath[] paths = tree.getSelectionPaths();
      if (paths == null || paths.length == 0) {
         return false;
      }
      return hasSingleParent(tree, paths);
   }

   public static void moveOut(EditableTree tree) {
      if (!canMoveOut(tree)) {
         return;
      }
      EditableTreeModel model = tree.getEditableModel();
      Object node = tree.getSelectionNode();
      Object parent = model.getParent(node);
      int[] pp = EditableTreePathUtils.toPosPath(model, parent);
      TreePath[] paths = tree.getSelectionPaths();
      int[] children = new int[paths.length];
      for (int i = 0; i < paths.length; i++) {
         children[i] = model.getIndexOfChild(parent, paths[i].getLastPathComponent());
      }
      int leadPath = model.getIndexOfChild(parent, tree.getSelectionNode());
      Arrays.sort(children);

      MoveOutEdit moe = new MoveOutEdit(tree, pp, children, leadPath);
      tree.getUndoManager().addEdit(moe);
      moe.moveOut();
   }

   private static class MoveOutEdit extends AbstractUndoableEdit {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final EditableTree mTree;
      private final int[] mParentPath;
      private final int[] mChildIndexes;
      private final int mLeadIndex;

      public MoveOutEdit(EditableTree tree, int[] parentPath, int[] childIndexes, int leadIndex) {
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

         EditableTreeModel model = mTree.getEditableModel();
         TreePath pp = EditableTreePathUtils.fromPosPath(model, mParentPath);
         if (pp == null) {
            return;
         }
         Object parent = pp.getLastPathComponent();
         Object gparent = model.getParent(parent);
         if (gparent == null) {
            return;
         }
         int index = model.getIndexOfChild(gparent, parent);
         Object[] removed = new Object[mChildIndexes.length];
         for (int i = 0; i < mChildIndexes.length; i++) {
            removed[i] = model.getChild(parent, mChildIndexes[i]);
         }
         for (int i = 0; i < removed.length; i++) {
            removeFrom(mTree, removed[i]);
            addTo(mTree, gparent, index + 1 + i, removed[i]);
         }
         updateSelection(removed);
         mTree.markReportDirty();
         mTree.contentChanged();
      }

      private void undoit() {
         mTree.stopEditing();

         EditableTreeModel model = mTree.getEditableModel();
         TreePath pp = EditableTreePathUtils.fromPosPath(model, mParentPath);
         if (pp == null) {
            return;
         }
         Object parent = pp.getLastPathComponent();
         Object gparent = model.getParent(parent);
         if (gparent == null) {
            return;
         }
         int index = model.getIndexOfChild(gparent, parent);
         Object[] removed = new Object[mChildIndexes.length];
         for (int i = 0; i < mChildIndexes.length; i++) {
            removed[i] = model.getChild(gparent, index + 1 + i);
         }
         for (int i = 0; i < removed.length; i++) {
            removeFrom(mTree, removed[i]);
            addTo(mTree, parent, mChildIndexes[i], removed[i]);
         }
         updateSelection(removed);
         mTree.markReportDirty();
         mTree.contentChanged();
      }

      private void updateSelection(Object[] removed) {
         mTree.clearSelection();
         EditableTreeModel model = mTree.getEditableModel();

         // set the lead selection path:
         for (int i = 0; i < removed.length; i++) {
            if (mChildIndexes[i] == mLeadIndex) {
               TreePath tp = EditableTreePathUtils.getTreePath(model, removed[i]);
               mTree.setSelectionPath(tp);
            }
         }
         // add the others:
         for (int i = 0; i < removed.length; i++) {
            if (mChildIndexes[i] != mLeadIndex) {
               TreePath tp = EditableTreePathUtils.getTreePath(model, removed[i]);
               mTree.addSelectionPath(tp);
            }
         }
         mTree.reenableButtons();
         mTree.requestFocus();
      }
   }

   private static class MoveInEdit extends AbstractUndoableEdit {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final EditableTree mTree;
      private final int[] mParentPath;
      private final int[] mChildIndexes;
      private final int mLeadIndex;
      private final Object mMoveIntoNode;
      private int mUndoLeadIndex;

      public MoveInEdit(EditableTree tree, Object moveIntoNode, int[] parentPath, int[] childIndexes, int leadIndex) {
         mTree = tree;
         mMoveIntoNode = moveIntoNode;
         mParentPath = parentPath;
         mChildIndexes = childIndexes;
         mLeadIndex = leadIndex;
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

         EditableTreeModel model = mTree.getEditableModel();
         TreePath pp = EditableTreePathUtils.fromPosPath(model, mParentPath);
         if (pp == null) {
            return;
         }

         Object parent = pp.getLastPathComponent();
         Object lead = model.getChild(parent, mLeadIndex);
         if (lead == null) {
            return;
         }
         // ... and that they share the same parent.
         Object[] children = new Object[mChildIndexes.length];
         for (int i = 0; i < mChildIndexes.length; i++) {
            children[i] = model.getChild(parent, mChildIndexes[i]);
            if (children[i] == null) {
               return;
            }
         }
         for (int i = 0; i < mChildIndexes.length; i++) {
            if (children[i] == lead) {
               replace(mTree, lead, mMoveIntoNode);
            }
            else {
               removeFrom(mTree, children[i]);
            }
         }
         for (int i = 0; i < mChildIndexes.length; i++) {
            addTo(mTree, mMoveIntoNode, i, children[i]);
         }
         Object p = model.getParent(mMoveIntoNode);
         mUndoLeadIndex = model.getIndexOfChild(p, mMoveIntoNode);

         // Mark report dirty before selection (selection may cause edit)
         mTree.markReportDirty();

         updateSelection(children);
      }

      private void undoit() {
         mTree.stopEditing();

         EditableTreeModel model = mTree.getEditableModel();
         TreePath pp = EditableTreePathUtils.fromPosPath(model, mParentPath);
         if (pp == null) {
            return;
         }
         Object parent = pp.getLastPathComponent();
         Object[] removed = new Object[mChildIndexes.length];
         Object added = model.getChild(parent, mUndoLeadIndex);
         for (int i = 0; i < mChildIndexes.length; i++) {
            removed[i] = model.getChild(added, i);
         }
         removeFrom(mTree, added);
         for (int i = 0; i < removed.length; i++) {
            removeFrom(mTree, removed[i]);
            addTo(mTree, parent, mChildIndexes[i], removed[i]);
         }
         // Mark report dirty before selection (selection may cause edit)
         mTree.markReportDirty();

         updateSelection(removed);
      }

      private void updateSelection(Object[] removed) {
         mTree.clearSelection();
         EditableTreeModel model = mTree.getEditableModel();

         // set the lead selection path:
         for (int i = 0; i < removed.length; i++) {
            if (mChildIndexes[i] == mLeadIndex) {
               TreePath tp = EditableTreePathUtils.getTreePath(model, removed[i]);
               mTree.setSelectionPath(tp);
            }
         }
         // add the others:
         for (int i = 0; i < removed.length; i++) {
            if (mChildIndexes[i] != mLeadIndex) {
               TreePath tp = EditableTreePathUtils.getTreePath(model, removed[i]);
               mTree.addSelectionPath(tp);
            }
         }
         mTree.reenableButtons();
         mTree.requestFocus();
      }
   }

   /**
    * Internal utility add method, does not undo/redo.
    */
   private static void addTo(EditableTree tree, Object parent, int at, Object child) {
      EditableTreeModel model = tree.getEditableModel();

      // hope we can add:
      model.addAt(parent, at, child);

      // Not required; assumed done externally: tree.markReportDirty();
      tree.contentChanged();
   }

   /**
    * Internal utility remove method, does not undo/redo.
    */
   private static Object removeFrom(EditableTree tree, Object node) {
      EditableTreeModel model = tree.getEditableModel();
      Object parent = model.getParent(node);
      int pos = model.getIndexOfChild(parent, node);
      model.remove(parent, pos);

      tree.contentChanged();

      return node;
   }

   public static void move(EditableTree tree, Object node, Object to, int toIndex) {
      EditableTreeModel model = tree.getEditableModel();
      MoveEdit me = new MoveEdit(tree, EditableTreePathUtils.toPosPath(model, node), EditableTreePathUtils.toPosPath(model, to), toIndex);
      tree.getUndoManager().addEdit(me);
      me.doit();
   }

   private static class MoveEdit extends AbstractUndoableEdit {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final EditableTree mTree;
      private final int[] mFromPath;
      private final int[] mToPath;
      private final int mToIndex;
      private int[] mUndoFromPath;
      private int[] mUndoToPath;
      private int mUndoToIndex;

      public MoveEdit(EditableTree tree, int[] fromPath, int[] toPath, int toIndex) {
         mTree = tree;
         mFromPath = fromPath;
         mToPath = toPath;
         mToIndex = toIndex;
      }

      public void undo() throws CannotUndoException {
         super.undo();
         undoit();
      }

      public void redo() throws CannotRedoException {
         super.redo();
         doit();
      }

      public String getPresentationName() {
         return BasicTreeResources.MOVE;
      }

      public void doit() {
         TreePath f = EditableTreePathUtils.fromPosPath(mTree.getEditableModel(), mFromPath);
         TreePath t = EditableTreePathUtils.fromPosPath(mTree.getEditableModel(), mToPath);
         if (f == null || t == null) {
            return;
         }
         Object fn = f.getLastPathComponent();
         Object tn = t.getLastPathComponent();
         move(fn, tn, mToIndex);
         mTree.requestFocus();
      }

      private void undoit() {
         if (mUndoFromPath == null) {
            return;
         }
         TreePath from = EditableTreePathUtils.fromPosPath(mTree.getEditableModel(), mUndoFromPath);
         if (from == null) {
            return;
         }
         TreePath to = EditableTreePathUtils.fromPosPath(mTree.getEditableModel(), mUndoToPath);
         if (to == null) {
            return;
         }
         Object node = from.getLastPathComponent();
         Object rnode = removeFrom(mTree, node);
         Object tonode = to.getLastPathComponent();
         if (rnode != null) {
            addTo(mTree, tonode, mUndoToIndex, rnode);
            TreePath selPath = EditableTreePathUtils.getTreePath(mTree.getEditableModel(), rnode);
            mTree.setSelectionPath(selPath);
         }
         mTree.requestFocus();
      }

      private void move(Object node, Object to, int toIndex) {
         EditableTreeModel model = mTree.getEditableModel();
         Object pnode = model.getParent(node);
         if (pnode == to) {
            // in same directory.
            int p1 = model.getIndexOfChild(pnode, node);
            int actualIndex;
            if (p1 > toIndex) {
               actualIndex = toIndex;
            }
            else {
               actualIndex = toIndex - 1;
            }
            int pos = model.getIndexOfChild(pnode, node);
            Object removed = removeFrom(mTree, node);
            if (removed == null) {
               return;
            }
            addTo(mTree, to, actualIndex, removed);
            mUndoFromPath = EditableTreePathUtils.toPosPath(model, removed);
            mUndoToPath = EditableTreePathUtils.toPosPath(model, pnode);
            mUndoToIndex = pos;

            // Mark report dirty before selection:
            mTree.markReportDirty();

            TreePath removedPath = EditableTreePathUtils.getTreePath(model, removed);
            mTree.setSelectionPath(removedPath);
         }
         else {
            // pretty simple:
            Object p = model.getParent(node);
            int pos = model.getIndexOfChild(p, node);
            Object rnode = removeFrom(mTree, node);
            if (rnode != null) {
               addTo(mTree, to, toIndex, rnode);
               TreePath rpath = EditableTreePathUtils.getTreePath(model, rnode);

               // Mark report dirty before selection:
               mTree.markReportDirty();

               mTree.setSelectionPath(rpath);
               mUndoFromPath = EditableTreePathUtils.toPosPath(model, rnode);
               mUndoToPath = EditableTreePathUtils.toPosPath(model, pnode);
               mUndoToIndex = pos;
            }
         }
      }
   }

   /**
    * Expands the currently selected row.
    *
    * @param tree
    * @param expand
    */
   public static void expandCurrentRow(EditableTree tree, boolean expand) {
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

   /**
    * Recursively expands the given path of the tree (limits depth/size for monster or recursive trees, though)
    */
   public static void expandAll(EditableTree tree) {
      Object node = tree.getSelectionNode();
      if (node == null) {
         node = tree.getRootNode();
      }
      expand(tree, node, EXPAND_TYPE_ALL);
   }

   /**
    * Recursively expands the given path of the tree (limits depth/size for monster or recursive trees, though) for
    * content.
    * Requires {@link EditableTree#getExpansionHandler} to be set to function.
    */
   public static void expandContent(EditableTree tree) {
      Object node = tree.getSelectionNode();
      if (node == null) {
         node = tree.getRootNode();
      }
      expand(tree, node, EXPAND_TYPE_CONTENT);
   }

   /**
    * Recursively expands the given path of the tree (limits depth/size for monster or recursive trees, though) for
    * errors.
    * Requires {@link EditableTree#getExpansionHandler} to be set to function.
    */
   public static void expandErrors(EditableTree tree) {
      Object node = tree.getSelectionNode();
      if (node == null) {
         node = tree.getRootNode();
      }
      expand(tree, node, EXPAND_TYPE_ERROR);
   }

   @SuppressWarnings("rawtypes")
private static void expand(EditableTree tree, Object node, int expandType) {
      tree.stopEditing();
      tree.waitForReport();
      expandNode(tree, node, expandType, new HashSet<Object>(), new HashSet(), 0);
   }

   private static final int EXPAND_TYPE_ALL = 0;
   private static final int EXPAND_TYPE_CONTENT = 1;
   private static final int EXPAND_TYPE_ERROR = 2;

   /**
    * @param expandType One of {@link #EXPAND_TYPE_ALL}, etc.
    */
   @SuppressWarnings("rawtypes")
private static void expandNode(EditableTree tree, Object node, int expandType, Set<Object> recursiveSet, Set total, int depth) {
      // Stop if there are just too many... some recursive schemas will cause this.
      if (total.size() > 1000) {
         return;
      }
      if (depth > 10) {
         return; // don't go too deep (another recursion buster)
      }
      EditableTreeExpansionHandler eh = tree.getExpansionHandler();
      switch (expandType) {
         case EXPAND_TYPE_CONTENT:
            {
               if (eh == null || !eh.hasContent(node)) {
                  return;
               }
            }
            break;
         case EXPAND_TYPE_ERROR:
            {
               if (eh == null || !eh.hasError(node)) {
                  return;
               }
            }
            break;
      }
      Object identity = null;
      if (eh != null) {
         identity = eh.getNodeIdentity(node);
      }
      if (identity != null) {
         if (recursiveSet.contains(identity)) {
            // don't recurse.
            return;
         }
         recursiveSet.add(identity);
      }
      tree.expandPath(EditableTreePathUtils.getTreePath(tree.getEditableModel(), node));
      int ct = tree.getEditableModel().getChildCount(node);
      for (int i = 0; i < ct; i++) {
         Object cnode = tree.getEditableModel().getChild(node, i);
         expandNode(tree, cnode, expandType, recursiveSet, total, depth + 1);
      }
      recursiveSet.remove(identity);
   }

   /**
    * Checks that all the paths share the same parent.
    *
    * @param paths The paths.
    * @return true if they all have one parent, false otherwise.
    */
   private static boolean hasSingleParent(EditableTree tree, TreePath[] paths) {
      EditableTreeModel model = tree.getEditableModel();
      Object onlyParent = null;
      for (int i = 0; i < paths.length; i++) {
         Object node = paths[i].getLastPathComponent();
         if (node == null) {
            return false;
         }
         Object p = model.getParent(node);
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
      }
      return true;
   }

   /**
    * Gets the first parent out of the list of paths (useful for calling after {@link #hasSingleParent}.
    *
    * @param tree  The tree
    * @param paths The paths.
    * @return The first parent.
    */
   private static Object getFirstParent(EditableTree tree, TreePath[] paths) {
      EditableTreeModel model = tree.getEditableModel();
      Object node = paths[0].getLastPathComponent();
      return model.getParent(node);
   }

   /**
    * Checks that there is exactly 1 path that is the root.
    *
    * @param paths The paths.
    * @return true if they all have one parent, false otherwise.
    */
   private static boolean isSingleRoot(TreePath[] paths) {
      if (paths.length != 1) {
         return false;
      }
      return paths[0].getParentPath() == null;
   }
}

