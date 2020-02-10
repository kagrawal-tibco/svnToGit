/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.graph.visitor;

import java.util.Set;

import com.tibco.be.monitor.thread.graph.Graph;

/**
 *
 * @author ksubrama
 */
public interface DeadlockVisitor extends Visitor {

    /**
     * Returns the deadlocks found in the graph.
     * @return deadlocks
     */
    Set<Graph> getDeadlocks();

    /**
     * Returns true if the graph has any cycles.
     * @return boolean specifying graph cycles.
     */
    boolean hasCycles();

}
