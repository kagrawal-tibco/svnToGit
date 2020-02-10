package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import java.util.List;

import com.tibco.cep.mapper.xml.xdata.bind.CopyOfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.data.bind.CopyOfPanel;
import com.tibco.cep.studio.mapper.ui.wizard.WizardPanel;

/**
 * The wizard panel for when a drop happens on top of a {@link com.tibco.xml.xdata.bind.CopyBinding} or {@link CopyOfBinding} or {@link com.tibco.xml.xdata.bind.virt.TypeCopyOfBinding}.
 */
public class DropOnExistingCopyOfPanel extends DropOnExistingBasicPanel {
   public DropOnExistingCopyOfPanel(BindingWizardState state, TemplateReport on, String xpath) {
      super(state, on, xpath, CopyOfPanel.LABEL);
   }

public void addAdditionalChoices(List<WizardPanel> choiceWizardPanels) {
      // no additional options.
   }
}

;
