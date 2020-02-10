/**
 *
 */
package com.tibco.cep.dashboard.plugin.beviews;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.config.ClientConfiguration;
import com.tibco.cep.dashboard.management.ManagementConfigurator.MODE;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.plugin.beviews.streaming.BENotificationsMXBean;
import com.tibco.cep.dashboard.plugin.beviews.streaming.BENotificationsMXBeanImpl;
import com.tibco.cep.dashboard.psvr.plugin.Builder;
import com.tibco.cep.dashboard.psvr.plugin.IResolver;
import com.tibco.cep.dashboard.psvr.plugin.PlugIn;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;
import com.tibco.cep.dashboard.psvr.plugin.ShutDown;
import com.tibco.cep.dashboard.psvr.plugin.StartUp;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.jmx.AnnotationAwareStandardMBean;

/**
 * @author anpatil
 *
 */
public final class BEViewsPlugIn extends PlugIn {

	private static final int START_ORDER = 0;

	public final static String PLUGIN_ID = Integer.toString(START_ORDER);

	public static final String SERVICE_NAME = null;

	private Map<ResolverType, IResolver> resolvers;

	private Builder builder;

	private StartUp startUp;

	private DeployedWebRoot deployedWebRoot;

	public BEViewsPlugIn() {
		super();
		resolvers = new HashMap<ResolverType, IResolver>();
	}

	@Override
	public String getDescriptiveName() {
		return "BE Views PlugIn";

	}

	@Override
	public String getId() {
		return PLUGIN_ID;
	}

	@Override
	public String getName() {
		return "beviews";
	}

	@Override
	public IResolver getResolver(ResolverType type) {
		return resolvers.get(type);
	}

