package com.tibco.cep.studio.mapper.ui.xmlui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashSet;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.nsutils.ImportRegistryEntry;
import com.tibco.cep.mapper.xml.nsutils.ImportRegistrySupport;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.utils.BetterJDialog;
import com.tibco.cep.studio.mapper.ui.data.utils.OKCancelPanel;
import com.tibco.objectrepo.schema.SchemaRepoUtils;

/**
 * A dialog used by {@link QNamePanel} to ask about updating imports.
 */
public class QNamePanelImportQueryDialog extends BetterJDialog {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public static final int CODE_CANCEL = 0;
   public static final int CODE_REPLACE = 1;
   public static final int CODE_KEEP = 2;
   public static final int CODE_BY_LOCATION = 3;

   private boolean m_okHit;
   private JRadioButton m_keep;
   private JRadioButton m_replace;
   private JRadioButton m_byLocation;

   public QNamePanelImportQueryDialog(UIAgent uiAgent, Frame f) {
      super(uiAgent, f);
      setup();
   }

   public QNamePanelImportQueryDialog(UIAgent uiAgent, Dialog d) {
      super(uiAgent, d);
      setup();
   }

   private void setup() {
      setTitle(QNamePanelResources.IMPORT_CONFLICT_TITLE);
      JLabel label = new JLabel(QNamePanelResources.IMPORT_CONFLICT);
      m_keep = new JRadioButton(QNamePanelResources.KEEP_OLD_IMPORT);
      m_replace = new JRadioButton(QNamePanelResources.REPLACE_OLD_IMPORT);
      m_byLocation = new JRadioButton(QNamePanelResources.REFERENCE_BY_LOCATION);
      m_keep.setSelected(true);
      ButtonGroup bg = new ButtonGroup();
      bg.add(m_keep);
      bg.add(m_replace);
      bg.add(m_byLocation);
      final OKCancelPanel okc = new OKCancelPanel();
      ActionListener al = new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            m_okHit = e.getSource() == okc.getOKButton();
            dispose();
         }
      };
      JPanel panel = new JPanel(new BorderLayout());
      panel.add(label, BorderLayout.NORTH);

      JPanel options = new JPanel(new GridLayout(3, 1, 4, 0));
      options.add(m_keep);
      options.add(m_replace);
      options.add(m_byLocation);
      panel.add(options, BorderLayout.CENTER);

      okc.getOKButton().addActionListener(al);
      okc.getCancelButton().addActionListener(al);

      JPanel display = new JPanel(new BorderLayout());
      display.add(panel, BorderLayout.CENTER);
      display.add(okc, BorderLayout.SOUTH);

      setContentPane(display);
   }

   /**
    * Shows the dialog & returns the users response.
    *
    * @param owner A component for dialog placement.
    * @return The action code, i.e. {@link #CODE_CANCEL}, {@link #CODE_KEEP}, {@link #CODE_REPLACE}, or {@link #CODE_BY_LOCATION}.
    */
   private static int showDialog(UIAgent uiAgent, Component owner) {
      QNamePanelImportQueryDialog q;
      if (owner instanceof Frame) {
         q = new QNamePanelImportQueryDialog(uiAgent, (Frame) owner);
      }
      else {
         Window w = SwingUtilities.getWindowAncestor(owner);
         if (w instanceof Frame) {
            q = new QNamePanelImportQueryDialog(uiAgent, (Frame) w);
         }
         else {
            q = new QNamePanelImportQueryDialog(uiAgent, (Dialog) w);
         }
      }
      q.pack();
      q.setLocationRelativeTo(owner);
      q.setModal(true);
      q.setVisible(true);
      if (q.m_okHit) {
         if (q.m_replace.isSelected()) {
            return CODE_REPLACE;
         }
         if (q.m_byLocation.isSelected()) {
            return CODE_BY_LOCATION;
         }
         return CODE_KEEP;
      }
      return CODE_CANCEL;
   }

   /**
    * Adds an import, possibly asking the user in the case of an ambiguity.<br>
    * A possible result is to reference the schema by a pseudo-namespace location, where the
    * {@link SchemaRepoUtils#getLocationFromNoTargetNamespaceNamespace} method can extract the location
    * from a pseudo-namespace.  If that is the result, the new pseudo namespace is returned.
    *
    * @param owner           A component, for dialog placement if a dialog is used.
    * @param namespace       The namespace to add
    * @param contextLocation The context location of the choice, if any, null otherwise.
    * @param location        The imported location (assumed project relative now, will be absolute in a later release).
    * @param importRegistry  The current import registry, which will probably get modified.
    * @return The namespace that was added, or null if cancelled.<br>
    *         The namespace added may either be the specified namespace or the {@link SchemaRepoUtils#getLocationFromNoTargetNamespaceNamespace}
    *         namespace/location.
    */
   public static String addImport(UIAgent uiAgent, QNamePlugin plugin, Component owner, String namespace, String contextLocation, String location, ImportRegistry importRegistry) {
      if (contextLocation != null && contextLocation.equals(location)) {
         return namespace; // no need for an import!
      }
      // if we have the context, then change the location to be relative to that context.
      String relLocation;
      if (contextLocation != null) {
         relLocation = QNamePanel.getResourceRelativePath(location, contextLocation);
      }
      else {
         relLocation = location;
      }

      // First check for dead imports at that location:
      pruneDeadImports(uiAgent, importRegistry, contextLocation, relLocation, plugin);

      String[] existingImports = ImportRegistrySupport.getLocationsForNamespaceURI(importRegistry, namespace);
      if (existingImports.length == 0) {
         // go ahead & add, no problemo!
         ImportRegistrySupport.addImport(importRegistry, new ImportRegistryEntry(namespace, relLocation));
         return namespace;
      }
      if (ImportRegistrySupport.containsImport(importRegistry, new ImportRegistryEntry(namespace, relLocation))) {
         // already there, nothing to do.
         return namespace;
      }
      // Oh, boy, have multiple locations, but not ours yet, ask Joe-User what do to:
      int code = QNamePanelImportQueryDialog.showDialog(uiAgent, owner);
      if (code == QNamePanelImportQueryDialog.CODE_CANCEL) {
         return null;
      }
      if (code == QNamePanelImportQueryDialog.CODE_REPLACE) {
         // eliminate old ones:
         for (int i = 0; i < existingImports.length; i++) {
            ImportRegistrySupport.removeImport(importRegistry, new ImportRegistryEntry(namespace, existingImports[i]));
         }
      }
      if (code == QNamePanelImportQueryDialog.CODE_BY_LOCATION) {
         return SchemaRepoUtils.makeNoTargetNamespaceNamespace(location);
      }
      // (Otherwise with 'keep', don't need to do anything special.)

      // Now add the new one (want to do this for either replace or keep)
      ImportRegistrySupport.addImport(importRegistry, new ImportRegistryEntry(namespace, relLocation));
      return namespace;
   }

   /**
    * Prunes any dead imports at the specified location.<br>
    * So, for example, if an import namespace 'myNamespace' at 'abc.xsd' is no longer valid, it'll be removed.
    */
   public static boolean pruneDeadImports(UIAgent uiAgent,
                                          ImportRegistry importRegistry,
                                          String contextURI,
                                          String location,
                                          QNamePlugin plugin) {
      String[] namespaces = ImportRegistrySupport.getNamespaceURIsForLocation(importRegistry, location);
      if (namespaces.length == 0) {
         // no conflicts possible, quicky optimization:
         return true;
      }
      // Check for bad imports:
      String[] allPossibleNamespaces;
      try {
         String projectRelativeLocation = QNamePluginSupport.makeProjectRelativeURI(contextURI, location);
         allPossibleNamespaces = plugin.getNamespacesFor(uiAgent, projectRelativeLocation);
      }
      catch (SAXException se) {
         // Sigh, don't see how to recover here, so punt.
         return true;
      }
      HashSet<String> possibleSet = new HashSet<String>(Arrays.asList(allPossibleNamespaces));

      for (int i = 0; i < namespaces.length; i++) {
         String ns = namespaces[i];
         if (!possibleSet.contains(ns)) {
            // this is a bad import, remove it?

            // WCETODO maybe make this user-controlled? (Currently kind of harsh & very fault intolerant)
            ImportRegistrySupport.removeImport(importRegistry, new ImportRegistryEntry(ns, location));
         }
      }
      return true;
   }
}
