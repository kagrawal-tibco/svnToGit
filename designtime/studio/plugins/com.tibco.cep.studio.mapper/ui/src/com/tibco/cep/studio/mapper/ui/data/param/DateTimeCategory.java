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

public class DateTimeCategory extends TypeCategory {

   public static final DateTimeCategory INSTANCE = new DateTimeCategory();

   private DateTimeCategory() {
   }

   static class Size {
      public final String xsdName;
      public final String displayName;

      public Size(String xsdName, String displayName) {
         this.xsdName = xsdName;
         this.displayName = displayName;
      }
   }

   public static final Size[] SIZES = new Size[]
   {
      new Size("time", DataIcons.XSD_TIME),
      new Size("date", DataIcons.XSD_DATE),
      new Size("dateTime", DataIcons.XSD_DATE_TIME),
      new Size("duration", DataIcons.XSD_DURATION),
      new Size("gDay", DataIcons.XSD_DAY),
      new Size("gMonth", DataIcons.XSD_MONTH),
      new Size("gYear", DataIcons.XSD_YEAR),
      new Size("gYearMonth", DataIcons.XSD_YEAR_MONTH),
      new Size("gMonthDay", DataIcons.XSD_MONTH_DAY)
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
            return new Details(SIZES[i].displayName);
         }
      }
      return new Details(SIZES[0].displayName);
   }

   public String getXsdType(Object details) {
      Details id = (Details) details;
      for (int i = 0; i < SIZES.length; i++) {
         if (id.mType.equals(SIZES[i].displayName)) {
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
      return DataIcons.XSD_GENERIC_DATE_TIME;//"Date / Time";
   }

   public Icon getDisplayIcon() {
      return DataIcons.getDateIcon();
   }

   public Object createDetails() {
      return new Details(SIZES[0].displayName);
   }

   static class Details {
      public Details(String type) {
         mType = type;
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
         Details id = (Details) val;
         String str = id.mType;
         mChangeListener = cl;
         mType = new JComboBox();
         for (int i = 0; i < SIZES.length; i++) {
            mType.addItem(SIZES[i].displayName);
         }
         mType.setMaximumRowCount(SIZES.length);
         mType.setLightWeightPopupEnabled(false);
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


      public Details getValue() {
         return new Details((String) mType.getSelectedItem());
      }

      private void changed() {
         mChangeListener.stateChanged(new ChangeEvent(this));
      }
   }
}
