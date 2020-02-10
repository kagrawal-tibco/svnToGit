/**
 * 
 */
package com.tibco.cep.studio.rms.client.builder.impl;

import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_ARTIFACT_COMMIT_COMPLETE;

import java.util.List;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants;
import com.tibco.cep.studio.rms.model.ArtifactCommitCompletionMetaData;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

/**
 * @author aathalye
 *
 */
public class ArtifactsCommitCompletionRequestBuilderInput extends AbstractRequestBuilderInput {
	
	
	private ArtifactCommitCompletionMetaData artifactCommitMetaData;
	
	
	/**
	 * @param artifact
	 */
	public ArtifactsCommitCompletionRequestBuilderInput(ArtifactCommitCompletionMetaData artifactCommitMetaData) {
		this.artifactCommitMetaData = artifactCommitMetaData;
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.client.IRequestBuilderInput#buildRequest()
	 */
	
	public XiNode buildXMLPart() {
		XiFactory factory = XiSupport.getXiFactory();
		XiNode root = factory.createDocument();
		//Create element artifact
		XiNode checkinElement = factory.createElement(EX_ARTIFACT_COMMIT_COMPLETE);
		root.appendChild(checkinElement);
		
		String patternId = artifactCommitMetaData.getPatternId();
		List<Artifact> artifacts = artifactCommitMetaData.getArtifacts();
		//# indicates old model.
		String extId = "CHECKIN"  + "#" + patternId;
		
		XiNode extIdAttr = factory.createAttribute("extId", extId);
		checkinElement.setAttribute(extIdAttr);
		
		for (Artifact artifact : artifacts) {
			XiNode artifactPathElement = 
				factory.createElement(ExpandedName.makeName("artifactPaths"));
			artifactPathElement.setStringValue(artifact.getArtifactPath());
			checkinElement.appendChild(artifactPathElement);
		}
		
		XiNode commentsElement = 
			factory.createElement(ExpandedName.makeName("checkinComments"));
		commentsElement.setStringValue(artifactCommitMetaData.getCheckinComments());
		checkinElement.appendChild(commentsElement);
		
		XiNode checkinTimeElement = 
			factory.createElement(ExpandedName.makeName("checkinTime"));
		checkinTimeElement.setStringValue(ArtifactsManagerConstants.SDF.format(artifactCommitMetaData.getCheckinTime()));
		checkinElement.appendChild(checkinTimeElement);
		
		XiNode usernameElement = 
			factory.createElement(ExpandedName.makeName("username"));
		usernameElement.setStringValue(artifactCommitMetaData.getUsername());
		checkinElement.appendChild(usernameElement);
		
		XiNode patternIdElement = 
			factory.createElement(ExpandedName.makeName("patternId"));
		patternIdElement.setStringValue(patternId);
		checkinElement.appendChild(patternIdElement);
		
		return root;
	}

}
