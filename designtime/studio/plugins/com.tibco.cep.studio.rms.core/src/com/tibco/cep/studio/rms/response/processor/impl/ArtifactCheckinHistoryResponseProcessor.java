/**
 * 
 */
package com.tibco.cep.studio.rms.response.processor.impl;

import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_ARTIFACT_COMMIT;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_HISTORY_ENTRY;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.SDF;

import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.xml.sax.InputSource;

import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactOperation;
import com.tibco.cep.studio.rms.artifacts.ArtifactsFactory;
import com.tibco.cep.studio.rms.artifacts.ArtifactsType;
import com.tibco.cep.studio.rms.core.RMSCorePlugin;
import com.tibco.cep.studio.rms.core.utils.OperationType;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.model.ArtifactCheckinHistoryEntry;
import com.tibco.cep.studio.rms.model.CommittedArtifactDetails;
import com.tibco.cep.studio.rms.response.IResponse;
import com.tibco.cep.studio.rms.response.ResponseProcessingException;
import com.tibco.cep.studio.rms.response.impl.ArtifactCheckinHistoryResponse;
import com.tibco.cep.studio.rms.response.processor.IResponseProcessor;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * @author aathalye
 *
 */
public class ArtifactCheckinHistoryResponseProcessor<O extends Object, R extends IResponse> extends AbstractArtifactsResponseProcessor
		implements IResponseProcessor<O, R> {
	
	private IResponseProcessor<O, R> processor;
	
	private static final String CLASS = ArtifactCheckinHistoryResponseProcessor.class.getName();
	
	private static final ExpandedName EX_USER_CHECKIN = ExpandedName.makeName("www.tibco.com/be/ontology/WebStudio/Core/Concepts/Lifecycle/WS_C_UserCheckin", "WS_C_UserCheckin");
	
		
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.processor.IResponseProcessor#addProcessor(com.tibco.cep.studio.rms.response.processor.IResponseProcessor)
	 */
	@Override
	public <T extends IResponseProcessor<O, R>> void addProcessor(T processor) {
		this.processor = processor;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.processor.IResponseProcessor#nextProcessor()
	 */
	@Override
	public IResponseProcessor<O, R> nextProcessor() {
		return processor;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.processor.IResponseProcessor#processResponse(com.tibco.cep.mgmtserver.rms.utils.OperationType, org.apache.commons.httpclient.HttpMethodBase, java.io.InputStream)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IResponse processResponse(OperationType responseType,
			 						 HttpResponse httpResponse,
			                         InputStream response) throws ResponseProcessingException {
		if (OperationType.CHECKIN_HISTORY == responseType) {
			try {
				ArtifactCheckinHistoryResponse checkinHistoryResponse = new ArtifactCheckinHistoryResponse();
				XiNode rootNode = null;
				try {
					rootNode = 
						RMSUtil.getXiNodeFromSource(new InputSource(response)).getFirstChild();
					//Get all <HistoryEntry> nodes
					Iterator<XiNode> historyEntryNodes = XiChild.getIterator(rootNode, EX_HISTORY_ENTRY);
					List<ArtifactCheckinHistoryEntry> historyEntries = new ArrayList<ArtifactCheckinHistoryEntry>(0);
					if (historyEntryNodes != null) {
						while (historyEntryNodes.hasNext()) {
							//Get artifactPaths
							XiNode historyEntryNode = historyEntryNodes.next();
							//Extract information 
							ArtifactCheckinHistoryEntry checkinHistoryEntry = getHistoryEntry(historyEntryNode);
							historyEntries.add(checkinHistoryEntry);
						}
					}
					checkinHistoryResponse.holdResponseObject(historyEntries);
					return checkinHistoryResponse;
				} catch (Exception e) {
					throw new ResponseProcessingException(e);
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
	
	/**
	 * 
	 * @param historyEntryNode
	 * @return
	 */
	private ArtifactCheckinHistoryEntry getHistoryEntry(XiNode historyEntryNode) throws ParseException {
		//Get hold of the checkin node
		XiNode checkinNode = XiChild.getChild(historyEntryNode, EX_USER_CHECKIN);
		//Get artifact paths
		CommittedArtifactDetails[] artifactsCommitted = getArtifactsCommitted(historyEntryNode);
		//Get author
		String author = getAuthor(checkinNode);
		//Get rev Id
		long revisionId = Long.parseLong(getRevisionId(checkinNode));
		//Get checkin comments
		String checkinComments = getCheckinComments(checkinNode);
		//Get checkin time
		Date checkinTime = getCheckinTime(checkinNode);
		
		return new ArtifactCheckinHistoryEntry(artifactsCommitted, 
				                               author, 
				                               revisionId, 
				                               checkinComments, 
				                               checkinTime);
	}
	
	/**
	 * 
	 * @param historyEntryNode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private CommittedArtifactDetails[] getArtifactsCommitted(XiNode historyEntryNode) {
		//Get all committed artifact Nodes
		Iterator<XiNode> artifactCommittedNodes = XiChild.getIterator(historyEntryNode, EX_ARTIFACT_COMMIT);
		List<CommittedArtifactDetails> artifactsCommitted = new ArrayList<CommittedArtifactDetails>();
		if (artifactCommittedNodes != null) {
			while (artifactCommittedNodes.hasNext()) {
				XiNode artifactCommittedNode = artifactCommittedNodes.next();
				String artifactPath = getArtifactPath(artifactCommittedNode);
				String artifactFileExtension = getArtifactExtension(artifactCommittedNode);
				String artifactOperation = getArtifactOperation(artifactCommittedNode);
				String artifactApprovalStatus = getArtifactStatus(artifactCommittedNode);
				
				CommittedArtifactDetails committedArtifactDetails = new CommittedArtifactDetails();
				Artifact artifact = ArtifactsFactory.eINSTANCE.createArtifact();
				artifact.setArtifactPath(artifactPath);
				artifact.setArtifactType(ArtifactsType.get(artifactFileExtension));
				committedArtifactDetails.setArtifact(artifact);
				//Set operation
				committedArtifactDetails.setArtifactOperation(ArtifactOperation.get(artifactOperation));
				committedArtifactDetails.setStatus(artifactApprovalStatus);
				artifactsCommitted.add(committedArtifactDetails);
			}
		}
		return artifactsCommitted.toArray(new CommittedArtifactDetails[artifactsCommitted.size()]);
	}
	
	/**
	 * 
	 * @param historyEntryNode
	 * @return
	 */
	private String getAuthor(XiNode checkinNode) {
		XiNode authorNode = 
			XiChild.getChild(checkinNode, ExpandedName.makeName("username"));
		
		if (authorNode != null) {
			return authorNode.getStringValue();
		}
		return null;
	}
	
	/**
	 * 
	 * @param historyEntryNode
	 * @return
	 */
	private String getCheckinComments(XiNode checkinNode) {
		XiNode checkinCommentsNode = 
			XiChild.getChild(checkinNode, ExpandedName.makeName("checkinComments"));
		
		if (checkinCommentsNode != null) {
			return checkinCommentsNode.getStringValue();
		}
		return null;
	}
	
		
	/**
	 * 
	 * @param historyEntryNode
	 * @return
	 */
	private Date getCheckinTime(XiNode checkinNode) throws ParseException {
		XiNode checkinTimeNode = 
			XiChild.getChild(checkinNode, ExpandedName.makeName("checkinTime"));
		
		if (checkinTimeNode != null) {
			String checkinTimeString = checkinTimeNode.getStringValue();
			RMSCorePlugin.debug(CLASS, "Checkin Time %s", checkinTimeString);
			return SDF.parse(checkinTimeString);
		}
		return new Date();
	}
}
