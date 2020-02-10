package com.tibco.cep.studio.mapper.ui.xmlui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.share.util.Path;
import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.nsutils.NamespaceManipulationUtils;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.utils.ErrorCheckingComboBox;
import com.tibco.cep.studio.mapper.ui.data.utils.ErrorCheckingComboBoxChecker;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.helpers.NoNamespace;

/**
 * A control for choosing XML resources (QNames).<br>
 * Change notifications are issued through the property change {@link #VALUE_PROPERTY}.
 * See {@link QNameFormField} for the form-field-friendly wrapper of this.
 */
public class QNamePanel extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel m_body;
   private JLabel m_namespaceLabel;
   private QNamePanelNamespaceField m_namespaceField;
   private JLabel m_localNameLabel;
   private ErrorCheckingComboBox m_localNameField;
   private String m_currentSelectedNamespace;
   private boolean m_isSetting; // true when setting local name field (so listener can ignore)
   private QNameSubControl m_subField; // may be null, if there's a dependent field of this.
   private QNamePanelTargetNamespaceNames m_targetNamespaceNames;

   private String m_contextProjectRelativePath;

   private boolean m_errorChecking = true; // is error checking on, true by default.

   /**
    * A 'hidden' form used to add the namespace/location/localnames.
    */
   private NamespaceContextRegistry m_namespaceContextRegistry;
   private ImportRegistry m_importRegistry;
   private UIAgent uiAgent;
   private QNamePlugin m_qnamePlugin;

   /**
    * A context (externally) set list of suggestions.  If set (non-null), these are displayed as a combo box.
    * (There is a special entry for 'not on list')
    */
   private ExpandedName[] m_suggestions;
   private JPanel m_suggestionsPanel; // For suggestions, null if not in use.
   private JComboBox m_suggestionsCombo; // For suggestions, null if not in use.
   private JLabel m_suggestionsLabel; // For suggestions, null if not in use.

   private HashSet<String> m_currentValidLocalNames = new HashSet<String>();
   private HashSet<String> m_currentAmbiguousLocalNames = new HashSet<String>();

   //ssi: the selected AE m_resource
