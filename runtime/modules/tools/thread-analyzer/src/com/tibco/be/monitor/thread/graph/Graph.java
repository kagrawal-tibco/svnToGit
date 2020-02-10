/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.graph;

import java.util.Iterator;
import java.util.Set;

/**
 * This interface represents the resource-thread request & allocation
 * graph formed for the given thread dump.
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
public interface Graph {

    /**
     * Sort key 
     */
    public enum SortKey {
        /**
         * Time spent by the thread in blocked/wait state.
         */
        BLOCK_TIME,
        /**
         * Number of times the thread was blocked/suspended.
         */
        BLOCK_COUNT,
        /**
         * Number of threads blocked/waiting for the resource
         * owned by this thread.
         */
        DEPENDENCY;
    }

    /**
     * Sort order.
     */
    public enum SortOrder {
        /**
         * Ascending order - from low to high.
         */
        ASCENDING,
        /**
         * Descending order - from high to low.
         */
        DESCENDING;
    }
    
    /**
     * Returns the deadlocks that are found in the graph.
     * If there is no deadlock in the graph, an empty set is returned.
     * 
     * @return a set of deadlock objects.
     */
    Set<Graph> findDeadlocks();

    /**
     * Returns the bottlenecks that are found in the graph.
     * If there is no bottleneck in the graph, an empty set is returned.
     *
     * @return a set of bottleneck objects.
     */
    Set<Graph> findBottlenecks();

    /**
     * Returns true is the graph has any cycles or else false is returned.
     *
     * @return a boolean specifying if the graph has any cycles.
     */
    boolean isCyclic();

    /**
     * Returns a set of start nodes for the graph.
     * These Thread nodes are the nodes that are blocked/waiting for some
     * resource
     * @return starting point nodes.
     */
    Set<Thread> getStartNodes();

    /**
     * Returns all the nodes in the graph.
     * @return all nodes.
     */
    Set<Node> getAllNodes();

    /**
     * Returns the Thread objects that are part of the graph.
     * @return threads
     */
    Set<Thread> getThreadNodes();

    /**
     * Returns the resource objects that are part of the graph.
     * @return resources.
     */
    Set<Resource> getResourceNodes();

    /**
     * Returns the iterator for the nodes sorted 
     * based on the sort key and order.
     * @param key Sort key used for sorting the nodes.
     * @param order sort order used for sorting the nodes.
     * @return sorted node iterator
     */
    Iterator<? extends Node> iterator(SortKey key, SortOrder order);
}
