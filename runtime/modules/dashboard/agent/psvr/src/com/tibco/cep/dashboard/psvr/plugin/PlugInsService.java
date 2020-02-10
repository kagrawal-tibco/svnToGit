package com.tibco.cep.dashboard.psvr.plugin;

import java.util.List;
import java.util.Properties;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.ManagementProperties;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.Service;
import com.tibco.cep.kernel.service.logging.Level;

public class PlugInsService extends Service {

	private static final String DESCRIPTIVE_NAME = "Plugins Service";

	static final String INTERNAL_PLUGIN = "com.tibco.cep.dashboard.plugin.internal.DefaultPlugIn";

	private static PlugInsService instance;

	static final synchronized PlugInsService getInstance() {
		if (instance == null) {
			throw new IllegalStateException(DESCRIPTIVE_NAME+" has not been initialized");
		}
		return instance;
	}

	private PlugIn internalPlugin;

	private List<PlugIn> plugIns;

	public PlugInsService() {
		super("plugins", DESCRIPTIVE_NAME);
		instance = this;
	}

	@Override
	protected void doInit() throws ManagementException {
		createDefaultPlugIn(properties);

		// initialize the UnifiedMALElementResolver
		UnifiedMALElementResolver.LOGGER = logger;
		UnifiedMALElementResolver.EXCEPTION_HANDLER = exceptionHandler;
		UnifiedMALElementResolver.MESSAGE_GENERATOR = messageGenerator;
		UnifiedMALElementResolver.INTERNAL_PLUGIN = internalPlugin;

		// search for external plug-ins
		logger.log(Level.INFO, "Searching for plugins...");
		plugIns = PluginFinder.getInstance().getPlugins();
		logger.log(Level.INFO, "Found " + plugIns.size() + " plugins...");
		// initialize each external plug-in
		for (PlugIn plugIn : plugIns) {
			initializePlugIn(plugIn, properties);
		}

//		if (mode.compareTo(MODE.SERVER) == 0) {
//			addDependent(new PlugInsStartUpShutDownProviderService());
//		}
	}

	private void createDefaultPlugIn(Properties properties) throws ManagementException {
		try {
			// create and initialize the internal plugin
			internalPlugin = (PlugIn) Class.forName(INTERNAL_PLUGIN).newInstance();
			initializePlugIn(internalPlugin, properties);
		} catch (ClassNotFoundException e) {
			throw new ManagementException(messageGenerator.getMessage("internal.plugin.class.notfound.failure"), e);
		} catch (InstantiationException e) {
			throw new ManagementException(messageGenerator.getMessage("internal.plugin.class.instantiation.failure"), e);
		} catch (IllegalAccessException e) {
			throw new ManagementException(messageGenerator.getMessage("internal.plugin.class.access.failure"), e);
		}
	}

	private void initializePlugIn(PlugIn plugIn, Properties properties) throws ManagementException {
		plugIn.logger = LoggingService.getChildLogger(logger, plugIn.getName());
		plugIn.exceptionHandler = new ExceptionHandler(plugIn.logger);
		plugIn.messageGenerator = new MessageGenerator(plugIn.getName(), plugIn.exceptionHandler);
		plugIn.properties = new PlugInProperties((ManagementProperties) properties, plugIn.getName());
		plugIn.serviceContext = serviceContext;
		plugIn.mode = mode;
		plugIn.init();
	}

	@Override
	protected void doStart() throws ManagementException {
		internalPlugin.start();
		for (PlugIn plugIn : plugIns) {
			plugIn.start();
		}
	}

	@Override
	protected void doPause() throws ManagementException {
		for (PlugIn plugIn : plugIns) {
			plugIn.pause();
		}
		internalPlugin.pause();
	}

	@Override
	protected void doResume() throws ManagementException {
		for (PlugIn plugIn : plugIns) {
			plugIn.unpause();
		}
		internalPlugin.unpause();
	}

	@Override
	protected boolean doStop() {
		boolean success = true;
		for (PlugIn plugIn : plugIns) {
			boolean stop = plugIn.stop();
			if (stop == false) {
				logger.log(Level.WARN, plugIn.getDescriptiveName() + " did not stop properly...");
				success = stop;
			}
		}
		if (internalPlugin.stop() == false) {
			logger.log(Level.WARN, internalPlugin.getDescriptiveName() + " did not stop properly...");
			return false;
		}
		return success;
	}

	@Override
	protected boolean doPing() {
		if (internalPlugin.ping() == true) {
			for (PlugIn plugIn : plugIns) {
				if (plugIn.ping()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	PlugIn getDefaultPlugin() {
		return internalPlugin;
	}

	@Override
	protected void registerMBeans(MBeanServer server, ObjectName baseName) {
		for (PlugIn plugIn : plugIns) {
			plugIn.registerMBeans(server, baseName + ",plugin="+plugIn.getName());
		}
	}

}