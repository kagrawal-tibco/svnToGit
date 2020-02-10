package com.tibco.cep.query.exec.descriptors.impl;

import java.util.LinkedHashMap;
import java.util.List;

import com.tibco.cep.query.stream.expression.Expression;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Jan 14, 2009
* Time: 4:58:18 PM
*/
public class ExpressionDescriptor {


    protected final AliasMapDescriptor aliasMapDescriptor;
    private final LinkedHashMap<String, String> usedColumnNamesToType;
    protected Expression expression;
    private List<ExpressionDescriptor> childExpressions;


    public ExpressionDescriptor(
            Expression expression,
            List<ExpressionDescriptor> childExpressions,
            AliasMapDescriptor amd,
            LinkedHashMap<String, String> usedColumnNamesToType) {
        this.aliasMapDescriptor = amd;
        this.expression = expression;
        this.usedColumnNamesToType = usedColumnNamesToType;
        this.childExpressions = childExpressions;
    }


    public AliasMapDescriptor getAliasMapDescriptor() {
        return this.aliasMapDescriptor;
    }


    public Expression getExpression() {
        return this.expression;
    }


    public LinkedHashMap<String, String> getUsedColumnNames() {
        return this.usedColumnNamesToType;
    }


    public List<ExpressionDescriptor> getChildExpressionDescriptors() {
        return childExpressions;
    }


    public void setExpression(Expression expression) {
        this.expression = expression;
    }

}
