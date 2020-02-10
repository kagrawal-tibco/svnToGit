package com.tibco.cep.query.stream.impl.expression.comparison;

import java.io.Serializable;
import java.util.Calendar;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.query.exec.util.Comparisons;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Aug 14, 2008
* Time: 4:08:45 PM
*/
public class EqualityEvaluator
        implements ExpressionEvaluator, Serializable {


    protected ExpressionEvaluator leftEvaluator;
    protected ExpressionEvaluator rightEvaluator;


    public EqualityEvaluator(ExpressionEvaluator leftEvaluator, ExpressionEvaluator rightEvaluator) {
        this.leftEvaluator = leftEvaluator;
        this.rightEvaluator = rightEvaluator;
    }

    public ExpressionEvaluator[] getOperands() {
        return new ExpressionEvaluator[] {leftEvaluator, rightEvaluator};
    }

    /**
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return evaluateBoolean(globalContext,  queryContext,  aliasAndTuples);
    }


    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Object left = this.leftEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        final Object right = this.rightEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);

        if (left instanceof Boolean) {
            return Comparisons.equalTo((Boolean) left, right);
        }
        else if (left instanceof Calendar) {
            return Comparisons.equalTo((Calendar) left, right);
        }
        else if (left instanceof Entity) {
            return Comparisons.equalTo((Entity) left, right);
        }
        else if (left instanceof Number) {
            return Comparisons.equalTo((Number) left, right);
        }
        else if (left instanceof String) {
            return Comparisons.equalTo((String) left, right);
        }
        else if (right instanceof Boolean) {
            return Comparisons.equalTo((Boolean) right, left);
        }
        else if (right instanceof Calendar) {
            return Comparisons.equalTo((Calendar) right, left);
        }
        else if (right instanceof Entity) {
            return Comparisons.equalTo((Entity) right, left);
        }
        else if (right instanceof Number) {
            return Comparisons.equalTo((Number) right, left);
        }
        else if (right instanceof String) {
            return Comparisons.equalTo((String) right, left);
        }
        else {
            return Comparisons.equalTo(left, right);
        }
    }


    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


}
