/**
 * 
 */
package com.tibco.cep.webstudio.client.request.model;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import com.tibco.cep.webstudio.client.logging.WebStudioClientLogger;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.model.UserData;
import com.tibco.cep.webstudio.client.request.model.impl.ArtifactLockRequestData;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.AclDataItem;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.ApplicationPreferenceDataItem;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.ArtifactDataItem;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.ArtifactLockDataItem;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.GroupDataItem;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.NotificationPreferenceDataItem;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.UserPermissionsDataItem;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.UserPreferenceDataItem;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.UsersDataItem;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.WorklistDataItem;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.WorklistDelegationDataItem;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.WorklistDeleteDataItem;
import com.tibco.cep.webstudio.client.request.model.impl.requestdata.CommitRequestData;
import com.tibco.cep.webstudio.client.request.model.impl.requestdata.UserRequestData;
import com.tibco.cep.webstudio.client.request.model.impl.requestdata.WorklistData;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * Take params from UI and based on operation convert to stringified request
 * 
 * @author aathalye
 */
public class RequestModelBuilder {
	private static WebStudioClientLogger logger = WebStudioClientLogger.getLogger(RequestModelBuilder.class.getName());

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <D extends IRequestDataItem, P extends IRequestProject<D>, Q extends IRequestData<D, P>, R extends IRequest<D, P, Q>> String buildXMLRequest(	ServerEndpoints serverAPI,
																																								Map<String, Object> requestParameters) {
		// Build model
		R baseRequest = RequestModelFactory.INSTANCE.<D, P, Q, R> createBaseRequest();
		Q baseRequestData = RequestModelFactory.INSTANCE.<D, P, Q> createBaseRequestData(serverAPI);
		P requestProject = RequestModelFactory.INSTANCE.<D, P> createBaseRequestProject(serverAPI);

		baseRequest.addRequestData(baseRequestData);
		baseRequestData.addRequestProject(requestProject);

		Set<Entry<String, Object>> entrySet = requestParameters.entrySet();

		switch (serverAPI) {
		case RMS_POST_PROJECT_CHECKOUT:
		case RMS_DEPLOY_ARTIFACT:
		case RMS_POST_PROJECT_COMMITTED_ARTIFACTS:
		case RMS_POST_FETCH_ARTIFACTS_TO_COMMIT:
		case RMS_POST_FETCH_ARTIFACTS_TO_UPDATE:
		case RMS_POST_SYNCRONIZE_PROJECT_ARTIFACTS:
		case RMS_DELETE_ARTIFACT:
		case RMS_POST_FETCH_ARTIFACTS_TO_DEPLOY:
		case RMS_POST_PROJECT_REVERT_ARTIFACTS:
		case RMS_POST_PROJECT_REPOSITORY_SYNC:

			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				if (RequestParameter.REQUEST_PROJECT_NAME == key.intern()) {
					String projectName = (String) entry.getValue();
					requestProject.setName(projectName);
				} else if (RequestParameter.REQUEST_OPERATION_NAME == key.intern()) {
					String operationName = (String) entry.getValue();
					requestProject.setOperationName(operationName);
				} else if (RequestParameter.REQUEST_PARAM_COMMIT_COMMENTS == key.intern()) {
					if (requestProject instanceof CommitRequestData<?>) {
						String commitComments = (String) entry.getValue();
						((CommitRequestData) requestProject).setCommitComments(commitComments);
					}
				} else if (RequestParameter.REQUEST_PARAM_ARTIFACTLIST == key.intern()) {
					Object value = entry.getValue();
					List<RequestParameter> artifactListParams = (List<RequestParameter>) value;

					for (RequestParameter artifactListParam : artifactListParams) {
						ArtifactDataItem artifactDataItem = (ArtifactDataItem) RequestModelFactory.INSTANCE.createDataItem(serverAPI);
						Set<Entry<String, Object>> parameters = artifactListParam.getParameters().entrySet();
						for (Entry<String, Object> innerKey : parameters) {
							if (RequestParameter.REQUEST_PARAM_PATH == innerKey.getKey().intern()) {
								String artifactPath = (String) innerKey.getValue();
								artifactDataItem.setArtifactPath(artifactPath);
							}
							if (RequestParameter.REQUEST_PARAM_TYPE == innerKey.getKey().intern()) {
								String artifactType = (String) innerKey.getValue();
								artifactDataItem.setArtifactType(artifactType);
							}
							if (RequestParameter.REQUEST_PARAM_FILE_EXTN == innerKey.getKey().intern()) {
								String fileExtension = (String) innerKey.getValue();
								artifactDataItem.setFileExtension(fileExtension);
							}
							if (RequestParameter.REQUEST_PARAM_CHANGE_TYPE == innerKey.getKey().intern()) {
								String changeType = (String) innerKey.getValue();
								artifactDataItem.setChangeType(changeType);
							}
							if (RequestParameter.REQUEST_PARAM_BASE_ARTIFACT_PATH == innerKey.getKey().intern()) {
								String baseArtifactPath = (String) innerKey.getValue();
								artifactDataItem.setBaseArtifactPath(baseArtifactPath);
							}
						}
						requestProject.addRequestDataItem((D) artifactDataItem);
					}
				}
			}
			break;

		case RMS_POST_ARTIFACT_SAVE:

			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				if (RequestParameter.REQUEST_PROJECT_NAME == key.intern()) {
					String projectName = (String) entry.getValue();
					requestProject.setName(projectName);
				} else if (RequestParameter.REQUEST_ARTIFACT_DATA_ITEM == key.intern()) {
					Object value = entry.getValue();
					if (value instanceof IRequestDataItem) {
						requestProject.addRequestDataItem((D) value);
					}
				}
			}
			break;

