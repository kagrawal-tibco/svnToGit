/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.callstack.impl;

import java.lang.management.ThreadInfo;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import com.tibco.be.monitor.thread.callstack.CallStack;
import com.tibco.be.monitor.thread.callstack.Construct;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 29, 2009 / Time: 12:29:19 PM
 */
public class CallStackImpl implements CallStack {

    private final ThreadInfo tInfo;
    private final Deque<Construct> callStack;
    private String module, blockingModule, blockerInfo = "";
    private final List<String> locks = new LinkedList<String>();

    private CallStackImpl(ThreadInfo tInfo, Deque<Construct> stack) {
        this.tInfo = tInfo;
        callStack = new ArrayDeque<Construct>(stack);
    }

    public static CallStack getCallStack(ThreadInfo tInfo,
            Deque<Construct> stack) {
        return new CallStackImpl(tInfo, stack);
    }

    @Override
    public ThreadInfo getThreadInfo() {
        return this.tInfo;
    }

    @Override
    public Deque<Construct> getStack() {
        return new ArrayDeque<Construct>(callStack);
    }

    public void setThreadOwner(String module) {
        this.module = module;
    }

    @Override
    public String getThreadOwner() {
        return this.module;
    }

    @Override
    public String getBlockingModule() {
        return blockingModule;
    }

    public void setBlockingModule(String blockingModule) {
        this.blockingModule = blockingModule;
    }

    @Override
    public String getBlockerInfo() {
        return this.blockerInfo;
    }

    public void setBlockerInfo(String blockerInfo) {
        this.blockerInfo = blockerInfo;
    }

    @Override
    public List<String> getOwnedLocks() {
        return Collections.unmodifiableList(locks);
    }

    public void addLock(String lockName) {
        this.locks.add(lockName);
    }
}
