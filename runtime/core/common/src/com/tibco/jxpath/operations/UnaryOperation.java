package com.tibco.jxpath.operations;

import javax.xml.transform.TransformerException;

import com.tibco.jxpath.Expression;
import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 10/31/11 / Time: 5:17 PM
*/
public abstract class UnaryOperation implements Expression {

    protected Expression expr;

    public UnaryOperation()
    {

    }

    public void setExpression(Expression expr) {
        this.expr = expr;
    }

    public XObject eval(XPathContext context) throws TransformerException {
        XObject obj = expr.eval(context);
        return operate(obj);
    }

    public abstract XObject operate(XObject obj);
}
