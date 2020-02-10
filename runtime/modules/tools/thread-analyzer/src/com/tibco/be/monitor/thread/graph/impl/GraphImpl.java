/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.graph.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.tibco.be.monitor.thread.graph.Graph;
import com.tibco.be.monitor.thread.graph.Node;
import com.tibco.be.monitor.thread.graph.Resource;
import com.tibco.be.monitor.thread.graph.Thread;
import com.tibco.be.monitor.thread.graph.comparator.BlockedCountComparator;
import com.tibco.be.monitor.thread.graph.comparator.BlockedTimeComparator;
import com.tibco.be.monitor.thread.graph.comparator.DependencyComparator;
import com.tibco.be.monitor.thread.graph.visitor.DeadlockVisitor;
import com.tibco.be.monitor.thread.graph.visitor.impl.DeadlockVisitorImpl;
import com.tibco.be.monitor.thread.util.ThreadAnalyzerUtil;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
public class GraphImpl implements Graph {

    private final Set<Node> nodes;
    private DeadlockVisitor deadlockVisitor;
    private boolean isDeadlockGraph = false;
    private GraphImpl(Set<Node> nodes) {
        this.nodes = nodes;
    }

    private GraphImpl(Set<Node> nodes, boolean isDeadlock) {
        this.nodes = nodes;
        this.isDeadlockGraph = isDeadlock;
    }

    /**
     * Factory method used to create Graph object.
     * @param nodes nodes in the graph.
     * @return graph 
     */
    public static final GraphImpl getGraph(Set<Node> nodes) {
        return new GraphImpl(nodes);
    }

    /**
     * Factory method used to create Graph object.
     * @param nodes nodes in the graph.
     * @param isDeadlock boolean specifying if the graph is a deadlock graph.
     * @return graph
     */
    public static final GraphImpl getGraph(Set<Node> nodes, boolean isDeadlock) {
        return new GraphImpl(nodes, isDeadlock);
    }

    @Override
    public Set<Graph> findDeadlocks() {
        if(isDeadlockGraph) {
            Set<Graph> result = new HashSet<Graph>();
            result.add(this);
            return result;
        }
        if(deadlockVisitor == null) {
            deadlockVisitor = DeadlockVisitorImpl.getDeadlockVisitor(this);
        }
        return deadlockVisitor.getDeadlocks();
    }

    @Override
    public Set<Graph> findBottlenecks() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isCyclic() {
        if(isDeadlockGraph) {
            return true;
        }
        if(deadlockVisitor == null) {
            deadlockVisitor = DeadlockVisitorImpl.getDeadlockVisitor(this);
        }
        return deadlockVisitor.hasCycles();
    }

    @Override
    public Set<Thread> getStartNodes() {
        Set<Thread> threads = getThreadXSet();
        Set<Thread> result = new HashSet<Thread>();
        for(Thread thread : threads) {
            if(thread.getWaitsOn() != null) {
                result.add(thread);
            }
        }
        return ThreadAnalyzerUtil.getUnmodifiableSet(result);
    }

    @Override
    public Set<Node> getAllNodes() {
        return ThreadAnalyzerUtil.getUnmodifiableSet(nodes);
    }

    @Override
    public Set<Thread> getThreadNodes() {
        return ThreadAnalyzerUtil.getUnmodifiableSet(getThreadXSet());
    }

    @Override
    public Set<Resource> getResourceNodes() {
        return ThreadAnalyzerUtil.getUnmodifiableSet(getResourceSet());
    }

    @Override
    public Iterator<? extends Node> iterator(SortKey key, SortOrder order) {
        Thread[] threads;
        Node[] nodeArray;
        Comparator<Thread> threadXComparator;
        Comparator<Node> nodeComparator;
        switch(key) {
            case BLOCK_COUNT:
                threads = getThreadXSet().toArray(new Thread[0]);
                if(order == SortOrder.ASCENDING) {
                    threadXComparator =
                            BlockedCountComparator.getComparator(true);
                } else {
                    threadXComparator =
                            BlockedCountComparator.getComparator(false);
                }
                Arrays.sort(threads, threadXComparator);
                return Arrays.asList(threads).iterator();
            case BLOCK_TIME:
                threads = getThreadXSet().toArray(new Thread[0]);
                if(order == SortOrder.ASCENDING) {
                    threadXComparator =
                            BlockedTimeComparator.getComparator(true);
                } else {
                    threadXComparator =
                            BlockedTimeComparator.getComparator(false);
                }
                Arrays.sort(threads, threadXComparator);
                return Arrays.asList(threads).iterator();
            case DEPENDENCY:
                nodeArray = nodes.toArray(new Node[0]);
                if(order == SortOrder.ASCENDING) {
                    nodeComparator =
                            DependencyComparator.getComparator(true);
                } else {
                    nodeComparator =
                            DependencyComparator.getComparator(false);
                }
                Arrays.sort(nodeArray, nodeComparator);
                return Arrays.asList(nodeArray).iterator();
        }
        return Collections.<Node>emptyList().iterator();
    }

    private Set<Thread> getThreadXSet() {
        Set<Thread> threads = new HashSet<Thread>();
        for(Node node : nodes) {
            if(node instanceof Thread) {
                threads.add((Thread)node);
            }
        }
        return threads;        
    }

    private Set<Resource> getResourceSet() {
        Set<Resource> resources = new HashSet<Resource>();
        for(Node node : nodes) {
            if(node instanceof Resource) {
                resources.add((Resource)node);
            }
        }
        return resources;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GraphImpl other = (GraphImpl) obj;
        if (this.nodes != other.nodes &&
                (this.nodes == null || !this.nodes.equals(other.nodes))) {
            return false;
        }
        if(this.nodes.size() != other.nodes.size()) {
            return false;
        }
        if(!this.nodes.containsAll(other.nodes)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash +
                (this.nodes != null ? this.nodes.hashCode() : 0);
        return hash;
    }
}
