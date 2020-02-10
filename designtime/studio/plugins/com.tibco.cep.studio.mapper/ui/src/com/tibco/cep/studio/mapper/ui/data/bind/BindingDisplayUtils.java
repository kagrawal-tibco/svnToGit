package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import com.tibco.cep.mapper.xml.xdata.bind.BindUtilities;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.BindingNamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateEditorConfiguration;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportArguments;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportFormulaCache;
import com.tibco.cep.mapper.xml.xdata.bind.virt.BindingVirtualizer;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeResources;
import com.tibco.cep.studio.mapper.ui.data.bind.wizard.BindingDropWizardPanelBuilder;
import com.tibco.cep.studio.mapper.ui.data.bind.wizard.BindingWizardResources;
import com.tibco.cep.studio.mapper.ui.data.bind.wizard.DropOnBindingWizardPanelBuilder;
import com.tibco.cep.studio.mapper.ui.data.bind.wizard.InstantiateChoiceChoicePanel;
import com.tibco.cep.studio.mapper.ui.data.utils.PreferenceUtils;
import com.tibco.cep.studio.mapper.ui.wizard.Wizard;
import com.tibco.cep.studio.mapper.ui.wizard.WizardPanel;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * Methods used by the GUI.
 */
public final class BindingDisplayUtils {
   public static void drop(UIAgent uiAgent,
                           TemplateReportFormulaCache formulaCache,
                           BindingTree tree,
                           TemplateReport onBinding,
                           String xpath) {
      // launch wizard here:

      // First, take care of any unfilled parents, if any:
      BindingWizardState state = new BindingWizardState(uiAgent,
                                                        formulaCache, tree,
                                                        onBinding.getBinding());

      onBinding = fillInAnyUnfilledParents(state, onBinding);

//        BindingDisplayUtils.un
      BindingDropWizardPanelBuilder firstPanelBuilder = getDropPanelBuilder();
      WizardPanel firstPanel = firstPanelBuilder.build(state, onBinding, xpath);
      if (firstPanel == null) {
         // trivial, no wizard required:
         firstPanelBuilder.buildFinisher(state, onBinding, xpath).run();
      }
      else {
         // non-trivial, show wizard:
         if (!showBindingWizard(tree, firstPanel)) {
            state.restore();
         }
      }
   }

   @SuppressWarnings("rawtypes")
public static void deleteAndMoveOut(BindingTree tree, TemplateReport on) {
      final ExprContext oldContext = on.getChildContext();
      final ExprContext newContext = on.getContext();
      Binding cloned = on.getBinding().getParent().cloneDeep();
      int ioc = on.getBinding().getParent().getIndexOfChild(on.getBinding());
      Binding clonedChild = cloned.getChild(ioc);

      BindUtilities.updateNestedChildFormula(clonedChild, oldContext, newContext, null, new ArrayList());
      BindingManipulationUtils.removeSaveChildren(clonedChild);

      BindingChangeUndoableEdit lde = new BindingChangeUndoableEdit(tree, on.getBinding().getParent(), cloned);
      lde.doit();
      lde.setPresentationName(BasicTreeResources.DELETE);
      tree.getUndoManager().addEdit(lde);

      tree.contentChanged();
      tree.rebuild();
   }

