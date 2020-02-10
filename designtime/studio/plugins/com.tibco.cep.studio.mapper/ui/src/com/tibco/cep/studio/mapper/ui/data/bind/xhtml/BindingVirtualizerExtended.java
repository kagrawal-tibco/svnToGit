package com.tibco.cep.studio.mapper.ui.data.bind.xhtml;

import com.tibco.cep.mapper.xml.xdata.bind.virt.BindingVirtualizer;

public class BindingVirtualizerExtended extends BindingVirtualizer {
   public static final BindingVirtualizerExtended INSTANCE = new BindingVirtualizerExtended();

   @SuppressWarnings("unchecked")
   protected BindingVirtualizerExtended() {
      m_secondPassMatchers.add(XhtmlDynamicTableMatcher.INSTANCE);
   }
}

