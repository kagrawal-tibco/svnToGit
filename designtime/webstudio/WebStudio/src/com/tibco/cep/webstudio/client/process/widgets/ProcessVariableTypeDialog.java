package com.tibco.cep.webstudio.client.process.widgets;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedEvent;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.groups.ContentGroup;
import com.tibco.cep.webstudio.client.groups.ContentModelManager;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.process.properties.tabs.ProcessPropertyTabSet;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.client.widgets.AbstractWebStudioDialog;
import com.tibco.cep.webstudio.client.widgets.WebStudioMenubar;

/**
 * This dialog box is used to show the dialog for selection of process variable
 * type
 * 
 * @author dijadhav
 * 
 */
public class ProcessVariableTypeDialog extends AbstractWebStudioDialog {
	private static final String CONCEPT = "concept";
	private ListGridRecord record;
	private String ROOT_NODE = "Root";
	private String selectedType;
	private String selectedPath;
	private ListGrid processVariableGrid;
	private ProcessPropertyTabSet processPropertyTabSet;
	private GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	/**
	 * Path for images
	 */
	protected String iconPath = Page.getAppImgDir() + "icons/16/";

	public ProcessVariableTypeDialog(ListGridRecord record,
			ListGrid processVariableGrid,
			ProcessPropertyTabSet processPropertyTabSet) {
		this.record = record;
		this.processVariableGrid = processVariableGrid;
		this.processPropertyTabSet = processPropertyTabSet;
		setTitle("Variable Property Selector");
		this.setDialogWidth(400);
		this.setDialogHeight(250);
		this.setDialogTitle("Variable Property Selector");
		this.initialize();
		setIsModal(true);
		setShowModalMask(true);
		setDialogHeaderIcon(WebStudioMenubar.ICON_PREFIX + "tibco16-32.gif");
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

		StaticTextItem typeLabel = new StaticTextItem("");
		typeLabel.setValue("Types");
		typeLabel.setShowTitle(false);
		typeLabel.setColSpan(2);
		typeLabel.setWidth("100%");

		final ListGrid variableTypesGrid = new ListGrid();
		variableTypesGrid.setWidth100();
		variableTypesGrid.setShowHeader(false);
		ListGridField listGridField = new ListGridField("type");
		listGridField.setShowTitle(false);
		listGridField.setValueIcons(getTypeFieldValueIconMap());

		variableTypesGrid.setFields(new ListGridField[] { listGridField });
		String[] types = { "String", "int", "long", "double", "boolean",
				"DateTime", "ContainedConcept", "ConceptReference" };

		ListGridRecord[] listGridRecords = new ListGridRecord[types.length];
		int index = 0;
		for (String type : types) {
			if (null != type) {
				ListGridRecord listGridRecord = new ListGridRecord();
				listGridRecord.setAttribute("type", type);
				listGridRecords[index] = listGridRecord;
				index++;
			}
		}
		variableTypesGrid.setRecords(listGridRecords);

		VLayout layout = new VLayout();
		layout.addChild(variableTypesGrid);

		CanvasItem canvasItem = new CanvasItem();
		canvasItem.setShowTitle(false);
		canvasItem.setColSpan(2);
		canvasItem.setWidth("100%");
		canvasItem.setCanvas(layout);

		// Setup the tree
		final Tree resourceTree = new Tree();
		resourceTree.setModelType(TreeModelType.PARENT);
		resourceTree.setIdField("id");
		resourceTree.setParentIdField("parent");
		resourceTree.setNameProperty("name");
		TreeNode root = new TreeNode(ROOT_NODE);
		resourceTree.setRoot(root);
		final TreeGrid resourceTreeGrid = new TreeGrid();
		resourceTreeGrid.setData(resourceTree);
		resourceTreeGrid.setWidth("100%");
		resourceTreeGrid.setHeight(300);
		resourceTreeGrid.setAnimateFolders(true);
		resourceTreeGrid.setShowSortArrow(SortArrow.NONE);
		resourceTreeGrid.setShowHeaderContextMenu(false);
		resourceTreeGrid.setShowHeaderMenuButton(false);
		resourceTreeGrid.setShowHeader(false);
		resourceTreeGrid.setShowHeaderSpanTitlesInSortEditor(false);
		resourceTreeGrid.setSelectionType(SelectionStyle.SINGLE);
		resourceTreeGrid
				.addSelectionUpdatedHandler(new SelectionUpdatedHandler() {

					@Override
					public void onSelectionUpdated(SelectionUpdatedEvent event) {
						ListGridRecord selectedRecord = resourceTreeGrid
								.getSelectedRecord();
						if (null != selectedRecord
								&& selectedRecord instanceof NavigatorResource) {
							NavigatorResource navigatorResource = (NavigatorResource) selectedRecord;
							if (!(ARTIFACT_TYPES.PROJECT
									.equals(navigatorResource.getEditorType()) || ARTIFACT_TYPES.FOLDER
									.equals(navigatorResource.getEditorType()))) {
								final String selectedProject = WebStudio.get()
										.getCurrentlySelectedProject();
								String id = navigatorResource.getId();
								id = id.replace(selectedProject, "").replace(
										"$", "/");
								selectedPath = id.trim()
										.replace(".concept", "");
							}
						}
						ProcessVariableTypeDialog.this.okButton
								.setDisabled(false);
					}
				});

		final StaticTextItem selectResourceLabel = new StaticTextItem("");
		selectResourceLabel.setValue(globalMsgBundle.selectResourceDialog_tilteMessage());
		selectResourceLabel.setShowTitle(false);
		selectResourceLabel.setColSpan(2);
		selectResourceLabel.setWidth("100%");
		selectResourceLabel.setDisabled(true);

		VLayout layout1 = new VLayout();
		layout1.addChild(resourceTreeGrid);

		final CanvasItem canvasItem1 = new CanvasItem();
		canvasItem1.setShowTitle(false);
		canvasItem1.setColSpan(2);
		canvasItem1.setWidth("100%");
		canvasItem1.setCanvas(layout1);
		canvasItem1.setDisabled(true);
		selectResourceForm.setFields(typeLabel, canvasItem,
				selectResourceLabel, canvasItem1);
		container.addChild(selectResourceForm);

		variableTypesGrid
				.addSelectionUpdatedHandler(new VariableTypeSelectionUpdateHandler(
						selectResourceLabel, canvasItem1, variableTypesGrid,
						resourceTree));
		return container;
	}

