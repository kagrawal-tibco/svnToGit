package com.tibco.cep.webstudio.client.groups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.smartgwt.client.util.Page;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.DisplayPropertiesManager;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

public class ContentModelManager implements HttpSuccessHandler, HttpFailureHandler {

	public static final String PROJECTS_GROUP_ID = "Projects";
	public static final String BUSINESS_RULES_GROUP_ID = "Business Rules";
	public static final String DECISION_TABLES_GROUP_ID = "Decision Tables";
	public static final String PROCESSES_GROUP_ID = "Processes";

	public static final String GROUP_UPDATE_ADD_OP = "ADD_ARTIFACT";
	public static final String GROUP_UPDATE_DELETE_OP = "DELETE_ARTIFACT";

	private static final String BASE_ICON_PATH = Page.getAppImgDir() + "icons/";

	private static ContentModelManager instance;

	private List<ContentGroup> groups;
	private List<IContentChangedListener> changeListeners = new ArrayList<IContentChangedListener>();
	private boolean canDeleteGroup;

	/**
	 * Singleton instance
	 * 
	 * @return
	 */
	public static ContentModelManager getInstance() {
		if (instance == null) {
			instance = new ContentModelManager();
			DisplayPropertiesManager.getInstance();
		}
		return instance;
	}

	/**
	 * Fetch user groups
	 * 
	 * @return
	 */
	public List<ContentGroup> getGroups() {
		if (groups == null) {
			initializeGroups();
		}
		return groups;
	}

	/**
	 * Fetch from server if locally not present
	 */
	private void initializeGroups() {
		// initialize groups here, to avoid possible NPE, or in onSuccess?
		groups = new ArrayList<ContentGroup>();
		ArtifactUtil.addHandlers(this);
		HttpRequest request = new HttpRequest();
		request.submit(ServerEndpoints.RMS_GET_MY_GROUPS.getURL());
	}

	@Override
	public void onFailure(HttpFailureEvent event) {
		boolean validEvent = false;

		if (event.getUrl().equals(ServerEndpoints.RMS_GET_MY_GROUPS.getURL())) {
			ArtifactUtil.removeHandlers(this);
		} else if (event.getUrl().indexOf(ServerEndpoints.RMS_DELETE_GROUP.getURL()) != -1) {
			validEvent = true;
		} else if (event.getUrl().indexOf(ServerEndpoints.RMS_UPDATE_GROUP.getURL()) != -1) {
			validEvent = true;
		}

		if (validEvent) {
			ArtifactUtil.removeHandlers(this);
		}
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {

		boolean validEvent = false;

		ContentModelChangedDelta delta = null;

		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_MY_GROUPS.getURL()) != -1) {
			groups = new ArrayList<ContentGroup>();
			Element docElement = event.getData();

			NodeList groupElements = docElement.getElementsByTagName("record");
			List<ContentGroupChangedDelta> groupDelta = new ArrayList<ContentGroupChangedDelta>();
			for (int i = 0; i < groupElements.getLength(); i++) {
				NodeList groupDetails = groupElements.item(i).getChildNodes();
				ContentGroup newGroup = null;
				for (int j = 0; j < groupDetails.getLength(); j++) {
					if (!groupDetails.item(j).toString().trim().isEmpty()) {
						String nodeName = groupDetails.item(j).getNodeName();
						String nodeValue = null;
						if (!nodeName.equals("artifactPaths")) {
							nodeValue = groupDetails.item(j).getFirstChild().getNodeValue();
						}

						if (nodeName.equals("name")) {
							if (newGroup == null) {
								if (PROJECTS_GROUP_ID.equals(nodeValue)) {
									newGroup = new ProjectsContentGroup(nodeValue);
								} else {
									newGroup = new ContentGroup(nodeValue);
								}
							}
						}
						if (nodeName.equals("systemGroup")) {
							newGroup.setSystem((nodeValue.equalsIgnoreCase("true")) ? true : false);
						}
						if (nodeName.equals("icon")) {
							newGroup.setHeaderIcon(BASE_ICON_PATH + "16/" + nodeValue);
							newGroup.setGroupIcon(BASE_ICON_PATH + "48/" + nodeValue);
						}
						if (nodeName.equals("fileType")) {
							newGroup.setFileType(nodeValue);
						}
					}
				}
				groups.add(newGroup);
				groupDelta.add(new ContentGroupChangedDelta(newGroup.getGroupId(), null, ContentGroupChangeType.ADDED));
			}
			delta = new ContentModelChangedDelta(groupDelta);
			validEvent = true;
		} else if (event.getUrl().indexOf(ServerEndpoints.RMS_DELETE_GROUP.getURL()) != -1) {
			validEvent = true;
		} else if (event.getUrl().indexOf(ServerEndpoints.RMS_UPDATE_GROUP.getURL()) != -1) {
			validEvent = true;
		}

