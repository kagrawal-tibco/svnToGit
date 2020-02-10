package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XDateTime;
import com.tibco.jxpath.objects.XObject;

/**
* XPath 2.0 function
*/
public class CurrentDateTimeFunc implements Function {

    public static final Function FUNCTION = new CurrentDateTimeFunc();
    public static final QName FUNCTION_NAME = new QName("current-dateTime");

    private CurrentDateTimeFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 0) throw new XPathFunctionException("Expecting 0 arguments, received "+args.size());

        return  XDateTime.currentDateTime();

    }
}
