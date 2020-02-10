/**
 * 
 */
package com.tibco.cep.webstudio.client.datasources;

import com.smartgwt.client.data.fields.DataSourceTextField;
import com.tibco.cep.webstudio.client.request.model.IRequest;
import com.tibco.cep.webstudio.client.request.model.IRequestData;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.IRequestProject;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * Creates a Datasource for listing managed projects under the selected RMS
 * server
 * 
 * @author Vikram Patil
 */
public class RMSManagedProjectDS
		extends
		AbstractRestDS<IRequestDataItem, IRequestProject<IRequestDataItem>, IRequestData<IRequestDataItem, IRequestProject<IRequestDataItem>>, IRequest<IRequestDataItem, IRequestProject<IRequestDataItem>, IRequestData<IRequestDataItem, IRequestProject<IRequestDataItem>>>> {

	private static RMSManagedProjectDS instance;

	/**
	 * Create a Singleton
	 * 
	 * @return
	 */
	public static RMSManagedProjectDS getInstance() {
		if (instance == null) {
			instance = new RMSManagedProjectDS("managedProjectList_DS");
		}
		return instance;
	}

	private RMSManagedProjectDS(String id) {
		super(id);
//		setRecordXPath(getRecordNode());

		DataSourceTextField pkProjectIdField = new DataSourceTextField("no");
		pkProjectIdField.setHidden(true);
		pkProjectIdField.setPrimaryKey(true);
		pkProjectIdField.setCanEdit(false);

		DataSourceTextField managedProjectsField = new DataSourceTextField("projectName");
		this.setFields(pkProjectIdField, managedProjectsField);
		
//		setDataURL("ds/test_data/projectList.data.xml");
//		setClientOnly(true);
	}

	@Override
	public ServerEndpoints getServerURL() {
		return ServerEndpoints.RMS_GET_MANAGED_PROJECTS;
	}
}
