package com.tibco.cep.query.stream.expression;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.query.stream.tuple.Tuple;

/*
 * Author: Ashwin Jayaprakash Date: Oct 26, 2007 Time: 11:21:00 AM
 */

public abstract class ComplexExpression extends AbstractExpression {
    protected final Expression[] components;

    public ComplexExpression(Expression[] components) {
        super(collectAliases(components));

        this.components = components;
    }

    private static Map<String, Class<? extends Tuple>> collectAliases(Expression[] components) {
        HashMap<String, Class<? extends Tuple>> retVal =
                new HashMap<String, Class<? extends Tuple>>();

        for (Expression andExp : components) {
            retVal.putAll(andExp.getAliasAndTypes());
        }

        return retVal;
    }

    public Expression[] getComponents() {
        return components;
    }
}
