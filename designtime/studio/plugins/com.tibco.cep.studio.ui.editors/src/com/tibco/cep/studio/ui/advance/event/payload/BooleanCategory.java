package com.tibco.cep.studio.ui.advance.event.payload;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;

public class BooleanCategory extends TypeCategory {

   public static final TypeCategory INSTANCE = new BooleanCategory();

   private BooleanCategory() {
   }

   public JComponent createEditor(ChangeListener changeListener, Object detailsValue,
		   UIAgent uiAgent, NamespaceContextRegistry namespaceContextRegistry, ImportRegistry importRegistry) {
      return null;
   }

   public Object getEditorValue(JComponent c) {
      return null;
   }

   public String[] getXsdTypes() {
      return new String[]{"boolean"};
   }

   public Object readDetails(String type) {
      return null;
   }

   public String getXsdType(Object details) {
      return "boolean";
   }

   public Object createDetails() {
      return null;
   }

   public String getDisplayName() {
      return DataIcons.XSD_BOOLEAN;
   }

   public Icon getDisplayIcon() {
      return DataIcons.getBooleanIcon();
   }
}
