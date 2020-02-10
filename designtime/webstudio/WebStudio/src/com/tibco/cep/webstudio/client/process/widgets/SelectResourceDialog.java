package com.tibco.cep.webstudio.client.process.widgets;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedEvent;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.NodeClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeClickHandler;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableNavigatorResource;
import com.tibco.cep.webstudio.client.decisiontable.RuleFunctionNavigatorResource;
import com.tibco.cep.webstudio.client.groups.ContentGroup;
import com.tibco.cep.webstudio.client.groups.ContentModelManager;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;		
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.process.ProcessNavigatorResource;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.client.widgets.AbstractWebStudioDialog;
import com.tibco.cep.webstudio.client.widgets.WebStudioMenubar;
import com.tibco.cep.webstudio.server.model.ProcessElementTypes;

/**
 * This class is used to display the selected resource.
 * 
 * @author dijadhav
 * 
 */
public class SelectResourceDialog extends AbstractWebStudioDialog {
	private String resourceType;
	private String taskType;
	private TextItem resourceItem;
	private ListGrid listGrid;
	private TreeGrid resourceTreeGrid = new TreeGrid();
	private final String ICON_PATH = Page.getAppImgDir() + "icons/16/";
	private String ROOT_NODE = "Root";
	protected String selectedResource;
	protected TreeNode selectedRecord;
	protected CanvasItem canvasItem;
	protected Tree resourceTree;
	private GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	public SelectResourceDialog(String resourceType, String taskType,
			TextItem resourceItem, ListGrid rulesListGrid) {
		this.resourceType = resourceType;
		this.taskType = taskType;
		this.resourceItem = resourceItem;
		this.listGrid = rulesListGrid;
		setTitle(globalMsgBundle.selectResourceDialog_tilteMessage());
		this.setDialogWidth(400);
		this.setDialogHeight(250);
		this.setDialogTitle(globalMsgBundle.selectResourceDialog_tilteMessage());
		this.initialize();
		setIsModal(true);
		setShowModalMask(true);
		setDialogHeaderIcon(WebStudioMenubar.ICON_PREFIX  + "tibco16-32.gif");
	}

	public SelectResourceDialog() {
		setTitle(globalMsgBundle.selectResourceDialog_tilteMessage());
		this.setDialogWidth(400);
		this.setDialogHeight(250);
		this.setDialogTitle(globalMsgBundle.selectResourceDialog_tilteMessage());
		this.initialize();
		setIsModal(true);
		setShowModalMask(true);
		setDialogHeaderIcon(Page.getAppImgDir() + "icons/16/tibco16-32.png");
	}

	@Override
	public List<Widget> getWidgetList() {
		List<Widget> widgetList = new ArrayList<Widget>();
		widgetList.add(createForm());
		return widgetList;
	}

