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
 * Create a Datasource for getting an Artifact List associated to the select
 * project from the RMS server
 * 
 * @author Vikram Patil
 */
public class RMSGetArtifactDS
		extends
		AbstractRestDS<IRequestDataItem, IRequestProject<IRequestDataItem>, IRequestData<IRequestDataItem, IRequestProject<IRequestDataItem>>, IRequest<IRequestDataItem, IRequestProject<IRequestDataItem>, IRequestData<IRequestDataItem, IRequestProject<IRequestDataItem>>>> {

	private static RMSGetArtifactDS instance;

	private RMSMessages rmsMsgBundle = (RMSMessages) I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);
	
	/**
	 * Creates a singleton
	 * 
	 * @return
	 */
	public static RMSGetArtifactDS getInstance() {
		if (instance == null) {
			instance = new RMSGetArtifactDS("getArtifactList_DS");
		}
		return instance;
	}
	
	private RMSGetArtifactDS(String id) {
		super(id);
//		this.setRecordXPath(this.getRecordNode());

		DataSourceIntegerField pkArtifactIdField = new DataSourceIntegerField("no");
		pkArtifactIdField.setHidden(true);
		pkArtifactIdField.setPrimaryKey(true);
		pkArtifactIdField.setCanEdit(false);

		DataSourceTextField path = new DataSourceTextField("artifactPath", this.rmsMsgBundle.rmsArtifact_path());
		path.setCanEdit(false);
		path.setAttribute("width", 302);
		path.setRequired(true);

		DataSourceTextField type = new DataSourceTextField("artifactType", this.rmsMsgBundle.rmsArtifact_fileType());
		type.setCanEdit(false);
		type.setRequired(true);
		type.setAttribute("width", 175);

		DataSourceTextField extention = new DataSourceTextField("fileExtension");
		extention.setCanEdit(false);
		extention.setHidden(true);
		extention.setRequired(true);

		CustomDataSourceTextField changeType = new CustomDataSourceTextField("changeType",
				this.rmsMsgBundle.rmsArtifact_changeType());
		changeType.setCanEdit(false);
		changeType.setAttribute("width", 105);
		changeType.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				return RMSArtifactOperationDisplayUtil.getDisplayName(value);
			}
		});
		

		DataSourceImageField typeIcon = new DataSourceImageField("imageSrc", this.rmsMsgBundle.rmsArtifact_type());
		typeIcon.setCanEdit(false);
		typeIcon.setAttribute("imageURLPrefix", WebStudioMenubar.ICON_PREFIX);
		typeIcon.setAttribute("width", 50);
		typeIcon.setAttribute("align", "center");
		
		DataSourceTextField baseArtifactPath = new DataSourceTextField("baseArtifactPath");
		baseArtifactPath.setCanEdit(false);
		baseArtifactPath.setHidden(true);

		this.setFields(pkArtifactIdField, typeIcon, path, type, extention, changeType, baseArtifactPath);

//		this.setDataURL("ds/test_data/artifactList.data.xml");
//		this.setClientOnly(true);
	}

	@Override
	public ServerEndpoints getServerURL() {
		return ServerEndpoints.RMS_GET_PROJECT_ARTIFACTS;
	}
	
	public void setServerURL(ServerEndpoints serverEndPoint) {
		this.setDataURL(serverEndPoint.getURL());
	}
}
