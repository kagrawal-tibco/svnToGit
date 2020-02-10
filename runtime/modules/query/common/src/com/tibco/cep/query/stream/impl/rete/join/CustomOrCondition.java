package com.tibco.cep.query.stream.impl.rete.join;

import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;

/*
 * Author: Ashwin Jayaprakash Date: Oct 26, 2007 Time: 5:37:34 PM
 */

public class CustomOrCondition extends CustomCondition {
    protected ExpressionEvaluator[] evaluators;

    public CustomOrCondition(Rule rule, Identifier[] identifiers,
                             String regionName, String queryName,
                             ExpressionEvaluator[] evaluators) {
        super(rule, identifiers, regionName, queryName);

        this.evaluators = evaluators;
    }

    @Override
    protected boolean doEvaluate() {
        boolean answer = false;

        for (ExpressionEvaluator evaluator : evaluators) {
            answer = answer
                    || evaluator.evaluateBoolean(globalContext, queryContext, aliasAndTuples);

            if (answer) {
                break;
            }
        }

        return answer;
    }
}
