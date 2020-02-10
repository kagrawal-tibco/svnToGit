package com.tibco.cep.query.stream.expression;

import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.impl.expression.comparison.EqualityEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
* Author: Ashwin Jayaprakash Date: Apr 8, 2008 Time: 11:22:45 AM
*/
public class EqualsExpressionEvaluator implements ExpressionEvaluator {
    protected ExpressionEvaluator leftExpressionEvaluator;

    protected ExpressionEvaluator rightExpressionEvaluator;

    /**
     * @param leftExpressionEvaluator
     * @param rightExpressionEvaluator
     */
    public EqualsExpressionEvaluator(ExpressionEvaluator leftExpressionEvaluator,
                                     ExpressionEvaluator rightExpressionEvaluator) {
        this.leftExpressionEvaluator = leftExpressionEvaluator;
        this.rightExpressionEvaluator = rightExpressionEvaluator;
    }

    public int extractLeftHashCode(GlobalContext globalContext, DefaultQueryContext queryContext,
                                   FixedKeyHashMap<String, Tuple> aliasAndTuples) {
        Object o = leftExpressionEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);

        if (o == null) {
            return 0;
        }

        return o.hashCode();
    }

    public int extractRightHashCode(GlobalContext globalContext, DefaultQueryContext queryContext,
                                    FixedKeyHashMap<String, Tuple> aliasAndTuples) {
        Object o = rightExpressionEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);

        if (o == null) {
            return 0;
        }

        return o.hashCode();
    }

    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
//        Object left =
//                leftExpressionEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
//
//        Object right =
//                rightExpressionEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
//
//        return (left == right) || (left != null && left.equals(right));

        EqualityEvaluator equalityEvaluator = new EqualityEvaluator(leftExpressionEvaluator, rightExpressionEvaluator);

        return equalityEvaluator.evaluateBoolean(globalContext, queryContext, aliasAndTuples);
    }

    public Boolean evaluate(GlobalContext globalContext, QueryContext queryContext,
                            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return evaluateBoolean(globalContext, queryContext, aliasAndTuples);
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
