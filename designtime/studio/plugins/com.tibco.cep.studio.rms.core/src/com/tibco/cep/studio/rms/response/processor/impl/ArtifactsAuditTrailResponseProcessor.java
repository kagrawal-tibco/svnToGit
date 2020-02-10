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

import com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants;
import com.tibco.cep.studio.rms.core.utils.OperationType;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.model.ArtifactAuditTrailRecord;
import com.tibco.cep.studio.rms.response.IResponse;
import com.tibco.cep.studio.rms.response.ResponseProcessingException;
import com.tibco.cep.studio.rms.response.impl.ArtifactAuditTrailResponse;
import com.tibco.cep.studio.rms.response.processor.IResponseProcessor;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * @author aathalye
 *
 */
public class ArtifactsAuditTrailResponseProcessor<O extends Object, R extends IResponse> implements
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
		if (OperationType.AUDIT_TRAIL == responseType) {
			XiNode rootNode = null;
			try {
				rootNode = RMSUtil.getXiNodeFromSource(new InputSource(response));
				ArtifactAuditTrailResponse auditTrailResponse = new ArtifactAuditTrailResponse();
				
				//Get all history elements
				Iterator<XiNode> historyNodes = 
					XiChild.getIterator(rootNode.getFirstChild(), ArtifactsManagerConstants.EX_REVIEWER_HISTORY);
				List<ArtifactAuditTrailRecord> auditTrailHistory = new ArrayList<ArtifactAuditTrailRecord>();
				while (historyNodes.hasNext()) {
					XiNode historyNode = historyNodes.next();
					if (historyNode != null) {
						ArtifactAuditTrailRecord auditTrailRecord = getAuditTrailRecord(historyNode);
						auditTrailHistory.add(auditTrailRecord);
					}
				}
				auditTrailResponse.holdResponseObject(auditTrailHistory);
				return auditTrailResponse;
			} catch (Exception e) {
				throw new ResponseProcessingException(e);
			}
		}
		if (processor != null) {
			return processor.processResponse(responseType, httpResponse, response);
		}
		return null;
	}
	
	private ArtifactAuditTrailRecord getAuditTrailRecord(XiNode historyNode) {
		XiNode usernameElement = 
			XiChild.getChild(historyNode, ExpandedName.makeName("username"));
		String username = usernameElement.getStringValue();
		
		XiNode reviewerCommentsElement = 
			XiChild.getChild(historyNode, ExpandedName.makeName("reviewerComments"));
		String reviewerComments = null;
		if (reviewerCommentsElement != null) {
			reviewerComments = reviewerCommentsElement.getStringValue();
		}
		
		XiNode oldStatusElement = 
			XiChild.getChild(historyNode, ExpandedName.makeName("oldStatus"));
		String oldStatus = null;
		if (oldStatusElement != null) {
			oldStatus = oldStatusElement.getStringValue();
		}
		
		XiNode newStatusElement = 
			XiChild.getChild(historyNode, ExpandedName.makeName("newStatus"));
		String newStatus = null;
		if (newStatusElement != null) {
			newStatus = newStatusElement.getStringValue();
		}
		
		XiNode reviewerRoleElement = 
			XiChild.getChild(historyNode, ExpandedName.makeName("reviewerRole"));
		String reviewerRole = null;
		if (reviewerRoleElement != null) {
			reviewerRole = reviewerRoleElement.getStringValue();
		}
		
		return new ArtifactAuditTrailRecord(username, reviewerRole, newStatus, oldStatus, reviewerComments);
	}
}
