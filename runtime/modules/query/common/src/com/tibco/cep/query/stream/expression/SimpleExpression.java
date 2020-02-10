package com.tibco.cep.query.stream.expression;

import java.util.Map;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Jan 5, 2009
* Time: 6:20:46 PM
*/
public class SimpleExpression
    extends AbstractExpression
    implements EvaluatableExpression  {


    private ExpressionEvaluator evaluator;


    /**
     * @param aliasAndTypes Stream alias and the Tuple types.
     */
    public SimpleExpression(
            ExpressionEvaluator evaluator,
            Map<String, Class<? extends Tuple>> aliasAndTypes) {
        super(aliasAndTypes);
        this.evaluator = evaluator;
    }


    /**
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public Object evaluate(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return this.evaluator.evaluate(globalContext, queryContext, aliasAndTuples);
    }


    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public boolean evaluateBoolean(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return this.evaluator.evaluateBoolean(globalContext, queryContext, aliasAndTuples);
    }


    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public int evaluateInteger(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return this.evaluator.evaluateInteger(globalContext, queryContext, aliasAndTuples);
    }


    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public long evaluateLong(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return this.evaluator.evaluateLong(globalContext, queryContext, aliasAndTuples);
    }


    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public float evaluateFloat(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return this.evaluator.evaluateFloat(globalContext, queryContext, aliasAndTuples);
    }


    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public double evaluateDouble(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return this.evaluator.evaluateDouble(globalContext, queryContext, aliasAndTuples);
    }
}
