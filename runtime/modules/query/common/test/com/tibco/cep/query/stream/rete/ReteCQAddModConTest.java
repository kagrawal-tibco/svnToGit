package com.tibco.cep.query.stream.rete;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.core.DefaultTupleValueExtractor;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.framework.AbstractCacheTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.impl.rete.*;
import com.tibco.cep.query.stream.impl.rete.integ.Manager;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.query.ReteQueryImpl;
import com.tibco.cep.query.stream.impl.rete.service.ReteEntityDispatcher;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.sort.SortInfo;
import com.tibco.cep.query.stream.sort.SortItemInfo;
import com.tibco.cep.query.stream.sort.SortedStream;
import com.tibco.cep.query.stream.tuple.HeavyTuple;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeys;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomIntSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomStringSimple;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

/*
 * Author: Ashwin Jayaprakash Date: Mar 12, 2008 Time: 12:05:18 PM
 */

public class ReteCQAddModConTest extends AbstractCacheTest {
    private int customerId, addressId;
    
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        customerId = classToTypeMap.get("be.gen.Concepts.Customer");
        addressId = classToTypeMap.get("be.gen.Concepts.Address");
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
                        (ReteEntitySource[])sources, sink, false, null);        
    }

    protected void runTest() throws Exception {
        // ----------
        TupleValueExtractor[] extractors = new DefaultTupleValueExtractor[1];
        extractors[0] = new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                PropertyAtomIntSimple atomIntSimple = (PropertyAtomIntSimple) ((Concept) tuple
                        .getColumn(0)).getProperty("age");

                return atomIntSimple.getInt();
            }
        };

        List<Comparator<Object>> comparators = new ArrayList<Comparator<Object>>(extractors.length);
        comparators.add(new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                if (o1 == FixedKeys.NULL) {
                    if (o2 == FixedKeys.NULL) {
                        return 0;
                    }

                    // Nulls first.
                    return -1;
                }
                else if (o2 == FixedKeys.NULL) {
                    return 1;
                }

                int thisVal = (Integer) o1;
                int anotherVal = (Integer) o2;

                return (thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1));
            }
        });

        // ----------

        String[] columnNames = new String[]{"customer"};
        Class[] columnTypes = new Class[]{customerClass};
        final ReteEntityInfo reteEntityInfo =
                new ReteEntityInfoImpl(CustomerTuple.class, columnNames,
                        columnTypes);
        ReteEntityWithdrawableSource source = new ReteEntityWithdrawableSourceImpl(new ResourceId(
                "source"), reteEntityInfo, customerClass);
        sources.put("source1", source);

        // ----------

        SortInfo sortInfo = new SortInfo(new SortItemInfo[]{new SortItemInfo(true)});
        SortedStream sortedStream = new SortedStream(source.getInternalStream(), new ResourceId(
                "sort"), sortInfo, extractors, comparators);

        // ----------

        sink = new StreamedSink(sortedStream, new ResourceId("Sink"));

        ReteQuery query = getReteQuery("CustomerQry", new ReteEntitySource[]{source});

        ReteEntityDispatcher dispatcher = regionManager.getDispatcher();

        try {
            query.init(repo, null);

            dispatcher.registerQuery(query);
            query.start();

            List<Entity> entities = conceptHelper.addCustomerAndAddress(5);

            Thread.sleep(4 * 1000);
            List<Object[]> expectedResults = new ArrayList<Object[]>();
            LinkedIdentityHashMap<Tuple, Object[]> received = collectAndMatchStreamedSink(expectedResults,
                    10);

            LinkedList<Tuple> lastSet = new LinkedList<Tuple>(received.keySet());
            while (lastSet.size() > 5) {
                lastSet.removeFirst();
            }

            Concept result = (Concept) lastSet.get(0).getColumn(0);
            Object[] results = extractArray(result);
            Assert.assertTrue(Arrays.equals(results, new Object[]{"fn: 12", 26, "USA",
                    "Timbuctoo"}), "Values do not match: " + Arrays.asList(results));

            result = (Concept) lastSet.get(1).getColumn(0);
            results = extractArray(result);
            Assert.assertTrue(Arrays.equals(results,
                    new Object[]{"fn: 9", 27, "USA", "Timbuctoo"}), "Values do not match: "
                    + Arrays.asList(results));

            result = (Concept) lastSet.get(2).getColumn(0);
            results = extractArray(result);
            Assert.assertTrue(Arrays.equals(results,
                    new Object[]{"fn: 6", 28, "USA", "Timbuctoo"}), "Values do not match: "
                    + Arrays.asList(results));

            result = (Concept) lastSet.get(3).getColumn(0);
            results = extractArray(result);
            Assert.assertTrue(Arrays.equals(results,
                    new Object[]{"fn: 3", 29, "USA", "Timbuctoo"}), "Values do not match: "
                    + Arrays.asList(results));

            result = (Concept) lastSet.get(4).getColumn(0);
            results = extractArray(result);
            Assert.assertTrue(Arrays.equals(results,
                    new Object[]{"fn: 0", 30, "USA", "Timbuctoo"}), "Values do not match: "
                    + Arrays.asList(results));

            Concept lastCustomer = (Concept) lastSet.get(4).getColumn(0);
            Long lastCustomerId = lastCustomer.getId();

            System.err.println("==============");
            System.err.println("Sending modifications...");

            conceptHelper.deleteAndModifyCustomer(entities);

            expectedResults = new ArrayList<Object[]>();
            received = collectAndMatchStreamedSink(expectedResults, 10);

            lastSet = new LinkedList<Tuple>(received.keySet());
            while (lastSet.size() > 5) {
                lastSet.removeFirst();
            }

            // From previous sequence.
            result = (Concept) lastSet.get(0).getColumn(0);
            results = extractArray(result);
            Assert.assertTrue(Arrays.equals(results, new Object[]{"fn: 12", -26, "USA",
                    "Timbuctoo"}), "Values do not match: " + Arrays.asList(results));

            result = (Concept) lastSet.get(1).getColumn(0);
            results = extractArray(result);
            Assert.assertTrue(Arrays.equals(results, new Object[]{"fn: 3", -29, "USA",
                    "Timbuctoo"}), "Values do not match: " + Arrays.asList(results));

            result = (Concept) lastSet.get(2).getColumn(0);
            results = extractArray(result);
            Assert.assertTrue(Arrays.equals(results, new Object[]{"fn: 6", -28, "USA",
                    "Timbuctoo"}), "Values do not match: " + Arrays.asList(results));

            result = (Concept) lastSet.get(3).getColumn(0);
            results = extractArray(result);
            Assert.assertTrue(Arrays.equals(results, new Object[]{"fn: 9", -27, "USA",
                    "Timbuctoo"}), "Values do not match: " + Arrays.asList(results));

            result = (Concept) lastSet.get(4).getColumn(0);
            results = extractArray(result);
            Assert.assertTrue(Arrays.equals(results, new Object[]{"fn: 12", -26, "USA",
                    "Timbuctoo"}), "Values do not match: " + Arrays.asList(results));

            Cache primary = regionManager.getSOSRepository().getPrimaryCache();
            Object oPrimary = primary.get(lastCustomerId);
            Assert.assertNull(oPrimary, "Last customer should not present in the Primary cache");

            Cache dead = regionManager.getSOSRepository().getDeadPoolCache();
            Concept oDead = (Concept) dead.get(lastCustomerId);
            results = extractArray(oDead);
            Assert.assertTrue(Arrays.equals(results,
                    new Object[]{"fn: 0", 30, "USA", "Timbuctoo"}), "Values do not match: "
                    + Arrays.asList(results));
        }
        finally {
            query.stop();
        }
    }

    private Object[] extractArray(Concept result) {
        String name = (String) ((PropertyAtomStringSimple) result.getProperty("name")).getValue();
        int age = ((PropertyAtomIntSimple) result.getProperty("age")).getInt();
        PropertyArrayContainedConcept addressConcepts = (PropertyArrayContainedConcept) result
                .getProperty("addresses");
        String address1 = ((PropertyAtomStringSimple) ((Concept) addressConcepts.get(0).getValue())
                .getProperty("country")).getString();
        String address2 = ((PropertyAtomStringSimple) ((Concept) addressConcepts.get(1).getValue())
                .getProperty("country")).getString();

        Object[] results = {name, age, address1, address2};
        return results;
    }

    public static class CustomerTuple extends HeavyReteEntity {
        public CustomerTuple(Number id) {
            super(id);
        }
    }

    public static class OutputTuple extends HeavyTuple {
        public OutputTuple(Number id) {
            super(id);
        }

        public OutputTuple(Long id, Object[] columns) {
            super(id, columns);
        }
    }
}