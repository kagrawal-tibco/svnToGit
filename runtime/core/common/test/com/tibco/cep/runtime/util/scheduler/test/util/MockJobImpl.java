package com.tibco.cep.runtime.util.scheduler.test.util;

import com.tibco.cep.runtime.util.scheduler.*;
import com.tibco.cep.runtime.util.scheduler.impl.AbstractJobImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public class MockJobImpl extends AbstractJobImpl implements Job {
    // logger
    private static final Logger logger = Logger.getLogger(MockJobImpl.class.getName());
    // Scheduler
    private final Scheduler scheduler;
    // input queue
    private final BlockingQueue<Object> inputQueue = new LinkedBlockingQueue<Object>();
    private Id id;
    private static final short DRAIN_LIMIT = 1024;
    // run slow
    private final byte[] lock = new byte[0];
    private final long waitTime;

    public MockJobImpl(Scheduler scheduler, long waitTime) {
        super(scheduler);
        this.scheduler = scheduler;
        this.waitTime = waitTime;
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
    public void execute(JobContext context) {
        // Pickup a maximum of 1024 input items and execute.
        List<Object> inputs = new LinkedList<Object>();
        inputQueue.drainTo(inputs, DRAIN_LIMIT);
        processInputs(inputs);
        int size = inputQueue.size();
        // Still more events in the queue. Ask for scheduling.
        if(size != 0) {
            try {
                scheduler.scheduleNow(this.id);
            } catch (SchedulerException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    public void queueInput(Object input) {
        inputQueue.offer(input);
        try {
            // Request new schedule from the scheduler.
            scheduler.scheduleNow(id);
        } catch (SchedulerException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    private void processInputs(List<Object> inputs) {
        for (Object inputObj : inputs) {
            // dummy processing.
            logger.log(Level.INFO, id.getValue() + "\tprocessing " + inputs.size() +
                    " inputs.Result:" + processInput(inputObj));
            if(waitTime > 0) {
                synchronized(lock) {
                    try {
                        lock.wait(waitTime);
                    } catch (InterruptedException ex) {
                        // do nothing.
                    }
                }
            }
        }
    }

    private long processInput(Object input) {
        int x = 0;
        // do something here.
        for (int i = 0; i < 10000; i++) {
            x += (x + i * 5) - 2 + input.hashCode();
        }
        return x;
    }
}
