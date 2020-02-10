/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 19/8/2010
 */

package com.tibco.cep.runtime.service.cluster.util;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 25, 2007
 * Time: 5:40:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkPool {
    ArrayList jobs = new ArrayList();
    int numberOfJobsCompleted = 0;
    boolean active = false;

    public WorkPool() {
    }


    public void addJob(Runnable job) {
        jobs.add(new Work(job));
    }

    public void submit(WorkManager workMgr) {
        if (!active) {
            workMgr.submitJobs(jobs);
            active = true;
        } else {
            throw new RuntimeException("WorkPool: Cannot add a job, pool already active");
        }
    }

    protected void increment() {
        synchronized (jobs) {
            ++numberOfJobsCompleted;
            jobs.notifyAll();
        }

    }

    public boolean hasException() {
        for (int i = 0; i < jobs.size(); i++) {
            Work wrk = (Work) jobs.get(i);
            if (wrk.hasException) {
                return true;
            }
        }
        return false;
    }

    public Exception getException() {
        Exception exception = new Exception("Work Failed ");
        ArrayList st = new ArrayList();
        int numStackTraces = 0;

        for (int i = 0; i < jobs.size(); i++) {
            Work wrk = (Work) jobs.get(i);
            if (wrk.hasException) {
                if (wrk.error != null) {
                    StackTraceElement[] sts = wrk.error.getStackTrace();
                    st.add(sts);
                    numStackTraces += sts.length;
                }
            }
        }
        StackTraceElement[] ret = new StackTraceElement[numStackTraces];
        int ctr = 0;
        for (int i = 0; i < st.size(); i++) {
            StackTraceElement[] sts = (StackTraceElement[]) st.get(i);
            for (int j = 0; j < sts.length; j++) {
                ret[ctr++] = sts[j];
            }
        }
        exception.setStackTrace(ret);
        return exception;
    }

    /**
     *
     */
    public void waitForCompletion() {
        synchronized (jobs) {
            while (true) {
                if (numberOfJobsCompleted == jobs.size()) {
                    active = false;
                    return;
                } else {
                    try {
                        jobs.wait();
                    } catch (InterruptedException iex) {
                        throw new RuntimeException(iex);
                    }
                }
            }
        }
    }

    public void reset() {
        numberOfJobsCompleted = 0;
        jobs.clear();
        active = false;
    }

    class Work implements Runnable {
        Runnable job;
        boolean hasException = false;
        Exception error;

        /**
         * @param job
         */
        public Work(Runnable job) {
            this.job = job;
        }

        /**
         *
         */
        public void run() {
            try {
                job.run();
            } catch (Exception ex) {
                hasException = true;
                error = ex;
            } finally {
                increment();
            }
        }
    }
}
