/**
 * 
 */
package com.tibco.cep.studio.rms.response.processor.impl;

import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.ARTIFACTS_TASKS_DELEGATION_ROLES_NS;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.ARTIFACTS_TASKS_IDS_LIST_RESPONSE_NS;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.ARTIFACT_COMMIT_COMPLETE_NS;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_TASK;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.SDF;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.xml.sax.InputSource;

import com.tibco.cep.studio.rms.core.utils.OperationType;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.model.ArtifactReviewTask;
import com.tibco.cep.studio.rms.model.ArtifactReviewTaskSummary;
import com.tibco.cep.studio.rms.response.IResponse;
import com.tibco.cep.studio.rms.response.ResponseProcessingException;
import com.tibco.cep.studio.rms.response.impl.ArtifactsWorklistResponse;
import com.tibco.cep.studio.rms.response.processor.IResponseProcessor;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * @author aathalye
 *
 */
public class ArtifactsTasksListResponseProcessor<O extends Object, R extends IResponse> implements IResponseProcessor<O, R> {
	
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
	 * @see com.tibco.cep.mgmtserver.rms.response.processor.IResponseProcessor#processResponse(com.tibco.cep.mgmtserver.rms.utils.ResponseType, java.io.InputStream)
	 */
	@SuppressWarnings("unchecked")
	public IResponse processResponse(OperationType responseType,
									 HttpResponse httpResponse,
			                         InputStream response) throws ResponseProcessingException {
		if (OperationType.WORKLISTS_IDS == responseType) {
			try {
				ArtifactsWorklistResponse wlResponse = new ArtifactsWorklistResponse();
				XiNode rootNode = null;
				try {
					rootNode = RMSUtil.getXiNodeFromSource(new InputSource(response));
					XiNode taskSummaryNode = 
						XiChild.getChild(rootNode, 
								         ExpandedName.
								         makeName(ARTIFACTS_TASKS_IDS_LIST_RESPONSE_NS, "TaskSummary"));
					if (taskSummaryNode == null) {
						return null;
					}
					ArtifactReviewTaskSummary taskSummary = new ArtifactReviewTaskSummary();
					
					Iterator<XiNode> taskNodes = XiChild.getIterator(taskSummaryNode, EX_TASK);
					while (taskNodes.hasNext()) {
						XiNode taskNode = taskNodes.next();
						ArtifactReviewTask reviewTask = new ArtifactReviewTask();
						reviewTask.setTaskId(getTaskId(taskNode));
						getCheckinInfo(taskNode, reviewTask);
						taskSummary.addTask(reviewTask);
					}
					//Read delegation roles
					XiNode delegationRolesNode = 
						XiChild.getChild(taskSummaryNode, 
								         ExpandedName.
								         makeName(ARTIFACTS_TASKS_DELEGATION_ROLES_NS, "DelegationRoles"));
					String[] delegationRoles = getDelegationRoles(delegationRolesNode);
					taskSummary.setDelegationRoles(delegationRoles);
					wlResponse.holdResponseObject(taskSummary);
				} catch (Exception e) {
					throw new ResponseProcessingException(e);
				}
				return wlResponse;
			} catch (Exception e) {
				throw new ResponseProcessingException(e);
			}
		}
		if (processor != null) {
			return processor.processResponse(responseType, httpResponse, response);
		}
		return null;
	}
	
	private String getTaskId(XiNode taskNode) {
		XiNode taskIdNode = XiChild.getChild(taskNode, ExpandedName.makeName("TaskId"));
		if (taskIdNode != null) {
			return taskIdNode.getStringValue();
		}
		return null;
	}
	
	/**
	 * 
	 * @param taskNode
	 * @param reviewTask
	 * @throws Exception
	 */
	private void getCheckinInfo(XiNode taskNode, 
			                    ArtifactReviewTask reviewTask) throws Exception {
		XiNode checkinNode = XiChild.getChild(taskNode, 
				ExpandedName.makeName(ARTIFACT_COMMIT_COMPLETE_NS, "AMS_C_Checkin"));
		if (checkinNode != null) {
			XiNode patternId = XiChild.getChild(checkinNode, ExpandedName.makeName("patternId"));
			reviewTask.setPatternId(patternId.getStringValue());
			
			XiNode checkinCommentsNode = 
				XiChild.getChild(checkinNode, ExpandedName.makeName("checkinComments"));
			if (checkinCommentsNode != null) {
				reviewTask.setCheckinComments(checkinCommentsNode.getStringValue());
			}
			
			XiNode checkinTimeNode = 
				XiChild.getChild(checkinNode, ExpandedName.makeName("checkinTime"));
			reviewTask.setCheckinTime(SDF.parse(checkinTimeNode.getStringValue()));
			
			
			XiNode usernameNode = 
				XiChild.getChild(checkinNode, ExpandedName.makeName("username"));
			reviewTask.setUsername(usernameNode.getStringValue());
			
			XiNode projectNameNode = 
				XiChild.getChild(checkinNode, ExpandedName.makeName("projectName"));
			reviewTask.setProjectName(projectNameNode.getStringValue());
		}
	}
	
	/**
	 * 
	 * @param delegationRolesNode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String[] getDelegationRoles(XiNode delegationRolesNode) throws Exception {
		List<String> delegationRolesList = new ArrayList<String>();
		//Get all children
		Iterator<XiNode> childrenNodes = XiChild.getIterator(delegationRolesNode, ExpandedName.makeName(ARTIFACTS_TASKS_DELEGATION_ROLES_NS, "delegationRole"));
		while (childrenNodes.hasNext()) {
			XiNode roleNode = childrenNodes.next();
			delegationRolesList.add(roleNode.getStringValue());
		}
		return delegationRolesList.toArray(new String[delegationRolesList.size()]);
	}
}
