package com.tibco.cep.studio.mapper.ui.data.bind;

import com.tibco.cep.mapper.xml.xdata.bind.BindingTypeCheckUtilities;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualDataComponentBinding;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.XPathCheckArguments;
import com.tibco.cep.studio.mapper.ui.data.xpath.XTypeChecker;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * The editor panel that appears at the bottom of the binding tree.
 * It allows inline formula editing.
 */
class BindingXPathTypeChecker implements XTypeChecker {
   private TemplateReport m_templateReport;
   private int mType;

   public BindingXPathTypeChecker(TemplateReport forBinding) {
      m_templateReport = forBinding;
      mType = BindingTypeCheckUtilities.getTypeCheckType(forBinding.getBinding());
   }

   public ErrorMessageList check(SmSequenceType gotType, TextRange errorMessageTextRange) {
      SmSequenceType et = m_templateReport.getExpectedType();
      if (et == null) {
         et = SMDT.PREVIOUS_ERROR;
      }
      if (m_templateReport.getBinding() instanceof VirtualDataComponentBinding) {
         // For virtual dc bindings, we're type checking the typed value of it, not the element/attribute itself.
         et = et.typedValue(true); // true, want validated typed value, not unvalidated.
      }
      XPathCheckArguments args = new XPathCheckArguments();//WCETODO have passed in.
      return BindingTypeCheckUtilities.typeCheck(mType, et, gotType, errorMessageTextRange, args);
   }

   public SmSequenceType getBasicType() {
      // For value types, want the simple type (and more importantly, the cardinality)
      if (mType == BindingTypeCheckUtilities.TYPE_CHECK_VALUE) {
         return m_templateReport.getExpectedType();
      }
      // otherwise, no checking.
      return null;
   }
}
