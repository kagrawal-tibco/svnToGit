package com.tibco.cep.query.stream.rete;

import com.tibco.cep.kernel.model.entity.Entity;
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
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.query.snapshot.Bridge;
import com.tibco.cep.query.stream.query.snapshot.PassThruBridge;
import com.tibco.cep.query.stream.tuple.Tuple;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 6:27:33 PM
 */

public class ReteSSAddEvtPassThruBridgeTest extends AbstractCacheTest {

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

    @Override
    public ReteQuery getReteQuery(String resourceId, Source[] sources) {
        return new ReteQueryImpl(master.getAgentService().getName(), new ResourceId(resourceId),
                (ReteEntitySource[])sources, sink, true, null);        
    }

    protected void runTest() throws Exception {
        // ----------
        final ReteEntityInfo reteEntityInfo = eventHelper.getReteEntityInfo(personEventClass);
        ReteEntitySource source = new ReteEntitySourceImpl(new ResourceId("source"),
                reteEntityInfo, personEventClass);
        sources.put("source1", source);

        Bridge bridge = new PassThruBridge(source.getInternalStream(), new ResourceId("bridge"));

        //Special case - not static-sink.
        sink = new StreamedSink(new ResourceId("Sink"), reteEntityInfo);

        bridge.setup(sink.getInternalStream());

        // ----------

        ReteQuery query = getReteQuery("AgeCounterQry", new ReteEntitySource[]{source});

        SnapshotQueryManager ssQueryManager = regionManager.getSSQueryManager();

        try {
            query.init(repo, null);
            query.start();

            List<Entity> entities = eventHelper.addPersonEvents(0, 50997);

            ssQueryManager.registerAndStartFeeding(query, bridge);

            waitForQueryToComplete(query);

            List<Object[]> expectedResults = new ArrayList<Object[]>();
            for (Entity entity : entities) {
                expectedResults.add(new Object[]{entity});
            }

            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStreamedSink(expectedResults, 10);

            HashSet<Entity> receivedAndExtracted = new HashSet<Entity>();
            for (Object obj : received.keySet()) {
                Tuple t = (Tuple) obj;
                Object o = t.getColumn(0);
                receivedAndExtracted.add((Entity) o);
            }

            for (Entity e : entities) {
                boolean b = receivedAndExtracted.remove(e);
                Assert.assertTrue(b, "Entity: " + e + " is not present.");
            }

            Assert.assertEquals(receivedAndExtracted.size(), 0, "Extra elements received.");
        }
        finally {
            ssQueryManager.stopFeedingAndUnregister(query);
        }
    }
    
}