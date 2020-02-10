package com.tibco.cep.studio.mapper.ui.data.param;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;

public class AnyCategory extends TypeCategory {

   public static final TypeCategory INSTANCE = new AnyCategory();

   private AnyCategory() {
   }

   public JComponent createEditor(ChangeListener changeListener, Object detailsValue,
		   						UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry, ImportRegistry importRegistry) {
      return null;
   }

   public Object createDetails() {
      return null;
   }

   public Object getEditorValue(JComponent editor) {
      return null;
   }

   public String getDisplayName() {
      return ParameterEditorResources.ANY_TYPE;
   }

   public Icon getDisplayIcon() {
      return DataIcons.getAnyIcon();
   }

   public String[] getXsdTypes() {
      return new String[]{"anyType"};
   }

   public Object readDetails(String typeName) {
      return null;
   }

   public String getXsdType(Object details) {
      return "anyType";
   }
}
