package com.tibco.cep.dashboard.plugin.beviews.biz.drilldown.nextgen;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;
import com.tibco.cep.dashboard.plugin.beviews.biz.BaseSessionCheckerAction;
import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.ExportDataCSVFormatter;
import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.ExportDataFormatter;
import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.ExportDataXMLFormatter;
import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.tree.DrillDownDataTree;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.RawBizResponseImpl;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.security.SecurityToken;

public class ExportDrillDownDataAction extends BaseSessionCheckerAction {

	@Override
	protected BizResponse doExecute(SecurityToken token, BizSessionRequest request) {
		try {
			String exportFormat = request.getParameter("type");
			if (StringUtil.isEmptyOrBlank(exportFormat) == true) {
				exportFormat = String.valueOf(BEViewsProperties.QUERY_MANAGER_EXPORT_DEFAULT_TYPE.getValue(this.properties));
			}
			String includeSystemFieldsValue = request.getParameter("includesystemfields");
			boolean includeSystemFields = (Boolean) (BEViewsProperties.QUERY_MANAGER_EXPORT_INCLUDE_SYSTEM_FIELDS.getValue(this.properties));
			if ("true".equalsIgnoreCase(includeSystemFieldsValue) == true) {
				includeSystemFields = true;
			}
			else if ("false".equalsIgnoreCase(includeSystemFieldsValue) == true) {
				includeSystemFields = false;
			}
			String suggestedFileName = "export.csv";
			ExportDataFormatter exportDataFormatter = new ExportDataCSVFormatter();
			if ("xml".equals(exportFormat) == true) {
				suggestedFileName = "export.xml";
				exportDataFormatter = new ExportDataXMLFormatter();
			}
			DrillDownDataTree drillDownDataTree = (DrillDownDataTree) request.getSession().getAttribute(DrillDownDataTree.class.getName());
			BizResponse bizResponse = new RawBizResponseImpl(exportDataFormatter.convert(drillDownDataTree, includeSystemFields));
			bizResponse.addHeader("Content-Type", new StringBuilder("text/").append(exportFormat).append("; charset=utf-8").toString());
			bizResponse.addHeader("Content-Disposition", "attachment; filename=" + suggestedFileName);
			return bizResponse;
		} catch (NonFatalException e) {
			String message = getMessage("drilldown.data.export.failure", getMessageGeneratorArgs(token, e));
			return handleError(message, e);
		}
	}

	@Override
	protected BizResponse handleInvalidSession(SecurityToken token, String sessionId, BizSessionRequest request) {
		return handleError("Your session has timed out");
	}
}
