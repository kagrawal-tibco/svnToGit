/**
 * 
 */
package com.tibco.cep.webstudio.client.datasources;

import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.tibco.cep.webstudio.client.data.fields.CustomDataSourceTextField;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RMSMessages;
import com.tibco.cep.webstudio.client.request.model.IRequest;
import com.tibco.cep.webstudio.client.request.model.IRequestData;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.IRequestProject;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.client.widgets.WebStudioMenubar;

/**
 * Creates a Data source for retrieving the Revision details from the RMS
 * server.
 * 
 * @author Vikram Patil
 */
public class RMSRevisionDetailsDS
		extends
		AbstractRestDS<IRequestDataItem, IRequestProject<IRequestDataItem>, IRequestData<IRequestDataItem, IRequestProject<IRequestDataItem>>, IRequest<IRequestDataItem, IRequestProject<IRequestDataItem>, IRequestData<IRequestDataItem, IRequestProject<IRequestDataItem>>>> {
	private static RMSRevisionDetailsDS instance;
	private RMSMessages rmsMsgBundle = (RMSMessages) I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);
	
	public static final String DATASOURCE_ID = "revisionDetailsList_DS";
	public static final String FIELD_NAME_REVISION_ID = "revisionId";
	public static final String FIELD_NAME_COMMIT_OPERATION = "commitOperation";
	public static final String FIELD_NAME_ARTIFACT_IMAGE = "artifactImageName";
	public static final String FIELD_NAME_ARTIFACT_PATH = "artifactPath";
	public static final String FIELD_NAME_ARTIFACT_TYPE = "artifactType";
	public static final String FIELD_NAME_REVIEW_STATUS = "reviewStatus";
	public static final String FIELD_NAME_ARTIFACT_EXTN = "artifactFileExtn";
	//applicableStages; 
	
	/**
	 * Creates a singleton
	 * 
	 * @return
	 */
	public static RMSRevisionDetailsDS getInstance() {
		if (instance == null) {
			instance = new RMSRevisionDetailsDS(DATASOURCE_ID);
		}
		return instance;
	}

	private RMSRevisionDetailsDS(String id) {
		super(id);
//		this.setRecordXPath(this.getRecordNode());

		DataSourceIntegerField revisionIdField = new DataSourceIntegerField(FIELD_NAME_REVISION_ID, this.rmsMsgBundle.rmsHistory_gridHeader_revision());
		revisionIdField.setHidden(true);
		revisionIdField.setRequired(true);
		revisionIdField.setForeignKey(RMSRevisionDS.DATASOURCE_ID + "." + RMSRevisionDS.FIELD_NAME_REVISION_ID );
		
		DataSourceIntegerField pkRevisionIdField = new DataSourceIntegerField("id");
		pkRevisionIdField.setPrimaryKey(true);
		pkRevisionIdField.setCanEdit(false);
		pkRevisionIdField.setHidden(true);
		
		CustomDataSourceTextField action = new CustomDataSourceTextField(FIELD_NAME_COMMIT_OPERATION, this.rmsMsgBundle.rmsRevision_action());
		action.setCanEdit(false);
		action.setAttribute("width", "10%");
		action.setRequired(true);
		action.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				record.setAttribute("operationType", value.toString());
				return RMSArtifactOperationDisplayUtil.getDisplayName(value);
			}
		});
		
		DataSourceImageField typeIcon = new DataSourceImageField(FIELD_NAME_ARTIFACT_IMAGE, this.rmsMsgBundle.rmsArtifact_type());
		typeIcon.setCanEdit(false);
		typeIcon.setAttribute("imageURLPrefix", WebStudioMenubar.ICON_PREFIX);
		typeIcon.setAttribute("width", "6%");
		typeIcon.setAttribute("align", "center");
		
		DataSourceTextField path = new DataSourceTextField(FIELD_NAME_ARTIFACT_PATH, this.rmsMsgBundle.rmsArtifact_path());
		path.setCanEdit(false);
		path.setRequired(true);
		path.setAttribute("width", "50%");
		
		DataSourceTextField type = new DataSourceTextField(FIELD_NAME_ARTIFACT_TYPE, this.rmsMsgBundle.rmsArtifact_fileType());
		type.setCanEdit(false);
		type.setRequired(true);
		type.setAttribute("width", "17%");

		CustomDataSourceTextField status = new CustomDataSourceTextField(FIELD_NAME_REVIEW_STATUS, this.rmsMsgBundle.rmsRevisionDetails_status());
		status.setCanEdit(false);
		status.setAttribute("width", "17%");
		status.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				return RMSArtifactOperationDisplayUtil.getDisplayName(value);
			}
		});
		
		this.setFields(pkRevisionIdField, action, typeIcon, path, type, status);
		
//		this.setDataURL("ds/test_data/revisionDetails.data.xml");
//		this.setClientOnly(true);
	}

	@Override
	public ServerEndpoints getServerURL() {
		return ServerEndpoints.RMS_GET_WORKLIST_ITEMS;
	}
}
