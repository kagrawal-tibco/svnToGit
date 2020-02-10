/**
 * 
 */
package com.tibco.cep.webstudio.client.request.model.impl.dataitem;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.XMLRequestBuilderConstants;

/**
 * XML representation of worklist details
 * 
 * @author Vikram Patil
 */
public class WorklistDataItem implements IRequestDataItem {
	
	private String artifactPath;
	private String reviewStatus;
	private String reviewComments;
	private String artifactType;
	private String projectName;
	private String deployEnvironments;
	
	/**
	 * 
	 * @param revisionId
	 * @param artifactPath
	 * @param reviewStatus
	 * @param reviewComments
	 * @param artifactType
	 */
	public WorklistDataItem(String artifactPath,
							String reviewStatus,
							String reviewComments,
							String artifactType,
							String projectName) {
		super();
		this.artifactPath = artifactPath;
		this.reviewStatus = reviewStatus;
		this.reviewComments = reviewComments;
		this.artifactType = artifactType;
		this.projectName = projectName;
	}

	/**
	 * 
	 */
	public WorklistDataItem() {
	}

	@Override
	public void serialize(Document rootDocument, Node rootNode) {
		Node worklistItemElement = rootDocument.createElement(XMLRequestBuilderConstants.WORKLIST_ITEM_ELEMENT);
		rootNode.appendChild(worklistItemElement);
		
		Node worklistArtifactPath = rootDocument.createElement(XMLRequestBuilderConstants.WORKLIST_ARTIFACT_PATH_ELEMENT);
		worklistArtifactPath.appendChild(rootDocument.createTextNode(artifactPath));
		worklistItemElement.appendChild(worklistArtifactPath);
		
		Node worklistArtifactType = rootDocument.createElement(XMLRequestBuilderConstants.WORKLIST_ARTIFACT_TYPE_ELEMENT);
		worklistArtifactType.appendChild(rootDocument.createTextNode(artifactType));
		worklistItemElement.appendChild(worklistArtifactType);
		
		Node worklistProjectName = rootDocument.createElement(XMLRequestBuilderConstants.WORKLIST_PROJECT_NAME_ELEMENT);
		worklistProjectName.appendChild(rootDocument.createTextNode(projectName));
		worklistItemElement.appendChild(worklistProjectName);
		
		Node worklistReviewStatus = rootDocument.createElement(XMLRequestBuilderConstants.WORKLIST_REVIEWER_STATUS_ELEMENT);
		worklistReviewStatus.appendChild(rootDocument.createTextNode(reviewStatus));
		worklistItemElement.appendChild(worklistReviewStatus);
		
		Node worklistReviewComments = rootDocument.createElement(XMLRequestBuilderConstants.WORKLIST_REVIEWER_COMMENTS_ELEMENT);
		worklistReviewComments.appendChild(rootDocument.createTextNode(reviewComments));
		worklistItemElement.appendChild(worklistReviewComments);
		
		if (deployEnvironments != null) {
			Node worklistDeployEnvironments = rootDocument.createElement(XMLRequestBuilderConstants.WORKLIST_DEPLOY_ENVIRONMENTS);
			worklistDeployEnvironments.appendChild(rootDocument.createTextNode(deployEnvironments));
			worklistItemElement.appendChild(worklistDeployEnvironments);
		}
	}

	public void setArtifactPath(String artifactPath) {
		this.artifactPath = artifactPath;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public void setReviewComments(String reviewComments) {
		this.reviewComments = reviewComments;
	}

	public void setArtifactType(String artifactType) {
		this.artifactType = artifactType;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setDeployEnvironments(String deployEnvironments) {
		this.deployEnvironments = deployEnvironments;
	}
}
