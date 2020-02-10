/**
 * 
 */
package com.tibco.cep.studio.rms.client.builder.impl;

import java.util.List;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants;
import com.tibco.cep.studio.rms.model.ArtifactReviewerMetadata;
import com.tibco.cep.studio.rms.model.ArtifactStatusChangeDetails;
import com.tibco.cep.studio.rms.model.ArtifactStatusChangeMetadata;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

/**
 * @author aathalye
 *
 */
public class ArtifactStatusChangeRequestBuilderInput extends AbstractRequestBuilderInput {
	
	private static final ExpandedName EX_ARTIFACT_REVIEW_SUMMARY = 
		ExpandedName.makeName(ArtifactsManagerConstants.ARTIFACT_REVIEW_SUMMARY_NS, "ArtifactReviewSummary");
	
	private static final ExpandedName EX_ARTIFACTS = 
		ExpandedName.makeName(ArtifactsManagerConstants.ARTIFACT_REVIEW_SUMMARY_NS, "artifacts");
	
	private static final ExpandedName EX_ARTIFACT = 
		ExpandedName.makeName(ArtifactsManagerConstants.ARTIFACT_REVIEW_SUMMARY_NS, "artifact");
	
	private static final ExpandedName EX_ARTIFACT_CONCEPT =
		ExpandedName.makeName(ArtifactsManagerConstants.ARTIFACT_COMMIT_NS, "AMS_C_Artifact");
	
	private static final ExpandedName EX_REVIEWER_HISTORY =
		ExpandedName.makeName(ArtifactsManagerConstants.ARTIFACT_REVIEWER_HISTORY_NS, "AMS_C_ReviewerHistory");
	
	private List<ArtifactStatusChangeDetails> artfactsStatusChangeSummary;
	
	public ArtifactStatusChangeRequestBuilderInput(List<ArtifactStatusChangeDetails> artfactsStatusChangeSummary) {
		this.artfactsStatusChangeSummary = artfactsStatusChangeSummary;
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.client.builder.impl.AbstractRequestBuilderInput#buildXMLPart()
	 */
	@Override
	protected XiNode buildXMLPart() {
		XiFactory factory = XiSupport.getXiFactory();
		XiNode root = factory.createDocument();
		//Create element ArtifactReviewSummary
		XiNode artifactReviewSummaryElement = factory.createElement(EX_ARTIFACT_REVIEW_SUMMARY);
		root.appendChild(artifactReviewSummaryElement);
		
		for (ArtifactStatusChangeDetails statusChangeDetails : artfactsStatusChangeSummary) {
			XiNode artifactsElement = factory.createElement(EX_ARTIFACTS);
			
			List<ArtifactStatusChangeMetadata> statusChangeMetadatas = statusChangeDetails.getArtifactsStatusCangeMetadata();
			
			for (ArtifactStatusChangeMetadata statusChangeMetadata : statusChangeMetadatas) {
				
				XiNode artifactElement = factory.createElement(EX_ARTIFACT);
				
				//Create concept node
				XiNode artifactConceptElement = factory.createElement(EX_ARTIFACT_CONCEPT);
				
				Artifact artifact = statusChangeMetadata.getArtifact();
				
				
				XiNode artifactPathElement = 
					factory.createElement(ExpandedName.makeName("artifactPath"));
				artifactPathElement.setStringValue(artifact.getArtifactPath());
				artifactConceptElement.appendChild(artifactPathElement);
				
				//Create extId
				String extId = statusChangeDetails.getPatternId() + "@" + artifact.getArtifactPath();
				XiNode extIdAttr = 
					factory.createAttribute(ExpandedName.makeName("extId"), extId);
				artifactConceptElement.setAttribute(extIdAttr);
				
				XiNode artifactExtnElement = 
					factory.createElement(ExpandedName.makeName("artifactFileExtension"));
				artifactExtnElement.setStringValue(artifact.getArtifactExtension());
				artifactConceptElement.appendChild(artifactExtnElement);
				
				XiNode revisionIdElement = 
					factory.createElement(ExpandedName.makeName("artifactContent"));
				revisionIdElement.setStringValue(Long.toString(statusChangeDetails.getRevisionId()));
				artifactConceptElement.appendChild(revisionIdElement);
				
				XiNode projectElement = 
					factory.createElement(ExpandedName.makeName("projectName"));
				projectElement.setStringValue(statusChangeDetails.getProjectName());
				artifactConceptElement.appendChild(projectElement);
				
				XiNode operationElement = 
					factory.createElement(ExpandedName.makeName("operation"));
				operationElement.setStringValue(statusChangeMetadata.getArtifactOperation().getLiteral());
				artifactConceptElement.appendChild(operationElement);
				
				ArtifactReviewerMetadata reviewerInfo = statusChangeMetadata.getReviewerInfo();
				
				XiNode statusElement = 
					factory.createElement(ExpandedName.makeName("status"));
				statusElement.setStringValue(reviewerInfo.getNewStatus());
				artifactConceptElement.appendChild(statusElement);
				
				artifactElement.appendChild(artifactConceptElement);
				
				//Create reviewer history
				XiNode reviewerHistoryElement = factory.createElement(EX_REVIEWER_HISTORY);
				
				XiNode usernameElement = 
					factory.createElement(ExpandedName.makeName("username"));
				usernameElement.setStringValue(reviewerInfo.getReviewerUsername());
				reviewerHistoryElement.appendChild(usernameElement);
				
				XiNode reviewerCommentsElement = 
					factory.createElement(ExpandedName.makeName("reviewerComments"));
				reviewerCommentsElement.setStringValue(reviewerInfo.getReviewerComments());
				reviewerHistoryElement.appendChild(reviewerCommentsElement);
				
				XiNode reviewerRoleElement = 
					factory.createElement(ExpandedName.makeName("reviewerRole"));
				reviewerRoleElement.setStringValue(reviewerInfo.getReviewerRole());
				reviewerHistoryElement.appendChild(reviewerRoleElement);
				
				XiNode newStatusElement = 
					factory.createElement(ExpandedName.makeName("newStatus"));
				newStatusElement.setStringValue(reviewerInfo.getNewStatus());
				reviewerHistoryElement.appendChild(newStatusElement);
				
				XiNode oldStatusElement = 
					factory.createElement(ExpandedName.makeName("oldStatus"));
				oldStatusElement.setStringValue(reviewerInfo.getOldStatus());
				reviewerHistoryElement.appendChild(oldStatusElement);
				
				artifactElement.appendChild(reviewerHistoryElement);
				
				artifactsElement.appendChild(artifactElement);
			}
			artifactReviewSummaryElement.appendChild(artifactsElement);
		}
		return artifactReviewSummaryElement;
	}
}
