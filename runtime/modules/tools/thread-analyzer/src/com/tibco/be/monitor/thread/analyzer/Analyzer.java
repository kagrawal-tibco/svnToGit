/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.analyzer;

import java.util.Deque;

import com.tibco.be.monitor.thread.callstack.Construct;

/**
 *
 * @author ksubrama
 */
public interface Analyzer {

    Deque<Construct> analyzeStack(Deque<Construct> stack);
}
