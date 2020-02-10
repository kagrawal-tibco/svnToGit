package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.xpath.Utilities;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.wizard.DefaultWizardPanel;

/**
 * The wizard (choice) panel for incorporating into an existing formula.
 */
public class IncorporateIntoFormulaChoicePanel extends DefaultWizardPanel {
   private BindingWizardState mState;
   private TemplateReport mOn;
   private String mXPath;

   public IncorporateIntoFormulaChoicePanel(final BindingWizardState state, final TemplateReport on, final String xpath) {
      mState = state;
      mOn = on;
      mXPath = xpath;

      setLabel(BindingWizardResources.INCORPORATE_INTO_EXISTING_FORMULA);
   }

   public void finish() {
      String xp = Utilities.makeRelativeXPath(mOn.getContext(), null, mXPath, null);
      String nxpath = mOn.getBinding().getFormula() + " << operator >> " + xp;
      mOn.getBinding().setFormula(nxpath);
      mState.wizardFinished(mOn.getBinding());
      //mState.getTree().edit(mOn.getBinding());
   }
}

;
