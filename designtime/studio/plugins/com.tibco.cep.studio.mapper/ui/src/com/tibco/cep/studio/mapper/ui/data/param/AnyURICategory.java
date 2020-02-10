package com.tibco.cep.studio.mapper.ui.data.param;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;

/**
 * The category for AnyURI.
 */
public class AnyURICategory extends TypeCategory {

   public static final TypeCategory INSTANCE = new AnyURICategory();
   private Icon mIcon = DataIcons.getStringIcon(); // No icon for now.

   private AnyURICategory() {
   }

   public String[] getXsdTypes() {
      return new String[]{"anyURI"};
   }

   public Object readDetails(String type) {
      return null;
   }

   public String getXsdType(Object details) {
      return "anyURI";
   }

   public JComponent createEditor(ChangeListener changeListener, Object detailsValue,
		   							UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry, ImportRegistry importRegistry) {
      return null;
   }

   public Object createDetails() {
      return null;
   }

   public Object getEditorValue(JComponent c) {
      return null;
   }

   public String getDisplayName() {
      return DataIcons.XSD_ANY_URI;
   }

   public Icon getDisplayIcon() {
      return mIcon;
   }
}
