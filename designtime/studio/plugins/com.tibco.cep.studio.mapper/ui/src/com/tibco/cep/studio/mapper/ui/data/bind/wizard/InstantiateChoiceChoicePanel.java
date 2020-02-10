package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.BindingNamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateEditorConfiguration;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.utils.XsdChoiceComboBox;
import com.tibco.cep.studio.mapper.ui.wizard.DefaultWizardPanel;
import com.tibco.cep.studio.mapper.ui.wizard.WizardPanel;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * The choice wizard panel for when a choice element is instantiated (also now does anonymous sequences).
 */
public class InstantiateChoiceChoicePanel extends DefaultWizardPanel {
   private BindingWizardState m_state;
   private Binding m_onBinding;
   private String m_xpath;
   private XsdChoiceComboBox m_comboBox;
   private boolean m_isAnonSeq; // Indicates if this is for a choice (false) or anonymous sequence (true)
   private BindingDropWizardPanelBuilder m_next;

   private InstantiateChoiceChoicePanel(BindingWizardState state, Binding on, SmSequenceType choiceType, String xpath, BindingDropWizardPanelBuilder nextPanel) {
      m_state = state;
      m_onBinding = on;
      m_xpath = xpath;
      m_next = nextPanel;
      SmSequenceType choiceOnly = SmSequenceTypeSupport.stripOccursAndParens(choiceType);
      SmSequenceType[] choices;
      if (SmSequenceTypeSupport.isSequence(choiceOnly)) {
         choices = SmSequenceTypeSupport.getAllSequences(choiceOnly);
         // slightly different label for sequences:
         setLabel("Map to");
         m_isAnonSeq = true;
      }
      else {
         choices = SmSequenceTypeSupport.getAllChoices(choiceOnly);
         setLabel("Make a");
         m_isAnonSeq = false;
      }
      XsdChoiceComboBox jcb = new XsdChoiceComboBox(choices);
      m_comboBox = jcb;
      if (state == null) {
         // standalone.
         JPanel jp = new JPanel(new BorderLayout(4, 0));
         jp.add(new JLabel("Choice: "), BorderLayout.WEST);
         jp.add(jcb);

         JPanel spacer = new JPanel(new BorderLayout());
         spacer.add(jp, BorderLayout.NORTH);
         setComponent(spacer);
      }
      else {
         setComponent(jcb);
      }
   }

   public static WizardPanel createStandalone(Binding on, SmSequenceType choice) {
      return new InstantiateChoiceChoicePanel(null, on, choice, null, null);
   }

   public static WizardPanel createIfApplicable(BindingWizardState state, TemplateReport report, String xpath, BindingDropWizardPanelBuilder builder) {
      SmSequenceType choiceType = report.getExpectedType();
      // If this is a marker binding, we want to use the computed type (the actual type of the marker) because it may
      // be more specific than the expected type. (In fact, this entire choice-choice panel can be recursively called if
      // there's a nasty content model such as (A | B | (C | D)*), and the user picks (C | D)* on the first pass --- in
      // subsequent passes, we want to use (C | D)* as the basis for future choices.
      if (report.getBinding() instanceof MarkerBinding) {
         choiceType = report.getComputedType();
      }
      if (choiceType == null) {
         // just in case.
         return null;
      }
      //WCETODO substitution groups!
      if (!SmSequenceTypeSupport.isChoice(choiceType) && !SmSequenceTypeSupport.isSequence(choiceType)) {
         // not applicable.
         return null;
      }
      return new InstantiateChoiceChoicePanel(state, report.getBinding(), choiceType, xpath, builder);
   }

   public boolean canGoToNext() {
      return m_state != null;
   }

   public boolean canFinish() {
      return m_state == null;
   }

   /**
    * Gets the choices to be displayed, for testing mostly.
    *
    * @return
    */
   public SmSequenceType[] getChoices() {
      return m_comboBox.getChoices();
   }

   /**
    * Sets the currently selected choice, for testing mostly.
    */
   public void setSelectedChoice(int index) {
      m_comboBox.setSelectedIndex(index);
   }

   public void finish() {
      SmSequenceType selchoice = m_comboBox.getSelectedXType();
      NamespaceContextRegistry ni = BindingNamespaceManipulationUtils.createNamespaceImporter(m_onBinding);
      Binding[] bindings = new Binding[]{BindingManipulationUtils.createAppropriateAttributeOrElementOrMarkerBinding(selchoice, ni)};
      BindingManipulationUtils.replaceInParent(m_onBinding, bindings);
   }

   public WizardPanel next() {
      SmSequenceType selchoice = m_comboBox.getSelectedXType();

      // In the case of anon. sequences, just instantiate one:
      NamespaceContextRegistry ni = BindingNamespaceManipulationUtils.createNamespaceImporter(m_onBinding);
      // Handles anon sequences, too.
      Binding[] bindings;
      int interestingIndex;
      if (m_isAnonSeq) {
         SmSequenceType[] choices = m_comboBox.getChoices();
         bindings = new Binding[choices.length];
         interestingIndex = m_comboBox.getSelectedIndex();
         for (int i = 0; i < choices.length; i++) {
            if (i == interestingIndex) {
               bindings[i] = BindingManipulationUtils.createAppropriateAttributeOrElementOrMarkerBinding(selchoice, ni);
            }
            else {
               bindings[i] = new MarkerBinding(choices[i]);
            }
         }
      }
      else {
         bindings = new Binding[]{BindingManipulationUtils.createAppropriateAttributeOrElementOrMarkerBinding(selchoice, ni)};
         interestingIndex = 0;
      }
      TemplateEditorConfiguration tec = m_state.createConfigurationCopy(m_onBinding);
      Binding equivalentBinding = BindingManipulationUtils.getEquivalentBindingInTemplate(m_onBinding, tec.getBinding());
      BindingManipulationUtils.replaceInParent(equivalentBinding, bindings);
      TemplateReport newReport = BindingManipulationUtils.getTemplateReportFor(tec,
                                                                               m_state.getFormulaCache(),
                                                                               bindings[interestingIndex]);
      WizardPanel wp = m_next.build(m_state, newReport, m_xpath);
      if (wp == null) {
         // SHOULD NOT happen, but just in case:
         DefaultWizardPanel dwp = new DefaultWizardPanel();
         dwp.setComponent(new JLabel("???"));
         dwp.setFinisher(m_next.buildFinisher(m_state, newReport, m_xpath));
         return dwp;
      }
      return wp;
   }

   public void enter() {
      super.enter();
      if (m_state != null) {
         m_state.showStep(m_onBinding);
      }
   }
}

;
