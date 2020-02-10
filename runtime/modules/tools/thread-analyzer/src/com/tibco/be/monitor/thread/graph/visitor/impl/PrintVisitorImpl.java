/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread.graph.visitor.impl;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.be.monitor.thread.graph.Node;
import com.tibco.be.monitor.thread.graph.Resource;
import com.tibco.be.monitor.thread.graph.Thread;
import com.tibco.be.monitor.thread.graph.impl.ResourceImpl;
import com.tibco.be.monitor.thread.graph.impl.ThreadImpl;
import com.tibco.be.monitor.thread.graph.visitor.PrintVisitor;
import com.tibco.be.monitor.thread.report.Table;
import com.tibco.be.monitor.thread.report.TableException;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
public class PrintVisitorImpl extends BaseVisitor implements PrintVisitor {
    private static final Logger logger = Logger.getLogger(PrintVisitorImpl.class.getName());
    private final Node start;
    private final Set<Node> visited = new HashSet<Node>();
    private int index = 1;
    private Table table;

    private PrintVisitorImpl(String name, Node startNode) throws TableException {
        table = new Table(name, 4);
        table.setHeader("Index", "Name", "Id", "Info");
        this.start = startNode;
        visit(start);
    }

    /**
     * Returns the print visitor for the graph path with given
     * start node.
     * @param start
     * @return Print visitor
     */
    public static final PrintVisitorImpl getVisitor(String name, Node start) throws Exception {
        return new PrintVisitorImpl(name, start);
    }

    @Override
    public void printString(PrintStream stream) throws Exception {
        table.printTable(stream);
    }

    @Override
    protected void visitNode(ThreadImpl node) {
        if (visited.contains(node)) {
            return;
        }
        try {
            table.addRow(String.valueOf(index), node.getInfo().getThreadName(),
                    String.valueOf(node.getInfo().getThreadId()),
                    "Thread is waiting for " + node.getWaitsOn().getInfo().
                    getClass().getName() + ":" + node.getWaitsOn().getInfo().hashCode());
        } catch (TableException ex) {
            logger.log(Level.SEVERE, "Table exception", ex);
        }
        index++;
        visited.add(node);
        if (node.getWaitsOn() != null) {
            ((ResourceImpl) node.getWaitsOn()).accept(this);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitNode(ResourceImpl node) {
        if (visited.contains(node)) {
            return;
        }
        try {
            table.addRow(String.valueOf(index), node.getInfo().getClass().getName(),
                    String.valueOf(node.getInfo().hashCode()),
                    "Resource is used by " + getResourceUsers(node));
        } catch (TableException ex) {
            logger.log(Level.SEVERE, "Table exception", ex);
        }
        index++;
        visited.add(node);
        Set<Thread> users = node.getUsers();
        for (Thread thread : users) {
            ((ThreadImpl) thread).accept(this);
        }
    }

    private String getResourceUsers(Resource node) {
        StringBuilder string = new StringBuilder();
        for (com.tibco.be.monitor.thread.graph.Thread user : node.getUsers()) {
            if (string.length() != 0) {
                string.append(",");
            }
            string.append(user.getInfo().getThreadName());
        }
        return string.toString();
    }
}
