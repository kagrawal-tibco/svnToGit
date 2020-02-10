package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateContentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

/**
 * Panel (i.e. GUI information) for a template binding.
 */
public class TemplatePanel implements StatementPanel {

   public TemplatePanel() {
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      return new TemplateBinding(BindingElementInfo.EMPTY_INFO, null, null);
   }

   public boolean canHaveAsChild(Binding binding, Binding childBinding) {
      return childBinding instanceof TemplateContentBinding;
   }

   public Class<TemplateBinding> getHandlesBindingClass() {
      return TemplateBinding.class;
   }

   public boolean getIsNilled(TemplateReport report) {
      return false;
   }

   public SmCardinality getOccurrence(TemplateReport report) {
      return SmCardinality.EXACTLY_ONE;
   }

   public JComponent getDetailsEditor(Binding binding,
                                      NamespaceContextRegistry contextNamespaceContextRegistry,
                                      ImportRegistry importRegistry,
                                      UIAgent uiAgent,
                                      SmSequenceType outputContext,
                                      SmSequenceType remainderType) {
      return new ExcludeEditor((TemplateBinding) binding);
   }

   static class ExcludeEditor extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField m_field;
      private JCheckBox m_exclude;
      private TemplateBinding m_sb;

      public ExcludeEditor(TemplateBinding tb) {
         super(new BorderLayout());
         m_sb = tb;
         JTextField jtf = new JTextField();
         m_exclude = new JCheckBox();
         m_field = jtf;
         String ep = tb.getLocalExcludePrefixes();
         m_exclude.setSelected(ep != null);
         jtf.setText(ep == null ? "" : ep);
         m_exclude.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               refresh();
            }
         });
         jtf.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
               refresh();
            }

            public void insertUpdate(DocumentEvent e) {
               refresh();
            }

            public void removeUpdate(DocumentEvent e) {
               refresh();
            }
         });
         JLabel prefixesLabel = new JLabel(BindingEditorResources.EXCLUDED_RESULT_PREFIXES + ":");
         JPanel space = new JPanel(new GridBagLayout());
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.fill = GridBagConstraints.BOTH;
         gbc.insets = new Insets(4, 4, 4, 4);
         gbc.weightx = 0;
         gbc.weighty = 0;
         gbc.gridy = 1;
         gbc.gridx = 1;
         space.add(new JLabel(BindingEditorResources.EXCLUDE_RESULT_PREFIXES + ":"), gbc);
         gbc.weightx = 1;
         gbc.gridy = 1;
         gbc.gridx = 2;
         space.add(m_exclude, gbc);
         gbc.weightx = 0;
         gbc.gridy = 2;
         gbc.gridx = 1;
         space.add(prefixesLabel, gbc);
         gbc.weightx = 1;
         gbc.gridy = 2;
         gbc.gridx = 2;
         space.add(jtf, gbc);
         add(space, BorderLayout.NORTH);
         reenable();
      }

      private void reenable() {
         boolean sel = m_exclude.isSelected();
         m_field.setEditable(sel);
         m_field.setEnabled(sel);
      }

      private void refresh() {
         reenable();
         if (m_exclude.isSelected()) {
            String txt = m_field.getText().trim();
            m_sb.setLocalExcludePrefixes(txt);
         }
         else {
            m_sb.setLocalExcludePrefixes(null);
         }
      }
   }

   public Binding getDefaultAddAroundBinding() {
      return null;
   }

   public Binding getDefaultAddChildBinding() {
      return null;
   }

   public String getDisplayName() {
      return "Template";
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      return DataIcons.getForEachIcon();
   }

   public String getDisplayNameFor(TemplateReport report) {
      return getDisplayName();
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      return FIELD_TYPE_NOT_EDITABLE;
   }

   public boolean isLeaf(TemplateReport templateReport) {
      return false;
   }
}

