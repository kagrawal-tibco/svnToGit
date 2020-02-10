/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread.graph;

import java.util.Set;

/**
 * This interface represents the resource objects that are requested/alloted
 * to the threads.
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
public interface Resource extends Node {

    /**
     * Returns all the threads that are using this resource.
     * @return users of the resource.
     */
    Set<Thread> getUsers();

    /**
     * Returns all the threads that are waiting for this resource.
     * @return waiters of the resource.
     */
    Set<Thread> getWaiters();
}
