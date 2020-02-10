package com.tibco.cep.query.exec;

import com.tibco.cep.query.exec.prebuilt.HeavyJoinedTuples;
import com.tibco.cep.query.exec.prebuilt.HeavyReteEntities;
import com.tibco.cep.query.exec.prebuilt.HeavyTuples;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.EvaluatableExpression;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Nov 5, 2007
 * Time: 6:52:43 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Sample execution plan.
 */
public class SampleManuallyGeneratedExecutionPlan extends com.tibco.cep.query.exec.codegen.AbstractQueryExecutionPlan {


    // Shared objects
    protected static final com.tibco.cep.query.stream.impl.rete.ReteEntityInfo TUPLE_INFO_0001;
    protected static final com.tibco.cep.query.stream.impl.rete.ReteEntityInfo TUPLE_INFO_0002;
    protected static final com.tibco.cep.query.stream.expression.TupleValueExtractor VE_0003;
    protected static final com.tibco.cep.query.stream.expression.TupleValueExtractor VE_0004;
    protected static final java.util.Map<String, Class<? extends com.tibco.cep.query.stream.tuple.Tuple>> ALIAS_MAP_0005a;
    protected static final java.util.Map<String, Class<? extends com.tibco.cep.query.stream.tuple.Tuple>> ALIAS_MAP_0005b;
    protected static final com.tibco.cep.query.stream.expression.EvaluatableExpression EXPR_0006a;
    protected static final com.tibco.cep.query.stream.expression.EvaluatableExpression EXPR_0006b;
    protected static final com.tibco.cep.query.stream.impl.rete.join.EqualsExpression EXPR_0006c;
    protected static final com.tibco.cep.query.stream.expression.ComplexAndExpression EXPR_0007;
    protected static final com.tibco.cep.query.stream.join.JoinedTupleInfo TUPLE_INFO_0008;
    protected static final com.tibco.cep.query.stream.tuple.TupleInfo TUPLE_INFO_0009;
    protected static final com.tibco.cep.query.stream.expression.TupleValueExtractor VE_0010;
    protected static final com.tibco.cep.query.stream.expression.TupleValueExtractor VE_0011;
    protected static final java.util.LinkedHashMap<String, com.tibco.cep.query.stream.transform.TransformItemInfo> TRANSFINFO_MAP_0012;
    protected static final com.tibco.cep.query.stream.transform.TransformInfo TRANSFINFO_0013;


    // Initialization of shared objects
    static {

        // Source 1 output

        TUPLE_INFO_0001 = new HeavyReteEntities.HeavyReteEntityInfo(
                new String[]{"stockquote"},
                new Class[]{GeneratedEvent0547.class}, 1);

        // Source 2 output

        TUPLE_INFO_0002 = new HeavyReteEntities.HeavyReteEntityInfo(
                new String[]{"stockquoteToo"},
                new Class[]{GeneratedEvent0547.class}, 2);

        // Join expression

        VE_0003 = new TVE0079();
        VE_0004 = new TVE0080();

        ALIAS_MAP_0005a = new java.util.HashMap<String, Class<? extends com.tibco.cep.query.stream.tuple.Tuple>>();
        ALIAS_MAP_0005a.put("stream1", TUPLE_INFO_0001.getContainerClass());

        EXPR_0006a = new E0001();

        ALIAS_MAP_0005b = new java.util.HashMap<String, Class<? extends com.tibco.cep.query.stream.tuple.Tuple>>();
        ALIAS_MAP_0005b.put("stream2", TUPLE_INFO_0002.getContainerClass());

        EXPR_0006b = new E0002();

        EXPR_0006c = new com.tibco.cep.query.stream.impl.rete.join.EqualsExpression(EXPR_0006a, EXPR_0006b);

        EXPR_0007 = new com.tibco.cep.query.stream.expression.ComplexAndExpression(
                new com.tibco.cep.query.stream.expression.Expression[]{EXPR_0006c});

        // Join output

        TUPLE_INFO_0008 = new HeavyJoinedTuples.HeavyJoinedTupleInfo(
                new String[]{"stream1", "stream2"},
                new Class[]{TUPLE_INFO_0001.getContainerClass(), TUPLE_INFO_0002.getContainerClass()}, 0);

        // Transformation info

        VE_0010 = new TVE0081();
        VE_0011 = new TVE0082();

        TRANSFINFO_MAP_0012 = new java.util.LinkedHashMap<String, com.tibco.cep.query.stream.transform.TransformItemInfo>();
        TRANSFINFO_MAP_0012.put("blah", new com.tibco.cep.query.stream.transform.TransformItemInfo(VE_0010));
        TRANSFINFO_MAP_0012.put("p", new com.tibco.cep.query.stream.transform.TransformItemInfo(VE_0011));

        TRANSFINFO_0013 = new com.tibco.cep.query.stream.transform.TransformInfo(TRANSFINFO_MAP_0012);

        // Transformation output

        TUPLE_INFO_0009 = new HeavyTuples.HeavyTupleInfo(new String[]{"blah", "p"},
                new Class[]{String.class, Double.class}, 0);

    }