	private Widget createForm() {
		VLayout container = new VLayout(5);
		container.setWidth100();

		DynamicForm selectResourceForm = new DynamicForm();
		selectResourceForm.setWidth100();
		selectResourceForm.setNumCols(2);
		selectResourceForm.setAlign(Alignment.CENTER);

		StaticTextItem selectResourceLabel = new StaticTextItem("");
		selectResourceLabel.setValue(globalMsgBundle.selectResourceDialog_tilteMessage());
		selectResourceLabel.setShowTitle(false);
		selectResourceLabel.setColSpan(2);
		selectResourceLabel.setWidth("100%");

		final String selectedProject = WebStudio.get()
				.getCurrentlySelectedProject();

		// Setup the tree
		resourceTree = new Tree();
		resourceTree.setModelType(TreeModelType.PARENT);
		resourceTree.setIdField("id");
		resourceTree.setParentIdField("parent");
		resourceTree.setNameProperty("name");
		TreeNode root = new TreeNode(ROOT_NODE);
		resourceTree.setRoot(root);

		List<ContentGroup> contentGroups = ContentModelManager.getInstance()
				.getGroups();
		if (null != contentGroups && !contentGroups.isEmpty()) {
			for (ContentGroup contentGroup : contentGroups) {
				if (null != contentGroup
						&& ContentModelManager.PROJECTS_GROUP_ID
								.equals(contentGroup.getGroupId())) {
					List<NavigatorResource> navigatorResources = contentGroup
							.getResources();
					if (null != navigatorResources
							&& !navigatorResources.isEmpty()) {
						for (NavigatorResource navigatorResource : navigatorResources) {
							if (navigatorResource.getId().contains(
									selectedProject)) {
								if (null == navigatorResource.getType()) {
									String resourceName = navigatorResource
											.getName();
									if (null != resourceName
											&& resourceName
													.endsWith(resourceType)) {
										buildResourceTree(navigatorResource);
									}
								} else {
									if (ARTIFACT_TYPES.RULEFUNCTION.getValue()
											.equals(resourceType)
											&& navigatorResource.getType()
													.equals(resourceType)) {
										if (navigatorResource instanceof RuleFunctionNavigatorResource) {
											RuleFunctionNavigatorResource ruleFunctionNavigatorResource = (RuleFunctionNavigatorResource) navigatorResource;

											if (null == taskType
													&& !ruleFunctionNavigatorResource
															.isVRF()) {
												buildResourceTree(navigatorResource);
											} else {
												if (null != taskType
														&& ProcessElementTypes.BusinessRuleTask
																.getName()
																.equals(taskType)
														&& ruleFunctionNavigatorResource
																.isVRF()) {

													buildResourceTree(navigatorResource);
												}
											}
										}

									} else if (ARTIFACT_TYPES.EVENT.getValue()
											.equals(resourceType)
											&& navigatorResource.getType()
													.equals(resourceType)) {
										buildResourceTree(navigatorResource);
									} else if (ARTIFACT_TYPES.RULE.getValue()
											.equals(resourceType)
											&& navigatorResource.getType()
													.equals(resourceType)) {
										buildResourceTree(navigatorResource);
									} else if (ARTIFACT_TYPES.PROCESS
											.getValue().equals(resourceType)
											&& navigatorResource.getType()
													.equals("process")) {
										if (navigatorResource instanceof ProcessNavigatorResource) {
											if (("Private"
													.equalsIgnoreCase(((ProcessNavigatorResource) navigatorResource)
															.getProcessType()))) {
												buildResourceTree(navigatorResource);
											}
										}
									}
								}
							}
						}
					}

				}
			}
		}

		resourceTreeGrid.setData(resourceTree);
		resourceTreeGrid.setWidth("100%");
		resourceTreeGrid.setHeight(300);
		resourceTreeGrid.setAnimateFolders(true);
		resourceTreeGrid.setShowSortArrow(SortArrow.NONE);
		resourceTreeGrid.setShowHeaderContextMenu(false);
		resourceTreeGrid.setShowHeaderMenuButton(false);
		resourceTreeGrid.setShowHeader(false);
		resourceTreeGrid.setShowHeaderSpanTitlesInSortEditor(false);
		if (ARTIFACT_TYPES.RULE.getValue().equals(resourceType)) {
			resourceTreeGrid.setSelectionType(SelectionStyle.MULTIPLE);
			resourceTreeGrid
					.setSelectionAppearance(SelectionAppearance.CHECKBOX);
			resourceTreeGrid.setShowSelectedStyle(true);
			resourceTreeGrid.setShowPartialSelection(true);
			resourceTreeGrid.setCascadeSelection(true);
			resourceTreeGrid
					.addSelectionUpdatedHandler(new SelectionUpdatedHandler() {

						@Override
						public void onSelectionUpdated(
								SelectionUpdatedEvent event) {
							ListGridRecord[] navigatorResources = resourceTreeGrid
									.getSelectedRecords();
							if (null != navigatorResources
									&& navigatorResources.length > 0) {
								SelectResourceDialog.this.okButton
										.setDisabled(false);
							} else {
								SelectResourceDialog.this.okButton
										.setDisabled(true);
							}
						}
					});

		} else {
			resourceTreeGrid.setSelectionType(SelectionStyle.SINGLE);
			resourceTreeGrid.addNodeClickHandler(new TreeNodeClickHandler(
					selectedProject));
		}

		if (null != resourceItem) {
			String resourceValue = (String) resourceItem.getValue();
			if (null != resourceValue && !resourceValue.trim().isEmpty()
					&& resourceValue.endsWith("rulefunction")) {
				String[] parts = resourceValue.split("/");
				TreeNode selectedRecord = new RuleFunctionNavigatorResource(
						parts[parts.length - 1],
						parts[parts.length - 2].replace("/", "$"),
						resourceValue.replace("/", "$"),
						ARTIFACT_TYPES.RULEFUNCTION.getValue(), ICON_PATH
								+ "rulefunction.png",
						ARTIFACT_TYPES.RULEFUNCTION);
				if (null != selectedRecord) {
					resourceTreeGrid.selectRecord(selectedRecord);
				}
			}
		}
		VLayout layout = new VLayout();
		layout.addChild(resourceTreeGrid);
		canvasItem = new CanvasItem();
		canvasItem.setShowTitle(false);
		canvasItem.setColSpan(2);
		canvasItem.setWidth("100%");
		canvasItem.setCanvas(layout);
		selectResourceForm.setFields(selectResourceLabel, canvasItem);
		container.addChild(selectResourceForm);
		return container;
	}

