/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.query.stream.rete;

import com.tangosol.net.NamedCache;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.core.DefaultTupleValueExtractor;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.expression.*;
import com.tibco.cep.query.stream.framework.AbstractCacheTest;
import com.tibco.cep.query.stream.framework.ConceptHelper;
import com.tibco.cep.query.stream.framework.JoinedTupleInfoImpl;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.impl.rete.ReteEntityInfo;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySource;
import com.tibco.cep.query.stream.impl.rete.ReteEntityWithdrawableSource;
import com.tibco.cep.query.stream.impl.rete.ReteEntityWithdrawableSourceImpl;
import com.tibco.cep.query.stream.impl.rete.join.JoinedStreamImpl;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.query.ReteQueryImpl;
import com.tibco.cep.query.stream.impl.rete.service.ReteEntityDispatcher;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.join.JoinedTupleInfo;
import com.tibco.cep.query.stream.monitor.QueryWatcher;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.sort.SortInfo;
import com.tibco.cep.query.stream.sort.SortItemInfo;
import com.tibco.cep.query.stream.sort.SortedStream;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.query.stream.util.FixedKeys;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomStringSimple;
import com.tibco.cep.runtime.service.cluster.system.impl.ObjectTupleImpl;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransaction;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

/*
 * Author: Ashwin Jayaprakash Date: Mar 12, 2008 Time: 12:05:18 PM
 */

public class ReteCQNullSimulatorTest extends AbstractCacheTest {
    private int customerId;

    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        ClassLoader oldCL = Thread.currentThread().getContextClassLoader();
        customerId = classToTypeMap.get("be.gen.Concepts.Customer");
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

    @Override
    public ReteQuery getReteQuery(String resourceId, Source[] sources) {
        return new ReteQueryImpl(master.getAgentService().getName(), new ResourceId(resourceId),
                (ReteEntitySource[]) sources, sink, false, null);
    }

    protected void runTest() throws Exception {
        System.err.println("+-----------------------------------------------+");
        System.err.println("| Observe the logs. You should see 2 exceptions |");
        System.err.println("+-----------------------------------------------+");

        // ----------

        TupleValueExtractor[] extractors = new DefaultTupleValueExtractor[1];
        extractors[0] = new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                Object obj = ((Tuple) tuple.getColumn(0)).getColumn(0);

                Object addressRef = ((Concept) obj).getProperty("addresses");

                PropertyArrayContainedConcept addressConcepts =
                        (PropertyArrayContainedConcept) addressRef;

                PropertyAtom addressAtom = addressConcepts.get(1);

                Concept address = (Concept) addressAtom.getValue();

                PropertyAtomStringSimple atomString =
                        (PropertyAtomStringSimple) address.getProperty("country");

                return atomString.getString();
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
                } else if (o2 == FixedKeys.NULL) {
                    return 1;
                }

                String thisVal = (String) o1;
                String anotherVal = (String) o2;

