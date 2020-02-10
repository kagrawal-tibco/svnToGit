package com.tibco.cep.query.stream._retired_;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.kernel.core.rete.conflict.SimpleConflictResolver;
import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.model.rule.Condition;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.impl.ActionImpl;
import com.tibco.cep.kernel.model.rule.impl.ConditionImpl;
import com.tibco.cep.kernel.model.rule.impl.IdentifierImpl;
import com.tibco.cep.kernel.model.rule.impl.RuleImpl;
import com.tibco.cep.kernel.service.impl.DefaultExceptionHandler;
import com.tibco.cep.kernel.service.impl.DefaultLogger;
import com.tibco.cep.kernel.service.impl.DefaultObjectManager;
import com.tibco.cep.kernel.service.impl.DefaultTimeManager;
import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.aggregate.AggregateItemInfo;
import com.tibco.cep.query.stream.aggregate.AggregatedStream;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.framework.TrackedTuple;
import com.tibco.cep.query.stream.group.GroupAggregateInfo;
import com.tibco.cep.query.stream.group.GroupAggregateItemInfo;
import com.tibco.cep.query.stream.group.GroupAggregateTransformer;
import com.tibco.cep.query.stream.group.GroupItemInfo;
import com.tibco.cep.query.stream.impl.aggregate.AverageAggregator;
import com.tibco.cep.query.stream.impl.aggregate.CountAggregator;
import com.tibco.cep.query.stream.impl.rete.HeavyReteEntity;
import com.tibco.cep.query.stream.impl.rete.ReteEntityInfo;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySource;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySourceImpl;
import com.tibco.cep.query.stream.impl.rete.input.ReteEntityDispatcher;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.io.StaticSink;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.AggregatedPartitionedStream;
import com.tibco.cep.query.stream.partition.SimpleAggregatedStreamWindow;
import com.tibco.cep.query.stream.partition.Window;
import com.tibco.cep.query.stream.partition.WindowBuilder;
import com.tibco.cep.query.stream.partition.WindowInfo;
import com.tibco.cep.query.stream.partition.WindowOwner;
import com.tibco.cep.query.stream.query.singleshot.Bridge;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.HeavyTuple;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;

/*
 * Author: Ashwin Jayaprakash Date: Nov 19, 2007 Time: 11:16:23 AM
 */

public class InitialDataFeederTest extends AbstractTest {
    @Test(groups = { TestGroupNames.RUNTIME })
    public void testSync() {
        test(true);
    }

    @Test(groups = { TestGroupNames.RUNTIME })
    public void testAsync() {
        test(false);
    }

