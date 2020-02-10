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

public class DecimalCategory extends TypeCategory {

   public static final TypeCategory INSTANCE = new DecimalCategory();

   private DecimalCategory() {
   }

   public JComponent createEditor(ChangeListener changeListener, Object detailsValue,
		   UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry, ImportRegistry importRegistry) {
      return new Editor(changeListener, detailsValue);
   }

   public Object createDetails() {
      return new Details(SIZES[1].displayName);
   }

   public Object getEditorValue(JComponent editor) {
      return ((Editor) editor).getDetails();
   }

   public String getDisplayName() {
      return DataIcons.XSD_DECIMAL;
   }

   public Icon getDisplayIcon() {
      return DataIcons.getRealIcon();
   }

   public String[] getXsdTypes() {
      String[] t = new String[SIZES.length];
      for (int i = 0; i < t.length; i++) {
         t[i] = SIZES[i].xsdName;
      }
      return t;
   }

   public Object readDetails(String typeName) {
      for (int i = 0; i < SIZES.length; i++) {
         if (typeName.equals(SIZES[i].xsdName)) {
            return new Details(SIZES[i].displayName);
         }
      }
      return new Details(SIZES[1].displayName);
   }

   static class Size {
      public final String xsdName;
      public final String displayName;

      public Size(String xsdName, String displayName) {
         this.xsdName = xsdName;
         this.displayName = displayName;
      }
   }

   private static final Size[] SIZES = new Size[]{
      new Size("float", DataIcons.XSD_FLOAT),
      new Size("double", DataIcons.XSD_DOUBLE),
      new Size("decimal", DataIcons.XSD_DECIMAL)
   };

   public String getXsdType(Object details) {
      Details d = (Details) details;
      for (int i = 0; i < SIZES.length; i++) {
         if (d.mType.equals(SIZES[i].displayName)) {
            return SIZES[i].xsdName;
         }
      }
      return "double";
   }

   static class Details {
      public Details(String n) {
         mType = n;
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
         Details d = (Details) detailsValue;

         for (int i = 0; i < SIZES.length; i++) {
            mType.addItem(SIZES[i].displayName);
         }
         for (int i = 0; i < SIZES.length; i++) {
            if (d.mType.equals(SIZES[i].displayName)) {
               mType.setSelectedIndex(i);
            }
         }

         mType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
               cl.stateChanged(new ChangeEvent(this));
            }
         });
         JPanel panel = new JPanel(new BorderLayout());
         panel.add(new JLabel(ParameterEditorResources.INTEGER_SIZE + ":  "), BorderLayout.WEST);
         JPanel sp2 = new JPanel(new BorderLayout());
         sp2.add(mType, BorderLayout.WEST);
         panel.add(sp2);
         add(panel, BorderLayout.NORTH);
      }

      public void setEnabled(boolean enabled) {
         super.setEnabled(enabled);
         mType.setEnabled(enabled);
      }

      public Details getDetails() {
         String t = (String) mType.getSelectedItem();
         return new Details(t);
      }
   }
}
