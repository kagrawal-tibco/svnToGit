package com.tibco.cep.studio.mapper.ui.data.bind;

import javax.swing.tree.TreePath;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;

/**
 * Methods used by the GUI.
 */
public class BindingChangeUndoableEdit extends AbstractUndoableEdit {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
// Make more efficient by storing highest point of change rather than entire template....)
   private TemplateBinding mBackup;
   private TemplateBinding mNew;
   private Binding mBackupAt;
   private Binding mNewAt;
   private BindingTree mTree;
   private String mPresentationName = "edit";

   public BindingChangeUndoableEdit(BindingTree tree, Binding backupAt, Binding newAt) {
      mTree = tree;
      mBackup = (TemplateBinding) BindingManipulationUtils.getContainingTemplateBinding(backupAt).cloneDeep();
      mNew = (TemplateBinding) BindingManipulationUtils.getContainingTemplateBinding(newAt).cloneDeep();
      mNewAt = newAt;
      mBackupAt = BindingManipulationUtils.getEquivalentBinding(BindingManipulationUtils.getContainingTemplateBinding(backupAt), backupAt, mBackup);
   }

   public void setPresentationName(String name) {
      mPresentationName = name;
   }

   public String getPresentationName() {
      return mPresentationName;
   }

   public void redo() throws CannotRedoException {
      super.redo();
      doit();
   }

   public void undo() throws CannotUndoException {
      super.undo();
      Binding cloned = mBackup.cloneDeep();
      Binding at = BindingManipulationUtils.getEquivalentBinding(mBackup, mBackupAt, cloned);
      BindingManipulationUtils.copyBindingContents(cloned, mTree.getTemplateEditorConfiguration().getBinding());
      setup(at);
   }

   /**
    * Do the first time.
    */
   public void doit() {
      Binding cloned = mNew.cloneDeep();
      Binding at = BindingManipulationUtils.getEquivalentBinding(mNew, mNewAt, cloned);
      BindingManipulationUtils.copyBindingContents(cloned, mTree.getTemplateEditorConfiguration().getBinding());
      setup(at);
   }

   private void setup(Binding selAt) {
      mTree.rebuild();
      if (selAt != null) {
         BindingNode rootNode = (BindingNode) mTree.getRootNode();
         BindingNode nn = rootNode.findForBinding(selAt, true);
         if (nn != null) { // shouldn't ever be null..
            TreePath p = nn.getTreePath();
            mTree.expandPath(p);
            mTree.setSelectionPath(p);
            mTree.scrollPathToVisible(p);
         }
      }
      // No need to mark report dirty; rebuild does that.
      mTree.contentChanged(); // (Do need to fire generic change event, though)
   }
}
