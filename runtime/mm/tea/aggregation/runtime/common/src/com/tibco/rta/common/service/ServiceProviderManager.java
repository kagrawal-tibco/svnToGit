package com.tibco.rta.common.service;

import java.util.Properties;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.service.admin.AdminService;
import com.tibco.rta.service.cluster.ClusterService;
import com.tibco.rta.service.cluster.GroupMembershipService;
import com.tibco.rta.service.hawk.HawkService;
import com.tibco.rta.service.heartbeat.HeartbeatService;
import com.tibco.rta.service.metric.MetricIntrospectionService;
import com.tibco.rta.service.metric.MetricService;
import com.tibco.rta.service.om.ObjectManager;
import com.tibco.rta.service.persistence.PersistenceService;
import com.tibco.rta.service.persistence.SessionPersistenceService;
import com.tibco.rta.service.purge.PurgeService;
import com.tibco.rta.service.query.QueryService;
import com.tibco.rta.service.recovery.RecoveryService;
import com.tibco.rta.service.rules.RuleService;
import com.tibco.rta.service.rules.SnapshotRuleEvalService;
import com.tibco.rta.service.transport.TransportService;

/**
 * @author bgokhale
 *         <p/>
 *         This class manages and owns the various services used by the engine
 */

public class ServiceProviderManager {

	private static ServiceProviderManager instance;
	private TransportService transportService;
	private PersistenceService persistenceService;
	private AdminService adminService;
	private MetricService metricsService;
	private MetricIntrospectionService metricIntrospectionService;
	private WorkItemService workItemService;
	private Properties configuration;
	private RuleService ruleService;
	private QueryService queryService;

	private ClusterService clusterService;
	private ObjectManager objectManager;
	private RecoveryService recoveryService;
	private GroupMembershipService groupMembershipService;
	private HeartbeatService heartbeatService;
	// Started only in FT mode
	private SessionPersistenceService sessionPersistenceService;
	private PurgeService purgeService;

	protected LockService lockService;
	protected ShutdownService shutdownService;
	protected RuntimeService runtimeService;
	protected HawkService microagentService;
	
	private SnapshotRuleEvalService snapshotRuleService;
	
	public static ServiceProviderManager getInstance() {
		if (instance == null) {
			instance = new ServiceProviderManager();
		}
		return instance;
	}

	public void setConfiguration(Properties configuration) {
		this.configuration = configuration;
	}

	public PersistenceService getPersistenceService() throws Exception {
		if (persistenceService == null) {
			String type = (String) ConfigProperty.RTA_PERSISTENCE_PROVIDER.getValue(configuration);
			if ("tibas".equalsIgnoreCase(type)) {
				persistenceService = (PersistenceService) Class
						.forName("com.tibco.rta.service.persistence.as.BatchedASPersistenceService").newInstance();
			} else if ("MEMORY".equalsIgnoreCase(type)) {
				persistenceService = (PersistenceService) Class
						.forName("com.tibco.rta.service.persistence.memory.InMemoryPersistenceProvider").newInstance();
			} else if ("database".equalsIgnoreCase(type)) {
				persistenceService = (PersistenceService) Class
						.forName("com.tibco.rta.service.persistence.db.BatchedDatabasePersistenceService")
						.newInstance();
			} else if (type.equals("NOSTORE")) {
				persistenceService = (PersistenceService) Class
						.forName("com.tibco.rta.service.persistence.nostore.NullPersistenceProvider").newInstance();
			}
		}
		return persistenceService;
	}

	public RecoveryService getRecoveryService() throws Exception {
		if (recoveryService == null) {
			recoveryService = (RecoveryService) Class.forName("com.tibco.rta.service.recovery.impl.RecoveryServiceImpl")
					.newInstance();
		}
		return recoveryService;
	}

	public HeartbeatService getHeartbeatService() throws Exception {
		if (heartbeatService == null) {
			heartbeatService = (HeartbeatService) Class
					.forName("com.tibco.rta.common.service.session.SessionHeartbeatService").newInstance();
		}
		return heartbeatService;
	}

