/**
 * 
 */
package com.tibco.cep.webstudio.client.util;

import static com.tibco.cep.webstudio.client.util.ArtifactUtil.isDecisionManagerInstalled;
import static com.tibco.cep.webstudio.client.util.ArtifactUtil.isBPMNInstalled;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tab.Tab;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableEditor;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableNavigatorResource;
import com.tibco.cep.webstudio.client.decisiontable.RuleFunctionNavigatorResource;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor;
import com.tibco.cep.webstudio.client.groups.ContentGroup;
import com.tibco.cep.webstudio.client.groups.ContentModelManager;
import com.tibco.cep.webstudio.client.groups.GroupContentsWindow;
import com.tibco.cep.webstudio.client.i18n.DisplayPropertiesManager;
import com.tibco.cep.webstudio.client.model.ArtifactDetail;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RuleTemplateInstanceNavigatorResource;
import com.tibco.cep.webstudio.client.model.RuleTemplateNavigatorResource;
import com.tibco.cep.webstudio.client.portal.DASHBOARD_PORTLETS;
import com.tibco.cep.webstudio.client.process.ProcessNavigatorResource;
import com.tibco.cep.webstudio.client.widgets.WebStudioToolbar;
import com.tibco.cep.webstudio.model.rule.instance.DisplayModel;

/**
 * Utility class providing various methods for generating a project structure.
 * 
 * @author Vikram Patil
 */
public class ProjectExplorerUtil {
	public static final String ICON_PATH = Page.getAppImgDir() + "icons/16/";
	public static final String ROOT_NODE = "Root";

	public static enum ARTIFACT_TYPES {
		PROJECT, FOLDER, PROCESS, RULE, RULEFUNCTION, RULEFUNCTIONIMPL, RULETEMPLATE, RULETEMPLATEINSTANCE, DOMAIN, SCORECARD, CONCEPT, EVENT, ARGUMENT, RULETEMPLATEVIEW, CHANNEL,
		CONCEPTTESTDATA, EVENTTESTDATA;

		public String getValue() {
			return this.toString().toLowerCase();
		}
	}
	
	public static ARTIFACT_TYPES typeToEditorType(String type){
		if (type == null){
			return null;
		} else if (type.equalsIgnoreCase("beprocess") || type.equalsIgnoreCase("process")) {
			return ARTIFACT_TYPES.PROCESS;
		} else if (type.equalsIgnoreCase("ruletemplate")) {
			return ARTIFACT_TYPES.RULETEMPLATE;
		} else if (type.equalsIgnoreCase("ruletemplateinstance")) {
			return ARTIFACT_TYPES.RULETEMPLATEINSTANCE;
		} else if (type.equalsIgnoreCase("scorecard")) {
			return ARTIFACT_TYPES.SCORECARD;
		} else if (type.equalsIgnoreCase("concept")) {
			return ARTIFACT_TYPES.CONCEPT;
		} else if (type.equalsIgnoreCase("event")) {
			return ARTIFACT_TYPES.EVENT;
		} else if (type.equalsIgnoreCase("rule")) {
			return ARTIFACT_TYPES.RULE;
		} else if (type.equalsIgnoreCase("rulefunctionimpl")) {
			return ARTIFACT_TYPES.RULEFUNCTIONIMPL;
		} else if (type.equalsIgnoreCase("rulefunction")) {
			return ARTIFACT_TYPES.RULEFUNCTION;
		} else if (type.equalsIgnoreCase("ruletemplateview")) {
			return ARTIFACT_TYPES.RULETEMPLATEVIEW;
		} else if (type.equalsIgnoreCase("channel")) {
			return ARTIFACT_TYPES.CHANNEL;
		} else if (type.equalsIgnoreCase("domain")) {
			return ARTIFACT_TYPES.DOMAIN;
		}
		return null;
	}