		if (delta != null) {
			fireContentModelChangedEvent(delta);
		}
		if (validEvent) {
			ArtifactUtil.removeHandlers(this);
		}
	}

	/**
	 * 
	 * @param delta
	 */
	public void fireContentModelChangedEvent(ContentModelChangedDelta delta) {
		// fire change after each add/remove of groups/resources
		// update views based on models (create listeners for each relevant
		// view)
		canDeleteGroup = false;
		ContentModelChangedEvent event = new ContentModelChangedEvent(delta);
		for (int i = 0; i < changeListeners.size(); i++) {
			IContentChangedListener listener  = (IContentChangedListener) changeListeners.get(i);
			if (!hasMoreGroupListeners(i)) {
				canDeleteGroup = true;
			}
			listener.contentChanged(event);
		}
	}
	
	/**
	 * Check if more listener of type MyGroups exist
	 * 
	 * @param currentIndex
	 * @return
	 */
	private boolean hasMoreGroupListeners(int currentIndex) {
		for (int i = currentIndex+1; i < changeListeners.size(); i++) {
			IContentChangedListener listener  = (IContentChangedListener) changeListeners.get(i);
			if (listener instanceof MyGroups) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Add specified listener for any change to group content
	 * 
	 * @param listener
	 */
	public void addContentChangedListener(IContentChangedListener listener) {
		if (!changeListeners.contains(listener)) {
			changeListeners.add(listener);
		}
	}

	/**
	 * Remove any associated group content change listners
	 * 
	 * @param listener
	 */
	public void removeContentChangedListener(IContentChangedListener listener) {
		if (!changeListeners.contains(listener)) {
			changeListeners.add(listener);
		}
	}

	/**
	 * Add's artifacts to group
	 * 
	 * @param groupId
	 * @param selectedResource
	 */
	public void addToGroup(String groupId, NavigatorResource selectedResource) {
		ContentGroup group = getGroup(groupId);
		if (group.addResource(selectedResource)) {

			updateGroup(groupId, selectedResource, GROUP_UPDATE_ADD_OP);

			ContentGroupResourceChangedDelta rDelta = new ContentGroupResourceChangedDelta(selectedResource,
					ContentGroupChangeType.ADDED);
			List<ContentGroupResourceChangedDelta> changed = new ArrayList<ContentGroupResourceChangedDelta>();
			changed.add(rDelta);
			ContentGroupChangedDelta gDelta = new ContentGroupChangedDelta(groupId,
					changed,
					ContentGroupChangeType.CHANGED);
			List<ContentGroupChangedDelta> changedGrps = new ArrayList<ContentGroupChangedDelta>();
			changedGrps.add(gDelta);
			ContentModelChangedDelta modelDelta = new ContentModelChangedDelta(changedGrps);
			fireContentModelChangedEvent(modelDelta);
		}
	}

	/**
	 * Removes artifacts from group
	 * 
	 * @param groupId
	 * @param selectedResource
	 */
	public void removeFromGroup(String groupId, NavigatorResource selectedResource) {
		ContentGroup group = getGroup(groupId);
		if (group.removeResource(selectedResource)) {

			updateGroup(groupId, selectedResource, GROUP_UPDATE_DELETE_OP);

			ContentGroupResourceChangedDelta rDelta = new ContentGroupResourceChangedDelta(selectedResource,
					ContentGroupChangeType.REMOVED);
			List<ContentGroupResourceChangedDelta> changed = new ArrayList<ContentGroupResourceChangedDelta>();
			changed.add(rDelta);
			ContentGroupChangedDelta gDelta = new ContentGroupChangedDelta(groupId,
					changed,
					ContentGroupChangeType.CHANGED);
			List<ContentGroupChangedDelta> changedGrps = new ArrayList<ContentGroupChangedDelta>();
			changedGrps.add(gDelta);
			ContentModelChangedDelta modelDelta = new ContentModelChangedDelta(changedGrps);
			fireContentModelChangedEvent(modelDelta);
		}
	}

	/**
	 * Add a new Group
	 * 
	 * @param group
	 */
	public void addGroup(ContentGroup group) {
		List<ContentGroup> groups = getGroups();
		if (!groups.contains(group)) {
			groups.add(group);
			ContentGroupChangedDelta gDelta = new ContentGroupChangedDelta(group.getGroupId(),
					null,
					ContentGroupChangeType.ADDED);
			List<ContentGroupChangedDelta> changedGrps = new ArrayList<ContentGroupChangedDelta>();
			changedGrps.add(gDelta);
			ContentModelChangedDelta modelDelta = new ContentModelChangedDelta(changedGrps);
			fireContentModelChangedEvent(modelDelta);
		}
	}

	/**
	 * Remove the selected Group
	 * 
	 * @param group
	 */
	public void removeGroup(String groupId) {
		ContentGroup groupToDelete = getGroup(groupId);

		List<ContentGroup> groups = getGroups();
		if (groups.contains(groupToDelete)) {

			deleteGroup(groupToDelete.getGroupId());

			ContentGroupChangedDelta gDelta = new ContentGroupChangedDelta(groupToDelete.getGroupId(),
					null,
					ContentGroupChangeType.REMOVED);
			List<ContentGroupChangedDelta> changedGrps = new ArrayList<ContentGroupChangedDelta>();
			changedGrps.add(gDelta);
			ContentModelChangedDelta modelDelta = new ContentModelChangedDelta(changedGrps);
			fireContentModelChangedEvent(modelDelta);
		}
	}

	/**
	 * Remove the select group based on the specified group id
	 * 
	 * @param groupId
	 */
	public void removeGroupById(String groupId) {
		ContentGroup groupToDelete = getGroup(groupId);

		List<ContentGroup> groups = getGroups();
		if (groups.contains(groupToDelete)) {
			groups.remove(groupToDelete);
		}
	}

	/**
	 * Get the group based on the group Id
	 * 
	 * @param groupId
	 * @return
	 */
	public ContentGroup getGroup(String groupId) {
		List<ContentGroup> groups = getGroups();
		if (groups == null) {
			return null; // not initialized yet
		}
		for (ContentGroup contentGroup : groups) {
			if (contentGroup != null && contentGroup.getGroupId().equals(groupId)) {
				return contentGroup;
			}
		}
		return null;
	}

	public List<NavigatorResource> getGroupContents(ContentGroup group) {
		return group.getResources();
	}

	public ProjectsContentGroup getProjectsGroup() {
		return (ProjectsContentGroup) getGroup(PROJECTS_GROUP_ID);
	}

	/**
	 * Remove artifact locally from the available groups. Server removal is
	 * handled via the artifact deletion independently
	 * 
	 * @param resource
	 */
	public void removeArtifactFromGroups(NavigatorResource resource) {
		List<ContentGroup> groups = this.getGroups();
		Map<ContentGroup, List<NavigatorResource>> groupToDeletedResources = new HashMap<ContentGroup, List<NavigatorResource>>();

		for (ContentGroup grp : groups) {
			if (grp.getLocalResources() != null) {
				for (NavigatorResource navResource : (List<NavigatorResource>) grp.getLocalResources()) {
					if (navResource.getId().startsWith(resource.getId())) {
						List<NavigatorResource> resourcesToDelete = groupToDeletedResources.get(grp);
						if (resourcesToDelete == null) {
							resourcesToDelete = new ArrayList<NavigatorResource>();
							groupToDeletedResources.put(grp, resourcesToDelete);
						}
						resourcesToDelete.add(navResource);
					}
				}
			}
		}

		List<ContentGroupChangedDelta> changedGrps = new ArrayList<ContentGroupChangedDelta>();
		for (ContentGroup grp : groupToDeletedResources.keySet()) {
			List<NavigatorResource> resourcesToDelete = groupToDeletedResources.get(grp);

			List<ContentGroupResourceChangedDelta> changed = new ArrayList<ContentGroupResourceChangedDelta>();
			for (NavigatorResource navResource : resourcesToDelete) {
				if (grp.getLocalResources().contains(navResource)) {
					grp.getLocalResources().remove(navResource);
					ContentGroupResourceChangedDelta rDelta = new ContentGroupResourceChangedDelta(navResource, ContentGroupChangeType.REMOVED);
					changed.add(rDelta);
				}
			}
			ContentGroupChangedDelta gDelta = new ContentGroupChangedDelta(grp.groupId, changed, ContentGroupChangeType.CHANGED);
			changedGrps.add(gDelta);
			
			if (grp.getLocalResources().size() == 0) {
				grp.setResources(null);
			}
			
			if (grp.getGroupId().equals(ContentModelManager.PROJECTS_GROUP_ID) && resource.getType().equalsIgnoreCase(ARTIFACT_TYPES.PROJECT.getValue())) {
				ProjectsContentGroup projectGroup = (ProjectsContentGroup) grp;
				projectGroup.removeProject(resource.getName());
			}
		}
		
		ContentModelChangedDelta modelDelta = new ContentModelChangedDelta(changedGrps);
		fireContentModelChangedEvent(modelDelta);
	}

	/**
	 * Add/Remove artifacts from the specified group
	 * 
	 * @param groupId
	 * @param selectedResource
	 */
	private void updateGroup(String groupId, NavigatorResource selectedResource, String operationType) {
		String artifactPath = selectedResource.getId().replace("$", "/");

		ArtifactUtil.addHandlers(this);

		HttpRequest request = new HttpRequest();
		request.setMethod(HttpMethod.PUT);
//		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_GROUP_NAME, groupId));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_PATH, artifactPath));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_GROUP_OPERATION_TYPE, operationType));

		request.submit(ServerEndpoints.RMS_UPDATE_GROUP.getURL("groups", groupId));
	}

	/**
	 * Delete the specified group
	 * 
	 * @param groupId
	 */
	private void deleteGroup(String groupId) {
		ArtifactUtil.addHandlers(this);
		HttpRequest request = new HttpRequest();
		request.setMethod(HttpMethod.DELETE);
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_GROUP_NAME, groupId));

		request.submit(ServerEndpoints.RMS_DELETE_GROUP.getURL("groups", groupId));
	}

	public boolean canDeleteGroup() {
		return canDeleteGroup;
	}

	public void setCanDeleteGroup(boolean canDeleteGroup) {
		this.canDeleteGroup = canDeleteGroup;
	}
}
