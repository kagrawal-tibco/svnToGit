package com.tibco.cep.query.exec.descriptors.impl;

import java.util.ArrayList;

import com.tibco.cep.query.stream.expression.Expression;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Jul 28, 2008
* Time: 6:50:17 PM
*/
public class SimpleExpressionDescriptor
        extends ExpressionDescriptor {

    private final EvaluatorDescriptor evaluatorDescriptor;


    public SimpleExpressionDescriptor(
            Expression expression,
            AliasMapDescriptor amd,
            EvaluatorDescriptor ed) {

        super(expression, new ArrayList<ExpressionDescriptor>(), amd, ed.getUsedColumnNames());
        this.evaluatorDescriptor = ed;
    }


    public EvaluatorDescriptor getEvaluatorDescriptor() {
        return this.evaluatorDescriptor;
    }


}