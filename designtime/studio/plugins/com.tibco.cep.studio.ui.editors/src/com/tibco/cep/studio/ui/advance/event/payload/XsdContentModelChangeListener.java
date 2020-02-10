package com.tibco.cep.studio.ui.advance.event.payload;

import com.tibco.cep.mapper.xml.xdata.bind.fix.XsdContentModelChangeEvent;

/**
 * A change event that notifies how an Xsd content model has changed.
 */
public interface XsdContentModelChangeListener {
   void contentModelChange(XsdContentModelChangeEvent changeEvent);
}
