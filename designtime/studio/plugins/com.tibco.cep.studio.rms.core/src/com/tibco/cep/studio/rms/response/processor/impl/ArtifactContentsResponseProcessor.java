package com.tibco.cep.studio.rms.response.processor.impl;

import java.io.InputStream;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.xml.sax.InputSource;

import com.tibco.be.functions.java.string.JavaFunctions;
import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactsFactory;
import com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants;
import com.tibco.cep.studio.rms.core.utils.OperationType;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.response.IResponse;
import com.tibco.cep.studio.rms.response.ResponseProcessingException;
import com.tibco.cep.studio.rms.response.impl.ArtifactContentsResponse;
import com.tibco.cep.studio.rms.response.processor.IResponseProcessor;
import com.tibco.net.mime.Base64Codec;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

public class ArtifactContentsResponseProcessor<O extends Object, R extends IResponse> extends AbstractArtifactsResponseProcessor implements IResponseProcessor<O, R> {
	
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
	
	
	public IResponse processResponse(OperationType responseType,
			HttpResponse httpResponse,
			InputStream response) throws ResponseProcessingException {
		if (OperationType.ARTIFACT_CONTENTS == responseType) {
			try {
				ArtifactContentsResponse crs = new ArtifactContentsResponse();
				XiNode rootNode = null;
				try {
					rootNode = RMSUtil.getXiNodeFromSource(new InputSource(response));
					XiNode artifactsRootNode = getRootArtifactNode(rootNode);
					String artifactPath = getArtifactPath(artifactsRootNode);
					String artifactExtn = getArtifactExtension(artifactsRootNode);
					String artifactContent = getArtifactContent(artifactsRootNode);
					Date updateTime = getArtifactUpdateTime(artifactsRootNode);
					Artifact checkedOutArtifact = ArtifactsFactory.eINSTANCE.createArtifact();
					checkedOutArtifact.setArtifactPath(artifactPath);
					checkedOutArtifact.setArtifactExtension(artifactExtn);
					//Decode contents
					if (artifactContent != null) {
						byte[] decodedBytes = Base64Codec.decodeBase64(artifactContent);
						//byte[] inflatedBytes = getInflatedContents(decodedBytes);
						//Convert to string
						String inflatedContents = JavaFunctions.convertByteArrayToString(decodedBytes, "UTF-8");
						checkedOutArtifact.setArtifactContent(inflatedContents);
					}					
					checkedOutArtifact.setUpdateTime(updateTime);
					
					crs.holdResponseObject(checkedOutArtifact);
				} catch (Exception e) {
					throw new ResponseProcessingException(e);
				}
				return crs;
			} catch (Exception e) {
				throw new ResponseProcessingException(e);
			} 
		}
		return processor.processResponse(responseType, httpResponse, response);
	}
	
	
	
	private XiNode getRootArtifactNode(XiNode rootNode) {
		//Get artifact root child
		XiNode artifactsRootNode = 
			XiChild.getChild(rootNode, ArtifactsManagerConstants.EX_ARTIFACT_NAME);
		return artifactsRootNode;
	}
}
