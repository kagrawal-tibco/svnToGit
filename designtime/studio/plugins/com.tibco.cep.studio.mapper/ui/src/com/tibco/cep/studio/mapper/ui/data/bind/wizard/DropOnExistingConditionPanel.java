package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import java.util.List;

import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.wizard.WizardPanel;

/**
 * The wizard panel for when a drop happens on top of a 'choose' or 'if'.
 */
public class DropOnExistingConditionPanel extends DropOnExistingBasicPanel {
   public DropOnExistingConditionPanel(BindingWizardState state, TemplateReport on, String xpath) {
      super(state, on, xpath, "Test");
   }

public void addAdditionalChoices(List<WizardPanel> choiceWizardPanels) {
   }
}

;
