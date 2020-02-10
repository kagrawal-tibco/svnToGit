package com.tibco.cep.webstudio.client.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.NodeContextClickHandler;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.ArgumentNavigatorResource;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableNavigatorResource;
import com.tibco.cep.webstudio.client.decisiontable.NavigatorTreeDragHandler;
import com.tibco.cep.webstudio.client.decisiontable.ParentArgumentNavigatorResource;
import com.tibco.cep.webstudio.client.decisiontable.RuleFunctionNavigatorResource;
import com.tibco.cep.webstudio.client.decisiontable.constraint.StringTokenizer;
import com.tibco.cep.webstudio.client.handler.NavigationHandler;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.logging.WebStudioClientLogger;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RuleTemplateInstanceNavigatorResource;
import com.tibco.cep.webstudio.client.model.RuleTemplateNavigatorResource;
import com.tibco.cep.webstudio.client.process.ProcessNavigatorResource;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.client.view.WebStudioResourceCellFormatter;

/**
 *  Project Explorer Tree to load all the existing and/or newly created Studio projects. Additionally supports node context click options.
 * 
 * @author Vikram Patil
 */
public class WebStudioNavigatorGrid extends TreeGrid implements KeyPressHandler {

	private Tree resourceTree;
	private NavigatorResource resourceTreeRootNode = null;
	private NavigatorResource searchTreeRootNode = null;
	private Menu contextMenu;
	private NavigationHandler clickHandler;
	private WebStudioResourceCellFormatter cellFormatter;
	private boolean showAsTree = WebStudio.get().getUserPreference().getItemView().equals("Tree") ? true : false;
	private String searchKeyword;
	private Tree searchTree;

	
	private static final String ICON_PATH = Page.getAppImgDir() + "icons/16/";
	private static final String ROOT_NODE = "Root";
	
	private GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private WebStudioClientLogger logger = WebStudioClientLogger.getLogger(WebStudioNavigatorGrid.class.getName());
	
	/**
	 * @param data {@link NavigatorResource}
	 * @param clickHandler {@link RecordDoubleClickHandler}
	 */
	public WebStudioNavigatorGrid() {
		logger.info("Init NavigationPane Section");
		     
		setWidth100();
		setHeight100();
		setShowAllRecords(false);  
		setShowHeader(false);
		setShowOpenIcons(false);
		setShowDropIcons(false);
		setClosedIconSuffix("");
		setFixedFieldWidths(false);
		setCanDragRecordsOut(true);
		setIndentSize(23);
		setDragDataAction(DragDataAction.COPY);
		setSelectionType(SelectionStyle.SINGLE);
		setLoadDataOnDemand(false);
		setStyleName("ws-myGroups-content-margin");
		setEmptyMessage(globalMsgBundle.message_noData());

		// initialize by setting field data 
		TreeGridField gridField = new TreeGridField("name", "Name");
		gridField.setCanFilter(true);
		setFields(gridField);  
		setOverflow(Overflow.AUTO);
		addSort(new SortSpecifier("name", SortDirection.ASCENDING));
		
		//Setup the tree
		resourceTree = new Tree();
		resourceTree.setModelType(TreeModelType.PARENT);
		resourceTree.setIdField("id");
		resourceTree.setParentIdField("parent");
		resourceTree.setNameProperty("name");
		resourceTree.setReportCollisions(false);
		
		TreeNode root = new TreeNode ("Root");
		resourceTree.setRoot(root);
		
		// set up the field data
		this.setData(resourceTree);  
		// register the click handler
		this.clickHandler = new NavigationHandler();
		addRecordClickHandler((RecordClickHandler)this.clickHandler);
		addRecordDoubleClickHandler((RecordDoubleClickHandler)this.clickHandler);
		
		addDragStartHandler(new NavigatorTreeDragHandler());
		
		addContextMenu();
		this.clickHandler.setContextMenu(this.contextMenu);
		addNodeContextClickHandler((NodeContextClickHandler)this.clickHandler);
		this.cellFormatter = new WebStudioResourceCellFormatter();
		setCellFormatter(this.cellFormatter);
		this.addKeyPressHandler(this);
	}
	
	/**
	 * Method to remove the search tree and render the resource tree.
	 */
	public void removeSearchTree() {
		searchKeyword = null;
		ListGridRecord selectedRecord = getSelectedRecord();
		String selectedNodeName = null;
		if (null != selectedRecord) {
			selectedNodeName = selectedRecord.getAttributeAsString("name");
		}
		setData(resourceTree);
		// select the previously selected record of search tree in resource tree
		selectRecord(resourceTree,selectedNodeName);
		searchTree = null;
	}
	
