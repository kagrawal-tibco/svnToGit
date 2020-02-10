/**
 * 
 */
package com.tibco.cep.studio.rms.client.builder.impl;

import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_ARTIFACT_COMMIT;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_ARTIFACT_EXTENSION;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_ARTIFACT_PATH;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_PROJECT_NAMES;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_REVISION_ID;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.model.ArtifactRevisionMetadata;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

/**
 * @author aathalye
 *
 */
public class ArtifactsRevisionContentRequestBuilderInput extends AbstractRequestBuilderInput {
	
	private ArtifactRevisionMetadata artifactRevisionMetadata;
	
	
	public ArtifactsRevisionContentRequestBuilderInput(ArtifactRevisionMetadata artifactRevisionMetadata) {
		this.artifactRevisionMetadata = artifactRevisionMetadata;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.client.builder.impl.AbstractRequestBuilderInput#buildXMLPart()
	 */
	@Override
	protected XiNode buildXMLPart() {
		Artifact artifact = artifactRevisionMetadata.getArtifact();
		String projectName = artifactRevisionMetadata.getProjectName();
		//Get its path and type
		String artifactPath = artifact.getArtifactPath();
		String artifactType = artifact.getArtifactType().getLiteral();
		
		String revisionId = artifactRevisionMetadata.getRevisionId();
				
		XiFactory factory = XiSupport.getXiFactory();
		XiNode root = factory.createDocument();
		//Create element artifact
		XiNode artifactCommittedElement = factory.createElement(EX_ARTIFACT_COMMIT);
		root.appendChild(artifactCommittedElement);
		
		XiNode projectNameElement = factory.createElement(EX_PROJECT_NAMES);
		projectNameElement.setStringValue(projectName);
		artifactCommittedElement.appendChild(projectNameElement);
		
		XiNode artifactPathElement = factory.createElement(EX_ARTIFACT_PATH);
		artifactPathElement.setStringValue(artifactPath);
		artifactCommittedElement.appendChild(artifactPathElement);
		
		XiNode revisionIdElement = factory.createElement(EX_REVISION_ID);
		revisionIdElement.setStringValue(revisionId);
		artifactCommittedElement.appendChild(revisionIdElement);
		
		XiNode artifactExtnElement = factory.createElement(EX_ARTIFACT_EXTENSION);
		artifactExtnElement.setStringValue(artifactType);
		artifactCommittedElement.appendChild(artifactExtnElement);
		
		return root;
	}
}
