/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread;

import com.tibco.be.monitor.thread.util.JMXUtil;
import com.tibco.be.monitor.thread.report.Reporter;
import com.tibco.be.thread.analyzer.util.DeadlockGeneratorUtil;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.remote.JMXConnector;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;

/**
 * Base Test class. Contains various utility methods.
 * Do not test this class.
 * 
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
@Ignore
public abstract class BaseTest {

    private static final Logger logger = Logger.getLogger(
            BaseTest.class.getName());
    private static final String[] LOGGER_NAMES = new String[]{
        "com.tibco.be.monitor.thread.analyzer",
        "com.tibco.be.monitor.thread.analyzer.impl",
        "com.tibco.be.monitor.thread.graph",
        "com.tibco.be.monitor.thread.graph.impl",
        "com.tibco.be.monitor.thread.graph.comparator",
        "com.tibco.be.monitor.thread.graph.visitor",
        "com.tibco.be.monitor.thread.graph.visitor.impl",};
    /**
     *
     */
    protected static int port = 3000;
    private static JMXConnector connector;
    /**
     *
     */
    protected static ThreadMXBean tmxBean;
    /**
     *
     */
    protected static int deadlockThreadCount = 4;
    /**
     *
     */
    protected static ExecutorService deadlockedThreadpool;
    
    /**
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
        createDeadlock();
        System.out.println("Waiting for deadlock threads to initialize.");
        Thread.sleep(2000L);
        JMXUtil.createSimpleJMXServer(port);
        connector = JMXUtil.getJMXConnector("localhost", port);
        tmxBean = JMXUtil.getThreadMXBean(connector);
        // set the log level to warning.
        for (String LOGGER_NAME : LOGGER_NAMES) {
            // set the log level to warning.
            Logger.getLogger(LOGGER_NAME).setLevel(Level.WARNING);
        }
    }

    /**
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
        shutdownThreadpool(deadlockedThreadpool);
        System.out.println("Waiting for deadlock threads to shutdown");
        Thread.sleep(2000L);
        connector.close();
    }

    /**
     *
     * @throws Exception
     */
    protected final static void createDeadlock()
            throws Exception {
        deadlockedThreadpool = DeadlockGeneratorUtil.generateDeadlock(
                deadlockThreadCount);
    }

    /**
     * 
     */
    protected final static void shutdownThreadpool(ExecutorService threadpool) {
        try {
            threadpool.shutdown();
            int retry = 0;
            while (retry++ < 10 && !threadpool.awaitTermination(
                    500, TimeUnit.MILLISECONDS)) {
                threadpool.shutdown();
            }
            threadpool.shutdownNow();
        } catch (InterruptedException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    protected final static void printThreadDump(ThreadInfo[] threadDump,
            Logger logger) throws Exception {
        Reporter.printThreadGroups(threadDump, logger);
    }

    protected final static void printThreadDump(Logger logger)
            throws Exception {
        Reporter.printThreadGroups(tmxBean.
                dumpAllThreads(true, true), logger);
    }
}