    public void test(boolean syncOrAsync) {
        ReteWM rete = null;
        try {
            DefaultLogger logger = new DefaultLogger();
            DefaultObjectManager objectManager = new DefaultObjectManager("Test");

            rete = new ReteWM("Test", logger, new DefaultExceptionHandler(logger), objectManager,
                    new DefaultTimeManager(new Action() {
                        public void execute(Object[] objects) {
                        }

                        public Identifier[] getIdentifiers() {
                            return null;
                        }

                        public Rule getRule() {
                            return null;
                        }
                    }), new SimpleConflictResolver());

            objectManager.init(rete);
            rete.start(true);

            init(rete);
            runTest(rete, syncOrAsync);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if (rete != null) {
                try {
                    rete.shutdown(null);
                }
                catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    public void init(ReteWM rete) throws Exception {
        List<Rule> rules = new LinkedList<Rule>();

        SimpleRule simpleRule = new SimpleRule(Employee.class.getName(), Employee.class);
        simpleRule.init();
        rules.add(simpleRule);

        simpleRule = new SimpleRule(Department.class.getName(), Department.class);
        simpleRule.init();
        rules.add(simpleRule);

        rete.init(null, null, rules);
    }

    public void runTest(ReteWM reteWM, boolean syncOrAsync) throws Exception {
        LinkedHashMap<String, GroupAggregateItemInfo> itemInfos = new LinkedHashMap<String, GroupAggregateItemInfo>();

        itemInfos.put("name", new GroupAggregateItemInfo(new GroupItemInfo(
                new TupleValueExtractor() {
                    @Override
                    public Object extract(GlobalContext globalContext, QueryContext queryContext,
                            Tuple tuple) {
                        Employee event = (Employee) tuple.getColumn(0);
                        return event.getName();
                    }
                })));
        itemInfos.put("count", new GroupAggregateItemInfo(new AggregateItemInfo(
                CountAggregator.CREATOR, new TupleValueExtractor() {
                    @Override
                    public Object extract(GlobalContext globalContext, QueryContext queryContext,
                            Tuple tuple) {
                        Employee event = (Employee) tuple.getColumn(0);
                        return event.getName();
                    }
                })));
        itemInfos.put("avg", new GroupAggregateItemInfo(new AggregateItemInfo(
                AverageAggregator.CREATOR, new TupleValueExtractor() {
                    @Override
                    public Object extract(GlobalContext globalContext, QueryContext queryContext,
                            Tuple tuple) {
                        Employee event = (Employee) tuple.getColumn(0);
                        return event.getAge();
                    }
                })));

        // ----------

        String[] columnNames = new String[] { "employeeEvent" };
        Class[] columnTypes = new Class[] { Employee.class };
        final ReteEntityInfo reteEntityInfo = new ReteEntityInfo(EmployeeEventTuple.class,
                columnNames, columnTypes);
        ReteEntitySource reteEntitySource = new ReteEntitySourceImpl(new ResourceId("Employees"),
                reteEntityInfo, Employee.class);
        sources.put("source", reteEntitySource);

        // ----------

        GroupAggregateInfo groupAggregateInfo = new GroupAggregateInfo(itemInfos);

        columnNames = new String[] { "name", "count", "avg" };
        columnTypes = new Class[] { String.class, Integer.class, Double.class };
        final TupleInfo outputTupleInfo = new TupleInfo(OutputTuple.class, columnNames, columnTypes);

        WindowBuilder builder = new WindowBuilder() {
            public Window buildAndInit(ResourceId parentResourceId, String id, int groupHash,
                    Object[] groupColumns, AggregateInfo aggregateInfo, WindowInfo windowInfo,
                    WindowOwner windowOwner, GroupAggregateTransformer aggregateTransformer) {
                Window window = new SimpleAggregatedStreamWindow(new ResourceId(parentResourceId,
                        "SimpleWindow:" + id), reteEntityInfo, groupHash, groupColumns, windowOwner);

                String[] names = new String[] { "count", "avg" };
                Class[] types = new Class[] { Integer.class, Double.class };
                AbstractTupleInfo aggregateTupleInfo = new TupleInfo(InternalAggregateTuple.class,
                        names, types);

                AggregatedStream aggregatedStream = new AggregatedStream(window, new ResourceId(
                        parentResourceId, "Aggregate:" + id), aggregateTupleInfo, aggregateInfo,
                        true);

                window.init(aggregateTransformer, aggregatedStream, null);

                return window;
            }
        };

        AggregatedPartitionedStream partitionedStream = new AggregatedPartitionedStream(
                reteEntitySource.getInternalStream(), new ResourceId("EmployeeWatcher"),
                outputTupleInfo, groupAggregateInfo, builder);

        // ----------

        Bridge bridge = new Bridge(partitionedStream, new ResourceId("Bridge"));

        sink = new StaticSink(new ResourceId("Sink"), bridge.getOutputInfo());

        bridge.setup(sink.getInternalStream());

        ReteQuery query = new ReteQuery(new ResourceId("AgeCounterQry:"
                + (syncOrAsync ? "Sync" : "Async")), new ReteEntitySource[] { reteEntitySource },
                sink);

        ReteEntityDispatcher dispatcher = regionManager.getDispatcher();
        dispatcher.registerQuery(query);

        query.init(repo, null);
        query.start();

        try {
            Set<Employee> allEmployees = new HashSet<Employee>();

            int total = 1000;
            String[] names = { "A", "B", "C", "D" };
            for (int i = 0; i < total; i++) {
                String name = names[i % names.length];
                int age = 25 + i;

                Employee e = new Employee(name, age);
                allEmployees.add(e);

                reteWM.assertObject(e, true);
            }

            reteWM.assertObject(new Department("D1"), true);
            reteWM.assertObject(new Department("D2"), true);
            reteWM.assertObject(new Department("D3"), true);
            reteWM.assertObject(new Department("D4"), true);
            reteWM.assertObject(new Department("D5"), true);

            // -----------

            // This part must be done by the Query framework.

            InitialDataFeeder dataFeeder = new InitialDataFeeder(query, syncOrAsync, regionManager
                    .getSom(), regionManager.getQom());
            FindMatchesRule[] matchesRules = dataFeeder.createFindMatchesRules();
            for (FindMatchesRule rule : matchesRules) {
                rule.init();

                reteWM.getRuleLoader().addRule("$Global", rule);
                reteWM.loadObjectToAddedRule();
            }

            // -----------

            for (FindMatchesRule rule : matchesRules) {
                reteWM.findMatches(rule, new Object[] { rule.getTrigger() }, true, true);
            }

            for (FindMatchesRule rule : matchesRules) {
                rule.end();
            }

            dataFeeder.end();

            // todo Find a better way to wait for the Query to complete.
            Thread.sleep(10 * 1000);
            bridge.endStreamInputAndFlush(query.getContext());

            // ----------

            List<Object[]> expectedResults = new ArrayList<Object[]>();
            expectedResults.add(new Object[] { "A", 250, 523.0 });
            expectedResults.add(new Object[] { "B", 250, 524.0 });
            expectedResults.add(new Object[] { "C", 250, 525.0 });
            expectedResults.add(new Object[] { "D", 250, 526.0 });

            Map<Tuple, Object[]> received = collectAndMatchStaticSink(expectedResults, 10);
            verifyCollection(expectedResults, received);

            // ----------

            for (Tuple tuple : received.keySet()) {
                if (tuple instanceof HeavyTuple) {
                    HeavyTuple heavyTuple = (HeavyTuple) tuple;

                    if (heavyTuple.getRefCount() <= 0) {
                        Assert.fail("Ref count should not be 0: " + tuple);
                    }
                }
            }

            for (Iterator<? extends Tuple> iter = InternalAggregateTuple.tuples.iterator(); iter
                    .hasNext();) {
                Tuple tuple = iter.next();

                if (tuple.getId() == null) {
                    iter.remove();
                }
            }

            Assert.assertEquals(InternalAggregateTuple.tuples.size(), 4,
                    "Some internal Tuples are still non-zero.");

            Set<Tuple> exceptions = new HashSet<Tuple>(received.keySet());
            exceptions.addAll(InternalAggregateTuple.tuples);

            commonTests(exceptions);

            InternalAggregateTuple.tuples.clear();
        }
        finally {
            query.stop();
            query.discard();
        }
    }

    // ----------

    public static class Employee {
        protected final String name;

        protected final int age;

        public Employee(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }
    }

    public static class Department {
        protected final String name;

        public Department(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static class SimpleRule extends RuleImpl {
        protected final Identifier ident;

        public SimpleRule(String name, Class targetClass) {
            super(name);

            ident = new IdentifierImpl(targetClass, "$x");
        }

        public void init() {
            m_identifiers = createIdentifiers();
            m_conditions = createConditions();
            m_actions = createActions();
        }

        protected Identifier[] createIdentifiers() {
            return new Identifier[] { ident };
        }

        protected Condition[] createConditions() {
            return new Condition[] { new ConditionImpl(this, new Identifier[] { ident }) {
                @Override
                public boolean eval(Object[] objects) {
                    return false;
                }
            } };
        }

        protected Action[] createActions() {
            return new Action[] { new ActionImpl(this) {
                @Override
                public void execute(Object[] objects) {
                }
            } };
        }
    }

    public static class EmployeeEventTuple extends HeavyReteEntity {
        public EmployeeEventTuple() {
            super();
        }

        public EmployeeEventTuple(long reteId) {
            super(reteId);
        }

        public EmployeeEventTuple(Long id, Object[] columns, long reteId) {
            super(id, columns, reteId);
        }
    }

    public static class DepartmentEventTuple extends HeavyReteEntity {
        public DepartmentEventTuple() {
            super();
        }

        public DepartmentEventTuple(long reteId) {
            super(reteId);
        }

        public DepartmentEventTuple(Long id, Object[] columns, long reteId) {
            super(id, columns, reteId);
        }
    }

    public static class OutputTuple extends TrackedTuple {
        public OutputTuple() {
            super();
        }

        public OutputTuple(Long id, Object[] columns) {
            super(id, columns);
        }
    }

    public static class InternalAggregateTuple extends TrackedTuple {
        static final LinkedList<InternalAggregateTuple> tuples = new LinkedList<InternalAggregateTuple>();

        public InternalAggregateTuple() {
            super();

            tuples.addLast(this);
        }

        public InternalAggregateTuple(Long id, Object[] columns) {
            super(id, columns);

            tuples.addLast(this);
        }
    }
}
