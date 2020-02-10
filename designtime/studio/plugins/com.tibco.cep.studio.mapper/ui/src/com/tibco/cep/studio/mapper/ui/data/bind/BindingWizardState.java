package com.tibco.cep.studio.mapper.ui.data.bind;

import javax.swing.tree.TreePath;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateEditorConfiguration;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportFormulaCache;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.TreeState;
import com.tibco.cep.studio.mapper.ui.data.bind.wizard.BindingWizardResources;

/**
 * Methods used by the GUI.
 */
public final class BindingWizardState {
   private final BindingTree mTree;
   private final TemplateBinding mOriginal;
   private final TreeState mOriginalTreeState;
   private final Binding mOriginalAtBinding;
   protected final UIAgent uiAgent;
   private final TemplateReportFormulaCache m_formulaCache;

   public UIAgent getUiAgent() {
	return uiAgent;
}

public BindingWizardState(UIAgent uiAgent,
                             TemplateReportFormulaCache functionCache, BindingTree tree, Binding onBinding) {
	   this.uiAgent = uiAgent;
	   mTree = tree;
      mOriginal = (TemplateBinding) tree.getTemplateEditorConfiguration().getBinding().clone();
      mOriginalTreeState = tree.getTreeState();
      m_formulaCache = functionCache;
      if (onBinding == null) {
         throw new NullPointerException();
      }
      mOriginalAtBinding = BindingManipulationUtils.getEquivalentBinding(tree.getTemplateEditorConfiguration().getBinding(), onBinding, mOriginal);
   }

   public void wizardFinished(Binding endBinding) {
      BindingChangeUndoableEdit de = new BindingChangeUndoableEdit(mTree, mOriginalAtBinding, endBinding);
      de.setPresentationName(BindingWizardResources.MAPPING_DROP_UNDOREDO);
      de.doit();
      mTree.getUndoManager().addEdit(de);
      // No need to mark report dirty; the edit should do that.
   }

   public TemplateEditorConfiguration createConfigurationCopy(Binding onBinding) {
      TemplateBinding clonedB = createTemplateCopy(onBinding);
      TemplateEditorConfiguration tec = (TemplateEditorConfiguration) mTree.getTemplateEditorConfiguration().clone();
      tec.setBinding(clonedB);
      return tec;
   }

   public static TemplateBinding createTemplateCopy(Binding onBinding) {
      TemplateBinding tb = BindingManipulationUtils.getContainingTemplateBinding(onBinding);
      TemplateBinding clonedB = (TemplateBinding) tb.cloneDeep();
      // Copy this to get the namespaces, etc:
      StylesheetBinding sb = (StylesheetBinding) tb.getParent().cloneShallow();
      sb.addChild(clonedB);
      return clonedB;
   }

   public void restore() {
      // restore:
      BindingManipulationUtils.copyBindingContents(mOriginal, mTree.getTemplateEditorConfiguration().getBinding());
      mTree.rebuild();
      mTree.setTreeState(mOriginalTreeState);
   }

   public void showStep(Binding focalBinding) {

      // NOTE: this used to reshow the entire tree (new report, everything).
      // 1) This was just way too expensive.
      // 2) Given how the wizard is proceeding (getting much simpler), it doesn't make sense anymore.
      //

      /*TemplateEditorConfiguration tec2 = createConfigurationCopy(focalBinding);

      // Update the configuration, but preserve the tree state.
      TreeState state = mTree.getTreeState();
      mTree.setTemplateEditorConfiguration(tec2);
      mTree.waitForReport();
      mTree.setTreeState(state);*/

      BindingNode rn = (BindingNode) mTree.getRootNode();
      BindingNode nn = rn.findForBinding(focalBinding, true);
      if (nn != null) { // shouldn't ever be null..
         TreePath p = nn.getTreePath();
         mTree.expandPath(p);
         mTree.setSelectionPath(p);
         mTree.scrollPathToVisible(p);
      }
   }

   public BindingTree getTree() {
      return mTree;
   }

   public TemplateReportFormulaCache getFormulaCache() {
      return m_formulaCache;
   }

   /**
    * A utility assertion called by many wizard panels; catch nulls early rather than later.
    */
   public static void assertNonNulls(BindingWizardState state, TemplateReport on, String xpath) {
      if (state == null) {
         throw new NullPointerException("Null state");
      }
      if (on == null) {
         throw new NullPointerException("Null On");
      }
      if (xpath == null) {
         throw new NullPointerException("Null XPath");
      }
   }
}

