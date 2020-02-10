/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread.analyzer.impl;

import java.lang.management.LockInfo;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.be.monitor.thread.graph.Graph;
import com.tibco.be.monitor.thread.graph.Node;
import com.tibco.be.monitor.thread.graph.Resource;
import com.tibco.be.monitor.thread.graph.Thread;
import com.tibco.be.monitor.thread.graph.impl.GraphImpl;
import com.tibco.be.monitor.thread.graph.impl.ResourceImpl;
import com.tibco.be.monitor.thread.graph.impl.ThreadImpl;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
class GraphBuilder {

    // Declare internal tables.
    Map<String, Resource> locks = new HashMap<String, Resource>();
    Map<String, Resource> monitors = new HashMap<String, Resource>();
    Map<Long, ThreadImpl> threads = new HashMap<Long, ThreadImpl>();
    Map<String, Set<ThreadImpl>> owners =
            new HashMap<String, Set<ThreadImpl>>();
    Map<Long, Set<Resource>> resourceOwned = new HashMap<Long, Set<Resource>>();
    Map<Long, Resource> dependencies = new HashMap<Long, Resource>();
    // Auxilary data structures used to return result
    Map<String, Node> vertexMap = new HashMap<String, Node>();
    Map<String, ResourceImpl> resourceMap = new HashMap<String, ResourceImpl>();

    private GraphBuilder(ThreadInfo[] tInfos) {
        buildGraph(tInfos);
    }

    /**
     * Returns an array of graphs that are built from the thread dump.
     * @param tInfos
     * @return array of graphs.
     */
    public static final Graph[] buildGraphs(ThreadInfo[] tInfos) {
        GraphBuilder builder = new GraphBuilder(tInfos);
        return builder.getGraphs();
    }

    private Graph[] getGraphs() {
        Set<Graph> graphs = new HashSet<Graph>();
        List<Thread> threadList = new LinkedList<Thread>(threads.values());
        while (threadList.size() != 0) {
            Thread thread = threadList.get(0);
            Set<Node> nodes = new HashSet<Node>();
            traverseGraph(nodes, thread);
            graphs.add(GraphImpl.getGraph(nodes));
            threadList.removeAll(nodes);
        }
        return graphs.toArray(new Graph[0]);
    }

    private void buildGraph(ThreadInfo[] tInfos) {
        // Initialize threads and resources.
        for (ThreadInfo tInfo : tInfos) {
            initThreadX(tInfo);
        }
        // resolve dependencies.
        for (ThreadInfo tInfo : tInfos) {
            resolveDependencies(tInfo);
        }
    }

    private ThreadImpl getThreadX(ThreadInfo tInfo) {
        ThreadImpl threadX = threads.get(tInfo.getThreadId());
        if (threadX == null) {
            threadX = ThreadImpl.getThreadX(tInfo, null, 
                    new HashSet<Resource>());
            threads.put(tInfo.getThreadId(), threadX);
            vertexMap.put(String.valueOf(tInfo.getThreadId()), threadX);
        } else {
            threadX.setInfo(tInfo);
        }
        return threadX;
    }

    private ThreadImpl getThreadX(long threadId) {
        ThreadImpl threadX = threads.get(threadId);
        if (threadX == null) {
            threadX = ThreadImpl.getThreadX(null, null,
                    new HashSet<Resource>());
            threads.put(threadId, threadX);
            vertexMap.put(String.valueOf(threadId), threadX);
        }
        return threadX;
    }

    private <I> ResourceImpl<I> getResource(I info) {
        return getResource(info, new HashSet<Thread>(),
                new HashSet<Thread>());
    }

    @SuppressWarnings("unchecked")
    private <I> ResourceImpl<I> getResource(I info, Set<Thread> users,
            Set<Thread> waiters) {
        ResourceImpl<I> res = (ResourceImpl<I>)resourceMap.get(
                info.toString());
        if (res == null) {
            res = ResourceImpl.getResource(info, users, waiters);
            resourceMap.put(info.toString(), res);
            vertexMap.put(info.toString(), res);
        }
        return res;
    }

    private void initThreadX(ThreadInfo tInfo) {
        // Get the threadX.
        ThreadImpl threadX = getThreadX(tInfo);
        // Init all locks owned by this threadinfo.
        for (LockInfo lInfo : tInfo.getLockedSynchronizers()) {
            ResourceImpl<LockInfo> lInfoRes = getResource(lInfo);
            lInfoRes.addUser(threadX);
            threadX.addResourceUsed(lInfoRes);
        }
        // Init all monitors owned by this threadinfo.
        for (MonitorInfo mInfo : tInfo.getLockedMonitors()) {
            ResourceImpl<MonitorInfo> mInfoRes = getResource(mInfo);
            mInfoRes.addUser(threadX);
            threadX.addResourceUsed(mInfoRes);
        }
    }

    private void traverseGraph(Set<Node> nodes, Thread thread) {
        if (nodes.contains(thread)) {
            return;
        }
        nodes.add(thread);
        for (Resource resource : thread.getResourcesUsed()) {
            traverseGraph(nodes, resource);
        }
        if (thread.getWaitsOn() != null) {
            traverseGraph(nodes, thread.getWaitsOn());
        }
    }

    private void traverseGraph(Set<Node> nodes, Resource resource) {
        if (nodes.contains(resource)) {
            return;
        }
        nodes.add(resource);
        Set<Thread> users = resource.getUsers();
        for (Thread thread : users) {
            traverseGraph(nodes, thread);
        }
        Set<Thread> waiters = resource.getWaiters();
        for (Thread thread : waiters) {
            traverseGraph(nodes, thread);
        }
    }

    private void resolveDependencies(ThreadInfo tInfo) {
        // Get the threadX.
        ThreadImpl threadX = getThreadX(tInfo);
        // Resolve dependencies.
        if (tInfo.getLockInfo() != null) {
            ResourceImpl<LockInfo> res = getResource(tInfo.getLockInfo());
            // check if the resource owner is VM Thread.
            if (tInfo.getLockOwnerId() == -1L) {
                // Make the current thread owner of the resource.
                threadX.addResourceUsed(res);
                // Add current Thread as resource user.
                res.addUser(threadX);
            } else {
                // Initialize the lock owner thread.
                ThreadImpl resOwner = getThreadX(tInfo.getLockOwnerId());
                // check if the thread info is available,
                // else make the current thread as the owner of the resource.
                if (resOwner.getInfo() == null) {
                    // Make the current thread owner of the resource.
                    threadX.addResourceUsed(res);
                    // Add current Thread as resource user.
                    res.addUser(threadX);
                } else {
                    // Add lock waiter.
                    res.addWaiter(threadX);
                    // Add this resource as blocking resource.
                    threadX.setWaitsOn(res);
                    // Add resource user
                    res.addUser(resOwner);
                }
            }
        }
    }
}