	/**
	 * Based on the selected artifacts associated to a project, create a project
	 * structure to be displayed in the explorer.
	 * 
	 * @param artifactList
	 * @param projectName
	 * @return
	 */
	public static NavigatorResource[] createProjectExplorerTree(List<String> artifactList, String projectName) {

		List<NavigatorResource> navResources = new ArrayList<NavigatorResource>();

		NavigatorResource rootNode = new NavigatorResource(projectName,
				ROOT_NODE,
				projectName,
				ARTIFACT_TYPES.PROJECT.getValue(),
				ICON_PATH + "studioproject.gif",
				null);
		navResources.add(rootNode);

		String filePath[];
		for (String artifactPath : artifactList) {
			filePath = (artifactPath.startsWith("/")) ? artifactPath.substring(1).split("/")
					: new String[] { artifactPath };
			addChildren(filePath, rootNode, artifactPath.substring(artifactPath.lastIndexOf(".") + 1), navResources);
		}
		return navResources.toArray(new NavigatorResource[navResources.size()]);
	}

	/**
	 * Adding children to the Project tree structure
	 * 
	 * @param paths
	 * @param rootNode
	 * @param extension
	 * @param navResources
	 */
	private static void addChildren(String[] paths,
									NavigatorResource rootNode,
									String extension,
									List<NavigatorResource> navResources) {
		List<NavigatorResource> childNodeList = rootNode.getChildren();
		NavigatorResource currentNode = rootNode;

		NavigatorResource parent = null;
		String artifactId = null;
		NavigatorResource newResource = null;
		boolean artifactExists = false;

		for (int i = 0; i < paths.length; i++) {
			artifactExists = false;

			for (NavigatorResource node : childNodeList) {
				if (paths[i].equals(node.getName())) {
					newResource = node;
					artifactExists = true;
					break;
				}
			}

			if (!artifactExists) {
				// reset values
				parent = (i == 0) ? rootNode : currentNode;
				artifactId = createID(paths, i, rootNode.getName());

				boolean isFolder = (i < paths.length - 1) ? true : false;
				newResource = createProjectResource(paths[i], parent.getId(), extension, artifactId, isFolder);

				currentNode.getChildren().add(newResource);
				navResources.add(newResource);
			}

			if (i < paths.length - 1) {
				currentNode = newResource;
				childNodeList = currentNode.getChildren();
			}
		}
	}

	/**
	 * Creates a project resource based of the specified type
	 * 
	 * @param name
	 * @param parentId
	 * @param extension
	 * @param artifactId
	 * @param isFolder
	 * @return
	 */	
	public static NavigatorResource createProjectResource(String name,
			String parentId,
			String extension,
			String artifactId,
			boolean isFolder) {
		return createProjectResource(name, parentId, null, extension, artifactId, isFolder);
	}
	
