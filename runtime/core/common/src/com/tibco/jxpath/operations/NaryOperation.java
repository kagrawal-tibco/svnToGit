package com.tibco.jxpath.operations;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;

import com.tibco.jxpath.Expression;
import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 11/3/11 / Time: 10:45 AM
*/
public abstract class NaryOperation implements Expression {

    List<Expression> expressions = new ArrayList<Expression>();


    public void addExpression(Expression expression)
    {
        expressions.add(expression);
    }

    @Override
    public XObject eval(XPathContext context) throws TransformerException {

        List<XObject> resultList = new ArrayList<XObject>();

        for (Expression expr: expressions) {
            resultList.add(expr.eval(context));
        }

        return operate(context, resultList);
    }

    public abstract XObject operate(XPathContext context, List<XObject> args) throws TransformerException;
}
