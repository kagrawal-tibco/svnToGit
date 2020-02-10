/**
 * 
 */
package com.tibco.cep.studio.rms.client.builder.impl;

import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.ARTIFACT_NS;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_ARTIFACT_EXTENSION;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_ARTIFACT_PATH;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

/**
 * @author aathalye
 *
 */
public class ArtifactContentsRequestBuilderInput extends AbstractRequestBuilderInput {
	
	
	private static final ExpandedName EX_ARTIFACT = 
		ExpandedName.makeName(ARTIFACT_NS, "artifact");
	/**
	 * The input artifact
	 */
	protected Artifact artifact;

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.client.IRequestBuilderInput#buildRequest()
	 */
	@Override
	public Object buildRequest() throws Exception {
		return super.buildRequest();
	}
	
	protected XiNode buildXMLPart() {
		//Get its path and type
		String artifactPath = artifact.getArtifactPath();
		//The path contains
		String artifactType = artifact.getArtifactExtension();
		
		XiFactory factory = XiSupport.getXiFactory();
		XiNode root = factory.createDocument();
		//Create element artifact
		XiNode artifactElement = factory.createElement(EX_ARTIFACT);
		root.appendChild(artifactElement);
		
		XiNode artifactPathElement = factory.createElement(EX_ARTIFACT_PATH);
		artifactPathElement.setStringValue(artifactPath);
		artifactElement.appendChild(artifactPathElement);
		
		XiNode artifactExtnElement = factory.createElement(EX_ARTIFACT_EXTENSION);
		artifactExtnElement.setStringValue(artifactType);
		artifactElement.appendChild(artifactExtnElement);
		
		return root;
	}

	/**
	 * @param artifact
	 */
	public ArtifactContentsRequestBuilderInput(Artifact artifact) {
		this.artifact = artifact;
	}
}