                return thisVal.compareTo(anotherVal);
            }
        });

        // ----------

        String[] columnNames = new String[]{"customer"};
        Class[] columnTypes = new Class[]{customerClass};
        final ReteEntityInfo reteEntityInfo = new ReteEntityInfoImpl(ConceptHelper.CustomerTuple.class, columnNames,
                columnTypes);
        ReteEntityWithdrawableSource source = new ReteEntityWithdrawableSourceImpl(new ResourceId(
                "source"), reteEntityInfo, customerClass);
        sources.put("source1", source);

        // ----------

        HashMap<String, Class<? extends Tuple>> aliases =
                new HashMap<String, Class<? extends Tuple>>();
        aliases.put("source1", ConceptHelper.CustomerTuple.class);

        ComplexExpression joinExpression =
                new ComplexAndExpression(new Expression[]{new JoinExpr(aliases)});

        final JoinedTupleInfo joinInfo = new JoinedTupleInfoImpl(ConceptHelper.CustomerAndOrderTuple.class,
                new String[]{"source1"}, new Class[]{ConceptHelper.CustomerTuple.class});

        Map<String, Stream> joinSources = new HashMap<String, Stream>();
        joinSources.put("source1", source.getInternalStream());
        JoinedStreamImpl joinedStream =
                new JoinedStreamImpl(master.getAgentService().getName(), "CustomerJoinQry",
                        new ResourceId("join"), joinSources, null, joinExpression, joinInfo);

        // ----------

        SortInfo sortInfo = new SortInfo(new SortItemInfo[]{new SortItemInfo(true)});
        SortedStream sortedStream = new SortedStream(joinedStream, new ResourceId(
                "sort"), sortInfo, extractors, comparators);

        // ----------

        sink = new StreamedSink(sortedStream, new ResourceId("Sink"));

        ReteQuery query = getReteQuery("CustomerJoinQry", new ReteEntitySource[]{source});

        ReteEntityDispatcher dispatcher = regionManager.getDispatcher();

        try {
            query.init(repo, null);

            dispatcher.registerQuery(query);
            query.start();

            Thread.sleep(4 * 1000);

            List<Entity> entities = doAdds();

            //---------

            List<Object[]> expectedResults = new ArrayList<Object[]>();
            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStreamedSink(expectedResults, 10);

            Assert.assertEquals(received.size(), 0, "No results were expected.");

            QueryWatcher.Run run1 =
                    query.getContext().getQueryContext().getQueryWatcher().pollOldestRun();
            Throwable t1 = run1.getError();
            Assert.assertNotNull(t1, "First run was supposed to throw an exception.");

            QueryWatcher.Run run2 =
                    query.getContext().getQueryContext().getQueryWatcher().pollOldestRun();
            QueryWatcher.Status status = run2.getStatus();
            Assert.assertEquals(status, QueryWatcher.Status.SUCCESS, "Second run was supposed " +
                    "succeed but with an error logged.");

            Assert.assertNotSame(run1, run2, "2 distinct runs were expected.");
        }
        finally {
            query.stop();
        }
    }

    private List<Entity> doAdds() throws Exception {
        int customerId = classToTypeMap.get("be.gen.Concepts.Customer");
        int addressId = classToTypeMap.get("be.gen.Concepts.Address");
        LinkedList<Entity> createdEvents = new LinkedList<Entity>();

        NamedCache customerCache = conceptHelper.getEntityCache(customerClass);
        NamedCache addressCache = conceptHelper.getEntityCache(addressClass);
        Map<Long, ObjectTupleImpl> objTableEntries = new HashMap<Long, ObjectTupleImpl>();

        int idGen = -1;
        for (int i = 0; i < 2; i++) {
            idGen++;
            int custId = idGen;
            Object customer = conceptHelper.addCustomer(custId, 30 - i);

            idGen++;
            int address1Id = idGen;
            Object address1 = conceptHelper.addAddress(address1Id, "USA");

            idGen++;
            int address2Id = idGen;
            Object address2 = conceptHelper.addAddress(address2Id, "Timbuctoo");

            conceptHelper.addAddressToCustomer(customer, address1, address2);

            objTableEntries.put((long) address1Id, new ObjectTupleImpl(address1Id, ((Concept) address1).getExtId(), addressId));
            objTableEntries.put((long) address2Id, new ObjectTupleImpl(address2Id, ((Concept) address2).getExtId(), addressId));

            // ----------

            customerCache.put(new Long(custId), customer);
            createdEvents.add((Entity) customer);

            objTableEntries.put((long) custId, new ObjectTupleImpl(custId, ((Concept) customer).getExtId(), customerId));

            /*
             * Test to see how the error messages get propagated.
             * There are 2 stages: Join and then Sort.
             */
            if (i == 0) {
                //Passes Join, but Fails at sort.
                addressCache.put(new Long(address1Id), address1);
            } else {
                //Fails at Join.
                addressCache.put(new Long(address2Id), address2);
            }
            RtcTransaction txn = rtcTransactionHelper.createNewRtcTransaction();
            rtcTransactionHelper.addConcept(txn, (Concept) customer, customerId);
            conceptHelper.updateObjectTable(objTableEntries, txn);
        }

        return createdEvents;
    }

    public static class JoinExpr extends AbstractExpression implements EvaluatableExpression {
        /**
         * @param aliasAndTypes Stream alias and the Tuple types.
         */
        public JoinExpr(Map<String, Class<? extends Tuple>> aliasAndTypes) {
            super(aliasAndTypes);
        }

        public Object evaluate(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            return evaluateBoolean(globalContext, queryContext, aliasAndTuples);
        }

        public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext,
                                       FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            Tuple tuple = aliasAndTuples.get("source1");

            Object obj = tuple.getColumn(0);

            Object addressRef = ((Concept) obj).getProperty("addresses");

            PropertyArrayContainedConcept addressConcepts =
                    (PropertyArrayContainedConcept) addressRef;

            PropertyAtom addressAtom = addressConcepts.get(0);

            Concept address = (Concept) addressAtom.getValue();

            PropertyAtomStringSimple atomString =
                    (PropertyAtomStringSimple) address.getProperty("country");

            return atomString.getString().length() > 0;
        }

        public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            return 0;
        }

        public long evaluateLong(GlobalContext globalContext, QueryContext queryContext,
                                 FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            return 0;
        }

        public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            return 0;
        }

        public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext,
                                     FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            return 0;
        }
    }
}