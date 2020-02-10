/**
 * 
 */
package com.tibco.cep.webstudio.client.groups;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.SearchForm;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.RuleFunctionNavigatorResource;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RuleTemplateNavigatorResource;
import com.tibco.cep.webstudio.client.widgets.WebStudioNavigatorGrid;

/**
 * Window representing the user group contents
 * 
 * @author Vikram Patil
 */
public class GroupContentsWindow extends Window implements CloseClickHandler, IContentChangedListener {
	
	private GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private List<Group> selectedGroups = new ArrayList<Group>();
	private WebStudioNavigatorGrid artifactTreeGrid;
	private DynamicForm searchForm;
//	private Menu contextMenu;
	private String selectedProjectId;
	private String selectedArtifact;
	private Label progressMsgLabel;
	
	private String progressMsg = "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+ "&nbsp;&nbsp;&nbsp;"
			+ Canvas.imgHTML(Page.getAppImgDir() + "icons/16/wait.gif");
	private List<String> groupsToRefresh;

	/**
	 * Base Contructor
	 * 
	 * @param selectedGroup
	 */
	public GroupContentsWindow() {
		super();

		this.setWidth100();
		this.setHeight100();
		this.setLayoutLeftMargin(0);
		this.setLayoutRightMargin(0);
		this.setLayoutTopMargin(0);
		this.setLayoutBottomMargin(0);		
		this.setShowEdges(false);
		this.setOverflow(Overflow.HIDDEN);
		this.setShowHeader(false);
		this.setAnimateMinimize(true);
		this.setAutoSize(false);

		this.setAnimateMinimize(true);
		this.setCanDragReposition(false);
		this.setCanDragResize(false);

		this.addCloseClickHandler(this);
		this.initialize();
	}

	/**
	 * Remove the contents of the selected group
	 * 
	 * @param group
	 */
	public void removeGroupContents(Group group) {
		if (this.selectedGroups.contains(group)) {
			this.selectedGroups.remove(group);
		}
		ContentGroup grp = ContentModelManager.getInstance().getGroup(group.getGroupTitle());
		if (grp != null) {
			List<NavigatorResource> resources = grp.getResources();
			if (resources != null) {
				for (NavigatorResource navigatorResource : resources) {
					remove(navigatorResource);
				}
			}
		}
	}

