/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.analyzer.impl;

import java.lang.management.ThreadInfo;
import java.util.LinkedList;
import java.util.List;

import com.tibco.be.monitor.thread.analyzer.ThreadInfoAnalyzer;
import com.tibco.be.monitor.thread.analyzer.ThreadInfoGroup;
import com.tibco.be.monitor.thread.graph.Graph;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
public class ThreadInfoAnalyzerImpl implements ThreadInfoAnalyzer {

    private ThreadInfoAnalyzerImpl() {
    }

    /**
     * This is a factory method that constructs and 
     * returns ThreadInfoAnalyzer object.
     * 
     * @return threadinfoanalyzer
     */
    public static final ThreadInfoAnalyzer getAnalyzer() {
        return new ThreadInfoAnalyzerImpl();
    }

    @Override
    public List<ThreadInfoGroup> findGroups(ThreadInfo[] dump) {
        return TIGroupBuilder.buildGroups(dump);
    }

    @Override
    public List<Graph> findDeadlocks(ThreadInfo[] dump) {
        List<Graph> result = new LinkedList<Graph>();
        Graph[] graphs = GraphBuilder.buildGraphs(dump);
        for(Graph graph : graphs) {
            result.addAll(graph.findDeadlocks());
        }
        return result;
    }
}
