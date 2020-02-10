package com.tibco.cep.studio.ui.advance.event.payload;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;

/**
 * A category containing only String, see {@link StringAndSubtypesCategory} for one containing all the subtypes.
 */
public class StringCategory extends TypeCategory {

   public static final TypeCategory INSTANCE = new StringCategory();
   private Icon mIcon = DataIcons.getStringIcon();

   private StringCategory() {
   }

   public String[] getXsdTypes() {
      return new String[]{"string"};
   }

   public Object readDetails(String type) {
      return null;
   }

   public String getXsdType(Object details) {
      return "string";
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
      return DataIcons.XSD_STRING;
   }

   public Icon getDisplayIcon() {
      return mIcon;
   }
}