    /**
     * Method to render the search result for the given keyword.
     * @param keyword
     */
	public void renderSearchResults(String keyword) {
		searchTree = null;
		if (null == keyword || (null != keyword && keyword.equals(""))) {
			searchKeyword = null;
			setData(resourceTree);
			return;
		}
		searchKeyword = keyword.trim().toLowerCase();
		setData(new Tree());
		TreeNode[] nodes = resourceTree.getAllNodes();
		Tree searchTree = getSearchTree();
		for (TreeNode node : nodes) {
			NavigatorResource record = (NavigatorResource) node;
			if (record.contains(searchKeyword)) {
				addRecord(searchTree, searchTreeRootNode, createResource(record));
			}
		}
		setData(searchTree);
		searchTree.setShowRoot(false);
		searchTree.openAll();
	}

	/**
	 * Refresh the project explorer structure
	 */
	public void refresh() {
		this.refreshFields();
	}
	
	/**
	 * Add a new Project structure to Project Explorer tree
	 * 
	 * @param sectionData
	 */
	public void addToResourceNavigator(NavigatorResource[] data) {
		Map<String, NavigatorResource> resourcesTreeDelta = new LinkedHashMap<String, NavigatorResource>();
		for (NavigatorResource res : data) {
			addRecord(res, resourcesTreeDelta);
		}
		
		Set<String> resourceIds = resourcesTreeDelta.keySet();
		Iterator<String> itr = resourceIds.iterator();
		while(itr.hasNext()) {
			String resourceId = itr.next();
			NavigatorResource resourceNode = resourcesTreeDelta.get(resourceId);
			List<NavigatorResource> childResources = resourceNode.getChildren(true);
			if (childResources != null && !childResources.isEmpty()) {
				TreeNode actualResourceNode = null;
				if (resourceTree.getRoot().getName().equals(resourceId)) {
					actualResourceNode = resourceTree.getRoot();
				} else {
					actualResourceNode = getResourceById(resourceId);
					if(actualResourceNode == null) {
						actualResourceNode = resourceNode;
					}
				}
				resourceTree.addList(childResources.toArray(new NavigatorResource[childResources.size()]), actualResourceNode);
			}
		}
		
		// If in search mode add the matching node to search tree.
		if (searchKeyword != null
				&& !searchKeyword.equals("")) {
			setData(searchTree);
			searchTree.openAll();
		} 
	}
	

	/**
	 * Method to get the search tree.
	 * 
	 * @return
	 */
	public Tree getSearchTree() {
		if (null == searchTree) {
			searchTree = new Tree();
			searchTree.setModelType(TreeModelType.PARENT);
			searchTree.setIdField("id");
			searchTree.setParentIdField("parent");
			searchTree.setNameProperty("name");
			searchTree.setReportCollisions(false);

			TreeNode root = new TreeNode("Root");
			searchTree.setRoot(root);
		}
		return searchTree;
	}

	/**
	 * Select a specified project artifact
	 * 
	 * @param name
	 */
	public void selectRecord(Tree tree,String name) {
		if(null != name) {
			TreeNode[] data = tree.getAllNodes();
			for (int i = 0; i < data.length; i++) { 
				TreeNode record = data[i];
				if (name.contentEquals(record.getName())) {
					deselectAllRecords();
					selectRecord(record);
					TreeNode parentRecord = tree.getParent(record);
					while (null != parentRecord) {
						tree.openFolder(parentRecord);
						parentRecord = tree.getParent(parentRecord);
					}
				}
			}
		}
	}

	/**
	 * Retrieve the selected project artifact
	 * 
	 * @param name
	 * @return
	 */
	public int getRecord(String name) {
		NavigatorResource[] data = (NavigatorResource[])resourceTree.getAllNodes();
		int result = -1;
		for (int i = 0; i < data.length; i++) { 
			NavigatorResource record = data[i];

			if (name.contentEquals(record.getName())) {
				result = i;
			}
		}

		return result;
	}

	/**
	 * Remove all the projects from the Project Explorer
	 */
	public void clearResourceTree() {
		resourceTree.unloadChildren(resourceTree.getRoot());
		resourceTree.remove(resourceTree.getRoot());
	}
	
	/**
	 * Add a artifact context node
	 */
	private void addContextMenu() {
		contextMenu = new Menu();
		contextMenu.setShowShadow(true);
		contextMenu.setShadowDepth(10);
		setContextMenu(contextMenu);
	}

	/**
	 * Open a specified resource
	 * 
	 * @param resourceToOpen
	 */
	public void openResource(String resourceToOpen) {
		openResource(resourceToOpen, true);
	}
	
	/**
	 * Open the resource tree for the specified entity
	 * 
	 * @param resourceToOpen
	 * @param fetchResourceContents
	 */
	public void openResource(String resourceToOpen, boolean fetchResourceContents) {
		ProjectExplorerUtil.captureRecordSelection(resourceToOpen.replace("/", "$"));
		StringTokenizer st = new StringTokenizer(resourceToOpen, '/');
		openResource(resourceTree.getRoot(), st, fetchResourceContents);
	}
	

