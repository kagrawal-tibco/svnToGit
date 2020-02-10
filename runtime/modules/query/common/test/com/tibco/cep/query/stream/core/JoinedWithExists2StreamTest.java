package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultGlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.expression.AbstractExpression;
import com.tibco.cep.query.stream.expression.ComplexAndExpression;
import com.tibco.cep.query.stream.framework.*;
import com.tibco.cep.query.stream.impl.rete.join.ExistsPetey2Impl;
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
import java.util.concurrent.TimeUnit;

/*
 * Author: Ashwin Jayaprakash Date: Oct 17, 2007 Time: 3:51:43 PM
 */

public class JoinedWithExists2StreamTest extends AbstractTest {
    // ??? Test fails. Action Identifiers mismatch.
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
            final TupleInfo orderFulfilmentInfo = new AbstractTupleInfo(OrderFulfilmentTuple.class,
                    new String[]{"orderFulfilment"}, new Class[]{OrderFulfilment.class}) {
                public Tuple createTuple(Number id) {
                    return new OrderFulfilmentTuple(id);
                }
            };
            final TupleInfo priorityCustomerInfo =
                    new AbstractTupleInfo(PriorityCustomerTuple.class,
                            new String[]{"priorityCustomer"}, new Class[]{PriorityCustomer.class}) {
                        public Tuple createTuple(Number id) {
                            return new PriorityCustomerTuple(id);
                        }
                    };

            final JoinedTupleInfo resultInfo =
                    new JoinedTupleInfoImpl(ResultTuple.class, new String[]{
                            "orderStream", "orderFulfilmentStream"}, new Class[]{OrderTuple.class,
                            OrderFulfilmentTuple.class});

            String queryName = "Test";

            Source orderStream = new SourceImpl(new ResourceId("order"), orderInfo);
            sources.put("orderStream", orderStream);
            Source orderFulfilmentStream = new SourceImpl(new ResourceId("fulfilment"),
                    orderFulfilmentInfo);
            sources.put("orderFulfilmentStream", orderFulfilmentStream);
            Source priorityCustomerStream = new SourceImpl(new ResourceId("priority"),
                    priorityCustomerInfo);
            sources.put("priorityCustomerStream", priorityCustomerStream);

            ExistsPeteyInfo peteyInfo = new ExistsPeteyInfo();
            peteyInfo.setResourceId(new ResourceId("Exists"));
            peteyInfo.setInnerTupleAlias("priorityCustomerStream");
            peteyInfo.setInnerTupleInfo(priorityCustomerInfo);

            peteyInfo.addOuterTupleAliasAndInfo("orderStream", orderInfo);
            peteyInfo.addOuterTupleAliasAndInfo("orderFulfilmentStream", orderFulfilmentInfo);

            Map<String, Class<? extends Tuple>> aliases =
                    new HashMap<String, Class<? extends Tuple>>();
            aliases.put("orderStream", OrderTuple.class);
            aliases.put("orderFulfilmentStream", OrderFulfilmentTuple.class);
            aliases.put("priorityCustomerStream", PriorityCustomerTuple.class);
            peteyInfo
                    .setExpression(new SimpleExpression(
                            aliases,
                            "orderStream.getColumn(0).customerId == priorityCustomerStream.getColumn(0).customerId"
                                    +
                                    " && orderFulfilmentStream.getColumn(0).shipmentRegion == priorityCustomerStream.getColumn(0).shipmentRegion"));

            ExistsPetey2Impl petey = new ExistsPetey2Impl(peteyInfo);
            ComplexAndExpression andExpression = new ComplexAndExpression(
                    new AbstractExpression[]{new ExistsSimpleExpression(petey)});

            Map<String, Stream> sourceStreams = new HashMap<String, Stream>();
            sourceStreams.put("orderStream", orderStream.getInternalStream());
            sourceStreams.put("orderFulfilmentStream", orderFulfilmentStream.getInternalStream());

            Collection<NestedSource> nestedSources = new LinkedList<NestedSource>();
            nestedSources.add(new NestedSource(priorityCustomerStream.getInternalStream(), petey));

            JoinedStreamImpl joinedStream = new JoinedStreamImpl(master.getAgentService().getName(),
                    queryName, new ResourceId("Join"), sourceStreams, nestedSources, andExpression,
                    resultInfo);

            sink = new StreamedSink(joinedStream, new ResourceId("Sink"));

            Context context = new Context(new DefaultGlobalContext(),
                    new DefaultQueryContext(master.getAgentService().getName(), queryName));

            ContextRepository contextRepository =
                    Registry.getInstance().getComponent(ContextRepository.class);
            contextRepository.registerContext(context.getQueryContext());

            // ----------

            WrappedCustomCollection<Tuple> newOrders =
                    new WrappedCustomCollection(new LinkedList<Tuple>());
            Order order = new Order();
            order.setCustomerId("Ashwin");
            order.setOrderId("ASHWIN376RRESDETR");
            Tuple data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            newOrders.add(data);

            order = new Order();
            order.setCustomerId("Simpson");
            order.setOrderId("SIMPSONYBF3345GER");
            data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            newOrders.add(data);

            order = new Order();
            order.setCustomerId("Ashwin");
            order.setOrderId("ASHWINWWQSTWER323");
            data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            newOrders.add(data);

            order = new Order();
            order.setCustomerId("Ashwin");
            order.setOrderId("ASHWIN0090932SERB");
            data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            newOrders.add(data);

            order = new Order();
            order.setCustomerId("Teriyaki");
            order.setOrderId("TERIYAKIDGGRTYWEF");
            data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            newOrders.add(data);

            WrappedCustomCollection<Tuple> deadOrders = null;

