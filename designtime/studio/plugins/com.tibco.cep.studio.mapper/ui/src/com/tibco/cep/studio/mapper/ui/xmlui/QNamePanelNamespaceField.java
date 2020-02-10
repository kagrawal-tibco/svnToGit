package com.tibco.cep.studio.mapper.ui.xmlui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.nsutils.ImportRegistryEntry;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.utils.JLongNameComboBox;
import com.tibco.cep.studio.mapper.ui.utils.StudioGridConstraints;
import com.tibco.cep.studio.mapper.ui.utils.StudioGridLayout;
import com.tibco.xml.schema.helpers.NoNamespace;

/**
 * Used by {@link QNamePanel} and only QNamePanel.
 * A customized referenceURI form field designed for use inside a QNameReally a XML schema component picker form field (i.e. either xsd element, wsdl port, etc.)
 */
public class QNamePanelNamespaceField extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JButton m_browse;
   private JButton m_goTo;
   private JButton m_clear;
   private JLongNameComboBox m_namespaces;
   private ImportRegistry m_importRegistry;
   private QNamePanel m_owner;
   private String m_targetNamespace;
   private boolean m_isSetting; // set while inside setNamespace
   private boolean m_allowsNoNamespace;
   private boolean m_editable;

   private ActionListener m_actionListener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         buttonHit(e);
      }
   };

   private static final Dimension BUTTON_SIZE = new Dimension(21, 21);

   /**
    * Only used by QName panel, just delegate.
    *
    * @param owner
    */
   public QNamePanelNamespaceField(QNamePanel owner) {
      buildButtons();
      m_owner = owner;
      m_namespaces.setRenderer(new DefaultListCellRenderer() {
         /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Component getListCellRendererComponent(JList list,
                                                       Object value,
                                                       int index,
                                                       boolean isSelected,
                                                       boolean cellHasFocus) {
            if ("".equals(value)) {
               // for display purposes, need something:
               value = "(" + DataIcons.getNoNamespaceLabel() + ")";
            }
            if (m_targetNamespace != null && m_targetNamespace.equals(value)) {
               value = "(" + DataIcons.getTargetNamespaceLabel() + ")";
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
         }
      });
   }

   public void setAllowsNoNamespace(boolean allowsNoNamespace) {
      if (allowsNoNamespace == m_allowsNoNamespace) {
         return;
      }
      m_allowsNoNamespace = allowsNoNamespace;
      rebuildList();
   }

   public boolean getAllowsNoNamespace() {
      return m_allowsNoNamespace;
   }

   public void setEditable(boolean editable) {
      m_editable = editable;
      m_namespaces.setEditable(editable && isEnabled());
   }

   public boolean getEditable() {
      return m_editable;
   }

   public void setImportRegistry(ImportRegistry importRegistry) {
      m_importRegistry = importRegistry;
      rebuildList();
   }

   public void setTargetNamespace(String targetNamespace) {
      // a couple quick checks:
      if (m_targetNamespace == targetNamespace) {
         return;
      }
      if (m_targetNamespace != null && m_targetNamespace.equals(targetNamespace)) {
         return;
      }
      // ok, did change:
      m_targetNamespace = targetNamespace;
      rebuildList();
   }

   public String getTargetNamespace() {
      return m_targetNamespace;
   }

   private void rebuildList() {
      if (m_importRegistry == null) {
         return;
      }
      m_isSetting = true;
      try {
         ImportRegistryEntry[] entry = m_importRegistry.getImports();
         m_namespaces.removeAllItems();
         HashSet<String> uniqueNs = new HashSet<String>();
         if (m_targetNamespace != null) {
            m_namespaces.addItem(m_targetNamespace);
            uniqueNs.add(m_targetNamespace);
         }
         if (m_allowsNoNamespace) {
            m_namespaces.addItem("");
         }
         for (int i = 0; i < entry.length; i++) {
            // Do NOT sort; present in import decls order.
            String ns = entry[i].getNamespaceURI();
            if (ns == null || ns.length() == 0) {
               continue;
            }
            if (!uniqueNs.contains(ns)) {
               uniqueNs.add(ns);
               m_namespaces.addItem(ns);
            }
         }
      }
      finally {
         m_isSetting = false;
      }
   }

   public void setNamespace(String namespace) {
      // internally, must use "" for no-namespace; on getNamespace returns the proper NoNamespace.URI.
      if (namespace == null) {
         namespace = "";
      }
      try {
         m_isSetting = true;
         m_namespaces.setSelectedItem(namespace);
         if (!namespace.equals(m_namespaces.getSelectedItem())) {
            // none listed; just display it & don't whine:
            m_namespaces.addItem(namespace);
            m_namespaces.setSelectedItem(namespace);
         }
      }
      finally {
         m_isSetting = false;
      }
   }

   /**
    * Implementation override, reenables components.
    */
   public void setEnabled(boolean enabled) {
      if (isEnabled() == enabled) {
         return;
      }
      super.setEnabled(enabled);
      m_namespaces.setEnabled(enabled);
      m_namespaces.setEditable(enabled && m_editable);
      m_browse.setEnabled(enabled);
      m_clear.setEnabled(enabled);
      // leave goto enabled.
      
      //Temp fix for BE-13239
      //project Library : Project Library : Open project library event, in payload click on arrow to open XML schema. It shows error windows can not find file. Either arrow button should be disabled or open valid file.
      
      m_goTo.setEnabled(enabled);
   }

   public String getNamespace() {
      String ns = (String) m_namespaces.getSelectedItem();
      if (ns == null || ns.equals("")) {
         return NoNamespace.URI;
      }
      return ns;
   }

   public void browse() {
      m_owner.browse();
   }

   public void goTo() {
      m_owner.goTo();
   }

   public void clear() {
      m_owner.clear();
   }

   private void buttonHit(ActionEvent ae) {
      Object btn = ae.getSource();
      if (btn == m_browse) {
         browse();
      }
      if (btn == m_goTo) {
         goTo();
      }
      if (btn == m_clear) {
         clear();
      }
      if (btn == m_namespaces) {
         if (!m_isSetting) {
            m_owner.namespaceChanged();
         }
      }
   }

   private void buildButtons() {
      // Mostly cut'n'pasted from ReferenceURIFormField.

      int[] h = {0, 5, 21, 4, 21, 4, 21};
      int[] v = {0};
      StudioGridLayout l = new StudioGridLayout(h, v);
      StudioGridConstraints c = new StudioGridConstraints();
      l.setColumnWeight(1, 1);
      setLayout(l);

      m_namespaces = new JLongNameComboBox();
      m_namespaces.addActionListener(m_actionListener);
      add(m_namespaces, c.xy(1, 1, "lr"));

      Icon icon = ResourceBundleManager.getIcon("ae.resource.reference.form.field.browse.icon", getClass().getClassLoader());
      m_browse = new JButton(icon);
      add(m_browse, c.xy(3, 1));
      m_browse.addActionListener(m_actionListener);
      m_browse.setToolTipText(ResourceBundleManager.getMessage("ae.resource.reference.form.field.browse.tooltip"));

      icon = ResourceBundleManager.getIcon("ae.resource.reference.form.field.goto.icon", getClass().getClassLoader());
      m_goTo = new JButton(icon);
      add(m_goTo, c.xy(5, 1));
      m_goTo.setPreferredSize(BUTTON_SIZE);
      m_goTo.setMinimumSize(BUTTON_SIZE);
      m_goTo.setActionCommand("goto");
      m_goTo.addActionListener(m_actionListener);
      m_goTo.setToolTipText(ResourceBundleManager.getMessage("ae.resource.reference.form.field.goto.tooltip"));

      icon = ResourceBundleManager.getIcon("ae.resource.reference.form.field.clear.icon", getClass().getClassLoader());
      m_clear = new JButton(icon);
      add(m_clear, c.xy(7, 1));
      m_clear.setPreferredSize(BUTTON_SIZE);
      m_clear.setMinimumSize(BUTTON_SIZE);
      m_clear.addActionListener(m_actionListener);
      m_clear.setToolTipText(ResourceBundleManager.getMessage("ae.resource.reference.form.field.clear.tooltip"));
   }
}
