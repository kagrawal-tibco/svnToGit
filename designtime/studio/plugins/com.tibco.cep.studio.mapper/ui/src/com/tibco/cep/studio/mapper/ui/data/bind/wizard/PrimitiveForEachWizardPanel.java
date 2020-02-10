package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.ForEachBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualDataComponentBinding;
import com.tibco.cep.mapper.xml.xdata.xpath.Utilities;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.wizard.DefaultWizardPanel;
import com.tibco.cep.studio.mapper.ui.wizard.WizardPanel;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * This is a wizard choice panel for surrounding a primitive repeating element (i.e. element city* where city is a string) with a for-each & having a formula of '.'
 */
public class PrimitiveForEachWizardPanel extends DefaultWizardPanel {
   private BindingWizardState m_state;
   private TemplateReport m_on;
   private String m_xpath;
   private String m_relpath;

   public PrimitiveForEachWizardPanel(final BindingWizardState state, final TemplateReport on, final String xpath) {
      m_state = state;
      m_on = on;
      m_xpath = xpath;

      String relpath = Utilities.makeRelativeXPath(m_on.getContext(), null, m_xpath, null);
      m_relpath = relpath;

      String termName = SmSequenceTypeSupport.getDisplayName(on.getComputedType());
      //WCETODO resourceize.
      setLabel("For each '" + relpath + "' create a " + termName + " set to " + relpath);

   }

   public static WizardPanel create(BindingWizardState state, TemplateReport on, String xpath) {
      // repeats:
      WizardPanel addForEach = new PrimitiveForEachWizardPanel(state, on, xpath);
      return addForEach;
   }

   public boolean canGoToNext() {
      return false;
   }

   public boolean canFinish() {
      return true;
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
      if (el instanceof VirtualDataComponentBinding) {
         VirtualDataComponentBinding vdc = (VirtualDataComponentBinding) el;
         //WCETODO redovdc.setCopyMHasImplicitIf(false);
         vdc.setHasInlineFormula(true);
         vdc.setInlineIsText(false);
         el.setFormula(".");
      }
      fob.addChild(el);
      BindingManipulationUtils.replaceInParent(on.getBinding(), fob);
      BindingDropWizardUtils.preserveTreeStateAfterInsertion(m_state.getTree(),
                                                             m_on.getBinding(), // the original
                                                             fob, // inserted into
                                                             el // the cloned version of the original.
      );
      return fob;
   }

   public void enter() {
      super.enter();
      m_state.showStep(m_on.getBinding());
   }
}

;
