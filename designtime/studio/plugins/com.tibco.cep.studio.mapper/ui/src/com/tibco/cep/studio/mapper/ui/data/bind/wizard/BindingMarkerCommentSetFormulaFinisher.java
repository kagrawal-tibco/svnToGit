package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.CopyOfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ForEachBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualBindingSupport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualDataComponentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualDataComponentCopyMode;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualElementBinding;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.Utilities;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditorResources;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * A binding wizard finisher for setting panels.
 */
public class BindingMarkerCommentSetFormulaFinisher implements Runnable {
   private TemplateReport m_report;
   private String m_xpath;
   private BindingWizardState m_state;

   public BindingMarkerCommentSetFormulaFinisher(TemplateReport report, String xpath, BindingWizardState state) {
      m_report = report;
      if (!(report.getBinding() instanceof MarkerBinding)) {
         throw new IllegalArgumentException("Expected marker");
      }
      m_xpath = xpath;
      m_state = state;
   }

   public void run() {
      Binding b = BindingManipulationUtils.createAppropriateAttributeOrElementBinding((MarkerBinding) m_report.getBinding());
      if (b != null) {
         String relativedFormula = Utilities.makeRelativeXPath(m_report.getContext(), null, m_xpath, null);
         if (m_report.getExpectedType() == null || SmSequenceTypeSupport.hasTypedValue(m_report.getComputedType(), true)) {
            String markeredFormula = getMarkersFormulaIfAppropriate(b, m_report.getContext(), relativedFormula);
            VirtualBindingSupport.setXPathOn(b, markeredFormula);
            BindingManipulationUtils.replaceInParent(m_report.getBinding(), b);
            SmSequenceType droppedType = Parser.parse(relativedFormula).evalType(m_report.getContext()).xtype;
            if (b instanceof VirtualDataComponentBinding) {
               setAppropriateCopyMode(m_report.getExpectedType(), m_report.getComputedType(), (VirtualDataComponentBinding) b, droppedType);
            }
         }
         else {
            Binding fe = new ForEachBinding(BindingElementInfo.EMPTY_INFO, relativedFormula);
            fe.addChild(b);
            BindingManipulationUtils.replaceInParent(m_report.getBinding(), fe);
         }
      }
      else {
         String relativedFormula = Utilities.makeRelativeXPath(m_report.getContext(), null, m_xpath, null);
         // b==null, for copying to wildcards, etc.
         b = new CopyOfBinding(BindingElementInfo.EMPTY_INFO, relativedFormula);
         BindingManipulationUtils.replaceInParent(m_report.getBinding(), b);
      }
      m_state.wizardFinished(b);
   }

   /**
    * From the dropped and to-type, set are both nillable (and known nillable), and the binding is an element (should be),
    * sets copy-nil, if they are either not nillable, clears copy-nil, otherwise leaves copy-nil alone.
    */
   public static void setAppropriateCopyMode(SmSequenceType toExpectedType, SmSequenceType toComputedType, VirtualDataComponentBinding newBinding, SmSequenceType droppedType) {
      newBinding.setCopyMode(VirtualDataComponentCopyMode.REQUIRED_TO_REQUIRED);
      if (toExpectedType == null || droppedType == null || toComputedType == null) {
         return;
      }
      boolean implicitIf = droppedType.quantifier().getMinOccurs() == 0 && toExpectedType.quantifier().getMinOccurs() == 0;
      if (implicitIf) {
         newBinding.setCopyMode(VirtualDataComponentCopyMode.OPTIONAL_TO_OPTIONAL);
      }
      boolean nilCopy = toComputedType.isNillabilityKnown() && toComputedType.isNillable() && droppedType.isNillabilityKnown() && droppedType.isNillable();
      if (nilCopy) {
         newBinding.setCopyMode(newBinding.getCopyMode().getWithCopyNil());
      }
      else {
         if (toComputedType.isNillabilityKnown() && toComputedType.isNillable() && droppedType.quantifier().getMinOccurs() == 0) {
            newBinding.setCopyMode(VirtualDataComponentCopyMode.OPTIONAL_TO_NIL);
         }
         else {
            if (droppedType.isNillabilityKnown() && droppedType.isNillable() && toComputedType.quantifier().getMinOccurs() == 0) {
               newBinding.setCopyMode(VirtualDataComponentCopyMode.NIL_TO_OPTIONAL);
            }
         }
      }
   }

   /**
    * If the dropped type has a greater repetition than the expected, returns the new xpath with filter 'markers',
    * otherwise returns an unchanged xpath.
    */
   public static String getMarkersFormulaIfAppropriate(Binding newBinding, ExprContext context, String xpath) {
      if (!(newBinding instanceof VirtualElementBinding)) {
         return xpath;
      }
      return Utilities.insertFilters(context, xpath, BindingEditorResources.FILTER_MARKER);
   }
}

;
