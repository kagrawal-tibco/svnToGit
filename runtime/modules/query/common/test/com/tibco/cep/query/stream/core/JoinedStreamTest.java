package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.context.*;
import com.tibco.cep.query.stream.expression.EvaluatableExpression;
import com.tibco.cep.query.stream.framework.*;
import com.tibco.cep.query.stream.impl.rete.join.EqualsExpression;
import com.tibco.cep.query.stream.impl.rete.join.JoinedStreamImpl;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.io.WithdrawableSourceImpl;
import com.tibco.cep.query.stream.join.JoinedTupleInfo;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.query.stream.util.WrappedCustomCollection;
import org.testng.annotations.Test;

import java.util.*;

/*
 * Author: Ashwin Jayaprakash Date: Oct 2, 2007 Time: 3:57:16 PM
 */

public class JoinedStreamTest extends AbstractTest {
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
            final TupleInfo fulfilmentInfo = new AbstractTupleInfo(FulfilmentTuple.class,
                    new String[]{"fulfilment"}, new Class[]{Fulfilment.class}) {
                public Tuple createTuple(Number id) {
                    return new FulfilmentTuple(id);
                }
            };
            final JoinedTupleInfo joinInfo = new JoinedTupleInfoImpl(OrderFulfilmentTuple.class,
                    new String[]{"orderStream", "fulfilmentStream"}, new Class[]{
                    OrderTuple.class, FulfilmentTuple.class});

            String queryName = "Test";

            WithdrawableSource orderStream =
                    new WithdrawableSourceImpl(new ResourceId(""), orderInfo);
            DStream deadOrderStream =
                    new DStream(orderStream.getInternalStream(), new ResourceId(""));
            sources.put("deadOrderStream", orderStream);

            WithdrawableSource fulfilmentStream =
                    new WithdrawableSourceImpl(new ResourceId(""), fulfilmentInfo);
            sources.put("fulfilmentStream", fulfilmentStream);

            Map<String, Stream> joinSources = new HashMap<String, Stream>();
            joinSources.put("orderStream", deadOrderStream);
            joinSources.put("fulfilmentStream", fulfilmentStream.getInternalStream());

            JoinEval joinExpr = new JoinEval(new LeftHashExpr(), new RightHashExpr());

            JoinedStreamImpl joinedStream =
                    new JoinedStreamImpl(master.getAgentService().getName(), queryName,
                            new ResourceId(""), joinSources,
                            null, joinExpr, joinInfo);

            sink = new StreamedSink(joinedStream, new ResourceId(""));

            Context context = new Context(new DefaultGlobalContext(),
                    new DefaultQueryContext(master.getAgentService().getName(), queryName));

            ContextRepository contextRepository =
                    Registry.getInstance().getComponent(ContextRepository.class);
            contextRepository.registerContext(context.getQueryContext());

            // ----------

            WrappedCustomCollection<Tuple> newOrders =
                    new WrappedCustomCollection(new LinkedList<Tuple>());
            Order order = new Order();
            order.setCustomerId("123AJ");
            order.setOrderId("FFER55376RRESDETR");
            Tuple data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            newOrders.add(data);
            Tuple candidateOrder1 = data;

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
            Tuple candidateOrder2 = data;

            WrappedCustomCollection<Tuple> deadOrders = null;

            orderStream.getInternalStream().getLocalContext().setDeadTuples(deadOrders);
            orderStream.getInternalStream().getLocalContext().setNewTuples(newOrders);
            orderStream.getInternalStream().process(context);

            // ----------

            WrappedCustomCollection<Tuple> allFulfilments =
                    new WrappedCustomCollection(new LinkedList<Tuple>());
            WrappedCustomCollection<Tuple> newFulfilments =
                    new WrappedCustomCollection(new LinkedList<Tuple>());

            Fulfilment fulfilment = new Fulfilment();
            fulfilment.setOrderId("FFER55376RRESDETR");
            data = new FulfilmentTuple(SimpleIdGenerator.generateNewId(), new Object[]{fulfilment});
            newFulfilments.add(data);
            Tuple candidateFulfilment1 = data;
            allFulfilments.add(data);

