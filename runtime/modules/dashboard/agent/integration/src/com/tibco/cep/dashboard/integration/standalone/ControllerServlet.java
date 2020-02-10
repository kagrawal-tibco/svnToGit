package com.tibco.cep.dashboard.integration.standalone;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.ManagementConfigurator;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.ManagementService;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BusinessActionsController;
import com.tibco.cep.dashboard.psvr.streaming.StreamingProperties;

public class ControllerServlet extends HttpServlet {

	private static final String ROOT_DIR_PROP_NAME = "BASEDIR";

	private static final long serialVersionUID = -5083457150930757618L;

	private File rootDir;

	private File configFile;

	private ManagementService managementService;

	private TCPStreamingServer streamingServer;

	@Override
	public void init() throws ServletException {
		String rootDirValue = System.getProperty(ROOT_DIR_PROP_NAME);
		if (StringUtil.isEmptyOrBlank(rootDirValue) == true) {
			throw new ServletException("The specified " + ROOT_DIR_PROP_NAME + " is invalid");
		}
		rootDir = new File(rootDirValue);
		if (rootDir.exists() == false) {
			throw new ServletException(rootDirValue + " does not exist");
		}
		configFile = new File(rootDir, "config" + File.separator + "beviews.tra");
		if (configFile.exists() == false) {
			throw new ServletException(configFile.getAbsolutePath() + " does not exist");
		}
		try {
			ManagementConfigurator configurator = new ManagementConfigurator();
			configurator.setDashboardSession(new StandAloneDashboardSession(configFile));
			configurator.createManagementService();
			configurator.init();
			managementService = configurator.getManagementService();
			managementService.start();
			streamingServer = new TCPStreamingServer();
			Integer port = (Integer)StreamingProperties.STREAMING_PORT.getValue(managementService.getProperties());
			if (port != null){
				streamingServer.setPort(port);
			}
			streamingServer.init();
			streamingServer.start();
		} catch (ManagementException e) {
			throw new ServletException("could not start dashboard agent", e);
		} catch (IOException e) {
			throw new ServletException("could not start streaming server", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int length = req.getContentLength();
		if (length == -1){
			return;
		}
		ServletInputStream stream = req.getInputStream();
		byte[] bytes = new byte[length];
		int bytesRead = stream.read(bytes);
		if (bytesRead != length){
			return;
		}
		String request = new String(bytes);
		try {
			request = URLDecoder.decode(request, (req.getCharacterEncoding() != null ? req.getCharacterEncoding() : "UTF-8"));
		} catch (Exception e) {
			log("could not decode incoming request, using the request as it is", e);
		}
		BizResponse bizResponse = BusinessActionsController.getInstance().process(request);
		String response = bizResponse.toString();
		for (String header : bizResponse.getHeaderNames()) {
			resp.setHeader(header, bizResponse.getHeader(header));
		}
        //for HTTP 1.1
		resp.setHeader("Cache-Control","no-cache");
        //for HTTP 1.0
		resp.setHeader("Pragma","no-cache");
		resp.setDateHeader ("Expires", -1);
		resp.setContentType("text/xml");
		resp.setContentLength(response.length());
		resp.getWriter().write(response);
		resp.getWriter().flush();
	}

	@Override
	public void destroy() {
		if (streamingServer != null) {
			streamingServer.stop();
			streamingServer = null;
		}
		if (managementService != null) {
			managementService.stop();
			managementService = null;
		}
	}

}
