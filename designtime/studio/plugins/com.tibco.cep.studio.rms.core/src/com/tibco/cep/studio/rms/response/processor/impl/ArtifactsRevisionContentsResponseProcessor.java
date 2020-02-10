/**
 * 
 */
package com.tibco.cep.studio.rms.response.processor.impl;

import java.io.InputStream;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.xml.sax.InputSource;

import com.tibco.be.functions.java.string.JavaFunctions;
import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactsFactory;
import com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants;
import com.tibco.cep.studio.rms.core.utils.OperationType;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.model.ArtifactRevisionMetadata;
import com.tibco.cep.studio.rms.response.IResponse;
import com.tibco.cep.studio.rms.response.ResponseProcessingException;
import com.tibco.cep.studio.rms.response.impl.ArtifactRevisionContentsResponse;
import com.tibco.cep.studio.rms.response.processor.IResponseProcessor;
import com.tibco.net.mime.Base64Codec;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * @author aathalye
 *
 */
public class ArtifactsRevisionContentsResponseProcessor<O extends Object, R extends IResponse> extends AbstractArtifactsResponseProcessor implements IResponseProcessor<O, R> {
	
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
		if (OperationType.REVISION_CONTENTS == responseType) {
			try {
				ArtifactRevisionContentsResponse crs = new ArtifactRevisionContentsResponse();
				XiNode rootNode = null;
				try {
					rootNode = RMSUtil.getXiNodeFromSource(new InputSource(response));
					XiNode artifactsRootNode = getRootArtifactNode(rootNode);
					String artifactPath = getArtifactPath(artifactsRootNode);
					String artifactExtn = getArtifactExtension(artifactsRootNode);
					String artifactContent = getArtifactContent(artifactsRootNode);
					String revisionId = getRevisionId(artifactsRootNode);
					boolean notCompressed = isContentCompressed(artifactsRootNode);
					
					Artifact revisionedArtifact = ArtifactsFactory.eINSTANCE.createArtifact();
					ArtifactRevisionMetadata artifactRevisionMetadata = new ArtifactRevisionMetadata();
					revisionedArtifact.setArtifactPath(artifactPath);
					revisionedArtifact.setArtifactExtension(artifactExtn);
					artifactRevisionMetadata.setRevisionId(revisionId);
					//Decode contents
					String inflatedContents = null;
					if (artifactContent != null) {
						if (!notCompressed) {
							byte[] decodedBytes = Base64Codec.decodeBase64(artifactContent);
							//This is encrypted form
							byte[] inflatedBytes = getInflatedContents(decodedBytes);
							//Convert to string
							inflatedContents = JavaFunctions.convertByteArrayToString(inflatedBytes, "UTF-8");
						} else {
							inflatedContents = StringEscapeUtils.unescapeXml(artifactContent);
						}
						revisionedArtifact.setArtifactContent(inflatedContents);
					}
					artifactRevisionMetadata.setArtifact(revisionedArtifact);
					crs.holdResponseObject(artifactRevisionMetadata);
				} catch (Exception e) {
					throw new ResponseProcessingException(e);
				}
				return crs;
			} catch (Exception e) {
				throw new ResponseProcessingException(e);
			} 
		}
		if (processor != null) {
			return processor.processResponse(responseType, httpResponse, response);
		}
		return null;
	}
	
	
	
	private XiNode getRootArtifactNode(XiNode rootNode) {
		//Get artifact root child
		XiNode artifactsRootNode = 
			XiChild.getChild(rootNode, ArtifactsManagerConstants.EX_ARTIFACT_COMMIT);
		return artifactsRootNode;
	}
	
	/**
	 * @param rootArtifactNode
	 * @return
	 */
	private boolean isContentCompressed(XiNode rootArtifactNode) {
		XiNode notCompressedNode = XiChild.getChild(rootArtifactNode, ExpandedName.makeName("notCompressed"));
		if (notCompressedNode != null) {
			return Boolean.parseBoolean(notCompressedNode.getStringValue());
		}
		return false;
	}
}
