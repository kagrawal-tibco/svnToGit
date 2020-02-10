package com.tibco.cep.studio.mapper.ui.xmlui;

import java.util.regex.Pattern;

import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.tns.cache.TnsDocumentProvider;

/**
 * A find window for Xsd types.
 */
public class XsdFindWindowPlugin extends TnsBasedFindWindowPlugin {
   public XsdFindWindowPlugin(int smComponentType, TnsDocumentProvider scp) {
      super(SmNamespace.class, smComponentType, scp);
      addNameSearchType(); // for free.
      if (smComponentType == SmComponent.ELEMENT_TYPE) {
         addSearchType(new SearchType() {
            public String getLabel() {
               return QNamePanelResources.SUBSTITUTES_FOR;
            }

            public boolean matches(Pattern regularExpression, Object component) {
               SmElement sc = (SmElement) component;
               for (sc = sc.getSubstitutionGroup(); sc != null; sc = sc.getSubstitutionGroup()) {
                  if (regularExpression.matcher(sc.getName()).matches()) {
                     return true;
                  }
               }
               return false;
            }
         });
      }
   }

   public void selectResult(String displayResource, Object findData) {
      // do nothing yet.
   }
}
