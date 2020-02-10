package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XNumber;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:59 PM
*/
public class NumberFunc implements Function {

    public static final Function FUNCTION = new NumberFunc();
    public static final QName FUNCTION_NAME = new QName("number");

    private NumberFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() == 0) throw new XPathFunctionException("Expecting 1 argument, received 0");

        return  XNumber.class.cast( args.get(0));

    }
}
