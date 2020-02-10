package com.tibco.cep.webstudio.client.groups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.RuleFunctionNavigatorResource;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.model.ArtifactDetail;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.model.RuleTemplateNavigatorResource;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

public class ContentGroup implements HttpSuccessHandler, HttpFailureHandler {
	
	protected List<NavigatorResource> resources;
	protected String groupId;
	private boolean system;
	private String headerIcon;
	private String groupIcon;
	private String fileType;
	
	// Set only via projectPortlet 
	// will be used to conditionally pass parameter during server fetch
	private String selectedProject;
	private String artifactToOpen;
	private List<String> groupsToRefresh;
	
	public ContentGroup(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * Fetch resources if locally present
	 * 
	 * @return
	 */
	public List<NavigatorResource> getResources() {
		if (resources == null || (getSelectedProject() != null && getResourcesByProject(getSelectedProject()) == null)
				|| (this instanceof ProjectsContentGroup && ((ProjectsContentGroup)this).getTotalProjects() < WebStudio.get().getPortalPage().getTotalAvailableProjectsPortlet())) {
			getGroupResources();
		}else if(resources != null && groupsToRefresh!=null && groupsToRefresh.contains(groupId)){
			resources = null;
			getGroupResources();
		}
		return resources;
	}

	public List<NavigatorResource> getLocalResources() {
		return resources;
	}

	/**
	 * Fetch the group artifacts from the server
	 */
	private void getGroupResources() {
		ArtifactUtil.addHandlers(this);

		HttpRequest request = new HttpRequest();
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_GROUP_NAME, groupId));
		if (fileType != null) {
			request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_GROUP_FILE_TYPE, fileType));
		}
		if (groupId.equals(ContentModelManager.PROJECTS_GROUP_ID) && getSelectedProject() != null) {
			request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, getSelectedProject()));
			setSelectedProject(null);
		}
		
		request.submit(ServerEndpoints.RMS_GET_GROUP_ARTIFACTS.getURL("groups", groupId));
	}

	@Override
	public void onFailure(HttpFailureEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_GROUP_ARTIFACTS.getURL("groups", groupId)) != -1) {
			ArtifactUtil.removeHandlers(this);
		}
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_GROUP_ARTIFACTS.getURL("groups", groupId)) != -1) {
			Element docElement = event.getData();

			Map<String, List<ArtifactDetail>> projectArtifactList = new HashMap<String, List<ArtifactDetail>>();
			NodeList artifactElements = docElement.getElementsByTagName("record");
			for (int i=0; i < artifactElements.getLength(); i++) {
				NodeList artifactDetails = artifactElements.item(i).getChildNodes();
				
				ArtifactDetail artifactDetailItem = null;
				for (int j=0; j<artifactDetails.getLength(); j++) {
					if (!artifactDetails.item(j).toString().trim().isEmpty()) {
						if (artifactDetails.item(j).getNodeName().equals("artifactPath")) {
							String artifact = artifactDetails.item(j).getFirstChild().getNodeValue();
							
							String projectName = artifact.substring(0, artifact.indexOf("/"));
							String artifactPath = artifact.substring(artifact.indexOf("/"), artifact.length());
							
							List<ArtifactDetail> artifactList = projectArtifactList.get(projectName);
							if (artifactList == null) {
								artifactList = new ArrayList<ArtifactDetail>();
								projectArtifactList.put(projectName, artifactList);
							}
							
							artifactDetailItem = new ArtifactDetail(artifactPath, null);
							artifactList.add(artifactDetailItem);
							
						} else if (artifactDetails.item(j).getNodeName().equals("baseArtifactPath")) {
							String baseArtifactPath = artifactDetails.item(j).getFirstChild().getNodeValue();
							artifactDetailItem.setBaseArtifactPath(baseArtifactPath);
							
						} else if (artifactDetails.item(j).getNodeName().equals("locked")) {
							String locked = artifactDetails.item(j).getFirstChild().getNodeValue();
							artifactDetailItem.setLocked(Boolean.valueOf(locked));
							
						} else if (artifactDetails.item(j).getNodeName().equals("processType")) {
							String processType = artifactDetails.item(j).getFirstChild().getNodeValue();
							artifactDetailItem.setProcessType(processType);
						}
					}
				}
			}
			
			for (String project : projectArtifactList.keySet()) {
				List<ArtifactDetail> artifactList = projectArtifactList.get(project);
				NavigatorResource[] resourceList = ProjectExplorerUtil.createProjectResources(artifactList, project);
                if (groupId.equals(ContentModelManager.PROJECTS_GROUP_ID)) {
					ContentModelManager.getInstance().getProjectsGroup().setProjectResources(project, resourceList);
				}else {
					if(!WebStudio.get().getUserPreference().isGroupRelatedArtifacts()){
						List<NavigatorResource> resourcesFilteredList = new ArrayList<NavigatorResource>();
						for(int i =0 ; i < resourceList.length ; i++){
							if(!(resourceList[i] instanceof RuleFunctionNavigatorResource || resourceList[i] instanceof RuleTemplateNavigatorResource)){
								resourcesFilteredList.add(resourceList[i]);
							}
						}

						addModelChangedDelta((NavigatorResource[]) resourcesFilteredList.toArray(new NavigatorResource[resourcesFilteredList.size()]));

					}else{
						addModelChangedDelta(resourceList);
					}

				}
			}
			
			if (getArtifactToOpen() != null) {
				WebStudio.get().getWorkspacePage().openResource(getArtifactToOpen());
				setArtifactToOpen(null);
			}
			
			ArtifactUtil.removeHandlers(this);
			
			// fetch data of the next default group if any
			if (WebStudio.get().getHeader().isWorkSpacePageSelected()) {
				WebStudio.get().getWorkspacePage().getMyGroups().fetchNextDefaultGroupContents(groupId);
			} else {
				WebStudio.get().getPortalPage().getMyGroupsFromGroupPortlet().fetchNextDefaultGroupContents(groupId);
			}

		}
	}
	
	/**
	 * Add model changed delta for the selected group
	 * 
	 * @param resourceList
	 */
	private void addModelChangedDelta(NavigatorResource[] resourceList) {
		List<ContentGroupResourceChangedDelta> changed = new ArrayList<ContentGroupResourceChangedDelta>();
		if (resources == null) {
			resources = new ArrayList<NavigatorResource>();
		}
		for (NavigatorResource resource : resourceList) {
			ContentGroupResourceChangedDelta rDelta = new ContentGroupResourceChangedDelta(resource,
					ContentGroupChangeType.ADDED);
			changed.add(rDelta);
			resources.add(resource);
		}
		
		ContentGroupChangedDelta gDelta = new ContentGroupChangedDelta(groupId, changed, ContentGroupChangeType.CHANGED);
		List<ContentGroupChangedDelta> changedGrps = new ArrayList<ContentGroupChangedDelta>();
		changedGrps.add(gDelta);
		
		ContentModelChangedDelta modelDelta = new ContentModelChangedDelta(changedGrps);
		ContentModelManager.getInstance().fireContentModelChangedEvent(modelDelta);
	}

	public void setSystem(boolean system) {
		this.system = system;
	}

	public void setHeaderIcon(String headerIcon) {
		this.headerIcon = headerIcon;
	}

	public void setGroupIcon(String groupIcon) {
		this.groupIcon = groupIcon;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getGroupId() {
		return groupId;
	}

	public boolean isSystem() {
		return system;
	}

	public String getHeaderIcon() {
		return headerIcon;
	}

	public String getGroupIcon() {
		return groupIcon;
	}

	public String getFileType() {
		return fileType;
	}

	public void setResources(List<NavigatorResource> resources) {
		this.resources = resources;
	}

	public String getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(String selectedProject) {
		this.selectedProject = selectedProject;
	}

	public String getArtifactToOpen() {
		return artifactToOpen;
	}

	public void setArtifactToOpen(String artifactToOpen) {
		this.artifactToOpen = artifactToOpen;
	}

	/**
	 * Add Resource to group
	 * @param selectedResource
	 * @return
	 */
	public boolean addResource(NavigatorResource selectedResource) {
		if (resources == null) {
			resources = new ArrayList<NavigatorResource>();
		}
		if (!resources.contains(selectedResource)) {
			resources.add(selectedResource);
			return true;
		}
		return false;
	}
	
	/**
	 * Remove the specified resource from the group
	 * 
	 * @param selectedResource
	 * @return
	 */
	public boolean removeResource(NavigatorResource selectedResource) {
		if (resources.contains(selectedResource)) {
			resources.remove(selectedResource);
			return true;
		}
		return false;
	}

	/**
	 * Fetch resources by project
	 * 
	 * @param projectName
	 * @return
	 */
	protected List<NavigatorResource> getResourcesByProject(String projectName) {
		return null;
	}
	
	/**
	 * Return artifact detail List
	 * @param artifactList
	 * @return
	 */
	private List<ArtifactDetail> getArtifactList(List<String> artifactList) {
		List<ArtifactDetail> artifactDetailList = new ArrayList<ArtifactDetail>();
		
		for (String artifact : artifactList) {
			artifactDetailList.add(new ArtifactDetail(artifact,""));
		}
		
		return artifactDetailList;
	}
	
	public void setgroupsToRefreshList(List<String> groupsToRefresh){
		this.groupsToRefresh = groupsToRefresh;
	}
}
