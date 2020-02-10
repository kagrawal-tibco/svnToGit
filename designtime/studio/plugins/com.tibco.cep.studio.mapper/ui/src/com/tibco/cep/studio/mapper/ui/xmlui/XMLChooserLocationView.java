package com.tibco.cep.studio.mapper.ui.xmlui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.studio.mapper.TreeFilter;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.utils.PreferenceUtils;

/**
 * Used by {@link XMLChooser}
 */
class XMLChooserLocationView extends JPanel {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	private class WrappedFileNode extends DefaultMutableTreeNode {
//		
//	}
	
	private class FileCellRenderer extends DefaultTreeCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean selected, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {
			super.getTreeCellRendererComponent(
					tree, value, selected,
					expanded, leaf, row,
					hasFocus);
			if (value instanceof DefaultMutableTreeNode) {

				String fileName = null;
				Object userObj = ((DefaultMutableTreeNode) value).getUserObject();
				if (userObj instanceof File) {
					File file = (File) userObj;
					fileName = file.getName();
				} else if (userObj instanceof String) {
					fileName = (String) userObj;
					int idx = fileName.lastIndexOf('/');
					if (idx >= 0) {
						fileName = fileName.substring(idx+1);
					}
				}
    			int idx = fileName.lastIndexOf('.');
    			if (idx >= 0) {
    				String ext = fileName.substring(idx+1);
    				fileName = fileName.substring(0, idx);
    				setIcon(getIcon(ext));
    			}

				setText(fileName);
			}
			if (leaf) {
//				setIcon(tutorialIcon);
				setToolTipText("This is a leaf");
			} else {
				setToolTipText(null); //no tool tip
			}
			return this;
		}

		private Icon getIcon(String ext) {
			if ("scorecard".equalsIgnoreCase(ext)) {
				return ResourceBundleManager.getIcon("ae.resource.chooser.scorecard.icon", getClass().getClassLoader());
			} else if ("concept".equalsIgnoreCase(ext)) {
				return ResourceBundleManager.getIcon("ae.resource.chooser.concept.icon", getClass().getClassLoader());
			} else if ("event".equalsIgnoreCase(ext)) {
				return ResourceBundleManager.getIcon("ae.resource.chooser.event.icon", getClass().getClassLoader());
			} else if ("time".equalsIgnoreCase(ext)) {
				return ResourceBundleManager.getIcon("ae.resource.chooser.timeevent.icon", getClass().getClassLoader());
			} else if ("channel".equalsIgnoreCase(ext)) {
				return ResourceBundleManager.getIcon("ae.resource.chooser.channel.icon", getClass().getClassLoader());
			} else if ("rule".equalsIgnoreCase(ext)) {
				return ResourceBundleManager.getIcon("ae.resource.chooser.rule.icon", getClass().getClassLoader());
			} else if ("rulefunction".equalsIgnoreCase(ext)) {
				return ResourceBundleManager.getIcon("ae.resource.chooser.rulefunction.icon", getClass().getClassLoader());
			} else if ("xsd".equalsIgnoreCase(ext)) {
				return ResourceBundleManager.getIcon("ae.resource.chooser.xsd.icon", getClass().getClassLoader());
			}
			// return the default icon
			return getIcon();
		}
		
	}
	
	
    /**
     * The tree to display information
     */
    private JTree m_tree;
    private final UIAgent uiAgent;
    private XMLChooserLocalNamePicker m_localNamePicker;
    private JSplitPane m_splitter;
    private JPanel m_errorOrPickerPanel;
    private XMLChooser m_owner;
    private QNamePlugin m_plugin;
    private HashSet<String> m_interestingSuffixes = new HashSet<String>();

