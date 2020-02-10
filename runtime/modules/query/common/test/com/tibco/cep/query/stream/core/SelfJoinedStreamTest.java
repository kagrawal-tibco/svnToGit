package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultGlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.expression.AbstractExpression;
import com.tibco.cep.query.stream.expression.ComplexAndExpression;
import com.tibco.cep.query.stream.expression.ComplexExpression;
import com.tibco.cep.query.stream.framework.*;
import com.tibco.cep.query.stream.impl.rete.join.JoinedStreamImpl;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.io.WithdrawableSourceImpl;
import com.tibco.cep.query.stream.join.JoinedTupleInfo;
import com.tibco.cep.query.stream.join.ProxyStream;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.WrappedCustomCollection;
import org.testng.annotations.Test;

import java.util.*;

/*
 * Author: Ashwin Jayaprakash Date: Oct 2, 2007 Time: 3:57:16 PM
 */

public class SelfJoinedStreamTest extends AbstractTest {
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
            final JoinedTupleInfo joinInfo = new JoinedTupleInfoImpl(SimilarOrderTuple.class,
                    new String[]{"orderStream", "proxyOrderStream"}, new Class[]{
                    OrderTuple.class, OrderTuple.class});

            String queryName = "Test";
            Registry.getInstance().getComponent(ContextRepository.class).registerContext(
                    new DefaultQueryContext(master.getAgentService().getName(), queryName));

            WithdrawableSource orderStream = new WithdrawableSourceImpl(new ResourceId(
                    "OrderStream"), orderInfo);
            sources.put("orderStream", orderStream);

            ProxyStream proxyOrderStream = new ProxyStream(new ResourceId(orderStream
                    .getResourceId(), "ProxyOrderStream"), orderStream.getInternalStream());

            Map<String, Stream> joinedSources = new HashMap<String, Stream>();
            joinedSources.put("orderStream", orderStream.getInternalStream());
            joinedSources.put("proxyOrderStream", proxyOrderStream);

            String joinExpressionStr =
                    "orderStream.getColumn(0).customerId == proxyOrderStream.getColumn(0).customerId"
                            +
                            " && orderStream.getColumn(0).orderId != proxyOrderStream.getColumn(0).orderId";
            HashMap<String, Class<? extends Tuple>> aliases =
                    new HashMap<String, Class<? extends Tuple>>();
            aliases.put("orderStream", OrderTuple.class);
            aliases.put("proxyOrderStream", OrderTuple.class);
            ComplexExpression joinExpression = new ComplexAndExpression(
                    new AbstractExpression[]{new SimpleExpression(aliases, joinExpressionStr)});
            JoinedStreamImpl joinedStream = new JoinedStreamImpl(master.getAgentService().getName(),
                    queryName, new ResourceId("Join"), joinedSources, null, joinExpression,
                    joinInfo);

            sink = new StreamedSink(joinedStream, null);

            Context context = new Context(new DefaultGlobalContext(),
                    new DefaultQueryContext(master.getAgentService().getName(), "Joiner"));

            // ----------

            WrappedCustomCollection<Tuple> newOrders =
                    new WrappedCustomCollection(new LinkedList<Tuple>());
            Order order = new Order();
            order.setCustomerId("123AJ");
            order.setOrderId("FFER55376RRESDETR");
            Tuple data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            newOrders.add(data);
            Tuple expectedTuple1 = data;

            order = new Order();
            order.setCustomerId("345Si");
            order.setOrderId("DTYYRRTYBF3345GER");
            data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            newOrders.add(data);

            order = new Order();
            order.setCustomerId("Teru7");
            order.setOrderId("W245345SDGGRTYWEF");
            data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            newOrders.add(data);

            order = new Order();
            order.setCustomerId("123AJ");
            order.setOrderId("DDAAAA3979K23312L");
            data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            newOrders.add(data);
            Tuple expectedTuple2 = data;

            WrappedCustomCollection<Tuple> deadOrders = null;

            orderStream.getInternalStream().getLocalContext().setDeadTuples(deadOrders);
            orderStream.getInternalStream().getLocalContext().setNewTuples(newOrders);
            orderStream.getInternalStream().process(context);

            // ----------

            List<Object[]> expectedResults = new ArrayList<Object[]>();
            expectedResults.add(new Object[]{expectedTuple1, expectedTuple2});
            expectedResults.add(new Object[]{expectedTuple2, expectedTuple1});

            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStreamedSink(expectedResults, 5);
            verifyCollection(expectedResults, received);

            // -----------

            orderStream.getInternalStream().getLocalContext().setDeadTuples(newOrders);
            orderStream.getInternalStream().getLocalContext().setNewTuples(null);
            orderStream.getInternalStream().process(context);

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

    public static class SimilarOrderTuple extends TrackedJoinedTuple {
        public SimilarOrderTuple(Number id) {
            super(id);
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
            return "Order:: OrderId: " + getOrderId() + ", CustomerId: " + getCustomerId();
        }
    }
}
