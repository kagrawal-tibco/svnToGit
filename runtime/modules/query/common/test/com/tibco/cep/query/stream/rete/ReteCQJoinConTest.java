package com.tibco.cep.query.stream.rete;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.expression.*;
import com.tibco.cep.query.stream.framework.AbstractCacheTest;
import com.tibco.cep.query.stream.framework.JoinedTupleInfoImpl;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.impl.rete.HeavyReteEntity;
import com.tibco.cep.query.stream.impl.rete.ReteEntityInfo;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySource;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySourceImpl;
import com.tibco.cep.query.stream.impl.rete.join.JoinedStreamImpl;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.query.ReteQueryImpl;
import com.tibco.cep.query.stream.impl.rete.service.ReteEntityDispatcher;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.join.HeavyJoinedTuple;
import com.tibco.cep.query.stream.join.JoinedTupleInfo;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyAtomLong;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomIntSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomStringSimple;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 6:27:33 PM
 */

public class ReteCQJoinConTest extends AbstractCacheTest {

    private ClassLoader entityCL;
    private int customerId, orderId;

    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        ClassLoader oldCL = Thread.currentThread().getContextClassLoader();
        customerId = classToTypeMap.get("be.gen.Concepts.Customer");
        orderId = classToTypeMap.get("be.gen.Concepts.Order");
        try {
            entityCL = getEntityClassLoader();
            Thread.currentThread().setContextClassLoader(entityCL);

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
        return new ReteQueryImpl(master.getAgentService().getName(),new ResourceId(resourceId),
                (ReteEntitySource[])sources, sink, false, null);
    }

    protected void runTest() throws Exception {
        // ----------

        String[] columnNames = new String[]{"customer"};
        Class[] columnTypes = new Class[]{customerClass};
        final ReteEntityInfo customerReteEntityInfo =
                new ReteEntityInfoImpl(CustomerTuple.class, columnNames,
                        columnTypes);
        ReteEntitySource customerSource = new ReteEntitySourceImpl(new ResourceId(
                "CustomerSource"), customerReteEntityInfo, customerClass);
        sources.put(customerSource.getResourceId().getId(), customerSource);

        columnNames = new String[]{"order"};
        columnTypes = new Class[]{orderClass};
        final ReteEntityInfo orderReteEntityInfo =
                new ReteEntityInfoImpl(OrderTuple.class, columnNames,
                        columnTypes);
        ReteEntitySource orderSource = new ReteEntitySourceImpl(new ResourceId(
                "OrderSource"), orderReteEntityInfo, orderClass);
        sources.put(orderSource.getResourceId().getId(), orderSource);

        // ----------

        HashMap<String, Class<? extends Tuple>> aliases =
                new HashMap<String, Class<? extends Tuple>>();
        aliases.put("customerStream", CustomerTuple.class);
        aliases.put("orderStream", OrderTuple.class);

        ComplexExpression joinExpression =
                new ComplexAndExpression(new Expression[]{new JoinExpr(aliases)});

        final JoinedTupleInfo joinInfo = new JoinedTupleInfoImpl(CustomerAndOrderTuple.class,
                new String[]{"customerStream", "orderStream"}, new Class[]{
                        CustomerTuple.class, OrderTuple.class});

        Map<String, Stream> joinSources = new HashMap<String, Stream>();
        joinSources.put("customerStream", customerSource.getInternalStream());
        joinSources.put("orderStream", orderSource.getInternalStream());
        JoinedStreamImpl joinedStream =
                new JoinedStreamImpl(master.getAgentService().getName(), "CustomerOrderJoinQry",
                        new ResourceId("Join"), joinSources, null, joinExpression, joinInfo);

        // ----------

        sink = new StreamedSink(joinedStream, new ResourceId("Sink"));

        ReteQuery query = getReteQuery("CustomerOrderJoinQry",
                new ReteEntitySource[]{customerSource, orderSource});

        ReteEntityDispatcher dispatcher = regionManager.getDispatcher();

        try {
            query.init(repo, null);
            query.start();

            dispatcher.registerQuery(query);

            List<Entity> entities = conceptHelper.addCustomerAndOrder(5);

            Thread.sleep(4 * 1000);
            List<Object[]> expectedResults = new ArrayList<Object[]>();
            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStreamedSink(expectedResults,
                            10);

            ArrayList<Tuple> lastSet = new ArrayList<Tuple>(received.keySet());

            Assert.assertEquals(lastSet.size(), 5, "Not all pairs were returned.");

            HashSet<Long> expectedCustomerIds = new HashSet<Long>();
            expectedCustomerIds.add(0L);
            expectedCustomerIds.add(2L);
            expectedCustomerIds.add(4L);
            expectedCustomerIds.add(6L);
            expectedCustomerIds.add(8L);

            for (Tuple joinedTuple : lastSet) {
                Tuple customerTuple = (Tuple) joinedTuple.getColumn(0);
                Concept customer = (Concept) customerTuple.getColumn(0);

                Tuple orderTuple = (Tuple) joinedTuple.getColumn(1);
                Concept order = (Concept) orderTuple.getColumn(0);

                Assert.assertEquals(customer.getId(), extractCustomerIdFromOrder(order),
                        "Join criteria does not match");

                expectedCustomerIds.remove(customer.getId());
            }

            Assert.assertTrue(expectedCustomerIds.isEmpty(), "Not all Customers were received.");
        }
        finally {
            handleTestEnd(query);
        }
    }

