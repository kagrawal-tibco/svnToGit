/**
 * 
 */
package com.tibco.cep.webstudio.client.datasources;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.MultipleAppearance;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemValueFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
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
 * Creates a Data Source for list all the artifacts associated to a particular revision.
 * 
 * @author Vikram Patil
 */
public class RMSWorklistDetailsDS
		extends
		AbstractRestDS<IRequestDataItem, IRequestProject<IRequestDataItem>, IRequestData<IRequestDataItem, IRequestProject<IRequestDataItem>>, IRequest<IRequestDataItem, IRequestProject<IRequestDataItem>, IRequestData<IRequestDataItem, IRequestProject<IRequestDataItem>>>> {

	private static List<RMSWorklistDetailsDS> INSTANCES;

	private static RMSMessages rmsMsgBundle = (RMSMessages) I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);

	/**
	 * Creates a singleton.
	 * 
	 * @return
	 */
	public static RMSWorklistDetailsDS getInstance(String revisionId) {
		RMSWorklistDetailsDS cachedDSObject = null;
		if (INSTANCES != null) {
			cachedDSObject = getCachedDSObject("getWorklistDetailsDS_"+revisionId);
		} else {
			INSTANCES = new ArrayList<RMSWorklistDetailsDS>();
		}
		
		if (cachedDSObject == null) {
			cachedDSObject = new RMSWorklistDetailsDS("getWorklistDetailsDS_"+revisionId);
			INSTANCES.add(cachedDSObject);
		}
		return cachedDSObject;
	}
	
	/**
	 * 
	 * @param id
	 */
	private RMSWorklistDetailsDS(String id) {
		super(id);
//		this.setRecordXPath(this.getRecordNode());
		
		DataSourceIntegerField revisionIdField = new DataSourceIntegerField("revisionId", "Revision");
		revisionIdField.setHidden(true);
		revisionIdField.setRequired(true);
		revisionIdField.setForeignKey("getWorklist1DS.revisionId");
		
		DataSourceTextField path = new DataSourceTextField("artifactPath", rmsMsgBundle.rmsArtifact_path());
		path.setCanEdit(false);
		path.setAttribute("width", "28%");
		path.setPrimaryKey(true);
		path.setRequired(true);

		DataSourceTextField type = new DataSourceTextField("artifactType", rmsMsgBundle.rmsArtifact_fileType());
		type.setCanEdit(false);
		type.setRequired(true);
		type.setAttribute("width", "12%");

		CustomDataSourceTextField changeType = new CustomDataSourceTextField("commitOperation",	rmsMsgBundle.rmsArtifact_changeType());
		changeType.setCanEdit(false);
		changeType.setAttribute("width", "11%");
		changeType.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				record.setAttribute("operationType", value.toString());
				return RMSArtifactOperationDisplayUtil.getDisplayName(value);
			}
		});

		DataSourceImageField typeIcon = new DataSourceImageField("artifactImageName", rmsMsgBundle.rmsArtifact_type());
		typeIcon.setCanEdit(false);
		typeIcon.setAttribute("imageURLPrefix", WebStudioMenubar.ICON_PREFIX);
		typeIcon.setAttribute("width", "5%");
		typeIcon.setAttribute("align", "center");
		
		DataSourceTextField artifactFileExtn = new DataSourceTextField("artifactFileExtn");
		artifactFileExtn.setCanEdit(false);
		artifactFileExtn.setHidden(true);
		
		DataSourceTextField applicableStages = new DataSourceTextField("applicableStages");
		applicableStages.setHidden(true);
		applicableStages.setRequired(true);
		
		SelectItem commitStateCombo = new SelectItem();
		CustomDataSourceTextField reviewStatus = new CustomDataSourceTextField("reviewStatus", rmsMsgBundle.rmsWorklist_gridHeader_columnStatus());
		reviewStatus.setCanEdit(true);
		reviewStatus.setAttribute("width", "12%");
		reviewStatus.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				return RMSArtifactOperationDisplayUtil.getDisplayName(value);
			}
		});
		
		reviewStatus.setEditorType(commitStateCombo);
		
		DataSourceTextField applicableEnvironments = new DataSourceTextField("applicableEnvironments");
		applicableEnvironments.setHidden(true);
		applicableEnvironments.setRequired(true);
		
		SelectItem deployEnvironmentCombo = new SelectItem();
		deployEnvironmentCombo.setMultiple(true);
		deployEnvironmentCombo.setMultipleAppearance(MultipleAppearance.PICKLIST);
		CustomDataSourceTextField deployEnvironment = new CustomDataSourceTextField("deployEnvironments", rmsMsgBundle.rmsWorklist_gridHeader_environments());
		deployEnvironment.setCanEdit(false);
		deployEnvironment.setAttribute("width", "11%");		
		deployEnvironment.setEditorType(deployEnvironmentCombo);
		
		TextAreaItem approvalCommentsArea = new TextAreaItem();
		approvalCommentsArea.setHeight(50);
		
		DataSourceTextField reviewComments = new DataSourceTextField("reviewComments", rmsMsgBundle.rmsWorklist_gridHeader_comments());
		reviewComments.setCanEdit(true);
		reviewComments.setAttribute("width", "21%");
		reviewComments.setEditorType(approvalCommentsArea);

		this.setFields(revisionIdField, applicableStages, applicableEnvironments, artifactFileExtn, typeIcon, path, type, changeType, reviewStatus, deployEnvironment, reviewComments);
		
//		this.setDataURL("ds/test_data/worklistDetails.data.xml");
//		this.setClientOnly(true);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.webstudio.client.datasources.AbstractRestDS#getServerURL()
	 */
	@Override
	public ServerEndpoints getServerURL() {
		return ServerEndpoints.RMS_GET_WORKLIST_ITEMS;
	}
	
	/**
	 * check if a Data Source associated to a specific Id exists
	 * 
	 * @param dsID
	 * @return
	 */
	public static RMSWorklistDetailsDS getCachedDSObject(String dsID) {
		for (RMSWorklistDetailsDS worklistDetailsDS : INSTANCES) {
			if (worklistDetailsDS.getID().equals(dsID)) {
				return worklistDetailsDS;
			}
		}
		return null;
	}
}