	/**
	 * Check if the artifact to be removed is presently listed
	 * 
	 * @param navigatorResource
	 * @return
	 */
	private boolean isCurrentlyShown(NavigatorResource navigatorResource) {
		List<Group> selectedGroups = getSelectedGroups();
		for (Group group : selectedGroups) {
			ContentGroup contentGroup = ContentModelManager.getInstance().getGroup(group.getGroupTitle());
			// makes sense to check only if the selected group has any contents, no need to download contents at this moment
			if (contentGroup.getLocalResources() != null &&  contentGroup.getLocalResources().size() > 0) {
				if (groupContainsResource(contentGroup, navigatorResource)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check if the artifact to be removed exists in other groups
	 * 
	 * @param contentGroup
	 * @param navigatorResource
	 * @return
	 */
	private boolean groupContainsResource(ContentGroup contentGroup, NavigatorResource navigatorResource) {
		List<NavigatorResource> resources = contentGroup.getResources();
		if (resources != null) {
			for (NavigatorResource res : resources) {
				if (navigatorResource.getId().startsWith(res.getId())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Add artifacts to the specified group
	 * 
	 * @param group
	 */
	public void addGroupContents(Group group) {
		if (!this.selectedGroups.contains(group)) {
			this.selectedGroups.add(group);
		}
		ContentGroup grp = ContentModelManager.getInstance().getGroup(group.getGroupTitle());
		if (grp != null) {
			showProgressMsgLabel();	
			grp.setgroupsToRefreshList(groupsToRefresh);
 			List<NavigatorResource> resources = grp.getResources();
 			if(groupsToRefresh!=null){
 				groupsToRefresh.remove(group.getGroupTitle());
 			}
			List<NavigatorResource> resourcesFilteredList = new ArrayList<NavigatorResource>();
			if(!WebStudio.get().getUserPreference().isGroupRelatedArtifacts() && !group.getGroupTitle().equalsIgnoreCase(ContentModelManager.PROJECTS_GROUP_ID) && resources !=null){
				for(NavigatorResource resource : resources){
					if(!(resource instanceof RuleFunctionNavigatorResource || resource instanceof RuleTemplateNavigatorResource)){
						resourcesFilteredList.add(resource);
					}
				}
				if (resourcesFilteredList != null) {
					add((NavigatorResource[]) resourcesFilteredList.toArray(new NavigatorResource[resourcesFilteredList.size()]));

					// fetch data of the next default group if any
					if (WebStudio.get().getHeader().isWorkSpacePageSelected()) {
						WebStudio.get().getWorkspacePage().getMyGroups().fetchNextDefaultGroupContents(grp.getGroupId());
					} else {
						WebStudio.get().getPortalPage().getMyGroupsFromGroupPortlet().fetchNextDefaultGroupContents(grp.getGroupId());
					}
				}

			}else{
				if (resources != null) {
					add((NavigatorResource[]) resources.toArray(new NavigatorResource[resources.size()]));

					// fetch data of the next default group if any
					if (WebStudio.get().getHeader().isWorkSpacePageSelected()) {
						WebStudio.get().getWorkspacePage().getMyGroups().fetchNextDefaultGroupContents(grp.getGroupId());
					} else {
						WebStudio.get().getPortalPage().getMyGroupsFromGroupPortlet().fetchNextDefaultGroupContents(grp.getGroupId());
					}
				}
			}
		}
	}

	/**
	 * Initialize the Widget
	 */
	private void initialize() {
		this.artifactTreeGrid = new WebStudioNavigatorGrid();
		
		getSearchContainer();
		this.addItem(createProgressMsgLabel());
		this.addItem(searchForm);
		this.addItem(this.artifactTreeGrid);
		ContentModelManager.getInstance().addContentChangedListener(this);
	}
	
	private Label createProgressMsgLabel() {
		progressMsgLabel = new Label();
		progressMsgLabel.setWidth100();
		progressMsgLabel.setHeight(10);
		progressMsgLabel.setValign(VerticalAlignment.BOTTOM);
		progressMsgLabel.hide();
		return progressMsgLabel;
	}

	protected void showProgressMsgLabel() {
		if (progressMsgLabel != null) {
			artifactTreeGrid.setShowEmptyMessage(false);
			progressMsgLabel.setContents(progressMsg);
			progressMsgLabel.show();
		}	
	}
	
	protected void hideProgressMsgLabel() {
		if (progressMsgLabel != null) {
			artifactTreeGrid.setShowEmptyMessage(true);
			progressMsgLabel.hide();
		}	
	}
	
	/**
	 * Method to get the search container.
	 */
	private void getSearchContainer() {
		searchForm = new SearchForm();
		searchForm.setTitleSuffix("");
		searchForm.setWidth100();
		searchForm.setNumCols(2);
		searchForm.setColWidths("*", "98%");
		searchForm.setStyleName("ws-search-form");
		
	    final TextItem searchText = new TextItem("search");
	    searchText.setTitle("");
	    searchText.setDefaultValue("");
	    searchText.setWidth("*");
	    searchText.setHint(globalMsgBundle.search_hint());
	    searchText.setShowHintInField(true);
	    
	    PickerIcon cancelPicker = new PickerIcon(PickerIcon.CLEAR, new FormItemClickHandler() {
            public void onFormItemClick(FormItemIconClickEvent event) {
            	searchForm.setValue("search", "");
				searchForm.hide();
				artifactTreeGrid.removeSearchTree();
            }
        });
	    cancelPicker.setPrompt(globalMsgBundle.button_cancel());
	    searchText.setIcons(cancelPicker);
	    
	    searchText.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				Timer timer = new Timer() {
					@Override
					public void run() {
						artifactTreeGrid.renderSearchResults(searchForm
								.getValueAsString("search"));
					}
				};
				timer.schedule(300);
			}
		});
	    searchForm.setItems(searchText);
	    searchForm.hide();
	}
	
	public void showSearchBox() {
		searchForm.show();
	}

	@Override
	public void destroy() {
		super.destroy();
		ContentModelManager.getInstance().removeContentChangedListener(this);
	}

	/**
	 * Handler for closing group window
	 */
	public void onCloseClick(CloseClickEvent event) {
		WebStudio.get().getMyGroups().clearGroupWindow(this.getID());
	}

	/**
	 * Add newly created artifacts
	 */
	public void addArtifact(NavigatorResource newArtifact) {
		this.artifactTreeGrid.getTree().add(newArtifact, artifactTreeGrid.getTree().getRoot());
	}

	public List<Group> getSelectedGroups() {
		return this.selectedGroups;
	}
	
	

	public void add(NavigatorResource[] data) {
		artifactTreeGrid.addToResourceNavigator(data);
	}

	/**
	 * Adding a new record to project explorer tree
	 * 
	 * @param newResource
	 */
	public void add(NavigatorResource newResource) {
		artifactTreeGrid.addRecord(newResource);
	}

	public void remove(NavigatorResource navigatorResource) {
		if (!isCurrentlyShown(navigatorResource) || canShowResource(navigatorResource)) {
			artifactTreeGrid.removeRecord(navigatorResource.getId(), true);
		}
	}
	
	public boolean canShowResource(NavigatorResource navigatorResource){
		boolean isProjectGrpPresent = false;
		for(Group group : selectedGroups){
			if(group.getGroupTitle().equalsIgnoreCase("Projects")){
				isProjectGrpPresent = true;
			}
		}

		if(!WebStudio.get().getUserPreference().isGroupRelatedArtifacts() && !isProjectGrpPresent && (navigatorResource instanceof RuleFunctionNavigatorResource || navigatorResource instanceof RuleTemplateNavigatorResource)){
			return true;
		}
		return false;
	}

	public void openResource(String resourceToOpen) {
		artifactTreeGrid.openResource(resourceToOpen);
	}
	
	public void openResourceTree(String resourceToOpen) {
		artifactTreeGrid.openResource(resourceToOpen, false);
	}


	public String getSelectedProjectId() {
		return selectedProjectId;
	}

	public WebStudioNavigatorGrid getArtifactTreeGrid() {
		return artifactTreeGrid;
	}

	public void setSelectedProjectId(String selectedProjectId) {
		this.selectedProjectId = selectedProjectId;
	}

	public String getSelectedArtifact() {
		return selectedArtifact;
	}

	public void setSelectedArtifact(String selectedArtifact) {
		this.selectedArtifact = selectedArtifact;
	}

	public boolean projectExists(String projectName) {
		if (artifactTreeGrid == null) {
			return false;
		} else {
			return artifactTreeGrid.projectExists(projectName);
		}
	}

	@Override
	public void contentChanged(ContentModelChangedEvent changeEvent) {
		ContentModelChangedDelta delta = changeEvent.getDelta();
		List<ContentGroupChangedDelta> changedGroups = delta.getChangedGroups();
		for (ContentGroupChangedDelta grpDelta : changedGroups) {
			processGroupDelta(grpDelta);
		}
	}

	/**
	 * Process the change propagated
	 * 
	 * @param grpDelta
	 */
	private void processGroupDelta(ContentGroupChangedDelta grpDelta) {
		Group changedGroup = getCurrentGroup(grpDelta.getGroupId());
		if (changedGroup == null) {
			// this group is not currently selected, don't care about changes
			return;
		}
		List<ContentGroupResourceChangedDelta> affectedResources = grpDelta.getAffectedResources();
		if (affectedResources != null) {
			List<NavigatorResource> resourcesAddDelta = new ArrayList<NavigatorResource>();			
			for (ContentGroupResourceChangedDelta delta : affectedResources) {
				NavigatorResource changedResource = delta.getChangedResource();
				switch (delta.getType()) {
				case ADDED:
//					add(changedResource);
					resourcesAddDelta.add(changedResource);
					break;

				case REMOVED:
					remove(changedResource);
					break;

				case CHANGED:
					// don't care about content change
					break;

				default:
					break;
				}
			}
			artifactTreeGrid.addToResourceNavigator(resourcesAddDelta.toArray(new NavigatorResource[resourcesAddDelta.size()]));
		}
	}

	/**
	 * Get the currently selected group
	 * 
	 * @param groupId
	 * @return
	 */
	public Group getCurrentGroup(String groupId) {
		for (Group group : selectedGroups) {
			if (group.getGroupTitle().equals(groupId)) {
				return group;
			}
		}
		return null;
	}

	/**
	 * Display the group content as tree
	 * 
	 * @param asTree
	 */
	public void setShowAsTree(boolean asTree) {
		if (artifactTreeGrid.setShowAsTree(asTree)) {
			List<Group> grps = getSelectedGroups();
			String projectGroupId = "group_"+ContentModelManager.PROJECTS_GROUP_ID;
			List<NavigatorResource> resources = new ArrayList<NavigatorResource>();
			for (Group group : grps) {
				List<NavigatorResource> grpResources = ContentModelManager.getInstance()
						.getGroup(group.getGroupTitle()).getResources();
				if (grpResources != null) {
					if (projectGroupId.equals(group.getGroupID())) {
						resources.clear();
						resources.addAll(grpResources);
						break;
					} else {
						List<NavigatorResource> resourcesFilteredList = new ArrayList<NavigatorResource>();
						if(!WebStudio.get().getUserPreference().isGroupRelatedArtifacts()){
							for(NavigatorResource resource : grpResources){
								if(!(resource instanceof RuleFunctionNavigatorResource || resource instanceof RuleTemplateNavigatorResource)){
									resourcesFilteredList.add(resource);
								}
							}
							resources.addAll(resourcesFilteredList);
						}else{
							resources.addAll(grpResources);
						}

					}
				}
			}
			artifactTreeGrid.reloadTreeNodes(resources);
		}
	}

	/**
	 * Get artifacts by type
	 * 
	 * @param artifactTypes
	 * @return
	 */
	public String[] getArtifactsByType(String[] artifactTypes) {
		return artifactTreeGrid.getNodesByType(artifactTypes);
	}
	
	/**
	 * Get if the the the project is displayed as tree/list
	 * @return
	 */
	public boolean isShowAsTree() {
		return artifactTreeGrid.isShowAsTree();
	}
	
	public void setgroupToRefreshList(List<String> groupsToRefresh){
		this.groupsToRefresh = groupsToRefresh;
	}

	public List<String> getSelectedGrpsIdList() {
		List<String> selectedGrpsIdList = new ArrayList<String>();
		for(Group selectedGrp : selectedGroups){
			selectedGrpsIdList.add(selectedGrp.getGroupTitle());
		}
		return selectedGrpsIdList;
	}
	
}
