package com.tibco.cep.studio.ui.advance.event.payload;

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
import com.tibco.cep.studio.mapper.ui.data.param.ParameterEditorResources;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;

/**
 * The type category of integers.<br>
 */
public class IntegerCategory extends TypeCategory {

   public static final TypeCategory INSTANCE = new IntegerCategory();

   private IntegerCategory() {
   }

   static class Size {
      public final String xsdName;
      public final String displayName;
      public final String min;
      public final String max;

      public Size(String xsdName, String displayName, String min, String max) {
         this.xsdName = xsdName;
         this.displayName = displayName;
         this.min = min;
         this.max = max;
      }

   }

   private static final String NONE = "";

   public static final Size[] SIZES = new Size[]{
      new Size("byte", DataIcons.XSD_BYTE, "-128", "127"),
      new Size("short", DataIcons.XSD_SHORT, "-32768", "32767"),
      new Size("int", DataIcons.XSD_INT, "-2147483648", "2147483647"),
      new Size("long", DataIcons.XSD_LONG, "-9223372036854775808", "9223372036854775807"),
      new Size("unsignedByte", DataIcons.XSD_UNSIGNED_BYTE, "0", "255"),
      new Size("unsignedShort", DataIcons.XSD_UNSIGNED_SHORT, "0", "65535"),
      new Size("unsignedInt", DataIcons.XSD_UNSIGNED_INT, "0", "4294967295"),
      new Size("unsignedLong", DataIcons.XSD_UNSIGNED_LONG, "0", "18446744073709551615"),
      new Size("integer", DataIcons.XSD_INTEGER, NONE, NONE),
      new Size("positiveInteger", DataIcons.XSD_POSITIVE_INTEGER, "1", NONE),
      new Size("negativeInteger", DataIcons.XSD_NEGATIVE_INTEGER, NONE, "-1"),
      new Size("nonPositiveInteger", DataIcons.XSD_NONPOSITIVE_INTEGER, NONE, "0"),
      new Size("nonNegativeInteger", DataIcons.XSD_NONNEGATIVE_INTEGER, "0", NONE)
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
            return new IntDetails(SIZES[i].displayName);
         }
      }
      return new IntDetails(SIZES[0].displayName);
   }

   public String getXsdType(Object details) {
      IntDetails id = (IntDetails) details;
      for (int i = 0; i < SIZES.length; i++) {
         if (id.mType.equals(SIZES[i].displayName)) {
            return SIZES[i].xsdName;
         }
      }
      return "int";
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
      return DataIcons.XSD_INTEGER;
   }

   public Icon getDisplayIcon() {
      return DataIcons.getIntegerIcon();
   }

   public Object createDetails() {
      return new IntDetails(SIZES[2].displayName); // default to Int
   }

   static class IntDetails {
      public IntDetails(String type) {
         mType = type;
      }

      public boolean equals(Object val) {
         if (!(val instanceof IntDetails)) {
            return false;
         }
         IntDetails id = (IntDetails) val;
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
      //private JTextField mMin;
      //private JTextField mMax;
      private ChangeListener mChangeListener;

      public Editor(ChangeListener cl, Object val) {
         super(new BorderLayout());
         IntDetails id = (IntDetails) val;
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
         typeLabel.add(new JLabel(ParameterEditorResources.INTEGER_SIZE + ":  "), BorderLayout.WEST);
         JPanel moreSpace = new JPanel(new BorderLayout());
         moreSpace.add(mType, BorderLayout.WEST);
         typeLabel.add(moreSpace);

         add(typeLabel, BorderLayout.NORTH);
      }

      public void setEnabled(boolean enabled) {
         super.setEnabled(enabled);
         mType.setEnabled(enabled);
      }


      public IntDetails getValue() {
         return new IntDetails((String) mType.getSelectedItem());
      }

      private void changed() {
         mChangeListener.stateChanged(new ChangeEvent(this));
      }
   }
}