	@Override
	public void onAction() {
		record.setAttribute("type", selectedType);
		record.setAttribute("path", selectedPath);
		processVariableGrid.redraw();
		this.processPropertyTabSet.invokeUpdateCommand(processVariableGrid
				.getRecords());
		this.destroy();
	}

	/**
	 * This method used to get the value icon map.
	 * 
	 * @return Map of Value Icon
	 */
	private Map<String, String> getTypeFieldValueIconMap() {
		Map<String, String> iconMap = new LinkedHashMap<String, String>();
		iconMap.put("String", iconPath + "iconString16.gif");
		iconMap.put("int", iconPath + "iconInteger16.gif");
		iconMap.put("long", iconPath + "iconLong16.gif");
		iconMap.put("double", iconPath + "iconReal16.gif");
		iconMap.put("boolean", iconPath + "iconBoolean16.gif");
		iconMap.put("ContainedConcept", iconPath + "iconConcept16.gif");
		iconMap.put("ConceptReference", iconPath + "iconConceptRef16.gif");
		iconMap.put("DateTime", iconPath + "iconDate16.gif");
		return iconMap;
	}

	private void buildResourceTree(NavigatorResource navResource,
			Tree resourceTree) {
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
							iconPath + "studioproject.gif",
							ARTIFACT_TYPES.PROJECT);
				} else {
					if (resourcePath.equals(navResource.getId())) {
						if (parts[i].contains(CONCEPT)) {
							resourceNode = new NavigatorResource(parts[i],
									prevParent.replace("/", "$"),
									(prevParent + "$" + parts[i]).replace("/",
											"$"),
									ARTIFACT_TYPES.CONCEPT.getValue(), iconPath
											+ "concept.png",
									ARTIFACT_TYPES.CONCEPT);

						}
					} else {
						resourceNode = new NavigatorResource(
								parts[i],
								prevParent.replace("/", "$"),
								(prevParent + "$" + parts[i]).replace("/", "$"),
								ARTIFACT_TYPES.FOLDER.getValue(), iconPath
										+ "folder.png", ARTIFACT_TYPES.FOLDER);
					}
				}
				if (null != resourceNode)
					resourceTree.add(resourceNode, parentFolder);
			}
			parentFolder = resourceNode;
		}
	}

	/**
	 * This is the variable type selection change handler
	 * 
	 * @author dijadhav
	 * 
	 */
	private final class VariableTypeSelectionUpdateHandler implements
			SelectionUpdatedHandler {
		private final StaticTextItem selectResourceLabel;
		private final CanvasItem canvasItem1;
		private final ListGrid variableTypesGrid;
		private final Tree resourceTree;

		private VariableTypeSelectionUpdateHandler(
				StaticTextItem selectResourceLabel, CanvasItem canvasItem1,
				ListGrid variableTypesGrid, Tree resourceTree) {
			this.selectResourceLabel = selectResourceLabel;
			this.canvasItem1 = canvasItem1;
			this.variableTypesGrid = variableTypesGrid;
			this.resourceTree = resourceTree;
		}

		@Override
		public void onSelectionUpdated(SelectionUpdatedEvent event) {
			ListGridRecord selectedRecord = variableTypesGrid
					.getSelectedRecord();
			if (null != selectedRecord) {
				ProcessVariableTypeDialog.this.okButton.setDisabled(true);
				selectedType = selectedRecord.getAttribute("type");
				if ("ContainedConcept".equals(selectedType)
						|| "ConceptReference".equals(selectedType)) {
					canvasItem1.setDisabled(false);
					selectResourceLabel.setDisabled(false);
					final String selectedProject = WebStudio.get()
							.getCurrentlySelectedProject();
					List<ContentGroup> contentGroups = ContentModelManager
							.getInstance().getGroups();
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
										if (ARTIFACT_TYPES.CONCEPT.getValue()
												.equals(CONCEPT)
												&& navigatorResource.getType()
														.equals(CONCEPT)) {
											buildResourceTree(
													navigatorResource,
													resourceTree);
										}
									}
								}
							}
						}
					}
				} else {
					ProcessVariableTypeDialog.this.okButton.setDisabled(false);
					canvasItem1.setDisabled(true);
					selectResourceLabel.setDisabled(true);
					TreeNode[] treeNodes = resourceTree.getAllNodes();
					if (null != treeNodes && treeNodes.length > 0) {
						resourceTree.removeList(treeNodes);
					}
				}
			}
		}
	}
}
