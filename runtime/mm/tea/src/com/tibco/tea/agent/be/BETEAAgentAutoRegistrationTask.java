package com.tibco.tea.agent.be;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.ConnectException;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.tibco.cep.bemm.common.data.poller.ApplicationDataPoller;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.ApplicationDataProviderService;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.taskdefs.Task;
import com.tibco.cep.bemm.management.service.BEApplicationsManagementService;
import com.tibco.cep.bemm.management.service.BEMasterHostManagementService;
import com.tibco.cep.bemm.runtime.service.management.process.BETeaAgent;
import com.tibco.cep.bemm.runtime.service.management.process.BETeaAgentMBean;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.tea.agent.internal.server.TeaAgentRegistrationException;
import com.tibco.tea.agent.server.TeaAgentServer;

/**
 * Task used to to perform auto registration task
 * 
 * @author dijadhav
 *
 */
public class BETEAAgentAutoRegistrationTask implements Task {
	/**
	 * Logger object
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BETEAAgentAutoRegistrationTask.class);

	private ApplicationDataProviderService dataProviderService;
	private BEApplicationsManagementService applicationService;
	private BEMasterHostManagementService masterHostManagementService;
	private File beTEATempFileLocation;
	private TeaAgentServer server;
	private String teaServerURL;
	private long pollerDelay;
	private MessageService messageService;

	private ApplicationDataPoller appPoller;
	private TeaServerPinger teaServerPinger;
	private BETeaMasterHostPinger masterHostPinger;

	private BETeaDeleteTempFileTimerTask tempFolderFileTask;
	private long tempFileDeleteTaskDelay;
	private String agentName;
	private boolean unregisterEnabled;
	private boolean autoregisterEnabled;
	private static AtomicInteger counter = new AtomicInteger();

	public BETEAAgentAutoRegistrationTask(ApplicationDataProviderService dataProviderService,
			BEApplicationsManagementService applicationService, TeaAgentServer server, String teaServerURL,
			long pollerDelay, BEMasterHostManagementService masterHostManagementService, File beTEATempFileLocation,
			long tempFileDeleteTaskDelay, boolean unregisterEnabled, String agentName, boolean autoregisterEnabled) {
		super();
		this.dataProviderService = dataProviderService;
		this.applicationService = applicationService;
		this.masterHostManagementService = masterHostManagementService;
		this.server = server;
		this.teaServerURL = teaServerURL;
		this.pollerDelay = pollerDelay;
		this.beTEATempFileLocation = beTEATempFileLocation;
		this.tempFileDeleteTaskDelay = tempFileDeleteTaskDelay;
		this.agentName = agentName;
		this.unregisterEnabled = unregisterEnabled;
		this.autoregisterEnabled = autoregisterEnabled;
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.common.taskdefs.Task#perform()
	 */
	@Override
	public Object perform() throws Throwable {
		boolean taskCompleted = false;
		try {
			LOGGER.log(Level.INFO,
					messageService.getMessage(MessageKey.PERFORMING_BE_TEA_AGENT_AUTO_REGISTRATION_TASK));
			if (unregisterEnabled) {
				try {

					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.UNREGISTERING_BE_TEA_AGENT));

					server.unregisterAgent(teaServerURL);

					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.UNREGISTRED_BE_TEA_AGENT));

				} catch (Exception e) {
				}
			}
			// Auto register agent
			if (autoregisterEnabled)
				server.autoRegisterAgent(agentName, teaServerURL);
			counter.incrementAndGet();
			taskCompleted = true;
		} catch (Exception e) {
			if (e instanceof IllegalArgumentException) {
				if (e.getCause() instanceof TeaAgentRegistrationException) {
					if (null != e.getCause().getCause()
							&& e.getCause().getCause().getCause() instanceof ConnectException) {
						throw e;
					}
				}
				LOGGER.log(Level.WARN, e.getMessage());
				counter.incrementAndGet();
				taskCompleted = true;
			} else {
				throw e;
			}
		}
		if (counter.get() == 1) {
			appPoller = new ApplicationDataPoller(dataProviderService, applicationService);
			appPoller.init(ServiceProviderManager.getInstance().getConfiguration());
			regieterMbean();

			// Start the Application poller thread
			Timer timer = new Timer("teagent-status-poller", true);
			timer.schedule(appPoller, 0, pollerDelay);

			// TEA server pinger task
			if (autoregisterEnabled) {
				teaServerPinger = new TeaServerPinger(teaServerURL, server, agentName);
				Timer pinger = new Timer("teagent-pinger", true);
				pinger.schedule(teaServerPinger, 0, pollerDelay);
			}
			// BE Master host pinger task
			masterHostPinger = new BETeaMasterHostPinger(masterHostManagementService);
			Timer hostPinger = new Timer("host-pinger", true);
			hostPinger.schedule(masterHostPinger, 0, pollerDelay);

			tempFolderFileTask = new BETeaDeleteTempFileTimerTask(beTEATempFileLocation);
			Timer tempFolderDeleteTimer = new Timer("be-tea-agent-temp-folder-task", true);
			tempFolderDeleteTimer.schedule(tempFolderFileTask, 0, tempFileDeleteTaskDelay);
			LOGGER.log(Level.DEBUG,
					messageService.getMessage(MessageKey.PERFORMED_BE_TEA_AGENT_AUTO_REGISTRATION_TASK));

		}
		return taskCompleted;
	}

	

	/**
	 * Register BusinessEvents TEA agent Mbean
	 * 
	 * @throws MalformedObjectNameException
	 * @throws InstanceAlreadyExistsException
	 * @throws MBeanRegistrationException
	 * @throws NotCompliantMBeanException
	 */
	private void regieterMbean() throws MalformedObjectNameException, InstanceAlreadyExistsException,
			MBeanRegistrationException, NotCompliantMBeanException {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.REGISTERING_BE_TEA_AGENT_MBEAN));

		String objectName = "com.tibco.cep.bemm:type=BE TEA Agent";

		MBeanServer server = ManagementFactory.getPlatformMBeanServer();

		// Construct the ObjectName for the Hello MBean we will register
		ObjectName mbeanName = new ObjectName(objectName);

		BETeaAgentMBean mbean = new BETeaAgent();
		server.registerMBean(mbean, mbeanName);

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.REGISTRED_BE_TEA_MBEAN_SUCCESS));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.common.taskdefs.Task#getTaskName()
	 */
	@Override
	public String getTaskName() {
		return (messageService.getMessage(MessageKey.AUTO_REGISTRATION_TASK));
	}

	public void stop() {
		if (masterHostPinger != null) {
			try {
				masterHostPinger.cancel();
			} catch (Exception e) {

			}
		}

		if (appPoller != null) {
			try {
				appPoller.cancel();
			} catch (Exception e) {

			}
		}

		if (teaServerPinger != null) {
			try {
				teaServerPinger.cancel();
			} catch (Exception e) {

			}
		}

		if (masterHostPinger != null) {
			try {
				masterHostPinger.cancel();
			} catch (Exception e) {

			}
		}

		if (tempFolderFileTask != null) {
			try {
				tempFolderFileTask.cancel();
			} catch (Exception e) {

			}
		}

	}
}
