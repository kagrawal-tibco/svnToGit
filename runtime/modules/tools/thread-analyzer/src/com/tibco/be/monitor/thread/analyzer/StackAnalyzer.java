/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.analyzer;

import java.lang.management.ThreadInfo;
import java.util.List;

import com.tibco.be.monitor.thread.callstack.CallStack;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Dec 2, 2009 / Time: 9:39:59 PM
 */
public interface StackAnalyzer {

    List<CallStack> analyzeStack(ThreadInfo[] dump);
}
