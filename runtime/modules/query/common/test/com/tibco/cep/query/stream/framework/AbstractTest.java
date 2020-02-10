package com.tibco.cep.query.stream.framework;

import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.core.Sink;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.impl.query.Helper;
import com.tibco.cep.query.stream.impl.rete.ReteEntityClassHierarchyHelper;
import com.tibco.cep.query.stream.impl.rete.integ.Manager;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.service.RegionManager;
import com.tibco.cep.query.stream.io.StaticSink;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.monitor.Flags;
import com.tibco.cep.query.stream.query.continuous.ContinuousQuery;
import com.tibco.cep.query.stream.tuple.Tuple;
import org.testng.Assert;
import org.testng.TestNGException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.TimeUnit;

/*
 * Author: Ashwin Jayaprakash Date: Dec 20, 2007 Time: 1:33:37 PM
 */

public class AbstractTest {
    protected TestSession testSession;

    protected CustomMaster master;

    protected SharedObjectSourceRepository repo;

    protected RegionManager regionManager;

    protected Map<String, Source> sources;

    protected Sink sink;

    protected String queryName;

    protected ReteQuery getReteQuery(String resourceId, Source[] sources) {
        return null;
    }

    public CustomMaster createCustomMaster() throws Exception {
        return new CustomMaster(testSession);
    }

    @BeforeMethod(groups = {TestGroupNames.RUNTIME})
    public void beforeTest() {
        if (Flags.DEV_MODE == false || Flags.TRACK_TUPLE_REFS == false) {
            System.err.println("+-------------------------------+");
            System.err.println("| DEVELOPMENT mode not enabled! |");
            System.err.println("+-------------------------------+");
        }

        testSession = new TestSession();
        TrackedTuple.setTestSession(testSession);

        try {
            master = createCustomMaster();
            master.start(master.getProperties());
        }
        catch (Exception e) {
            throw new TestNGException(e);
        }

        //------------

        Manager manager = Registry.getInstance().getComponent(Manager.class);
        RegionManager rm = manager.getRegionManagers().values().iterator().next();

        regionManager = wrapRM(rm);

        repo = regionManager.getSOSRepository();

        sources = new HashMap<String, Source>();
    }

    private RegionManager wrapRM(RegionManager rm) {
        RegionManagerInterceptor proxiedRMHandler = provideRegionManagerInterceptor(rm);

        Object proxiedRM = Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{RegionManager.class}, proxiedRMHandler);

        String regionName = rm.getRegionName();
        Registry.getInstance().getComponent(Manager.class).getRegionManagers()
                .put(regionName, (RegionManager) proxiedRM);

