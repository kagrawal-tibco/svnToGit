/**
 * 
 */
package com.tibco.cep.webstudio.client.datasources;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FieldType;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RMSMessages;
import com.tibco.cep.webstudio.client.request.model.IRequest;
import com.tibco.cep.webstudio.client.request.model.IRequestData;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.IRequestProject;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * Creates a data source for retrieving all the revisions associated with a
 * particular artifact.
 * 
 * @author Vikram Patil
 */
public class RMSRevisionDS
		extends
		AbstractRestDS<IRequestDataItem, IRequestProject<IRequestDataItem>, IRequestData<IRequestDataItem, IRequestProject<IRequestDataItem>>, IRequest<IRequestDataItem, IRequestProject<IRequestDataItem>, IRequestData<IRequestDataItem, IRequestProject<IRequestDataItem>>>> {
	private static RMSRevisionDS instance;
	private RMSMessages rmsMsgBundle = (RMSMessages) I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);
	private GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	public static final String DATASOURCE_ID = "revisionList_DS";
	public static final String FIELD_NAME_REVISION_ID = "revisionId";
	public static final String FIELD_NAME_USERNAME = "userName";
	public static final String FIELD_NAME_CHECKIN_TIME = "checkinTime";
	public static final String FIELD_NAME_CHECKIN_COMMENT = "checkinComments";
	//projectName; 
	
	/**
	 * Creates a singleton
	 * 
	 * @return
	 */
	public static RMSRevisionDS getInstance() {
		if (instance == null) {
			instance = new RMSRevisionDS(DATASOURCE_ID);
		}
		return instance;
	}

	private RMSRevisionDS(String id) {
		super(id);
//		this.setRecordXPath(this.getRecordNode());

		DataSourceIntegerField pkRevisionIdField = new DataSourceIntegerField(FIELD_NAME_REVISION_ID, this.rmsMsgBundle.rmsHistory_gridHeader_revision());
		pkRevisionIdField.setPrimaryKey(true);
		pkRevisionIdField.setCanEdit(false);
		pkRevisionIdField.setAttribute("width", 75);
		pkRevisionIdField.setAttribute("align", Alignment.LEFT);
		
		DataSourceTextField userName = new DataSourceTextField(FIELD_NAME_USERNAME, this.rmsMsgBundle.rmsHistory_gridHeader_username());
		userName.setCanEdit(false);
		userName.setRequired(true);
		userName.setAttribute("width", 100);

		DataSourceField checkinTime = new DataSourceField(FIELD_NAME_CHECKIN_TIME, FieldType.DATETIME, this.rmsMsgBundle.rmsRevision_date());
		checkinTime.setCanEdit(false);
		checkinTime.setAttribute("width", 130);
		checkinTime.setRequired(true);
		checkinTime.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		checkinTime.setAttribute("align", Alignment.LEFT);

		DataSourceTextField checkinComments = new DataSourceTextField(FIELD_NAME_CHECKIN_COMMENT, this.globalMsgBundle.text_comments());
		checkinComments.setCanEdit(false);
		checkinComments.setAttribute("width", 320);

		this.setFields(pkRevisionIdField, userName, checkinTime, checkinComments);
	}

	@Override
	public ServerEndpoints getServerURL() {
		return ServerEndpoints.RMS_GET_ARTIFACT_REVISIONS;
	}
}
