package com.tibco.rta.engine;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.log.Level;
import com.tibco.rta.service.persistence.SessionPersistenceService;

import java.util.Properties;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/4/13
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class FTEnabledRtaEngine extends RtaEngine {


    /**
     * Session Persistence service for FT.
     */
    protected SessionPersistenceService sessionPersistenceService;

    @Override
    public void init(Properties configuration) throws Exception {
        this.configuration = configuration;

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
        String type = (String) ConfigProperty.RTA_PERSISTENCE_PROVIDER.getValue(configuration);
        if ("tibas".equals(type)) {
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

        //TODO Start GMP service
        groupMembershipService = providerManager.getGroupMembershipService();
        groupMembershipService.init(configuration);

        sessionPersistenceService = providerManager.getSessionPersistenceService();
        sessionPersistenceService.init(configuration);

        ruleService = providerManager.getRuleService();
        ruleService.init(configuration);

        queryService = providerManager.getQueryService();
        queryService.init(configuration);

        objectManager = providerManager.getObjectManager();
        objectManager.init(configuration);

//        assetStatusService = providerManager.getPingService();
//        assetStatusService.init(configuration);

        if ((clusterService == null || !clusterService.isCacheNode())) {
            //Attach transport
            transportService = ServiceProviderManager.getInstance().getTransportService();
            //TODO uncomment this later
            //                if (primaryMember != null) {
            //                    configuration.setProperty("shouldRecover", "false");
            //                } else {
            //                    configuration.setProperty("shouldRecover", "true");
            //                }
            configuration.setProperty("shouldRecover", "true");
            transportService.init(configuration);
        }

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
        try {
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

//            groupMembershipService.start();
//            groupMembershipService.subscribe();
            try {
                sessionPersistenceService.start();
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Error while starting Session Persistence Service", e);
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

//Bala: This service will be started upon "onActivation" See RuleServiceImpl
//            try {
//                ruleService.start();
//            } catch (Exception e) {
//                LOGGER.log(Level.ERROR, "Error while starting Rule Service", e);
//                throw e;
//            }

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

            //See if primary is elected
            //TODO uncomment this later
//            GroupMember primaryMember = groupMembershipService.getPrimaryMember();
//            GroupMember primaryMember = null;
            if ((clusterService == null || !clusterService.isCacheNode())) {
                // if its pure cache node, dont start some services.
                try {
                    metricService.start();
                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, "Error while starting Metric Service", e);
                    throw e;
                }
            }

//Bala: This service will be started upon "onActivation" See RecoveryServiceImpl
//            try {
//                recoveryService.start();
//            } catch (Exception e) {
//                LOGGER.log(Level.ERROR, "Error while starting Recovery Service", e);
//                throw e;
//            }

//            try {
//                assetStatusService.start();
//            } catch (Exception e) {
//                LOGGER.log(Level.ERROR, "Error while starting AssetStatus Service", e);
//                throw e;
//            }


//Bala: This service will be started upon "onActivation" See PurgeServiceImpl
//            try {
//                purgeService.start();
//            } catch (Exception e) {
//                LOGGER.log(Level.ERROR, "Error while starting Purge Service", e);
//                throw e;
//            }

            if ((clusterService == null || !clusterService.isCacheNode())) {
                transportService.start();
            }


            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Starting Metric Server Complete.");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    @Override
    public void stop() {
        super.stop();
//        try {
//            groupMembershipService.stop();
//        } catch (Exception e) {
//            LOGGER.log(Level.ERROR, "", e);
//        }
    }
}
