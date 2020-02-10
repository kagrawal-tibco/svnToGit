package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import java.util.List;

import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.wizard.WizardPanel;

/**
 * The wizard panel for when a drop happens on top of a virtual data component (also see {@link DropOnStructurePanel}
 * which needs to be consolidated w/ this)
 */
public class DropOnExistingVirtualDataComponentPanel extends DropOnExistingBasicPanel {
   public DropOnExistingVirtualDataComponentPanel(final BindingWizardState state, final TemplateReport on, String xpath) {
      super(state, on, xpath, "'" + DataIcons.getName(on.getComputedType()) + "'");
   }

   public void addAdditionalChoices(List<WizardPanel> choiceWizardPanels) {
      WizardPanel addForEach = SurroundWithForEachWizard.createIfRequired(getState(), getOn(), getXPath());
      if (addForEach != null) {
         choiceWizardPanels.add(addForEach);
      }
   }
}

;
