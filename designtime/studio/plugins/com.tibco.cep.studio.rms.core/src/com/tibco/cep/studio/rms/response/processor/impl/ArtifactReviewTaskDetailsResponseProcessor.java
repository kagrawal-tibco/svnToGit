/**
 * 
 */
package com.tibco.cep.studio.rms.response.processor.impl;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.ARTIFACT_COMMIT_NS;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_ARTIFACT_APPLICABLE_STAGES;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_ARTIFACT_APPLICABLE_STAGES_STAGE;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_ATTR_ARTIFACT_APPLICABLE_STAGES_STAGE_NAME;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_COMMITTED_ARTIFACTS;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.xml.sax.InputSource;

import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactOperation;
import com.tibco.cep.studio.rms.artifacts.ArtifactsFactory;
import com.tibco.cep.studio.rms.core.utils.OperationType;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.model.ArtifactWorkflowInfo;
import com.tibco.cep.studio.rms.model.CommittedArtifactDetails;
import com.tibco.cep.studio.rms.response.IResponse;
import com.tibco.cep.studio.rms.response.ResponseProcessingException;
import com.tibco.cep.studio.rms.response.impl.ArtifactReviewTaskDetailsResponse;
import com.tibco.cep.studio.rms.response.processor.IResponseProcessor;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * @author aathalye
 *
 */
public class ArtifactReviewTaskDetailsResponseProcessor<O extends Object, R extends IResponse> extends AbstractArtifactsResponseProcessor implements
		IResponseProcessor<O, R> {
	
	protected IResponseProcessor<O, R> processor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.mgmtserver.rms.response.processor.IResponseProcessor#addProcessor(com.tibco.cep.mgmtserver.rms.response.processor.IResponseProcessor)
	 */
	public <T extends IResponseProcessor<O, R>> void addProcessor(T  processor) {		
		// TODO Auto-generated method stub
		this.processor = processor;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.mgmtserver.rms.response.processor.IResponseProcessor#nextProcessor()
	 */
	public IResponseProcessor<O, R> nextProcessor() {
		// TODO Auto-generated method stub
		return processor;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.processor.IResponseProcessor#processResponse(com.tibco.cep.mgmtserver.rms.utils.OperationType, org.apache.commons.httpclient.HttpMethodBase, java.io.InputStream)
	 */
	@SuppressWarnings("unchecked")
	public IResponse processResponse(OperationType responseType,
									 HttpResponse httpResponse, 
			                         InputStream response)
			throws ResponseProcessingException {
		if (OperationType.WORK_DETAILS == responseType) {
			XiNode rootNode = null;
			try {
				ArtifactReviewTaskDetailsResponse taskDetailsResponse = new ArtifactReviewTaskDetailsResponse();
				rootNode = RMSUtil.getXiNodeFromSource(new InputSource(response));
				XiNode committedArtifactsRootNode = 
					XiChild.getChild(rootNode, EX_COMMITTED_ARTIFACTS);
				
				List<CommittedArtifactDetails> allCommittedArtifacts = new ArrayList<CommittedArtifactDetails>();
				
				if (committedArtifactsRootNode != null) {
					//<CommittedArtifact>
					Iterator<XiNode> committedArtifactsChildren = 
								XiChild.getIterator(committedArtifactsRootNode);
					while (committedArtifactsChildren.hasNext()) {
						XiNode committedArtifactChild = committedArtifactsChildren.next();
						XiNode conceptChild = 
							XiChild.getChild(committedArtifactChild, 
									ExpandedName.makeName(ARTIFACT_COMMIT_NS, "AMS_C_ArtifactCommited"));
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
							XiChild.getChild(committedArtifactChild, EX_ARTIFACT_APPLICABLE_STAGES);
						ArtifactWorkflowInfo workflowInfo = getWorkflowInfo(applicableStagesNode);
						
						CommittedArtifactDetails committedArtifactDetails = new CommittedArtifactDetails();
						committedArtifactDetails.setArtifactOperation(artifactOperation);
						committedArtifactDetails.setArtifact(artifact);
						committedArtifactDetails.setStatus(status);
						committedArtifactDetails.setWorkflowInfo(workflowInfo);
						
						allCommittedArtifacts.add(committedArtifactDetails);
					}
					taskDetailsResponse.holdResponseObject(allCommittedArtifacts);
					return taskDetailsResponse;
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
	
	@SuppressWarnings("unchecked")
	protected ArtifactWorkflowInfo getWorkflowInfo(XiNode applicableStagesNode) {
		if (applicableStagesNode == null) {
			return null;
		}
		
		ArtifactWorkflowInfo workflowInfo = new ArtifactWorkflowInfo();
		//Get stages
		Iterator<XiNode> stageNodes = 
			XiChild.getIterator(applicableStagesNode, EX_ARTIFACT_APPLICABLE_STAGES_STAGE);
		
		List<String> stages = new ArrayList<String>();
		while (stageNodes.hasNext()) {
			XiNode stageNode = stageNodes.next();
			//Get attribute values
			String stageName = 
				stageNode.getAttributeStringValue(EX_ATTR_ARTIFACT_APPLICABLE_STAGES_STAGE_NAME);
			stages.add(stageName);
		}
		workflowInfo.setStages(stages);
		return workflowInfo;
	}
}
