package com.tibco.cep.query.stream.expression;

/*
* Author: Ashwin Jayaprakash Date: Oct 26, 2007 Time: 11:20:48 AM
*/

public class NotExpression extends AbstractExpression {
    protected final Expression innerExpression;

    public NotExpression(Expression innerExpression) {
        super(innerExpression.getAliasAndTypes());

        this.innerExpression = innerExpression;
    }

    public Expression getInnerExpression() {
        return innerExpression;
    }
}
