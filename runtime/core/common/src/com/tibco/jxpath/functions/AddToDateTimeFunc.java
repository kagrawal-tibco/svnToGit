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
public class AddToDateTimeFunc implements Function {

    public static final Function FUNCTION = new AddToDateTimeFunc();
    public static final QName FUNCTION_NAME = new QName("add-to-dateTime");

    private AddToDateTimeFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 7) throw new XPathFunctionException("Expecting 7 arguments, received "+args.size());

        String dateTimeString= args.get(0).str();
        int year = (int) args.get(1).num();
        int month = (int) args.get(2).num();
        int day = (int) args.get(3).num();
        int hour = (int) args.get(4).num();
        int minutes = (int) args.get(5).num();
        double seconds = args.get(6).num();
        
        try {
			XDateTime dt = XDateTime.compile(dateTimeString);
			return XDateTime.create(dt.getYear()+year, dt.getMonth()+month, dt.getDayOfMonth()+day, dt.getHourOfDay()+hour, dt.getMinutes()+minutes, BigDecimal.valueOf(dt.getSeconds().doubleValue()+seconds), null);
		} catch (XPathValueParseException e) {
			throw new XPathFunctionException(e);
		}

    }
}
