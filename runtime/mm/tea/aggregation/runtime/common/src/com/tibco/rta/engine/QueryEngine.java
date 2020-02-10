package com.tibco.rta.engine;

import java.util.Properties;

import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.log.Level;
import com.tibco.rta.service.persistence.SessionPersistenceService;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 25/5/13
 * Time: 10:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class QueryEngine extends RtaEngine {

    /**
     * Session Persistence service for FT.
     */
    protected SessionPersistenceService sessionPersistenceService;

    @Override
    public void init(Properties configuration) throws Exception {
        this.configuration = configuration;

        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Initializing Metric Server..");
        }
        ServiceProviderManager providerManager = ServiceProviderManager.getInstance();
        providerManager.setConfiguration(configuration);

        //initialize the shutdown hook as one of the first activities.
        ServiceProviderManager.getInstance().getShutdownService().init(configuration);

        workItemService = providerManager.getWorkItemService();
        workItemService.init(configuration);


        //persistence layer
        persistenceService = providerManager.getPersistenceService();
        persistenceService.init(configuration);

         metricIntrospectionService = providerManager.getMetricIntrospectionService();
        metricIntrospectionService.init(configuration);

        //TODO Start GMP service
        groupMembershipService = providerManager.getGroupMembershipService();
        groupMembershipService.init(configuration);

        sessionPersistenceService = providerManager.getSessionPersistenceService();
        sessionPersistenceService.init(configuration);

        queryService = providerManager.getQueryService();
        queryService.init(configuration);

        objectManager = providerManager.getObjectManager();
        objectManager.init(configuration);


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

        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Initializing Query Server Complete.");
        }
    }

    @Override
    public void start() throws Exception {
        try {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Starting Query Server..");
            }


            try {
                workItemService.start();
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Error while starting WorkItem Service", e);
                throw e;
            }


            sessionPersistenceService.start();

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


//            try {
//                assetStatusService.start();
//            } catch (Exception e) {
//                LOGGER.log(Level.ERROR, "Error while starting AssetStatus Service", e);
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
    }
}
