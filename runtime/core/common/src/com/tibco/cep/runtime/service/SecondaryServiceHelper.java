/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 */

package com.tibco.cep.runtime.service;

import com.tibco.cep.runtime.management.ManagementCentral;
import com.tibco.cep.runtime.management.impl.DefaultManagementCentral;
import com.tibco.cep.runtime.metrics.StopWatchManager;
import com.tibco.cep.runtime.metrics.impl.DefaultStopWatchManager;
import com.tibco.cep.runtime.service.time.DefaultHeartbeatService;
import com.tibco.cep.runtime.service.time.HeartbeatService;

/*
* Author: Ashwin Jayaprakash Date: Jan 26, 2009 Time: 11:39:04 AM
*/
public class SecondaryServiceHelper {
    static void init() {
        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();

        try {
            initHeartbeatService(registry);

            initStopWatch(registry);

            initManagementCentral(registry);

            initCacheProviderService(registry);
        }
        catch (Exception e) {
            new RuntimeException("Error occurred while initializing secondary services", e)
                    .printStackTrace();
        }
    }

    private static void initManagementCentral(ServiceRegistry registry) throws Exception {
        DefaultManagementCentral managementCentral = new DefaultManagementCentral();
        managementCentral.init(registry.getConfiguration());
        registry.registerService(ManagementCentral.class, managementCentral);

        managementCentral.start();
    }

    private static void initStopWatch(ServiceRegistry registry) throws Exception {
        StopWatchManager stopWatchManager = new DefaultStopWatchManager();
        stopWatchManager.init(registry.getConfiguration());
        registry.registerService(StopWatchManager.class, stopWatchManager);

        stopWatchManager.start();
    }

    private static void initHeartbeatService(ServiceRegistry registry) throws Exception {
        HeartbeatService heartbeatService = new DefaultHeartbeatService();
        heartbeatService.init(registry.getConfiguration());
        registry.registerService(HeartbeatService.class, heartbeatService);

        heartbeatService.start();
    }

    private static void initCacheProviderService(ServiceRegistry registry) throws Exception {
        //Suresh TODO : Check with Ashwin : Commented below @9/26/2010
//        CacheProviderService cacheService = new CacheProviderService();
//        cacheService.init(registry.getConfiguration());
//        registry.registerService(CacheProviderService.class, cacheService);

        //cacheService.start();
    }
}
