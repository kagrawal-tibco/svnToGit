package com.tibco.cep.dashboard.plugin.beviews.biz.querymgr.nextgen;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.ExportDataCSVFormatter;
import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.ExportDataFormatter;
import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.ExportDataXMLFormatter;
import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.tree.DrillDownDataTree;
import com.tibco.cep.dashboard.plugin.beviews.querymgr.export.QueryDataExportWorker;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.RawBizResponseImpl;
import com.tibco.cep.dashboard.psvr.biz.RequestProcessingException;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.security.SecurityToken;

public class ExportQueryDataAction extends CreateQueryAction {

	@Override
	protected BizResponse doExecute(SecurityToken token, BizSessionRequest request) {
		try {
			QuerySpec querySpec = super.createQuery(token, request);
			//get export format
			String exportFormat = request.getParameter("type");
			if (StringUtil.isEmptyOrBlank(exportFormat) == true) {
				exportFormat = String.valueOf(BEViewsProperties.QUERY_MANAGER_EXPORT_DEFAULT_TYPE.getValue(this.properties));
			}
			//find out if user wants system fields
			String includeSystemFieldsValue = request.getParameter("includesystemfields");
			boolean includeSystemFields = (Boolean) (BEViewsProperties.QUERY_MANAGER_EXPORT_INCLUDE_SYSTEM_FIELDS.getValue(this.properties));
			if ("true".equalsIgnoreCase(includeSystemFieldsValue) == true) {
				includeSystemFields = true;
			}
			else if ("false".equalsIgnoreCase(includeSystemFieldsValue) == true) {
				includeSystemFields = false;
			}
			//determine the export file name
			String suggestedFileName = "export.csv";
			ExportDataFormatter exportDataFormatter = new ExportDataCSVFormatter();
			if ("xml".equals(exportFormat) == true) {
				suggestedFileName = "export.xml";
				exportDataFormatter = new ExportDataXMLFormatter();
			}
			//find out how much depth the user wants
			int depth = getParameterValue(request, "depth", (Integer) BEViewsProperties.QUERY_MANAGER_EXPORT_DEPTH.getValue(this.properties));
			//find out per type count
			int perTypeCnt = getParameterValue(request, "pertypecount", (Integer) BEViewsProperties.QUERY_MANAGER_EXPORT_TYPE_COUNT.getValue(this.properties));
			//find out overall count
			int overallCnt = getParameterValue(request, "overallcount", (Integer) BEViewsProperties.QUERY_MANAGER_EXPORT_ALL_COUNT.getValue(this.properties));
			//generate the drill down data tree
			DrillDownDataTree drillDownDataTree = new DrillDownDataTree();
			if (querySpec != null) {
				//create the worker
				try {
					QueryDataExportWorker worker = new QueryDataExportWorker(logger, properties, exceptionHandler, messageGenerator);
					worker.setQuerySpec(querySpec);
					worker.setDepth(depth);
					worker.setPerTypeCount(perTypeCnt);
					worker.setOverallCount(overallCnt);
					worker.run();
					drillDownDataTree = worker.getDrillDownDataTree();
				} catch (QueryException e) {
					String message = getMessage("querymanager.data.export.exceution.failure", getMessageGeneratorArgs(token, e));
					exceptionHandler.handleException(message,e);
				}
			}
			//convert it to the request format
			BizResponse bizResponse = new RawBizResponseImpl(exportDataFormatter.convert(drillDownDataTree, includeSystemFields));
			//set content type
			bizResponse.addHeader("Content-Type", new StringBuilder("text/").append(exportFormat).append("; charset=utf-8").toString());
			//set content disposition
			bizResponse.addHeader("Content-Disposition", "attachment; filename=" + suggestedFileName);
			//return the response
			return bizResponse;
		} catch (NonFatalException e) {
			String message = getMessage("querymanager.data.export.failure", getMessageGeneratorArgs(token, e));
			return handleError(message, e);
		} catch (RequestProcessingException e) {
			return handleError(getMessage("querymanager.query.processing.failure", getMessageGeneratorArgs(token,e)), e);
		} catch (QueryException e) {
			return handleError(getMessage("querymanager.query.processing.failure", getMessageGeneratorArgs(token,e)), e);
		}
	}

	private int getParameterValue(BizSessionRequest request, String name, int defaultValue) {
		String value = request.getParameter(name);
		if (StringUtil.isEmptyOrBlank(value) == false) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException ex){
				//do nothing
			}
		}
		return defaultValue;
	}

}
