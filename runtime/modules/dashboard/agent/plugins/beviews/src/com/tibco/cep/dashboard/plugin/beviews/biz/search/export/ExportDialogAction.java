package com.tibco.cep.dashboard.plugin.beviews.biz.search.export;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;
import com.tibco.cep.dashboard.plugin.beviews.biz.BaseSessionCheckerAction;
import com.tibco.cep.dashboard.plugin.beviews.export.ExportUtils;
import com.tibco.cep.dashboard.plugin.beviews.runtime.VelocityViewHelper;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.KnownParameterNames;
import com.tibco.cep.dashboard.security.SecurityToken;

public class ExportDialogAction extends BaseSessionCheckerAction {

	private static final String[] EMPTY_ARRAY = new String[0];

	private String exportHandlerCommand;

	private String defaultIncludeSystemFields;

	@Override
	protected void init(String command, Properties properties, Map<String, String> configuration) throws Exception {
		super.init(command, properties, configuration);
		exportHandlerCommand = configuration.get("exporthandlercommand");
		if (StringUtil.isEmptyOrBlank(exportHandlerCommand) == true){
			throw new Exception("Invalid exporthandlercommand specified for "+command);
		}
		boolean qMgrExpIncludeSysFields = (Boolean) BEViewsProperties.QUERY_MANAGER_EXPORT_INCLUDE_SYSTEM_FIELDS.getValue(this.properties);
		boolean searchPageIncludeSysFields = (Boolean) BEViewsProperties.SEARCH_INCLUDE_SYSTEM_FIELDS.getValue(this.properties);
		defaultIncludeSystemFields = String.valueOf(qMgrExpIncludeSysFields || searchPageIncludeSysFields);
	}

	@Override
	protected BizResponse doExecute(SecurityToken token, BizSessionRequest request) {
		StringBuilder exportActionUrl = new StringBuilder();
		exportActionUrl.append(request.getContextPath() + "?" + KnownParameterNames.COMMAND + "=" + exportHandlerCommand);
		for (Iterator<String> params = request.getParameterNames(); params.hasNext();) {
			String paramName = (String) params.next();
			if (paramName.equals(KnownParameterNames.COMMAND)) {
				continue;
			}
			exportActionUrl.append("&");
			exportActionUrl.append(paramName);
			exportActionUrl.append("=");
			exportActionUrl.append(request.getParameter(paramName));
		}
		request.setAttribute("exportURL", exportActionUrl.toString());
		request.setAttribute("customxslforxml", getCustomXSLs(ExportUtils.TYPE_XML));
		request.setAttribute("customxslforcsv", getCustomXSLs(ExportUtils.TYPE_CSV));
		//add defaults for drilldown/query export options
		request.setAttribute("exporttype", BEViewsProperties.QUERY_MANAGER_EXPORT_DEFAULT_TYPE.getValue(properties));
		request.setAttribute("exportdepth", BEViewsProperties.QUERY_MANAGER_EXPORT_DEPTH.getValue(properties));
		request.setAttribute("exporttypecount", BEViewsProperties.QUERY_MANAGER_EXPORT_TYPE_COUNT.getValue(properties));
		request.setAttribute("exportallcount", BEViewsProperties.QUERY_MANAGER_EXPORT_ALL_COUNT.getValue(properties));
		request.setAttribute("exportincludesystemfields", defaultIncludeSystemFields);

		BizResponse bizResponse = VelocityViewHelper.getInstance().prepareRespone(request, "success");
		return bizResponse;
	}

	private String[] getCustomXSLs(String type) {
		return EMPTY_ARRAY;
	}

	@Override
	protected BizResponse handleInvalidSession(SecurityToken token, String sessionId, BizSessionRequest request) {
		request.setAttribute("errormessage", "Your session has timed out");
		return VelocityViewHelper.getInstance().prepareRespone(request, "error");
	}
}
