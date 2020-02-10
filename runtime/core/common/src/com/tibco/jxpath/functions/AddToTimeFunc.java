package com.tibco.jxpath.functions;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XPathValueParseException;
import com.tibco.jxpath.objects.XTime;

/**
* XPath 2.0 function
*/
public class AddToTimeFunc implements Function {

    public static final Function FUNCTION = new AddToTimeFunc();
    public static final QName FUNCTION_NAME = new QName("add-to-time");

    private AddToTimeFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 4) throw new XPathFunctionException("Expecting 4 arguments, received "+args.size());

        String timeString = args.get(0).str();
        int hour = (int) args.get(1).num();
        int minutes = (int) args.get(2).num();
        double seconds = args.get(3).num();
        
        try {
        	XTime time = XTime.compile(timeString);
			return XTime.create(time.getHourOfDay()+hour, time.getMinutes()+minutes, BigDecimal.valueOf(time.getSeconds().doubleValue()+seconds), null);
		} catch (XPathValueParseException e) {
			throw new XPathFunctionException(e);
		}

    }
}