	@Override
	public int getStartOrder() {
		return START_ORDER;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.dashboard.core.plugin.PlugIn#init()
	 */
	@Override
	protected void init() throws ManagementException {
		//update the client configuration
		//client debug
		ClientConfiguration.getInstance().addConfigurationValue("debug",BEViewsProperties.CLIENT_DEBUG_ENABLED.getValue(properties).toString());
		//PATCH BEViewsProperties.CLIENT_STREAMING_RECONNECT_ATTEMPTS & BEViewsProperties.CLIENT_STREAMING_RECONNECT_FREQUENCY should move to StreamingProperties
		//client streamingreconnectattempts
		ClientConfiguration.getInstance().addConfigurationValue("streamingreconnectattempts",BEViewsProperties.CLIENT_STREAMING_RECONNECT_ATTEMPTS.getValue(properties).toString());
		//client streamingreconnectfrequency
		ClientConfiguration.getInstance().addConfigurationValue("streamingreconnectfrequency",BEViewsProperties.CLIENT_STREAMING_RECONNECT_FREQUENCY.getValue(properties).toString());
		loadResolvers();
		if (mode.compareTo(MODE.SERVER) == 0) {
			try {
				//create the deployed web root
				deployedWebRoot = new DeployedWebRoot(properties, logger, exceptionHandler, messageGenerator, serviceContext.getRuleServiceProvider().getProject());
				//add shutdown hook
				Runtime.getRuntime().addShutdownHook(new ShutdownHook());
			} catch (IOException e) {
				throw new ManagementException("could not create http document root", e);
			}
		}
		builder = new BEViewsBuilder(this);
		startUp = new BEViewsStartup(this, deployedWebRoot);
	}

	private void loadResolvers() throws ManagementException {
		ResolverType resolverType = null;
		String className = null;
		try {
			Properties props = new Properties();
			props.load(this.getClass().getResourceAsStream("/beviewsresolvers.map"));
			Enumeration<Object> keys = props.keys();
			while (keys.hasMoreElements()) {
				String resolverTypeAsStr = (String) keys.nextElement();
				resolverType = ResolverType.valueOf(resolverTypeAsStr);
				className = props.getProperty(resolverTypeAsStr);
				if (StringUtil.isEmptyOrBlank(className) == false){
					Class<?> clazz = Class.forName(className);
					IResolver instance = (IResolver) clazz.newInstance();
					instance.init(logger, exceptionHandler, messageGenerator);
					resolvers.put(resolverType, instance);
				}
			}
		} catch (IOException e) {
			throw new ManagementException(messageGenerator.getMessage("resolver.mappingfile.loading.failure",new MessageGeneratorArgs(e)));
		} catch (ClassNotFoundException e) {
			throw new ManagementException(messageGenerator.getMessage("resolver.class.notfound.failure",new MessageGeneratorArgs(e,resolverType,className)));
		} catch (InstantiationException e) {
			throw new ManagementException(messageGenerator.getMessage("resolver.class.instantiation.failure",new MessageGeneratorArgs(e,resolverType,className)));
		} catch (IllegalAccessException e) {
			throw new ManagementException(messageGenerator.getMessage("resolver.class.access.failure",new MessageGeneratorArgs(e,resolverType,className)));
		} catch (PluginException e){
			throw new ManagementException(messageGenerator.getMessage("resolver.class.initialization.failure",new MessageGeneratorArgs(e,resolverType,className,e.getCause())));
		}
	}

	@Override
	protected void pause() throws ManagementException {
	}

	@Override
	protected boolean ping() {
		return true;
	}

	@Override
	public boolean registerMBeans(MBeanServer server, String namePrefix) {
		String registrationName = namePrefix + ",service=notificationprocessor";
		try {
			ObjectName name = new ObjectName(registrationName);
			AnnotationAwareStandardMBean standardMBean = new AnnotationAwareStandardMBean(new BENotificationsMXBeanImpl(), BENotificationsMXBean.class, true);
			server.registerMBean(standardMBean, name);
		} catch (InstanceAlreadyExistsException e) {
			String message = messageGenerator.getMessage("service.mbean.instancealreadyexisting.failure", new MessageGeneratorArgs(e,BENotificationsMXBean.class.getName(),registrationName));
			exceptionHandler.handleException(message, e, Level.WARN);
			return false;
		} catch (MBeanRegistrationException e) {
			String message = messageGenerator.getMessage("service.mbean.registration.failure", new MessageGeneratorArgs(e,BENotificationsMXBean.class.getName(),registrationName));
			exceptionHandler.handleException(message, e, Level.WARN);
			return false;
		} catch (NotCompliantMBeanException e) {
			String message = messageGenerator.getMessage("service.mbean.noncompliantbean.failure", new MessageGeneratorArgs(e,BENotificationsMXBean.class));
			exceptionHandler.handleException(message, e, Level.WARN);
			return false;
		} catch (MalformedObjectNameException e) {
			throw new RuntimeException(e);
		} catch (NullPointerException e) {
			throw new RuntimeException(e);
		}
		return true;
	}

	@Override
	protected void start() throws ManagementException {
	}

	@Override
	protected boolean stop() {
		return true;
	}

	@Override
	protected void unpause() throws ManagementException {
	}

	@Override
	public boolean unregisterMBeans(MBeanServer server) {
		return true;
	}

	@Override
	public Builder getBuilder() {
		return builder;
	}

	@Override
	public URL getActionConfigURL() {
		return this.getClass().getResource("/beviewsactionsconfig.xml");
	}

	@Override
	public ShutDown getShutdown() {
		return null;
	}

	@Override
	public StartUp getStartUp() {
		return startUp;
	}

	public DeployedWebRoot getDeployedWebRoot() {
		return deployedWebRoot;
	}

	private class ShutdownHook extends Thread {

		@Override
		public void run() {
			if (deployedWebRoot != null) {
				//delete the deployed web root
				boolean deleted = deployedWebRoot.delete();
				if (deleted == false) {
					logger.log(Level.WARN, "could not delete " + deployedWebRoot.getLocation());
				}
			}
		}
	}
}