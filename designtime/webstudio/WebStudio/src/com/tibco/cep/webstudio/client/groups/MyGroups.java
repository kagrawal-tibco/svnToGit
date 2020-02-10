/**
 * 
 */
package com.tibco.cep.webstudio.client.groups;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TileLayoutPolicy;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.TileLayout;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;
import com.tibco.cep.webstudio.client.widgets.WebStudioToolbar;

/**
 * Widget representing the User groups for the currently logged-in user
 * 
 * @author Vikram Patil
 */
public class MyGroups extends VLayout implements IContentChangedListener {

	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private GroupContentsWindow groupContentsWindow;
	private TileLayout groupList;
	private List<Group> currentGroups = new ArrayList<Group>();
	private List<String> defaultGroups = new ArrayList<String>();

	/**
	 * Default constructor
	 * 
	 * @param resourcesContainer
	 */
	public MyGroups(GroupContentsWindow resourcesContainer) {
		super();

		this.setWidth100();
		this.setHeight100();
		this.setShowResizeBar(false);
		this.setOverflow(Overflow.AUTO);
		this.setMembersMargin(10);
		this.setStyleName("ws-myGroups");

		this.groupContentsWindow = resourcesContainer;

		this.initialize();
	}

	/**
	 * Initialize the My groups widget
	 */
	private void initialize() {
		this.groupList = new TileLayout();
		this.groupList.setLayoutPolicy(TileLayoutPolicy.FLOW);
		this.groupList.setTileVMargin(10);
		this.groupList.setTileHMargin(10);
		this.groupList.setWidth100();
		this.groupList.setHeight100();
		this.groupList.setLayoutMargin(5);
		this.groupList.setMargin(5);

		this.addMember(groupList);

		ContentModelManager.getInstance().addContentChangedListener(this);
		List<ContentGroup> groups = ContentModelManager.getInstance().getGroups();
		if (groups != null) {
			// groups have already been loaded from server, add them immediately
			for (ContentGroup contentGroup : groups) {
				Group newGroup = createGroup(contentGroup);
				addGroup(newGroup);
			}
		} else {
			// haven't yet been loaded, wait for callback
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		ContentModelManager.getInstance().removeContentChangedListener(this);
	}

	public Canvas getContainer() {
		return this.groupContentsWindow;
	}

	/**
	 * Remove the window of artifacts associated to a group
	 * 
	 * @param groupId
	 */
	@SuppressWarnings("static-access")
	public void clearGroupWindow(String groupId) {
		this.getContainer().removeChild(this.getContainer().getById(groupId));
	}

	/**
	 * Add's a specified group
	 * 
	 * @param group
	 */
	public void addGroup(Group group) {
		group.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (event.getSource() instanceof Group) {
					Group selectedGrp = (Group) event.getSource();
					toggleSelection(selectedGrp);
					
					// enable/disable delete on group selection
					if (WebStudio.get().getHeader().isWorkSpacePageSelected()) {
						if (groupContentsWindow.getSelectedGroups().size() > 0) {
							WebStudio.get().getWorkspacePage().toggleRemoveStatus(true);
						} else {
							WebStudio.get().getWorkspacePage().toggleRemoveStatus(false);
							
							if (WebStudio.get().getEditorPanel().getTabs().length == 0) {
								WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_DELETE_ID, true);
							}
							WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_REVERT_ID, true);
							WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_COMMIT_ID, true);
							WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_UPDATE_ID, true);
							WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_EXPORT_ID, true);
						}
					}
				}
			}

		});

		if (!group.isSystem()) {
			group.addContextMenu();
		}

		this.groupList.addTile(group);
		this.groupList.layoutTiles();
		this.currentGroups.add(group);
	}

	protected void toggleSelection(Group selectedGrp) {
		if (selectedGrp.isSelected()) {
			selectedGrp.setSelected(false);
			groupContentsWindow.removeGroupContents(selectedGrp);
			showGroupSelected(selectedGrp, false);
			
			// disable toolbar options
			WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_DELETE_ID, true);
			WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_COMMIT_ID, true);
			WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_UPDATE_ID, true);
			WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_REVERT_ID, true);
			
			if (!ProjectExplorerUtil.isNavigatorArtifactSelected() && WebStudio.get().getEditorPanel().getTabs().length == 0) {
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_IMPORT_ID, true);
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_EXPORT_ID, true);
			}
		} else {
			selectedGrp.setSelected(true);
			groupContentsWindow.addGroupContents(selectedGrp);
			showGroupSelected(selectedGrp, true);
		}
	}

	/**
	 * Delete a specified group from all containers
	 * 
	 * @param group
	 */
	@SuppressWarnings("static-access")
	public void deleteGroup(String groupId) {
		List<Group> curGrps = this.currentGroups;
		for (Group currentGroup : curGrps) {
			if (groupId.equals(currentGroup.getGroupTitle())) {
				if (!currentGroup.isSelected()) currentGroup.setSelected(true);
				deleteGroup(currentGroup);
				return;
			}
		}
	}

	protected void deleteGroup(Group group) {
		// change selection to remove associated contents from the content
		// window
		toggleSelection(group);

		this.groupList.removeChild(group);
		this.currentGroups.remove(group);
		
		if (ContentModelManager.getInstance().canDeleteGroup()) {
			ContentModelManager.getInstance().removeGroupById(group.getGroupTitle());
		}

		group.destroy();
	}

	/**
	 * Get all possible custom groups where an artifact can be moved
	 * 
	 * @param selectedResource
	 * @return
	 */
	public List<String> getAddToGroups(NavigatorResource selectedResource) {
		List<String> addTogroups = new ArrayList<String>();

		List<ContentGroup> existingGroups = ContentModelManager.getInstance().getGroups();
		if (existingGroups != null) {
			for (ContentGroup group : existingGroups) {
				if (!group.isSystem() && !containsArtifact(group, selectedResource)) {
					addTogroups.add(group.getGroupId());
				}
			}
		}

		return addTogroups;
	}

	/**
	 * Get all possible custom groups from where an artifact can be removed
	 * 
	 * @param selectedResource
	 * @return
	 */
	public List<String> getRemoveFromGroups(NavigatorResource selectedResource) {
		List<String> addTogroups = new ArrayList<String>();

		List<ContentGroup> existingGroups = ContentModelManager.getInstance().getGroups();
		if (existingGroups != null) {
			for (ContentGroup group : existingGroups) {
				if (!group.isSystem() && containsArtifact(group, selectedResource)) {
					addTogroups.add(group.getGroupId());
				}
			}
		}

		return addTogroups;
	}

	/**
	 * Check if the specified resource exists in the given group
	 * 
	 * @param group
	 * @param resource
	 * @return
	 */
	private boolean containsArtifact(ContentGroup group, NavigatorResource resource) {
		List<NavigatorResource> groupResources = group.getLocalResources();

		if (groupResources != null) {
			for (NavigatorResource res : groupResources) {
				if (res.getId().equals(resource.getId())) {
					return true;
				}
			}
		}

		return false;
	}

	private void removeArtifactFromGroup(String groupName, NavigatorResource selectedResource) {
		this.groupContentsWindow.remove(selectedResource);
	}

	/**
	 * Add's a specified artifact to a group
	 * 
	 * @param groupName
	 * @param selectedResource
	 */
	private void addArtifactToGroup(String groupName, NavigatorResource selectedResource) {
		this.groupContentsWindow.addArtifact(selectedResource);
	}

	/**
	 * Delete all the selected groups
	 */
	public void deleteSelectedGroups() {
		
		CustomSC.confirm(globalMsgBundle.text_confirm(), globalMsgBundle.groupDelete_confirmMessage(), new BooleanCallback() {
			public void execute(Boolean value) {
				if (value) {
					List<Group> toBeDeleted = new ArrayList<Group>();
					boolean anySystemGroups = false;

					List<Group> selectedGroups = groupContentsWindow.getSelectedGroups();
					for (Group group : selectedGroups) {
						if (group.isSelected()) {
							if (!group.isSystem()) {
								toBeDeleted.add(group);
							} else {
								anySystemGroups = true;
							}
						}
					}

					for (Group group : toBeDeleted) {
						ContentModelManager.getInstance().removeGroup(group.getGroupTitle());
					}

					if (anySystemGroups) {
						CustomSC.say(globalMsgBundle.groupDelete_denied());
					} else {
						WebStudio.get().getWorkspacePage().toggleRemoveStatus(false);
					}
				}

			}
		});
	}

	public List<Group> getGroups() {
		return currentGroups;
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
	 * Process group updations
	 * 
	 * @param delta
	 */
	private void processGroupDelta(ContentGroupChangedDelta delta) {
		switch (delta.getType()) {
		case ADDED:
			Group newGroup = createGroup(delta);
			addGroup(newGroup);
			break;

		case REMOVED:
			deleteGroup(delta.getGroupId());
			break;

		case CHANGED:
			processChanges(delta);
			break;

		default:
			break;
		}
	}

	/**
	 * Process the change associated to the group
	 * 
	 * @param delta
	 */
	private void processChanges(ContentGroupChangedDelta delta) {
		// do we care about resource changes here? I think this is all handled
		// in the GroupContentsWindow (?)
		if (true)
			return;
		List<ContentGroupResourceChangedDelta> affectedResources = delta.getAffectedResources();
		for (ContentGroupResourceChangedDelta affectResource : affectedResources) {
			switch (delta.getType()) {
			case ADDED:
				addArtifactToGroup(delta.getGroupId(), affectResource.getChangedResource());
				break;

			case REMOVED:
				removeArtifactFromGroup(delta.getGroupId(), affectResource.getChangedResource());
				break;

			case CHANGED:
				// don't care about content change
				break;

			default:
				break;
			}
		}
	}

	/**
	 * Create a group object
	 * 
	 * @param delta
	 * @return
	 */
	private Group createGroup(ContentGroup delta) {
		Group newGroup = null;
		String id = delta.getGroupId();
		newGroup = new Group(id);
		ContentGroup group = ContentModelManager.getInstance().getGroup(delta.getGroupId());
		newGroup.setSystem(group.isSystem());
		newGroup.setHeaderIcon(group.getHeaderIcon());
		newGroup.setGroupIcon(group.getGroupIcon());
		newGroup.setGroupImage();
		newGroup.setFileType(group.getFileType());

		return newGroup;
	}
	
	/**
	 * Select the specified group by Id
	 * 
	 * @param groupId
	 */
	public void selectGroup(String groupId) {
		Group grp = getGroupById(groupId);
		if (grp != null) {
			toggleSelection(grp);
			return;
		}
	}

	/**
	 * DeSelect a group
	 * 
	 * @param groupId
	 */
	public void deSelectGroup(String groupId) {
		Group grp = getGroupById(groupId);
		if (grp != null) {
			grp.setSelected(false);
			if (groupContentsWindow.getSelectedGroups().contains(grp)) {
				groupContentsWindow.getSelectedGroups().remove(grp);
			}
			showGroupSelected(grp, false);
		}
	}
	
	/**
	 * Show the group as selected
	 * @param groupId
	 */
	public void showGroupSelected(String groupId, boolean selected) {
		Group grp = getGroupById(groupId);
		showGroupSelected(grp, selected);
	}
	
	private void showGroupSelected(Group grp, boolean selected) {
		if (grp != null) {
			if (selected) {
				grp.setStyleName("ws-myGroups-selected");
			} else {
				grp.setStyleName("ws-myGroups");
			}
		}
	}

	/**
	 * Fetch Group by Id
	 * 
	 * @param groupId
	 * @return
	 */
	private Group getGroupById(String groupId) {
		for (Group grp : currentGroups) {
			if (grp.getGroupTitle().equals(groupId)) {
				return grp;
			}
		}

		return null;
	}
	
	/**
	 * 
	 * @param groupType
	 */
	public void addToDefaultGroups(String group) {
		defaultGroups.add(group);
	}
	
	/**
	 * 
	 * @param groupType
	 */
	public void removeFromDefaultGroups(String group) {
		defaultGroups.remove(group);
	}
	
	public void clearDefaultGroupsList() {
		defaultGroups.clear();
	}
	
	/**
	 * 
	 * @param groupType
	 * @return
	 */
	public boolean existsInDefaultGroups(String group) {
		return this.defaultGroups.contains(group);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getNextDefaultGroup() {
		String group = null;
		if (this.defaultGroups.size() > 0) {
			group = defaultGroups.get(0);
		}
		return group;
	}
	
	/**
	 * Clear details of the already fetched group and fetch data for the next default group in the list
	 * @param groupId
	 */
	public void fetchNextDefaultGroupContents(String groupId) {
		if(WebStudio.get().getWorkspacePage().getMyGroups().existsInDefaultGroups(groupId)) {
			WebStudio.get().getWorkspacePage().getMyGroups().removeFromDefaultGroups(groupId);
			
			String nextDefaultGroupId = WebStudio.get().getWorkspacePage().getMyGroups().getNextDefaultGroup();
			if (nextDefaultGroupId != null && !nextDefaultGroupId.isEmpty()) {
				WebStudio.get().getWorkspacePage().getMyGroups().selectGroup(nextDefaultGroupId);
				groupContentsWindow.hideProgressMsgLabel();
			} else {
				groupContentsWindow.hideProgressMsgLabel();
			}
		} else {
			groupContentsWindow.hideProgressMsgLabel();
		}
	}
	
	/**
	 * Clear already selected groups if any
	 */
	public void clearSelectedGroups() {
		List<String> selectedGroups = new ArrayList<String>();
		
		for (Group grp : groupContentsWindow.getSelectedGroups()) {
			selectedGroups.add(grp.getGroupTitle());
		}
		
		for (String groupTitle : selectedGroups) {
			ContentGroup contentGrp = ContentModelManager.getInstance().getGroup(groupTitle);
			
			if (contentGrp.getLocalResources() != null && contentGrp.getLocalResources().size() > 0) {
				selectGroup(groupTitle);
			} else {
				deSelectGroup(groupTitle);
			}
		}
	}
}
