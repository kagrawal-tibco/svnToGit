package com.tibco.cep.dashboard.tools;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.w3c.dom.Node;

import com.tibco.cep.dashboard.common.utils.XMLUtil;
import com.tibco.cep.dashboard.integration.standalone.StandAloneDashboardSession;
import com.tibco.cep.dashboard.management.ManagementConfigurator;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.ManagementService;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BusinessActionsController;
import com.tibco.cep.dashboard.psvr.streaming.LoggerStreamer;
import com.tibco.cep.dashboard.psvr.streaming.Streamer;
import com.tibco.cep.dashboard.psvr.streaming.StreamerRegistry;
import com.tibco.cep.runtime.session.RuleSessionManager;

public class LocalDashboardAgentClient implements DashboardAgentClient {
	
	private ManagementService managementService;
	private StandAloneDashboardSession dashboardSession;

	public LocalDashboardAgentClient(File configurationFile) throws ManagementException {
		ManagementConfigurator configurator = new ManagementConfigurator();
		dashboardSession = new StandAloneDashboardSession(configurationFile);
		configurator.setDashboardSession(dashboardSession);
		configurator.createManagementService();
		configurator.init();
		managementService = configurator.getManagementService();
	}
	
	@Override
	public void start() throws IOException {
		if (managementService != null) {
			RuleSessionManager.currentRuleSessions.set(dashboardSession);
			try {
				managementService.start();
			} catch (ManagementException e) {
				throw new IOException(e);
			} finally {
				RuleSessionManager.currentRuleSessions.set(null);
			}
		}		
	}	

	@Override
	public String execute(BizRequest requestXML) throws IOException {
		String response = null;
		try {
			RuleSessionManager.currentRuleSessions.set(dashboardSession);
			response = BusinessActionsController.getInstance().process(requestXML).toString();
		} finally {
			RuleSessionManager.currentRuleSessions.set(null);
		}
		Node responseNode;
		try {
			responseNode = XMLUtil.parse(response);
		} catch (Exception e) {
			throw new IOException(e);
		}
		String status = XMLUtil.getString(responseNode, "state");
		if (status != null) { 
			if (status.equals(BizResponse.SUCCESS_STATUS) == true){
				String content = null;
				Node contentNode = XMLUtil.getNode(responseNode, "content");
				List<Node> childNodes = XMLUtil.getChildNodes(contentNode, true);
				if (childNodes.isEmpty() == true){
					content = XMLUtil.getString(contentNode, "/");
				}
				else {
					Node node = childNodes.get(0);
					if (node.getNodeType() == Node.CDATA_SECTION_NODE){
						content = node.getNodeValue();
					}
					else {
						content = XMLUtil.toSimpleString(node);
					}
				}
				return content;
			}
			throw new IOException(XMLUtil.getString(responseNode, "message"));
		}
		return response;
	}

	@Override
	public void startStreaming(BizRequest request) throws IOException {
		RuleSessionManager.currentRuleSessions.set(dashboardSession);
		try {
			Streamer streamer = new LoggerStreamer(dashboardSession.getRuleServiceProvider().getLogger("Streamer"));
			streamer.init();
			//register the streamer with the streaming registry
			StreamerRegistry.getInstance().registerStreamer("teststreamer", streamer);
			//add the streamer's id
			request.addParameter(Streamer.class.getName(), "teststreamer");
			request.addParameter("command", "startstreaming");
			//process the request
			execute(request);
		} finally {
			RuleSessionManager.currentRuleSessions.set(null);
		}
	}

	@Override
	public void stopStreaming(BizRequest request) throws IOException {
		RuleSessionManager.currentRuleSessions.set(dashboardSession);
		try {
			//add the streamer's id
			request.addParameter(Streamer.class.getName(), "teststreamer");
			request.addParameter("command", "stopstreaming");
			//process the request
			execute(request);
			//unregister the streamer from the registry
			StreamerRegistry.getInstance().unregisterStreamer("teststreamer");
		} finally {
			RuleSessionManager.currentRuleSessions.set(null);
		}		
	}

	@Override
	public void close() throws IOException {
		
	}

	@Override
	public void pause() throws IOException {
		if (managementService != null) {
			RuleSessionManager.currentRuleSessions.set(dashboardSession);
			try {
				managementService.pause();
			} catch (ManagementException e) {
				throw new IOException(e);
			} finally {
				RuleSessionManager.currentRuleSessions.set(null);
			}
		}		
	}

	@Override
	public void resume() throws IOException {
		if (managementService != null) {
			RuleSessionManager.currentRuleSessions.set(dashboardSession);
			try {
				managementService.resume();
			} catch (ManagementException e) {
				throw new IOException(e);
			} finally {
				RuleSessionManager.currentRuleSessions.set(null);
			}
		}
	}

	@Override
	public void stop() throws IOException {
		if (managementService != null) {
			RuleSessionManager.currentRuleSessions.set(dashboardSession);
			try {
				managementService.stop();
			} finally {
				RuleSessionManager.currentRuleSessions.set(null);
			}
			
		}
	}

}