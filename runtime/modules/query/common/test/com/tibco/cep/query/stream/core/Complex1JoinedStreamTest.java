package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.context.*;
import com.tibco.cep.query.stream.expression.ComplexAndExpression;
import com.tibco.cep.query.stream.expression.EvaluatableExpression;
import com.tibco.cep.query.stream.expression.Expression;
import com.tibco.cep.query.stream.framework.*;
import com.tibco.cep.query.stream.impl.rete.join.EqualsExpression;
import com.tibco.cep.query.stream.impl.rete.join.JoinedStreamImpl;
import com.tibco.cep.query.stream.io.SourceImpl;
import com.tibco.cep.query.stream.io.StreamedSink;
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

public class Complex1JoinedStreamTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        try {
            final TupleInfo aInfo =
                    new AbstractTupleInfo(A.class, new String[]{"a"},
                            new Class[]{String.class}) {
                        public Tuple createTuple(Number id) {
                            return new A(id);
                        }
                    };
            final TupleInfo bInfo = new AbstractTupleInfo(B.class,
                    new String[]{"b"}, new Class[]{String.class}) {
                public Tuple createTuple(Number id) {
                    return new B(id);
                }
            };
            final TupleInfo cInfo =
                    new AbstractTupleInfo(C.class, new String[]{"c"},
                            new Class[]{String.class}) {
                        public Tuple createTuple(Number id) {
                            return new C(id);
                        }
                    };
            final JoinedTupleInfo joinInfo = new JoinedTupleInfoImpl(ABC.class,
                    new String[]{"aStream", "bStream", "cStream"}, new Class[]{
                    A.class, B.class, C.class});

            String queryName = "Test";

            Source aStream = new SourceImpl(new ResourceId(""), aInfo);
            sources.put("aStream", aStream);

            Source bStream = new SourceImpl(new ResourceId(""), bInfo);
            sources.put("bStream", bStream);

            Source cStream = new SourceImpl(new ResourceId(""), cInfo);
            sources.put("cStream", cStream);

            Map<String, Stream> joinSources = new HashMap<String, Stream>();
            joinSources.put("aStream", aStream.getInternalStream());
            joinSources.put("bStream", bStream.getInternalStream());
            joinSources.put("cStream", cStream.getInternalStream());

            JoinEval joinABExpr = new JoinEval(new HashExpr("aStream", A.class),
                    new HashExpr("bStream", B.class));

            JoinEval joinBCExpr = new JoinEval(new HashExpr("bStream", B.class),
                    new HashExpr("cStream", C.class));

            ComplexAndExpression andExpression =
                    new ComplexAndExpression(new Expression[]{joinABExpr, joinBCExpr});

            JoinedStreamImpl joinedStream =
                    new JoinedStreamImpl(master.getAgentService().getName(), queryName,
                            new ResourceId("Join"), joinSources,
                            null, andExpression, joinInfo);

            sink = new StreamedSink(joinedStream, new ResourceId(""));

            Context context = new Context(new DefaultGlobalContext(),
                    new DefaultQueryContext(master.getAgentService().getName(), queryName));

            ContextRepository contextRepository =
                    Registry.getInstance().getComponent(ContextRepository.class);
            contextRepository.registerContext(context.getQueryContext());

            // ----------

            WrappedCustomCollection<Tuple> newAs =
                    new WrappedCustomCollection(new LinkedList<Tuple>());
            Tuple data = new A(SimpleIdGenerator.generateNewId(), new Object[]{"a1"});
            newAs.add(data);
            Tuple candidateOrder1 = data;

            data = new A(SimpleIdGenerator.generateNewId(), new Object[]{"a2"});
            newAs.add(data);

            data = new A(SimpleIdGenerator.generateNewId(), new Object[]{"a3"});
            newAs.add(data);
            Tuple candidateOrder2 = data;

            aStream.getInternalStream().getLocalContext().setNewTuples(newAs);
            aStream.getInternalStream().process(context);

            // ----------

            WrappedCustomCollection<Tuple> newBs =
                    new WrappedCustomCollection(new LinkedList<Tuple>());

            data = new B(SimpleIdGenerator.generateNewId(), new Object[]{"b1"});
            newBs.add(data);

            data = new B(SimpleIdGenerator.generateNewId(), new Object[]{"b2"});
            newBs.add(data);

            data = new B(SimpleIdGenerator.generateNewId(), new Object[]{"b3"});
            newBs.add(data);

            bStream.getInternalStream().getLocalContext().setNewTuples(newBs);
            bStream.getInternalStream().process(context);

            // ----------

            WrappedCustomCollection<Tuple> newCs =
                    new WrappedCustomCollection(new LinkedList<Tuple>());

            data = new C(SimpleIdGenerator.generateNewId(), new Object[]{"c1"});
            newCs.add(data);

            data = new C(SimpleIdGenerator.generateNewId(), new Object[]{"c2"});
            newCs.add(data);

            data = new C(SimpleIdGenerator.generateNewId(), new Object[]{"c3"});
            newCs.add(data);

            bStream.getInternalStream().getLocalContext().setNewTuples(newBs);
            bStream.getInternalStream().process(context);

            // ----------

            List<Object[]> expectedResults = new ArrayList<Object[]>();
            collectAndMatchStreamedSink(expectedResults, 5);

            // ----------

            joinedStream.stop();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class A extends TrackedTuple {
        public A(Number id, Object[] columns) {
            super(id, columns);
        }

        public A(Number id) {
            super(id);
        }
    }

    public static class B extends TrackedTuple {
        public B(Number id, Object[] columns) {
            super(id, columns);
        }

        public B(Number id) {
            super(id);
        }
    }

    public static class C extends TrackedTuple {
        public C(Number id, Object[] columns) {
            super(id, columns);
        }

        public C(Number id) {
            super(id);
        }
    }

    public static class ABC extends TrackedJoinedTuple {
        public ABC(Number id) {
            super(id);
        }
    }

    public static class HashExpr implements EvaluatableExpression {
        protected final String streamName;

        protected final Map<String, Class<? extends Tuple>> map;

        public HashExpr(String streamName, Class<? extends Tuple> tupleClass) {
            this.streamName = streamName;

            this.map = new HashMap<String, Class<? extends Tuple>>();
            this.map.put(streamName, tupleClass);
        }

        public Map<String, Class<? extends Tuple>> getAliasAndTypes() {
            return map;
        }

        public Object evaluate(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            return aliasAndTuples.get(streamName).getColumn(0);
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