	@Override
	public void onAction() {

		if (null != taskType) {
			String selectedProject = WebStudio.get()
					.getCurrentlySelectedProject();
			if (ProcessElementTypes.InferenceTask.getName().equals(taskType)) {
				getRulesRecord(selectedProject);
			} else if (ProcessElementTypes.BusinessRuleTask.getName().equals(
					taskType)) {
				getRulefunctionImplRecord(selectedProject);
				resourceItem.setValue(selectedResource.trim().replace(
						"." + resourceType, ""));
				resourceItem.fireEvent(new ResourceValueChangeEvent(resourceItem
						.getJsObj()));			
			}
		} else {
			resourceItem.setValue(selectedResource.trim().replace(
					"." + resourceType, ""));
			resourceItem.fireEvent(new ResourceValueChangeEvent(resourceItem
					.getJsObj()));
		}
		this.destroy();

	}

	/**
	 * 
	 */
	private void getRulefunctionImplRecord(String selectedProject) {
		String nodeName = selectedResource.substring(
				selectedResource.lastIndexOf("/") + 1,
				selectedResource.length());
		List<RuleFunctionImplRecord> rulesImplRecord = new LinkedList<RuleFunctionImplRecord>();
		List<ContentGroup> contentGroups = ContentModelManager.getInstance()
				.getGroups();
		if (null != contentGroups && !contentGroups.isEmpty()) {
			for (ContentGroup contentGroup : contentGroups) {
				if (null != contentGroup
						&& ContentModelManager.PROJECTS_GROUP_ID
								.equals(contentGroup.getGroupId())) {
					List<NavigatorResource> navigatorResources = contentGroup
							.getResources();
					if (null != navigatorResources
							&& !navigatorResources.isEmpty()) {
						for (TreeNode treeNode : navigatorResources) {
							if (null != treeNode
									&& treeNode instanceof DecisionTableNavigatorResource) {
								DecisionTableNavigatorResource vrfImplResource = (DecisionTableNavigatorResource) treeNode;
								if (null != vrfImplResource
										&& vrfImplResource.getUIParent()
												.contains(nodeName)) {
									String id = vrfImplResource.getId();
									id = id.replace(selectedProject, "")
											.replace("$", "/");
									String vrfImpl = id.trim().replace(
											".rulefunctionimpl", "");
									rulesImplRecord
											.add(new RuleFunctionImplRecord(
													vrfImpl, false));
								}
							}
						}
					}
				}
			}
		}
		RuleFunctionImplRecord[] records = new RuleFunctionImplRecord[rulesImplRecord
				.size()];
		int index = 0;
		for (RuleFunctionImplRecord rulesRecord : rulesImplRecord) {
			if (null != rulesRecord) {
				records[index] = rulesRecord;
				index++;

			}

		}
		listGrid.setRecords(records);
	}

