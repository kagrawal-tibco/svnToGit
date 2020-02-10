package com.tibco.cep.query.stream.framework;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/*
 * Author: Ashwin Jayaprakash Date: Dec 19, 2007 Time: 3:50:22 PM
 */

public class Suite {
    @BeforeSuite(groups = { TestGroupNames.INIT }, alwaysRun = true)
    public void start() throws Exception {
    }

    // Dummy method to force the Test-group start/stop.
    @Test(groups = { TestGroupNames.INIT })
    public void dummyTest() {
    }

    @AfterSuite(groups = { TestGroupNames.INIT }, alwaysRun = true)
    public void end() throws Exception {
    }
}
