/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread.graph;

import com.tibco.be.monitor.thread.BaseTest;
import com.tibco.be.monitor.thread.analyzer.ThreadInfoAnalyzer;
import com.tibco.be.monitor.thread.analyzer.impl.ThreadInfoAnalyzerImpl;
import java.lang.management.ThreadInfo;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
public class GraphTest extends BaseTest {

    private ThreadInfoAnalyzer analyzer = ThreadInfoAnalyzerImpl.getAnalyzer();
    private static final Logger logger = Logger.getLogger(
            GraphTest.class.getName());

    /**
     *
     */
    public GraphTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        logger.setLevel(Level.ALL);
        BaseTest.setUpClass();
    }

    /**
     *
     */
    @Test
    public void testFindDeadlocks() {
        logger.info("testFindDeadlocks");
        ThreadInfo[] threadDump = tmxBean.dumpAllThreads(true, true);
        List<Graph> graphs = analyzer.findDeadlocks(threadDump);
        assertTrue(graphs.size() > 0);
    }
}
