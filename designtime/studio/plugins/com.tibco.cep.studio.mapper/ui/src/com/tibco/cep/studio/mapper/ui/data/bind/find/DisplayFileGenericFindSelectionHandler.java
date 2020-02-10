package com.tibco.cep.studio.mapper.ui.data.bind.find;

import java.awt.Component;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;

/**
 * The generic floating dialog that shows documentation.
 */
class DisplayFileGenericFindSelectionHandler implements GenericFindSelectionHandler {
//	private UIAgent uiAgent;
//	private Component m_over;

   public DisplayFileGenericFindSelectionHandler(UIAgent uiAgent, Component over) {
//	   this.uiAgent = uiAgent;
//	   m_over = over;
   }

   public void show(String location, FindWindowPlugin plugin, Object findObject) {
//      DesignerDocument[] docs = m_designerApp.getAllOpenDocuments();
//      for (int i = 0; i < docs.length; i++) {
//         DesignerDocument doc = docs[i];
//         String baseURI = doc.getResourceStore().getVFileFactory().getURI();
//         if (location.startsWith(baseURI)) {
//            String ln = location.substring(baseURI.length());
//            // for now use the first....
//            AEResource res = doc.getRootFolder().findResource(ln);
//            if (res != null) {
//               Cursor old = m_over.getCursor();
//               m_over.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//               doc.selectResource(res);
//               m_over.setCursor(old);
//
//               if (res != null) {
//                  // attempt to show details:
//                  plugin.selectResult(res, findObject);
//               }
//            }
//         }
//      }
   }
}
