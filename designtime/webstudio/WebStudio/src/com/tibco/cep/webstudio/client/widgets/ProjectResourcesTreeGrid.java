package com.tibco.cep.webstudio.client.widgets;

import static com.tibco.cep.webstudio.client.util.ArtifactUtil.removeHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.tibco.cep.webstudio.client.groups.ContentGroup;
import com.tibco.cep.webstudio.client.groups.ContentModelManager;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.model.ArtifactDetail;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;

public class ProjectResourcesTreeGrid extends TreeGrid implements HttpSuccessHandler, HttpFailureHandler {

	private final String ICON_PATH = Page.getAppImgDir() + "icons/16/";
	private final String ROOT_NODE = "Root";
	private String projectName = null;
	private Tree folderTree;
	private TreeNode projectNode;
	
	public ProjectResourcesTreeGrid(String projectName) {
		this.projectName = projectName;
		setWidth(300);
		setHeight(200);
		setShowAllRecords(false);  
		setShowHeader(false);
		setShowOpenIcons(false);
		setShowDropIcons(false);
		setClosedIconSuffix("");
		setFixedFieldWidths(false);
		setIndentSize(23);
		setSelectionType(SelectionStyle.SINGLE);
		setLayoutAlign(Alignment.RIGHT);
		setBorder("1px solid lightgray");
		// initialize by setting field data 
		setFields(new TreeGridField("name", "Name"));  
		setOverflow(Overflow.AUTO);
		addSort(new SortSpecifier("name", SortDirection.ASCENDING));
		
		//Setup the tree
		folderTree = new Tree();
		folderTree.setModelType(TreeModelType.PARENT);
		folderTree.setIdField("id");
		folderTree.setParentIdField("parent");
		folderTree.setNameProperty("name");
		folderTree.setReportCollisions(false);
		TreeNode root = new TreeNode ("Root");
		folderTree.setRoot(root);
		
		getProjectResources();
						
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		boolean validEvent = false;
		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_GROUP_ARTIFACTS.getURL("groups", ContentModelManager.PROJECTS_GROUP_ID)) != -1) {
			Element docElement = event.getData();

			Map<String, List<ArtifactDetail>> projectArtifactList = new HashMap<String, List<ArtifactDetail>>();
			NodeList artifactElements = docElement.getElementsByTagName("record");
			for (int i=0; i < artifactElements.getLength(); i++) {
				NodeList artifactDetails = artifactElements.item(i).getChildNodes();
				
				ArtifactDetail artifactDetailItem = null;
				for (int j = 0; j < artifactDetails.getLength(); j++) {
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
						}
					}
				}
			}
			
			List<ArtifactDetail> artifactList = projectArtifactList.get(projectName);
			NavigatorResource[] resourceList = ProjectExplorerUtil.createProjectResources(artifactList, projectName);				
			for (int i = 0; i < resourceList.length; i++) {
				buildFolderTree(resourceList[i]);
			}	
			folderTree.openFolder(projectNode);					
			// set up the field data
			this.setData(folderTree);
			validEvent = true;
		}

	 	if (validEvent) {
			removeHandlers(this);
		}
	}	

	@Override
	public void onFailure(HttpFailureEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_GROUP_ARTIFACTS.getURL("groups", ContentModelManager.PROJECTS_GROUP_ID)) != -1) {
			ArtifactUtil.removeHandlers(this);
		}
	}
	
	/**
	 * Fetch the list of project artifacts from the server
	 */
	private void getProjectResources() {

		List<NavigatorResource> projectResources = null; //ContentModelManager.getInstance().getProjectsGroup().getResourcesByProject(projectName);
		List<ContentGroup> contentGroups = ContentModelManager.getInstance().getGroups();
		if (contentGroups != null && !contentGroups.isEmpty()) {
			for (ContentGroup contentGroup : contentGroups) {
				if (contentGroup != null && ContentModelManager.PROJECTS_GROUP_ID.equals(contentGroup.getGroupId())) {
					projectResources = contentGroup.getResources();
					break;
				}
			}
		}	

		if (projectResources != null) {
			Iterator<NavigatorResource> itr = projectResources.iterator();
			while (itr.hasNext()) {
				buildFolderTree(itr.next());
			}	
			folderTree.openFolder(projectNode);					
			// set up the field data
			this.setData(folderTree);
		} else {
			ArtifactUtil.addHandlers(this);
			HttpRequest request = new HttpRequest();
			request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_GROUP_NAME, ContentModelManager.PROJECTS_GROUP_ID));
			request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectName));	
			request.submit(ServerEndpoints.RMS_GET_GROUP_ARTIFACTS.getURL("groups", ContentModelManager.PROJECTS_GROUP_ID));
		}
	}
		
	private void buildFolderTree(NavigatorResource navResource) {
		TreeNode parentFolder = folderTree.getRoot();			
		TreeNode folderNode = null;
		String folderPath = "";

		String[] parts = null;
		if (ARTIFACT_TYPES.FOLDER.getValue().equals(navResource.getType())) {
			parts = navResource.getId().split("\\$");
		} else {
			parts = navResource.getId().substring(0, navResource.getId().lastIndexOf("$")).split("\\$");
		}
		
		for (int i = 0; i < parts.length; i++) {			
			String prevParent = folderPath;
			folderPath += (folderPath.isEmpty()) ? parts[i] : "$" + parts[i];
			folderNode = folderTree.findById(folderPath);
			if (folderNode == null) {			
				if (prevParent == "") {
					folderNode = new NavigatorResource(parts[i],
							ROOT_NODE,
							parts[i],
							ARTIFACT_TYPES.PROJECT.getValue(),
							ICON_PATH + "studioproject.gif",
							ARTIFACT_TYPES.PROJECT);
					projectNode = folderNode;
				} else {
					folderNode = new NavigatorResource(
							parts[i], 
							prevParent.replace("/", "$"), 
							(prevParent + "$" + parts[i]).replace("/", "$"), 
							ARTIFACT_TYPES.FOLDER.getValue(), 
							ICON_PATH + "folder.png",
							ARTIFACT_TYPES.FOLDER);
				}	
				folderTree.add(folderNode, parentFolder);
			}				
			parentFolder = folderNode;
		}
	}		
}
