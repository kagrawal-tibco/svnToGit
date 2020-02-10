package com.tibco.cep.studio.mapper.ui.wizard.decision;

import com.tibco.cep.studio.mapper.ui.wizard.DefaultWizardPanel;

/**
 * A trivial WizardPanel implementation that has no content, just a label.
 * This is useful, for example, in DecisionWizardPanels where there is no configuration beyond the choice itself.
 */
public class LabelWizardPanel extends DefaultWizardPanel {
   public LabelWizardPanel(String labelText) {
      setLabel(labelText);
   }
}
