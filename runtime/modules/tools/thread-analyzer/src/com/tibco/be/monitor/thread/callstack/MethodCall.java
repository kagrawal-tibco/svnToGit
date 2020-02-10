/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.callstack;

/**
 * Author: Karthikeyan Subramanian / Date: Dec 9, 2009 / Time: 5:55:21 PM
 * @author ksubrama
 */
public interface MethodCall extends Construct {

    int getDepth();

    /**
     * For a construct representing a single method call,
     * this method returns the stack trace element of that particular method call.
     * @return stack trace element
     */
    StackTraceElement getStackTraceElement();
}
