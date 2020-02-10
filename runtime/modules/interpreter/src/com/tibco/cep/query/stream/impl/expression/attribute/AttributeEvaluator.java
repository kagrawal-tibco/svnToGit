package com.tibco.cep.query.stream.impl.expression.attribute;

import java.io.Serializable;

import com.tibco.cep.query.stream.expression.ExpressionEvaluator;

public abstract class AttributeEvaluator
        implements ExpressionEvaluator, Serializable {


    protected ExpressionEvaluator containerEvaluator;

    
    /**
     * @param containerEvaluator ExpressionEvaluator for the container of the attribute.
     */
    public AttributeEvaluator(
            ExpressionEvaluator containerEvaluator) {
        this.containerEvaluator = containerEvaluator;
    }


    public ExpressionEvaluator getContainerEvaluator() {
        return this.containerEvaluator;
    }


}
