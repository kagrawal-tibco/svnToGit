package com.tibco.cep.webstudio.client.request.model;

import com.tibco.cep.webstudio.client.request.model.impl.ArtifactLockRequestData;
import com.tibco.cep.webstudio.client.request.model.impl.DefaultRequestData;
import com.tibco.cep.webstudio.client.request.model.impl.DefaultRequestProject;
import com.tibco.cep.webstudio.client.request.model.impl.DefaultXMLRequest;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.ArtifactDataItem;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.ArtifactLockDataItem;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.WorklistDataItem;
import com.tibco.cep.webstudio.client.request.model.impl.requestdata.AclRequestData;
import com.tibco.cep.webstudio.client.request.model.impl.requestdata.ApplicationPreferenceData;
import com.tibco.cep.webstudio.client.request.model.impl.requestdata.CommitRequestData;
import com.tibco.cep.webstudio.client.request.model.impl.requestdata.GroupRequestData;
import com.tibco.cep.webstudio.client.request.model.impl.requestdata.NotificationPreferenceData;
import com.tibco.cep.webstudio.client.request.model.impl.requestdata.UserPermissionsData;
import com.tibco.cep.webstudio.client.request.model.impl.requestdata.UserPreferenceData;
import com.tibco.cep.webstudio.client.request.model.impl.requestdata.UserRequestData;
import com.tibco.cep.webstudio.client.request.model.impl.requestdata.WorklistData;
import com.tibco.cep.webstudio.client.request.model.impl.requestdata.WorklistDelegationData;
import com.tibco.cep.webstudio.client.request.model.impl.requestdata.WorklistDeletionData;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * Factory class for building XML post data request body.
 * 
 * @author aathalye
 * 
 */
public class RequestModelFactory {

	public static final RequestModelFactory INSTANCE = new RequestModelFactory();

	private RequestModelFactory() {
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <D extends IRequestDataItem, P extends IRequestProject<D>, Q extends IRequestData<D, P>, R extends IRequest<D, P, Q>> R createBaseRequest() {
		return (R) new DefaultXMLRequest<D, P, Q>();
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <D extends IRequestDataItem, P extends IRequestProject<D>> P createBaseRequestProject(ServerEndpoints serverOP) {
		switch (serverOP) {
		case RMS_ADD_GROUP:
			return (P) new GroupRequestData<D>();
		case RMS_UPDATE_USER_PREFERENCES:
			return (P) new UserPreferenceData<D>();
		case RMS_UPDATE_APPLICATION_PREFERENCES:
			return (P) new ApplicationPreferenceData<D>();
		case RMS_UPDATE_NOTIFICATION_PREFERENCES:
			return (P) new NotificationPreferenceData<D>();
		case RMS_UPDATE_USER_PERMISSIONS:
			return (P) new UserPermissionsData<D>();
		case RMS_UPDATE_USER_DATA:
			return (P) new UserRequestData<D>();
		case RMS_UPDATE_ACL_DATA:
			return (P) new AclRequestData<D>();
		case RMS_POST_PROJECT_COMMITTED_ARTIFACTS:
			return (P) new CommitRequestData<D>();
		case RMS_POST_WORKLIST_ITEMS:
			return (P) new WorklistData<D>();
		case RMS_PUT_DELEGATE_WORKLIST_ITEM:
			return (P) new WorklistDelegationData<D>();
		case RMS_DELETE_WORKLIST_ITEM:
			return (P) new WorklistDeletionData<D>();
		case RMS_LOCK_ARTIFACT:
		case RMS_UNLOCK_ARTIFACT:
			return (P) new ArtifactLockRequestData<D>();
		default:
			return (P) new DefaultRequestProject<D>();
		}
	}

	@SuppressWarnings("unchecked")
	public <D extends IRequestDataItem, P extends IRequestProject<D>, R extends IRequestData<D, P>> R createBaseRequestData(ServerEndpoints serverOP) {
		switch (serverOP) {
		default:
			return (R) new DefaultRequestData<D, P>();
		}
	}

	public IRequestDataItem createDataItem(ServerEndpoints serverOP) {
		switch (serverOP) {
		case RMS_POST_WORKLIST_ITEMS:
			return new WorklistDataItem();
		case RMS_LOCK_ARTIFACT:
		case RMS_UNLOCK_ARTIFACT:
			return new ArtifactLockDataItem();
		default:
			return new ArtifactDataItem();
		}
	}
}