	public SessionPersistenceService getSessionPersistenceService() throws Exception {
		if (sessionPersistenceService == null && isFTEnabled()) {
			String type = (String) ConfigProperty.RTA_PERSISTENCE_PROVIDER.getValue(configuration);
			if ("database".equalsIgnoreCase(type)) {
				sessionPersistenceService = (SessionPersistenceService) Class
						.forName("com.tibco.rta.service.persistence.db.SessionDBPersistenceService").newInstance();
			} else if ("tibas".equalsIgnoreCase(type)) {
				sessionPersistenceService = (SessionPersistenceService) Class
						.forName("com.tibco.rta.service.persistence.as.SessionASPersistenceService").newInstance();
			} else if ("MEMORY".equalsIgnoreCase(type)) {
				sessionPersistenceService = (SessionPersistenceService) Class
						.forName("com.tibco.rta.service.persistence.memory.SessionMemoryPersistenceService")
						.newInstance();
			} else if (type.equals("NOSTORE")) {
				sessionPersistenceService = (SessionPersistenceService) Class
						.forName("com.tibco.rta.service.persistence.nostore.NullSessionPersistenceProvider")
						.newInstance();
			}
		}
		return sessionPersistenceService;
	}

	public AdminService getAdminService() throws Exception {
		if (adminService == null) {
			adminService = (AdminService) Class.forName("com.tibco.rta.service.admin.AdminServiceImpl").newInstance();
		}
		return adminService;

	}

	public WorkItemService getWorkItemService() throws Exception {
		if (workItemService == null) {
			workItemService = (WorkItemService) Class.forName("com.tibco.rta.common.service.impl.WorkItemServiceImpl")
					.newInstance();
		}
		return workItemService;

	}

	public GroupMembershipService getGroupMembershipService() throws Exception {
		if (groupMembershipService == null) {
			groupMembershipService = (GroupMembershipService) Class
					.forName("com.tibco.rta.service.cluster.impl.DefaultGroupMembershipService").newInstance();
		}
		return groupMembershipService;

	}

	public WorkItemService newWorkItemService(String threadPoolName) throws Exception {
		WorkItemService wis = (WorkItemService) Class.forName("com.tibco.rta.common.service.impl.WorkItemServiceImpl")
				.newInstance();
		wis.setThreadPoolName(threadPoolName);
		return wis;
	}

	public CustomWorkItemService newCustomWorkItemService(String threadPoolName) throws Exception {
		CustomWorkItemService wis = (CustomWorkItemService) Class
				.forName("com.tibco.rta.common.service.impl.CustomWorkItemServiceImpl").newInstance();
		wis.setThreadPoolName(threadPoolName);
		return wis;
	}

	public MetricService getMetricsService() throws Exception {
		if (metricsService == null) {
			String clzNm = "com.tibco.rta.service.metric.BatchedParallelMetricServiceImpl";
			String useParallel = (String) ConfigProperty.RTA_METRIC_SERVICE_STRATEGY.getValue(configuration);
			if (useParallel.equals("true")) {
				boolean useBatching = true;
				try {
					useBatching = Boolean
							.parseBoolean((String) ConfigProperty.RTA_TRANSACTION_USE_BATCHING.getValue(configuration));
				} catch (Exception e) {
				}

				boolean storeProcessedFacts = false;
				try {
					storeProcessedFacts = Boolean
							.parseBoolean((String) ConfigProperty.RTA_STORE_PROCESSED_FACTS.getValue(configuration));
				} catch (Exception e) {
				}

				boolean useAssetFeature = true;
				try {
					useAssetFeature = Boolean
							.parseBoolean((String) ConfigProperty.RTA_USE_ASSET_FEATURE.getValue(configuration));
				} catch (Exception e) {
				}

				if (useBatching) {
					if (storeProcessedFacts) {
						throw new RuntimeException("Store processed facts [true] not supported");

						// if (!useAssetLocking) {
						// clzNm =
						// "com.tibco.rta.service.metric.depr.BatchedParallelMetricServiceImpl";
						// } else {
						// clzNm =
						// "com.tibco.rta.service.metric.depr.BatchedParallelMetricServiceImpl2";
						// }
					} else {
						if (useAssetFeature) {
							clzNm = "com.tibco.rta.service.metric.AssetBasedJmsAckPolicyBatchedParallelMetricServiceImpl";
						} else {
							// this was never supported in the first place.
							// hence commenting out. the new implemenmtation
							// works.
							// clzNm =
							// "com.tibco.rta.service.metric.depr.JmsAckPolicyBatchedParallelMetricServiceImpl2";
							// clzNm =
							// "com.tibco.rta.service.metric.depr.JmsAckPolicyBatchedParallelMetricServiceImpl";
							throw new RuntimeException("Use Asset Locking [false] not supported");
						}
					}
				} else {
					// clzNm =
					// "com.tibco.rta.service.metric.depr.ParallelMetricServiceImpl";
					throw new RuntimeException("Use batching [false] not supported");
				}
			}
			metricsService = (MetricService) Class.forName(clzNm).newInstance();
		}
		return metricsService;
	}

