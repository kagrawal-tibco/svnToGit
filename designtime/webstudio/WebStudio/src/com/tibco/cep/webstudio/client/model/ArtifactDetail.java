/**
 * 
 */
package com.tibco.cep.webstudio.client.model;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;

/**
 * @author vpatil
 */
public class ArtifactDetail {
	private String artifactPath;
	private String baseArtifactPath;
	private boolean isLocked;
	private String processType;
	
	public ArtifactDetail(String artifactPath, String baseArtifactPath) {
		super();
		this.artifactPath = artifactPath;
		this.baseArtifactPath = baseArtifactPath;
	}

	public String getArtifactPath() {
		return artifactPath;
	}

	public void setArtifactPath(String artifactPath) {
		this.artifactPath = artifactPath;
	}

	public String getBaseArtifactPath() {
		return baseArtifactPath;
	}

	public void setBaseArtifactPath(String baseArtifactPath) {
		this.baseArtifactPath = baseArtifactPath;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}
	
	public void serialize(Document rootDocument, Node rootNode) {
		Element artifactElement = rootDocument.createElement("artifactRecord");
		rootNode.appendChild(artifactElement);
		
		Element artifactPathElement = rootDocument.createElement("artifactPath");
		Text artifactPathText = rootDocument.createTextNode(artifactPath);
		artifactPathElement.appendChild(artifactPathText);
		artifactElement.appendChild(artifactPathElement);

		Element baseArtifactPathElement = rootDocument.createElement("baseArtifactPath");
		Text baseArtifactPathText = rootDocument.createTextNode(baseArtifactPath);
		baseArtifactPathElement.appendChild(baseArtifactPathText);
		artifactElement.appendChild(baseArtifactPathElement);
	}	
}
