package com.tibco.cep.dashboard.plugin.beviews.runtime;

import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.w3c.dom.Node;

import com.tibco.cep.dashboard.common.utils.XMLUtil;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSession;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.KnownParameterNames;
import com.tibco.cep.dashboard.psvr.biz.RawBizResponseImpl;

public class VelocityViewHelper {

	private static final String VIEWSCONFIG_FILENAME = "beviewsviewsconfig.xml";
	private static VelocityViewHelper instance = null;
	private Map<String, Map<String, String>> viewMap;

	private VelocityViewHelper() {
		init();
	}

	private void init() {
		URL resourceURL = this.getClass().getResource("/" + VIEWSCONFIG_FILENAME);
		if (resourceURL == null) {
			throw new RuntimeException("could not find the default " + VIEWSCONFIG_FILENAME);
		}
		parseConfig(resourceURL);
	}

	private void parseConfig(URL resourceURL) {
		Node root;
		try {
			root = XMLUtil.parse(resourceURL.openStream());
		} catch (Exception ex) {
			// String message = messageGenerator.getMessage("bizactions.fileparsing.exception", new MessageGeneratorArgs(ex, resourceURL));
			// exceptionHandler.handleException(message, ex, Level.WARNING);
			ex.printStackTrace();
			return;
		}
		try {
			viewMap = new HashMap<String, Map<String, String>>();
			Iterator<Node> viewNodesIterator = XMLUtil.getAllNodes(root, "view");
			while (viewNodesIterator.hasNext()) {
				Node viewNode = viewNodesIterator.next();
				String command = XMLUtil.getAttribute(viewNode, "@command");
				Iterator<Node> resultNodes = XMLUtil.getAllNodes(viewNode, "result");
				Map<String, String> resultMap = new HashMap<String, String>();
				while (resultNodes.hasNext()) {
					Node resultNode = (Node) resultNodes.next();
					String type = XMLUtil.getAttribute(resultNode, "@type");
					String template = XMLUtil.getAttribute(resultNode, "@template");
					resultMap.put(type, template);
				}
				viewMap.put(command, resultMap);
			}
			Properties properties = new Properties();
			properties.put("resource.loader", "file, class");
			properties.put("class.resource.loader.description", "Velocity Classpath Resource Loader");
			properties.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			properties.put("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
			Velocity.init(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static VelocityViewHelper getInstance() {
		if (instance == null) {
			instance = new VelocityViewHelper();
		}
		return instance;
	}

	public String getViewTemplatePath(String command, String viewId) {
		return viewMap.get(command).get(viewId);
	}

	public BizResponse prepareRespone(BizSessionRequest request, String viewId) {
		// based on the viewId, load the correct template and work with it.
		String command = request.getParameter(KnownParameterNames.COMMAND);
		String templatePath = VelocityViewHelper.getInstance().getViewTemplatePath(command, viewId);
		VelocityContext context = new VelocityContext();
		try {
			Template template = Velocity.getTemplate(templatePath);
			initContext(context, request);
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			writer.flush();
			BizResponse bizResponse = new RawBizResponseImpl(writer.toString());
			writer.close();
			return bizResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return new RawBizResponseImpl("Failed to create view for [" + viewId + "]");
		}
	}
	
	public BizResponse prepareResponseUsingTemplatePath(BizSessionRequest request, String templatePath){
		VelocityContext context = new VelocityContext();
		try {
			Template template = Velocity.getTemplate(templatePath);
			initContext(context, request);
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			writer.flush();
			BizResponse bizResponse = new RawBizResponseImpl(writer.toString());
			writer.close();
			return bizResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return new RawBizResponseImpl("Failed to create view for [" + templatePath + "]");
		}
	}

	private void initContext(VelocityContext context, BizSessionRequest request) {
		Iterator<String> attributeNames = request.getAttributeNames();
		while (attributeNames.hasNext()) {
			String attributeName = (String) attributeNames.next();
			context.put(attributeName, request.getAttribute(attributeName));
		}
		BizSession session = request.getSession();
		if (session != null) {
			context.put(KnownParameterNames.SESSION_ID,session.getId());
		}
		context.put(KnownParameterNames.TOKEN,request.getParameter(KnownParameterNames.TOKEN));
	}
}
