package com.tibco.cep.query.stream.rete;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.framework.AbstractCacheTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.impl.expression.*;
import com.tibco.cep.query.stream.impl.rete.ReteEntityClassHierarchyHelper;
import com.tibco.cep.query.stream.impl.rete.ReteEntityInfo;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySource;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySourceImpl;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilter;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilterImpl;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.query.ReteQueryImpl;
import com.tibco.cep.query.stream.impl.rete.service.RegionManager;
import com.tibco.cep.query.stream.impl.rete.service.SnapshotQueryManager;
import com.tibco.cep.query.stream.io.StaticSink;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.query.snapshot.TruncatedBridge;
import com.tibco.cep.query.stream.truncate.TruncatedStream;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 6:27:33 PM
 */

public class ReteSSPreFilterTest extends AbstractCacheTest {

    private int personEventId;

    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        ClassLoader oldCL = Thread.currentThread().getContextClassLoader();
        personEventId = classToTypeMap.get("be.gen.PersonEvent");

        try {
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

        final int limit = 14;

        final ReteEntityInfo reteEntityInfo = eventHelper.getReteEntityInfo(personEventClass);

        ReteEntityFilter filter = new ReteEntityFilterImpl(createE2EAdapter());

        ReteEntitySource source = new ReteEntitySourceImpl(new ResourceId("source"),
                reteEntityInfo, personEventClass, filter);
        sources.put("source1", source);

        TruncatedBridge bridge =
                new TruncatedBridge(source.getInternalStream(), new ResourceId("bridge"));

        TruncatedStream truncatedStream =
                new TruncatedStream(new ResourceId("Truncater"), reteEntityInfo, limit, 300);

        bridge.setup(truncatedStream);

        sink = new StaticSink(truncatedStream, new ResourceId("Sink"));

        // ----------

        ReteQuery query =  getReteQuery("AgeCounterQry", new ReteEntitySource[]{source});

        SnapshotQueryManager ssQueryManager = regionManager.getSSQueryManager();

        try {
            query.init(repo, null);
            query.start();

            List<Entity> entities = eventHelper.addPersonEvents(
                    new String[]{"Clarke", "Baxter", "Asimov", "Brin", "Le Guin"}, 0, 51249);

            ssQueryManager.registerAndStartFeeding(query, bridge);

            waitForQueryToComplete(query);

            List<Object[]> expectedResults = new ArrayList<Object[]>();
            for (Entity entity : entities) {
                expectedResults.add(new Object[]{entity});
            }

            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStaticSink(expectedResults, 15);

            Assert.assertEquals(received.size(), truncatedStream.getFirstX(query.getContext()),
                    "Incorrect number of elements received.");

            HashSet<Event> receivedAndExtracted = new HashSet<Event>();
            for (Object obj : received.keySet()) {
                Tuple tuple = (Tuple) obj;
                Event e = (Event) tuple.getColumn(0);

                int age = (Integer) ((SimpleEvent) e).getProperty("age");
                long id = e.getId();

                String lastName = (String) ((SimpleEvent) e).getProperty("lastName");

                System.err.println("Id: " + id + ", Age: " + age + ", LName: " + lastName);

                Assert.assertEquals(lastName, "Baxter", "Wrong name received.");

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

    protected EvaluatorToExtractorAdapter createE2EAdapter() {
        TupleExtractor personColumnGetter = new TupleExtractor(0);

        ModelExtractor lastNamePropGetter = new ModelExtractor(ModelType.SIMPLE_EVENT, "lastName");
        ModelExtractor[] modelExtractors = new ModelExtractor[]{lastNamePropGetter};

        ChainedTupleExtractor tupleExtractor =
                new ChainedTupleExtractor(null, personColumnGetter, modelExtractors, null);

        ExtractorToEvaluatorAdapter lhs = new ExtractorToEvaluatorAdapter(tupleExtractor,
                EvaluatorToExtractorAdapter.FIXED_KEY_POSITION_IN_MAP);

        final String rhsName = "Baxter";
        ExpressionEvaluator rhs = new ConstantValueEvaluator(rhsName);

        SimpleEvaluator simpleEvaluator =
                new SimpleEvaluator(lhs, rhs, JavaType.STRING, Operation.EQUAL);

        return new EvaluatorToExtractorAdapter(simpleEvaluator);
    }

    @Override
    protected RegionManagerInterceptor provideRegionManagerInterceptor(
            RegionManager rm) {
        return new Interceptor(rm);
    }

    //---------------

    protected static class Interceptor extends RegionManagerInterceptor {
        protected HeirarchyHelper heirarchyHelper;

        public Interceptor(RegionManager defaultRM) {
            super(defaultRM);

            this.heirarchyHelper = new HeirarchyHelper();
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("getHierarchyHelper")) {
                return heirarchyHelper;
            }

            return method.invoke(defaultRM, args);
        }
    }

    protected static class HeirarchyHelper extends ReteEntityClassHierarchyHelper {
        public HeirarchyHelper() {
            super(null);
        }

        @Override
        public Class[] getSubClasses(Class reteEntityClass) {
            return new Class[]{reteEntityClass};
        }
    }
}