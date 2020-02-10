package com.tibco.jxpath.operations;

import javax.xml.transform.TransformerException;

import com.tibco.jxpath.Expression;
import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;

public class FilterExpression extends PredicateExpression {


    Expression expression; //This is usually a Variable


    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public XObject eval(XPathContext context) throws TransformerException {

        XObject xobj = expression.eval(context);

        return evalPredicates(context, xobj);

    }

}
