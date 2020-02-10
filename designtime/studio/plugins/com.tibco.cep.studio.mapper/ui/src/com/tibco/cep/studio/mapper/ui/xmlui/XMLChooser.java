package com.tibco.cep.studio.mapper.ui.xmlui;

import java.awt.BorderLayout;
import java.awt.SystemColor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.studio.mapper.MapperSchemaUtils;
import com.tibco.cep.studio.mapper.TreeFilter;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.basic.DetailsViewFactory;
import com.tibco.cep.studio.mapper.ui.data.bind.find.FindWindowPlugin;
import com.tibco.cep.studio.mapper.ui.data.bind.find.GenericFindSelectionHandler;
import com.tibco.cep.studio.mapper.ui.data.bind.find.GenericFindWindow;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * A chooser customized for QName picking, used by {@link com.tibco.cep.studio.mapper.ui.xmlui.QNamePanel}
 */
public class XMLChooser extends BaseChooser {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QNamePlugin m_plugin;
   private QNamePluginSubField[] m_subFields;
   private JPanel m_displayPanel;
   private XMLChooserNamespaceView m_namespaceView;
   private XMLChooserLocationView m_locationView;
   private GenericFindWindow m_searchView;
   private String m_preferencesPrefix;
   private JTabbedPane m_tabbed;
   private ImportRegistry m_importRegistry;
   private DetailsViewFactory m_detailsViewFactory;
   private String m_contextLocation;

   private Object m_currentlyPreviewedObject;
   private JComponent m_currentlyPreviewedComponent;

   private String m_searchFindLocation;
   private ExpandedName m_searchFindName;

   private static final String LOCATION_VIEW_PROPERTY_NAME = "location"; // written into properties file only.
   private static final String NAMESPACE_VIEW_PROPERTY_NAME = "namespace"; // written into properties file only.
   private static final String SEARCH_VIEW_PROPERTY_NAME = "search"; // written into properties file only.

