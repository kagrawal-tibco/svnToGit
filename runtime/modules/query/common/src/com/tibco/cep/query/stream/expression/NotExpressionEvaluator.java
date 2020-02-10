package com.tibco.cep.query.stream.expression;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
 * Author: Ashwin Jayaprakash Date: Oct 30, 2007 Time: 2:24:14 PM
 */

public class NotExpressionEvaluator implements ExpressionEvaluator {
    protected final ExpressionEvaluator innerExpressionEval;

    public NotExpressionEvaluator(ExpressionEvaluator innerExpressionEval) {
        this.innerExpressionEval = innerExpressionEval;
    }

    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        // Invert.
        return !innerExpressionEval.evaluateBoolean(globalContext, queryContext, aliasAndTuples);
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
