package com.tibco.cep.studio.mapper.ui.data.bind.coerce;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.xpath.Coercion;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.basic.DetailsViewFactory;
import com.tibco.cep.studio.mapper.ui.data.param.ParameterEditorResources;
import com.tibco.cep.studio.mapper.ui.data.xpath.XPathTextArea;
import com.tibco.cep.studio.mapper.ui.data.xpath.XTypeChecker;
import com.tibco.cep.studio.mapper.ui.edittree.DefaultTreeNodeEditableTreeModel;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTree;
import com.tibco.cep.studio.mapper.ui.xmlui.XsdElementOrTypePanel;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * The panel used for the coercion editing.
 */
class CoercionPanelDetails extends JPanel implements DetailsViewFactory {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private XPathTextArea m_xpath;
   private XsdElementOrTypePanel m_elementOrTypePanel;
   private JLabel m_xpathLabel;
   private CoercionXPathList.Node m_currentNode;
   private boolean m_isSettingNow;
   private EditableTree m_tree;
   private boolean m_editable = true;
   private PropertyChangeListener m_listener;
   private JComboBox m_cardinalityCombo = new JComboBox();
   private JCheckBox m_cardinalityEnabled = new JCheckBox(ParameterEditorResources.CARDINALITY);

   public CoercionPanelDetails(UIAgent uiAgent,
                               EditableTree tm, NamespaceContextRegistry ni, ImportRegistry ir) {
      super(new BorderLayout(2, 4));

      m_tree = tm; // hacky, for change events.

      Border lb = BorderFactory.createLineBorder(Color.black);
      Border db = BorderFactory.createCompoundBorder(lb, BorderFactory.createEmptyBorder(4, 4, 4, 4));
      JPanel details = new JPanel(new BorderLayout());
      details.setBorder(db);

      JPanel p = new JPanel(new GridBagLayout());
      p.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

      GridBagConstraints gbc = new GridBagConstraints();
      m_xpath = new XPathTextArea(uiAgent);
      m_xpath.setFieldMode();
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.weightx = 0;
      gbc.weighty = 0;
      gbc.fill = GridBagConstraints.BOTH;
      m_xpathLabel = new JLabel(DataIcons.getXPathLabel() + ":   ");
      p.add(m_xpathLabel, gbc);
      gbc.gridx = 1;
      gbc.weightx = 1;
      p.add(m_xpath, gbc);

      gbc.gridy++;
      gbc.gridx = 0;
      gbc.weightx = 0;
      m_xpath.getDocument().addDocumentListener(new DocumentListener() {
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

      gbc.gridwidth = 2;
      gbc.gridx = 0;

      add(p, BorderLayout.NORTH);
      m_elementOrTypePanel = new XsdElementOrTypePanel(uiAgent, ni, ir);
      m_elementOrTypePanel.setAllowsGroupRefs(true);
      m_listener = new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent evt) {
            changed();
         }
      };
      m_elementOrTypePanel.addPropertyChangeListener(XsdElementOrTypePanel.VALUE_PROPERTY, m_listener);
      m_elementOrTypePanel.addPropertyChangeListener(XsdElementOrTypePanel.TYPE_PROPERTY, m_listener);
      details.add(m_elementOrTypePanel, BorderLayout.CENTER);

      JPanel bottom = new JPanel(new BorderLayout(8, 0));

      ActionListener al = new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            changed();
         }
      };
      m_cardinalityCombo.addActionListener(al);
      m_cardinalityEnabled.addActionListener(al);
      bottom.add(m_cardinalityEnabled, BorderLayout.WEST);
      bottom.add(m_cardinalityCombo, BorderLayout.CENTER);
      refillCardinalityCombo();
      details.add(bottom, BorderLayout.SOUTH);

      JPanel detailsSpacer = new JPanel(new BorderLayout());
      detailsSpacer.add(details);
      detailsSpacer.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
      add(detailsSpacer, BorderLayout.CENTER);
   }

   public void setEditable(boolean editable) {
      m_editable = editable;
      // Force re-enable.
      getComponentForNode(m_currentNode);
   }

   /**
    * Implements the details method; this implementation initializes the panel GUI & always returns this.
    *
    * @param node The node or null.
    */
   public JComponent getComponentForNode(Object node) {
      m_tree.waitForReport();
      m_isSettingNow = true;
      boolean enable;
      if (node == null || !(node instanceof CoercionXPathList.Node)) // (Root node can get selected somehow)
      {
         enable = false;
         m_currentNode = null;
         m_xpath.setText("");
         m_xpath.setAllowsEmptyString(true);
         m_elementOrTypePanel.setIsType(true);
         m_elementOrTypePanel.setExpandedName(ExpandedName.makeName(""));
         m_cardinalityEnabled.setSelected(false);
         m_cardinalityCombo.setSelectedIndex(0);
      }
      else {
         enable = true;
         m_xpath.setAllowsEmptyString(false);
         final CoercionXPathList.Node l = (CoercionXPathList.Node) node;
         ExprContext ec = null;
         if (l.getReport() != null) {
            ec = l.getReport().getInitialContext();
         }
         ;
         final ExprContext fec = ec;
         m_xpath.setExprContext(ec);
         m_xpath.setTypeChecker(new XTypeChecker() {
            public ErrorMessageList check(SmSequenceType gotType, TextRange errorMessageTextRange) {
               if (fec != null) {
                  Coercion c = l.createCoercion(fec.getNamespaceMapper());
                  ErrorMessage em = c.checkApplyTo(fec, errorMessageTextRange);
                  if (em != null) {
                     return new ErrorMessageList(em);
                  }
               }
               return ErrorMessageList.EMPTY_LIST;
            }

            public SmSequenceType getBasicType() {
               // whatever.
               return SMDT.REPEATING_ITEM;
            }
         });
         m_currentNode = l;
         String xp = l.getXPath();
         m_xpath.setText(xp);
         int tt = l.getType();
         int pt = tt == Coercion.COERCION_TYPE ? XsdElementOrTypePanel.TYPE_TYPE : (tt == Coercion.COERCION_ELEMENT ? XsdElementOrTypePanel.TYPE_ELEMENT : XsdElementOrTypePanel.TYPE_GROUP);
         m_elementOrTypePanel.setReferenceType(pt);
         ExpandedName name = l.getExpandedName();
         m_elementOrTypePanel.setExpandedName(name);
         setForOccurrence(l.getOccurrence());
      }
      if (!m_editable) {
         enable = false;
      }
      m_elementOrTypePanel.setEnabled(enable);
      m_xpath.setEnabled(enable);
      m_xpathLabel.setEnabled(enable);
      m_cardinalityCombo.setEnabled(enable && m_cardinalityEnabled.isSelected());
      m_cardinalityEnabled.setEnabled(enable);

      m_isSettingNow = false;

      return this;
   }

   /**
    * Handles any gui change.
    */
   private void changed() {
      if (m_isSettingNow) {
         return;
      }
      if (m_currentNode != null) {
         int pt = m_elementOrTypePanel.getReferenceType();
         int tt = pt == XsdElementOrTypePanel.TYPE_TYPE ? Coercion.COERCION_TYPE : (pt == XsdElementOrTypePanel.TYPE_ELEMENT ? Coercion.COERCION_ELEMENT : Coercion.COERCION_GROUP);
         m_currentNode.setType(tt);
         m_currentNode.setXPath(m_xpath.getText());
         m_currentNode.setOccurrence(computeOccurrence());
         m_cardinalityCombo.setEnabled(m_cardinalityEnabled.isSelected());

         DefaultTreeNodeEditableTreeModel tm = (DefaultTreeNodeEditableTreeModel) m_tree.getModel();
         TreeModelListener[] l = tm.getTreeModelListeners();
         for (int i = 0; i < l.length; i++) {
            l[i].treeNodesChanged(new TreeModelEvent(tm, m_currentNode.getPath()));
         }
         m_currentNode.setExpandedName(m_elementOrTypePanel.getExpandedName());
         m_xpath.startErrorCheck(); // Mark this dirty since we've changed the type (and substitution errors show up there)
         m_tree.markReportDirty();
      }
   }

   public void readPreferences(JComponent component, String keyprefix, UIAgent uiAgent) {
      // n/a yet.
   }

   public void writePreferences(JComponent component, String keyprefix, UIAgent uiAgent) {
      // n/a yet.
   }

   /**
    * Fills in the combo with the allowed items.
    */
   private void refillCardinalityCombo() {
      m_cardinalityCombo.addItem(ParameterEditorResources.OCCURRENCE_REQUIRED);
      m_cardinalityCombo.addItem(ParameterEditorResources.OCCURRENCE_OPTIONAL_WITH_PAREN);
      m_cardinalityCombo.addItem(ParameterEditorResources.OCCURRENCE_REPEATING_WITH_PAREN);
      m_cardinalityCombo.addItem(ParameterEditorResources.OCCURRENCE_AT_LEAST_ONE_WITH_PAREN);
   }

   private SmCardinality computeOccurrence() {
      if (!m_cardinalityEnabled.isSelected()) {
         return null;
      }
      int idx = m_cardinalityCombo.getSelectedIndex();
      if (idx == 0) {
         return SmCardinality.EXACTLY_ONE;
      }
      if (idx == 1) {
         return SmCardinality.OPTIONAL;
      }
      if (idx == 2) {
         return SmCardinality.REPEATING;
      }
      return SmCardinality.AT_LEAST_ONE;
   }

   private void setForOccurrence(SmCardinality card) {
      if (card == null) {
         m_cardinalityCombo.setSelectedIndex(0);
         m_cardinalityEnabled.setSelected(false);
         m_cardinalityCombo.setEnabled(false);
         return;
      }
      m_cardinalityEnabled.setSelected(true);
      m_cardinalityCombo.setEnabled(true);
      if (card.equals(SmCardinality.OPTIONAL)) {
         m_cardinalityCombo.setSelectedIndex(1);
      }
      else {
         if (card.equals(SmCardinality.EXACTLY_ONE)) {
            m_cardinalityCombo.setSelectedIndex(0);
         }
         else {
            if (card.equals(SmCardinality.AT_LEAST_ONE)) {
               m_cardinalityCombo.setSelectedIndex(3);
            }
            else {
               m_cardinalityCombo.setSelectedIndex(2);
            }
         }
      }
   }
}

