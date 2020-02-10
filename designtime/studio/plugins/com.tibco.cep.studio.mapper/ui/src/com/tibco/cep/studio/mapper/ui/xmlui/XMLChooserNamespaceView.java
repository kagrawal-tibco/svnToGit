package com.tibco.cep.studio.mapper.ui.xmlui;

import java.awt.BorderLayout;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.utils.PreferenceUtils;
import com.tibco.xml.schema.helpers.NoNamespace;

/**
 * A tree view of namespaces used interanlly by {@link XMLChooser}.
 * (Public for testing purposes -- maybe move to subdir)
 */
public class XMLChooserNamespaceView extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private QNamePlugin m_plugin;
   private UIAgent uiAgent;
//   private ObjectProvider m_provider;
   private JSplitPane m_splitter;
   private boolean m_cancelled;
   private XMLChooser m_owner;
   private XMLChooserLocationLocalNamePicker m_localNamePicker;
   private HashMap<String, List<Object>> m_namespacesToLocations; // String (namespace) ->ArrayList of absolute locations.
   private String m_selectedNamespace;
   private String m_selectedLocalName;
   private int m_dividerLocation;
   private JTree m_tree;

   public XMLChooserNamespaceView(UIAgent uiAgent,
                                  XMLChooser owner,
                                  QNamePlugin plugin, QNamePluginSubField[] subField) {
      super(new BorderLayout());
      m_owner = owner;
      this.uiAgent = uiAgent;
      if (plugin == null) {
         throw new NullPointerException();
      }
      m_plugin = plugin;
//      m_provider = uiAgent.getObjectProvider();
      m_localNamePicker = new XMLChooserLocationLocalNamePicker(m_owner, uiAgent, plugin, subField);
      Runnable r = new Runnable() {
         public void run() {
            final HashMap<String, List<Object>> nsToLocations = buildNamespacesList();
            final TreeNode tn = buildTree(nsToLocations.keySet().toArray(new String[0]));
            SwingUtilities.invokeLater(new Runnable() {
               public void run() {
                  setTreeRoot(tn, nsToLocations);
               }
            });
         }
      };
      Thread th = new Thread(r, "NamespaceView search");
      th.setPriority(Thread.MIN_PRIORITY);
      th.start();
   }

   @SuppressWarnings("rawtypes")
private HashMap<String, List<Object>> buildNamespacesList() {
       HashMap<String, List<Object>> allNamespaces = new HashMap<String, List<Object>>();
       Iterator itr = this.uiAgent.getTnsCache().getLocations();
       while (itr.hasNext()) {
           String location = (String) itr.next();
		   URI uri;
		   try {
			   uri = new URI((String)location);
			   processFile(allNamespaces, location, null);
		   } catch (URISyntaxException e) {
			   e.printStackTrace();
		   }
	   }

	   return allNamespaces;
   }

//   private void processFile(DefaultMutableTreeNode rootNode, File file, HashMap<File, DefaultMutableTreeNode> parentNodes) {
//   	DefaultMutableTreeNode parentNode = getParentNode(rootNode, file.getParentFile(), parentNodes);
//   	DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(file);
//		parentNode.add(treeNode);
//   }