    /**
     * PreferencesId is used for writing preferences to designer file (i.e. last window size, etc.)
     * @param uiAgent The UI Agent
     */
    public XMLChooserLocationView(UIAgent uiAgent, XMLChooser owner, QNamePlugin plugin, QNamePluginSubField[] subField)
    {
        super(new BorderLayout());
        this.uiAgent = uiAgent;
        m_plugin = plugin;
        m_owner = owner;
        
        buildInterestingSuffixMap();

        m_tree = new JTree();
        m_tree.setModel(buildRootModel()); //RYANTODO - Need theIndexer Content Root
        m_tree.setCellRenderer(new FileCellRenderer());
        m_tree.addTreeSelectionListener(new TreeSelectionListener()
        {
            public void valueChanged(TreeSelectionEvent e)
            {
                String location = getSelectedResourceLocation();
                setViewedItem(location);
            }
        });

        JScrollPane scrollPane = new JScrollPane(m_tree);
        JSplitPane locationPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        m_splitter = locationPanel;
        locationPanel.setLeftComponent(scrollPane);
        m_localNamePicker = new XMLChooserLocalNamePicker(owner,uiAgent,plugin,subField);
        m_errorOrPickerPanel = new JPanel(new BorderLayout());
        m_errorOrPickerPanel.add(m_localNamePicker);
        locationPanel.setRightComponent(m_errorOrPickerPanel);
        add(locationPanel);

    }

