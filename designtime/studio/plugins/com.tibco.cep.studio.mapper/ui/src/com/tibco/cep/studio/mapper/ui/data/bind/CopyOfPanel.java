package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.CopyOfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateContentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.StudioStrings;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * The panel corresponding to {@link CopyOfBinding}.
 */
public class CopyOfPanel implements StatementPanel {

   public CopyOfPanel() {
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      return new CopyOfBinding(BindingElementInfo.EMPTY_INFO, null);
   }

   public Binding getDefaultAddAroundBinding() {
      return null;
   }

   public Binding getDefaultAddChildBinding() {
      return null;
   }

   public boolean getIsNilled(TemplateReport report) {
      return false;
   }

   public boolean canHaveAsChild(Binding binding, Binding childBinding) {
      return childBinding instanceof TemplateContentBinding;
   }

   public SmCardinality getOccurrence(TemplateReport report) {
      return SmCardinality.EXACTLY_ONE;
   }

   public Class<CopyOfBinding> getHandlesBindingClass() {
      return CopyOfBinding.class;
   }

   public String getDisplayNameFor(TemplateReport report) {
      String base = "[" + getDisplayName() + "]";
      String dn = getOptionalTypeDisplayName(report.getFormulaType());
      if (dn == null) {
         return base;
      }
      return dn + " - " + base;
   }

   static String getOptionalTypeDisplayName(SmSequenceType t) {
      if (t == null) {
         return null;
      }
      if (SmSequenceTypeSupport.isPreviousError(t)) {
         return null;
      }
      // Filter out some stupid ones (sort of, anyway)
      if (t == SMDT.NODE || t == SMDT.ATTRIBUTE_NODE) {
         return null;
      }
      return SmSequenceTypeSupport.getDisplayName(t);
   }

   static class Editor extends JPanel {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CopyOfBinding m_copyOfBinding;

      private JComboBox m_namespaces = new JComboBox();

      public Editor(CopyOfBinding cob) {
         super(new BorderLayout());
         m_copyOfBinding = cob;
         JPanel lblp = new JPanel(new BorderLayout(8, 0));
         lblp.add(new JLabel(DataIcons.getCopyOfPanelCopyNamespacesLabel()), BorderLayout.WEST);
         m_namespaces.addItem(StudioStrings.DEFAULT + " (" + StudioStrings.YES + ")");
         m_namespaces.addItem(StudioStrings.YES);
         m_namespaces.addItem(StudioStrings.NO);
         lblp.add(m_namespaces);
         Boolean v = m_copyOfBinding.getCopyNamespaces();
         if (v != null) {
            m_namespaces.setSelectedIndex(v.booleanValue() ? 1 : 2);
         }
         else {
            m_namespaces.setSelectedIndex(0); // default
         }
         m_namespaces.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int selectedIndex = m_namespaces.getSelectedIndex();
               Boolean bv;
               switch (selectedIndex) {
                  case 0:
                     bv = null;
                     break;
                  case 1:
                     bv = Boolean.TRUE;
                     break;
                  default:
                     bv = Boolean.FALSE;
                     break;
               }
               m_copyOfBinding.setCopyNamespaces(bv);
            }
         });
         add(lblp, BorderLayout.NORTH);
      }
   }

   public JComponent getDetailsEditor(Binding binding,
                                      NamespaceContextRegistry contextNamespaceContextRegistry,
                                      ImportRegistry importRegistry,
                                      UIAgent uiAgent,
                                      SmSequenceType outputContext,
                                      SmSequenceType remainderType) {
      return new Editor((CopyOfBinding) binding);
   }

   public static final String LABEL = "Copy-Of";

   public String getDisplayName() {
      return LABEL;
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      return DataIcons.getCopyIcon();
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      return FIELD_TYPE_XPATH;
   }

   public boolean isLeaf(TemplateReport templateReport) {
      return true;
   }

   public boolean isAvailable(SmSequenceType term) {
      return true; // always available.
   }
}

