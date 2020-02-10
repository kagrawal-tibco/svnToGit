package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import java.util.ArrayList;

import javax.swing.JCheckBox;

import com.tibco.cep.mapper.xml.xdata.bind.BindUtilities;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingAutofill;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.ForEachBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateEditorConfiguration;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.Utilities;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.wizard.DefaultWizardPanel;
import com.tibco.cep.studio.mapper.ui.wizard.WizardPanel;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * This is a wizard choice panel for surrounding things with a for-each.
 */
public class SurroundWithForEachWizard extends DefaultWizardPanel {
   private BindingWizardState m_state;
   private TemplateReport m_on;
   private String m_xpath;
   private String m_relpath;
   private JCheckBox m_adjustChildFormulas;
   private boolean m_hasAutoFill;
   private SmSequenceType m_droppedType;

   public SurroundWithForEachWizard(final BindingWizardState state, final TemplateReport on, final String xpath) {
      m_state = state;
      m_on = on;
      m_xpath = xpath;

      String relpath = Utilities.makeRelativeXPath(m_on.getContext(), null, m_xpath, null);
      m_relpath = relpath;

      Expr expr = Parser.parse(relpath);
      SmSequenceType droppedType = expr.evalType(on.getContext()).xtype;
      m_droppedType = droppedType;
      final JCheckBox adjustChildFormulas = new JCheckBox(BindingWizardResources.ADJUST_CONTAINED_FORMULA);
      m_adjustChildFormulas = adjustChildFormulas;
      final ExprContext newContext = on.getContext().createWithInput(droppedType.prime());
      boolean hasChildFormulas = BindUtilities.hasUpdateableNestedChildren(on.getBinding(), on.getChildContext(), newContext, relpath);
      adjustChildFormulas.setSelected(true);
      if (hasChildFormulas) {
         setComponent(adjustChildFormulas);
      }

      String termName = SmSequenceTypeSupport.getDisplayName(on.getComputedType());
      String msg = DataIcons.printf(BindingWizardResources.FOR_EACH_CREATE, relpath, termName);
      setLabel(msg);

      m_hasAutoFill = BindingAutofill.hasAutofill(on, droppedType);
   }

   public static WizardPanel createIfRequired(BindingWizardState state, TemplateReport on, String xpath) {
      SmSequenceType candidateType = on.getExpectedType();
      if (candidateType == null) {
         return null;
      }
      // If this is a marker binding, we want to use the computed type (the actual type of the marker) because it may
      // be more specific than the expected type. (for example, a choice-choice panel can have already made a choice
      // of a type)
      if (on.getBinding() instanceof MarkerBinding) {
         candidateType = on.getComputedType();
      }
      SmSequenceType type = SmSequenceTypeSupport.stripParens(candidateType);

      // Don't want to use quantifier here because, for choices, etc. we really care if the choice is repeating, not
      // if any of the elements in the choice repeats.
      SmCardinality oc = type.getOccurrence();
      if (oc == null) {
         return null;
      }
      if (oc.getMaxOccurs() > 1) {
         // repeats:
         WizardPanel addForEach = new SurroundWithForEachWizard(state, on, xpath);
         return addForEach;
      }
      return null;
   }

   public boolean canGoToNext() {
      return m_hasAutoFill;
   }

   public boolean canFinish() {
      return !m_hasAutoFill;
   }

   public WizardPanel next() {
      if (!canGoToNext()) {
         return null;
      }
      // Make a copy of everything:
      TemplateEditorConfiguration tec = m_state.createConfigurationCopy(m_on.getBinding());
      Binding cb = BindingManipulationUtils.getEquivalentBinding(BindingManipulationUtils.getContainingTemplateBinding(m_on.getBinding()),
                                                                 m_on.getBinding(),
                                                                 tec.getBinding());
      TemplateReport newReport = BindingManipulationUtils.getTemplateReportFor(tec,
                                                                               m_state.getFormulaCache(),
                                                                               cb);

      // Insert the for each:
      Binding b = doWork(newReport);

      // Rerun report:
//        Binding cb2 = BindingManipulationUtils.getEquivalentBinding(m_on.getBinding().getContainingStylesheetBinding(),b,tec.getBinding());
      TemplateReport newReport2 = BindingManipulationUtils.getTemplateReportFor(tec,
                                                                                m_state.getFormulaCache(),
                                                                                b);

      TemplateReport elementChild = newReport2.getChild(0);
      // Hand over to the next guy:
      return new AutoFillWizardPanel(m_state, elementChild);
   }

   public void finish() {
      Binding b = doWork(m_on);
      m_state.wizardFinished(b);
   }

   /**
    * Inserts the for-each around the node.
    *
    * @param on The report to use (does not touch {@link #m_on}).
    * @return The new for-each node.
    */
   @SuppressWarnings("rawtypes")
private Binding doWork(TemplateReport on) {
      ForEachBinding fob = new ForEachBinding(BindingElementInfo.EMPTY_INFO, m_relpath);
      Binding el;
      if (on.getBinding() instanceof MarkerBinding) {
         // instantiate the marker:
         el = BindingManipulationUtils.createAppropriateAttributeOrElementBinding((MarkerBinding) on.getBinding());
         if (el == null) {
            // In the case of a choice or wildcard, this will be null, just leave it as a marker:
            el = on.getBinding().cloneDeep();
         }
      }
      else {
         el = on.getBinding().cloneDeep();
      }
      fob.addChild(el);
      BindingManipulationUtils.replaceInParent(on.getBinding(), fob);
      BindingDropWizardUtils.preserveTreeStateAfterInsertion(m_state.getTree(),
                                                             m_on.getBinding(), // the original
                                                             fob, // inserted into
                                                             el // the cloned version of the original.
      );
      if (m_adjustChildFormulas.isSelected()) {
         final ExprContext oldContext = on.getContext();
         final ExprContext newContext = on.getContext().createWithInput(m_droppedType.prime());
         BindUtilities.updateNestedFormula(el, oldContext, newContext, m_relpath, new ArrayList());
      }
      return fob;
   }

   public void enter() {
      super.enter();
      m_state.showStep(m_on.getBinding());
   }
}

;
