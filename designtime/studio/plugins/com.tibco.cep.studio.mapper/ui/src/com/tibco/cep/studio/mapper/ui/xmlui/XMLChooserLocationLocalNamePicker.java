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
import com.tibco.cep.studio.mapper.ui.data.utils.JLongNameComboBox;

/**
 * Used by {@link XMLChooser}, this is control that picks a local name from within a namespace.
 */
class XMLChooserLocationLocalNamePicker extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private UIAgent uiAgent;
   private JLongNameComboBox m_locationList; // Currently stores repo relative locations (String)
   private JPanel m_namespaceChoicePanel;
   private String m_currentNamespace;
   private XMLChooserLocalNameField m_localNameField;

   public XMLChooserLocationLocalNamePicker(XMLChooser owner,
		   UIAgent uiAgent,
                                            QNamePlugin plugin,
                                            QNamePluginSubField[] subField) {
      super(new BorderLayout());
      m_localNameField = new XMLChooserLocalNameField(owner, uiAgent, plugin, subField);
      this.uiAgent = uiAgent;

      m_locationList = new JLongNameComboBox();
      m_locationList.setEditable(false); // just a picker.
      m_locationList.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            showSelectedLocation();
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
      m_namespaceChoicePanel.add(new JLabel(QNamePanelResources.LOCATION + ":"), gbc);
      gbc.gridy++;
      m_namespaceChoicePanel.add(m_locationList, gbc);
      add(m_namespaceChoicePanel, BorderLayout.NORTH);
      add(m_localNameField, BorderLayout.CENTER);
   }

   public void setSelectedLocalName(String name) {
      m_localNameField.setSelectedLocalName(name);
   }

   public void setSelectedLocation(String location) {
      for (int i = 0; i < m_locationList.getModel().getSize(); i++) {
         String s = (String) m_locationList.getModel().getElementAt(i);
         if (s.equals(location)) {
            m_locationList.setSelectedIndex(i);
            break;
         }
      }
   }

   public String getSelectedLocation() {
      Object obj = m_locationList.getSelectedItem();
      if (obj == null) {
         return "";
      }
      return (String) obj;
   }

   public String getSelectedLocalName() {
      return m_localNameField.getSelectedLocalName();
   }

   public String[] getSelectedSubFieldNames() {
      return m_localNameField.getSelectedSubFieldNames();
   }

   public void setSelectedSubFieldNames(String[] subFieldNames) {
      m_localNameField.setSelectedSubFieldNames(subFieldNames);
   }

   /**
    * Shows the selected schema from the list of ones in this file (i.e. for WSDL)
    */
   private void showSelectedLocation() {
      String location = (String) m_locationList.getSelectedItem();
      showLocation(location);
   }

   public void refreshPreview() {
      m_localNameField.refreshPreview();
   }

   public void clear() {
      if (m_locationList != null) {
         m_locationList.removeAllItems();
      }
      m_localNameField.clear();
   }

   public void showLocations(String[] absoluteLocations, String namespace) {
      String[] relLocations = new String[absoluteLocations.length];
      for (int i = 0; i < relLocations.length; i++) {
         if (absoluteLocations[i] == null) {
            throw new NullPointerException("Null location: " + i + " passed in");
         }
         relLocations[i] = absoluteLocations[i];//use absolute location here to maintain project library location information
//       relLocations[i] = uiAgent.getProjectRelativeURIFromAbsoluteURI(absoluteLocations[i]);
      }

      m_currentNamespace = namespace;

      m_locationList.removeAllItems();
      m_locationList.setEnabled(true);
      for (int i = 0; i < relLocations.length; i++) {
         m_locationList.addItem(relLocations[i]);
      }
      if (relLocations.length > 0) {
         try {
            m_locationList.setSelectedIndex(0);
         }
         catch (Exception e) {
            // Happend once (startup?), ignore.
         }
      }
      else {
         m_localNameField.clear();
      }
   }

   public void showLocation(String location) {
      try {
         if (location == null) {
            // shouldn't happen, so don't worry about I10n.
            badItemSelection("<html>No schema found");//WCETODO fix cleanup error.
            return;
         }
         String absLocation = uiAgent.getAbsoluteURIFromProjectRelativeURI(location);
         m_localNameField.showLocationNamespace(absLocation, m_currentNamespace);
      }
      catch (Exception spe) {
         spe.printStackTrace(System.err);
         String msg = "<html>Internal error:\n" + spe.getMessage();
         badItemSelection(msg);
      }
   }

   private void badItemSelection(String msg) {
      if (m_locationList != null) {
         m_locationList.removeAllItems();
         m_locationList.setEnabled(false);
      }
      m_localNameField.badItemSelection(msg);
   }
}
