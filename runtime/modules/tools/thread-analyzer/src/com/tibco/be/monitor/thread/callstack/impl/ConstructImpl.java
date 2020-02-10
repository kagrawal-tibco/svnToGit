/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.callstack.impl;

import java.util.ArrayDeque;
import java.util.Deque;

import com.tibco.be.monitor.thread.callstack.Construct;
/**
 *
 * Author: Karthikeyan Subramanian / Date: Nov 30, 2009 / Time: 3:17:40 PM
 */
public class ConstructImpl implements Construct {

    protected Deque<Construct> calls;
    protected String info, group;

    protected ConstructImpl(String info, String group, Deque<Construct> calls) {
        this.calls= new ArrayDeque<Construct>(calls);
        this.info = info;
        this.group = group;
    }

    public static Construct getConstruct(String info, String group, Deque<Construct> calls) {
        return new ConstructImpl(info, group, calls);
    }
    
    @Override
    public Deque<Construct> getCalls() {
        return new ArrayDeque<Construct>(calls);
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String getGroup() {
        return this.group;
    }
}