    public SampleManuallyGeneratedExecutionPlan(String $0, String $1)
            throws Exception {
        super($0, $1, "select stock1.symbol as blah, stock1.price as p from \"/Concepts/StockQuote\" stock1, \"/Concepts/StockQuote\" stock2 where stock1.x = stock2.y");
    }


    public void initializeAsContinuous() throws Exception {
        final com.tibco.cep.query.stream.core.Stream source0001 = this.buildSource("stream1", TUPLE_INFO_0001, "stock1");

        final com.tibco.cep.query.stream.core.Stream source0002 = this.buildSource("stream2", TUPLE_INFO_0002, "stock2");

        final com.tibco.cep.query.stream.core.Stream join0003 = this.buildJoin("joinStream",
                new com.tibco.cep.query.stream.core.Stream[]{source0001, source0002}, EXPR_0007, TUPLE_INFO_0008);

        final com.tibco.cep.query.stream.core.Stream transf0004 = this.buildTransform("transfStream", join0003,
                TRANSFINFO_0013, TUPLE_INFO_0009);

        final com.tibco.cep.query.stream.core.Sink sink0005 = this.buildSink("sink", transf0004, null);
        this.sink = sink0005;
    }


    public void initializeAsSnapshot() throws Exception {
        final com.tibco.cep.query.stream.core.Stream source0001 = this.buildSource("stream1", TUPLE_INFO_0001, "stock1");
        final com.tibco.cep.query.stream.core.Stream source0002 = this.buildSource("stream2", TUPLE_INFO_0002, "stock2");

        final com.tibco.cep.query.stream.core.Stream join0003 = this.buildJoin("joinStream",
                new com.tibco.cep.query.stream.core.Stream[]{source0001, source0002}, EXPR_0007, TUPLE_INFO_0008);

        final com.tibco.cep.query.stream.core.Stream transf0004 = this.buildTransform("transfStream", join0003,
                TRANSFINFO_0013, TUPLE_INFO_0009);

        final com.tibco.cep.query.stream.core.Sink sink0005 = this.buildSink("sink", null, null);
        this.sink = sink0005;

        this.buildSimpleBridge("bridge", transf0004, sink0005.getInternalStream());
    }

    // EvaluatableExpression's


    private static class E0001 extends com.tibco.cep.query.stream.expression.AbstractExpression implements
            EvaluatableExpression {

        public E0001() {
            super(ALIAS_MAP_0005a);
        }

        public Object evaluate(GlobalContext $1, QueryContext $2,
                               FixedKeyHashMap<String, ? extends Tuple> $3) {
            final com.tibco.cep.query.stream.context.GlobalContext globalCtx = $1;
            final QueryContext queryCtx = $2;
            final java.util.Map aliasToTuple = $3;
            try {
                return VE_0003.extract(globalCtx, queryCtx,
                        ((com.tibco.cep.query.stream.tuple.Tuple) aliasToTuple.get("stream1")));
            } catch (Exception shouldNotHappen) {
                throw new RuntimeException(shouldNotHappen);
            }
        }

        public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            throw new UnsupportedOperationException();
        }

        public long evaluateLong(GlobalContext globalContext, QueryContext queryContext,
                                 FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            throw new UnsupportedOperationException();
        }

