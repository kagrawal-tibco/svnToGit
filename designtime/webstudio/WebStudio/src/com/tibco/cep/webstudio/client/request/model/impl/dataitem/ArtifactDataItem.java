package com.tibco.cep.webstudio.client.request.model.impl.dataitem;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.XMLRequestBuilderConstants;

/**
 * Can be used to represent an artifact for checkout/commit etc.
 *
 */
public class ArtifactDataItem implements IRequestDataItem {
	
	/**
	 * FQN of artifact.
	 */
	private String artifactPath;
	
	/**
	 * Type of artifact.
	 */
	private String artifactType;
	
	/**
	 * File extension of artifact.
	 */
	private String fileExtension;
	
	/**
	 * Base64 encoded if required.
	 */
	private String artifactContents;
	
	/**
	 * Change type for artifact
	 */
	private String changeType;
	
	/**
	 * Base artifact Path
	 */
	private String baseArtifactPath;

	@Override
	public void serialize(Document rootDocument, Node rootNode) {
		Node artifactItemElement = rootDocument.createElement(XMLRequestBuilderConstants.ARTIFACT_ITEM_ELEMENT);
		rootNode.appendChild(artifactItemElement);
		
		Element artifactPathElement = 
				rootDocument.createElement(XMLRequestBuilderConstants.ARTIFACT_PATH_ELEMENT);
		Text artifactPathText = rootDocument.createTextNode(artifactPath);
		artifactPathElement.appendChild(artifactPathText);
		artifactItemElement.appendChild(artifactPathElement);
		
		if (artifactType != null) {
			Element artifactTypelement = 
					rootDocument.createElement(XMLRequestBuilderConstants.ARTIFACT_TYPE_ELEMENT);
			Text artifactTypeText = rootDocument.createTextNode(artifactType);
			artifactTypelement.appendChild(artifactTypeText);
			artifactItemElement.appendChild(artifactTypelement);
		}
		
		if (fileExtension != null) {
			Element artifactFileExtnElement = 
					rootDocument.createElement(XMLRequestBuilderConstants.ARTIFACT_FILE_EXTN_ELEMENT);
			Text artifactFileExtnText = rootDocument.createTextNode(fileExtension);
			artifactFileExtnElement.appendChild(artifactFileExtnText);
			artifactItemElement.appendChild(artifactFileExtnElement);
		}
		
		if (artifactContents != null) {
			Element artifactContentsElement = 
					rootDocument.createElement(XMLRequestBuilderConstants.ARTIFACT_CONTENTS_ELEMENT);
			Text artifactContentsText = rootDocument.createTextNode(artifactContents);
			artifactContentsElement.appendChild(artifactContentsText);
			artifactItemElement.appendChild(artifactContentsElement);
		}
		
		if (baseArtifactPath != null) {
			Element baseArtifactPathElement = 
					rootDocument.createElement(XMLRequestBuilderConstants.ARTIFACT_BASE_ARTIFACT_PATH);
			Text baseArtifactPathText = rootDocument.createTextNode(baseArtifactPath);
			baseArtifactPathElement.appendChild(baseArtifactPathText);
			artifactItemElement.appendChild(baseArtifactPathElement);
		}
		
		if (changeType != null) {
			Element changeTypeElement = 
					rootDocument.createElement(XMLRequestBuilderConstants.ARTIFACT_CHANGE_TYPE);
			Text changeTypeText = rootDocument.createTextNode(changeType);
			changeTypeElement.appendChild(changeTypeText);
			artifactItemElement.appendChild(changeTypeElement);
		}		
	}

	/**
	 * @return the artifactPath
	 */
	public final String getArtifactPath() {
		return artifactPath;
	}

	/**
	 * @param artifactPath the artifactPath to set
	 */
	public final void setArtifactPath(String artifactPath) {
		this.artifactPath = artifactPath;
	}

	/**
	 * @return the artifactType
	 */
	public final String getArtifactType() {
		return artifactType;
	}

	/**
	 * @param artifactType the artifactType to set
	 */
	public final void setArtifactType(String artifactType) {
		this.artifactType = artifactType;
	}

	/**
	 * @return the fileExtension
	 */
	public final String getFileExtension() {
		return fileExtension;
	}

	/**
	 * @param fileExtension the fileExtension to set
	 */
	public final void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	/**
	 * @return the artifactContents
	 */
	public final String getArtifactContents() {
		return artifactContents;
	}

	/**
	 * @param artifactContents the artifactContents to set
	 */
	public final void setArtifactContents(String artifactContents) {
		this.artifactContents = artifactContents;
	}

	/**
	 * @return changeType
	 */
	public String getChangeType() {
		return changeType;
	}

	/**
	 * @param changeType
	 */
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	/**
	 * 
	 * @return
	 */
	public String getBaseArtifactPath() {
		return baseArtifactPath;
	}

	/**
	 * 
	 * @param baseArtifactPath
	 */
	public void setBaseArtifactPath(String baseArtifactPath) {
		this.baseArtifactPath = baseArtifactPath;
	}
}
