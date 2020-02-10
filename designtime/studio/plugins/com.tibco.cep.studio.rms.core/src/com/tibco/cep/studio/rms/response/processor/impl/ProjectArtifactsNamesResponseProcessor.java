/**
 * 
 */
package com.tibco.cep.studio.rms.response.processor.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.xml.sax.InputSource;

import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactsFactory;
import com.tibco.cep.studio.rms.artifacts.ArtifactsType;
import com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants;
import com.tibco.cep.studio.rms.core.utils.OperationType;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.model.Error;
import com.tibco.cep.studio.rms.response.IResponse;
import com.tibco.cep.studio.rms.response.ResponseProcessingException;
import com.tibco.cep.studio.rms.response.impl.ErrorResponse;
import com.tibco.cep.studio.rms.response.impl.ProjectArtifactsNamesResponse;
import com.tibco.cep.studio.rms.response.processor.IResponseProcessor;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * @author aathalye
 *
 */
public class ProjectArtifactsNamesResponseProcessor<O extends Object, R extends IResponse> extends AbstractArtifactsResponseProcessor implements IResponseProcessor<O, R> {
	
	private IResponseProcessor<O, R> processor;
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.mgmtserver.rms.response.processor.IResponseProcessor#addProcessor(com.tibco.cep.mgmtserver.rms.response.processor.IResponseProcessor)
	 */
	public <T extends IResponseProcessor<O, R>> void addProcessor(T processor) {
		this.processor = processor;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.mgmtserver.rms.response.processor.IResponseProcessor#nextProcessor()
	 */
	public IResponseProcessor<O, R> nextProcessor() {
		return processor;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.processor.IResponseProcessor#processResponse(com.tibco.cep.mgmtserver.rms.utils.OperationType, java.io.InputStream)
	 */
	@SuppressWarnings("unchecked")
	public IResponse processResponse(OperationType responseType,
									 HttpResponse httpResponse,
			                         InputStream response) throws ResponseProcessingException {
		if (responseType == OperationType.PROJECT_ARTIFACTS_LIST) {
			XiNode rootNode = null;
			ProjectArtifactsNamesResponse artifactNamesResponse = new ProjectArtifactsNamesResponse();
			try {
				rootNode = RMSUtil.getXiNodeFromSource(new InputSource(response));
				XiNode childNode = rootNode.getFirstChild();
				//Check if this is error
				boolean isError = isErrorNode(childNode);
				if (isError) {
					Error error = getError(childNode);
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.holdResponseObject(error);
					return errorResponse;
				}
				XiNode artifactsCollRootNode = childNode;
				Iterator<XiNode> elementChildren = 
					XiChild.getIterator(artifactsCollRootNode, ArtifactsManagerConstants.EX_APPROVED_ARTIFACT_RECORD);
				List<Artifact> artifacts = new ArrayList<Artifact>();
				while (elementChildren.hasNext()) {
					XiNode elementChild = elementChildren.next();
					String artifactPath = getArtifactPath(elementChild);
					String artifactType = getArtifactType(elementChild);
					//Create Artifact 
					Artifact artifact = ArtifactsFactory.eINSTANCE.createArtifact();
					artifact.setArtifactPath(artifactPath);
					artifact.setArtifactExtension(artifactType);
					ArtifactsType artifactTypeEnum = ArtifactsType.getByName(artifactType.toUpperCase());
					if (artifactTypeEnum == null) {
						//Resolve by extension
						artifactTypeEnum = ArtifactsType.get(artifactType);
					}
					artifact.setArtifactType(artifactTypeEnum);
					artifacts.add(artifact);
				}
				artifactNamesResponse.holdResponseObject(artifacts);
			} catch (Exception e) {
				throw new ResponseProcessingException(e);
			}
			return artifactNamesResponse;
		} 
		return processor.processResponse(responseType, httpResponse, response);
	}
}