	/**
	 * @param selectedProject
	 */
	private void getRulesRecord(String selectedProject) {
		List<RulesRecord> rulesRecords = new LinkedList<RulesRecord>();
		ListGridRecord[] listGridRecords = resourceTreeGrid
				.getSelectedRecords();
		if (null != listGridRecords && listGridRecords.length > 0) {
			for (ListGridRecord listGridRecord : listGridRecords) {
				if (null != listGridRecord
						&& listGridRecord instanceof NavigatorResource) {
					NavigatorResource navigatorResource = (NavigatorResource) listGridRecord;
					if (!(ARTIFACT_TYPES.PROJECT.equals(navigatorResource
							.getEditorType()) || ARTIFACT_TYPES.FOLDER
							.equals(navigatorResource.getEditorType()))) {
						String id = navigatorResource.getId();
						id = id.replace(selectedProject, "").replace("$", "/");
						selectedResource = id.trim().replace(".rule", "");
						rulesRecords.add(new RulesRecord(selectedResource));
					}
				}
			}
		}
		RulesRecord[] records = new RulesRecord[rulesRecords.size()];
		int index = 0;
		for (RulesRecord rulesRecord : rulesRecords) {
			if (null != rulesRecord) {
				records[index] = rulesRecord;
				index++;

			}

		}
		listGrid.setRecords(records);
	}

	private void buildResourceTree(NavigatorResource navResource) {
		TreeNode parentFolder = resourceTree.getRoot();
		TreeNode resourceNode = null;
		String resourcePath = "";
		String[] parts = navResource.getId().split("\\$");

		for (int i = 0; i < parts.length; i++) {
			String prevParent = resourcePath;
			resourcePath += (resourcePath.isEmpty()) ? parts[i] : "$"
					+ parts[i];
			resourceNode = resourceTree.findById(resourcePath);
			if (resourceNode == null) {
				if (prevParent == "") {
					resourceNode = new NavigatorResource(parts[i], ROOT_NODE,
							parts[i], ARTIFACT_TYPES.PROJECT.getValue(),
							ICON_PATH + "studioproject.gif",
							ARTIFACT_TYPES.PROJECT);
				} else {
					if (resourcePath.equals(navResource.getId())) {
						if (parts[i].contains("rulefunction")) {
							resourceNode = new RuleFunctionNavigatorResource(
									parts[i], prevParent.replace("/", "$"),
									(prevParent + "$" + parts[i]).replace("/",
											"$"),
									ARTIFACT_TYPES.RULEFUNCTION.getValue(),
									ICON_PATH + "rulefunction.png",
									ARTIFACT_TYPES.RULEFUNCTION);
						} else if (parts[i].contains("event")) {
							resourceNode = new NavigatorResource(parts[i],
									prevParent.replace("/", "$"),
									(prevParent + "$" + parts[i]).replace("/",
											"$"),
									ARTIFACT_TYPES.EVENT.getValue(), ICON_PATH
											+ "event.png", ARTIFACT_TYPES.EVENT);
						} else if (parts[i].contains("rule")) {
							resourceNode = new NavigatorResource(parts[i],
									prevParent.replace("/", "$"),
									(prevParent + "$" + parts[i]).replace("/",
											"$"),
									ARTIFACT_TYPES.RULE.getValue(), ICON_PATH
											+ "rule.png", ARTIFACT_TYPES.RULE);
						} else if (parts[i].contains("beprocess")) {
							resourceNode = new NavigatorResource(parts[i],
									prevParent.replace("/", "$"),
									(prevParent + "$" + parts[i]).replace("/",
											"$"),
									ARTIFACT_TYPES.PROCESS.getValue(),
									ICON_PATH + "appicon16x16.gif", ARTIFACT_TYPES.PROCESS);
						} else {
							resourceNode = new NavigatorResource(parts[i],
									prevParent.replace("/", "$"),
									(prevParent + "$" + parts[i]).replace("/",
											"$"), null, null, null);
						}
					} else {
						resourceNode = new NavigatorResource(
								parts[i],
								prevParent.replace("/", "$"),
								(prevParent + "$" + parts[i]).replace("/", "$"),
								ARTIFACT_TYPES.FOLDER.getValue(), ICON_PATH
										+ "folder.png", ARTIFACT_TYPES.FOLDER);
					}
				}

				resourceTree.add(resourceNode, parentFolder);
			}
			parentFolder = resourceNode;
		}
	}

