package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.aggregate.AggregateItemInfo;
import com.tibco.cep.query.stream.aggregate.AggregatedStream;
import com.tibco.cep.query.stream.context.*;
import com.tibco.cep.query.stream.expression.AbstractExpression;
import com.tibco.cep.query.stream.expression.ComplexOrExpression;
import com.tibco.cep.query.stream.framework.*;
import com.tibco.cep.query.stream.group.GroupAggregateInfo;
import com.tibco.cep.query.stream.group.GroupAggregateItemInfo;
import com.tibco.cep.query.stream.group.GroupAggregateTransformer;
import com.tibco.cep.query.stream.group.GroupKey;
import com.tibco.cep.query.stream.impl.aggregate.CountAggregator;
import com.tibco.cep.query.stream.impl.aggregate.IntegerSumAggregator;
import com.tibco.cep.query.stream.impl.rete.join.InPeteyImpl;
import com.tibco.cep.query.stream.impl.rete.join.JoinedStreamImpl;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.io.WithdrawableSourceImpl;
import com.tibco.cep.query.stream.join.AbstractInPetey.InPeteyInfo;
import com.tibco.cep.query.stream.join.InSimpleExpression;
import com.tibco.cep.query.stream.join.JoinedTupleInfo;
import com.tibco.cep.query.stream.join.NestedSource;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.*;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.WrappedCustomCollection;
import org.testng.annotations.Test;

import java.util.*;

/*
 * Author: Ashwin Jayaprakash Date: Oct 17, 2007 Time: 3:51:43 PM
 */

public class JoinedOrWithInStreamTrackerTest extends AbstractTest {
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

            WithdrawableSource orderStream = new WithdrawableSourceImpl(new ResourceId("orders"),
                    orderInfo);
            WithdrawableSource priorityCustomerStream = new WithdrawableSourceImpl(new ResourceId(
                    "priorityCustomers"), priorityCustomerInfo);

            sources.put("orderStream", orderStream);
            sources.put("priorityCustomerStream", priorityCustomerStream);

