package com.tibco.cep.webstudio.client.handler;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.getArgumentProperties;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.getArguments;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.indeterminateProgress;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.populateArguments;
import static com.tibco.cep.webstudio.client.util.ArtifactUtil.addHandlers;
import static com.tibco.cep.webstudio.client.util.ArtifactUtil.getArtifactURL;
import static com.tibco.cep.webstudio.client.util.ArtifactUtil.isDecisionManagerInstalled;
import static com.tibco.cep.webstudio.client.util.ArtifactUtil.isBPMNInstalled;
import static com.tibco.cep.webstudio.client.util.ArtifactUtil.isSupportedArtifact;
import static com.tibco.cep.webstudio.client.util.ArtifactUtil.removeHandlers;
import static com.tibco.cep.webstudio.client.util.ErrorMessageDialog.showError;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tree.events.NodeContextClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeContextClickHandler;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.ArgumentNavigatorResource;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableNavigatorResource;
import com.tibco.cep.webstudio.client.decisiontable.ImportDecisionTableDialog;
import com.tibco.cep.webstudio.client.decisiontable.ParentArgumentNavigatorResource;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.editor.EditorFactory;
import com.tibco.cep.webstudio.client.editor.IEditorFactory;
import com.tibco.cep.webstudio.client.groups.ContentModelManager;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.DTMessages;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.portal.DASHBOARD_PORTLETS;
import com.tibco.cep.webstudio.client.problems.ProblemMarker;
import com.tibco.cep.webstudio.client.process.ProcessNavigatorResource;
import com.tibco.cep.webstudio.client.util.ArtifactDeletionHelper;
import com.tibco.cep.webstudio.client.util.ArtifactRenameHelper;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.client.widgets.CreateNewArtifactDialog;
import com.tibco.cep.webstudio.client.widgets.WebStudioNavigatorGrid;
import com.tibco.cep.webstudio.client.widgets.WebStudioToolbar;

/**
 * Handles all Project Navigation operations. Following events are supported,
 * 
 * Tree Node Single Click Tree Node Double click Tree Node Context menu Click
 * Http Success/Failure events
 */