	public TransportService getTransportService() throws Exception {
		if (transportService == null) {
			Class<?> transportServiceClazz = Class.forName("com.tibco.rta.service.transport.TransportServiceImpl");
			transportService = (TransportService) transportServiceClazz.newInstance();
		}
		return transportService;
	}

	public MetricIntrospectionService getMetricIntrospectionService() throws Exception {
		if (metricIntrospectionService == null) {
			Class<?> metricIntrospectionServiceClazz = Class
					.forName("com.tibco.rta.service.metric.MetricIntrospectionServiceImpl");
			metricIntrospectionService = (MetricIntrospectionService) metricIntrospectionServiceClazz.newInstance();
		}
		return metricIntrospectionService;
	}

	public QueryService getQueryService() throws Exception {
		if (queryService == null) {
			queryService = (QueryService) Class.forName("com.tibco.rta.query.QueryServiceImpl").newInstance();
		}
		return queryService;
	}

	public RuleService getRuleService() throws Exception {
		if (ruleService == null) {
			ruleService = (RuleService) Class.forName("com.tibco.rta.service.rules.impl.RuleServiceImpl").newInstance();
		}
		return ruleService;
	}

	public ClusterService getClusterService() throws Exception {
		if (clusterService == null) {
			clusterService = (ClusterService) Class.forName("com.tibco.rta.service.cluster.as.ASClusterService")
					.newInstance();
		}
		return clusterService;
	}

	public Properties getConfiguration() {
		return configuration;
	}

	public ObjectManager getObjectManager() throws Exception {
		if (objectManager == null) {
			objectManager = (ObjectManager) Class.forName("com.tibco.rta.service.om.DefaultObjectManager")
					.newInstance();
		}
		return objectManager;
	}

	/**
	 * Whether this engine has been enabled for FT.
	 *
	 * @return
	 */
	public boolean isFTEnabled() {
		return Boolean.parseBoolean((String) ConfigProperty.RTA_FT_ENABLED.getValue(configuration));
	}

	/**
	 * Whether this engine has been enabled for FT.
	 *
	 * @return
	 */
	public boolean isQueryEnabled() {
		return Boolean.parseBoolean((String) ConfigProperty.RTA_QUERY_ENGINE.getValue(configuration));
	}

	public PurgeService getPurgeService() throws Exception {
		if (purgeService == null) {
			purgeService = (PurgeService) Class.forName("com.tibco.rta.service.purge.impl.PurgeServiceImpl")
					.newInstance();
		}
		return purgeService;
	}

	public LockService getLockService() throws Exception {
		if (lockService == null) {
			lockService = (LockService) Class.forName("com.tibco.rta.common.service.impl.LockServiceImpl")
					.newInstance();
		}
		return lockService;
	}

	public ShutdownService getShutdownService() throws Exception {
		if (shutdownService == null) {
			shutdownService = (ShutdownService) Class.forName("com.tibco.rta.common.service.impl.ShutdownServiceExImpl")
					.newInstance();
		}
		return shutdownService;
	}

	public RuntimeService getRuntimeService() throws Exception {
		if (runtimeService == null) {
			runtimeService = (RuntimeService) Class.forName("com.tibco.rta.common.service.impl.RuntimeServiceImpl")
					.newInstance();
		}
		return runtimeService;
	}

	public HawkService getMicroagentService() throws Exception {
		if (microagentService == null) {
			microagentService = (HawkService) Class.forName("com.tibco.rta.hawk.ma.SPMMicroAgent").newInstance();
		}
		return microagentService;
	}
	
	public SnapshotRuleEvalService getSnapshotRuleService() throws Exception {
		if (snapshotRuleService == null) {
			snapshotRuleService = (SnapshotRuleEvalService) Class.forName("com.tibco.rta.service.rules.impl.SnapshotRuleEvalServiceImpl").newInstance();
		}
		return snapshotRuleService;
	}
}