package com.tibco.cep.dashboard.psvr.streaming;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.naming.NamingException;

import com.tibco.cep.dashboard.config.ClientConfiguration;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.ManagementUtils;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.management.Service;
import com.tibco.cep.dashboard.security.SecurityClient;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.dashboard.security.SecurityTokenListener;
import com.tibco.cep.dashboard.timer.TimerThreadPool;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.jmx.AnnotationAwareStandardMBean;

public class StreamingService extends Service {

	private SecurityClient securityClient;
	private SecurityTokenListenerImpl securityTokenListener;

	private TimerThreadPool timerThreadPool;

	public StreamingService() {
		super("streaming","Streaming Service");
	}

	@Override
	protected void doInit() throws ManagementException {
		addDependent(DataSourceUpdateHandlerFactory.getInstance());
		addDependent(ChannelGroup.getInstance());
		addDependent(new DataUpdatesCommunicator());
		addDependent(new AutoSubscriber());

		int count = (Integer) StreamingProperties.STREAMING_THREAD_POOL_COUNT.getValue(properties);
		timerThreadPool = new TimerThreadPool(logger, count, new ThreadGroup("dashboard.agent.streaming.timers"));
		StreamingTimerProvider.getInstance().setTimerThreadPool(timerThreadPool);
	}

	@Override
	protected void doStart() throws ManagementException {
		//setting the client configiuration in start instead of init to allow disabling fo streaming server when using https
		//see com.tibco.cep.dashboard.integration.be.AutoTransportDeployer.deploy(DashboardSession, Logger, Properties, Class<T>)
		//update the client configuration to the right streaming mode
		boolean streamingEnabled = (Boolean) StreamingProperties.STREAMING_ENABLED.getValue(properties);
		if (streamingEnabled == true) {
			ClientConfiguration.getInstance().addConfigurationValue("updatemode", "tcp" );
			// update the client configuration
			Object value = StreamingProperties.STREAMING_PORT.getValue(properties);
			if (value != null) {
				ClientConfiguration.getInstance().addConfigurationValue(StreamingProperties.STREAMING_PORT.getName(), value.toString());
			}
		}
		else {
			ClientConfiguration.getInstance().addConfigurationValue("updatemode", "http");
			Long frequency = (Long) StreamingProperties.PULL_STREAMING_FREQUENCY.getValue(properties);
			ClientConfiguration.getInstance().addConfigurationValue("updatefrequency", frequency.toString());
		}
		try {
			securityClient = (SecurityClient) ManagementUtils.getContext().lookup("security");
		} catch (NamingException e) {
			String msg = messageGenerator.getMessage("security.lookup.failure");
			throw new ManagementException(msg, e);
		}
		securityTokenListener = new SecurityTokenListenerImpl();
		securityClient.addSecurityTokenListener(securityTokenListener);
	}

	@Override
	protected boolean doStop() {
		if (securityTokenListener != null && securityClient != null) {
			securityClient.removeSecurityTokenListener(securityTokenListener);
			try {
				securityClient.cleanup();
			} catch (ManagementException e) {
			}
		}
		securityTokenListener = null;
		securityClient = null;
		timerThreadPool.stop();
		StreamingTimerProvider.getInstance().setTimerThreadPool(null);
		return true;
	}

	@Override
	protected void registerMBeans(MBeanServer server, ObjectName baseName) {
		String registrationName = baseName.toString() + ",service=datacacheupdater";
		try {
			ObjectName name = new ObjectName(registrationName);
			AnnotationAwareStandardMBean standardMBean = new AnnotationAwareStandardMBean(new DataCacheUpdatersMXBeanImpl(), DataCacheUpdatersMXBean.class, true);
			server.registerMBean(standardMBean, name);
		} catch (InstanceAlreadyExistsException e) {
			String message = messageGenerator.getMessage("service.mbean.instancealreadyexisting.failure", new MessageGeneratorArgs(e,DataCacheUpdatersMXBean.class.getName(),registrationName));
			exceptionHandler.handleException(message, e, Level.WARN);
		} catch (MBeanRegistrationException e) {
			String message = messageGenerator.getMessage("service.mbean.registration.failure", new MessageGeneratorArgs(e,DataCacheUpdatersMXBean.class.getName(),registrationName));
			exceptionHandler.handleException(message, e, Level.WARN);
		} catch (NotCompliantMBeanException e) {
			String message = messageGenerator.getMessage("service.mbean.noncompliantbean.failure", new MessageGeneratorArgs(e,DataCacheUpdatersMXBean.class));
			exceptionHandler.handleException(message, e, Level.WARN);
		} catch (MalformedObjectNameException e) {
			throw new RuntimeException(e);
		} catch (NullPointerException e) {
			throw new RuntimeException(e);
		}

		registrationName = baseName.toString() + ",service=streaming";
		try {
			ObjectName name = new ObjectName(registrationName);
			AnnotationAwareStandardMBean standardMBean = new AnnotationAwareStandardMBean(new StreamingMXBeanImpl(), StreamingMXBean.class, true);
			server.registerMBean(standardMBean, name);
		} catch (InstanceAlreadyExistsException e) {
			String message = messageGenerator.getMessage("service.mbean.instancealreadyexisting.failure", new MessageGeneratorArgs(e,StreamingMXBean.class.getName(),registrationName));
			exceptionHandler.handleException(message, e, Level.WARN);
		} catch (MBeanRegistrationException e) {
			String message = messageGenerator.getMessage("service.mbean.registration.failure", new MessageGeneratorArgs(e,StreamingMXBean.class.getName(),registrationName));
			exceptionHandler.handleException(message, e, Level.WARN);
		} catch (NotCompliantMBeanException e) {
			String message = messageGenerator.getMessage("service.mbean.noncompliantbean.failure", new MessageGeneratorArgs(e,StreamingMXBean.class));
			exceptionHandler.handleException(message, e, Level.WARN);
		} catch (MalformedObjectNameException e) {
			throw new RuntimeException(e);
		} catch (NullPointerException e) {
			throw new RuntimeException(e);
		}

	}

	class SecurityTokenListenerImpl implements SecurityTokenListener {

		@Override
		public void tokenCreated(SecurityToken token) {
			// do nothing;
		}

		@Override
		public void tokenDeleted(SecurityToken token) {
			ChannelGroup.getInstance().destroy(token, messageGenerator.getMessage("token.deleted"));
		}

		@Override
		public void tokenExpired(SecurityToken token) {
			ChannelGroup.getInstance().destroy(token, messageGenerator.getMessage("token.expired"));
		}

	}

}