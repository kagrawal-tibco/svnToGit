package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import java.util.List;

import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.wizard.WizardPanel;

/**
 * The wizard panel for when a drop happens on top of a 'for-each' and 'for-each-group'.
 */
public class DropOnExistingForEachPanel extends DropOnExistingBasicPanel {
   public DropOnExistingForEachPanel(BindingWizardState state, TemplateReport on, String xpath) {
      super(state, on, xpath, "for-each");
   }

   public void addAdditionalChoices(List<WizardPanel> choiceWizardPanels) {
      WizardPanel wp = MergeParallelArraysChoicePanel.createIfAppropriate(getState(), getOn(), getXPath());
      if (wp != null) {
         choiceWizardPanels.add(wp);
      }
   }
}

;
