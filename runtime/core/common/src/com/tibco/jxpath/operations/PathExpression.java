package com.tibco.jxpath.operations;

import java.util.LinkedList;

import javax.xml.namespace.QName;
import javax.xml.transform.TransformerException;

import com.tibco.jxpath.Expression;
import com.tibco.jxpath.MutableXPathContext;
import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XObjectFactory;

public class PathExpression implements Expression {

	LinkedList<Expression> steps = new LinkedList<Expression>();
	
	public LinkedList<Expression> getSteps() {
		return steps;
	}


    @Override
    public XObject eval(XPathContext context) throws TransformerException {
        XObject ret = null;
        if (steps.getFirst() instanceof StepAxisExpression) {
        	StepAxisExpression exp = (StepAxisExpression) steps.getFirst();
        	if (exp.abbreviated) {
        		((MutableXPathContext)context).setCurrentContextNode(XObjectFactory.create(context.getNodeResolver().resolveVariable(new QName("$current"))));
        	}
        }
        for (Expression expr : steps) {
            ret = expr.eval(context);
            ((MutableXPathContext)context).setCurrentContextNode(ret);
        }

        return ret;

    }



}