            fulfilment = new Fulfilment();
            fulfilment.setOrderId("SSW2343ASA7KKASKL");
            data = new FulfilmentTuple(SimpleIdGenerator.generateNewId(), new Object[]{fulfilment});
            newFulfilments.add(data);
            allFulfilments.add(data);

            fulfilment = new Fulfilment();
            fulfilment.setOrderId("W245345SDGGRTYWEF");
            data = new FulfilmentTuple(SimpleIdGenerator.generateNewId(), new Object[]{fulfilment});
            newFulfilments.add(data);
            Tuple candidateFulfilment2 = data;
            allFulfilments.add(data);

            fulfilmentStream.getInternalStream().getLocalContext().setNewTuples(newFulfilments);
            fulfilmentStream.getInternalStream().process(context);

            // ----------

            System.err.println("Waiting for Orders to expire...");
            Thread.sleep(5000);
            System.err.println("Orders expiring");

            deadOrders = newOrders;
            newOrders = null;

            orderStream.getInternalStream().getLocalContext().setDeadTuples(deadOrders);
            orderStream.getInternalStream().getLocalContext().setNewTuples(newOrders);
            orderStream.getInternalStream().process(context);

            // ----------

            DefaultQueryContext queryContext = context.getQueryContext();
            queryContext.transferNextToCurrentCycleSchedule();
            Set<Stream> scheduledStreams = queryContext.getCurrentCycleSchedule();
            while (scheduledStreams.isEmpty() == false) {
                Stream scheduledStream = scheduledStreams.iterator().next();
                System.err.println("Scheduled Stream: " + scheduledStream);

                context.setLocalContext(new LocalContext(true));
                scheduledStream.process(context);
            }

            // ----------

            List<Object[]> expectedResults = new ArrayList<Object[]>();
            expectedResults.add(new Object[]{candidateOrder1, candidateFulfilment1});
            expectedResults.add(new Object[]{candidateOrder2, candidateFulfilment2});

            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStreamedSink(expectedResults, 5);
            verifyCollection(expectedResults, received);

            // ----------

            fulfilmentStream.getInternalStream().getLocalContext().setNewTuples(null);
            fulfilmentStream.getInternalStream().getLocalContext().setDeadTuples(allFulfilments);
            fulfilmentStream.getInternalStream().process(context);

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

    public static class FulfilmentTuple extends TrackedTuple {
        public FulfilmentTuple(Number id, Object[] columns) {
            super(id, columns);
        }

        public FulfilmentTuple(Number id) {
            super(id);
        }
    }

    public static class OrderFulfilmentTuple extends TrackedJoinedTuple {
        public OrderFulfilmentTuple(Number id) {
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
            return "Order.OrderId: " + getOrderId();
        }
    }

    public static class Fulfilment {
        protected String orderId;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        @Override
        public String toString() {
            return "Fulfilment.OrderId: " + getOrderId();
        }
    }

    public static class LeftHashExpr implements EvaluatableExpression {
        protected final Map<String, Class<? extends Tuple>> map;

        public LeftHashExpr() {
            this.map = new HashMap<String, Class<? extends Tuple>>();
            this.map.put("orderStream", OrderTuple.class);
        }

        public Map<String, Class<? extends Tuple>> getAliasAndTypes() {
            return map;
        }

        public Object evaluate(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            Object o = aliasAndTuples.get("orderStream").getColumn(0);

            return ((Order) o).getOrderId();
        }

        public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext,
                                       FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            return false;
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

    public static class RightHashExpr implements EvaluatableExpression {
        protected final HashMap<String, Class<? extends Tuple>> map;

        public RightHashExpr() {
            this.map = new HashMap<String, Class<? extends Tuple>>();
            this.map.put("fulfilmentStream", FulfilmentTuple.class);
        }

        public Map<String, Class<? extends Tuple>> getAliasAndTypes() {
            return map;
        }

        public Object evaluate(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            Object o = aliasAndTuples.get("fulfilmentStream").getColumn(0);

            return ((Fulfilment) o).getOrderId();
        }

        public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext,
                                       FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            return false;
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

    public static class JoinEval extends EqualsExpression {
        public JoinEval(EvaluatableExpression leftHashCodeExpression,
                        EvaluatableExpression rightHashCodeExpression) {
            super(leftHashCodeExpression, rightHashCodeExpression);
        }
    }
}
