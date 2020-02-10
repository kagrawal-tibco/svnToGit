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

public class BinaryCategory extends TypeCategory {

   public static final TypeCategory INSTANCE = new BinaryCategory();

   private BinaryCategory() {
   }

   public String[] getXsdTypes() {
      String[] t = new String[ENCODINGS.length];
      for (int i = 0; i < t.length; i++) {
         t[i] = ENCODINGS[i].xsdName;
      }
      return t;
   }

   public Object readDetails(String typeName) {
      for (int i = 0; i < ENCODINGS.length; i++) {
         if (typeName.equals(ENCODINGS[i].xsdName)) {
            return new BinaryDetails(ENCODINGS[i].displayName);
         }
      }
      return new BinaryDetails(ENCODINGS[0].displayName);
   }

   public String getXsdType(Object details) {
      BinaryDetails d = (BinaryDetails) details;
      if (d == null) {
         throw new NullPointerException();
      }
      for (int i = 0; i < ENCODINGS.length; i++) {
         if (d.mType.equals(ENCODINGS[i].displayName)) {
            return ENCODINGS[i].xsdName;
         }
      }
      return ENCODINGS[0].xsdName;
   }

   public JComponent createEditor(ChangeListener changeListener, Object detailsValue,
		   								UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry, ImportRegistry importRegistry) {
      return new Editor(changeListener, detailsValue);
   }

   public Object createDetails() {
      return new BinaryDetails(ENCODINGS[0].displayName);
   }

   public Object getEditorValue(JComponent c) {
      Editor e = (Editor) c;
      return e.getDetails();
   }

   public String getDisplayName() {
      return DataIcons.XSD_GENERIC_BINARY;
   }

   public Icon getDisplayIcon() {
      return DataIcons.getBinaryIcon();
   }

   static class Encoding {
      public final String xsdName;
      public final String displayName;

      public Encoding(String xsdName, String displayName) {
         this.xsdName = xsdName;
         this.displayName = displayName;
      }
   }

   private static final Encoding[] ENCODINGS = new Encoding[]
   {
      new Encoding("base64Binary", DataIcons.XSD_BASE64BINARY),
      new Encoding("hexBinary", DataIcons.XSD_HEXBINARY)
   };

   static class BinaryDetails {
      public BinaryDetails(String type) {
         mType = type;
         if (type == null) {
            throw new NullPointerException();
         }
      }

      public boolean equals(Object val) {
         if (!(val instanceof BinaryDetails)) {
            return false;
         }
         BinaryDetails id = (BinaryDetails) val;
         return id.mType.equals(mType);
      }

      public final String mType;
   }

   static class Editor extends JPanel {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox mType = new JComboBox();

      public Editor(final ChangeListener cl, Object detailsValue) {
         super(new BorderLayout());
         BinaryDetails d = (BinaryDetails) detailsValue;

         for (int i = 0; i < ENCODINGS.length; i++) {
            mType.addItem(ENCODINGS[i].displayName);
         }
         for (int i = 0; i < ENCODINGS.length; i++) {
            if (d.mType.equals(ENCODINGS[i].displayName)) {
               mType.setSelectedIndex(i);
            }
         }

         mType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
               cl.stateChanged(new ChangeEvent(this));
            }
         });
         JPanel panel = new JPanel(new BorderLayout());
         panel.add(new JLabel(ParameterEditorResources.BINARY_ENCODING + ":  "), BorderLayout.WEST);
         JPanel sp2 = new JPanel(new BorderLayout());
         sp2.add(mType, BorderLayout.WEST);
         panel.add(sp2);
         add(panel, BorderLayout.NORTH);
      }

      public void setEnabled(boolean enabled) {
         super.setEnabled(enabled);
         mType.setEnabled(enabled);
      }

      public BinaryDetails getDetails() {
         String t = (String) mType.getSelectedItem();
         if (t == null) {
            t = ENCODINGS[0].displayName;
         }
         return new BinaryDetails(t);
      }
   }
}
