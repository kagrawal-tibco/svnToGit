package com.tibco.cep.loadbalancer.impl.server;

import static com.tibco.cep.util.Helper.$id;
import static com.tibco.cep.util.Helper.$processId;
import static com.tibco.cep.util.Helper.$uniqueMachineId;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.log.LoggerService;
import com.tibco.cep.common.resource.Configuration;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.impl.common.log.LogDelegatorService;
import com.tibco.cep.impl.common.resource.DefaultConfiguration;
import com.tibco.cep.impl.common.resource.DefaultResourceProvider;
import com.tibco.cep.loadbalancer.impl.CommonConstants;
import com.tibco.cep.loadbalancer.server.BeServerAdmin;
import com.tibco.cep.loadbalancer.server.ServerAdmin;
import com.tibco.cep.service.ScriptingService;

/*
* Author: Ashwin Jayaprakash / Date: Apr 13, 2010 / Time: 4:45:05 PM
*/

public class ServerMaster {
    protected static ServerMaster singleton;

    protected Configuration configuration;

    protected ResourceProvider resourceProvider;

    protected LoggerService loggerService;

    protected ScriptingService scriptingService;

    protected BeServerAdmin serverAdmin;

    public static synchronized ServerMaster init() throws LifecycleException {
        if (singleton == null) {
            singleton = new ServerMaster();
            singleton.start();
        }

        return singleton;
    }

    /**
     * @return true if {@link #init()} has already been called.
     */
    public static boolean isInited() {
        return singleton != null;
    }

    /**
     * {@link #init()} must be called once, in the beginning.
     *
     * @return
     */
    public static ResourceProvider getResourceProvider() {
        return singleton.resourceProvider;
    }

    protected void start() throws LifecycleException {
        resourceProvider = new DefaultResourceProvider();

        Id defaultConfigId = $id(Configuration.class.getSimpleName());
        configuration = new DefaultConfiguration(defaultConfigId, System.getProperties());
        resourceProvider.registerResource(Configuration.class, configuration);

        loggerService = new LogDelegatorService();
        loggerService.start();
        resourceProvider.registerResource(LoggerService.class, loggerService);

        try {
            Id scriptingServiceId = $id(ScriptingService.class.getSimpleName());

            Class klass = Class.forName("com.tibco.cep.impl.service.DefaultScriptingService");

            scriptingService = (ScriptingService) klass
                    .getConstructor(ResourceProvider.class, Id.class)
                    .newInstance(resourceProvider, scriptingServiceId);

            scriptingService.start();
            resourceProvider.registerResource(ScriptingService.class, scriptingService);
        }
        catch (Exception e) {
            throw new LifecycleException(e);
        }

        //-------------

        String processId = $processId();
        String machineId = $uniqueMachineId();
        Id id = $id(resourceProvider, CommonConstants.NAME_SERVICE, "machineid", machineId, "processid", processId);

        serverAdmin = new DefaultBeServerAdmin(id);
        serverAdmin.start();
        resourceProvider.registerResource(ServerAdmin.class, serverAdmin);
        resourceProvider.registerResource(BeServerAdmin.class, serverAdmin);
    }

    public void stop() throws LifecycleException {
        serverAdmin.stop();

        scriptingService.stop();

        loggerService.stop();

        resourceProvider.discard();
    }
}