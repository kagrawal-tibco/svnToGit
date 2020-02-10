package com.tibco.jxpath.functions;

import java.math.BigDecimal;
import java.util.List;
import java.util.SimpleTimeZone;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XDateTime;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XPathValueParseException;

/**
* XPath 2.0 function
*/
public class CreateDateTimeTimezoneFunc implements Function {

    public static final Function FUNCTION = new CreateDateTimeTimezoneFunc();
    public static final QName FUNCTION_NAME = new QName("create-dateTime-timezone");

    private CreateDateTimeTimezoneFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 8 && args.size() != 9) throw new XPathFunctionException("Expecting 8 or 9 arguments, received "+args.size());

        int argIdx = 0;
        int year = (int) args.get(argIdx++).num();
        int month = (int) args.get(argIdx++).num();
        int day = (int) args.get(argIdx++).num();
        int hour = (int) args.get(argIdx++).num();
        int minutes = (int) args.get(argIdx++).num();
        double seconds = args.get(argIdx++).num();
        int milliseconds = 0;
        if (args.size() == 9) {
        	milliseconds = (int) args.get(argIdx++).num();
        }
        int hoursOffset = (int) args.get(argIdx++).num();
        int minutesOffset = (int) args.get(argIdx++).num();
        
        try {
			return XDateTime.create(year, month, day, hour, minutes, BigDecimal.valueOf(seconds+milliseconds/1000L), new SimpleTimeZone(hoursOffset*60*60*1000+minutesOffset*60*1000, "GMT+00:00"));
		} catch (XPathValueParseException e) {
			throw new XPathFunctionException(e);
		}

    }
}