	/**
	 * Creates a project resource based of the specified type
	 * 
	 * @param name
	 * @param parentId
	 * @param UI parentId
	 * @param extension
	 * @param artifactId
	 * @param isFolder
	 * @return
	 */
	public static NavigatorResource createProjectResource(String name,
															String parentId,
															String uiParentId,
															String extension,
															String artifactId,
															boolean isFolder) {
		NavigatorResource newResource;
		String iconPath = getIcon(extension);
		if (isFolder) {
			newResource = new NavigatorResource(name, parentId, artifactId, ARTIFACT_TYPES.FOLDER.getValue(), ICON_PATH
					+ "folder.png", ARTIFACT_TYPES.FOLDER);
		} else {
			if ("rulefunctionimpl".equalsIgnoreCase(extension)) {
				newResource = new DecisionTableNavigatorResource(name,
						parentId,
						uiParentId,
						artifactId,
						ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue(),
						iconPath,
						ARTIFACT_TYPES.RULEFUNCTIONIMPL);
			} else if ("beprocess".equalsIgnoreCase(extension)) {
				newResource = new ProcessNavigatorResource(name,
						parentId,
						artifactId,
						ARTIFACT_TYPES.PROCESS.getValue(),
						iconPath,
						ARTIFACT_TYPES.PROCESS);
			} else if ("ruletemplate".equalsIgnoreCase(extension)) {
				newResource = new RuleTemplateNavigatorResource(name,
						parentId,
						artifactId,
						ARTIFACT_TYPES.RULETEMPLATE.getValue(),
						iconPath,
						ARTIFACT_TYPES.RULETEMPLATE,
						null,
						null);
			} else if ("ruletemplateinstance".equalsIgnoreCase(extension)) {
				newResource = new RuleTemplateInstanceNavigatorResource(name,
						parentId,
						uiParentId,
						artifactId,
						ARTIFACT_TYPES.RULETEMPLATEINSTANCE.getValue(),
						iconPath,
						ARTIFACT_TYPES.RULETEMPLATEINSTANCE);
			} else if ("scorecard".equalsIgnoreCase(extension)) {
				newResource = new NavigatorResource(name,
						parentId,
						artifactId,
						ARTIFACT_TYPES.SCORECARD.getValue(),
						iconPath,
						ARTIFACT_TYPES.SCORECARD);
			} else if ("concept".equalsIgnoreCase(extension)) {
				newResource = new NavigatorResource(name,
						parentId,
						artifactId,
						ARTIFACT_TYPES.CONCEPT.getValue(),
						iconPath,
						ARTIFACT_TYPES.CONCEPT);
			} else if ("event".equalsIgnoreCase(extension)) {
				newResource = new NavigatorResource(name,
						parentId,
						artifactId,
						ARTIFACT_TYPES.EVENT.getValue(),
						iconPath,
						ARTIFACT_TYPES.EVENT);
			} else if ("rule".equalsIgnoreCase(extension)) {
				newResource = new NavigatorResource(name,
						parentId,
						artifactId,
						ARTIFACT_TYPES.RULE.getValue(),
						iconPath,
						ARTIFACT_TYPES.RULE);
			} else if ("rulefunction".equalsIgnoreCase(extension)) {
				newResource = new RuleFunctionNavigatorResource(name,
						parentId,
						artifactId,
						ARTIFACT_TYPES.RULEFUNCTION.getValue(),
						iconPath,
						ARTIFACT_TYPES.RULEFUNCTION);
			} else if ("ruletemplateview".equalsIgnoreCase(extension)) {
				newResource = new NavigatorResource(name,
						parentId,
						artifactId,
						ARTIFACT_TYPES.RULETEMPLATEVIEW.getValue(),
						iconPath,
						ARTIFACT_TYPES.RULETEMPLATEVIEW);
			} else if ("channel".equalsIgnoreCase(extension)) {
				newResource = new NavigatorResource(name,
						parentId,
						artifactId,
						ARTIFACT_TYPES.CHANNEL.getValue(),
						iconPath,
						ARTIFACT_TYPES.CHANNEL);
			} else if ("domain".equalsIgnoreCase(extension)) {
				newResource = new NavigatorResource(name,
						parentId,
						artifactId,
						ARTIFACT_TYPES.DOMAIN.getValue(),
						iconPath,
						ARTIFACT_TYPES.DOMAIN);
			} else if ("concepttestdata".equalsIgnoreCase(extension)) {
				newResource = new NavigatorResource(name,
						parentId,
						artifactId,
						ARTIFACT_TYPES.CONCEPTTESTDATA.getValue(),
						iconPath,
						ARTIFACT_TYPES.CONCEPTTESTDATA);
			} else if ("eventtestdata".equalsIgnoreCase(extension)) {
				newResource = new NavigatorResource(name,
						parentId,
						artifactId,
						ARTIFACT_TYPES.EVENTTESTDATA.getValue(),
						iconPath,
						ARTIFACT_TYPES.EVENTTESTDATA);
			} else {
				// Other types
				newResource = new NavigatorResource(name,
						parentId,
						artifactId,
						null,
						iconPath,
						null);
			}
		}
		return newResource;
	}

	/**
	 * Generate the appropriate id for the project node.
	 * 
	 * @param paths
	 * @param cnt
	 * @param rootID
	 * @return
	 */
	private static String createID(String[] paths, int cnt, String rootID) {
		String id = rootID + "$";

		for (int i = 0; i <= cnt; i++) {
			id += paths[i];
			if (i < cnt)
				id += "$";
		}

		return id;
	}

