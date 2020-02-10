/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread.report;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.management.LockInfo;
import java.lang.management.ThreadInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.be.monitor.thread.analyzer.ThreadInfoAnalyzer;
import com.tibco.be.monitor.thread.analyzer.ThreadInfoGroup;
import com.tibco.be.monitor.thread.analyzer.impl.ThreadInfoAnalyzerImpl;
import com.tibco.be.monitor.thread.graph.Graph;
import com.tibco.be.monitor.thread.graph.Node;
import com.tibco.be.monitor.thread.graph.Resource;
import com.tibco.be.monitor.thread.graph.visitor.PrintVisitor;
import com.tibco.be.monitor.thread.graph.visitor.impl.PrintVisitorImpl;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 17, 2009 / Time: 3:55:13 PM
 */
public class Reporter {
    private static final ThreadInfoAnalyzer tInfoAnalyzer =
            ThreadInfoAnalyzerImpl.getAnalyzer();
    private static final String STR_TD_SUMMARY = "THREAD DUMP SUMMARY";
    private static final String STR_DL_SUMMARY = "DEADLOCK  SUMMARY";
    private static final String STR_HA_SUMMARY = "HOST  SUMMARY";
    private static final int LINE_LENGTH = 75;

    private Reporter() {
    }

    public static final void printHostSummary(String host,
            int port, Logger logger) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(outStream);
        stream.printf("%n");
        List<String> entries = new ArrayList<String>(3);
        entries.add(STR_HA_SUMMARY);
        entries.add("Host:" + host + "    Port:" + port);
        entries.add("Report generation time: " + new Date().toString());
        OutputFormatter.printHeaderString(entries, 10, stream);
        stream.printf("%n").flush();
        logger.log(Level.INFO, outStream.toString());
    }

    public static final void printCompressedReport(ThreadInfo[] threadDump, Logger logger)
            throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(outStream);
        stream.printf("%n");
        printCompressedReport(threadDump, stream);
        logger.info(outStream.toString());
    }

    public static final void printCompressedReport(ThreadInfo[] threadDump,
            PrintStream stream) throws Exception {
        printDeadlocks(threadDump, stream);
        printThreadGroups(threadDump, stream);
        stream.flush();
    }

    public static final void printDeadlocks(ThreadInfo[] threadDump,
            Logger logger) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(outStream);
        stream.printf("%n");
        printDeadlocks(threadDump, stream);
        logger.info(outStream.toString());
    }

    private static final void printDeadlocks(ThreadInfo[] threadDump,
            PrintStream stream) throws Exception {
        List<Graph> deadlocks = tInfoAnalyzer.findDeadlocks(threadDump);
        printDeadlockHeader(deadlocks.size(), stream);
        printDeadlocks(deadlocks, stream);
        stream.flush();
    }

    public static final void printThreadGroups(ThreadInfo[] threadDump,
            Logger logger) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(outStream);
        stream.printf("%n");
        printThreadGroups(threadDump, stream);
        logger.info(outStream.toString());
    }

    public static final void printThreadGroups(ThreadInfo[] threadDump,
            PrintStream stream) throws Exception {
        List<ThreadInfoGroup> groups = tInfoAnalyzer.findGroups(threadDump);
        printThreaddumpHeader(groups.size(), stream);
        printThreadGroups(groups, stream);
        stream.flush();
    }

    /**
     * Returns the printable stack trace string.
     * @param stackTrace
     * @return stack trace string
     */
    public static final String getStackTraceString(StackTraceElement[] stackTrace) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(outStream);
        for(StackTraceElement stackTraceElem : stackTrace) {
            stream.printf("    %s%n", stackTraceElem.toString());
        }
        stream.flush();
        return outStream.toString();
    }

    public static final void printSummaryStack(ThreadInfo[] dump, Logger logger) 
            throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(outStream);
        stream.printf("%n");
        printSummaryStack(dump, stream);
        logger.log(Level.INFO, outStream.toString());
    }

    public static final void printSummaryStack(ThreadInfo[] dump, PrintStream stream) 
            throws Exception {
        List<ThreadInfoGroup> groups = tInfoAnalyzer.findGroups(dump);
        // Now create summary report for each of the call stack
        // and print the summary reports.
        SummaryReport report = new SummaryReport(groups);
        report.printSummaryStack(stream);
    }
    
    private static final void printThreadGroups(List<ThreadInfoGroup> groups,
            PrintStream stream) throws Exception {
        stream.printf("%n");
        for (int i = 0, I = groups.size(); i < I; i++) {
            ThreadInfoGroup group = groups.get(i);
            stream.printf("%-17s [Total Threads: %s]%n",
                    "Grp #" + (i + 1),
                    group.getThreadInfos().size());
            printExtendedThreadTable(group.getThreadInfos(), stream);
            stream.printf("%-13s%n", "Stacktrace:");
            stream.printf("%s%n", getStackTraceString(group.
                    getGroupStackTrace()));
            stream.printf("%n");
            stream.flush();
        }
    }

    private static void printDeadlockHeader(int number, PrintStream stream) {
        List<String> entries = new ArrayList<String>(2);
        entries.add(STR_DL_SUMMARY);
        entries.add("Total Deadlocks:" + number);
        OutputFormatter.getHeaderString(entries, 45);
        stream.printf("%n").flush();
    }

    private static void printThreaddumpHeader(int number, PrintStream stream) throws Exception {
        Table table = new Table(null, 1);
        String row_1 = STR_TD_SUMMARY;
        String row_2 = "Total Compressed Groups:" + number;
        int size = ((row_1.length() + 60) > row_2.length()? 
            row_1.length() + 60 : row_2.length());
        row_1 = OutputFormatter.formatCenter(row_1, size, false);
        row_2 = OutputFormatter.formatCenter(row_2, size, false);
        table.addRow(row_1);
        table.addRow(row_2);
        table.printTable(stream);
        stream.flush();
    }

    private static void printDeadlocks(List<Graph> deadlocks,
            PrintStream stream) throws Exception {
        for (int i = 0, I = deadlocks.size(); i < I; i++) {
            Graph deadlock = deadlocks.get(i);
            stream.printf("%-15s - [%s Threads, %s Resources]%n",
                    "Deadlock #" + (i + 1),
                    deadlock.getThreadNodes().size(),
                    deadlock.getResourceNodes().size());
            OutputFormatter.printHeaderLine(stream, LINE_LENGTH);
            printResourceTable(deadlock.getResourceNodes(), stream);
            stream.printf("%n");
            printThreadTable(deadlock.getThreadNodes(), stream);
            stream.printf("%n%s%n", "Details:");
            printDeadlockInfo(deadlock, stream);
            stream.printf("%n");
            stream.flush();
        }
    }

    private static void printDeadlockInfo(Graph graph, PrintStream stream) throws Exception {
        PrintVisitor visitor = PrintVisitorImpl.getVisitor("DEADLOCK",
                graph.getAllNodes().toArray(new Node[0])[0]);
        visitor.printString(stream);
    }

    private static void printResourceTable(Set<Resource> resourceNodes,
            PrintStream stream) throws Exception {
        //stream.printf("%-12s%n", "Resources:");
        Table table = new Table("RESOURCES", 4);
        table.setHeader("Class Name", "Hash Code", "Users", "Waiters");
        for (Resource res : resourceNodes) {
            LockInfo info = (LockInfo) res.getInfo();
            table.addRow(info.getClass().getName(),
                    String.valueOf(info.hashCode()),
                    String.valueOf(res.getUsers().size()),
                    String.valueOf(res.getWaiters().size()));
        }
        table.printTable(stream);
        stream.flush();
    }

    private static void printThreadTable(
            Set<com.tibco.be.monitor.thread.graph.Thread> threadNodes,
            PrintStream stream) throws Exception {
        printThreadTable(getThreadInfos(threadNodes), stream);
    }

    public static void printThreadTable(List<ThreadInfo> threads,
            PrintStream stream) throws Exception {
        Table table = new Table("THREADS", 3);
        table.setHeader("Id", "State", "Name");
        for (ThreadInfo info : threads) {
            table.addRow(String.valueOf(info.getThreadId()),
                    info.getThreadState().toString(), info.getThreadName());
        }
        table.printTable(stream);
        stream.flush();
    }

    public static Table getThreadTable(List<ThreadInfo> threads) throws Exception {
        Table table = new Table("THREADS", 3);
        table.setHeader("Id", "State", "Name");
        for (ThreadInfo info : threads) {
            table.addRow(String.valueOf(info.getThreadId()),
                    info.getThreadState().toString(), info.getThreadName());
        }
        return table;
    }

    public static Table getThreadStatsTable(List<ThreadInfo> tInfos) throws Exception {
        Table table = new Table("THREADS", 7);
        table.setHeader("Id", "State", "Name", "Blocked Time", "Blocked Count",
                "Wait Time", "Wait Count");
        for (ThreadInfo info : tInfos) {
            table.addRow(String.valueOf(info.getThreadId()),
                    info.getThreadState().toString(), info.getThreadName(),
                    String.valueOf(info.getBlockedTime()),
                    String.valueOf(info.getBlockedCount()),
                    String.valueOf(info.getWaitedTime()),
                    String.valueOf(info.getWaitedCount()));
        }
        return table;
    }

    private static void printExtendedThreadTable(List<ThreadInfo> threads,
            PrintStream stream) throws Exception {
        //stream.printf("%-10s%n", "Threads:");
        Table table = new Table("THREADS", 6);
        table.setHeader("Id", "State", "Name", "Locks Used",
                "Monitors Used", "Waiting for");
        for(ThreadInfo info : threads) {
            table.addRow(String.valueOf(info.getThreadId()),
                    info.getThreadState().toString(), info.getThreadName(),
                    getLocksUsed(info.getLockedSynchronizers()), 
                    getLocksUsed(info.getLockedMonitors()),
                    getWaitingFor(info));
        }
        table.printTable(stream);
        stream.flush();
    }

    private static List<ThreadInfo> getThreadInfos(
            Set<com.tibco.be.monitor.thread.graph.Thread> threadNodes) {
        List<ThreadInfo> tInfos = new ArrayList<ThreadInfo>(threadNodes.size());
        for (com.tibco.be.monitor.thread.graph.Thread thread : threadNodes) {
            tInfos.add(thread.getInfo());
        }
        return tInfos;
    }

    private static final <T extends LockInfo> String getLocksUsed(T[] locks) {
        StringBuilder str = new StringBuilder();
        for(LockInfo lInfo : locks) {
            if(str.length() != 0) {
                str.append(",");
            }
            str.append(lInfo.toString());
        }
        return str.toString();
    }

    private static final String getWaitingFor(ThreadInfo info) {
        String result = info.getLockName();
        if(result == null) {
            return "";
        }
        return result;
    }

    private static String getString(int len, char ch) {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < len; i++) {
            str.append(ch);
        }
        return str.toString();
    }
}
