package com.tibco.cep.query.stream.impl.expression.string;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.impl.expression.ConstantValueEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
* Author: Karthikeyan Subramanian / Date: Jun 10, 2010 / Time: 11:49:36 AM
*/
public class LikeEvaluator implements ExpressionEvaluator, Serializable {

    protected ExpressionEvaluator leftEvaluator;
    protected ExpressionEvaluator rightEvaluator;
    //final Pattern pattern;
    static final String PATTERN_KEY ="<pattern>";
    private final String patternkey;


    public LikeEvaluator(ExpressionEvaluator leftEvaluator, ExpressionEvaluator rightEvaluator) {
        this.leftEvaluator = leftEvaluator;
        this.rightEvaluator = rightEvaluator;
        this.patternkey = PATTERN_KEY + this.hashCode();
        //String patternString = (String)((ConstantValueEvaluator)rightEvaluator).getConstant();
       // pattern = Pattern.compile(patternString);
    }

    public ExpressionEvaluator[] getOperands() {
        return new ExpressionEvaluator[]{this.leftEvaluator, this.rightEvaluator};
    }

    @Override
    public Object evaluate(
            GlobalContext globalContext, QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return evaluateBoolean(globalContext, queryContext, aliasAndTuples);
    }

    @Override
    public boolean evaluateBoolean(
            GlobalContext globalContext, QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        String leftValue = (String)leftEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        if (leftValue != null) {
            String rightValue = (String) rightEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
            Pattern pattern = (Pattern) queryContext.getGenericStore().get(patternkey);
            if (pattern == null) {
                //String patternString = (String)((ConstantValueEvaluator)rightEvaluator).getConstant();
                pattern = Pattern.compile(rightValue);
                queryContext.getGenericStore().put(patternkey, pattern);
            }
            Matcher matcher = pattern.matcher(leftValue);
            return matcher.matches();
        }
        return false;
    }

    @Override
    public int evaluateInteger(
            GlobalContext globalContext, QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long evaluateLong(
            GlobalContext globalContext, QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float evaluateFloat(
            GlobalContext globalContext, QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double evaluateDouble(
            GlobalContext globalContext, QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }
}
