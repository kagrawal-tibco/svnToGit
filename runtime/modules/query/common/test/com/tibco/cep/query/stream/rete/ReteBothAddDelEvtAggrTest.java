package com.tibco.cep.query.stream.rete;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.framework.AbstractCacheTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySource;
import com.tibco.cep.query.stream.impl.rete.ReteEntityWithdrawableSource;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.query.ReteQueryImpl;
import com.tibco.cep.query.stream.impl.rete.query.SnapshotAssistant;
import com.tibco.cep.query.stream.impl.rete.service.ReteEntityDispatcher;
import com.tibco.cep.query.stream.impl.rete.service.SnapshotFeederForCQ;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 6:27:33 PM
 */

public class ReteBothAddDelEvtAggrTest extends AbstractCacheTest {

    private int personEventId;
    protected SnapshotAssistant assistant;

    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        personEventId = classToTypeMap.get("be.gen.PersonEvent");
        ClassLoader oldCL = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader loader = getEntityClassLoader();
            Thread.currentThread().setContextClassLoader(loader);

            runTest();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            Thread.currentThread().setContextClassLoader(oldCL);
        }
    }

    @Override
    public ReteQuery getReteQuery(String resourceId, Source[] sources) {
        return new ReteQueryImpl(master.getAgentService().getName(), new ResourceId(resourceId),
                (ReteEntitySource[]) sources, sink, assistant, new boolean[]{true}, null);
    }

    protected void runTest() throws Exception {

        ReteEntityWithdrawableSource source = getSource(personEventClass);
        sink = getSink(personEventClass, source);

        // ----------

        SnapshotFeederForCQ feederForCQ = new SnapshotFeederForCQ();

        assistant = feederForCQ.step1();

        ReteQuery query = getReteQuery("AgeCounterQry", new ReteEntitySource[]{source});

        feederForCQ.step2(query, repo, regionManager.getSSQueryManager().getExecutorService());

        // ----------

        ReteEntityDispatcher dispatcher = regionManager.getDispatcher();

        try {
            //Query starts even before data addition. So, it has to start accumulating.
            query.init(repo, null);
            query.start();

            //It is also registered before data addition starts.
            dispatcher.registerQuery(query);

            // ----------

            List<Entity> entities1 = eventHelper.addPersonEvents(0, 25000);

            // ----------

            //Now we start the snapshot fetching.
            feederForCQ.stepGo();

            // ----------

            List<Entity> entities2 = eventHelper.addPersonEvents(25000, 50000);

            List<Object[]> expectedResults = new ArrayList<Object[]>();
            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStreamedSink(expectedResults,
                            10);

            LinkedList<Tuple> lastSet = new LinkedList<Tuple>(received.keySet());
            while(lastSet.size() > 4) {
                lastSet.removeFirst();
            }

            Tuple tuple = lastSet.get(0);
            Assert.assertTrue(Arrays.equals(tuple.getRawColumns(), new Object[]{"Asimov", 12500,
                    312500, 25.0, 25, 25}), "Values do not match: " + tuple);

            tuple = lastSet.get(1);
            Assert.assertTrue(Arrays.equals(tuple.getRawColumns(), new Object[]{"Baxter", 12500,
                    312500, 25.0, 25, 25}), "Values do not match: " + tuple);

            tuple = lastSet.get(2);
            Assert.assertTrue(Arrays.equals(tuple.getRawColumns(), new Object[]{"Brin", 12500,
                    312500, 25.0, 25, 25}), "Values do not match: " + tuple);

            tuple = lastSet.get(3);
            Assert.assertTrue(Arrays.equals(tuple.getRawColumns(), new Object[]{"Clarke", 12500,
                    312500, 25.0, 25, 25}), "Values do not match: " + tuple);

            // ----------

            System.err.println("==============");
            System.err.println("Sending deletes...");

            List<Entity> allEntities = new LinkedList<Entity>();
            allEntities.addAll(entities1);
            allEntities.addAll(entities2);

            eventHelper.removeEntities(allEntities, personEventId);

            expectedResults = new ArrayList<Object[]>();
            received = collectAndMatchStreamedSink(expectedResults, 10);

            boolean[] allReceived = {false, false, false, false};
            for(Object obj : received.keySet()) {
                Tuple t = (Tuple) obj;
                if(Arrays.equals(t.getRawColumns(), new Object[]{"Asimov", 0, 0,
                        0.0, null, null})) {
                    allReceived[0] = true;
                } else if(Arrays.equals(t.getRawColumns(), new Object[]{"Baxter", 0, 0,
                        0.0, null, null})) {
                    allReceived[1] = true;
                } else if(Arrays.equals(t.getRawColumns(), new Object[]{"Brin", 0, 0,
                        0.0, null, null})) {
                    allReceived[2] = true;
                } else if(Arrays.equals(t.getRawColumns(), new Object[]{"Clarke", 0, 0,
                        0.0, null, null})) {
                    allReceived[3] = true;
                }
            }

            Assert.assertTrue(allReceived[0], "Asimov not received");
            Assert.assertTrue(allReceived[1], "Baxter not received");
            Assert.assertTrue(allReceived[2], "Brin not received");
            Assert.assertTrue(allReceived[3], "Clarke not received");
        }
        finally {
            query.stop();
        }
    }
}