//   private AEResource m_resource;

   /**
    * The java-bean property that can be used to listen for changes in this control.
    */
   public static final String VALUE_PROPERTY = "value";

   public static final String PATH_SEPARATOR = "/";

   /**
    * Constructor, with this constructor, though {@link #setNamespaceImporter} and {@link #setImportRegistry}
    * must be called before actual use.
    *
    * @param doc    The DesignerDocument
    * @param plugin The plugin for the specific type of qnamed thing being picked, i.e. element, port, etc.
    */
   public QNamePanel(UIAgent uiAgent, QNamePlugin plugin) {
      super(new BorderLayout());
      JPanel body = new JPanel(new GridBagLayout());
      m_body = body;
      this.uiAgent = uiAgent;
      m_qnamePlugin = plugin;
      m_namespaceField = new QNamePanelNamespaceField(this);

      m_localNameField = new ErrorCheckingComboBox();
      m_localNameField.setErrorChecker(new ErrorCheckingComboBoxChecker() {
         public boolean isValid(String name) {
            if (!m_errorChecking) {
               return true; // always valid if not error checking.
            }
            return m_currentValidLocalNames.contains(name) && !m_currentAmbiguousLocalNames.contains(name);
         }
      });
      m_localNameField.setEditable(true);

      m_localNameLabel = new JLabel("<ln placeholder>"); // This name is never actually displayed (unless there's a bug).
      m_namespaceLabel = new JLabel("<ns placeholder>"); // ditto.

      m_localNameField.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if (!m_isSetting) {
               reloadSubFieldOptions();
               fieldChanged();
            }
         }
      });
      JTextField jtf = (JTextField) m_localNameField.getEditor().getEditorComponent();
      jtf.getDocument().addDocumentListener(new DocumentListener() {
         public void changedUpdate(DocumentEvent e) {
            fieldChanged();
         }

         public void insertUpdate(DocumentEvent e) {
            fieldChanged();
         }

         public void removeUpdate(DocumentEvent e) {
            fieldChanged();
         }
      });

      rebuildBody();
      add(m_body, BorderLayout.CENTER); // add to larger panel so 'suggestions' can be added later (if used)

      resetForQNameType();
   }

   /**
    * Constructor, with this constructor {@link #setNamespaceImporter} and {@link #setImportRegistry}
    * are called as a shortcut.
    *
    * @param doc    The DesignerDocument
    * @param plugin The plugin for the specific type of qnamed thing being picked, i.e. element, port, etc.
    */
   public QNamePanel(UIAgent uiAgent, QNamePlugin plugin,
                     NamespaceContextRegistry ni, ImportRegistry imports) {
      this(uiAgent, plugin);
      setNamespaceImporter(ni);
      setImportRegistry(imports);
   }

   /**
    * Implementation override, forwards enabled to sub-fields.
    */
   public void setEnabled(boolean state) {
      super.setEnabled(state);
      m_localNameField.setEnabled(state);
      m_namespaceField.setEnabled(state);
      m_localNameField.setEditable(state);
      m_localNameLabel.setEnabled(state);
      m_namespaceLabel.setEnabled(state);
      if (m_suggestionsCombo != null) {
         m_suggestionsCombo.setEnabled(state);
         m_suggestionsLabel.setEnabled(state);
      }
   }

   /**
    * Sets if the labels for the namespace/local-name should be shown.<br>
    * By default, these are on.
    */
   public void setShowLabels(boolean showLabels) {
      m_localNameLabel.setVisible(showLabels);
      m_namespaceLabel.setVisible(showLabels);
   }

   public boolean getShowLabels() {
      return m_localNameLabel.isVisible();
   }

   /**
    * Sets if {@link NoNamespace} is a legal choice.<br>
    * By default, this is false.
    */
   public void setAllowsNoNamespace(boolean allowsNoNamespace) {
      m_namespaceField.setAllowsNoNamespace(allowsNoNamespace);
   }

   public boolean getAllowsNoNamespace() {
      return m_namespaceField.getAllowsNoNamespace();
   }

   /**
    * Sets if error checking (i.e. red underlines) is enabled or not for this usage.<br>
    * By default this is <code>true</code>.
    */
   public void setErrorChecking(boolean enabled) {
      if (enabled != m_errorChecking) {
         repaint();
         m_errorChecking = enabled;
      }
   }

   public boolean getErrorChecking() {
      return m_errorChecking;
   }

   /**
    * Sets if the namespace field allows inline editing (as opposed to only choosing).<br>
    * Default is false.
    *
    * @param namespaceEditable
    */
   public void setNamespaceEditable(boolean namespaceEditable) {
      m_namespaceField.setEditable(true);
   }

   public boolean isNamespaceEditable() {
      return m_namespaceField.getEditable();
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
         m_namespaceField.setImportRegistry(importRegistry);
      }
   }


   /**
    * If you want the import locations to be relative paths, then provide the
    * project relative path here, which the locations should be relative to...
    * WCETODO this is problematic, also, in that 'project relative' is undefined without a sense of 'current project'
    * which is not really established.  Should use just a absolute-URL and that's that; maybe in the next release
    * when this is all fixed (right!), just deprecate & rename to setContextURI().
    *
    * @param path The project relative path.
    */
   public void setContextProjectRelativePath(String path) {
      m_contextProjectRelativePath = path;
   }

   public String getContextProjectRelativePath() {
      return m_contextProjectRelativePath;
   }

   public ImportRegistry getImportRegistry() {
      return m_importRegistry;
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

   /**
    * Set this if this panel is being used in a context where there is a 'this' namespace;
    * it will display the 'this' namespace specially and allow the local-name options.<br>
    * The call {@link #setTargetNamespaceNames} may be used in conjunction with this in order
    * to 'override' the local-names found in the context namespace from the {@link QNamePlugin}.<br>
    * Also see
    *
    * @param ns The context namespace, or null for none.  By default this is null.
    */
   public void setTargetNamespace(String ns) {
      m_namespaceField.setTargetNamespace(ns);
   }

   /**
    * Gets the context namespace.
    *
    * @return The context namespace, or null, if none.
    */
   public String getTargetNamespace() {
      return m_namespaceField.getTargetNamespace();
   }

   /**
    * Sets the provider which provides the local names for the {@link #getTargetNamespace} namespace.<br>
    * When set, this allows the local-name options in the current namespace to be provided separately from
    * the {@link QNamePlugin} which will usually be working off of an index cache.<br>
    * {@link #setTargetNamespace} must also be set for this to have any effect.
    *
    * @param provider The provider, or null for none.
    */
   public void setTargetNamespaceNames(QNamePanelTargetNamespaceNames provider) {
      m_targetNamespaceNames = provider;
      m_currentSelectedNamespace = null;
      reloadLocalNameOptions();
   }

   /**
    * Gets the current context namespace provider.
    *
    * @return The provider or null, default is null.
    */
   public QNamePanelTargetNamespaceNames getTargetNamespaceNames() {
      return m_targetNamespaceNames;
   }

   private void resetForQNameType() {
      String localNameLabel = m_qnamePlugin.getLocalNameLabel();
      if (localNameLabel == null || localNameLabel.length() == 0) // Allow defaults.
      {
         localNameLabel = DataIcons.getLocalNameLabel();
      }
      m_localNameLabel.setText(colonize(localNameLabel));

      String namespaceLabelName = m_qnamePlugin.getNamespaceLabel();
      if (namespaceLabelName == null || namespaceLabelName.length() == 0) // allow defaults.
      {
         namespaceLabelName = DataIcons.getNamespaceLabel();
      }
      m_namespaceLabel.setText(colonize(namespaceLabelName));
   }

   /**
    * Adds a colon to the end if there isn't one already (should be standard fn. somewhere)
    */
   private static String colonize(String string) {
      if (!string.endsWith(":")) {
         return string + ":";
      }
      return string;
   }

   public NamespaceContextRegistry getNamespaceImporter() {
      return m_namespaceContextRegistry;
   }


   public void browse() {
      checkNsAndImport();
      ArrayList<QNamePluginSubField> subFields = new ArrayList<QNamePluginSubField>();
      QNameSubControl sf = m_subField;
      while (sf != null) {
         subFields.add(sf.getPlugin());
         sf = sf.getChainedSubControl();
      }
      QNamePluginSubField[] sfa = subFields.toArray(new QNamePluginSubField[0]);
//      SchemaTool st = (SchemaTool) m_designerDocument.getTool(SchemaTool.NAME);
//      if (st != null) {
//         st.makeReady(); // make sure this is called from a swing thread.
//      }
      XMLChooser chooser = new XMLChooser(uiAgent, m_qnamePlugin, sfa, m_contextProjectRelativePath, m_importRegistry);

      ExpandedName name = getExpandedName();
      chooser.setSelectedName(name);
      ArrayList<String> subFieldVals = new ArrayList<String>();
      QNameSubControl at = m_subField;
      while (at != null) {
         subFieldVals.add(at.getSelectedName());
         at = at.getChainedSubControl();
      }
      chooser.setSelectedSubFieldNames(subFieldVals.toArray(new String[0]));
      chooser.setVisible(true);

      if (chooser.isOK())
      {
          String location = chooser.getSelectedResourceLocation();
          String ns = chooser.getSelectedNamespace();

          if (ns!=null)
          {
              // Add the import.
              String actualNs = QNamePanelImportQueryDialog.addImport(
                      uiAgent,
                      m_qnamePlugin,
                      this,
                      ns,
                      m_contextProjectRelativePath,
                      location,
                      m_importRegistry);
              if (actualNs==null)
              {
                  // aborted.
                  return;
              }
              // Add the namespace (if not already there)
              m_namespaceContextRegistry.getOrAddPrefixForNamespaceURI(actualNs);
              ns = actualNs;

              // Imports changed, so refresh this.
              reloadLocalNameOptions(true); // true -> force the refresh, even if actual name didn't change.
          }

          String ln = chooser.getSelectedLocalName();
          if (ln==null)
          {
              ln = "";
          }
          setExpandedName(ExpandedName.makeName(ns,ln));
          if (m_subField!=null)
          {
              String[] selSubFields = chooser.getSelectedSubFieldNames();
              QNameSubControl at2 = m_subField;
              for (int i=0;i<selSubFields.length;i++)
              {
                  String oldName = at2.getSelectedName();
                  String newName = selSubFields[i];
                  if (oldName==null || !oldName.equals(newName))
                  {
                      at2.setSelectedName(selSubFields[i]);
                      at2.fireChange();
                  }
                  at2 = at2.getChainedSubControl();
              }
          }
          fieldChanged();
      }
   }

   public void clear() {
      setExpandedName(ExpandedName.makeName(""));
      fieldChanged();
   }

   public void goTo() {
	   String loc = QNamePluginSupport.getLocationForName(uiAgent, m_qnamePlugin, m_contextProjectRelativePath, m_importRegistry, getExpandedName());
	   if (loc != null) {
		   try {
			   uiAgent.openResource(loc);
		   } catch (Exception e) {
			   // Do a print-out here to scold those who crash, but otherwise eat it; it's not important.
			   e.printStackTrace(System.err);
		   }
	   }
   }

   private void fieldChanged() {
      super.firePropertyChange(VALUE_PROPERTY, null, getExpandedName());
   }

   /**
    * Sets the qname.<br>
    * The {@link ExpandedName} is computed from the qname using the {@link #setNamespaceImporter}.
    * It will not crash'n'burn (i.e. throw exceptions, etc.) if the prefix doesn't exist,
    * but the exact behavior is 'private' to the control.
    *
    * @param qn The new qname, cannot be null.
    */
   public void setQName(QName qn) {
      String ns;
      try {
         ns = m_namespaceContextRegistry.getNamespaceURIForPrefix(qn.getPrefix());
      }
      catch (Exception e) {
         // be fault (and racially, etc.) tolerant
         ns = "";
      }
      ExpandedName ename = ExpandedName.makeName(ns, qn.getLocalName());
      setExpandedName(ename);
   }

   /**
    * Returns the qname from the full name and namespace.<br>
    * This is computed from {@link #getExpandedName} and {@link #setNamespaceImporter} --- if the
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
    * Gets the current value of the control.<br>
    * {@link #getQName} is related, however, this method is the master.
    *
    * @return The name, never null.
    */
   public ExpandedName getExpandedName() {
      String ns = getNamespaceInternal();
      if (ns != null && ns.length() == 0) {
         ns = NoNamespace.URI; // this is ugly. Make it empty string & be done with it!
      }
      return ExpandedName.makeName(ns, getLocalNameInternal());
   }

   /**
    * Sets the current value for the control.<br>
    * Note that both {@link #setNamespaceImporter} and {@link #setImportRegistry} must have been called prior to
    * calling this.
    *
    * @param ename The name, if null then treated as no namespace, empty local name
    */
   public void setExpandedName(ExpandedName ename) {
      if (ename == null) {
         ename = ExpandedName.makeName("");
      }
      checkNsAndImport();
      String ns = ename.getNamespaceURI();
      if (ns == null) {
         ns = "";
      }
      setNamespaceInternal(ns);
      setLocalNameInternal(ename.getLocalName());
      reloadLocalNameOptions();
      reloadSubFieldOptions();
      selectMatchedSuggestion();
   }

   /**
    * Returns the string in the textfield
    *
    * @return the text field value
    * @deprecated Use {@link #getQName} or {@link #getExpandedName} only.
    */
   public String getLocalName() {
      return getLocalNameInternal();
   }

   private String getLocalNameInternal() {
      return getComboField().getText();
   }

   /**
    * Sets the text field value
    *
    * @param name the new value
    * @deprecated Use {@link #setQName} or {@link #setExpandedName} only.
    */
   public void setLocalName(String name) {
      setLocalNameInternal(name);
   }

   public void setLocalNameInternal(String name) {
      checkNsAndImport();
      if (name == null) {
         name = "";
      }
      m_isSetting = true;
      try {
         // Must do this because JComboBox falls on its nose if there are no selected items (loses name)
         getComboField().setText(name);
         // attempt to select an existing choice:
         m_localNameField.setSelectedItem(name);
      }
      finally {
         m_isSetting = false;
      }
   }

   private JTextField getComboField() {
      return (JTextField) m_localNameField.getEditor().getEditorComponent();
   }

   /**
    * @deprecated Doesn't work.
    */
   public String getResourceLocation() {
      return "";//(String) locationField.getValue();
   }

   /**
    * Sets the location
    *
    * @deprecated Does not work; does nothing.
    */
   public void setResourceLocation(String name) {
   }

   /**
    * Sets the xml value
    *
    * @param namespace The new namespace value.
    * @deprecated Use {@link #setExpandedName} or {@link #setQName} only.
    */
   public void setNamespace(String namespace) {
      setNamespaceInternal(namespace);
   }

   private void setNamespaceInternal(String namespace) {
      checkNsAndImport();
      if (namespace == null) {
         namespace = "";
      }
      m_namespaceField.setNamespace(namespace);
   }

   /**
    * @deprecated Use {@link #getExpandedName} or {@link #getQName} only.
    */
   public String getNamespace() {
      return m_namespaceField.getNamespace();
   }

   private String getNamespaceInternal() {
      return m_namespaceField.getNamespace();
   }

   /**
    * Changes the type of picking to be done (i.e. for Xsd TYPE or Ws Part, or whatever)
    *
    * @param plugin The plugin to fill in the details about what is to be picked.
    */
   public void setQNameType(QNamePlugin plugin) {
      if (plugin == null) {
         throw new NullPointerException();
      }
      m_qnamePlugin = plugin;
      resetForQNameType();
   }

   public QNamePlugin getQNameType() {
      return m_qnamePlugin;
   }

   public void setSuggestions(ExpandedName[] suggestions) {
      m_suggestions = suggestions;
      if (suggestions == null) {
         if (m_suggestionsPanel != null) {
            remove(m_suggestionsPanel);
            revalidate();
            m_suggestionsPanel = null;
            m_suggestionsCombo = null;
         }
      }
      else {
         m_suggestionsPanel = new JPanel(new BorderLayout());
         m_suggestionsCombo = new JComboBox();
         m_suggestionsLabel = new JLabel(QNamePanelResources.SUGGESTED + ":  ");
         m_suggestionsPanel.add(m_suggestionsLabel, BorderLayout.WEST);
         m_suggestionsPanel.add(m_suggestionsCombo);
         JPanel sepArea = new JPanel(new BorderLayout());
         sepArea.add(new JSeparator());
         sepArea.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
         m_suggestionsPanel.add(sepArea, BorderLayout.SOUTH);
         m_suggestionsCombo.addItem("(" + QNamePanelResources.OTHER + ")");
         for (int i = 0; i < suggestions.length; i++) {
            ExpandedName en = suggestions[i];
            String ln = en.getLocalName();
            m_suggestionsCombo.addItem(ln);
         }
         m_suggestionsCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int sel = m_suggestionsCombo.getSelectedIndex();
               if (sel <= 0) {
                  // leave it.
                  return;
               }
               ExpandedName ename = m_suggestions[sel - 1];
               setExpandedName(ename);
            }
         });
         m_suggestionsCombo.setEnabled(isEnabled());
         m_suggestionsLabel.setEnabled(isEnabled());
         add(m_suggestionsPanel, BorderLayout.NORTH);
         revalidate();
         selectMatchedSuggestion();
      }
   }

   /**
    * Callback from {@link QNamePanelNamespaceField}
    */
   void namespaceChanged() {
      if (reloadLocalNameOptions()) {
         // changed.
         fieldChanged();
      }
   }

   private void selectMatchedSuggestion() {
      if (m_suggestions == null) {
         return; // n/a
      }
      ExpandedName ename = getExpandedName();
      for (int i = 0; i < m_suggestions.length; i++) {
         if (m_suggestions[i].equals(ename)) {
            m_suggestionsCombo.setSelectedIndex(i + 1); // +1 for (other)
         }
      }
   }

   public ExpandedName[] getSuggestions() {
      return m_suggestions;
   }

   /**
    * Reloads the set of local names which are chooseable.
    *
    * @return True if it actually updated.
    */
   private boolean reloadLocalNameOptions() {
      return reloadLocalNameOptions(false);
   }

   /**
    * Reloads the set of local names which are chooseable.
    *
    * @param forceUpdate If set, will reload even if no qname change detected; important for when import list changes, for example.
    * @return True if it actually updated.
    */
   private boolean reloadLocalNameOptions(boolean forceUpdate) {
      String ns = getNamespaceInternal();
      if (!forceUpdate && m_currentSelectedNamespace != null && m_currentSelectedNamespace.equals(ns)) {
         // no change.
         return false;
      }
      m_isSetting = true;
      m_currentSelectedNamespace = ns;
      String currentLocalName = getLocalNameInternal();
      m_localNameField.removeAllItems();
      try {
         String[] ln = getAllValidLocalNames();
         // This is our job:
         Arrays.sort(ln);
         m_currentValidLocalNames.clear();

         // Now add 'em all:
         ComboBoxModel newmodel = new DefaultComboBoxModel(ln); // much quicker to add them like this than individually.
         m_localNameField.setModel(newmodel);
         for (int i = 0; i < ln.length; i++) {
            m_currentValidLocalNames.add(ln[i]);
         }

         m_currentAmbiguousLocalNames.clear();
         String[] ambig = getAllAmbiguousLocalNames();
         for (int i = 0; i < ambig.length; i++) {
            m_currentAmbiguousLocalNames.add(ambig[i]);
         }
      }
      catch (SAXException e) {
         // eat this exception; can't do anything meaningful, so do nothing:-)
      }
      // Restore the local name (which gets clobbered somehow in the rebuilding above)
      setLocalNameInternal(currentLocalName);
      m_isSetting = false;
      return true;
   }

   /**
    * Gets all the valid local names given the current namespace & import list, including ambiguous ones.
    *
    * @return The valid local names.
    * @throws SAXException For exceptions looking up names.
    */
   private String[] getAllValidLocalNames() throws SAXException {
      String namespace = m_currentSelectedNamespace;
      // Check if we're on the context namespace:
      if (namespace != null && namespace.equals(m_namespaceField.getTargetNamespace()) && m_targetNamespaceNames != null) {
         String[] ln = m_targetNamespaceNames.getLocalNames();
         if (ln == null) {
            ln = new String[0];
         }
         return ln;
      }
      else {
         return QNamePluginSupport.getAllValidLocalNames(uiAgent,
                                                         m_qnamePlugin,
                                                         namespace,
                                                         m_contextProjectRelativePath,
                                                         m_importRegistry);
      }
   }

   /**
    * Gets all the ambiguous local names given the current namespace & import list (so if there's not more than 1 import, can't be ambiguous).
    *
    * @return The valid local names.
    * @throws SAXException For exceptions looking up names.
    */
   private String[] getAllAmbiguousLocalNames() throws SAXException {
      String namespace = m_currentSelectedNamespace;
      // Check if we're on the context namespace:
      if (namespace != null && namespace.equals(m_namespaceField.getTargetNamespace()) && m_targetNamespaceNames != null) {
         return new String[0];
      }
      else {
         return QNamePluginSupport.getAllAmbiguousLocalNames(uiAgent, m_qnamePlugin, namespace, m_contextProjectRelativePath, m_importRegistry);
      }
   }

   void setSubControl(QNameSubControl subField) {
      if (m_subField != null) {
         throw new IllegalStateException("Attempting to set a second sub-controls; only 1 allowed");
      }
      m_subField = subField;
   }

   private void reloadSubFieldOptions() {
      if (m_subField != null) {
         Object obj = getSelectedObject();
         m_subField.reloadOptions(obj);
      }
   }

   /**
    * Gets the selected object. Returns null if not found or if ambiguous.
    */
   private Object getSelectedObject() {
      return QNamePluginSupport.getQNameObject(m_qnamePlugin,
    		  uiAgent,
                                               m_contextProjectRelativePath,
                                               m_importRegistry, getExpandedName());
   }

   private void checkNsAndImport() {
      if (m_importRegistry == null) {
         throw new NullPointerException("Import registry not initialized!");
      }
      if (m_namespaceContextRegistry == null) {
         throw new NullPointerException("Namespace importer not initialized!");
      }
   }

   private void rebuildBody() {
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.weightx = 0;
      gbc.insets = new Insets(2, 2, 2, 2);
      gbc.weighty = 0;
      gbc.fill = GridBagConstraints.BOTH;

      m_body.add(m_namespaceLabel, gbc);
      gbc.gridx = 1;
      gbc.weightx = 1;
      m_body.add(m_namespaceField, gbc);
      gbc.gridy++;
      gbc.gridx = 0;
      gbc.weightx = 0;
      m_body.add(m_localNameLabel, gbc);
      gbc.gridx++;
      gbc.weightx = 1;
      m_body.add(m_localNameField, gbc);

      gbc.gridy++;
      gbc.weighty = 1;
      m_body.add(new JLabel(), gbc); // spacer.

      m_body.revalidate();
   }

   /**
    * Converts a project relative path to a m_resource relative path.<br>
    * Currently public just for re-use inside migration; find a better home for this later.
    *
    * @param projectRelativePath The project relative path that we wish to convert. Starts
    *                            with a pathSeparator, and ends with a fileName.
    * @param relativeToPath      Path to the m_resource that the returned path is relative to. Note
    *                            that this path must also be project relative.
    * @return - The m_resource relative path.
    */
   public static String getResourceRelativePath(String projectRelativePath, String relativeToPath) {

      // turn the strings into Path objects... The assumption is that both paths
      // are projectRelative so they start with a PathSeparator, and they also end
      // with a fileName...
      Path projectRelative = new Path(projectRelativePath, PATH_SEPARATOR);
      Path relativeTo = new Path(relativeToPath, PATH_SEPARATOR);

      // both paths are projectRelative but we need to change the 1st to be relative to the 2nd.
      // I.E. Starting in the relativeTo directory, navigate to find the projectRelative file.
      // We can throw out that part of the path that is the same in both... So lets find the
      // first directory that is different in the two paths...
      int count = Math.min(projectRelative.size(), relativeTo.size());
      int index;
      for (index = 0; index < count; index++) {
         if (!projectRelative.nameAt(index).equals(relativeTo.nameAt(index))) {
            break;
         }
      }
      int firstDiff = index;

      // if the relativeTo path is deeper into the hierarchy then we need to
      // backup from that there... to the highest point where the two paths cross...
      int backup = relativeTo.size() - firstDiff - 1;
      Path resourceRelative = new Path();
      for (int k = 0; k < backup; k++) {
         resourceRelative.grow("..");
      }

      // From here we append that part of the path that was different.
      resourceRelative = resourceRelative.append(projectRelative.subPath(firstDiff));

      // convert the resourceRelative path to a string and return...
      String resultPath = resourceRelative.pathStr(PATH_SEPARATOR.charAt(0));
      return resultPath;
   }

   public UIAgent getUIAgent() {
	   return uiAgent;
   }
}
