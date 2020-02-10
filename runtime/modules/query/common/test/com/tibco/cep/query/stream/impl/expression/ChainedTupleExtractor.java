package com.tibco.cep.query.stream.impl.expression;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.tuple.Tuple;

import java.io.Serializable;

/*
* Author: Ashwin Jayaprakash Date: Jun 10, 2008 Time: 3:51:16 PM
*/
public class ChainedTupleExtractor implements TupleValueExtractor, Serializable {
    protected TupleExtractor firstTupleExtractor;

    /**
     * Cannot be <code>null</code>.
     */
    protected TupleExtractor lastObjectExtractor;

    protected ModelExtractor[] initialModelExtractors;

    protected ModelExtractorAdapter modelExtractorAdapter;

    /**
     * <pre>
     * Expression: ..from T t .. group by t.address.zipcode
     * From JoinedTuple: extractColumnAsTuple(x)
     * T: extractColumnAsObject(z)
     * Address: extractObject()
     * Zipcode: extractString()
     * </pre>
     *
     * @param firstTupleExtractor
     * @param lastObjectExtractor    Cannot be <code>null</code>.
     * @param initialModelExtractors
     * @param modelExtractorAdapter
     */
    public ChainedTupleExtractor(TupleExtractor firstTupleExtractor,
                                 TupleExtractor lastObjectExtractor,
                                 ModelExtractor[] initialModelExtractors,
                                 ModelExtractorAdapter modelExtractorAdapter) {
        this.firstTupleExtractor = firstTupleExtractor;
        this.lastObjectExtractor = lastObjectExtractor;
        this.initialModelExtractors = initialModelExtractors;
        this.modelExtractorAdapter = modelExtractorAdapter;
    }

    /**
     * <pre>
     * Expression: ..from T t .. group by t
     * extractColumnAsObject(x)
     * </pre>
     * <p/>
     * Only {@link com.tibco.cep.query.stream.expression.ExpressionEvaluator#evaluateInteger(com.tibco.cep.query.stream.context.GlobalContext, com.tibco.cep.query.stream.context.QueryContext, com.tibco.cep.query.stream.util.FixedKeyHashMap)}
     * be used.
     *
     * @param lastObjectExtractor
     */
    public ChainedTupleExtractor(TupleExtractor lastObjectExtractor) {
        this(null, lastObjectExtractor, null, null);
    }

    public TupleExtractor getFirstTupleExtractor() {
        return firstTupleExtractor;
    }

    public TupleExtractor getLastObjectExtractor() {
        return lastObjectExtractor;
    }

    public ModelExtractor[] getInitialModelExtractors() {
        return initialModelExtractors;
    }

    public ModelExtractorAdapter getModelExtractorAdapter() {
        return modelExtractorAdapter;
    }

    /**
     * <pre>
     * firstTupleExtractor.extractColumnAsTuple(x)
     * lastObjectExtractor.extractColumnAsObject(y..)
     * [initialModelExtractors.extractObject(..)]*
     * </pre>
     *
     * @param globalContext
     * @param queryContext
     * @param sourceTuple
     * @return
     */
    protected Object extractUntilModelExtractorAdapter(GlobalContext globalContext,
                                                       QueryContext queryContext,
                                                       Tuple sourceTuple) {
        Tuple tuple = sourceTuple;

        if (firstTupleExtractor != null) {
            tuple = firstTupleExtractor.extractColumnAsTuple(globalContext, queryContext, tuple);
        }

        //------------

        Object object =
                lastObjectExtractor.extractColumnAsObject(globalContext, queryContext, tuple);

        //------------

        if (initialModelExtractors != null) {
            for (ModelExtractor modelExtractor : initialModelExtractors) {
                object = modelExtractor.extractObject(globalContext, queryContext, object);
            }
        }

        return object;
    }

    public int extractInteger(GlobalContext globalContext, QueryContext queryContext,
                              Tuple sourceTuple) {
        Object object =
                extractUntilModelExtractorAdapter(globalContext, queryContext, sourceTuple);

        return modelExtractorAdapter.extractInteger(globalContext, queryContext, object);
    }

    public float extractFloat(GlobalContext globalContext, QueryContext queryContext,
                              Tuple sourceTuple) {
        Object object =
                extractUntilModelExtractorAdapter(globalContext, queryContext, sourceTuple);

        return modelExtractorAdapter.extractFloat(globalContext, queryContext, object);
    }

    public long extractLong(GlobalContext globalContext, QueryContext queryContext,
                            Tuple sourceTuple) {
        Object object =
                extractUntilModelExtractorAdapter(globalContext, queryContext, sourceTuple);

        return modelExtractorAdapter.extractLong(globalContext, queryContext, object);
    }

    public double extractDouble(GlobalContext globalContext, QueryContext queryContext,
                                Tuple sourceTuple) {
        Object object =
                extractUntilModelExtractorAdapter(globalContext, queryContext, sourceTuple);

        return modelExtractorAdapter.extractDouble(globalContext, queryContext, object);
    }

    public Object extract(GlobalContext globalContext, QueryContext queryContext,
                          Tuple sourceTuple) {
        Object object =
                extractUntilModelExtractorAdapter(globalContext, queryContext, sourceTuple);

        if (modelExtractorAdapter == null) {
            return object;
        }

        return modelExtractorAdapter.extractObject(globalContext, queryContext, object);
    }

    public String extractString(GlobalContext globalContext, QueryContext queryContext,
                                Tuple sourceTuple) {
        Object object =
                extractUntilModelExtractorAdapter(globalContext, queryContext, sourceTuple);

        return modelExtractorAdapter.extractString(globalContext, queryContext, object);
    }

    public boolean extractBoolean(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple sourceTuple) {
        Object object =
                extractUntilModelExtractorAdapter(globalContext, queryContext, sourceTuple);

        return modelExtractorAdapter.extractBoolean(globalContext, queryContext, object);
    }
}