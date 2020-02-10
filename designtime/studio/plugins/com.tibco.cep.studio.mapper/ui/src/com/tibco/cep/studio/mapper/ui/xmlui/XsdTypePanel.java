package com.tibco.cep.studio.mapper.ui.xmlui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.nsutils.NamespaceManipulationUtils;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.param.ParameterEditor;
import com.tibco.cep.studio.mapper.ui.data.param.SpecialTypeCategory;
import com.tibco.cep.studio.mapper.ui.data.param.TypeCategory;
import com.tibco.cep.studio.mapper.ui.data.param.TypeRefCategory;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * A xsd:type chooser panel (control).<br>
 * Picks an xsd:type much like a {@link QNamePanel} but gives preference (visually, etc.) to picking XSD primitive types.
 * The api is designed to be as close as possible to {@link QNamePanel}.
 */
public class XsdTypePanel extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JLabel m_typeTypeLabel;
   private JComboBox m_typeTypeCombo = new JComboBox();
   private JPanel m_detailsArea;
   private Object m_currentDetailsObject;
   private JComponent m_currentDetailsComponent;
   private TypeCategory m_currentTypeCategory;
   private boolean m_setting;
   private TypeCategory[] m_typeCategories;
   private String m_contextProjectPathPath;
   private boolean m_namespaceEditable;
   private NamespaceContextRegistry m_namespaceContextRegistry;
   private ImportRegistry m_importRegistry;
   private ChangeListener m_changeListener = new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
         detailsChanged();
      }
   };
   private ExpandedName[] m_suggestions;
   private UIAgent uiAgent;
   
   protected ActionListener mInternalListeners = new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
         action(ae);
      }
   };

   /**
    * The java-bean property that can be used to listen for changes in this control.
    */
   public static final String VALUE_PROPERTY = "value";

   public XsdTypePanel(UIAgent uiAgent) {
      this(uiAgent, null, null, ParameterEditor.BUILT_IN_TYPES);
   }

   public XsdTypePanel(UIAgent uiAgent, NamespaceContextRegistry ni, ImportRegistry ir) {
      this(uiAgent, ni, ir, ParameterEditor.BUILT_IN_TYPES);
   }

   public XsdTypePanel(UIAgent uiAgent,
                       NamespaceContextRegistry ni, ImportRegistry ir, TypeCategory[] typeCats) {
      super(new BorderLayout());
      this.uiAgent = uiAgent;

      m_typeCategories = typeCats;
      m_namespaceContextRegistry = ni;
      m_importRegistry = ir;

      fillInTypeCategories();

      JPanel nameAndCard = new JPanel(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy++;
      m_typeTypeLabel = new JLabel(DataIcons.getTypeLabel() + ":   ");
      nameAndCard.add(m_typeTypeLabel, gbc);
      gbc.gridx = 1;
      nameAndCard.add(m_typeTypeCombo, gbc);

      gbc.gridy++;
      gbc.weightx = 1;
      gbc.gridx = 2;
      nameAndCard.add(new JLabel(), gbc); // spacer

      setBorder(BorderFactory.createEmptyBorder(0, 6, 5, 0));
      JPanel topPanel = new JPanel(new BorderLayout());
      topPanel.add(nameAndCard, BorderLayout.NORTH);
      add(topPanel, BorderLayout.NORTH);

      JPanel catAndType = new JPanel(new BorderLayout());
      m_detailsArea = new JPanel(new BorderLayout());
      catAndType.add(new JSeparator(), BorderLayout.NORTH);
      catAndType.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
      catAndType.add(m_detailsArea, BorderLayout.CENTER);

      add(catAndType, BorderLayout.CENTER);

      // initialize w/ default.
      setExpandedName(ExpandedName.makeName(XSDL.NAMESPACE, "string"));
   }

   /**
    * Sets the import context for this qname pick.<br>
    * This is <b>mandatory</b> before doing any other calls such as {@link #setExpandedName}.
    * This may be called with different values, i.e. for forms recycling.
    *
    * @param importRegistry The import registry.
    */
   public void setImportRegistry(ImportRegistry importRegistry) {
      if (importRegistry == null) {
         throw new NullPointerException("Null import registry");
      }
      if (m_importRegistry != importRegistry) {
         m_importRegistry = importRegistry;
      }
   }

   public ImportRegistry getImportRegistry() {
      return m_importRegistry;
   }

   public void setContextProjectRelativePath(String path) {
      m_contextProjectPathPath = path;
   }

   public String getContextProjectRelativePath() {
      return m_contextProjectPathPath;
   }

   /**
    * Sets the namespace context for this qname pick.<br>
    * This is <b>mandatory</b> before doing any other calls such as {@link #setExpandedName}.
    * This may be called with different values, i.e. for forms recycling.
    *
    * @param namespaceContextRegistry The import registry.
    */
   public void setNamespaceImporter(NamespaceContextRegistry namespaceContextRegistry) {
      if (namespaceContextRegistry == null) {
         throw new NullPointerException("Null namespace importer");
      }
      if (m_namespaceContextRegistry != namespaceContextRegistry) {
         m_namespaceContextRegistry = namespaceContextRegistry;
      }
   }

   public NamespaceContextRegistry getNamespaceImporter() {
      return m_namespaceContextRegistry;
   }

   /**
    * Sets the value of the type (by expanded name).<br>
    * For a qname version, see {@link #setQName}.
    *
    * @param expandedName The expanded name.
    */
   public void setExpandedName(ExpandedName expandedName) {
      m_setting = true;
      setExpandedNameInternal(expandedName);
      m_setting = false;
   }

   /**
    * Gets the current value of the type (by expanded name).<br>
    * For a qname version, see {@link #getQName}.
    *
    * @return The name, never null.
    */
   public ExpandedName getExpandedName() {
      ExpandedName ename = m_currentTypeCategory.getTypeExpandedName(m_currentDetailsObject);
      return ename;
   }

   /**
    * Sets the qname.<br>
    * The {@link ExpandedName} is computed from the qname using the {@link NamespaceContextRegistry}.
    * It will not crash'n'burn (i.e. throw exceptions, etc.) if the prefix doesn't exist,
    * but the exact behavior is 'private' to the control.
    *
    * @param qname The new qname, cannot be null.
    */
   public void setQName(QName qname) {
      String ns;
      try {
         ns = m_namespaceContextRegistry.getNamespaceURIForPrefix(qname.getPrefix());
      }
      catch (Exception e) {
         // be fault tolerant
         ns = "";
      }
      ExpandedName ename = ExpandedName.makeName(ns, qname.getLocalName());
      setExpandedName(ename);
   }

   /**
    * Returns the qname from the full name and namespace.<br>
    * This is computed from {@link #getExpandedName} and {@link NamespaceContextRegistry} --- if the
    * namespace is not declared at the time of this call, a new declaration will be added using
    * {@link NamespaceContextRegistry#getOrAddPrefixForNamespaceURI} on the namespace.
    */
   public QName getQName() {
      // String location = getResourceLocation();
      // ugly, need to resolve this much much better.
      ExpandedName ename = getExpandedName();
      return NamespaceManipulationUtils.getOrAddQNameFromExpandedName(ename, m_namespaceContextRegistry);
   }

   /**
    * Implementation override - disabled component controls, too.
    */
   public void setEnabled(boolean enabled) {
      if (isEnabled() == enabled) {
         return;
      }
      super.setEnabled(enabled);
      m_typeTypeLabel.setEnabled(enabled);
      m_typeTypeCombo.setEnabled(enabled);
      if (m_currentDetailsComponent != null) {
         m_currentDetailsComponent.setEnabled(enabled);
      }
   }

   private void setExpandedNameInternal(ExpandedName expandedName) {
      if (expandedName == null) {
         // do nothing for now.
         return;
      }
      String ns = expandedName.getNamespaceURI();
      if (XSDL.NAMESPACE.equals(ns)) {
         selectXsdType(expandedName.getLocalName());
      }
      else {
         selectRef(expandedName);
      }
   }

   private void selectRef(ExpandedName name) {
      boolean found = false;
      for (int i = 0; i < m_typeCategories.length && !found; i++) {
         TypeCategory tc = m_typeCategories[i];
         if (tc == SpecialTypeCategory.INSTANCE) {
            String ln = name.getLocalName();
            String[] tt = tc.getXsdTypes();
            for (int t = 0; t < tt.length && !found; t++) {
               if (tt[t].equals(ln)) {
                  m_typeTypeCombo.setSelectedIndex(i);
                  m_currentDetailsObject = tc.readRefDetails(name);
                  m_currentTypeCategory = tc;
                  refreshDetailsPanel();
                  found = true;
               }
            }
         }
      }

      if (!found) {
         for (int i = 0; i < m_typeCategories.length; i++) {
            TypeCategory tc = m_typeCategories[i];
            if (tc == TypeRefCategory.INSTANCE) {
               m_typeTypeCombo.setSelectedIndex(i);
               m_currentTypeCategory = tc;
               m_currentDetailsObject = tc.readRefDetails(name);
               refreshDetailsPanel();
            }
         }
      }
   }

   private void selectXsdType(String ln) {
      for (int i = 0; i < m_typeCategories.length; i++) {
         TypeCategory tc = m_typeCategories[i];
         String[] tt = tc.getXsdTypes();
         for (int t = 0; t < tt.length; t++) {
            if (tt[t].equals(ln)) {
               m_typeTypeCombo.setSelectedIndex(i);
               m_currentDetailsObject = tc.readDetails(ln);
               m_currentTypeCategory = tc;
               refreshDetailsPanel();
               return;
            }
         }
      }
      TypeCategory def = m_typeCategories[0];
      m_currentDetailsObject = def.createDetails();
      m_currentTypeCategory = def;
      m_typeTypeCombo.setSelectedIndex(0);
      refreshDetailsPanel();
   }

   static class TypeTypeRenderer extends DefaultListCellRenderer {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean sel, boolean foc) {
         // On Unix, a null value is passed when schema editor
         // is in initial empty state;
         // to avoid an exception, return immediately if value is null.
         if (value == null) {
            return this;
         }
         TypeCategory cat = (TypeCategory) value;
         String name = cat.getDisplayName();
         super.getListCellRendererComponent(list, name, index, sel, foc);
         setIcon(cat.getDisplayIcon());
         return this;
      }
   }

   private void fillInTypeCategories() {
      if (m_typeCategories == null) {
         return;
      }
      m_typeTypeCombo.removeAllItems();

      for (int i = 0; i < m_typeCategories.length; i++) {
         m_typeTypeCombo.addItem(m_typeCategories[i]);
      }
      m_typeTypeCombo.setRenderer(new TypeTypeRenderer());
      m_typeTypeCombo.addActionListener(mInternalListeners);
      m_typeTypeCombo.setMaximumRowCount(m_typeCategories.length + 2); //?
      m_typeTypeCombo.setLightWeightPopupEnabled(false); // only use these otherwise can go off window...
   }

   public void setCategories(TypeCategory[] typeCats) {
      m_typeCategories = typeCats;
      fillInTypeCategories();
   }

   public void setNamespaceEditable(boolean val) {
      m_namespaceEditable = val;
   }

   private void action(ActionEvent ae) {
      Object src = ae.getSource();
      if (src == m_typeTypeCombo) {
         if (!m_setting) {
            typeTypeChanged();
         }
      }
   }

   private void typeTypeChanged() {
      TypeCategory tc = (TypeCategory) m_typeTypeCombo.getSelectedItem();
      if (tc != null) {
         if (tc == m_currentTypeCategory) {
            return;
         }
      }
      else {
         return;
      }
      m_currentDetailsObject = tc.createDetails();
      m_currentTypeCategory = tc;
      refreshDetailsPanel();
      detailsChanged();
   }

   private void refreshDetailsPanel() {
      JComponent det = m_currentTypeCategory.createEditor(m_changeListener, m_currentDetailsObject, uiAgent,
                                                          m_namespaceContextRegistry, m_importRegistry);
      if (det instanceof QNamePanel) // hack, for ref category.
      {
         QNamePanel qp = (QNamePanel) det;
         qp.setNamespaceEditable(m_namespaceEditable);
         qp.setSuggestions(m_suggestions);
         qp.setContextProjectRelativePath(m_contextProjectPathPath);
      }
      m_currentDetailsComponent = det;
      if (m_currentDetailsComponent == null) {
         m_currentDetailsComponent = new JLabel(); // can happen.
      }
      m_currentDetailsComponent.setEnabled(isEnabled());
      m_detailsArea.removeAll();
      m_detailsArea.add(m_currentDetailsComponent);
      m_detailsArea.revalidate();
      m_detailsArea.repaint();
   }

   /**
    * Sets the suggestions, or null for don't display suggestion options, for the type substitutions.
    *
    * @param suggestions A set of suggested substitutions, or null to indicate don't display suggestions (the default)
    */
   public void setSuggestions(ExpandedName[] suggestions) {
      m_suggestions = suggestions;
      refreshDetailsPanel();
   }

   public ExpandedName[] getSuggestions() {
      return m_suggestions;
   }

   private void detailsChanged() {
      m_currentDetailsObject = m_currentTypeCategory.getEditorValue(m_currentDetailsComponent);
      super.firePropertyChange(VALUE_PROPERTY, null, getExpandedName());
   }
}
