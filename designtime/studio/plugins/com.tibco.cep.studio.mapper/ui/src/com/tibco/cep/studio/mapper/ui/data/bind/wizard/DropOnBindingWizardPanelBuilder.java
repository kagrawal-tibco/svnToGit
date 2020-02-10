package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import javax.swing.JLabel;

import com.tibco.cep.mapper.xml.xdata.bind.AttributeBinding;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.ChooseBinding;
import com.tibco.cep.mapper.xml.xdata.bind.CopyBinding;
import com.tibco.cep.mapper.xml.xdata.bind.CopyOfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ElementBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ForEachBinding;
import com.tibco.cep.mapper.xml.xdata.bind.IfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.OtherwiseBinding;
import com.tibco.cep.mapper.xml.xdata.bind.SetVariableBinding;
import com.tibco.cep.mapper.xml.xdata.bind.SortBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TextBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ValueOfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.WhenBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.TypeCopyOfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualDataComponentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualForEachGroupBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualGroupingBinding;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.wizard.DefaultWizardPanel;
import com.tibco.cep.studio.mapper.ui.wizard.WizardPanel;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * This is a panel builder used by the binding wizard to determine what to do with a standard, drop on a binding.<br>
 * It is the 'main entry point' for the drop wizard, it only assumes that there will be no unfilled parents.
 */
public class DropOnBindingWizardPanelBuilder implements BindingDropWizardPanelBuilder {
   /**
    * Creates a panel builder for a standard node drop.
    */
   public DropOnBindingWizardPanelBuilder() {
   }

   public Runnable buildFinisher(final BindingWizardState state, final TemplateReport report, final String xpath) {
      if (report.getBinding() instanceof MarkerBinding) {
         return new BindingMarkerCommentSetFormulaFinisher(report, xpath, state);
      }
      else {
         return null;
      }
   }

   public WizardPanel build(BindingWizardState state, TemplateReport report, String xpath) {
      BindingWizardState.assertNonNulls(state, report, xpath);
      if (report.getBinding() instanceof MarkerBinding) {
         SmSequenceType typeOfNode = report.getComputedType();
         if (typeOfNode != null && !SmSequenceTypeSupport.hasTypedValue(typeOfNode, true)) {
            if (SmSequenceTypeSupport.isChoice(typeOfNode) || SmSequenceTypeSupport.isWildcardElement(typeOfNode) || SmSequenceTypeSupport.isSequence(typeOfNode)) {
               return new DropOnChoiceOrAnyPanel(state, report, xpath, this);
            }
            else {
               // This is a complex structure, ask if it should be a template, etc
               return new DropOnStructurePanel(state, report, xpath);
            }
         }
         if (typeOfNode != null && typeOfNode.quantifier().getMaxOccurs() > 1) {
            // see if a for-each makes sense:
            return new DropOnStructurePanel(state, report, xpath);
         }
//            if (typeOfNode!=null && )
         // otherwise this is trivial, build a finisher:
         return null;
      }
      else {
         // non-marker:
         Binding rb = report.getBinding();
         if (rb instanceof VirtualDataComponentBinding) {
            if (((VirtualDataComponentBinding) rb).getHasInlineFormula()) {
               return new DropOnExistingVirtualDataComponentPanel(state, report, xpath);
            }
            else {
               return new DropOnStructurePanel(state, report, xpath);
            }
         }
         else {
            if (rb instanceof ForEachBinding || rb instanceof VirtualForEachGroupBinding) {
               return new DropOnExistingForEachPanel(state, report, xpath);
            }
            if (rb instanceof IfBinding || rb instanceof WhenBinding) {
               return new DropOnExistingConditionPanel(state, report, xpath);
            }
            if (report.getBinding() instanceof OtherwiseBinding || report.getBinding() instanceof ChooseBinding) {
               // WCETODO ??? what is this logic?
               return new DropOnExistingConditionPanel(state, report, xpath);
            }
            if (rb instanceof SortBinding || rb instanceof VirtualGroupingBinding) {
               return new DropOnExistingSortPanel(state, report, xpath);
            }
            if (rb instanceof SetVariableBinding && rb.getFormula() != null) // variable select
            {
               return new DropOnExistingSelectVariablePanel(state, report, xpath);
            }
            if (rb instanceof ValueOfBinding) {
               return new DropOnExistingValueOfPanel(state, report, xpath);
            }
            if (rb instanceof CopyOfBinding || rb instanceof TypeCopyOfBinding || rb instanceof CopyBinding) {
               return new DropOnExistingCopyOfPanel(state, report, xpath);
            }
            if (rb instanceof TextBinding) {
               //WCETODO do something here...
            }
            if (rb instanceof ElementBinding || rb instanceof AttributeBinding) {
               // Can't happen.... should have been virtualized and/or handled earlier.
               DefaultWizardPanel dwp = new DefaultWizardPanel();
               dwp.setComponent(new JLabel("Error, drop shouldn't happen on: " + report.getBinding().getClass().getName()));
               return dwp;
            }
            // Ok, give a default one:
            return new DropOnExistingUnknownPanel(state, report, xpath);
         }
      }
   }
}

;
