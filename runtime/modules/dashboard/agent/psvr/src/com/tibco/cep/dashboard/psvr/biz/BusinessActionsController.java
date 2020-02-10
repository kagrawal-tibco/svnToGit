package com.tibco.cep.dashboard.psvr.biz;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.w3c.dom.Node;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.common.utils.XMLUtil;
import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.management.ServiceDependent;
import com.tibco.cep.dashboard.psvr.plugin.PlugIn;
import com.tibco.cep.dashboard.psvr.plugin.PlugInUtils;
import com.tibco.cep.dashboard.psvr.plugin.PluginFinder;
import com.tibco.cep.dashboard.psvr.util.PerformanceMeasurement;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public final class BusinessActionsController extends ServiceDependent {

	private static final String POST_REQUEST_PREFIX = "request=";

	private static final int POST_REQUEST_PREFIX_LENGTH = POST_REQUEST_PREFIX.length();

	private static final String ACTIONSCONFIG_FILENAME = "actionsconfig.xml";

	private static BusinessActionsController instance;

	public static final synchronized BusinessActionsController getInstance() {
		if (instance == null) {
			instance = new BusinessActionsController();
		}
		return instance;
	}

	private Map<String, BaseAction> actions;

	private BaseAction NO_COMMAND_ACTION = new BaseAction() {

		@Override
		public BizResponse execute(BizRequest request) {
			return doExecute(request);
		}

		@Override
		protected BizResponse doExecute(BizRequest request) {
			return handleError("no command parameter found in request");
		}

	};

	private BaseAction UNKNOWN_COMMAND_ACTION = new BaseAction() {

		@Override
		public BizResponse execute(BizRequest request) {
			return doExecute(request);
		}

		@Override
		protected BizResponse doExecute(BizRequest request) {
			return handleError("Unknown command[" + request.getParameter("command") + "]");
		}

	};

	@SuppressWarnings("unused")
	private BaseAction INVALID_REQUEST_ACTION = new BaseAction() {

		@Override
		protected BizResponse doExecute(BizRequest request) {
			return handleError("Invalid request");
		}

	};

	private BizResponse notRunningResponse;

	private PerformanceMeasurement requestCount;

	private PerformanceMeasurement failureRequestCount;

	private BusinessActionsController() {
		super("bizactionscontroller","Business Actions Controller");
		actions = new HashMap<String, BaseAction>();
		requestCount = new PerformanceMeasurement("Total Request");
		failureRequestCount = new PerformanceMeasurement("Total Failure Count");
	}

	@Override
	protected void doStart() throws ManagementException {
		notRunningResponse = new XMLBizResponseImpl(BizResponse.ERROR_STATUS,messageGenerator.getMessage("bizservice.not.running"));
		loadCoreActions();
		loadPluginActions();
	}

	private void loadCoreActions() throws ManagementException {
		URL resourceURL = this.getClass().getResource("/" + ACTIONSCONFIG_FILENAME);
		if (resourceURL == null) {
			throw new ManagementException("could not find the default " + ACTIONSCONFIG_FILENAME);
		}
		parseConfig(resourceURL, logger, properties, parent.getName());
	}

	private void loadPluginActions() {
		Map<String, URL> actionConfigURLs = PlugInUtils.getActionConfigURLs();
		for (String plugInId : actionConfigURLs.keySet()) {
			PlugIn plugIn = PluginFinder.getInstance().getPluginById(plugInId);
			parseConfig(actionConfigURLs.get(plugInId), plugIn.getLogger(), plugIn.getProperties(), plugIn.getName());
		}
	}

	private void parseConfig(URL resourceURL, Logger logger, Properties properties, String serviceName) {
		Logger actionLogger = LoggingService.getChildLogger(logger, "actions");
		ExceptionHandler exceptionHandler = new ExceptionHandler(logger);
		MessageGenerator messageGenerator = new MessageGenerator(serviceName, exceptionHandler);
		Node root;
		try {
			root = XMLUtil.parse(resourceURL.openStream());
		} catch (Exception ex) {
			String message = messageGenerator.getMessage("bizservice.fileparsing.exception", new MessageGeneratorArgs(ex, resourceURL));
			exceptionHandler.handleException(message, ex, Level.WARN);
			return;
		}
		Iterator<Node> actionNodesIterator = XMLUtil.getAllNodes(root, "action");
		while (actionNodesIterator.hasNext()) {
			Node actionNode = actionNodesIterator.next();
			String command = "unknown";
			try {
				command = XMLUtil.getAttribute(actionNode, "@command");
				String actionClass = XMLUtil.getAttribute(actionNode, "@type");
				Iterator<Node> configNodes = XMLUtil.getAllNodes(actionNode, "config");
				Map<String, String> configMap = new HashMap<String, String>();
				while (configNodes.hasNext()) {
					Node node = (Node) configNodes.next();
					String name = XMLUtil.getAttribute(node, "@name");
					String value = XMLUtil.getAttribute(node, "@value");
					configMap.put(name, value);
				}
				if ("sample".equalsIgnoreCase(command) == false) {
					BaseAction action = createAction(command, actionClass, properties, actionLogger, exceptionHandler, messageGenerator, Collections.unmodifiableMap(configMap));
					if (action != null) {
						actions.put(command, action);
					}
				}
			} catch (Exception e) {
				String message = messageGenerator.getMessage("bizservice.actionparsing.exception", new MessageGeneratorArgs(e, command, resourceURL));
				exceptionHandler.handleException(message, e, Level.WARN);
			}
		}
	}

	private BaseAction createAction(String command, String actionClass, Properties props, Logger logger, ExceptionHandler exHandler, MessageGenerator msgGenerator, Map<String, String> configMap) {
		try {
			Class<? extends BaseAction> clazz = Class.forName(actionClass).asSubclass(BaseAction.class);
			BaseAction action = clazz.newInstance();
			action.logger = logger;
			action.exceptionHandler = exHandler;
			action.messageGenerator = msgGenerator;
			action.init(command, props, configMap);
			return action;
		} catch (ClassNotFoundException e) {
			String message = messageGenerator.getMessage("bizservice.classnotfound.exception", new MessageGeneratorArgs(e, command, actionClass));
			exceptionHandler.handleException(message, e, Level.WARN);
			return null;
		} catch (InstantiationException e) {
			String message = messageGenerator.getMessage("bizservice.instantiation.exception", new MessageGeneratorArgs(e, command, actionClass));
			exceptionHandler.handleException(message, e, Level.WARN);
			return null;
		} catch (IllegalAccessException e) {
			String message = messageGenerator.getMessage("bizservice.illegalaccess.exception", new MessageGeneratorArgs(e, command, actionClass));
			exceptionHandler.handleException(message, e, Level.WARN);
			return null;
		} catch (Exception e) {
			String message = messageGenerator.getMessage("bizservice.initialization.exception", new MessageGeneratorArgs(e, command, actionClass));
			exceptionHandler.handleException(message, e, Level.WARN);
			return null;
		}
	}

	private BaseAction getAction(BizRequest request) {
		String command = request.getParameter(KnownParameterNames.COMMAND);
		if (StringUtil.isEmptyOrBlank(command) == true) {
			logger.log(Level.WARN, messageGenerator.getMessage("bizservice.invalid.command", new MessageGeneratorArgs(null, request.toXML())));
			return NO_COMMAND_ACTION;
		}
		BaseAction baseAction = actions.get(command);
		if (baseAction == null) {
			logger.log(Level.WARN, messageGenerator.getMessage("bizservice.unknown.command", new MessageGeneratorArgs(null, request.toXML(), command)));
			return UNKNOWN_COMMAND_ACTION;
		}
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, messageGenerator.getMessage("bizservice.known.command", new MessageGeneratorArgs(null, request.toXML(), command, baseAction.getClass().getName())));
		}
		return baseAction;
	}

	public BizResponse process(String request) {
		if (request.startsWith(POST_REQUEST_PREFIX)) {
			request = request.substring(POST_REQUEST_PREFIX_LENGTH);
		}
		BizRequest xmlRequest = new XMLBizRequestImpl(request);
		return process(xmlRequest);
	}

	public BizResponse process(BizRequest request) {
		long stime = System.currentTimeMillis();
		BizResponse response = null;
		try {
			if (isRunning() == false){
				response = notRunningResponse;
			}
			else {
				String command = request.getParameter(KnownParameterNames.COMMAND);
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, messageGenerator.getMessage("bizservice.incoming.command.request", new MessageGeneratorArgs(null, request.toXML(), command)));
				}
				BaseAction action = getAction(request);
				response = action.execute(request);
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, messageGenerator.getMessage("bizservice.known.command.response", new MessageGeneratorArgs(null, request.toXML(), command, response)));
				}
			}
			return response;
		} finally {
			long etime = System.currentTimeMillis();
			long timeTaken = etime - stime;
			if (response != null){
				if (response.getStatus().equals(BizResponse.SUCCESS_STATUS) == true){
					requestCount.add(System.currentTimeMillis(), timeTaken);
				}
				else {
					failureRequestCount.add(System.currentTimeMillis(), timeTaken);
				}
			}
		}
	}

	PerformanceMeasurement getRequestCount() {
		return requestCount;
	}

	PerformanceMeasurement getFailureRequestCount() {
		return failureRequestCount;
	}

	@Override
	protected boolean doStop() {
		for (BaseAction action : actions.values()) {
			action.cleanup();
		}
		actions.clear();
		return true;
	}

}