            orderStream.getInternalStream().getLocalContext().setDeadTuples(deadOrders);
            orderStream.getInternalStream().getLocalContext().setNewTuples(newOrders);
            orderStream.getInternalStream().process(context);

            // ----------

            WrappedCustomCollection<Tuple> newOrderFulfilments =
                    new WrappedCustomCollection(new LinkedList<Tuple>());
            OrderFulfilment orderFulfilment = new OrderFulfilment();
            orderFulfilment.setOrderId("ASHWIN376RRESDETR");
            orderFulfilment.setShipmentRegion("Mountain View");
            data = new OrderFulfilmentTuple(SimpleIdGenerator.generateNewId(),
                    new Object[]{orderFulfilment});
            newOrderFulfilments.add(data);

            orderFulfilment = new OrderFulfilment();
            orderFulfilment.setOrderId("TERIYAKIDGGRTYWEF");
            orderFulfilment.setShipmentRegion("Palo Alto");
            data = new OrderFulfilmentTuple(SimpleIdGenerator.generateNewId(),
                    new Object[]{orderFulfilment});
            newOrderFulfilments.add(data);

            orderFulfilment = new OrderFulfilment();
            orderFulfilment.setOrderId("ASHWINWWQSTWER323");
            orderFulfilment.setShipmentRegion("Mountain View");
            data = new OrderFulfilmentTuple(SimpleIdGenerator.generateNewId(),
                    new Object[]{orderFulfilment});
            newOrderFulfilments.add(data);

            orderFulfilment = new OrderFulfilment();
            orderFulfilment.setOrderId("ASHWIN0090932SERB");
            orderFulfilment.setShipmentRegion("Bangalore");
            data = new OrderFulfilmentTuple(SimpleIdGenerator.generateNewId(),
                    new Object[]{orderFulfilment});
            newOrderFulfilments.add(data);

            orderFulfilment = new OrderFulfilment();
            orderFulfilment.setOrderId("SIMPSONYBF3345GER");
            orderFulfilment.setShipmentRegion("Springfield");
            data = new OrderFulfilmentTuple(SimpleIdGenerator.generateNewId(),
                    new Object[]{orderFulfilment});
            newOrderFulfilments.add(data);

            WrappedCustomCollection<Tuple> deadOrderFulfilments = null;

            orderFulfilmentStream.getInternalStream().getLocalContext().setDeadTuples(
                    deadOrderFulfilments);
            orderFulfilmentStream.getInternalStream().getLocalContext().setNewTuples(
                    newOrderFulfilments);
            orderFulfilmentStream.getInternalStream().process(context);

            // ----------

            System.out.println("Asserting: Ashwin:MV");

            WrappedCustomCollection<Tuple> priorityCustomers =
                    new WrappedCustomCollection(new LinkedList<Tuple>());
            PriorityCustomer customer = new PriorityCustomer();
            customer.setCustomerId("Ashwin");
            customer.setShipmentRegion("Mountain View");

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

            orderStream.getInternalStream().getLocalContext().setDeadTuples(deadOrders);
            orderStream.getInternalStream().getLocalContext().setNewTuples(newOrders);
            orderStream.getInternalStream().process(context);

            // ----------

            orderFulfilment = new OrderFulfilment();
            orderFulfilment.setOrderId("ASHWIN42DHFR346SS");
            orderFulfilment.setShipmentRegion("Springfield");
            data = new OrderFulfilmentTuple(SimpleIdGenerator.generateNewId(),
                    new Object[]{orderFulfilment});
            newOrderFulfilments.add(data);

            orderFulfilmentStream.getInternalStream().getLocalContext().setDeadTuples(
                    deadOrderFulfilments);
            orderFulfilmentStream.getInternalStream().getLocalContext().setNewTuples(
                    newOrderFulfilments);
            orderFulfilmentStream.getInternalStream().process(context);

            // ----------

            // todo imcomplete verification.
            Tuple tuple = null;
            StreamedSink streamedSink = (StreamedSink) sink;
            while ((tuple = streamedSink.poll(5, TimeUnit.SECONDS)) != null) {
                System.out.println(Arrays.asList(tuple.getRawColumns()));
            }

            System.out.println("Expect Ashwin.");

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

    public static class OrderFulfilmentTuple extends TrackedTuple {
        public OrderFulfilmentTuple(Number id) {
            super(id);
        }

        public OrderFulfilmentTuple(Number id, Object[] columns) {
            super(id, columns);
        }
    }

    public static class ResultTuple extends TrackedJoinedTuple {
        public ResultTuple(Number id) {
            super(id);
        }

        public ResultTuple(Number id, Tuple[] columns) {
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
            return "Order.OrderId: " + getOrderId() + ", CustomerId: " + getCustomerId();
        }
    }

    public static class OrderFulfilment {
        protected String orderId;

        protected String shipmentRegion;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String customerId) {
            this.orderId = customerId;
        }

        public String getShipmentRegion() {
            return shipmentRegion;
        }

        public void setShipmentRegion(String shipmentRegion) {
            this.shipmentRegion = shipmentRegion;
        }

        @Override
        public String toString() {
            return "OrderFulfilment.OrderId: " + getOrderId()
                    + ", OrderFulfilment.ShipmentRegion: " + getShipmentRegion();
        }
    }

    public static class PriorityCustomer {
        protected String customerId;

        protected String shipmentRegion;

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getShipmentRegion() {
            return shipmentRegion;
        }

        public void setShipmentRegion(String shipmentRegion) {
            this.shipmentRegion = shipmentRegion;
        }

        @Override
        public String toString() {
            return "Priority.CustomerId: " + getCustomerId() + ", Priority.ShipmentRegion: "
                    + getShipmentRegion();
        }
    }
}
