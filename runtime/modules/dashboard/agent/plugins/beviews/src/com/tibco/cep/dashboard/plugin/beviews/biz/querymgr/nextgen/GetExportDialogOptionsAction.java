package com.tibco.cep.dashboard.plugin.beviews.biz.querymgr.nextgen;

import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;
import com.tibco.cep.dashboard.plugin.beviews.biz.BaseSessionCheckerAction;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.DialogOption;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.DialogOptions;
import com.tibco.cep.dashboard.psvr.ogl.model.types.DataType;
import com.tibco.cep.dashboard.security.SecurityToken;

public class GetExportDialogOptionsAction extends BaseSessionCheckerAction {

	private String defaultExportType;

	private String defaultExportDepth;

	private String defaultPerTypeCount;

	private String defaultOverallCount;

	private String defaultIncludeSystemFields;

	@Override
	protected void init(String command, Properties properties, Map<String, String> configuration) throws Exception {
		super.init(command, properties, configuration);
		defaultExportType = String.valueOf(BEViewsProperties.QUERY_MANAGER_EXPORT_DEFAULT_TYPE.getValue(this.properties));
		defaultExportDepth = String.valueOf(BEViewsProperties.QUERY_MANAGER_EXPORT_DEPTH.getValue(this.properties));
		defaultPerTypeCount = String.valueOf(BEViewsProperties.QUERY_MANAGER_EXPORT_TYPE_COUNT.getValue(this.properties));
		defaultOverallCount = String.valueOf(BEViewsProperties.QUERY_MANAGER_EXPORT_ALL_COUNT.getValue(this.properties));
		boolean qMgrExpIncludeSysFields = (Boolean) BEViewsProperties.QUERY_MANAGER_EXPORT_INCLUDE_SYSTEM_FIELDS.getValue(this.properties);
		boolean searchPageIncludeSysFields = (Boolean) BEViewsProperties.SEARCH_INCLUDE_SYSTEM_FIELDS.getValue(this.properties);
		defaultIncludeSystemFields = String.valueOf(qMgrExpIncludeSysFields || searchPageIncludeSysFields);
	}

	@Override
	protected BizResponse handleInvalidSession(SecurityToken token, String sessionId, BizSessionRequest request) {
		return handleError("Your session has timed out");
	}

	@Override
	protected BizResponse doExecute(SecurityToken token, BizSessionRequest request) {
		try {
			DialogOptions dialogOptions = new DialogOptions();
			dialogOptions.addDialogOption(createDialogOption("type", DataType.STRING, defaultExportType));
			dialogOptions.addDialogOption(createDialogOption("depth", DataType.INT, defaultExportDepth));
			dialogOptions.addDialogOption(createDialogOption("pertypecount", DataType.INT, defaultPerTypeCount));
			dialogOptions.addDialogOption(createDialogOption("overallcount", DataType.INT, defaultOverallCount));
			dialogOptions.addDialogOption(createDialogOption("includesystemfields", DataType.BOOLEAN, defaultIncludeSystemFields));
			return handleSuccess(OGLMarshaller.getInstance().marshall(token, dialogOptions));
		} catch (OGLException e) {
			return handleError(getMessage("drilldown.xml.marshalling.failure", getMessageGeneratorArgs(token, e)), e);
		}
	}

	private DialogOption createDialogOption(String name, DataType dataType, String value) {
		DialogOption option = new DialogOption();
		option.setName(name);
		option.setDataType(dataType);
		option.setContent(value);
		return option;
	}

}