	/**
	 * Select appropriate icon file based on the attribute type.
	 * 
	 * @param extn
	 * @return
	 */
	public static String getIcon(String extn) {
		if (extn.equals("concept")) {
			return ICON_PATH + "concept.png";
		} else if (extn.equals("scorecard")) {
			return ICON_PATH + "scorecard.png";
		} else if (extn.equals("event")) {
			return ICON_PATH + "event.png";
		} else if (extn.equals("time")) {
			return ICON_PATH + "time-event.gif";
		} else if (extn.equals("channel")) {
			return ICON_PATH + "channel.png";
		} else if (extn.equals("rule")) {
			return ICON_PATH + "rule.png";
		} else if (extn.equals("rulefunction")) {
			return ICON_PATH + "rulefunction.png";
		} else if (extn.equals("ruletemplate")) {
			return ICON_PATH + "rulesTemplate.png";
		} else if (extn.equals("ruletemplateinstance")) {
			return ICON_PATH + "rulesTemplateInstance.png";
		} else if (extn.equals("ruletemplateview")) {
			return ICON_PATH + "rulesTemplateView.png";
		} else if (extn.equals("rulefunctionimpl")) {
			return ICON_PATH + "decisiontable.png";
		} else if (extn.equals("cdd")) {
			return ICON_PATH + "cdd_16x16.gif";
		} else if (extn.equals("statemachine")) {
			return ICON_PATH + "state_machine.png";
		} else if (extn.equals("beprocess")) {
			return ICON_PATH + "appicon16x16.gif";
		} else if (extn.equals("domain")) {
			return ICON_PATH + "domain.png";
		} else if (extn.equals("concepttestdata")) {
			return ICON_PATH + "conceptTestData.png";
		} else if (extn.equals("eventtestdata")) {
			return ICON_PATH + "eventTestData.png";
		}
		return ICON_PATH + "file.png";
	}

	/**
	 * Updates the project Explorer based of the selected list of artifact
	 * Types.
	 * 
	 * @param artifactList
	 * @param projectName
	 */
	public static void updateProjectExplorerTree(ListGridRecord[] artifactList, String projectName) {
		for (ListGridRecord record : artifactList) {
			String changeType = record.getAttributeAsString("changeType");
			
			if (!(changeType.toLowerCase().equals("modify") || changeType.toLowerCase().equals("modified"))) {
				String artifactId = (projectName + record.getAttributeAsString("artifactPath") + "."
						+ record.getAttributeAsString("fileExtension")).replace("/", "$");
				String parentId = artifactId.substring(0, artifactId.lastIndexOf("$"));
				String extn = record.getAttributeAsString("fileExtension");
				String artifactName = artifactId.substring(artifactId.lastIndexOf("$")+1, artifactId.length());

				String baseArtifactPath = record.getAttributeAsString("baseArtifactPath");
				if (baseArtifactPath != null) { 
					baseArtifactPath = projectName + baseArtifactPath.replace("/", "$");
				}
				
				NavigatorResource updatedArtifact = createProjectResource(artifactName, parentId, baseArtifactPath, extn, artifactId, false);

				boolean toAdd = (changeType.toLowerCase().equals("added")) ? true : false;
				
				// update groups
				updateGroups(updatedArtifact, toAdd);
				
				// update artifacts
				if (!toAdd) updateDashboardArtifacts(updatedArtifact);
			}
		}
	}
	
