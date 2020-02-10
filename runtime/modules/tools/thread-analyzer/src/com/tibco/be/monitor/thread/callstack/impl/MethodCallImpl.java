/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.callstack.impl;

import java.util.ArrayDeque;

import com.tibco.be.monitor.thread.callstack.Construct;
import com.tibco.be.monitor.thread.callstack.MethodCall;

/**
 * Author: Karthikeyan Subramanian / Date: Dec 9, 2009 / Time: 5:56:21 PM
 * @author ksubrama
 */
public class MethodCallImpl extends ConstructImpl implements MethodCall {
    protected StackTraceElement element;
    protected int depth;

    private MethodCallImpl(String info, String group,
            StackTraceElement elem, int depth) {
        super(info, group, new ArrayDeque<Construct>(0));
        this.element = elem;
        this.depth = depth;
    }

    public static MethodCall getMethodCall(String info, String group, 
            StackTraceElement elem, int depth) {
        return new MethodCallImpl(info, group, elem, depth);
    }

    @Override
    public StackTraceElement getStackTraceElement() {
        return this.element;
    }

    @Override
    public int getDepth() {
        return this.depth;
    }
}
