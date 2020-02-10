/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.graph.visitor;

import com.tibco.be.monitor.thread.graph.Node;

/**
 *
 * @param <T> node to visit
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
public interface Visitor<T extends Node> {

    /**
     * Visit the node.
     * @param node
     */
    void visit(T node);
}
