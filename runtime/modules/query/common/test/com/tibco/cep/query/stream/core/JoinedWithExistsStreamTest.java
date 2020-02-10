package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultGlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.expression.AbstractExpression;
import com.tibco.cep.query.stream.expression.ComplexAndExpression;
import com.tibco.cep.query.stream.framework.*;
import com.tibco.cep.query.stream.impl.rete.join.ExistsPeteyImpl;
import com.tibco.cep.query.stream.impl.rete.join.JoinedStreamImpl;
import com.tibco.cep.query.stream.io.SourceImpl;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.join.AbstractExistsPetey.ExistsPeteyInfo;
import com.tibco.cep.query.stream.join.ExistsSimpleExpression;
import com.tibco.cep.query.stream.join.JoinedTupleInfo;
import com.tibco.cep.query.stream.join.NestedSource;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.WrappedCustomCollection;
import org.testng.annotations.Test;

import java.util.*;

/*
 * Author: Ashwin Jayaprakash Date: Oct 17, 2007 Time: 3:51:43 PM
 */

public class JoinedWithExistsStreamTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        try {
            final TupleInfo orderInfo =
                    new AbstractTupleInfo(OrderTuple.class, new String[]{"order"},
                            new Class[]{Order.class}) {
                        public Tuple createTuple(Number id) {
                            return new OrderTuple(id);
                        }
                    };
            final TupleInfo priorityCustomerInfo =
                    new AbstractTupleInfo(PriorityCustomerTuple.class,
                            new String[]{"priorityCustomer"}, new Class[]{PriorityCustomer.class}) {
                        public Tuple createTuple(Number id) {
                            return new PriorityCustomerTuple(id);
                        }
                    };
            final JoinedTupleInfo joinOutputInfo = new JoinedTupleInfoImpl(JoinTuple.class,
                    new String[]{"orderStream"}, new Class[]{OrderTuple.class});

            String queryName = "Test";

            Source orderStream = new SourceImpl(new ResourceId("orders"), orderInfo);
            sources.put("orderStream", orderStream);
            Source priorityCustomerStream = new SourceImpl(new ResourceId("priorityCustomers"),
                    priorityCustomerInfo);
            sources.put("priorityCustomerStream", priorityCustomerStream);

            ExistsPeteyInfo peteyInfo = new ExistsPeteyInfo();
            peteyInfo.setResourceId(new ResourceId("Exists"));
            peteyInfo.setInnerTupleAlias("priorityCustomerStream");
            peteyInfo.setInnerTupleInfo(priorityCustomerInfo);

            peteyInfo.addOuterTupleAliasAndInfo("orderStream", orderInfo);

            Map<String, Class<? extends Tuple>> aliases =
                    new HashMap<String, Class<? extends Tuple>>();
            aliases.put("orderStream", OrderTuple.class);
            aliases.put("priorityCustomerStream", PriorityCustomerTuple.class);
            peteyInfo
                    .setExpression(new SimpleExpression(aliases,
                            "orderStream.getColumn(0).customerId == priorityCustomerStream.getColumn(0).customerId"));

            ExistsPeteyImpl petey = new ExistsPeteyImpl(peteyInfo);
            ComplexAndExpression andExpression = new ComplexAndExpression(
                    new AbstractExpression[]{new ExistsSimpleExpression(petey)});

            Map<String, Stream> joinSources = new HashMap<String, Stream>();
            joinSources.put("orderStream", orderStream.getInternalStream());
            Collection<NestedSource> nestedSources = new ArrayList<NestedSource>();
            nestedSources.add(new NestedSource(priorityCustomerStream.getInternalStream(), petey));

            JoinedStreamImpl joinedStream = new JoinedStreamImpl(master.getAgentService().getName(),
                    queryName, new ResourceId("join"), joinSources, nestedSources, andExpression,
                    joinOutputInfo);

            sink = new StreamedSink(joinedStream, new ResourceId("sink"));

            Context context = new Context(new DefaultGlobalContext(),
                    new DefaultQueryContext(master.getAgentService().getName(), queryName));

            // ----------

            WrappedCustomCollection<Tuple> allOrders =
                    new WrappedCustomCollection(new LinkedList<Tuple>());
            List<Tuple> expectedOrders = new LinkedList<Tuple>();

            WrappedCustomCollection<Tuple> newOrders =
                    new WrappedCustomCollection(new LinkedList<Tuple>());
            Order order = new Order();
            order.setCustomerId("Ashwin");
            order.setOrderId("ASHWIN376RRESDETR");
            Tuple data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            newOrders.add(data);
            expectedOrders.add(data);
            allOrders.add(data);

