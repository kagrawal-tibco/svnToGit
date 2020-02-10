package com.tibco.rta.engine;

import java.util.Properties;
import java.util.UUID;

import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.session.ServerSessionRegistry;
import com.tibco.rta.engine.RtaEngine;


/**
 * @author vdhumal
 *
 */
public class RtaEngineEx extends RtaEngine {
	
	private static final Logger LOGGER2 = LogManagerFactory.getLogManager().getLogger(RtaEngineEx.class);
	
    @Override
    public void init(Properties configuration) throws Exception {
    	this.configuration = configuration;

    	engineName = "Engine-" + UUID.randomUUID().toString();
        //Add to config
        configuration.put(ConfigProperty.RTA_ENGINE_NAME.getPropertyName(), engineName);

        if (LOGGER2.isEnabledFor(Level.INFO)) {
        	LOGGER2.log(Level.INFO, "Initializing Metric Server..");
        }
        ServiceProviderManager providerManager = ServiceProviderManager.getInstance();
        providerManager.setConfiguration(configuration);
        
        //initialize the shutdown hook as one of the first activities.
        //BALA: Moved to BETeaAgentManager..
//        ServiceProviderManager.getInstance().getShutdownService().init(configuration);

//Viraj : NR        
//        boolean isHawkEnabled = Boolean.parseBoolean((String) ConfigProperty.RTA_HAWK_ENABLED.getValue(configuration));
//        if (isHawkEnabled) {
//            microagentService = providerManager.getMicroagentService();
//            microagentService.init(configuration);
//        }

        lockService = providerManager.getLockService();
        lockService.init(configuration);

        workItemService = providerManager.getWorkItemService();
        workItemService.init(configuration);

//Viraj : NR        
        //cluster service
//        String type = (String) ConfigProperty.RTA_PERSISTENCE_PROVIDER.getValue(configuration);
//        if (!"database".equals(type)) {
//            clusterService = providerManager.getClusterService();
//            clusterService.init(configuration);
//        }
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

//Viraj : NR        
        //handles the transport layer
//        transportService = providerManager.getTransportService();
//        configuration.setProperty("shouldRecover", "true");
//        transportService.init(configuration);

        ruleService = providerManager.getRuleService();
        ruleService.init(configuration);

        snapshotRuleService = providerManager.getSnapshotRuleService();
        snapshotRuleService.init(configuration);
        
        queryService = providerManager.getQueryService();
        queryService.init(configuration);

        objectManager = providerManager.getObjectManager();
        objectManager.init(configuration);

//        assetStatusService = providerManager.getPingService();
//        assetStatusService.init(configuration);

//Viraj - NR        
//        groupMembershipService = providerManager.getGroupMembershipService();
//        groupMembershipService.init(configuration);

//Viraj - NR        
//        recoveryService = providerManager.getRecoveryService();
//        recoveryService.init(configuration);
        
        purgeService = providerManager.getPurgeService();
        purgeService.init(configuration);
        
        runtimeService = providerManager.getRuntimeService();
        runtimeService.init(configuration);

        if (LOGGER2.isEnabledFor(Level.INFO)) {
        	LOGGER2.log(Level.INFO, "Initializing Metric Server Complete.");
        }        
    }
    
    @Override
	public void start() throws Exception {
		if (LOGGER2.isEnabledFor(Level.INFO)) {
			LOGGER2.log(Level.INFO, "Starting Metric Server..");
		}
		
		if (lockService != null) {
			try {
				lockService.start();
			} catch (Exception e) {
				LOGGER2.log(Level.ERROR, "Error while starting Lock Service", e);
				throw e;
			}
		}
		
		if (clusterService != null) {
			try {
				clusterService.start();
			} catch (Exception e) {
				LOGGER2.log(Level.ERROR, "Error while starting Cluster Service", e);
				throw e;
			}
		}
		try {
			workItemService.start();
		} catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while starting WorkItem Service", e);
			throw e;
		}

		try {
			adminService.start();
		} catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while starting Admin Service", e);
			throw e;
		}

		try {
			persistenceService.start();
		} catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while starting Persistence Service", e);
			throw e;
		}

		try {
			objectManager.start();
		} catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while starting ObjectManager Service", e);
			throw e;
		}
		
		try {
			purgeService.start();
		} catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while starting purgeService Service", e);
			throw e;
		}

		try {
			ruleService.start();
		} catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while starting Rule Service", e);
			throw e;
		}
		
		try {
			snapshotRuleService.start();
		} catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while starting Snapshot Rule Service", e);
			throw e;
		}

		try {
			queryService.start();
		} catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while starting Query Service", e);
			throw e;
		}

		try {
			metricIntrospectionService.start();
		} catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while starting MetricIntrospection Service", e);
			throw e;
		}
		
		
		if (clusterService == null || !clusterService.isCacheNode()) {

			// if its pure cache node, dont start some services.
			try {
				metricService.start();
			} catch (Exception e) {
				LOGGER2.log(Level.ERROR, "Error while starting Metric Service", e);
				throw e;
			}
		}

//Viraj : NR		
//		if (clusterService == null || !clusterService.isCacheNode()) {
//			try {
//				transportService.start();
//			} catch (Exception e) {
//				LOGGER.log(Level.ERROR, "Error while starting Transport Service", e);
//				throw e;
//			}
//		}

		if (LOGGER2.isEnabledFor(Level.INFO)) {
			LOGGER2.log(Level.INFO, "Starting Metric Server Complete.");
		}
		setStarted(true);
	}    
    
    public void stop() {
		if (LOGGER2.isEnabledFor(Level.INFO)) {
			LOGGER2.log(Level.INFO, "Stopping RTEngine..");
		}

		try {
			if (metricService != null) {
				metricService.stop();
			}
		} catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while stopping Metric Service", e);
		}
		
		try {
			if (lockService != null) {
				lockService.stop();
			}
		} catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while stopping Lock Service", e);
		}
		
		/*try {
			if (transportService != null) {
				transportService.stop();
			}
		} catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while stopping Transport Service", e);
		}*/
		
		try {
			ServerSessionRegistry.INSTANCE.stop();
		} catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while stopping Server Session Registry", e);
		}

		try {
			if (adminService != null) {
				adminService.stop();
			}
		} catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while stopping Admin Service", e);
		}
		try {
			if (ruleService != null) {
				ruleService.stop();
			}
		} catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while stopping Rule Service", e);
		}
		
		try {
			if (snapshotRuleService != null) {
				snapshotRuleService.stop();
			}
		} catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while stopping Snapshot Rule Service", e);
		}
		
		try {
			if (queryService != null) {
				queryService.stop();
			}
		} catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while stopping Query Service", e);
		}
		try {
			if (persistenceService != null) {
				persistenceService.stop();
			}
		} catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while stopping Persistence Service", e);
		}
		try {
			if (objectManager != null) {
				objectManager.stop();
			}
		} catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while stopping ObjectManager Service", e);
		}

		try {
			if (purgeService != null) {
				purgeService.stop();
			}
		} catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while stopping Data Purge Service", e);
		}

		/*try {
			if (groupMembershipService != null) {
				groupMembershipService.stop();
			}
		}catch (Exception e) {
			LOGGER2.log(Level.ERROR, "Error while stopping Group Membership Service", e);
		}*/
		
		if (LOGGER2.isEnabledFor(Level.INFO)) {
			LOGGER2.log(Level.INFO, "Stopping RTEngine Complete.");
		}
		
		setStarted(false);
	}
}
