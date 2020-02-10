package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import java.util.List;

import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.wizard.WizardPanel;

/**
 * The wizard panel for when a drop happens on top of a 'variable' which is using select.
 */
public class DropOnExistingSelectVariablePanel extends DropOnExistingBasicPanel {
   public DropOnExistingSelectVariablePanel(BindingWizardState state, TemplateReport on, String xpath) {
      super(state, on, xpath, "Variable");
   }

public void addAdditionalChoices(List<WizardPanel> choiceWizardPanels) {
   }
}

;