            order = new Order();
            order.setCustomerId("Simpson");
            order.setOrderId("SIMPSONYBF3345GER");
            data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            newOrders.add(data);
            allOrders.add(data);

            order = new Order();
            order.setCustomerId("Teriyaki");
            order.setOrderId("TERIYAKIDGGRTYWEF");
            data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            newOrders.add(data);
            allOrders.add(data);

            WrappedCustomCollection<Tuple> deadOrders = null;

            orderStream.getInternalStream().getLocalContext().setDeadTuples(deadOrders);
            orderStream.getInternalStream().getLocalContext().setNewTuples(newOrders);
            orderStream.getInternalStream().process(context);

            // ----------

            System.out.println("Asserting: Ashwin");

            WrappedCustomCollection<Tuple> priorityCustomers =
                    new WrappedCustomCollection(new LinkedList<Tuple>());
            PriorityCustomer customer = new PriorityCustomer();
            customer.setCustomerId("Ashwin");
            Tuple data2 = new PriorityCustomerTuple(SimpleIdGenerator.generateNewId(),
                    new Object[]{customer});
            priorityCustomers.add(data2);

            priorityCustomerStream.getInternalStream().getLocalContext().setDeadTuples(null);
            priorityCustomerStream.getInternalStream().getLocalContext().setNewTuples(
                    priorityCustomers);
            priorityCustomerStream.getInternalStream().process(context);

            // ----------

            newOrders = new WrappedCustomCollection(new LinkedList<Tuple>());
            order = new Order();
            order.setCustomerId("Ashwin");
            order.setOrderId("ASHWIN42DHFR346SS");
            data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            newOrders.add(data);
            expectedOrders.add(data);
            allOrders.add(data);

            orderStream.getInternalStream().getLocalContext().setDeadTuples(deadOrders);
            orderStream.getInternalStream().getLocalContext().setNewTuples(newOrders);
            orderStream.getInternalStream().process(context);

            // ----------

            List<Object[]> expectedResults = new ArrayList<Object[]>();
            for (Tuple t : expectedOrders) {
                expectedResults.add(new Object[]{t});
            }

            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStreamedSink(expectedResults, 5);
            verifyCollection(expectedResults, received);

            // ----------

            orderStream.getInternalStream().getLocalContext().setDeadTuples(allOrders);
            orderStream.getInternalStream().getLocalContext().setNewTuples(null);
            orderStream.getInternalStream().process(context);

            expectedResults = new ArrayList<Object[]>();
            received = collectAndMatchStreamedSink(expectedResults, 5);

            // ----------

            priorityCustomerStream.getInternalStream().getLocalContext().setDeadTuples(
                    priorityCustomers);
            priorityCustomerStream.getInternalStream().getLocalContext().setNewTuples(null);
            priorityCustomerStream.getInternalStream().process(context);

            expectedResults = new ArrayList<Object[]>();
            received = collectAndMatchStreamedSink(expectedResults, 5);

            commonTests();

            joinedStream.stop();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class OrderTuple extends TrackedTuple {
        public OrderTuple(Number id, Object[] columns) {
            super(id, columns);
        }

        public OrderTuple(Number id) {
            super(id);
        }
    }

    public static class PriorityCustomerTuple extends TrackedTuple {
        public PriorityCustomerTuple(Number id, Object[] columns) {
            super(id, columns);
        }

        public PriorityCustomerTuple(Number id) {
            super(id);
        }
    }

    public static class JoinTuple extends TrackedJoinedTuple {
        public JoinTuple(Number id) {
            super(id);
        }

        public JoinTuple(Number id, Tuple[] columns) {
            super(id, columns);
        }
    }

    public static class Order {
        protected String orderId;

        protected String customerId;

        protected Map<String, Integer> skuAndQty;

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public void setSkuAndQty(Map<String, Integer> skuAndQty) {
            this.skuAndQty = skuAndQty;
        }

        public String getCustomerId() {
            return customerId;
        }

        public String getOrderId() {
            return orderId;
        }

        public Map<String, Integer> getSkuAndQty() {
            return skuAndQty;
        }

        @Override
        public String toString() {
            return "Order.OrderId: " + getOrderId();
        }
    }

    public static class PriorityCustomer {
        protected String customerId;

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        @Override
        public String toString() {
            return "Priority.Customer: " + getCustomerId();
        }
    }
}
