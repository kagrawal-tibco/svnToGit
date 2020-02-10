package com.tibco.cep.webstudio.client.groups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tibco.cep.webstudio.client.decisiontable.RuleFunctionNavigatorResource;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;

public class ProjectsContentGroup extends ContentGroup {

	private HashMap<String, List<NavigatorResource>> projectToResourceMap = new HashMap<String, List<NavigatorResource>>();

	public ProjectsContentGroup(String groupId) {
		super(groupId);
	}

	public void setProjectResources(String projectName, NavigatorResource[] projectResources) {
		List<NavigatorResource> list = this.projectToResourceMap.get(projectName);
		List<NavigatorResource> projResources;
		if (list != null) {
			projResources = new ArrayList<NavigatorResource>(list);
		} else {
			projResources = new ArrayList<NavigatorResource>();
		}
		List<ContentGroupResourceChangedDelta> changed = new ArrayList<ContentGroupResourceChangedDelta>();
		if (this.resources == null) {
			this.resources = new ArrayList<NavigatorResource>();
		}
		for (NavigatorResource navigatorResource : projectResources) {
			navigatorResource.setShouldShowAsRootNode(true);
			projResources.add(navigatorResource);
			this.resources.add(navigatorResource);
			ContentGroupResourceChangedDelta rDelta = new ContentGroupResourceChangedDelta(navigatorResource, ContentGroupChangeType.ADDED);
			changed.add(rDelta);
			
			addArtifactsToGroups(navigatorResource);
		}
		this.projectToResourceMap.put(projectName, projResources);
		ContentGroupChangedDelta gDelta = new ContentGroupChangedDelta(groupId, changed, ContentGroupChangeType.CHANGED);
		List<ContentGroupChangedDelta> changedGrps = new ArrayList<ContentGroupChangedDelta>();
		changedGrps.add(gDelta);
		ContentModelChangedDelta modelDelta = new ContentModelChangedDelta(changedGrps);
		ContentModelManager.getInstance().fireContentModelChangedEvent(modelDelta);
	}
	
	/**
	 * Add project artifacts to RTI & DT system groups
	 * 
	 * @param resource
	 */
	private void addArtifactsToGroups(NavigatorResource resource) {
		ContentGroup contentGroup = null;
		
		if (resource.getType() != null) {
			if (resource.getType().equals(ARTIFACT_TYPES.RULETEMPLATEINSTANCE.getValue().toLowerCase()) || 
					resource.getType().equals(ARTIFACT_TYPES.RULETEMPLATE.getValue().toLowerCase())) {
				contentGroup = ContentModelManager.getInstance().getGroup(ContentModelManager.BUSINESS_RULES_GROUP_ID);
			} else if (resource.getType().equals(ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue().toLowerCase()) ||
					resource.getType().equals(ARTIFACT_TYPES.RULEFUNCTION.getValue().toLowerCase())) {
				if (resource instanceof RuleFunctionNavigatorResource && !((RuleFunctionNavigatorResource) resource).isVRF()) {
					return;
				}
				contentGroup = ContentModelManager.getInstance().getGroup(ContentModelManager.DECISION_TABLES_GROUP_ID);
			} else if (resource.getType().equals(ARTIFACT_TYPES.PROCESS.getValue().toLowerCase())) {
				contentGroup = ContentModelManager.getInstance().getGroup(ContentModelManager.PROCESSES_GROUP_ID);
			}

			if (contentGroup != null) {
				contentGroup.addResource(resource);
			}
		}
	}
	
	/**
	 * Fetch resources associated to the specified project
	 * 
	 * @param projectName
	 * @return
	 */
	public List<NavigatorResource> getResourcesByProject(String projectName) {
		return projectToResourceMap.get(projectName);
	}
	
	/**
	 * Remove the project name and the associated artifact list
	 * 
	 * @param projectName
	 */
	public void removeProject(String projectName) {
		if (projectToResourceMap.containsKey(projectName)) {
			projectToResourceMap.remove(projectName);
		}
	}
	
	/**
	 * Get the total projects checkedout
	 * @return
	 */
	public int getTotalProjects() {
		return projectToResourceMap.size();
	}
}
