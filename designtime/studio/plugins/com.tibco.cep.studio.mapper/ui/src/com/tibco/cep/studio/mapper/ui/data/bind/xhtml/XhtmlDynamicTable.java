package com.tibco.cep.studio.mapper.ui.data.bind.xhtml;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;
import com.tibco.cep.mapper.xml.xdata.bind.ElementBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportArguments;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportFormulaCache;
import com.tibco.cep.mapper.xml.xdata.bind.virt.BindingVirtualizer;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualXsltElement;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;

/**
 * Currently not used, but provides an example of how 'virtual' bindings can be used to give a more user-friendly
 * representation of specific elements.
 */
public class XhtmlDynamicTable extends DefaultVirtualBinding {
   private static final ExpandedName NAME = ExpandedName.makeName("dynamic-table");

   public XhtmlDynamicTable() {
   }

   public XhtmlDynamicTable(Binding at, BindingVirtualizer bv, CancelChecker cancelChecker) {
      Binding caption = XhtmlDynamicTableMatcher.getChildElement(at, XhtmlDynamicTableMatcher.XHTML_CAPTION);
      if (caption != null) {
         XhtmlCaptionBinding cb = new XhtmlCaptionBinding();
         bv.virtualizeChildren(caption, cb, cancelChecker);
         addChild(cb);
      }
   }

   public Binding cloneShallow() {
      return new XhtmlDynamicTable();
   }

   public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationContext, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {
      return null; //WCETODO.
   }

   public ExpandedName getName() {
      return NAME;
   }

   public Binding normalize(Binding parent) {
      Binding tb = new ElementBinding(BindingElementInfo.EMPTY_INFO, XhtmlDynamicTableMatcher.XHTML_TABLE);
      for (int i = 0; i < getChildCount(); i++) {
         Binding b = getChild(i);
         Binding r;
         if (b instanceof VirtualXsltElement) {
            r = ((VirtualXsltElement) b).normalize(this);
         }
         else {
            r = b.cloneDeep();
         }
         tb.addChild(r);
      }
      return tb;
   }
}