    @SuppressWarnings("rawtypes")
	private TreeModel buildRootModel() {
    	String rootProjectPath = uiAgent.getRootProjectPath();
    	File file = new File(rootProjectPath);
    	DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(file);
    	Iterator locations = uiAgent.getTnsCache().getLocations();
    	HashMap<File, DefaultMutableTreeNode> parentNodes = new HashMap<File, DefaultMutableTreeNode>();
    	while (locations.hasNext()) {
			Object object = (Object) locations.next();
			URI uri;
			try {
				uri = new URI((String)object);
				if ("jar".equals(uri.getScheme())) {
					String jarEntryPath = new File(new URI(uri.toURL().getFile()).toURL().getFile()).getAbsolutePath();
					int idx = jarEntryPath.indexOf('!');
					String entryPath = jarEntryPath.substring(idx+1);
					File entryFile = new File(file, entryPath);
					processJarEntry(rootNode, (String)object, entryFile, parentNodes);
				} else {
					File f = new File(uri);
					processFile(rootNode, f, parentNodes );
				}
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
//    	processDirectory(rootNode, file);
    	TreeModel model = new DefaultTreeModel(rootNode);
    	
		return model;
	}

    private void processFile(DefaultMutableTreeNode rootNode, File file, HashMap<File, DefaultMutableTreeNode> parentNodes) {
    	DefaultMutableTreeNode parentNode = getParentNode(rootNode, file.getParentFile(), parentNodes);
    	DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(file);
		parentNode.add(treeNode);
    }

    private void processJarEntry(DefaultMutableTreeNode rootNode, String jarEntryPath, File entryFile, HashMap<File, DefaultMutableTreeNode> parentNodes) {
    	DefaultMutableTreeNode parentNode = getParentNode(rootNode, entryFile.getParentFile(), parentNodes);
    	DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(jarEntryPath);
    	parentNode.add(treeNode);
    }
    
	private DefaultMutableTreeNode getParentNode(
			DefaultMutableTreeNode rootNode, File parentFile,
			HashMap<File, DefaultMutableTreeNode> parentNodes) {
		if (parentFile == null || uiAgent.getRootProjectPath().equals(parentFile.getAbsolutePath())) {
			return rootNode;
		}
		if (parentNodes.get(parentFile) != null) {
			return parentNodes.get(parentFile);
		}
		DefaultMutableTreeNode parentNode = getParentNode(rootNode, parentFile.getParentFile(), parentNodes);
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(parentFile);
		parentNode.add(childNode);
		parentNodes.put(parentFile, childNode);
		return childNode;
	}

//	private boolean processDirectory(DefaultMutableTreeNode node, File parentDir) {
//    	boolean addedChildren = false;
//    	File[] listFiles = parentDir.listFiles();
//    	
//    	for (File file : listFiles) {
//    		DefaultMutableTreeNode treeNode = null;
//    		if (file.isFile()) {
//    			String fileName = file.getName();
//    			int idx = fileName.lastIndexOf('.');
//    			if (idx >= 0) {
//    				String suffix = fileName.substring(idx+1);
//    				if (m_interestingSuffixes.contains(suffix)) {
//    	    			treeNode = new DefaultMutableTreeNode(file);
//    	    			node.add(treeNode);
//    	    			addedChildren = true;
//    				}
//    			}
//    		} else {
//    			treeNode = new DefaultMutableTreeNode(file);
//        		if (file.isDirectory()) {
//        			if (processDirectory(treeNode, file)) {
//        				node.add(treeNode);
//        				addedChildren = true;
//        			}
//        		}
//    		}
//		}
//    	return addedChildren;
//	}

	private void buildInterestingSuffixMap()
    {
        String[] str = m_plugin.getInterestingSuffixes(uiAgent.getTnsCache());
        for (int i=0;i<str.length;i++)
        {
            m_interestingSuffixes.add(str[i]);
        }
    }

    /**
     * Use the specified filters during choosing
     * @param tf The filter to use
     * @param logic
     */
    public void setFilters(TreeFilter[] tf, boolean logic)
    {
        //m_tree.setModel(new FilteredTreeModel(uiAgent.getTreeModel().getRoot(), tf, logic));

        //m_tree.setCellRenderer(new AEResourceNodeRenderer());
    }

    public void refreshPreview()
    {
        m_localNamePicker.refreshPreview();
    }

    public void readPreferences(String prefix)
    {
        m_splitter.setDividerLocation(PreferenceUtils.readInt(uiAgent, prefix + ".location.splitterLocation", 180));
    }

    public void writePreferences(String prefix)
    {

        PreferenceUtils.writeInt(uiAgent, prefix + ".location.splitterLocation", m_splitter.getDividerLocation());
    }

    public void setSelectedResource(String loc) {
    	if (loc != null) {
    		Object[] nodePaths = getSelectedNodePath(loc);
    		if (nodePaths != null) {
    			TreePath path = new TreePath(nodePaths);
    			m_tree.setSelectionPath(path);
    		} else {
    			m_tree.setSelectionPath(null);
    		}
    	}
        else {
           m_tree.setSelectionPath(null);
        }
     }

    private Object[] getSelectedNodePath(String loc) {
    	if (loc == null) {
    		return null;
    	}
    	try {
    		loc = loc.replaceAll("\\\\", "/");
    		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) m_tree.getModel().getRoot();
    		List<DefaultMutableTreeNode> nodes = new ArrayList<DefaultMutableTreeNode>();
    		nodes.add(rootNode);
    		StringTokenizer st = new StringTokenizer(loc, "/");
    		return findNode(nodes, rootNode, st);
		} catch (Exception e) {
		}
		return null;
	}

	private Object[] findNode(List<DefaultMutableTreeNode> nodes, TreeNode parentNode,
			StringTokenizer st) {
		if (st.hasMoreTokens()) {
			String token = st.nextToken();
			int count = parentNode.getChildCount();
			for (int i = 0; i < count; i++) {
				DefaultMutableTreeNode childAt = (DefaultMutableTreeNode) parentNode.getChildAt(i);
				File obj = (File) childAt.getUserObject();
				if (obj != null && token.equals(obj.getName())) {
					nodes.add(childAt);
					if (st.hasMoreTokens()) {
						return findNode(nodes, childAt, st);
					} else {
						return nodes.toArray();
					}
				}
			}
		}
    	return null;
	}

	public void setSelectedNamespace(String namespace)
    {
        m_localNamePicker.setSelectedNamespace(namespace);
    }

    public void setSelectedLocalName(String localName)
    {
        m_localNamePicker.setSelectedLocalName(localName);
    }

    public String getSelectedLocalName()
    {
        return m_localNamePicker.getSelectedLocalName();
    }

    public String[] getSelectedSubFieldNames()
    {
        return m_localNamePicker.getSelectedSubFieldNames();
    }

    public void setSelectedSubFieldNames(String[] names)
    {
        m_localNamePicker.setSelectedSubFieldNames(names);
    }

    /**
     * Returns the currently selected resource in the tree
     * @return The selected resource
     */
    public String getSelectedResourceLocation()
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) m_tree.getLastSelectedPathComponent();
        if (node == null) {
        	return "";
        }
        Object userObject = node.getUserObject();
        if (userObject instanceof File) {
        	String absPath = ((File) userObject).getAbsolutePath();
        	return uiAgent.getProjectRelativeURIFromAbsoluteURI(absPath);
        } else if (userObject instanceof String) {
        	return (String) userObject;
        }
        
