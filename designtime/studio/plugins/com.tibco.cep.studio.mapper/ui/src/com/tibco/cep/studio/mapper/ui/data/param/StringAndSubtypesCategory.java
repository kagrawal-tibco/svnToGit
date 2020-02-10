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
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;

/**
 * The type category for String, all the subtypes, and QName.<br>
 */
public class StringAndSubtypesCategory extends TypeCategory {
   public static final TypeCategory INSTANCE = new StringAndSubtypesCategory();

   private StringAndSubtypesCategory() {
   }

   static class Size {
      public final String xsdName;
      public final String displayName;

      public Size(String xsdName, String displayName) {
         this.xsdName = xsdName;
         this.displayName = displayName;
      }
   }

   public static final Size[] SIZES = new Size[]{
      new Size("string", DataIcons.XSD_STRING),
      new Size("normalizedString", DataIcons.XSD_NORMALIZED_STRING),
      new Size("token", DataIcons.XSD_TOKEN),
      new Size("language", DataIcons.XSD_LANGUAGE),
      new Size("Name", DataIcons.XSD_NAME),
      new Size("NCName", DataIcons.XSD_NCNAME),
      new Size("QName", DataIcons.XSD_QNAME),
      new Size("NMToken", DataIcons.XSD_NMTOKEN),
      new Size("NMTokens", DataIcons.XSD_NMTOKENS),
      new Size("ID", DataIcons.XSD_ID),
      new Size("IDREF", DataIcons.XSD_IDREF),
      new Size("IDREFS", DataIcons.XSD_IDREFS),
      new Size("ENTITY", DataIcons.XSD_ENTITY),
      new Size("ENTITIES", DataIcons.XSD_ENTITIES)
   };

   public String[] getXsdTypes() {
      String[] t = new String[SIZES.length];
      for (int i = 0; i < t.length; i++) {
         t[i] = SIZES[i].xsdName;
      }
      return t;
   }

   public Object readDetails(String type) {
      for (int i = 0; i < SIZES.length; i++) {
         if (SIZES[i].xsdName.equals(type)) {
            return new StringDetails(SIZES[i].displayName);
         }
      }
      // default it:
      return new StringDetails(SIZES[0].displayName);
   }

   public String getXsdType(Object details) {
      StringDetails id = (StringDetails) details;
      for (int i = 0; i < SIZES.length; i++) {
         if (id != null && id.mType.equals(SIZES[i].displayName)) {
            return SIZES[i].xsdName;
         }
      }
      return SIZES[0].xsdName;
   }

   public Object getEditorValue(JComponent c) {
      Editor e = (Editor) c;
      return e.getValue();
   }

   public JComponent createEditor(ChangeListener changeListener, Object detailsValue,
		   UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry, ImportRegistry importRegistry) {
      return new Editor(changeListener, detailsValue);
   }

   public String getDisplayName() {
      return DataIcons.XSD_STRING;
   }

   public Icon getDisplayIcon() {
      return DataIcons.getStringIcon();
   }

   public Object createDetails() {
      return new StringDetails(SIZES[0].displayName);
   }

   static class StringDetails {
      public StringDetails(String type) {
         mType = type;
      }

      public boolean equals(Object val) {
         if (!(val instanceof StringDetails)) {
            return false;
         }
         StringDetails id = (StringDetails) val;
         return id.mType.equals(mType);
      }

      public final String mType;
   }

   static class Editor extends JPanel {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox mType;
      private ChangeListener mChangeListener;

      public Editor(ChangeListener cl, Object val) {
         super(new BorderLayout());
         StringDetails id = (StringDetails) val;
         String str = id.mType;
         mChangeListener = cl;
         mType = new JComboBox();
         mType.setMaximumRowCount(SIZES.length);
         mType.setLightWeightPopupEnabled(false);
         for (int i = 0; i < SIZES.length; i++) {
            mType.addItem(SIZES[i].displayName);
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
         typeLabel.add(new JLabel(DataIcons.getTypeLabel() + ": "), BorderLayout.WEST);
         JPanel moreSpace = new JPanel(new BorderLayout());
         moreSpace.add(mType, BorderLayout.WEST);
         typeLabel.add(moreSpace);

         add(typeLabel, BorderLayout.NORTH);
      }

      public void setEnabled(boolean enabled) {
         super.setEnabled(enabled);
         mType.setEnabled(enabled);
      }

      public StringDetails getValue() {
         return new StringDetails((String) mType.getSelectedItem());
      }

      private void changed() {
         mChangeListener.stateChanged(new ChangeEvent(this));
      }
   }
}
