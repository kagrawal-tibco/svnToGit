package com.tibco.jxpath.operations;

import javax.xml.transform.TransformerException;

import com.tibco.jxpath.Expression;
import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 10/31/11 / Time: 2:53 PM
*/
public abstract class BinaryOperation implements Expression {

    protected Expression left;
    protected Expression right;

    public BinaryOperation()
    {
    }

    public void setLeft(Expression left)
    {
        this.left = left;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    public XObject eval(XPathContext context) throws TransformerException
    {

        XObject leftResult = this.left.eval(context);
        XObject rightResult = this.right.eval(context);


        return operate(leftResult, rightResult);

    }

    public abstract XObject operate(XObject leftResult, XObject rightResult);
}
