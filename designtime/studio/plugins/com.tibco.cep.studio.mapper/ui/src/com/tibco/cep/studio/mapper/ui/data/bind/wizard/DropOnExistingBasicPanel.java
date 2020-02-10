package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import com.tibco.cep.mapper.xml.xdata.bind.CallTemplateBinding;
import com.tibco.cep.mapper.xml.xdata.bind.OtherwiseBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.data.bind.StatementPanel;
import com.tibco.cep.studio.mapper.ui.wizard.WizardPanel;
import com.tibco.cep.studio.mapper.ui.wizard.decision.DecisionWizardPanel;
import com.tibco.sdk.MMessageBundle;

/**
 * The wizard panel for when a drop happens on top of a 'for-each'.
 */
public abstract class DropOnExistingBasicPanel extends DecisionWizardPanel {
   private final BindingWizardState mState;
   private final TemplateReport mOn;
   private final String mXPath;

   public DropOnExistingBasicPanel(BindingWizardState state, TemplateReport on, String xpath, String formulaLabel) {
      BindingWizardState.assertNonNulls(state, on, xpath);
      mState = state;
      mOn = on;
      mXPath = xpath;
      if (hasExistingFormula()) {
         setLabel(BindingWizardResources.FORMULA_ALREADY_EXISTS_TITLE);

         String msg = DataIcons.printf(BindingWizardResources.FORMULA_ALREADY_EXISTS, formulaLabel) + ":";
         setHeaderComponent(new JLabel("<html>" + msg));
      }
      else {
         setLabel(BindingWizardResources.SET_FORMULA);
      }
      ArrayList<WizardPanel> panelChoices = new ArrayList<WizardPanel>();
      boolean canHaveFormula = true;
      // hacky, fix:
      if (on.getBinding() instanceof OtherwiseBinding || on.getBinding() instanceof CallTemplateBinding) {
         canHaveFormula = false;
      }
      if (canHaveFormula) {
         WizardPanel replace = new ReplaceExistingFormulaChoicePanel(state, on, xpath);
         panelChoices.add(replace);
      }

      if (canHaveFormula && hasExistingFormula()) {
         WizardPanel joinInFormula = new IncorporateIntoFormulaChoicePanel(state, on, xpath);
         panelChoices.add(joinInFormula);
      }
      addAdditionalChoices(panelChoices);
      WizardPanel[] wp = panelChoices.toArray(new WizardPanel[0]);
      if (wp.length == 0) {
         // No choices, display message.
         setLabel(BindingWizardResources.SET_FORMULA);
         StatementPanel sp = state.getTree().getStatementPanelManager().getStatementPanelFor(on.getBinding());
         String msg = MMessageBundle.getMessage(BindingWizardResources.NO_ACTION_AVAILABLE, sp.getDisplayName());
         setHeaderComponent(new JLabel("<html>" + msg));
      }
      setChoices(wp);
   }

   /**
    * Helper method which indicates if the field currently has an existing formula.
    */
   public final boolean hasExistingFormula() {
      String f = mOn.getBinding().getFormula();
      return f != null && f.length() > 0;
   }

   public final String getXPath() {
      return mXPath;
   }

   public final TemplateReport getOn() {
      return mOn;
   }

   public final BindingWizardState getState() {
      return mState;
   }

   public abstract void addAdditionalChoices(List<WizardPanel> choiceWizardPanels);

   public final void enter() {
      super.enter();
      mState.showStep(mOn.getBinding());
   }
}

;