   /**
    * Runs a wizard (setting sizes, etc.).
    *
    * @return True if 'ok' was hit, 'false' otherwise.
    */
   private static boolean showBindingWizard(BindingTree tree, WizardPanel firstPanel) {
      Wizard w = Wizard.create(SwingUtilities.getWindowAncestor(tree));
      w.setCurrentPanel(firstPanel);
      w.setIconImage(((ImageIcon)DataIcons.getWindowTitleIcon()).getImage());
      UIAgent uiAgent = tree.getUIAgent();
      Dimension sz = PreferenceUtils.readDimension(uiAgent, "BindingWizard.windowSize",
                                                   new Dimension(400, 200),
                                                   Toolkit.getDefaultToolkit().getScreenSize(),
                                                   new Dimension(450, 250));
      Point loc = PreferenceUtils.readLocation(uiAgent, "BindingWizard.windowLocation",
                                               null,
                                               sz);

      if (loc == null) {
         w.setLocationRelativeTo(w.getOwner());
      }
      else {
         w.setLocation(loc);
      }
      w.setSize(sz);
      w.setTitle(BindingWizardResources.WIZARD_TITLE);
      w.setModal(true);
      w.setVisible(true);
      PreferenceUtils.writeDimension(uiAgent, "BindingWizard.windowSize", w.getSize());
      PreferenceUtils.writePoint(uiAgent, "BindingWizard.windowLocation", w.getLocation());
      return !w.wasCancelled();
   }

   /**
    * For when the user drops from the binding tree somewhere left (i.e. the create gesture)
    *
    * @param node The source node.
    */
   public static boolean dropLeft(BindingTree tree, BindingNode node) {
      if (!tree.isTreeEditable()) {
         return false;
      }
      final Binding b = node.getBinding();
      if (!(b instanceof MarkerBinding)) {
         return false;
      }
      TemplateBinding nt = BindingWizardState.createTemplateCopy(b);
      Binding nb = BindingManipulationUtils.getEquivalentBindingInTemplate(b, nt);
      Binding rb = doLeftDrop(tree, nb);
      if (rb == null) {
         return false;
      }
      BindingChangeUndoableEdit lde = new BindingChangeUndoableEdit(tree, b, rb);
      lde.doit();
      lde.setPresentationName(BindingWizardResources.MAPPING_DROP_UNDOREDO);
      tree.getUndoManager().addEdit(lde);
      return true;
   }

   private static Binding doLeftDrop(BindingTree tree, Binding b) {
      BindingDisplayUtils.instantiateAnyAncestorOrSelfMarkers(b.getParent());
      MarkerBinding mb = (MarkerBinding) b;
      SmSequenceType t = mb.getMarkerType();
      SmSequenceType tp = SmSequenceTypeSupport.stripOccursAndParens(t);
      if (tp.getTypeCode() == SmSequenceType.TYPE_CODE_SEQUENCE || tp.getTypeCode() == SmSequenceType.TYPE_CODE_INTERLEAVE) {
         // make els of sequence.
         SmSequenceType[] items = SmSequenceTypeSupport.getAllSequences(tp);
         if (items.length == 0) {
            return null;
         }
         Binding[] newones = new Binding[items.length];
         for (int i = 0; i < items.length; i++) {
            newones[i] = new MarkerBinding(items[i]);
         }
         BindingManipulationUtils.replaceInParent(b, newones);
         return newones[0];
      }
      else {
         if (tp.getTypeCode() == SmSequenceType.TYPE_CODE_CHOICE) {
            WizardPanel ccp = InstantiateChoiceChoicePanel.createStandalone(b, tp);
            Binding bp = b.getParent();
            int ioc = bp.getIndexOfChild(b);
            if (!showBindingWizard(tree, ccp)) {
               return null;
            }
            // Since we're replacing the child, refetch by index:
            return bp.getChild(ioc);
         }
         else {
            return BindingDisplayUtils.instantiateAnyAncestorOrSelfMarkers(b);
         }
      }
   }

   private static TemplateReport fillInAnyUnfilledParents(BindingWizardState state, TemplateReport onBinding) {
      if (!hasAnyParentMarkers(onBinding.getBinding())) {
         // no work required.
         return onBinding;
      }
      // Ok, it does, go ahead & fill them in now.
      TemplateEditorConfiguration tec = state.createConfigurationCopy(onBinding.getBinding());
      Binding nc = BindingManipulationUtils.getEquivalentBindingInTemplate(onBinding.getBinding(), tec.getBinding());
      instantiateAnyAncestorOrSelfMarkers(nc.getParent());

      // Changed it all, re-run report:
      TemplateReport finalReport = BindingManipulationUtils.getTemplateReportFor(tec,
                                                                                 state.getFormulaCache(),
                                                                                 nc);
      return finalReport;
   }