        return (RegionManager) proxiedRM;
    }

    protected RegionManagerInterceptor provideRegionManagerInterceptor(final RegionManager rm) {
        return new RegionManagerInterceptor(rm);
    }

    @AfterMethod(groups = {TestGroupNames.RUNTIME}, alwaysRun = true)
    public void afterTest() {
        TrackedTuple.setTestSession(null);
        TrackedCache.setTestSession(null);

        try {
            master.stop();
            master = null;
        }
        catch (Exception e) {
            throw new TestNGException(e);
        }

        testSession.discard();
        testSession = null;

        repo = null;
        regionManager = null;

        sources.clear();
        sources = null;
        sink = null;
    }

    protected void commonTests() {
        commonTests(null);
    }

    protected void commonTests(Set<Tuple> except) {
        Map<Number, Tuple> tuples = testSession.getTuples();
        for (Tuple tuple : tuples.values()) {
            if (except != null && except.contains(tuple)) {
                continue;
            }

            if (tuple instanceof TrackedTuple) {
                TrackedTuple trackedTuple = (TrackedTuple) tuple;

                if (trackedTuple.getRefCount() != 0) {
                    Assert.fail("Ref count is not 0: " + tuple);
                }
            }
        }
    }

    /**
     * @param expectedResults
     * @param orderMatters    Checks to see if the received results are in the same order as the
     *                        expected results.
     * @param timeoutSeconds
     * @return
     * @throws InterruptedException
     */
    protected LinkedIdentityHashMap<Tuple, Object[]> collectAndMatchStreamedSink(
            List<Object[]> expectedResults, boolean orderMatters, long timeoutSeconds)
            throws InterruptedException {
        System.err.println();
        System.err.println("Receiving...");

        LinkedIdentityHashMap<Tuple, Object[]> results =
                new LinkedIdentityHashMap<Tuple, Object[]>(16, 0.6f, true);

        StreamedSink streamedSink = (StreamedSink) sink;

        Tuple marker = streamedSink.getBatchEndMarker();
        Tuple tuple = null;

        while ((tuple = streamedSink.poll(timeoutSeconds, TimeUnit.SECONDS)) != null) {
            if (tuple == marker) {
                System.err.println("-----");
                continue;
            }

            results.put(new TupleIdentityWrapper(tuple), null);

            Object[] received = tuple.getRawColumns();
            String s = "";
            for (int i = 0; i < received.length; i++) {
                s = s + " " + tuple.getColumn(i);
            }

            System.err.println(tuple.getId() + "::" + s);

            for (Iterator<Object[]> iter = expectedResults.iterator(); iter.hasNext();) {
                Object[] row = iter.next();
                if (Arrays.equals(received, row)) {
                    iter.remove();
                    results.put(new TupleIdentityWrapper(tuple), row);

                    break;
                }
                else if (orderMatters) {
                    Assert.fail("Tuple received in wrong order.");
                }
            }
        }

        return results;
    }

    /**
     * @param expectedResults Matched rows will be removed.
     * @return List of Tuples received along with the value it matched with.
     * @throws InterruptedException
     */
    protected LinkedIdentityHashMap<Tuple, Object[]> collectAndMatchStreamedSink(
            List<Object[]> expectedResults, long timeoutSeconds) throws InterruptedException {
        return collectAndMatchStreamedSink(expectedResults, false, timeoutSeconds);
    }

    protected LinkedIdentityHashMap<Tuple, Object[]> collectAndMatchStaticSink(
            List<Object[]> expectedResults, int timeoutSeconds) throws InterruptedException {
        return collectAndMatchStaticSink(expectedResults, false, timeoutSeconds);
    }

    protected LinkedIdentityHashMap<Tuple, Object[]> collectAndMatchStaticSink(
            List<Object[]> expectedResults, boolean orderMatters, int timeoutSeconds)
            throws InterruptedException {
        System.err.println();
        System.err.println("Receiving...");

        LinkedIdentityHashMap<Tuple, Object[]> results =
                new LinkedIdentityHashMap<Tuple, Object[]>(16, 0.6f, true);

        Collection<? extends Tuple> tuples = ((StaticSink) sink).pollFinalResult(timeoutSeconds,
                TimeUnit.SECONDS);
        if (tuples != null) {
            for (Tuple tuple : tuples) {
                results.put(new TupleIdentityWrapper(tuple), null);

                Object[] received = tuple.getRawColumns();

                String s = "";
                for (int i = 0; i < received.length; i++) {
                    s = s + " " + tuple.getColumn(i);
                }
                System.err.println(tuple.getId() + "::" + s);

                for (Iterator<Object[]> iter = expectedResults.iterator(); iter.hasNext();) {
                    Object[] row = iter.next();
                    if (Arrays.equals(received, row)) {
                        iter.remove();
                        results.put(new TupleIdentityWrapper(tuple), row);

                        break;
                    }
                    else if (orderMatters) {
                        Assert.fail("Tuple received in wrong order.");
                    }
                }
            }
        }

        return results;
    }

    protected void verifyCollection(List<Object[]> assumedEmptyExpectedResults,
                                    LinkedIdentityHashMap<Tuple, Object[]> received) {
        Assert.assertFalse(received.isEmpty(), "No tuples received.");

        Set<Tuple> tuples = received.keySet();
        for (Tuple tuple : tuples) {
            Object[] match = received.get(tuple);
            Assert.assertNotNull(match, "No match found for tuple: " + tuple);

            tuple.decrementRefCount();
        }

        Assert.assertEquals(assumedEmptyExpectedResults.size(), 0,
                "Not all expected results arrived.");
    }

    protected void waitForQueryToComplete(ContinuousQuery query) throws InterruptedException {
        Helper.waitForQueryCompletion(query);
    }

    protected ClassLoader getEntityClassLoader() {
        return master.getAgentService().getEntityClassLoader();
    }

    //----------

    protected static class TupleIdentityWrapper {
        protected final Tuple tuple;

        public TupleIdentityWrapper(Tuple tuple) {
            this.tuple = tuple;
        }

        public Tuple getTuple() {
            return tuple;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            TupleIdentityWrapper that = (TupleIdentityWrapper) o;

            if (tuple != that.tuple) {
                return false;
            }

            return true;
        }

        public int hashCode() {
            return (tuple != null ? System.identityHashCode(tuple) : 0);
        }
    }

    protected static class IdentitySet extends AbstractSet<Tuple> {
        protected LinkedList<Tuple> list;

        public IdentitySet() {
            this.list = new LinkedList<Tuple>();
        }

        @Override
        public boolean add(Tuple t) {
            list.add(t);

            return true;
        }

        public Iterator<Tuple> iterator() {
            return list.iterator();
        }

        public int size() {
            return list.size();
        }
    }

    protected static class LinkedIdentityHashMap<K extends Tuple, V>
            extends LinkedHashMap<Object, V> {
        public LinkedIdentityHashMap(int initialCapacity, float loadFactor, boolean accessOrder) {
            super(initialCapacity, loadFactor, accessOrder);
        }

        @Override
        public V get(Object key) {
            return super.get(new TupleIdentityWrapper((Tuple) key));
        }

        /**
         * @return {@link com.tibco.cep.query.stream.framework.AbstractTest.IdentitySet}.
         */
        @Override
        public Set keySet() {
            IdentitySet set = new IdentitySet();
            for (Object o : super.keySet()) {
                TupleIdentityWrapper wrapper = (TupleIdentityWrapper) o;
                set.add(wrapper.getTuple());
            }

            return set;
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

    public static class RegionManagerInterceptor implements InvocationHandler {
        protected RegionManager defaultRM;

        protected HeirarchyHelper heirarchyHelper;

        public RegionManagerInterceptor(RegionManager defaultRM) {
            this.defaultRM = defaultRM;
            this.heirarchyHelper = new HeirarchyHelper();
        }

        public RegionManager getDefaultRM() {
            return defaultRM;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("getHierarchyHelper")) {
                return heirarchyHelper;
            }

            return method.invoke(defaultRM, args);
        }
    }
}