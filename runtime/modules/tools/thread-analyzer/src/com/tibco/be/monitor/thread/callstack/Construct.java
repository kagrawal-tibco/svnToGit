/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.callstack;

import java.util.Deque;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Nov 30, 2009 / Time: 3:08:12 PM
 */
public interface Construct {

    public static final String TYPE = "CONSTRUCT";
    
    Deque<Construct> getCalls();

    String getInfo();

    String getGroup();

}
