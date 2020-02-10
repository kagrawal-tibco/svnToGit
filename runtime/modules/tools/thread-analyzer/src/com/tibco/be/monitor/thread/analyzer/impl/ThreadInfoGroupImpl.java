/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.analyzer.impl;

import java.lang.management.ThreadInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.tibco.be.monitor.thread.analyzer.ThreadInfoGroup;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
public class ThreadInfoGroupImpl implements ThreadInfoGroup {

    private final List<ThreadInfo> threadInfos;
    
    private ThreadInfoGroupImpl(List<ThreadInfo> tInfos) {
        threadInfos = tInfos;
    }

    /**
     * Returns the thread info group for the given set of thread infos
     * @param tInfos set of thread info objects
     * @return ThreadInfoGroup
     */
    public static final ThreadInfoGroup getThreadInfoGroup(
            Set<ThreadInfo> tInfos) {
        return new ThreadInfoGroupImpl(new ArrayList<ThreadInfo>(tInfos));
    }

    @Override
    public List<ThreadInfo> getThreadInfos() {
        return this.threadInfos;
    }

    @Override
    public StackTraceElement[] getGroupStackTrace() {
        if(this.threadInfos != null && this.threadInfos.size() > 0) {
            return this.threadInfos.get(0).getStackTrace();
        }
        return new StackTraceElement[0];
    }
}
