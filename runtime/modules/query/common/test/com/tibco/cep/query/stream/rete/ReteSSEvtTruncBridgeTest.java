package com.tibco.cep.query.stream.rete;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.framework.AbstractCacheTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.impl.rete.ReteEntityInfo;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySource;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySourceImpl;
import com.tibco.cep.query.stream.impl.rete.integ.Manager;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.query.ReteQueryImpl;
import com.tibco.cep.query.stream.impl.rete.service.SnapshotQueryManager;
import com.tibco.cep.query.stream.io.StaticSink;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.query.snapshot.TruncatedBridge;
import com.tibco.cep.query.stream.truncate.TruncatedStream;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 6:27:33 PM
 */

public class ReteSSEvtTruncBridgeTest extends AbstractCacheTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        ClassLoader oldCL = Thread.currentThread().getContextClassLoader();
        try {
            Manager.ManagerInput managerInput = (Manager.ManagerInput) master.getProperties()
                    .get(Manager.ManagerInput.KEY_INPUT);

            ClassLoader loader = getEntityClassLoader();
            Thread.currentThread().setContextClassLoader(loader);

            runTest();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            Thread.currentThread().setContextClassLoader(oldCL);
        }
    }

    protected void runTest() throws Exception {

        // ----------

        final ReteEntityInfo reteEntityInfo = eventHelper.getReteEntityInfo(personEventClass);
        ReteEntitySource source = new ReteEntitySourceImpl(new ResourceId("source"),
                reteEntityInfo, personEventClass);
        sources.put("source1", source);

        TruncatedBridge bridge =
                new TruncatedBridge(source.getInternalStream(), new ResourceId("bridge"));

        TruncatedStream truncatedStream =
                new TruncatedStream(new ResourceId("Truncater"), reteEntityInfo, 12, 3);

        bridge.setup(truncatedStream);

        sink = new StaticSink(truncatedStream, new ResourceId("Sink"));

        // ----------

        ReteQuery query = getReteQuery("AgeCounterQry", new ReteEntitySource[]{source});

        SnapshotQueryManager ssQueryManager = regionManager.getSSQueryManager();

        try {
            query.init(repo, null);
            query.start();

            List<Entity> entities = eventHelper.addPersonEvents(0, 51200);

            ssQueryManager.registerAndStartFeeding(query, bridge);

            waitForQueryToComplete(query);

            List<Object[]> expectedResults = new ArrayList<Object[]>();
            for (Entity entity : entities) {
                expectedResults.add(new Object[]{entity});
            }

            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStaticSink(expectedResults, 10);

            Assert.assertEquals(received.size(), truncatedStream.getFirstX(query.getContext()),
                    "Incorrect number of elements received.");

            HashSet<Event> receivedAndExtracted = new HashSet<Event>();
            for (Object obj : received.keySet()) {
                Tuple tuple = (Tuple) obj;
                Event e = (Event) tuple.getColumn(0);

                int age = (Integer) ((SimpleEvent) e).getProperty("age");
                long id = e.getId();
                System.err.println("Id: " + id + ", Age: " + age);

                Assert.assertTrue(receivedAndExtracted.add(e), "Duplicate element received.");
            }
        }
        finally {
            ssQueryManager.stopFeedingAndUnregister(query);
        }
    }

    @Override
    public ReteQuery getReteQuery(String resourceId, Source[] sources) {
        return new ReteQueryImpl(master.getAgentService().getName(), new ResourceId(resourceId),
                (ReteEntitySource[])sources, sink, true, null);
    }
}