        public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            throw new UnsupportedOperationException();
        }

        public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext,
                                     FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            throw new UnsupportedOperationException();
        }

        public boolean evaluateBoolean(com.tibco.cep.query.stream.context.GlobalContext $1,
                                       QueryContext $2,
                                       FixedKeyHashMap<String, ? extends Tuple> $3) {
            throw new UnsupportedOperationException();
        }
    }


    private static class E0002 extends com.tibco.cep.query.stream.expression.AbstractExpression implements
            EvaluatableExpression {

        public E0002() {
            super(ALIAS_MAP_0005b);
        }

        public Object evaluate(GlobalContext $1, QueryContext $2,
                               FixedKeyHashMap<String,? extends Tuple> $3) {
            final com.tibco.cep.query.stream.context.GlobalContext globalCtx = $1;
            final QueryContext queryCtx = $2;
            final java.util.Map aliasToTuple = $3;
            try {
                return VE_0004.extract(globalCtx, queryCtx,
                        ((com.tibco.cep.query.stream.tuple.Tuple) aliasToTuple.get("stream2")));
            } catch (Exception shouldNotHappen) {
                throw new RuntimeException(shouldNotHappen);
            }
        }

        public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            throw new UnsupportedOperationException();
        }

        public long evaluateLong(GlobalContext globalContext, QueryContext queryContext,
                                 FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            throw new UnsupportedOperationException();
        }

        public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            throw new UnsupportedOperationException();
        }

        public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext,
                                     FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            throw new UnsupportedOperationException();
        }

        public boolean evaluateBoolean(com.tibco.cep.query.stream.context.GlobalContext $1,
                                       QueryContext $2,
                                       FixedKeyHashMap<String, ? extends Tuple> $3) {
            throw new UnsupportedOperationException();
        }
    }


    // TupleValueExtractor's


    protected static class TVE0079 implements TupleValueExtractor {


        public Object extract(com.tibco.cep.query.stream.context.GlobalContext $1,
                              QueryContext $2,
                              com.tibco.cep.query.stream.tuple.Tuple $3) {
            final com.tibco.cep.query.stream.tuple.Tuple tuple = $3;
            try {
                return ((com.tibco.cep.runtime.model.event.SimpleEvent) tuple.getColumn(0)).getProperty("x");
            } catch (Exception shouldNotHappen) {
                throw new RuntimeException(shouldNotHappen);
            }
        }

        public int extractInteger(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
            return 0;
        }

        public long extractLong(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
            return 0;
        }

        public float extractFloat(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
            return 0;
        }

        public double extractDouble(GlobalContext globalContext, QueryContext queryContext,
                                    Tuple tuple) {
            return 0;
        }

    }


    protected static class TVE0080 implements TupleValueExtractor {


        public Object extract(com.tibco.cep.query.stream.context.GlobalContext $1,
                              QueryContext $2,
                              com.tibco.cep.query.stream.tuple.Tuple $3) {
            final com.tibco.cep.query.stream.tuple.Tuple tuple = $3;
            try {
                return ((com.tibco.cep.runtime.model.event.SimpleEvent) tuple.getColumn(1)).getProperty("y");
            } catch (Exception shouldNotHappen) {
                throw new RuntimeException(shouldNotHappen);
            }
        }

        public int extractInteger(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
            return 0;
        }

        public long extractLong(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
            return 0;
        }

        public float extractFloat(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
            return 0;
        }

        public double extractDouble(GlobalContext globalContext, QueryContext queryContext,
                                    Tuple tuple) {
            return 0;
        }

    }


    protected static class TVE0081 implements TupleValueExtractor {


        public Object extract(com.tibco.cep.query.stream.context.GlobalContext $1,
                              QueryContext $2,
                              com.tibco.cep.query.stream.tuple.Tuple $3) {
            final com.tibco.cep.query.stream.tuple.Tuple tuple = $3;
            try {
                return ((com.tibco.cep.runtime.model.event.SimpleEvent) ((com.tibco.cep.query.stream.tuple.Tuple) tuple.getColumn(0)).getColumn(0)).getProperty("symbol");
            } catch (Exception shouldNotHappen) {
                throw new RuntimeException(shouldNotHappen);
            }
        }

        public int extractInteger(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
            return 0;
        }

        public long extractLong(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
            return 0;
        }

        public float extractFloat(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
            return 0;
        }

        public double extractDouble(GlobalContext globalContext, QueryContext queryContext,
                                    Tuple tuple) {
            return 0;
        }

    }

    protected static class TVE0082 implements TupleValueExtractor {

        public Object extract(com.tibco.cep.query.stream.context.GlobalContext $1,
                              QueryContext $2,
                              com.tibco.cep.query.stream.tuple.Tuple $3) {
            final com.tibco.cep.query.stream.tuple.Tuple tuple = $3;
            try {
                return ((com.tibco.cep.runtime.model.event.SimpleEvent) ((com.tibco.cep.query.stream.tuple.Tuple) tuple.getColumn(1)).getColumn(1)).getProperty("price");
            } catch (Exception shouldNotHappen) {
                throw new RuntimeException(shouldNotHappen);
            }
        }

        public int extractInteger(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
            return 0;
        }

        public long extractLong(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
            return 0;
        }

        public float extractFloat(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
            return 0;
        }

        public double extractDouble(GlobalContext globalContext, QueryContext queryContext,
                                    Tuple tuple) {
            return 0;
        }

    }












    //__________________________________________________________________________________________________________

    // Will be found in the Ontology instead of here.

    private static class GeneratedEvent0547 extends com.tibco.cep.runtime.model.event.impl.SimpleEventImpl {

        public void deserializeProperty(com.tibco.cep.runtime.model.event.EventDeserializer deserializer, int index) {
        }

        public String[] getPropertyNames() {
            return new String[]{"price", "symbol"};
        }

        public com.tibco.xml.data.primitive.ExpandedName getExpandedName() {
            return null;
        }

        public long getTTL() {
            return 0;
        }

        public boolean getRetryOnException() {
            return true;
        }

        @Override
        public String getType() {
            //todo
            return null;
        }
    }


}
