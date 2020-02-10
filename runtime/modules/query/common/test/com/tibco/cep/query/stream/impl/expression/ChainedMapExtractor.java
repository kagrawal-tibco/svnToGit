package com.tibco.cep.query.stream.impl.expression;

import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

import java.io.Serializable;

/*
* Author: Ashwin Jayaprakash Date: Jun 10, 2008 Time: 3:51:16 PM
*/
public class ChainedMapExtractor implements ExpressionEvaluator, Serializable {
    /**
     * Cannot be <code>null</code>.
     */
    protected TupleExtractor firstTupleExtractor;

    /**
     * Cannot be <code>null</code>.
     */
    protected TupleExtractor lastObjectExtractor;

    protected ModelExtractor[] initialModelExtractors;

    protected ModelExtractorAdapter modelExtractorAdapter;

    /**
     * <pre>
     * Expression: ..from T t where t.address.zipcode = ...
     * <p/>
     * extractTuple(alias)
     * extractColumnAsObject(x)
     * Address: extractObject()
     * Zipcode: extractString()
     * </pre>
     *
     * @param firstTupleExtractor    Cannot be <code>null</code>.
     * @param lastObjectExtractor    Cannot be <code>null</code>.
     * @param initialModelExtractors
     * @param modelExtractorAdapter
     */
    public ChainedMapExtractor(TupleExtractor firstTupleExtractor,
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
     * Expression: ..from T t, R r where t == r
     * <p/>
     * extractTuple(alias)
     * extractColumnAsObject(x)
     * </pre>
     * <p/>
     * Only {@link TupleExtractor#extractColumnAsObject(com.tibco.cep.query.stream.context.GlobalContext,com.tibco.cep.query.stream.context.QueryContext,com.tibco.cep.query.stream.tuple.Tuple)}
     * can be used.
     *
     * @param firstTupleExtractor
     * @param lastObjectExtractor
     */
    public ChainedMapExtractor(TupleExtractor firstTupleExtractor,
                               TupleExtractor lastObjectExtractor) {
        this(firstTupleExtractor, lastObjectExtractor, null, null);
    }

    /**
     * <pre>
     * Expression: ..where t.age
     * <p/>
     * extractTuple(alias)
     * extractColumnAsObject(x)
     * extractInt/Float/Long/Double/String/Object()
     * </pre>
     *
     * @param firstTupleExtractor
     * @param lastObjectExtractor
     * @param modelExtractorAdapter
     */
    public ChainedMapExtractor(TupleExtractor firstTupleExtractor,
                               TupleExtractor lastObjectExtractor,
                               ModelExtractorAdapter modelExtractorAdapter) {
        this(firstTupleExtractor, lastObjectExtractor, null, modelExtractorAdapter);
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
     * firstTupleExtractor.extractTuple(alias)
     * lastObjectExtractor.extractColumnAsObject(..)
     * [initialModelExtractors.extractObject(..)]*
     * </pre>
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    protected Object extractUntilModelExtractorAdapter(GlobalContext globalContext,
                                                       QueryContext queryContext,
                                                       FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        Tuple tuple = firstTupleExtractor.extractTuple(globalContext, queryContext, aliasAndTuples);

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

    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        Object object =
                extractUntilModelExtractorAdapter(globalContext, queryContext, aliasAndTuples);

        return modelExtractorAdapter.extractBoolean(globalContext, queryContext, object);
    }

    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        Object object =
                extractUntilModelExtractorAdapter(globalContext, queryContext, aliasAndTuples);

        return modelExtractorAdapter.extractInteger(globalContext, queryContext, object);
    }

    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        Object object =
                extractUntilModelExtractorAdapter(globalContext, queryContext, aliasAndTuples);

        return modelExtractorAdapter.extractFloat(globalContext, queryContext, object);
    }

    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext,
                             FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        Object object =
                extractUntilModelExtractorAdapter(globalContext, queryContext, aliasAndTuples);

        return modelExtractorAdapter.extractLong(globalContext, queryContext, object);
    }

    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext,
                                 FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        Object object =
                extractUntilModelExtractorAdapter(globalContext, queryContext, aliasAndTuples);

        return modelExtractorAdapter.extractDouble(globalContext, queryContext, object);
    }

    public Object evaluate(GlobalContext globalContext, QueryContext queryContext,
                           FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        Object object =
                extractUntilModelExtractorAdapter(globalContext, queryContext, aliasAndTuples);

        if (modelExtractorAdapter == null) {
            return object;
        }

        return modelExtractorAdapter.extractObject(globalContext, queryContext, object);
    }

    public String evaluateString(GlobalContext globalContext, DefaultQueryContext queryContext,
                                 FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        Object object =
                extractUntilModelExtractorAdapter(globalContext, queryContext, aliasAndTuples);

        return modelExtractorAdapter.extractString(globalContext, queryContext, object);
    }
}
