package com.tibco.rta.engine;

/**
 * @author bgokhale
 *
 * The Engine class
 *
 */

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.service.LockService;
import com.tibco.rta.common.service.RuntimeService;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.WorkItemService;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.common.service.session.ServerSessionRegistry;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.service.admin.AdminService;
import com.tibco.rta.service.cluster.ClusterService;
import com.tibco.rta.service.cluster.GroupMembershipService;
import com.tibco.rta.service.hawk.HawkService;
import com.tibco.rta.service.metric.MetricIntrospectionService;
import com.tibco.rta.service.metric.MetricService;
import com.tibco.rta.service.om.ObjectManager;
import com.tibco.rta.service.persistence.PersistenceService;
import com.tibco.rta.service.purge.PurgeService;
import com.tibco.rta.service.query.QueryService;
import com.tibco.rta.service.recovery.RecoveryService;
import com.tibco.rta.service.rules.RuleService;
import com.tibco.rta.service.rules.SnapshotRuleEvalService;
import com.tibco.rta.service.transport.TransportService;

import java.util.Properties;
import java.util.UUID;

public class RtaEngine extends AbstractStartStopServiceImpl {

    protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_RUNTIME_CONTAINER.getCategory());

    protected String engineName;

    protected ClusterService clusterService;

    protected TransportService transportService;

    protected PersistenceService persistenceService;

    protected AdminService adminService;

    protected MetricService metricService;

    protected MetricIntrospectionService metricIntrospectionService;

    protected WorkItemService workItemService;

    protected RuleService ruleService;

    protected QueryService queryService;

    protected HawkService microagentService;

    protected ObjectManager objectManager;

//    protected AssetStatusService assetStatusService;

    protected RecoveryService recoveryService;

    protected GroupMembershipService groupMembershipService;
    
    protected PurgeService purgeService;
    
    protected LockService lockService;
    
    protected RuntimeService runtimeService;
    
    protected SnapshotRuleEvalService snapshotRuleService;

    public RtaEngine() {
    	
    	//add a shutdown handler here.
    }


    @Override
    public void init(Properties configuration) throws Exception {
        super.init(configuration);

        engineName = "Engine-" + UUID.randomUUID().toString();
        //Add to config
        configuration.put(ConfigProperty.RTA_ENGINE_NAME.getPropertyName(), engineName);

        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Initializing Metric Server..");
        }
        ServiceProviderManager providerManager = ServiceProviderManager.getInstance();
        providerManager.setConfiguration(configuration);
        
        //initialize the shutdown hook as one of the first activities.
        ServiceProviderManager.getInstance().getShutdownService().init(configuration);

        boolean isHawkEnabled = Boolean.parseBoolean((String) ConfigProperty.RTA_HAWK_ENABLED.getValue(configuration));

        if (isHawkEnabled) {
            microagentService = providerManager.getMicroagentService();
            microagentService.init(configuration);
        }

        lockService = providerManager.getLockService();
        lockService.init(configuration);

        workItemService = providerManager.getWorkItemService();
        workItemService.init(configuration);

        //cluster service
        String type =(String) ConfigProperty.RTA_PERSISTENCE_PROVIDER.getValue(configuration);
        if (!"database".equals(type)) {
            clusterService = providerManager.getClusterService();
            clusterService.init(configuration);
        }
        //handles admin messages
        adminService = providerManager.getAdminService();
        adminService.init(configuration);

        //persistence layer
        persistenceService = providerManager.getPersistenceService();
        persistenceService.init(configuration);

        metricService = providerManager.getMetricsService();
        metricService.init(configuration);

        metricIntrospectionService = providerManager.getMetricIntrospectionService();
        metricIntrospectionService.init(configuration);

        //handles the transport layer
        transportService = providerManager.getTransportService();
        configuration.setProperty("shouldRecover", "true");
        transportService.init(configuration);

        ruleService = providerManager.getRuleService();
        ruleService.init(configuration);

        queryService = providerManager.getQueryService();
        queryService.init(configuration);

        objectManager = providerManager.getObjectManager();
        objectManager.init(configuration);

