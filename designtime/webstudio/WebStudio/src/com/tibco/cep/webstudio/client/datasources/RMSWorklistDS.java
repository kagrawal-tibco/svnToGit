/**
 * 
 */
package com.tibco.cep.webstudio.client.datasources;

import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RMSMessages;
import com.tibco.cep.webstudio.client.request.model.IRequest;
import com.tibco.cep.webstudio.client.request.model.IRequestData;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.IRequestProject;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * Creates a Data Source for list all the revision and artifacts associated.
 * 
 * @author Vikram Patil
 */
public class RMSWorklistDS
		extends
		AbstractRestDS<IRequestDataItem, IRequestProject<IRequestDataItem>, IRequestData<IRequestDataItem, IRequestProject<IRequestDataItem>>, IRequest<IRequestDataItem, IRequestProject<IRequestDataItem>, IRequestData<IRequestDataItem, IRequestProject<IRequestDataItem>>>> {

	private static RMSWorklistDS INSTANCE;
	
	private static RMSMessages rmsMsgBundle = (RMSMessages) I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);
	
	/**
	 * Creates a singleton.
	 * 
	 * @return
	 */
	public static RMSWorklistDS getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RMSWorklistDS("getWorklist1DS");
		}
		return INSTANCE;
	}
	
	/**
	 * 
	 * @param id
	 */
	private RMSWorklistDS(String id) {
		super(id);
//		this.setRecordXPath(this.getRecordNode());
		
		DataSourceTextField revisionIdField = new DataSourceTextField("revisionId", rmsMsgBundle.rmsWorklist_gridHeader_revision());
		revisionIdField.setCanEdit(false);
		revisionIdField.setRequired(true);
		revisionIdField.setPrimaryKey(true);
		revisionIdField.setAttribute("width", "9.5%");
		
		DataSourceTextField userName = new DataSourceTextField("username", rmsMsgBundle.rmsWorklist_gridHeader_username());
		userName.setCanEdit(false);
		userName.setRequired(true);
		userName.setAttribute("width", "13.5%");
		
		DataSourceTextField projectName = new DataSourceTextField("managedProjectName", rmsMsgBundle.rmsWorklist_gridHeader_project());
		projectName.setCanEdit(false);
		projectName.setRequired(true);
		projectName.setAttribute("width", "20%");
		
		TextAreaItem checkInCommentsArea = new TextAreaItem();
		checkInCommentsArea.setHeight(50);
		checkInCommentsArea.setCanEdit(false);
		
		DataSourceTextField checkinComments = new DataSourceTextField("checkinComments", rmsMsgBundle.rmsWorklist_gridHeader_commitComment());
		checkinComments.setCanEdit(true);
		checkinComments.setRequired(true);
		checkinComments.setAttribute("width", "37%");
		checkinComments.setEditorType(checkInCommentsArea);
		
		DataSourceDateField checkInTime = new DataSourceDateField("checkinTime", rmsMsgBundle.rmsWorklist_gridHeader_commitTime());
		checkInTime.setCanEdit(false);
		checkInTime.setRequired(true);
		checkInTime.setAttribute("width", "20%");
		checkInTime.setAttribute("align", "left");
		checkInTime.setDateFormatter(DateDisplayFormat.TOUSSHORTDATETIME);

		this.setFields(revisionIdField, userName, projectName, checkinComments, checkInTime);

//		this.setDataURL("ds/test_data/worklist.data.xml");
//		this.setClientOnly(true);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.webstudio.client.datasources.AbstractRestDS#getServerURL()
	 */
	@Override
	public ServerEndpoints getServerURL() {
		return ServerEndpoints.RMS_GET_WORKLIST;
	}
}
