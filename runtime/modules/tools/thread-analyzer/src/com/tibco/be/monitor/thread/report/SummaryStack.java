/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread.report;

import java.util.ArrayDeque;
import java.util.Deque;

import com.tibco.be.monitor.data.structure.TrieNode;
import com.tibco.be.monitor.thread.callstack.Construct;
import com.tibco.be.monitor.thread.callstack.impl.ConstructImpl;
import com.tibco.be.monitor.thread.callstack.impl.MethodCallImpl;

/**
 * Author: Karthikeyan Subramanian / Date: Dec 14, 2009 / Time: 12:07:19 PM
 * @author ksubrama
 */
class SummaryStack {

    private final TrieNode root;
    private final Deque<Construct> stack = new ArrayDeque<Construct>();
    private Deque<Construct> mCalls = new ArrayDeque<Construct>();
    // Used for setting construct info.
    private String runningGroup = null;
    // Used for matching method call constructs.
    private String runningPrefix = null;

    public SummaryStack(StackTraceElement[] stackTrace, TrieNode root) {
        this.root = root;
        init(stackTrace);
    }

    private void init(StackTraceElement[] stackTrace) {
        for (int i = 0; i < stackTrace.length; i++) {
            addCall(stackTrace[i], i + 1);
        }
    }

    private void addCall(StackTraceElement elem, int depth) {
        // Init the stack trace element and the method call object.
        String elemStr = elem.getClassName() + "." + elem.getMethodName();        
        // Check for current node path and initialize current prefix and group.
        String currPrefix = getTrimmedPrefix(elemStr);
        String currGroup = root.getBestMatch(elemStr).getGroup();
        // There is no current group and prefix
        // Set the values.
        if (runningGroup == null && runningPrefix == null) {
            runningGroup = currGroup;
            runningPrefix = currPrefix;
        }
        Construct mCall = MethodCallImpl.getMethodCall(null, runningGroup, elem, depth);
        addMethodCall(currGroup, currPrefix, mCall);
    }

    public Deque<Construct> getStack() {
        if (mCalls.size() > 0) {
            stack.addLast(ConstructImpl.getConstruct(runningPrefix,
                    runningGroup, mCalls));
        }
        return this.stack;
    }

    private void addMethodCall(String currGroup, String currPrefix,
            Construct mCall) {
        if (currGroup != null && runningGroup != null &&
                !runningGroup.equals("UNCLASSIFIED") &&
                runningGroup.equals(currGroup)) {
            runningPrefix = findCommonPrefix(currPrefix, runningPrefix);
        } else if((currGroup == null && runningGroup != null) && 
                (currGroup != null && runningGroup == null)) {
            stack.addLast(ConstructImpl.getConstruct(runningPrefix,
                    runningGroup, mCalls));
            mCalls = new ArrayDeque<Construct>();
            runningPrefix = getTrimmedPrefix(currPrefix);
            runningGroup = currGroup;
        } else {
            String commonPrefix = findCommonPrefix(currPrefix, runningPrefix);
            int packageLvl = 0;
            if (!commonPrefix.equals("")) {
                packageLvl = countPackageLevel(commonPrefix);
            }
            if (packageLvl <= 1) {
                // If package prefix starts with java. then, package level 1
                // is allowed. Else consider it as a new grouping.
                if (commonPrefix.startsWith("java")) {
                    runningPrefix = getTrimmedPrefix(commonPrefix);
                } else {
                    stack.addLast(ConstructImpl.getConstruct(runningPrefix,
                            runningGroup, mCalls));
                    mCalls = new ArrayDeque<Construct>();
                    runningPrefix = getTrimmedPrefix(currPrefix);
                    runningGroup = currGroup;
                }
            } else {
                runningPrefix = getTrimmedPrefix(commonPrefix);
            }
        }
        mCalls.add(mCall);
    }

    private String findCommonPrefix(String prefix_1, String prefix_2) {
        StringBuilder result = new StringBuilder();
        prefix_1 = getTrimmedPrefix(prefix_1);
        prefix_2 = getTrimmedPrefix(prefix_2);
        String[] parts_1 = prefix_1.split("\\.");
        String[] parts_2 = prefix_2.split("\\.");
        int size = (parts_1.length < parts_2.length ? parts_1.length
                : parts_2.length);
        for (int i = 0; i < size; i++) {
            if (parts_1[i].equals(parts_2[i])) {
                if (result.length() != 0) {
                    result.append(".");
                }
                result.append(parts_1[i]);
            } else {
                break;
            }
        }
        return result.toString();
    }

    private String getTrimmedPrefix(String prefix) {
        if (prefix.contains("$")) {
            return prefix.substring(0, prefix.indexOf("$"));
        }
        return prefix;
    }

    private int countPackageLevel(String commonPrefix) {
        String[] parts = commonPrefix.split("\\.");
        return parts.length;
    }
}
