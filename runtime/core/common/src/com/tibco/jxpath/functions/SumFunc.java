package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XNumber;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 6:00 PM
*/
public class SumFunc implements Function {

    public static final Function FUNCTION = new SumFunc();
    public static final QName FUNCTION_NAME = new QName("sum");

    private SumFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) {

        double sum = 0.0;

        for (XObject xobj : args)
        {
            sum += xobj.num();
        }

        return new XNumber(sum);

    }
}
