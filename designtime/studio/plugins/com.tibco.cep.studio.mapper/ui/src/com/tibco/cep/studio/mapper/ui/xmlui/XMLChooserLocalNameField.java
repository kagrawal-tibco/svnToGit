package com.tibco.cep.studio.mapper.ui.xmlui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.xml.sax.SAXException;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Used by {@link XMLChooserLocalNamePicker} and {@link XMLChooserLocationLocalNamePicker}, this is control that picks a local name from within a namespace.
 */
class XMLChooserLocalNameField extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIAgent uiAgent;
   private JList m_itemsList;
   private JScrollPane m_itemsListScroller;

   private JComboBox m_itemsComboBox; // set if items list isn't.

   private QNamePlugin m_plugin;
   private XMLChooser m_owner;
   private LocationAndNamespace m_currentLocationAndNamespace;
   private QNamePluginSubField m_subField[]; // non null if has sub-field
   private JPanel m_subFieldsPanel;

   private JLabel[] m_subFieldLabel; // non null if has sub-field.
   private JComboBox[] m_subFieldCombo; // non null if has sub-field
   private Object[] m_currentObject; // the currently selected opaque object for subfields.

   /**
    * Vertical space between label & combo box:
    */
   private static final int LABEL_VGAP = 2;

   /**
    * Vertical space between label & combo box:
    */
   private static final int SUB_FIELD_VGAP = 4;

   /**
    * Keeps the most recently selected local-name for any given namespace so that if you switch back & forth, you
    * don't have to re-locate the 'interesting' local-name.
    */
   private HashMap<LocationAndNamespace, String> m_locationAndNamespaceToLastLocalName = new HashMap<LocationAndNamespace, String>(); // String (namespace) -> String (local-name) or null.


   /**
    * Simple pair of location/namespace for putting in hash-maps.
    */
   private static class LocationAndNamespace {
      private final String m_location;
      private final String m_namespace;

      public LocationAndNamespace(String location, String namespace) {
         m_location = location;
         m_namespace = namespace;
      }

      public int hashCode() {
         return m_location.hashCode() + m_namespace.hashCode();
      }

      public boolean equals(Object obj) {
         if (!(obj instanceof LocationAndNamespace)) {
            return false;
         }
         LocationAndNamespace lan = (LocationAndNamespace) obj;
         return lan.m_location.equals(m_location) && lan.m_namespace.equals(m_namespace);
      }

      public String getLocation() {
         return m_location;
      }

      public String getNamespace() {
         return m_namespace;
      }
   }

   public XMLChooserLocalNameField(XMLChooser owner, UIAgent uiAgent,
                                   QNamePlugin plugin, QNamePluginSubField[] subFields) {
      super(new BorderLayout());
      this.m_plugin = plugin;
      m_owner = owner;
      this.uiAgent = uiAgent;
      m_subField = subFields.length > 0 ? subFields : null;

      buildSubFields(subFields);
      if (subFields.length == 0) {
         // use a list:
         m_itemsList = new JList();

         JPanel items = new JPanel(new BorderLayout(0, LABEL_VGAP));
         String lbl = plugin.getLocalNameLabel();
         items.add(new JLabel(lbl), BorderLayout.NORTH);
         m_itemsListScroller = new JScrollPane(m_itemsList);
         items.add(m_itemsListScroller);

         m_itemsList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
               updatePreviewTree();
            }
         });
         m_itemsList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
               updatePreviewTree();
            }
         });
         add(items, BorderLayout.CENTER);
      }
      else {
         // use a combo for local-names:
         m_itemsComboBox = new JComboBox();
         m_itemsComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
               updatePreviewTree();
            }
         });
         JPanel items = new JPanel(new BorderLayout(0, LABEL_VGAP));
         String lbl = plugin.getLocalNameLabel();
         items.add(new JLabel(lbl), BorderLayout.NORTH);
         items.add(m_itemsComboBox, BorderLayout.CENTER);
         items.add(m_subFieldsPanel, BorderLayout.SOUTH);
         JPanel spacer = new JPanel(new BorderLayout());
         spacer.add(items, BorderLayout.NORTH);

         add(spacer, BorderLayout.CENTER);
      }

      setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
   }

   private void buildSubFields(QNamePluginSubField[] subFields) {
      m_subFieldCombo = new JComboBox[subFields.length];
      m_subFieldLabel = new JLabel[subFields.length];
      m_currentObject = new Object[subFields.length];
      JPanel subFieldsPanel = null;
      if (subFields.length > 0) {
         subFieldsPanel = new JPanel(new GridLayout(subFields.length, 1, 0, SUB_FIELD_VGAP));
         subFieldsPanel.setBorder(BorderFactory.createEmptyBorder(SUB_FIELD_VGAP, 0, 0, 0));
      }
      for (int i = 0; i < subFields.length; i++) {
         QNamePluginSubField sf = subFields[i];
         m_subFieldCombo[i] = new JComboBox();
         m_subFieldLabel[i] = new JLabel(sf.getFieldNameLabel());
         JPanel panel = new JPanel(new BorderLayout());
         panel.add(m_subFieldLabel[i], BorderLayout.NORTH);
         panel.add(m_subFieldCombo[i], BorderLayout.CENTER);
         final int fi = i;
         m_subFieldCombo[i].addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
               subFieldItemChanged(fi);
            }
         });
         subFieldsPanel.add(panel);
      }
      m_subFieldsPanel = subFieldsPanel;
   }

   public void setSelectedLocalName(String name) {
      if (m_itemsList == null) {
         m_itemsComboBox.setSelectedItem(name);
      }
      else {
         ListModel lm = m_itemsList.getModel();
         for (int i = 0; i < lm.getSize(); i++) {
            Object v = lm.getElementAt(i);
            if (v.equals(name)) {
               m_itemsList.setSelectedIndex(i);
               m_itemsList.ensureIndexIsVisible(i);
               break;
            }
         }
      }
   }

   public String getSelectedLocalName() {
      if (m_itemsList != null) {
         return (String) m_itemsList.getSelectedValue();
      }
      else {
         return (String) m_itemsComboBox.getSelectedItem();
      }
   }

   public String[] getSelectedSubFieldNames() {
      if (m_subField == null) {
         return new String[0];
      }
      String[] s = new String[m_subField.length];
      for (int i = 0; i < s.length; i++) {
         s[i] = (String) m_subFieldCombo[i].getSelectedItem();
      }
      return s;
   }

   public void setSelectedSubFieldNames(String[] names) {
      for (int i = 0; i < names.length; i++) {
         m_subFieldCombo[i].setSelectedItem(names[i]);
      }
   }

   public void clear() {
      if (m_itemsList != null) {
         m_itemsList.setModel(new DefaultListModel());
         m_itemsList.setEnabled(false);
      }
      else {
         m_itemsComboBox.removeAllItems();
         m_itemsComboBox.setEnabled(false);
      }
   }

   public void showLocationNamespace(String absoluteLocationURI, String namespace) {
      try {
         for (int i = 0; i < m_subFieldCombo.length; i++) {
            m_subFieldCombo[i].removeAllItems();
         }

         if (namespace == null) {
            badItemSelection(QNamePanelResources.NO_SCHEMA_FOUND);//WCETODO fix cleanup error.
            return;
         }
         if (m_itemsList != null) {
            m_itemsList.setEnabled(true);
         }
         else {
            m_itemsComboBox.setEnabled(true);
         }

         // record last looked at local name (so that if we later re-select this local-name, it'll still show up):
         if (m_currentLocationAndNamespace != null) {
            m_locationAndNamespaceToLastLocalName.put(m_currentLocationAndNamespace, getSelectedLocalName());
         }

         LocationAndNamespace lan = new LocationAndNamespace(absoluteLocationURI, namespace);
         m_currentLocationAndNamespace = lan;

         String[] names = m_plugin.getLocalNamesFor(uiAgent, absoluteLocationURI, namespace);
         if (names == null) {
            names = new String[0];
         }
         Arrays.sort(names);
         fillItemsList(names);
         String ln = m_locationAndNamespaceToLastLocalName.get(lan);
         int index = ln == null ? -1 : findIndexOfLocalName(names, ln);
         if (index < 0) {
            index = 0; // by default select the first.
         }
         setItemAtIndex(index);
      }
      catch (Exception spe) {
         spe.printStackTrace(System.err);
         String msg = DataIcons.printf(QNamePanelResources.INTERNAL_ERROR_EXCEPTION_MAKING_PREVIEW,
                                       spe.getMessage());

         badItemSelection(msg);
      }
   }

   private void setItemAtIndex(int index) {
      if (m_itemsList != null) {
         if (index < m_itemsList.getModel().getSize()) {
            m_itemsList.setSelectedIndex(index);
            m_itemsList.ensureIndexIsVisible(index);
         }
      }
      else {
         if (index < m_itemsComboBox.getModel().getSize()) {
            m_itemsComboBox.setSelectedIndex(index);
         }
      }
   }

   private void fillItemsList(String[] names) {
      if (m_itemsList != null) {
         DefaultListModel dlm = new DefaultListModel();
         for (int i = 0; i < names.length; i++) {
            dlm.addElement(names[i]);
         }
         m_itemsList.setModel(dlm);
         m_itemsList.clearSelection();
         m_itemsList.setEnabled(true);
      }
      else {
         m_itemsComboBox.removeAllItems();
         for (int i = 0; i < names.length; i++) {
            m_itemsComboBox.addItem(names[i]);
         }
      }
   }

   /**
    * Does a search through the array of local names returning the index where the find name is, or -1 if not found.
    *
    * @param localNames The local name list.
    * @param find       The local name for which the index should be found.
    * @return -1 if not found, the array index otherwise.
    */
   private static int findIndexOfLocalName(String[] localNames, String find) {
      for (int i = 0; i < localNames.length; i++) {
         String ln = localNames[i];
         if (ln.equals(find)) {
            return i;
         }
      }
      return -1; // not found.
   }

   public void refreshPreview() {
      if (m_subField == null) {
         updatePreviewTree();
      }
      else {
         subFieldItemChanged(m_subFieldLabel.length - 1);
      }
   }

   private void updatePreviewTree() {
      String ln = getSelectedLocalName();
      if (ln == null || ln.length() == 0) {
         noParticleSelected();
         return;
      }
      if (m_currentLocationAndNamespace == null) {
         noParticleSelected();
         return;
      }
      ExpandedName ename = ExpandedName.makeName(m_currentLocationAndNamespace.getNamespace(), ln);
      String absLocationURI = m_currentLocationAndNamespace.getLocation();
      if (m_subField == null) {
         setPreview(absLocationURI, ename);
      }
      else {
         setSubField(absLocationURI, ename);
      }
   }

   private void setSubField(String absLocationURI, ExpandedName name) {
      Object object = m_plugin.getObjectFor(uiAgent.getTnsCache(),
                                            absLocationURI, name);
      setSubField(object, 0);
   }

   private void setSubField(Object previousObject, int offset) {
      String[] choices;
      try {
         if (previousObject == null) {
            choices = new String[0];
         }
         else {
            choices = m_subField[offset].getChoicesFor(previousObject);
         }
      }
      catch (SAXException se) {
         //WCETODO handle this
         choices = new String[0];
         se.printStackTrace(System.err);
      }
      m_currentObject[offset] = previousObject;
      m_subFieldCombo[offset].removeAllItems();
      for (int i = 0; i < choices.length; i++) {
         m_subFieldCombo[offset].addItem(choices[i]);
      }
      if (choices.length > 0) {
         m_subFieldCombo[offset].setSelectedIndex(0);
         subFieldItemChanged(offset);
      }
   }

   private void subFieldItemChanged(int offset) {
      String str = (String) m_subFieldCombo[offset].getSelectedItem();
      if (str == null || m_currentObject[offset] == null) {
         if (offset + 1 < m_currentObject.length) {
            setSubField(null, offset + 1);
         }
         else {
            m_owner.setPreview(new JLabel());
         }
         return;
      }
      Object object = m_subField[offset].getObjectFor(m_currentObject[offset], str);

      // Do we have 1 more sub-field?
      if (offset + 1 < m_currentObject.length) {
         // yes
         setSubField(object, offset + 1);
         return;
      }
      // no, show preview:
      JComponent jc;
      try {
         jc = m_subField[offset].createPreviewView(uiAgent, object);
      }
      catch (Exception e) {
         // be robust; these are only displayed in the event of a bug.
         jc = new JLabel(DataIcons.printf(QNamePanelResources.INTERNAL_ERROR_EXCEPTION_MAKING_PREVIEW,
                                          e.getMessage()));
      }
      if (jc == null) {
         jc = new JLabel(DataIcons.printf(QNamePanelResources.INTERNAL_ERROR_NULL_COMPONENT_RETURNED_BY,
                                          m_subField.getClass().getName()));
      }
      m_owner.setPreview(jc);
   }

   /**
    * Sets the preview in the non-sub-field case.
    */
   private void setPreview(String absLocationURI, ExpandedName name) {
      Object object = m_plugin.getObjectFor(uiAgent.getTnsCache(), absLocationURI, name);
      m_owner.setPreviewForObject(object, name);
   }

   public void badItemSelection(String msg) {
      JPanel jp = new JPanel(new BorderLayout());
      jp.add(new JLabel(msg), BorderLayout.NORTH);
      jp.add(new JLabel(""), BorderLayout.CENTER);
      jp.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
      m_owner.setPreview(jp);

      noParticleSelected();
   }

   /**
    * Configures the previewTree for when there is no element (or type) selected.
    */
   private void noParticleSelected() {
      JComponent n = new JLabel();
      n.setOpaque(true);
      n.setBackground(SystemColor.control);
      m_owner.setPreview(n);
   }
}