            InPeteyInfo peteyInfo = new InPeteyInfo();
            peteyInfo.setResourceId(new ResourceId("In"));
            peteyInfo.setInnerTupleAlias("priorityCustomerStream");
            peteyInfo.setInnerTupleInfo(priorityCustomerInfo);
            peteyInfo.setInnerTupleValueExtractor(new DefaultTupleValueExtractor() {
                @Override
                public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                      Tuple tuple) {
                    return ((PriorityCustomer) tuple.getColumn(0)).getCustomerId();
                }
            });

            peteyInfo.addOuterTupleAliasAndInfo("orderStream", orderInfo);
            peteyInfo.setOuterTupleValueExtractor(new DefaultTupleValueExtractor() {
                @Override
                public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                      Tuple tuple) {
                    return ((Order) tuple.getColumn(0)).getCustomerId();
                }
            });

            String queryName = "Test";

            InPeteyImpl petey = new InPeteyImpl(peteyInfo);

            Map<String, Class<? extends Tuple>> aliases =
                    new HashMap<String, Class<? extends Tuple>>();
            aliases.put("orderStream", OrderTuple.class);
            SimpleExpression expression = new SimpleExpression(aliases,
                    "orderStream.getColumn(0).customerId == \"Ashwin\"");

            ComplexOrExpression orExpression = new ComplexOrExpression(new AbstractExpression[]{
                    new InSimpleExpression(petey), expression});

            Map<String, Stream> joinSources = new HashMap<String, Stream>();
            joinSources.put("orderStream", orderStream.getInternalStream());
            Collection<NestedSource> nestedSources = new ArrayList<NestedSource>();
            nestedSources.add(new NestedSource(priorityCustomerStream.getInternalStream(), petey));

            JoinedStreamImpl joinedStream = new JoinedStreamImpl(master.getAgentService().getName(),
                    queryName, new ResourceId("join"),
                    joinSources, nestedSources, orExpression, joinOutputInfo);

            LinkedHashMap<String, GroupAggregateItemInfo> aggregateItems =
                    new LinkedHashMap<String, GroupAggregateItemInfo>();

            aggregateItems.put("count",
                    new GroupAggregateItemInfo(new AggregateItemInfo(CountAggregator.CREATOR,
                            new DefaultTupleValueExtractor() {
                                @Override
                                public Object extract(GlobalContext globalContext,
                                                      QueryContext queryContext, Tuple tuple) {
                                    return 1;
                                }
                            })));
            aggregateItems.put("qtySum",
                    new GroupAggregateItemInfo(new AggregateItemInfo(IntegerSumAggregator.CREATOR,
                            new DefaultTupleValueExtractor() {
                                @Override
                                public Object extract(GlobalContext globalContext,
                                                      QueryContext queryContext, Tuple tuple) {
                                    Tuple orderTuple = (Tuple) tuple.getColumn(0);
                                    Order order = (Order) orderTuple.getColumn(0);
                                    return order.qty;
                                }
                            })));

            GroupAggregateInfo groupAggregateInfo = new GroupAggregateInfo(aggregateItems);

            String[] columnNames = {"count", "qtySum"};
            Class[] columnTypes = {Integer.class, Integer.class};
            final TupleInfo aggregatedTupleInfo =
                    new AbstractTupleInfo(AggregatedOutputTuple.class, columnNames,
                            columnTypes) {
                        public Tuple createTuple(Number id) {
                            return new AggregatedOutputTuple(id);
                        }
                    };


            WindowBuilder builder = new WindowBuilder() {
                public Window buildAndInit(ResourceId parentResourceId, String id,
                                           GroupKey groupKey, AggregateInfo aggregateInfo,
                                           WindowInfo windowInfo, WindowOwner windowOwner,
                                           GroupAggregateTransformer aggregateTransformer) {
                    Window window = new SimpleAggregatedStreamWindow(new ResourceId(
                            parentResourceId, "SimpleWindow:" + id), joinOutputInfo, groupKey,
                            windowOwner);

                    AggregatedStream aggregatedStream = new AggregatedStream(window,
                            new ResourceId(parentResourceId, "Aggregate:" + id),
                            aggregateInfo, true);

                    window.init(aggregateTransformer, aggregatedStream, null);

                    return window;
                }
            };

            AggregatedPartitionedStream partitionedStream = new AggregatedPartitionedStream(
                    joinedStream, new ResourceId("aggregate-partition"), aggregatedTupleInfo,
                    groupAggregateInfo, builder);


            sink = new StreamedSink(partitionedStream, new ResourceId("sink"));

            Context context = new Context(new DefaultGlobalContext(),
                    new DefaultQueryContext(master.getAgentService().getName(), queryName));

            ContextRepository contextRepository =
                    Registry.getInstance().getComponent(ContextRepository.class);
            contextRepository.registerContext(context.getQueryContext());

            // ----------

            WrappedCustomCollection<Tuple> allOrders =
                    new WrappedCustomCollection(new LinkedList<Tuple>());

            WrappedCustomCollection<Tuple> orders =
                    new WrappedCustomCollection(new LinkedList<Tuple>());
            Order order = new Order();
            order.setCustomerId("Ashwin");
            order.setOrderId("ASHWIN376RRESDETR");
            order.setQty(10);
            Tuple data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            orders.add(data);
            allOrders.add(data);

            order = new Order();
            order.setCustomerId("Simpson");
            order.setOrderId("SIMPSONYBF3345GER");
            order.setQty(3);
            data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            orders.add(data);
            allOrders.add(data);

            order = new Order();
            order.setCustomerId("Ashwin");
            order.setOrderId("ASHWIN442K4239JK");
            order.setQty(9);
            data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            orders.add(data);
            allOrders.add(data);
            Tuple ashwinOrder = data;

            order = new Order();
            order.setCustomerId("Teriyaki");
            order.setOrderId("TERIYAKIDGGRTYWEF");
            order.setQty(18);
            data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            orders.add(data);
            allOrders.add(data);

            WrappedCustomCollection<Tuple> deadOrders = null;

            orderStream.getInternalStream().getLocalContext().setDeadTuples(deadOrders);
            orderStream.getInternalStream().getLocalContext().setNewTuples(orders);
            orderStream.getInternalStream().process(context);

            // ----------

            System.out.println("Asserting: Ashwin");

            WrappedCustomCollection<Tuple> allCustomers =
                    new WrappedCustomCollection(new LinkedList<Tuple>());

            WrappedCustomCollection<Tuple> priorityCustomers =
                    new WrappedCustomCollection(new LinkedList<Tuple>());
            PriorityCustomer customer = new PriorityCustomer();
            customer.setCustomerId("Ashwin");
            Tuple data2 = new PriorityCustomerTuple(SimpleIdGenerator.generateNewId(),
                    new Object[]{customer});
            priorityCustomers.add(data2);
            allCustomers.add(data2);

            priorityCustomerStream.getInternalStream().getLocalContext().setDeadTuples(null);
            priorityCustomerStream.getInternalStream().getLocalContext().setNewTuples(
                    priorityCustomers);
            priorityCustomerStream.getInternalStream().process(context);

            // ----------

            orders = new WrappedCustomCollection(new LinkedList<Tuple>());

            order = new Order();
            order.setCustomerId("Flintstone");
            order.setOrderId("FLINTSTONE1231233");
            order.setQty(6);
            data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            orders.add(data);
            allOrders.add(data);

            order = new Order();
            order.setCustomerId("Ashwin");
            order.setOrderId("ASHWIN42DHFR346SS");
            order.setQty(19);
            data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            orders.add(data);
            allOrders.add(data);

            order = new Order();
            order.setCustomerId("Simpson");
            order.setOrderId("SIMPSON44DGER5664");
            order.setQty(23);
            data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            orders.add(data);
            allOrders.add(data);

            orderStream.getInternalStream().getLocalContext().setDeadTuples(deadOrders);
            orderStream.getInternalStream().getLocalContext().setNewTuples(orders);
            orderStream.getInternalStream().process(context);

            // ----------

            System.out.println("Asserting: Simpson");

            priorityCustomers = new WrappedCustomCollection(new LinkedList<Tuple>());
            customer = new PriorityCustomer();
            customer.setCustomerId("Simpson");
            data2 = new PriorityCustomerTuple(SimpleIdGenerator.generateNewId(),
                    new Object[]{customer});
            priorityCustomers.add(data2);
            allCustomers.add(data2);

            priorityCustomerStream.getInternalStream().getLocalContext().setDeadTuples(null);
            priorityCustomerStream.getInternalStream().getLocalContext().setNewTuples(
                    priorityCustomers);
            priorityCustomerStream.getInternalStream().process(context);

            // ----------

            orders = new WrappedCustomCollection(new LinkedList<Tuple>());

            order = new Order();
            order.setCustomerId("Simpson");
            order.setOrderId("SIMPSONEE463FDSG2");
            order.setQty(27);
            data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            orders.add(data);
            allOrders.add(data);

            order = new Order();
            order.setCustomerId("Teriyaki");
            order.setOrderId("TERIYAKIWWERQ3353");
            order.setQty(29);
            data = new OrderTuple(SimpleIdGenerator.generateNewId(), new Object[]{order});
            orders.add(data);
            allOrders.add(data);

            orderStream.getInternalStream().getLocalContext().setDeadTuples(deadOrders);
            orderStream.getInternalStream().getLocalContext().setNewTuples(orders);
            orderStream.getInternalStream().process(context);

            // ----------

            orders = new WrappedCustomCollection(new LinkedList<Tuple>());
            orders.add(ashwinOrder);
            allOrders.remove(ashwinOrder);
            orderStream.getInternalStream().getLocalContext().setDeadTuples(orders);
            orderStream.getInternalStream().getLocalContext().setNewTuples(null);
            orderStream.getInternalStream().process(context);

            // ----------

            List<Object[]> expectedResults = new ArrayList<Object[]>();

            expectedResults.add(new Object[]{2, 19});
            expectedResults.add(new Object[]{2, 19});
            expectedResults.add(new Object[]{3, 38});
            expectedResults.add(new Object[]{5, 64});
            expectedResults.add(new Object[]{6, 91});
            expectedResults.add(new Object[]{5, 82});

            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStreamedSink(expectedResults, 5);
            verifyCollection(expectedResults, received);

            // ----------

            orderStream.getInternalStream().getLocalContext().setDeadTuples(allOrders);
            orderStream.getInternalStream().getLocalContext().setNewTuples(null);
            orderStream.getInternalStream().process(context);

            expectedResults = new ArrayList<Object[]>();
            expectedResults.add(new Object[]{0, 0});

            received = collectAndMatchStreamedSink(expectedResults, 5);
            verifyCollection(expectedResults, received);

            // ----------

            LocalContext lc = new LocalContext();
            lc.setDeadTuples(null);
            lc.setNewTuples(null);
            LocalContext origLC = context.getLocalContext();
            context.setLocalContext(lc);
            partitionedStream.process(context);
            context.setLocalContext(origLC);

            expectedResults = new ArrayList<Object[]>();

            received = collectAndMatchStreamedSink(expectedResults, 5);

            // ----------

            priorityCustomerStream.getInternalStream().getLocalContext()
                    .setDeadTuples(allCustomers);
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

    public static class OrderFulfilmentTuple extends TrackedTuple {
        public OrderFulfilmentTuple(Number id) {
            super(id);
        }

        public OrderFulfilmentTuple(Number id, Object[] columns) {
            super(id, columns);
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

    public static class AggregatedOutputTuple extends TrackedTuple {
        public AggregatedOutputTuple(Number id) {
            super(id);
        }

        public AggregatedOutputTuple(Number id, Object[] columns) {
            super(id, columns);
        }
    }

    public static class Order {
        protected String orderId;

        protected String customerId;

        protected int qty;

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public String getOrderId() {
            return orderId;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
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