//	private DefaultMutableTreeNode getParentNode(
//			DefaultMutableTreeNode rootNode, File parentFile,
//			HashMap<File, DefaultMutableTreeNode> parentNodes) {
//		if (parentFile == null || uiAgent.getRootProjectPath().equals(parentFile.getAbsolutePath())) {
//			return rootNode;
//		}
//		if (parentNodes.get(parentFile) != null) {
//			return parentNodes.get(parentFile);
//		}
//		DefaultMutableTreeNode parentNode = getParentNode(rootNode, parentFile.getParentFile(), parentNodes);
//		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(parentFile);
//		parentNode.add(childNode);
//		parentNodes.put(parentFile, childNode);
//		return childNode;
//	}

   public void dispose() {
      synchronized (this) {
         m_cancelled = true;
      }
   }

   public void refreshPreview() {
      m_localNamePicker.refreshPreview();
   }

   public void setSelectedLocalName(String localName) {
      synchronized (this) {
         m_selectedLocalName = localName;
         if (m_localNamePicker != null) {
            m_localNamePicker.setSelectedLocalName(localName);
         }
      }
   }

   public void readPreferences(String prefix) {
      synchronized (this) {
         int loc = PreferenceUtils.readInt(uiAgent, prefix + ".namespace.splitterLocation", 180);
         m_dividerLocation = loc;
         if (m_splitter != null) {
            m_splitter.setDividerLocation(loc);
         }
      }
   }

   public void writePreferences(String prefix) {
      synchronized (this) {
         int dl = m_splitter != null ? m_splitter.getDividerLocation() : m_dividerLocation;
         PreferenceUtils.writeInt(uiAgent, prefix + ".namespace.splitterLocation", dl);
      }
   }
   public String getSelectedResourceLocation()
   {
       synchronized (this)
       {
           if (m_tree==null)
           {
               return null;
           }
           String location = m_localNamePicker.getSelectedLocation();
           return location;
           /* RYANTODO - Indexer
           if (location!=null)
           {
               AEResource res = uiAgent.getRootFolder().findResource(location);
               return res;
           }
           return null;
           */
       }
   }

   public String getSelectedNamespace() {
      synchronized (this) {
         if (m_tree == null) {
            return NoNamespace.URI; // "";
         }
         TreePath path = m_tree.getSelectionPath();
         if (path == null) {
            return NoNamespace.URI; // "";
         }
         NamespaceNode n = (NamespaceNode) path.getLastPathComponent();
         return n.getNamespaceURI();
      }
   }

   public void setSelectedNamespace(String ns) {
      synchronized (this) {
         m_selectedNamespace = ns;
         if (m_tree != null) {
            setNamespaceInternal(ns);
         }
      }
   }

   public String getSelectedLocalName() {
      synchronized (this) {
         if (m_localNamePicker == null) {
            return m_selectedLocalName;
         }
         return m_localNamePicker.getSelectedLocalName();
      }
   }

   public String[] getSelectedSubFieldNames() {
      return m_localNamePicker.getSelectedSubFieldNames();
   }

   public void setSelectedSubFieldNames(String[] names) {
      m_localNamePicker.setSelectedSubFieldNames(names);
   }

   private void setNamespaceInternal(String ns) {
      // very brute force & stupid algorithm... but it'll work.
      NamespaceNode root = (NamespaceNode) m_tree.getModel().getRoot();
      NamespaceNode leaf = findNamespace(root, ns);
      if (leaf != null) {
         TreePath path = new TreePath(leaf.getPath());
         m_tree.expandPath(path);
         m_tree.setSelectionPath(path);
      }
   }

   private NamespaceNode findNamespace(NamespaceNode at, String ns) {
      if (at.getNamespaceURI() == null ? ns == null : at.getNamespaceURI().equals(ns)) {
         return at;
      }
      int cc = at.getChildCount();
      for (int i = 0; i < cc; i++) {
         NamespaceNode t = findNamespace((NamespaceNode) at.getChildAt(i), ns);
         if (t != null) {
            return t;
         }
      }
      return null;
   }

   private void setTreeRoot(TreeNode treeNode, HashMap<String, List<Object>> nsToLocations) {
      if (hasBeenCancelled()) {
         return;
      }
      m_namespacesToLocations = nsToLocations;

      JTree tree = new JTree();
      m_tree = tree;
      tree.setModel(new DefaultTreeModel(treeNode));
      tree.setRootVisible(false);
      tree.setShowsRootHandles(true);
      JScrollPane jsp = new JScrollPane(tree);
      tree.addTreeSelectionListener(new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent e) {
            selectionChanged();
         }
      });
      if (m_selectedNamespace != null) {
         setNamespaceInternal(m_selectedNamespace);
      }
      if (m_selectedLocalName != null) {
         m_localNamePicker.setSelectedLocalName(m_selectedLocalName);
      }

      removeAll();

      JSplitPane splitter = new JSplitPane();
      m_splitter = splitter;
      splitter.setLeftComponent(jsp);
      splitter.setRightComponent(m_localNamePicker);
      splitter.setDividerLocation(m_dividerLocation);
      add(splitter);
      revalidate();
      repaint();
   }

   private void selectionChanged() {
      String ns = getSelectedNamespace();
      ArrayList<Object> locs = (ArrayList<Object>) m_namespacesToLocations.get(ns);
      String[] locsa;
      if (locs != null) {
         locsa = locs.toArray(new String[0]);
      }
      else {
         // just in case.
         locsa = new String[0];
      }
      m_localNamePicker.showLocations(locsa, ns);
   }

   private boolean hasBeenCancelled() {
      synchronized (this) {
         return m_cancelled;
      }
   }

   /**
    * @return A map of string -> ArrayList of locations.
    */
