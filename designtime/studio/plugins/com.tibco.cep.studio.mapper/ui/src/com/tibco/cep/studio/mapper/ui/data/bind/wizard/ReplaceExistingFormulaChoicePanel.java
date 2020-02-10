package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import com.tibco.cep.mapper.xml.xdata.bind.AttributeBinding;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualDataComponentBinding;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.Utilities;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.wizard.DefaultWizardPanel;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * The wizard (choice) panel for incorporating into an existing formula.
 */
public class ReplaceExistingFormulaChoicePanel extends DefaultWizardPanel {
   private BindingWizardState mState;
   private TemplateReport mOn;
   private String mXPath;

   public ReplaceExistingFormulaChoicePanel(final BindingWizardState state, final TemplateReport on, final String xpath) {
      BindingWizardState.assertNonNulls(state, on, xpath);
      mState = state;
      mOn = on;
      mXPath = xpath;

      String f = on.getBinding().getFormula();
      if (f == null) {
         f = "";
      }
      if (f.length() == 0) {
         setLabel(BindingWizardResources.SET_FORMULA);
      }
      else {
         setLabel(BindingWizardResources.REPLACE_EXISTING_FORMULA);
      }
   }

   public void finish() {
      String relpath = Utilities.makeRelativeXPath(mOn.getContext(), null, mXPath, null);

      Binding b = mOn.getBinding();
      if (b instanceof VirtualDataComponentBinding) {
         VirtualDataComponentBinding vdc = (VirtualDataComponentBinding) b;
         if (vdc instanceof AttributeBinding && !((AttributeBinding) vdc).isExplicitXslRepresentation()) {
            // Make into an AVT:
            relpath = "{" + relpath + "}";
         }
         else {
            vdc.setHasInlineFormula(true);
            vdc.setInlineIsText(false);
            SmSequenceType droppedType = Parser.parse(relpath).evalType(mOn.getContext()).xtype;
            if (droppedType == null) {
               // just in case.
               droppedType = SMDT.PREVIOUS_ERROR;
            }
            SmSequenceType exType = mOn.getExpectedType();
            if (exType == null) {
               exType = SMDT.PREVIOUS_ERROR;
            }
            BindingMarkerCommentSetFormulaFinisher.setAppropriateCopyMode(exType, mOn.getComputedType(), vdc, droppedType);
         }
      }
      String nxpath = BindingMarkerCommentSetFormulaFinisher.getMarkersFormulaIfAppropriate(mOn.getBinding(), mOn.getContext(), relpath);

      mOn.getBinding().setFormula(nxpath);
      mState.wizardFinished(mOn.getBinding());
   }
}

;
