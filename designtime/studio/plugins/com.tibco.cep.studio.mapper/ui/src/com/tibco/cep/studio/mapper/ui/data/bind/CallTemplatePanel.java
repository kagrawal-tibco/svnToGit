package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.CallTemplateBinding;
import com.tibco.cep.mapper.xml.xdata.bind.CommentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.WithParamBinding;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.utils.XMLNameDocument;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

public class CallTemplatePanel implements StatementPanel {
   static class Editor extends JPanel {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField mName;
      private CallTemplateBinding mBinding;

      public Editor(CallTemplateBinding b) {
         super(new BorderLayout());
         mBinding = b;
         JPanel item = new JPanel(new GridBagLayout());
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.weightx = 0;
         gbc.weighty = 0;
         gbc.gridx = 0;
         gbc.insets = new Insets(4, 4, 4, 4);
         gbc.fill = GridBagConstraints.HORIZONTAL;
         item.add(new JLabel("Call Template Name"), gbc);
         gbc.gridx = 1;
         gbc.weightx = 1;
         mName = new JTextField();
         item.add(mName, gbc);
         gbc.gridx = 0;
         gbc.gridy++;
         gbc.weightx = 0;
         gbc.weighty = 1;
         gbc.fill = GridBagConstraints.BOTH;
         item.add(new JPanel(), gbc); // spacer.

         mName.setDocument(new XMLNameDocument());
         String n = mBinding.getTemplateName();
         if (n == null) {
            n = "";
         }
         mName.setText(n);
         mName.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
               update();
            }

            public void insertUpdate(DocumentEvent e) {
               update();
            }

            public void removeUpdate(DocumentEvent e) {
               update();
            }
         });

         add(item, BorderLayout.NORTH);
      }

      private void update() {
         mBinding.setTemplateName(mName.getText());
      }
   }

   public CallTemplatePanel() {
   }

   public boolean canHaveAsChild(Binding binding, Binding childBinding) {
      return childBinding instanceof WithParamBinding || childBinding instanceof CommentBinding;
   }

   public boolean getIsNilled(TemplateReport report) {
      return false;
   }

   public SmCardinality getOccurrence(TemplateReport report) {
      return SmCardinality.EXACTLY_ONE;
   }

   public Binding getDefaultAddAroundBinding() {
      // nothing special.
      return null;
   }

   public Binding getDefaultAddChildBinding() {
      return new WithParamBinding(BindingElementInfo.EMPTY_INFO, "");
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      return new CallTemplateBinding(BindingElementInfo.EMPTY_INFO, null); // No shared resource providers!
   }

   public String getDisplayName() {
      return "Call Template";
   }

   public String getDisplayNameFor(TemplateReport report) {
      CallTemplateBinding tb = (CallTemplateBinding) report.getBinding();
      return "[Call '" + tb.getTemplateName() + "']";
   }

   public Class<CallTemplateBinding> getHandlesBindingClass() {
      return CallTemplateBinding.class;
   }

   public void test_setParameter(String name, String value) {
//        if (name.equals("count"))
//        {
//            mCount.setText("" + value);
//            return;
//        }
      throw new RuntimeException("No parameter: " + name);
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      return DataIcons.getCallTemplateIcon();
   }

   public JComponent getDetailsEditor(Binding binding,
                                      NamespaceContextRegistry contextNamespaceContextRegistry,
                                      ImportRegistry importRegistry,
                                      UIAgent uiAgent,
                                      SmSequenceType outputContext,
                                      SmSequenceType remainderType) {
      return new Editor((CallTemplateBinding) binding);
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      return FIELD_TYPE_NOT_EDITABLE; // the template name.
   }

   public boolean isLeaf(TemplateReport templateReport) {
      return false;
   }
}

