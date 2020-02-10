package com.tibco.cep.studio.mapper.ui.data.bind.xhtml;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.bind.AbstractBinding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualXsltElement;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;

public abstract class DefaultVirtualBinding extends AbstractBinding implements VirtualXsltElement {
   public DefaultVirtualBinding() {
      super(BindingElementInfo.EMPTY_INFO);
   }

   /**
    * A default implementation suitable for Virtual elements.
    *
    * @param handler
    * @throws SAXException
    */
   public void formatFragment(XmlContentHandler handler) throws SAXException {
      ExpandedName name = getName();
      handler.startElement(name, null, null);
      int cc = getChildCount();
      for (int i = 0; i < cc; i++) {
         getChild(i).formatFragment(handler);
      }
      handler.endElement(name, null, null);
   }
}
