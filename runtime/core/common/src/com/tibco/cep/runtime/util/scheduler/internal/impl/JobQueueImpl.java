/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.cep.runtime.util.scheduler.internal.impl;

import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.util.scheduler.Id;
import com.tibco.cep.runtime.util.scheduler.internal.CustomQueue;
import com.tibco.cep.runtime.util.scheduler.internal.Request;

/**
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public class JobQueueImpl implements CustomQueue<Request> {
    // logger
    private static final Logger logger = LogManagerFactory.getLogManager().getLogger(JobQueueImpl.class);

    // Stop flag
    private final AtomicBoolean stopFlag = new AtomicBoolean(false);
    // lookup map
    private final ConcurrentMap<Id, Request> lookupMap;
    // Internal queue
    private final PriorityBlockingQueue<Request> queue;

    public JobQueueImpl() {
        queue = new PriorityBlockingQueue<Request>(10, new RequestComparator());
        lookupMap = new ConcurrentHashMap<Id, Request>();
    }

    @Override
    public void addItem(Request req) {
        if (stopFlag.get() == true) {
            return;
        }
        Request request = lookupMap.putIfAbsent(req.getJobId(), req);
        if (request != null) {
            logger.log(Level.DEBUG, "Job-" + request.getJobId().getValue()
                    + ":Updating the request.");
            request.addRequest(req);
        } else {
            logger.log(Level.DEBUG, "Job-" + req.getJobId().getValue()
                    + ":Adding new request.");
            queue.offer(req);
        }
    }

    @Override
    public Request removeItem(Id jobId) {
        return removeRequest(jobId, true);
    }

    @Override
    public Request getItem() throws InterruptedException {
        Request req = queue.take();
        removeRequest(req);
        logger.log(Level.DEBUG, "Job-" + req.getJobId().getValue() + ":Returned to scheduler.Q Size:" +
                queue.size());
        return req;
    }

    @Override
    public void close() {
        stopFlag.set(true);
        queue.clear();
        lookupMap.clear();
    }

    @Override
    public boolean hasItem(Id jobId) {        
        return queue.contains(jobId);
    }

    private Request removeRequest(Id jobId, boolean removeFromQ) {
        Request req = lookupMap.remove(jobId);
        if (req != null) {
            logger.log(Level.DEBUG, "JobId-" + jobId.getValue() + ":Removing the entry.");
            if (removeFromQ == true) {
                queue.remove(req);
            }
            return req;
        } else {
            // No entry found in the map.
            logger.log(Level.DEBUG, "JobId-" + jobId.getValue() + " not found in the map.");
            return null;
        }
    }

    private void removeRequest(Request request) {
        Id jobId = request.getJobId();
        if (lookupMap.remove(jobId, request) != false) {
            logger.log(Level.DEBUG, "JobId-" + jobId.getValue() + ":Removing the entry.");
        } else {
            // No entry found in the map.
            logger.log(Level.DEBUG, "JobId-" + jobId.getValue() + " Entry not found in the map.");
        }
    }

    static class RequestComparator implements Comparator<Request> {

        public RequestComparator() {
        }

        public int compare(Request request_1, Request request_2) {
            if (request_1.getJobId().getValue() == request_2.getJobId().getValue()) {
                return 0;
            }
            long date = request_1.getOldestTimestamp();
            long otherDate = request_2.getOldestTimestamp();
            return (int) (date - otherDate);
        }
    }
}
