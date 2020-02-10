package com.tibco.cep.impl.service;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.log.LoggerService;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.impl.common.log.DefaultLoggerService;
import com.tibco.cep.service.Clock;
import com.tibco.cep.service.Service;
import com.tibco.cep.util.annotation.Optional;

/*
* Author: Ashwin Jayaprakash / Date: Jun 2, 2010 / Time: 3:15:30 PM
*/
public abstract class AbstractService implements Service {
    protected ResourceProvider resourceProvider;

    protected DefaultClock clock;

    protected Id resourceId;

    @Optional
    protected LoggerService loggerServiceWeCreated;

    public AbstractService(ResourceProvider resourceProvider, Id resourceId) {
        this.resourceProvider = resourceProvider;
        this.resourceId = resourceId;
    }

    @Override
    public Id getResourceId() {
        return resourceId;
    }

    //------------

    public void start() throws LifecycleException {
        LoggerService loggerService = resourceProvider.fetchResource(LoggerService.class);
        if (loggerService == null) {
            loggerService = new DefaultLoggerService();
            loggerService.start();

            resourceProvider.registerResource(LoggerService.class, loggerService);

            loggerServiceWeCreated = loggerService;
        }

        clock = new DefaultClock();
        clock.start();
        resourceProvider.registerResource(Clock.class, clock);
    }

    public void stop() throws LifecycleException {
        clock.stop();
        resourceProvider.unregisterResource(Clock.class);

        if (loggerServiceWeCreated != null) {
            loggerServiceWeCreated.stop();
            resourceProvider.unregisterResource(LoggerService.class);
        }
    }

    @Override
    public AbstractService recover(ResourceProvider resourceProvider, Object... params) throws RecoveryException {
        return this;
    }
}