   public static BindingDropWizardPanelBuilder getDropPanelBuilder() {
      BindingDropWizardPanelBuilder next = new DropOnBindingWizardPanelBuilder();
      // Because this changes the parents (potentially, need to recompute)
      // repeats thing sucks: AccountForRepeatsWizardPanelBuilder repeats = new AccountForRepeatsWizardPanelBuilder(next); //WCETODO maybe move this inside.

      return next;//repeats;//BindingDropWizardPanelBuilder wp = new FillInUnfilledParentWizardPanelBuilder(repeats);
      //return wp;
   }

   /**
    * For the given binding, make any ancestors that are markers real, same for self.<br>
    * This function mutates the tree.
    *
    * @param onBinding The binding
    * @return returns The update binding, may be the same object (but in a possibly mutated tree)
    */
   public static Binding instantiateAnyAncestorOrSelfMarkers(Binding onBinding) {
      if (onBinding == null) {
         return null;
      }
      Binding b = instantiateAnyAncestorOrSelfMarkers(onBinding.getParent());
      if (onBinding instanceof MarkerBinding) {
         MarkerBinding commentBinding = (MarkerBinding) onBinding;
         Binding p = commentBinding.getParent();
         Binding realBinding = BindingManipulationUtils.createAppropriateAttributeOrElementBindingNeverNull(commentBinding);
         int iic = p.getIndexOfChild(onBinding);
         p.removeChild(iic);
         p.addChild(iic, realBinding);
         moveChildrenOver(commentBinding, realBinding);
         if (b == null) {
            b = p;
         }
      }
      return b;
   }

   /**
    * Indicates if any of the parents of this binding are markers (not counting the binding itself)
    *
    * @param onBinding
    */
   public static boolean hasAnyParentMarkers(Binding onBinding) {
      Binding at = onBinding.getParent();
      while (at != null) {
         if (at instanceof MarkerBinding) {
            return true;
         }
         at = at.getParent();
      }
      return false;
   }

   /**
    * Checks if the binding has any non-marker children.
    *
    * @param binding The binding.
    * @return true if there are no non-marker children (including no chidlren), false otherwise.
    */
   public static boolean hasAnyNonMarkerChildren(Binding binding) {
      for (int i = 0; i < binding.getChildCount(); i++) {
         if (!(binding.getChild(i) instanceof MarkerBinding)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Fills in a 'default' (i.e. starting) binding on the template given the expected output type.<br>
    * This probably should only be called with an empty template.
    *
    * @param binding    The template binding.
    * @param outputType The expected output type of the template.
    */
   public static void initializeDefaultBindings(TemplateBinding binding, SmSequenceType outputType) {
      NamespaceContextRegistry ni = BindingNamespaceManipulationUtils.createNamespaceImporter(binding);
      Binding b = BindingManipulationUtils.createAppropriateAttributeOrElementOrMarkerBinding(outputType, ni);
      BindingVirtualizer.normalize(b, binding);
   }


   /**
    * Adds markers (for schema items) on the binding tree.
    */
   public static void addMarkers(TemplateEditorConfiguration tec, TemplateReportFormulaCache formulaCache) {
      TemplateReportArguments args = new TemplateReportArguments();
      args.setRecordingMissing(true);
      TemplateReport tr = TemplateReport.create(tec, formulaCache, args);
      BindingManipulationUtils.insertMarkers(tr);
   }

   private static void moveChildrenOver(Binding from, Binding to) {
      Binding[] c = from.getChildren();
      from.removeAllChildren();
      to.removeAllChildren();
      for (int i = 0; i < c.length; i++) {
         to.addChild(c[i]);
      }
   }
}

