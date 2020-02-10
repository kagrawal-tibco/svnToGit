/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.graph.impl;

import java.util.HashSet;
import java.util.Set;

import com.tibco.be.monitor.thread.graph.Resource;
import com.tibco.be.monitor.thread.graph.Thread;
import com.tibco.be.monitor.thread.util.ThreadAnalyzerUtil;

/**
 *
 * @param <I> Resource Info
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
@SuppressWarnings("unchecked")
public class ResourceImpl<I> extends NodeImpl implements Resource {

    private final Set<Thread> users = new HashSet<Thread>();
    private Set<Thread> waiters = new HashSet<Thread>();
    
    private ResourceImpl(I info, Set<Thread> users,
            Set<Thread> waiters) {
        setUsers(users);
        setWaiters(waiters);
        this.info = info;
    }

    /**
     * Returns the resource for the given parameters
     * @param <I>
     * @param info
     * @param users
     * @param waiters
     * @return Resource
     */
    public static final <I> ResourceImpl<I> getResource(I info,
            Set<Thread> users, Set<Thread> waiters) {
        return new ResourceImpl<I>(info, users, waiters);
    }
    
    @Override
    public Set<Thread> getUsers() {
        return ThreadAnalyzerUtil.getUnmodifiableSet(this.users);
    }

    @Override
    public Set<Thread> getWaiters() {
        return ThreadAnalyzerUtil.getUnmodifiableSet(this.waiters);
    }

    /**
     *
     * @param user
     */
    public final void addUser(Thread user) {
        this.users.add(user);
    }

    /**
     *
     * @param users
     */
    public final void setUsers(Set<Thread> users) {
        this.users.clear();
        this.users.addAll(users);
    }

    /**
     *
     * @param waiter
     */
    public final void addWaiter(Thread waiter) {
        this.waiters.add(waiter);
    }

    /**
     *
     * @param waiters
     */
    public final void setWaiters(Set<Thread> waiters) {
        this.waiters.clear();
        this.waiters.addAll(waiters);
    }

    public String getPrintableString(String format) {
        if(format == null) {
            format = "| %-10s | %-3s | %-5d | %-7s |%n";
        }
        if(info == null) {
            return String.format("%n");
        }
        StringBuilder str = new StringBuilder();
        str.append(String.format(format,
                info.getClass().getName(), info.hashCode(),
                users.size(), waiters.size()));
        return str.toString();
    }

    @Override
    public String toString() {
        if(info == null) {
            return "";
        }
        return info.toString();
    }
}
