/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread.report;

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;

import com.tibco.be.monitor.data.structure.Dictionary;
import com.tibco.be.monitor.data.structure.impl.DictionaryImpl;
import com.tibco.be.monitor.service.spi.DictionaryService;
import com.tibco.be.monitor.thread.analyzer.ThreadInfoGroup;
import com.tibco.be.monitor.thread.callstack.Construct;
import com.tibco.be.monitor.thread.callstack.MethodCall;
import com.tibco.be.monitor.thread.util.ServiceLoaderUtil;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Dec 2, 2009 / Time: 6:01:25 PM
 */
public class SummaryReport {

    private final Dictionary dictionary = new DictionaryImpl();
    private final List<ThreadInfoGroup> group;

    public SummaryReport(List<ThreadInfoGroup> group) {
        this.group = group;
        initDictionary();
    }

    public void printSummaryStack(PrintStream stream) throws Exception {
        summarizeStackTrace(stream);
    }

    private void summarizeStackTrace(PrintStream stream) throws Exception {
        for (ThreadInfoGroup tiGroup : group) {
            Table tbl = Reporter.getThreadStatsTable(tiGroup.getThreadInfos());
            tbl.printTable(stream, true, false);
            printSummarizedStack(tiGroup.getGroupStackTrace(), stream);
            stream.printf("%n%n");
        }
    }

    private void printSummarizedStack(StackTraceElement[] stackTrace,
            PrintStream stream) throws Exception {
        Table table = new Table("Stack Trace Summary", 5);
        table.setHeader("DEPTH", "CLASS", "METHOD", "GROUP", "CALL INFO");
        SummaryStack stack = new SummaryStack(stackTrace,
                dictionary.getRootNode());
        Deque<Construct> constructs = stack.getStack();
        while (!constructs.isEmpty()) {
            Construct construct = constructs.pop();
            String callInfo = getCallFlow(construct);
            String grp = construct.getGroup();
            addConstructToTable(construct, callInfo, (grp == null? "" : grp), table);
        }
        table.printTable(stream, true, true);
        stream.flush();
    }

    private void addConstructToTable(Construct construct, String callInfo,
            String group, Table table) throws TableException {
        if(construct instanceof MethodCall) {
            MethodCall mCall = (MethodCall)construct;
            String depth = String.valueOf(mCall.getDepth());
            StackTraceElement element = mCall.getStackTraceElement();
            table.addRow(depth, element.getClassName(), element.getMethodName(), 
                    group, callInfo);
            return;
        }
        Deque<Construct> calls = construct.getCalls();
        int i = 0;
        while (!calls.isEmpty()) {
            if(i++ == 0) {
                addConstructToTable(calls.pop(), callInfo, group, table);
            } else {
                addConstructToTable(calls.pop(), "", "", table);
            }
        }
    }

    private String getCallFlow(Construct construct) {
        String commonPrefix = construct.getInfo();
        String grp = construct.getGroup();
        grp = (grp == null? "" : grp);
        if(commonPrefix.equals(grp)) {
            return "";
        } else if(construct.getCalls().size() == 1) {
            return construct.getInfo();
        }
        StringBuilder str = new StringBuilder(commonPrefix + "[");
        int i = 0;
        Deque<Construct> calls = new ArrayDeque<Construct>(construct.getCalls());
        while(!calls.isEmpty()) {
            Construct cons = calls.pop();
            if(cons instanceof MethodCall) {
                MethodCall mCall = (MethodCall)cons;
                StackTraceElement elem = mCall.getStackTraceElement();
                String elemStr = elem.getClassName() + "." + elem.getMethodName();
                if(i++ != 0) {
                    str.append(" <- ");
                }
                str.append(getSuffix(commonPrefix, elemStr));
            } else {
                return "";
            }
        }
        str.append("]");
        return str.toString();
    }

    private String getSuffix(String prefix, String elemStr) {
        if(elemStr.startsWith(prefix)) {
            if(elemStr.length() > prefix.length() + 1) {
            return elemStr.substring(prefix.length() + 1);
            }
            return prefix;
        }
        return elemStr;
    }

    private void initDictionary() {
        for(DictionaryService service : ServiceLoaderUtil.getDictionaryServices()) {
            initGroups(service.getGroups());
        }
    }

    private void initGroups(Map<String, List<String>> groups) {
        for(String grp : groups.keySet()) {
            List<String> classes = groups.get(grp);
            for(String clsName : classes) {
                dictionary.addEntry(clsName, grp);
            }
        }
    }
}