		case RMS_ADD_GROUP:

			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				if (RequestParameter.REQUEST_GROUP_NAME == key.intern()) {
					String groupName = (String) entry.getValue();
					requestProject.setName(groupName);
				} else if (RequestParameter.REQUEST_ARTIFACT_DATA_ITEM == key.intern()) {
					GroupDataItem value = (GroupDataItem) entry.getValue();
					requestProject.addRequestDataItem((D) value);
				}
			}
			break;

		case RMS_UPDATE_USER_PREFERENCES:

			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				if (RequestParameter.REQUEST_ARTIFACT_DATA_ITEM == key.intern()) {
					UserPreferenceDataItem value = (UserPreferenceDataItem) entry.getValue();
					requestProject.addRequestDataItem((D) value);
				}
			}
			break;
			
		case RMS_UPDATE_APPLICATION_PREFERENCES:

			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				if (RequestParameter.REQUEST_ARTIFACT_DATA_ITEM == key.intern()) {
					ApplicationPreferenceDataItem value = (ApplicationPreferenceDataItem) entry.getValue();
					requestProject.addRequestDataItem((D) value);
				}
			}
			break;
			
		case RMS_UPDATE_NOTIFICATION_PREFERENCES:
			
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				if (RequestParameter.REQUEST_ARTIFACT_DATA_ITEM == key.intern()) {
					NotificationPreferenceDataItem value = (NotificationPreferenceDataItem) entry.getValue();
					requestProject.addRequestDataItem((D) value);
				}
			}
			
			break;
			
