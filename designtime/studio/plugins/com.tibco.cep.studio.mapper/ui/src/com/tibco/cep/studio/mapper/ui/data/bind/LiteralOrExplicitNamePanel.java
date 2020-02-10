package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.nsutils.NamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.AttributeBinding;
import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;
import com.tibco.cep.mapper.xml.xdata.bind.DataComponentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.DefaultCancelChecker;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.xmlui.QNamePanel;
import com.tibco.cep.studio.mapper.ui.xmlui.QNamePlugin;
import com.tibco.cep.studio.mapper.ui.xmlui.ScannerFilter;
import com.tibco.cep.studio.mapper.ui.xmlui.SchemaScanner;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmSupport;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.helpers.NoNamespace;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * A panel to pick a qname, used by {@link ElementPanel} and {@link AttributePanel}
 */
class LiteralOrExplicitNamePanel extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private QNamePanel m_QNamePanel;
   private UIAgent uiAgent;

   private JLabel m_nameFormulaLabel;
   private JTextField m_nameFormulaField;
   private JLabel m_namespaceFormulaLabel;
   private JTextField m_namespaceFormulaField;
   private JCheckBox m_showNamespace;
   private NamespaceContextRegistry m_namespaceContextRegistry;

   private JPanel m_explicitPanel;
   private JPanel m_area;
   private DataComponentBinding m_binding;

   private JRadioButton mLiteral;
   private JRadioButton mExplicit;

   public LiteralOrExplicitNamePanel(UIAgent uiAgent,
                                     NamespaceContextRegistry ni, ImportRegistry ir, QNamePlugin qnp, DataComponentBinding b, SmSequenceType remainingType) {
      super(new BorderLayout());
      this.uiAgent = uiAgent;
      setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
      m_binding = b;
      JPanel labels = new JPanel(new FlowLayout());
      labels.add(new JLabel(DataIcons.getLiteralExplicitPanelRepresentationLabel()));//WCETODO resourceize these.
      mLiteral = new JRadioButton(DataIcons.getLiteralExplicitPanelLiteralLabel());
      mExplicit = new JRadioButton(DataIcons.getLiteralExplicitPanelExplicitLabel());
      JPanel labelSpacer = new JPanel(new BorderLayout());
      labelSpacer.add(labels, BorderLayout.WEST);
      ButtonGroup bg = new ButtonGroup();
      bg.add(mLiteral);
      bg.add(mExplicit);
      labels.add(mLiteral);
      labels.add(mExplicit);

      m_area = new JPanel(new BorderLayout());
      add(labelSpacer, BorderLayout.NORTH);
      Border lb = BorderFactory.createLineBorder(Color.gray);
      Border sp = BorderFactory.createEmptyBorder(8, 8, 8, 8);
      m_area.setBorder(BorderFactory.createCompoundBorder(lb, sp));
      add(m_area, BorderLayout.CENTER);

      m_QNamePanel = new QNamePanel(uiAgent, qnp, ni, ir);
      m_QNamePanel.setNamespaceEditable(true);
      m_QNamePanel.setAllowsNoNamespace(true);
      m_QNamePanel.setErrorChecking(false); // don't show errors because of local elements (maybe update control to deal w/ those later)
      m_namespaceContextRegistry = ni;

      mLiteral.setSelected(!m_binding.isExplicitXslRepresentation());
      mExplicit.setSelected(m_binding.isExplicitXslRepresentation());
      if (m_binding.isExplicitXslRepresentation()) {
         showExplicit();
      }
      else {
         showLiteral();
         m_QNamePanel.setExpandedName(b.getName());
      }
      mLiteral.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            copyToLiteral();
            showLiteral();
            m_binding.setExplicitXslRepresentation(false);
            nameschanged();
         }
      });
      mExplicit.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            copyToExplicit();
            showExplicit();
            m_binding.setExplicitXslRepresentation(true);
            nameschanged();
         }
      });

      m_QNamePanel.addPropertyChangeListener(new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent evt) {
            ExpandedName ename = m_QNamePanel.getExpandedName();
            m_binding.setLiteralName(ename);
            m_binding.setExplicitNameAVT(""); // clear it (can't be null.)
            m_binding.setExplicitNamespaceAVT(null); // clear it (can't be null.)
         }
      });
      computeSuggestions(remainingType);
   }

   private void computeSuggestions(final SmSequenceType remainingType) {
      final DefaultCancelChecker cc = new DefaultCancelChecker();
      cc.setNiceMode(true); // we never cancel here, but we set the 'nice' mode so it Sleeps a bit.
      Thread th = new Thread(new Runnable() {
         public void run() {
            computeSuggestionsInternal(remainingType, cc);
         }
      }, "literal-name-panel subst scanner");
      th.setPriority(Thread.MIN_PRIORITY);
      th.start();
   }

   private void computeSuggestionsInternal(SmSequenceType remainingType, CancelChecker cancelChecker) {
      SmSequenceType[] els = SmSequenceTypeSupport.getAllChoices(remainingType.prime());
      HashSet<ExpandedName> choices = new HashSet<ExpandedName>();
      ArrayList<ExpandedName> choicesInOrder = new ArrayList<ExpandedName>();
      for (int i = 0; i < els.length; i++) {
         SmSequenceType e = els[i];
         if (e.getName() != null) {
            ExpandedName en = e.getName();
            addChoice(en, choices, choicesInOrder);
            ExpandedName[] subs = findSubstitutableElements(uiAgent, e, cancelChecker);
            for (int xi = 0; xi < subs.length; xi++) {
               ExpandedName sub = subs[xi];
               addChoice(sub, choices, choicesInOrder);
            }
         }
      }
      if (m_binding instanceof AttributeBinding) {
         // also suggest magic attributes:
         choicesInOrder.add(XSDL.ATTR_NO_NAMESPACE_SCHEMA_LOCATION.getExpandedName());
         choicesInOrder.add(XSDL.ATTR_SCHEMA_LOCATION.getExpandedName());
      }
      final ExpandedName[] e = choicesInOrder.toArray(new ExpandedName[0]);
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            m_QNamePanel.setSuggestions(e);
         }
      });
   }

   private static ExpandedName[] findSubstitutableElements(UIAgent uiAgent,
                                                           SmSequenceType t, CancelChecker cancelChecker) {
      SmParticleTerm pt = t.getParticleTerm();
      if (!(pt instanceof SmElement)) {
         return new ExpandedName[0];
      }
      final SmElement el = (SmElement) pt;
      if (!SmSupport.isGlobalComponent(el)) {
         // can't override this...
         return new ExpandedName[0];
      }
      if (el.getAllowedDerivation() == 0) // none
      {
         // can't override this...
         return new ExpandedName[0];
      }
      ScannerFilter filter = new ScannerFilter() {
         public boolean isMember(Object candidate) {
            SmElement testElement = (SmElement) candidate;
            while (testElement != null) {
               testElement = testElement.getSubstitutionGroup();
               if (testElement == el) {
                  return true;
               }
            }
            return false;
         }
      };
      ExpandedName[] r = SchemaScanner.scanComponents(uiAgent, SmComponent.ELEMENT_TYPE, filter, cancelChecker);
      SchemaScanner.sortByLocalName(r);
      return r;
   }

   private static void addChoice(ExpandedName name, Set<ExpandedName> set, ArrayList<ExpandedName> choicesInOrder) {
      if (!set.contains(name)) {
         set.add(name);
         choicesInOrder.add(name);
      }
   }

   private void buildExplicitPanel() {
      if (m_explicitPanel != null) {
         return;
      }
      m_explicitPanel = new JPanel(new GridBagLayout());
      m_namespaceFormulaLabel = new JLabel(DataIcons.getNamespaceLabel() + ":");
      m_nameFormulaLabel = new JLabel(DataIcons.getLocalNameLabel() + ":");
      m_nameFormulaField = new JTextField();
      m_namespaceFormulaField = new JTextField();
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(4, 4, 4, 4);
      gbc.fill = GridBagConstraints.BOTH;
      gbc.weightx = 0;
      gbc.weighty = 0;
      gbc.gridx = 0;
      gbc.gridy = 0;

      m_showNamespace = new JCheckBox(DataIcons.getLiteralExplicitPanelSeparateNamespaceLabel());
      m_explicitPanel.add(m_showNamespace, gbc);

      gbc.gridy++;
      m_showNamespace.setSelected(m_binding.getExplicitNamespaceAVT() != null);
      m_showNamespace.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            nameschanged();
         }
      });
      m_explicitPanel.add(m_namespaceFormulaLabel, gbc);
      gbc.gridx++;
      gbc.weightx = 1;
      m_explicitPanel.add(m_namespaceFormulaField, gbc);

      gbc.gridx = 0;
      gbc.gridy++;
      gbc.weightx = 0;
      m_explicitPanel.add(m_nameFormulaLabel, gbc);
      gbc.gridx++;
      gbc.weightx = 1;
      m_explicitPanel.add(m_nameFormulaField, gbc);

      gbc.gridx = 0;
      gbc.weightx = 0;
      gbc.gridy++;
      gbc.weighty = 1;
      m_explicitPanel.add(new JLabel(), gbc); // spacer.

      if (m_binding.getExplicitNamespaceAVT() != null) {
         m_namespaceFormulaField.setText(m_binding.getExplicitNamespaceAVT());
      }
      m_nameFormulaField.setText(m_binding.getExplicitNameAVT());
      nameschanged(); // init enabled, etc.

      DocumentListener dl = new DocumentListener() {
         public void changedUpdate(DocumentEvent e) {
            nameschanged();
         }

         public void insertUpdate(DocumentEvent e) {
            nameschanged();
         }

         public void removeUpdate(DocumentEvent e) {
            nameschanged();
         }
      };
      m_namespaceFormulaField.getDocument().addDocumentListener(dl);
      m_nameFormulaField.getDocument().addDocumentListener(dl);
   }

   private void nameschanged() {
      m_binding.setExplicitNameAVT(m_nameFormulaField.getText());
      if (m_showNamespace.isSelected()) {
         m_binding.setExplicitNamespaceAVT(m_namespaceFormulaField.getText());
         m_namespaceFormulaField.setEnabled(true);
         m_namespaceFormulaLabel.setEnabled(true);
      }
      else {
         m_binding.setExplicitNamespaceAVT(null);
         m_namespaceFormulaField.setEnabled(false);
         m_namespaceFormulaLabel.setEnabled(false);
      }
   }

   private void showLiteral() {
      m_area.removeAll();
      m_area.add(m_QNamePanel);
      m_area.revalidate();
      m_area.repaint();
   }

   private void showExplicit() {
      buildExplicitPanel();
      m_area.removeAll();
      m_area.add(m_explicitPanel);
      m_area.revalidate();
      m_area.repaint();
   }

   private void copyToLiteral() {
      if (m_explicitPanel == null) {
         return;
      }
      ExpandedName name = m_QNamePanel.getExpandedName();
      if (name.getLocalName().length() == 0) {
         ExpandedName nen = ExpandedName.makeName(name.getNamespaceURI(), m_nameFormulaField.getText());
         m_QNamePanel.setExpandedName(nen);
         name = nen;
      }
      if (NoNamespace.isNoNamespaceURI(name.getNamespaceURI())) {
         if (m_showNamespace.isSelected()) {
            String ns = m_namespaceFormulaField.getText();
            if (ns == "") {
               ns = NoNamespace.URI;
            }
            ExpandedName nen = ExpandedName.makeName(ns, name.getLocalName());
            m_QNamePanel.setExpandedName(nen);
         }
         else {
            QName qn = new QName(m_namespaceFormulaField.getText());
            try {
               String ns = qn.getExpandedName(m_namespaceContextRegistry).getNamespaceURI();
               if (ns == null) {
                  ns = "";
               }
               m_namespaceFormulaField.setText(ns);
            }
            catch (Exception e) {
               // eat it, nobody cares here.
            }
         }
      }
   }

   private void copyToExplicit() {
      buildExplicitPanel();
      if (m_nameFormulaField.getText().length() == 0) {
         ExpandedName oldName = m_QNamePanel.getExpandedName();
         QName qn = NamespaceManipulationUtils.getOrAddQNameFromExpandedName(oldName,
                                                                             m_namespaceContextRegistry);
         m_nameFormulaField.setText(qn.toString());
      }
   }
}

