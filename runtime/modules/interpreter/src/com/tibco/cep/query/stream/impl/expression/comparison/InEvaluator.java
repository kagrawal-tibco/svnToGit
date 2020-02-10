package com.tibco.cep.query.stream.impl.expression.comparison;

import java.io.Serializable;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Aug 14, 2008
* Time: 6:32:01 PM
*/
public class InEvaluator
        implements ExpressionEvaluator, Serializable {


    protected ExpressionEvaluator testedEvaluator;
    protected ExpressionEvaluator[] setMemberEvaluators;


    public InEvaluator(
            ExpressionEvaluator testedEvaluator,
            ExpressionEvaluator[] setMembers) {
        this.testedEvaluator = testedEvaluator;
        this.setMemberEvaluators = setMembers;
    }

    public ExpressionEvaluator[] getSetValues() {
        return setMemberEvaluators;
    }

    public ExpressionEvaluator getEvaluator() {
        return testedEvaluator;
    }

    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return evaluateBoolean(globalContext, queryContext, aliasAndTuples);
    }


    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Object expr = this.testedEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);

        if (null == expr) {
            for (ExpressionEvaluator setMember : this.setMemberEvaluators) {
                if (null == setMember.evaluate(globalContext, queryContext, aliasAndTuples)) {
                    return true;
                }
            }
        } else {
            for (ExpressionEvaluator setMember : this.setMemberEvaluators) {
                if (expr.equals(setMember.evaluate(globalContext, queryContext, aliasAndTuples))) {
                    return true;
                }
            }
        }

        return false;
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
