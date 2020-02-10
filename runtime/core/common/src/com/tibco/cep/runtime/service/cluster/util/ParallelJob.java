/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 26/8/2010
 */

package com.tibco.cep.runtime.service.cluster.util;


import java.util.ArrayList;
import java.util.Iterator;

public class ParallelJob {
    Runnable endJob;
    Runnable errorJob;
    java.util.ArrayList<Runnable> jobs = new ArrayList();
    volatile int num_completed = 0;

    public ParallelJob(Runnable endJob, Runnable errorJob) {
        this.endJob = endJob;
        this.errorJob = errorJob;

    }

    public void addJob(Runnable job) {
        jobs.add(new ParallelJobItem(job));
    }

    Iterator<Runnable> jobs() {
        return jobs.iterator();
    }

    void increment() {
        synchronized (jobs) {
            ++num_completed;
            if (num_completed == jobs.size()) {
                if (endJob != null) {
                    endJob.run();
                }
            }
        }
    }

    class ParallelJobItem implements Runnable {
        Runnable job;

        ParallelJobItem(Runnable job) {
            this.job = job;
        }

        public void run() {
            try {
                job.run();
            } catch (Throwable ex) {
                ex.printStackTrace();
            } finally {
                increment();
            }
        }
    }
}