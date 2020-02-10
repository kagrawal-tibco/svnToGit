package com.tibco.cep.query.stream.rete;

import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.impl.monitor.QueryMonitor;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collection;

/*
* Author: Ashwin Jayaprakash Date: May 8, 2008 Time: 2:31:22 PM
*/
public class QueryMonitorTest extends ReteCQJoinConTest {

    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        super.test();
    }

    @Override
    protected void handleTestEnd(ReteQuery query) throws Exception {
        QueryMonitor monitor = Registry.getInstance().getComponent(QueryMonitor.class);

        //----------

        Collection<String> collection = monitor.fetchTraceOutput(query);
        Assert.assertTrue(collection.size() > 0, "At least 1 Run must've been tracked.");
        for(String s : collection) {
            System.err.println(s);
            System.err.println("----------");
        }

        //----------

        Collection<ReteQuery> cqs = monitor.listContinuousQueries(regionManager.getRegionName());
        Assert.assertEquals(cqs.size(), 1, "Wrong number of registered Queries.");
        for(ReteQuery cq : cqs) {
            Assert.assertSame(cq, query, "Wrong/different instance of Query received.");
        }

        super.handleTestEnd(query);

        cqs = monitor.listContinuousQueries(regionManager.getRegionName());
        Assert.assertEquals(cqs.size(), 0, "Queries must've been unregistered by now.");
    }
}
