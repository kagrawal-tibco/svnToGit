package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XString;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:46 PM
*/
public class StringFunc implements Function {

    public static final Function FUNCTION = new StringFunc();
    public static final QName FUNCTION_NAME = new QName("string");

    private StringFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() == 0) throw new XPathFunctionException("Expecting 1 argument, received 0");

        XString str = new XString (args.get(0).str());

        return str;
    }
}
