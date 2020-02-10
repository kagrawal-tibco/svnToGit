/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.analyzer;

import com.tibco.be.monitor.thread.BaseTest;
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
public class ThreadInfoAnalyzerTest extends BaseTest {
    private ThreadInfoAnalyzer analyzer = ThreadInfoAnalyzerImpl.getAnalyzer();
    private static final Logger logger = Logger.getLogger(
            ThreadInfoAnalyzerTest.class.getName());
    /**
     *
     */
    public ThreadInfoAnalyzerTest() {
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
     public void testFindGroups() {
        logger.info("testFindGroups");
        ThreadInfo[] threadDump = tmxBean.dumpAllThreads(true, true);
        List<ThreadInfoGroup> groups = analyzer.findGroups(threadDump);
        assertTrue(groups.size() > 0);
     }
}