/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread.report;

import com.tibco.be.monitor.thread.BaseTest;
import com.tibco.be.thread.analyzer.util.BottleneckGeneratorUtil;
import com.tibco.be.thread.analyzer.util.BottleneckGeneratorUtil.BottleneckType;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ksubrama
 */
public class ReportTest extends BaseTest {

    private static final int count = 10;
    private static final Logger logger = Logger.getLogger(
            ReportTest.class.getName());
    private static ExecutorService threadpool;

    public ReportTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        threadpool = BottleneckGeneratorUtil.generateBottleneck(
                BottleneckType.INSUFFICIENT_RESOURCE, count);
        BaseTest.createDeadlock();
        BaseTest.setUpClass();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        shutdownThreadpool(threadpool);
        BaseTest.tearDownClass();
    }

    @Test
    public void fullThreadDumpReportTest() {
        logger.info("fullThreadDumpReportTest");
        try {
            printThreadDump(tmxBean.dumpAllThreads(true, true), logger);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Unable to generate report", ex);
            fail();
        }
    }

    @Test
    public void summaryReportTest() {
        logger.info("summaryReportTest");
        try {
            Reporter.printSummaryStack(tmxBean.dumpAllThreads(true, true), logger);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Failed to create summary report", ex);
            fail();
        } finally {
            if (threadpool != null) {
                shutdownThreadpool(threadpool);
            }
        }
    }
}