	/**
	 * Open a specified resource
	 * 
	 * @param parentNode
	 * @param st
	 */
	private void openResource(TreeNode parentNode, StringTokenizer st, boolean fetchResourceContents) {
		if (showAsTree) {
			openTreeResource(parentNode, st, fetchResourceContents, null);
		} else {
			openListResource(parentNode, st, fetchResourceContents, null);
		}
	}
	
	/**
	 * This is an internal method called from openResource() method, it opens the specified resource when resources are viewed as Tree.
	 * @param parentNode
	 * @param st
	 * @param fetchResourceContents
	 * @param name
	 */
	private void openTreeResource(TreeNode parentNode, StringTokenizer st, boolean fetchResourceContents, String name) {
		TreeNode[] data = resourceTree.getChildren(parentNode);
		if (name == null && st.hasMoreTokens(true)) {
			name = st.nextToken(true);
		}
		for (TreeNode treeNode : data) {
			if (treeNode instanceof RuleFunctionNavigatorResource || treeNode instanceof RuleTemplateNavigatorResource) {
				openTreeResource(treeNode, st, fetchResourceContents, name);
				continue;
			}
			if (name != null && name.equals(treeNode.getName())) {
				if (st.hasMoreTokens(true)) {
					openTreeResource(treeNode, st, fetchResourceContents, null);
				} else {
					if (treeNode instanceof NavigatorResource) {
						deselectAllRecords();
						selectRecord(treeNode);
						TreeNode tmp = treeNode;
						while (tmp != null) {
							resourceTree.openFolder(tmp);
							tmp = resourceTree.getParent(tmp);
						}
						if (fetchResourceContents)
							clickHandler.createPage((NavigatorResource) treeNode, false);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * This is an internal method called from openResource() method, it opens the specified resource when resources are viewed as List.
	 * @param parentNode
	 * @param st
	 * @param fetchResourceContents
	 * @param name
	 */
	private void openListResource(TreeNode parentNode, StringTokenizer st, boolean fetchResourceContents, String name) {
		TreeNode[] data = resourceTree.getChildren(parentNode);
		while (st.hasMoreTokens(true)) {
			name = st.nextToken(true);
		}
		for (TreeNode treeNode : data) {
			if (treeNode instanceof RuleFunctionNavigatorResource || treeNode instanceof RuleTemplateNavigatorResource) {
				openListResource(treeNode, st, fetchResourceContents, name);
				continue;
			}
			if (name.equals(treeNode.getName())) {
				deselectAllRecords();
				selectRecord(treeNode);
				TreeNode tmp = treeNode;
				while (tmp != null) {
					resourceTree.openFolder(tmp);
					tmp = resourceTree.getParent(tmp);
				}
				if (fetchResourceContents)
					clickHandler.createPage((NavigatorResource) treeNode, false);
				break;
			}
		}
	}
	
	/**
	 * Add selected artifact from the project explorer
	 * 
	 * @param artifactPath
	 * @param projectName
	 */
	public void insertRecord(String artifactPath, String projectName){
		String[] paths = (artifactPath.startsWith("/"))?artifactPath.substring(1).split("/"):new String[]{artifactPath};
		
		String nodeId;
		String extension = artifactPath.substring(artifactPath.indexOf(".")+1);
		String parentId = projectName;
		
		for (int i=0;i<paths.length;i++){			
			nodeId = parentId+"$"+paths[i];
			
			if (resourceTree.findById(nodeId) == null){
				NavigatorResource newResource = ProjectExplorerUtil.createProjectResource(paths[i], parentId, extension,nodeId,(i < paths.length-1));
				addRecord(newResource);
			} else {
				parentId = nodeId;
				continue;
			}
	
			parentId = nodeId;
		}
	}
	
	
	/**
	 * Add record to project explorer tree
	 * 
	 * @param newResource
	 */
	public void addRecord(NavigatorResource newResource, Map<String, NavigatorResource> resourcesTreeDelta) {
		addRecord(resourceTree, resourceTreeRootNode, newResource, resourcesTreeDelta);
		// If in search mode add matching records to the search tree
		if (searchKeyword != null
				&& !searchKeyword.equals("")) {
			NavigatorResource node = (NavigatorResource) newResource;
			if (node.contains(searchKeyword)) {
				searchTree = getSearchTree();
				addRecord(searchTree, searchTreeRootNode, createResource(newResource));
			}
		}
	}
	
	/**
	 * Add record to project explorer tree
	 * 
	 * @param newResource
	 */
	public void addRecord(NavigatorResource newResource) {
		addRecord(resourceTree, resourceTreeRootNode, newResource);
		// If in search mode add matching records to the search tree
		if (searchKeyword != null
				&& !searchKeyword.equals("")) {
			NavigatorResource node = (NavigatorResource) newResource;
			if (node.contains(searchKeyword)) {
				searchTree = getSearchTree();
				addRecord(searchTree, searchTreeRootNode, createResource(newResource));
			}
		}
	}
	
	/**
	 * Method to create a new instance of the given navigator resource.
	 * @param resource
	 * @return
	 */
	private NavigatorResource createResource(NavigatorResource resource) {
		if (resource instanceof RuleTemplateNavigatorResource) {
			return new RuleTemplateNavigatorResource(
					(RuleTemplateNavigatorResource) resource);
		} else if (resource instanceof RuleTemplateInstanceNavigatorResource) {
			return new RuleTemplateInstanceNavigatorResource(
					(RuleTemplateInstanceNavigatorResource) resource);
		} else if (resource instanceof RuleFunctionNavigatorResource) {
			return new RuleFunctionNavigatorResource(
					(RuleFunctionNavigatorResource) resource);
		} else if (resource instanceof ArgumentNavigatorResource) {
			return new ArgumentNavigatorResource(
					(ArgumentNavigatorResource) resource);
		} else if (resource instanceof ParentArgumentNavigatorResource) {
			return new ParentArgumentNavigatorResource(
					(ParentArgumentNavigatorResource) resource);
		} else if (resource instanceof ProcessNavigatorResource) {
			return new ProcessNavigatorResource(
					(ProcessNavigatorResource) resource);
		} else if (resource instanceof DecisionTableNavigatorResource) {
			return new DecisionTableNavigatorResource(
					(DecisionTableNavigatorResource) resource);
		}
		return new NavigatorResource(resource);
	}
	
	/**
	 * Enrich & add the resource record to its parent's children list and the parent to
	 * the resourcesTreeDelta Map
	 * 
	 * @param tree
	 * @param rootNode
	 * @param newResource
	 * @param resourcesTreeDelta
	 */
	private void addRecord(Tree tree, NavigatorResource rootNode, NavigatorResource newResource, Map<String, NavigatorResource> resourcesTreeDelta) {
		boolean doGrouping = WebStudio.get().getUserPreference().isGroupRelatedArtifacts();

		TreeNode node = tree.findById(newResource.getId());
		if (node == null) {
			node = resourcesTreeDelta.get(newResource.getId());
		}
		boolean isParent = newResource.getType() != null && (newResource.getType().equals("ruletemplateinstance") || newResource.getType().equals("rulefunctionimpl")) && doGrouping;
		if (node == null) {
			if (shouldHideNavResource(newResource)) { return; }
			
			if (isNonLeafNode(newResource)) {
				resourcesTreeDelta.put(newResource.getId(), newResource);
			}			

			String parentId = null;
			if (isShowAsTree() && (newResource.getUIParent() != null && doGrouping)) {
				parentId = newResource.getUIParent();
			}
			else {
				if (isParent) {
					if (newResource.getUIParent() != null && doGrouping) {
						parentId = newResource.getUIParent();
					}
					else {
						parentId = newResource.getId().substring(0, newResource.getId().lastIndexOf("$"));
					}
				}
				else {
					parentId = (newResource.getId().indexOf("$") != -1) ? newResource.getId().substring(0, newResource.getId().lastIndexOf("$")) : newResource.getId();
				}
			}

			NavigatorResource parentNode = parentId != null ? resourcesTreeDelta.get(parentId) : null;
			if (parentNode == null || !parentNode.getChildren().contains(newResource)) {
				if ((newResource.getParent() != null && newResource.getParent().equals(ROOT_NODE))
					|| parentId.equals(newResource.getId())
					|| (!isShowAsTree() && !shouldShowHierarchicallyInList(newResource) && !isParent ) || parentId.equals(ROOT_NODE)) {
					if (newResource.getParent() == null) {
						parentId = ROOT_NODE;
						newResource.setParent(parentId);
					}
					else {
						newResource.setParent(parentId);
					}
					if (isShowAsTree()){
						newResource.setUIParent(parentId);
					}
	//				tree.add(newResource, tree.getRoot());
					if (rootNode == null) {
						rootNode = new NavigatorResource(ROOT_NODE,
								null,
								ROOT_NODE,
								null,
								null,
								null);
					}				
					parentNode = resourcesTreeDelta.get(ROOT_NODE);
					if (parentNode == null) {
						parentNode = rootNode;
						resourcesTreeDelta.put(ROOT_NODE, parentNode);
					}				
				} else {
					if (isShowAsTree() || isParent) {
						newResource.setUIParent(parentId);
					}
					newResource.setParent(parentId);
					if (parentNode == null) {
						if(isShowAsTree() || isParent) {
							parentNode = getParentNode(tree, rootNode, newResource, resourcesTreeDelta);
						}
						else {
							if (rootNode == null) {
								rootNode = new NavigatorResource(ROOT_NODE,
		 								null,
		 								ROOT_NODE,
		 								null,
		 								null,
		 								null);
							}
							parentNode = resourcesTreeDelta.get(ROOT_NODE);
							if (parentNode == null) {
								parentNode = rootNode;
		 						resourcesTreeDelta.put(ROOT_NODE, parentNode);
							}
						}
					} 
					
					resourcesTreeDelta.put(newResource.getUIParent(), parentNode);
//					tree.add(newResource, parentNode);
				}
				//Attach the resource to its parent 
				parentNode.getChildren().add(newResource);				
			}
		}
		else {
			if ( !isShowAsTree() && isParent ){
				tree.remove(node);
				addRecord(tree, rootNode, newResource, resourcesTreeDelta);
			}
		}	
	}	
	
	/**
	 * @param resource
	 * @return true, if the resource is Non-Leaf type, false other-wise
	 */
	private boolean isNonLeafNode(NavigatorResource resource) {
		return resource.getEditorType() != null && (resource.getEditorType().equals(ARTIFACT_TYPES.PROJECT)
				|| resource.getEditorType().equals(ARTIFACT_TYPES.FOLDER)
				|| resource.getEditorType().equals(ARTIFACT_TYPES.RULEFUNCTIONIMPL)
				|| resource.getEditorType().equals(ARTIFACT_TYPES.RULEFUNCTION)
				|| resource.getEditorType().equals(ARTIFACT_TYPES.EVENT)
				|| resource.getEditorType().equals(ARTIFACT_TYPES.CONCEPT)
				|| (resource.getEditorType().equals(ARTIFACT_TYPES.ARGUMENT)
					&& resource instanceof ParentArgumentNavigatorResource));		
	}
	
	/**
	 * Method to add resource to the given tree. 
	 * @param tree
	 * @param rootNode
	 * @param newResource
	 */
	private void addRecord(Tree tree, NavigatorResource rootNode, NavigatorResource newResource) {
		boolean doGrouping = WebStudio.get().getUserPreference().isGroupRelatedArtifacts();
		
		TreeNode node = tree.findById(newResource.getId());
		boolean isParent = newResource.getType() != null && (newResource.getType().equals("ruletemplateinstance") || newResource.getType().equals("rulefunctionimpl")) && doGrouping;
		if (node == null) {
			if (shouldHideNavResource(newResource)) { return; }
			String parentId = null;
			if (isShowAsTree() && (newResource.getUIParent() != null && doGrouping)){
				parentId = newResource.getUIParent();
			}
			else{
				if (isParent) {
					if (newResource.getUIParent() != null && doGrouping) {
						parentId = newResource.getUIParent();
					}
					else {
						parentId = newResource.getId().substring(0, newResource.getId().lastIndexOf("$"));
					}
				}
				else {
					parentId = (newResource.getId().indexOf("$") != -1) ? newResource.getId().substring(0, newResource.getId().lastIndexOf("$")) : newResource.getId();
				}
			}
		
			if ((newResource.getParent() != null && newResource.getParent().equals(ROOT_NODE))
				|| parentId.equals(newResource.getId())
				|| (!isShowAsTree() && !shouldShowHierarchicallyInList(newResource) && !isParent ) || parentId.equals(ROOT_NODE)) {
				if (newResource.getParent() == null) {
					parentId = ROOT_NODE;
					newResource.setParent(parentId);
				}
				else {
					newResource.setParent(parentId);
				}
				if (isShowAsTree()){
					newResource.setUIParent(parentId);
				}
//				tree.add(newResource, tree.getRoot());
				if (rootNode == null) {
					rootNode = new NavigatorResource(ROOT_NODE,
							null,
							ROOT_NODE,
							null,
							null,
							null);
				}				
				if (!rootNode.getChildren().contains(newResource))
					rootNode.getChildren().add(newResource);	
				int position = Collections.binarySearch(rootNode.getChildren(true), newResource, new NavigatorResource.ResourceNameComparator());				
				tree.move(newResource, tree.getRoot(), position);				
			} else {
				if (isShowAsTree() || isParent){
					newResource.setUIParent(parentId);
				}
				newResource.setParent(parentId);
				TreeNode parentNode;
					parentNode = (NavigatorResource) tree.findById(parentId);
				if (parentNode == null) {
					if(isShowAsTree() || isParent) {
						getParentNode(tree, rootNode, newResource, null);
					}
					parentNode =  ( isShowAsTree() || isParent ) ? getParentNode(tree, rootNode, newResource, null) : tree.getRoot();
				}
//				tree.add(newResource, parentNode);
				int position = 0;
				if (parentNode instanceof NavigatorResource) {
					((NavigatorResource)parentNode).getChildren().add(newResource);
					NavigatorResource parentResource = (NavigatorResource)parentNode;
					if (!parentResource.getChildren().contains(newResource))
						parentResource.getChildren().add(newResource);
					position = Collections.binarySearch(parentResource.getChildren(true), newResource, new NavigatorResource.ResourceNameComparator());					
				}
				if(position == 0) {
					tree.move(newResource, parentNode);
				} else {
					tree.move(newResource, parentNode, position);
				}
			}
		} 
		else {
			if ( !isShowAsTree() && isParent ){	
				tree.remove(node);
				addRecord(tree, rootNode, newResource);
			}
		} 
	}
	
	/**
	 * Get the parent resource for the artifact to be added
	 * 
	 * @param tree
	 * @param rootNode
	 * @param newResource
	 * @param resourcesTreeDelta
	 * @return
	 */
	private NavigatorResource getParentNode(Tree tree, NavigatorResource rootNode, NavigatorResource newResource, Map<String, NavigatorResource> resourcesTreeDelta) {
		boolean doGrouping = WebStudio.get().getUserPreference().isGroupRelatedArtifacts();
		String[] parts = (newResource.getUIParent() != null && doGrouping) ? newResource.getUIParent().split("\\$") : newResource.getId().substring(0,newResource.getId().lastIndexOf("$")).split("\\$");
		String parentPath = "";
		
		TreeNode parentNode = null;
		for (int i = 0; i < parts.length; i++) {
			String prevParent = parentPath;
			parentPath += (parentPath.isEmpty()) ? parts[i] : "$" + parts[i];
						
			parentNode = tree.findById(parentPath);
			if (parentNode == null) {
				if (prevParent == "") {
					parentNode = new NavigatorResource(parts[i],
							ROOT_NODE,
							parts[i],
							ARTIFACT_TYPES.PROJECT.getValue(),
							ICON_PATH + "studioproject.gif",
							ARTIFACT_TYPES.PROJECT);
				} else {
						if (parts[i].contains("rulefunction")){
							parentNode = new RuleFunctionNavigatorResource(
									parts[i], 
									prevParent.replace("/", "$"), 
									(prevParent + "$" + parts[i]).replace("/", "$"), 
									ARTIFACT_TYPES.RULEFUNCTION.getValue(), 
									ICON_PATH + "vrf_16x16.png",
									ARTIFACT_TYPES.RULEFUNCTION);							
						}
						else if (parts[i].contains("ruletemplate")){
							parentNode = new RuleTemplateNavigatorResource(
									parts[i], 
									prevParent.replace("/", "$"), 
									(prevParent + "$" + parts[i]).replace("/", "$"), 
									ARTIFACT_TYPES.RULETEMPLATE.getValue(), 
									ICON_PATH + "rulesTemplate.png",
									ARTIFACT_TYPES.RULETEMPLATE,null,null);							
						}
						else{
							parentNode = new NavigatorResource(
									parts[i], 
									prevParent.replace("/", "$"), 
									(prevParent + "$" + parts[i]).replace("/", "$"), 
									ARTIFACT_TYPES.FOLDER.getValue(), 
									ICON_PATH + "folder.png",
									ARTIFACT_TYPES.FOLDER);
						}
				}
				//logger.fine("Adding Parent Node: " + parentNode.toString());
				if (resourcesTreeDelta != null)
					addRecord(tree, rootNode, (NavigatorResource) parentNode, resourcesTreeDelta);
				else
					addRecord(tree, rootNode, (NavigatorResource) parentNode);
			}
		}
		
		return (NavigatorResource)parentNode;
	}
	
	/**
	 * Determines whether a particular NavigatorResource should be shown in the current display.
	 * If showing as tree, this will always return false;
	 * 
	 * @param resource NavigatorResource to show or hide.
	 * @return True when the NavigatorResource should be hidden.
	 */
	private boolean shouldHideNavResource(NavigatorResource resource){
		return !isShowAsTree()
			&& resource.getEditorType() != null
			&& (resource.getEditorType().equals(ARTIFACT_TYPES.FOLDER)
				|| resource.getEditorType().equals(ARTIFACT_TYPES.PROJECT));
	}
	
	/**
	 * Determines whether a particular NavigatorResource should be shown as a hierarchical child.
	 * This is only used when the grid is not displayed as a full tree.
	 * 
	 * @param resource NavigatorResource to show or hide.
	 * @return True when the NavigatorResource should be shown as a hierarchical child.
	 * 		False when the NavigatorResource should be shown as a first-level or root element of
	 * 		the list.
	 */
	private boolean shouldShowHierarchicallyInList(NavigatorResource resource){
		return !resource.getShouldShowAsRootNode()
			&& resource.getEditorType() != null
			&& (resource.getEditorType().equals(ARTIFACT_TYPES.EVENT)
				|| resource.getEditorType().equals(ARTIFACT_TYPES.CONCEPT)
				|| resource.getEditorType().equals(ARTIFACT_TYPES.ARGUMENT));
	}

	
	/**
	 * Remove the selected artifact from the project explorer.
	 * 
	 * @param artifactPath
	 * @param projectName
	 * @param removeParentIfEmpty
	 */
	public void removeRecord(String artifactPath, String projectName, boolean removeParentIfEmpty){
		String nodeId = projectName + artifactPath.replace("/", "$");
		removeRecord(resourceTree, resourceTreeRootNode, nodeId, removeParentIfEmpty);
		
	}
	
	/**
	 * Remove the node based on the specified node id
	 * 
	 * @param nodeId
	 * @param removeParentIfEmpty
	 */
	public void removeRecord(String nodeId, boolean removeParentIfEmpty) {
		removeRecord(resourceTree, resourceTreeRootNode, nodeId, removeParentIfEmpty);
		if(null != searchTree) {
			removeRecord(getSearchTree(), searchTreeRootNode, nodeId, removeParentIfEmpty);
		}
	}
	
	/**
	 * Remove the node based on the specified node id
	 * 
	 * @param nodeId
	 * @param removeParentIfEmpty
	 */
	private void removeRecord(Tree tree, NavigatorResource rootNode, String nodeId, boolean removeParentIfEmpty) {
		boolean doGrouping = WebStudio.get().getUserPreference().isGroupRelatedArtifacts();
		NavigatorResource selectedNode = getByResourceId(tree, nodeId);
		if (selectedNode != null){
			tree.remove(selectedNode);
			
			String parentId;
			if ((selectedNode.getUIParent() != null && doGrouping) || ((null != selectedNode.getParent() && !selectedNode.getUIParent().isEmpty()) && doGrouping)) {
				parentId = selectedNode.getUIParent();
			}
			else {
				parentId = (selectedNode.getId().indexOf("$") != -1) ? selectedNode.getId().substring(0, selectedNode.getId().lastIndexOf("$")) : selectedNode.getId();
			}
			NavigatorResource parentNode = (NavigatorResource) tree.findById(parentId);
			if (parentNode != null) {
				parentNode.getChildren().remove(selectedNode);
				removeParentNode(tree, rootNode, parentNode, removeParentIfEmpty);
			} else if (null != rootNode) {
				rootNode.getChildren().remove(selectedNode);
			} 
		} 
		if (rootNode != null && rootNode.getChildren().isEmpty())
			rootNode = null;		
	}
	
	
	/**
	 * Remove parent folders if empty
	 * 
	 * @param selectedNode
	 * @param removeParentIfEmpty
	 */
	private void removeParentNode(Tree tree, NavigatorResource rootNode, NavigatorResource selectedNode, boolean removeParentIfEmpty) {
		String parentId = (selectedNode.getId().indexOf("$") != -1) ? selectedNode.getId().substring(0, selectedNode.getId().lastIndexOf("$")) : selectedNode.getId();
		NavigatorResource parentNode = (NavigatorResource) tree.findById(parentId);

		if (removeParentIfEmpty && selectedNode.getChildren().size() == 0) {
			tree.remove(selectedNode);
			
			if (parentNode != null) {
				parentNode.getChildren().remove(selectedNode);
				removeParentNode(tree, rootNode, parentNode, removeParentIfEmpty);
			} else if (null != rootNode) {
				rootNode.getChildren().remove(selectedNode);
			}
		}
		if (rootNode != null && rootNode.getChildren().isEmpty())
			rootNode = null;		
	}
	
	/**
	 * Find Resource by Id
	 * 
	 * @param id
	 * @return
	 */
	public NavigatorResource getResourceById(String id) {
		return (NavigatorResource) resourceTree.findById(id);
	}

	/**
	 * Check if the given project is already checked out
	 * 
	 * @param projectName
	 * @return
	 */
	public boolean projectExists(String projectName) {
		if (resourceTree == null) {
			return false;
		}
		TreeNode[] projectList = resourceTree.getChildren(resourceTree.getRoot());

		for (TreeNode project : projectList) {
			if (project.getName().equals(projectName)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * @return
	 */
	public boolean isShowAsTree() {
		return showAsTree;
	}

	/**
	 * Return whether the setting was changed
	 * 
	 * @param showAsTree
	 * @return
	 */
	public boolean setShowAsTree(boolean showAsTree) {
		this.cellFormatter.setShowAsTree(showAsTree);
		if (this.showAsTree != showAsTree) {
			this.showAsTree = showAsTree;
			return true;
		}
		return false;
	}

	/**
	 * Reload all Tree Nodes
	 * 
	 * @param resources
	 */
	public void reloadTreeNodes(List<NavigatorResource> resources) {
		ListGridRecord selectedRecord = getSelectedRecord();
		String selectedNodeName = null;
		if (null != selectedRecord) {
			selectedNodeName = selectedRecord.getAttributeAsString("name");
		}
		
		removeAllNodes();
		addToResourceNavigator((NavigatorResource[]) resources.toArray(new NavigatorResource[resources.size()]));
		// If in search mode add the matching node to search tree.
		if (searchKeyword != null
				&& !searchKeyword.equals("")) {
			selectRecord(searchTree,selectedNodeName);
		} else {
			selectRecord(resourceTree,selectedNodeName);
		}
	}

	/**
	 * Remove all nodes
	 */
	private void removeAllNodes() {
		TreeNode[] allNodes = resourceTree.getAllNodes();
		for (TreeNode treeNode : allNodes) {
			resourceTree.remove(treeNode);
		}
		resourceTreeRootNode = null;
		searchTree = null;
		searchTreeRootNode = null;
	}
	
	/**
	 * Fetch resources by type
	 * 
	 * @param resourceType
	 * @return
	 */
	public String[] getNodesByType(String[] resourceType) {
		List<String> nodeList = new ArrayList<String>();
		
		TreeNode[] allNodes = resourceTree.getAllNodes();
		for (TreeNode res : allNodes) {
			if (res instanceof NavigatorResource) {
				NavigatorResource navResource = (NavigatorResource) res;
				if (ProjectExplorerUtil.matchesType(navResource.getName(), resourceType)) {
					nodeList.add(navResource.getId());
				}
			}
		}
		
		return nodeList.toArray(new String[nodeList.size()]);
	}
	
	/**
	 * Check if the specified resource already exits
	 * 
	 * @param resourceId
	 * @return
	 */
	public boolean resourceNameExits(String resourceId) {
		boolean doGrouping = WebStudio.get().getUserPreference().isGroupRelatedArtifacts();
		
		String resourcePath = resourceId;
		if (resourceId.indexOf(".") != -1) {
			resourcePath = resourceId.substring(0, resourceId.lastIndexOf("."));
		}
		String parentId = resourceId.substring(0, resourceId.lastIndexOf("$"));
		NavigatorResource parentResource = getResourceById(parentId);

		if (parentResource != null) {
			List<NavigatorResource> childResources = parentResource.getChildren();
			for (NavigatorResource child : childResources) {
				String childPath = (child.getUIParent() != null && doGrouping) ? ( child.getUIParent() + "$" + child.getName() ) : child.getId();
				if (childPath.indexOf(".") != -1){
					childPath = childPath.substring(0, childPath.lastIndexOf("."));
				}
				if (childPath.toLowerCase().equals(resourcePath.toLowerCase())) {
					return true;
				}
			}
		} else {
			TreeNode[] allNodes = resourceTree.getAllNodes();
			for (TreeNode res : allNodes) {
				if (res instanceof NavigatorResource) {
					NavigatorResource navResource = (NavigatorResource) res;
					String nodePath = (navResource.getId().indexOf(".") != -1) ? navResource.getId().substring(0, navResource.getId().indexOf(".")) : navResource.getId();
					if (nodePath.toLowerCase().equals(resourcePath.toLowerCase())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Get the artifact from the tree matching the specified resource
	 * 
	 * @param resource
	 * @return
	 */
	public NavigatorResource getByResource(NavigatorResource resource) {
		NavigatorResource selectedResource = getResourceById(resource.getId());

		if (selectedResource == null) {
			TreeNode[] allNodes = resourceTree.getAllNodes();
			for (TreeNode res : allNodes) {
				if (res instanceof NavigatorResource) {
					NavigatorResource navResource = (NavigatorResource) res;
					if (navResource.getId().equalsIgnoreCase(resource.getId())) {
						selectedResource = navResource;
						break;
					}
				}
			}
		}
		return selectedResource;
	}
	
	/**
	 * Get the artifact from the tree matching the specified resource Id
	 * 
	 * @param tree
	 * @param resource
	 * @return
	 */
	public NavigatorResource getByResourceId(Tree tree, String resourceId) {
		NavigatorResource selectedResource = getResourceById(resourceId);

		if (selectedResource == null) {
			TreeNode[] allNodes = tree.getAllNodes();
			for (TreeNode res : allNodes) {
				if (res instanceof NavigatorResource) {
					NavigatorResource navResource = (NavigatorResource) res;
					if (navResource.getId().equalsIgnoreCase(resourceId)) {
						selectedResource = navResource;
						break;
					}
				}
			}
		}
		return selectedResource;
	}
	
	/**
	 * 
	 * @return
	 */
	public NavigationHandler getClickHandler() {
		return clickHandler;
	}
	@Override
	public void onKeyPress(KeyPressEvent event) {
		String keyName = event.getKeyName();
		if ("Delete".equals(keyName)) {
				ToolStripButton deleteButton = WebStudio.get().getApplicationToolBar().getDeleteButton();
				deleteButton.fireEvent(new ClickEvent(deleteButton.getJsObj()));
		}
	}
}