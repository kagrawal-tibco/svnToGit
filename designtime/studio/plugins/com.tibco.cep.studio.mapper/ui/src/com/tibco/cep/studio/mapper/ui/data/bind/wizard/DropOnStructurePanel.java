package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import java.util.ArrayList;

import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.Utilities;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.wizard.DefaultWizardPanel;
import com.tibco.cep.studio.mapper.ui.wizard.WizardPanel;
import com.tibco.cep.studio.mapper.ui.wizard.decision.DecisionWizardPanel;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * The wizard panel for when a drop happens on top of a complex structure or repeating primitive.<br>
 * Checks for for-each, leave element alone, copy-of, make choice, split off template, call existing template.
 */
public class DropOnStructurePanel extends DecisionWizardPanel {
   private BindingWizardState mState;
   private TemplateReport mOn;
   private String mXPath;

   public DropOnStructurePanel(final BindingWizardState state, final TemplateReport on, final String xpath) {
      mState = state;
      mOn = on;
      mXPath = xpath;

      ArrayList<WizardPanel> choices = new ArrayList<WizardPanel>();
      //setLabel("For structure");
      //String name = DataIcons.getName(on.getComputedType());
      //setHeaderComponent(new JLabel("<html>'" + name + "' already has a formula:"));
      final String relpath = Utilities.makeRelativeXPath(mOn.getContext(), null, mXPath, null);

      boolean isComplex = !SmSequenceTypeSupport.isLeafDataComponent(on.getComputedType(), true);
      boolean hasTypedValue = SmSequenceTypeSupport.hasTypedValue(on.getComputedType(), true);
      boolean isRepeating = on.getComputedType().quantifier().getMaxOccurs() > 1;
      SmSequenceType droppedType = Parser.parse(relpath).evalType(on.getContext()).xtype;
      if (isComplex) {
         WizardPanel copyOf = CopyOfChoiceWizardPanel.createPanelIfApplicable(state, droppedType, on, relpath);
         if (copyOf != null) {
            choices.add(copyOf);
         }
      }
      if (hasTypedValue) {
         if (mOn.getBinding() instanceof MarkerBinding) {
            boolean rhsIsRepeatingLeaf = !isComplex && isRepeating;
            boolean lhsIsRepeatingLeaf = SmSequenceTypeSupport.isLeafDataComponent(droppedType, true) && droppedType.quantifier().getMaxOccurs() > 1;
            boolean rleafToRleaf = lhsIsRepeatingLeaf && rhsIsRepeatingLeaf;
            if (rleafToRleaf) {
               // Both left & right hand sides are repeating leaves:
               choices.add(PrimitiveForEachWizardPanel.create(mState, mOn, mXPath));
            }
            else {
               DefaultWizardPanel wp = new DefaultWizardPanel();
               wp.setLabel(BindingWizardResources.SET_FORMULA);
               wp.setFinisher(new BindingMarkerCommentSetFormulaFinisher(mOn, mXPath, mState));
               choices.add(wp);
            }
         }
         else {
            WizardPanel replace = new ReplaceExistingFormulaChoicePanel(state, on, xpath);
            choices.add(replace);
         }
      }

      WizardPanel addForEach = new SurroundWithForEachWizard(state, on, xpath);
      choices.add(addForEach);

      setChoices(choices.toArray(new WizardPanel[0]));
   }

   public void enter() {
      super.enter();
      mState.showStep(mOn.getBinding());
   }
}

;
