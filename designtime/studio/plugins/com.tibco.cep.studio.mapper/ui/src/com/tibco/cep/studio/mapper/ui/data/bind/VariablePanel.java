package com.tibco.cep.studio.mapper.ui.data.bind;

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
import com.tibco.cep.mapper.xml.xdata.bind.CommentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.SetVariableBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateContentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.helpers.NoNamespace;

public class VariablePanel implements StatementPanel {
   public VariablePanel() {
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      return new SetVariableBinding(BindingElementInfo.EMPTY_INFO,
                                    ExpandedName.makeName(NoNamespace.URI, "var"),
                                    "");
   }

   public Class<SetVariableBinding> getHandlesBindingClass() {
      return SetVariableBinding.class;
   }

   public boolean canHaveAsChild(Binding binding, Binding childBinding) {
      SetVariableBinding svb = (SetVariableBinding) binding;
      // If it has a select, cannot have stuff, otherwise, it can have anything.
      if (svb.getFormula() == null) {
         // not a select:
         return childBinding instanceof TemplateContentBinding;
      }
      else {
         // is a select:
         return childBinding instanceof CommentBinding;
      }
   }

   private static class Editor extends JPanel {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField m_nameField = new JTextField();
      private JCheckBox m_formula = new JCheckBox(DataIcons.getVariablePanelUseSelectLabel());
      private SetVariableBinding m_binding;
      private String m_startingFormula = "";

      public Editor(SetVariableBinding b) {
         m_binding = b;
         setLayout(new GridBagLayout());
         GridBagConstraints gbc = new GridBagConstraints();

         m_nameField.setText(b.getVariableName().getLocalName());
         m_formula.setSelected(b.getFormula() != null);
         if (b.getFormula() != null) {
            m_startingFormula = b.getFormula();
         }
         m_nameField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
               changed();
            }

            public void insertUpdate(DocumentEvent e) {
               changed();
            }

            public void removeUpdate(DocumentEvent e) {
               changed();
            }
         });
         m_formula.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               changed();
            }
         });

         gbc.weightx = 0;
         gbc.weighty = 0;
         gbc.gridy = 0;
         gbc.gridx = 0;
         gbc.insets = new Insets(4, 4, 4, 4);
         gbc.fill = GridBagConstraints.BOTH;
         add(new JLabel(DataIcons.getVariablePanelVariableNameLabel() + ":"), gbc);
         gbc.gridx++;
         gbc.weightx = 1;
         add(m_nameField, gbc);
         gbc.gridx = 0;
         gbc.weightx = 0;
         gbc.gridy++;
         add(m_formula, gbc);
         gbc.gridy++;
         gbc.weighty = 1;
         add(new JLabel(), gbc); // spacer.
      }

      private void changed() {
         String n = m_nameField.getText();
         m_binding.setVariableName(ExpandedName.makeName(NoNamespace.URI, n));
         boolean form = m_formula.isSelected();
         m_binding.setFormula(form ? m_startingFormula : null);
      }
   }

   public JComponent getDetailsEditor(Binding binding,
                                      NamespaceContextRegistry contextNamespaceContextRegistry,
                                      ImportRegistry importRegistry,
                                      UIAgent uiAgent,
                                      SmSequenceType outputContext,
                                      SmSequenceType remainderType) {
      return new Editor((SetVariableBinding) binding);
   }

   public boolean getIsNilled(TemplateReport report) {
      return false;
   }

   public SmCardinality getOccurrence(TemplateReport report) {
      return SmCardinality.EXACTLY_ONE;
   }

   public Binding getDefaultAddAroundBinding() {
      return null;
   }

   public Binding getDefaultAddChildBinding() {
      return null;
   }

   public String getDisplayName() {
      return "Variable";
   }

   public String getDisplayNameFor(TemplateReport report) {
      if (report == null) {
         return getDisplayName();
      }
      SetVariableBinding svb = (SetVariableBinding) report.getBinding();
      return "[" + svb.getVariableName() + "= ]";
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      return DataIcons.getVariableIcon();
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      Binding b = templateReport.getBinding();
      if (b.getFormula() != null) {
         return FIELD_TYPE_XPATH;
      }
      else {
         return FIELD_TYPE_NOT_EDITABLE;
      }
   }

   public boolean isLeaf(TemplateReport templateReport) {
      Binding b = templateReport.getBinding();
      // If it has a formula (a select, it is a leaf, o.w. not)
      return b.getFormula() != null ? true : false;
   }
}

