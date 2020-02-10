package com.tibco.jxpath.operations;

import javax.xml.transform.TransformerException;

import com.tibco.jxpath.Expression;
import com.tibco.jxpath.MutableXPathContext;
import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;

public class StepAxisExpression implements Expression {


    protected boolean abbreviated = false;

    public StepAxisExpression()
    {
    }

    public XObject eval(XPathContext context) throws TransformerException {
    	((MutableXPathContext)context).setAbbreviatedStep(this.abbreviated);
        return context.getCurrentContextNode();
    }



	public void setAbbreviated(boolean abbreviated) {
		this.abbreviated = abbreviated;
	}

}
