package com.tibco.cep.studio.mapper.ui.data.bind.xhtml;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.ElementBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportArguments;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportFormulaCache;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualXsltElement;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;

/**
 * Currently not used, but provides an example of how 'virtual' bindings can be used to give a more user-friendly
 * representation of specific elements.
 */
public class XhtmlCaptionBinding extends DefaultVirtualBinding {
   private static final ExpandedName NAME = ExpandedName.makeName("caption");

   public XhtmlCaptionBinding() {
   }

   public Binding cloneShallow() {
      return new XhtmlCaptionBinding();
   }

   public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationContext, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {
      return null; //WCETODO.
   }

   public void formatFragment(XmlContentHandler handler) throws SAXException {
      handler.startElement(NAME, null, null);

      handler.endElement(NAME, null, null);
   }

   public ExpandedName getName() {
      return NAME;
   }

   public Binding normalize(Binding parent) {
      Binding tb = new ElementBinding(BindingElementInfo.EMPTY_INFO, XhtmlDynamicTableMatcher.XHTML_CAPTION);
      for (int i = 0; i < getChildCount(); i++) {
         Binding b = getChild(i);
         if (b instanceof VirtualXsltElement) {
            ((VirtualXsltElement) b).normalize(tb);
         }
         else {
            tb.addChild(b.cloneDeep());
         }
      }
      return tb;
   }
}

