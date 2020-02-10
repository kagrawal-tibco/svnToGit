/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.graph.impl;

import java.lang.management.ThreadInfo;
import java.util.HashSet;
import java.util.Set;

import com.tibco.be.monitor.thread.graph.Resource;
import com.tibco.be.monitor.thread.graph.Thread;
import com.tibco.be.monitor.thread.util.ThreadAnalyzerUtil;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
public class ThreadImpl extends NodeImpl<ThreadInfo> implements Thread {

    private Resource waitsOn;
    private Set<Resource> resourcesUsed = new HashSet<Resource>();
    private ThreadImpl(ThreadInfo info, Resource waitsOn,
            Set<Resource> resourcesUsed) {
        this.info = info;
        this.waitsOn = waitsOn;
        setResourcesUsed(resourcesUsed);
    }

    /**
     * Returns the threadX for the given parameters.
     * @param info
     * @param waitsOn
     * @param resourcesUsed
     * @return threadX
     */
    public static final ThreadImpl getThreadX(ThreadInfo info, Resource waitsOn,
            Set<Resource> resourcesUsed) {
        return new ThreadImpl(info, waitsOn, resourcesUsed);
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        if(this.info == null) {
            return new StackTraceElement[0];
        }
        return this.info.getStackTrace();
    }

    @Override
    public Set<Resource> getResourcesUsed() {
        return ThreadAnalyzerUtil.getUnmodifiableSet(resourcesUsed);
    }

    @Override
    public Resource getWaitsOn() {
        return this.waitsOn;
    }

    /**
     * Returns true if the thread is waiting on itself.
     * @return selfWait
     */
    public boolean isWaitingOnItself() {
        if(waitsOn != null) {
            Set<Thread> users = waitsOn.getUsers();
            for(Thread thread : users) {
                if(thread.equals(this)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param resourcesUsed
     */
    public void setResourcesUsed(Set<Resource> resourcesUsed) {
        this.resourcesUsed.clear();
        if(resourcesUsed != null) {
            this.resourcesUsed.addAll(resourcesUsed);
        } 
    }

    /**
     *
     * @param resource
     */
    public void addResourceUsed(Resource resource) {
        this.resourcesUsed.add(resource);
    }

    /**
     * Set the resource for which this thread is waiting for.
     * @param waitsOn resource for which the thread is waiting for.
     */
    public void setWaitsOn(Resource waitsOn) {
        this.waitsOn = waitsOn;
    }

    /**
     * Set the info for the Thread
     * @param info threadX info
     */
    public void setInfo(ThreadInfo info) {
        this.info = info;
    }

    @Override
    public String toString() {
        if(info == null) {
            return "";
        }
        return "Id:" + info.getThreadId() +
                "\tName:" + info.getThreadName();
    }

    public String getPrintableString(String format) {
        if(info == null) {
            return "";
        }
        if(format == null) {
            format = "| %-5s | %-14s | %-10s |%n";
        }
        return String.format(format, info.getThreadId(),
                info.getThreadState(), info.getThreadName());
    }
}
