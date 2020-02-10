/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread.analyzer.impl;

import java.lang.management.ThreadInfo;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.tibco.be.monitor.thread.analyzer.Analyzer;
import com.tibco.be.monitor.thread.analyzer.StackAnalyzer;
import com.tibco.be.monitor.thread.callstack.CallStack;
import com.tibco.be.monitor.thread.callstack.Construct;
import com.tibco.be.monitor.thread.callstack.impl.CallStackImpl;
import com.tibco.be.monitor.thread.callstack.impl.MethodCallImpl;
import com.tibco.be.monitor.thread.util.ServiceLoaderUtil;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Dec 2, 2009 / Time: 9:40:40 PM
 */
public class StackAnalyzerImpl implements StackAnalyzer {

    private static final StackAnalyzer INSTANCE = new StackAnalyzerImpl();

    private StackAnalyzerImpl() {
    }

    public static final StackAnalyzer getAnalyzer() {
        return INSTANCE;
    }

    @Override
    public List<CallStack> analyzeStack(ThreadInfo[] dump) {
        List<CallStack> callStacks = new ArrayList<CallStack>(dump.length);
        for (ThreadInfo tInfo : dump) {
            Deque<Construct> constructs = getConstructs(tInfo);
            // Analyze constructs
            constructs = doAnalysis(constructs);
            CallStack callStack = getCallStack(tInfo, constructs);
            callStacks.add(callStack);
            // Analyze thread naming.
        }
        return callStacks;
    }

    private Deque<Construct> getConstructs(ThreadInfo tInfo) {
        Deque<Construct> mStack = new ArrayDeque<Construct>();
        StackTraceElement[] stElements = tInfo.getStackTrace();
        for (int i = 0; i < stElements.length; i++) {
            mStack.addLast(MethodCallImpl.getMethodCall(null, null,
                    stElements[i], i + 1));
        }
        return mStack;
    }

    private Deque<Construct> doAnalysis(Deque<Construct> constructs) {
        // Pass this stack to other mid level analyzers.
        List<Analyzer> analyzers = ServiceLoaderUtil.getAnalyzers();
        for (Analyzer analyzer : analyzers) {
            constructs = analyzer.analyzeStack(constructs);
        }
        return constructs;
    }

    private CallStack getCallStack(ThreadInfo tInfo, Deque<Construct> constructs) {
        return CallStackImpl.getCallStack(tInfo, constructs);
    }
}
