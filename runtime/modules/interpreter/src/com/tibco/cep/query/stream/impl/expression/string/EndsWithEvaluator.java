package com.tibco.cep.query.stream.impl.expression.string;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.impl.expression.comparison.EqualityEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;


/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Aug 14, 2008
* Time: 4:08:45 PM
*/
public class EndsWithEvaluator
    extends EqualityEvaluator {


    public EndsWithEvaluator(
            ExpressionEvaluator leftEvaluator,
            ExpressionEvaluator rightEvaluator) {
        super(leftEvaluator, rightEvaluator);
    }


    public boolean evaluateBoolean(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {

        final Object left = this.leftEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        final Object right = this.rightEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);

        if (null == left) {
            return null == right;
        }
        else return (null != right)
                && String.valueOf(left).endsWith(String.valueOf(right));
    }

}
