package com.tibco.cep.webstudio.client.datasources;

import com.smartgwt.client.data.fields.DataSourceTextField;
import com.tibco.cep.webstudio.client.request.model.IRequest;
import com.tibco.cep.webstudio.client.request.model.IRequestData;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.IRequestProject;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * DataSource to get the List of projects for Manage Locks operation
 * @author vdhumal
 *
 */
public class RMSLockMgmtProjectListDS 		
		extends
		AbstractRestDS<IRequestDataItem, IRequestProject<IRequestDataItem>, IRequestData<IRequestDataItem, IRequestProject<IRequestDataItem>>, IRequest<IRequestDataItem, IRequestProject<IRequestDataItem>, IRequestData<IRequestDataItem, IRequestProject<IRequestDataItem>>>> {

	private static RMSLockMgmtProjectListDS instance;

	/**
	 * Create a Singleton
	 * 
	 * @return
	 */
	public static RMSLockMgmtProjectListDS getInstance() {
		if (instance == null) {
			instance = new RMSLockMgmtProjectListDS("lockMgmtProjectList_DS");
		}
		return instance;
	}

	private RMSLockMgmtProjectListDS(String id) {
		super(id);
		DataSourceTextField pkProjectIdField = new DataSourceTextField("no");
		pkProjectIdField.setHidden(true);
		pkProjectIdField.setPrimaryKey(true);
		pkProjectIdField.setCanEdit(false);

		DataSourceTextField managedProjectsField = new DataSourceTextField("projectName");
		this.setFields(pkProjectIdField, managedProjectsField);
	}

	@Override
	public ServerEndpoints getServerURL() {
		return ServerEndpoints.RMS_GET_PROJECTS_FOR_LOCK_MGMT;
	}

}
