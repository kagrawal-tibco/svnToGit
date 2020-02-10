/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread.graph;

import com.tibco.be.monitor.thread.BaseTest;
import com.tibco.be.thread.analyzer.util.BottleneckGeneratorUtil;
import com.tibco.be.thread.analyzer.util.BottleneckGeneratorUtil.BottleneckType;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author ksubrama
 */
public class BottleneckAnalyzerTest extends BaseTest {

    private static final int count = 10;
    private static final Logger logger = Logger.getLogger(
            BottleneckAnalyzerTest.class.getName());

    public BottleneckAnalyzerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        logger.setLevel(Level.ALL);
        BaseTest.setUpClass();
    }

    @Test
    public void insufficientResourceBottleneckTest() {
        logger.warning("insufficientResourceBottleneckTest");
        ExecutorService threadpool = null;
        try {
            threadpool = BottleneckGeneratorUtil.generateBottleneck(
                    BottleneckType.INSUFFICIENT_RESOURCE, count);
            printThreadDump(logger);
        } catch (Exception ex) {
            // ignore.
        } finally {
            if (threadpool != null) {
                shutdownThreadpool(threadpool);
            }
        }
    }

    @Test
    public void boundQueueBottleneckTest() {
        logger.warning("boundQueueBottleneckTest");
        ExecutorService threadpool = null;
        try {
            threadpool = BottleneckGeneratorUtil.generateBottleneck(
                    BottleneckType.BOUND_QUEUE, count);
            printThreadDump(logger);
        } catch (Exception ex) {
            // ignore.
        } finally {
            if (threadpool != null) {
                shutdownThreadpool(threadpool);
            }
        }
    }
}
