package com.tibco.cep.webstudio.client.datasources;

import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DateDisplayFormat;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RMSMessages;
import com.tibco.cep.webstudio.client.request.model.IRequest;
import com.tibco.cep.webstudio.client.request.model.IRequestData;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.IRequestProject;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.client.widgets.WebStudioMenubar;

/**
 * DataSource to get the List of artifact Locks for Manage Locks operation
 * @author vdhumal
 *
 */
public class RMSProjectArtifactsLockInfoDS 
		extends
		AbstractRestDS<IRequestDataItem, IRequestProject<IRequestDataItem>, IRequestData<IRequestDataItem, IRequestProject<IRequestDataItem>>, IRequest<IRequestDataItem, IRequestProject<IRequestDataItem>, IRequestData<IRequestDataItem, IRequestProject<IRequestDataItem>>>> {

	private RMSMessages rmsMsgBundle = (RMSMessages) I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);
	
	private static RMSProjectArtifactsLockInfoDS instance;

	/**
	 * Creates a singleton
	 * 
	 * @return
	 */
	public static RMSProjectArtifactsLockInfoDS getInstance() {
		if (instance == null) {
			instance = new RMSProjectArtifactsLockInfoDS("getArtifactLocksList_DS");
		}
		return instance;
	}
	
	private RMSProjectArtifactsLockInfoDS(String id) {
		super(id);

		DataSourceIntegerField pkArtifactIdField = new DataSourceIntegerField("no");
		pkArtifactIdField.setHidden(true);
		pkArtifactIdField.setPrimaryKey(true);
		pkArtifactIdField.setCanEdit(false);

		DataSourceTextField path = new DataSourceTextField("artifactPath", this.rmsMsgBundle.rmsArtifact_path());
		path.setCanEdit(false);
		path.setAttribute("width", "35%");
		path.setRequired(true);

		DataSourceTextField type = new DataSourceTextField("artifactType", this.rmsMsgBundle.rmsArtifact_fileType());
		type.setCanEdit(false);
		type.setRequired(true);
		type.setAttribute("width", "15%");

		DataSourceTextField extention = new DataSourceTextField("fileExtension");
		extention.setCanEdit(false);
		extention.setHidden(true);
		extention.setRequired(true);
		type.setAttribute("width", "15%");

		DataSourceTextField lockOwner = new DataSourceTextField("lockOwner", this.rmsMsgBundle.rmsArtifact_lockOwner());
		lockOwner.setCanEdit(false);
		lockOwner.setRequired(true);
		lockOwner.setAttribute("width", "15%");

		DataSourceDateField lockedTime = new DataSourceDateField("lockedTime", this.rmsMsgBundle.rmsArtifact_lockedTime());
		lockedTime.setCanEdit(false);
		lockedTime.setRequired(true);
		lockedTime.setAttribute("width", "15%");
		lockedTime.setDateFormatter(DateDisplayFormat.TOUSSHORTDATETIME);

		DataSourceImageField typeIcon = new DataSourceImageField("imageSrc", this.rmsMsgBundle.rmsArtifact_type());
		typeIcon.setCanEdit(false);
		typeIcon.setAttribute("imageURLPrefix", WebStudioMenubar.ICON_PREFIX);
		typeIcon.setAttribute("width", "5%");
		typeIcon.setAttribute("align", "center");
		
		DataSourceTextField baseArtifactPath = new DataSourceTextField("baseArtifactPath");
		baseArtifactPath.setCanEdit(false);
		baseArtifactPath.setHidden(true);

		this.setFields(pkArtifactIdField, typeIcon, path, type, extention, lockOwner, lockedTime, baseArtifactPath);

	}
	
	@Override
	public ServerEndpoints getServerURL() {
		return ServerEndpoints.RMS_GET_PROJECT_ARTIFACT_LOCKS;
	}

}