        TreePath path = m_tree.getSelectionPath();
        if (path != null) {
        	return path.toString(); //RYANTODO - return the proper selected path from the Indexer.
        }
        return "";
//        AEResource retVal = null;
//
//        if (path != null)
//        {
//            retVal = (AEResource) path.getLastPathComponent();
//        }
//
//        return retVal;
    }

    /**
     * Returns the currently selected namespace
     */
    public String getSelectedNamespace()
    {
        return m_localNamePicker.getSelectedNamespace();
    }

    private void setViewedItem(String location)
    {
        if (location == null || location.length() == 0) {
            noLocationSelected();
            return;
        }

        if (location.startsWith("/")) // another 'feature' --- this extra slash
        {
            location = location.substring(1);
        }
        try
        {
            String suffix = getSuffix(location);
            if (m_interestingSuffixes.contains(suffix))
            {
            	location = location.replaceAll("\\\\", "/");
            	URI uri = URI.create(location);
            	String absLocation = uri.isAbsolute() ? uri.toString() : uiAgent.getAbsoluteURIFromProjectRelativeURI(location);
                String[] ns = m_plugin.getNamespacesFor(uiAgent,absLocation);
                if (ns==null)
                {
                    ns = new String[0];
                }
                for (int i=0;i<ns.length;i++)
                {
                    if (ns[i]==null)
                    {
                        ns[i] = "InternalError, control: " + m_plugin.getClass().getName() + " returned null ns";
                    }
                }
                m_errorOrPickerPanel.removeAll();
                m_errorOrPickerPanel.add(m_localNamePicker);
                m_errorOrPickerPanel.revalidate();
                m_errorOrPickerPanel.repaint();
                m_localNamePicker.showNamespaces(location,ns);
            }
            else
            {
                invalidResourceTypeSelected();
            }
        }
        catch (Exception ore)
        {
            cantParseLocation("Cannot read:<br>" + ore.getMessage());
        }
    }

    /**
     * WCETODO locate an helper function which must exist somewhere for this!!!
     */
    private static String getSuffix(String location)
    {
        int i = location.lastIndexOf('.');
        if (i>0)
        {
            return location.substring(i+1);
        }
        return "";
    }

    private void noLocationSelected()
    {
        m_errorOrPickerPanel.removeAll();
        // just have it blank.
        m_errorOrPickerPanel.revalidate();
        m_errorOrPickerPanel.repaint();
        m_localNamePicker.clear();
        m_owner.clearPreview();
    }

    private void cantParseLocation(String msg)
    {
        JLabel label = new JLabel("<html>" + msg + "</html>");
        m_errorOrPickerPanel.removeAll();
        m_errorOrPickerPanel.add(new JScrollPane(label));
        m_errorOrPickerPanel.revalidate();
        m_errorOrPickerPanel.repaint();
        m_owner.clearPreview();
    }

    private void invalidResourceTypeSelected()
    {
        noLocationSelected(); // for now, just show nothing, maybe make it show a message later.
        m_owner.clearPreview();
    }
}

