package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XDateTime;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XTime;

/**
* XPath 2.0 function
*/
public class TimestampFunc implements Function {

    public static final Function FUNCTION = new TimestampFunc();
    public static final QName FUNCTION_NAME = new QName("timestamp");

    private TimestampFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 0) throw new XPathFunctionException("Expecting 0 arguments, received "+args.size());
        XDateTime currentDateTime = XDateTime.currentDateTime();
        return new XTime(currentDateTime.getHourOfDay(),currentDateTime.getMinutes(),currentDateTime.getSeconds(),currentDateTime.getTimeZone()); 
    }
}
