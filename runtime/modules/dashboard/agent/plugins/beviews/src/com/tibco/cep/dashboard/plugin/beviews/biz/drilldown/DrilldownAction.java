package com.tibco.cep.dashboard.plugin.beviews.biz.drilldown;

import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.plugin.beviews.biz.BaseSessionCheckerAction;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.DrilldownTableTreeController;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTreeConstants;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.utils.DrillDownConfiguration;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.utils.DrilldownRequestStore;
import com.tibco.cep.dashboard.plugin.beviews.runtime.VelocityViewHelper;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSession;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.KnownParameterNames;
import com.tibco.cep.dashboard.psvr.mal.MALSession;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author rajesh
 * 
 */
public class DrilldownAction extends BaseSessionCheckerAction {

	public DrilldownAction() {
		super();
	}

	@Override
	protected void init(String command, Properties properties, Map<String, String> configuration) throws Exception {
		super.init(command, properties, configuration);
		// update the loggers
		logger = LoggingService.getChildLogger(logger, "drilldown");
		exceptionHandler = new ExceptionHandler(logger);
		messageGenerator = new MessageGenerator(messageGenerator.getServiceName(), exceptionHandler);
		DrillDownConfiguration.init(properties);
	}

	@Override
	protected BizResponse doExecute(SecurityToken token, BizSessionRequest request) {
		try {
			PresentationContext pCtx = new PresentationContext(token);
			TokenRoleProfile trProfile = pCtx.getTokenRoleProfile();
			MALSession malSession = trProfile.getMALSession();
			DrilldownRequestStore.setSecurityToken(request, token);
			DrilldownRequestStore.setMALSession(request, malSession);
			// get session if any
			BizSession session = request.getSession();
			// PATCH setting logger as session attribute
			session.setAttribute(Logger.class.getName(), logger);
			// PATCH setting exception handler as session attribute
			session.setAttribute(ExceptionHandler.class.getName(), exceptionHandler);
			request.setAttribute("exporturl", createExportURL(session));
			DrilldownTableTreeController tableTreeController = new DrilldownTableTreeController(token, properties, logger, exceptionHandler, messageGenerator, request);
			boolean bSuccess = tableTreeController.process();
			if (bSuccess) {
				if (DrilldownRequestStore.isSendEmptyResponse(request) == false) {
					if (request.getAttribute(TableTreeConstants.KEY_CMD).equals(TableTreeConstants.CMD_FULL_TABLE)) {
						return VelocityViewHelper.getInstance().prepareRespone(request, "success");
					} else {
						return VelocityViewHelper.getInstance().prepareRespone(request, "inline");
					}
				}
				return VelocityViewHelper.getInstance().prepareRespone(request, "emptydrilldown");
			}
		} catch (Exception e) {
			exceptionHandler.handleException(e);
		}
		return VelocityViewHelper.getInstance().prepareRespone(request, "error");
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

	@Override
	protected BizResponse handleInvalidSession(SecurityToken token, String sessionId, BizSessionRequest request) {
		DrilldownTableTreeController tableTreeController = new DrilldownTableTreeController(token, properties, logger, exceptionHandler, messageGenerator, request);
		tableTreeController.processError("Your session has timed out");
		return VelocityViewHelper.getInstance().prepareRespone(request, "error");
	}
}
