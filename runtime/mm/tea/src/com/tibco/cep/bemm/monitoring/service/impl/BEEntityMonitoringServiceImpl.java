package com.tibco.cep.bemm.monitoring.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.bemm.common.handler.ApplicationDataFeedHandler;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.OMEnum;
import com.tibco.cep.bemm.management.service.exception.ServiceInitializationException;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.model.Monitorable.ENTITY_TYPE;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.impl.AbstractMonitorableEntity;
import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;
import com.tibco.cep.bemm.monitoring.metric.collector.ServiceInstanceStartStopListener;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.Attribute;
import com.tibco.cep.bemm.monitoring.service.BEEntityMonitoringService;
import com.tibco.cep.bemm.monitoring.util.MonitoringUtils;
import com.tibco.rta.Fact;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.tea.agent.be.util.BEEntityHealthStatus;
import com.tibco.tea.agent.be.util.BETeaAgentProps;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;

public class BEEntityMonitoringServiceImpl extends AbstractStartStopServiceImpl implements BEEntityMonitoringService {

	private Map<Monitorable, Object> monitorableEntitiesRegistry = null;
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BEEntityMonitoringService.class);
	private MessageService messageService;

	@Override
	public void init(Properties properties) throws Exception {

		registerStatusChangeListeners();

		this.monitorableEntitiesRegistry = new ConcurrentHashMap<>();
		messageService = BEMMServiceProviderManager.getInstance().getMessageService();

		// Creating the Rule store location , if it is not present already
		// ------------------------------------------------------------------------------
		String ruleStoreLocation = ((File) BEMMServiceProviderManager.getInstance()
				.getBEApplicationsManagementDataStoreService().getApplicationManagementDataStore()).getAbsolutePath();
		ruleStoreLocation = ruleStoreLocation + "/" + BETeaAgentProps.BE_DATASTORE_RULE_FOLDER;
		File ruleStore = new File(ruleStoreLocation);
		if (!ruleStore.exists()) {
			try {
				Files.createDirectories(ruleStore.toPath());
			} catch (IOException ioex) {
				throw new ServiceInitializationException(ioex);
			}
		}
		// ------------------------------------------------------------------------------
	}

	@Override
	public void startMetricsCollection(ServiceInstance serviceInstance) throws Exception {
		
		for (Agent agent : serviceInstance.getAgents()) {			
			register(agent, agent.getInstance().getJmxPort());
			// Send Service Instance Start Fact
			sendEntityStartFact(agent);
		}

	}

	@Override
	public void stopMetricsCollection(ServiceInstance serviceInstance, String oldStatus, String newStatus)
			throws Exception {

		for (Agent agent : serviceInstance.getAgents()) {
			unregister(agent);
			// Send Service Instance Stop Fact
			sendEntityStopFact(agent, oldStatus, newStatus);
		}
		// Removing the connection associated to the URI post instance stop
		BEMMServiceProviderManager.getInstance().getJmxConnectionPool().closeConnection(
				MonitoringUtils.getJmxServiceUrl(serviceInstance.getHost().getHostIp(), serviceInstance.getJmxPort()));
	}

	public final synchronized void register(Monitorable monitorableEntity, Object value) throws IOException {

		if (!monitorableEntitiesRegistry.containsKey(monitorableEntity)) {

			/*
			 * Note : Currently saving Accumulator type with the registry.Can be
			 * updated to include more details as per requirement .Revisit to
			 * make it comprehensive if necessary.
			 */
			monitorableEntitiesRegistry.put(monitorableEntity, value);
		}
	}

	public final synchronized void unregister(Monitorable monitorableEntity) throws IOException {
		if (monitorableEntitiesRegistry.containsKey(monitorableEntity)) {
			monitorableEntitiesRegistry.remove(monitorableEntity);
		}
	}

	@Override
	public Map<Monitorable, Object> getMonitorableRegistry() {
		return monitorableEntitiesRegistry;
	}

	private void registerStatusChangeListeners() throws Exception {
		AbstractMonitorableEntity.addStatusChangeListener(ENTITY_TYPE.PU_INSTANCE,
				new ServiceInstanceStartStopListener());
	}

	private void sendEntityStartFact(Agent agent) {

		Fact fact;
		try {
			List<Attribute> attrs = agent.getEntityStatusAttributes();
			fact = MonitoringUtils.getFact(attrs);
			BEMMServiceProviderManager.getInstance().getAggregationService().getSession().publishFact(fact);


			// Feeding the instance start fact to instance hierarchy separately
			// to avoid delay
			// and discrepancy in setting of health
			attrs = agent.getBasicFactAttributes();
			fact = MonitoringUtils.getFact(attrs);
			BEMMServiceProviderManager.getInstance().getAggregationService().getSession().publishFact(fact);

		} catch (Exception e) {
			LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.SENDING_SERVICE_INSTANCE_ERROR, "start"), e);
		}

	}

	private void sendEntityStopFact(Agent agent, String oldStatus, String newStatus) {
		Fact fact;
		try {

			// Sending Asset Stop Fact
			Fact assetFact = BEMMServiceProviderManager.getInstance().getAggregationService().createFact();
			assetFact.setAttribute(MetricAttribute.PU_INSTANCE, agent.getInstance().getName());
			assetFact.setAttribute(MetricAttribute.CLUSTER, agent.getInstance().getHost().getApplication().getName());
			assetFact.setAttribute(MetricAttribute.ASSET_STATUS, 3);
			assetFact.setAttribute(MetricAttribute.ASSET_NAME, MetricAttribute.DIM_INSTANCE);

			BEMMServiceProviderManager.getInstance().getAggregationService().getSession().publishFact(assetFact);

			// Sending Instance Stop Fact
			List<Attribute> attrs = agent.getEntityStatusAttributes();
			fact = MonitoringUtils.getFact(attrs);
			// Note : Explicitly setting the ISactive attribute as false ,
			// because we cannot be sure that
			// the instance is completely stopped at the moment of sending this
			// fact.
			fact.setAttribute(MetricAttribute.PU_INSTANCE_ISACTIVE, 0);

			fact.setAttribute(MetricAttribute.PU_NAME, agent.getInstance().getPuId());
			fact.setAttribute(MetricAttribute.AGENT_TYPE, agent.getAgentType().getType());

			// Note : if the last status of the entity is NeedsDeployment
			// meaning this entity is coming
			// in the application scope for the first time hence we need to send
			// the default health state
			if (BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(oldStatus)) {

				fact.setAttribute(MetricAttribute.PU_INSTANCE_HEALTH, BEEntityHealthStatus.ok.getHealthStatus());
				fact.setAttribute(MetricAttribute.AGENT_HEALTH, BEEntityHealthStatus.ok.getHealthStatus());
			}

			BEMMServiceProviderManager.getInstance().getAggregationService().getSession().publishFact(fact);

		} catch (Exception e) {
			LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.SENDING_SERVICE_INSTANCE_ERROR, "Stop"), e);
		}
	}

	@Override
	public void performUndeployOperation(ServiceInstance serviceInstance, String oldStatus, String newStatus) {

		for (Agent agent : serviceInstance.getAgents()) {
			// Send Service Instance Undeploy Fact
			sendEntityUndeployFact(agent, oldStatus, newStatus);
		}
	}

	private void sendEntityUndeployFact(Agent agent, String oldStatus, String newStatus) {
		Fact fact;
		try {
			List<Attribute> attrs = agent.getEntityStatusAttributes();
			fact = MonitoringUtils.getFact(attrs);
			// Note : Explicitly setting the ISactive attribute as false ,
			// because we cannot be sure that
			// the instance is completely stopped at the moment of sending this
			// fact.
			fact.setAttribute(MetricAttribute.PU_INSTANCE_ISACTIVE, 0);
			fact.setAttribute(MetricAttribute.PU_INSTANCE_COUNT, 0);
			fact.setAttribute(MetricAttribute.PU_AGENT_COUNT, 0);

			// Note : resetting the health state of the entity if it moves to
			// needs deployment state to Ok
			if (BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(newStatus)) {

				fact.setAttribute(MetricAttribute.PU_INSTANCE_HEALTH, BEEntityHealthStatus.ok.getHealthStatus());
				fact.setAttribute(MetricAttribute.AGENT_HEALTH, BEEntityHealthStatus.ok.getHealthStatus());
			}

			BEMMServiceProviderManager.getInstance().getAggregationService().getSession().publishFact(fact);

			// Sending Asset Delete Fact
			Fact assetFact = BEMMServiceProviderManager.getInstance().getAggregationService().createFact();
			assetFact.setAttribute(MetricAttribute.PU_INSTANCE, agent.getInstance().getName());
			assetFact.setAttribute(MetricAttribute.CLUSTER, agent.getInstance().getHost().getApplication().getName());
			assetFact.setAttribute(MetricAttribute.ASSET_STATUS, 4);
			assetFact.setAttribute(MetricAttribute.ASSET_NAME, MetricAttribute.DIM_INSTANCE);

			BEMMServiceProviderManager.getInstance().getAggregationService().getSession().publishFact(assetFact);

		} catch (Exception e) {
			LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.SENDING_SERVICE_INSTANCE_ERROR, "Stop"), e);
		}
	}

	/**
	 * Publish initial facts for each application in the BeTea scope with entity
	 * info
	 */
	@Override
	public void sendApplicationsBootstrapFacts() throws Exception {
		ArrayList<Thread> jobList = new ArrayList<Thread>();
		Map<String, Application> applicationMap = BEMMServiceProviderManager.getInstance()
				.getBEApplicationsManagementService().getApplications();

		for (Map.Entry<String, Application> application : applicationMap.entrySet()) {
			ApplicationDataFeedHandler<ServiceInstance> applicationDataFeedHandler = application.getValue()
					.getApplicationDataFeedHandler(OMEnum.JMX);

			for (Host host : application.getValue().getHosts()) {
				for (ServiceInstance instance : host.getInstances()) {
					// Note: An instance is considered the part of the
					// monitoring
					// only if it is deployed.Undeployed instances are ignored.
					if (application.getValue().isMonitorableOnly() || instance.getDeployed()) {
						PublishEntityBootstrapFactJob job = new PublishEntityBootstrapFactJob(instance,
								applicationDataFeedHandler);
						Thread t = job.start();
						jobList.add(t);
					}
				}
			}

			// Note:Wait for all threads to complete so that we can be sure at
			// the time of enabling rules that ,
			// all the initial facts have been published
			for (Thread job : jobList) {
				job.join();
			}
		}
	}

	@Override
	public void performMonitoringBootstrapOperations() throws Exception {

		Thread t = new Thread() {
			public void run() {
				try {
					sendApplicationsBootstrapFacts();
					// Enable the loaded rules
					BEMMServiceProviderManager.getInstance().getMetricRuleService().enableLoadedRules();
				} catch (Exception e) {
					LOGGER.log(Level.ERROR, messageService
							.getMessage(MessageKey.PERFORMING_MONITORING_RELATED_BOOTSTRAP_OPERATIONS_ERROR), e);
				}
			}
		};
		t.start();
	}

	class PublishEntityBootstrapFactJob implements Runnable {

		ServiceInstance instance;
		ApplicationDataFeedHandler<ServiceInstance> applicationDataFeedHandler;

		public PublishEntityBootstrapFactJob(ServiceInstance instance,
				ApplicationDataFeedHandler<ServiceInstance> applicationDataFeedHandler) {
			this.instance = instance;
			this.applicationDataFeedHandler = applicationDataFeedHandler;
		}

		@Override
		public void run() {
			Boolean isRunning = false;
			// Note : Getting the current state of the instance by invoking the
			// Mbean
			try {
				ServiceInstance rtServiceInstance = applicationDataFeedHandler.getTopologyData(instance);
				if (match(instance, rtServiceInstance)) {
					isRunning = true;
					if (instance.getHost().getApplication().isMonitorableOnly() && null != rtServiceInstance.getAgents()
							&& !rtServiceInstance.getAgents().isEmpty()) {
						for (Agent agent : rtServiceInstance.getAgents()) {
							if (null != agent) {
								instance.addAgent(agent);
								agent.setInstance(instance);
							}
						}

					}
				}
			} catch (Exception e) {
				LOGGER.log(Level.DEBUG, e.getMessage());
			}

			for (Agent agent : instance.getAgents()) {
				Fact fact;
				try {
					List<Attribute> attrs = agent.getEntityStatusAttributes();
					fact = MonitoringUtils.getFact(attrs);
					// Note : Send initial health of entities as ok
					fact.setAttribute(MetricAttribute.PU_INSTANCE_HEALTH, BEEntityHealthStatus.ok.getHealthStatus());
					fact.setAttribute(MetricAttribute.AGENT_HEALTH, BEEntityHealthStatus.ok.getHealthStatus());
					fact.setAttribute(MetricAttribute.PU_INSTANCE_ISACTIVE, isRunning == true ? 1 : 0);
					BEMMServiceProviderManager.getInstance().getAggregationService().getSession().publishFact(fact);

				} catch (Exception e) {
					LOGGER.log(Level.ERROR,
							messageService.getMessage(MessageKey.SENDING_SERVICE_INSTANCE_ERROR, "Start"), e);
				}
			}

		}

		public Thread start() {
			Thread t = new Thread(this, "teagent-bootstrap-facts-publisher");
			t.start();
			return t;
		}

	}

	/**
	 * @param stServiceInstance
	 * @param rtServiceInstance
	 * @return
	 */
	private boolean match(ServiceInstance stServiceInstance, ServiceInstance rtServiceInstance) {
		boolean isMatching = false;
		if (rtServiceInstance != null) {
			Application stApplication = stServiceInstance.getHost().getApplication();
			String rtClusterName = rtServiceInstance.getHost().getApplication().getClusterName();
			String rtInstanceName = rtServiceInstance.getName();
			String rtHostProcessId = rtServiceInstance.getProcessId();
			if (!stApplication.isMonitorableOnly()) {
				if (stApplication.getClusterName().equals(rtClusterName)
						&& stServiceInstance.getName().equals(rtInstanceName) && rtHostProcessId != null) {
					isMatching = true;
				}
			} else {
				isMatching = true;
			}

		}
		return isMatching;
	}
}
