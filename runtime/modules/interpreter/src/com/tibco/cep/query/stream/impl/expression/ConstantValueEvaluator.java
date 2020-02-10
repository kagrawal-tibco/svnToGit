package com.tibco.cep.query.stream.impl.expression;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.query.utils.TypeHelper;

import java.io.Serializable;

/*
* Author: Ashwin Jayaprakash Date: Jun 13, 2008 Time: 4:39:41 PM
*/
public class ConstantValueEvaluator implements ExpressionEvaluator, Serializable {


    public static final ConstantValueEvaluator EMPTY_STRING = new ConstantValueEvaluator("");
    public static final ConstantValueEvaluator FALSE = new ConstantValueEvaluator(false);
    public static final ConstantValueEvaluator NULL = new ConstantValueEvaluator(null);
    public static final ConstantValueEvaluator TRUE = new ConstantValueEvaluator(true);

    private static final long serialVersionUID = 1L;


    protected Object constant;


    /**
     * @param constant Has to be {@link java.io.Serializable}.
     */
    public ConstantValueEvaluator(Object constant) {
        this.constant = constant;
    }

    public Object getConstant() {
        return constant;
    }

    public Object evaluate(GlobalContext globalContext, QueryContext queryContext,
                           FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return constant;
    }

    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return TypeHelper.toBoolean(constant);
    }

    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return TypeHelper.toInteger(constant);
    }

    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext,
                             FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return TypeHelper.toLong(constant);
    }

    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return TypeHelper.toFloat(constant);
    }

    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext,
                                 FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return TypeHelper.toDouble(constant);
    }


    public boolean equals(Object that) {
        return (this == that)
                || ((that instanceof ConstantValueEvaluator)
                && ((null == this.constant) && (null == ((ConstantValueEvaluator) that).constant)
                || ((null != this.constant) &&
                this.constant.equals(((ConstantValueEvaluator) that).constant))));
    }


    public int hashCode() {
        return (null == this.constant) ? 0 : this.constant.hashCode();
    }

}
