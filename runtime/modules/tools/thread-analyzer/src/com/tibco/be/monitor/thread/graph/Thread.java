/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.graph;

import java.lang.management.ThreadInfo;
import java.util.Set;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
public interface Thread extends Node<ThreadInfo> {

    /**
     * Returns the stacktrace for this thread.
     * @return stack trace of the thread.
     */
    StackTraceElement[] getStackTrace();

    /**
     * Returns a set of resource nodes that are used by this node.
     * @return Set of nodes used.
     */
    Set<Resource> getResourcesUsed();

    /**
     * Returns the node for which the thread is waiting for.
     * @return waitsOn node
     */
    Resource getWaitsOn();
}
