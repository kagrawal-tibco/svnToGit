package com.tibco.cep.query.stream.rete.impl_v2;

import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.impl.query.CQScheduler;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySource;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.query.ReteQueryImpl_V2;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.rete.ReteCQNullSimulatorTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Properties;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 6:27:33 PM
 */

public class ReteCQ_V2_NullSimulatorTest extends ReteCQNullSimulatorTest {

    protected final Properties properties = new Properties();
    protected final CQScheduler cqScheduler = new CQScheduler();

    {
        properties.put("com.tibco.cep.continuous.query.sched.min.threads", "1");
        properties.put("com.tibco.cep.continuous.query.sched.max.threads", "1");
    }

    @BeforeClass
    public void beforeClass() {
        try {
            cqScheduler.init(properties);
        } catch(Exception e) {
            e.printStackTrace();
        }
        Registry.getInstance().register(CQScheduler.class, cqScheduler);
    }

    @AfterClass
    public void afterClass() {
        try {
            cqScheduler.stop();
        } catch(Exception e) {
            e.printStackTrace();
        }
        Registry.getInstance().unregister(CQScheduler.class);
    }

    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        super.test();
    }

    @Override
    public ReteQuery getReteQuery(String resourceId, Source[] sources) {
        return new ReteQueryImpl_V2(master.getAgentService().getName(), new ResourceId(resourceId),
                (ReteEntitySource[]) sources, sink, false, null);
    }
}