	/**
	 * Update all groups with the project updated data
	 * 
	 * @param updatedArtifact
	 * @param toAdd
	 */
	public static void updateGroups(NavigatorResource updatedArtifact, boolean toAdd) {
		List<ContentGroup> groupList = ContentModelManager.getInstance().getGroups();

		boolean groupAffected = false;
		List<ContentGroup> updatedGroups = new ArrayList<ContentGroup>();
		for (ContentGroup group : groupList) {
			List<NavigatorResource> groupResources = group.getLocalResources();

			if (groupResources != null) {
				if (toAdd && (group.getGroupId().equals(ContentModelManager.PROJECTS_GROUP_ID)
						|| (group.getGroupId().equals(ContentModelManager.BUSINESS_RULES_GROUP_ID) && updatedArtifact
								.getId().indexOf("ruletemplateinstance") != -1)
						|| (group.getGroupId().equals(ContentModelManager.DECISION_TABLES_GROUP_ID) && updatedArtifact.getId()
								.indexOf("rulefunctionimpl") != -1)
						|| (group.getGroupId().equals(ContentModelManager.PROCESSES_GROUP_ID) && updatedArtifact.getId()
										.indexOf("beprocess") != -1))) {
						if (!groupResources.contains(updatedArtifact)) {
							groupResources.add(updatedArtifact);
							groupAffected = true;
						} else {
							int resourceIndex = groupResources.indexOf(updatedArtifact);
							groupResources.set(resourceIndex, updatedArtifact);
							groupAffected = true;
						}
				} else if (!toAdd) {
					if (groupResources.contains(updatedArtifact)) {
						groupResources.remove(updatedArtifact);
						groupAffected = true;
					}
				}
				
				if (groupAffected) {
					updatedGroups.add(group);
					groupAffected = false;
				}
			}
		}
		
		// update the explorer
		GroupContentsWindow workspaceGroupContentWindow = (GroupContentsWindow) WebStudio.get().getWorkspacePage()
				.getMyGroups().getContainer();
		GroupContentsWindow dashboardGroupContentWindow = WebStudio.get().getPortalPage().getGroupContentWindowtFromGroupPortlet();
		for (ContentGroup group : updatedGroups) {
			// update workspace
			if (workspaceGroupContentWindow.getCurrentGroup(group.getGroupId()) != null) {
				if (toAdd) {
					NavigatorResource selectedResource = workspaceGroupContentWindow.getArtifactTreeGrid().getByResource(updatedArtifact);
					if (selectedResource != null) {
						workspaceGroupContentWindow.getArtifactTreeGrid().removeRecord(selectedResource.getId(), true);
					}
					workspaceGroupContentWindow.add(updatedArtifact);
				} else {
					workspaceGroupContentWindow.remove(updatedArtifact);
				}
				ProjectExplorerUtil.removeArtifactFromEditor(updatedArtifact);
			}
			//update dashboard
			if (dashboardGroupContentWindow.getCurrentGroup(group.getGroupId()) != null) {
				if (toAdd) {
					NavigatorResource selectedResource = dashboardGroupContentWindow.getArtifactTreeGrid().getByResource(updatedArtifact);
					if (selectedResource != null) {
						dashboardGroupContentWindow.getArtifactTreeGrid().removeRecord(selectedResource.getId(), true);
					}
					dashboardGroupContentWindow.add(updatedArtifact);
				} else {
					dashboardGroupContentWindow.remove(updatedArtifact);
				}
			}
		}
	}
	
	/**
	 * Remove the artifact editor(if open) once the artifact is deleted
	 * 
	 * @param resource
	 */
	public static void removeArtifactFromEditor(NavigatorResource resource) {
		Tab[] tabs = WebStudio.get().getEditorPanel().getTabs();
		
		for (Tab tab : tabs) {
			if (tab instanceof AbstractEditor) {
				NavigatorResource resourceInEditor = ((AbstractEditor) tab).getSelectedResource();
				if (resourceInEditor.getId().equalsIgnoreCase(resource.getId()) || resourceInEditor.getId().startsWith(resource.getId())) {
					((AbstractEditor)tab).close();
					WebStudio.get().getEditorPanel().removeTab(tab);
					WebStudio.get().getEditorPanel().getBottomPane().setVisible(false);
				}
			}
		}
	}
	
	/**
	 * Remove artifacts from recently opened portlets if they have been deleted
	 * from the server
	 * 
	 * @param updatedArtifact
	 */
	private static void updateDashboardArtifacts(NavigatorResource updatedArtifact) {
		if (updatedArtifact != null) {
			String projectName = updatedArtifact.getId().substring(0,
					updatedArtifact.getId().indexOf("$"));
			String artifactPath = updatedArtifact.getId().substring(
					updatedArtifact.getId().indexOf("$"),
					updatedArtifact.getId().indexOf(".")).replace("$", "/");

			WebStudio
					.get()
					.getPortalPage()
					.removeFromDashboardArtifactPortlet(projectName,
							artifactPath, DASHBOARD_PORTLETS.RECENTLY_OPENED);
		}
	}

