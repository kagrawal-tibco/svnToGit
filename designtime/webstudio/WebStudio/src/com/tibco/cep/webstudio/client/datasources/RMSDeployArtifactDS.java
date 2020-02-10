/**
 * 
 */
package com.tibco.cep.webstudio.client.datasources;

import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RMSMessages;
import com.tibco.cep.webstudio.client.request.model.IRequest;
import com.tibco.cep.webstudio.client.request.model.IRequestData;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.IRequestProject;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.client.widgets.WebStudioMenubar;

/**
 * Create a Datasource for getting an deployable Artifact List. For now it
 * supports only RTI and DT
 * 
 * @author Vikram Patil
 */
public class RMSDeployArtifactDS
		extends
		AbstractRestDS<IRequestDataItem, IRequestProject<IRequestDataItem>, IRequestData<IRequestDataItem, IRequestProject<IRequestDataItem>>, IRequest<IRequestDataItem, IRequestProject<IRequestDataItem>, IRequestData<IRequestDataItem, IRequestProject<IRequestDataItem>>>> {

	private static RMSDeployArtifactDS instance;
	private RMSMessages rmsMsgBundle = (RMSMessages) I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);

	/**
	 * Creates a singleton
	 * 
	 * @return
	 */
	public static RMSDeployArtifactDS getInstance() {
		if (instance == null) {
			instance = new RMSDeployArtifactDS("getDeployableArtifactList_DS");
		}
		return instance;
	}

	/**
	 * Default Constructor
	 */
	private RMSDeployArtifactDS(String id) {
		super(id);
//		this.setRecordXPath(this.getRecordNode());

		DataSourceIntegerField pkArtifactIdField = new DataSourceIntegerField("no");
		pkArtifactIdField.setHidden(true);
		pkArtifactIdField.setPrimaryKey(true);
		pkArtifactIdField.setCanEdit(false);

		DataSourceImageField typeIcon = new DataSourceImageField("imageSrc", this.rmsMsgBundle.rmsArtifact_type());
		typeIcon.setCanEdit(false);
		typeIcon.setAttribute("imageURLPrefix", WebStudioMenubar.ICON_PREFIX);
		typeIcon.setAttribute("width", 50);
		typeIcon.setAttribute("align", "center");

		DataSourceTextField path = new DataSourceTextField("artifactPath", this.rmsMsgBundle.rmsArtifact_path());
		path.setCanEdit(false);
		path.setAttribute("width", 373);
		path.setRequired(true);

		DataSourceTextField type = new DataSourceTextField("artifactType", this.rmsMsgBundle.rmsArtifact_fileType());
		type.setCanEdit(false);
		type.setRequired(true);
		type.setHidden(true);
		type.setAttribute("width", 175);

		DataSourceTextField extention = new DataSourceTextField("fileExtension");
		extention.setCanEdit(false);
		extention.setHidden(true);
		extention.setRequired(true);

		this.setFields(pkArtifactIdField, typeIcon, path, type, extention);

//		 this.setDataURL("ds/test_data/artifactList_Deployable.data.xml");
//		 this.setClientOnly(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.webstudio.client.datasources.AbstractRestDS#getServerURL()
	 */
	@Override
	public ServerEndpoints getServerURL() {
		return ServerEndpoints.RMS_POST_FETCH_ARTIFACTS_TO_DEPLOY;
	}
}
