package com.tibco.cep.query.stream.expression;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
 * Author: Ashwin Jayaprakash Date: Oct 26, 2007 Time: 2:03:07 PM
 */

public class ComplexAndExpressionEvaluator implements ExpressionEvaluator {
    protected final ExpressionEvaluator[] andComponents;

    public ComplexAndExpressionEvaluator(ExpressionEvaluator[] andComponents) {
        this.andComponents = andComponents;
    }

    public ExpressionEvaluator[] getAndComponents() {
        return andComponents;
    }

    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        boolean retVal = true;

        for (ExpressionEvaluator evaluator : andComponents) {
            retVal = retVal &&
                    evaluator.evaluateBoolean(globalContext, queryContext, aliasAndTuples);
            if (retVal == false) {
                break;
            }
        }

        return retVal;
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
