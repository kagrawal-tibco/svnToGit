package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import java.util.List;

import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.wizard.WizardPanel;

/**
 * The wizard panel for when a drop happens on top of a 'grouping'.
 */
public class DropOnExistingGroupPanel extends DropOnExistingBasicPanel {
   public DropOnExistingGroupPanel(BindingWizardState state, TemplateReport on, String xpath) {
      super(state, on, xpath, "Grouping");
   }

public void addAdditionalChoices(List<WizardPanel> choiceWizardPanels) {
      // no additional choices for now.
   }
}

;