   public XMLChooser(UIAgent uiAgent, QNamePlugin plugin, QNamePluginSubField[] subField, String contextLocation, ImportRegistry importRegistry) {
      super(uiAgent);
      String prefix = "XMLChooser"; // preferences prefix, not displayed.
      m_contextLocation = contextLocation;
      m_preferencesPrefix = prefix;
      m_plugin = plugin;
      m_subFields = subField;
      m_displayPanel = new JPanel(new BorderLayout());
      m_importRegistry = importRegistry;

      final JComponent nv = initNamespaceView();
      final JComponent sv = initSearchView();
      final JTabbedPane jtp = new JTabbedPane();
      m_tabbed = jtp;
      jtp.add(QNamePanelResources.BY_LOCATION, initLocationView());
      if (nv != null) {
         jtp.add(QNamePanelResources.BY_NAMESPACE, nv);
      }
      if (sv != null) {
         jtp.add(QNamePanelResources.BY_SEARCH, sv);
      }

      super.init(jtp, prefix);

      String last = uiAgent.getUserPreference(m_preferencesPrefix + ".activePanel", LOCATION_VIEW_PROPERTY_NAME);
      if (last == null) {
         last = LOCATION_VIEW_PROPERTY_NAME;
      }
      if (last.equals(LOCATION_VIEW_PROPERTY_NAME)) {
         jtp.setSelectedIndex(0);
      }
      if (last.equals(NAMESPACE_VIEW_PROPERTY_NAME)) {
         jtp.setSelectedIndex(1);
      }
      if (last.equals(SEARCH_VIEW_PROPERTY_NAME)) {
         if (m_searchView != null) {
            jtp.setSelectedIndex(2);
         }
      }
      jtp.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            if (jtp.getSelectedIndex() == 2) {
               m_searchView.refreshPreview();
            }
            if (jtp.getSelectedIndex() == 0) {
               m_locationView.refreshPreview();
            }
            if (jtp.getSelectedIndex() == 1) {
               m_namespaceView.refreshPreview();
            }
         }
      });
      setFilters(buildTreeFilters(), true); // true -> ???

      noSchemaSelected(); // setup.
   }

   private TreeFilter[] buildTreeFilters() {
      ArrayList<FileSuffixTreeFilter> filters = new ArrayList<FileSuffixTreeFilter>();
      String[] sfx = m_plugin.getInterestingSuffixes(uiAgent.getTnsCache());
      filters.add(new FileSuffixTreeFilter(sfx)); // must be a VFile thingy!
      return filters.toArray(new TreeFilter[0]);
   }

   public void dispose() {
      m_locationView.writePreferences(m_preferencesPrefix);
      m_namespaceView.writePreferences(m_preferencesPrefix);
      m_namespaceView.dispose();
      m_searchView.dispose();
      String str = LOCATION_VIEW_PROPERTY_NAME;
      if (isLocationPanelSelected()) {
         str = LOCATION_VIEW_PROPERTY_NAME;
      }
      else {
         if (isNamespacePanelSelected()) {
            str = NAMESPACE_VIEW_PROPERTY_NAME;
         }
         else {
            str = SEARCH_VIEW_PROPERTY_NAME;
         }
      }
      uiAgent.setUserPreference(m_preferencesPrefix + ".activePanel", str);
      super.dispose();
   }

   private void setFilters(TreeFilter[] tf, boolean logic) {
      m_locationView.setFilters(tf, logic);
   }

   private JComponent initLocationView() {
      m_locationView = new XMLChooserLocationView(uiAgent, this, m_plugin, m_subFields);
      m_locationView.readPreferences(m_preferencesPrefix);
      return m_locationView;
   }

   private JComponent initNamespaceView() {
      XMLChooserNamespaceView nv = new XMLChooserNamespaceView(uiAgent, this, m_plugin, m_subFields);
      m_namespaceView = nv;
      m_namespaceView.readPreferences(m_preferencesPrefix);
      return nv;
   }

   private JComponent initSearchView() {
      FindWindowPlugin fwp = m_plugin.createFindWindowPlugin(uiAgent);
      if (fwp == null) {
         return null;
      }
      GenericFindSelectionHandler fsh = new GenericFindSelectionHandler() {
         public void show(String absLocation, FindWindowPlugin findPlugin, Object findObject) {
            // ignore findPlugin here...
            if (absLocation == null) {
               setPreview(null, null);
            }
            else {
               m_searchFindLocation = uiAgent.getProjectRelativeURIFromAbsoluteURI(absLocation);
               ExpandedName ename = m_plugin.getNameFromFindResult(absLocation, findObject);
               m_searchFindName = ename;
               setPreview(absLocation, ename);
            }
         }
      };
      GenericFindWindow gfw = new GenericFindWindow(uiAgent,
                                                    fwp,
                                                    fsh,
                                                    true // single click selection enabled.
                                                    /*null  TODO : ryanh mapper*/);
      m_searchView = gfw;
      return gfw;
   }

   public void setSelectedName(ExpandedName name) {
      String loc = QNamePluginSupport.getLocationForName(uiAgent, m_plugin, m_contextLocation, m_importRegistry, name);
      if (loc == null) {
         String ns = name.getNamespaceURI();
         if (ns != null && MapperSchemaUtils.isNoTargetNamespaceNamespace(ns)) {
            loc = MapperSchemaUtils.getLocationFromNoTargetNamespaceNamespace(ns);
         }
      }
      if (loc == null) {
         // is it unambiguous by namespace?
         String[] locs = m_plugin.getLocationsForName(uiAgent, name);
         if (locs != null && locs.length == 1) {
            loc = locs[0];
         }
      }
      if (loc != null) {
//         AEResource res = doc.getRootFolder().findResource(loc);
//         if (res != null) {
            m_locationView.setSelectedResource(loc);
//         }
      }
      String ns = name.getNamespaceURI();
      if (MapperSchemaUtils.isNoTargetNamespaceNamespace(ns) && loc != null) {
         // find the namespace by location:
         try {
            // If unambiguous:
            String absoluteLoc = uiAgent.getAbsoluteURIFromProjectRelativeURI(loc);
            String[] nses = m_plugin.getNamespacesFor(uiAgent, absoluteLoc);
            if (nses != null && nses.length == 1) {
               ns = nses[0];
            }
         }
         catch (SAXException se) {
         }
      }
      m_locationView.setSelectedNamespace(ns);
      m_locationView.setSelectedLocalName(name.getLocalName());
      m_namespaceView.setSelectedNamespace(ns);
      m_namespaceView.setSelectedLocalName(name.getLocalName());
   }

   private boolean isLocationPanelSelected() {
      return m_tabbed.getSelectedIndex() == 0;
   }

   private boolean isNamespacePanelSelected() {
      return m_tabbed.getSelectedIndex() == 1;
   }

   public String getSelectedResourceLocation()
   {
       if (isLocationPanelSelected()) // location:m_locationView)
       {
           return m_locationView.getSelectedResourceLocation();
       }
       if (isNamespacePanelSelected())
       {
           return m_namespaceView.getSelectedResourceLocation();
       }
       // search panel:
       if (m_searchFindLocation!=null)
       {
           return "/" + m_searchFindLocation; //RYANTODO - Search PanelTODO
       }
       return null;
   }

   /**
    * Gets the selected local-name.
    *
    * @return The local name, never null.
    */
   public String getSelectedLocalName() {
      if (isLocationPanelSelected()) // location:m_locationView)
      {
         return m_locationView.getSelectedLocalName();
      }
      if (isNamespacePanelSelected()) {
         return m_namespaceView.getSelectedLocalName();
      }
      // search panel:
      if (m_searchFindName != null) {
         return m_searchFindName.getLocalName();
      }
      // oh, well.
      return "";
   }

   public String getSelectedNamespace() {
      if (isLocationPanelSelected()) // location:m_locationView)
      {
         return m_locationView.getSelectedNamespace();
      }
      if (isNamespacePanelSelected()) {
         return m_namespaceView.getSelectedNamespace();
      }
      // search panel:
      if (m_searchFindName != null) {
         return m_searchFindName.getNamespaceURI();
      }
      return null;
   }

   public String[] getSelectedSubFieldNames() {
      if (isLocationPanelSelected()) // location:m_locationView)
      {
         return m_locationView.getSelectedSubFieldNames();
      }
      if (isNamespacePanelSelected()) {
         return m_namespaceView.getSelectedSubFieldNames();
      }
      // search panel:
      if (m_searchFindName != null) {
         return new String[]{"TODO"};//WCETODO.m_searchFindName.getLocalName();
      }
      throw new IllegalStateException("Internal error; no tab selected.");
   }

   public void setSelectedSubFieldNames(String[] names) {
      m_locationView.setSelectedSubFieldNames(names);
      m_namespaceView.setSelectedSubFieldNames(names);
   }

   private void setPreview(String absLocationURI, ExpandedName name) {
      if (absLocationURI == null && name == null) {
         super.setPreview(new JLabel());
         return;
      }
      Object object = m_plugin.getObjectFor(uiAgent.getTnsCache(),
                                            absLocationURI, name);
      setPreviewForObject(object, name);
   }

   void setPreviewForObject(Object object, ExpandedName name) {
      if (m_currentlyPreviewedObject == object) {
         // avoid needless refreshes.
         super.setPreview(m_currentlyPreviewedComponent);
      }
      else {
         m_currentlyPreviewedObject = object;
         JComponent jc;
         try {
            if (m_detailsViewFactory == null) {
               m_detailsViewFactory = m_plugin.createPreviewView(uiAgent);
            }
            jc = m_detailsViewFactory.getComponentForNode(object);
            if (jc == null) // sanity.
            {
               // Only in the event of a bug:
               jc = new JScrollPane(new JLabel("Null preview returned for: " + name));
            }
         }
         catch (Exception e) // sanity.
         {
            // Only in the event of a bug:
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            jc = new JScrollPane(new JLabel(htmlize("Error thrown getting preview: " + e.getMessage() + "\n" + sw.toString())));
         }
         m_currentlyPreviewedComponent = jc;
         super.setPreview(jc);
      }
   }

   /**
    * Converts new-lines into html 'br' tags and adds a leading 'html' tag.
    *
    * @param str
    * @return
    */
   private static String htmlize(String str) {
      StringBuffer sb = new StringBuffer();
      sb.append("<html>");
      for (int i = 0; i < str.length(); i++) {
         char c = str.charAt(i);
         if (c == '\n') {
            sb.append("<br></br>\n");
         }
         else {
            sb.append(c);
         }
      }
      return sb.toString();
   }

   /**
    * Configures the item tree & previewTree for when there is no schema selected.
    */
   private void noSchemaSelected() {
      badItemSelection("");
   }

   private void badItemSelection(String msg) {
      JPanel jp = new JPanel(new BorderLayout());
      jp.add(new JLabel(msg), BorderLayout.NORTH);
      jp.add(new JLabel(""), BorderLayout.CENTER);
      jp.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
      m_displayPanel.removeAll();
      m_displayPanel.add(jp);
      m_displayPanel.revalidate();
      m_displayPanel.repaint();

      noParticleSelected();
   }

   /**
    * Configures the previewTree for when there is no element (or type) selected.
    */
   private void noParticleSelected() {
      JComponent n = new JLabel();
      n.setOpaque(true);
      n.setBackground(SystemColor.control);
      super.setPreview(n);
   }
}
