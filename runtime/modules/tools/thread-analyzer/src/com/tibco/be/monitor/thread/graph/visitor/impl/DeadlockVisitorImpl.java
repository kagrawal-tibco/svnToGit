/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread.graph.visitor.impl;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import com.tibco.be.monitor.thread.graph.Graph;
import com.tibco.be.monitor.thread.graph.Node;
import com.tibco.be.monitor.thread.graph.Thread;
import com.tibco.be.monitor.thread.graph.impl.GraphImpl;
import com.tibco.be.monitor.thread.graph.impl.ResourceImpl;
import com.tibco.be.monitor.thread.graph.impl.ThreadImpl;
import com.tibco.be.monitor.thread.graph.visitor.DeadlockVisitor;
import com.tibco.be.monitor.thread.util.ThreadAnalyzerUtil;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
@SuppressWarnings("unchecked")
public class DeadlockVisitorImpl extends BaseVisitor implements DeadlockVisitor {
    private static final Logger logger = Logger.getLogger(
            DeadlockVisitorImpl.class.getName());
    private final GraphImpl graph;
    private final Set<ThreadImpl> visitedThreads =
            new HashSet<ThreadImpl>();
    private final Set<ResourceImpl> visitedResources =
            new HashSet<ResourceImpl>();
    private boolean hasCycle = false;
    private Deque<Node> stack = new ArrayDeque<Node>();
    private final Set<Graph> deadlocks = new HashSet<Graph>();

    private DeadlockVisitorImpl(GraphImpl graph) {
        this.graph = graph;
        visitNodes();
    }

    /**
     * Returns the DeadlockVisitorImpl for the given graph.
     * @param graph
     * @return deadlockvisitor
     */
    public static final DeadlockVisitor getDeadlockVisitor(GraphImpl graph) {
        return new DeadlockVisitorImpl(graph);
    }

    /**
     * Returns true if the graph has any cycles.
     * @return boolean specifying graph cycles.
     */
    @Override
    public boolean hasCycles() {
        return hasCycle;
    }

    /**
     * Returns the deadlocks found in the graph.
     * @return deadlocks
     */
    @Override
    public Set<Graph> getDeadlocks() {
        return ThreadAnalyzerUtil.getUnmodifiableSet(deadlocks);
    }

    private void visitNodes() {
        long time = System.currentTimeMillis();
        logger.fine("Visiting all nodes in the graph.");
        for (Thread thread : this.graph.getThreadNodes()) {
            ((ThreadImpl) thread).accept(this);
        }
        long endTime = System.currentTimeMillis();
        logger.fine("Time taken for visiting all nodes:"
                + (endTime - time) + " ms");
    }

    @Override
    protected void visitNode(ThreadImpl node) {
        if (visitedThreads.contains(node)) {
            handleCycle();
            return;
        }
        // Add to graph traversal stack.
        stack.push(node);
        visitedThreads.add(node);
        if (node.getWaitsOn() != null) {
            ((ResourceImpl) (node.getWaitsOn())).accept(this);
        }
        // remove from graph traversal stack.
        stack.pop();
    }

    @Override
    protected void visitNode(ResourceImpl node) {
        if (visitedResources.contains(node)) {
            handleCycle();
            return;
        }
        // Add to graph traversal stack.
        stack.push(node);
        visitedResources.add(node);
        Set<Thread> users = node.getUsers();
        for (Thread user : users) {
            ((ThreadImpl) user).accept(this);
        }
        // remove from graph traversal stack.
        stack.pop();
    }

    private Graph getPath() {
        Set<Node> path = new HashSet<Node>();
        Iterator<Node> it = stack.iterator();
        boolean waitingOnItself = false;
        while (it.hasNext()) {
            Node node = it.next();
            if (node instanceof Thread) {
                if (((ThreadImpl) node).isWaitingOnItself()) {
                    waitingOnItself = true;
                    break;
                }
            }
            path.add(node);
        }
        if (waitingOnItself) {
            logger.fine("Thread waiting on itself. " +
                    "Probably timed wait.");
            path.clear();
        }
        return GraphImpl.getGraph(path, true);
    }

    private void handleCycle() {
        // Already visited. Deadlock Found.
        hasCycle = true;
        logger.fine("Found a visited node.");
        Graph path = getPath();
        if (path.getAllNodes().size() != 0) {
            deadlocks.add(path);
        }
    }
}
