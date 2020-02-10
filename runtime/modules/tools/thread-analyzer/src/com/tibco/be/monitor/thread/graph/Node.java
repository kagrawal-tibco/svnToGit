/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.graph;

/**
 * @param <I> Node Info
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
public interface Node<I> {
    /**
     * Returns the node info object
     * @return node info.
     */
    I getInfo();

}
