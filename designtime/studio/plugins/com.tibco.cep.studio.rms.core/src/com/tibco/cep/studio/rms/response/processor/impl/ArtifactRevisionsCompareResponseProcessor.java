/**
 * 
 */
package com.tibco.cep.studio.rms.response.processor.impl;

import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_DIFFED_ARTIFACTS;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_MASTER_ARTIFACT_CONTENT;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_REMOTE_ARTIFACT_CONTENT;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.xml.sax.InputSource;

import com.tibco.cep.studio.rms.core.utils.OperationType;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.model.ArtifactDiffContent;
import com.tibco.cep.studio.rms.response.IResponse;
import com.tibco.cep.studio.rms.response.ResponseProcessingException;
import com.tibco.cep.studio.rms.response.impl.ArtifactsRevisionCompareResponse;
import com.tibco.cep.studio.rms.response.processor.IResponseProcessor;
import com.tibco.net.mime.Base64Codec;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * @author aathalye
 *
 */
public class ArtifactRevisionsCompareResponseProcessor<O extends Object, R extends IResponse> extends
		AbstractArtifactsResponseProcessor implements IResponseProcessor<O, R> {
	
	private IResponseProcessor<O, R> processor;

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

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.processor.IResponseProcessor#processResponse(com.tibco.cep.mgmtserver.rms.utils.OperationType, org.apache.commons.httpclient.HttpMethodBase, java.io.InputStream)
	 */
	public IResponse processResponse(OperationType responseType,
									 HttpResponse httpResponse,
			                         InputStream response)
			throws ResponseProcessingException {
		if (responseType == OperationType.COMPARE_REVISIONS) {
			try {
				ArtifactsRevisionCompareResponse revisionCompareResponse = new ArtifactsRevisionCompareResponse();
				XiNode rootNode = null;
				try {
					rootNode = RMSUtil.getXiNodeFromSource(new InputSource(response));
					XiNode diffedArtifactsNode = 
						XiChild.getChild(rootNode, EX_DIFFED_ARTIFACTS);
					//Get any remote artifact contents
					List<ArtifactDiffContent> diffContents = new ArrayList<ArtifactDiffContent>(2);
					@SuppressWarnings("unchecked")
					Iterator<XiNode> diffContentChildren = diffedArtifactsNode.getChildren();
					while (diffContentChildren.hasNext()) {
						//Look for remote elements
						XiNode childNode = diffContentChildren.next();
						
						if (EX_REMOTE_ARTIFACT_CONTENT.equals(childNode.getName())) {
							byte[] decodedBytes = Base64Codec.decodeBase64(childNode.getStringValue());
							//Get its contents
							byte[] inflatedContents = getInflatedContents(decodedBytes);
							//Get revision id
							String revisionIdStr = childNode.getAttributeStringValue(ExpandedName.makeName("revisionId"));
							ArtifactDiffContent diffContent = 
								new ArtifactDiffContent(Long.parseLong(revisionIdStr), inflatedContents, false);
							diffContents.add(diffContent);
						}
						if (EX_MASTER_ARTIFACT_CONTENT.equals(childNode.getName())) {
							byte[] decodedBytes = Base64Codec.decodeBase64(childNode.getStringValue());
							//Get its contents
							byte[] inflatedContents = getInflatedContents(decodedBytes);
							ArtifactDiffContent diffContent = 
								new ArtifactDiffContent(0, inflatedContents, true);
							diffContents.add(diffContent);
						}
					}
					revisionCompareResponse.holdResponseObject(diffContents);
				} catch (Exception e) {
					throw new ResponseProcessingException(e);
				}
				return revisionCompareResponse;
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
