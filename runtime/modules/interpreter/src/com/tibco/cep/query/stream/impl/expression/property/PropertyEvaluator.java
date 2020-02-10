package com.tibco.cep.query.stream.impl.expression.property;

import java.io.Serializable;

import com.tibco.cep.query.stream.expression.ExpressionEvaluator;

public abstract class PropertyEvaluator
        implements ExpressionEvaluator, Serializable {

    protected ExpressionEvaluator containerEvaluator;
    protected String propertyName;


    /**
     * @param containerEvaluator ExpressionEvaluator for the container of the property.
     * @param propertyName       String name of the property.
     */
    public PropertyEvaluator(
            ExpressionEvaluator containerEvaluator,
            String propertyName) {
        this.containerEvaluator = containerEvaluator;
        this.propertyName = propertyName;
    }


    public ExpressionEvaluator getContainerEvaluator() {
        return this.containerEvaluator;
    }


    public String getPropertyName() {
        return propertyName;
    }

}
