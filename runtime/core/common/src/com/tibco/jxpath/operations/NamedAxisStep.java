package com.tibco.jxpath.operations;

import javax.xml.namespace.QName;
import javax.xml.transform.TransformerException;

import com.tibco.jxpath.AxisName;
import com.tibco.jxpath.NodeResolver;
import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XObjectFactory;

public class NamedAxisStep extends PredicateExpression {

    private QName qName;
    private AxisName axisName = AxisName.Child;


    public void setQName(QName qName)
    {
        this.qName = qName;
    }

    @Override
    public XObject eval(XPathContext context) throws TransformerException {
        //System.out.println(qName);

        NodeResolver nodeResolver = context.getNodeResolver();

        XObject currObject = context.getCurrentContextNode();
        boolean abbr = context.isAbbreviatedStep();

        Object ret = nodeResolver.getChild(currObject, qName, abbr, axisName);

        XObject xobj = XObjectFactory.create(ret);

        return evalPredicates(context, xobj);
    }


    public void setAxisName(AxisName axisName) {
        this.axisName = axisName;
    }
}
