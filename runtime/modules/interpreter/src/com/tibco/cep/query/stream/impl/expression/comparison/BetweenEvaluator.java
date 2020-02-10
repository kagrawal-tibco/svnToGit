package com.tibco.cep.query.stream.impl.expression.comparison;

import java.io.Serializable;

import com.tibco.cep.query.exec.util.Comparisons;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.query.utils.TypeHelper;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Aug 14, 2008
* Time: 6:32:01 PM
*/
public class BetweenEvaluator
        implements ExpressionEvaluator, Serializable {


    protected ExpressionEvaluator exprEvaluator;
    protected ExpressionEvaluator bound1Evaluator;
    protected ExpressionEvaluator bound2Evaluator;

    public ExpressionEvaluator[] getBoundsEvaluator() {
        return new ExpressionEvaluator[]{bound1Evaluator, bound2Evaluator};
    }

    public ExpressionEvaluator getExpressionEvaluator() {
        return exprEvaluator;
    }

    public BetweenEvaluator(
            ExpressionEvaluator exprEvaluator,
            ExpressionEvaluator bound1Evaluator,
            ExpressionEvaluator bound2Evaluator) {
        this.exprEvaluator = exprEvaluator;
        this.bound1Evaluator = bound1Evaluator;
        this.bound2Evaluator = bound2Evaluator;
    }


    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return evaluateBoolean(globalContext, queryContext, aliasAndTuples);
    }


    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Comparable expr = (Comparable) this.exprEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        final Comparable bound1 = (Comparable) this.bound1Evaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        final Comparable bound2 = (Comparable) this.bound2Evaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        final Double exprAsDouble = TypeHelper.toDouble(expr);
        final Double bound1AsDouble = TypeHelper.toDouble(bound1);
        final Double bound2AsDouble = TypeHelper.toDouble(bound2);

        return (Comparisons.compare(bound1AsDouble, exprAsDouble)
                * Comparisons.compare(exprAsDouble, bound2AsDouble)) >= 0; // Same sign or zero.
    }


    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


}
