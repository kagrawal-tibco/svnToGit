package com.tibco.jxpath.functions;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XDateTime;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XPathValueParseException;

/**
* XPath 2.0 function
*/
public class CreateDateTimeFunc implements Function {

    public static final Function FUNCTION = new CreateDateTimeFunc();
    public static final QName FUNCTION_NAME = new QName("create-dateTime");

    private CreateDateTimeFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 6 && args.size() != 7) throw new XPathFunctionException("Expecting 6 or 7 arguments, received "+args.size());

        int year = (int) args.get(0).num();
        int month = (int) args.get(1).num();
        int day = (int) args.get(2).num();
        int hour = (int) args.get(3).num();
        int minutes = (int) args.get(4).num();
        double seconds = args.get(5).num();
        int milliseconds = 0;
        if (args.size() == 7) {
        	milliseconds = (int) args.get(6).num();
        }
        
        try {
			return XDateTime.create(year, month, day, hour, minutes, BigDecimal.valueOf(seconds+milliseconds/1000L), null);
		} catch (XPathValueParseException e) {
			throw new XPathFunctionException(e);
		}

    }
}
