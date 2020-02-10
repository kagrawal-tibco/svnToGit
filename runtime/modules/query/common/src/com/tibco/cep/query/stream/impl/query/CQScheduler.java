package com.tibco.cep.query.stream.impl.query;

import java.util.Properties;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.service.QueryLogger;
import com.tibco.cep.query.service.QueryProperty;
import com.tibco.cep.query.stream.core.Component;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.runtime.util.scheduler.Id;
import com.tibco.cep.runtime.util.scheduler.Scheduler;
import com.tibco.cep.runtime.util.scheduler.impl.ParametersImpl;
import com.tibco.cep.runtime.util.scheduler.impl.SchedulerImpl;

/*
* Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:35:46 PM
*/
public class CQScheduler implements Component {
    private final Logger logger;
    private final ResourceId id;
    // scheduler
    private final Scheduler scheduler;
    private final ParametersImpl parameters = new ParametersImpl();
    public CQScheduler() {
        Registry.getInstance().register(CQScheduler.class, this);
        scheduler = new SchedulerImpl();
        id = new ResourceId(CQScheduler.class.getName());
        logger = new QueryLogger(CQScheduler.class.getName());
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

    @Override
    public void init(Properties properties) throws Exception {
        String maxThreadsStr = properties.getProperty(
                QueryProperty.CONTINUOUS_QUERY_MAX_THREADS.getPropName(), "20");
        int maxThreads = -1;
        if(maxThreadsStr != null) {
            try {
                maxThreads = Integer.parseInt(maxThreadsStr);
            } catch (NumberFormatException e) {
                // Ignore this and use the default value for max threads.
            }
        }

        parameters.setMaxThreads(maxThreads);
        parameters.setMinThreads(maxThreads);
        parameters.setName("CQ-Sched");
    }

    @Override
    public void discard() throws Exception {
        // Do nothing
    }

    @Override
    public void start() throws Exception {
        logger.log(Level.DEBUG, "Starting CQ Scheduler");
        this.scheduler.start(parameters);
    }

    @Override
    public void stop() throws Exception {
        logger.log(Level.DEBUG, "Stopping CQ Scheduler");
        for(Id jobId : this.scheduler.getRegisteredJobIds()) {
            this.scheduler.unregisterJob(jobId);
        }
        this.scheduler.stop();
    }

    @Override
    public ResourceId getResourceId() {
        return id;
    }
}
