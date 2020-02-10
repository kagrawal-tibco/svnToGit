/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.analyzer;

import java.lang.management.ThreadInfo;
import java.util.List;

/**
 * This interface represents the compressed thread dump.
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
public interface ThreadInfoGroup {

    /**
     * Returns a list of ThreadInfo objects that have same stacktrace.
     *
     * @return a list of ThreadInfo objects.
     */
    List<ThreadInfo> getThreadInfos();

    /**
     * Returns the StackTrace for this ThreadInfoGroup.
     *
     * @return an array of StackTraceElement.
     */
    StackTraceElement[] getGroupStackTrace();

}