	/**
	 * Event class which is used to fire to resource value change.
	 * 
	 * @author dijadhav
	 * 
	 */
	private final class ResourceValueChangeEvent extends ChangedEvent {

		private ResourceValueChangeEvent(JavaScriptObject jsObj) {
			super(jsObj);
		}

		@Override
		public FormItem getItem() {
			return resourceItem;
		}

		@Override
		public Object getValue() {
			return resourceItem.getValue();
		}

	}

	/**
	 * Class which holds the Rules record.
	 * 
	 * @author dijadhav
	 * 
	 */
	private final class RulesRecord extends ListGridRecord {
		private RulesRecord(String rulePath) {
			setRulePath(rulePath);
		}

		public void setRulePath(String rulePath) {
			setAttribute("rulePath", rulePath);
		}

		public String getRulePath() {
			return getAttributeAsString("rulePath");
		}
	}

	/**
	 * Class which holds the RuleFunctionImplementation record.
	 * 
	 * @author dijadhav
	 * 
	 */
	private final class RuleFunctionImplRecord extends ListGridRecord {
		private RuleFunctionImplRecord(String rulePath, boolean isDeployed) {
			setRulePath(rulePath);
			setIsDeployed(isDeployed);
		}

		public void setIsDeployed(boolean isDeployed) {
			setAttribute("checked", isDeployed);
		}

		public boolean getIsDeployed() {
			return getAttributeAsBoolean("checked");
		}

		public void setRulePath(String ruleImlURI) {
			setAttribute("ruleImlURI", ruleImlURI);
		}

		public String getRulePath() {
			return getAttributeAsString("ruleImlURI");
		}
	}

	/**
	 * This is the handler when user clicks on the tree node.
	 * 
	 * @author dijadhav
	 * 
	 */
	private final class TreeNodeClickHandler implements NodeClickHandler {
		private final String selectedProject;

		private TreeNodeClickHandler(String selectedProject) {
			this.selectedProject = selectedProject;
		}

		@Override
		public void onNodeClick(NodeClickEvent event) {
			TreeNode treeNode = event.getNode();
			if (treeNode instanceof NavigatorResource) {
				NavigatorResource navigatorResource = (NavigatorResource) treeNode;
				if (!(ARTIFACT_TYPES.PROJECT.equals(navigatorResource
						.getEditorType()) || ARTIFACT_TYPES.FOLDER
						.equals(navigatorResource.getEditorType()))) {
					selectedRecord = navigatorResource;
					String id = navigatorResource.getId();
					id = id.replace(selectedProject, "").replace("$", "/");
					selectedResource = id.trim();
					SelectResourceDialog.this.okButton.setDisabled(false);
				} else {
					SelectResourceDialog.this.okButton.setDisabled(true);
				}
			}
		}
	}
}