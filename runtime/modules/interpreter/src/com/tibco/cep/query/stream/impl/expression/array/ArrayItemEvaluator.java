package com.tibco.cep.query.stream.impl.expression.array;

import java.io.Serializable;

import com.tibco.cep.query.stream.expression.ExpressionEvaluator;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Aug 14, 2008
* Time: 4:08:45 PM
*/
public abstract class ArrayItemEvaluator
        implements ExpressionEvaluator, Serializable {


    protected ExpressionEvaluator arrayEvaluator;
    protected ExpressionEvaluator indexEvaluator;


    public ArrayItemEvaluator(ExpressionEvaluator arrayEvaluator, ExpressionEvaluator indexEvaluator) {
        this.arrayEvaluator = arrayEvaluator;
        this.indexEvaluator = indexEvaluator;
    }



}
