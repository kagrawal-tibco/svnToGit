package com.tibco.cep.studio.mapper.ui.data.param;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.nsutils.ImportRegistryEntry;
import com.tibco.cep.mapper.xml.nsutils.ImportRegistrySupport;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;

public class SpecialTypeCategory extends TypeCategory {
   public static final TypeCategory INSTANCE = new SpecialTypeCategory();

   private SpecialTypeCategory() {
   }

   public static final TypeDetails[] SPECIAL_TYPES = new TypeDetails[]{
      new TypeDetails("Attachment", getDisplayName("attachment"), ExpandedName.makeName("http://ws-i.org/profiles/basic/1.1/xsd", "swaRef")),
   };

   private static String getDisplayName(String type) {
      return DataIcons.getString("ae.specialType." + type + ".label");
   }

   public static ExpandedName getExpandedNameForType(String type) {
      ExpandedName en = null;
      boolean found = false;
      for (int i = 0; i < SPECIAL_TYPES.length && !found; i++) {
         if (SPECIAL_TYPES[i].displayName.equals(type)) {
            en = SPECIAL_TYPES[i].qName;
            found = true;
         }
      }

      return en;
   }

   public static TypeDetails getTypeByName(String typeName) {
      TypeDetails type = null;
      boolean found = false;
      for (int i = 0; i < SPECIAL_TYPES.length && !found; i++) {
         if (SPECIAL_TYPES[i].mType.equals(typeName)) {
            type = SPECIAL_TYPES[i];
            found = true;
         }
      }

      return type;
   }

   public String[] getXsdTypes() {
      String[] types = new String[SPECIAL_TYPES.length];
      for (int i = 0; i < SPECIAL_TYPES.length; i++) {
         types[i] = SPECIAL_TYPES[i].qName.getLocalName();
      }
      return types;
   }

   public Object readDetails(String type) {
      for (int i = 0; i < SPECIAL_TYPES.length; i++) {
         if (SPECIAL_TYPES[i].mType.equals(type)) {
            return SPECIAL_TYPES[i];
         }
      }
      return SPECIAL_TYPES[0];
   }

   public Object readRefDetails(ExpandedName name) {
      for (int i = 0; i < SPECIAL_TYPES.length; i++) {
         if (SPECIAL_TYPES[i].qName.equals(name)) {
            return SPECIAL_TYPES[i];
         }
      }
      return SPECIAL_TYPES[0];
   }

   public ExpandedName getTypeExpandedName(Object details) {
      ExpandedName retTypeQn = null;
      if (details != null) {
         retTypeQn = ((TypeDetails) details).qName;
      }
      return retTypeQn;
   }

   public Object getEditorValue(JComponent c) {
      Editor e = (Editor) c;
      return e.getValue();
   }

   public JComponent createEditor(ChangeListener changeListener, Object detailsValue,
		   UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry, ImportRegistry importRegistry) {
      Editor editor = new Editor(changeListener, detailsValue, namespaceContextRegistry, importRegistry);
      TypeDetails qn = editor.getValue();
      editor.setExpandedName(qn.qName);
      return editor;
   }

   public String getDisplayName() {
      return ParameterEditorResources.SPECIAL_TYPE;
   }

   public Icon getDisplayIcon() {
      return DataIcons.getTypeReferenceIcon();
   }

   public Object createDetails() {
      return SPECIAL_TYPES[0]; // default to Attachment
   }

   static class TypeDetails {
      public TypeDetails(String type, String displayName, ExpandedName qName) {
         this.mType = type;
         this.displayName = displayName;
         this.qName = qName;
      }

      public boolean equals(Object val) {
         if (!(val instanceof TypeDetails)) {
            return false;
         }
         TypeDetails id = (TypeDetails) val;
         return id.mType.equals(mType) && id.qName.equals(qName);
      }

      public String mType;
      public String displayName;
      public ExpandedName qName;
   }

   static class Editor extends JPanel {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox mType;
      //private JTextField mMin;
      //private JTextField mMax;
      private ChangeListener mChangeListener;
      private NamespaceContextRegistry namespaceContextRegistry;
      private ImportRegistry importRegistry;

      public Editor(ChangeListener cl, Object val, NamespaceContextRegistry namespaceContextRegistry, ImportRegistry importRegistry) {
         super(new BorderLayout());
         TypeDetails id = (TypeDetails) val;
         String str = id.mType;
         mChangeListener = cl;
         this.namespaceContextRegistry = namespaceContextRegistry;
         this.importRegistry = importRegistry;
         mType = new JComboBox();
         mType.setMaximumRowCount(SPECIAL_TYPES.length);
         mType.setLightWeightPopupEnabled(false);
         for (int i = 0; i < SPECIAL_TYPES.length; i++) {
            mType.addItem(SPECIAL_TYPES[i].displayName);
         }
         for (int i = 0; i < mType.getItemCount(); i++) {
            if (mType.getItemAt(i).equals(str)) {
               mType.setSelectedIndex(i);
               break;
            }
         }

         mType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
               changed();
            }
         });

         JPanel typeLabel = new JPanel(new BorderLayout());
         typeLabel.add(new JLabel(ParameterEditorResources.SPECIAL_TYPE_SUBTYPE + ":  "), BorderLayout.WEST);
         JPanel moreSpace = new JPanel(new BorderLayout());
         moreSpace.add(mType, BorderLayout.WEST);
         typeLabel.add(moreSpace);

         add(typeLabel, BorderLayout.NORTH);
      }

      public void setEnabled(boolean enabled) {
         super.setEnabled(enabled);
         mType.setEnabled(enabled);
      }


      public TypeDetails getValue() {
         String typeName = (String) mType.getSelectedItem();
         return getTypeByName(typeName);
      }

      private void changed() {
         TypeDetails selectedItem = getValue();
         setExpandedName(selectedItem.qName);

         mChangeListener.stateChanged(new ChangeEvent(this));
      }

      public void setExpandedName(ExpandedName ename) {
         String namespace = ename.getNamespaceURI();
         addImport(namespace);
         namespaceContextRegistry.getOrAddPrefixForNamespaceURI(namespace);
      }

      private void addImport(String namespace) {
         ImportRegistryEntry importRegistryEntry = new ImportRegistryEntry(namespace, null);
         boolean containsImport = ImportRegistrySupport.containsImport(importRegistry, importRegistryEntry);
         if (!containsImport) {
            ImportRegistrySupport.addImport(importRegistry, importRegistryEntry);
         }
      }
   }
}