    protected void handleTestEnd(ReteQuery query) throws Exception {
        ReteEntityDispatcher dispatcher = regionManager.getDispatcher();

        dispatcher.unregisterAndStopQuery(query);
    }

    static long extractCustomerIdFromOrder(Concept order) {
        Property customerIdProp = order.getProperty("customerId");

        return ((PropertyAtomLong) customerIdProp).getLong();
    }

    public static class CustomerTuple extends HeavyReteEntity {

        public CustomerTuple(Number id) {
            super(id);
        }
    }

    public static class OrderTuple extends HeavyReteEntity {

        public OrderTuple(Number id) {
            super(id);
        }
    }

    public static class CustomerAndOrderTuple extends HeavyJoinedTuple {

        public CustomerAndOrderTuple(Number id) {
            super(id);
        }

        public CustomerAndOrderTuple(Long id, Object[] columns) {
            super(id, columns);
        }
    }

    public class JoinExpr extends AbstractExpression implements EvaluatableExpression {

        /**
         * Implementations can use "Bind variables" by using contextual information passed in the {@link
         * com.tibco.cep.query.stream.context.DefaultQueryContext}.
         *
         * @param aliasAndTypes
         */
        public JoinExpr(Map<String, Class<? extends Tuple>> aliasAndTypes) {
            super(aliasAndTypes);
        }

        public boolean evaluateBoolean(
                GlobalContext globalContext, QueryContext queryContext,
                FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            Object customer = aliasAndTuples.get("customerStream").getColumn(0);
            long customerId = ((Concept) customer).getId();

            //Just checking if the props have all deserialized.
            Property ageProp = ((Concept) customer).getProperty("age");
            int age = ((PropertyAtomIntSimple) ageProp).getInt();

            Property nameProp = ((Concept) customer).getProperty("name");
            String name = ((PropertyAtomStringSimple) nameProp).getString();

            Object order = aliasAndTuples.get("orderStream").getColumn(0);
            long customerIdInOrder =
                    ReteCQJoinConTest.extractCustomerIdFromOrder((Concept) order);

            return customerId == customerIdInOrder;
        }

        public Boolean evaluate(
                GlobalContext globalContext, QueryContext queryContext,
                FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            return evaluateBoolean(globalContext, queryContext, aliasAndTuples);
        }

        public int evaluateInteger(
                GlobalContext globalContext, QueryContext queryContext,
                FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            return 0;
        }

        public long evaluateLong(
                GlobalContext globalContext, QueryContext queryContext,
                FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            return 0;
        }

        public float evaluateFloat(
                GlobalContext globalContext, QueryContext queryContext,
                FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            return 0;
        }

        public double evaluateDouble(
                GlobalContext globalContext, QueryContext queryContext,
                FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            return 0;
        }
    }
}