package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XBoolean;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:49 PM
*/
public class StartsWithFunc implements Function {

    public static final Function FUNCTION = new StartsWithFunc();
    public static final QName FUNCTION_NAME = new QName("starts-with");

    private StartsWithFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 2) throw new XPathFunctionException("Expecting 2 arguments");

        String s0 = args.get(0).str();
        String s1 = args.get(1).str();

        return new XBoolean(s0.startsWith(s1));
    }
}
