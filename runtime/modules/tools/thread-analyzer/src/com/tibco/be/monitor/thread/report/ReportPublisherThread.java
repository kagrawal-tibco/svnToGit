/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread.report;

import java.lang.management.ThreadMXBean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 24, 2009 / Time: 3:08:07 PM
 */
public class ReportPublisherThread implements Runnable {
    private static final Logger logger = Logger.getLogger(
            ReportPublisherThread.class.getName());
    private final String host;
    private final int port;
    private final ThreadMXBean tmxBean;
    private final Logger log;

    public ReportPublisherThread(Logger logger, String host,
            int port, ThreadMXBean bean) {
        this.host = host;
        this.port = port;
        this.tmxBean = bean;
        log = logger;
    }

    @Override
    public void run() {
        try {
            // Print the host summary.
            Reporter.printHostSummary(host, port, log);
            // Print the compressed report.
            Reporter.printCompressedReport(
                    tmxBean.dumpAllThreads(true, true), log);
            // Print the summary report.
            Reporter.printSummaryStack(tmxBean.dumpAllThreads(true, true), log);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Unable to connect to the "
                    + host + ":" + port, ex);
        } 
    }
}
