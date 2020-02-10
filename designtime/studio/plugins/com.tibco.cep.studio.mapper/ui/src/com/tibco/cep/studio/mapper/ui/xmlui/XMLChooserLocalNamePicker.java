package com.tibco.cep.studio.mapper.ui.xmlui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.utils.JLongNameComboBox;
import com.tibco.xml.schema.helpers.NoNamespace;

/**
 * Used by {@link XMLChooser}, this is control that picks a local name from within a namespace.
 */
class XMLChooserLocalNamePicker extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private UIAgent uiAgent;
   private JLongNameComboBox m_namespaceList;
   private JPanel m_namespaceChoicePanel;
   private XMLChooserLocalNameField m_localNameField;
   private String m_currentRelativeLocation;

   public XMLChooserLocalNamePicker(XMLChooser owner, UIAgent uiAgent, QNamePlugin plugin,
                                    QNamePluginSubField[] subField) {
      super(new BorderLayout());
      this.uiAgent = uiAgent;
      m_localNameField = new XMLChooserLocalNameField(owner, uiAgent, plugin, subField);

      m_namespaceList = new JLongNameComboBox();
      m_namespaceList.setEditable(false); // just a picker.
      m_namespaceList.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            showSelectedNamespace();
         }
      });
      m_namespaceChoicePanel = new JPanel(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.BOTH;
      gbc.weightx = 1;
      gbc.weighty = 0;
      gbc.insets = new Insets(4, 4, 4, 4);
      gbc.gridy = 0;
      gbc.gridx = 0;
      m_namespaceChoicePanel.add(new JLabel(DataIcons.getNamespaceLabel() + ":"), gbc);
      gbc.gridy++;
      m_namespaceChoicePanel.add(m_namespaceList, gbc);
      add(m_namespaceChoicePanel, BorderLayout.NORTH);
      add(m_localNameField, BorderLayout.CENTER);
   }

   public void setSelectedLocalName(String name) {
      m_localNameField.setSelectedLocalName(name);
   }

   public void setSelectedNamespace(String namespace) {
      for (int i = 0; i < m_namespaceList.getModel().getSize(); i++) {
         String s = (String) m_namespaceList.getModel().getElementAt(i);
         if (s.equals(namespace)) {
            m_namespaceList.setSelectedIndex(i);
            break;
         }
      }
   }

   public String getSelectedNamespace() {
      Object obj = m_namespaceList.getSelectedItem();
      if (obj == null) {
         return NoNamespace.URI; // "";
      }
      return (String) obj;
   }

   public String getSelectedLocalName() {
      return m_localNameField.getSelectedLocalName();
   }

   public String[] getSelectedSubFieldNames() {
      return m_localNameField.getSelectedSubFieldNames();
   }

   public void setSelectedSubFieldNames(String[] names) {
      m_localNameField.setSelectedSubFieldNames(names);
   }

   /**
    * Shows the selected schema from the list of ones in this file (i.e. for WSDL)
    */
   private void showSelectedNamespace() {
      String namespace = (String) m_namespaceList.getSelectedItem();
      showNamespace(namespace);
   }

   public void clear() {
      if (m_namespaceList != null) {
         m_namespaceList.removeAllItems();
      }
      m_localNameField.clear();
   }

   public void showNamespaces(String location, String[] namespaces) {
      m_currentRelativeLocation = location;
      m_namespaceList.removeAllItems();
      m_namespaceList.setEnabled(true);
      for (int i = 0; i < namespaces.length; i++) {
         if (namespaces[i] == null) {
            throw new NullPointerException("Null namespace: " + i + " passed in");
         }
         m_namespaceList.addItem(namespaces[i]);
      }
      if (namespaces.length > 0) {
         try {
            m_namespaceList.setSelectedIndex(0);
         }
         catch (Exception e) {
         }
      }
      else {
         m_localNameField.clear();
      }
   }

   public void showNamespace(String namespace) {
      try {
         if (namespace == null) {
            badItemSelection("<html>No schema found");//WCETODO fix cleanup error.
            return;
         }
         String absLocation = uiAgent.getAbsoluteURIFromProjectRelativeURI(m_currentRelativeLocation);
         m_localNameField.showLocationNamespace(absLocation, namespace);
      }
      catch (Exception spe) {
         spe.printStackTrace(System.err);
         String msg = "<html>Internal error:\n" + spe.getMessage();
         badItemSelection(msg);
      }
   }

   public void refreshPreview() {
      m_localNameField.refreshPreview();
   }

   private void badItemSelection(String msg) {
      if (m_namespaceList != null) {
         m_namespaceList.removeAllItems();
         m_namespaceList.setEnabled(false);
      }
      m_localNameField.badItemSelection(msg);
   }
}