//   private HashMap buildNamespacesListOld() {
//      // global search:
//      HashMap allNamespaces = new HashMap();
//      Iterator i = m_provider.getAllProjects();
//      while (i.hasNext()) {
//         VFileFactory vff = (VFileFactory) i.next();
//         VFileDirectory vfd = vff.getRootDirectory();
//         runDirectory(vfd, allNamespaces);
//         if (hasBeenCancelled()) {
//            break;
//         }
//      }
//      return allNamespaces;
//   }
   
//   private HashMap<String, List<Object>> buildNamespacesList2()
//   {
//       HashMap<String, List<Object>> allNamespaces = new HashMap<String, List<Object>>();
//       File file = new File(uiAgent.getRootProjectPath());
//       processDirectory(allNamespaces, file);
//
//       return allNamespaces;
//   }
   
   private void processDirectory(HashMap<String, List<Object>> allNamespaces, File parentDir) {
	   File[] listFiles = parentDir.listFiles();
	   for (File file : listFiles) {
		   processFile(allNamespaces, file.getAbsolutePath(), file);
	   }
   }

   private void processFile(HashMap<String, List<Object>> allNamespaces, String absLocation, File file) {
	   try {
		   String[] namespaces = m_plugin.getNamespacesFor(uiAgent, absLocation);
		   if (namespaces!=null)
		   {
			   for (int ni=0;ni<namespaces.length;ni++)
			   {
				   String namespace = namespaces[ni];
				   List<Object> al;
				   if (allNamespaces.containsKey(namespace))
				   {
					   al = allNamespaces.get(namespace);
				   }
				   else
				   {
					   al = new ArrayList<Object>();
					   allNamespaces.put(namespace,al);
				   }
				   al.add(absLocation);
			   }
		   }
	   } catch (SAXException e) {
		   e.printStackTrace();
	   }

	   if (file != null && file.isDirectory()) {
		   processDirectory(allNamespaces, file);
	   }
}


