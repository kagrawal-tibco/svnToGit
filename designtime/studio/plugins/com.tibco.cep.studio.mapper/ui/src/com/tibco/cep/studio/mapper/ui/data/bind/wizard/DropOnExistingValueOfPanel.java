package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import java.util.List;

import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.data.bind.ValueOfPanel;
import com.tibco.cep.studio.mapper.ui.wizard.WizardPanel;

/**
 * (Really for a VirtualDataComponent now, need renaming)
 * The wizard panel for when a drop happens on top of a 'value-of' (or simple element/attribute containing a value of)
 */
public class DropOnExistingValueOfPanel extends DropOnExistingBasicPanel {
   public DropOnExistingValueOfPanel(final BindingWizardState state, final TemplateReport on, String xpath) {
      super(state, on, xpath, ValueOfPanel.getBindingDisplayName());
   }

   public void addAdditionalChoices(List<WizardPanel> choiceWizardPanels) {
      // no additional choices.
   }
}

;
