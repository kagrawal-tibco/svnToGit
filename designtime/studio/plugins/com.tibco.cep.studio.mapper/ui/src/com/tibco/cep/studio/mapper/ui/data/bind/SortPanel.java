package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.CommentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ForEachBinding;
import com.tibco.cep.mapper.xml.xdata.bind.SortBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

public class SortPanel implements StatementPanel {

   public SortPanel() {
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      return new SortBinding(BindingElementInfo.EMPTY_INFO);
   }

   public Class<SortBinding> getHandlesBindingClass() {
      return SortBinding.class;
   }

   public boolean canHaveAsChild(Binding binding, Binding childBinding) {
      return childBinding instanceof CommentBinding;
   }

   public Binding getDefaultAddAroundBinding() {
      return new ForEachBinding(BindingElementInfo.EMPTY_INFO);
   }

   public Binding getDefaultAddChildBinding() {
      return null;
   }

   public boolean getIsNilled(TemplateReport report) {
      return false;
   }

   public SmCardinality getOccurrence(TemplateReport report) {
      return SmCardinality.EXACTLY_ONE;
   }

   static class AVTComboBox extends JComboBox {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AVTComboBox() {
         super.setEditable(true);
      }

      private String[] m_values;
      private String[] m_labels;

      public String getText() {
         int index = getSelectedIndex();
         if (index == -1) {
            JTextField jtf = (JTextField) super.getEditor().getEditorComponent();
            return jtf.getText();
         }
         return m_values[index];
      }

      public void setText(String text) {
         for (int i = 0; i < m_values.length; i++) {
            if (text == null && m_values[i] == null) {
               setSelectedIndex(i);
               return;
            }
            if (text != null && text.equals(m_values[i])) {
               setSelectedIndex(i);
               return;
            }
         }
         // custom:
         JTextField jtf = (JTextField) super.getEditor().getEditorComponent();
         jtf.setText(text);
      }

      public void setupChoices(String[] values, String[] labels) {
         m_values = values;
         m_labels = labels;
         super.removeAllItems();
         for (int i = 0; i < m_labels.length; i++) {
            addItem(m_labels[i]);
         }
      }
   }


   static class Editor extends JPanel {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SortBinding m_binding;
      private AVTComboBox m_sortOrder;
      private AVTComboBox m_caseOrder;
      private AVTComboBox m_dataType;
      private AVTComboBox m_language;

      public Editor(SortBinding sb) {
         super(new GridBagLayout());
         m_binding = sb;
         m_sortOrder = new AVTComboBox();
         String[] sol = new String[]{"Default (Ascending)", "Ascending", "Descending"};
         String[] sov = new String[]{null, SortBinding.SORT_ORDER_ASCENDING, SortBinding.SORT_ORDER_DESCENDING};
         m_sortOrder.setupChoices(sov, sol);
         m_sortOrder.setText(m_binding.getOrder());
         m_sortOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               m_binding.setOrder(m_sortOrder.getText());
            }
         });
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.weightx = 0;
         gbc.gridy = 0;
         gbc.gridx = 0;
         gbc.weighty = 0;
         gbc.insets = new Insets(4, 4, 4, 4);
         gbc.fill = GridBagConstraints.BOTH;

         gbc.weightx = 0;
         gbc.gridx = 0;

         add(new JLabel("Ordering:"), gbc);
         gbc.weightx = 1;
         gbc.gridx = 1;
         add(m_sortOrder, gbc);

         gbc.weightx = 0;
         gbc.gridx = 0;
         gbc.gridy++;

         add(new JLabel("Data-Type:"), gbc);
         gbc.weightx = 1;
         gbc.gridx = 1;
         m_dataType = new AVTComboBox();
         String[] dtl = new String[]{"Default (Text)", "Text", "Number"};
         String[] dtv = new String[]{null, SortBinding.DATA_TYPE_TEXT, SortBinding.DATA_TYPE_NUMBER};
         m_dataType.setupChoices(dtv, dtl);
         m_dataType.setText(m_binding.getDataType());
         m_dataType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               m_binding.setDataType(m_dataType.getText());
               reenableButtons();
            }
         });
         add(m_dataType, gbc);

         m_language = new AVTComboBox();
         String[] ll = new String[]{"Default (System dependent)"};
         String[] lv = new String[]{null};
         m_language.setupChoices(lv, ll);
         m_language.setText(m_binding.getLang());
         m_language.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               m_binding.setLang(m_language.getText());
            }
         });
         gbc.gridx = 0;
         gbc.weightx = 0;
         gbc.gridy++;
         add(new JLabel("Language:"), gbc);
         gbc.gridx = 1;
         gbc.weightx = 1;
         add(m_language, gbc);

         gbc.gridx = 0;
         gbc.weightx = 0;
         gbc.gridy++;

         m_caseOrder = new AVTComboBox();

         String[] col = new String[]{"Default (Language-dependent)", "Upper-First", "Lower-First"};
         String[] cov = new String[]{null, SortBinding.CASE_ORDER_UPPER_FIRST, SortBinding.CASE_ORDER_LOWER_FIRST};
         m_caseOrder.setupChoices(cov, col);
         m_caseOrder.setText(m_binding.getCaseOrder());

         add(new JLabel("Case Order:"), gbc);
         gbc.weightx = 1;
         gbc.gridx = 1;
         add(m_caseOrder, gbc);
         m_caseOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               m_binding.setCaseOrder(m_caseOrder.getText());
            }
         });

         gbc.gridy++;
         gbc.gridx = 0;
         gbc.weightx = 0;
         gbc.weighty = 1;
         add(new JLabel(), gbc);

         reenableButtons();
      }

      private void reenableButtons() {
         boolean isNumber = SortBinding.DATA_TYPE_NUMBER.equals(m_dataType.getText());
         m_language.setEnabled(!isNumber);
         m_caseOrder.setEnabled(!isNumber);
      }
   }

   public JComponent getDetailsEditor(Binding binding,
                                      NamespaceContextRegistry contextNamespaceContextRegistry,
                                      ImportRegistry importRegistry,
                                      UIAgent uiAgent,
                                      SmSequenceType outputContext,
                                      SmSequenceType remainderType) {
      SortBinding sb = (SortBinding) binding;
      return new Editor(sb);
   }

   public String getDisplayName() {
      return "Sort";
   }

   public String getDisplayNameFor(TemplateReport report) {
      return "[" + getDisplayName() + "]";
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      return DataIcons.getSortIcon();
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      return FIELD_TYPE_XPATH;
   }

   public boolean isLeaf(TemplateReport templateReport) {
      return true;
   }
}

