package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import java.util.ArrayList;

import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.Utilities;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.wizard.WizardPanel;
import com.tibco.cep.studio.mapper.ui.wizard.decision.DecisionWizardPanel;
import com.tibco.xml.schema.SmSequenceType;

/**
 * The wizard panel for when a drop happens on top of a choice or any type (or, now anonymous sequences).<br>
 */
public class DropOnChoiceOrAnyPanel extends DecisionWizardPanel {
   private BindingWizardState mState;
   private TemplateReport mOn;
   private String mXPath;

   public DropOnChoiceOrAnyPanel(final BindingWizardState state, final TemplateReport on, final String xpath, BindingDropWizardPanelBuilder next) {
      mState = state;
      mOn = on;
      mXPath = xpath;
      //setLabel("For structure");
      //String name = DataIcons.getName(on.getComputedType());
      //setHeaderComponent(new JLabel("<html>'" + name + "' already has a formula:"));
      final String relpath = Utilities.makeRelativeXPath(mOn.getContext(), null, mXPath, null);

      ArrayList<WizardPanel> choicePanels = new ArrayList<WizardPanel>();

      WizardPanel addForEach = SurroundWithForEachWizard.createIfRequired(state, on, xpath);
      if (addForEach != null) {
         choicePanels.add(addForEach);
      }

      SmSequenceType droppedType = Parser.parse(relpath).evalType(on.getContext()).xtype;
      WizardPanel copyOf = CopyOfChoiceWizardPanel.createPanelIfApplicable(state, droppedType, on, relpath);
      if (copyOf != null) {
         choicePanels.add(copyOf);
      }

      WizardPanel choicesP = InstantiateChoiceChoicePanel.createIfApplicable(mState, mOn, mXPath, next);
      if (choicesP != null) {
         choicePanels.add(choicesP);
      }

      WizardPanel[] wp = choicePanels.toArray(new WizardPanel[0]);
      setChoices(wp);
   }

   public void enter() {
      super.enter();
      mState.showStep(mOn.getBinding());
   }
}

;
