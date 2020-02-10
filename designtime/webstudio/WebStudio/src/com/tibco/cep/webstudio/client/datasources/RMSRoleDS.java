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
 * Creates a data source for retrieving all the roles associated with the server
 * 
 * @author vpatil
 */
public class RMSRoleDS
		extends
		AbstractRestDS<IRequestDataItem, IRequestProject<IRequestDataItem>, IRequestData<IRequestDataItem, IRequestProject<IRequestDataItem>>, IRequest<IRequestDataItem, IRequestProject<IRequestDataItem>, IRequestData<IRequestDataItem, IRequestProject<IRequestDataItem>>>> {

	private static RMSRoleDS instance;

	/**
	 * Creates a singleton
	 * 
	 * @return
	 */
	public static RMSRoleDS getInstance() {
		if (instance == null) {
			instance = new RMSRoleDS("getRoles_DS");
		}
		return instance;
	}

	/**
	 * 
	 * @param id
	 */
	public RMSRoleDS(String id) {
		super(id);
//		this.setRecordXPath(this.getRecordNode());
		
		DataSourceTextField pkRoleIdField = new DataSourceTextField("no");
		pkRoleIdField.setHidden(true);
		pkRoleIdField.setPrimaryKey(true);
		pkRoleIdField.setCanEdit(false);

		DataSourceTextField rolesField = new DataSourceTextField("role");
		this.setFields(pkRoleIdField, rolesField);
		
		
		//TODO - Need to fetch roles from the Server
//		this.setDataURL("ds/test_data/roles.data.xml");
//		this.setClientOnly(true);
	}

	@Override
	public ServerEndpoints getServerURL() {
		return ServerEndpoints.RMS_GET_DELEGATE_WORKLIST_ROLES;
	}
}
