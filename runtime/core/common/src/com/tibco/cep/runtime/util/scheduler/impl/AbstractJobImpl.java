package com.tibco.cep.runtime.util.scheduler.impl;

import com.tibco.cep.runtime.util.scheduler.Id;
import com.tibco.cep.runtime.util.scheduler.Job;
import com.tibco.cep.runtime.util.scheduler.JobContext;
import com.tibco.cep.runtime.util.scheduler.Scheduler;
import com.tibco.cep.runtime.util.scheduler.SchedulerException;


/**
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public abstract class AbstractJobImpl implements Job {
    // Scheduler
    private final Scheduler scheduler;
    private Id id;

    public AbstractJobImpl(Scheduler scheduler) {
        this.scheduler = scheduler;
        try {
            id = this.scheduler.registerJob(this);
        } catch (SchedulerException ex) {
            // Ignore this.
        }
    }

    @Override
    public void setId(Id id) {
        this.id = id;
    }

    @Override
    public Id getId() {
        return this.id;
    }

    @Override
    public abstract void execute(JobContext context);

}
