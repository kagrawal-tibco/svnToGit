package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XNumber;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:57 PM
*/
public final class AbsFunc implements Function {

    public static final Function FUNCTION = new AbsFunc();
    public static final QName FUNCTION_NAME = new QName("abs");

    private AbsFunc() {}

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 1) throw new XPathFunctionException("Expecting 1 arguments, received "+args.size());

        XObject arg0 = args.get(0);

        return new XNumber(Math.abs(arg0.num()));
    }
}
