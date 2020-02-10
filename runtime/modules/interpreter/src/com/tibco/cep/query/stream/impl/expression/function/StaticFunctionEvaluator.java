package com.tibco.cep.query.stream.impl.expression.function;

import com.tibco.cep.query.stream.expression.ExpressionEvaluator;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Aug 14, 2008
* Time: 6:32:01 PM
*/
public class StaticFunctionEvaluator
        extends FunctionEvaluator {

    // Required by Serializer
    public StaticFunctionEvaluator() {
    }

    public StaticFunctionEvaluator(
            Class functionClass,
            String functionName,
            Class[] functionArgClasses,
            ExpressionEvaluator[] argEvaluators) {
        super(functionClass, functionName, functionArgClasses, argEvaluators);
    }


    protected void initializeInvocationTarget() {
        this.invocationTarget = null;
    }


}
