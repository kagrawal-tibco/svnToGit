package com.tibco.cep.loadbalancer.impl.client;

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
import com.tibco.cep.loadbalancer.client.ClientAdmin;
import com.tibco.cep.loadbalancer.impl.CommonConstants;
import com.tibco.cep.service.ScriptingService;

/*
* Author: Ashwin Jayaprakash / Date: Apr 14, 2010 / Time: 11:45:54 AM
*/

public class ClientMaster {
    protected static ClientMaster singleton;

    protected Configuration configuration;

    protected ResourceProvider resourceProvider;

    protected LoggerService loggerService;

    protected ScriptingService scriptingService;

    protected ClientAdmin clientAdmin;

    protected ClientMaster() {
    }

    public static synchronized ClientMaster init() throws LifecycleException {
        if (singleton == null) {
            singleton = new ClientMaster();
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

        clientAdmin = new DefaultClientAdmin(id);
        clientAdmin.start();
        resourceProvider.registerResource(ClientAdmin.class, clientAdmin);
    }

    public void stop() throws LifecycleException {
        clientAdmin.stop();

        scriptingService.stop();

        loggerService.stop();

        resourceProvider.discard();
    }
}
