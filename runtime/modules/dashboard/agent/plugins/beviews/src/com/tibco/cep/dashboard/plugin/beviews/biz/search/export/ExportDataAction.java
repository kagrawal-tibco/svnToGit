package com.tibco.cep.dashboard.plugin.beviews.biz.search.export;

import javax.servlet.http.HttpServletResponse;

import com.tibco.cep.dashboard.plugin.beviews.biz.BaseSessionCheckerAction;
import com.tibco.cep.dashboard.plugin.beviews.export.CSVExportContentFormatter;
import com.tibco.cep.dashboard.plugin.beviews.export.ExportContentHolder;
import com.tibco.cep.dashboard.plugin.beviews.export.ExportUtils;
import com.tibco.cep.dashboard.plugin.beviews.export.XMLExportContentFormatter;
import com.tibco.cep.dashboard.plugin.beviews.runtime.VelocityViewHelper;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.RawBizResponseImpl;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;

/**
 * @author rgupta
 *
 */
public abstract class ExportDataAction extends BaseSessionCheckerAction {

	private static final String LOADING_TITLE = "Exporting data...";

	@Override
	protected BizResponse doExecute(SecurityToken token, BizSessionRequest request) {
		try {
			String output = null;
			String mode = request.getParameter("exporttype");
			String suggestedFileName = getFileName(mode);

			ExportContentHolder contentHolder = getContentHolder(request);
			if (contentHolder == null) {
				if (mode.equals(ExportUtils.TYPE_XML)) {
					output = "<error>No Data</error>";
				} else {
					output = "No Data";
				}
			} else {
				boolean includeSystemField = Boolean.valueOf(request.getParameter("exportincludesystemfields"));
				// use of default
				if (mode.equals(ExportUtils.TYPE_XML)) {
					output = new XMLExportContentFormatter().transform(contentHolder.getContentRoot(), includeSystemField);
				} else if (mode.equals(ExportUtils.TYPE_CSV)) {
					output = new CSVExportContentFormatter().transform(contentHolder.getContentRoot(), includeSystemField);
				}
			}
			BizResponse bizResponse = new RawBizResponseImpl(output);
			bizResponse.addHeader("Content-Type", new StringBuilder("text/").append(mode).append("; charset=utf-8").toString());
			bizResponse.addHeader("Content-Disposition", "attachment; filename=" + suggestedFileName);
			return bizResponse;
		} catch (Exception e) {
			if (e instanceof IllegalArgumentException) {
				request.setAttribute("errormessage", "Illegal Arguement passed. " + e.getMessage());
				logger.log(Level.WARN, e.getMessage());
			} else {
				request.setAttribute("errormessage", "An exception occurred while processing your request");
				logger.log(Level.ERROR, e.getMessage(), e);
			}
			return VelocityViewHelper.getInstance().prepareRespone(request, "error");
		}
	}

	protected void prepResponseForNoCaching(HttpServletResponse response) {
	}

	protected abstract ExportContentHolder getContentHolder(BizSessionRequest request) throws Exception;

	private String getFileName(String mode) {
		String fileNameNew = null;
		if (mode.equals(ExportUtils.TYPE_CSV)) {
			fileNameNew = "export.csv";
		} else {
			fileNameNew = "export.xml";
		}
		return fileNameNew;
	}

	protected String getLoadingTitle() {
		return LOADING_TITLE;
	}

	@Override
	protected BizResponse handleInvalidSession(SecurityToken token, String sessionId, BizSessionRequest request) {
		request.setAttribute("errormessage", "Your session has timed out");
		return VelocityViewHelper.getInstance().prepareRespone(request, "error");
	}
}
