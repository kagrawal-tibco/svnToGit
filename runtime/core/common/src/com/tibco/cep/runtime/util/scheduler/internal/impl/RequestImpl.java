/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.cep.runtime.util.scheduler.internal.impl;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;

import com.tibco.cep.runtime.util.scheduler.Id;
import com.tibco.cep.runtime.util.scheduler.internal.Request;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public class RequestImpl implements Request {

    private final Id jobId;
    private final SortedSet<Long> reqTimes = new ConcurrentSkipListSet<Long>();
    private final AtomicLong oldestTime;
    private final AtomicLong missedExec = new AtomicLong(0);
    private final long penaltyFactor = 20;
    private Type type;

    public RequestImpl(Id id) {
        this.jobId = id;
        long currTime = System.currentTimeMillis();
        oldestTime = new AtomicLong(currTime);
        this.reqTimes.add(currTime);
        this.type = Type.TRIGGERED;
    }

    public RequestImpl(Id id, long time) {
        this.jobId = id;
        this.oldestTime = new AtomicLong(time);
        this.reqTimes.add(time);
        this.type = Type.SCHEDULED;
    }

    public Id getJobId() {
        return this.jobId;
    }

    public void addRequest(Request request) {
        for(long reqTime : request.getRequestTimes()) {
            this.reqTimes.add(reqTime);
        }
        updateOldestTime(request.getOldestTimestamp());
        updateType(request);
    }

    public long getOldestTimestamp() {
        return this.oldestTime.get();
    }

    public void penalize() {
        // Check Missed execution count.
        long count = missedExec.incrementAndGet();
        // Each time a job misses execution, missedExec is incremented.
        // This missedExec is multiplied by penalty factor.
        // This calculated penalty time is added to the timestamp
        // This updated time stamp will push this job down the priority queue
        // Thus penalizing the job for slow run.
        long penalty = count * penaltyFactor;
        this.oldestTime.addAndGet(penalty);
    }

    public List<Long> getRequestTimes() {
        return Arrays.asList(this.reqTimes.toArray(new Long[0]));
    }

    public Type getType() {
        return type;
    }

    private void updateOldestTime(long time) {
        long currOldTime = this.oldestTime.get();
        while(true) {
            if(currOldTime > time) {
                if(this.oldestTime.compareAndSet(currOldTime, time) == true) {
                    break;
                } else {
                    currOldTime = this.oldestTime.get();
                }
            } else {
                break;
            }
        }
    }

    private void updateType(Request request) {
        if(request.getType() != type) {
            type = Type.BOTH;            
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RequestImpl other = (RequestImpl) obj;
        if (this.jobId != other.jobId) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        long hash = 3;
        hash = 79 * hash + this.jobId.hashCode();
        return (int) hash;
    }
}
