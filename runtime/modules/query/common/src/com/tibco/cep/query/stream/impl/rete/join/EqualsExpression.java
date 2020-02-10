package com.tibco.cep.query.stream.impl.rete.join;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.query.stream.expression.Expression;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
* Author: Ashwin Jayaprakash Date: Apr 8, 2008 Time: 5:25:56 PM
*/
public class EqualsExpression implements Expression {
    protected final Expression leftExpression;

    protected final Expression rightExpression;

    private Map<String, Class<? extends Tuple>> aliasAndTypes;

    public EqualsExpression(Expression leftExpression, Expression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;

        this.aliasAndTypes = new HashMap<String, Class<? extends Tuple>>();
        this.aliasAndTypes.putAll(leftExpression.getAliasAndTypes());
        this.aliasAndTypes.putAll(rightExpression.getAliasAndTypes());
    }

    public Map<String, Class<? extends Tuple>> getAliasAndTypes() {
        return aliasAndTypes;
    }

    public Expression getLeftExpression() {
        return leftExpression;
    }

    public Expression getRightExpression() {
        return rightExpression;
    }
}
