package com.tibco.cep.query.stream.rete.perf;

import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.query.exec.prebuilt.HeavyReteEntities;
import com.tibco.cep.query.stream.framework.AbstractCacheTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.impl.rete.ReteEntityInfo;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySource;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySourceImpl;
import com.tibco.cep.query.stream.impl.rete.integ.Manager;
import com.tibco.cep.query.stream.impl.rete.query.ReteQueryImpl;
import com.tibco.cep.query.stream.impl.rete.service.SnapshotQueryManager;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.query.snapshot.Bridge;
import com.tibco.cep.query.stream.query.snapshot.PassThruBridge;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.CustomHashSet;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 6:27:33 PM
 */

public class ReteSSAddEvtPassThruBridgePerfTest extends AbstractCacheTest {
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
        Class personEventClass = Class.forName("be.gen.PersonEvent");

        master.getAgentService().getTypeToClassMap().put(1, personEventClass);

        // ----------

        String[] columnNames = new String[]{"personEvent"};
        Class[] columnTypes = new Class[]{personEventClass};
        ReteEntityInfo reteEntityInfo =
                new HeavyReteEntities.HeavyReteEntityInfo(columnNames, columnTypes, 1);

        ReteEntitySource source = new ReteEntitySourceImpl(new ResourceId("source"),
                reteEntityInfo, personEventClass);
        sources.put("source1", source);

        Bridge bridge = new PassThruBridge(source.getInternalStream(), new ResourceId("bridge"));

        //Special case - not static-sink.
        sink = new StreamedSink(new ResourceId("Sink"), reteEntityInfo);

        bridge.setup(sink.getInternalStream());

        // ----------

        ReteQueryImpl query =
                new ReteQueryImpl(master.getAgentService().getName(), new ResourceId("PassThruQry"),
                        new ReteEntitySource[]{source}, sink, true, null);

        SnapshotQueryManager ssQueryManager = regionManager.getSSQueryManager();

        try {
            query.init(repo, null);
            query.start();

            sendAndMeasure(personEventClass, bridge, query, ssQueryManager);
        }
        finally {
            ssQueryManager.stopFeedingAndUnregister(query);
        }
    }

    private void sendAndMeasure(Class personEventClass, Bridge bridge, ReteQueryImpl query,
                                SnapshotQueryManager ssQueryManager) throws Exception {
        final int totalEvents = 500000;

        System.err.println("Starting to send..");
        long startTime = System.currentTimeMillis();
        eventHelper.addPersonEvents(1, totalEvents);
        long time = System.currentTimeMillis() - startTime;
        System.err.println("Sent. Time: " + time + "(millis) for " + totalEvents + " events.");

        //----------

        StreamedSink streamedSink = (StreamedSink) sink;
        Tuple marker = streamedSink.getBatchEndMarker();
        Tuple tuple = null;

        System.err.println("Starting query..");
        startTime = System.currentTimeMillis();

        ssQueryManager.registerAndStartFeeding(query, bridge);

        long endTime = pollAndCollect(totalEvents, streamedSink, marker);

        time = endTime - startTime;
        System.err.println("Received. Time: " + time + "(millis) for " + totalEvents + " events.");
    }

    private long pollAndCollect(int totalEvents, StreamedSink streamedSink, Tuple marker)
            throws InterruptedException {
        Tuple tuple;
        int receivedCount = 0;

        long endTime = 0;

        CustomHashSet<Long> ids = new CustomHashSet<Long>(totalEvents, 0.5f);

        while ((tuple = streamedSink.poll(10, TimeUnit.SECONDS)) != null) {
            if (tuple == marker) {
                continue;
            }

            Event e = (Event) tuple.getColumn(0);

            if (ids.add(e.getId()) == false) {
                Assert.fail("Duplicate event:" + e);
            }

            receivedCount++;
            if (receivedCount == totalEvents) {
                //Capture the time but make sure there aren't any more events.
                endTime = System.currentTimeMillis();
            }

            if (receivedCount > totalEvents) {
                Assert.fail("Wrong number of events received. Expected " + totalEvents +
                        ", but received more: " + e);
            }
        }

        Assert.assertEquals(ids.size(), totalEvents, "Not all events were received.");
        ids.clear();

        return endTime;
    }
}