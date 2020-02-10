package com.tibco.cep.query.stream.expression;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
 * Author: Ashwin Jayaprakash Date: Oct 26, 2007 Time: 2:03:07 PM
 */

public class ComplexOrExpressionEvaluator implements ExpressionEvaluator {
    protected final ExpressionEvaluator[] orComponents;

    public ComplexOrExpressionEvaluator(ExpressionEvaluator[] andComponents) {
        this.orComponents = andComponents;
    }

    public ExpressionEvaluator[] getOrComponents() {
        return orComponents;
    }

    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        boolean retVal = false;

        for (ExpressionEvaluator evaluator : orComponents) {
            retVal = retVal ||
                    evaluator.evaluateBoolean(globalContext, queryContext, aliasAndTuples);
            if (retVal) {
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