//		case RMS_UPDATE_USER_PERMISSIONS:
//
//			for (Entry<String, Object> entry : entrySet) {
//				String key = entry.getKey();
//				if (RequestParameter.REQUEST_ARTIFACT_DATA_ITEM == key.intern()) {
//					UserPermissionsDataItem value = (UserPermissionsDataItem) entry.getValue();
//					requestProject.addRequestDataItem((D) value);
//				}
//			}
//			break;
			
		case RMS_UPDATE_USER_DATA:

			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				if (RequestParameter.REQUEST_ARTIFACT_DATA_ITEM == key.intern()) {
					UsersDataItem value = (UsersDataItem) entry.getValue();
					requestProject.addRequestDataItem((D) value);
				}
			}
			break;
			
		case RMS_UPDATE_ACL_DATA:

			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				if (RequestParameter.REQUEST_ARTIFACT_DATA_ITEM == key.intern()) {
					AclDataItem value = (AclDataItem) entry.getValue();
					requestProject.addRequestDataItem((D) value);
				}
			}
			break;

		case RMS_POST_WORKLIST_ITEMS:
			P worklistData = requestProject;

			int cnt = 0;
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();

				if (cnt > 0) {
					worklistData = RequestModelFactory.INSTANCE.<D, P> createBaseRequestProject(serverAPI);
					baseRequestData.addRequestProject((P) worklistData);
				}

				WorklistData<?> wklistData = (WorklistData<?>) worklistData;
				wklistData.setRevisionId(key);

				Object value = entry.getValue();
				List<RequestParameter> artifactListParams = (List<RequestParameter>)value;

				for (RequestParameter artifactListParam : artifactListParams) {
					WorklistDataItem worklistDataItem = (WorklistDataItem) RequestModelFactory.INSTANCE.createDataItem(serverAPI);
					Set<Entry<String, Object>> parameters = artifactListParam.getParameters().entrySet();
					for (Entry<String, Object> innerKey : parameters) {
						if (RequestParameter.REQUEST_PARAM_PATH == innerKey.getKey().intern()) {
							String artifactPath = (String)innerKey.getValue();
							worklistDataItem.setArtifactPath(artifactPath);
						} else if (RequestParameter.REQUEST_PARAM_TYPE == innerKey.getKey().intern()) {
							String artifactType = (String)innerKey.getValue();
							worklistDataItem.setArtifactType(artifactType);
						} else if (RequestParameter.REQUEST_PARAM_APPROVAL_STATUS == innerKey.getKey().intern()) {
							String approvalStatus = (String)innerKey.getValue();
							worklistDataItem.setReviewStatus(approvalStatus);
						} else if (RequestParameter.REQUEST_PARAM_APPROVAL_COMMENTS == innerKey.getKey().intern()) {
							String approvalComments = (String)((innerKey.getValue() != null) ? innerKey.getValue() : "");
							worklistDataItem.setReviewComments(approvalComments);
						} else if (RequestParameter.REQUEST_PROJECT_NAME == innerKey.getKey().intern()) {
							String projectName = (String)innerKey.getValue();
							worklistDataItem.setProjectName(projectName);
						} else if (RequestParameter.REQUEST_PARAM_APPROVAL_ENVIRONMENTS == innerKey.getKey().intern()) {
							String deployEnvironments = (String)((innerKey.getValue() != null) ? innerKey.getValue() : "");
							worklistDataItem.setDeployEnvironments(deployEnvironments);
						}
					}
					worklistData.addRequestDataItem((D) worklistDataItem);
				}
				cnt++;
			}
			break;
			
		case RMS_PUT_DELEGATE_WORKLIST_ITEM:
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				if (RequestParameter.REQUEST_ARTIFACT_DATA_ITEM == key.intern()) {
					WorklistDelegationDataItem value = (WorklistDelegationDataItem) entry.getValue();
					requestProject.addRequestDataItem((D) value);
				}
			}
			break;
			
		case RMS_DELETE_WORKLIST_ITEM:
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				if (RequestParameter.REQUEST_ARTIFACT_DATA_ITEM.equals(key.intern())) {
					WorklistDeleteDataItem value = (WorklistDeleteDataItem) entry.getValue();
					requestProject.addRequestDataItem((D) value);
				}
			}
			break;
			
		case RMS_LOCK_ARTIFACT:
		case RMS_UNLOCK_ARTIFACT:
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				if (RequestParameter.REQUEST_PROJECT_NAME == key.intern()) {
					String projectName = (String) entry.getValue();
					requestProject.setName(projectName);
				} else if ("actionForcibly" == key.intern()) {
					Boolean actionForcibly = (Boolean) entry.getValue();
					if (requestProject instanceof ArtifactLockRequestData<?>) {
						((ArtifactLockRequestData) requestProject).setActionForcibly(actionForcibly);
					}	
				}
				else if (RequestParameter.REQUEST_PARAM_ARTIFACTLIST == key.intern()) {
					Object value = entry.getValue();
					List<RequestParameter> artifactListParams = (List<RequestParameter>) value;

					for (RequestParameter artifactListParam : artifactListParams) {
						ArtifactLockDataItem artifactLockDataItem = (ArtifactLockDataItem) RequestModelFactory.INSTANCE.createDataItem(serverAPI);
						Set<Entry<String, Object>> parameters = artifactListParam.getParameters().entrySet();
						for (Entry<String, Object> innerKey : parameters) {
							if (RequestParameter.REQUEST_PARAM_PATH == innerKey.getKey().intern()) {
								String artifactPath = (String) innerKey.getValue();
								artifactLockDataItem.setArtifactPath(artifactPath);
							}
							if (RequestParameter.REQUEST_PARAM_TYPE == innerKey.getKey().intern()) {
								String artifactType = (String) innerKey.getValue();
								artifactLockDataItem.setArtifactType(artifactType);
							}
							if (RequestParameter.REQUEST_PARAM_FILE_EXTN == innerKey.getKey().intern()) {
								String fileExtension = (String) innerKey.getValue();
								artifactLockDataItem.setFileExtension(fileExtension);
							}
							if ("lockRequestor" == innerKey.getKey().intern()) {
								String lockRequestor = (String) innerKey.getValue();
								artifactLockDataItem.setLockRequestor(lockRequestor);
							}
						}
						requestProject.addRequestDataItem((D) artifactLockDataItem);
					}
				}
			}	
			break;
		}
		//serialize
		return serialize(baseRequest);
	}
	
	private static <D extends IRequestDataItem, P extends IRequestProject<D>, Q extends IRequestData<D, P>, R extends IRequest<D, P, Q>> String serialize(R baseRequest) {
		Document rootDocument = XMLParser.createDocument();
		baseRequest.serialize(rootDocument, rootDocument);
		
		String doc = rootDocument.toString();
		logger.debug("Serialized Request - " + doc);
		
		return doc;
	}
}
