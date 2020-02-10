package com.tibco.cep.dashboard.plugin.beviews.drilldown;

import java.util.Properties;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.utils.DrillDownConfiguration;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.utils.DrilldownRequestStore;
import com.tibco.cep.dashboard.plugin.beviews.runtime.ComponentContentProvider;
import com.tibco.cep.dashboard.plugin.beviews.runtime.VelocityViewHelper;
import com.tibco.cep.dashboard.psvr.biz.BizSession;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.KnownParameterNames;
import com.tibco.cep.kernel.service.logging.Logger;

public class SearchViewComponentContentProvider extends ComponentContentProvider {
	
	private boolean drilldownConfigured;
	
	public SearchViewComponentContentProvider() {
		drilldownConfigured = false;
	}
	
	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
		if (drilldownConfigured == false) {
			synchronized (this) {
				if (drilldownConfigured == false){
					DrillDownConfiguration.init(properties);
					drilldownConfigured = true;
				}
			}
		}
	}
	
	@Override
	public String getComponentContent(BizSessionRequest request) {
		BizSession session = request.getSession();
		// PATCH setting logger as session attribute
		session.setAttribute(Logger.class.getName(), logger);
		// PATCH setting exception handler as session attribute
		session.setAttribute(ExceptionHandler.class.getName(), exceptionHandler);
		request.setAttribute("exporturl", createExportURL(session));
		DrilldownTableTreeController tableTreeController = new DrilldownTableTreeController(securityToken, properties, logger, exceptionHandler, messageGenerator, request);
		boolean bSuccess = tableTreeController.process();
		String templateid =  "baserendererror";
		if (bSuccess) {
			if (DrilldownRequestStore.isSendEmptyResponse(request) == false) {
				//we always will deal with CMD_FULL_TABLE
				templateid =  "baserender";
			}
			else {
				templateid =  "emptybaserender";
			}
		}
		String viewTemplatePath = VelocityViewHelper.getInstance().getViewTemplatePath("drilldown", templateid);
		return VelocityViewHelper.getInstance().prepareResponseUsingTemplatePath(request, viewTemplatePath).toString();
	}
	
	private String createExportURL(BizSession session) {
		StringBuilder sb = new StringBuilder("?");
		sb.append(KnownParameterNames.COMMAND);
		sb.append("=showdrilldownexportdialog&");
		sb.append(KnownParameterNames.TOKEN);
		sb.append("=");
		sb.append(session.getTokenId());
		sb.append("&");
		sb.append(KnownParameterNames.SESSION_ID);
		sb.append("=");
		sb.append(session.getId());
		sb.append("&export=true&source=search");
		return sb.toString();
	}

}