public class NavigationHandler implements RecordClickHandler, RecordDoubleClickHandler, NodeContextClickHandler,
		ClickHandler, HttpSuccessHandler, HttpFailureHandler {
	private Menu contextMenu;
	private NavigatorResource selectedResource;
	private String dashboardArtifactProject, dashboardArtifactPath;
	private DASHBOARD_PORTLETS selectedPortlet;
	
	private GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private DTMessages dtMsgs = (DTMessages) I18nRegistry.getResourceBundle(I18nRegistry.DT_MESSAGES);
	
	private static final String MENUITEM_ARTIFACT_RENAME_ID = "id_artifact_rename";

	public void setContextMenu(Menu contextMenu) {
		this.contextMenu = contextMenu;
	}

	public void setSelectedResource(NavigatorResource selectedResource) {
		this.selectedResource = selectedResource;
	}

	public NavigatorResource getSelectedResource() {
		return this.selectedResource;
	}

	public NavigationHandler() {
	}

	/**
	 * @see com.smartgwt.client.widgets.grid.events.RecordClickHandler#onRecordClick(com.smartgwt.client.widgets.grid.events.RecordClickEvent)
	 */
	@Override
	public void onRecordClick(RecordClickEvent event) {
		NavigatorResource record = (NavigatorResource) event.getRecord();
		ProjectExplorerUtil.captureRecordSelection(record);		
		WebStudio.get().getStatusbar().updateStatus(record.getId().replace("$", "/"), record.getIcon());
		
		ProjectExplorerUtil.toggleToolbarOptions(record);
	}

	/**
	 * @see com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler#onRecordDoubleClick(com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent)
	 */
	@Override
	public void onRecordDoubleClick(RecordDoubleClickEvent event) {
		NavigatorResource record = (NavigatorResource) event.getRecord();
		restoreGroupPortlet();
		
		this.createPage(record);
	}

	/**
	 * Create editors for selected Navigation record Node
	 * 
	 * @param record
	 */
	public void createPage(NavigatorResource record) {
		createPage(record, true);
	}
	
	/**
	 * Create editors for selected Navigation record Node
	 * 
	 * @param record
	 *            {@link NavigatorResource}
	 * @param openResource
	 */
	public void createPage(NavigatorResource record, boolean openResource) {
		if (isSupportedArtifact(record)) {
			if (record.getType().equals(ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue().toLowerCase()) && !isDecisionManagerInstalled(true)) {
				return;
			}
			
			if (record.getType().equals(ARTIFACT_TYPES.PROCESS.getValue().toLowerCase()) && !isBPMNInstalled(true)) {
				return;
			}
			
			Tab[] tabs = WebStudio.get().getEditorPanel().getTabs();
			for (Tab tab : tabs) {
				if (tab instanceof AbstractEditor) {
					if (record.equals(((AbstractEditor) tab).getSelectedResource()) && !((AbstractEditor) tab).isReadOnly() && ((AbstractEditor) tab).getRevisionId() == null) {
						WebStudio.get().getEditorPanel().selectTab(tab);
						return;
					}
				}
			}
	
			Tab editor = null;
			IEditorFactory editorFactory = EditorFactory.getArtifactEditorFactory(record);
			editor = editorFactory.createEditor(record);
			
			WebStudio.get().getEditorPanel().addTab(editor);
			WebStudio.get().getEditorPanel().selectTab(editor);
			
			WebStudio.get().showWorkSpacePage();
			if(openResource) WebStudio.get().getWorkspacePage().openResourceTree(record.getId().replace("$", "/"));
			
			ProjectExplorerUtil.toggleToolbarOptions(record, WebStudioToolbar.TOOL_STRIP_VALIDATE_ID);
			
			String projectName = record.getId().substring(0,record.getId().indexOf("$"));
			String artifactPath = record.getId().substring(record.getId().indexOf("$"),record.getId().length());
			artifactPath = artifactPath.replaceAll("\\$", "/");
			
			// add to recently opened only if not already present
			if (! WebStudio.get().getPortalPage().checkIfDashboardArtifactExists(projectName, artifactPath, DASHBOARD_PORTLETS.RECENTLY_OPENED)) {
				this.addDashboardArtifacts(projectName, artifactPath, ServerEndpoints.RMS_PUT_RECENTLYOPENED_ARTIFACTS);
			}
		} else if (record.getType().equals("ruletemplate"))  {
			this.selectedResource = record;
			this.createNewResource(globalMsgBundle.createNew_resource_rti(), ".ruletemplateinstance");
		} else if (record.getType().equals("rulefunction"))  {
			this.selectedResource = record;
			this.createNewResource(globalMsgBundle.createNew_resource_dt(), ".rulefunctionimpl");
		} 
		else {
			CustomSC.warn(globalMsgBundle.explorer_OpenResource_error());
		}
	}
	
	/**
	 * Create editors for selected Navigation record Node
	 * 
	 * @param record
	 * @param marker
	 */
	public void createPage(NavigatorResource record, ProblemMarker marker) {
		createPage(record, true);
		((AbstractEditor)WebStudio.get().getEditorPanel().getSelectedTab()).setOnOpenMarker(marker);
	}
	
	/**
	 * Add the record to the recently opened list/favorites.
	 * 
	 * @param record
	 */
	private void addDashboardArtifacts(String projectName, String artifactPath, ServerEndpoints serverUrl) {
		addHandlers(this);
		
		HttpRequest request = new HttpRequest();
		request.setMethod(HttpMethod.PUT);
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectName));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_PATH, artifactPath));
		
		request.submit(serverUrl.getURL());
			
		selectedPortlet = (serverUrl.equals(ServerEndpoints.RMS_ADD_TO_FAVORITE_ARTIFACTS)) ? DASHBOARD_PORTLETS.MY_FAVORITES
				: DASHBOARD_PORTLETS.RECENTLY_OPENED;
		dashboardArtifactProject = projectName;
		dashboardArtifactPath = artifactPath;
	}

	/**
	 * @see com.smartgwt.client.widgets.tree.events.NodeContextClickHandler#onNodeContextClick(com.smartgwt.client.widgets.tree.events.NodeContextClickEvent)
	 */
	@Override
	public void onNodeContextClick(NodeContextClickEvent event) {
		this.selectedResource = (NavigatorResource) event.getNode();

		ProjectExplorerUtil.captureRecordSelection(this.selectedResource);

		boolean showFavourites = true, showRMS = true, showRuleTemplate = false, showArguments = false, showDT = false, showGroups = true, showDelete = true, showFolder = false, showExport = false, showImport = false,showNewProcess=false;
		if (this.selectedResource.getType() != null) {
			if (this.selectedResource.getType().equals(ARTIFACT_TYPES.PROJECT.getValue())) {
				showFavourites = showGroups = false;
			} else if (this.selectedResource.getType().equals(ARTIFACT_TYPES.RULETEMPLATE.getValue())) {
				showRuleTemplate = showRMS = showFavourites = true;
			} else if (this.selectedResource.getType().equals(ARTIFACT_TYPES.FOLDER.getValue())) {
				showFavourites = showGroups = false;
				showRMS = true;
				showNewProcess=true;
			} else if (this.selectedResource.getType().equals(ARTIFACT_TYPES.RULETEMPLATEINSTANCE.getValue())) {
				showExport = true;
			} else if (this.selectedResource.getType().equals(ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue())) {
				if (!isDecisionManagerInstalled(false)) {
					showArguments = showRMS = showFavourites = showExport = showDelete = false;
					event.cancel();
					return;
				}
				showArguments = showRMS = showFavourites = showExport = true;
				if (WebStudio.get().getPortalPage().isVisible()) {
					showArguments = false;
				}
				
			} else if (this.selectedResource.getType().equals(ARTIFACT_TYPES.RULEFUNCTION.getValue())) {
				if (!isDecisionManagerInstalled(false)) {
					showArguments = showRMS = showFavourites = showDT =  showImport = showExport = showDelete = false;
					event.cancel();
					return;
				}
				showDT = showImport = true;
			} else if (this.selectedResource.getType().equals(ARTIFACT_TYPES.PROCESS.getValue())) {
				if (!isBPMNInstalled(false)) {
					showArguments = showRMS = showFavourites = showExport = showDelete = false;					
					event.cancel();
					return;
				}
			}
			
			if (this.selectedResource instanceof ArgumentNavigatorResource) {
				ArgumentNavigatorResource argNavResource = (ArgumentNavigatorResource)selectedResource;
				showArguments = showRuleTemplate = showRMS = showFavourites = showGroups = showDelete = false;
				if (argNavResource.isParent()) {
					showArguments = true;
				}
			}
		}
		
		this.createContextMenu(showFavourites, showDelete, showRMS,
				showRuleTemplate, showArguments, showDT, showFolder, showExport, showImport, ProjectExplorerUtil.typeToEditorType(selectedResource.getType()),showNewProcess);
		if (showGroups) {
			this.addGroupsMenu();
		}

		// No contextmenu display for folder selections
		if (this.contextMenu.getItems().length == 0) {
			event.cancel();
		}
	}

	/**
	 * Menu option to add to available groups
	 */
	private void addGroupsMenu() {
		List<String> addToGroups = WebStudio.get().getWorkspacePage().getMyGroups()
				.getAddToGroups(this.selectedResource);
		
		List<String> removeFromGroups = WebStudio.get().getWorkspacePage().getMyGroups()
				.getRemoveFromGroups(this.selectedResource);

		if (addToGroups.size() > 0 || removeFromGroups.size() > 0) {
			MenuItem groupMenuItem = new MenuItem(globalMsgBundle.contextMenu_groups(), Page.getAppImgDir() + "icons/16/customgroup.png");
			this.contextMenu.addItem(groupMenuItem);
			
			Menu subGroupMenu = new Menu();
			if (addToGroups.size() > 0) {
				MenuItem addToGrpMenuItem = new MenuItem(globalMsgBundle.contextMenu_subMenu_addTo(), Page.getAppImgDir() + "icons/16/add.png");
				subGroupMenu.addItem(addToGrpMenuItem);
				
				addToGrpMenuItem.setSubmenu(createGroupMenuItems(addToGroups, true));
			}
			
			if (removeFromGroups.size() > 0) {
				MenuItem removeFromGrpMenuItem = new MenuItem(globalMsgBundle.contextMenu_subMenu_removeFrom(), Page.getAppImgDir() + "icons/16/delete.png");
				subGroupMenu.addItem(removeFromGrpMenuItem);
				
				removeFromGrpMenuItem.setSubmenu(createGroupMenuItems(removeFromGroups, false));
			}
			groupMenuItem.setSubmenu(subGroupMenu);
		}
	}
	
	/**
	 * Create menu items for group options
	 * 
	 * @param groups
	 * @param add
	 * @return
	 */
	private Menu createGroupMenuItems(List<String> groups, final boolean add) {
		Menu subMenu = new Menu();

		for (final String group : groups) {
			MenuItem subMenuOption = new MenuItem(group);
			subMenuOption.addClickHandler(new ClickHandler() {
				public void onClick(MenuItemClickEvent event) {
					if (add) {
						ContentModelManager.getInstance().addToGroup(group, selectedResource);
					} else {
						ContentModelManager.getInstance().removeFromGroup(group, selectedResource);
					}
				}
			});
			subMenu.addItem(subMenuOption);
		}
		
		return subMenu;
	}

	/**
	 * Dynamically create context menu options based on project artifact
	 * selected
	 * 
	 * @param showFavourites
	 * @param showDelete
	 * @param showRMS
	 * @param showRuleTemplate
	 * @param showArguments
	 * @param createNewDT
	 * @param createNewFolder
	 * @param showExport
	 * @param showNewProcess 
	 */
	private void createContextMenu(boolean showFavourites, 
			                       boolean showDelete, 
			                       boolean showRMS, 
			                       boolean showRuleTemplate, 
			                       boolean showArguments, 
			                       boolean createNewDT, 
			                       boolean createNewFolder,
			                       boolean showExport,
			                       boolean showImport,
			                       ARTIFACT_TYPES artifactType, boolean showNewProcess) {
		MenuItem menuItem = null;

		this.clearContextMenuItems();
//		if (showFavourites) {
//			menuItem = new MenuItem(this.globalMsgBundle.projectExplorer_favourites(), Page.getAppImgDir()
//					+ "icons/16/add.png");
//			menuItem.addClickHandler(this);
//			this.contextMenu.addItem(menuItem);
//		}
		if (showArguments) {
			menuItem = new MenuItem(this.selectedResource.getChildren().size() > 0? this.globalMsgBundle.projectExplorer_rf_hide_arguments():
				this.globalMsgBundle.projectExplorer_rf_arguments(), Page.getAppImgDir() + "icons/16/show_arguments.png");
			menuItem.addClickHandler(this);
			this.contextMenu.addItem(menuItem);
		}
		
		if (showDelete) {
			menuItem = new MenuItem(globalMsgBundle.contextMenu_delete(), Page.getAppImgDir() + "icons/16/delete.png");
			menuItem.addClickHandler(this);
			this.contextMenu.addItem(menuItem);
		}
		
		if (canRenameArtifact(artifactType)) {
			menuItem = new MenuItem(globalMsgBundle.menu_rmsRename());
			menuItem.setAttribute("id", MENUITEM_ARTIFACT_RENAME_ID);
			menuItem.addClickHandler(this);
			this.contextMenu.addItem(menuItem);
		}
		
		if (showRMS) {
			menuItem = new MenuItem(this.globalMsgBundle.menu_rms());
			Menu subMenu = new Menu();
			boolean showLockOption = ArtifactUtil.isLockingEnabled() && ArtifactUtil.isSupportedArtifact(this.selectedResource);
			WebStudio.get().getApplicationMenu().addRMSMenuOptions(subMenu, canViewRMSDiff(artifactType), showLockOption, selectedResource.isLocked(), canViewArtifactHistory(artifactType));
			menuItem.setSubmenu(subMenu);
			this.contextMenu.addItem(menuItem);
			
			subMenu.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					WebStudio.get().setRmsOptionViaContextMenu(true);
				}
			});
		}
		if (showRuleTemplate) {
			menuItem = new MenuItem(this.globalMsgBundle.projectExplorer_ruleTemplate(), Page.getAppImgDir() + "icons/16/rule.png");
			menuItem.addClickHandler(this);
			this.contextMenu.addItem(menuItem);
		}
		if (createNewDT) {
			menuItem = new MenuItem(this.globalMsgBundle.projectExplorer_new_dt(), Page.getAppImgDir() + "icons/16/decisiontable.png");
			menuItem.addClickHandler(this);
			this.contextMenu.addItem(menuItem);
		}
		if (createNewFolder) {
			menuItem = new MenuItem(this.globalMsgBundle.projectExplorer_new_folder(), Page.getAppImgDir() + "icons/16/folder.png");
			menuItem.addClickHandler(this);
			this.contextMenu.addItem(menuItem);
		}
		if (showExport) {
			menuItem = new MenuItem(this.globalMsgBundle.menu_export(), Page.getAppImgDir() + "icons/16/export.gif");
			menuItem.addClickHandler(this);
			this.contextMenu.addItem(menuItem);
		}
		if (showImport) {
			menuItem = new MenuItem(this.globalMsgBundle.menu_import(), Page.getAppImgDir() + "icons/16/import.gif");
			menuItem.addClickHandler(this);
			this.contextMenu.addItem(menuItem);
		}
		if (showNewProcess) {
			menuItem = new MenuItem(this.globalMsgBundle.projectExplorer_new_process(), Page.getAppImgDir()
					+ "icons/16/appicon16x16.gif");
			menuItem.addClickHandler(this);
			this.contextMenu.addItem(menuItem);
		}
	}

	/**
	 * Clears all existing menu options
	 */
	private void clearContextMenuItems() {
		for (MenuItem menuItem : this.contextMenu.getItems()) {
			this.contextMenu.removeItem(menuItem);
		}
	}

	/**
	 * @see com.smartgwt.client.widgets.menu.events.MenuItemClickEvent
	 *      @onClick(com.smartgwt.client.widgets.menu.events.MenuItemClickEvent)
	 */
	@Override
	public void onClick(MenuItemClickEvent event) {
		String applicationName = event.getItem().getTitle();
		String applicationId = event.getItem().getAttribute("id");
		
		if (this.globalMsgBundle.projectExplorer_ruleTemplate().equals(applicationName.trim())) {
			restoreGroupPortlet();
			this.createNewResource(globalMsgBundle.createNew_resource_rti(), ".ruletemplateinstance");
		} else if (this.globalMsgBundle.projectExplorer_rf_arguments().equals(applicationName.trim())) {
			this.createArguments();
		} 
		else if (this.globalMsgBundle.projectExplorer_rf_hide_arguments().equals(applicationName.trim())) {
			this.removeArguments(this.selectedResource);
		} else if (this.globalMsgBundle.projectExplorer_new_dt().equals(applicationName.trim())) {
			restoreGroupPortlet();
			this.createNewResource(globalMsgBundle.createNew_resource_dt(), ".rulefunctionimpl");
		} else if (this.globalMsgBundle.projectExplorer_new_folder().equals(applicationName.trim())) {
			this.createNewResource(globalMsgBundle.createNew_resource_folder(), null);
		} else if (this.globalMsgBundle.contextMenu_delete().equals(applicationName.trim())) {
			ArtifactDeletionHelper.getInstance().deleteArtifact(new NavigatorResource[]{this.selectedResource});
		} else if (this.globalMsgBundle.menu_export().equals(applicationName.trim())) {
			if (selectedResource.getType().equals(ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue().toLowerCase()) 
					&& !isDecisionManagerInstalled(true)) {
				return;
			}
			String rURL = getArtifactURL(selectedResource);
			if (rURL != null) { 
				Window.open(rURL, "_self", null);
			}
		} else if (this.globalMsgBundle.menu_import().equals(applicationName.trim())) {
			doImport();
		} else if (MENUITEM_ARTIFACT_RENAME_ID.equals(applicationId)) {
			ArtifactRenameHelper.getInstance().renameArtifact(this.selectedResource);
		}else if(this.globalMsgBundle.projectExplorer_new_process().equals(applicationName.trim())){
			this.createNewResource(globalMsgBundle.createNew_resource_process(), ".beprocess");
		}
	}	

	/**
	 * Create a Rule Template instance/DT
	 * 
	 * @param resourceName
	 * @param resourceExtn
	 */
	private void createNewResource(final String resourceType, final String resourceExtn) {
		if (this.selectedResource != null && this.selectedResource.getType() != null) {
			new CreateNewArtifactDialog(selectedResource, resourceType, resourceExtn).draw();
		}
	}

	/**
	 * Create required arguments for DT
	 */
	private void createArguments() {
		if (this.selectedResource.getChildren().size() > 0 ) {
			return;
		}
		indeterminateProgress(dtMsgs.dtArgumentFetchProgress(), false);
		addNavigatorHandlers();
		
		if (this.selectedResource instanceof ParentArgumentNavigatorResource) {
			getArgumentProperties(selectedResource);
		} else {
			getArguments(selectedResource);
		}
	}
	
	private void removeArguments(NavigatorResource parent) {
		WebStudioNavigatorGrid navGrid = WebStudio.get().getWorkspacePage().getGroupContentsWindow().getArtifactTreeGrid();
		NavigatorResource[] resources = new NavigatorResource[parent.getChildren().size()];
		parent.getChildren().toArray(resources);
		for (NavigatorResource resource : resources) {
			navGrid.removeRecord(resource.getId(), false);
			if (!(resource instanceof ParentArgumentNavigatorResource)) {
				NavigatorResource[] resources1 = new NavigatorResource[resource.getChildren().size()];
				resource.getChildren().toArray(resources1);
				for (NavigatorResource navigatorResource : resources1) {
					resource.getChildren().remove(navigatorResource);
				}
			}
			
			if(resource.getChildren().size() > 0) {
				removeArguments(resource);
			}
			
			parent.getChildren().remove(resource);
			ProjectExplorerUtil.updateGroups(resource, false);
			WebStudio.get().getWorkspacePage().getGroupContentsWindow().remove(resource);
		}
		ProjectExplorerUtil.updateGroups(parent, true);
	}

	/**
	 * Add the necessary handlers
	 */
	public void addNavigatorHandlers() {
		addHandlers(this);
	}
	
	public void createNewFolder(String folderName) {
		String parentId = this.selectedResource.getId();
		NavigatorResource navigatorResource = new NavigatorResource(folderName,
				parentId,
				parentId+ "$" + folderName,
				null,
				Page.getAppImgDir() + "icons/16/folder.png",
				ARTIFACT_TYPES.FOLDER);
		ProjectExplorerUtil.updateGroups(navigatorResource, true);
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		boolean validEvent = false;
		if ((event.getUrl().indexOf(ServerEndpoints.RMS_ADD_TO_FAVORITE_ARTIFACTS.getURL()) != -1)
				|| (event.getUrl().indexOf(ServerEndpoints.RMS_PUT_RECENTLYOPENED_ARTIFACTS.getURL()) != -1)) {
			WebStudio.get().getPortalPage().addToDashboardArtifactPortlet(dashboardArtifactProject, dashboardArtifactPath, selectedPortlet);
			
			validEvent = true;
		} 	
		//To Populate Arguments 
		else if (event.getUrl().indexOf(ServerEndpoints.RMS_SHOW_DT_ARGUMENTS.getURL()) != -1
				&& event.getUrl().indexOf(RequestParameter.REQUEST_PARAM_TYPE) != -1) {
			populateArguments(selectedResource, event.getData(), (selectedResource instanceof ArgumentNavigatorResource));
			validEvent = true;
		}
		if (validEvent) {
			removeHandlers(this);
		}
	}

	@Override
	public void onFailure(HttpFailureEvent event) {
		boolean validEvent = false;
		
		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL()) != -1) {
			validEvent = true;
		} else if ((event.getUrl().indexOf(ServerEndpoints.RMS_ADD_TO_FAVORITE_ARTIFACTS.getURL()) != -1)
				|| (event.getUrl().indexOf(ServerEndpoints.RMS_PUT_RECENTLYOPENED_ARTIFACTS.getURL()) != -1)) {
			validEvent = true;
		} else if (event.getUrl().indexOf(ServerEndpoints.RMS_SHOW_DT_ARGUMENTS.getURL()) != -1) {
			validEvent = true;
		}

		if (validEvent) {
			String responseMessage = event.getData().getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
			if ((event.getUrl().indexOf(ServerEndpoints.RMS_ADD_TO_FAVORITE_ARTIFACTS.getURL()) == -1)
					&& (event.getUrl().indexOf(ServerEndpoints.RMS_PUT_RECENTLYOPENED_ARTIFACTS.getURL()) == -1)) {
				showError(responseMessage);
			}
			
			removeHandlers(this);
		}
	}
		
	public void openDecisionTable(NavigatorResource parentResource, String folder, String newDecisionTableName) {
		String Id = folder + "$" + newDecisionTableName;
		DecisionTableNavigatorResource navigatorResource = new DecisionTableNavigatorResource(newDecisionTableName,
				folder,
				parentResource.getId(),
				Id,
				"rulefunctionimpl",
				Page.getAppImgDir() + "icons/16/decisiontable.png",
				ARTIFACT_TYPES.RULEFUNCTIONIMPL);

		// update project explorer and any DT specific group/s
		ProjectExplorerUtil.updateGroups(navigatorResource, true);
		this.createPage(navigatorResource);
	}
	
	public void openProcess(NavigatorResource selectedResource, String newProcessName) {
		String parentId = selectedResource.getId().substring(0, selectedResource.getId().lastIndexOf("$"));
		ProcessNavigatorResource navigatorResource = new ProcessNavigatorResource(newProcessName,
				parentId,
				parentId + "$" + newProcessName,
				"beprocess",
				Page.getAppImgDir() + "icons/16/appicon16x16.gif",
				ARTIFACT_TYPES.PROCESS);

		// update project explorer and any DT specific group/s
		ProjectExplorerUtil.updateGroups(navigatorResource, true);
		this.createPage(navigatorResource);
	}
	
	public void doImport() {
		this.selectedResource = (NavigatorResource)WebStudio.get().getWorkspacePage().getGroupContentsWindow()
		.getArtifactTreeGrid().getSelectedRecord();
		if (this.selectedResource != null &&
				this.selectedResource.getType() != null) {
			if (this.selectedResource.getType().equals("rulefunction") 
					&& ArtifactUtil.isDecisionManagerInstalled(true)) {
				new ImportDecisionTableDialog(selectedResource, globalMsgBundle.createNew_resource_dt()).draw();
			}
		} 
	}
	
	/**
	 * Restore Group portlet if switching to workspace is desired
	 */
	private void restoreGroupPortlet() {
		WebStudio.get().getPortalPage().restorePortlet(DASHBOARD_PORTLETS.GROUPS.getTitle());
	}
	
	/**
	 * Checks whether can view difference between base copy and working copy of an artifact, based on its type.
	 * @return
	 */
	private boolean canViewRMSDiff(ARTIFACT_TYPES artifactType) {
		if (artifactType == null) {
			return false;
		}
		switch (artifactType) {
		case RULETEMPLATEINSTANCE:
		case RULEFUNCTIONIMPL:
			return true;
		default:
			return false;
		}
	}
	
	/**
	 * Checks whether can view artifact history, based on its type.
	 * @return
	 */
	private boolean canViewArtifactHistory(ARTIFACT_TYPES artifactType) {
		if (artifactType == null) {
			return false;
		}
		switch (artifactType) {
		case RULETEMPLATEINSTANCE:
		case RULEFUNCTIONIMPL:
		case PROCESS:
			return true;
		default:
			return false;
		}
	}
	
	/**
	 * Checks whether rename is supported for the artifact type.
	 * @param artifactType
	 * @return
	 */
	private boolean canRenameArtifact(ARTIFACT_TYPES artifactType) {
		if (artifactType == null) {
			return false;
		}
		switch (artifactType) {
		case RULETEMPLATEINSTANCE:
		case RULEFUNCTIONIMPL:
		case PROCESS:
		case DOMAIN:
			return true;
		default:
			return false;
		}
	}
}