//        assetStatusService = providerManager.getPingService();
//        assetStatusService.init(configuration);

        groupMembershipService = providerManager.getGroupMembershipService();
        groupMembershipService.init(configuration);

        recoveryService = providerManager.getRecoveryService();
        recoveryService.init(configuration);
        
        purgeService = providerManager.getPurgeService();
        purgeService.init(configuration);
        
        runtimeService = providerManager.getRuntimeService();
        runtimeService.init(configuration);

        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Initializing Metric Server Complete.");
        }
    }

    @Override
	public void start() throws Exception {
        //By default this is primary
        //TODO signal primary somehow
//        groupMembershipService.signalPrimary();
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Starting Metric Server..");
		}
		
		if (lockService != null) {
			try {
				lockService.start();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, "Error while starting Lock Service", e);
				throw e;
			}
		}

		
		if (clusterService != null) {
			try {
				clusterService.start();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, "Error while starting Cluster Service", e);
				throw e;
			}
		}
		try {
			workItemService.start();
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Error while starting WorkItem Service", e);
			throw e;
		}

		try {
			adminService.start();
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Error while starting Admin Service", e);
			throw e;
		}

		try {
			persistenceService.start();
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Error while starting Persistence Service", e);
			throw e;
		}

		try {
			objectManager.start();
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Error while starting ObjectManager Service", e);
			throw e;
		}
//Bala: This service will be started upon "onActivation" See RuleServiceImpl, even for non-ft mode.
//		try {
//			ruleService.start();
//		} catch (Exception e) {
//			LOGGER.log(Level.ERROR, "Error while starting Rule Service", e);
//			throw e;
//		}
		

		try {
			queryService.start();
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Error while starting Query Service", e);
			throw e;
		}

		try {
			metricIntrospectionService.start();
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Error while starting MetricIntrospection Service", e);
			throw e;
		}
		
		
		if (clusterService == null || !clusterService.isCacheNode()) {

			// if its pure cache node, dont start some services.
			try {
				metricService.start();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, "Error while starting Metric Service", e);
				throw e;
			}
		}
		
//Bala: This service will be started upon "onActivation" See RecoveryServiceImpl, even for non-ft mode.
//      try {
//          recoveryService.start();
//      } catch (Exception e) {
//          LOGGER.log(Level.ERROR, "Error while starting Recovery Service", e);
//          throw e;
//      }

		
//		try {
//			assetStatusService.start();
//		} catch (Exception e) {
//			LOGGER.log(Level.ERROR, "Error while starting AssetStatus Service", e);
//			throw e;
//		}

//Bala: This service will be started upon "onActivation" See PurgeServiceImpl, even for non-ft mode.
//      try {
//          purgeService.start();
//      } catch (Exception e) {
//          LOGGER.log(Level.ERROR, "Error while starting Purge Service", e);
//          throw e;
//      }

		if (clusterService == null || !clusterService.isCacheNode()) {
			try {
				transportService.start();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, "Error while starting Transport Service", e);
				throw e;
			}
		}



		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Starting Metric Server Complete.");
		}
	}

//	private void startTest() {
//
//		try {
//			Runnable runnable = (Runnable) Class.forName("com.tibco.rta.query.Tester").newInstance();
//			runnable.run();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}


	public void stop() {
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Stopping RTEngine..");
		}

		try {
			if (metricService != null) {
				metricService.stop();
			}
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Error while stopping Metric Service", e);
		}
		
		try {
			if (lockService != null) {
				lockService.stop();
			}
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Error while stopping Lock Service", e);
		}
		
		try {
			if (transportService != null) {
				transportService.stop();
			}
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Error while stopping Transport Service", e);
		}
		
		try {
			ServerSessionRegistry.INSTANCE.stop();
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Error while stopping Server Session Registry", e);
		}

		try {
			if (adminService != null) {
				adminService.stop();
			}
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Error while stopping Admin Service", e);
		}
		try {
			if (ruleService != null) {
				ruleService.stop();
			}
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Error while stopping Rule Service", e);
		}
		try {
			if (queryService != null) {
				queryService.stop();
			}
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Error while stopping Query Service", e);
		}
		try {
			if (persistenceService != null) {
				persistenceService.stop();
			}
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Error while stopping Persistence Service", e);
		}
		try {
			if (objectManager != null) {
				objectManager.stop();
			}
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Error while stopping ObjectManager Service", e);
		}

		try {
			if (purgeService != null) {
				purgeService.stop();
			}
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Error while stopping Data Purge Service", e);
		}

		try {
			if (groupMembershipService != null) {
				groupMembershipService.stop();
			}
		}catch (Exception e) {
			LOGGER.log(Level.ERROR, "Error while stopping Group Membership Service", e);
		}
		
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Stopping RTEngine Complete.");
		}
	}
}