	/**
	 * Captures the details associated with the currently selected record.
	 * 
	 * @param selectedNode
	 */
	public static void captureRecordSelection(NavigatorResource selectedNode) {
		captureRecordSelection(selectedNode.getId());
	}
	
	/**
	 * Captures the details associated with the currently selected record via Id
	 * 
	 * @param nodeId
	 */
	public static void captureRecordSelection(String nodeId) {
		String selectedProject = (nodeId.indexOf("$") == -1) ? nodeId : nodeId
				.substring(0, nodeId.indexOf("$"));
		WebStudio.get().setCurrentlySelectedProject(selectedProject);
		WebStudio.get().setCurrentlySelectedArtifact(nodeId);
	}
	
	/**
	 * Enable/Disable Export & Import option based on selected artifact
	 * 
	 * @param record
	 */
	public static void toggleImportExportSelection(NavigatorResource record) {
		List<String> artifactTypes = new ArrayList<String>();
		artifactTypes.add(ARTIFACT_TYPES.RULEFUNCTION.getValue().toLowerCase());
		artifactTypes.add(ARTIFACT_TYPES.RULETEMPLATEINSTANCE.getValue().toLowerCase());
		artifactTypes.add(ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue().toLowerCase());
		
		boolean selected = (record != null && ProjectExplorerUtil.isNavigatorArtifactSelected() && (record
				.getType() != null && (artifactTypes.contains(record.getType()))))
				|| (WebStudio.get().getEditorPanel().getTabs().length > 0);
		WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_IMPORT_ID, true);
		if (!selected) {
			if (WebStudio.get().getWorkspacePage().getEditorPanel().getSelectedTab() instanceof DecisionTableEditor 
					|| WebStudio.get().getWorkspacePage().getEditorPanel().getSelectedTab() instanceof RuleTemplateInstanceEditor) {
				AbstractEditor editor = (AbstractEditor)WebStudio.get().getWorkspacePage().getEditorPanel().getSelectedTab();
				if (editor.isReadOnly()) {
					WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_EXPORT_ID, true);
				} else {
					WebStudio.get().getApplicationToolBar().enableButton(WebStudioToolbar.TOOL_STRIP_EXPORT_ID);
				}
			} else {
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_EXPORT_ID, true);
			}
		} else {
			boolean isArtifactSelected = false;
			if (record != null && record.getType().equals(ARTIFACT_TYPES.RULEFUNCTION.getValue().toLowerCase())) {
				WebStudio.get().getApplicationToolBar().enableButton(WebStudioToolbar.TOOL_STRIP_IMPORT_ID);
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_EXPORT_ID, true);
			} else if (record != null && (record.getType().equals(ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue().toLowerCase()) || record.getType().equals(ARTIFACT_TYPES.RULETEMPLATEINSTANCE.getValue().toLowerCase()))) {
				isArtifactSelected =  true;
				WebStudio.get().getApplicationToolBar().enableButton(WebStudioToolbar.TOOL_STRIP_EXPORT_ID);
			}
			if (WebStudio.get().getWorkspacePage().getEditorPanel().getSelectedTab() instanceof DecisionTableEditor
					|| WebStudio.get().getWorkspacePage().getEditorPanel().getSelectedTab() instanceof RuleTemplateInstanceEditor) {
				AbstractEditor editor = (AbstractEditor)WebStudio.get().getWorkspacePage().getEditorPanel().getSelectedTab();
				if (!isArtifactSelected && editor.isReadOnly()) {
					WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_EXPORT_ID, true);
				} else {
					WebStudio.get().getApplicationToolBar().enableButton(WebStudioToolbar.TOOL_STRIP_EXPORT_ID);
				}
			}
		}
	}

	/**
	 * Get a List of artifacts based on types
	 * 
	 * @param artifacts
	 * @param artifactType
	 * @param parentNode
	 * @param addType
	 * @param includeProject
	 */
	public static void getArtifacts(List<ListGridRecord> artifacts,
									String[] artifactTypes,
									NavigatorResource parentNode,
									boolean addType,
									boolean includeProject) {
		List<NavigatorResource> childNodes = parentNode.getChildren();
		if (childNodes.size() > 0) {
			for (NavigatorResource child : childNodes) {
				if (child.getChildren().size() > 0) {
					getArtifacts(artifacts, artifactTypes, child, addType, includeProject);
				} else {
					if (matchesType(child.getName(), artifactTypes)) {
						ListGridRecord record = new ListGridRecord();
						String artifactPath = (includeProject)? child.getId() : child.getId().substring(child.getId().indexOf("$"));
						record.setAttribute("artifact", artifactPath.replace("$", "/"));
						if (addType) {
							String typeIcon = getIcon(child.getId().substring(child.getId().indexOf(".") + 1));
							record.setAttribute("artifactType", typeIcon);
						}
						artifacts.add(record);
					}
				}
			}
		}
	}

	/**
	 * Check for possible artifact Types
	 * 
	 * @param artifact
	 * @param possibleTypes
	 * @return
	 */
	public static boolean matchesType(String artifact, String[] possibleTypes) {
		for (String type : possibleTypes) {
			if (artifact.endsWith("." + type)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Create resources/nodes associated to a project
	 * 
	 * @param artifactList
	 * @param project
	 * @return
	 */
	public static NavigatorResource[] createProjectResources(List<ArtifactDetail>artifactList, String project) {
		List<NavigatorResource> projectResources = new ArrayList<NavigatorResource>();
		Set<String> VRFResources = new HashSet<String>();
		
		for (ArtifactDetail artifactDetail : artifactList) {
			String artifact = artifactDetail.getArtifactPath();
			
			String artifactName = artifact.substring(artifact.lastIndexOf("/")+1, artifact.length());
			String artifactId = ((artifact.startsWith("/"))?project + artifact : project + "/" + artifact).replace("/", "$");
			String parentId = artifactId.substring(0, artifactId.lastIndexOf("$"));
			String extn = artifact.substring(artifact.indexOf(".")+1, artifact.length());
			
			NavigatorResource newResource = createProjectResource(artifactName, parentId, extn, artifactId, false);
			
			if (artifactDetail.getBaseArtifactPath() != null && !artifactDetail.getBaseArtifactPath().isEmpty()) {
				String artifactParentId = (artifactDetail.getBaseArtifactPath().startsWith("/") ? 
						(project + artifactDetail.getBaseArtifactPath()) :
							(project + "/" + artifactDetail.getBaseArtifactPath())).replace("/", "$"); 
				
				if (newResource instanceof DecisionTableNavigatorResource) {
					VRFResources.add(artifactParentId);
				}
				newResource.setUIParent(artifactParentId);
			}
			
			if (newResource instanceof ProcessNavigatorResource) {
				((ProcessNavigatorResource)newResource).setProcessType(artifactDetail.getProcessType());
			}
			newResource.setLocked(artifactDetail.isLocked());
			
			projectResources.add(newResource);
		}
		
		for (NavigatorResource resource : projectResources) {
			if (VRFResources.contains(resource.getId())) {
				((RuleFunctionNavigatorResource) resource).setVRF(true);
				resource.setIcon(ICON_PATH + "vrf_16x16.png");
			}
		}
		return projectResources.toArray(new NavigatorResource[projectResources.size()]);
	}
	
	/**
	 * Check if artifacts are selected/deselected
	 * 
	 * @return
	 */
	public static boolean isNavigatorArtifactSelected() {
		if (WebStudio.get().getWorkspacePage().getGroupContentsWindow()
				.getArtifactTreeGrid().getSelectedRecords().length > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Check if artifact is of project type and artifact list is shown as a
	 * tree.
	 * 
	 * @param record
	 * @return
	 */
	public static boolean isProjectSelected(NavigatorResource record) {
		if (WebStudio.get().getWorkspacePage().getGroupContentsWindow()
				.getArtifactTreeGrid().isShowAsTree()
				&& record.getType().equalsIgnoreCase(
						ARTIFACT_TYPES.PROJECT.toString())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if valid resources are selected. This is typically to enable
	 * disable toolbar options
	 * 
	 * @param record
	 * @return
	 */
	private static boolean isValidResourceSelected(NavigatorResource record) {
		if (record != null && (ArtifactUtil.isSupportedArtifact(record) || 
				record.getType().equals(ARTIFACT_TYPES.PROJECT.getValue().toLowerCase()) || 
						record.getType().equals(ARTIFACT_TYPES.FOLDER.getValue().toLowerCase()))) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Enable/Disable tool bar options based on the artifact selection
	 * 
	 * @param record
	 */
	public static void toggleToolbarOptions(NavigatorResource record, String... otherButtons) {
		if (record == null) {
			record = (NavigatorResource) WebStudio.get().getWorkspacePage().getGroupContentsWindow().getArtifactTreeGrid().getSelectedRecord();
		}
		
		if (isValidResourceSelected(record)) {
			
			if (record.getType().equals(ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue().toLowerCase()) && !isDecisionManagerInstalled(false)) {
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_DELETE_ID, true);
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_COMMIT_ID, true);
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_UPDATE_ID, true);
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_REVERT_ID, true);
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_VALIDATE_ID, true);
				for (String toolButton : otherButtons) {
					WebStudio.get().getApplicationToolBar().disableButton(toolButton, true);
				}
				return;
			} else if (record.getType().equals(ARTIFACT_TYPES.PROCESS.getValue().toLowerCase()) && !isBPMNInstalled(false)) {
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_DELETE_ID, true);
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_COMMIT_ID, true);
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_UPDATE_ID, true);
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_REVERT_ID, true);
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_VALIDATE_ID, true);
				for (String toolButton : otherButtons) {
					WebStudio.get().getApplicationToolBar().disableButton(toolButton, true);
				}
				return;
			}
			
			if(record.getType().equals(ARTIFACT_TYPES.PROJECT.getValue().toLowerCase())) {
				WebStudio.get().getApplicationToolBar().enableButton(WebStudioToolbar.TOOL_STRIP_VALIDATE_ID);
			}
			
			WebStudio.get().getApplicationToolBar().enableButton(WebStudioToolbar.TOOL_STRIP_DELETE_ID);
			WebStudio.get().getApplicationToolBar().enableButton(WebStudioToolbar.TOOL_STRIP_COMMIT_ID);
			WebStudio.get().getApplicationToolBar().enableButton(WebStudioToolbar.TOOL_STRIP_UPDATE_ID);
			WebStudio.get().getApplicationToolBar().enableButton(WebStudioToolbar.TOOL_STRIP_REVERT_ID);
			
			for (String toolButton : otherButtons) {
				WebStudio.get().getApplicationToolBar().enableButton(toolButton);
			}
		} else {
			WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_DELETE_ID, true);
			if (WebStudio.get().getWorkspacePage().isShownAsTree()) {
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_VALIDATE_ID, true);
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_COMMIT_ID, true);
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_UPDATE_ID, true);
				WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_REVERT_ID, true);
			}
			
			for (String toolButton : otherButtons) {
				WebStudio.get().getApplicationToolBar().disableButton(toolButton, true);
			}
		}
		toggleImportExportSelection(record);
	}

	public static DisplayModel getDisplayModelForResource(
			NavigatorResource record) {
		String id = ((NavigatorResource) record).getId();
		String[] split = id.split("\\$");
		String projName = split[0];
		String fileName = split[split.length-1];
		int idx = fileName.lastIndexOf('.');
		if (idx >= 0) {
			fileName = fileName.substring(0, idx);
		}
		String entityPath = "/";
		for (int i=1; i<split.length-1; i++) {
			entityPath += split[i];
			entityPath += "/";
		}
		entityPath += fileName;
		DisplayModel model = DisplayPropertiesManager.getInstance().getDisplayModel(projName, entityPath);
		return model;
	}
}