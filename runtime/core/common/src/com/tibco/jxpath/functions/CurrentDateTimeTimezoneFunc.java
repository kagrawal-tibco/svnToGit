package com.tibco.jxpath.functions;

import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XDateTime;
import com.tibco.jxpath.objects.XObject;

/**
* XPath 2.0 function
*/
public class CurrentDateTimeTimezoneFunc implements Function {

    public static final Function FUNCTION = new CurrentDateTimeTimezoneFunc();
    public static final QName FUNCTION_NAME = new QName("current-dateTime-timezone");

    private CurrentDateTimeTimezoneFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 2) throw new XPathFunctionException("Expecting 2 arguments, received "+args.size());

        int hrOffset = (int) args.get(0).num();
        int minOffset = (int) args.get(1).num();
        TimeZone offset = new SimpleTimeZone(hrOffset*60*60*1000+minOffset*60*1000, "GMT+00:00");
        return new XDateTime(new Date(), offset);

    }
}
