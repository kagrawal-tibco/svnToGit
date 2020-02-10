/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.analyzer;

import java.lang.management.ThreadInfo;
import java.util.List;

import com.tibco.be.monitor.thread.graph.Graph;

/**
 * This interface provides an methods for analyzing the thread dump.
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
public interface ThreadInfoAnalyzer {

    /**
     * Analyze the dependencies and find the deadlocks.
     *
     * @param dump an array of ThreadInfo 
     * @return list of deadlock graphs.
     */
    List<Graph> findDeadlocks(ThreadInfo[] dump);

//    /**
//     * Analyze the dependencies and find the bottlenecks.
//     * @param dump an array of ThreadInfo
//     * @return list of bottleneck graphs
//     */
//    List<Graph> findBottlenecks(ThreadInfo[] dump);

    /**
     * Groups the thread dump into ThreadInfoGroups and
     * returns a list of ThreadInfoGroup objects
     * @param dump an array of ThreadInfo objects
     * @return a list of ThreadInfoGroup objects.
     */
    List<ThreadInfoGroup> findGroups(ThreadInfo[] dump);

}