//   private void runDirectory(VFileDirectory dir, HashMap<String, ArrayList<String>> allNamespaces) {
//      try {
//         Iterator i = dir.getChildren();
//         while (i.hasNext()) {
//            if (hasBeenCancelled()) {
//               break;
//            }
//            VFile vf = (VFile) i.next();
//            if (!(vf instanceof VFileDirectory)) {
//               String absLocation = vf.getFullURI();
//               try {
//                  String[] ns = m_plugin.getNamespacesFor(uiAgent, absLocation);
//                  if (ns != null) {
//                     for (int ni = 0; ni < ns.length; ni++) {
//                        String namespace = ns[ni];
//                        ArrayList<String> al;
//                        if (allNamespaces.containsKey(namespace)) {
//                           al = allNamespaces.get(namespace);
//                        }
//                        else {
//                           al = new ArrayList<String>();
//                           allNamespaces.put(namespace, al);
//                        }
//                        al.add(absLocation);
//                     }
//                  }
//               }
//               catch (SAXException se) {
//                  // eat it, don't add to list (maybe put in another list somewhere)
//               }
//            }
//            else {
//               runDirectory((VFileDirectory) vf, allNamespaces);
//            }
//         }
//      }
//      catch (ObjectRepoException ore) {
//         // eat it.
//      }
//   }

   public static class NamespaceNode extends DefaultMutableTreeNode {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String m_fullNamespace; // build on demand.

      public NamespaceNode(String uriFragment) {
         super(uriFragment);
      }

      /**
       * Gets the full namespace URI (by reconstructing it)
       */
      public String getNamespaceURI() {
         if (m_fullNamespace == null) {
            m_fullNamespace = computeNamespaceURI();
         }
         return m_fullNamespace;
      }

      private String computeNamespaceURI() {
         NamespaceNode p = (NamespaceNode) getParent();
         if (p == null) {
            // this is the root.
            return NoNamespace.URI; // "";
         }
         String base = p.getNamespaceURI();
         String rel = (String) getUserObject();
         if (base != null && base.endsWith("://") && p.getParent() != null) // Is this the protocol string?
         {
            return base + rel;
         }
         if (base == null || base.length() == 0) {
            return rel;
         }
         // otherwise, must add a '/'.
         return base + "/" + rel;
      }

      public String toString() {
         return super.toString();
      }

      public String formatTree() {
         StringBuffer sb = new StringBuffer();
         format(sb, 0);
         return sb.toString();
      }

      private void format(StringBuffer sb, int depth) {
         if (depth != 0) {
            for (int i = 0; i < depth - 1; i++) {
               sb.append(' '); // indent.
            }
            sb.append("" + getUserObject());
            sb.append('\n');
         }
         for (int i = 0; i < getChildCount(); i++) {
            NamespaceNode nn = (NamespaceNode) getChildAt(i);
            nn.format(sb, depth + 1);
         }
      }
   }

   private TreeNode buildTree(String[] namespaces) {
      return buildTree(namespaces, new CancelChecker() {
         public boolean hasBeenCancelled() {
            return XMLChooserNamespaceView.this.hasBeenCancelled();
         }
      });
   }

   public static NamespaceNode buildTree(String[] namespaces, CancelChecker cc) {
      NamespaceNode root = new NamespaceNode("<root>"); // root never displayed
      buildTree(namespaces, cc, root);
      return root;
   }

   /**
    * Takes a list of URIs and forms a URI-syntax aware tree out of them.
    *
    * @param namespaces
    * @param addTo
    */
   private static void buildTree(String[] namespaces, CancelChecker cc, NamespaceNode addTo) {
      // A map of 'branch' (leading path part of a URI) to List of (remainder) URIs.
      HashMap<String, ArrayList<String>> branchToList = new HashMap<String, ArrayList<String>>();
      for (int i = 0; i < namespaces.length; i++) {
         String ns = namespaces[i];
         if (ns == null) {
            continue; // WCETODO maybe add some sort of pseudo entry in the tree to represent those that didn't parse.
         }
         if (ns.indexOf("//") >= 0) {
            String b1 = ns.substring(0, ns.indexOf("//") + 2); // keep the //
            String r = ns.substring(ns.indexOf("//") + 2, ns.length());
            addToList(branchToList, b1, r);
         }
         else {
            if (ns.indexOf("/") >= 0) {
               String b1 = ns.substring(0, ns.indexOf("/"));
               String r = ns.substring(ns.indexOf("/") + 1, ns.length());
               addToList(branchToList, b1, r);
            }
            else {
               // make a node for this. (figure out duplicate detection logic...)
               addTo.add(new NamespaceNode(ns));
            }
         }
      }
      String[] branchArray = branchToList.keySet().toArray(new String[0]);
      Arrays.sort(branchArray);
      for (int i = 0; i < branchArray.length; i++) {
         String b = branchArray[i];
         ArrayList<String> items = branchToList.get(b);
         NamespaceNode node = new NamespaceNode(b);
         addTo.add(node);
         String[] itemsa = (String[]) items.toArray(new String[items.size()]);
         if (!cc.hasBeenCancelled()) {
            buildTree(itemsa, cc, node);
         }
         if (node.getChildCount() == 1) {
            // superfluous step, move it out 1:
            NamespaceNode nn = (NamespaceNode) node.getChildAt(0);
            node.removeAllChildren();
            addTo.remove(node);
            String newkey = b.endsWith("/") ? (b + nn.getUserObject()) : (b + "/" + nn.getUserObject());
            nn.setUserObject(newkey);
            addTo.add(nn);
         }
      }
   }

   private static void addToList(HashMap<String, ArrayList<String>> branchToList, String key, String remainder) {
      if (!branchToList.containsKey(key)) {
         branchToList.put(key, new ArrayList<String>());
      }
      ArrayList<String> items = branchToList.get(key);
      items.add(remainder);
   }
}
