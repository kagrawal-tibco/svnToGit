/**
 * 
 */
package com.tibco.cep.studio.rms.response.processor.impl;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.xml.sax.InputSource;

import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactOperation;
import com.tibco.cep.studio.rms.artifacts.ArtifactsFactory;
import com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants;
import com.tibco.cep.studio.rms.core.utils.OperationType;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.model.ArtifactWorkflowInfo;
import com.tibco.cep.studio.rms.model.CommittedArtifactDetails;
import com.tibco.cep.studio.rms.response.IResponse;
import com.tibco.cep.studio.rms.response.ResponseProcessingException;
import com.tibco.cep.studio.rms.response.impl.CommittedArtifactDetailsResponse;
import com.tibco.cep.studio.rms.response.processor.IResponseProcessor;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * @author aathalye
 *
 */
public class CommittedArtifactDetailsResponseProcessor<O extends Object, R extends IResponse> extends
		ArtifactReviewTaskDetailsResponseProcessor<O, R> implements IResponseProcessor<O, R> {
	
	private IResponseProcessor<O, R> processor;
	
	private static final String COMMITTED_ARTIFACT_DETAIL_NS = "www.tibco.com/be/ontology/rms/Approval/Concepts/ArtifactsConcepts/AMS_C_ArtifactCommited";
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.processor.IResponseProcessor#addProcessor(com.tibco.cep.studio.rms.response.processor.IResponseProcessor)
	 */
	
	public <T extends IResponseProcessor<O, R>> void addProcessor(T processor) {
		this.processor = processor;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.processor.IResponseProcessor#nextProcessor()
	 */
	
	public IResponseProcessor<O, R> nextProcessor() {
		return processor;
	}
	
	
	@Override
	protected String getArtifactOperation(XiNode rootArtifactNode) {
		XiNode artifactTypeNode = 
			XiChild.getChild(rootArtifactNode, ExpandedName.makeName(COMMITTED_ARTIFACT_DETAIL_NS, ArtifactsManagerConstants.ELEM_COMMITTED_ARTIFACT_OPERATION));
		if (artifactTypeNode != null) {
			return artifactTypeNode.getStringValue();
		}
		return null;
	}

	@Override
	protected String getArtifactPath(XiNode rootArtifactNode) {
		XiNode artifactPathNode = 
			XiChild.getChild(rootArtifactNode, ExpandedName.makeName(COMMITTED_ARTIFACT_DETAIL_NS, ArtifactsManagerConstants.ELEM_ARTIFACT_PATH));
		if (artifactPathNode != null) {
			return artifactPathNode.getStringValue();
		}
		return null;
	}
	
	@Override
	protected String getArtifactStatus(XiNode rootArtifactNode) {
		XiNode artifactStatusNode = 
			XiChild.getChild(rootArtifactNode, ExpandedName.makeName(COMMITTED_ARTIFACT_DETAIL_NS, ArtifactsManagerConstants.ELEM_ARTIFACT_STATUS));
		if (artifactStatusNode != null) {
			return artifactStatusNode.getStringValue();
		}
		return null;
	}

	@Override
	protected String getArtifactExtension(XiNode rootArtifactNode) {
		XiNode artifactExtnNode = 
			XiChild.getChild(rootArtifactNode, ExpandedName.makeName(COMMITTED_ARTIFACT_DETAIL_NS, ArtifactsManagerConstants.ELEM_ARTIFACT_EXTENSION));
		if (artifactExtnNode != null) {
			return artifactExtnNode.getStringValue();
		}
		return null;
	}

	@Override
	protected String getArtifactType(XiNode rootArtifactNode) {
		XiNode artifactTypeNode = 
			XiChild.getChild(rootArtifactNode, ExpandedName.makeName(COMMITTED_ARTIFACT_DETAIL_NS, ArtifactsManagerConstants.ELEM_ARTIFACT_TYPE));
		if (artifactTypeNode != null) {
			return artifactTypeNode.getStringValue();
		}
		return null;
	}

	@Override
	public IResponse processResponse(OperationType responseType,
									 HttpResponse httpResponse, 
			                         InputStream response)
			throws ResponseProcessingException {
		if (responseType == OperationType.COMMITTED_ARTIFACT_DETAILS) {
			try {
				XiNode rootNode = RMSUtil.getXiNodeFromSource(new InputSource(response));
				
				if (rootNode != null) {
					//Get first child
					XiNode childNode = rootNode.getFirstChild();
					CommittedArtifactDetailsResponse artifactDetailsResponse = new CommittedArtifactDetailsResponse();
					
					XiNode conceptChild = 
						XiChild.getChild(childNode, 
								ExpandedName.makeName("www.tibco.com/be/rms/CommittedArtifactDetails", "ArtifactCommitted"));
					
					String artifactPath = getArtifactPath(conceptChild);
					String artifactExtension = getArtifactExtension(conceptChild);
					String status = getArtifactStatus(conceptChild);
					ArtifactOperation artifactOperation = 
						ArtifactOperation.get(getArtifactOperation(conceptChild));
					Artifact artifact = ArtifactsFactory.eINSTANCE.createArtifact();
					artifact.setArtifactPath(artifactPath);
					artifact.setArtifactExtension(artifactExtension);
					
					//Get stages info
					XiNode applicableStagesNode = 
						XiChild.getChild(childNode, 
								ExpandedName.makeName("www.tibco.com/be/rms/CommittedArtifactDetails", "AMS_C_ApplicableStages"));
					ArtifactWorkflowInfo workflowInfo = getWorkflowInfo(applicableStagesNode);
					
					CommittedArtifactDetails committedArtifactDetails = new CommittedArtifactDetails();
					committedArtifactDetails.setArtifactOperation(artifactOperation);
					committedArtifactDetails.setArtifact(artifact);
					committedArtifactDetails.setStatus(status);
					committedArtifactDetails.setWorkflowInfo(workflowInfo);
					
					artifactDetailsResponse.holdResponseObject(committedArtifactDetails);
					return artifactDetailsResponse;
				}
			} catch (Exception e) {
				throw new ResponseProcessingException(e);
			}
		}
		if (processor != null) {
			return processor.processResponse(responseType, httpResponse, response);
		}
		return null;
	}
}
