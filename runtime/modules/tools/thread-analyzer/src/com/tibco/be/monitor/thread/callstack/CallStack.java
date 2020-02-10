/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.callstack;

import java.lang.management.ThreadInfo;
import java.util.Deque;
import java.util.List;

/**
 *
 * @author ksubrama
 */
public interface CallStack {

    ThreadInfo getThreadInfo();

    Deque<Construct> getStack();

    String getThreadOwner();

    List<String> getOwnedLocks();

    String getBlockingModule();

    String getBlockerInfo();
}
